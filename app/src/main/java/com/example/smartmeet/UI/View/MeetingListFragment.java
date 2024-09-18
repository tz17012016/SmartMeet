package com.example.smartmeet.UI.View;

import android.app.AlertDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.smartmeet.Data.Model.Meeting;
import com.example.smartmeet.UI.Controller.MainActivity;
import com.example.smartmeet.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MeetingListFragment extends Fragment {

    private ListView meetingListView;
    private ArrayAdapter<String> adapter;
    private List<Meeting> meetingList;
    private List<String> titles; // הגדרת titles ברמת המחלקה
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);

        meetingListView = view.findViewById(R.id.meeting_list);
        Button addMeetingButton = view.findViewById(R.id.button_add_meeting);

        mainActivity = (MainActivity) getActivity();

        // אתחול רשימת הכותרות
        titles = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, titles);
        meetingListView.setAdapter(adapter);

        // רענון הרשימה
        reloadMeetingList();

        // לחיצה על כפתור הוספת פגישה חדשה
        addMeetingButton.setOnClickListener(v -> {
            AddEditMeetingFragment fragment = new AddEditMeetingFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        });

        // לחיצה על פריט כדי לערוך או למחוק
        meetingListView.setOnItemClickListener((parent, view1, position, id) -> {
            Meeting selectedMeeting = meetingList.get(position);

            // הצגת תיבת דו-שיח עם אפשרויות עריכה או מחיקה
            new AlertDialog.Builder(getContext())
                    .setTitle("בחר פעולה")
                    .setItems(new CharSequence[]{"ערוך", "מחק"}, (dialog, which) -> {
                        if (which == 0) {
                            // עריכת פגישה
                            AddEditMeetingFragment fragment = new AddEditMeetingFragment();
                            Bundle args = new Bundle();
                            args.putInt("meeting_id", selectedMeeting.getId());
                            fragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .commit();
                        } else if (which == 1) {
                            // מחיקת פגישה
                            mainActivity.deleteMeeting(selectedMeeting.getId());
                            reloadMeetingList(); // עדכון הרשימה לאחר מחיקה
                        }
                    })
                    .show();
        });

        return view;
    }

    // רענון הרשימה
    private void reloadMeetingList() {
        meetingList = mainActivity.getAllMeetings();
        titles.clear();
        for (Meeting meeting : meetingList) {
            // הצגת כותרת הפגישה יחד עם התאריך והשעה
            String meetingInfo = meeting.getTitle() + "\n" + formatDateTime(meeting.getDateTime());
            titles.add(meetingInfo);
        }
        adapter.notifyDataSetChanged();
    }

    // פורמט התאריך והשעה להצגה ברשימת הפגישות
    private String formatDateTime(long dateTimeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(dateTimeInMillis));
    }



}
