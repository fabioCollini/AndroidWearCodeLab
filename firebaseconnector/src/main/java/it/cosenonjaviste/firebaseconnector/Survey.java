package it.cosenonjaviste.firebaseconnector;

import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.Map;

public class Survey {
    private String question;

    private Map<String, String> answers;

    private long timestamp;

    public Survey() {
    }

    public Survey(String question, Map<String, String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public int getYesCount() {
        return countAnswers("yes");
    }

    public int getNoCount() {
        return countAnswers("no");
    }

    private int countAnswers(String value) {
        int tot = 0;
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            if (value.equals(entry.getValue())) {
                tot++;
            }
        }
        return tot;
    }

    public String toJson() {
        timestamp = System.currentTimeMillis();
        return new Gson().toJson(this);
    }

    public static Survey parse(String json) {
        return new Gson().fromJson(json, Survey.class);
    }

    @Nullable public String getUserAnswer(String userName) {
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            if (userName.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
