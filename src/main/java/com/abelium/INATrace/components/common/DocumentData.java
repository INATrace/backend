package com.abelium.INATrace.components.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;

import com.abelium.INATrace.db.entities.Document;


public class DocumentData 
{
    public byte[] file;
    public Document data;
    
    public DocumentData(byte[] file, Document data) {
        this.file = file;
        this.data = data;
    }
    
    public String getMD5Hash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new String(Hex.encode(md.digest(file)));
    }
}
