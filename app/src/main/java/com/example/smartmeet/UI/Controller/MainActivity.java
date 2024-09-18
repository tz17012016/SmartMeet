package com.example.smartmeet.UI.Controller;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartmeet.Data.DB.DatabaseHelper;
import com.example.smartmeet.Data.Model.Meeting;
import com.example.smartmeet.R;
import com.example.smartmeet.Services.NotificationService;
import com.example.smartmeet.UI.View.MeetingListFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // יישום Insets עבור ממשק המשתמש
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // אתחול מנהל מסד הנתונים
        dbHelper = new DatabaseHelper(this);

        // טען את ה-Fragment של רשימת הפגישות
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MeetingListFragment())
                .commit();
    }
    @SuppressLint("ScheduleExactAlarm")
    public void setMeetingReminder(Meeting meeting) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("meeting_title", meeting.getTitle());
        intent.putExtra("meeting_time", meeting.getDateTime());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) meeting.getDateTime(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // הגדרת הזמן לתזכורת (מתי הפגישה תתחיל)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, meeting.getDateTime(), pendingIntent);
    }

    // הוספת פגישה חדשה
    public void addMeeting(Meeting meeting) {
        dbHelper.addMeeting(meeting);
    }

    // קבלת כל הפגישות
    public List<Meeting> getAllMeetings() {
        return dbHelper.getAllMeetings();
    }

    // עדכון פגישה קיימת
    public void updateMeeting(Meeting meeting) {
        dbHelper.updateMeeting(meeting);
    }

    // מחיקת פגישה
    public void deleteMeeting(int meetingId) {
        dbHelper.deleteMeeting(meetingId);
    }

    // קבלת פגישה לפי ID
    public Meeting getMeetingById(int id) {
        return dbHelper.getMeetingById(id);
    }
}
