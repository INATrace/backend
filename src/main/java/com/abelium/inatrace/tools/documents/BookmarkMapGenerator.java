package com.abelium.inatrace.tools.documents;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.ReflectionUtils;

public class BookmarkMapGenerator 
{
    Map<String, BookmarkValue> map = new HashMap<>();
    Map<String, BookmarkHtmlValue> htmlMap = new HashMap<>();
    
    public BookmarkMapGenerator addFromObject(XWPFDocument doc, Object object) {
        if (object != null) {
            Class<?> clazz = object.getClass();
            
            int id = 1;
            for (Field f : getAllFields(clazz)) {
                ReflectionUtils.makeAccessible(f);
                
                List<Bookmark> bookmarks = new ArrayList<>();
                Bookmarks fieldBookmarks = f.getAnnotation(Bookmarks.class);
                if (fieldBookmarks != null) {
                    bookmarks.addAll(Arrays.asList(fieldBookmarks.value()));
                }
                Bookmark fieldBookmark = f.getAnnotation(Bookmark.class);
                if (fieldBookmark != null) {
                    bookmarks.add(fieldBookmark);
                }
                
                for (Bookmark bookmark : bookmarks) {
                    if (bookmark != null) {
                    	if(bookmark.isHtml()) {
                    		try {
                    			BookmarkHtmlValue htmlVal = BookmarkHtmlValue.createHtmlDocument(doc, id++, (String) f.get(object));
                    			htmlMap.put(bookmark.value(), htmlVal);
                    		} catch (Exception e) {
                    			throw new RuntimeException("Error adding html bookmarks", e);
                    		}
                    	} else {
                    		try {
                    			Object fValue = f.get(object);
                    			BookmarkValueProvider valueProvider = bookmark.valueProvider().getDeclaredConstructor().newInstance();
                    			map.put(bookmark.value(), valueProvider.bookmarkValue(fValue, bookmark.text())); 
                    		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException |
                    				NoSuchMethodException | SecurityException e) {
                    			throw new RuntimeException("Error adding bookmarks", e);
                    		}
                    	}
                    }
                }
            }
        }
        return this;
    }
    
    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        getAllFields(fields, clazz);
        return fields;
    }
    
    private void getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
    }
    
    public BookmarkMapGenerator add(String bookmark, String value) {
        map.put(bookmark, BookmarkValue.fromString(value));        
        return this;
    }
    
    public Map<String, BookmarkValue> get() {
        return map;
    }

	public Map<String, BookmarkHtmlValue> getHtml() {
		return htmlMap;
	}
}
