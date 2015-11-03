package it.cosenonjaviste.mywearapp;

import com.mariux.teleport.lib.TeleportService;

import it.cosenonjaviste.firebaseconnector.SurveyManager;

public class MyService extends TeleportService {
    private SurveyManager surveyManager;

    @Override public void onCreate() {
        super.onCreate();
        surveyManager = new SurveyManager(this);
        setOnGetMessageCallback(new MyOnGetMessageCallback());
    }

    private class MyOnGetMessageCallback extends OnGetMessageCallback {
        @Override public void onCallback(String s) {
            surveyManager.updateAnswer(s);
            sendMessage("ok", null);
            setOnGetMessageCallback(new MyOnGetMessageCallback());
        }
    }
}
