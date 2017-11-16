package com.majd.technow.ui;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.majd.technow.R;
import com.majd.technow.adapters.ArticleAdapter;
import com.majd.technow.database.ArticleContract;
import com.majd.technow.fetchData.ArticleAsyncTask;
import com.majd.technow.fetchData.FetchArticels;
import com.majd.technow.models.Article;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.majd.technow.appwidget.UpdateWidgetService.startUpdateTeckWidgets;
import static com.majd.technow.ui.ChoiceFragment.isTwoPane;


/**
 * Created by majd on 11/10/17.
 */

public class RecentFragment extends Fragment implements ArticleAdapter.OnArticleClickListener {
    private String website;
    private static final String TAG = TopFragment.class.getName();
    private ArrayList<Article> articles;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArticleAdapter articleAdapter;
    private View rootView;

    public RecentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            articles = new ArrayList<>();
        } else {
            articles = savedInstanceState.getParcelableArrayList("articles");
            website = savedInstanceState.getString("website");
        }
        rootView = inflater.inflate(R.layout.fragment_recent, container, false);
        if (website.equals(new String("verge"))) {
            FetchArticels.sourceValue = "the-verge";
            FetchArticels.sortByValue = "latest";
        }
        if (website.equals(new String("TechRadar"))) {
            FetchArticels.sourceValue = "techradar";
            FetchArticels.sortByValue = "latest";
        }
        if (website.equals(new String("Engadget"))) {
            FetchArticels.sourceValue = "engadget";
            FetchArticels.sortByValue = "latest";
        }
        if (website.equals(new String("TechCrunch"))) {
            FetchArticels.sourceValue = "techcrunch";
            FetchArticels.sortByValue = "latest";
        }

        if (isNetworkAvailable()) {
            recyclerView = rootView.findViewById(R.id.rv_recent);
            if (isTwoPane) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            try {
                articles = new ArticleAsyncTask().execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d(TAG + "**", articles.get(0).getAuthor());

            articleAdapter = new ArticleAdapter(articles, getContext(), this);
            recyclerView.setAdapter(articleAdapter);


        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.networkFalier), Toast.LENGTH_LONG).show();


        }

        return rootView;
    }


    public void setWebsite(String website) {
        this.website = website;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    @Override
    public void onArticleClick(int clickedItemIndex, View view) {

        int imageButtonTag = (Integer) view.getTag();
        if (imageButtonTag == R.drawable.unsaved_article)


        {
            ContentValues cv = new ContentValues();
            cv.put(ArticleContract.ArticleEntry.COLUMN_AUthor, articles.get(clickedItemIndex).getAuthor());
            cv.put(ArticleContract.ArticleEntry.COLUMN_TITLE, articles.get(clickedItemIndex).getTitle());
            cv.put(ArticleContract.ArticleEntry.COLUMN_DESCRIPTION, articles.get(clickedItemIndex).getDescription());
            cv.put(ArticleContract.ArticleEntry.COLUMN_IMAGE_URL, articles.get(clickedItemIndex).getImageUrl());
            cv.put(ArticleContract.ArticleEntry.COLUMN_ARTICLE_URL, articles.get(clickedItemIndex).getUrl());
            cv.put(ArticleContract.ArticleEntry.COLUMN_PUBLISHED_DATE, articles.get(clickedItemIndex).getPublishedDate());


            view.setBackground(getResources().getDrawable(R.drawable.saved_article));
            Uri uri = getContext().getContentResolver().insert(ArticleContract.ArticleEntry.CONTENT_URI, cv);
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.insert), Toast.LENGTH_LONG).show();
            view.setTag(R.drawable.saved_article);


        } else {
            view.setBackground(getResources().getDrawable(R.drawable.unsaved_article));
            view.setTag(R.drawable.unsaved_article);
            getContext().getContentResolver().delete(ArticleContract.ArticleEntry.CONTENT_URI,
                    ArticleContract.ArticleEntry.COLUMN_DESCRIPTION + "=?",
                    new String[]{articles.get(clickedItemIndex).getDescription()});
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.delete), Toast.LENGTH_LONG).show();


        }

        startUpdateTeckWidgets(getContext());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("articles", articles);
        outState.putString("website", getWebsite());

    }

    public String getWebsite() {
        return website;
    }
}
