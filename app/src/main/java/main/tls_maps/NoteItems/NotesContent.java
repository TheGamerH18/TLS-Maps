package main.tls_maps.NoteItems;

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

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<Note> ITEMS = new ArrayList<Note>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    public static void addItem(Note item) {
        ITEMS.add(item);
    }

    /**
     * Reads the List ITEMS from Filesystem
     */
    private static void readlist(Context act) {
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
     *
     */
    private static void writelist(Context act) {
        String ser = SerializeObject.objectToString((Serializable) ITEMS);
        if(ser != null && !ser.equalsIgnoreCase("")) {
            SerializeObject.WriteSettings(act, ser, "notes.dat");
        }
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class Note implements Serializable{
        public final String content;

        public Note(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}