package com.example.user.mychat.network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class NetworkStateMonitor {

    private volatile static NetworkStateMonitor INSTANCE;
    private final String TAG = "NetworkStateMonitor";
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private final List<WeakReference<OnNetworkStateChangedListener>> mListeners = new ArrayList<WeakReference<OnNetworkStateChangedListener>>();
    private NetworkInfo mNetworkInfo;

    private NetworkStateMonitor(Context context) {
        mContext = context;
        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkStateMonitor getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NetworkStateMonitor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetworkStateMonitor(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Indicates whether network connectivity is possible. A network is
     * unavailable when a persistent or semi-persistent condition prevents the
     * possibility of connecting to that network. Examples include: the device
     * is out of the coverage area for any network of this type, device is on a
     * network other than the home network (i.e., roaming), and data roaming has
     * been disabled, device's radio is turned off, e.g., because airplane mode
     * is enabled.
     *
     * @return <code>true</code> if the network is available, <code>false</code>
     *         otherwise
     */
    public boolean isAvailable() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.isAvailable() : false;
    }

    /**
     * Indicates whether network connectivity exists and it is possible to
     * establish connections and pass data.
     *
     * @return <code>true</code> if network connectivity exists,
     *         <code>false</code> otherwise.
     */
    public boolean isConnected() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.isConnected() : false;
    }

    public boolean isWifi() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getType() == ConnectivityManager.TYPE_WIFI : false;
    }

    public boolean isMobile() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getType() == ConnectivityManager.TYPE_MOBILE : false;
    }

    public String getTypeName() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();

        return networkInfo != null ? networkInfo.getTypeName() : "";
    }

    @Override
    public String toString() {
        return "Network state: available:" + isAvailable() + ";connected:" + isConnected() + ";isWifi:" + isWifi()
                + ";type:" + getTypeName();
    }

    public void registerListener(OnNetworkStateChangedListener listener) {
        boolean listenerInserted = false;
        WeakReference<OnNetworkStateChangedListener> listenerRef;
        for (int i = mListeners.size() - 1; i >= 0; i--) {
            listenerRef = mListeners.get(i);
            if (listenerRef.get() == null) {
                if (listenerInserted) {
                    mListeners.remove(i--);
                } else {
                    listenerRef = new WeakReference<OnNetworkStateChangedListener>(listener);
                    mListeners.set(i, listenerRef);
                    listenerInserted = true;
                }
            }
        }
        if (!listenerInserted) {
            mListeners.add(new WeakReference<OnNetworkStateChangedListener>(listener));
        }
    }

    public void unregisterListener(OnNetworkStateChangedListener listener) {
        OnNetworkStateChangedListener listenerRef;
        for (int i = mListeners.size() - 1; i >= 0; i--) {
            listenerRef = mListeners.get(i).get();
            if (listenerRef == null || listenerRef == listener) {
                mListeners.remove(i--);
            }
        }
    }

    protected void handleConnectivityChange() {
        OnNetworkStateChangedListener listenerRef;
        for (int i = mListeners.size() - 1; i >= 0; i--) {
            listenerRef = mListeners.get(i).get();
            if (listenerRef != null) {
                listenerRef.onNetworkStateChanged(this);
            } else {
                mListeners.remove(i--);
            }
        }
    }

    public NetworkInfo getNetworkInfo(){
        return mNetworkInfo;
    }

    public void setNetworkInfo(NetworkInfo networkInfo){
        mNetworkInfo = networkInfo;
    }
    /*
	 * When wifi changes need to iterate all secure session objs and
	 * SecureSession.doProximity() in all bjs and if all failed then raise user
	 * error.
	 */

    public static interface OnNetworkStateChangedListener {
        public void onNetworkStateChanged(NetworkStateMonitor networkStateMonitor);
    }
}
