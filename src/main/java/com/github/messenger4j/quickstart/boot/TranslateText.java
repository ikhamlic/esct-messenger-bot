package com.github.messenger4j.quickstart.boot;


import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.LanguageListOption;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.common.collect.ImmutableList;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public class TranslateText {


    /**
     * Detects the language of input text.
     *
     * @param sourceText source text to be detected for language
     * @return language detected
     */

    public static String detectLanguage(String sourceText) throws UnsupportedEncodingException {
        Translate translate = createTranslateService();
        List<Detection> detections = translate.detect(ImmutableList.of(sourceText));
        float maxConfidence = -1.0f;
        Detection detectionMaxConfidence = detections.get(0);
        for (Detection detection: detections) {
            if (detection.getConfidence() > maxConfidence) {
                maxConfidence = detection.getConfidence();
                detectionMaxConfidence = detection;

            }
        }

        return detectionMaxConfidence.getLanguage();
    }



    /**
     * Translates the source text in any language to English.
     *
     * @param sourceText source text to be translated
     * @return translated text
     */
    public static String translateText(String sourceText) {
        Translate translate = createTranslateService();
        Translation translation = translate.translate(sourceText);

        return translation.getTranslatedText();


    }





    /**
     * Create Google Translate API Service.
     *
     * @return Google Translate Service
     */
    public static Translate createTranslateService() {
        return TranslateOptions.newBuilder().build().getService();
    }


}