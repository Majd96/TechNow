package com.majd.technow.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by majd on 11/12/17.
 */

public class ArticleContract {

    public final static String CONTENT_AUTHORITY = "com.majd.technow";
    public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ARTICLE = "article";


    public static class ArticleEntry implements BaseColumns {

        public static final Uri CONTENT_URI =

                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLE).build();
        public static final String TABLE_NAME = "articleTable";
        public static final String COLUMN_AUthor = "author";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_IMAGE_URL = "imageUrl";

        public static final String COLUMN_ARTICLE_URL = "articleUrl";

        public static final String COLUMN_PUBLISHED_DATE = "publishedDate";

        //function that build uri for an article depends on its id
        public static Uri buildArticleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }
}
