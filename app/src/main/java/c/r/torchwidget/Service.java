package c.r.torchwidget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.widget.RemoteViews;


public class Service extends IntentService {
//my code start

    @Override
    protected void onHandleIntent(Intent intent) {
        RemoteViews views;

        SharedPreferences sp = getSharedPreferences("Torch", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Boolean b = sp.getBoolean("switch", false);
        views = new RemoteViews(getPackageName(), R.layout.torch);

        if (b) {
            mSwitch(Service.this, false);
            views.setImageViewResource(R.id.Torch,R.drawable.moon_off );
            editor.putBoolean("switch", false);
        } else {
            mSwitch(Service.this, true);
            views.setImageViewResource(R.id.Torch,R.drawable.moon_on );
            editor.putBoolean("switch", true);
        }
        editor.apply();

        AppWidgetManager appWidgetManager;
        appWidgetManager = AppWidgetManager.getInstance(Service.this);
        appWidgetManager.updateAppWidget(new ComponentName(this, sigDealer.class), views);
    }
    void mSwitch(Context context,boolean oo){
        CameraManager cm = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIdList = cm.getCameraIdList();

            for (String Cam:cameraIdList) {
                CameraCharacteristics camera = cm.getCameraCharacteristics(Cam);
                Integer integer = camera.get(CameraCharacteristics.LENS_FACING);
                Boolean has = camera.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (has != null && integer != null && integer == CameraMetadata.LENS_FACING_BACK && has) {
                    cm.setTorchMode(Cam, oo);
                    break; } } }

        catch (CameraAccessException e) { e.printStackTrace(); }
    }
//My code end


    public Service() { super("Service"); }
}
