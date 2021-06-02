package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationTimer extends BroadcastReceiver {

    Notification notification;

    public NotificationTimer () {
        super();
    };

    public NotificationTimer(String msg, String title, Context context) {
        this.notification = new Notification(msg, title, context);
    }

    public NotificationTimer(String msg, Context context) {
        this.notification = new Notification(msg, "", context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(this.notification == null)
            return;
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
        this.notification.Notify(notification);
    }
}
