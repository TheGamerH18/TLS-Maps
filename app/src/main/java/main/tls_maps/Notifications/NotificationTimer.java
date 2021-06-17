package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

/**
 * BroadcastReceiver called using {@link ScheduleNotification}. Creates and shows the timed Notification
 */
public class NotificationTimer extends BroadcastReceiver {

    private Notification notification;

    /**
     * Empty Constructor for the Init of the Receiver
     * for {@link ScheduleNotification}
     * Notification: {@link Notification}
     */
    public NotificationTimer () {
        super();
    };

    @Override
    public void onReceive(Context context, Intent intent) {

        // Get the Message from the Intent
        String msg = intent.getStringExtra("Notification");

        // Get the UID | Or get a Fallback UID
        int uid = intent.getIntExtra("UID", (int) System.currentTimeMillis());

        // Get the NoteUID or get a Fallback UID
        int noteUID = intent.getIntExtra("NoteUID", -1);

        // Create the Notification, either with or without noteUID
        if(noteUID == -1)
            notification = new Notification(msg, context, uid);
        else
            notification = new Notification(context, msg, uid, noteUID);

        // Create the Notification Manager
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        // Try to send Notification. Usually fails, if the Notification is null
        try {NM.notify(uid, notification.getNotification());}
        catch (Exception ignored){}
    }
}
