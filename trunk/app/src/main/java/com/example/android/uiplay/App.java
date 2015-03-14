package com.example.android.uiplay;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.DisplayMetrics;

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }

        super.onCreate();
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
