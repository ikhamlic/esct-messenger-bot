package com.github.messenger4j.quickstart.boot;


import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.LanguageListOption;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.common.collect.ImmutableList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public class TranslateText {
    /**
     * Detect the language of input text.
     *
     * @param sourceText source text to be detected for language
     * @param out print stream
     */
    public static void detectLanguage(String sourceText, PrintStream out) {


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
        out.printf("%s", detectionMaxConfidence.getLanguage());


    }

    public static String detectLanguage(String sourceText) throws UnsupportedEncodingException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);

        detectLanguage(sourceText, ps);
        String output = os.toString("UTF8");
        return output;
    }

    /**
     * Translates the source text in any language to English.
     *
     * @param sourceText source text to be translated
     * @param out print stream
     */
    public static void translateText(String sourceText, PrintStream out) {
        Translate translate = createTranslateService();
        Translation translation = translate.translate(sourceText);
        out.printf("Source Text:\n\t%s\n", sourceText);
        out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText());
    }

    /**
     * Translate the source text from source to target language.
     * Make sure that your project is whitelisted.
     *
     * @param sourceText source text to be translated
     * @param sourceLang source language of the text
     * @param targetLang target language of translated text
     * @param out print stream
     */
    public static void translateTextWithOptionsAndModel(
            String sourceText,
            String sourceLang,
            String targetLang,
            PrintStream out) {

        Translate translate = createTranslateService();
        TranslateOption srcLang = TranslateOption.sourceLanguage(sourceLang);
        TranslateOption tgtLang = TranslateOption.targetLanguage(targetLang);

        // Use translate `model` parameter with `base` and `nmt` options.
        TranslateOption model = TranslateOption.model("nmt");

        Translation translation = translate.translate(sourceText, srcLang, tgtLang, model);
        out.printf("Source Text:\n\tLang: %s, Text: %s\n", sourceLang, sourceText);
        out.printf("TranslatedText:\n\tLang: %s, Text: %s\n", targetLang,
                translation.getTranslatedText());
    }


    /**
     * Translate the source text from source to target language.
     *
     * @param sourceText source text to be translated
     * @param sourceLang source language of the text
     * @param targetLang target language of translated text
     * @param out print stream
     */
    public static void translateTextWithOptions(
            String sourceText,
            String sourceLang,
            String targetLang,
            PrintStream out) {

        Translate translate = createTranslateService();
        TranslateOption srcLang = TranslateOption.sourceLanguage(sourceLang);
        TranslateOption tgtLang = TranslateOption.targetLanguage(targetLang);

        Translation translation = translate.translate(sourceText, srcLang, tgtLang);
        out.printf("Source Text:\n\tLang: %s, Text: %s\n", sourceLang, sourceText);
        out.printf("TranslatedText:\n\tLang: %s, Text: %s\n", targetLang,
                translation.getTranslatedText());
    }

    /**
     * Displays a list of supported languages and codes.
     *
     * @param out print stream
     * @param tgtLang optional target language
     */
    public static void displaySupportedLanguages(PrintStream out, Optional<String> tgtLang) {
        Translate translate = createTranslateService();
        LanguageListOption target = LanguageListOption.targetLanguage(tgtLang.orElse("en"));
        List<Language> languages = translate.listSupportedLanguages(target);

        for (Language language : languages) {
            out.printf("Name: %s, Code: %s\n", language.getName(), language.getCode());
        }
    }

    /**
     * Create Google Translate API Service.
     *
     * @return Google Translate Service
     */
    public static Translate createTranslateService() {
        return TranslateOptions.newBuilder().setApiKey("AIzaSyBlCX4hc8-s7F0d29pNRf_KJ3-0AVpRGis").build().getService();
        //return TranslateOptions.newBuilder().build().getService();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("*********");
        System.out.println(detectLanguage("hello"));
        System.out.println("*********");
        String[] args2 = {"detect", "salut"};
        args = args2;
        String command = args[0];
        String text;

        if (command.equals("detect")) {
            text = args[1];
            TranslateText.detectLanguage(text, System.out);
        } else if (command.equals("translate")) {
            text = args[1];
            try {
                String sourceLang = args[2];
                String targetLang = args[3];
                TranslateText.translateTextWithOptions(text, sourceLang, targetLang, System.out);
            } catch (ArrayIndexOutOfBoundsException ex) {
                TranslateText.translateText(text, System.out);
            }
        } else if (command.equals("langsupport")) {
            try {
                String target = args[1];
                TranslateText.displaySupportedLanguages(System.out, Optional.of(target));
            } catch (ArrayIndexOutOfBoundsException ex) {
                TranslateText.displaySupportedLanguages(System.out, Optional.empty());
            }
        }
    }
}