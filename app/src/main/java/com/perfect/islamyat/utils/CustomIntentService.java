package com.perfect.islamyat.utils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.perfect.islamyat.R;

public class CustomIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 3;

    public CustomIntentService() {
        super("CustomIntentService");
    }

    public NotificationManager notificationManager;
    MediaPlayer sound;
    String CHANNEL_ID_EST = "EST_";
    String CHANNEL_ID_FAJR = "FAJR_";
    String CHANNEL_ID_PRAYER = "PRAYER_";
    String selectedChannelID = "";

    @Override
    protected void onHandleIntent(Intent intent) {
//        String CHANNEL_ID="Prayer";
//        NotificationChannel notificationChannel= null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            notificationChannel = new NotificationChannel(CHANNEL_ID, "Prayer", NotificationManager.IMPORTANCE_HIGH);
//            notificationChannel.enableLights(true);
//            notificationChannel.enableVibration(true);
//            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
//            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
//                    .setContentText("Heading"+ Calendar.getInstance().getTime())
//                    .setContentTitle("subheading")
//                    .setContentIntent(pendingIntent)
//                    .addAction(android.R.drawable.sym_action_chat, "Title", pendingIntent)
//                    .setChannelId(CHANNEL_ID)
//                    .setSmallIcon(android.R.drawable.sym_action_chat)
//                    .build();
//
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(notificationChannel);
////            notificationManager.notify(1, notification);
//            startForeground(1, notification);
//        }
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {

        Log.d("onStarted", String.valueOf(startId));
//        MediaController mController = new MediaController(getApplicationContext());
        int audioID = intent.getExtras().getInt("id");
        Log.d("TAGaudioEEEWWW", "onStart: "+audioID);
//        Intent intentAction = new Intent(getApplicationContext(),ActionReceiver.class);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = null;
        String audioPath = getAudioPath(getApplicationContext(), audioID);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.d("TAGO", "onStart: OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            notificationChannel = new NotificationChannel(selectedChannelID, selectedChannelID, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(Uri.parse(audioPath), new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
//            PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 1, intentAction, PendingIntent.FLAG_CANCEL_CURRENT);

//            Intent deleteIntent = new Intent(this, autostart.class);
//            deleteIntent.putExtra("ACTION", "STOP");
//            PendingIntent deletePendingIntent = PendingIntent.getService(this,
//                    audioID,
//                    deleteIntent,
//                    PendingIntent.FLAG_CANCEL_CURRENT);

//            Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.azan_qatar);

//            Intent intent1 = new Intent(getApplicationContext(), AlarmActivity.class);
//            intent1.putExtra("id",intent.getExtras().getInt("id"));
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 1, intent1, PendingIntent.FLAG_CANCEL_CURRENT);


            /**
             * this what i added
             */
            Notification notification = new Notification.Builder(getApplicationContext(), selectedChannelID)
                    .setContentTitle(getNotificationText(audioID))
                    .setContentIntent(pendingIntent)
                    .setChannelId(selectedChannelID)
                    .setAutoCancel(true)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
//                    .setFullScreenIntent(pendingIntent2,true)
//                    .setOngoing(false)
//                    .addAction(new Notification.Action(R.drawable.qeblatabicon,"TEST",pendingIntent2))
//                    .setSound(sound, AudioManager.STREAM_ALARM)
//                    .setDeleteIntent(deletePendingIntent)
                    .setSmallIcon(R.drawable.splashicon)
                    .build();

            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.createNotificationChannel(notificationChannel);
//            notificationManager.notify(1, notification);
            startForeground(1, notification);
//            startForeground(1,notification,);
//            playRingtone();
//            playSound(getApplicationContext(),audioID);
//            Intent intent1 = new Intent(getApplicationContext(), AlarmActivity.class);
//            intent1.putExtra("id",intent.getExtras().getInt("id"));
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent1);
        }
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
                notificationText = "صلاة الفجر";
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
        Log.d("noti ", notificationText);
        return notificationText;
    }


    public String getAudioPath(Context context, int audioID) {
//        "android.resource://" + getPackageName() + "/" + R.raw.estghfar_0
        String audioPathStr = "android.resource://" + getPackageName() + "/";
        int resID = R.raw.azan_maky_first;
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

    public void playSound(Context context, int audioID) {
        int resID;
        resID = R.raw.azan_maky_first;

        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
        if (audioID > 10) {
            if (audioID != 102) {
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
                    case 4:
                        resID = R.raw.azan_madny_sec;
                        break;
                    case 5:
                        resID = R.raw.azan_qatar;
                        break;
                    case 6:
                        resID = R.raw.azan_sound_1;
                        break;
                    case 7:
                        resID = R.raw.azan_sound_2;
                        break;
                    case 8:
                        resID = R.raw.azan_sound_3;
                        break;
                    case 9:
                        resID = R.raw.azan_sound_4;
                        break;
                    case 10:
                        resID = R.raw.azan_sound_5;
                        break;
                    case 11:
                        resID = R.raw.azan_sound_6;
                        break;
                    case 12:
                        resID = R.raw.azan_sound_7;
                        break;
                    case 13:
                        resID = R.raw.azan_sound_8;
                        break;
                    case 14:
                        resID = R.raw.azan_sound_9;
                        break;
                    case 15:
                        resID = R.raw.azan_sound_10;
                        break;
                    case 16:
                        resID = R.raw.azan_sound_0;
                        break;
                    default:
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
            case 1:
                sound = MediaPlayer.create(context, R.raw.azan_madny_first);
                break;
            case 2:
                sound = MediaPlayer.create(context, R.raw.estghfar_1);
                break;
            case 3:
                sound = MediaPlayer.create(context, R.raw.estghfar_3);
                break;
            case 4:
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
            case 9:
                sound = MediaPlayer.create(context, R.raw.estghfar_9);
                break;
            default:
                sound = MediaPlayer.create(context, resID);
                break;
        }
        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                notificationManager.cancelAll();
                stopForeground(true);
            }

        });
        notificationManager.cancelAll();
        sound.start();
        Boolean isPlaying = true;
        while (isPlaying) {
            Log.v("SOUND", " ---- Playing");
            if (prefs.getBoolean("continuePlaying", false)) {
                Log.v("SOUND", " ---- Stopped");
                isPlaying = false;
//                sound.stop();
//                sound.release();
                stopForeground(true);
            }
        }
    }


    public void startService(Context context) {
        Intent service1 = new Intent(context, CustomIntentService.class);
        context.startService(service1);
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopForeground(true);
    }

    public void playRingtone() {
        try {
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/raw/estghfar_6.mp3");
            RingtoneManager.setActualDefaultRingtoneUri(
                    getApplicationContext(), RingtoneManager.TYPE_NOTIFICATION, path
            );
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), path);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("TAGOnCommand", "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }


}
