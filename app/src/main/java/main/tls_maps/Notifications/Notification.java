package main.tls_maps.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import main.tls_maps.R;

public class Notification {

    private static final String title = "TLS-Maps";
    static int ID = 0;
    public final int notificationID;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;

    /**
     * Creates a new Notification
     * @param msg - Message of Notification
     * @param context - Context of Application
     */
    public Notification(String msg, Context context) {
        // Tries to create a new NotificationChannel in > Android 8.0
        if ( Build.VERSION.SDK_INT >= 26 ) {
            // Name of Channel | Description of Channel | Importance of Channel
            CharSequence name = "studentChannel";
            String description = "Channel for notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            // Create Object of Channel
            NotificationChannel channel = new NotificationChannel(""+ID, name, importance);
            channel.setDescription(description);

            // Add Channel to NotificationManager
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager = NotificationManagerCompat.from(context);
        // Create Notification
        builder = new NotificationCompat.Builder(context, ""+ID)
                .setSmallIcon(R.drawable.ic_reminder)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        this.notificationID = ID;
        ++ID;
    }

    public void Notify () {
        if(notificationManager == null)
            return;
        notificationManager.notify(this.notificationID, this.builder.build());
    }

    public android.app.Notification getNotification() {
        if(this.builder == null)
            return new android.app.Notification();
        return this.builder.build();
    }
}
