package main.tls_maps.NoteItems;

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

    private static Note createPlaceholderItem(int position) {
        return new Note("Note: " + position);
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class Note {
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