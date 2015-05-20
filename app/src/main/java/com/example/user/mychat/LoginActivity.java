package com.example.user.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.mychat.utils.LoginAnimationListener;
import com.example.user.mychat.utils.ResourceUtil;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher{

    private SpringConfig CONVERGING = SpringConfig.fromOrigamiTensionAndFriction(20, 4);
    private final BaseSpringSystem mSpringSystem = SpringSystem.create();
    private Spring mSpring;
    private LoginAnimationListener mListener;
    private Button mJoinButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        View logo = findViewById(R.id.logo);
        View loginLayout = findViewById(R.id.login_layout);
        mJoinButton = (Button)findViewById(R.id.input_join_button);
        mJoinButton.setOnClickListener(this);
        mEditText = (EditText)findViewById(R.id.input_username);
        mEditText.addTextChangedListener(this);

        mListener = new LoginAnimationListener(logo, loginLayout);
        mSpring = mSpringSystem.createSpring();
        mSpring.setCurrentValue(0);
        mSpring.setSpringConfig(CONVERGING);
        mSpring.addListener(mListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mSpring.setEndValue(1);
            }
        });
    }

    private void setEnableButton(boolean isConnected){
        if(mJoinButton != null) {
            if (isConnected) {
                mJoinButton.setEnabled(true);
            }else{
                mJoinButton.setEnabled(false);
            }
        }
    }

    @Override
    protected void networkSateChanged(boolean isConnected) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.input_join_button:
                if(mEditText.getText().length() > 3){
                    ChatApplication.getInstance().setUsername(mEditText.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(this, ResourceUtil.getString(this, R.string.alert_input_4_characters), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if(charSequence.length() > 0){
            setEnableButton(true);
        }else{
            setEnableButton(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
