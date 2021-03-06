package main.tls_maps.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import main.tls_maps.MainActivity;

/**
 * Used to create a Notification, either instantly or at a specified time
 * Calls {@link NotificationTimer}.
 */
public class ScheduleNotification {

    /**
     * To Create a time Based Notification
     * @param msg - The Message him self
     * @param triggerAt - the Time when the Event triggers in Milliseconds
     * @param context - The Context of the Application
     */
    public ScheduleNotification (String msg, long triggerAt, Context context) {

        // Create the Intent
        Intent notificationIntent = new Intent(context, NotificationTimer.class);

        // Create UID
        int UID = ((int) getUniqueID());

        // Put the UID in the Intent
        notificationIntent.putExtra("UID" , UID);
        // Put the Message in the Intent
        notificationIntent.putExtra("Notification" , msg);

        // Create The Pending
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                UID ,
                notificationIntent ,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT
        );
        // Create the Alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
    }

    /**
     * Schedules a new Notification for a Note
     * @param context - Context of Application
     * @param msg - Message / Usually the Content of the Note
     * @param triggerAt - Time in Milliseconds, when the Notification should be Triggered
     * @param NoteUID - Unique ID of the Note
     */
    public ScheduleNotification (Context context, String msg, long triggerAt, int NoteUID) {
        Intent notificationIntent = new Intent(context, NotificationTimer.class);
        int uid = getUniqueID();

        // Adds Notification to Note
        MainActivity.notes.getbyUID(context, NoteUID).addNotification(uid);
        MainActivity.notes.writelist(context);

        // Put Extras into the Intent, to Identify the Notification.
        notificationIntent.putExtra("UID", uid);
        notificationIntent.putExtra("Notification", msg);
        notificationIntent.putExtra("NoteUID", NoteUID);

        // Create PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                uid,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT
                );

        // Give the PendingIntent to the Systems Alarm Manager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
    }

    // To create a Instant Notification
    public ScheduleNotification (String msg, Context context) {
        this(msg, 0, context);
    }

    /**
     * Creates a Notification, which is fired Instantly
     * @param context - Context of Application
     * @param msg - Message of the Notification / Usually the Content of the Note
     * @param NoteUID - ID of the Note
     */
    public ScheduleNotification (Context context, String msg, int NoteUID) {
        this(context, msg, 0, NoteUID);
    }

    /**
     * @return a UID based on the Time, to send the
     * the Notification and for the Pending intent,
     * if 2 Pendings have the same ID only 1 should be Fired
     */
    private int getUniqueID() {
        return (int) ((System.currentTimeMillis() < 0)?-(System.currentTimeMillis()):System.currentTimeMillis());
    }
}
