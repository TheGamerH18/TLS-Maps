package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
    /**
     * called from {@link ScheduleNotification}
     */
    public void onReceive(Context context, Intent intent) {
        // get the notification from the Intent
        android.app.Notification notification = intent.getParcelableExtra("Notification");
        // get the ID from the Notification
        int id = 0;
        try {
            id = Integer.parseInt(intent.getParcelableExtra("ID"));
        } catch (NumberFormatException | NullPointerException e) {
            Log.d("Handled Exception ", e.toString());
        }
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        NM.notify(id, notification);
    }
}
