package com.hapangama;

import com.hapangama.commands.manager.CommandsManager;
import com.hapangama.events.manager.EventsManager;
import com.hapangama.menus.TranslateMenu;
import com.hapangama.utils.DatabaseUtil;
import com.hapangama.utils.EnvVrariableValidator;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;
import lombok.Data;

import java.sql.SQLException;


@Data
public class MainManager {
    App app;
    AppConfig config;
    SlackAppServer server;
    CommandsManager commandsManager;
    EventsManager eventsManager;
    TranslateMenu translateMenu;

    public MainManager() throws SQLException {

        Constants.install();
        EnvVrariableValidator envVrariableValidator = new EnvVrariableValidator();

        DatabaseUtil.createTable();

        config = AppConfig.builder()
                .singleTeamBotToken(System.getProperty("SLACK_BOT_TOKEN"))
                .signingSecret(System.getProperty("SLACK_SIGNING_SECRET"))
                .clientSecret(System.getProperty("SLACK_CLIENT_SECRET"))
                .build();

        app = new App(config);

        translateMenu = new TranslateMenu(app);
        commandsManager = new CommandsManager(app);
        eventsManager = new EventsManager(app);

        startServer();

    }

    private void startServer() {
        server = new SlackAppServer(app, "/", Integer.parseInt(System.getProperty("SLACK_PORT")));
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
