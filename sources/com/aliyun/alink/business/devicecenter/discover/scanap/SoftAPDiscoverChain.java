package com.aliyun.alink.business.devicecenter.discover.scanap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.cache.WiFiScanResultsCache;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.discover.annotation.DeviceDiscovery;
import com.aliyun.alink.business.devicecenter.discover.base.DiscoverChainBase;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.PermissionCheckerUtils;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.alink.business.devicecenter.utils.WiFiUtils;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@DeviceDiscovery(discoveryType = {DiscoveryType.SOFT_AP_DEVICE, DiscoveryType.BEACON_DEVICE})
/* loaded from: classes2.dex */
public class SoftAPDiscoverChain extends DiscoverChainBase {

    /* renamed from: c, reason: collision with root package name */
    public static final int[] f10491c = {10, 10};

    /* renamed from: d, reason: collision with root package name */
    public static final int[] f10492d = {10, 30, 80};

    /* renamed from: e, reason: collision with root package name */
    public Context f10493e;

    /* renamed from: f, reason: collision with root package name */
    public IDeviceDiscoveryListener f10494f;

    /* renamed from: g, reason: collision with root package name */
    public ScheduledFuture f10495g;

    /* renamed from: h, reason: collision with root package name */
    public Future f10496h;

    /* renamed from: i, reason: collision with root package name */
    public AtomicBoolean f10497i;

    /* renamed from: j, reason: collision with root package name */
    public AtomicBoolean f10498j;

    /* renamed from: k, reason: collision with root package name */
    public AtomicInteger f10499k;

    /* renamed from: l, reason: collision with root package name */
    public int[] f10500l;

    /* renamed from: m, reason: collision with root package name */
    public AtomicBoolean f10501m;

    /* renamed from: n, reason: collision with root package name */
    public AtomicBoolean f10502n;

    /* renamed from: o, reason: collision with root package name */
    public Runnable f10503o;

    /* renamed from: p, reason: collision with root package name */
    public BroadcastReceiver f10504p;

    public SoftAPDiscoverChain(Context context) {
        super(context);
        this.f10493e = null;
        this.f10494f = null;
        this.f10495g = null;
        this.f10496h = null;
        this.f10497i = new AtomicBoolean(false);
        this.f10498j = new AtomicBoolean(false);
        this.f10499k = new AtomicInteger(0);
        this.f10500l = f10491c;
        this.f10501m = new AtomicBoolean(false);
        this.f10502n = new AtomicBoolean(false);
        this.f10503o = new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.scanap.SoftAPDiscoverChain.3
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                int i2;
                try {
                    int i3 = SoftAPDiscoverChain.this.f10500l[0];
                    SoftAPDiscoverChain softAPDiscoverChain = SoftAPDiscoverChain.this;
                    softAPDiscoverChain.a(softAPDiscoverChain.f10493e);
                    while (SoftAPDiscoverChain.this.f10497i.get()) {
                        if (PermissionCheckerUtils.hasLocationAccess(SoftAPDiscoverChain.this.f10493e)) {
                            boolean zStartScan = WiFiUtils.startScan(SoftAPDiscoverChain.this.f10493e);
                            StringBuilder sb = new StringBuilder();
                            sb.append("scan runnable, scan started =");
                            sb.append(zStartScan);
                            ALog.d("SoftAPDiscoverChain", sb.toString());
                            SoftAPDiscoverChain.this.f10498j.set(true);
                            i2 = SoftAPDiscoverChain.this.f10500l[SoftAPDiscoverChain.this.f10499k.getAndIncrement() % SoftAPDiscoverChain.this.f10500l.length];
                        } else {
                            ALog.d("SoftAPDiscoverChain", "scan runnable, wait for location permission.");
                            i2 = 5;
                        }
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("sleep ");
                            sb2.append(i2);
                            sb2.append(" before next scan.");
                            ALog.d("SoftAPDiscoverChain", sb2.toString());
                            Thread.sleep(i2 * 1000);
                        } catch (Exception unused) {
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        this.f10504p = new BroadcastReceiver() { // from class: com.aliyun.alink.business.devicecenter.discover.scanap.SoftAPDiscoverChain.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent == null) {
                    return;
                }
                if (!"android.net.wifi.SCAN_RESULTS".equals(intent.getAction())) {
                    ALog.d("SoftAPDiscoverChain", "action not equal to android.net.wifi.SCAN_RESULTS, ignore.");
                    return;
                }
                if (!SoftAPDiscoverChain.this.f10497i.get()) {
                    ALog.d("SoftAPDiscoverChain", "scan has stopped, ignore to get scan results.");
                    return;
                }
                boolean booleanExtra = intent.getBooleanExtra("resultsUpdated", false);
                ALog.d("SoftAPDiscoverChain", "onReceive() scan started called with: c = [" + context2 + "], intent = [" + intent + "], success = [" + booleanExtra + "]");
                if (!booleanExtra) {
                    SoftAPDiscoverChain softAPDiscoverChain = SoftAPDiscoverChain.this;
                    softAPDiscoverChain.a(softAPDiscoverChain.f10494f, 2, "BRStartFailed");
                } else if (Build.VERSION.SDK_INT < 27) {
                    SoftAPDiscoverChain softAPDiscoverChain2 = SoftAPDiscoverChain.this;
                    softAPDiscoverChain2.a(softAPDiscoverChain2.f10494f, 2, "BRStartSuccessDelay");
                } else {
                    SoftAPDiscoverChain softAPDiscoverChain3 = SoftAPDiscoverChain.this;
                    softAPDiscoverChain3.a(softAPDiscoverChain3.f10494f, 0, "BRStartSuccess");
                }
            }
        };
        this.f10493e = context;
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.base.AbilityReceiver
    public void onNotify(Intent intent) {
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void startDiscover(final IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        ALog.d("SoftAPDiscoverChain", "startDiscover() called with: listener = [" + iDeviceDiscoveryListener + "]");
        this.f10494f = iDeviceDiscoveryListener;
        if (this.f10497i.get()) {
            ALog.i("SoftAPDiscoverChain", "scan started, return.");
            return;
        }
        this.f10499k.set(0);
        this.f10497i.set(true);
        if (Build.VERSION.SDK_INT > 27) {
            this.f10500l = f10492d;
        } else {
            this.f10500l = f10491c;
        }
        ThreadPool.submit(this.f10503o);
        this.f10496h = ThreadPool.schedule(new Callable<Object>() { // from class: com.aliyun.alink.business.devicecenter.discover.scanap.SoftAPDiscoverChain.1
            @Override // java.util.concurrent.Callable
            public Object call() throws Exception {
                if (SoftAPDiscoverChain.this.f10497i.get()) {
                    SoftAPDiscoverChain.this.a(iDeviceDiscoveryListener, 0, "defaultDelayToScan");
                    return null;
                }
                ALog.d("SoftAPDiscoverChain", "isScanning = false, defaultDelayToScan return.");
                return null;
            }
        }, 5L, TimeUnit.SECONDS);
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void stopDiscover() {
        ALog.d("SoftAPDiscoverChain", "stopDiscover() called");
        try {
            this.f10494f = null;
            this.f10497i.set(false);
            this.f10498j.set(false);
            this.f10499k.set(0);
            this.f10502n.set(false);
            ScheduledFuture scheduledFuture = this.f10495g;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
                this.f10495g = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            Future future = this.f10496h;
            if (future != null) {
                future.cancel(true);
                this.f10496h = null;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            if (this.f10493e == null || !this.f10501m.get()) {
                return;
            }
            this.f10501m.set(false);
            this.f10493e.unregisterReceiver(this.f10504p);
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    public final void a(final IDeviceDiscoveryListener iDeviceDiscoveryListener, int i2, String str) {
        ALog.d("SoftAPDiscoverChain", "to getScanResult listener=" + iDeviceDiscoveryListener + ", initDelay=" + i2 + ", from=" + str);
        if (this.f10502n.get()) {
            ALog.d("SoftAPDiscoverChain", "to getScanResult has started, return.");
        } else if (!this.f10497i.get()) {
            ALog.d("SoftAPDiscoverChain", "to getScanResult has stopped, return.");
        } else {
            this.f10502n.set(true);
            this.f10495g = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.scanap.SoftAPDiscoverChain.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (SoftAPDiscoverChain.this.f10498j.get() && SoftAPDiscoverChain.this.f10497i.get()) {
                            if (iDeviceDiscoveryListener == null) {
                                ALog.w("SoftAPDiscoverChain", "startScan not started. or listener=null.");
                                return;
                            }
                            List<ScanResult> scanResult = WiFiUtils.getScanResult(SoftAPDiscoverChain.this.f10493e);
                            if (scanResult == null || scanResult.size() <= 1) {
                                return;
                            }
                            final ArrayList arrayList = new ArrayList();
                            final ArrayList arrayList2 = new ArrayList();
                            ArrayList arrayList3 = new ArrayList();
                            for (int i3 = 0; i3 < scanResult.size(); i3++) {
                                ScanResult scanResult2 = scanResult.get(i3);
                                if (scanResult2 != null && !TextUtils.isEmpty(scanResult2.SSID)) {
                                    String pkFromAp = AlinkHelper.getPkFromAp(scanResult2.SSID);
                                    if (!TextUtils.isEmpty(pkFromAp) || !scanResult2.SSID.startsWith(AlinkConstants.SOFT_AP_SSID_PREFIX)) {
                                        DeviceInfo deviceInfo = new DeviceInfo();
                                        deviceInfo.productKey = pkFromAp;
                                        deviceInfo.id = AlinkHelper.getMacFromAp(scanResult2.SSID);
                                        deviceInfo.setExtraDeviceInfo(AlinkConstants.KEY_APP_SSID, scanResult2.SSID);
                                        deviceInfo.setExtraDeviceInfo(AlinkConstants.KEY_APP_BSSID, scanResult2.BSSID);
                                        if (scanResult2.SSID.startsWith(AlinkConstants.MOCK_AP_SSID_PREFIX) && SoftAPDiscoverChain.this.discoveryTypes != null && SoftAPDiscoverChain.this.discoveryTypes.contains(DiscoveryType.BEACON_DEVICE)) {
                                            arrayList2.add(deviceInfo);
                                        } else if (AlinkHelper.isValidSoftAp(scanResult2.SSID, false) && SoftAPDiscoverChain.this.discoveryTypes != null && SoftAPDiscoverChain.this.discoveryTypes.contains(DiscoveryType.SOFT_AP_DEVICE)) {
                                            arrayList.add(deviceInfo);
                                        }
                                        arrayList3.add(scanResult2);
                                    }
                                }
                            }
                            if (SoftAPDiscoverChain.this.f10498j.get()) {
                                if (arrayList.size() > 0 || arrayList2.size() > 0) {
                                    WiFiScanResultsCache.getInstance().updateCache(arrayList3);
                                    DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.scanap.SoftAPDiscoverChain.2.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (iDeviceDiscoveryListener != null) {
                                                ArrayList arrayList4 = arrayList;
                                                if (arrayList4 != null && arrayList4.size() > 0) {
                                                    iDeviceDiscoveryListener.onDeviceFound(DiscoveryType.SOFT_AP_DEVICE, arrayList);
                                                }
                                                ArrayList arrayList5 = arrayList2;
                                                if (arrayList5 == null || arrayList5.size() <= 0) {
                                                    return;
                                                }
                                                iDeviceDiscoveryListener.onDeviceFound(DiscoveryType.BEACON_DEVICE, arrayList2);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }, i2, 2L, TimeUnit.SECONDS);
        }
    }

    public final void a(Context context) {
        ALog.d("SoftAPDiscoverChain", "registerScan() called with: context = [" + context + "], hasRegisteredReceiver=" + this.f10501m);
        if (context != null) {
            try {
                if (this.f10501m.compareAndSet(false, true)) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
                    context.registerReceiver(this.f10504p, intentFilter);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public SoftAPDiscoverChain(Context context, EnumSet<DiscoveryType> enumSet) {
        super(context);
        this.f10493e = null;
        this.f10494f = null;
        this.f10495g = null;
        this.f10496h = null;
        this.f10497i = new AtomicBoolean(false);
        this.f10498j = new AtomicBoolean(false);
        this.f10499k = new AtomicInteger(0);
        this.f10500l = f10491c;
        this.f10501m = new AtomicBoolean(false);
        this.f10502n = new AtomicBoolean(false);
        this.f10503o = new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.scanap.SoftAPDiscoverChain.3
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                int i2;
                try {
                    int i3 = SoftAPDiscoverChain.this.f10500l[0];
                    SoftAPDiscoverChain softAPDiscoverChain = SoftAPDiscoverChain.this;
                    softAPDiscoverChain.a(softAPDiscoverChain.f10493e);
                    while (SoftAPDiscoverChain.this.f10497i.get()) {
                        if (PermissionCheckerUtils.hasLocationAccess(SoftAPDiscoverChain.this.f10493e)) {
                            boolean zStartScan = WiFiUtils.startScan(SoftAPDiscoverChain.this.f10493e);
                            StringBuilder sb = new StringBuilder();
                            sb.append("scan runnable, scan started =");
                            sb.append(zStartScan);
                            ALog.d("SoftAPDiscoverChain", sb.toString());
                            SoftAPDiscoverChain.this.f10498j.set(true);
                            i2 = SoftAPDiscoverChain.this.f10500l[SoftAPDiscoverChain.this.f10499k.getAndIncrement() % SoftAPDiscoverChain.this.f10500l.length];
                        } else {
                            ALog.d("SoftAPDiscoverChain", "scan runnable, wait for location permission.");
                            i2 = 5;
                        }
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("sleep ");
                            sb2.append(i2);
                            sb2.append(" before next scan.");
                            ALog.d("SoftAPDiscoverChain", sb2.toString());
                            Thread.sleep(i2 * 1000);
                        } catch (Exception unused) {
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        this.f10504p = new BroadcastReceiver() { // from class: com.aliyun.alink.business.devicecenter.discover.scanap.SoftAPDiscoverChain.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent == null) {
                    return;
                }
                if (!"android.net.wifi.SCAN_RESULTS".equals(intent.getAction())) {
                    ALog.d("SoftAPDiscoverChain", "action not equal to android.net.wifi.SCAN_RESULTS, ignore.");
                    return;
                }
                if (!SoftAPDiscoverChain.this.f10497i.get()) {
                    ALog.d("SoftAPDiscoverChain", "scan has stopped, ignore to get scan results.");
                    return;
                }
                boolean booleanExtra = intent.getBooleanExtra("resultsUpdated", false);
                ALog.d("SoftAPDiscoverChain", "onReceive() scan started called with: c = [" + context2 + "], intent = [" + intent + "], success = [" + booleanExtra + "]");
                if (!booleanExtra) {
                    SoftAPDiscoverChain softAPDiscoverChain = SoftAPDiscoverChain.this;
                    softAPDiscoverChain.a(softAPDiscoverChain.f10494f, 2, "BRStartFailed");
                } else if (Build.VERSION.SDK_INT < 27) {
                    SoftAPDiscoverChain softAPDiscoverChain2 = SoftAPDiscoverChain.this;
                    softAPDiscoverChain2.a(softAPDiscoverChain2.f10494f, 2, "BRStartSuccessDelay");
                } else {
                    SoftAPDiscoverChain softAPDiscoverChain3 = SoftAPDiscoverChain.this;
                    softAPDiscoverChain3.a(softAPDiscoverChain3.f10494f, 0, "BRStartSuccess");
                }
            }
        };
        this.f10493e = context;
        if (enumSet == null || enumSet.size() < 1) {
            return;
        }
        DiscoveryType discoveryType = DiscoveryType.BEACON_DEVICE;
        if (enumSet.contains(discoveryType)) {
            addDiscoveryType(discoveryType);
        }
        DiscoveryType discoveryType2 = DiscoveryType.SOFT_AP_DEVICE;
        if (enumSet.contains(discoveryType2)) {
            addDiscoveryType(discoveryType2);
        }
    }
}
