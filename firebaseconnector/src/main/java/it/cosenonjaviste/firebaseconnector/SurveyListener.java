package it.cosenonjaviste.firebaseconnector;

public interface SurveyListener {
    void onSurveyChange(Survey survey);

    void onCancelled();
}
