package main.tls_maps.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    // To create a Instant Notification
    public ScheduleNotification (String msg, Context context) {
        this(msg, 0, context);
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
