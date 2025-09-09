package aisscanner;

import aisscanner.ScanSettings;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import com.alibaba.ailabs.iot.aisbase.C0420q;
import com.alibaba.ailabs.iot.aisbase.C0422r;
import com.alibaba.ailabs.iot.aisbase.RunnableC0390b;
import com.alibaba.ailabs.iot.aisbase.RunnableC0394d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class BluetoothLeScannerCompat {

    /* renamed from: a, reason: collision with root package name */
    public static BluetoothLeScannerCompat f1656a;

    /* renamed from: b, reason: collision with root package name */
    public Handler f1657b = new Handler(Looper.getMainLooper());

    /* JADX INFO: Access modifiers changed from: package-private */
    public static class a {

        /* renamed from: b, reason: collision with root package name */
        public final boolean f1659b;

        /* renamed from: c, reason: collision with root package name */
        public final boolean f1660c;

        /* renamed from: d, reason: collision with root package name */
        public final boolean f1661d;

        /* renamed from: e, reason: collision with root package name */
        public boolean f1662e;

        /* renamed from: f, reason: collision with root package name */
        @NonNull
        public final List<ScanFilter> f1663f;

        /* renamed from: g, reason: collision with root package name */
        @NonNull
        public final ScanSettings f1664g;

        /* renamed from: h, reason: collision with root package name */
        @NonNull
        public final ScanCallback f1665h;

        /* renamed from: i, reason: collision with root package name */
        @NonNull
        public final Handler f1666i;

        /* renamed from: m, reason: collision with root package name */
        @NonNull
        public final Runnable f1670m;

        /* renamed from: n, reason: collision with root package name */
        @NonNull
        public final Runnable f1671n;

        /* renamed from: a, reason: collision with root package name */
        @NonNull
        public final Object f1658a = new Object();

        /* renamed from: j, reason: collision with root package name */
        @NonNull
        public final List<ScanResult> f1667j = new ArrayList();

        /* renamed from: k, reason: collision with root package name */
        @NonNull
        public final Set<String> f1668k = new HashSet();

        /* renamed from: l, reason: collision with root package name */
        @NonNull
        public final Map<String, ScanResult> f1669l = new HashMap();

        public a(boolean z2, boolean z3, @NonNull List<ScanFilter> list, @NonNull ScanSettings scanSettings, @NonNull ScanCallback scanCallback, @NonNull Handler handler) {
            RunnableC0390b runnableC0390b = new RunnableC0390b(this);
            this.f1670m = runnableC0390b;
            this.f1671n = new RunnableC0394d(this);
            this.f1663f = Collections.unmodifiableList(list);
            this.f1664g = scanSettings;
            this.f1665h = scanCallback;
            this.f1666i = handler;
            boolean z4 = false;
            this.f1662e = false;
            this.f1661d = (scanSettings.getCallbackType() == 1 || scanSettings.getUseHardwareCallbackTypesIfSupported()) ? false : true;
            this.f1659b = (list.isEmpty() || (z3 && scanSettings.getUseHardwareFilteringIfSupported())) ? false : true;
            long reportDelayMillis = scanSettings.getReportDelayMillis();
            if (reportDelayMillis > 0 && (!z2 || !scanSettings.getUseHardwareBatchingIfSupported())) {
                z4 = true;
            }
            this.f1660c = z4;
            if (z4) {
                handler.postDelayed(runnableC0390b, reportDelayMillis);
            }
        }

        public void a() {
            this.f1662e = true;
            this.f1666i.removeCallbacksAndMessages(null);
            synchronized (this.f1658a) {
                this.f1669l.clear();
                this.f1668k.clear();
                this.f1667j.clear();
            }
        }

        public void b() {
            if (!this.f1660c || this.f1662e) {
                return;
            }
            synchronized (this.f1658a) {
                this.f1665h.onBatchScanResults(new ArrayList(this.f1667j));
                this.f1667j.clear();
                this.f1668k.clear();
            }
        }

        public final boolean b(@NonNull ScanResult scanResult) {
            Iterator<ScanFilter> it = this.f1663f.iterator();
            while (it.hasNext()) {
                if (it.next().matches(scanResult)) {
                    return true;
                }
            }
            return false;
        }

        public void a(@NonNull ScanResult scanResult) {
            boolean zIsEmpty;
            ScanResult scanResultPut;
            if (this.f1662e) {
                return;
            }
            if (this.f1663f.isEmpty() || b(scanResult)) {
                String address = scanResult.getDevice().getAddress();
                if (this.f1661d) {
                    synchronized (this.f1669l) {
                        zIsEmpty = this.f1669l.isEmpty();
                        scanResultPut = this.f1669l.put(address, scanResult);
                    }
                    if (scanResultPut == null && (this.f1664g.getCallbackType() & 2) > 0) {
                        this.f1665h.onScanResult(2, scanResult);
                    }
                    if (!zIsEmpty || (this.f1664g.getCallbackType() & 4) <= 0) {
                        return;
                    }
                    this.f1666i.removeCallbacks(this.f1671n);
                    this.f1666i.postDelayed(this.f1671n, this.f1664g.getMatchLostTaskInterval());
                    return;
                }
                if (this.f1660c) {
                    synchronized (this.f1658a) {
                        try {
                            if (!this.f1668k.contains(address)) {
                                this.f1667j.add(scanResult);
                                this.f1668k.add(address);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                }
                this.f1665h.onScanResult(1, scanResult);
            }
        }

        public void a(@NonNull List<ScanResult> list) {
            if (this.f1662e) {
                return;
            }
            if (this.f1659b) {
                ArrayList arrayList = new ArrayList();
                for (ScanResult scanResult : list) {
                    if (b(scanResult)) {
                        arrayList.add(scanResult);
                    }
                }
                list = arrayList;
            }
            this.f1665h.onBatchScanResults(list);
        }

        public void a(int i2) {
            this.f1665h.onScanFailed(i2);
        }
    }

    public static synchronized BluetoothLeScannerCompat getScanner() {
        BluetoothLeScannerCompat bluetoothLeScannerCompat = f1656a;
        if (bluetoothLeScannerCompat != null) {
            return bluetoothLeScannerCompat;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            C0422r c0422r = new C0422r();
            f1656a = c0422r;
            return c0422r;
        }
        C0420q c0420q = new C0420q();
        f1656a = c0420q;
        return c0420q;
    }

    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public abstract void a(@NonNull List<ScanFilter> list, @NonNull ScanSettings scanSettings, @NonNull ScanCallback scanCallback, @NonNull Handler handler);

    public abstract void flushPendingScanResults(@NonNull ScanCallback scanCallback);

    public void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.f1657b.post(runnable);
        } else {
            runnable.run();
        }
    }

    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void startScan(@NonNull ScanCallback scanCallback) {
        if (scanCallback == null) {
            throw new IllegalArgumentException("callback is null");
        }
        a(Collections.emptyList(), new ScanSettings.Builder().build(), scanCallback, new Handler(Looper.getMainLooper()));
    }

    @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
    public abstract void stopScan(@NonNull ScanCallback scanCallback);

    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void startScan(@Nullable List<ScanFilter> list, @Nullable ScanSettings scanSettings, @NonNull ScanCallback scanCallback) {
        if (scanCallback != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            if (list == null) {
                list = Collections.emptyList();
            }
            if (scanSettings == null) {
                scanSettings = new ScanSettings.Builder().build();
            }
            a(list, scanSettings, scanCallback, handler);
            return;
        }
        throw new IllegalArgumentException("callback is null");
    }

    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void startScan(@Nullable List<ScanFilter> list, @Nullable ScanSettings scanSettings, @NonNull ScanCallback scanCallback, @Nullable Handler handler) {
        if (scanCallback != null) {
            if (list == null) {
                list = Collections.emptyList();
            }
            if (scanSettings == null) {
                scanSettings = new ScanSettings.Builder().build();
            }
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            a(list, scanSettings, scanCallback, handler);
            return;
        }
        throw new IllegalArgumentException("callback is null");
    }
}
