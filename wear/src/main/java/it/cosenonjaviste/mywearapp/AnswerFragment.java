package it.cosenonjaviste.mywearapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mariux.teleport.lib.TeleportClient;

public class AnswerFragment extends Fragment {

    private TeleportClient teleportClient;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teleportClient = new TeleportClient(getActivity());
        teleportClient.setOnGetMessageCallback(new MyOnGetMessageCallback());
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.answer, container, false);

        view.findViewById(R.id.answer_y).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                teleportClient.sendMessage("yes", null);
            }
        });
        view.findViewById(R.id.answer_n).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                teleportClient.sendMessage("no", null);
            }
        });

        return view;
    }

    @Override public void onStart() {
        super.onStart();
        teleportClient.connect();
    }

    @Override public void onStop() {
        super.onStop();
        teleportClient.disconnect();
    }

    private class MyOnGetMessageCallback extends TeleportClient.OnGetMessageCallback {
        @Override public void onCallback(String s) {
            showConfirmationActivity();
            teleportClient.setOnGetMessageCallback(new MyOnGetMessageCallback());
        }
    }

    private void showConfirmationActivity() {
        Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                getString(R.string.answer_sent));
        startActivity(intent);
    }
}
