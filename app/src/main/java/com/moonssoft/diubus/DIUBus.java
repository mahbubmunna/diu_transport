package com.moonssoft.diubus;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by MoonS on 30-01-17.
 */

public class DIUBus extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
