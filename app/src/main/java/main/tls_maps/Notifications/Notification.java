package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.Snackbar;

import main.tls_maps.R;
import main.tls_maps.databinding.FragmentNotesBinding;

import static main.tls_maps.NoteItems.NotesContent.ITEMS;

public abstract class Notification extends BroadcastReceiver {

    String msg, title;
    static int ID = 0;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;


    protected Notification (String msg, String title, Context context) {
        notificationManager = NotificationManagerCompat.from(context);
        title = "TLS-Maps " + title;
        builder = new NotificationCompat.Builder(context, ""+this.ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        ++ID;
    }

    protected void Notify (android.app.Notification notification) {
        notificationManager.notify(this.ID, notification);
    }
    public void Notify () {
        notificationManager.notify(this.ID, this.builder.build());
    }

    public android.app.Notification getNotification() {
        if(this.builder == null)
            return new android.app.Notification();
        return this.builder.build();
    }
}
