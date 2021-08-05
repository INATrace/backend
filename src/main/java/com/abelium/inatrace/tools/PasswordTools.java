package com.abelium.inatrace.tools;

import org.apache.commons.lang3.StringUtils;

public class PasswordTools {
	
    public static boolean isPasswordComplex(String password) {
        int length = StringUtils.length(password);
        return length >= 8 && length <= 50;
//        if (StringUtils.isBlank(password))
//            return false;
//        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9])(?=\\S+$).{8,50}";
//        return password.matches(pattern);
    }

}
