package com.example.user.mychat;


import android.app.Activity;
import android.widget.Toast;

import com.example.user.mychat.network.NetworkStateMonitor;
import com.example.user.mychat.utils.ResourceUtil;

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
        Toast.makeText(this, ResourceUtil.getString(this, R.string.alert_network_changed), Toast.LENGTH_LONG).show();
    }
}
