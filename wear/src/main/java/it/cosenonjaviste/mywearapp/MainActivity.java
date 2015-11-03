package it.cosenonjaviste.mywearapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.View;

import com.mariux.teleport.lib.TeleportClient;

public class MainActivity extends Activity {

    private TeleportClient teleportClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);
        teleportClient = new TeleportClient(this);
        findViewById(R.id.answer_y).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                teleportClient.sendMessage("yes", null);
            }
        });
        findViewById(R.id.answer_n).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                teleportClient.sendMessage("no", null);
            }
        });

        teleportClient.setOnGetMessageCallback(new MyOnGetMessageCallback());
    }

    @Override protected void onStart() {
        super.onStart();
        teleportClient.connect();
    }

    @Override protected void onStop() {
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
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                getString(R.string.answer_sent));
        startActivity(intent);
    }
}
