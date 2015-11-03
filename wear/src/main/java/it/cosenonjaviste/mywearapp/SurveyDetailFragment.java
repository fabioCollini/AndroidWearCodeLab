package it.cosenonjaviste.mywearapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import it.cosenonjaviste.wearlib.Survey;

public class SurveyDetailFragment extends Fragment {
    private TeleportClient teleportClient;
    private TextView yesCount;
    private TextView noCount;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teleportClient = new TeleportClient(getActivity());
        teleportClient.setOnSyncDataItemCallback(new MyOnSyncDataItemCallback());
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.survey_detail, container, false);
        yesCount = (TextView) view.findViewById(R.id.yes_count);
        noCount = (TextView) view.findViewById(R.id.no_count);
        return view;
    }

    @Override public void onStart() {
        super.onStart();
        teleportClient.connect();
        teleportClient.sendMessage("start", null);
    }

    @Override public void onStop() {
        super.onStop();
        teleportClient.disconnect();
        teleportClient.sendMessage("stop", null);
    }

    private class MyOnSyncDataItemCallback extends TeleportClient.OnSyncDataItemCallback {
        @Override public void onDataSync(DataMap dataMap) {
            String s = dataMap.getString("survey");
            Survey survey = Survey.parse(s);
            yesCount.setText(Integer.toString(survey.getYesCount()));
            noCount.setText(Integer.toString(survey.getNoCount()));

            teleportClient.setOnSyncDataItemCallback(new MyOnSyncDataItemCallback());
        }
    }
}
