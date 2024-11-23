package com.hapangama;


public interface Constants {

    // make sure to include a token that start with xoxb
    String SLACK_BOT_TOKEN = "";
    String SLACK_SIGNING_SECRET = "";
    String SLACK_CLIENT_SECRET = "";
    String DEEPL_API_KEY = "";
    int SLACK_PORT = 3000;

    public static void install() {

        if (System.getenv("SLACK_BOT_TOKEN") == null || System.getenv("SLACK_BOT_TOKEN").isEmpty()) {

            System.err.println("⚠️ Environment variables are not already set - using default Constants [you can ignore this warning if you configured Constants.java]");
            System.setProperty("SLACK_BOT_TOKEN", SLACK_BOT_TOKEN);
            System.setProperty("SLACK_SIGNING_SECRET", SLACK_SIGNING_SECRET);
            System.setProperty("SLACK_CLIENT_SECRET", SLACK_CLIENT_SECRET);
            System.setProperty("DEEPL_API_KEY", DEEPL_API_KEY);
            System.setProperty("SLACK_PORT", String.valueOf(SLACK_PORT));
        }

    }

}
