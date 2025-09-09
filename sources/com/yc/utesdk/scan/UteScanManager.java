package com.yc.utesdk.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class UteScanManager {
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private UteScanCallback mUteScanCallback;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private List<UteScanDevice> deviceList = new ArrayList();
    private Runnable scanRunnable = new utendo();
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new utenif();

    public class utendo implements Runnable {
        public utendo() {
        }

        @Override // java.lang.Runnable
        public void run() {
            UteScanManager.this.mScanning = false;
            UteScanManager.this.mBluetoothAdapter.stopLeScan(UteScanManager.this.mLeScanCallback);
            UteScanManager.this.onComplete();
            UteScanManager.this.mHandler.removeCallbacks(UteScanManager.this.scanRunnable);
        }
    }

    public class utenif implements BluetoothAdapter.LeScanCallback {
        public utenif() {
        }

        @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
        public void onLeScan(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
            if (bluetoothDevice == null) {
                return;
            }
            UteScanDevice uteScanDevice = new UteScanDevice(bluetoothDevice, i2, bArr);
            UteScanManager.this.addScanDevice(uteScanDevice);
            UteScanManager.this.mUteScanCallback.onScanning(uteScanDevice);
        }
    }

    public UteScanManager(BluetoothAdapter bluetoothAdapter) {
        this.mBluetoothAdapter = bluetoothAdapter;
    }

    public void addScanDevice(UteScanDevice uteScanDevice) {
        boolean z2 = false;
        for (int i2 = 0; i2 < this.deviceList.size(); i2++) {
            if (this.deviceList.get(i2).getDevice().getAddress().equals(uteScanDevice.getDevice().getAddress())) {
                this.deviceList.remove(i2);
                this.deviceList.add(i2, uteScanDevice);
                z2 = true;
            }
        }
        if (z2) {
            return;
        }
        this.deviceList.add(uteScanDevice);
    }

    public void onComplete() {
        UteScanCallback uteScanCallback = this.mUteScanCallback;
        if (uteScanCallback != null) {
            uteScanCallback.onScanComplete(this.deviceList);
        }
    }

    public boolean startScan(UteScanCallback uteScanCallback, long j2) {
        if (this.mScanning) {
            this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
            this.mScanning = false;
            this.mHandler.removeCallbacks(this.scanRunnable);
        }
        this.mUteScanCallback = uteScanCallback;
        this.deviceList.clear();
        this.mHandler.postDelayed(this.scanRunnable, j2);
        boolean zStartLeScan = this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
        this.mScanning = zStartLeScan;
        return zStartLeScan;
    }

    public void stopScan() {
        this.mScanning = false;
        this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
        this.mHandler.removeCallbacks(this.scanRunnable);
        onComplete();
    }
}
