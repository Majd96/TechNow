package com.majd.technow.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.majd.technow.R;
import com.majd.technow.ui.MainActivity;

import static com.majd.technow.appwidget.UpdateWidgetService.UPDATE_THE_WIDGET_ACTION;
import static com.majd.technow.appwidget.UpdateWidgetService.startUpdateTeckWidgets;

/**
 * Implementation of App Widget functionality.
 */
public class TeckNowWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String savedArticles) {

        CharSequence widgetText = "TeckNow";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.teck_now_widget);
        views.setTextViewText(R.id.appwidget_text, savedArticles);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        startUpdateTeckWidgets(context);
    }


    public static void updateTeckWidgets(Context context, AppWidgetManager appWidgetManager,
                                         int[] appWidgetIds, String savedTitles) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, savedTitles);
        }
        Log.d("****", "update");
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

