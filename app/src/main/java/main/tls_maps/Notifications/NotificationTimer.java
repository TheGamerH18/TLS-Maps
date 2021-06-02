package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class NotificationTimer extends BroadcastReceiver {

    /**
     * This Class is for the Time Based Notification
     */

    public NotificationTimer () {
        // Needed for the init
        super();
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        // get the notification from the Intent
        android.app.Notification notification = intent.getParcelableExtra( "Notification" );
        // get the ID from the Notification
        int id = Integer.parseInt(intent.getParcelableExtra( "ID" ));
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        NM.notify(id, notification);
    }
}
