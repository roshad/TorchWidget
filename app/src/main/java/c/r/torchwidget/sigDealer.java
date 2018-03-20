package c.r.torchwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;



public class sigDealer extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //set icon
        SharedPreferences sp = context.getSharedPreferences("Torch", Context.MODE_PRIVATE);
        Boolean b = sp.getBoolean("switch", false);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.torch);

        if (b) views.setImageViewResource(R.id.Torch,R.drawable.moon_on );
        else views.setImageViewResource(R.id.Torch,R.drawable.moon_off );


        //set onClickIntent
        Intent intent = new Intent(context,Service.class);
        PendingIntent pendingitent = PendingIntent.getService
            (context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.Torch, pendingitent);
        //must put last
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId); } }
}

