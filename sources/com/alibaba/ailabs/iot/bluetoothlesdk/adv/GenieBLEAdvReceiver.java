package com.alibaba.ailabs.iot.bluetoothlesdk.adv;

import aisscanner.ScanCallback;
import aisscanner.ScanSettings;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice;
import com.alibaba.ailabs.iot.bluetoothlesdk.Utils;
import com.alibaba.ailabs.iot.bluetoothlesdk.d;
import com.alibaba.ailabs.iot.bluetoothlesdk.datasource.RequestManager;
import com.alibaba.ailabs.iot.iotmtopdatasource.bean.DeviceStatus;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSONObject;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import datasource.NetworkCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes2.dex */
public class GenieBLEAdvReceiver implements ILeScanCallback {
    private static final int MAX_COUNT_IN_TIME_WINDOW = 3;
    private static final int SIZE_RECENTLY_UPLOADED_ARRAY = 2;
    private static final int SIZE_TIME_WINDOW = 5000;
    public static final String TAG = "GenieBLEAdvReceiver";
    private static GenieBLEAdvReceiver mInstance;
    private BLEScannerProxy mBLEScannerProxy = null;
    private ScanCallback mScanCallback = null;
    private List<String> mWhitelist = new ArrayList();
    private boolean mStartFlag = false;
    private byte[][] mRecentlyUploadedDataArray = new byte[2][];
    private int mRecentlyUploadedDataArrayOffset = 0;
    private Map<String, LinkedList<byte[]>> mTimeSeriesUploadedData = new HashMap();
    private Queue<a> mToReportQueue = new LinkedList();
    private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

    private class a {

        /* renamed from: b, reason: collision with root package name */
        private byte[] f8642b;

        /* renamed from: c, reason: collision with root package name */
        private BluetoothDeviceWrapper f8643c;

        public a(byte[] bArr, BluetoothDeviceWrapper bluetoothDeviceWrapper) {
            this.f8642b = bArr;
            this.f8643c = bluetoothDeviceWrapper;
        }

        public byte[] a() {
            return this.f8642b;
        }

        public BluetoothDeviceWrapper b() {
            return this.f8643c;
        }
    }

    private GenieBLEAdvReceiver() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendToRecentlyUploadedDataArray(byte[] bArr) {
        byte[][] bArr2 = this.mRecentlyUploadedDataArray;
        int i2 = this.mRecentlyUploadedDataArrayOffset;
        bArr2[i2] = bArr;
        this.mRecentlyUploadedDataArrayOffset = (i2 + 1) % 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendToTimeWindowCache(String str, byte[] bArr, long j2) {
        if (TextUtils.isEmpty(str) || bArr == null) {
            return;
        }
        LinkedList<byte[]> linkedList = this.mTimeSeriesUploadedData.get(str);
        if (linkedList == null) {
            LinkedList<byte[]> linkedList2 = new LinkedList<>();
            linkedList2.addFirst(bArr);
            this.mTimeSeriesUploadedData.put(str, linkedList2);
            return;
        }
        LinkedList linkedList3 = new LinkedList();
        Iterator<byte[]> it = linkedList.iterator();
        while (it.hasNext()) {
            byte[] next = it.next();
            if (next == null || next.length < Utils.SIZE_LONG) {
                linkedList3.add(next);
            }
            if (j2 - Utils.bytesToLong(Arrays.copyOfRange(next, 0, Utils.SIZE_LONG)) > 5000) {
                linkedList3.add(next);
            }
        }
        linkedList.removeAll(linkedList3);
        if (linkedList.size() > 3) {
            linkedList.removeAll(linkedList.subList(0, linkedList.size() - 3));
        }
        byte[] bArr2 = new byte[Utils.SIZE_LONG + bArr.length];
        System.arraycopy(Utils.longToBytes(j2), 0, bArr2, 0, Utils.SIZE_LONG);
        System.arraycopy(bArr, 0, bArr2, Utils.SIZE_LONG, bArr.length);
        linkedList.add(bArr2);
        this.mTimeSeriesUploadedData.put(str, linkedList);
    }

    private boolean checkDataInRecentlyUploadedRecord(byte[] bArr) {
        for (int i2 = 0; i2 < 2; i2++) {
            if (Arrays.equals(this.mRecentlyUploadedDataArray[i2], bArr)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDataInTimeWindowCache(String str, byte[] bArr, long j2) {
        LinkedList<byte[]> linkedList = this.mTimeSeriesUploadedData.get(str);
        if (linkedList == null) {
            return false;
        }
        Iterator<byte[]> it = linkedList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            byte[] next = it.next();
            if (next != null && next.length > 8) {
                long jBytesToLong = Utils.bytesToLong(Arrays.copyOfRange(next, 0, 8));
                byte[] bArrCopyOfRange = Arrays.copyOfRange(next, 8, next.length);
                long j3 = j2 - jBytesToLong;
                if (j3 < 5000 && Arrays.equals(bArr, bArrCopyOfRange)) {
                    return true;
                }
                if (j3 < 5000) {
                    i2++;
                }
            }
        }
        return i2 >= 3;
    }

    public static GenieBLEAdvReceiver getInstance() {
        if (mInstance == null) {
            synchronized (GenieBLEAdvReceiver.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new GenieBLEAdvReceiver();
                    }
                } finally {
                }
            }
        }
        return mInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void next() {
        LogUtils.d(TAG, "Prepare report next data");
        if (this.mToReportQueue.isEmpty()) {
            return;
        }
        a aVarPeek = this.mToReportQueue.peek();
        reportDeviceStatus(aVarPeek.b(), aVarPeek.a(), null);
    }

    public void addWhitelist(String str) {
        if (BluetoothAdapter.checkBluetoothAddress(str)) {
            this.mWhitelist.add(str);
        } else {
            LogUtils.w(TAG, "Illegal device address");
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onAliBLEDeviceFound(final BluetoothDeviceWrapper bluetoothDeviceWrapper, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
        final byte[] manufacturerSpecificData;
        if (bluetoothDeviceWrapper instanceof GenieBLEAdvDevice) {
            if (this.mWhitelist.contains(bluetoothDeviceWrapper.getAddress()) && (manufacturerSpecificData = bluetoothDeviceWrapper.getScanRecord().getManufacturerSpecificData(424)) != null) {
                this.mMainThreadHandler.post(new Runnable() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.adv.GenieBLEAdvReceiver.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LogUtils.d(GenieBLEAdvReceiver.TAG, "Append new record");
                        GenieBLEAdvReceiver.this.mToReportQueue.add(GenieBLEAdvReceiver.this.new a(manufacturerSpecificData, bluetoothDeviceWrapper));
                        if (GenieBLEAdvReceiver.this.mToReportQueue.size() == 1) {
                            GenieBLEAdvReceiver.this.next();
                        }
                    }
                });
            }
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onStartScan() {
        LogUtils.d(TAG, "Scanning begins");
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onStopScan() {
        LogUtils.d(TAG, "End of scan");
        this.mStartFlag = false;
    }

    public void reportDeviceStatus(final BluetoothDeviceWrapper bluetoothDeviceWrapper, final byte[] bArr, IActionListener<Boolean> iActionListener) {
        final d dVar = new d(iActionListener, bluetoothDeviceWrapper, AgooConstants.MESSAGE_REPORT);
        if (bArr == null || bArr.length < 12) {
            LogUtils.w(TAG, "Illegal Payload: " + ConvertUtils.bytes2HexString(bArr));
            dVar.onFailure(-302, "Illegal Payload: " + ConvertUtils.bytes2HexString(bArr));
            this.mToReportQueue.remove();
            next();
            return;
        }
        if (checkDataInRecentlyUploadedRecord(bArr)) {
            LogUtils.w(TAG, "Conflict with recent history upload data collection, discard");
            this.mToReportQueue.remove();
            next();
            return;
        }
        final long jCurrentTimeMillis = System.currentTimeMillis();
        if (checkDataInTimeWindowCache(bluetoothDeviceWrapper.getAddress(), bArr, jCurrentTimeMillis)) {
            LogUtils.w(TAG, "Conflict with recent time window upload data collection, discard");
            this.mToReportQueue.remove();
            next();
            return;
        }
        String strBytes2HexString = ConvertUtils.bytes2HexString(Arrays.copyOfRange(bArr, 12, bArr.length));
        LogUtils.d(TAG, "Report status(opcode:, parameter:" + strBytes2HexString);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("opcode", (Object) "");
        jSONObject.put(PushConstants.PARAMS, (Object) strBytes2HexString);
        String jSONString = jSONObject.toJSONString();
        DeviceStatus deviceStatus = new DeviceStatus();
        String userId = RequestManager.getInstance().getUserId();
        deviceStatus.setDevId(bluetoothDeviceWrapper.getAddress());
        deviceStatus.setUserId(userId);
        deviceStatus.setStatus(jSONString);
        deviceStatus.setUuid("");
        RequestManager.getInstance().reportDevicesStatus("", Collections.singletonList(deviceStatus), new NetworkCallback<String>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.adv.GenieBLEAdvReceiver.2
            @Override // datasource.NetworkCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(String str) {
                LogUtils.d(GenieBLEAdvReceiver.TAG, "reportDevicesStatus request success");
                dVar.onSuccess(Boolean.TRUE);
                GenieBLEAdvReceiver.this.appendToRecentlyUploadedDataArray(bArr);
                GenieBLEAdvReceiver.this.appendToTimeWindowCache(bluetoothDeviceWrapper.getAddress(), bArr, jCurrentTimeMillis);
                GenieBLEAdvReceiver.this.mToReportQueue.remove();
                GenieBLEAdvReceiver.this.next();
            }

            @Override // datasource.NetworkCallback
            public void onFailure(String str, String str2) {
                LogUtils.e(GenieBLEAdvReceiver.TAG, "reportDevicesStatus request failed, errorMessage: " + str2);
                dVar.onFailure(-300, str2);
                GenieBLEAdvReceiver.this.appendToTimeWindowCache(bluetoothDeviceWrapper.getAddress(), bArr, jCurrentTimeMillis);
                GenieBLEAdvReceiver.this.mToReportQueue.remove();
                GenieBLEAdvReceiver.this.next();
            }
        });
    }

    public void startListen(Context context) {
        if (this.mStartFlag) {
            LogUtils.w(TAG, "Already listening");
            return;
        }
        LogUtils.d(TAG, "Start listen...");
        if (this.mBLEScannerProxy == null) {
            this.mBLEScannerProxy = BLEScannerProxy.getInstance();
        }
        ScanSettings scanSettingsBuild = new ScanSettings.Builder().setScanMode(0).setUseHardwareFilteringIfSupported(true).build();
        this.mStartFlag = true;
        this.mScanCallback = this.mBLEScannerProxy.startLeScan(context, 0, false, GenieBLEDevice.GENIE_BLE, this, scanSettingsBuild);
    }

    public void stopListen() {
        BLEScannerProxy bLEScannerProxy;
        this.mStartFlag = false;
        if (this.mScanCallback == null || (bLEScannerProxy = this.mBLEScannerProxy) == null) {
            return;
        }
        bLEScannerProxy.stopScan(this);
    }
}
