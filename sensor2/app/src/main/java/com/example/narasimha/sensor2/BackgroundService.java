package com.example.narasimha.sensor2;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundService extends Service implements SensorEventListener{
    SensorManager sensorManager;
    Sensor light;
    boolean isSilent=false;
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
        /*Toast.makeText(this,"i am running",Toast.LENGTH_SHORT).show();*/
        super.onCreate();
        Toast.makeText(this,"service created",Toast.LENGTH_SHORT).show();
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        light=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(light==null)
        {
            Toast.makeText(this,"no sensor",Toast.LENGTH_SHORT).show();
        }
        else
        {
            am=(AudioManager)getSystemService(AUDIO_SERVICE);
            sensorManager.registerListener(this,light,SensorManager.SENSOR_DELAY_FASTEST);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float optical_density[]=sensorEvent.values;
        if(optical_density[0]<1.0f){
            if((am.getRingerMode())!=AudioManager.RINGER_MODE_SILENT)
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        else
        {
            if((am.getRingerMode())!=AudioManager.RINGER_MODE_NORMAL)
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
