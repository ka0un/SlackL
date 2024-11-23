package com.hapangama.utils;


import com.hapangama.models.TranslateChannel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:h2:./data/translateDB";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load H2 driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void saveTranslateChannel(TranslateChannel channel) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM TranslateChannel WHERE channelId = ?";
        String insertSql = "INSERT INTO TranslateChannel (channelId, includeOriginal, lang, asReply, asUser) VALUES (?, ?, ?, ?, ?)";
        String updateSql = "UPDATE TranslateChannel SET includeOriginal = ?, lang = ?, asReply = ?, asUser = ? WHERE channelId = ?";

        try (Connection conn = getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, channel.getChannelId());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setBoolean(1, channel.isIncludeOriginal());
                        updateStmt.setString(2, channel.getLang());
                        updateStmt.setBoolean(3, channel.isAsReply());
                        updateStmt.setBoolean(4, channel.isAsUser());
                        updateStmt.setString(5, channel.getChannelId());
                        updateStmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setString(1, channel.getChannelId());
                        insertStmt.setBoolean(2, channel.isIncludeOriginal());
                        insertStmt.setString(3, channel.getLang());
                        insertStmt.setBoolean(4, channel.isAsReply());
                        insertStmt.setBoolean(5, channel.isAsUser());
                        insertStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public static TranslateChannel getTranslateChannel(String channelId) throws SQLException {
        String sql = "SELECT * FROM TranslateChannel WHERE channelId = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, channelId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TranslateChannel(
                            rs.getString("channelId"),
                            rs.getString("lang"),
                            rs.getBoolean("includeOriginal"),
                            rs.getBoolean("asReply"),
                            rs.getBoolean("asUser")
                    );
                }
            }
        }
        return null;
    }

    public static List<TranslateChannel> getAllTranslateChannels() throws SQLException {
        List<TranslateChannel> channels = new ArrayList<>();
        String sql = "SELECT * FROM TranslateChannel";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                channels.add(new TranslateChannel(
                        rs.getString("channelId"),
                        rs.getString("lang"),
                        rs.getBoolean("includeOriginal"),
                        rs.getBoolean("asReply"),
                        rs.getBoolean("asUser")
                ));
            }
        }
        return channels;
    }

    public static void updateTranslateChannel(TranslateChannel channel) throws SQLException {
        String sql = "UPDATE TranslateChannel SET includeOriginal = ?, lang = ?, asReply = ?, asUser = ? WHERE channelId = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, channel.isIncludeOriginal());
            stmt.setString(2, channel.getLang());
            stmt.setBoolean(3, channel.isAsReply());
            stmt.setBoolean(4, channel.isAsUser());
            stmt.setString(5, channel.getChannelId());
            stmt.executeUpdate();
        }
    }

    public static void deleteTranslateChannel(String channelId) throws SQLException {
        String sql = "DELETE FROM TranslateChannel WHERE channelId = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, channelId);
            stmt.executeUpdate();
        }
    }

    public static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS TranslateChannel (" +
                "channelId VARCHAR(255) PRIMARY KEY, " +
                "lang VARCHAR(255), " +
                "includeOriginal BOOLEAN, " +
                "asReply BOOLEAN, " +
                "asUser BOOLEAN)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
}