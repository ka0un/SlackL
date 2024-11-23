package com.hapangama.utils;

import com.deepl.api.DeepLException;
import com.deepl.api.Translator;

public class CustomTranslator {
    static Translator translator;
    static TranslationCache cache = new TranslationCache();

    public static String translate(String text, String sourceLang, String targetLang, boolean includeOriginal) {
        String inputHash = TranslationCache.generateHash(text, targetLang);
        String cachedTranslation = cache.get(inputHash);

        if (cachedTranslation != null) {
            System.out.println("[Translation Service] [🍀] Providing cached translation for: " + inputHash);
            return includeOriginal ? text + "\n\n" + cachedTranslation : cachedTranslation;
        }

        System.out.println("[Translation Service] [⚙️] Translating: " + inputHash);

        String translatedText;
        String apiKey = System.getProperty("DEEPL_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("WARNING: DeepL API Key is not set!");
        }

        translator = new com.deepl.api.Translator(apiKey);

        try {
            translatedText = translator.translateText(text, sourceLang, targetLang).getText();
        } catch (DeepLException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        cache.put(inputHash, translatedText);
        return includeOriginal ? text + "\n\n" + translatedText : translatedText;
    }
}