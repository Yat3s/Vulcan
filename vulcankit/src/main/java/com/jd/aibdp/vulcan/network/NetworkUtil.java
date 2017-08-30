package com.jd.aibdp.vulcan.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkUtil {
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_UNKNOWN = 3;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0x10;
    public static final int NETWORK_STATUS_CONNECTED_WIFI = 0x11;
    public static final int NETWORK_STATUS_CONNECTED_MOBILE = 0x12;

    private static int getConnectivityType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_UNKNOWN;
    }

    public static int getConnectivityStatus(Context context) {
        int conn = NetworkUtil.getConnectivityType(context);
        int status = NETWORK_STATUS_NOT_CONNECTED;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = NETWORK_STATUS_CONNECTED_WIFI;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = NETWORK_STATUS_CONNECTED_MOBILE;
        } else if (conn == NetworkUtil.TYPE_UNKNOWN) {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        return status;
    }

    public static String getConnectedWifiSSID(Context context) {
        if (getConnectivityStatus(context) == NETWORK_STATUS_CONNECTED_WIFI) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            return info.getSSID();
        }

        return null;
    }
}