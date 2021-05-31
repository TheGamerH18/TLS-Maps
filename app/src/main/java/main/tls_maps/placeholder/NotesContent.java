package main.tls_maps.placeholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, Note> ITEM_MAP = new HashMap<String, Note>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(Note item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Note createPlaceholderItem(int position) {
        return new Note(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class Note {
        public final String id;
        public final String content;
        public final String details;

        public Note(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}