package com.alibaba.ailabs.iot.aisbase;

import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanCallback;
import aisscanner.ScanFilter;
import aisscanner.ScanSettings;
import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: com.alibaba.ailabs.iot.aisbase.i, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0404i extends BluetoothLeScannerCompat {

    /* renamed from: d, reason: collision with root package name */
    @Nullable
    public HandlerThread f8411d;

    /* renamed from: e, reason: collision with root package name */
    @Nullable
    public Handler f8412e;

    /* renamed from: f, reason: collision with root package name */
    public long f8413f;

    /* renamed from: g, reason: collision with root package name */
    public long f8414g;

    /* renamed from: c, reason: collision with root package name */
    @NonNull
    public final Map<ScanCallback, BluetoothLeScannerCompat.a> f8410c = new HashMap();

    /* renamed from: h, reason: collision with root package name */
    public final Runnable f8415h = new RunnableC0396e(this);

    /* renamed from: i, reason: collision with root package name */
    public final Runnable f8416i = new RunnableC0398f(this);

    /* renamed from: j, reason: collision with root package name */
    public final BluetoothAdapter.LeScanCallback f8417j = new C0402h(this);

    @Override // aisscanner.BluetoothLeScannerCompat
    @RequiresPermission("android.permission.BLUETOOTH")
    public void flushPendingScanResults(@NonNull ScanCallback scanCallback) {
        BluetoothLeScannerCompat.a aVar;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || defaultAdapter.getState() != 12) {
            return;
        }
        if (scanCallback == null) {
            throw new IllegalArgumentException("callback cannot be null!");
        }
        synchronized (this.f8410c) {
            aVar = this.f8410c.get(scanCallback);
        }
        if (aVar == null) {
            throw new IllegalArgumentException("callback not registered!");
        }
        aVar.b();
    }

    @Override // aisscanner.BluetoothLeScannerCompat
    @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
    public void stopScan(@NonNull ScanCallback scanCallback) {
        if (scanCallback == null) {
            throw new IllegalArgumentException("scanCallback cannot be null!");
        }
        synchronized (this.f8410c) {
            try {
                BluetoothLeScannerCompat.a aVar = this.f8410c.get(scanCallback);
                if (aVar == null) {
                    return;
                }
                this.f8410c.remove(scanCallback);
                boolean zIsEmpty = this.f8410c.isEmpty();
                aVar.a();
                a();
                if (zIsEmpty) {
                    BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (defaultAdapter != null) {
                        defaultAdapter.stopLeScan(this.f8417j);
                    }
                    Handler handler = this.f8412e;
                    if (handler != null) {
                        handler.removeCallbacksAndMessages(null);
                    }
                    HandlerThread handlerThread = this.f8411d;
                    if (handlerThread != null) {
                        handlerThread.quitSafely();
                        this.f8411d = null;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // aisscanner.BluetoothLeScannerCompat
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void a(@NonNull List<ScanFilter> list, @NonNull ScanSettings scanSettings, @NonNull ScanCallback scanCallback, @NonNull Handler handler) {
        boolean zIsEmpty;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || defaultAdapter.getState() != 12) {
            return;
        }
        synchronized (this.f8410c) {
            if (this.f8410c.containsKey(scanCallback)) {
                throw new IllegalArgumentException("scanner already started with given scanCallback");
            }
            BluetoothLeScannerCompat.a aVar = new BluetoothLeScannerCompat.a(false, false, list, scanSettings, scanCallback, handler);
            zIsEmpty = this.f8410c.isEmpty();
            this.f8410c.put(scanCallback, aVar);
        }
        if (this.f8411d == null) {
            HandlerThread handlerThread = new HandlerThread(C0404i.class.getName());
            this.f8411d = handlerThread;
            handlerThread.start();
            this.f8412e = new Handler(this.f8411d.getLooper());
        }
        a();
        if (zIsEmpty) {
            defaultAdapter.startLeScan(this.f8417j);
        }
    }

    public final void a() {
        long powerSaveRest;
        long powerSaveScan;
        synchronized (this.f8410c) {
            try {
                Iterator<BluetoothLeScannerCompat.a> it = this.f8410c.values().iterator();
                powerSaveRest = Long.MAX_VALUE;
                powerSaveScan = Long.MAX_VALUE;
                while (it.hasNext()) {
                    ScanSettings scanSettings = it.next().f1664g;
                    if (scanSettings.hasPowerSaveMode()) {
                        if (powerSaveRest > scanSettings.getPowerSaveRest()) {
                            powerSaveRest = scanSettings.getPowerSaveRest();
                        }
                        if (powerSaveScan > scanSettings.getPowerSaveScan()) {
                            powerSaveScan = scanSettings.getPowerSaveScan();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (powerSaveRest < Long.MAX_VALUE && powerSaveScan < Long.MAX_VALUE) {
            this.f8413f = powerSaveRest;
            this.f8414g = powerSaveScan;
            Handler handler = this.f8412e;
            if (handler != null) {
                handler.removeCallbacks(this.f8416i);
                this.f8412e.removeCallbacks(this.f8415h);
                this.f8412e.postDelayed(this.f8415h, this.f8414g);
                return;
            }
            return;
        }
        this.f8414g = 0L;
        this.f8413f = 0L;
        Handler handler2 = this.f8412e;
        if (handler2 != null) {
            handler2.removeCallbacks(this.f8416i);
            this.f8412e.removeCallbacks(this.f8415h);
        }
    }
}
