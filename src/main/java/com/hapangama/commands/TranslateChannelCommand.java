package com.hapangama.commands;

import com.hapangama.models.TranslateChannel;
import com.hapangama.utils.DatabaseUtil;
import com.slack.api.bolt.App;
import lombok.Data;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class TranslateChannelCommand {
    private final ConcurrentHashMap<String, TranslateChannel> channelConfig;

    public TranslateChannelCommand(App app) {
        this.channelConfig = new ConcurrentHashMap<>();

        // Load existing data from the repository into memory
        try {
            DatabaseUtil.getAllTranslateChannels().forEach(channel -> channelConfig.put(channel.getChannelId(), channel));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Command to enable/disable translation for a channel
        app.command("/translate-channel", (req, ctx) -> {
            String channelId = req.getPayload().getChannelId();
            String[] args;

            if (req.getPayload().getText() !=null){
                args = req.getPayload().getText().split("\\s+");
            }else {
                args = new String[0];
            }

            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "en":
                    case "enable":
                        TranslateChannel enableChannel = new TranslateChannel(channelId, null, false, false, false);
                        channelConfig.put(channelId, enableChannel);
                        try {
                            DatabaseUtil.saveTranslateChannel(enableChannel);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        return ctx.ack(String.format(
                                "⚙️ *Configuration Updated*\n" +
                                        "• Channel: <#%s>\n" +
                                        "• Setting: `Translation`\n" +
                                        "• Value: `Enabled ✅`\n\n" +
                                        "_execute `/translate-channel lang <code>` to set the language_" +
                                        "_execute `/translate-channel langs` to list all supported languages_",
                                channelId
                        ));

                    case "dis":
                    case "disable":
                        channelConfig.remove(channelId);
                        try {
                            DatabaseUtil.deleteTranslateChannel(channelId);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        return ctx.ack(String.format(
                                "⚙️ *Configuration Updated*\n" +
                                        "• Channel: <#%s>\n" +
                                        "• Setting: `Translation`\n" +
                                        "• Value: `Disabled ❌`\n\n" +
                                        "_All messages in this channel will no longer be translated_",
                                channelId
                        ));

                    case "lang":
                    case "language":
                        if (args.length > 1) {
                            TranslateChannel languageChannel = channelConfig.getOrDefault(channelId, new TranslateChannel(channelId, null, false, false, false));
                            languageChannel.setLang(args[1]);
                            channelConfig.put(channelId, languageChannel);
                            try {
                                DatabaseUtil.saveTranslateChannel(languageChannel);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            return ctx.ack(String.format(
                                    "⚙️ *Configuration Updated*\n" +
                                            "• Channel: <#%s>\n" +
                                            "• Setting: `Language`\n" +
                                            "• Value: `%s`\n\n" +
                                            "_All messages in this channel will now be translated to `%s`_",
                                    channelId,
                                    args[1],
                                    args[1]
                            ));
                        } else {
                            return ctx.ack("Usage: /translate-channel lang <code>");
                        }

                        case "original":
                        case "includeoriginal":
                            if (args.length > 1) {
                                boolean includeOriginal = Boolean.parseBoolean(args[1]);
                                TranslateChannel includeOriginalChannel = channelConfig.getOrDefault(channelId, new TranslateChannel(channelId, null, false, false, false));
                                includeOriginalChannel.setIncludeOriginal(includeOriginal);
                                channelConfig.put(channelId, includeOriginalChannel);
                                try {
                                    DatabaseUtil.saveTranslateChannel(includeOriginalChannel);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                                return ctx.ack(String.format(
                                        "⚙️ *Configuration Updated*\n" +
                                                "• Channel: <#%s>\n" +
                                                "• Setting: `Include Original`\n" +
                                                "• Value: `%s`\n\n" +
                                                "_Original messages will %s be included in translations for this channel_",
                                        channelId,
                                        includeOriginal,
                                        includeOriginal ? "now" : "no longer"
                                ));
                            }else {
                                return ctx.ack("Usage: /translate-channel includeOriginal <true|false>");
                            }

                    case "asreply":
                        if (args.length > 1) {
                            boolean asReply = Boolean.parseBoolean(args[1]);
                            TranslateChannel asReplyChannel = channelConfig.getOrDefault(channelId, new TranslateChannel(channelId, null, false, false, false));
                            asReplyChannel.setAsReply(asReply);
                            channelConfig.put(channelId, asReplyChannel);
                            try {
                                DatabaseUtil.saveTranslateChannel(asReplyChannel);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            return ctx.ack(String.format(
                                    "⚙️ *Configuration Updated*\n" +
                                            "• Channel: <#%s>\n" +
                                            "• Setting: `As Reply`\n" +
                                            "• Value: `%s`\n\n" +
                                            "_Translations will %s be posted as replies for this channel_",
                                    channelId,
                                    asReply,
                                    asReply ? "now" : "no longer"
                            ));
                        }else {
                            return ctx.ack("Usage: /translate-channel asReply <true|false>");
                        }

                   case "asuser":
                        if (args.length > 1) {
                            boolean asUser = Boolean.parseBoolean(args[1]);
                            TranslateChannel asUserChannel = channelConfig.getOrDefault(channelId, new TranslateChannel(channelId, null, false, false, false));
                            asUserChannel.setAsUser(asUser);
                            channelConfig.put(channelId, asUserChannel);
                            try {
                                DatabaseUtil.saveTranslateChannel(asUserChannel);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            return ctx.ack(String.format(
                                    "⚙️ *Configuration Updated*\n" +
                                            "• Channel: <#%s>\n" +
                                            "• Setting: `As User`\n" +
                                            "• Value: `%s`\n\n" +
                                            "_Translations will %s be posted as the user for this channel_",
                                    channelId,
                                    asUser,
                                    asUser ? "now" : "no longer"
                            ));
                        }else {
                            return ctx.ack("Usage: /translate-channel asUser <true|false>");
                        }

                    case "langs":
                    case "languages":
                        return ctx.ack(
                                "Supported Languages:\n" +
                                        "• en - English\n" +
                                        "• ja - Japanese\n" +
                                        "• ko - Korean\n" +
                                        "• zh - Chinese\n" +
                                        "• de - German\n" +
                                        "• fr - French\n" +
                                        "• es - Spanish\n" +
                                        "• it - Italian\n" +
                                        "• nl - Dutch\n" +
                                        "• pl - Polish\n" +
                                        "• pt - Portuguese\n" +
                                        "• ru - Russian\n" +
                                        "• bg - Bulgarian\n" +
                                        "• da - Danish\n" +
                                        "• fi - Finnish\n" +
                                        "• hu - Hungarian\n" +
                                        "• lv - Latvian\n" +
                                        "• lt - Lithuanian\n" +
                                        "• ro - Romanian\n" +
                                        "• sk - Slovak\n" +
                                        "• sl - Slovenian\n" +
                                        "• sv - Swedish\n" +
                                        "• cs - Czech\n" +
                                        "• el - Greek\n" +
                                        "• no - Norwegian\n" +
                                        "• tr - Turkish\n" +
                                        "• id - Indonesian\n" +
                                        "• et - Estonian\n" +
                                        "• ar - Arabic\n"
                        );



                    case "all":
                    case "list":
                        StringBuilder list = new StringBuilder("Translation enabled for the following channels:\n");
                        channelConfig.forEach((key, value) -> {
                            list.append("```" + key).append(" - ").append(value.getLang()).append("```\n");
                        });
                        return ctx.ack(list.toString());


                }
            }

            return ctx.ack("```" +
                    "• /translate-channel enable - Enable translation for this channel\n" +
                    "• /translate-channel disable - Disable translation for this channel\n" +
                    "• /translate-channel lang <code> - Set the language for translations\n" +
                    "• /translate-channel includeOriginal <true|false> - Include original messages in translations\n" +
                    "• /translate-channel asReply <true|false> - Post translations as replies\n" +
                    "• /translate-channel list - List all channels with translation enabled\n" +
                    "• /translate-channel help - Display this help message\n" +
                    "```");
        });
    }

}