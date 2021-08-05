package com.abelium.inatrace.tools.documents;

public class DefaultBookmarkValueProvider implements BookmarkValueProvider
{
    @Override
    public BookmarkValue bookmarkValue(Object o, String text) {
        if (o == null) {
            return null;
        } else {
            if (o instanceof ProvidesBookmarkValue) {
                return ((ProvidesBookmarkValue) o).bookmarkValue();
            } else {
                return BookmarkValue.fromString(o.toString());
            }
        }
    }
}
