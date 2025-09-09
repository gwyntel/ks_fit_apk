package com.alibaba.ailabs.iot.aisbase;

import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.env.AppEnv;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import datasource.AuthManager;
import datasource.NetworkCallback;
import datasource.implemention.DefaultAuthManager;
import datasource.implemention.FeiyanAuthManager;
import datasource.implemention.data.DeviceVersionInfo;
import datasource.implemention.data.GetDeviceUUIDRespData;
import datasource.implemention.data.OtaProgressRespData;
import datasource.implemention.data.UpdateDeviceVersionRespData;

/* loaded from: classes2.dex */
public class RequestManage {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8324a = "RequestManage";

    /* renamed from: b, reason: collision with root package name */
    public AuthInfoListener f8325b;

    /* renamed from: c, reason: collision with root package name */
    public AuthManager f8326c;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final RequestManage f8327a = new RequestManage();
    }

    public RequestManage() {
        this.f8326c = AppEnv.IS_GENIE_ENV ? new DefaultAuthManager() : new FeiyanAuthManager();
    }

    public static RequestManage getInstance() {
        return a.f8327a;
    }

    public void authCheckAndGetBleKey(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull boolean z2, NetworkCallback<String> networkCallback) {
        if (this.f8326c == null) {
            networkCallback.onFailure("", "");
            return;
        }
        AuthInfoListener authInfoListener = this.f8325b;
        String authInfo = authInfoListener != null ? authInfoListener.getAuthInfo() : "";
        if (z2) {
            this.f8326c.authCipherCheckThenGetKeyForBLEDevice(authInfo, str, str2, str3, networkCallback);
        } else {
            this.f8326c.authCheckAndGetBleKey(authInfo, str, str2, str3, networkCallback);
        }
    }

    public void getAuthRandomId(@NonNull String str, @NonNull String str2, @NonNull boolean z2, NetworkCallback<String> networkCallback) {
        LogUtils.i(f8324a, "getAuthRandomId " + str);
        if (this.f8326c == null) {
            networkCallback.onFailure("", "");
            return;
        }
        AuthInfoListener authInfoListener = this.f8325b;
        String authInfo = authInfoListener != null ? authInfoListener.getAuthInfo() : "";
        if (z2) {
            this.f8326c.getAuthRandomIdForBLEDevice(authInfo, str, str2, networkCallback);
        } else {
            this.f8326c.getAuthRandomId(authInfo, str, str2, networkCallback);
        }
    }

    public void getDeviceUUIDViaProductId(@NonNull String str, @NonNull String str2, NetworkCallback<GetDeviceUUIDRespData> networkCallback) {
        if (this.f8326c == null) {
            networkCallback.onFailure(String.valueOf(-303), "Network not initialized");
        } else {
            AuthInfoListener authInfoListener = this.f8325b;
            this.f8326c.getDeviceUUID(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, networkCallback);
        }
    }

    public String getUserId() {
        AuthInfoListener authInfoListener = this.f8325b;
        if (authInfoListener == null) {
            LogUtils.e(f8324a, "mAuthInfoListener is null");
            return "";
        }
        try {
            JSONObject object = JSON.parseObject(authInfoListener.getAuthInfo());
            if (object != null) {
                return object.getString("userId");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public String getUtdId() {
        AuthInfoListener authInfoListener = this.f8325b;
        if (authInfoListener == null) {
            LogUtils.e(f8324a, "mAuthInfoListener is null");
            return "";
        }
        try {
            JSONObject object = JSON.parseObject(authInfoListener.getAuthInfo());
            if (object != null) {
                return object.getString("utdId");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public void gmaOtaProgressReport(@NonNull String str, @NonNull String str2, @NonNull String str3, NetworkCallback<OtaProgressRespData> networkCallback) {
        if (this.f8326c == null) {
            networkCallback.onFailure(String.valueOf(-303), "Network not initialized");
        } else {
            AuthInfoListener authInfoListener = this.f8325b;
            this.f8326c.gmaOtaProgressReport(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, str3, networkCallback);
        }
    }

    public void init(AuthInfoListener authInfoListener, AuthManager authManager) {
        LogUtils.d(f8324a, "init...");
        this.f8325b = authInfoListener;
        this.f8326c = authManager;
    }

    public void queryOtaInfo(@NonNull String str, @NonNull String str2, NetworkCallback<DeviceVersionInfo> networkCallback) {
        if (this.f8326c == null) {
            networkCallback.onFailure(String.valueOf(-303), "Network not initialized");
        } else {
            AuthInfoListener authInfoListener = this.f8325b;
            this.f8326c.queryOtaInfo(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, networkCallback);
        }
    }

    public void updateDeviceVersion(@NonNull String str, @NonNull String str2, NetworkCallback<UpdateDeviceVersionRespData> networkCallback) {
        if (this.f8326c == null) {
            networkCallback.onFailure(String.valueOf(-303), "Network not initialized");
        } else {
            AuthInfoListener authInfoListener = this.f8325b;
            this.f8326c.updateDeviceVersion(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, networkCallback);
        }
    }
}
