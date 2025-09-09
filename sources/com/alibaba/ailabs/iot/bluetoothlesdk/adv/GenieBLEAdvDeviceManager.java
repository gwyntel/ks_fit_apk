package com.alibaba.ailabs.iot.bluetoothlesdk.adv;

import aisscanner.ScanFilter;
import aisscanner.ScanResult;
import android.content.Context;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.alibaba.ailabs.iot.aisbase.Utils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.iot.aisbase.scanner.ILeScanStrategy;
import com.alibaba.ailabs.iot.aisbase.spec.AISManufacturerADData;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class GenieBLEAdvDeviceManager implements ILeScanStrategy {
    public static final int ALI_IOT_MANUFACTURE_ID = 424;
    private static final String TAG = "GenieBLEAdvDeviceManager";
    private static GenieBLEAdvDeviceManager mInstance;
    private BLEScannerProxy mBLEScanProxy;
    private ILeScanCallback mGetRemoteAnyoneLeScanCallback;
    private boolean mGetRemoteAnyoneDeviceSuccess = false;
    private boolean mGetRemoteSpecifiedPIDDeviceSuccess = false;
    private ILeScanCallback mGetRemoteSpecifiedPIDLeScanCallback = null;

    private GenieBLEAdvDeviceManager() {
        BLEScannerProxy bLEScannerProxy = BLEScannerProxy.getInstance();
        this.mBLEScanProxy = bLEScannerProxy;
        bLEScannerProxy.registerLeScanStrategy(GenieBLEAdvDevice.GENIE_BLE_ADV, this);
    }

    public static GenieBLEAdvDeviceManager getInstance() {
        if (mInstance == null) {
            synchronized (GenieBLEAdvDeviceManager.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new GenieBLEAdvDeviceManager();
                    }
                } finally {
                }
            }
        }
        return mInstance;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.scanner.ILeScanStrategy
    public BluetoothDeviceWrapper createFromScanResult(ScanResult scanResult) {
        byte[] manufacturerSpecificData;
        AISManufacturerADData fromBytes;
        if (scanResult == null || scanResult.getScanRecord() == null || (manufacturerSpecificData = scanResult.getScanRecord().getManufacturerSpecificData(424)) == null || (fromBytes = AISManufacturerADData.parseFromBytes(manufacturerSpecificData)) == null) {
            return null;
        }
        LogUtils.d(TAG, "Check subtype: " + Utils.byte2String(fromBytes.getBluetoothSubtype(), true));
        if (fromBytes.getBluetoothSubtype() != 9) {
            return null;
        }
        GenieBLEAdvDevice genieBLEAdvDevice = new GenieBLEAdvDevice(scanResult.getDevice());
        genieBLEAdvDevice.setAisManufactureDataADV(fromBytes);
        genieBLEAdvDevice.setScanResult(scanResult);
        return genieBLEAdvDevice;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.scanner.ILeScanStrategy
    public BluetoothDeviceSubtype getBluetoothDeviceSubtype() {
        return BluetoothDeviceSubtype.BEACON;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.scanner.ILeScanStrategy
    public List<ScanFilter> getCustomScanFilters() {
        ScanFilter scanFilterBuild = new ScanFilter.Builder().setServiceUuid(new ParcelUuid(Constants.AIS_PRIMARY_SERVICE)).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(scanFilterBuild);
        return arrayList;
    }

    public void getRemoteAnyoneGenieBLEDeviceWithScan(Context context, int i2, final IActionListener<GenieBLEAdvDevice> iActionListener) {
        LogUtils.d(TAG, "Get remote anyone genie BLE device");
        this.mGetRemoteAnyoneDeviceSuccess = false;
        BLEScannerProxy bLEScannerProxy = this.mBLEScanProxy;
        int i3 = GenieBLEAdvDevice.GENIE_BLE_ADV;
        ILeScanCallback iLeScanCallback = new ILeScanCallback() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.adv.GenieBLEAdvDeviceManager.1
            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onAliBLEDeviceFound(BluetoothDeviceWrapper bluetoothDeviceWrapper, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
                if (!GenieBLEAdvDeviceManager.this.mGetRemoteAnyoneDeviceSuccess && (bluetoothDeviceWrapper instanceof GenieBLEAdvDevice)) {
                    GenieBLEAdvDeviceManager.this.mGetRemoteAnyoneDeviceSuccess = true;
                    GenieBLEAdvDeviceManager.this.mBLEScanProxy.stopScan(this);
                    IActionListener iActionListener2 = iActionListener;
                    if (iActionListener2 != null) {
                        iActionListener2.onSuccess((GenieBLEAdvDevice) bluetoothDeviceWrapper);
                    }
                }
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onStartScan() {
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onStopScan() {
                IActionListener iActionListener2;
                GenieBLEAdvDeviceManager.this.mGetRemoteAnyoneLeScanCallback = null;
                if (GenieBLEAdvDeviceManager.this.mGetRemoteAnyoneDeviceSuccess || (iActionListener2 = iActionListener) == null) {
                    return;
                }
                iActionListener2.onFailure(-5, "Scanning device failed");
            }
        };
        this.mGetRemoteAnyoneLeScanCallback = iLeScanCallback;
        bLEScannerProxy.startLeScan(context, i2, true, i3, iLeScanCallback);
    }

    public void getRemoteSpecifiedPIDGenieBLEDeviceWithScan(Context context, final String str, int i2, final IActionListener<GenieBLEAdvDevice> iActionListener) {
        LogUtils.d(TAG, String.format("Get remote specified PID(%s) genie BLE device", str));
        this.mGetRemoteSpecifiedPIDDeviceSuccess = false;
        BLEScannerProxy bLEScannerProxy = this.mBLEScanProxy;
        int i3 = GenieBLEAdvDevice.GENIE_BLE_ADV;
        ILeScanCallback iLeScanCallback = new ILeScanCallback() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.adv.GenieBLEAdvDeviceManager.2
            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onAliBLEDeviceFound(BluetoothDeviceWrapper bluetoothDeviceWrapper, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
                if (!GenieBLEAdvDeviceManager.this.mGetRemoteSpecifiedPIDDeviceSuccess && (bluetoothDeviceWrapper instanceof GenieBLEAdvDevice)) {
                    if (TextUtils.isEmpty(str) || str.equals(bluetoothDeviceWrapper.getAisManufactureDataADV().getPidStr())) {
                        GenieBLEAdvDeviceManager.this.mGetRemoteSpecifiedPIDDeviceSuccess = true;
                        GenieBLEAdvDeviceManager.this.mBLEScanProxy.stopScan(this);
                        IActionListener iActionListener2 = iActionListener;
                        if (iActionListener2 != null) {
                            iActionListener2.onSuccess((GenieBLEAdvDevice) bluetoothDeviceWrapper);
                        }
                    }
                }
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onStartScan() {
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onStopScan() {
                IActionListener iActionListener2;
                GenieBLEAdvDeviceManager.this.mGetRemoteSpecifiedPIDLeScanCallback = null;
                if (GenieBLEAdvDeviceManager.this.mGetRemoteSpecifiedPIDDeviceSuccess || (iActionListener2 = iActionListener) == null) {
                    return;
                }
                iActionListener2.onFailure(-5, "Scanning device failed");
            }
        };
        this.mGetRemoteSpecifiedPIDLeScanCallback = iLeScanCallback;
        bLEScannerProxy.startLeScan(context, i2, true, i3, iLeScanCallback);
    }

    public void stopGetRemoteAnyoneGenieBLEDeviceWithScan() {
        ILeScanCallback iLeScanCallback = this.mGetRemoteAnyoneLeScanCallback;
        if (iLeScanCallback != null) {
            this.mBLEScanProxy.stopScan(iLeScanCallback);
        }
    }

    public void stopGetRemoteSpecifiedPIDGenieBLEDeviceWithScan() {
        ILeScanCallback iLeScanCallback = this.mGetRemoteSpecifiedPIDLeScanCallback;
        if (iLeScanCallback != null) {
            this.mBLEScanProxy.stopScan(iLeScanCallback);
        }
    }
}
