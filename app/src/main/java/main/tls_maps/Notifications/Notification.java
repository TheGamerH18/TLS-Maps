package main.tls_maps.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import main.tls_maps.MainActivity;
import main.tls_maps.R;
import main.tls_maps.ui.notes.NotesFragment;

public class Notification {

    private static final String title = "TLS-Maps";
    final int UID;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;

    public Notification(Context context, String msg, int UID, int NoteID) {
        this.UID = UID;
        NotificationChannel(context);
        notificationManager = NotificationManagerCompat.from(context);

        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        broadcastIntent.putExtra("NoteID", NoteID);
        PendingIntent actionIntent = PendingIntent.getBroadcast(context, this.UID, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create Notification
        builder = createBuilder(context, msg);
        builder.addAction(R.mipmap.ic_launcher, "Toast", actionIntent);
    }

    /**
     * Creates a new Notification
     * @param msg - Message of Notification
     * @param context - Context of Application
     * @param UID - Unique Identifier, for Channel and Notification
     */
    public Notification(String msg, Context context, int UID) {
        this.UID = UID;
        NotificationChannel(context);
        notificationManager = NotificationManagerCompat.from(context);

        builder = createBuilder(context, msg);
    }

    private NotificationCompat.Builder createBuilder(Context context, String msg) {
        // Create Notification
        return new NotificationCompat.Builder(context, ""+this.UID)
                .setSmallIcon(R.drawable.ic_reminder)
                .setContentTitle(title)
                .setContentText(msg)
                .setVibrate(new long[]{0, 500, 0})
                .setAutoCancel(true)
                .setContentIntent(contentIntent(context))
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void NotificationChannel(Context context) {
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
    }

    private PendingIntent contentIntent(Context context) {
        Intent activityintent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, this.UID, activityintent, 0);
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
