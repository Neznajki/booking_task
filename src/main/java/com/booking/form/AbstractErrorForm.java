package com.booking.form;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.ErrorContainerForm;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractErrorForm implements ErrorContainerForm {
    protected List<ContentReplacerItem> additionalItems = new ArrayList<>();

    @Override
    public void addAdditionalContentReplacerItem(ContentReplacerItem contentReplacerItem) {
        this.additionalItems.add(contentReplacerItem);
    }

    protected void addAdditionalItems(ContentReplacerItemCollection replacerItems) {
        for (ContentReplacerItem replacerItem: additionalItems) {
            replacerItems.addContentReplacerItem(replacerItem);
        }
    }
}
