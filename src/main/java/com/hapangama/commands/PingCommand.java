package com.hapangama.commands;

import com.slack.api.bolt.App;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.slack.api.bolt.response.Response;

public class PingCommand {
    private static final Logger logger = LoggerFactory.getLogger(PingCommand.class);

    public PingCommand(App app) {
        // Handle /ping slash command
        app.command("/ping", (req, ctx) -> {
            try {
                logger.debug("Received /ping command from user: {}", req.getPayload().getUserId());
                logger.debug("Channel ID: {}", req.getPayload().getChannelId());
                logger.debug("Full payload: {}", req.getPayload());

                Response response = ctx.ack(r -> r.text("Pong!"));
                logger.debug("Response: {}", response);
                return response;
            } catch (Exception e) {
                logger.error("Error processing /ping command", e);
                throw e;
            }
        });
    }
}