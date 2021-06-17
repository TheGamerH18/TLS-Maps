package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import main.tls_maps.MainActivity;
import main.tls_maps.NoteItems.NotesContent;
import main.tls_maps.R;

/**
 * BroadcastReceiver is called, if the User clicks on the Deleted button of a {@link Notification}.
 * Removes the Specified Note either using {@link MainActivity#notes} or using its own Object.
 * Cancels the Notification, when called
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get Extras from Intent
        int noteID = intent.getIntExtra("NoteUID", -1);
        int UID = intent.getIntExtra("UID", -1);

        // Make sure values are possible
        if (noteID == -1 || UID == -1) return;

        // Cancel Notification
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        NM.cancel(UID);
        // Create Toast to Notify User about the action
        Toast.makeText(context, R.string.deletednotemsg, Toast.LENGTH_LONG).show();

        // Try to delete the Note, either using the Object in MainActivity or using its own Object
        try {
            MainActivity.notes.removeItem(context, noteID);
        } catch (Exception e) {
            NotesContent notes = new NotesContent(context);
            notes.removeItem(context, noteID);
        }
    }
}
