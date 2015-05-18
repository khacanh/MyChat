package com.example.user.mychat;

import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class FireBaseManager implements ChildEventListener{

    private static final String FIREBASE_URL = "https://mysquar-test.firebaseio.com/room1";
    private Firebase mFireBaseRef;
    private OnMessageUpdatedListener mListener;

    public FireBaseManager(Context context){
        Firebase.setAndroidContext(context);
        mFireBaseRef = new Firebase(FIREBASE_URL);
        mFireBaseRef.addChildEventListener(this);
    }

    public void setOnMessageUpdatedListener(OnMessageUpdatedListener listener){
        mListener = listener;
    }

    public void insertText(String value){
        mFireBaseRef.push().setValue(value);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if(dataSnapshot.getValue() instanceof String) {
            Message message = new Message().initFromText((String) dataSnapshot.getValue());
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
