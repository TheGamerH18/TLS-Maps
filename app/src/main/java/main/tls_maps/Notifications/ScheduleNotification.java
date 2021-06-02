package main.tls_maps.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;

import main.tls_maps.MainActivity;

public class ScheduleNotification {

    NotificationTimer notification;

    public ScheduleNotification (String msg, String title, int toDate, Context context) {

        this.notification = new NotificationTimer(msg, title, context);

        Intent notificationIntent = new Intent(context, NotificationTimer.class);
        notificationIntent.putExtra("Notification" , notification.getNotification());
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT ) ;
        Date date = new Date();
        long futureInMillis = date.getTime()+(100*60);
        Log.d( "DEBUG",futureInMillis+"");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE );
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME , futureInMillis , pendingIntent);
    }


    public ScheduleNotification (String msg, String title, Context context) {
        this(msg, title, 0, context);
    }

    public ScheduleNotification (String msg, Context context) {
        this(msg, "", 0, context);
    }
}
