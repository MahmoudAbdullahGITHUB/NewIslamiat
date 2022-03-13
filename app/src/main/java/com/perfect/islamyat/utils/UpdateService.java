package com.perfect.islamyat.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.perfect.islamyat.autostart;

public class UpdateService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("SOUND"," ---- READY");
        // REGISTER RECEIVER THAT HANDLES SCREEN ON AND SCREEN OFF LOGIC
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new autostart();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.v("SOUND"," ---- RUNNING");
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
        if (!screenOn) {
            // your code
        } else {
            // your code
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
