package com.booking.services.html;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import org.springframework.stereotype.Service;

@Service
public class HtmlReplacerService {
    public String replaceItems(String content, ContentReplacerItemCollection replacerItemList) {
        String result = content;
        for (ContentReplacerItem contentReplacerItem: replacerItemList.getReplacingItems()) {
            String replacement = contentReplacerItem.replaceValue() == null ? "" : contentReplacerItem.replaceValue();
            result = result.replace(contentReplacerItem.replaceString(), replacement);
        }
        return result;
    }
}
