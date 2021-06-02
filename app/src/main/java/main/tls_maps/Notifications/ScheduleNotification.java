package main.tls_maps.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class ScheduleNotification {

    private final Notification notification;


    /**
     * This Class is to create a Time / Date Specific Notification
     * @param msg - its the Message him self
     * @param title - the Title of the Notification
     * @param context - Context of the Application
     * @param delay - the Delay in Milliseconds
     *
     * {@link Notification}
     */
    public ScheduleNotification (String msg, String title, int delay, Context context) {

        // Create the Notification from our self Notification Class
        this.notification = new Notification(msg, title, context);

        // Create the Intent
        Intent notificationIntent = new Intent(context, NotificationTimer.class);
        // Put a ID
        notificationIntent.putExtra("ID", notification.notificationID) ;
        // Put the Notification
        notificationIntent.putExtra("Notification" , notification.getNotification());
        // Start / Finish the Intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast( context, 0 , notificationIntent , PendingIntent.FLAG_ONE_SHOT ) ;

        // Create the Alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Set the Time
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , delay, pendingIntent);
    }


    // Constructor Overload
    public ScheduleNotification (String msg, String title, Context context) {
        this(msg, title, 0, context);
    }

    // Constructor Overload
    public ScheduleNotification (String msg, Context context) {
        this(msg, "", 0, context);
    }
}
