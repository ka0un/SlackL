package com.hapangama.menus;

import com.deepl.api.LanguageCode;
import com.hapangama.utils.CustomTranslator;
import com.hapangama.utils.LangUtils;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.Context;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.view.ViewState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;

public class TranslateMenu{

    App app;
    public TranslateMenu(App app){
        this.app = app;
        translateOutputMenu(app);
    }

    public static void TranslateInputMenu(String triggerId, Context ctx, String InputText, String suggestTranslateTo) throws IOException, SlackApiException {

        OptionObject suggestTranslateToOption = null;

        if (suggestTranslateTo == null) {
            suggestTranslateTo = LanguageCode.English;
        }

        for (var lang : LangUtils.getLanguages()) {
            if (lang.getValue().equals(suggestTranslateTo)) {
                suggestTranslateToOption = lang;
                break;
            }
        }

        if (suggestTranslateToOption == null) {
            suggestTranslateToOption = LangUtils.getLanguages().get(0);
        }

        final OptionObject finalSuggestTranslateToOption = suggestTranslateToOption;

        ctx.client().viewsOpen(r -> r
                .triggerId(triggerId)
                .view(view(v -> v
                        .type("modal")
                        .callbackId("advanced_translate_modal")
                        .title(viewTitle(title -> title
                                .type("plain_text")
                                .text("Translate Text")
                        ))
                        .submit(viewSubmit(submit -> submit
                                .type("plain_text")
                                .text("Translate")
                        ))
                        .blocks(asBlocks(
                                // Target Language Dropdown
                                input(input -> input
                                        .blockId("target_language")
                                        .label(plainText("Target Language", true))
                                        .element(staticSelect(ss -> ss
                                                .actionId("target_lang_select")
                                                .placeholder(plainText("Select target language", true))
                                                .options(LangUtils.getLanguages()) // Ensure `LangUtils.getLanguages()` returns a List<OptionObject>
                                                .initialOption(finalSuggestTranslateToOption)
                                                .focusOnLoad(false)
                                        ))
                                ),
                                // Text Input
                                input(input -> input
                                        .blockId("translation_block")
                                        .element(plainTextInput(pti -> pti
                                                .actionId("translation_input")
                                                .multiline(true)
                                                .initialValue(InputText == null ? "" : InputText) // Ensure `InputText` is properly defined in scope
                                                .focusOnLoad(true)
                                        ))
                                        .label(plainText("Text to Translate", true))
                                )
                        ))
                ))
        );

    }

    private void translateOutputMenu(App app) {
        app.viewSubmission("advanced_translate_modal", (req, ctx) -> {
            try {
                var payload = req.getPayload();
                var values = payload.getView().getState().getValues();

                // Extract values with null checks
                String targetLang = extractValue(values, "target_language", "target_lang_select");
                String inputText = extractValue(values, "translation_block", "translation_input");

                // Validation checks
                if (targetLang == null || inputText == null) {
                    return ctx.ack(r -> r
                            .responseAction("errors")
                            .errors(Map.of(
                                    "target_language", "Please select a target language",
                                    "translation_block", "Please enter text to translate"
                            ))
                    );
                }

                // Prepare output text
                String outputText = CustomTranslator.translate(inputText, null, targetLang, false);

                // Use response_action to push a new modal
                return ctx.ack(r -> r
                        .responseAction("push")
                        .view(view(v -> v
                                .type("modal")
                                .callbackId("translated_result")
                                .title(viewTitle(title -> title.type("plain_text").text("Translation Result")))
                                .close(viewClose(close -> close.type("plain_text").text("Close")))
                                .blocks(asBlocks(
                                        section(section -> section
                                                .text(MarkdownTextObject.builder()
                                                        .text(inputText)
                                                        .build())
                                        ),
                                        section(section -> section
                                                .text(MarkdownTextObject.builder()
                                                        .text(outputText)
                                                        .build())
                                        )
                                ))
                        ))
                );

            } catch (Exception e) {
                System.err.println("Translation submission error: " + e.getMessage());
                e.printStackTrace();

                return ctx.ack(r -> r
                        .responseAction("errors")
                        .errors(Map.of(
                                "translation_block", "An error occurred: " + e.getMessage()
                        ))
                );
            }
        });
    }

    // Helper method to safely extract values
    private String extractValue(Map<String, Map<String, ViewState.Value>> values, String blockId, String actionId) {
        try {
            ViewState.Value value = values.get(blockId).get(actionId);
            return value.getSelectedOption() != null
                    ? value.getSelectedOption().getValue()
                    : value.getValue();
        } catch (Exception e) {
            System.err.println("Error extracting value for " + blockId + "." + actionId);
            return null;
        }
    }
}
