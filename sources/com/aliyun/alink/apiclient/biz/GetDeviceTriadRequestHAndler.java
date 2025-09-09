package com.aliyun.alink.apiclient.biz;

import androidx.browser.trusted.sharing.ShareTarget;
import com.aliyun.alink.apiclient.CommonRequest;
import com.aliyun.alink.apiclient.CommonResponse;
import com.aliyun.alink.apiclient.IoTCallback;
import com.aliyun.alink.apiclient.LocalData;
import com.aliyun.alink.apiclient.model.DeviceAuthInfo;
import com.aliyun.alink.apiclient.utils.HmacSignUtils;
import com.aliyun.alink.apiclient.utils.ParameterHelper;
import com.aliyun.alink.apiclient.utils.StringUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.http.helper.HttpCallback;
import com.http.okhttp.OkHttpManager;
import com.http.utils.LogUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class GetDeviceTriadRequestHAndler implements IHandler {
    private static final String TAG = "GetDeviceTriadRequestHA";

    @Override // com.aliyun.alink.apiclient.biz.IHandler
    public void handle(final CommonRequest commonRequest, final IoTCallback ioTCallback) {
        LogUtils.print(TAG, "getDeviceSecret call()");
        if (ioTCallback == null) {
            LogUtils.error(TAG, "getDeviceSecret error callback=null.");
            return;
        }
        try {
            DeviceAuthInfo deviceData = LocalData.getInstance().getDeviceData();
            if ("/auth/register/device".equals(commonRequest.getPath())) {
                HashMap map = new HashMap();
                map.put("Content-Type", ShareTarget.ENCODING_TYPE_URL_ENCODED);
                TreeMap treeMap = new TreeMap();
                treeMap.put("productKey", deviceData.productKey);
                treeMap.put("deviceName", deviceData.deviceName);
                treeMap.put(AlinkConstants.KEY_RANDOM, ParameterHelper.getUniqueNonce());
                if (StringUtils.isEmptyString(deviceData.deviceSecret)) {
                    String hmacSign = HmacSignUtils.getHmacSign(treeMap, deviceData.productSecret);
                    treeMap.put(TmpConstant.KEY_SIGN_METHOD, MqttConfigure.SIGN_METHOD);
                    treeMap.put("sign", hmacSign);
                    OkHttpManager.getInstance().postAsync(RequestHelper.getBaseUrl(commonRequest, null), map, RequestHelper.getFormMapString(treeMap), new HttpCallback<IOException, String>() { // from class: com.aliyun.alink.apiclient.biz.GetDeviceTriadRequestHAndler.1
                        @Override // com.http.helper.HttpCallback
                        public void onFail(String str, IOException iOException) {
                            LogUtils.error(GetDeviceTriadRequestHAndler.TAG, "getDeviceSecret url=" + str + ",e=" + iOException);
                            ioTCallback.onFailure(commonRequest, iOException);
                        }

                        @Override // com.http.helper.HttpCallback
                        public void onSuccess(String str, String str2) {
                            LogUtils.error(GetDeviceTriadRequestHAndler.TAG, "getDeviceSecret url=" + str + ",result=" + str2);
                            CommonResponse commonResponse = new CommonResponse();
                            commonResponse.setData(str2);
                            ioTCallback.onResponse(commonRequest, commonResponse);
                        }
                    });
                } else {
                    ioTCallback.onFailure(commonRequest, new IllegalAccessException("deviceSecretNotEmpty"));
                }
            }
        } catch (Exception e2) {
            LogUtils.error(TAG, "getDeviceSecret failed. e=" + e2);
            ioTCallback.onFailure(commonRequest, e2);
        }
    }
}
