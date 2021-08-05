package com.abelium.inatrace.tools.documents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Bookmarks.class)
public @interface Bookmark 
{
    String value();
    
    String text() default "";
    
    boolean isHtml() default false;
    
    Class<? extends BookmarkValueProvider> valueProvider() default DefaultBookmarkValueProvider.class;
}
