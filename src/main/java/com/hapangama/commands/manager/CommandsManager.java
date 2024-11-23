package com.hapangama.commands.manager;

import com.hapangama.commands.PingCommand;
import com.hapangama.commands.TranslateCommand;
import com.slack.api.bolt.App;
import lombok.Data;

@Data
public class CommandsManager {
    PingCommand pingCommand;
    TranslateCommand quickTranslateHandler;

    public CommandsManager(App app) {
        pingCommand = new PingCommand(app);
        quickTranslateHandler = new TranslateCommand(app);
    }
}
