package com.hapangama.events.manager;


import com.hapangama.events.MessageEventListener;
import com.hapangama.events.TranslateMessageShortcut;
import com.slack.api.bolt.App;
import lombok.Data;

@Data
public class EventsManager {

    TranslateMessageShortcut messageEventListener3;
    MessageEventListener messageEventListener;
    public EventsManager(App app) {
        messageEventListener = new MessageEventListener(app);
        messageEventListener3 = new TranslateMessageShortcut(app);
    }
}
