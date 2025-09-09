package com.alibaba.ailabs.iot.aisbase.scanner;

import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanCallback;
import aisscanner.ScanResult;
import aisscanner.ScanSettings;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.ailabs.iot.aisbase.Ea;
import com.alibaba.ailabs.iot.aisbase.Fa;
import com.alibaba.ailabs.iot.aisbase.Ga;
import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.aisbase.utils.ThreadPool;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class BLEScannerProxy {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8518a = "BLEScannerProxy";

    /* renamed from: b, reason: collision with root package name */
    public static BLEScannerProxy f8519b;

    /* renamed from: c, reason: collision with root package name */
    public static IMeshNetworkPUDListener f8520c;

    /* renamed from: g, reason: collision with root package name */
    public ScanCallback f8524g;

    /* renamed from: h, reason: collision with root package name */
    public ScanCallback f8525h;

    /* renamed from: k, reason: collision with root package name */
    public c f8528k;

    /* renamed from: l, reason: collision with root package name */
    public c f8529l;

    /* renamed from: d, reason: collision with root package name */
    public Handler f8521d = new Handler(Looper.getMainLooper());

    /* renamed from: e, reason: collision with root package name */
    public boolean f8522e = false;

    /* renamed from: f, reason: collision with root package name */
    public boolean f8523f = false;

    /* renamed from: i, reason: collision with root package name */
    public Context f8526i = null;

    /* renamed from: j, reason: collision with root package name */
    public Map<Integer, ILeScanStrategy> f8527j = new ConcurrentHashMap();

    /* renamed from: m, reason: collision with root package name */
    public ReentrantLock f8530m = new ReentrantLock(false);

    /* renamed from: n, reason: collision with root package name */
    public BroadcastReceiver f8531n = null;

    /* renamed from: o, reason: collision with root package name */
    public LocalBroadcastManager f8532o = null;

    /* renamed from: p, reason: collision with root package name */
    public final String f8533p = "ACTION_SCAN_TOO_FREQUENTLY";

    /* renamed from: q, reason: collision with root package name */
    public List<b> f8534q = new ArrayList(6);

    /* renamed from: r, reason: collision with root package name */
    public volatile boolean f8535r = false;

    public interface IMeshNetworkPUDListener {
        void onMeshNetworkPDURecevied(ScanResult scanResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static class a extends ScanCallback {

        /* renamed from: a, reason: collision with root package name */
        public boolean f8536a;

        /* renamed from: b, reason: collision with root package name */
        public List<ILeScanStrategy> f8537b;

        /* renamed from: c, reason: collision with root package name */
        public Set<ILeScanCallback> f8538c;

        public a(boolean z2, List<ILeScanStrategy> list, ILeScanCallback iLeScanCallback) {
            this.f8536a = false;
            CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
            this.f8538c = copyOnWriteArraySet;
            this.f8536a = z2;
            this.f8537b = list;
            copyOnWriteArraySet.add(iLeScanCallback);
        }

        public void a(ILeScanCallback iLeScanCallback) {
            if (this.f8538c.contains(iLeScanCallback)) {
                return;
            }
            LogUtils.i(BLEScannerProxy.f8518a, "addLeScanCallback: " + iLeScanCallback);
            this.f8538c.add(iLeScanCallback);
        }

        public boolean b(ILeScanCallback iLeScanCallback) {
            return this.f8538c.remove(iLeScanCallback);
        }

        public void c() {
            Iterator<ILeScanCallback> it = this.f8538c.iterator();
            while (it.hasNext()) {
                it.next().onStopScan();
            }
        }

        @Override // aisscanner.ScanCallback
        public void onBatchScanResults(@NonNull List<ScanResult> list) {
            super.onBatchScanResults(list);
            Iterator<ScanResult> it = list.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
        }

        @Override // aisscanner.ScanCallback
        public void onScanFailed(int i2) {
            super.onScanFailed(i2);
            LogUtils.e(BLEScannerProxy.f8518a, String.format("scan failed, error code: %d", Integer.valueOf(i2)));
        }

        @Override // aisscanner.ScanCallback
        public void onScanResult(int i2, @NonNull ScanResult scanResult) {
            super.onScanResult(i2, scanResult);
            a(scanResult);
        }

        public void b() {
            this.f8538c.clear();
        }

        public void a(ILeScanStrategy iLeScanStrategy) {
            if (this.f8537b.contains(iLeScanStrategy)) {
                LogUtils.w(BLEScannerProxy.f8518a, "addStrategy: strategy has exist");
            } else {
                this.f8537b.add(iLeScanStrategy);
                LogUtils.i(BLEScannerProxy.f8518a, "addStrategy success");
            }
        }

        public boolean a() {
            return this.f8538c.size() == 0;
        }

        public final synchronized void a(ScanResult scanResult) {
            if (scanResult != null) {
                if (scanResult.getScanRecord() != null) {
                    if (scanResult.getScanRecord().getMeshNetworkPUD() != null) {
                        if (BLEScannerProxy.f8520c != null) {
                            ThreadPool.execute(new Ga(this, scanResult));
                        }
                        return;
                    }
                    scanResult.getScanRecord().getManufacturerSpecificData(424);
                    for (ILeScanStrategy iLeScanStrategy : this.f8537b) {
                        BluetoothDeviceWrapper bluetoothDeviceWrapperCreateFromScanResult = iLeScanStrategy.createFromScanResult(scanResult);
                        if (bluetoothDeviceWrapperCreateFromScanResult != null) {
                            Iterator it = new CopyOnWriteArraySet(this.f8538c).iterator();
                            while (it.hasNext()) {
                                ((ILeScanCallback) it.next()).onAliBLEDeviceFound(bluetoothDeviceWrapperCreateFromScanResult, iLeScanStrategy.getBluetoothDeviceSubtype());
                            }
                        }
                    }
                }
            }
        }
    }

    private class b {

        /* renamed from: a, reason: collision with root package name */
        public long f8539a;

        public b(long j2) {
            this.f8539a = j2;
        }
    }

    private class c implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public ILeScanCallback f8541a;

        /* renamed from: b, reason: collision with root package name */
        public ScanCallback f8542b;

        /* renamed from: c, reason: collision with root package name */
        public boolean f8543c;

        public c(ILeScanCallback iLeScanCallback, ScanCallback scanCallback, boolean z2) {
            this.f8541a = iLeScanCallback;
            this.f8542b = scanCallback;
            this.f8543c = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            LogUtils.d(BLEScannerProxy.f8518a, "Scan timeout task trigger");
            if (this.f8543c) {
                BLEScannerProxy.this.stopScan(this.f8541a);
            } else {
                BLEScannerProxy.this.stopDirectionalScan();
            }
        }
    }

    public static BLEScannerProxy getInstance() {
        if (f8519b == null) {
            synchronized (BLEScannerProxy.class) {
                try {
                    if (f8519b == null) {
                        f8519b = new BLEScannerProxy();
                    }
                } finally {
                }
            }
        }
        return f8519b;
    }

    public static boolean isBleEnabled() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter != null && defaultAdapter.isEnabled();
    }

    public static boolean isLocationPermissionsGranted(Context context) {
        return Build.VERSION.SDK_INT >= 33 ? ContextCompat.checkSelfPermission(context, "android.permission.NEARBY_WIFI_DEVICES") == 0 : ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    public final void c() {
        this.f8531n = new Ea(this);
    }

    public boolean checkIfInScanning() {
        return this.f8522e;
    }

    public boolean checkPermission(Context context, String str) {
        return (context == null || TextUtils.isEmpty(str) || ContextCompat.checkSelfPermission(context, str) != 0) ? false : true;
    }

    public final boolean d() {
        return this.f8534q.size() >= 5 && System.currentTimeMillis() - this.f8534q.get(0).f8539a < 30000;
    }

    public ScanCallback getDirectionalScanCallback(ILeScanStrategy iLeScanStrategy, ILeScanCallback iLeScanCallback) {
        this.f8530m.lock();
        try {
            ScanCallback scanCallback = this.f8525h;
            if (scanCallback != null) {
                return scanCallback;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(iLeScanStrategy);
            a aVar = new a(false, arrayList, iLeScanCallback);
            this.f8525h = aVar;
            return aVar;
        } finally {
            this.f8530m.unlock();
        }
    }

    public ScanCallback getScanCallback(List<ILeScanStrategy> list, ILeScanCallback iLeScanCallback) {
        this.f8530m.lock();
        try {
            if (this.f8524g == null) {
                Fa fa = new Fa(this, true, list, iLeScanCallback);
                this.f8524g = fa;
                this.f8530m.unlock();
                return fa;
            }
            Iterator<ILeScanStrategy> it = list.iterator();
            while (it.hasNext()) {
                ((a) this.f8524g).a(it.next());
            }
            ((a) this.f8524g).a(iLeScanCallback);
            ScanCallback scanCallback = this.f8524g;
            this.f8530m.unlock();
            return scanCallback;
        } catch (Throwable th) {
            this.f8530m.unlock();
            throw th;
        }
    }

    public boolean isBleScanPermissionGranted(Context context) {
        if (context == null) {
            return false;
        }
        Context applicationContext = context.getApplicationContext();
        int i2 = applicationContext instanceof Application ? applicationContext.getApplicationInfo().targetSdkVersion : 0;
        if (i2 < 31 || Build.VERSION.SDK_INT < 31) {
            return true;
        }
        String str = f8518a;
        LogUtils.d(str, "appTargetSdk = " + i2);
        if (checkPermission(applicationContext, "android.permission.BLUETOOTH_SCAN")) {
            return true;
        }
        LogUtils.w(str, "app target sdk = 31 and not BLUETOOTH_SCAN permission granted, return.");
        return false;
    }

    public void lock() {
        this.f8535r = true;
    }

    public boolean registerLeScanStrategy(int i2, ILeScanStrategy iLeScanStrategy) {
        String str = f8518a;
        LogUtils.d(str, "Register device type: " + i2);
        if (this.f8527j.get(Integer.valueOf(i2)) != null) {
            LogUtils.w(str, "The device type has been registered");
            return false;
        }
        this.f8527j.put(Integer.valueOf(i2), iLeScanStrategy);
        this.f8530m.lock();
        try {
            ScanCallback scanCallback = this.f8524g;
            if (scanCallback != null) {
                ((a) scanCallback).a(iLeScanStrategy);
            }
            this.f8530m.unlock();
            return true;
        } catch (Throwable th) {
            this.f8530m.unlock();
            throw th;
        }
    }

    public void setOnMeshNetworkPUDListener(IMeshNetworkPUDListener iMeshNetworkPUDListener) {
        f8520c = iMeshNetworkPUDListener;
    }

    public ScanCallback startDirectionalLeScan(Context context, int i2, String[] strArr, ILeScanStrategy iLeScanStrategy, ILeScanCallback iLeScanCallback) {
        if (this.f8535r) {
            LogUtils.w(f8518a, "Scan not allowed");
            return null;
        }
        String str = f8518a;
        LogUtils.d(str, "Start performing a directional scan[" + TextUtils.join(",", strArr) + "]");
        if (this.f8523f) {
            if (i2 != 0) {
                this.f8521d.removeCallbacks(this.f8529l);
                this.f8521d.postDelayed(this.f8529l, i2);
            }
            c cVar = this.f8529l;
            if (cVar == null) {
                return null;
            }
            return cVar.f8542b;
        }
        if (d()) {
            LogUtils.i(str, "Scanning too frequently: ACTION_SCAN_TOO_FREQUENTLY");
            this.f8532o = LocalBroadcastManager.getInstance(context);
            this.f8532o.sendBroadcast(new Intent("ACTION_SCAN_TOO_FREQUENTLY"));
            return null;
        }
        this.f8523f = true;
        this.f8526i = context.getApplicationContext();
        if (this.f8531n == null) {
            c();
            this.f8526i.registerReceiver(this.f8531n, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        }
        ScanSettings scanSettingsBuild = new ScanSettings.Builder().setScanMode(2).setUseHardwareFilteringIfSupported(true).build();
        ArrayList arrayList = new ArrayList();
        ScanCallback directionalScanCallback = getDirectionalScanCallback(iLeScanStrategy, iLeScanCallback);
        try {
            BluetoothLeScannerCompat.getScanner().startScan(arrayList, scanSettingsBuild, directionalScanCallback);
            this.f8534q.add(new b(System.currentTimeMillis()));
            if (iLeScanCallback != null) {
                iLeScanCallback.onStartScan();
            }
            if (i2 > 0) {
                c cVar2 = new c(iLeScanCallback, directionalScanCallback, false);
                this.f8529l = cVar2;
                this.f8521d.postDelayed(cVar2, i2);
            }
            this.f8534q.add(new b(System.currentTimeMillis()));
            LogUtils.v(str, "Start up system scanner success");
            return directionalScanCallback;
        } catch (IllegalArgumentException e2) {
            e = e2;
            LogUtils.e(f8518a, e.toString());
            return directionalScanCallback;
        } catch (IllegalStateException e3) {
            e = e3;
            LogUtils.e(f8518a, e.toString());
            return directionalScanCallback;
        } catch (Throwable th) {
            LogUtils.e(f8518a, "catch ex(t=31?)=" + th.toString());
            return directionalScanCallback;
        }
    }

    public ScanCallback startLeScan(Context context, int i2, boolean z2, int i3, ILeScanCallback iLeScanCallback) {
        CopyOnWriteArrayList copyOnWriteArrayList;
        if (this.f8535r) {
            LogUtils.w(f8518a, "Scan not allowed");
            return null;
        }
        String str = f8518a;
        LogUtils.d(str, "start scan, current in scanning: " + this.f8522e + ", scan callback: " + iLeScanCallback);
        if (!isBleEnabled()) {
            LogUtils.w(str, "Bluetooth not enable");
            return null;
        }
        if (!isBleScanPermissionGranted(context)) {
            LogUtils.w(str, "Bluetooth scan permission not enable for target sdk 31.");
            return null;
        }
        if (!isLocationPermissionsGranted(context)) {
            LogUtils.w(str, "Location permission is not granted");
            return null;
        }
        this.f8530m.lock();
        try {
            Set<Integer> setKeySet = this.f8527j.keySet();
            if (this.f8522e) {
                if (i2 != 0) {
                    this.f8521d.removeCallbacks(this.f8528k);
                    this.f8521d.postDelayed(this.f8528k, i2);
                }
                if (this.f8524g != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Add new scan callback: ");
                    sb.append(iLeScanCallback);
                    LogUtils.i(str, sb.toString());
                    ((a) this.f8524g).a(iLeScanCallback);
                    for (Integer num : setKeySet) {
                        int iIntValue = num.intValue();
                        if ((i3 & iIntValue) == iIntValue && this.f8527j.containsKey(num)) {
                            String str2 = f8518a;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Prepare add scan strategy for device type: ");
                            sb2.append(iIntValue);
                            LogUtils.v(str2, sb2.toString());
                            ((a) this.f8524g).a(this.f8527j.get(num));
                        }
                    }
                }
                if (iLeScanCallback != null) {
                    iLeScanCallback.onStartScan();
                }
                ScanCallback scanCallback = this.f8524g;
                this.f8530m.unlock();
                return scanCallback;
            }
            if (d()) {
                LogUtils.i(str, "Scanning too frequently: ACTION_SCAN_TOO_FREQUENTLY");
                this.f8532o = LocalBroadcastManager.getInstance(context);
                this.f8532o.sendBroadcast(new Intent("ACTION_SCAN_TOO_FREQUENTLY"));
                this.f8530m.unlock();
                return null;
            }
            List<ILeScanStrategy> copyOnWriteArrayList2 = new CopyOnWriteArrayList<>();
            CopyOnWriteArrayList copyOnWriteArrayList3 = new CopyOnWriteArrayList();
            for (Integer num2 : setKeySet) {
                int iIntValue2 = num2.intValue();
                if ((i3 & iIntValue2) == iIntValue2 && this.f8527j.containsKey(num2)) {
                    String str3 = f8518a;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Add scan strategy for device type: ");
                    sb3.append(iIntValue2);
                    LogUtils.v(str3, sb3.toString());
                    copyOnWriteArrayList2.add(this.f8527j.get(num2));
                    copyOnWriteArrayList3.addAll(this.f8527j.get(num2).getCustomScanFilters());
                }
            }
            this.f8522e = true;
            this.f8526i = context.getApplicationContext();
            if (this.f8531n == null) {
                c();
                this.f8526i.registerReceiver(this.f8531n, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
            }
            ScanSettings scanSettingsBuild = new ScanSettings.Builder().setScanMode(1).setUseHardwareFilteringIfSupported(true).build();
            if (z2) {
                copyOnWriteArrayList = new CopyOnWriteArrayList();
                if (copyOnWriteArrayList3.size() > 0) {
                    copyOnWriteArrayList.addAll(copyOnWriteArrayList3);
                }
            } else {
                copyOnWriteArrayList = null;
            }
            BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
            ScanCallback scanCallback2 = getScanCallback(copyOnWriteArrayList2, iLeScanCallback);
            try {
                scanner.startScan(copyOnWriteArrayList, scanSettingsBuild, scanCallback2);
                if (iLeScanCallback != null) {
                    iLeScanCallback.onStartScan();
                }
                c cVar = new c(iLeScanCallback, scanCallback2, true);
                this.f8528k = cVar;
                if (i2 > 0) {
                    this.f8521d.postDelayed(cVar, i2);
                }
                this.f8534q.add(new b(System.currentTimeMillis()));
                LogUtils.i(f8518a, "Start up system scanner success");
                this.f8530m.unlock();
                return scanCallback2;
            } catch (IllegalArgumentException | IllegalStateException e2) {
                this.f8522e = false;
                this.f8524g = null;
                String str4 = f8518a;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Start up system scanner failed: ");
                sb4.append(e2.toString());
                LogUtils.e(str4, sb4.toString());
                this.f8530m.unlock();
                return null;
            }
        } catch (Throwable th) {
            this.f8530m.unlock();
            throw th;
        }
    }

    public void stopDirectionalScan() {
        this.f8530m.lock();
        try {
            String str = f8518a;
            StringBuilder sb = new StringBuilder();
            sb.append("Stop directional scan, current in scanning: ");
            sb.append(this.f8523f);
            LogUtils.d(str, sb.toString());
            if (this.f8523f && this.f8525h != null) {
                this.f8521d.removeCallbacks(this.f8529l);
                try {
                    BluetoothLeScannerCompat.getScanner().stopScan(this.f8525h);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (this.f8534q.size() > 5) {
                    this.f8534q.remove(0);
                    LogUtils.i(f8518a, "Update timestamp history");
                }
                ((a) this.f8525h).c();
                this.f8525h = null;
                this.f8523f = false;
            }
        } finally {
            this.f8530m.unlock();
        }
    }

    public boolean stopScan(ILeScanCallback iLeScanCallback) {
        ScanCallback scanCallback;
        String str = f8518a;
        LogUtils.i(str, "Stop scan with callback: " + iLeScanCallback);
        this.f8530m.lock();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("stop scan, current in scanning: ");
            sb.append(this.f8522e);
            LogUtils.d(str, sb.toString());
            if (this.f8522e && (scanCallback = this.f8524g) != null) {
                a aVar = (a) scanCallback;
                if (iLeScanCallback != null) {
                    iLeScanCallback.onStopScan();
                }
                aVar.b(iLeScanCallback);
                if (aVar.a()) {
                    this.f8521d.removeCallbacks(this.f8528k);
                    BluetoothLeScannerCompat.getScanner().stopScan(this.f8524g);
                    if (this.f8534q.size() > 5) {
                        this.f8534q.remove(0);
                        LogUtils.i(str, "Update timestamp history");
                    }
                    this.f8522e = false;
                    this.f8524g = null;
                    LogUtils.i(str, "Stop system scanner success");
                }
                this.f8530m.unlock();
                return true;
            }
            return false;
        } finally {
            this.f8530m.unlock();
        }
    }

    public void unlock() {
        this.f8535r = false;
    }

    public boolean stopScan() {
        this.f8530m.lock();
        try {
            String str = f8518a;
            StringBuilder sb = new StringBuilder();
            sb.append("stop scan, current in scanning: ");
            sb.append(this.f8522e);
            LogUtils.d(str, sb.toString());
            if (this.f8522e && this.f8524g != null) {
                this.f8521d.removeCallbacks(this.f8528k);
                BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
                a aVar = (a) this.f8524g;
                aVar.c();
                aVar.b();
                try {
                    scanner.stopScan(this.f8524g);
                    LogUtils.i(str, "Stop system scanner success");
                } catch (Exception e2) {
                    String str2 = f8518a;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Stop system scanner failed: ");
                    sb2.append(e2.toString());
                    LogUtils.e(str2, sb2.toString());
                }
                if (this.f8534q.size() > 5) {
                    this.f8534q.remove(0);
                    LogUtils.i(f8518a, "Update timestamp history");
                }
                this.f8522e = false;
                this.f8530m.unlock();
                return true;
            }
            return false;
        } finally {
            this.f8530m.unlock();
        }
    }

    public ScanCallback startLeScan(Context context, int i2, boolean z2, int i3, ILeScanCallback iLeScanCallback, ScanSettings scanSettings) {
        ArrayList arrayList;
        if (this.f8535r) {
            LogUtils.w(f8518a, "Scan not allowed");
            return null;
        }
        String str = f8518a;
        LogUtils.d(str, "start scan, current in scanning: " + this.f8522e);
        if (!isBleEnabled()) {
            LogUtils.w(str, "Bluetooth not enable");
            return null;
        }
        if (!isLocationPermissionsGranted(context)) {
            LogUtils.w(str, "Location permission is not granted");
            return null;
        }
        if (!isBleScanPermissionGranted(context)) {
            LogUtils.w(str, "Bluetooth scan permission not enable for target sdk 31.");
            return null;
        }
        this.f8530m.lock();
        try {
            if (this.f8522e) {
                if (i2 != 0) {
                    this.f8521d.removeCallbacks(this.f8528k);
                    this.f8521d.postDelayed(this.f8528k, i2);
                }
                ScanCallback scanCallback = this.f8524g;
                if (scanCallback != null) {
                    ((a) scanCallback).a(iLeScanCallback);
                }
                if (iLeScanCallback != null) {
                    iLeScanCallback.onStartScan();
                }
                ScanCallback scanCallback2 = this.f8524g;
                this.f8530m.unlock();
                return scanCallback2;
            }
            if (d()) {
                LogUtils.i(str, "Scanning too frequently: ACTION_SCAN_TOO_FREQUENTLY");
                this.f8532o = LocalBroadcastManager.getInstance(context);
                this.f8532o.sendBroadcast(new Intent("ACTION_SCAN_TOO_FREQUENTLY"));
                this.f8530m.unlock();
                return null;
            }
            Set<Integer> setKeySet = this.f8527j.keySet();
            List<ILeScanStrategy> arrayList2 = new ArrayList<>();
            ArrayList arrayList3 = new ArrayList();
            for (Integer num : setKeySet) {
                int iIntValue = num.intValue();
                if ((i3 & iIntValue) == iIntValue && this.f8527j.containsKey(num)) {
                    String str2 = f8518a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Add scan strategy for device type: ");
                    sb.append(iIntValue);
                    LogUtils.v(str2, sb.toString());
                    arrayList2.add(this.f8527j.get(num));
                    arrayList3.addAll(this.f8527j.get(num).getCustomScanFilters());
                }
            }
            this.f8522e = true;
            this.f8526i = context.getApplicationContext();
            if (this.f8531n == null) {
                c();
                this.f8526i.registerReceiver(this.f8531n, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
            }
            if (z2) {
                arrayList = new ArrayList();
                if (arrayList3.size() > 0) {
                    arrayList.addAll(arrayList3);
                }
            } else {
                arrayList = null;
            }
            BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
            ScanCallback scanCallback3 = getScanCallback(arrayList2, iLeScanCallback);
            try {
                scanner.startScan(arrayList, scanSettings, scanCallback3);
                if (iLeScanCallback != null) {
                    iLeScanCallback.onStartScan();
                }
                c cVar = new c(iLeScanCallback, scanCallback3, true);
                this.f8528k = cVar;
                if (i2 > 0) {
                    this.f8521d.postDelayed(cVar, i2);
                }
                this.f8534q.add(new b(System.currentTimeMillis()));
                LogUtils.v(f8518a, "Start up system scanner success");
                this.f8530m.unlock();
                return scanCallback3;
            } catch (IllegalArgumentException | IllegalStateException e2) {
                this.f8522e = false;
                this.f8524g = null;
                LogUtils.e(f8518a, e2.toString());
                this.f8530m.unlock();
                return null;
            }
        } catch (Throwable th) {
            this.f8530m.unlock();
            throw th;
        }
    }
}
