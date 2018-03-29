package com.example.narasimha.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Random;

public class BackgroundService extends Service implements SensorEventListener{
    SensorManager sensorManager;
    Sensor light;
    boolean isSilent = false;
    AudioManager am;
    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(light == null){
            Toast.makeText(this, "Light sensor not present", Toast.LENGTH_SHORT).show();
        }else{
            am = (AudioManager)getSystemService(AUDIO_SERVICE);
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //call a non-blocking thread function
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] optical_density = sensorEvent.values;
        if(optical_density[0] <= 1.0f){
            if((am.getRingerMode())!= AudioManager.RINGER_MODE_SILENT)
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }else{
            if((am.getRingerMode())!=AudioManager.RINGER_MODE_NORMAL)
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
