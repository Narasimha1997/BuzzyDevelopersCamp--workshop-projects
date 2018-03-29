package com.example.narasimha.boundedservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WifiSignalService signalService;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = findViewById(R.id.status);
    }

    public void getStatus(View v){
        Toast.makeText(this, "Communicating with WifiSignalService", Toast.LENGTH_SHORT).show();
        try{
            Intent i = new Intent(this, WifiSignalService.class);
            bindService(i, connection, Context.BIND_AUTO_CREATE);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void startS(View v){
        startService(new Intent(this, WifiSignalService.class));
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }

    public void stopS(View v){
        try{
            stopService(new Intent(this, WifiSignalService.class));
            Toast.makeText(signalService, "Service stopped", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            WifiSignalService.LocalBinder binder = (WifiSignalService.LocalBinder)iBinder;
            signalService = binder.getService();
            int signal_strength = signalService.getSignalStrength();
            status.setText(""+signal_strength);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
