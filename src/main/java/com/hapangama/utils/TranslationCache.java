package com.hapangama.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TranslationCache {
    private static final int MAX_CACHE_SIZE = 5000;
    private static final int REMOVE_COUNT = 50;
    private final LinkedHashMap<String, String> cache;

    public TranslationCache() {
        this.cache = new LinkedHashMap<>(MAX_CACHE_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }

    public String get(String inputHash) {
        return cache.get(inputHash);
    }

    public void put(String inputHash, String outputText) {
        if (cache.size() >= MAX_CACHE_SIZE) {
            removeOldEntries();
        }
        cache.put(inputHash, outputText);
    }

    private void removeOldEntries() {
        int count = 0;
        for (String key : cache.keySet()) {
            cache.remove(key);
            count++;
            if (count >= REMOVE_COUNT) {
                break;
            }
        }
    }

    public static String generateHash(String inputText, String lang) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String combinedInput = inputText + lang;
            byte[] hashBytes = digest.digest(combinedInput.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }
}