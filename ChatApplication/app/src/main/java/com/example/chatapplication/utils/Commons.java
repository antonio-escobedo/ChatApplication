package com.example.chatapplication.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Commons {
    // I continue whit develop
    public static boolean isBlankOrEmpty(String string){
        if(isEmptyString(string) && isBlankString(string)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isEmptyString(String string) {
        return string == null || string.length() == 0;
    }

    public static String md5Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
}
