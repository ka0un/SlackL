package com.hapangama.utils;

import com.deepl.api.DeepLException;
import com.deepl.api.Translator;

public class CustomTranslator {
    static Translator translator;

    public static String translate(String text, String sourceLang, String targetLang, boolean includeOriginal) {
        System.out.println("[Translation Service] [⚙️] Translating.... " );

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

        return includeOriginal ? text + "\n\n" + translatedText : translatedText;
    }
}