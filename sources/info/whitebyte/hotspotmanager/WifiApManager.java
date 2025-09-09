package info.whitebyte.hotspotmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class WifiApManager {
    private Context context;
    private final WifiManager mWifiManager;

    public WifiApManager(Context context) {
        this.context = context;
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
    }

    public void getClientList(boolean z2, FinishScanListener finishScanListener) {
        getClientList(z2, 300, finishScanListener);
    }

    public WifiConfiguration getWifiApConfiguration() {
        try {
            return (WifiConfiguration) this.mWifiManager.getClass().getMethod("getWifiApConfiguration", null).invoke(this.mWifiManager, null);
        } catch (Exception e2) {
            Log.e(getClass().toString(), "", e2);
            return null;
        }
    }

    public WIFI_AP_STATE getWifiApState() {
        try {
            int iIntValue = ((Integer) this.mWifiManager.getClass().getMethod("getWifiApState", null).invoke(this.mWifiManager, null)).intValue();
            if (iIntValue >= 10) {
                iIntValue -= 10;
            }
            return ((WIFI_AP_STATE[]) WIFI_AP_STATE.class.getEnumConstants())[iIntValue];
        } catch (Exception e2) {
            Log.e(getClass().toString(), "", e2);
            return WIFI_AP_STATE.WIFI_AP_STATE_FAILED;
        }
    }

    public boolean isWifiApEnabled() {
        return getWifiApState() == WIFI_AP_STATE.WIFI_AP_STATE_ENABLED;
    }

    public boolean setWifiApConfiguration(WifiConfiguration wifiConfiguration) {
        try {
            return ((Boolean) this.mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class).invoke(this.mWifiManager, wifiConfiguration)).booleanValue();
        } catch (Exception e2) {
            Log.e(getClass().toString(), "", e2);
            return false;
        }
    }

    public boolean setWifiApEnabled(WifiConfiguration wifiConfiguration, boolean z2) {
        try {
            showWritePermissionSettings(false);
            if (z2) {
                this.mWifiManager.setWifiEnabled(false);
            }
            return ((Boolean) this.mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE).invoke(this.mWifiManager, wifiConfiguration, Boolean.valueOf(z2))).booleanValue();
        } catch (Exception e2) {
            Log.e(getClass().toString(), "", e2);
            return false;
        }
    }

    public void showWritePermissionSettings(boolean z2) {
        if (z2 || !Settings.System.canWrite(this.context)) {
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            intent.setData(Uri.parse("package:" + this.context.getPackageName()));
            intent.addFlags(268435456);
            this.context.startActivity(intent);
        }
    }

    public void getClientList(final boolean z2, final int i2, final FinishScanListener finishScanListener) {
        new Thread(new Runnable() { // from class: info.whitebyte.hotspotmanager.WifiApManager.1
            /* JADX WARN: Not initialized variable reg: 2, insn: 0x0053: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]) (LINE:84), block:B:18:0x0053 */
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                BufferedReader bufferedReader;
                Exception e2;
                BufferedReader bufferedReader2;
                final ArrayList arrayList = new ArrayList();
                BufferedReader bufferedReader3 = null;
                try {
                } catch (IOException e3) {
                    Log.e(getClass().toString(), e3.getMessage());
                }
                try {
                    try {
                        bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"));
                        while (true) {
                            try {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                }
                                String[] strArrSplit = line.split(" +");
                                if (strArrSplit != null && strArrSplit.length >= 4 && strArrSplit[3].matches("..:..:..:..:..:..")) {
                                    boolean zIsReachable = InetAddress.getByName(strArrSplit[0]).isReachable(i2);
                                    if (!z2 || zIsReachable) {
                                        arrayList.add(new ClientScanResult(strArrSplit[0], strArrSplit[3], strArrSplit[5], zIsReachable));
                                    }
                                }
                            } catch (Exception e4) {
                                e2 = e4;
                                Log.e(getClass().toString(), e2.toString());
                                bufferedReader.close();
                                new Handler(WifiApManager.this.context.getMainLooper()).post(new Runnable() { // from class: info.whitebyte.hotspotmanager.WifiApManager.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        finishScanListener.onFinishScan(arrayList);
                                    }
                                });
                            }
                        }
                        bufferedReader.close();
                    } catch (Exception e5) {
                        bufferedReader = null;
                        e2 = e5;
                    } catch (Throwable th) {
                        th = th;
                        try {
                            bufferedReader3.close();
                        } catch (IOException e6) {
                            Log.e(getClass().toString(), e6.getMessage());
                        }
                        throw th;
                    }
                    new Handler(WifiApManager.this.context.getMainLooper()).post(new Runnable() { // from class: info.whitebyte.hotspotmanager.WifiApManager.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            finishScanListener.onFinishScan(arrayList);
                        }
                    });
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader3 = bufferedReader2;
                    bufferedReader3.close();
                    throw th;
                }
            }
        }).start();
    }
}
