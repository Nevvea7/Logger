package me.nevvea.logger.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Anna on 6/15/16.
 */
public class LoggerApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}