package com.xiazhao.learning.mymirror.untils;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class SetBrightness {

    public static boolean isAutoBrightness(ContentResolver aContentResolver) {
        boolean autoMicBrightness = false;
        try {
            autoMicBrightness = Settings.System.getInt(aContentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return autoMicBrightness;
    }

    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams ip = activity.getWindow().getAttributes();
        ip.screenBrightness = Float.valueOf(brightness) * (1f/255f);
        activity.getWindow().setAttributes(ip);
    }

    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    public static void saveBrightness(Activity activity, int brightness) {
        ContentResolver contentResolver = activity.getContentResolver();
        Window window = activity.getWindow();
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        WindowManager.LayoutParams layoutpars = window.getAttributes();
        layoutpars.screenBrightness = brightness / (float)255;
        window.setAttributes(layoutpars);
    }

    public static ContentResolver getResolver(Activity activity) {
        ContentResolver cr = activity.getContentResolver();
        return cr;
    }
}
