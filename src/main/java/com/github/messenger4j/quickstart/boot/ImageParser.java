package com.github.messenger4j.quickstart.boot;


import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;

/**
 * Created by ilyeskhamlichi on 11/10/2017.
 */
public class ImageParser {

    public static File urlToFile(String url, String path) throws IOException {
        URL imageURL = new URL(url);
        File f =new File(path);
        f.createNewFile();

        FileUtils.copyURLToFile(imageURL, f);

        return f;
    }

    public static String fileToString(File imageFile) throws TesseractException {
        ITesseract instance = new Tesseract();
        instance.setDatapath("/");
        instance.setLanguage("eng");


        return instance.doOCR(imageFile);


    }

    public static String imageUrlToString(String url, String path) throws IOException, TesseractException {
        File imageFile = urlToFile(url, path);
        return fileToString(imageFile);

    }



}
