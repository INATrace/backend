package com.abelium.INATrace.tools.documents;

@FunctionalInterface
public interface BookmarkValueProvider {
    BookmarkValue bookmarkValue(Object o, String text);
}
