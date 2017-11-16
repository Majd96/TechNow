package com.majd.technow.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.majd.technow.R;
import com.majd.technow.adapters.ArticleAdapter;
import com.majd.technow.adapters.SavedArticlesAdapter;
import com.majd.technow.database.ArticleContract;
import com.majd.technow.models.Article;

import java.util.ArrayList;

import static com.majd.technow.adapters.SavedArticlesAdapter.mCursor;
import static com.majd.technow.appwidget.UpdateWidgetService.startUpdateTeckWidgets;
import static com.majd.technow.ui.ChoiceFragment.isTwoPane;


/**
 * Created by majd on 11/10/17.
 */

public class SavedArticlesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, SavedArticlesAdapter.OnArticleClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SavedArticlesAdapter articlesAdapter;
    private View rootview;

    public static final int LOADER_ID = 13;

    public SavedArticlesFragment() {
        //required public empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_saved_arcticles, container, false);
        recyclerView = rootview.findViewById(R.id.rv_saved);
        if (isTwoPane) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        articlesAdapter = new SavedArticlesAdapter(getContext(), this);
        recyclerView.setAdapter(articlesAdapter);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        return rootview;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getContext(),
                        ArticleContract.ArticleEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);


        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        articlesAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        articlesAdapter.swapCursor(null);
    }

    @Override
    public void onArticleClick(int clickedItemIndex, View view) {
        view.setBackground(getResources().getDrawable(R.drawable.unsaved_article));
        view.setTag(R.drawable.unsaved_article);
        Cursor cursor = mCursor;
        cursor.moveToPosition(clickedItemIndex);
        String descr = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_DESCRIPTION));

        getContext().getContentResolver().delete(ArticleContract.ArticleEntry.CONTENT_URI,
                ArticleContract.ArticleEntry.COLUMN_DESCRIPTION + "=?",
                new String[]{descr});
        Toast.makeText(getContext(), getContext().getResources().getString(R.string.delete), Toast.LENGTH_LONG).show();
        startUpdateTeckWidgets(getContext());
    }

}

