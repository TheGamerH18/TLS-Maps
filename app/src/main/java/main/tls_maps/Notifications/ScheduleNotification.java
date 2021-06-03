package main.tls_maps.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class ScheduleNotification {


    public ScheduleNotification (String msg, long delay, Context context) {

        Intent notificationIntent = new Intent(context, NotificationTimer.class);
        notificationIntent.putExtra("Notification" , msg);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT) ;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
    }

    public ScheduleNotification (String msg, Context context) {
        this(msg, 0, context);
    }
}
