package com.alibaba.ailabs.iot.bluetoothlesdk.datasource;

import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.AuthInfoListener;
import com.alibaba.ailabs.iot.iotmtopdatasource.IoTDeviceManager;
import com.alibaba.ailabs.iot.iotmtopdatasource.bean.BleControlResponse;
import com.alibaba.ailabs.iot.iotmtopdatasource.bean.DeviceStatus;
import com.alibaba.ailabs.iot.iotmtopdatasource.implemention.data.IotDeleteDeviceRespData;
import com.alibaba.ailabs.iot.iotmtopdatasource.implemention.data.IotDeviceControlRespData;
import com.alibaba.ailabs.iot.iotmtopdatasource.implemention.data.IotReportDevicesStatusRespData;
import com.alibaba.ailabs.iot.iotmtopdatasource.implemention.data.IotReportOtaProgressRespData;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import datasource.NetworkCallback;
import java.util.List;

/* loaded from: classes2.dex */
public class RequestManager {
    private static final String TAG = "RequestManager";
    private AuthInfoListener mAuthInfoListener;
    private IoTDeviceManager mIoTDeviceManager;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final RequestManager f8649a = new RequestManager();
    }

    public static RequestManager getInstance() {
        return a.f8649a;
    }

    public void deleteDevice(@NonNull String str, NetworkCallback<IotDeleteDeviceRespData.Extensions> networkCallback) {
        String str2 = TAG;
        LogUtils.d(str2, "deleteDevice called...");
        if (this.mIoTDeviceManager == null) {
            LogUtils.e(str2, "IoTDeviceManager is null");
            return;
        }
        AuthInfoListener authInfoListener = this.mAuthInfoListener;
        String authInfo = authInfoListener != null ? authInfoListener.getAuthInfo() : "";
        try {
            JSONObject object = JSON.parseObject(authInfo);
            if (object != null) {
                this.mIoTDeviceManager.deleteDevice(authInfo, object.getString("utdId"), str, networkCallback);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void deviceControl(@NonNull String str, @NonNull String str2, @NonNull String str3, NetworkCallback<IotDeviceControlRespData> networkCallback) {
        String str4 = TAG;
        LogUtils.d(str4, "deviceControl called...");
        if (this.mIoTDeviceManager == null) {
            LogUtils.e(str4, "IoTDeviceManager is null");
        } else {
            AuthInfoListener authInfoListener = this.mAuthInfoListener;
            this.mIoTDeviceManager.deviceControl(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, str3, networkCallback);
        }
    }

    public void getInfoByAuthInfo(@NonNull String str, @NonNull String str2, @NonNull String str3, NetworkCallback<String> networkCallback) {
        String str4 = TAG;
        LogUtils.d(str4, String.format("getInfoByAuthInfo called, module(%s), func(%s), args(%s)", str, str2, str3));
        if (this.mIoTDeviceManager == null) {
            LogUtils.e(str4, "IoTDeviceManager is null");
        } else {
            AuthInfoListener authInfoListener = this.mAuthInfoListener;
            this.mIoTDeviceManager.getInfoByAuthInfo(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, str3, networkCallback);
        }
    }

    public String getUserId() {
        AuthInfoListener authInfoListener = this.mAuthInfoListener;
        if (authInfoListener == null) {
            LogUtils.e(TAG, "mAuthInfoListener is null");
            return "";
        }
        try {
            JSONObject object = JSON.parseObject(authInfoListener.getAuthInfo());
            if (object != null) {
                return object.getString("userId");
            }
            return null;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String getUtdId() {
        AuthInfoListener authInfoListener = this.mAuthInfoListener;
        if (authInfoListener == null) {
            LogUtils.e(TAG, "mAuthInfoListener is null");
            return "";
        }
        try {
            JSONObject object = JSON.parseObject(authInfoListener.getAuthInfo());
            if (object != null) {
                return object.getString("utdId");
            }
            return null;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void init(AuthInfoListener authInfoListener, IoTDeviceManager ioTDeviceManager) {
        throw null;
    }

    public void reportBleControlResult(@NonNull String str, @NonNull String str2, BleControlResponse bleControlResponse, NetworkCallback<String> networkCallback) {
        String str3 = TAG;
        LogUtils.d(str3, "reportBleControlResult called...");
        if (this.mIoTDeviceManager == null) {
            LogUtils.e(str3, "IoTDeviceManager is null");
        } else {
            AuthInfoListener authInfoListener = this.mAuthInfoListener;
            this.mIoTDeviceManager.bleControlResult(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, bleControlResponse, networkCallback);
        }
    }

    public void reportDeviceOnlineStatus(@NonNull String str, @NonNull String str2, @NonNull boolean z2, @NonNull String str3, @NonNull String str4, @NonNull String str5, NetworkCallback<IotReportDevicesStatusRespData.OnlineRespModel> networkCallback) {
        String str6 = TAG;
        LogUtils.d(str6, "reportDeviceOnlineStatus called...");
        if (this.mIoTDeviceManager == null) {
            LogUtils.e(str6, "IoTDeviceManager is null");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("productKey", (Object) str);
        jSONObject.put(AlinkConstants.KEY_SUB_DEVICE_ID, (Object) str2);
        jSONObject.put("isOnline", (Object) Boolean.valueOf(z2));
        jSONObject.put("version", (Object) str3);
        jSONObject.put("searchSource", (Object) str4);
        jSONObject.put("subDevicePlatform", (Object) str5);
        AuthInfoListener authInfoListener = this.mAuthInfoListener;
        this.mIoTDeviceManager.reportOnlineStatus(authInfoListener != null ? authInfoListener.getAuthInfo() : "", jSONObject.toJSONString(), networkCallback);
    }

    public void reportDevicesStatus(@NonNull String str, @NonNull List<DeviceStatus> list, NetworkCallback<String> networkCallback) {
        String str2 = TAG;
        LogUtils.d(str2, "reportDevicesStatus called...");
        if (this.mIoTDeviceManager == null) {
            LogUtils.e(str2, "IoTDeviceManager is null");
        } else {
            AuthInfoListener authInfoListener = this.mAuthInfoListener;
            this.mIoTDeviceManager.reportDevicesStatus(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, list, networkCallback);
        }
    }

    public void reportFeiyanOtaProgress(@NonNull JSONObject jSONObject, NetworkCallback<Object> networkCallback) {
        String str = TAG;
        LogUtils.d(str, "reportOtaProgress called...");
        IoTDeviceManager ioTDeviceManager = this.mIoTDeviceManager;
        if (ioTDeviceManager == null) {
            LogUtils.e(str, "IoTDeviceManager is null");
        } else {
            ioTDeviceManager.reportFeiyanOtaProgressNew(jSONObject, networkCallback);
        }
    }

    public void reportOtaProgress(@NonNull String str, @NonNull String str2, @NonNull boolean z2, @NonNull String str3, @NonNull String str4, NetworkCallback<IotReportOtaProgressRespData> networkCallback) {
        String str5 = TAG;
        LogUtils.d(str5, "reportOtaProgress called...");
        if (this.mIoTDeviceManager == null) {
            LogUtils.e(str5, "IoTDeviceManager is null");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("productKey", (Object) str);
        jSONObject.put(AlinkConstants.KEY_SUB_DEVICE_ID, (Object) str2);
        jSONObject.put("isOnline", (Object) Boolean.valueOf(z2));
        jSONObject.put("version", (Object) str3);
        jSONObject.put(TmpConstant.SERVICE_DESC, (Object) PushConstants.EXTRA_APPLICATION_PENDING_INTENT);
        jSONObject.put("step", (Object) str4);
        LogUtils.d(str5, "Report OTA Progress..." + jSONObject.toJSONString());
        AuthInfoListener authInfoListener = this.mAuthInfoListener;
        this.mIoTDeviceManager.reportOtaProgressNew(authInfoListener != null ? authInfoListener.getAuthInfo() : "", jSONObject.toJSONString(), networkCallback);
    }
}
