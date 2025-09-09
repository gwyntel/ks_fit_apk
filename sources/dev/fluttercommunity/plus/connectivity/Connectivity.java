package dev.fluttercommunity.plus.connectivity;

import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;

/* loaded from: classes4.dex */
public class Connectivity {
    private final ConnectivityManager connectivityManager;

    public Connectivity(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    private String getNetworkTypeLegacy() {
        NetworkInfo activeNetworkInfo = this.connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return "none";
        }
        int type = activeNetworkInfo.getType();
        return type != 0 ? type != 1 ? (type == 4 || type == 5) ? XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE : type != 6 ? type != 7 ? type != 9 ? type != 17 ? "none" : "vpn" : "ethernet" : "bluetooth" : "wifi" : "wifi" : XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE;
    }

    String a() {
        NetworkCapabilities networkCapabilities = this.connectivityManager.getNetworkCapabilities(this.connectivityManager.getActiveNetwork());
        return networkCapabilities == null ? "none" : networkCapabilities.hasTransport(1) ? "wifi" : networkCapabilities.hasTransport(3) ? "ethernet" : networkCapabilities.hasTransport(4) ? "vpn" : networkCapabilities.hasTransport(0) ? XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE : networkCapabilities.hasTransport(2) ? "bluetooth" : getNetworkTypeLegacy();
    }

    public ConnectivityManager getConnectivityManager() {
        return this.connectivityManager;
    }
}
