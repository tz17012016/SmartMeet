package com.example.smartmeet.Data.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartmeet.Data.Model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "meetings.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE meetings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "description TEXT," +
                "dateTime INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS meetings");
        onCreate(db);
    }

    // הוספת פגישה חדשה למסד הנתונים
    public void addMeeting(Meeting meeting) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", meeting.getTitle());
        values.put("description", meeting.getDescription());
        values.put("dateTime", meeting.getDateTime());

        db.insert("meetings", null, values);
        db.close();
    }

    // קבלת כל הפגישות
    public List<Meeting> getAllMeetings() {
        List<Meeting> meetings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM meetings", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow("dateTime"));

                meetings.add(new Meeting(id, title, description, dateTime));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return meetings;
    }

    // עדכון פגישה קיימת
    public int updateMeeting(Meeting meeting) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", meeting.getTitle());
        values.put("description", meeting.getDescription());
        values.put("dateTime", meeting.getDateTime());

        // עדכון לפי ID של הפגישה
        return db.update("meetings", values, "id = ?", new String[]{String.valueOf(meeting.getId())});
    }

    // מחיקת פגישה לפי ID
    public void deleteMeeting(int meetingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("meetings", "id = ?", new String[]{String.valueOf(meetingId)});
        db.close();
    }

    // קבלת פגישה לפי ID
    public Meeting getMeetingById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("meetings",
                new String[]{"id", "title", "description", "dateTime"},
                "id = ?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int meetingId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow("dateTime"));

            Meeting meeting = new Meeting(meetingId, title, description, dateTime);
            cursor.close();
            return meeting;
        } else {
            return null;
        }
    }
}

