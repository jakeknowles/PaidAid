package uw.tacoma.edu.paidaid.model;

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
public class Requests {

    /**
     * An array of sample (request) items.
     */
    public static final List<RequestItem> ITEMS = new ArrayList<RequestItem>();

    /**
     * A map of sample (requests) items, by ID.
     */
    public static final Map<String, RequestItem> ITEM_MAP = new HashMap<String, RequestItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createRequestItem(i));
        }
    }

    private static void addItem(RequestItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static RequestItem createRequestItem(int position) {
        return new RequestItem(String.valueOf(position), "Item " + position, makeDetails(position));
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
     * A request item representing a piece of content.
     */
    public static class RequestItem {
        public final String id;
        public final String content;
        public final String details;

        public RequestItem(String id, String content, String details) {
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
