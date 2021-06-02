package main.tls_maps.Notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

public class NotificationTimer extends BroadcastReceiver {

    Notification notification;

    public NotificationTimer () {
        super();
    };

    public NotificationTimer(String msg, String title, Context context) {
        this.notification = new Notification(msg, title, context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        Log.d("Receiver: ", "FIRED");
        android.app.Notification notification = intent.getParcelableExtra( "Notification" );
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        NM.notify(1 ,notification);
    }
}
