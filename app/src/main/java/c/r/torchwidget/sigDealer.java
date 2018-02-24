package c.r.torchwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;



public class sigDealer extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //set icon
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.torch);
        views.setImageViewResource(R.id.Torch,R.drawable.moon_off );
        //set onclick
        Intent intentStart = new Intent(context,Service.class);
        PendingIntent pendingitent = PendingIntent.getService
            (context, 0, intentStart, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.Torch, pendingitent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId); } }}

