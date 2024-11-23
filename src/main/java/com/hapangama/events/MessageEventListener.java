package com.hapangama.events;

import com.hapangama.commands.TranslateChannelCommand;
import com.hapangama.models.TranslateChannel;
import com.hapangama.utils.CustomTranslator;
import com.slack.api.bolt.App;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.event.MessageEvent;


public class MessageEventListener {
    TranslateChannelCommand translateChannelCommand;

    public MessageEventListener(App app) {
        translateChannelCommand = new TranslateChannelCommand(app);

        app.event(MessageEvent.class, (payload, ctx) -> {
            MessageEvent event = payload.getEvent();

            //if translation is enabled for the channel
            if (translateChannelCommand.getChannelConfig().get(event.getChannel()) == null) {
                return ctx.ack();
            }

            //ignore null or empty messages
            if (event.getText() == null || event.getText().isEmpty() || event.getText().trim().isEmpty()) {
                return ctx.ack();
            }

            TranslateChannel translateChannel = translateChannelCommand.getChannelConfig().get(event.getChannel());
            String lang = translateChannel.getLang() == null ? "en-US" : translateChannel.getLang();
            boolean includeOriginal = translateChannel.isIncludeOriginal();
            String output = CustomTranslator.translate(event.getText(), null, lang, includeOriginal);

            if (event.getText() != null && !event.getText().isEmpty()) {

                if (translateChannel.isAsReply()) {
                    ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
                            .channel(event.getChannel())
                            .text(output)
                            .threadTs(event.getTs())
                    );
                }else {
                    ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
                            .channel(event.getChannel())
                            .text(output)
                    );
                }


            }

            return ctx.ack();
        });
    }
}
