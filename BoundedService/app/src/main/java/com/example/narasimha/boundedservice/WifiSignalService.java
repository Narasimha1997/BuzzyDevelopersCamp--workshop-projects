package com.example.narasimha.boundedservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class WifiSignalService extends Service {

    final IBinder myBinder = new LocalBinder();

    WifiManager manager;

    WifiSignalService(){

    }


    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    class LocalBinder extends Binder {
        WifiSignalService getService(){
            return WifiSignalService.this;
        }
    }

    public int getSignalStrength(){
        manager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        Toast.makeText(this, "Called function", Toast.LENGTH_SHORT).show();
        WifiInfo info = manager.getConnectionInfo();
        return  WifiManager.calculateSignalLevel(info.getRssi(), 5);
    }
}
