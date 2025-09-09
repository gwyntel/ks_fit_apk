package com.alibaba.ailabs.iot.bluetoothlesdk;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.MainThread;
import anetwork.channel.util.RequestConstant;
import com.alibaba.ailabs.iot.aisbase.AuthInfoListener;
import com.alibaba.ailabs.iot.aisbase.OTAUTLogDecorator;
import com.alibaba.ailabs.iot.aisbase.UTLogUtils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.IDetailActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ITransmissionLayerCallback;
import com.alibaba.ailabs.iot.aisbase.channel.LayerState;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayerManagerBase;
import com.alibaba.ailabs.iot.aisbase.exception.UnsupportedPluginTypeException;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTADownloadHelper;
import com.alibaba.ailabs.iot.aisbase.spec.AISManufacturerADData;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.bluetoothlesdk.ControlMessage;
import com.alibaba.ailabs.iot.bluetoothlesdk.auxiliary.AuxiliaryProvisionManager;
import com.alibaba.ailabs.iot.bluetoothlesdk.datasource.RequestManager;
import com.alibaba.ailabs.iot.bluetoothlesdk.interfaces.OnNotifyListener;
import com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin;
import com.alibaba.ailabs.iot.gattlibrary.plugin.GattCommandPlugin;
import com.alibaba.ailabs.iot.gattlibrary.plugin.auth.GattAuthPlugin;
import com.alibaba.ailabs.iot.gattlibrary.plugin.ota.GattOTAPlugin;
import com.alibaba.ailabs.iot.iotmtopdatasource.FeiyanDeviceManager;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Ascii;
import datasource.NetworkCallback;
import datasource.implemention.data.DeviceVersionInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class GenieBLEDevice extends BluetoothDeviceWrapper implements Parcelable, ITransmissionLayerCallback, OnNotifyListener {
    public static int GENIE_BLE = 2;
    private static final String TAG = "GenieBLEDevice";
    private IBLEInfrastructurePlugin mBLEInfrastructurePlugin;
    private Context mContext;
    private IGenieBLEDeviceCallback mGenieBLEDeviceCallback;
    private b mControlMessageQueue = new b();
    private boolean mHasBeenAuthenticatedSuccessfully = false;
    private IActionListener<BluetoothDevice> mConnectionListener = null;
    private boolean mMeshOtaFlag = false;
    private boolean mHasOtaActivity = false;
    private byte mSubVersion = 0;
    private final String AIS_VERSION_REGEX = "^([0-9]\\d|[0-9])(\\.([0-9]\\d|\\d)){1,3}$";
    private int bleConnectState = 0;

    /* renamed from: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8610a;

        static {
            int[] iArr = new int[ControlMessage.Type.values().length];
            f8610a = iArr;
            try {
                iArr[ControlMessage.Type.BIND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8610a[ControlMessage.Type.UNBIND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f8610a[ControlMessage.Type.CONTROL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public GenieBLEDevice(BluetoothDevice bluetoothDevice) {
        setBluetoothDevice(bluetoothDevice);
    }

    public static int adapterToAisVersion(String str) {
        if (str == null) {
            return 0;
        }
        String[] strArrSplit = str.split("\\.");
        LogUtils.d(TAG, "versionItems length: " + strArrSplit.length);
        if (strArrSplit.length < 3) {
            return 0;
        }
        int[] iArr = {Integer.valueOf(strArrSplit[0]).intValue(), Integer.valueOf(strArrSplit[1]).intValue(), Integer.valueOf(strArrSplit[2]).intValue()};
        return iArr[2] | (iArr[0] << 16) | (iArr[1] << 8);
    }

    private String getAdvertiseMac() {
        byte[] macAddress = this.mAisManufactureDataADV.getMacAddress();
        return String.format("%1$02x:%2$02x:%3$02x:%4$02x:%5$02x:%6$02x", Byte.valueOf(macAddress[0]), Byte.valueOf(macAddress[1]), Byte.valueOf(macAddress[2]), Byte.valueOf(macAddress[3]), Byte.valueOf(macAddress[4]), Byte.valueOf(macAddress[5]));
    }

    private IActionListener<byte[]> getAuthListener() {
        return new IDetailActionListener<byte[]>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.8
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(byte[] bArr) {
                byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, 16);
                Iterator it = GenieBLEDevice.this.mInstalledPlugins.iterator();
                while (it.hasNext()) {
                    ((IPlugin) it.next()).enableAESEncryption(bArrCopyOfRange);
                }
                GenieBLEDevice.this.notifyConnectionState(LayerState.AUTH_SUCCESSFUL);
                GenieBLEDevice.this.nextRequest(true, null);
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str) {
                LogUtils.d(GenieBLEDevice.TAG, "Auth failed, errorCode: " + i2 + ", errorDesc: " + str);
                GenieBLEDevice.this.notifyConnectionState(LayerState.AUTH_FAILED);
                GenieBLEDevice.this.mChannelManager.getTransmissionLayer().disconnectDevice(new IActionListener<BluetoothDevice>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.8.1
                    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onSuccess(BluetoothDevice bluetoothDevice) {
                    }

                    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
                    public void onFailure(int i3, String str2) {
                    }
                });
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IDetailActionListener
            public void onState(int i2, String str, Object obj) {
                LogUtils.d(GenieBLEDevice.TAG, "connect onState() called with: i = [" + i2 + "], s = [" + str + "], o = [" + obj + "]");
                GenieBLEDevice.this.bleConnectState = i2;
            }
        };
    }

    private IActionListener<BluetoothDevice> getConnectResultListener() {
        return new IActionListener<BluetoothDevice>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.7
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(BluetoothDevice bluetoothDevice) {
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str) {
                LogUtils.e(GenieBLEDevice.TAG, "Connection failed(" + i2 + "," + str + ")");
                GenieBLEDevice.this.nextRequest(true, null);
            }
        };
    }

    private byte[] getFillVersion(String str) {
        if (TextUtils.isEmpty(str) || !str.matches("^([0-9]\\d|[0-9])(\\.([0-9]\\d|\\d)){1,3}$")) {
            byte[] bArr = new byte[31];
            Arrays.fill(bArr, (byte) 0);
            byte[] bytes = str.getBytes();
            System.arraycopy(bytes, 0, bArr, 0, bytes.length >= 31 ? 30 : bytes.length);
            return bArr;
        }
        int iAdapterToAisVersion = adapterToAisVersion(str);
        LogUtils.d(TAG, "Real start ota, versionInt: " + iAdapterToAisVersion);
        return com.alibaba.ailabs.iot.aisbase.Utils.int2ByteArrayByLittleEndian(iAdapterToAisVersion);
    }

    private IOTAPlugin.IOTAActionListener getListenerWrapper(final IOTAPlugin.IOTAActionListener iOTAActionListener) {
        return new IOTAPlugin.IOTAActionListener() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.2
            @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
            public void onFailed(int i2, String str) {
                int i3;
                GenieBLEDevice.this.mHasOtaActivity = false;
                if (i2 == -202) {
                    i3 = IotServerErrorCode.NOT_SUPPORT_OTA;
                } else if (i2 == -201) {
                    i3 = IotServerErrorCode.NO_OTA_INFO;
                } else if (i2 == -1 || i2 == 0) {
                    i3 = IotServerErrorCode.NOT_CONNECTED;
                } else if (i2 == 2) {
                    i3 = IotServerErrorCode.DOES_NOT_ALLOW_OTA;
                } else if (i2 == 3) {
                    i3 = IotServerErrorCode.VERIFY_FIRMWARE_FAILED;
                } else if (i2 == 4) {
                    i3 = IotServerErrorCode.EXIST_OTA;
                } else if (i2 == 5) {
                    i3 = IotServerErrorCode.OTA_TIMEOUT;
                } else if (i2 != 6) {
                    switch (i2) {
                        case -402:
                            i3 = IotServerErrorCode.MD5_NOT_MATCH;
                            break;
                        case -401:
                        case -400:
                            i3 = IotServerErrorCode.DOWNLOAD_ERROR;
                            break;
                        default:
                            i3 = IotServerErrorCode.UNKNOWN;
                            break;
                    }
                } else {
                    i3 = IotServerErrorCode.LOSS_LINK;
                }
                IOTAPlugin.IOTAActionListener iOTAActionListener2 = iOTAActionListener;
                if (iOTAActionListener2 != null) {
                    iOTAActionListener2.onFailed(i3, str);
                }
                DeviceVersionInfo deviceVersionInfo = GenieBLEDevice.this.mBLEInfrastructurePlugin.getDeviceVersionInfo();
                GenieBLEDevice.this.reportOtaProgress(i3, false, (deviceVersionInfo == null || deviceVersionInfo.getModel() == null) ? "" : deviceVersionInfo.getModel().getVersion());
            }

            @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
            public void onProgress(int i2, int i3) {
                IOTAPlugin.IOTAActionListener iOTAActionListener2 = iOTAActionListener;
                if (iOTAActionListener2 != null) {
                    iOTAActionListener2.onProgress(i2, i3);
                }
            }

            @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
            public void onStateChanged(IOTAPlugin.OTAState oTAState) {
                IOTAPlugin.IOTAActionListener iOTAActionListener2 = iOTAActionListener;
                if (iOTAActionListener2 != null) {
                    iOTAActionListener2.onStateChanged(oTAState);
                }
            }

            @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
            public void onSuccess(String str) {
                LogUtils.d(GenieBLEDevice.TAG, "ota success, version: " + str);
                DeviceVersionInfo deviceVersionInfo = GenieBLEDevice.this.mBLEInfrastructurePlugin.getDeviceVersionInfo();
                String version = (deviceVersionInfo == null || deviceVersionInfo.getModel() == null) ? "" : deviceVersionInfo.getModel().getVersion();
                if (version.equalsIgnoreCase(str)) {
                    GenieBLEDevice.this.reportOtaProgress(IotServerErrorCode.INCONSISTENT_VERSION, true, version);
                    IOTAPlugin.IOTAActionListener iOTAActionListener2 = iOTAActionListener;
                    if (iOTAActionListener2 != null) {
                        iOTAActionListener2.onFailed(IotServerErrorCode.INCONSISTENT_VERSION, "Inconsistent version");
                        return;
                    }
                    return;
                }
                IOTAPlugin.IOTAActionListener iOTAActionListener3 = iOTAActionListener;
                if (iOTAActionListener3 != null) {
                    iOTAActionListener3.onSuccess(version);
                }
                GenieBLEDevice.this.reportOtaProgress(100, true, version);
                GenieBLEDevice.this.mHasOtaActivity = false;
            }
        };
    }

    private void getManufacturerSpecificData() {
        LogUtils.d(TAG, "Dynamic get manufacture specific data");
        this.mBasicPlugin.getManufacturerSpecificData(new IActionListener<byte[]>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.6
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(byte[] bArr) {
                if (bArr == null) {
                    LogUtils.w(GenieBLEDevice.TAG, "Get empty manufacture specific data!");
                    return;
                }
                GenieBLEDevice.this.setAisManufactureDataADV(AISManufacturerADData.parseFromBytes(bArr));
                if (!GenieBLEDevice.this.isIsSafetyMode()) {
                    GenieBLEDevice.this.notifyConnectionState(LayerState.AUTH_SUCCESSFUL);
                }
                GenieBLEDevice.this.installOptionalPlugins(true);
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str) {
                LogUtils.e(GenieBLEDevice.TAG, "Get manufacture specific data failed(code: " + i2 + ", message: " + str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IActionListener<Boolean> getOnlineEventListener() {
        return new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.9
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(Boolean bool) {
                LogUtils.d(GenieBLEDevice.TAG, "Online event: " + bool);
                if (GenieBLEDevice.this.mGenieBLEDeviceCallback != null) {
                    GenieBLEDevice.this.mGenieBLEDeviceCallback.onlineStateChanged(bool.booleanValue());
                }
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str) {
            }
        };
    }

    private IActionListener<BluetoothDevice> getUTLogDecoratorForConnectionListener(IActionListener<BluetoothDevice> iActionListener) {
        return new d(iActionListener, this, "connection");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void installOptionalPlugins(boolean z2) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(z2 ? "Dynamic " : "Static ");
        sb.append("loading optional plugin");
        LogUtils.d(str, sb.toString());
        if (isIsSafetyMode() && this.mAisManufactureDataADV != null) {
            LogUtils.d(str, "Support safety mode");
            GattAuthPlugin gattAuthPlugin = new GattAuthPlugin();
            gattAuthPlugin.setIsBLEDevice(true);
            gattAuthPlugin.setAuthParams(this.mAisManufactureDataADV.getPId(), getAdvertiseMac(), getAuthListener());
            try {
                if (z2) {
                    this.mChannelManager.dynamicInstallPlugin(gattAuthPlugin);
                } else {
                    this.mChannelManager.installPlugin(gattAuthPlugin);
                }
            } catch (UnsupportedPluginTypeException e2) {
                e2.printStackTrace();
            }
            this.mInstalledPlugins.add(gattAuthPlugin);
            gattAuthPlugin.setBluetoothDeviceWrapper(this);
        }
        if (isSupportOTA()) {
            LogUtils.d(TAG, "Support OTA");
            this.mOtaPlugin = new GattOTAPlugin();
            try {
                if (z2) {
                    this.mChannelManager.dynamicInstallPlugin(this.mOtaPlugin);
                } else {
                    this.mChannelManager.installPlugin(this.mOtaPlugin);
                }
            } catch (UnsupportedPluginTypeException e3) {
                e3.printStackTrace();
            }
            this.mInstalledPlugins.add(this.mOtaPlugin);
            this.mOtaPlugin.setBluetoothDeviceWrapper(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public synchronized void nextRequest(boolean z2, Context context) {
        try {
            LayerState connectionState = getConnectionState();
            LayerState layerState = LayerState.AUTH_SUCCESSFUL;
            if (connectionState != layerState) {
                if (z2) {
                    while (this.mControlMessageQueue.b()) {
                        ControlMessage controlMessageA = this.mControlMessageQueue.a();
                        if (controlMessageA != null) {
                            if (makeSurePluginIsInitialized(this.mBLEInfrastructurePlugin, controlMessageA.mCallback)) {
                                LogUtils.d(TAG, "Force performing operations, next one is " + controlMessageA.type);
                                int i2 = AnonymousClass5.f8610a[controlMessageA.type.ordinal()];
                                if (i2 == 1 || i2 == 2 || i2 == 3) {
                                    IActionListener iActionListener = controlMessageA.mCallback;
                                    if (iActionListener != null) {
                                        iActionListener.onFailure(-1, "Not connected");
                                    }
                                }
                            } else {
                                LogUtils.w(TAG, "BLE infrastructure plugin not initialized");
                            }
                        }
                    }
                } else {
                    LogUtils.d(TAG, "First connect");
                    if (connectionState != LayerState.CONNECTED) {
                        connect(context, getConnectResultListener());
                    }
                }
            } else if (connectionState == layerState) {
                while (this.mControlMessageQueue.b()) {
                    ControlMessage controlMessageA2 = this.mControlMessageQueue.a();
                    if (controlMessageA2 != null) {
                        if (makeSurePluginIsInitialized(this.mBLEInfrastructurePlugin, controlMessageA2.mCallback)) {
                            LogUtils.d(TAG, "Performing operations, next one is " + controlMessageA2.type);
                            int i3 = AnonymousClass5.f8610a[controlMessageA2.type.ordinal()];
                            if (i3 == 1) {
                                this.mBLEInfrastructurePlugin.bindDevice(controlMessageA2.mCallback);
                            } else if (i3 == 2) {
                                this.mBLEInfrastructurePlugin.unbindDevice(controlMessageA2.mCallback);
                            } else if (i3 == 3) {
                                if (controlMessageA2.getJsonParameters() != null) {
                                    this.mBLEInfrastructurePlugin.sendMessage(controlMessageA2.getJsonParameters(), true, controlMessageA2.mCallback);
                                } else {
                                    this.mBLEInfrastructurePlugin.sendMessage(controlMessageA2.getRequest(), controlMessageA2.getParameters(), true, controlMessageA2.mCallback);
                                }
                            }
                        } else {
                            LogUtils.w(TAG, "BLE infrastructure plugin not initialized");
                        }
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConnectionState(LayerState layerState) {
        LogUtils.d(TAG, "Connection state change to " + layerState);
        LayerState layerState2 = LayerState.AUTH_SUCCESSFUL;
        if (layerState == layerState2) {
            GenieBLEDeviceManager.cacheBLEDevice(this);
            IActionListener<BluetoothDevice> iActionListener = this.mConnectionListener;
            if (iActionListener != null) {
                iActionListener.onSuccess(getBluetoothDevice());
            }
            if (this.mHasOtaActivity) {
                return;
            }
            if (makeSurePluginIsInitialized(this.mOtaPlugin, null) && makeSurePluginIsInitialized(this.mBLEInfrastructurePlugin, null)) {
                this.mOtaPlugin.sendGetFirmwareVersionCommand(new IActionListener<Object>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.10
                    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
                    public void onFailure(int i2, String str) {
                        GenieBLEDevice.this.mBLEInfrastructurePlugin.reportOnlineStatus(true, "", GenieBLEDevice.this.getOnlineEventListener());
                    }

                    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
                    public void onSuccess(Object obj) {
                        if (obj instanceof Integer) {
                            GenieBLEDevice.this.mBLEInfrastructurePlugin.reportOnlineStatus(true, Utils.adapterToIotServerVersion(((Integer) obj).intValue()), GenieBLEDevice.this.getOnlineEventListener());
                        } else if (obj instanceof String) {
                            GenieBLEDevice.this.mBLEInfrastructurePlugin.reportOnlineStatus(true, (String) obj, GenieBLEDevice.this.getOnlineEventListener());
                        }
                    }
                });
            }
        } else if (layerState == LayerState.AUTH_FAILED) {
            Utils.notifyFailed(this.mConnectionListener, FailCallback.REASON_AUTH_FAILED, "auth failed");
        }
        this.mHasBeenAuthenticatedSuccessfully = layerState == layerState2;
        IGenieBLEDeviceCallback iGenieBLEDeviceCallback = this.mGenieBLEDeviceCallback;
        if (iGenieBLEDeviceCallback != null) {
            iGenieBLEDeviceCallback.onChannelStateChanged(layerState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportOtaProgress(int i2, boolean z2, String str) {
        RequestManager.getInstance().reportOtaProgress(getAisManufactureDataADV().getPidStr(), getAddress(), z2, str, String.valueOf(i2), null);
    }

    public void bindDevice(Context context, IActionListener<Boolean> iActionListener) {
        LogUtils.d(TAG, "Bind...");
        this.mControlMessageQueue.b(new ControlMessage(ControlMessage.Type.BIND).callback(iActionListener));
        nextRequest(false, context.getApplicationContext());
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public /* bridge */ /* synthetic */ BluetoothDeviceWrapper connect(Context context, IActionListener iActionListener) {
        return connect(context, (IActionListener<BluetoothDevice>) iActionListener);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public TransmissionLayerManagerBase createChannelManager(Context context, TransmissionLayer transmissionLayer) {
        synchronized (this) {
            this.mChannelManager = new c(context, this, transmissionLayer);
            this.mChannelManager.setTransmissionLayerCallback(this);
        }
        return this.mChannelManager;
    }

    public void disconnectBLEDevice() {
        disconnect(new IActionListener<BluetoothDevice>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.4
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(BluetoothDevice bluetoothDevice) {
                LogUtils.d(GenieBLEDevice.TAG, "BLE device disconnection successful.");
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str) {
                LogUtils.d(GenieBLEDevice.TAG, "BLE device disconnection failed, i:" + i2 + " s:" + str);
            }
        });
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public String getAddress() {
        return getAdvertiseMac().toUpperCase();
    }

    public int getBleConnectState() {
        return this.bleConnectState;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public LayerState getConnectionState() {
        return this.mChannelManager == null ? LayerState.NONE : (this.mChannelManager.getTransmissionLayer().getConnectionState() != LayerState.CONNECTED || (isIsSafetyMode() && !this.mHasBeenAuthenticatedSuccessfully)) ? this.mChannelManager.getTransmissionLayer().getConnectionState() : LayerState.AUTH_SUCCESSFUL;
    }

    public void getFirmwareVersion(IActionListener<Integer> iActionListener) {
        if (makeSurePluginIsInitialized(this.mOtaPlugin, iActionListener)) {
            this.mOtaPlugin.sendGetFirmwareVersionCommand(iActionListener);
        }
    }

    public void getFirmwareVersionV1(IActionListener<String> iActionListener) {
        if (makeSurePluginIsInitialized(this.mOtaPlugin, iActionListener)) {
            this.mOtaPlugin.sendGetFirmwareVersionCommandV1(iActionListener);
        }
    }

    public void initFeiyanNetwork(AuthInfoListener authInfoListener) {
        RequestManager.getInstance().init(authInfoListener, new FeiyanDeviceManager());
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public void installPlugins(TransmissionLayer transmissionLayer) {
        this.mBasicPlugin = new GattCommandPlugin();
        this.mBLEInfrastructurePlugin = this.mSubVersion == 18 ? new com.alibaba.ailabs.iot.bluetoothlesdk.plugin.b() : new com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a();
        this.mInstalledPlugins.add(this.mBasicPlugin);
        this.mInstalledPlugins.add(this.mBLEInfrastructurePlugin);
        this.mBLEInfrastructurePlugin.setOnNotifyListener(this);
        try {
            this.mChannelManager.installPlugin(this.mBasicPlugin);
            this.mChannelManager.installPlugin(this.mBLEInfrastructurePlugin);
            Iterator<IPlugin> it = this.mInstalledPlugins.iterator();
            while (it.hasNext()) {
                it.next().setBluetoothDeviceWrapper(this);
            }
            installOptionalPlugins(false);
        } catch (UnsupportedPluginTypeException unused) {
            LogUtils.w(TAG, String.format("Install plugin(%s) for transmission layer failed", this.mBasicPlugin));
        }
    }

    public boolean isMeshOtaDevice() {
        return this.mMeshOtaFlag;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ITransmissionLayerCallback
    public void onA2DPConnectionStateUpdate(BluetoothDevice bluetoothDevice, int i2) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ITransmissionLayerCallback
    public void onBindStateUpdate(BluetoothDevice bluetoothDevice, int i2) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ITransmissionLayerCallback
    public void onConnectionStateUpdate(BluetoothDevice bluetoothDevice, int i2) {
        String str = TAG;
        LogUtils.d(str, "Connection status changes to " + i2);
        if (i2 == 0) {
            notifyConnectionState(LayerState.DISCONNECTED);
            Iterator<IPlugin> it = this.mInstalledPlugins.iterator();
            while (it.hasNext()) {
                it.next().enableAESEncryption(null);
            }
            return;
        }
        if (i2 != 2) {
            return;
        }
        notifyConnectionState(LayerState.CONNECTED);
        if (this.mAisManufactureDataADV == null) {
            if (makeSurePluginIsInitialized(this.mBasicPlugin, null)) {
                getManufacturerSpecificData();
                return;
            } else {
                LogUtils.w(str, "Basic plugin not initialized");
                return;
            }
        }
        if (isIsSafetyMode()) {
            return;
        }
        notifyConnectionState(LayerState.AUTH_SUCCESSFUL);
        nextRequest(false, null);
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.interfaces.OnNotifyListener
    public void onNotify(byte b2, byte[] bArr) {
        LogUtils.d(TAG, "onNotify, command type: " + ConvertUtils.bytes2HexString(new byte[]{b2}));
        if (b2 == 1) {
            AuxiliaryProvisionManager.getInstance().notifyAuxiliaryDeviceStatusChange(getAddress(), bArr);
        } else if (b2 == 64) {
            AuxiliaryProvisionManager.getInstance().notifyAuxiliaryDeviceStatusChange(getAddress(), bArr);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ITransmissionLayerCallback
    public void onReceivedCommand(byte b2, byte[] bArr) {
        if (b2 == 1) {
            AuxiliaryProvisionManager.getInstance().notifyAuxiliaryDeviceStatusChange(getAddress(), bArr);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ITransmissionLayerCallback
    public void onReceivedStream(byte[] bArr) {
    }

    public void realStartFeiyanOta(String str, DeviceVersionInfo.DeviceInfoModel deviceInfoModel, IOTAPlugin.IOTAActionListener iOTAActionListener) throws IOException {
        String str2 = TAG;
        LogUtils.d(str2, "Real start feiyan ota, firmware path: " + str);
        if (!makeSurePluginIsInitialized(this.mOtaPlugin, null)) {
            if (iOTAActionListener != null) {
                iOTAActionListener.onFailed(-202, "Not connected or not supported");
                return;
            }
            return;
        }
        if (deviceInfoModel == null || deviceInfoModel.getCanOta().equals(RequestConstant.FALSE)) {
            iOTAActionListener.onFailed(-201, "");
            return;
        }
        String version = deviceInfoModel.getVersion();
        LogUtils.d(str2, "Real start Feiyan ota, version: " + version);
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            int iAvailable = fileInputStream.available();
            byte[] bArr = new byte[iAvailable];
            fileInputStream.read(bArr);
            fileInputStream.close();
            this.mOtaPlugin.startOTA(bArr, getFillVersion(version), com.alibaba.ailabs.iot.aisbase.Utils.int2ByteArrayByLittleEndian(iAvailable), (byte) 0, Arrays.copyOfRange(com.alibaba.ailabs.iot.aisbase.Utils.int2ByteArrayByLittleEndian(com.alibaba.ailabs.iot.aisbase.Utils.genCrc16CCITT(bArr, 0, iAvailable)), 0, 2), (byte) 0, iOTAActionListener);
        } catch (IOException e2) {
            LogUtils.e(TAG, e2.toString());
            if (iOTAActionListener != null) {
                iOTAActionListener.onFailed(-200, "Failed to open firmware file");
            }
        }
    }

    public void realStartOta(String str, IOTAPlugin.IOTAActionListener iOTAActionListener) throws IOException {
        String str2 = TAG;
        LogUtils.d(str2, "Real start ota, firmware path: " + str);
        if (!makeSurePluginIsInitialized(this.mOtaPlugin, null)) {
            if (iOTAActionListener != null) {
                iOTAActionListener.onFailed(-202, "Not connected or not supported");
                return;
            }
            return;
        }
        DeviceVersionInfo deviceVersionInfo = this.mBLEInfrastructurePlugin.getDeviceVersionInfo();
        if (deviceVersionInfo == null || deviceVersionInfo.getModel() == null || deviceVersionInfo.getModel().getCanOta().equals(RequestConstant.FALSE)) {
            iOTAActionListener.onFailed(-201, "");
            return;
        }
        String version = deviceVersionInfo.getModel().getVersion();
        LogUtils.d(str2, "Real start ota, version: " + version);
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            int iAvailable = fileInputStream.available();
            byte[] bArr = new byte[iAvailable];
            fileInputStream.read(bArr);
            fileInputStream.close();
            int iAdapterToAisVersion = adapterToAisVersion(version);
            LogUtils.d(str2, "Real start ota, versionInt: " + iAdapterToAisVersion);
            this.mOtaPlugin.startOTA(bArr, com.alibaba.ailabs.iot.aisbase.Utils.int2ByteArrayByLittleEndian(iAdapterToAisVersion), com.alibaba.ailabs.iot.aisbase.Utils.int2ByteArrayByLittleEndian(iAvailable), (byte) 0, Arrays.copyOfRange(com.alibaba.ailabs.iot.aisbase.Utils.int2ByteArrayByLittleEndian(com.alibaba.ailabs.iot.aisbase.Utils.genCrc16CCITT(bArr, 0, iAvailable)), 0, 2), (byte) 0, iOTAActionListener);
        } catch (IOException e2) {
            LogUtils.e(TAG, e2.toString());
            if (iOTAActionListener != null) {
                iOTAActionListener.onFailed(-200, "Failed to open firmware file");
            }
        }
    }

    public void reportDeviceStatus(byte[] bArr, IActionListener<Boolean> iActionListener) {
        if (makeSurePluginIsInitialized(this.mBLEInfrastructurePlugin, iActionListener)) {
            this.mBLEInfrastructurePlugin.reportDeviceStatus(bArr, iActionListener);
        }
    }

    public void reportFeiyanOtaProgress(JSONObject jSONObject) {
        RequestManager.getInstance().reportFeiyanOtaProgress(jSONObject, new NetworkCallback<Object>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.3
            @Override // datasource.NetworkCallback
            public void onFailure(String str, String str2) {
                LogUtils.i(GenieBLEDevice.TAG, "on Failed report Progress, s: " + str + " , s1: " + str2);
            }

            @Override // datasource.NetworkCallback
            public void onSuccess(Object obj) {
                LogUtils.i(GenieBLEDevice.TAG, "on Success report Progress: " + obj);
            }
        });
    }

    public void sendAuxiliaryProvisionMessage(Context context, byte[] bArr, IActionListener<BluetoothDevice> iActionListener) {
        this.mControlMessageQueue.a(new ControlMessage(ControlMessage.Type.CONTROL, (byte) 13, bArr).callback(iActionListener));
        nextRequest(false, context.getApplicationContext());
    }

    public void sendMessage(Context context, JSONObject jSONObject, IActionListener<Boolean> iActionListener) {
        this.mControlMessageQueue.a(new ControlMessage(ControlMessage.Type.CONTROL, jSONObject).callback(iActionListener));
        nextRequest(false, context.getApplicationContext());
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public void setAisManufactureDataADV(AISManufacturerADData aISManufacturerADData) {
        super.setAisManufactureDataADV(aISManufacturerADData);
        this.mSubVersion = (byte) (aISManufacturerADData.getExt()[0] & Ascii.RS);
    }

    public void setGenieBLEDeviceCallback(IGenieBLEDeviceCallback iGenieBLEDeviceCallback) {
        this.mGenieBLEDeviceCallback = iGenieBLEDeviceCallback;
    }

    public void setMeshOtaFlag(boolean z2) {
        this.mMeshOtaFlag = z2;
    }

    public void startDownloadFeiyanDeviceFirmware(Context context, DeviceVersionInfo.DeviceInfoModel deviceInfoModel, String str, final IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener) throws InterruptedException {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener2 = new IOTAPlugin.IFirmwareDownloadListener() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.13
            @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
            public void onComplete(String str2) {
                LogUtils.d(GenieBLEDevice.TAG, "Download Completed (path:" + str2 + ")");
                IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener3 = iFirmwareDownloadListener;
                if (iFirmwareDownloadListener3 != null) {
                    iFirmwareDownloadListener3.onComplete(str2);
                }
            }

            @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
            public void onDownloadStart() {
                LogUtils.d(GenieBLEDevice.TAG, "Download start");
                IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener3 = iFirmwareDownloadListener;
                if (iFirmwareDownloadListener3 != null) {
                    iFirmwareDownloadListener3.onDownloadStart();
                }
            }

            @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
            public void onFailed(int i2, String str2) {
                LogUtils.d(GenieBLEDevice.TAG, "Download failed(code:" + i2 + ", desc:" + str2 + ")");
                IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener3 = iFirmwareDownloadListener;
                if (iFirmwareDownloadListener3 != null) {
                    iFirmwareDownloadListener3.onFailed(i2, str2);
                }
                UTLogUtils.updateBusInfo("ota", UTLogUtils.buildDeviceInfo(GenieBLEDevice.this), UTLogUtils.buildOtaBusInfo("error", 0, i2, str2));
            }

            @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
            public void onProgress(int i2, int i3) {
                LogUtils.d(GenieBLEDevice.TAG, "Download progress: " + i2 + "/" + i3);
                IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener3 = iFirmwareDownloadListener;
                if (iFirmwareDownloadListener3 != null) {
                    iFirmwareDownloadListener3.onProgress(i2, i3);
                }
            }
        };
        UTLogUtils.updateBusInfo("ota", UTLogUtils.buildDeviceInfo(this), UTLogUtils.buildOtaBusInfo("start", (this.mChannelManager == null || this.mChannelManager.getTransmissionLayer() == null) ? 0 : this.mChannelManager.getTransmissionLayer().getMtu(), 0, ""));
        new OTADownloadHelper().startDownloadIlopFirmware(context, deviceInfoModel, str, iFirmwareDownloadListener2);
    }

    public void startOTA(Context context, String str, final IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener, IOTAPlugin.IOTAActionListener iOTAActionListener) {
        String str2 = TAG;
        LogUtils.d(str2, "Start OTA, layer state: " + this.mChannelManager.getTransmissionLayer().getConnectionState());
        this.mHasOtaActivity = true;
        final IOTAPlugin.IOTAActionListener listenerWrapper = getListenerWrapper(iOTAActionListener);
        if (makeSurePluginIsInitialized(this.mOtaPlugin, new IActionListener() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.11
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str3) {
                listenerWrapper.onFailed(i2, str3);
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onSuccess(Object obj) {
                LogUtils.d(GenieBLEDevice.TAG, "on Success of makeSurePluginIsInitialized");
            }
        })) {
            DeviceVersionInfo deviceVersionInfo = this.mBLEInfrastructurePlugin.getDeviceVersionInfo();
            if (deviceVersionInfo == null || deviceVersionInfo.getModel().getCanOta().equals(RequestConstant.FALSE)) {
                listenerWrapper.onFailed(-201, "");
                return;
            }
            LogUtils.d(str2, "Start download from server, otaInfo " + deviceVersionInfo);
            startDownloadDeviceFirmware(context, deviceVersionInfo, str, new IOTAPlugin.IFirmwareDownloadListener() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.12
                @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
                public void onComplete(String str3) throws IOException {
                    GenieBLEDevice genieBLEDevice = GenieBLEDevice.this;
                    genieBLEDevice.realStartOta(str3, new OTAUTLogDecorator(listenerWrapper, genieBLEDevice));
                }

                @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
                public void onDownloadStart() {
                    LogUtils.d(GenieBLEDevice.TAG, "Download start");
                    IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener2 = iFirmwareDownloadListener;
                    if (iFirmwareDownloadListener2 != null) {
                        iFirmwareDownloadListener2.onDownloadStart();
                    }
                }

                @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
                public void onFailed(int i2, String str3) {
                    LogUtils.d(GenieBLEDevice.TAG, "Download failed(code:" + i2 + ", desc:" + str3 + ")");
                    listenerWrapper.onFailed(i2, str3);
                }

                @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
                public void onProgress(int i2, int i3) {
                    LogUtils.d(GenieBLEDevice.TAG, "Download progress: " + i2 + "/" + i3);
                    IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener2 = iFirmwareDownloadListener;
                    if (iFirmwareDownloadListener2 != null) {
                        iFirmwareDownloadListener2.onProgress(i2, i3);
                    }
                }
            });
        }
    }

    public void unbindDevice(Context context, IActionListener<Boolean> iActionListener) {
        LogUtils.d(TAG, "Unbind...");
        this.mControlMessageQueue.b(new ControlMessage(ControlMessage.Type.UNBIND).callback(iActionListener));
        nextRequest(false, context.getApplicationContext());
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public /* bridge */ /* synthetic */ BluetoothDeviceWrapper connect(Context context, TransmissionLayer transmissionLayer, boolean z2, IActionListener iActionListener) {
        return connect(context, transmissionLayer, z2, (IActionListener<BluetoothDevice>) iActionListener);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public GenieBLEDevice connect(Context context, IActionListener<BluetoothDevice> iActionListener) {
        LogUtils.d(TAG, "Connect...");
        if (this.mChannelManager == null) {
            synchronized (this) {
                initTransmissionManager(context, TransmissionLayer.BLE);
            }
        }
        this.mConnectionListener = getUTLogDecoratorForConnectionListener(iActionListener);
        this.mChannelManager.getTransmissionLayer().connectDevice(this.mBluetoothDevice, new IActionListener<BluetoothDevice>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice.1
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(BluetoothDevice bluetoothDevice) {
                if (GenieBLEDevice.this.isIsSafetyMode()) {
                    return;
                }
                GenieBLEDevice.this.mConnectionListener.onSuccess(bluetoothDevice);
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str) {
                GenieBLEDevice.this.mConnectionListener.onFailure(i2, str);
            }
        });
        return this;
    }

    public void sendMessage(Context context, ControlMessage controlMessage) {
        this.mControlMessageQueue.a(controlMessage);
        nextRequest(false, context.getApplicationContext());
    }

    @Override // com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper
    public GenieBLEDevice connect(Context context, TransmissionLayer transmissionLayer, boolean z2, IActionListener<BluetoothDevice> iActionListener) {
        return connect(context, iActionListener);
    }

    public GenieBLEDevice(String str) {
        setBluetoothDevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str));
    }
}
