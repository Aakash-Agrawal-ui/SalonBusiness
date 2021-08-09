package com.salononline.salonbusiness.Data;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class TokenDecode {
    public static String decodeJWT(String EncodeString) throws Exception{
        String[] splitStr=EncodeString.split("\\.");
        return getJson(splitStr[1]);
    }

    public static String getJson(String EncodeString) throws UnsupportedEncodingException{
        byte[] decodeByte= Base64.decode(EncodeString,Base64.URL_SAFE);
        return new String(decodeByte, StandardCharsets.UTF_8);
    }

}
