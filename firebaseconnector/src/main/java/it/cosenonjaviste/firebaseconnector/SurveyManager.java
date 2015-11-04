package it.cosenonjaviste.firebaseconnector;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SurveyManager {

    public static boolean OFFLINE = false;

    private static Map<String, String> ANSWERS = createAnswers();

    @NonNull private static HashMap<String, String> createAnswers() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "yes");
        map.put("b", "yes");
        map.put("c", "yes");
        map.put("d", "no");
        map.put("e", "no");
        return map;
    }

    private static List<SurveyListener> LISTENERS = new ArrayList<>();

    public static final String USER_NAME = "USER_NAME";

    private final Firebase firebase;

    private String userName;
    private ValueEventListener valueEventListener;
    private SurveyListener surveyListener;

    public SurveyManager(Context context) {
        firebase = new Firebase("https://gdgsurveys.firebaseio.com/").child("survey1");
        userName = getOrCreateUserName(context);
    }

    public void addValueEventListener(final SurveyListener listener) {
        surveyListener = listener;
        if (OFFLINE) {
            LISTENERS.add(listener);
            listener.onSurveyChange(new Survey("Yes or no?", ANSWERS));
        } else {
            valueEventListener = new ValueEventListener() {
                @Override public void onDataChange(DataSnapshot dataSnapshot) {
                    Survey survey = dataSnapshot.getValue(Survey.class);
                    listener.onSurveyChange(survey);
                }

                @Override public void onCancelled(FirebaseError firebaseError) {
                    listener.onCancelled();
                }
            };
            firebase.addValueEventListener(valueEventListener);
        }
    }

    public void removeEventListener() {
        if (valueEventListener != null) {
            if (OFFLINE) {
                LISTENERS.remove(surveyListener);
            } else {
                firebase.removeEventListener(valueEventListener);
            }
            valueEventListener = null;
            surveyListener = null;
        }
    }

    public void updateAnswer(String answer) {
        if (OFFLINE) {
            ANSWERS.put(userName, answer);
            for (SurveyListener listener : LISTENERS) {
                listener.onSurveyChange(new Survey("Yes or no?", ANSWERS));
            }
        } else {
            firebase.child("answers").child(userName).setValue(answer);
        }
    }

    @NonNull private String getOrCreateUserName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String userName = prefs.getString(USER_NAME, null);
        if (userName == null) {
            userName = Build.MANUFACTURER + " " + Build.MODEL + " " + UUID.randomUUID();
            prefs.edit().putString(USER_NAME, userName).apply();
        }
        return userName;
    }

    public String getUserName() {
        return userName;
    }

}
