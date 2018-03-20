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
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;


public class Service extends IntentService {
//my code start

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sp = getSharedPreferences("Torch", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Boolean b = sp.getBoolean("switch", false);

        if (b) {
            mSwitch(Service.this, false);
            editor.putBoolean("switch", false);
        } else {
            mSwitch(Service.this, true);
            editor.putBoolean("switch", true);
        }
        editor.apply();

        //intent back to provider
        Intent mIntent = new Intent(this, sigDealer.class);
        mIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds
                (new ComponentName(getApplication(), sigDealer.class));
        mIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(mIntent);

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
