package anet.channel.status;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import anet.channel.util.g;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes2.dex */
public class NetworkStatusHelper {
    private static final String TAG = "awcn.NetworkStatusHelper";
    static CopyOnWriteArraySet<INetworkStatusChangeListener> listeners = new CopyOnWriteArraySet<>();

    public interface INetworkStatusChangeListener {
        void onNetworkStatusChanged(NetworkStatus networkStatus);
    }

    public enum NetworkStatus {
        NONE,
        NO,
        G2,
        G3,
        G4,
        WIFI,
        G5;

        public String getType() {
            return this == G2 ? "2G" : this == G3 ? "3G" : this == G4 ? "4G" : this == G5 ? "5G" : toString();
        }

        public boolean isMobile() {
            return this == G2 || this == G3 || this == G4 || this == G5;
        }

        public boolean isWifi() {
            return this == WIFI;
        }
    }

    public static void addStatusChangeListener(INetworkStatusChangeListener iNetworkStatusChangeListener) {
        listeners.add(iNetworkStatusChangeListener);
    }

    public static String getApn() {
        return b.f6928e;
    }

    public static String getCarrier() {
        return b.f6931h;
    }

    public static String getDnsServerAddress() {
        return !b.f6935l.isEmpty() ? b.f6935l.get(0).getHostAddress() : b.f();
    }

    public static String getNetworkSubType() {
        return b.f6927d;
    }

    public static String getProxyType() {
        NetworkStatus networkStatus = b.f6926c;
        return (networkStatus != NetworkStatus.WIFI || getWifiProxy() == null) ? (networkStatus.isMobile() && b.f6928e.contains("wap")) ? "wap" : (!networkStatus.isMobile() || g.a() == null) ? "" : "auth" : "proxy";
    }

    public static int getRestrictBackgroundStatus() {
        return b.g();
    }

    public static String getSimOp() {
        return b.f6932i;
    }

    public static NetworkStatus getStatus() {
        return b.f6926c;
    }

    public static String getUniqueId(NetworkStatus networkStatus) {
        if (networkStatus.isWifi()) {
            String strMd5ToHex = StringUtils.md5ToHex(getWifiBSSID());
            return "WIFI$" + (TextUtils.isEmpty(strMd5ToHex) ? "" : strMd5ToHex);
        }
        if (!networkStatus.isMobile()) {
            return "";
        }
        return networkStatus.getType() + "$" + getApn();
    }

    public static String getWifiBSSID() {
        return b.f6930g;
    }

    public static Pair<String, Integer> getWifiProxy() {
        if (b.f6926c != NetworkStatus.WIFI) {
            return null;
        }
        return b.f6933j;
    }

    public static String getWifiSSID() {
        return b.f6929f;
    }

    public static boolean isConnected() {
        if (Build.VERSION.SDK_INT >= 24) {
            if (b.f6925b) {
                return true;
            }
        } else if (b.f6926c != NetworkStatus.NO) {
            return true;
        }
        try {
            NetworkInfo networkInfoE = b.e();
            if (networkInfoE != null) {
                if (networkInfoE.isConnected()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    public static boolean isProxy() {
        NetworkStatus networkStatus = b.f6926c;
        String str = b.f6928e;
        if (networkStatus == NetworkStatus.WIFI && getWifiProxy() != null) {
            return true;
        }
        if (networkStatus.isMobile()) {
            return str.contains("wap") || g.a() != null;
        }
        return false;
    }

    public static boolean isRoaming() {
        return b.f6934k;
    }

    static void notifyStatusChanged(NetworkStatus networkStatus) {
        ThreadPoolExecutorFactory.submitScheduledTask(new a(networkStatus));
    }

    public static void printNetworkDetail() {
        try {
            NetworkStatus status = getStatus();
            StringBuilder sb = new StringBuilder(128);
            sb.append("\nNetwork detail*******************************\n");
            sb.append("Status: ");
            sb.append(status.getType());
            sb.append('\n');
            sb.append("Subtype: ");
            sb.append(getNetworkSubType());
            sb.append('\n');
            if (status != NetworkStatus.NO) {
                if (status.isMobile()) {
                    sb.append("Apn: ");
                    sb.append(getApn());
                    sb.append('\n');
                    sb.append("Carrier: ");
                    sb.append(getCarrier());
                    sb.append('\n');
                } else {
                    sb.append("BSSID: ");
                    sb.append(getWifiBSSID());
                    sb.append('\n');
                    sb.append("SSID: ");
                    sb.append(getWifiSSID());
                    sb.append('\n');
                }
            }
            if (isProxy()) {
                sb.append("Proxy: ");
                sb.append(getProxyType());
                sb.append('\n');
                Pair<String, Integer> wifiProxy = getWifiProxy();
                if (wifiProxy != null) {
                    sb.append("ProxyHost: ");
                    sb.append((String) wifiProxy.first);
                    sb.append('\n');
                    sb.append("ProxyPort: ");
                    sb.append(wifiProxy.second);
                    sb.append('\n');
                }
            }
            sb.append("*********************************************");
            ALog.i(TAG, sb.toString(), null, new Object[0]);
        } catch (Exception unused) {
        }
    }

    public static void removeStatusChangeListener(INetworkStatusChangeListener iNetworkStatusChangeListener) {
        listeners.remove(iNetworkStatusChangeListener);
    }

    public static synchronized void startListener(Context context) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        b.f6924a = context;
        b.a();
        b.c();
    }

    public void stopListener(Context context) {
        b.b();
    }
}
