package com.hapangama.commands;

import com.deepl.api.LanguageCode;
import com.hapangama.menus.TranslateMenu;
import com.slack.api.bolt.App;


public class TranslateCommand {

    public TranslateCommand(App app) {

        // Handle the /quickTranslate command
        app.command("/translate", (req, ctx) -> {
            try {
                TranslateMenu.TranslateInputMenu(req.getPayload().getTriggerId(), ctx, null, LanguageCode.Japanese);
                return ctx.ack();
            } catch (Exception e) {
                System.err.println("Error opening translation modal: " + e.getMessage());
                e.printStackTrace();
                return ctx.ack();
            }
        });

    }







}