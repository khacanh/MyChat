package com.example.user.mychat;

import android.content.Context;
import android.os.Build;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class FireBaseManager implements ChildEventListener{

    private static final String FIREBASE_URL = "https://mysquar-test.firebaseio.com/room1";
    private static final String AUTHOR_TAG = "author";
    private static final String MESSAGE_TAG = "message";
    private static final String USER_PREFIX = "User";

    private Firebase mFireBaseRef;
    private OnMessageUpdatedListener mListener;
    private String mAuthorName;

    public FireBaseManager(Context context){
        Firebase.setAndroidContext(context);
        mFireBaseRef = new Firebase(FIREBASE_URL);
        mFireBaseRef.addChildEventListener(this);
        mAuthorName = USER_PREFIX+ Math.abs(Build.MODEL.hashCode());
    }

    public void setOnMessageUpdatedListener(OnMessageUpdatedListener listener){
        mListener = listener;
    }

    public void insertText(String value){
        Map<String, String> post = new HashMap<String, String>();
        post.put(AUTHOR_TAG, mAuthorName);
        post.put(MESSAGE_TAG, value);
        mFireBaseRef.push().setValue(post);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if(dataSnapshot.getValue() instanceof Map) {
            Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
            Message message = new Message((String)newPost.get(AUTHOR_TAG), (String)newPost.get(MESSAGE_TAG));
            if (mListener != null) {
                mListener.onMessageChange(message);
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    public interface OnMessageUpdatedListener{
        public void onMessageChange(Message message);
    }
}
