# SlackL - DeepL Translations for Slack

## Overview
SlackL is a Slack bot that provides DeepL translations for messages in Slack. This guide will help you configure and deploy the Slack bot.

# Project Features

<table>
<tr>
  <td width="50%">
    <h3 align="center">Feature 1: Smart Search</h3>
    <p align="center">
      <img src="/api/placeholder/400/300" alt="Smart Search Feature" width="400">
    </p>
    <p align="center">
      Intelligent search functionality with auto-complete and filters. Helps users quickly find exactly what they're looking for with real-time suggestions and advanced filtering options.
    </p>
  </td>
  <td width="50%">
    <h3 align="center">Feature 2: Analytics Dashboard</h3>
    <p align="center">
      <img src="/api/placeholder/400/300" alt="Analytics Dashboard" width="400">
    </p>
    <p align="center">
      Comprehensive analytics dashboard with interactive charts and customizable reports. Monitor key metrics and track performance in real-time.
    </p>
  </td>
</tr>
<tr>
  <td width="50%">
    <h3 align="center">Feature 3: Team Collaboration</h3>
    <p align="center">
      <img src="/api/placeholder/400/300" alt="Team Collaboration" width="400">
    </p>
    <p align="center">
      Built-in collaboration tools for seamless teamwork. Share projects, leave comments, and work together in real-time with your team members.
    </p>
  </td>
  <td width="50%">
    <h3 align="center">Feature 4: Cloud Integration</h3>
    <p align="center">
      <img src="/api/placeholder/400/300" alt="Cloud Integration" width="400">
    </p>
    <p align="center">
      Seamless cloud integration with popular services. Automatically sync and backup your data across multiple platforms and devices.
    </p>
  </td>
</tr>
</table>

## Prerequisites
- Java 17
- Maven
- A Slack workspace
- DeepL API key

## Step-by-Step Instructions

### 1. Configure Slack Bot

#### Create a Slack App
1. Go to the [Slack API](https://api.slack.com/apps) and create a new app.
2. Choose "From an app manifest" and select the workspace.
3. Copy the contents of the provided `manifest.json` file and paste it into the manifest editor.
4. Replace the placeholders in the `manifest.json` file with temporary URLs (e.g., `https://example.com`).
5. Click "Next" and review the settings.
6. Click "Create" to create the app.

#### Install the App
1. Navigate to **OAuth & Permissions**.
2. Install the app to your workspace and copy the **Bot User OAuth Token**.

### 2. Configure Constants

#### Update `Constants.java`
Edit the `src/main/java/com/hapangama/Constants.java` file and set the following constants:
```java
package com.hapangama;

public interface Constants {
    String SLACK_BOT_TOKEN = "your-slack-bot-token";  // make sure to include the token that start with xoxb
    String SLACK_SIGNING_SECRET = "your-slack-signing-secret";
    String SLACK_CLIENT_SECRET = "your-slack-client-secret";
    String DEEPL_API_KEY = "your-deepl-api-key";
    int SLACK_PORT = 3000;
    //...
}
```

### 3. Build and Deploy

#### Build the Project
1. Open a terminal and navigate to the project directory.
2. Run the following command to build the project:
   ```sh
   mvn clean package
   ```

#### Run the Application
1. Run the application using the following command:
   ```sh
   java -jar target/SlackL-1.0-SNAPSHOT.jar
   ```

### 4. Update Slack App with Actual URLs

1. After deploying your application, obtain the actual URLs for your server endpoints.
2. Go back to the [Slack API](https://api.slack.com/apps) and select your app.
3. Navigate to **Event Subscriptions** and update the **Request URL** with your actual server endpoint.
4. Navigate to **Slash Commands** and update the URLs for each command with your actual server endpoint.
5. Save the changes.

Your Slack bot should now be up and running with the correct URLs. You can test it by using the configured slash commands in your Slack workspace.


### Updated `manifest.json`

```json
{
    "display_information": {
        "name": "SlackL",
        "description": "DeepL Translations For Slack",
        "background_color": "#142230"
    },
    "features": {
        "bot_user": {
            "display_name": "SlackL",
            "always_online": false
        },
        "shortcuts": [
            {
                "name": "Translate Message",
                "type": "message",
                "callback_id": "translate_message_sk",
                "description": "Translate Message"
            }
        ],
        "slash_commands": [
            {
                "command": "/ping",
                "url": "https://your-domain.com:port",
                "description": "Test Translation Bot",
                "should_escape": false
            },
            {
                "command": "/translate",
                "url": "https://your-domain.com:port",
                "description": "Quickly Translate Any Text From UI",
                "should_escape": false
            },
            {
                "command": "/translate-channel",
                "url": "https://your-domain.com:port",
                "description": "Automatically Translate Messages Sent to Channel",
                "should_escape": false
            }
        ]
    },
    "oauth_config": {
        "scopes": {
            "user": [
                "chat:write"
            ],
            "bot": [
                "app_mentions:read",
                "assistant:write",
                "bookmarks:read",
                "channels:history",
                "channels:join",
                "channels:read",
                "chat:write",
                "chat:write.customize",
                "chat:write.public",
                "commands",
                "reactions:read",
                "reactions:write",
                "users:read",
                "users:read.email"
            ]
        }
    },
    "settings": {
        "event_subscriptions": {
            "request_url": "https://your-domain.com:port",
            "bot_events": [
                "app_mention",
                "message.channels"
            ]
        },
        "interactivity": {
            "is_enabled": true,
            "request_url": "https://your-domain.com:port"
        },
        "org_deploy_enabled": false,
        "socket_mode_enabled": false,
        "token_rotation_enabled": false
    }
}
