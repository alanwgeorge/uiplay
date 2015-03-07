package com.example.android.uiplay;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

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
}
