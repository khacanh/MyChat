package com.example.user.mychat.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.mychat.model.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyChat.db";
    public static final String CHAT_TABLE_NAME = "chat";
    public static final String CHAT_COLUMN_ID = "id";
    public static final String CHAT_COLUMN_AUTHOR = "author";
    public static final String CHAT_COLUMN_MESSAGE = "message";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table "+ CHAT_TABLE_NAME +
                        "("+ CHAT_COLUMN_ID +" string primary key, "+ CHAT_COLUMN_AUTHOR +" text,"+ CHAT_COLUMN_MESSAGE +" text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS chat");
        onCreate(db);
    }

    public boolean insertMessage(String id, String author, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CHAT_COLUMN_ID, id);
        contentValues.put(CHAT_COLUMN_AUTHOR, author);
        contentValues.put(CHAT_COLUMN_MESSAGE, message);

        db.insert(CHAT_TABLE_NAME, null, contentValues);
        return true;
    }

    public Message getData(String id) {
        Message message = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ CHAT_TABLE_NAME +" where "+ CHAT_COLUMN_ID +"='" + id + "'", null);
        if (res != null && res.moveToFirst()) {
            String author = res.getString(1);
            String text = res.getString(2);
            message = new Message(id, author, text);
            res.close();
        }
        return message;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CHAT_TABLE_NAME);
        return numRows;
    }

    public boolean updateMessage(String id, String author, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHAT_COLUMN_AUTHOR, author);
        contentValues.put(CHAT_COLUMN_MESSAGE, message);
        db.update(CHAT_TABLE_NAME, contentValues, ""+ CHAT_COLUMN_ID +" = ? ", new String[]{id});
        return true;
    }

    public Integer deleteMessage(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CHAT_TABLE_NAME,
                "id = ? ",
                new String[]{id});
    }

    public ArrayList<Message> getAllChat() {
        ArrayList list = new ArrayList();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ CHAT_TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            String id = res.getString(res.getColumnIndex(CHAT_COLUMN_ID));
            String author = res.getString(res.getColumnIndex(CHAT_COLUMN_AUTHOR));
            String text = res.getString(res.getColumnIndex(CHAT_COLUMN_MESSAGE));
            Message message = new Message(id, author, text);
            list.add(message);
            res.moveToNext();
        }
        return list;
    }
}