package main.tls_maps.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import main.tls_maps.R;

public class Notification {

    String msg, title;
    static int ID = 0;
    public int notificationID;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;

    public Notification(String msg, Context context) {
        if ( Build.VERSION.SDK_INT >= 26 ) {
            CharSequence name = "studentChannel";
            String description = "Channel for notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
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
