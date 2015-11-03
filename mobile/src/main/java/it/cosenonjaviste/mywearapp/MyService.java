package it.cosenonjaviste.mywearapp;

import com.mariux.teleport.lib.TeleportService;

import it.cosenonjaviste.firebaseconnector.Survey;
import it.cosenonjaviste.firebaseconnector.SurveyListener;
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
            if (s.equals("start")) {
                surveyManager.addValueEventListener(new SurveyListener() {
                    @Override public void onSurveyChange(Survey survey) {
                        syncString("survey", survey.toJson());
                    }

                    @Override public void onCancelled() {

                    }
                });
            } else if (s.equals("stop")) {
                surveyManager.removeEventListener();
            } else {
                surveyManager.updateAnswer(s);
                sendMessage("ok", null);
            }
            setOnGetMessageCallback(new MyOnGetMessageCallback());
        }
    }
}
