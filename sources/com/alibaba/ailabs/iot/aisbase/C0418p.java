package com.alibaba.ailabs.iot.aisbase;

import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanCallback;
import aisscanner.ScanFilter;
import aisscanner.ScanRecord;
import aisscanner.ScanResult;
import aisscanner.ScanSettings;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@TargetApi(21)
/* renamed from: com.alibaba.ailabs.iot.aisbase.p, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0418p extends BluetoothLeScannerCompat {

    /* renamed from: c, reason: collision with root package name */
    public final Map<ScanCallback, a> f8448c = new HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.alibaba.ailabs.iot.aisbase.p$a */
    final class a extends BluetoothLeScannerCompat.a {

        /* renamed from: o, reason: collision with root package name */
        @NonNull
        public final android.bluetooth.le.ScanCallback f8449o;

        public /* synthetic */ a(C0418p c0418p, boolean z2, boolean z3, List list, ScanSettings scanSettings, ScanCallback scanCallback, Handler handler, RunnableC0406j runnableC0406j) {
            this(z2, z3, list, scanSettings, scanCallback, handler);
        }

        public a(boolean z2, boolean z3, @NonNull List<ScanFilter> list, @NonNull ScanSettings scanSettings, @NonNull ScanCallback scanCallback, @NonNull Handler handler) {
            super(z2, z3, list, scanSettings, scanCallback, handler);
            this.f8449o = new C0416o(this);
        }
    }

    @Override // aisscanner.BluetoothLeScannerCompat
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void a(@NonNull List<ScanFilter> list, @NonNull ScanSettings scanSettings, @NonNull ScanCallback scanCallback, @NonNull Handler handler) {
        a aVar;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || defaultAdapter.getState() != 12) {
            return;
        }
        BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            throw new IllegalStateException("BT le scanner not available");
        }
        boolean zIsOffloadedScanBatchingSupported = defaultAdapter.isOffloadedScanBatchingSupported();
        boolean zIsOffloadedFilteringSupported = defaultAdapter.isOffloadedFilteringSupported();
        synchronized (this.f8448c) {
            if (this.f8448c.containsKey(scanCallback)) {
                throw new IllegalArgumentException("scanner already started with given callback");
            }
            aVar = new a(this, zIsOffloadedScanBatchingSupported, zIsOffloadedFilteringSupported, list, scanSettings, scanCallback, handler, null);
            this.f8448c.put(scanCallback, aVar);
        }
        runOnUiThread(new RunnableC0406j(this, bluetoothLeScanner, (!list.isEmpty() && zIsOffloadedFilteringSupported && scanSettings.getUseHardwareFilteringIfSupported()) ? a(list) : null, a(defaultAdapter, scanSettings), aVar));
    }

    @Override // aisscanner.BluetoothLeScannerCompat
    @RequiresPermission("android.permission.BLUETOOTH")
    public void flushPendingScanResults(@NonNull ScanCallback scanCallback) {
        a aVar;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || defaultAdapter.getState() != 12) {
            return;
        }
        if (scanCallback == null) {
            throw new IllegalArgumentException("callback cannot be null!");
        }
        synchronized (this.f8448c) {
            aVar = this.f8448c.get(scanCallback);
        }
        if (aVar == null) {
            throw new IllegalArgumentException("callback not registered!");
        }
        ScanSettings scanSettings = aVar.f1664g;
        if (!defaultAdapter.isOffloadedScanBatchingSupported() || !scanSettings.getUseHardwareBatchingIfSupported()) {
            aVar.b();
            return;
        }
        BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            return;
        }
        bluetoothLeScanner.flushPendingScanResults(aVar.f8449o);
    }

    @Override // aisscanner.BluetoothLeScannerCompat
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void stopScan(@NonNull ScanCallback scanCallback) {
        BluetoothLeScanner bluetoothLeScanner;
        if (scanCallback == null) {
            throw new IllegalArgumentException("callback cannot be null!");
        }
        synchronized (this.f8448c) {
            try {
                a aVar = this.f8448c.get(scanCallback);
                if (aVar == null) {
                    return;
                }
                this.f8448c.remove(scanCallback);
                aVar.a();
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (defaultAdapter == null || (bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner()) == null) {
                    return;
                }
                runOnUiThread(new RunnableC0408k(this, bluetoothLeScanner, aVar));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @NonNull
    public android.bluetooth.le.ScanSettings a(@NonNull BluetoothAdapter bluetoothAdapter, @NonNull ScanSettings scanSettings) {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        if (bluetoothAdapter.isOffloadedScanBatchingSupported() && scanSettings.getUseHardwareBatchingIfSupported()) {
            builder.setReportDelay(scanSettings.getReportDelayMillis());
        }
        builder.setScanMode(scanSettings.getScanMode());
        scanSettings.a();
        return builder.build();
    }

    @NonNull
    public List<android.bluetooth.le.ScanFilter> a(@NonNull List<ScanFilter> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<ScanFilter> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(a(it.next()));
        }
        return arrayList;
    }

    @NonNull
    public android.bluetooth.le.ScanFilter a(@NonNull ScanFilter scanFilter) {
        ScanFilter.Builder builder = new ScanFilter.Builder();
        builder.setDeviceAddress(scanFilter.getDeviceAddress()).setDeviceName(scanFilter.getDeviceName()).setServiceUuid(scanFilter.getServiceUuid(), scanFilter.getServiceUuidMask()).setManufacturerData(scanFilter.getManufacturerId(), scanFilter.getManufacturerData(), scanFilter.getManufacturerDataMask());
        if (scanFilter.getServiceDataUuid() != null) {
            builder.setServiceData(scanFilter.getServiceDataUuid(), scanFilter.getServiceData(), scanFilter.getServiceDataMask());
        }
        return builder.build();
    }

    @NonNull
    public ScanResult a(@NonNull android.bluetooth.le.ScanResult scanResult) {
        return new ScanResult(scanResult.getDevice(), ScanRecord.parseFromBytes(scanResult.getScanRecord() != null ? scanResult.getScanRecord().getBytes() : null), scanResult.getRssi(), scanResult.getTimestampNanos());
    }
}
