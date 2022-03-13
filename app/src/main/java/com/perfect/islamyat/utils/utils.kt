package com.perfect.islamyat.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.perfect.islamyat.AlarmActivity

fun String.monthToArabic(): String {
    var monthName=""
    when (this) {
        "January" -> monthName = "يناير"
        "February" -> monthName = "فبراير"
        "March" -> monthName = "مارس"
        "April" -> monthName = "ابريل"
        "May" -> monthName = "مايو"
        "June" -> monthName = "يونيو"
        "July" -> monthName = "يوليو"
        "August" -> monthName = "أغسطس"
        "September" -> monthName = "سبتمبر"
        "October" -> monthName = "أكتوبر"
        "November" -> monthName = "نوفمبر"
        "December" -> monthName = "ديسمبر"
        else-> monthName=""
    }
    return monthName
}

fun Context.showNotificationWithFullScreenIntent(
    isLockScreen: Boolean = false,
    channelId: String = "EST_4",
    title: String = "Title",
    description: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    audioPath: String = "",
    sound: MediaPlayer

) {
    Log.d("TAGshowNotification", "showNotificationWithFullScreenIntent: ")

    val builder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(android.R.drawable.arrow_up_float)
        .setContentTitle(title)
        .setContentText(description)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
//        .setContentIntent(getFullScreenIntent(true))

        .setFullScreenIntent(getFullScreenIntent(isLockScreen, description,sound), true)
        .setSound(Uri.parse(audioPath))

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    with(notificationManager) {
        buildChannel(audioPath, channelId)


        val notification = builder.build()

        notify(0, notification)


    }
}

private fun NotificationManager.buildChannel(audioPath: String, channelId: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Log.d("TAGVersionOOO", "buildChannel: ")
        val name = "Example Notification Channel"
        val descriptionText = "This is used to demonstrate the Full Screen Intent"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val audioBuilder = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        channel.setSound(Uri.parse(audioPath), audioBuilder);

        createNotificationChannel(channel)



    }
}

private fun Context.getFullScreenIntent(isLockScreen: Boolean, description: String,sound:MediaPlayer): PendingIntent {
    Log.d("TAGetFullScreen", "getFullScreenIntent: ")
//    NotificationReceiver
    val destination = if (isLockScreen)
        AlarmActivity::class.java
    else
        AlarmActivity::class.java
    val intent = Intent(this, destination)
    intent.putExtra("text", description);
//    intent.putExtra("media",sound);
//    sound.stop();
//    sound.release();
//    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    // flags and request code are 0 for the purpose of demonstration
    return PendingIntent.getActivity(this, 0, intent, 0)
}

fun Activity.turnScreenOnAndKeyguardOff() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(true)
        setTurnScreenOn(true)
    } else {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
    }

    with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, null)
        }
    }
}

fun Activity.turnScreenOffAndKeyguardOn() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(false)
        setTurnScreenOn(false)
    } else {
        window.clearFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
    }
}


/*

private fun areNotificationsEnabled(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val manager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!manager.areNotificationsEnabled()) {
            return false
        }
        val channels = manager.notificationChannels
        for (channel in channels) {
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                return false
            }
        }
        true
    } else {
        NotificationManagerCompat.from(context!!).areNotificationsEnabled()
    }
}

private fun openNotificationSettings(){
    val notificationSettingsIntent = Intent()
    notificationSettingsIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
    notificationSettingsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationSettingsIntent.putExtra(
            "android.provider.extra.APP_PACKAGE",
            activity!!.packageName
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        notificationSettingsIntent.putExtra(
            "app_package",
            activity!!.packageName
        )
        notificationSettingsIntent.putExtra(
            "app_uid",
            activity!!.applicationInfo.uid
        )
    }
    activity!!.startActivityForResult(
        notificationSettingsIntent,
        5)
}

*/