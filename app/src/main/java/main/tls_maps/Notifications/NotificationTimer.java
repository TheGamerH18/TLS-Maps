package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

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

        int noteUID = intent.getIntExtra("NoteUID", -1);

        if(noteUID == -1) {
            // Create the Notification, without
            notification = new Notification(msg, context, uid);
        } else {
            notification = new Notification(context, msg, uid, noteUID);
        }

        // Create the Notification Manager
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        // send the Notification
        NM.notify(uid ,notification.getNotification());
    }
}
