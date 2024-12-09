package com.booking.contract;

import com.booking.collection.ContentReplacerItemCollection;

public interface HtmlPageInterface {
    String getHtmlFileLocation();
    String getPageTitle();
    ContentReplacerItemCollection getReplacerItems();
}
