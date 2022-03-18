package com.example.testapplication.Utils;

import android.net.Uri;

import com.example.testapplication.Utils.News_model.News;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String BASE_URL="https://newsapi.org/v2";
    private static final String TYPE_OF_SEARCH="/top-headlines";
    private static final String QUERY="q";
    private static final String API_KEY="apiKey";

    public static URL generateURL(String query) {
        Uri builtUri = Uri.parse(BASE_URL + TYPE_OF_SEARCH)
                .buildUpon()
                .appendQueryParameter(QUERY, query).appendQueryParameter(API_KEY, "a9a0dc5c0dd945e99c5233af23e9edb9")
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) Gecko/20100101 Firefox/31.0");

        try {
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput=scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else return null;
        } finally {
            connection.disconnect();
        }

    }

    public static News ResponseParse(String response) {
        Gson gson=new Gson();
        News news = gson.fromJson(response, News.class);
        return news;
    }
}
