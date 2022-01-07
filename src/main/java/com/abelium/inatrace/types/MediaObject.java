package com.abelium.inatrace.types;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class MediaObject {
    public final MediaType mediaType;
    public final byte[] content;

    public MediaObject(MediaType mediaType, byte[] content) {
        this.mediaType = mediaType;
        this.content = content;
    }
    
    public ResponseEntity<byte[]> toResponseEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
