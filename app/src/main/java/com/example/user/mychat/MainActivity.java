package com.example.user.mychat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.mychat.firebase.FireBaseManager;
import com.example.user.mychat.model.Message;
import com.example.user.mychat.network.NetworkStateMonitor;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener, FireBaseManager.OnMessageUpdatedListener{

    private EditText mEditText;
    private ListView mListView;
    private Button mSendButton;

    private ChatAdapter mAdapter;
    private List<Message> mListMess;
    private FireBaseManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init Firebase
        mManager = new FireBaseManager(this);
        mManager.setOnMessageUpdatedListener(this);

        //init views
        mEditText = (EditText) findViewById(R.id.messages);
        mListView = (ListView) findViewById(R.id.chatting_list);
        mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(this);

        //setup list view
        mListMess = mManager.getAllMessage();
        if(mListMess == null){
            mListMess = new ArrayList<>();
        }
        mAdapter = new ChatAdapter(this, mListMess);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(NetworkStateMonitor.getInstance(this).isConnected()){
            mSendButton.setEnabled(true);
        }else{
            mSendButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_send:
                postAComment();
                break;
        }
    }

    @Override
    public void onMessageChange(Message message) {
        mListMess.add(message);
        mAdapter.notifyDataSetChanged();
    }

    private void postAComment(){
        String text = mEditText.getText().toString();
        if(text.length() > 0){
            mEditText.setText("");
            mManager.insertText(text);
        }else{
            Toast.makeText(MainActivity.this, "Please insert text to send", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void networkSateChanged(boolean isConnected) {
        if(mSendButton != null) {
            if (isConnected) {
                mSendButton.setEnabled(true);
            }else{
                mSendButton.setEnabled(false);
            }
        }
    }
}
