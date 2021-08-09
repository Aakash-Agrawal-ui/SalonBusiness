package com.salononline.salonbusiness.Repositry;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static Prefs sharedPreferenceManager;
    private static SharedPreferences sharedPreferences;

    private Prefs() {
    }

    public static Prefs getInstance(Context context) {
        if (sharedPreferenceManager == null) {
            sharedPreferenceManager = new Prefs();

            sharedPreferences =  context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
        }

        return sharedPreferenceManager;
    }

    public void putInt(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key,"");
    }

}
