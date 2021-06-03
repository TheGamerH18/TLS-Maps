package main.tls_maps.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import main.tls_maps.R;

public class Notification {

    // Static ID to get a unique ID
    static int ID = 0;
    int notificationID;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;


    /**
     * This Class is to make a Notification much more easier
     * @param msg - its the Message him self
     * @param title - the Title of the Notification, with addition of TLS-Maps
     * @param context - Context of the Application
     */
    public Notification(String msg, String title, Context context) {

        // Check if the Android Version is below 26
        // Only available in SDK 26 and Heigher
        if ( Build.VERSION.SDK_INT >= 26 ) {
            CharSequence name = "TLS-Maps: Notifications";
            String description = "Channel for Notification";
            // Set the Importance to the Channel
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            // Create the Channel
            NotificationChannel channel = new NotificationChannel(""+ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager = NotificationManagerCompat.from(context);
        // Create the Title
        title = "TLS-Maps " + title;

        // Create the Notification
        builder = new NotificationCompat.Builder(context, ""+ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Save the ID for this Notification
        this.notificationID = ID;
        ++ID;
    }

    public void Notify () {
        // Display the Notification
        if(notificationManager == null)
            return;
        notificationManager.notify(this.notificationID, this.builder.build());
    }

    public android.app.Notification getNotification() {
        // Get the Notification
        if(this.builder == null)
            return new android.app.Notification();
        return this.builder.build();
    }
}
