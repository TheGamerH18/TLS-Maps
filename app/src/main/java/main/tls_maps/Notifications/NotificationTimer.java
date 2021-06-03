package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class NotificationTimer extends BroadcastReceiver {

    /**
     * Empty Constructor for the Init of the Receiver
     */
    public NotificationTimer () {
        super();
    };

    @Override
    public void onReceive(Context context, Intent intent) {

        // Get the Notification Message
        String msg = intent.getStringExtra("Notification");

        // Create the Notification
        Notification notification = new Notification(msg, context);

        // Create the Notification Manager
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        // send the Notification
        NM.notify(notification.notificationID ,notification.getNotification());
    }
}
