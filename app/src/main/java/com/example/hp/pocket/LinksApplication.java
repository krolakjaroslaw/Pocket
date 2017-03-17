package com.example.hp.pocket;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class LinksApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
