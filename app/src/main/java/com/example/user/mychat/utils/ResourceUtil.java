package com.example.user.mychat.utils;


import android.content.Context;

public class ResourceUtil {

    public static String getString(Context context, int resourceId) {
        return context.getResources().getString(resourceId);
    }
}
