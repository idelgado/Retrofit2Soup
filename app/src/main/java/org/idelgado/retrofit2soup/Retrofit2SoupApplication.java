package org.idelgado.retrofit2soup;

import android.app.Application;

import timber.log.Timber;

public class Retrofit2SoupApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
