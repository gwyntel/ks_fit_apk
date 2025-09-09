package androidx.media3.exoplayer;

import android.content.Context;
import android.net.wifi.WifiManager;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Log;

/* loaded from: classes.dex */
final class WifiLockManager {
    private static final String TAG = "WifiLockManager";
    private static final String WIFI_LOCK_TAG = "ExoPlayer:WifiLockManager";
    private final Context applicationContext;
    private boolean enabled;
    private boolean stayAwake;

    @Nullable
    private WifiManager.WifiLock wifiLock;

    public WifiLockManager(Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    private void updateWifiLock() {
        WifiManager.WifiLock wifiLock = this.wifiLock;
        if (wifiLock == null) {
            return;
        }
        if (this.enabled && this.stayAwake) {
            wifiLock.acquire();
        } else {
            wifiLock.release();
        }
    }

    public void setEnabled(boolean z2) {
        if (z2 && this.wifiLock == null) {
            WifiManager wifiManager = (WifiManager) this.applicationContext.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null) {
                Log.w(TAG, "WifiManager is null, therefore not creating the WifiLock.");
                return;
            } else {
                WifiManager.WifiLock wifiLockCreateWifiLock = wifiManager.createWifiLock(3, WIFI_LOCK_TAG);
                this.wifiLock = wifiLockCreateWifiLock;
                wifiLockCreateWifiLock.setReferenceCounted(false);
            }
        }
        this.enabled = z2;
        updateWifiLock();
    }

    public void setStayAwake(boolean z2) {
        this.stayAwake = z2;
        updateWifiLock();
    }
}
