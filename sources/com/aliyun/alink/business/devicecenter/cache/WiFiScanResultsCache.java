package com.aliyun.alink.business.devicecenter.cache;

import android.net.wifi.ScanResult;
import android.text.TextUtils;
import android.util.LruCache;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class WiFiScanResultsCache implements ICache<ScanResult> {

    /* renamed from: a, reason: collision with root package name */
    public LruCache<String, ScanResult> f10224a;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final WiFiScanResultsCache f10225a = new WiFiScanResultsCache();
    }

    public static WiFiScanResultsCache getInstance() {
        return SingletonHolder.f10225a;
    }

    public final String a(ScanResult scanResult) {
        if (scanResult == null || TextUtils.isEmpty(scanResult.SSID)) {
            return null;
        }
        return scanResult.SSID + scanResult.BSSID;
    }

    @Override // com.aliyun.alink.business.devicecenter.cache.ICache
    public void clearCache() {
        ALog.d("WiFiScanResultsCache", "clearCache() called.");
    }

    @Override // com.aliyun.alink.business.devicecenter.cache.ICache
    public void updateCache(List<ScanResult> list) {
        if (list == null || list.size() < 1 || this.f10224a == null) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ScanResult scanResult = list.get(i2);
            if (scanResult != null && !TextUtils.isEmpty(scanResult.SSID)) {
                this.f10224a.put(a(scanResult), scanResult);
            }
        }
    }

    public WiFiScanResultsCache() {
        this.f10224a = null;
        this.f10224a = new LruCache<>(256);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.aliyun.alink.business.devicecenter.cache.ICache
    public ScanResult getCache(String... strArr) {
        Map<String, ScanResult> mapSnapshot;
        if (strArr == null || strArr.length < 2 || this.f10224a == null) {
            return null;
        }
        String str = strArr[0];
        String str2 = strArr[1];
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || (mapSnapshot = this.f10224a.snapshot()) == null) {
            return null;
        }
        for (Map.Entry<String, ScanResult> entry : mapSnapshot.entrySet()) {
            if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                String pkFromAp = AlinkHelper.getPkFromAp(entry.getValue().SSID);
                String macFromAp = AlinkHelper.getMacFromAp(entry.getValue().SSID);
                if (str != null && str.equals(pkFromAp) && StringUtils.isEqualString(str2, macFromAp)) {
                    ALog.i("WiFiScanResultsCache", "find match cache scan result.");
                    return entry.getValue();
                }
                if (TextUtils.isEmpty(str) && StringUtils.isEqualString(str2, macFromAp)) {
                    ALog.i("WiFiScanResultsCache", "find match cache scan result with pk=null.");
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
