package com.github.messenger4j.quickstart.boot;

/**
 * Created by ilyeskhamlichi on 12/10/2017.
 */
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * API doesn't work... contacted Azure support.
 */
@Deprecated
public class BingSpellCheck {

    private String accountKey;

    public String response(String query) throws Exception {
        String bingUrl = "https://api.cognitive.microsoft.com/bing/v5.0/spellcheck/?text=" + java.net.URLEncoder.encode(query);


        System.out.println("* accountKey = " + accountKey);
        byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
        String accountKeyEnc = new String(accountKeyBytes);

        URL url = new URL(bingUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Ocp-Apim-Subscription-Key", accountKey);
        con.setRequestProperty("Host", "api.cognitive.microsoft.com");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);




        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();



        return response.toString();

    }

    public void setApiKey(String accountKey) {
        this.accountKey = accountKey;
    }



}