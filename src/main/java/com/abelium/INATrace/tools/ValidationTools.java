package com.abelium.INATrace.tools;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import com.google.common.net.InternetDomainName;

public class ValidationTools {
    /**
     * Validate email address only, allowing e.g. "user.name@gmail.com" BUT NOT "User Name <user.name@gmail.com>".
     * @param email string to be validated
     * @return true if email address is valid
     */
    public static boolean validateEmailAddress(String email, boolean validateDomain) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        try {
            EmailValidator ev = new EmailValidator();
            InternetAddress address = new InternetAddress(email, true);
            int index = address.getAddress().indexOf('@');
            boolean validFormat = index > 0 && index < address.getAddress().length()-1 && ev.isValid(email, null) && !StringUtils.contains(email, '<');
            return validFormat && (!validateDomain || validateDomain(address.getAddress().substring(index + 1)));
        } catch (AddressException | NullPointerException exc) {
            return false;
        }
    }

    public static boolean validateEmailAddress(String email) {
        return validateEmailAddress(email, true);
    }
    
    /**
     * Validate Internet domain name
     * @param hostname
     * @return true if domain exists
     * @throws NamingException
     */
    public static boolean validateDomain(String hostname) {
        if (!InternetDomainName.isValid(hostname))
            return false;
            
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(hostname, new String[] {"MX"});
            Attribute attr = attrs.get("MX");
            return attr != null;
        } catch (NamingException exc) {
            return false;
        }
    }
    
    /**
     * Validate format of currency. If c is null, it returns true, since
     * some other validation (@NotNull or required=true) should check
     * for nullity.
     * @param c
     * @return true if c is in the right format
     */
    public static boolean validateCurrency(String c) {
        Pattern p = Pattern.compile("^[a-z]{3}$", Pattern.CASE_INSENSITIVE);
        return c == null || p.matcher(c).matches();
    }
    
    /**
     * Validate format of currency pair. If pair is null, it returns true, since
     * some other validation (@NotNull or required=true) should check
     * for nullity.
     * @param pair
     * @return true if pair is in the right format
     */
    public static boolean validateCurrencyPair(String pair) {
        Pattern p = Pattern.compile("^[a-z]{6}$", Pattern.CASE_INSENSITIVE);
        return pair == null || p.matcher(pair).matches();        
    }
    
    /**
     * Validate that string length is between {@code min} and {@code max} (inclusive).
     */
    public static boolean validateStringLength(String str, int min, int max) {
        if (str == null) return true;
        int length = str.length();
        return length >= min && str.length() <= max;
    }
    
    /**
     * Validate that lengths of all strings in the collection are between {@code min} and {@code max} (inclusive).
     */
    public static boolean validateStringLength(Collection<String> collection, int min, int max) {
        if (collection == null) return true;
        for (String str : collection) {
            if (!validateStringLength(str, min, max)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate that value in miliseconds is rounded to 5 minutes
     */
    public static boolean validateFiveMinutes(Instant value) {
        if (value == null) return false;
        return value.toEpochMilli() % 300000 == 0;
    }
    
    /**
     * Validate that value in nanoseconds is rounded to 5 minutes
     */
    public static boolean validateFiveMinutes(LocalTime value) {
        if (value == null) return false;
        return value.toNanoOfDay() % 300000000000l == 0;
    }
    
}
