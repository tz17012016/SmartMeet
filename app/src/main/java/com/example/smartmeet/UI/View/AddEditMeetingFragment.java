package com.example.smartmeet.UI.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartmeet.Data.Model.Meeting;
import com.example.smartmeet.UI.Controller.MainActivity;
import com.example.smartmeet.R;

import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AddEditMeetingFragment extends Fragment {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button dateButton;
    private Button saveButton;
    private TextView dateTextView; // להצגת התאריך והשעה הנבחרים
    private long selectedDateTime;
    private Meeting existingMeeting;  // שדה לפגישה קיימת (במקרה של עריכה)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_meeting, container, false);

        titleEditText = view.findViewById(R.id.edit_text_title);
        descriptionEditText = view.findViewById(R.id.edit_text_description);
        dateButton = view.findViewById(R.id.button_date);
        saveButton = view.findViewById(R.id.button_save);
        dateTextView = view.findViewById(R.id.text_view_date); // הצגת התאריך והשעה

        // בדיקה אם יש פגישה לעריכה (מעביר אותה דרך Bundle)
        if (getArguments() != null) {
            int meetingId = getArguments().getInt("meeting_id");
            existingMeeting = ((MainActivity) getActivity()).getMeetingById(meetingId);

            if (existingMeeting != null) {
                titleEditText.setText(existingMeeting.getTitle());
                descriptionEditText.setText(existingMeeting.getDescription());
                selectedDateTime = existingMeeting.getDateTime();
                dateTextView.setText(formatDateTime(selectedDateTime)); // הצגת תאריך ושעה נוכחיים
            }
        }

        // לחיצה לבחירת תאריך ושעה
        dateButton.setOnClickListener(v -> showDateTimePickerDialog());
        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (existingMeeting == null) {
                // יצירת פגישה חדשה
                Meeting meeting = new Meeting(0, title, description, selectedDateTime);
                ((MainActivity) getActivity()).addMeeting(meeting);

                // הגדרת תזכורת לפגישה החדשה
                ((MainActivity) getActivity()).setMeetingReminder(meeting);
            } else {
                // עדכון פגישה קיימת
                existingMeeting.setTitle(title);
                existingMeeting.setDescription(description);
                existingMeeting.setDateTime(selectedDateTime);
                ((MainActivity) getActivity()).updateMeeting(existingMeeting);

                // הגדרת תזכורת לפגישה המעודכנת
                ((MainActivity) getActivity()).setMeetingReminder(existingMeeting);
            }

            // חזרה למסך הראשי
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MeetingListFragment())
                    .commit();
        });
        // לחיצה לשמירת הפגישה
        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (existingMeeting == null) {
                // יצירת פגישה חדשה
                Meeting meeting = new Meeting(0, title, description, selectedDateTime);
                ((MainActivity) getActivity()).addMeeting(meeting);
            } else {
                // עדכון פגישה קיימת
                existingMeeting.setTitle(title);
                existingMeeting.setDescription(description);
                existingMeeting.setDateTime(selectedDateTime);
                ((MainActivity) getActivity()).updateMeeting(existingMeeting);
            }

            // חזרה למסך הראשי
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MeetingListFragment())
                    .commit();
        });

        return view;
    }

    private void showDateTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (timeView, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                selectedDateTime = calendar.getTimeInMillis();
                if (dateTextView != null) {
                    dateTextView.setText(formatDateTime(selectedDateTime)); // עדכון תאריך ושעה שנבחרו
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    // פונקציה לפורמט התאריך והשעה להצגה ב-TextView
    private String formatDateTime(long dateTimeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(dateTimeInMillis));
    }
}

