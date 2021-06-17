package main.tls_maps.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import main.tls_maps.MainActivity;
import main.tls_maps.NoteItems.NotesContent;
import main.tls_maps.R;

/**
 * This Class is creating a Notification. Usually called from {@link ScheduleNotification}
 * or directly to create a Notification instantly. Remember to call
 * {@link Notification#getNotification()} to retrieve the Notification.
 */
public class Notification {

    private static final String title = "TLS-Maps";
    final int UID;
    NotificationCompat.Builder builder;

    /**
     * Creates a Notification with a NoteUID
     * @param context Context of Application
     * @param msg Message of Notification / Usually the Content of the Note
     * @param UID Unique ID of Notification
     * @param NoteUID Unique ID of Note
     */
    public Notification(Context context, String msg, int UID, int NoteUID) {
        this.UID = UID;
        if(!CheckUID(context, this.UID, NoteUID)) return;
        NotificationChannel(context);

        // Create pending Delete Intent
        PendingIntent deleteIntent = broadcastIntent(context, NotificationDeleteReceiver.class, NoteUID);

        // Create pending Action Intent
        PendingIntent actionIntent = broadcastIntent(context, NotificationReceiver.class, NoteUID);

        // Create Notification
        builder = createBuilder(context, msg)
                .addAction(R.mipmap.ic_launcher,
                    context.getString(R.string.NotificationDeleteBtn),
                    actionIntent)
                .setDeleteIntent(deleteIntent);
    }

    /**
     * Creates a new Notification without a NoteUID
     * @param msg Message of Notification
     * @param context Context of Application
     * @param UID Unique Identifier, for Channel and Notification
     */
    public Notification(String msg, Context context, int UID) {
        this.UID = UID;
        NotificationChannel(context);

        builder = createBuilder(context, msg);
    }

    /**
     * Creates a Basic Notification, with a ContentIntent pointing to the NotesFragment.
     * @param context Context of Application
     * @param msg Message of Notification
     * @return NotificationCompat.Builder modified with needed Information
     */
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

    /**
     * Creates the Notification Channel, if needed
     * @param context Context of Application
     */
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

    /**
     * Creates a Pending Intent, Pointing to the NotesFragment.
     * @param context Context of Application
     * @return Pending Intent, Pointing to MainActivity
     */
    private PendingIntent contentIntent(Context context) {
        return new NavDeepLinkBuilder(context)
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.nav_notes)
                .createPendingIntent();
    }

    /**
     * Creates Pending Intents, pointing to the specified {@link BroadcastReceiver} Class
     * @param context  Context of Application
     * @param receiverClass The {@link BroadcastReceiver}, the Intent should be Pointing to
     * @param NoteUID UID of Note
     * @return Pending Intent Pointing to defined BroadcastReceiver
     */
    private PendingIntent broadcastIntent(Context context, Class<? extends BroadcastReceiver> receiverClass, int NoteUID) {
        return PendingIntent.getBroadcast(context,
                this.UID,
                new Intent(context, receiverClass)
                .putExtra("UID", this.UID)
                .putExtra("NoteUID", NoteUID),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Checks if the Notification is still registered in the Note
     * @param context Context of Application
     * @param UID UID of Notification
     * @param NoteUID UID of the Note
     * @return true if the Notification exists
     */
    private boolean CheckUID(Context context, int UID, int NoteUID) {
        try {
            return MainActivity.notes.getbyUID(context, NoteUID).checkNotificationUID(UID);
        } catch (Exception ignored) {
            return new NotesContent(context).getbyUID(context, NoteUID).checkNotificationUID(UID);
        }
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
