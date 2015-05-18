package com.example.user.mychat.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetworkStateMonitorReceiver extends BroadcastReceiver {
    public NetworkStateMonitorReceiver() {

    }
	
	@Override
	public void onReceive(Context context, Intent intent) {
//		if (!isInitialStickyBroadcast() && !intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false)) {
			// while failing over, two events occur:
			// 1) the initial failover event.
			// 2) the resulting connection established or lost event.

			// TODO: Is the following assumption correct?
			// we only care about the resulting connection,
			// so ignore the failover event.
			NetworkStateMonitor networkState = NetworkStateMonitor.getInstance(context);
            networkState.setNetworkInfo(((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo());
			networkState.handleConnectivityChange();
//		}
	}
}