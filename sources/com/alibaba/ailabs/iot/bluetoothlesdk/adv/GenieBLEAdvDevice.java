package com.alibaba.ailabs.iot.bluetoothlesdk.adv;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.alibaba.ailabs.iot.aisbase.Utils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.spec.AISManufacturerADData;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice;
import com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDeviceManager;
import com.alibaba.ailabs.iot.bluetoothlesdk.d;
import com.alibaba.ailabs.iot.bluetoothlesdk.datasource.RequestManager;
import com.alibaba.ailabs.iot.iotmtopdatasource.bean.IotDevice;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSON;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import datasource.NetworkCallback;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class GenieBLEAdvDevice extends GenieBLEDevice {
    public static int GENIE_BLE_ADV = 32;
    private static final String TAG = "GenieBLEAdvDevice";
    private Handler mHandler;

    public GenieBLEAdvDevice(BluetoothDevice bluetoothDevice) {
        super(bluetoothDevice);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public static GenieBLEAdvDevice getGenieBLEAdvDevice(String str, String str2) {
        GenieBLEAdvDevice genieBLEAdvDevice = new GenieBLEAdvDevice(str2);
        byte[] bArr = new byte[14];
        bArr[0] = -88;
        bArr[1] = 1;
        bArr[2] = -107;
        bArr[3] = 0;
        byte[] bArrInt2ByteArrayByLittleEndian = Utils.int2ByteArrayByLittleEndian(Integer.parseInt(str));
        bArr[4] = bArrInt2ByteArrayByLittleEndian[0];
        bArr[5] = bArrInt2ByteArrayByLittleEndian[1];
        String[] strArrSplit = str2.split(":");
        Byte[] bArr2 = new Byte[6];
        for (int i2 = 0; i2 < 6; i2++) {
            bArr2[i2] = Byte.valueOf(Integer.valueOf(Integer.parseInt(strArrSplit[i2], 16)).byteValue());
        }
        for (int i3 = 0; i3 < 6; i3++) {
            bArr[11 - i3] = bArr2[i3].byteValue();
        }
        genieBLEAdvDevice.setAisManufactureDataADV(AISManufacturerADData.parseFromBytes(bArr));
        return genieBLEAdvDevice;
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice
    public void bindDevice(Context context, IActionListener<Boolean> iActionListener) {
        String str = TAG;
        LogUtils.d(str, "BindDevice...");
        final d dVar = new d(iActionListener, this, "bind");
        IotDevice iotDevice = new IotDevice();
        iotDevice.setPlatform("BLENIADV");
        iotDevice.setSource(PushConstants.EXTRA_APPLICATION_PENDING_INTENT);
        iotDevice.setDevId(getAddress());
        iotDevice.setProductKey(getAisManufactureDataADV().getPidStr());
        if (getScanRecord() == null || getScanRecord().getManufacturerSpecificData(424) == null) {
            LogUtils.e(str, "Empty manufacturer data");
            dVar.onFailure(-201, "Empty manufacturer data");
            return;
        }
        byte[] manufacturerSpecificData = getScanRecord().getManufacturerSpecificData(424);
        if (manufacturerSpecificData.length < 10) {
            LogUtils.e(str, "Invaild manufacture data");
            dVar.onFailure(-201, "Not enough data length");
            return;
        }
        iotDevice.setIdentifySign(ConvertUtils.bytes2HexString(Arrays.copyOfRange(manufacturerSpecificData, 12, manufacturerSpecificData.length)));
        iotDevice.setUserId(RequestManager.getInstance().getUserId());
        iotDevice.setUuid(RequestManager.getInstance().getUtdId());
        ArrayList arrayList = new ArrayList();
        arrayList.add(iotDevice);
        RequestManager.getInstance().getInfoByAuthInfo("iot", "bindBLEDevice", JSON.toJSONString(arrayList), new NetworkCallback<String>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.adv.GenieBLEAdvDevice.2
            @Override // datasource.NetworkCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(String str2) {
                LogUtils.d(GenieBLEAdvDevice.TAG, "request bind BLE device success");
                dVar.onSuccess(Boolean.TRUE);
            }

            @Override // datasource.NetworkCallback
            public void onFailure(String str2, String str3) {
                LogUtils.e(GenieBLEAdvDevice.TAG, "request bind BLE device failed, errorMessage: " + str3);
                dVar.onFailure(-300, str3);
            }
        });
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice, com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public /* bridge */ /* synthetic */ BluetoothDeviceWrapper connect(Context context, IActionListener iActionListener) {
        return connect(context, (IActionListener<BluetoothDevice>) iActionListener);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public void disconnect(IActionListener<BluetoothDevice> iActionListener) {
        LogUtils.d(TAG, "Disconnect...");
        GenieBLEAdvReceiver.getInstance().stopListen();
        if (iActionListener != null) {
            iActionListener.onSuccess(getBluetoothDevice());
        }
        GenieBLEDeviceManager.recycleGmaBluetoothDevice(this);
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice, com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public String getAddress() {
        String macAddress = Utils.formatMacAddress(getAisManufactureDataADV().getMacAddress());
        LogUtils.d(TAG, "Get address: " + macAddress);
        return macAddress;
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice
    public void unbindDevice(Context context, IActionListener<Boolean> iActionListener) {
        super.unbindDevice(context, iActionListener);
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice, com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public GenieBLEDevice connect(final Context context, IActionListener<BluetoothDevice> iActionListener) {
        LogUtils.d(TAG, "Connect...");
        final GenieBLEAdvReceiver genieBLEAdvReceiver = GenieBLEAdvReceiver.getInstance();
        genieBLEAdvReceiver.addWhitelist(getAddress());
        this.mHandler.postDelayed(new Runnable() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.adv.GenieBLEAdvDevice.1
            @Override // java.lang.Runnable
            public void run() {
                genieBLEAdvReceiver.startListen(context.getApplicationContext());
            }
        }, 1000L);
        if (iActionListener != null) {
            iActionListener.onSuccess(getBluetoothDevice());
        }
        GenieBLEDeviceManager.cacheBLEDevice(this);
        return this;
    }

    public GenieBLEAdvDevice(String str) {
        super(str);
        this.mHandler = new Handler(Looper.getMainLooper());
    }
}
