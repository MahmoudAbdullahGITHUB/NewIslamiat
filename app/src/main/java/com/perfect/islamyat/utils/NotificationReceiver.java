
package com.perfect.islamyat.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.perfect.islamyat.MainActivity;
import com.perfect.islamyat.R;
import com.perfect.islamyat.models.myPrayerModel;

import java.util.Calendar;
import java.util.Date;

public class NotificationReceiver extends BroadcastReceiver {
    /**
     * if you make NotificationManager.IMPORTANCE_HIGH you cannot change the default sound of notification
     */
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    String CHANNEL_ID_EST = "EST_";
    String CHANNEL_ID_FAJR = "FAJR_";
    String CHANNEL_ID_PRAYER = "PRAYER_";
    public static MediaPlayer sound;
    String selectedChannelID = "";
    int soundNum = 0;
    private String CHANNEL_ID = "channel_id_example_01";
    //    String maghm = "17:43";
    static NotificationManager notificationManager;
    static NotificationManager notificationManager555;
    private NotificationManagerCompat notificationManager2;
    private NotificationManagerCompat notificationManagerCompat;
    NotificationManager mNotificationManager;
    //    myPrayerModel mMyPrayerModel;
    static myPrayerModel mMyPrayerModel;


    public NotificationReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        mMyPrayerModel = new myPrayerModel();

        /** soundNum = prayerId; */
        soundNum = intent.getExtras().getInt("id");
        Log.d("EnteredReceiver ", String.valueOf(soundNum));
        Intent intent1 = new Intent(context, CustomIntentService.class);
//        Intent intent1 = new Intent(context, AlarmActivity.class);
        Log.d("TAGNot", String.valueOf(intent.getExtras().getInt("id")));
        intent1.putExtra("id", intent.getExtras().getInt("id"));
//        context.startService(intent1);


        Log.d("TAGry1", "onReceive: ");

        notificationManager2 = NotificationManagerCompat.from(context.getApplicationContext());

        mNotificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManagerCompat = NotificationManagerCompat.from(context);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        System.out.println("__________________________________________________####################");
        /**
         * added by me
         */
        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("FullScreenText", getNotificationText(soundNum));
        editor.putInt("notificationIDFullScreen", intent.getExtras().getInt("id"));
        editor.commit();


        String audioPath = getAudioPath(context, intent.getExtras().getInt("id"));
        System.out.println("audioPath = " + audioPath);

        /***/


//        chh(context,soundNum,"إسلاميات",getNotificationText(intent.getExtras().getInt("id")),audioPath);

//        chhh2(context,soundNum,"إسلاميات",getNotificationText(intent.getExtras().getInt("id")),audioPath);
//        showNotificationWithFullScreenIntent(context, true, selectedChannelID
//                , "إسلاميات", getNotificationText(intent.getExtras().getInt("id")), audioPath,sound);

//        deleteChannel();


        Log.d("CURRENT ID -->", intent.getExtras().getInt("id") + "");
        if (intent.getExtras().getInt("id") < 10) {
            setEsteghfarAlarm(context, intent.getExtras().getInt("id"));
        }
        chhh(context, soundNum, "إسلاميات", getNotificationText(intent.getExtras().getInt("id")), audioPath);


//        boolean bbb = checkInternetConnection(context);
//        if (bbb) {
//            Log.d("TAGinternetConnected", "onReceive: ");
//
//            /***/// prayer times
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("https://api.aladhan.com/v1/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("timestamp = " + timestamp.getTime());            // 1616574866666
//
//
//            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//
//
//            String timeStamp2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//
//            double besoLastlatitude = prefs.getFloat("Lastlatitude", 24.774265f);
//            double besoLastLongituide = prefs.getFloat("LastLongituide", 46.738586f);
//
//
//            Log.d("TAGLastLat", "onReceive: "+besoLastlatitude);
//            Log.d("TAGLastLong", "onReceive: "+besoLastLongituide);
//
////            Call<myPrayerModel> call = apiInterface.getTimings(timeStamp2, 30.005493
////                    , 31.477898, 5);
//
//            Call<myPrayerModel> call = apiInterface.getTimings(timeStamp2, besoLastlatitude
//                    , besoLastLongituide, 5);
//
//            call.enqueue(new Callback<myPrayerModel>() {
//                @Override
//                public void onResponse(Call<myPrayerModel> call, Response<myPrayerModel> response) {
//                    assert response.body() != null;
//                    mMyPrayerModel = response.body();
//                    Log.d("TAGWWW=", "onResponse: " + response.body().data.timings.Fajr);
//                    setPrayerTimesAlarmsWithInternet(context);
//                }
//
//                @Override
//                public void onFailure(Call<myPrayerModel> call, Throwable t) {
//                    Log.d("TAGtttt=", "onFailure: " + t.getMessage());
//                    setPrayerTimesAlarmsWithoutInternet(context);
//                }
//            });
//
//        }else {
//            Log.d("TAGNoInternet", "onReceive: ");
//            setPrayerTimesAlarmsWithoutInternet(context);
//        }
//



//        setPrayerTimesAlarms(context);


//        SystemClock.sleep(1000*60);


        //        playSound2(context, soundNum);

        //        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(intent1);
//            /**
//             *
//             */
//            context.startForegroundService(intent1);
//        }
//        playSound(context);
//         FULL SCREEN APPROACH
//        showAlarmNotification(context);
//        notificationManagerCompat = NotificationManagerCompat.from(context.getApplicationContext());
//
//        showNotification(context);
//        buildNotification2(context, audioPath);
//        playSound(context);


    }


    public Boolean checkInternetConnection(Context context) {
        if (bNetworkHelper.INSTANCE.isNetworkConnected(context)) {
            Toast.makeText(context, "تم الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            Log.d("تم الاتصال بالانترنت", "checkConnType: ");
            return true;
        } else {
            Toast.makeText(context, "لا يوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            Log.d("لا يوجد اتصال بالانترنت", "checkConnType: ");
            return false;
        }
    }


    public void chhh(Context context, int audioID, String title, String description, String audioPath) {

        Log.d("TAGry2", "chhh: ");

        String CHANNEL_ID = "1234";

        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
        int resID;
        int setResID;

        int returnSoundNumber = prefs.getInt("prayerTimesSound", 15);
        Log.d("prayerTimesSoundlog", String.valueOf(returnSoundNumber));
        System.out.println("prayerTimesSound = number = " + returnSoundNumber);


        resID = R.raw.azan_sound_2;
        setResID = R.raw.estghfar_0;

//        selectedChannelID+= resID;

        if (audioID > 10) {
            if (audioID != 102) {
                switch (prefs.getInt("prayerTimesSound", 15)) {
                    case 1:
                        resID = R.raw.azan_maky_first;
                        break;
                    case 2:
                        resID = R.raw.azan_maky_sec;
                        break;
                    case 3:
                        resID = R.raw.azan_madny_first;
                        break;
                    case 16:
                        resID = R.raw.azan_madny_sec;
                        break;
                    case 4:
                        resID = R.raw.azan_qatar;
                        break;
                    case 5:
                        resID = R.raw.azan_sound_1;
                        break;
                    case 6:
                        resID = R.raw.azan_sound_2;
                        break;
                    case 7:
                        resID = R.raw.azan_sound_3;
                        break;
                    case 8:
                        resID = R.raw.azan_sound_4;
                        break;
                    case 9:
                        resID = R.raw.azan_sound_5;
                        break;
                    case 10:
                        resID = R.raw.azan_sound_6;
                        break;
                    case 11:
                        resID = R.raw.azan_sound_7;
                        break;
                    case 12:
                        resID = R.raw.azan_sound_8;
                        break;
                    case 13:
                        resID = R.raw.azan_sound_9;
                        break;
                    case 14:
                        resID = R.raw.azan_sound_10;
                        break;
                    case 15:
                        resID = R.raw.azan_sound_0;
                        break;
                    default:
                        resID = R.raw.azan_sound_0;
                        break;
                }
            } else {
                switch (prefs.getInt("prayerTimesSpecialAlarmFajrSound", 0)) {
                    case 1:
                        resID = R.raw.fagr_sound_0;
                        break;
                    case 2:
                        resID = R.raw.fagr_sound_1;
                        break;
                    case 3:
                        resID = R.raw.fagr_sound_2;
                        break;
                    case 4:
                        resID = R.raw.fagr_sound_3;
                        break;
                    case 5:
                        resID = R.raw.fagr_sound_4;
                        break;
                    case 6:
                        resID = R.raw.fagr_sound_5;
                        break;
                    default:
                        resID = R.raw.fagr_sound_0;
                }
            }


            CHANNEL_ID += resID;

            Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                    context.getApplicationContext().getPackageName() + "/" + resID);


            NotificationChannel mChannel;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(CHANNEL_ID, "Utils.CHANNEL_NAME24424", NotificationManager.IMPORTANCE_HIGH);
                mChannel.setLightColor(Color.GRAY);
                mChannel.enableLights(true);
                mChannel.setDescription("Utils.CHANNEL_SIREN_DESCRIPTION");
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
                mChannel.setSound(soundUri, audioAttributes);

                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(mChannel);
                }
            }

            Log.d("resTAG", "chhh: " + resID + " R.raw" + R.raw.azan_sound_14);

            //General code:
            NotificationCompat.Builder status = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID);
            status.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    //.setOnlyAlertOnce(true)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setVibrate(new long[]{0, 500, 1000})
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + resID));
//            .setContentIntent(pendingIntent)
//            .setContent(views);

            mNotificationManager.notify(0, status.build());
            /**
             * if want to push many notification at the same time put different value in notifiy(value, status.build)
             */

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (!mNotificationManager.areNotificationsEnabled()) {
                    Log.d("TAGdehoity", "chhh: ");
                }
            }


        } else {


            switch (audioID) {
                /***/
                case 1:
                    sound = MediaPlayer.create(context, R.raw.estghfar_0);
                    setResID = R.raw.estghfar_0;

                    break;
                case 2:
                    sound = MediaPlayer.create(context, R.raw.estghfar_1);
                    setResID = R.raw.estghfar_1;

                    break;
                case 3:
                    sound = MediaPlayer.create(context, R.raw.estghfar_3);
                    setResID = R.raw.estghfar_3;

                    break;
                case 4:
                case 9:
                    sound = MediaPlayer.create(context, R.raw.estghfar_9);
                    setResID = R.raw.estghfar_9;

                    break;
                case 5:
                    sound = MediaPlayer.create(context, R.raw.estghfar_5);
                    setResID = R.raw.estghfar_5;

                    break;
                case 6:
                    sound = MediaPlayer.create(context, R.raw.estghfar_6);
                    setResID = R.raw.estghfar_6;

                    break;
                case 7:
                    sound = MediaPlayer.create(context, R.raw.estghfar_7);
                    setResID = R.raw.estghfar_7;

                    break;
                case 8:
                    sound = MediaPlayer.create(context, R.raw.estghfar_8);
                    setResID = R.raw.estghfar_8;

                    break;
                default:
                    sound = MediaPlayer.create(context, resID);
                    setResID = R.raw.estghfar_0;

                    break;
            }


            CHANNEL_ID += setResID;

            Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                    context.getApplicationContext().getPackageName() + "/" + setResID);


            NotificationChannel mChannel;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(CHANNEL_ID, "Utils.CHANNEL_NAME24424", NotificationManager.IMPORTANCE_HIGH);
                mChannel.setLightColor(Color.GRAY);
                mChannel.enableLights(true);
                mChannel.setDescription("Utils.CHANNEL_SIREN_DESCRIPTION");
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
                mChannel.setSound(soundUri, audioAttributes);

                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(mChannel);
                }
            }

            Log.d("resTAG", "chhh: " + setResID + " R.raw" + R.raw.azan_sound_14);

            //General code:
            NotificationCompat.Builder status = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID);
            status.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    //.setOnlyAlertOnce(true)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setVibrate(new long[]{0, 500, 1000})
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + setResID));
//            .setContentIntent(pendingIntent)
//            .setContent(views);

            mNotificationManager.notify(0, status.build());
            /**
             * if want to push many notification at the same time put different value in notifiy(value, status.build)
             */

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (!mNotificationManager.areNotificationsEnabled()) {
                    Log.d("TAGdehoity", "chhh: ");
                }
            }


        }


//status.setOngoing(true);


    }


    public void setPrayerTimesAlarmsWithInternet(Context context) {

        Log.d("TAGry3Internet", "onReceive: ");

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(pIntent(context, 100));
        alarmMgr.cancel(pIntent(context, 101));
        alarmMgr.cancel(pIntent(context, 102));
        alarmMgr.cancel(pIntent(context, 110));
        alarmMgr.cancel(pIntent(context, 120));
        alarmMgr.cancel(pIntent(context, 121));
        alarmMgr.cancel(pIntent(context, 130));
        alarmMgr.cancel(pIntent(context, 131));
        alarmMgr.cancel(pIntent(context, 140));
        alarmMgr.cancel(pIntent(context, 141));
        alarmMgr.cancel(pIntent(context, 150));
        alarmMgr.cancel(pIntent(context, 151));
        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);

        Log.d("TAGfffag ", "setPrayerTimesAlarms: " + prefs.getInt("prayerTimesCalcMethod", 4));



//        Log.d("TAGMainActivitycode", "setPrayerTimesAlarms: " + MainActivity.mMyPrayerModel.code);

//        SharedPreferences appPreferences = null;
//        appPreferences = context.getSharedPreferences("IslamyiatSettings", 0);
//
//
//        appPreferences.getString("esteghfarAlarmStartTime", "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("beso_Fajr", mMyPrayerModel.data.timings.Fajr);
        editor.putString("beso_Sunrise", mMyPrayerModel.data.timings.Sunrise);
        editor.putString("beso_Dhuhr", mMyPrayerModel.data.timings.Dhuhr);
        editor.putString("beso_Asr", mMyPrayerModel.data.timings.Asr);
        editor.putString("beso_Maghrib", mMyPrayerModel.data.timings.Maghrib);
        editor.putString("beso_Isha", mMyPrayerModel.data.timings.Isha);
        editor.commit();

        Log.d("TAGFajrhere1", "setPrayerTimesAlarmsWithInternet: "+prefs.getString("beso_Fajr","03:09"));

        String besoFajr =  prefs.getString("beso_Fajr", "03:03");
        String besoSunrise =  prefs.getString("beso_Sunrise", "04:53");
        String besoDhuhr =  prefs.getString("beso_Dhuhr", "11:55");
        String besoAsr =  prefs.getString("beso_Asr", "15:30");
        String besoMaghrib =  prefs.getString("beso_Maghrib", "18:57");
        String besoIsha =  prefs.getString("beso_Isha", "20:30");

        Log.d("TAGDDDDDDDDhhdhDD", "getPrayerTimes: " + besoFajr);


        String fajr_time = mMyPrayerModel.data.timings.Fajr;
        if ((fajr_time.substring(0, fajr_time.indexOf(":"))).length() == 1)
            fajr_time = "0" + fajr_time;
        if ((fajr_time.substring(fajr_time.indexOf(":") + 1)).length() == 1)
            fajr_time = fajr_time.replace(":", ":0");
        String sunrise_time = mMyPrayerModel.data.timings.Sunrise;
        if ((sunrise_time.substring(0, sunrise_time.indexOf(":"))).length() == 1)
            sunrise_time = "0" + sunrise_time;
        if ((sunrise_time.substring(sunrise_time.indexOf(":") + 1)).length() == 1)
            sunrise_time = sunrise_time.replace(":", ":0");
        String dhuhr_time = mMyPrayerModel.data.timings.Dhuhr;
        if ((dhuhr_time.substring(0, dhuhr_time.indexOf(":"))).length() == 1)
            dhuhr_time = "0" + dhuhr_time;
        if ((dhuhr_time.substring(dhuhr_time.indexOf(":") + 1)).length() == 1)
            dhuhr_time = dhuhr_time.replace(":", ":0");
        String asr_time = mMyPrayerModel.data.timings.Asr;
        if ((asr_time.substring(0, asr_time.indexOf(":"))).length() == 1) asr_time = "0" + asr_time;
        if ((asr_time.substring(asr_time.indexOf(":") + 1)).length() == 1)
            asr_time = asr_time.replace(":", ":0");
        String maghreb_time = mMyPrayerModel.data.timings.Maghrib;
        if ((maghreb_time.substring(0, maghreb_time.indexOf(":"))).length() == 1)
            maghreb_time = "0" + maghreb_time;
        if ((maghreb_time.substring(maghreb_time.indexOf(":") + 1)).length() == 1)
            maghreb_time = maghreb_time.replace(":", ":0");
        String isha_time = mMyPrayerModel.data.timings.Isha;
        if ((isha_time.substring(0, isha_time.indexOf(":"))).length() == 1)
            isha_time = "0" + isha_time;
        if ((isha_time.substring(isha_time.indexOf(":") + 1)).length() == 1)
            isha_time = isha_time.replace(":", ":0");



        if (prefs.getBoolean("prayerTimesAlarmFajrOn", true)) {
            setPrayerAlarm(context, fajr_time, 100, 0);
            if (prefs.getBoolean("prayerTimesSpecialAlarmFajrOn", false)) { // Special Fajr Alarm
                if (prefs.getBoolean("prayerTimesSpecialAlarmBefore", true)) { // Special Fajr Alarm

                    setPrayerAlarm(
                            context,
                            fajr_time,
                            102,
                            (prefs.getInt("prayerTimesSpecialAlarmOffset", 0) + 1) * 1000 * 60 * 5
                    );

//                    setPrayerAlarm(
//                            context,
//                            fajr_time,
//                            102,
//                            (prefs.getInt("prayerTimesIqamaFajr", 0) + 1) * 1000 * 60 * 5
//                    );
                } else {

                    setPrayerAlarm(
                            context,
                            fajr_time,
                            102,
                            (prefs.getInt("prayerTimesSpecialAlarmOffset", 0) + 1) * 1000 * 60 * -5
                    );

//                    setPrayerAlarm(
//                            context,
//                            fajr_time,
//                            102,
//                            (prefs.getInt("prayerTimesIqamaFajr", 0) + 1) * 1000 * 60 * -5
//                    );
                }
            }
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, fajr_time, 101, (prefs.getInt("prayerTimesIqamaFajr", 0) + 2) * 1000 * 60 * 5);
            }
        }
        if (prefs.getBoolean("prayerTimesAlarmSunriseOn", false)) {
            setPrayerAlarm(context, sunrise_time, 110, 0);
        }
        if (prefs.getBoolean("prayerTimesAlarmDhuhrOn", true)) {
            setPrayerAlarm(context, dhuhr_time, 120, 0);
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, dhuhr_time, 121, (prefs.getInt("prayerTimesIqamaDhuhr", 0) + 2) * 1000 * 60 * 5);
            }
        }
        if (prefs.getBoolean("prayerTimesAlarmAsrOn", true)) {
            setPrayerAlarm(context, asr_time, 130, 0);
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, asr_time, 131, (prefs.getInt("prayerTimesIqamaAsr", 0) + 2) * 1000 * 60 * 5);
            }
        }
        if (prefs.getBoolean("prayerTimesAlarmMaghrebOn", true)) {
            setPrayerAlarm(context, maghreb_time, 140, 0);
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, maghreb_time, 141, (prefs.getInt("prayerTimesIqamaMaghreb", 0) + 2) * 1000 * 60 * 5);
            }
        }
        if (prefs.getBoolean("prayerTimesAlarmIshaOn", true)) {
            Log.d("TAGsetPR", "setPrayerTimesAlarms: ");
            setPrayerAlarm(context, isha_time, 150, 0);
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, isha_time, 151, (prefs.getInt("prayerTimesIqamaIsha", 0) + 2) * 1000 * 60 * 5);
            }
        }

    }

    public void setPrayerTimesAlarmsWithoutInternet(Context context) {

        Log.d("TAGry3No", "onReceive: ");

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(pIntent(context, 100));
        alarmMgr.cancel(pIntent(context, 101));
        alarmMgr.cancel(pIntent(context, 102));
        alarmMgr.cancel(pIntent(context, 110));
        alarmMgr.cancel(pIntent(context, 120));
        alarmMgr.cancel(pIntent(context, 121));
        alarmMgr.cancel(pIntent(context, 130));
        alarmMgr.cancel(pIntent(context, 131));
        alarmMgr.cancel(pIntent(context, 140));
        alarmMgr.cancel(pIntent(context, 141));
        alarmMgr.cancel(pIntent(context, 150));
        alarmMgr.cancel(pIntent(context, 151));


        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);

        Log.d("TAGfffag ", "setPrayerTimesAlarms: " + prefs.getInt("prayerTimesCalcMethod", 4));

//        LinkedHashMap<String, String> prayerTimes = PrayTime.getPrayerTimes(
//                context,
//                prefs.getInt("prayerTimesCalcMethod", 4),
//                prefs.getFloat("Lastlatitude", 0F),
//                prefs.getFloat("LastLongituide", 0F),
//                PrayTime.TIME_24
//        );

        Log.d("TAGMainActivitycode", "setPrayerTimesAlarms: " + MainActivity.mMyPrayerModel.code);



//        prefs.getString("esteghfarAlarmStartTime", "");

        String besoFajr = prefs.getString("beso_Fajr", "03:03");
        String besoSunrise = prefs.getString("beso_Sunrise", "04:53");
        String besoDhuhr = prefs.getString("beso_Dhuhr", "11:55");
        String besoAsr = prefs.getString("beso_Asr", "15:30");
        String besoMaghrib = prefs.getString("beso_Maghrib", "18:57");
        String besoIsha = prefs.getString("beso_Isha", "20:30");

        Log.d("TAGDDDDDDDDDD", "getPrayerTimes: " + besoFajr);


        String fajr_time = besoFajr;
        if ((fajr_time.substring(0, fajr_time.indexOf(":"))).length() == 1)
            fajr_time = "0" + fajr_time;
        if ((fajr_time.substring(fajr_time.indexOf(":") + 1)).length() == 1)
            fajr_time = fajr_time.replace(":", ":0");
        String sunrise_time = besoSunrise;
        if ((sunrise_time.substring(0, sunrise_time.indexOf(":"))).length() == 1)
            sunrise_time = "0" + sunrise_time;
        if ((sunrise_time.substring(sunrise_time.indexOf(":") + 1)).length() == 1)
            sunrise_time = sunrise_time.replace(":", ":0");
        String dhuhr_time = besoDhuhr;
        if ((dhuhr_time.substring(0, dhuhr_time.indexOf(":"))).length() == 1)
            dhuhr_time = "0" + dhuhr_time;
        if ((dhuhr_time.substring(dhuhr_time.indexOf(":") + 1)).length() == 1)
            dhuhr_time = dhuhr_time.replace(":", ":0");
        String asr_time = besoAsr;
        if ((asr_time.substring(0, asr_time.indexOf(":"))).length() == 1) asr_time = "0" + asr_time;
        if ((asr_time.substring(asr_time.indexOf(":") + 1)).length() == 1)
            asr_time = asr_time.replace(":", ":0");
        String maghreb_time = besoMaghrib;
        if ((maghreb_time.substring(0, maghreb_time.indexOf(":"))).length() == 1)
            maghreb_time = "0" + maghreb_time;
        if ((maghreb_time.substring(maghreb_time.indexOf(":") + 1)).length() == 1)
            maghreb_time = maghreb_time.replace(":", ":0");
        String isha_time = besoIsha;
        if ((isha_time.substring(0, isha_time.indexOf(":"))).length() == 1)
            isha_time = "0" + isha_time;
        if ((isha_time.substring(isha_time.indexOf(":") + 1)).length() == 1)
            isha_time = isha_time.replace(":", ":0");


        if (prefs.getBoolean("prayerTimesAlarmFajrOn", true)) {
            setPrayerAlarm(context, fajr_time, 100, 0);
            if (prefs.getBoolean("prayerTimesSpecialAlarmFajrOn", false)) { // Special Fajr Alarm
                if (prefs.getBoolean("prayerTimesSpecialAlarmBefore", true)) { // Special Fajr Alarm

                    setPrayerAlarm(
                            context,
                            fajr_time,
                            102,
                            (prefs.getInt("prayerTimesSpecialAlarmOffset", 0) + 1) * 1000 * 60 * 5
                    );

//                    setPrayerAlarm(
//                            context,
//                            fajr_time,
//                            102,
//                            (prefs.getInt("prayerTimesIqamaFajr", 0) + 1) * 1000 * 60 * 5
//                    );
                } else {

                    setPrayerAlarm(
                            context,
                            fajr_time,
                            102,
                            (prefs.getInt("prayerTimesSpecialAlarmOffset", 0) + 1) * 1000 * 60 * -5
                    );

//                    setPrayerAlarm(
//                            context,
//                            fajr_time,
//                            102,
//                            (prefs.getInt("prayerTimesIqamaFajr", 0) + 1) * 1000 * 60 * -5
//                    );
                }
            }
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, fajr_time, 101, (prefs.getInt("prayerTimesIqamaFajr", 0) + 2) * 1000 * 60 * 5);
            }
        }
        if (prefs.getBoolean("prayerTimesAlarmSunriseOn", false)) {
            setPrayerAlarm(context, sunrise_time, 110, 0);
        }
        if (prefs.getBoolean("prayerTimesAlarmDhuhrOn", true)) {
            setPrayerAlarm(context, dhuhr_time, 120, 0);
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, dhuhr_time, 121, (prefs.getInt("prayerTimesIqamaDhuhr", 0) + 2) * 1000 * 60 * 5);
            }
        }
        if (prefs.getBoolean("prayerTimesAlarmAsrOn", true)) {
            setPrayerAlarm(context, asr_time, 130, 0);
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, asr_time, 131, (prefs.getInt("prayerTimesIqamaAsr", 0) + 2) * 1000 * 60 * 5);
            }
        }
        if (prefs.getBoolean("prayerTimesAlarmMaghrebOn", true)) {
            setPrayerAlarm(context, maghreb_time, 140, 0);
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, maghreb_time, 141, (prefs.getInt("prayerTimesIqamaMaghreb", 0) + 2) * 1000 * 60 * 5);
            }
        }
        if (prefs.getBoolean("prayerTimesAlarmIshaOn", true)) {
            Log.d("TAGsetPR", "setPrayerTimesAlarms: ");
            setPrayerAlarm(context, isha_time, 150, 0);
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(context, isha_time, 151, (prefs.getInt("prayerTimesIqamaIsha", 0) + 2) * 1000 * 60 * 5);
            }
        }

    }






    public void setEsteghfarAlarm(Context context, int currentID) {

        Log.d("TAGry4", "onReceive: ");


        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, NotificationReceiver.class);
        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
        int nextAlarmID = 0;
        Log.d("TAGboolGGG", "setEsteghfarAlarm: ");

        boolean alarmOn = prefs.getBoolean("esteghfarAlarmOn", false); // default is false

        if (alarmOn) {
            Log.d("TAGbool", "setEsteghfarAlarm: ");

//            String esteghfarSounds = prefs.getString("esteghfarAlarmSounds", "010000010");
            String esteghfarSounds = prefs.getString("esteghfarAlarmSounds", "111111010");

            Log.d("TAGesteghfarSoundsBroad", "setEsteghfarAlarm: " + esteghfarSounds);

            int esteghfarRepetition = prefs.getInt("esteghfarAlarmRepetitionPeriod", 0);
            String esteghfarStartTime = prefs.getString("esteghfarAlarmStartTime", "");
            String esteghfarEndTime = prefs.getString("esteghfarAlarmEndTime", "");
            nextAlarmID = esteghfarSounds.indexOf("1", currentID);

            Log.d("TAGesteghfarSounds", "setEsteghfarAlarm: " + esteghfarSounds);
            Log.d("TAGNot", "setEsteghfarAlarm: " + currentID);

//            Log.v("NEXT ID4 -->", currentID + "");
            if (nextAlarmID == -1) {
//                Log.v("NEXT ID3 -->", nextAlarmID + "");
                nextAlarmID = esteghfarSounds.indexOf("1");
                Log.d("TAGnextAlarmID", "setEsteghfarAlarm: " + nextAlarmID);
            }
            if (nextAlarmID > -1) {
//                Log.v("NEXT ID1 -->", nextAlarmID + "");
                Log.d("TAGnextAlarmID-1", "setEsteghfarAlarm: " + nextAlarmID);
                setAlarm(context, nextAlarmID + 1, esteghfarRepetition, esteghfarStartTime, esteghfarEndTime, currentID);
//                Log.v("NEXT ID2 -->", nextAlarmID + "");
            }
//            Log.v("NEXT ID -->", nextAlarmID + "");
        }

    }

    public void setAlarm(Context context, int nextAlarmID, int repetitionPeriod, String esteghfarStartTime, String esteghfarEndTime, int currentID) {

        Log.d("setAlarmS", "setAlarm: " + esteghfarStartTime);
        Log.d("setAlarmE", "setAlarm: " + esteghfarEndTime);
        Log.d("setAlarmCI", "setAlarm: " + currentID);
        Log.d("setAlarmNe", "setAlarm: " + nextAlarmID);
        Log.d("setAlarmRP", "setAlarm: " + repetitionPeriod);

        int offsetMilliseconds = 0;
        switch (repetitionPeriod) {
            case 0: // 15 Minutes
                Log.d("TAG15", "setAlarm: ");
                offsetMilliseconds = 1000 * 60 * 2;
//                offsetMilliseconds = 1000 * 60 * 15;
                break;
            case 1: // 30 Minutes
                offsetMilliseconds = 1000 * 60 * 30;
                break;
            case 2: // 45 Minutes
                offsetMilliseconds = 1000 * 60 * 45;
                break;
            case 3: // 60 Minutes
                offsetMilliseconds = 1000 * 60 * 60;
                break;
            case 4: // 120 Minutes
                offsetMilliseconds = 1000 * 60 * 60 * 2;
                break;
            case 5: // 180 Minutes
                offsetMilliseconds = 1000 * 60 * 60 * 3;
                break;
            case 6: // 240 Minutes
                offsetMilliseconds = 1000 * 60 * 60 * 4;
                break;
            case 7: // 360 Minutes
                offsetMilliseconds = 1000 * 60 * 60 * 6;
                break;
            default:
                offsetMilliseconds = 1000 * 60 * 60;
                break;
        }
        try {
            alarmMgr.cancel(pIntent(context, currentID));
            Calendar calEndTime = Calendar.getInstance();
            calEndTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(esteghfarEndTime.substring(0, 2)));
            calEndTime.set(Calendar.MINUTE, Integer.parseInt(esteghfarEndTime.substring(3, 5)));
            Calendar calStartTime = Calendar.getInstance();
            calStartTime.add(Calendar.DAY_OF_MONTH, 1);
            calStartTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(esteghfarStartTime.substring(0, 2)));
            calStartTime.set(Calendar.MINUTE, Integer.parseInt(esteghfarStartTime.substring(3, 5)));
            Date alarmTime = new Date(System.currentTimeMillis() + (offsetMilliseconds));
            if (alarmTime.compareTo(calEndTime.getTime()) > 0) {
                calStartTime.add(Calendar.MILLISECOND, offsetMilliseconds);
                alarmMgr.setAlarmClock(new AlarmManager.AlarmClockInfo(calStartTime.getTimeInMillis(), pIntent(context, nextAlarmID)), pIntent(context, nextAlarmID));
//                alarmMgr.setExactAndAllowWhileIdle(
//                        AlarmManager.RTC_WAKEUP, calStartTime.getTimeInMillis(),
//                        pIntent(context, nextAlarmID)
//                );
            } else {
                alarmMgr.setAlarmClock(new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + (offsetMilliseconds), pIntent(context, nextAlarmID)), pIntent(context, nextAlarmID));
//                alarmMgr.setExactAndAllowWhileIdle(
//                        AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (offsetMilliseconds),
//                        pIntent(context, nextAlarmID)
//                );
            }
        } catch (Exception e) {

        }
    }


    public void setPrayerAlarm(Context context, String prayerTime, int prayerID, int offset) {
        Log.d("TAGry5", "onReceive: ");

        Log.d("TAGPrayerIDDD", "setPrayerAlarm: " + prayerID);
        Log.d("TAGPrayerIDDDTime", "setPrayerAlarm: " + prayerTime);

        String prayerName = "";
        try {
            Calendar calPrayerTime = Calendar.getInstance();
            calPrayerTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(prayerTime.substring(0, 2)));
            calPrayerTime.set(Calendar.MINUTE, Integer.parseInt(prayerTime.substring(3, 5)));
//            calPrayerTime.set(Calendar.SECOND, 4);
//            calPrayerTime.set(Calendar.MILLISECOND, 5);
            Date currentTime = new Date(System.currentTimeMillis());
            if (currentTime.compareTo(calPrayerTime.getTime()) > 0) {
                Log.d("TAGKamOne", "setPrayerAlarm: ");
                calPrayerTime.add(Calendar.DAY_OF_MONTH, 1);
            }



            Log.d("TAGcalget0", "setPrayerAlarm: "+calPrayerTime.getTime());

//            if (prayerID==100){
//                Log.d("TAG100", "setPrayerAlarm: ");
//                calPrayerTime.add(Calendar.MINUTE, 2);
//                Log.d("TAGcalget", "setPrayerAlarm: "+calPrayerTime.getTime());
//            }

            switch (prayerID) {
                case 100:
                    prayerName = "Fajr Azan";
                    break;
                case 101:
                    prayerName = "Fajr Iqama";
                    break;
                case 110:
                    prayerName = "Sunrise Azan";
                    break;
                case 120:
                    prayerName = "Dhuhr Azan";
                    break;
                case 121:
                    prayerName = "Dhuhr Iqama";
                    break;
                case 130:
                    prayerName = "Asr Azan";
                    break;
                case 131:
                    prayerName = "Asr Iqama";
                    break;
                case 140:
                    prayerName = "Maghreb Azan";
                    break;
                case 141:
                    prayerName = "Maghreb Iqama";
                    break;
                case 150:
                    prayerName = "Isha Azan";
                    break;
                case 151:
                    prayerName = "Isha Iqama";
                    break;
                default:
                    break;
            }
            calPrayerTime.add(Calendar.MILLISECOND, offset);
            Log.v("AlarmSet: ", " For " + prayerName);
            Log.v("AlarmSet: ", "at " + (calPrayerTime.getTime()));

            Intent intent = new Intent(context, NotificationReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, prayerID, intent, 0);

            Log.d("TAGBeforeTop", "setPrayerAlarm: ");

//
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calPrayerTime.getTimeInMillis(), sender);


            Log.d("TAGBeforeTop2", "setPrayerAlarm: ");


//            alarmMgr.setAlarmClock(new AlarmManager.AlarmClockInfo(calPrayerTime.getTimeInMillis(), pIntent(context, prayerID)), pIntent(context, prayerID));
//            alarmMgr.setExactAndAllowWhileIdle(
//                    AlarmManager.RTC_WAKEUP, calPrayerTime.getTimeInMillis(),
//                    sender
//            );
        } catch (Exception e) {

        }
    }


    public String getAudioPath(Context context, int audioID) {
//        "android.resource://" + getPackageName() + "/" + R.raw.estghfar_0
        String audioPathStr = "android.resource://" + context.getPackageName() + "/";
        int resID = R.raw.azan_maky_first;
        System.out.println("resID = " + resID);
        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
        if (audioID > 10) {
            if (audioID != 102) {
                selectedChannelID = CHANNEL_ID_PRAYER + audioID;
                if (audioID == 101 || audioID == 121 || audioID == 131 || audioID == 141 || audioID == 151)
                    resID = R.raw.ekama_sound_0;
                else {
                    switch (prefs.getInt("prayerTimesSound", 0)) {
                        case 1:
                            resID = R.raw.azan_maky_first;
                            break;
                        case 2:
                            resID = R.raw.azan_maky_sec;
                            break;
                        case 3:
                            resID = R.raw.azan_madny_first;
                            break;
                        case 16:
                            resID = R.raw.azan_madny_sec;
                            break;
                        case 4:
                            resID = R.raw.azan_qatar;
                            break;
                        case 5:
                            resID = R.raw.azan_sound_1;
                            break;
                        case 6:
                            resID = R.raw.azan_sound_2;
                            break;
                        case 7:
                            resID = R.raw.azan_sound_3;
                            break;
                        case 8:
                            resID = R.raw.azan_sound_4;
                            break;
                        case 9:
                            resID = R.raw.azan_sound_5;
                            break;
                        case 10:
                            resID = R.raw.azan_sound_6;
                            break;
                        case 11:
                            resID = R.raw.azan_sound_7;
                            break;
                        case 12:
                            resID = R.raw.azan_sound_8;
                            break;
                        case 13:
                            resID = R.raw.azan_sound_9;
                            break;
                        case 14:
                            resID = R.raw.azan_sound_10;
                            break;
                        case 15:
                            resID = R.raw.azan_sound_0;
                            break;
                        default:
                            break;
                    }
                }
            } else {
                switch (prefs.getInt("prayerTimesSpecialAlarmFajrSound", 0)) {
                    case 1:
                        resID = R.raw.fagr_sound_0;
                        selectedChannelID = CHANNEL_ID_FAJR + "1";
                        break;
                    case 2:
                        resID = R.raw.fagr_sound_1;
                        selectedChannelID = CHANNEL_ID_FAJR + "2";
                        break;
                    case 3:
                        resID = R.raw.fagr_sound_2;
                        selectedChannelID = CHANNEL_ID_FAJR + "3";
                        break;
                    case 4:
                        resID = R.raw.fagr_sound_3;
                        selectedChannelID = CHANNEL_ID_FAJR + "4";
                        break;
                    case 5:
                        resID = R.raw.fagr_sound_4;
                        selectedChannelID = CHANNEL_ID_FAJR + "5";
                        break;
                    case 6:
                        resID = R.raw.fagr_sound_5;
                        selectedChannelID = CHANNEL_ID_FAJR + "6";
                        break;
                    default:
                        resID = R.raw.fagr_sound_0;
                        selectedChannelID = CHANNEL_ID_FAJR + "1";
                }
            }
        }
        if (audioID < 10) selectedChannelID = CHANNEL_ID_EST + audioID;
        switch (audioID) {
            case 1:
                audioPathStr += R.raw.estghfar_0;
                break;
            case 2:
                audioPathStr += R.raw.estghfar_1;
                break;
            case 3:
                audioPathStr += R.raw.estghfar_3;
                break;
            case 4:
                audioPathStr += R.raw.estghfar_9;
                break;
            case 5:
                audioPathStr += R.raw.estghfar_5;
                break;
            case 6:
                audioPathStr += R.raw.estghfar_6;
                break;
            case 7:
                audioPathStr += R.raw.estghfar_7;
                break;
            case 8:
                audioPathStr += R.raw.estghfar_8;
                break;
            case 9:
                audioPathStr += R.raw.estghfar_9;
                break;
            default:
                audioPathStr += resID;
                break;
        }
        return audioPathStr;
    }


    public PendingIntent pIntent(Context context, int id) {
        Intent intent = new Intent(context, NotificationReceiver.class);
//        intent.setAction();
        intent.putExtra("id", id);
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }


    public String getNotificationText(int notificationID) {
        String notificationText = "";
        switch (notificationID) {
            case 1:
                notificationText = "الله أكبر";
                break;
            case 2:
                notificationText = "سبحان الله وبحمده";
                break;
            case 3:
                notificationText = "لا إله إلا الله";
                break;
            case 4:
                notificationText = "سبحان الله";
                break;
            case 5:
                notificationText = "استغفر الله";
                break;
            case 6:
                notificationText = "استغفر الله وأتوب إليه";
                break;
            case 7:
                notificationText = "اللهم صلي على سيدنا محمد";
                break;
            case 8:
                notificationText = "سبحان الله وبحمده";
                break;
            case 9:
                notificationText = "مشاري راشد";
                break;
            case 100:
                notificationText = "حان الآن موعد أذان الفجر";
                break;
            case 101:
                notificationText = "حان الآن موعد إقامة صلاة الفجر";
                break;
            case 102:
                notificationText = "منبه صلاة الفجر";
                break;
            case 110:
                notificationText = "حان الآن موعد الشروق";
                break;
            case 120:
                notificationText = "حان الآن موعد أذان الظهر";
                break;
            case 121:
                notificationText = "حان الآن موعد إقامة صلاة الظهر";
                break;
            case 130:
                notificationText = "حان الآن موعد أذان العصر";
                break;
            case 131:
                notificationText = "حان الآن موعد إقامة صلاة العصر";
                break;
            case 140:
                notificationText = "حان الآن موعد أذان المغرب";
                break;
            case 141:
                notificationText = "حان الآن موعد إقامة صلاة المغرب";
                break;
            case 150:
                notificationText = "حان الآن موعد أذان العشاء";
                break;
            case 151:
                notificationText = "حان الآن موعد إقامة صلاة العشاء";
                break;
            default:
                notificationText = "";
                break;
        }
        return notificationText;
    }


    public void deleteChannel(String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("TAGdeleteChannel", "deleteChannel: ");
            mNotificationManager.deleteNotificationChannel(CHANNEL_ID);
        }
    }


    public void playSound2(Context context, int audioID) {
        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
        int resID;

        int returnSoundNumber = prefs.getInt("prayerTimesSound", 15);
        Log.d("prayerTimesSoundlog", String.valueOf(returnSoundNumber));
        System.out.println("prayerTimesSound = number = " + returnSoundNumber);


        resID = R.raw.azan_madny_sec;


        if (audioID > 10) {
            if (audioID != 102) {
                switch (prefs.getInt("prayerTimesSound", 15)) {
                    case 1:
                        resID = R.raw.azan_maky_first;
                        break;
                    case 2:
                        resID = R.raw.azan_maky_sec;
                        break;
                    case 3:
                        resID = R.raw.azan_madny_first;
                        break;
                    case 16:
                        resID = R.raw.azan_madny_sec;
                        break;
                    case 4:
                        resID = R.raw.azan_qatar;
                        break;
                    case 5:
                        resID = R.raw.azan_sound_1;
                        break;
                    case 6:
                        resID = R.raw.azan_sound_2;
                        break;
                    case 7:
                        resID = R.raw.azan_sound_3;
                        break;
                    case 8:
                        resID = R.raw.azan_sound_4;
                        break;
                    case 9:
                        resID = R.raw.azan_sound_5;
                        break;
                    case 10:
                        resID = R.raw.azan_sound_6;
                        break;
                    case 11:
                        resID = R.raw.azan_sound_7;
                        break;
                    case 12:
                        resID = R.raw.azan_sound_8;
                        break;
                    case 13:
                        resID = R.raw.azan_sound_9;
                        break;
                    case 14:
                        resID = R.raw.azan_sound_10;
                        break;
                    case 15:
                        resID = R.raw.azan_sound_0;
                        break;
                    default:
                        resID = R.raw.azan_sound_0;
                        break;
                }
            } else {
                switch (prefs.getInt("prayerTimesSpecialAlarmFajrSound", 0)) {
                    case 1:
                        resID = R.raw.fagr_sound_0;
                        break;
                    case 2:
                        resID = R.raw.fagr_sound_1;
                        break;
                    case 3:
                        resID = R.raw.fagr_sound_2;
                        break;
                    case 4:
                        resID = R.raw.fagr_sound_3;
                        break;
                    case 5:
                        resID = R.raw.fagr_sound_4;
                        break;
                    case 6:
                        resID = R.raw.fagr_sound_5;
                        break;
                    default:
                        resID = R.raw.fagr_sound_0;
                }
            }
        }


        switch (audioID) {
            /***/
            case 1:
                sound = MediaPlayer.create(context, R.raw.estghfar_0);
                break;
            case 2:
                sound = MediaPlayer.create(context, R.raw.estghfar_1);
                break;
            case 3:
                sound = MediaPlayer.create(context, R.raw.estghfar_3);
                break;
            case 4:
            case 9:
                sound = MediaPlayer.create(context, R.raw.estghfar_9);
                break;
            case 5:
                sound = MediaPlayer.create(context, R.raw.estghfar_5);
                break;
            case 6:
                sound = MediaPlayer.create(context, R.raw.estghfar_6);
                break;
            case 7:
                sound = MediaPlayer.create(context, R.raw.estghfar_7);
                break;
            case 8:
                sound = MediaPlayer.create(context, R.raw.estghfar_8);
                break;
            default:
                sound = MediaPlayer.create(context, resID);
                break;
        }
        final Boolean[] isPlaying = {true};
/**
 *
 */
//        notificationManager.cancelAll();
        /**
         *
         */
        if (sound.isPlaying()) {
            sound.stop();
            sound.reset();
            sound = null;
        } else {
            sound.start();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (notificationManager.areNotificationsEnabled()) {
                Log.d("TAGareNotificationsEna", "playSound2: true");
            }
        }


        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {


                notificationManager.cancelAll();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (notificationManager.areNotificationsEnabled()) {
                Log.d("TAG", "playSound2: true");
            } else {
                Log.d("TAG", "playSound2: false");
            }
        }


//        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                Log.d("onCompletion", "onCompletion: ");
//                mp.release();
//                NotificationManager notificationManager =
//                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancelAll();
//                isPlaying[0] = true;
////                sound.stop();
////                sound.release();
////                notificationManager.cancelAll();
////                stopForeground(true);
////            }
//
//        });
//        while (isPlaying[0]) {
//            Log.v("SOUND", " ---- Playing");
//            Log.v("SOUND", String.valueOf(prefs.getBoolean("continuePlaying", false)));
//
//            if (prefs.getBoolean("continuePlaying", false)) {
//                Log.v("SOUND", " ---- Stopped");
//                isPlaying[0] = false;
////                sound.stop();
////                sound.release();
////                stopForeground(true);
//            }
//        }
    }


    public void chh(Context context, int audioID, String title, String description, String audioPath) {

        Log.d("TAGaudioID ", "chh: " + audioID + "path" + audioPath);
        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
//        int resID = R.raw.azan_qatar;

//        int returnSoundNumber = prefs.getInt("prayerTimesSound", 15);

/*
        if (audioID > 10) {
            if (audioID != 102) {
                Log.d("TAG!102>10", "chh: "+prefs.getInt("prayerTimesSound", 15));
                switch (prefs.getInt("prayerTimesSound", 15)) {
                    case 1:
                        resID = R.raw.azan_maky_first;
                        break;
                    case 2:
                        resID = R.raw.azan_maky_sec;
                        break;
                    case 3:
                        resID = R.raw.azan_madny_first;
                        break;
                    case 16:
                        resID = R.raw.azan_madny_sec;
                        break;
                    case 4:
                        resID = R.raw.azan_qatar;
                        break;
                    case 5:
                        Log.d("TAGyeshena", "chh: "+resID);
                        resID = R.raw.azan_sound_1;
                        break;
                    case 6:
                        resID = R.raw.azan_sound_2;
                        break;
                    case 7:
                        resID = R.raw.azan_sound_3;
                        break;
                    case 8:
                        resID = R.raw.azan_sound_4;
                        break;
                    case 9:
                        resID = R.raw.azan_sound_5;
                        break;
                    case 10:
                        resID = R.raw.azan_sound_6;
                        break;
                    case 11:
                        resID = R.raw.azan_sound_7;
                        break;
                    case 12:
                        resID = R.raw.azan_sound_8;
                        break;
                    case 13:
                        resID = R.raw.azan_sound_9;
                        break;
                    case 14:
                        resID = R.raw.azan_sound_10;
                        break;
                    case 15:
                        resID = R.raw.azan_sound_0;
                        break;
                    default:
                        resID = R.raw.azan_sound_0;
                        break;
                }
            } else {
                switch (prefs.getInt("prayerTimesSpecialAlarmFajrSound", 0)) {
                    case 1:
                        resID = R.raw.fagr_sound_0;
                        break;
                    case 2:
                        resID = R.raw.fagr_sound_1;
                        break;
                    case 3:
                        resID = R.raw.fagr_sound_2;
                        break;
                    case 4:
                        resID = R.raw.fagr_sound_3;
                        break;
                    case 5:
                        resID = R.raw.fagr_sound_4;
                        break;
                    case 6:
                        resID = R.raw.fagr_sound_5;
                        break;
                    default:
                        resID = R.raw.fagr_sound_0;
                }
            }
        }
*/
//        Log.d("TAGresID", "chh: "+resID);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification.Builder notificationBuilder =
                    new Notification.Builder(context, "NOTIFICATION_CHANNEL_ID")
                            .setSmallIcon(android.R.drawable.arrow_up_float)
                            .setContentTitle(title)
                            .setContentText(description)
                            .setAutoCancel(true);
            //.setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
            //.setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);

            NotificationChannel notificationChannel = new NotificationChannel("NOTIFICATION_CHANNEL_ID",
                    "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            notificationChannel.setDescription("messageBody");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

//            Log.d("TAGtty", "chh: "+Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ context.getApplicationContext().getPackageName()
//                    + "/" + R.raw.azan_sound_4));

            notificationChannel.setSound(Uri.parse(audioPath), att);
            notificationManager.createNotificationChannel(notificationChannel);
            Log.d("TAGdasdasd", "chh: ");
//        if (imageThumbnail != null) {
//            notificationBuilder.setStyle(new Notification.BigPictureStyle()
//                    .bigPicture(imageThumbnail).setSummaryText(messageBody));
//        }

//            Log.d("TAGEEEE", "chh: "+notificationChannel.getSound().toString());

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


        } else {
            Log.d("TAGaaaaaaaaaaaa", "chh: ");
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("pTitle")
                            .setContentText("messageBody")
                            .setAutoCancel(true)
                            .setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                            .setSound(defaultSoundUri);
//                        .setContentIntent(pendingIntent);
//        if (imageThumbnail != null) {
//            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
//                    .bigPicture(imageThumbnail).setSummaryText(messageBody));
//        }
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        }
    }


    public void chhh2(Context context, int audioID, String title, String description, String audioPath) {
        String CHANNEL_ID = "1234331";
        int CHANNEL_ID2 = 12342212;


        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
        int resID;

        int returnSoundNumber = prefs.getInt("prayerTimesSound", 15);
        Log.d("prayerTimesSoundlog", String.valueOf(returnSoundNumber));
        System.out.println("prayerTimesSound = number = " + returnSoundNumber);


        resID = R.raw.azan_sound_4;

        selectedChannelID += resID;
        CHANNEL_ID2 += resID;
        if (audioID > 10) {
            if (audioID != 102) {
                switch (prefs.getInt("prayerTimesSound", 15)) {
                    case 1:
                        resID = R.raw.azan_maky_first;
                        break;
                    case 2:
                        resID = R.raw.azan_maky_sec;
                        break;
                    case 3:
                        resID = R.raw.azan_madny_first;
                        break;
                    case 16:
                        resID = R.raw.azan_madny_sec;
                        break;
                    case 4:
                        resID = R.raw.azan_qatar;
                        break;
                    case 5:
                        resID = R.raw.azan_sound_1;
                        break;
                    case 6:
                        resID = R.raw.azan_sound_2;
                        break;
                    case 7:
                        resID = R.raw.azan_sound_3;
                        break;
                    case 8:
                        resID = R.raw.azan_sound_4;
                        break;
                    case 9:
                        resID = R.raw.azan_sound_5;
                        break;
                    case 10:
                        resID = R.raw.azan_sound_6;
                        break;
                    case 11:
                        resID = R.raw.azan_sound_7;
                        break;
                    case 12:
                        resID = R.raw.azan_sound_8;
                        break;
                    case 13:
                        resID = R.raw.azan_sound_9;
                        break;
                    case 14:
                        resID = R.raw.azan_sound_10;
                        break;
                    case 15:
                        resID = R.raw.azan_sound_0;
                        break;
                    default:
                        resID = R.raw.azan_sound_0;
                        break;
                }
            } else {
                switch (prefs.getInt("prayerTimesSpecialAlarmFajrSound", 0)) {
                    case 1:
                        resID = R.raw.fagr_sound_0;
                        break;
                    case 2:
                        resID = R.raw.fagr_sound_1;
                        break;
                    case 3:
                        resID = R.raw.fagr_sound_2;
                        break;
                    case 4:
                        resID = R.raw.fagr_sound_3;
                        break;
                    case 5:
                        resID = R.raw.fagr_sound_4;
                        break;
                    case 6:
                        resID = R.raw.fagr_sound_5;
                        break;
                    default:
                        resID = R.raw.fagr_sound_0;
                }
            }
        }


        switch (audioID) {
            /***/
            case 1:
                sound = MediaPlayer.create(context, R.raw.estghfar_0);
                break;
            case 2:
                sound = MediaPlayer.create(context, R.raw.estghfar_1);
                break;
            case 3:
                sound = MediaPlayer.create(context, R.raw.estghfar_3);
                break;
            case 4:
            case 9:
                sound = MediaPlayer.create(context, R.raw.estghfar_9);
                break;
            case 5:
                sound = MediaPlayer.create(context, R.raw.estghfar_5);
                break;
            case 6:
                sound = MediaPlayer.create(context, R.raw.estghfar_6);
                break;
            case 7:
                sound = MediaPlayer.create(context, R.raw.estghfar_7);
                break;
            case 8:
                sound = MediaPlayer.create(context, R.raw.estghfar_8);
                break;
            default:
                sound = MediaPlayer.create(context, resID);
                break;
        }


        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getApplicationContext().getPackageName() + "/" + R.raw.azan_maky_first);
        mNotificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "channel2", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setLightColor(Color.GRAY);
            mChannel.enableLights(true);
            mChannel.setDescription("shapan mohsen khaled");
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            mChannel.setSound(soundUri, audioAttributes);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

        Log.d("resTAG", "chhh: " + resID + " R.raw" + R.raw.azan_sound_14);

        //General code:
        NotificationCompat.Builder status = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID);
        status.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.arrow_up_float)
                //.setOnlyAlertOnce(true)
                .setContentTitle(title)
                .setContentText(description)
                .setVibrate(new long[]{0, 500, 1000})
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + resID));
//            .setContentIntent(pendingIntent)
//            .setContent(views);

        mNotificationManager.notify(3, status.build());
        /**
         * if want to push many notification at the same time put different value in notifiy(value, status.build)
         */


    }


    //    public void playSound(Context context) {
//        System.out.println("secd = " + R.raw.azan_madny_sec);
//        System.out.println("secd = " + R.raw.azan_madny_first);
//        MediaPlayer sound = MediaPlayer.create(context, R.raw.estghfar_0);
//        sound.start();
//        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.release();
//                NotificationManager notificationManager =
//                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancelAll();
////                closeHandler = Handler();
//            }
//
//        });
//
//
////        quadrantChangeSound.start();
//    }



    /*
    public void showNotification(Context context){
        Notification notification = new NotificationCompat.Builder(context,"3000")
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Title")
                .setContentText("This is a default notification")
                .build();

    }
*/




/*
    public void KhaledNotification(Context context,String audioPath){
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.azan_maky_first);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            NotificationChannel mChannel = new NotificationChannel("YOUR_CHANNEL_ID",
//                    "YOUR CHANNEL NAME",
//                    NotificationManager.IMPORTANCE_DEFAULT)

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            Log.d("TAGmodoooooooooo", "KhaledNotification: ");
            mChannel.setDescription("modooooooooo");
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setSound(sound, attributes); // This is IMPORTANT


            if (mNotificationManager != null)
                mNotificationManager.createNotificationChannel(mChannel);
        }
    }
*/


    public void buildNotification(Context context, String audioPath) {
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getApplicationContext().getPackageName() + "/" + R.raw.azan_qatar);
        NotificationManager mNotificationManager = (NotificationManager) context.getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setLightColor(Color.GRAY);
            mChannel.enableLights(true);
            mChannel.setDescription("Utils.CHANNEL_SIREN_DESCRIPTION###########");
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            mChannel.setSound(soundUri, audioAttributes);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "My Notification")
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setLargeIcon(BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setTicker("title")
                .setContentTitle("contentTitle###########")
                .setContentText("contentText$$$$$$$$$$$")
                .setAutoCancel(true)
                .setLights(0xff0000ff, 300, 1000) // blue color
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        }

        int NOTIFICATION_ID = 1; // Causes to update the same notification over and over again.
        if (mNotificationManager != null) {
            mNotificationManager.notify(1664, mBuilder.build());
        }

    }


    private void showAlarmNotification(Context context) {
        NotificationManager notificationManager33 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "monChannel")
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("title")
                .setContentText("description##########################")
                .setSound(Uri.parse(String.valueOf(alarmSound)));


        Log.d("TAGshowAlarmNotifNougat", "showAlarmNotification: ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("Channel_ID",
                    "MonChannel_Name", NotificationManager.IMPORTANCE_HIGH);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            Log.d("TAGshowAlarmNotifOreo", "showAlarmNotification: ");

            notificationChannel.setSound(alarmSound, audioAttributes);

            if (notificationManager33 != null) {
                notificationManager33.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = mBuilder.build();

        if (notificationManager != null) {
            notificationManager.notify(355, notification);
        }


    }


    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, BroadcastReceiver.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "My Notification")
                        .setSmallIcon(0)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }




/*
    public void buildNotification2(Context context,String audioPath){
        NotificationChannel channel;
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ context.getApplicationContext().getPackageName()
                + "/" + R.raw.azan_qatar);
//        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = context.getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .build();
//            Log.d("TAG#########$$$$$$$$$$$", "buildNotification: ");
//            channel.setSound(soundUri, audioAttributes);
//
//            if (manager != null) {
//                manager.createNotificationChannel( channel );
//            }
//
//        }

//        NotificationCompat.Builder status = new NotificationCompat.Builder(context.getApplicationContext(),CHANNEL_ID);
//        Log.d("TAG@@@@@@@@@@@@@@@@@@@", "buildNotification: ");
//        status.setAutoCancel(true)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(android.R.drawable.arrow_up_float)
//                //.setOnlyAlertOnce(true)
//                .setContentTitle("getString(R.string.app_name)")
//                .setContentText("description##########################")
//                .setVibrate(new long[]{0, 500, 1000})
//                .setDefaults(Notification.DEFAULT_LIGHTS )
//                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" +context.getPackageName()+"/"+R.raw.azan_maky_first));
////                .setContentIntent(pendingIntent)
////                .setContent("views");
//
//        mNotificationManager.notify(30004, status.build());


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"My Notification");
        builder.setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("title")
                .setContentText("description##########################")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(Uri.parse(audioPath));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            Log.d("TAG#########$$$$$$$$$$$", "buildNotification: ");
            channel.setSound(soundUri, audioAttributes);

            if (manager != null) {
                Log.d("TAGman", "buildNotification2: ");
                manager.createNotificationChannel( channel );
            }

        }




        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1113,builder.build());



    }

*/

}
