package com.abelium.INATrace.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;

import com.abelium.INATrace.types.MediaObject;

public class ResourceTools
{
    public static boolean exists(String path) {
        String respath = StringUtils.stripStart(path, "/");     // resource paths must not start with "/" 
        return Thread.currentThread().getContextClassLoader().getResource(respath) != null;
    }
    
    public static String loadResourceText(String name) throws FileNotFoundException, IOException {
        try (InputStream stream = getResourceStream(name)) {
            return new String(stream.readAllBytes(), "UTF-8");
        }
    }
    
    public static byte[] loadResourceBytes(String name) throws FileNotFoundException, IOException {
        try (InputStream stream = getResourceStream(name)) {
            return stream.readAllBytes();
        }
    }
    
    public static MediaObject loadResourceObject(String name) throws FileNotFoundException, IOException {
        byte[] data = loadResourceBytes(name);
        String type = mediaTypeDetector().detect(data);
        return new MediaObject(MediaType.valueOf(type), data);
    }

    public static InputStream getResourceStream(String path) throws FileNotFoundException {
        String respath = StringUtils.stripStart(path, "/");     // resource paths must not start with "/" 
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(respath);
        if (stream == null) {
            throw new FileNotFoundException("Missing resource " + path);
        }
        return stream;
    }
    
    private static ThreadLocal<Tika> tikaInstance = ThreadLocal.withInitial(() -> new Tika());
    
    public static Tika mediaTypeDetector() {
        return tikaInstance.get();
    }
    
    public static void cleanupMediaDetector() {
        tikaInstance.remove();
    }
}
