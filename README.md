# 📅 Meeting Scheduler App

This project is a **Meeting Scheduler** app built with Android, designed to help users manage and get reminders for their meetings. The app leverages Android's native capabilities such as `Fragments`, `SQLite`, `AlarmManager`, and `Notifications` to deliver a smooth and responsive user experience.

## 🛠️ Technologies Used

- **Java (Android SDK)**: The primary language for building the app, using Android's SDK for activity and fragment management, background services, notifications, and more.
- **MVC Architecture**: The app follows the **Model-View-Controller** design pattern, ensuring a clean separation of concerns between the UI, business logic, and data.
- **SQLite Database**: Used to store and persist the meeting data locally on the device. This allows the app to function offline and ensure data is available across app sessions.
- **AlarmManager**: Utilized to schedule reminders for upcoming meetings. The app sets alarms based on the meeting time to trigger notifications at the appropriate moment.
- **Notifications**: Android's notification system is used to send alerts to the user when a meeting is about to start, ensuring the user stays informed about their schedule.
- **BroadcastReceiver**: Used to capture alarms from the `AlarmManager` and trigger the notifications to remind the user about upcoming meetings.
- **Fragments**: For a responsive and modular UI, fragments are used to manage different sections of the app, such as the meeting list and the meeting editor.
- **DatePickerDialog & TimePickerDialog**: Android's native dialogs for date and time selection, allowing users to easily set the meeting's date and time.

## 📱 Application Overview

The **Meeting Scheduler App** allows users to manage their meetings, set reminders, and get notified when a meeting is near. The app is simple yet effective in keeping track of upcoming meetings and ensuring that users don't miss important appointments.

### Key Features:
- **Add New Meetings**: Users can create a new meeting with a title, description, and set the date & time.
- **Edit or Delete Existing Meetings**: Easily edit the details of any scheduled meeting or delete unwanted meetings.
- **Date & Time Selection**: The app includes an intuitive date and time picker for scheduling meetings.
- **Automatic Reminders**: Users will receive notifications when a meeting is about to start. The app uses `AlarmManager` to ensure timely reminders, even if the app is in the background.
- **Persistent Data Storage**: All meeting information is stored in an **SQLite** database, so users can close and reopen the app without losing any data.

## 🚀 How to Use

1. **Create a Meeting**:
   - Tap the "Add Meeting" button.
   - Fill in the title and description.
   - Choose the date and time for the meeting.
   - Save the meeting.

2. **View Meetings**:
   - On the main screen, you will see a list of all upcoming meetings along with their scheduled date and time.

3. **Edit or Delete a Meeting**:
   - Tap on any meeting in the list to edit its details.
   - If you want to delete the meeting, select the "Delete" option.

4. **Receive Notifications**:
   - When the scheduled meeting time is near, the app will trigger a notification reminding you of the upcoming meeting.

---
##  Main screen
![image](https://github.com/user-attachments/assets/32a47263-a70c-4d1b-821a-7dca706eeb11)

##  The screen for adding a meeting includes the use of a date picker
![image](https://github.com/user-attachments/assets/8f712fd1-dba6-4e3a-99ad-8bb0ebcaea7a)

##  The main screen after creating a meeting
![image](https://github.com/user-attachments/assets/e0f6c5b7-4218-43b0-8151-a674197abd36)





