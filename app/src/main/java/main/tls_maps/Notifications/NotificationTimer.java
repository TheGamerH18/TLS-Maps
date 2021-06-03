package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class NotificationTimer extends BroadcastReceiver {


    Notification notification;

    public NotificationTimer () {
        super();
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("Notification" );
        Notification notification = new Notification(msg, context);
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        NM.notify(notification.notificationID ,notification.getNotification());
    }
}
