package com.example.user.mychat;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private static final String JSON_MESSAGE_TAG = "message";
    private static final String N_A = "N/A";

    private String mAuthor;
    private String mMessage;

    public String getAuthor() {
        return mAuthor;
    }

    public void setUser(String mUser) {
        this.mAuthor = mUser;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public Message initFromJSONObject(JSONObject object){
        mAuthor = N_A;
        mMessage = N_A;
        try {
            String message = object.getString(JSON_MESSAGE_TAG);
            if(message != null){
                mMessage = message;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Message(String author, String message){
        mAuthor = author;
        mMessage = message;
    }

    public Message initFromText(String text){
        mAuthor = N_A;
        mMessage = text;
        return this;
    }
}
