package com.abelium.inatrace.tools;

import jakarta.xml.bind.DatatypeConverter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionTools {
    
    private static final String UTF8 = "UTF-8";
    private static final String SHA256 = "SHA-256";
    private static final String SHA384 = "SHA-384";
    private static final String SHA512 = "SHA-512";
    private static final String SHA512_256 = "SHA-512/256";
    private static final String SHA3_256 = "SHA3-256";
    private static final String SHA3_384 = "SHA3-384";
    private static final String SHA3_512 = "SHA3-512";
    private static final String HMAC_SHA512 = "HmacSHA512";
    private static final String HMAC_SHA256 = "HmacSHA256";

    public static byte[] sha256(CharSequence message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA256);
        return md.digest(stringToBytes(message));
    }
    
    public static String sha384(CharSequence message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA384);
        return base64Encode(md.digest(stringToBytes(message)));
    }
    
    public static String sha512(CharSequence message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA512);
        return base64Encode(md.digest(stringToBytes(message)));
    }
    
    public static String sha512_256(CharSequence message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA512_256);
        return base64Encode(md.digest(stringToBytes(message)));
    }
    
    public static String sha3_256(CharSequence message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA3_256);
        return base64Encode(md.digest(stringToBytes(message)));
    }
    
    public static String sha3_384(CharSequence message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA3_384);
        return base64Encode(md.digest(stringToBytes(message)));
    }
    
    public static String sha3_512(CharSequence message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA3_512);
        return base64Encode(md.digest(stringToBytes(message)));
    }

    public static byte[] stringToBytes(CharSequence input) {
        if (input == null) {
            throw new IllegalArgumentException("Input can't be null.");
        }
        return input.toString().getBytes(Charset.forName(UTF8));
    }

    public static byte[] base64Decode(String input) {
        return Base64.getDecoder().decode(input);
    }
    
    public static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] hmac(String hmacType, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(hmacType);
        mac.init(new SecretKeySpec(key, hmacType));
        return mac.doFinal(message);        
    }

    public static byte[] hmacSha512(byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        return hmac(HMAC_SHA512, key, message);
    }
    
    public static byte[] hmacSha256(byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        return hmac(HMAC_SHA256, key, message);
    }

    public static String hexEncode(byte[] hmac) {
        return DatatypeConverter.printHexBinary(hmac);
    }

    public static byte[] concatArrays(byte[] path, byte[] sha256) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( path );
        outputStream.write( sha256 );
        return outputStream.toByteArray( );
    }
}
