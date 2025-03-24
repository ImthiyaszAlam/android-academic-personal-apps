package com.example.phoneapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPreference {

    SharedPreferences sp;

    public SharedPreference(Context con)
    {
        sp = con.getSharedPreferences("CallApp", Context.MODE_PRIVATE);
    }


    public void setValueString(String paramName, String paramValue)
    {
        Editor et = sp.edit();
        et.putString(paramName, paramValue);
        et.commit();
    }

    public String getValueString(String paramName)
    {
        return sp.getString(paramName, "");
    }

    public void setValueInt(String paramName, int paramValue) {
        Editor et = sp.edit();
        et.putInt(paramName, paramValue);
        et.commit();
    }

    public int getValueInt(String paramName) {
        return sp.getInt(paramName, 0);
    }

    public void setValueBool(String paramName, boolean paramValue) {
        Editor et = sp.edit();
        et.putBoolean(paramName, paramValue);
        et.commit();
    }

    public boolean getValueBoolean(String paramName)
    {
        return sp.getBoolean(paramName, false);
    }


    public void clearAll()
    {
        sp.edit().clear().commit();
    }




}
