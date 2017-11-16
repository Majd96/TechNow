package com.majd.technow.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by majd on 11/12/17.
 */

public class ArticleDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "articleList.db";

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ArticleContract.ArticleEntry.TABLE_NAME + " (" +
                        ArticleContract.ArticleEntry.COLUMN_AUthor + " TEXT, " +
                        ArticleContract.ArticleEntry.COLUMN_TITLE + " TEXT, " +
                        ArticleContract.ArticleEntry.COLUMN_DESCRIPTION + " TEXT, " +
                        ArticleContract.ArticleEntry.COLUMN_IMAGE_URL + " TEXT, " +
                        ArticleContract.ArticleEntry.COLUMN_ARTICLE_URL + " TEXT, " +
                        ArticleContract.ArticleEntry.COLUMN_PUBLISHED_DATE + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
