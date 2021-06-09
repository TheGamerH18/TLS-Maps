package main.tls_maps.NoteItems;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NotesContent {

    private List<Note> ITEMS = new ArrayList<Note>();

    /**
     * Initializes the Notes, Reads all Notes from Filesystem.
     * Needs to be called at start of application
     * @param context - Context of Application
     */
    public NotesContent(Context context){
        readlist(context);
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
    }

    /**
     * Reads the List ITEMS from Filesystem
     * @param act - Context of Application
     */
    private void readlist(Context act) {
        String ser = SerializeObject.ReadSettings(act, "notes.dat");
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
     * @param act - Context of Application
     */
    private void writelist(Context act) {
        String ser = SerializeObject.objectToString((Serializable) ITEMS);
        if(ser != null && !ser.equalsIgnoreCase("")) {
            SerializeObject.WriteSettings(act, ser, "notes.dat");
        }
    }

    private int getUniqueID() {
        return (int) (System.currentTimeMillis() < 0 ?-(System.currentTimeMillis()):System.currentTimeMillis());
    }

    /**
     * A Note containing, the content and a id
     */
    public static class Note implements Serializable{
        public final String content;
        private int id;
        private final int uid;

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

        public int getUid() {
            return uid;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}