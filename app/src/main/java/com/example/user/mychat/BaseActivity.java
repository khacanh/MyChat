package com.example.user.mychat;


import android.app.Activity;
import android.widget.Toast;

import com.example.user.mychat.network.NetworkStateMonitor;

public abstract class BaseActivity extends Activity implements NetworkStateMonitor.OnNetworkStateChangedListener{

    protected abstract void networkSateChanged(boolean isConnected);

    @Override
    protected void onStart() {
        super.onStart();
        NetworkStateMonitor.getInstance(this).registerListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetworkStateMonitor.getInstance(this).unregisterListener(this);
    }

    @Override
    public void onNetworkStateChanged(NetworkStateMonitor networkStateMonitor) {
        if(networkStateMonitor.isConnected()){
            networkSateChanged(true);
        }else{
            networkSateChanged(false);
        }
        Toast.makeText(this, "Network Changed!", Toast.LENGTH_LONG).show();
    }
}
