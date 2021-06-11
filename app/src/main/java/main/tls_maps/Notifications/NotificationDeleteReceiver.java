package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import main.tls_maps.MainActivity;
import main.tls_maps.NoteItems.NotesContent;

public class NotificationDeleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int NoteUID = intent.getIntExtra("NoteUID", -1);
        int NotificationUID = intent.getIntExtra("UID", -1);
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
