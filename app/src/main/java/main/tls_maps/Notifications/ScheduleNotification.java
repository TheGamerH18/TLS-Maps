package main.tls_maps.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

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

        // Put the Message in the Intent
        notificationIntent.putExtra("Notification" , msg);

        // Create The Pending
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT) ;

        // Create the Alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
    }

    public ScheduleNotification (String msg, Context context) {
        this(msg, 0, context);
    }
}
