package it.cosenonjaviste.firebaseconnector;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_detail);

        final SurveyManager surveyManager = new SurveyManager(this);

        final TextView question = (TextView) findViewById(R.id.question);
        final TextView answerYes = (TextView) findViewById(R.id.answer_yes);
        final TextView yesCount = (TextView) findViewById(R.id.yes_count);
        final TextView answerNo = (TextView) findViewById(R.id.answer_no);
        final TextView noCount = (TextView) findViewById(R.id.no_count);

        surveyManager.addValueEventListener(new SurveyListener() {
            @Override public void onSurveyChange(Survey survey) {
                question.setText(survey.getQuestion());
                answerYes.setSelected("yes".equals(survey.getUserAnswer(surveyManager.getUserName())));
                yesCount.setText(Integer.toString(survey.getYesCount()));
                answerNo.setSelected("no".equals(survey.getUserAnswer(surveyManager.getUserName())));
                noCount.setText(Integer.toString(survey.getNoCount()));
            }

            @Override public void onCancelled() {
                Snackbar.make(findViewById(R.id.root), "Error accessing data", Snackbar.LENGTH_SHORT).show();
            }
        });

        answerYes.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                surveyManager.updateAnswer("yes");
            }
        });

        answerNo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                surveyManager.updateAnswer("no");
            }
        });
    }
}
