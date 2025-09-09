package com.yc.utesdk.ble.close;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.google.common.base.Ascii;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.UUIDUtils;
import com.yc.utesdk.data.D1CommandProcessing;
import com.yc.utesdk.data.MessageRemindProcessing;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.ota.FrkBleOTAUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.crypto.NoSuchPaddingException;
import javax.jmdns.impl.constants.DNSRecordClass;

/* loaded from: classes4.dex */
public class UteBluetoothGattCallback extends BluetoothGattCallback {
    private static UteBluetoothGattCallback instance;
    public BluetoothGatt mBluetoothGatt;
    private int discoverFailCount = 0;
    private int startDiscoverCount = 0;
    private long startConnectTimeMillis = 0;
    private final int READY_START_DISCOVER_SERVICE_MSG = 0;
    private final int START_DISCOVER_SERVICE_FAIL_MSG = 2;
    private final int DISCOVER_SERVICE_SUCCESS_MSG = 3;
    private final int DISCOVER_SERVICE_FAIL_MSG = 4;
    private final int DISCOVER_SERVICE_TIMEOUT_MSG = 5;
    private boolean isDiscoveryServerAutoConnect = false;
    private final Handler mHandler = new utendo(Looper.getMainLooper());
    private DeviceBusyLockUtils mDeviceBusyLockUtils = DeviceBusyLockUtils.getInstance();

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            String lastConnectDeviceAddress;
            StringBuilder sb;
            String str;
            super.handleMessage(message);
            int i2 = message.what;
            if (i2 == 0) {
                LogUtils.i("开启发现服务");
                if (!UteBluetoothGattCallback.this.utendo()) {
                    UteBluetoothGattCallback.this.mHandler.sendEmptyMessage(2);
                    return;
                } else {
                    LogUtils.i("开启发现服务成功");
                    UteBluetoothGattCallback.this.mHandler.sendEmptyMessageDelayed(5, 4000L);
                    return;
                }
            }
            if (i2 == 2) {
                UteBluetoothGattCallback.utenint(UteBluetoothGattCallback.this);
                LogUtils.i("开启发现服务失败，startDiscoverCount =" + UteBluetoothGattCallback.this.startDiscoverCount);
                if (UteBluetoothGattCallback.this.startDiscoverCount < 3) {
                    UteBluetoothGattCallback.this.mHandler.sendEmptyMessageDelayed(0, 1000L);
                    return;
                }
                UteBleClient.getUteBleClient().disconnect();
                lastConnectDeviceAddress = SPUtil.getInstance().getLastConnectDeviceAddress();
                if (lastConnectDeviceAddress.equals("00:00:00:00:00:00") || !UteBluetoothGattCallback.this.isDiscoveryServerAutoConnect) {
                    return;
                }
            } else {
                if (i2 == 3) {
                    LogUtils.i("发现服务成功 ：" + (System.currentTimeMillis() - UteBluetoothGattCallback.this.startConnectTimeMillis));
                    if (UteBluetoothGattCallback.this.mBluetoothGatt != null) {
                        NotifyUtils.getInstance().displayGattServices(UteBluetoothGattCallback.this.mBluetoothGatt);
                        return;
                    }
                    return;
                }
                if (i2 != 4 && i2 != 5) {
                    return;
                }
                UteBluetoothGattCallback.utencase(UteBluetoothGattCallback.this);
                if (message.what == 4) {
                    sb = new StringBuilder();
                    str = "发现服务失败，discoverFailCount =";
                } else {
                    sb = new StringBuilder();
                    str = "发现服务失败，超时 discoverFailCount =";
                }
                sb.append(str);
                sb.append(UteBluetoothGattCallback.this.discoverFailCount);
                LogUtils.i(sb.toString());
                if (UteBluetoothGattCallback.this.discoverFailCount < 3) {
                    UteBluetoothGattCallback.this.startDiscoverCount = 0;
                    UteBluetoothGattCallback.this.mHandler.sendEmptyMessage(0);
                    return;
                } else {
                    UteBleClient.getUteBleClient().disconnect();
                    lastConnectDeviceAddress = SPUtil.getInstance().getLastConnectDeviceAddress();
                    if (lastConnectDeviceAddress.equals("00:00:00:00:00:00") || !UteBluetoothGattCallback.this.isDiscoveryServerAutoConnect) {
                        return;
                    }
                }
            }
            UteBleClient.getUteBleClient().connect(lastConnectDeviceAddress);
        }
    }

    public UteBluetoothGattCallback() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        utenif();
    }

    public static synchronized UteBluetoothGattCallback getInstance() {
        try {
            if (instance == null) {
                instance = new UteBluetoothGattCallback();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public static /* synthetic */ int utencase(UteBluetoothGattCallback uteBluetoothGattCallback) {
        int i2 = uteBluetoothGattCallback.discoverFailCount;
        uteBluetoothGattCallback.discoverFailCount = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int utenint(UteBluetoothGattCallback uteBluetoothGattCallback) {
        int i2 = uteBluetoothGattCallback.startDiscoverCount;
        uteBluetoothGattCallback.startDiscoverCount = i2 + 1;
        return i2;
    }

    public BluetoothGatt getBluetoothGatt() {
        return this.mBluetoothGatt;
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        ArrayList arrayList = new ArrayList();
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null) {
            return arrayList;
        }
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        LogUtils.i("发现服务  getSupportedGattServices mBluetoothGatt =" + this.mBluetoothGatt);
        return services;
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, IOException, ParseException, InvalidAlgorithmParameterException {
        UteListenerManager uteListenerManager;
        int i2;
        super.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
        byte[] value = bluetoothGattCharacteristic.getValue();
        BluetoothGatt bluetoothGatt2 = this.mBluetoothGatt;
        if (bluetoothGatt2 == null || bluetoothGatt2 != bluetoothGatt) {
            LogUtils.e("onCharacteristicChanged gatt 不一致 =" + bluetoothGatt + ",mBluetoothGatt=" + this.mBluetoothGatt);
            return;
        }
        String strBytes2HexString = (value == null || value.length <= 0) ? null : GBUtils.getInstance().bytes2HexString(value);
        if (strBytes2HexString == null) {
            return;
        }
        if (bluetoothGattCharacteristic.getUuid().equals(UUID.fromString(UUIDUtils.ONLY_READ_UUID))) {
            LogUtils.i("BLE--->APK4 = " + strBytes2HexString);
            UteDeviceDataAnalysis.getInstance().datePacketOperate(strBytes2HexString, value);
            return;
        }
        if (bluetoothGattCharacteristic.getUuid().equals(UUID.fromString(UUIDUtils.ONLY_READ_UUID_5))) {
            if (TextUtils.isEmpty(strBytes2HexString)) {
                return;
            }
            LogUtils.i("BLE--->APK5 = " + strBytes2HexString);
            UteDeviceDataAnalysis.getInstance().datePacketOperate34F2(strBytes2HexString, value);
            return;
        }
        if (bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.ONLY_READ_UUID_60)) {
            LogUtils.i("BLE--->APK60 = " + strBytes2HexString);
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 60;
        } else {
            if (!bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.ONLY_READ_UUID_61)) {
                if (bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.BLE_UUID_SERVICE_READ_ACTS) || bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.ONLY_READ_UUID_IFLY)) {
                    UteListenerManager.getInstance().onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
                    return;
                }
                if (bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.UUID_FRK_READ_DATA)) {
                    LogUtils.i("BLE--->APKFRK = " + strBytes2HexString);
                    if (TextUtils.isEmpty(strBytes2HexString)) {
                        return;
                    }
                    FrkBleOTAUtils.getInstance().dealWithFrkData(strBytes2HexString, value);
                    return;
                }
                return;
            }
            LogUtils.i("BLE--->APK61 = " + strBytes2HexString);
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 61;
        }
        uteListenerManager.onSuloseChanged(i2, value);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
        String strBytes2HexString;
        int length;
        String strValueOf;
        super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i2);
        synchronized (this.mDeviceBusyLockUtils.getDeviceBusyLock()) {
            this.mDeviceBusyLockUtils.setDeviceBusy(false);
        }
        LogUtils.w("onCharacteristicRead status =" + i2);
        byte[] value = bluetoothGattCharacteristic.getValue();
        if (value == null || value.length <= 0) {
            strBytes2HexString = null;
        } else {
            strBytes2HexString = GBUtils.getInstance().bytes2HexString(value);
            LogUtils.w("------------->onCharacteristicRead : " + strBytes2HexString);
        }
        if (i2 != 0) {
            if (bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.OTA_READ_PATCH_CHARACTERISTIC_UUID)) {
                LogUtils.i("read patch version 2 ");
                NotifyUtils.getInstance().sendMessageForNotifyCommand(false);
                return;
            } else {
                NotifyUtils.getInstance().resetNotifyProcess();
                LogUtils.i("disconnect() onCharacteristicRead");
                UteBleClient.getUteBleClient().disconnect();
                return;
            }
        }
        if (bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.OTA_READ_PATCH_CHARACTERISTIC_UUID)) {
            byte[] value2 = bluetoothGattCharacteristic.getValue();
            if (value2.length == 4) {
                strValueOf = utendo(value2);
            } else {
                ByteBuffer byteBufferWrap = ByteBuffer.wrap(value2);
                byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
                strValueOf = String.valueOf((int) byteBufferWrap.getShort(0));
            }
            SPUtil.getInstance().setDevicePatchVersion(strValueOf);
            LogUtils.i("gsj--old patch version: " + strValueOf);
            NotifyUtils.getInstance().sendMessageForNotifyCommand(false);
        }
        if (bluetoothGattCharacteristic.getUuid().equals(UUID.fromString(UUIDUtils.ONLY_WRITE_UUID)) && strBytes2HexString != null) {
            int length2 = strBytes2HexString.length();
            if (length2 == 40) {
                int i3 = length2 - 6;
                String strSubstring = strBytes2HexString.substring(i3, length2);
                int i4 = length2 - 12;
                String strSubstring2 = strBytes2HexString.substring(i4, i3);
                int i5 = length2 - 18;
                String strSubstring3 = strBytes2HexString.substring(i5, i4);
                int i6 = length2 - 24;
                String strSubstring4 = strBytes2HexString.substring(i6, i5);
                int i7 = length2 - 30;
                String strSubstring5 = strBytes2HexString.substring(i7, i6);
                String strSubstring6 = strBytes2HexString.substring(length2 - 36, i7);
                String strSubstring7 = strBytes2HexString.substring(0, 4);
                int iHexStringToAlgorism = GBUtils.getInstance().hexStringToAlgorism(strSubstring);
                int iHexStringToAlgorism2 = GBUtils.getInstance().hexStringToAlgorism(strSubstring2);
                int iHexStringToAlgorism3 = GBUtils.getInstance().hexStringToAlgorism(strSubstring3);
                int iHexStringToAlgorism4 = GBUtils.getInstance().hexStringToAlgorism(strSubstring4);
                int iHexStringToAlgorism5 = GBUtils.getInstance().hexStringToAlgorism(strSubstring5);
                int iHexStringToAlgorism6 = GBUtils.getInstance().hexStringToAlgorism(strSubstring6);
                int iHexStringToAlgorism7 = GBUtils.getInstance().hexStringToAlgorism(strSubstring7);
                SPUtil.getInstance().setCharacterisicFunctionList1(iHexStringToAlgorism);
                SPUtil.getInstance().setCharacterisicFunctionList2(iHexStringToAlgorism2);
                SPUtil.getInstance().setCharacterisicFunctionList3(iHexStringToAlgorism3);
                SPUtil.getInstance().setCharacterisicFunctionList4(iHexStringToAlgorism4);
                SPUtil.getInstance().setCharacterisicFunctionList5(iHexStringToAlgorism5);
                SPUtil.getInstance().setCharacterisicFunctionList6(iHexStringToAlgorism6);
                SPUtil.getInstance().setCharacterisicFunctionList7(iHexStringToAlgorism7);
            }
            NotifyUtils.getInstance().sendMessageForNotifyCommand(false);
        }
        if (bluetoothGattCharacteristic.getUuid().equals(UUID.fromString(UUIDUtils.ONLY_WRITE_UUID_5))) {
            LogUtils.i(" 特征值返回555,dataString =" + strBytes2HexString);
            if (value != null && value.length > 1) {
                int i8 = ((value[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (value[1] & 255);
                SPUtil.getInstance().setMaxCommunicationLength(i8);
                LogUtils.i(" 特征值返回555,phoneMtu =" + i8);
                NotifyUtils.getInstance().sendMessageForNotifyCommand(false);
            }
            if (strBytes2HexString == null || (length = strBytes2HexString.length()) != 40) {
                return;
            }
            int i9 = length - 6;
            String strSubstring8 = strBytes2HexString.substring(i9, length);
            int i10 = length - 12;
            String strSubstring9 = strBytes2HexString.substring(i10, i9);
            int i11 = length - 18;
            String strSubstring10 = strBytes2HexString.substring(i11, i10);
            int i12 = length - 24;
            String strSubstring11 = strBytes2HexString.substring(i12, i11);
            int i13 = length - 30;
            String strSubstring12 = strBytes2HexString.substring(i13, i12);
            String strSubstring13 = strBytes2HexString.substring(length - 36, i13);
            int iHexStringToAlgorism8 = GBUtils.getInstance().hexStringToAlgorism(strSubstring8);
            int iHexStringToAlgorism9 = GBUtils.getInstance().hexStringToAlgorism(strSubstring9);
            int iHexStringToAlgorism10 = GBUtils.getInstance().hexStringToAlgorism(strSubstring10);
            int iHexStringToAlgorism11 = GBUtils.getInstance().hexStringToAlgorism(strSubstring11);
            int iHexStringToAlgorism12 = GBUtils.getInstance().hexStringToAlgorism(strSubstring12);
            int iHexStringToAlgorism13 = GBUtils.getInstance().hexStringToAlgorism(strSubstring13);
            SPUtil.getInstance().setCharacterisicFunctionList8(iHexStringToAlgorism8);
            SPUtil.getInstance().setCharacterisicFunctionList9(iHexStringToAlgorism9);
            SPUtil.getInstance().setCharacterisicFunctionList10(iHexStringToAlgorism10);
            SPUtil.getInstance().setCharacterisicFunctionList11(iHexStringToAlgorism11);
            SPUtil.getInstance().setCharacterisicFunctionList12(iHexStringToAlgorism12);
            SPUtil.getInstance().setCharacterisicFunctionList13(iHexStringToAlgorism13);
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
        UteListenerManager uteListenerManager;
        int i3;
        super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i2);
        synchronized (this.mDeviceBusyLockUtils.getDeviceBusyLock()) {
            this.mDeviceBusyLockUtils.setDeviceBusy(false);
        }
        LogUtils.w("onCharacteristicWrite status =" + i2 + ",Uuid = " + bluetoothGattCharacteristic.getUuid());
        byte[] value = bluetoothGattCharacteristic.getValue();
        if (value != null && value.length > 0) {
            utendo(i2 == 0, GBUtils.getInstance().bytes2HexString(value), value, bluetoothGattCharacteristic.getUuid());
        }
        UteListenerManager.getInstance().onSimpleCallback(i2 == 0, 0);
        if (bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.ONLY_WRITE_UUID_60)) {
            uteListenerManager = UteListenerManager.getInstance();
            i3 = 60;
        } else {
            if (!bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.ONLY_WRITE_UUID_61)) {
                if (bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.BLE_UUID_SERVICE_WRITE_ACTS)) {
                    UteListenerManager.getInstance().onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i2);
                    return;
                }
                return;
            }
            uteListenerManager = UteListenerManager.getInstance();
            i3 = 61;
        }
        uteListenerManager.onSuloseWrite(i3, i2);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i2, int i3) {
        super.onConnectionStateChange(bluetoothGatt, i2, i3);
        synchronized (this.mDeviceBusyLockUtils.getDeviceBusyLock()) {
            this.mDeviceBusyLockUtils.setDeviceBusy(false);
        }
        LogUtils.i("onConnectionStateChange status =" + i2 + ",newState =" + i3);
        this.mHandler.removeMessages(5);
        if (i2 == 0) {
            if (i3 == 2) {
                this.discoverFailCount = 0;
                this.startDiscoverCount = 0;
                LogUtils.i("准备开启发现服务");
                this.startConnectTimeMillis = System.currentTimeMillis();
                this.mHandler.removeMessages(0);
                this.mHandler.sendEmptyMessageDelayed(0, 1600L);
                return;
            }
            if (i3 != 0) {
                return;
            }
        }
        bluetoothGatt.close();
        utendo(0);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i2) {
        super.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i2);
        synchronized (this.mDeviceBusyLockUtils.getDeviceBusyLock()) {
            this.mDeviceBusyLockUtils.setDeviceBusy(false);
        }
        LogUtils.w("onDescriptorRead status =" + i2);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i2) {
        super.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i2);
        synchronized (this.mDeviceBusyLockUtils.getDeviceBusyLock()) {
            this.mDeviceBusyLockUtils.setDeviceBusy(false);
        }
        LogUtils.w("onDescriptorWrite status =" + i2);
        if (i2 == 0) {
            if (NotifyUtils.getInstance().isConnectNotify()) {
                NotifyUtils.getInstance().sendMessageForNotifyCommand(false);
            }
        } else {
            NotifyUtils.getInstance().resetNotifyProcess();
            LogUtils.i("设置通知返回失败  gatt.close  gatt=" + bluetoothGatt);
            UteBleClient.getUteBleClient().disconnect();
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onMtuChanged(BluetoothGatt bluetoothGatt, int i2, int i3) {
        int i4;
        super.onMtuChanged(bluetoothGatt, i2, i3);
        LogUtils.i("onMtuChanged status =" + i3 + ",mtu =" + i2);
        if (i3 == 0 && i2 - 3 < SPUtil.getInstance().getMaxCommunicationLength()) {
            SPUtil.getInstance().setMaxCommunicationLength(i4);
        }
        if (NotifyUtils.getInstance().isConnectNotify()) {
            NotifyUtils.getInstance().sendMessageForNotifyCommand(false);
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i2, int i3) {
        super.onReadRemoteRssi(bluetoothGatt, i2, i3);
        LogUtils.i("onReadRemoteRssi rssi =" + i2);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i2) {
        super.onReliableWriteCompleted(bluetoothGatt, i2);
        synchronized (this.mDeviceBusyLockUtils.getDeviceBusyLock()) {
            this.mDeviceBusyLockUtils.setDeviceBusy(false);
        }
        LogUtils.w("onReliableWriteCompleted status =" + i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00ca  */
    @Override // android.bluetooth.BluetoothGattCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onServicesDiscovered(android.bluetooth.BluetoothGatt r4, int r5) {
        /*
            r3 = this;
            super.onServicesDiscovered(r4, r5)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "onServicesDiscovered status ="
            r0.append(r1)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            com.yc.utesdk.log.LogUtils.i(r0)
            android.os.Handler r0 = r3.mHandler
            r1 = 5
            r0.removeMessages(r1)
            r3.mBluetoothGatt = r4
            if (r5 != 0) goto Lca
            com.yc.utesdk.ble.open.UteBleClient r5 = com.yc.utesdk.ble.open.UteBleClient.getUteBleClient()
            r0 = 2
            r5.setDevicePlatform(r0)
            r3.mBluetoothGatt = r4
            com.yc.utesdk.utils.open.SPUtil r5 = com.yc.utesdk.utils.open.SPUtil.getInstance()
            java.lang.String r5 = r5.getLastConnectDeviceAddress()
            android.bluetooth.BluetoothDevice r4 = r4.getDevice()
            java.lang.String r4 = r4.getAddress()
            boolean r5 = r5.equals(r4)
            r0 = 0
            if (r5 != 0) goto L74
            com.yc.utesdk.utils.open.SPUtil r5 = com.yc.utesdk.utils.open.SPUtil.getInstance()
            r5.setLastConnectDeviceAddress(r4)
            com.yc.utesdk.utils.open.SPUtil r4 = com.yc.utesdk.utils.open.SPUtil.getInstance()
            r4.setPushMessageDisplay1(r0)
            com.yc.utesdk.utils.open.SPUtil r4 = com.yc.utesdk.utils.open.SPUtil.getInstance()
            r4.setPushMessageDisplay2(r0)
            com.yc.utesdk.utils.open.SPUtil r4 = com.yc.utesdk.utils.open.SPUtil.getInstance()
            r4.setPushMessageDisplay3(r0)
            com.yc.utesdk.utils.open.SPUtil r4 = com.yc.utesdk.utils.open.SPUtil.getInstance()
            r4.clearFunctionList()
            com.yc.utesdk.utils.open.SPUtil r4 = com.yc.utesdk.utils.open.SPUtil.getInstance()
            r4.setWatchFaceConfigurationSuccess(r0)
            com.yc.utesdk.utils.open.SPUtil r4 = com.yc.utesdk.utils.open.SPUtil.getInstance()
            r5 = 1
            r4.setNeedToRegenerateRandom63E(r5)
        L74:
            java.util.List r4 = r3.getSupportedGattServices()
            if (r4 == 0) goto Lca
            int r5 = r4.size()
            if (r5 <= 0) goto Lca
            int r5 = r4.size()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "发现服务  servicesSize ="
            r1.append(r2)
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            com.yc.utesdk.log.LogUtils.i(r5)
            r5 = r0
        L99:
            int r1 = r4.size()
            if (r5 >= r1) goto Lc3
            java.lang.Object r1 = r4.get(r5)
            android.bluetooth.BluetoothGattService r1 = (android.bluetooth.BluetoothGattService) r1
            java.util.UUID r1 = r1.getUuid()
            java.lang.String r1 = r1.toString()
            java.util.UUID r2 = com.yc.utesdk.command.UUIDUtils.OTA_SERVICE_UUID
            java.lang.String r2 = r2.toString()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto Lc0
            com.yc.utesdk.ble.open.UteBleClient r1 = com.yc.utesdk.ble.open.UteBleClient.getUteBleClient()
            r1.setDevicePlatform(r0)
        Lc0:
            int r5 = r5 + 1
            goto L99
        Lc3:
            android.os.Handler r4 = r3.mHandler
            r5 = 3
        Lc6:
            r4.sendEmptyMessage(r5)
            goto Lce
        Lca:
            android.os.Handler r4 = r3.mHandler
            r5 = 4
            goto Lc6
        Lce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.ble.close.UteBluetoothGattCallback.onServicesDiscovered(android.bluetooth.BluetoothGatt, int):void");
    }

    public void removeHandler() {
        LogUtils.i("removeHandler");
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
        this.mBluetoothGatt = bluetoothGatt;
    }

    public void setDiscoveryServerAutoConnect(boolean z2) {
        this.isDiscoveryServerAutoConnect = z2;
    }

    public final void utenif() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        LogUtils.e("手机信息：" + ("manufacturer =" + Build.MANUFACTURER + ",model =" + Build.MODEL + ",Android " + Build.VERSION.RELEASE + ",phone sdk version =" + Build.VERSION.SDK_INT + ",targetSdk =" + UteBleClient.getContext().getApplicationInfo().targetSdkVersion));
        LogUtils.e("Sdk版本：uteAndroidSdk_v5.3.1.aar");
        LogUtils.e("lastBleVersion = " + SPUtil.getInstance().getDeviceFirmwareVersion());
    }

    public final String utendo(byte[] bArr) {
        StringBuilder sb;
        int i2 = bArr[0] & 255;
        int i3 = (bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
        int i4 = ((bArr[3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | i2 | i3 | ((bArr[2] << 16) & 16711680);
        int i5 = (i4 >> 12) & DNSRecordClass.CLASS_MASK;
        int i6 = (i4 >> 27) & 31;
        if (i6 > 9) {
            sb = new StringBuilder();
            sb.append(i5);
            sb.append(String.valueOf(i6));
        } else {
            sb = new StringBuilder();
            sb.append(i5);
            sb.append("0");
            sb.append(i6);
        }
        return sb.toString();
    }

    public final boolean utendo() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null) {
            return false;
        }
        boolean zDiscoverServices = bluetoothGatt.discoverServices();
        LogUtils.i("开启发现服务 isStartDiscover =" + zDiscoverServices);
        return zDiscoverServices;
    }

    public final void utendo(boolean z2, String str, byte[] bArr, UUID uuid) {
        if (uuid.equals(UUID.fromString(UUIDUtils.ONLY_WRITE_UUID_5)) || uuid.equals(UUID.fromString(UUIDUtils.ONLY_WRITE_UUID))) {
            if (str.startsWith("2401")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onTemperatureStatus(z2, 1);
                return;
            }
            if (str.startsWith("C4")) {
                int i2 = bArr[1] & 255;
                if (i2 == 1) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    UteListenerManager.getInstance().onDeviceCameraStatus(z2, 2);
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    UteListenerManager.getInstance().onDeviceCameraStatus(z2, 1);
                    return;
                }
            }
            if (str.startsWith("D1")) {
                int i3 = bArr[1] & 255;
                if (i3 == 13 || i3 == 14) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    UteListenerManager.getInstance().onDeviceMusicStatus(true, 3);
                    return;
                }
                return;
            }
            if (str.startsWith("D3")) {
                if (DeviceMode.isHasFunction_10(256)) {
                    return;
                }
                D1CommandProcessing.getInstance().dealWithD3Command(bArr);
                return;
            }
            if (str.startsWith("D7")) {
                if (DeviceMode.isHasFunction_10(256)) {
                    return;
                }
                D1CommandProcessing.getInstance().dealWithD7Command(bArr);
                return;
            }
            if (str.startsWith("45")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                MessageRemindProcessing.getInstance().dealWithPhoneNumber(bArr);
                return;
            }
            if (str.startsWith("FD0155")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onSportsLocation(true, 9);
            } else if (str.startsWith("AD")) {
                UteListenerManager.getInstance().onSimpleCallback(z2, 38);
            } else if (str.startsWith("442001")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onContinuousPpgStatus(true, 3);
            }
        }
    }

    public final void utendo(int i2) {
        UteListenerManager.getInstance().onConnecteStateChange(i2);
    }
}
