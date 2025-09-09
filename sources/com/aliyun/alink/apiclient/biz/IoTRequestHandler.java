package com.aliyun.alink.apiclient.biz;

import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.aliyun.alink.apiclient.CommonRequest;
import com.aliyun.alink.apiclient.CommonResponse;
import com.aliyun.alink.apiclient.IoTCallback;
import com.aliyun.alink.apiclient.LocalData;
import com.aliyun.alink.apiclient.model.DeviceAuthInfo;
import com.aliyun.alink.apiclient.model.DeviceResponse;
import com.aliyun.alink.apiclient.utils.GsonUtils;
import com.aliyun.alink.apiclient.utils.HmacSignUtils;
import com.aliyun.alink.apiclient.utils.ParameterHelper;
import com.aliyun.alink.apiclient.utils.StringUtils;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.gson.reflect.TypeToken;
import com.http.helper.HttpCallback;
import com.http.helper.HttpFailCode;
import com.http.okhttp.OkHttpManager;
import com.http.utils.LogUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class IoTRequestHandler implements IHandler {
    private static final String TAG = "IoTRequestHandler";

    private void getDeviceAuthToken(final CommonRequest commonRequest, final IoTCallback ioTCallback) {
        LogUtils.print(TAG, "getDeviceSecret call()");
        final String baseUrl = RequestHelper.getBaseUrl(commonRequest, "/auth");
        try {
            HashMap map = new HashMap();
            map.put("Content-Type", "application/json");
            DeviceAuthInfo deviceData = LocalData.getInstance().getDeviceData();
            TreeMap treeMap = new TreeMap();
            treeMap.put("productKey", deviceData.productKey);
            treeMap.put("deviceName", deviceData.deviceName);
            treeMap.put(TmpConstant.KEY_CLIENT_ID, ParameterHelper.getUniqueNonce());
            String hmacSign = HmacSignUtils.getHmacSign(treeMap, deviceData.deviceSecret);
            treeMap.put("signmethod", MqttConfigure.SIGN_METHOD);
            treeMap.put("sign", hmacSign);
            if (ioTCallback == null) {
                LogUtils.error(TAG, "getDeviceAuthToken failed, callback=null.");
            } else {
                OkHttpManager.getInstance().postAsync(baseUrl, map, GsonUtils.toJsonString(treeMap), new HttpCallback<IOException, String>() { // from class: com.aliyun.alink.apiclient.biz.IoTRequestHandler.2
                    @Override // com.http.helper.HttpCallback
                    public void onFail(String str, IOException iOException) {
                        LogUtils.error(IoTRequestHandler.TAG, "getDeviceAuthToken onFail url=" + str + ", e=" + iOException);
                        ioTCallback.onFailure(commonRequest, iOException);
                    }

                    @Override // com.http.helper.HttpCallback
                    public void onSuccess(String str, String str2) {
                        LogUtils.print(IoTRequestHandler.TAG, "getDeviceAuthToken onSuccess url=" + str + ", result=" + str2);
                        if (StringUtils.isEmptyString(str2)) {
                            ioTCallback.onFailure(commonRequest, new IOException("onSuccessTokenNull", new HttpFailCode(0)));
                            return;
                        }
                        DeviceResponse deviceResponse = (DeviceResponse) GsonUtils.parseJson(str2, new TypeToken<DeviceResponse<Map<String, String>>>() { // from class: com.aliyun.alink.apiclient.biz.IoTRequestHandler.2.1
                        }.getType());
                        if (deviceResponse == null || deviceResponse.getInfo() == null || !((Map) deviceResponse.getInfo()).containsKey("token")) {
                            LogUtils.error(IoTRequestHandler.TAG, "getDeviceAuthToken failed.");
                            ioTCallback.onFailure(commonRequest, new IOException("onSuccessTokenNull", new HttpFailCode(0)));
                            return;
                        }
                        String str3 = (String) ((Map) deviceResponse.getInfo()).get("token");
                        LocalData.getInstance().setAuthToken(str3);
                        if ("/auth".equals(commonRequest.getPath())) {
                            CommonResponse commonResponse = new CommonResponse();
                            commonResponse.setData(str3);
                            ioTCallback.onResponse(commonRequest, commonResponse);
                        } else {
                            if (StringUtils.isEmptyString(str3)) {
                                ioTCallback.onFailure(commonRequest, new IOException("getAuthTokenFailed", new HttpFailCode(0)));
                                return;
                            }
                            HashMap map2 = new HashMap();
                            map2.put("Content-Type", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE);
                            map2.put("password", str3);
                            IoTRequestHandler.this.sendIoIRequest(baseUrl, commonRequest, ioTCallback);
                        }
                    }
                });
            }
        } catch (Exception e2) {
            LogUtils.error(TAG, "getDeviceSecret failed. e=" + e2);
            ioTCallback.onFailure(commonRequest, e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0040 A[Catch: IOException -> 0x003c, TRY_LEAVE, TryCatch #0 {IOException -> 0x003c, blocks: (B:3:0x0002, B:6:0x0009, B:10:0x0040), top: B:15:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void sendIoIRequest(java.lang.String r6, final com.aliyun.alink.apiclient.CommonRequest r7, final com.aliyun.alink.apiclient.IoTCallback r8) {
        /*
            r5 = this;
            if (r7 == 0) goto L3e
            java.util.Map r0 = r7.getQueryParams()     // Catch: java.io.IOException -> L3c
            if (r0 != 0) goto L9
            goto L3e
        L9:
            com.aliyun.alink.apiclient.CommonResponse r0 = new com.aliyun.alink.apiclient.CommonResponse     // Catch: java.io.IOException -> L3c
            r0.<init>()     // Catch: java.io.IOException -> L3c
            java.util.HashMap r1 = new java.util.HashMap     // Catch: java.io.IOException -> L3c
            r1.<init>()     // Catch: java.io.IOException -> L3c
            java.lang.String r2 = "Content-Type"
            java.lang.String r3 = "application/octet-stream"
            r1.put(r2, r3)     // Catch: java.io.IOException -> L3c
            java.lang.String r2 = "password"
            com.aliyun.alink.apiclient.LocalData r3 = com.aliyun.alink.apiclient.LocalData.getInstance()     // Catch: java.io.IOException -> L3c
            java.lang.String r3 = r3.getAuthToken()     // Catch: java.io.IOException -> L3c
            r1.put(r2, r3)     // Catch: java.io.IOException -> L3c
            java.util.Map r2 = r7.getQueryParams()     // Catch: java.io.IOException -> L3c
            java.lang.String r2 = com.aliyun.alink.apiclient.utils.GsonUtils.toJsonString(r2)     // Catch: java.io.IOException -> L3c
            com.http.okhttp.OkHttpManager r3 = com.http.okhttp.OkHttpManager.getInstance()     // Catch: java.io.IOException -> L3c
            com.aliyun.alink.apiclient.biz.IoTRequestHandler$1 r4 = new com.aliyun.alink.apiclient.biz.IoTRequestHandler$1     // Catch: java.io.IOException -> L3c
            r4.<init>()     // Catch: java.io.IOException -> L3c
            r3.postAsync(r6, r1, r2, r4)     // Catch: java.io.IOException -> L3c
            goto L64
        L3c:
            r6 = move-exception
            goto L4b
        L3e:
            if (r8 == 0) goto L64
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException     // Catch: java.io.IOException -> L3c
            java.lang.String r0 = "queryParamsNull"
            r6.<init>(r0)     // Catch: java.io.IOException -> L3c
            r8.onFailure(r7, r6)     // Catch: java.io.IOException -> L3c
            goto L64
        L4b:
            r6.printStackTrace()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "sendSync error, e="
            r7.append(r8)
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            java.lang.String r7 = "IoTRequestHandler"
            com.http.utils.LogUtils.error(r7, r6)
        L64:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.apiclient.biz.IoTRequestHandler.sendIoIRequest(java.lang.String, com.aliyun.alink.apiclient.CommonRequest, com.aliyun.alink.apiclient.IoTCallback):void");
    }

    @Override // com.aliyun.alink.apiclient.biz.IHandler
    public void handle(CommonRequest commonRequest, IoTCallback ioTCallback) {
        if (ioTCallback == null) {
            LogUtils.error(TAG, "IoTRequestHandler handle failed, callback=null.");
            return;
        }
        String baseUrl = RequestHelper.getBaseUrl(commonRequest, null);
        synchronized (this) {
            try {
                if (StringUtils.isEmptyString(LocalData.getInstance().getAuthToken())) {
                    getDeviceAuthToken(commonRequest, ioTCallback);
                } else {
                    if (!"/auth".equals(commonRequest.getPath())) {
                        sendIoIRequest(baseUrl, commonRequest, ioTCallback);
                        return;
                    }
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setData(LocalData.getInstance().getAuthToken());
                    ioTCallback.onResponse(commonRequest, commonResponse);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
