package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import main.tls_maps.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int noteID = intent.getIntExtra("NoteID", -1);
        if(noteID == -1) return;
        MainActivity.notes.removeItem(context, noteID);
        Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
    }
}
