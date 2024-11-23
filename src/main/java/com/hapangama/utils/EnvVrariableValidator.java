package com.hapangama.utils;

public class EnvVrariableValidator {
    public EnvVrariableValidator() {

        if (System.getProperty("SLACK_BOT_TOKEN") == null || System.getProperty("SLACK_BOT_TOKEN").isEmpty()) {
            System.err.println("⚠️ WARNING: SLACK_BOT_TOKEN is not set!");
            throw new RuntimeException("SLACK_BOT_TOKEN is not set!");
        }

        if (System.getProperty("SLACK_SIGNING_SECRET") == null || System.getProperty("SLACK_SIGNING_SECRET").isEmpty()) {
            System.err.println("⚠️ WARNING: SLACK_SIGNING_SECRET is not set!");
            throw new RuntimeException("SLACK_SIGNING_SECRET is not set!");
        }

        if (System.getProperty("SLACK_CLIENT_SECRET") == null || System.getProperty("SLACK_CLIENT_SECRET").isEmpty()) {
            System.err.println("⚠️ WARNING: SLACK_CLIENT_SECRET is not set!");
            throw new RuntimeException("SLACK_CLIENT_SECRET is not set!");
        }


        if (System.getProperty("DEEPL_API_KEY") == null || System.getProperty("DEEPL_API_KEY").isEmpty()) {
            System.err.println("⚠️ WARNING: DEEPL_API_KEY is not set!");
            throw new RuntimeException("DEEPL_API_KEY is not set!");
        }


        if (System.getProperty("SLACK_PORT") == null || System.getProperty("SLACK_PORT").isEmpty()) {
            System.err.println("⚠️ WARNING: SLACK_PORT is not set!");
            throw new RuntimeException("SLACK_PORT is not set!");
        }



    }

}
