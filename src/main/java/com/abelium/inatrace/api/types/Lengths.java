package com.abelium.inatrace.api.types;

public class Lengths
{
    /**
     * Default database maximum string length.
     */
    public static final int DEFAULT = 255;
    
    /**
     * Maximum string length for string UID-s (may be UUID, UUID with prefix, or something else).
     */
    public static final int UID = 64;
    
    /**
     * Maximum string length for authentication token.
     */
    public static final int AUTH_TOKEN = UID;

    /**
     * Default maximum string length for enums.
     */
    public static final int ENUM = 40;
    
    /**
     * Default maximum string length for url path.
     */
    public static final int URL_PATH = DEFAULT;
    
    /**
     * Default maximum string length for device alias username.
     */
    public static final int USERNAME = 100;
    public static final int PASSWORD = 100;

    /**
     * Country code length
     */
	public static final int COUNTRY_CODE = 3;

    /**
     * Country name length
     */
	public static final int COUNTRY_NAME = 100;

	/**
	 * Content type length
	 */
    public static final int CONTENT_TYPE = 128;

	/**
	 * Address, city, state, zip
	 */
	public static final int ADDRESS = DEFAULT;
    public static final int CITY = DEFAULT;	
    public static final int STATE = DEFAULT;	
    public static final int ZIPCODE = 50;
    public static final int OTHER_ADDRESS = 1000;

    /**
     * Cell, sector, village
     */
    public static final int CELL = DEFAULT;
    public static final int SECTOR = DEFAULT;
    public static final int VILLAGE = DEFAULT;

    // users
    public static final int NAME = DEFAULT;
    public static final int SURNAME = DEFAULT;
    public static final int ROLE_NAME = 40;
    
    // companies
    public static final int VAT_ID = 40;
    
    // contacts
    public static final int EMAIL = DEFAULT;
    public static final int PHONE_NUMBER = 20;
    
    // global settings
    public static final int SERVICE_NAME = 32;
    public static final int GLOBAL_SETTING_NAME = 128;
    
    // other
    public static final int COMMENT = DEFAULT;  // generic comment, should be abbreviated before putting to db to prevent problems (or use TEXT field without limitations)
    public static final int S3_PATH = 255;
    public static final int IP_4 = 16;
    public static final int IP_6 = 45;
    
    // Request log key
    public static final int REQUEST_LOG_KEY = 64; 

    public static final int ANALYTICS_KEY = 64;

    // B2C settings
    public static final int HEX_COLOR = 7;

}
