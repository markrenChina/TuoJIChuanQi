package com.example.tuojichuanqi;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * @author mark
 */
public class GameApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static Context getContext(){
        return context ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
