package main.tls_maps.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class ScheduleNotification {

    private final Notification notification;

    public ScheduleNotification (String msg, String title, int delay, Context context) {

        this.notification = new Notification(msg, title, context);

        Intent notificationIntent = new Intent(context, NotificationTimer.class);
        notificationIntent.putExtra(notification.notificationID+"", 1) ;
        notificationIntent.putExtra("Notification" , notification.getNotification());
        PendingIntent pendingIntent = PendingIntent.getBroadcast( context, 0 , notificationIntent , PendingIntent.FLAG_ONE_SHOT ) ;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , delay, pendingIntent);
    }


    public ScheduleNotification (String msg, String title, Context context) {
        this(msg, title, 0, context);
    }

    public ScheduleNotification (String msg, Context context) {
        this(msg, "", 0, context);
    }
}
