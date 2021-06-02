package main.tls_maps.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.Snackbar;

import main.tls_maps.R;
import main.tls_maps.databinding.FragmentNotesBinding;

import static main.tls_maps.NoteItems.NotesContent.ITEMS;

public class Notification {

    protected main.tls_maps.Notifications.Notification Notification;
    String msg, title;
    static int ID = 0;
    int notificationID;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;

    public Notification () {
        super();
    }

    public Notification(String msg, String title, Context context) {
        CharSequence name = "studentChannel";
        String description = "Channel for notification";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if ( Build.VERSION.SDK_INT >= 26 ) {
            NotificationChannel channel = new NotificationChannel(""+ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager = NotificationManagerCompat.from(context);
        title = "TLS-Maps " + title;
        builder = new NotificationCompat.Builder(context, ""+ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        this.notificationID = ID;
        ++ID;
    }

    protected void Notify (android.app.Notification notification) {
        if(notification == null)
            return;
        notificationManager.notify(this.notificationID, notification);
    }
    public void Notify () {
        if(notificationManager == null)
            return;
        Log.d("","Notify");
        notificationManager.notify(this.notificationID, this.builder.build());
    }

    public android.app.Notification getNotification() {
        if(this.builder == null)
            return new android.app.Notification();
        return this.builder.build();
    }
}
