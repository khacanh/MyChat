package com.example.user.mychat.model;

import android.database.Cursor;

import com.example.user.mychat.database.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private static final String JSON_MESSAGE_TAG = "message";
    private static final String N_A = "N/A";

    private String mAuthor;
    private String mMessage;
    private String mId;

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

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public Message(){
        mId = N_A;
        mAuthor = N_A;
        mMessage = N_A;
    }

    public Message(String id, String author, String message){
        mId = id;
        mAuthor = author;
        mMessage = message;
    }

    public Message update(JSONObject object){
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

    public Message update(String text){
        mAuthor = N_A;
        mMessage = text;
        return this;
    }

    public Message update(Cursor cursor){
        if(cursor.getCount() > 0){
            if(cursor.getPosition() == -1){
                cursor.moveToFirst();
            }
            int idIndex = cursor.getColumnIndexOrThrow(DBHelper.CHAT_COLUMN_ID);
            int authorIndex = cursor.getColumnIndexOrThrow(DBHelper.CHAT_COLUMN_AUTHOR);
            int messageIndex = cursor.getColumnIndexOrThrow(DBHelper.CHAT_COLUMN_MESSAGE);
            mId = cursor.getString(idIndex);
            mAuthor = cursor.getString(authorIndex);
            mMessage = cursor.getString(messageIndex);
            return this;
        }
        return null;
    }
}
