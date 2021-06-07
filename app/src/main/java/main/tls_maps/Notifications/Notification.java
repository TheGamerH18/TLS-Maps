package main.tls_maps.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import main.tls_maps.R;
import main.tls_maps.ui.notes.NotesFragment;

public class Notification {

    private static final String title = "TLS-Maps";
    final int UID;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;
    /**
     * Creates a new Notification
     * @param msg - Message of Notification
     * @param context - Context of Application
     * @param UID - Unique Identifier, for Channel and Notification
     */
    public Notification(String msg, Context context, int UID) {
        this.UID = UID;

        // Tries to create a new NotificationChannel in > Android 8.0
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            // Name of Channel | Description of Channel | Importance of Channel
            CharSequence name = "studentChannel";
            String description = "Channel for notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            // Create Object of Channel
            NotificationChannel channel = new NotificationChannel(""+this.UID, name, importance);
            channel.setDescription(description);

            // Add Channel to NotificationManager
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager = NotificationManagerCompat.from(context);
        // Create Notification
        builder = new NotificationCompat.Builder(context, ""+this.UID)
                .setSmallIcon(R.drawable.ic_reminder)
                .setContentTitle(title)
                .setContentText(msg)
                .setVibrate(new long[]{0, 500, 0})
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    /**
     * @return the finished notification
     */
    protected android.app.Notification getNotification() {
        if(this.builder == null)
            return new android.app.Notification();
        return this.builder.build();
    }
}
