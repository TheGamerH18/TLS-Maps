package main.tls_maps.Notifications;

import android.content.Context;
import android.content.Intent;

public class NotificationTimer extends Notification {

    public NotificationTimer(String msg, String title, Context context) {
        super(msg, title, context);
    }

    public NotificationTimer(String msg, Context context) {
        super(msg, "", context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        android.app.Notification notification = intent.getParcelableExtra( "Notification" ) ;
        super.Notify(notification);
    }
}
