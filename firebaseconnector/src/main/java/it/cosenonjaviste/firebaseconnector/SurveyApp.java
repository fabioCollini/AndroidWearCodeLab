package it.cosenonjaviste.firebaseconnector;

import android.app.Application;

import com.firebase.client.Firebase;

public class SurveyApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
