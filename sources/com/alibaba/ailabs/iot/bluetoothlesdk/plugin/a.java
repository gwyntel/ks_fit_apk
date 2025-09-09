package com.alibaba.ailabs.iot.bluetoothlesdk.plugin;

import aisble.callback.DataSentCallback;
import aisble.callback.FailCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import anetwork.channel.util.RequestConstant;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.alibaba.ailabs.iot.aisbase.Utils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ICastEventListener;
import com.alibaba.ailabs.iot.aisbase.channel.ITransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.channel.LayerState;
import com.alibaba.ailabs.iot.aisbase.dispatcher.CommandResponseDispatcher;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.IAuthPlugin;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.bluetoothlesdk.d;
import com.alibaba.ailabs.iot.bluetoothlesdk.datasource.RequestManager;
import com.alibaba.ailabs.iot.bluetoothlesdk.interfaces.OnNotifyListener;
import com.alibaba.ailabs.iot.gattlibrary.plugin.AISBluetoothGattPluginBase;
import com.alibaba.ailabs.iot.iotmtopdatasource.bean.BleControlResponse;
import com.alibaba.ailabs.iot.iotmtopdatasource.bean.DeviceStatus;
import com.alibaba.ailabs.iot.iotmtopdatasource.bean.IotDevice;
import com.alibaba.ailabs.iot.iotmtopdatasource.implemention.data.IotDeleteDeviceRespData;
import com.alibaba.ailabs.iot.iotmtopdatasource.implemention.data.IotDeviceControlRespData;
import com.alibaba.ailabs.iot.iotmtopdatasource.implemention.data.IotReportDevicesStatusRespData;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.share.internal.ShareConstants;
import com.google.common.base.Ascii;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import datasource.NetworkCallback;
import datasource.implemention.data.DeviceVersionInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes2.dex */
public class a extends AISBluetoothGattPluginBase implements ICastEventListener, CommandResponseDispatcher.OnCommandReceivedListener, IBLEInfrastructurePlugin {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8650a = "a";

    /* renamed from: b, reason: collision with root package name */
    private BluetoothGattCharacteristic f8651b;

    /* renamed from: c, reason: collision with root package name */
    private BluetoothGattCharacteristic f8652c;

    /* renamed from: d, reason: collision with root package name */
    private BluetoothGattCharacteristic f8653d;

    /* renamed from: h, reason: collision with root package name */
    private DeviceVersionInfo f8657h;

    /* renamed from: e, reason: collision with root package name */
    private SparseArray<IActionListener> f8654e = new SparseArray<>();

    /* renamed from: f, reason: collision with root package name */
    private SparseArray<Runnable> f8655f = new SparseArray<>();

    /* renamed from: g, reason: collision with root package name */
    private Handler f8656g = new Handler(Looper.getMainLooper());

    /* renamed from: i, reason: collision with root package name */
    private int f8658i = 0;

    /* renamed from: j, reason: collision with root package name */
    private int f8659j = 3;

    /* renamed from: k, reason: collision with root package name */
    private OnNotifyListener f8660k = null;

    private static int b(byte b2, byte b3) {
        return ((b2 & 255) << 8) | (b3 & 255);
    }

    protected byte[] a(byte[] bArr) {
        return bArr;
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin
    public void bindDevice(IActionListener<Boolean> iActionListener) {
        String str = f8650a;
        LogUtils.d(str, "bindDevice...");
        final d dVar = new d(iActionListener, getBluetoothDeviceWrapper(), "bind");
        this.f8658i = 0;
        IotDevice iotDevice = new IotDevice();
        BluetoothDeviceWrapper bluetoothDeviceWrapper = getBluetoothDeviceWrapper();
        if (bluetoothDeviceWrapper == null) {
            LogUtils.w(str, "device is null");
            return;
        }
        iotDevice.setPlatform("BLE");
        iotDevice.setSource(PushConstants.EXTRA_APPLICATION_PENDING_INTENT);
        iotDevice.setDevId(bluetoothDeviceWrapper.getAddress());
        iotDevice.setProductKey(bluetoothDeviceWrapper.getAisManufactureDataADV().getPidStr());
        iotDevice.setUserId(RequestManager.getInstance().getUserId());
        iotDevice.setUuid(RequestManager.getInstance().getUtdId());
        ArrayList arrayList = new ArrayList();
        arrayList.add(iotDevice);
        RequestManager.getInstance().getInfoByAuthInfo("iot", "bindBLEDevice", JSON.toJSONString(arrayList), new NetworkCallback<String>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.1
            @Override // datasource.NetworkCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(String str2) {
                LogUtils.d(a.f8650a, "request bind BLE device success");
                a.this.a(true);
                dVar.onSuccess(Boolean.TRUE);
            }

            @Override // datasource.NetworkCallback
            public void onFailure(String str2, String str3) {
                LogUtils.e(a.f8650a, "request bind BLE device failed, errorMessage: " + str3);
                dVar.onFailure(-300, str3);
            }
        });
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.PluginBase
    public String[] getChannelUUIDs() {
        return new String[0];
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin
    public DeviceVersionInfo getDeviceVersionInfo() {
        return this.f8657h;
    }

    @Override // com.alibaba.ailabs.iot.gattlibrary.plugin.AISBluetoothGattPluginBase, com.alibaba.ailabs.iot.aisbase.plugin.PluginBase, com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void init(ITransmissionLayer iTransmissionLayer) {
        super.init(iTransmissionLayer);
        CommandResponseDispatcher commandResponseDispatcher = iTransmissionLayer.getCommandResponseDispatcher(Constants.AIS_COMMAND_RES.toString());
        this.mGattTransmissionLayer.setNotificationCallback(this.f8652c).with(commandResponseDispatcher);
        this.mGattTransmissionLayer.enableIndications(this.f8652c).enqueue();
        commandResponseDispatcher.subscribeMultiCommandReceivedListener(new byte[]{3, 1, 4, Ascii.NAK, 14, 64}, this);
        if (this.f8653d != null) {
            CommandResponseDispatcher commandResponseDispatcher2 = this.mGattTransmissionLayer.getCommandResponseDispatcher(Constants.AIS_OTA_RES.toString());
            this.mGattTransmissionLayer.setNotificationCallback(this.f8653d).with(commandResponseDispatcher2);
            this.mGattTransmissionLayer.enableNotifications(this.f8653d).enqueue();
            commandResponseDispatcher2.subscribeMultiCommandReceivedListener(new byte[]{64}, this);
        }
        this.mGattTransmissionLayer.registerCastEventListener(this);
    }

    @Override // com.alibaba.ailabs.iot.gattlibrary.plugin.AISBluetoothGattPluginBase, com.alibaba.ailabs.iot.gattlibrary.plugin.BluetoothGattPlugin
    public boolean isRequiredServiceSupported(@NonNull BluetoothGatt bluetoothGatt) {
        BluetoothGattService service = bluetoothGatt.getService(Constants.AIS_PRIMARY_SERVICE);
        if (service == null) {
            return false;
        }
        this.f8651b = service.getCharacteristic(Constants.AIS_COMMAND_OUT);
        this.f8652c = service.getCharacteristic(Constants.AIS_COMMAND_RES);
        this.f8653d = service.getCharacteristic(Constants.AIS_OTA_RES);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.f8651b;
        return (this.f8651b == null || this.f8652c == null || !(bluetoothGattCharacteristic != null && (bluetoothGattCharacteristic.getProperties() & 8) > 0)) ? false : true;
    }

    @Override // com.alibaba.ailabs.iot.gattlibrary.plugin.AISBluetoothGattPluginBase, com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void onBluetoothConnectionStateChanged(int i2) {
        if (i2 == 0) {
            reportOnlineStatus(false, "", null);
        } else {
            if (i2 != 2) {
                return;
            }
            getBluetoothDeviceWrapper().isIsSafetyMode();
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ICastEventListener
    public void onCast(String str) {
        LogUtils.d(f8650a, "Handle cast message: " + str);
        str.equals(IAuthPlugin.EVENT_AUTH_SUCCESS);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.dispatcher.CommandResponseDispatcher.OnCommandReceivedListener
    public void onCommandReceived(byte b2, byte b3, byte[] bArr) {
        LogUtils.d(f8650a, "Received(cmd: " + ConvertUtils.bytes2HexString(new byte[]{b2}) + ", payload: " + ConvertUtils.bytes2HexString(bArr));
        IActionListener iActionListener = this.f8654e.get(b(b2, b3));
        if (b2 != 1) {
            if (b2 != 14) {
                if (b2 == 21) {
                    a(b(b2, b3));
                    return;
                } else if (b2 != 64) {
                    if (b2 != 3) {
                        if (b2 != 4) {
                            return;
                        }
                        reportDeviceStatus(a(bArr), new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.10
                            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
                            /* renamed from: a, reason: merged with bridge method [inline-methods] */
                            public void onSuccess(Boolean bool) {
                            }

                            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
                            public void onFailure(int i2, String str) {
                            }
                        });
                        return;
                    }
                }
            }
            if (iActionListener != null) {
                iActionListener.onSuccess(bArr);
            }
            a(b(b2, b3));
            return;
        }
        OnNotifyListener onNotifyListener = this.f8660k;
        if (onNotifyListener != null) {
            onNotifyListener.onNotify(b2, bArr);
        }
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin
    public void reportDeviceStatus(byte[] bArr, IActionListener<Boolean> iActionListener) {
        final d dVar = new d(iActionListener, getBluetoothDeviceWrapper(), AgooConstants.MESSAGE_REPORT);
        if (bArr == null || bArr.length <= 1) {
            LogUtils.w(f8650a, "Illegal Payload: " + ConvertUtils.bytes2HexString(bArr));
            dVar.onFailure(-302, "Illegal Payload: " + ConvertUtils.bytes2HexString(bArr));
            return;
        }
        a(bArr[0], bArr[1]);
        String strByte2String = Utils.byte2String(bArr[0], false);
        String strBytes2HexString = ConvertUtils.bytes2HexString(Arrays.copyOfRange(bArr, 1, bArr.length));
        LogUtils.d(f8650a, "Report status(opcode:" + strByte2String + ", parameter:" + strBytes2HexString);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("opcode", (Object) strByte2String);
        jSONObject.put(PushConstants.PARAMS, (Object) strBytes2HexString);
        String jSONString = jSONObject.toJSONString();
        DeviceStatus deviceStatus = new DeviceStatus();
        String userId = RequestManager.getInstance().getUserId();
        deviceStatus.setDevId(getBluetoothDeviceWrapper().getAddress());
        deviceStatus.setUserId(userId);
        deviceStatus.setStatus(jSONString);
        deviceStatus.setUuid("");
        RequestManager.getInstance().reportDevicesStatus("", Collections.singletonList(deviceStatus), new NetworkCallback<String>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.12
            @Override // datasource.NetworkCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(String str) {
                LogUtils.d(a.f8650a, "reportDevicesStatus request success");
                dVar.onSuccess(Boolean.TRUE);
            }

            @Override // datasource.NetworkCallback
            public void onFailure(String str, String str2) {
                LogUtils.e(a.f8650a, "reportDevicesStatus request failed, errorMessage: " + str2);
                dVar.onFailure(-300, str2);
            }
        });
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin
    public void reportOnlineStatus(final boolean z2, String str, IActionListener<Boolean> iActionListener) {
        String str2;
        String str3 = f8650a;
        StringBuilder sb = new StringBuilder();
        sb.append("Report online status: ");
        sb.append(z2 ? "online" : "offline");
        sb.append(", firmwareVersion: ");
        sb.append(str);
        LogUtils.d(str3, sb.toString());
        final d dVar = new d(iActionListener, getBluetoothDeviceWrapper(), z2 ? "online" : "offline");
        BluetoothDeviceWrapper bluetoothDeviceWrapper = getBluetoothDeviceWrapper();
        if (getBluetoothDeviceWrapper().getSubtype() != BluetoothDeviceSubtype.BASIC) {
            str2 = "ble";
        } else if (!z2) {
            return;
        } else {
            str2 = "sigmesh";
        }
        RequestManager.getInstance().reportDeviceOnlineStatus(bluetoothDeviceWrapper.getAisManufactureDataADV().getPidStr(), bluetoothDeviceWrapper.getAddress(), z2, str, PushConstants.EXTRA_APPLICATION_PENDING_INTENT, str2, new NetworkCallback<IotReportDevicesStatusRespData.OnlineRespModel>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.11
            @Override // datasource.NetworkCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(IotReportDevicesStatusRespData.OnlineRespModel onlineRespModel) {
                String str4 = a.f8650a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Report online status successfully: ");
                sb2.append(onlineRespModel == null ? "" : onlineRespModel.toString());
                LogUtils.d(str4, sb2.toString());
                dVar.onSuccess(Boolean.TRUE);
                if (!z2 || onlineRespModel == null || onlineRespModel.getOtaInfo() == null) {
                    return;
                }
                a.this.f8657h = new DeviceVersionInfo();
                DeviceVersionInfo.DeviceInfoModel deviceInfoModel = new DeviceVersionInfo.DeviceInfoModel();
                deviceInfoModel.setCanOta(onlineRespModel.isOtaFlag() ? "true" : RequestConstant.FALSE);
                deviceInfoModel.setOtaUrl(onlineRespModel.getOtaInfo().getUrl());
                deviceInfoModel.setSize(String.valueOf(onlineRespModel.getOtaInfo().getSize()));
                deviceInfoModel.setMd5(onlineRespModel.getOtaInfo().getVerifyValue());
                deviceInfoModel.setVersion(onlineRespModel.getOtaInfo().getVersion());
                a.this.f8657h.setModel(deviceInfoModel);
            }

            @Override // datasource.NetworkCallback
            public void onFailure(String str4, String str5) {
                dVar.onFailure(-300, str5);
                LogUtils.e(a.f8650a, "Reporting online status failed( " + str4 + "," + str5 + ")");
            }
        });
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public AISCommand sendCommandWithCallback(byte b2, byte[] bArr, DataSentCallback dataSentCallback, FailCallback failCallback) {
        return sendCommandWithCallback(this.f8651b, 2, b2, bArr, dataSentCallback, failCallback);
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin
    public void sendMessage(JSONObject jSONObject, boolean z2, IActionListener iActionListener) {
        LogUtils.d(f8650a, "Send message(" + jSONObject.toJSONString() + ")");
        BluetoothDeviceWrapper bluetoothDeviceWrapper = getBluetoothDeviceWrapper();
        final d dVar = new d(iActionListener, getBluetoothDeviceWrapper(), "sendMessage");
        int iIntValue = jSONObject.getInteger("channel").intValue();
        JSONObject jSONObject2 = new JSONObject();
        String address = bluetoothDeviceWrapper.getAddress();
        jSONObject2.put("devId", (Object) jSONObject.getString("devId"));
        jSONObject2.put("method", (Object) jSONObject.getString("method"));
        jSONObject2.put(RemoteMessageConst.MSGID, (Object) "");
        jSONObject2.put("version", (Object) "1.0");
        jSONObject2.put("params", JSON.parseObject(jSONObject.getString("params"), Map.class));
        HashMap map = new HashMap();
        map.put("pushGenie", iIntValue > 0 ? "true" : RequestConstant.FALSE);
        jSONObject2.put(ShareConstants.MEDIA_EXTENSION, (Object) map);
        RequestManager.getInstance().deviceControl(address, "3404", jSONObject2.toJSONString(), new NetworkCallback<IotDeviceControlRespData>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.8
            @Override // datasource.NetworkCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(IotDeviceControlRespData iotDeviceControlRespData) {
                IotDeviceControlRespData.Extensions extensions = iotDeviceControlRespData.getExtensions();
                if (extensions == null) {
                    LogUtils.w(a.f8650a, "Empty command");
                    dVar.onFailure(-302, "Empty command");
                    return;
                }
                String parameters = extensions.getParameters();
                final String msgId = extensions.getMsgId();
                if (!TextUtils.isEmpty(parameters)) {
                    a.this.a(new IActionListener<byte[]>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.8.1
                        @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
                        /* renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onSuccess(byte[] bArr) {
                            LogUtils.d(a.f8650a, "Control device success: " + ConvertUtils.bytes2HexString(bArr));
                            dVar.onSuccess(Boolean.TRUE);
                            a aVar = a.this;
                            aVar.a(aVar.a(bArr), msgId, true, 0, "");
                        }

                        @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
                        public void onFailure(int i2, String str) {
                            LogUtils.d(a.f8650a, "Failed to control device: " + i2);
                            dVar.onFailure(i2, str);
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("errorCode", (Object) Integer.valueOf(i2));
                            jSONObject3.put(TmpConstant.SERVICE_DESC, (Object) str);
                            a.this.a(null, msgId, false, 1, jSONObject3.toJSONString());
                        }
                    }, (byte) 2, com.alibaba.ailabs.iot.bluetoothlesdk.Utils.hexString2Bytes(parameters), (byte) 3);
                    return;
                }
                LogUtils.w(a.f8650a, "Empty command payload");
                dVar.onFailure(-302, "Empty command payload");
                a.this.a(null, msgId, false, 2, "Empty command payload");
            }

            @Override // datasource.NetworkCallback
            public void onFailure(String str, String str2) {
                LogUtils.d(a.f8650a, "errorCode:" + str + ", errorMessage:" + str2);
                dVar.onFailure(-300, str2);
            }
        });
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void sendRawDataWithCallback(byte[] bArr, DataSentCallback dataSentCallback, FailCallback failCallback) {
        sendRawDataWithCallback(this.f8651b, 2, bArr, dataSentCallback, failCallback);
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin
    public void setOnNotifyListener(OnNotifyListener onNotifyListener) {
        this.f8660k = onNotifyListener;
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin
    public void unbindDevice(IActionListener<Boolean> iActionListener) {
        LogUtils.d(f8650a, DeviceCommonConstants.VALUE_BOX_UNBIND);
        BluetoothDeviceWrapper bluetoothDeviceWrapper = getBluetoothDeviceWrapper();
        final d dVar = new d(iActionListener, getBluetoothDeviceWrapper(), DeviceCommonConstants.VALUE_BOX_UNBIND);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("deviceId", (Object) bluetoothDeviceWrapper.getAddress());
        jSONObject.put("productKey", (Object) bluetoothDeviceWrapper.getAisManufactureDataADV().getPidStr());
        jSONObject.put("skillId", (Object) "3404");
        HashMap map = new HashMap();
        map.put("pushGenie", Boolean.FALSE);
        jSONObject.put("params", (Object) map);
        RequestManager.getInstance().deleteDevice(jSONObject.toJSONString(), new NetworkCallback<IotDeleteDeviceRespData.Extensions>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.7
            @Override // datasource.NetworkCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(IotDeleteDeviceRespData.Extensions extensions) {
                LogUtils.d(a.f8650a, "deleteDevice request success");
                a.this.a(false);
                dVar.onSuccess(Boolean.TRUE);
            }

            @Override // datasource.NetworkCallback
            public void onFailure(String str, String str2) {
                LogUtils.e(a.f8650a, "deleteDevice request failed, errorMessage: " + str2);
                dVar.onFailure(-300, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        int i2 = this.f8658i;
        boolean z2 = i2 < this.f8659j;
        this.f8658i = i2 + 1;
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2) {
        LogUtils.d(f8650a, "Disconnect");
        ITransmissionLayer iTransmissionLayer = this.mTransmissionLayer;
        if (iTransmissionLayer == null || iTransmissionLayer.getConnectionState() != LayerState.CONNECTED || i2 <= 0) {
            return;
        }
        this.f8656g.postDelayed(new Runnable() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.5
            @Override // java.lang.Runnable
            public void run() {
                a.this.mTransmissionLayer.disconnectDevice(null);
            }
        }, i2);
    }

    private void a(byte b2, byte b3) {
        LogUtils.d(f8650a, "Ack for message(" + Utils.byte2String(b2, true));
        a(new IActionListener() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.13
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str) {
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onSuccess(Object obj) {
            }
        }, (byte) 5, new byte[]{(byte) (b2 + 1), b3}, (byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final boolean z2) {
        String str = f8650a;
        StringBuilder sb = new StringBuilder();
        sb.append("Notify the device ");
        sb.append(z2 ? "bind" : "unbind ");
        LogUtils.d(str, sb.toString());
        a(new IActionListener() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.14
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str2) {
                LogUtils.d(a.f8650a, "Notify bind result failed(" + i2 + "," + str2 + ")");
                if (a.this.b()) {
                    a.this.a(z2);
                } else {
                    a.this.b(1000);
                }
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onSuccess(Object obj) {
                LogUtils.d(a.f8650a, "Notify bind result success");
                a.this.b(1000);
            }
        }, Ascii.DC4, new byte[]{z2 ? (byte) 1 : (byte) 0}, Ascii.NAK);
    }

    protected void a(final IActionListener iActionListener, byte b2, byte[] bArr, byte b3) {
        AISCommand aISCommandSendCommandWithCallback = sendCommandWithCallback(b2, bArr, new DataSentCallback() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.2
            @Override // aisble.callback.DataSentCallback
            public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
            }
        }, new FailCallback() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.3
            @Override // aisble.callback.FailCallback
            public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
                iActionListener.onFailure(i2, "");
            }
        });
        if (aISCommandSendCommandWithCallback == null || b3 == 0) {
            return;
        }
        a(b(b3, aISCommandSendCommandWithCallback.getHeader().getMsgID()), iActionListener);
    }

    protected void a(int i2, final IActionListener iActionListener) {
        this.f8654e.put(i2, iActionListener);
        Runnable runnable = new Runnable() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.4
            @Override // java.lang.Runnable
            public void run() {
                iActionListener.onFailure(-5, "Command timeout");
            }
        };
        this.f8655f.put(i2, runnable);
        this.f8656g.postDelayed(runnable, 3000L);
    }

    @Override // com.alibaba.ailabs.iot.bluetoothlesdk.plugin.IBLEInfrastructurePlugin
    public void sendMessage(byte b2, byte[] bArr, boolean z2, IActionListener<byte[]> iActionListener) {
        final d dVar = new d(iActionListener, getBluetoothDeviceWrapper(), "sendMessage");
        a(new IActionListener<byte[]>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.9
            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(byte[] bArr2) {
                LogUtils.d(a.f8650a, "Control device success: " + ConvertUtils.bytes2HexString(bArr2));
                dVar.onSuccess(bArr2);
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
            public void onFailure(int i2, String str) {
                LogUtils.d(a.f8650a, "Failed to control device: " + i2);
                dVar.onFailure(i2, str);
            }
        }, b2, bArr, (byte) 14);
    }

    private void a(int i2) {
        this.f8654e.remove(i2);
        this.f8656g.removeCallbacks(this.f8655f.get(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(byte[] bArr, String str, boolean z2, int i2, String str2) {
        BluetoothDeviceWrapper bluetoothDeviceWrapper = getBluetoothDeviceWrapper();
        String pidStr = bluetoothDeviceWrapper.getAisManufactureDataADV().getPidStr();
        String address = bluetoothDeviceWrapper.getAddress();
        BleControlResponse bleControlResponse = new BleControlResponse();
        BleControlResponse.Data data = new BleControlResponse.Data();
        data.setParameters(bArr == null ? "" : ConvertUtils.bytes2HexString(bArr));
        bleControlResponse.setData(JSON.toJSONString(data));
        bleControlResponse.setPlatform("ble");
        bleControlResponse.setMessageId(str);
        bleControlResponse.setSuccess(z2);
        bleControlResponse.setCode(i2);
        bleControlResponse.setMessage(str2);
        RequestManager.getInstance().reportBleControlResult(address, pidStr, bleControlResponse, new NetworkCallback<String>() { // from class: com.alibaba.ailabs.iot.bluetoothlesdk.plugin.a.6
            @Override // datasource.NetworkCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(String str3) {
                LogUtils.d(a.f8650a, "Report BLE control response successfully");
            }

            @Override // datasource.NetworkCallback
            public void onFailure(String str3, String str4) {
                LogUtils.e(a.f8650a, "Report BLE control response failed(" + str3 + "," + str4 + ")");
            }
        });
    }
}
