package com.majd.technow.fetchData;

import android.net.Uri;

import com.majd.technow.models.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by majd on 11/10/17.
 */

public class FetchArticels {
    final static String BASE_URL = "https://newsapi.org/v1/";
    final static String QUERY = "articles";
    final static String PARAM_SOURCE = "source";
    final static String PARAM_KEy = "apiKey";
    final static String PARAM_SORT_BY = "sortBy";
    final static String API_KEY_VALUE = "8861dad4513840518c4900ba29ed6504";
    public static String sourceValue;
    public static String sortByValue;

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(QUERY)
                .appendQueryParameter(PARAM_SOURCE, sourceValue)
                .appendQueryParameter(PARAM_SORT_BY, sortByValue)
                .appendQueryParameter(PARAM_KEy, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<Article> getJsonFromString(String jsonString) throws JSONException {
        String author, title, description, url, imageUrl, publishedDate;


        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray articlesArray = jsonObject.getJSONArray("articles");
        ArrayList<Article> articlesList = new ArrayList<>();

        for (int i = 0; i < articlesArray.length(); i++) {
            JSONObject articleData = articlesArray.getJSONObject(i);

            author = articleData.getString("author");
            title = articleData.getString("title");
            description = articleData.getString("description");
            url = articleData.getString("url");
            imageUrl = articleData.getString("urlToImage");
            publishedDate = articleData.getString("publishedAt");


            articlesList.add(new Article(author, title, description, url, imageUrl, publishedDate));
            //Log.d("movie title" , moviesList.get(i).getTitle() );

        }
        return articlesList;
    }


}
