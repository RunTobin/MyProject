package com.huang.bchtsystem;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hikvision.netsdk.HCNetSDK;


public class MyApplication extends Application {
    private static MyApplication application;
    public static MyApplication getAppContext() {
        return application;
    }
    private ConnectivityManager mConnMgr;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        mConnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }
    private ConnectivityManager getConnectivityManager() {
        if (mConnMgr != null) {
            return mConnMgr;
        } else {
            Object obj = getSystemService(Context.CONNECTIVITY_SERVICE);
            return obj == null ? null : (ConnectivityManager) obj;
        }
    }
    public boolean hasNetwork() {
        ConnectivityManager manager = getConnectivityManager();
        return manager != null
                && manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public String getNetworkType() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        ConnectivityManager manager = getConnectivityManager();
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnectedOrConnecting()) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return "WIFI";
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    switch (tm.getNetworkType()) {
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                            return "RTT"; // ~ 50-100 kbps
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                            return "CDMA"; // ~ 14-64 kbps
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                            return "EDGE"; // ~ 50-100 kbps
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            return "EVDO_0"; // ~ 400-1000 kbps
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                            return "EVDO_A"; // ~ 600-1400 kbps
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                            return "GPRS"; // ~ 100 kbps
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                            return "HSDPA"; // ~ 2-14 Mbps
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                            return "HSPA"; // ~ 700-1700 kbps
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                            return "HSUPA"; // ~ 1-23 Mbps
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                            return "UMTS"; // ~ 400-7000 kbps
                        case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                            return "EHRPD"; // ~ 1-2 Mbps
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                            return "EVDOB"; // ~ 5 Mbps
                        case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                            return "HSDPA"; // ~ 10-20 Mbps
                        case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                            return "IDEN"; // ~25 kbps
                        case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                            return "LTE"; // ~ 10+ Mbps
                        // Unknown
                        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        default:
                            return "MOBILE";
                    }
                }
            }
            return "NoNetwork";
        }
        return "NoNetwork";
    }
}
