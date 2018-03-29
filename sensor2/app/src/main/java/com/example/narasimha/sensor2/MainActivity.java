package com.example.narasimha.sensor2;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import static android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void start_service(View v)
    {
        startService(new Intent(this,BackgroundService.class));

    }
    public void stop_service(View v)
    {
        stopService(new Intent(this,BackgroundService.class));
    }
    public void changePolicy(View v)
    {
        startActivity(new Intent(ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
    }
}
