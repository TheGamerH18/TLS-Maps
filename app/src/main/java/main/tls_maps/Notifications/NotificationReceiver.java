package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int noteID = intent.getIntExtra("NoteID", -1);
        if(noteID == -1) return;
        Toast.makeText(context, ""+noteID, Toast.LENGTH_LONG).show();
    }
}
