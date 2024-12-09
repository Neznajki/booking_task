package com.booking.container;

public record ContentReplacerItem(String replaceString, String replaceValue) {

    @Override
    public String replaceString() {
        return String.format("{{%s}}", replaceString);
    }
}
