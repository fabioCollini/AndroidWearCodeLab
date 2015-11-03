package it.cosenonjaviste.mywearapp;

import android.app.Activity;
import android.os.Bundle;
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
    }

    @Override protected void onStart() {
        super.onStart();
        teleportClient.connect();
    }

    @Override protected void onStop() {
        super.onStop();
        teleportClient.disconnect();
    }
}
