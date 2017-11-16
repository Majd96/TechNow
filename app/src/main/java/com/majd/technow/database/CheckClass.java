package com.majd.technow.database;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by majd on 11/14/17.
 */

public class CheckClass {
    public static int isSaved(Context context, String desc) {
        Cursor cursor = context.getContentResolver().query(
                ArticleContract.ArticleEntry.CONTENT_URI,
                null,
                ArticleContract.ArticleEntry.COLUMN_DESCRIPTION + " = ?",
                new String[]{desc},
                null
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }
}
