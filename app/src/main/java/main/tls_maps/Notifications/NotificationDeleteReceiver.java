package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import main.tls_maps.MainActivity;
import main.tls_maps.NoteItems.NotesContent;

/**
 * BroadcastReceiver is called, if the user has deleted the Notification, without interacting with it.
 * Removes the Notification UID from the specified Note, either using the Object in {@link MainActivity} or
 * by creating its own Object.
 */
public class NotificationDeleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int NoteUID = intent.getIntExtra("NoteUID", -1);
        int NotificationUID = intent.getIntExtra("UID", -1);

        // Tries to call the Object in MainActivity, otherwise creates its own Object
        try {
            MainActivity.notes.getbyUID(context, NoteUID).removeNotification(NotificationUID);
            MainActivity.notes.writelist(context);
        } catch (NullPointerException e) {
            NotesContent notesContent = new NotesContent(context);
            notesContent.getbyUID(context, NoteUID).removeNotification(NotificationUID);
            notesContent.writelist(context);
        }
    }
}
