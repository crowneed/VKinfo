package com.lex.vk_info.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by lex on 02.07.2018.
 */

public class NetworkUtils {
     private final static String VK_API_BASE_URL = "https://api.vk.com";
     private final static String VK_USERS_GET = "/method/users.get";
     private final static String PARAM_USER_ID = "user_ids";
     private final static String PARAM_VERSION = "v";
     private final static String ACCESS_TOKEN = "access_token";


    public static URL generateURL(String userId){
        Uri builtUri = Uri.parse(VK_API_BASE_URL + VK_USERS_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_ID, userId)
                .appendQueryParameter(PARAM_VERSION, "5.8")
                .appendQueryParameter(ACCESS_TOKEN,"22f8b48422f8b48422f8b484a3229dbd34222f822f8b48479c1da6d0258fc25eeeb8a63")
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponceFromURL(URL url) throws IOException{
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); //финт ушами,чтобы вывести первую строку с помощью регулярных выражений

            boolean hasInput = scanner.hasNext(); //Есть ли входные данные

            if(hasInput){return scanner.next();}
            else {return null;}
        } finally {
            urlConnection.disconnect();
        }

    }
}
