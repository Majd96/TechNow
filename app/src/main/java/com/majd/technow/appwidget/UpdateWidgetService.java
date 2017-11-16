package com.majd.technow.appwidget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.majd.technow.database.ArticleContract;

/**
 * Created by majd on 11/14/17.
 */

public class UpdateWidgetService extends IntentService {
    public static final String UPDATE_THE_WIDGET_ACTION =
            "package com.majd.technow.appwidget.action.updateWidget";
    String savedArticles = "Saved Articles: \n\n";

    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }


    public static void startUpdateTeckWidgets(Context context) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.setAction(UPDATE_THE_WIDGET_ACTION);

        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (UPDATE_THE_WIDGET_ACTION.equals(action)) {
                handleActionUpdateWidget();
            }
        }
    }

    private void handleActionUpdateWidget() {
        String title;
        Cursor cursor = getContentResolver().query(ArticleContract.ArticleEntry.CONTENT_URI,
                new String[]{ArticleContract.ArticleEntry.COLUMN_TITLE},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_TITLE));
                savedArticles += title + "\n\n";
            } while (cursor.moveToNext());
        }
        cursor.close();


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, TeckNowWidget.class));
        TeckNowWidget.updateTeckWidgets(this, appWidgetManager, appWidgetIds, savedArticles);


    }
}
