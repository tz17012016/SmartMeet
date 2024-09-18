package com.example.smartmeet.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.smartmeet.R;

public class NotificationService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // קבלת המידע על הפגישה מתוך ה-Intent
        String meetingTitle = intent.getStringExtra("meeting_title");
        long meetingTime = intent.getLongExtra("meeting_time", 0);

        // הצגת הודעה למשתמש כאשר הפגישה מגיעה
        showNotification(context, meetingTitle, meetingTime);
    }

    // הצגת הודעת ההתראה
    private void showNotification(Context context, String title, long time) {
        // הכנה של ערוץ התראות (באנדרואיד 8 ומעלה)
        String channelId = "meeting_channel";
        String channelName = "Meeting Notifications";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // יצירת הודעה
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification) // אייקון מותאם
                .setContentTitle("תזכורת לפגישה")
                .setContentText("הפגישה שלך: " + title + " תתחיל בקרוב.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // הצגת ההודעה
        notificationManager.notify((int) time, builder.build());
    }
}

