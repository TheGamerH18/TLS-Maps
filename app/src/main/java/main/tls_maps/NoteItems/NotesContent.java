package main.tls_maps.NoteItems;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import main.tls_maps.ui.notes.NotesFragment;
import main.tls_maps.ui.notes.NotesRecyclerViewAdapter;

/**
 * Class for providing Notes to the Specific Fragment.
 */
public class NotesContent {

    private List<Note> ITEMS = new ArrayList<>();
    RecyclerView rv;
    NotesFragment nf;

    /**
     * Initializes the Notes, Reads all Notes from Filesystem.
     * Needs to be called at start of application
     * @param context - Context of Application
     */
    public NotesContent(Context context){
        readlist(context);
    }

    /**
     * Returns Note by UID
     * @param UID - UID of the Note
     * @return Note if found, else null
     */
    public Note getbyUID(Context context, int UID) {
        readlist(context);
        for(Note note : ITEMS) {
            if(note.getUid() == UID) return note;
        }
        return null;
    }

    /**
     * Sets the Recyclerview
     * @param recyclerView - RecyclerView
     */
    public void recyclerview(RecyclerView recyclerView, NotesFragment fragment) {
        rv = recyclerView;
        nf = fragment;
    }

    /**
     * @return - List<> ITEMS
     */
    public List<Note> getITEMS(Context act) {
        readlist(act);
        return ITEMS;
    }

    /**
     * Adds a Note to the List, calculates the Identifier and writes it to the Filesystem
     * @param content - Content of the Note
     * @param context - Context of Application
     */
    public void addItem(String content, Context context) {
        int id = ITEMS.size();
        ITEMS.add(new Note(content, id, getUniqueID()));
        writelist(context);
    }

    /**
     * Removes an Item from the List ITEMS
     * @param context - Context of Application
     * @param uid - Index of the Item to delete
     */
    public void removeItem(Context context, int uid) {
        int id = -1;
        for (Note note: ITEMS)
            if (note.getUid() == uid) id = note.getId();
        if(id != -1) ITEMS.remove(id);

        for(int i = 0; i < ITEMS.size(); i ++) ITEMS.get(i).id = i;

        writelist(context);
        if(rv.getAdapter() != null)
            rv.setAdapter(new NotesRecyclerViewAdapter(ITEMS, nf));
    }

    /**
     * Reads the List ITEMS from Filesystem
     * @param context - Context of Application
     */
    private void readlist(Context context) {
        String ser = SerializeObject.ReadSettings(context, "notes.dat");
        if(ser != null && !ser.equalsIgnoreCase("")) {
            Object obj = SerializeObject.stringToObject(ser);
            // Cast in Arraylist
            if(obj instanceof List) {
                //noinspection unchecked
                ITEMS = (List<Note>) obj;
            }
        }
    }

    /**
     * Writes List ITEMS to Filesystem
     * @param context - Context of Application
     */
    public void writelist(Context context) {
        String ser = SerializeObject.objectToString((Serializable) ITEMS);
        if(ser != null && !ser.equalsIgnoreCase("")) {
            SerializeObject.WriteSettings(context, ser, "notes.dat");
        }
    }

    /**
     * Used to create a new Unique ID
     * @return A Unique ID
     */
    private int getUniqueID() {
        return (int) (System.currentTimeMillis() < 0 ?-(System.currentTimeMillis()):System.currentTimeMillis());
    }

    /**
     * A Note containing, the content, a ID and a Unique ID
     */
    public static class Note implements Serializable{
        public final String content;
        private int id;
        private final int uid;
        private final ArrayList<Integer> NotificationUIDs = new ArrayList<>();

        /**
         * Constructor
         * @param content - Content of Note
         * @param id - ID of Note
         */
        public Note(String content, int id, int uid) {
            this.content = content;
            this.id = id;
            this.uid = uid;
        }

        /**
         * Returns the Unique ID of the note to Identify the Specific note
         * @return Unique ID of note
         */
        public int getUid() {
            return uid;
        }

        /**
         * Sets the id of the note if it is above 0
         * @param id - new id
         */
        public void setId(int id) {
            if(id >= 0) this.id = id;
        }

        /**
         * Adds the UID of a Notification to the Note
         * @param uid - UID of the Notification
         */
        public void addNotification(int uid) {
            if(uid != 0) NotificationUIDs.add(uid);
        }

        /**
         * Removes the UID of a Notification from the Note
         * @param uid - UID of the Notification
         */
        public void removeNotification(int uid) {
            int index = -1;
            for(int i = 0; i < NotificationUIDs.size(); i ++) {
                if(NotificationUIDs.get(i) == uid) index = i;
            }
            if(index != -1) NotificationUIDs.remove(index);
        }

        /**
         * Checks if the UID of the Notification is registered in this Note
         * @param uid - UID of the Notification
         * @return true if the UID is registered
         */
        public boolean checkNotificationUID(int uid) {
            for(int UID : NotificationUIDs) {
                if(UID == uid) return true;
            }
            return false;
        }

        /**
         * @return Value of the id
         */
        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}