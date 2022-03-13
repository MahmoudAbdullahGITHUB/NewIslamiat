package com.perfect.islamyat;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.perfect.islamyat.utils.NotificationReceiver;
import com.perfect.islamyat.utils.UpdateService;

public class autostart extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            //Take count of the screen off position
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            //Take count of the screen on position
        }
        Intent i = new Intent(context, UpdateService.class);

        context.startService(i);
    }

    public PendingIntent pIntent(Context context, int id) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("id",id);
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
