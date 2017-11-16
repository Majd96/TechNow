package com.majd.technow.fetchData;

import android.os.AsyncTask;
import android.util.Log;

import com.majd.technow.models.Article;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by majd on 11/10/17.
 */

public class ArticleAsyncTask extends AsyncTask<Void, Void, ArrayList<Article>> {
    ArrayList<Article> articleArrayList;

    @Override
    protected ArrayList<Article> doInBackground(Void... voids) {

        URL url = FetchArticels.buildUrl();
        Log.d("koko", url + "");
        try {
            String jsonString = FetchArticels.getResponseFromHttpUrl(url);
            articleArrayList = FetchArticels.getJsonFromString(jsonString);
            return articleArrayList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
