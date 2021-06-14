package main.tls_maps.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import main.tls_maps.MainActivity;
import main.tls_maps.NoteItems.NotesContent;
import main.tls_maps.R;

public class Notification {

    private static final String title = "TLS-Maps";
    final int UID;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;

    /**
     * Creates a Notification with a NoteUID
     * @param context - Context of Application
     * @param msg - Message of Notification / Usually the Content of the Note
     * @param UID - Unique ID of Notification
     * @param NoteUID - Unique ID of Note
     */
    public Notification(Context context, String msg, int UID, int NoteUID) {
        this.UID = UID;
        if(!CheckUID(context, this.UID, NoteUID)) return;
        NotificationChannel(context);
        notificationManager = NotificationManagerCompat.from(context);

        Intent deleteIntent = new Intent(context, NotificationDeleteReceiver.class);
        deleteIntent.putExtra("UID", this.UID);
        deleteIntent.putExtra("NoteUID", NoteUID);
        PendingIntent pendingdeleteIntent = PendingIntent.getBroadcast(context,
                this.UID,
                deleteIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        broadcastIntent.putExtra("UID", this.UID);
        broadcastIntent.putExtra("NoteUID", NoteUID);
        broadcastIntent.addCategory("NOTES");
        PendingIntent actionIntent = PendingIntent.getBroadcast(context,
                this.UID,
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create Notification
        builder = createBuilder(context, msg);
        builder.addAction(R.mipmap.ic_launcher, "Löschen", actionIntent);
        builder.setDeleteIntent(pendingdeleteIntent);
    }

    /**
     * Creates a new Notification without a NoteUID
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

    /**
     * Creates a Basic Notification
     * @param context - Context of Application
     * @param msg - Message of Notification
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
     * @param context - Context of Application
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
     * Creates a Pending Intent, Pointing to the MainActivity
     * @param context - Context of Application
     * @return Pending Intent, Pointing to MainActivity
     */
    private PendingIntent contentIntent(Context context) {
        Intent activityintent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, this.UID, activityintent, 0);
    }

    /**
     * Checks if the Notification is still registered in the Note
     * @param context - Context of Application
     * @param UID - UID of Notification
     * @param NoteUID - UID of the Note
     * @return true if the Notification exists
     */
    private boolean CheckUID(Context context, int UID, int NoteUID) {
        NotesContent notesContent = new NotesContent(context);
        return notesContent.getbyUID(context, NoteUID).checkNotificationUID(UID);
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
