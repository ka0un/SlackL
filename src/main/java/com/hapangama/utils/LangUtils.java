package com.hapangama.utils;

import com.deepl.api.LanguageCode;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;

import java.util.ArrayList;
import java.util.List;

public class LangUtils {
    public static List<OptionObject> getLanguages() {
        List<OptionObject> languages = new ArrayList<>();
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("English").build()).value(LanguageCode.EnglishAmerican).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Japanese").build()).value(LanguageCode.Japanese).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Korean").build()).value(LanguageCode.Korean).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Chinese").build()).value(LanguageCode.Chinese).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("German").build()).value(LanguageCode.German).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("French").build()).value(LanguageCode.French).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Spanish").build()).value(LanguageCode.Spanish).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Italian").build()).value(LanguageCode.Italian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Dutch").build()).value(LanguageCode.Dutch).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Polish").build()).value(LanguageCode.Polish).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Portuguese").build()).value(LanguageCode.Portuguese).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Russian").build()).value(LanguageCode.Russian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Bulgarian").build()).value(LanguageCode.Bulgarian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Danish").build()).value(LanguageCode.Danish).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Finnish").build()).value(LanguageCode.Finnish).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Hungarian").build()).value(LanguageCode.Hungarian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Latvian").build()).value(LanguageCode.Latvian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Lithuanian").build()).value(LanguageCode.Lithuanian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Romanian").build()).value(LanguageCode.Romanian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Slovak").build()).value(LanguageCode.Slovak).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Slovenian").build()).value(LanguageCode.Slovenian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Swedish").build()).value(LanguageCode.Swedish).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Czech").build()).value(LanguageCode.Czech).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Greek").build()).value(LanguageCode.Greek).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Norwegian").build()).value(LanguageCode.Norwegian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Turkish").build()).value(LanguageCode.Turkish).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Indonesian").build()).value(LanguageCode.Indonesian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Estonian").build()).value(LanguageCode.Estonian).build());
        languages.add(OptionObject.builder().text(PlainTextObject.builder().text("Arabic").build()).value(LanguageCode.Arabic).build());
        return languages;
    }
}
