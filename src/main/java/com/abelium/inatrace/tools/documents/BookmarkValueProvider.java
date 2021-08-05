package com.abelium.inatrace.tools.documents;

@FunctionalInterface
public interface BookmarkValueProvider {
    BookmarkValue bookmarkValue(Object o, String text);
}
