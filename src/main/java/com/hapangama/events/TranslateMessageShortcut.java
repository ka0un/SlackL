package com.hapangama.events;

import com.deepl.api.LanguageCode;
import com.hapangama.menus.TranslateMenu;
import com.slack.api.bolt.App;



public class TranslateMessageShortcut {


    public TranslateMessageShortcut(App app) {

        // Register message shortcut
        app.messageShortcut("translate_message_sk", (req, ctx) -> {


            var payload = req.getPayload();
            var messageText = payload.getMessage().getText();

            try {
                TranslateMenu.TranslateInputMenu(payload.getTriggerId(), ctx, messageText, LanguageCode.English);
            } catch (Exception e) {
                System.err.println("Error processing message: " + e.getMessage());
            }
            return ctx.ack();
        });


    }
}