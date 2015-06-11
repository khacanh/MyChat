package com.example.user.mychat;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.mychat.firebase.FireBaseManager;
import com.example.user.mychat.model.Message;
import com.example.user.mychat.network.NetworkStateMonitor;
import com.example.user.mychat.database.DBHelper;
import com.example.user.mychat.utils.ResourceUtil;


public class MainActivity extends BaseActivity implements View.OnClickListener, FireBaseManager.OnMessageUpdatedListener{

    private EditText mEditText;
    private ListView mListView;
    private Button mSendButton;

    private ChatAdapter mAdapter;
    private FireBaseManager mManager;
    private Cursor mCursor;

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
        mCursor = mManager.getAllMessage();
        if(mCursor.moveToFirst()) {
            createAdapter();
        }
    }

    private void createAdapter(){
        mAdapter = new ChatAdapter(this, R.layout.message_item_left, mManager.getAllMessage(), DBHelper.FROM, DBHelper.TO, mManager.getAuthorName());
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEnableButton(NetworkStateMonitor.getInstance(this).isConnected());
        scrollToEnd();
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
        mCursor.close();
        mCursor = mManager.getAllMessage();
        if(mAdapter != null) {
            mAdapter.swapCursor(mCursor);
        }else{
            createAdapter();
        }
        scrollToEnd();
    }

    private void postAComment(){
        String text = mEditText.getText().toString();
        if(text.length() > 0){
            mEditText.setText("");
            mManager.insertText(text);
        }else{
            Toast.makeText(MainActivity.this, ResourceUtil.getString(this, R.string.alert_input_add_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void scrollToEnd(){
        if(mListView != null){
            mListView.setStackFromBottom(true);
            mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        }
    }

    private void setEnableButton(boolean isConnected){
        if(mSendButton != null) {
            if (isConnected) {
                mSendButton.setEnabled(true);
            }else{
                mSendButton.setEnabled(false);
            }
        }
    }

    @Override
    protected void networkSateChanged(boolean isConnected) {
        setEnableButton(isConnected);
    }
}
