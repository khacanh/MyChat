package com.example.user.mychat;


import android.app.Application;
import android.content.Context;
import android.util.AndroidRuntimeException;

public class ChatApplication extends Application{

    private static final String ERROR = "ChatApplication was not initialized.";
    private static ChatApplication sInstance;
    private String mUsername;

    public static ChatApplication getInstance(){
        if(sInstance == null){
           throw new AndroidRuntimeException(ERROR);
        }
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }
}
