package com.booking.collection;

import com.booking.container.ContentReplacerItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContentReplacerItemCollection {
    private final Map<String, ContentReplacerItem> contentReplacerItems = new LinkedHashMap<>();

    public ContentReplacerItemCollection addContentReplacerItem(ContentReplacerItem item) {
        if (contentReplacerItems.containsKey(item.replaceString())) {
            throw new RuntimeException(String.format("string %s already exists", item.replaceString()));
        }

        contentReplacerItems.put(item.replaceString(), item);
        return this;
    }

    public List<ContentReplacerItem> getReplacingItems() {
        return new ArrayList<>(contentReplacerItems.values());
    }
}
