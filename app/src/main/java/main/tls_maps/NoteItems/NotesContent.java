package main.tls_maps.NoteItems;

import android.content.Context;

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

    public static List<Note> ITEMS = new ArrayList<Note>();

    public static void init(Context context){
        readlist(context);
    }

    public static void addItem(String content, Context context) {
        int id = ITEMS.size();
        ITEMS.add(new Note(content, id));
        writelist(context);
    }

    public static void removeItem(String nr, Context context) {
        int id = Integer.parseInt(nr);
        System.out.println(ITEMS.toString());
        ITEMS.remove(id);
        System.out.println(ITEMS.toString());
        System.out.println(ITEMS.size());
        for(int i = 0; i < ITEMS.size(); i ++) {
            ITEMS.get(i).id = ""+i;
        }
        writelist(context);
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
        public String id;

        public Note(String content, int id) {
            this.content = content;
            this.id = ""+id;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}