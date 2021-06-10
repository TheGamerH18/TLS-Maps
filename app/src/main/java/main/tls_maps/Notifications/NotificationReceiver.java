package main.tls_maps.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import main.tls_maps.MainActivity;
import main.tls_maps.NoteItems.NotesContent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println(intent.getCategories());
        int noteID = intent.getIntExtra("NoteUID", -1);
        int UID = intent.getIntExtra("UID", -1);
        if(noteID == -1) return;
        if(UID == -1) return;
        NotificationManagerCompat NM = NotificationManagerCompat.from(context);
        NM.cancel(UID);
        try {
            MainActivity.notes.removeItem(context, noteID);
        } catch (Exception e) {
            NotesContent notes = new NotesContent(context);
            notes.removeItem(context, noteID);
        }
    }
}
