package com.aliyun.alink.linksdk.cmp.manager.connect.auth.mqtt;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alipay.sdk.m.t.a;
import com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.util.RandomStringUtil;
import com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ut.device.UTDevice;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class MobileAuthHttpRequest {
    private static final String TAG = "MobileAuthHttpRequest";
    private static String apiVersion = "1.0.0";
    private static String appkey = null;
    private static String authCode = "";
    private static String clientId = null;
    private static Context context = null;
    private static String deviceSn = null;
    private static final String path = "/app/aepauth/handle";

    public interface IAuthHttpRequestCallback {
        void onFailed(String str);

        void onSuceess(MobileTripleValue mobileTripleValue);
    }

    private static String appendStrs(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        int length = objArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            Object obj = objArr[i2];
            stringBuffer.append(obj != null ? obj.toString() : "");
        }
        return stringBuffer.toString();
    }

    public static void request(Context context2, String str, String str2, final IAuthHttpRequestCallback iAuthHttpRequestCallback) {
        String utdid;
        ALog.d(TAG, "request()");
        context = context2;
        appkey = str;
        authCode = str2;
        if (deviceSn == null) {
            try {
                utdid = UTDevice.getUtdid(context2);
            } catch (Throwable th) {
                ALog.e(TAG, "request(), utdid e:" + th.toString());
                utdid = null;
            }
            ALog.d(TAG, "request(), utdid = " + utdid);
            if (TextUtils.isEmpty(utdid)) {
                deviceSn = RandomStringUtil.getRandomString(32);
            } else {
                String strReplace = utdid.replace("/", "").replace(MqttTopic.SINGLE_LEVEL_WILDCARD, "").replace(ContainerUtils.KEY_VALUE_DELIMITER, "");
                deviceSn = strReplace + RandomStringUtil.getRandomString(32 - strReplace.length());
            }
        }
        ALog.d(TAG, "request(), deviceSn = " + deviceSn);
        if (clientId == null) {
            clientId = RandomStringUtil.getRandomString(8);
        }
        HashMap map = new HashMap();
        String str3 = System.currentTimeMillis() + "";
        map.put("appKey", appkey);
        map.put(a.f9743k, str3);
        map.put(TmpConstant.KEY_CLIENT_ID, clientId);
        map.put("deviceSn", deviceSn);
        String strSign = sign(map);
        ALog.d(TAG, "signed str = " + strSign);
        if (!TextUtils.isEmpty(strSign)) {
            map.put("sign", strSign);
        }
        map.remove("appKey");
        HashMap map2 = new HashMap();
        map2.put("authInfo", map);
        try {
            ConnectManager.getInstance().getApiGatewayConnect().send(ApiGatewayRequest.build(path, apiVersion, map2), new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.cmp.manager.connect.auth.mqtt.MobileAuthHttpRequest.1
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e(MobileAuthHttpRequest.TAG, "onErrorResponse(), error = " + aError.getMsg());
                    iAuthHttpRequestCallback.onFailed(aError.getCode() + "," + aError.getMsg());
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onResponse(),rsp = ");
                    sb.append((aResponse == null || (obj = aResponse.data) == null) ? "" : (String) obj);
                    ALog.d(MobileAuthHttpRequest.TAG, sb.toString());
                    try {
                        JSONObject object = JSON.parseObject((String) aResponse.data);
                        String string = object.getString("msg");
                        if (object.getIntValue("code") != 200) {
                            iAuthHttpRequestCallback.onFailed(string);
                            return;
                        }
                        String string2 = object.getJSONObject("data").getString("deviceName");
                        String string3 = object.getJSONObject("data").getString("deviceSecret");
                        String string4 = object.getJSONObject("data").getString("productKey");
                        if (TextUtils.isEmpty(string4) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
                            return;
                        }
                        iAuthHttpRequestCallback.onSuceess(new MobileTripleValue(string4, string2, string3));
                    } catch (Exception e2) {
                        ALog.e(MobileAuthHttpRequest.TAG, "onResponse(), error = " + e2.toString());
                        iAuthHttpRequestCallback.onFailed(e2.toString());
                    }
                }
            });
        } catch (Exception e2) {
            ALog.d(TAG, "request error, e = " + e2.toString());
            e2.printStackTrace();
            iAuthHttpRequestCallback.onFailed(e2.toString());
        }
    }

    private static String sign(Map<String, Object> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("appKey");
        arrayList.add(TmpConstant.KEY_CLIENT_ID);
        arrayList.add("deviceSn");
        arrayList.add(a.f9743k);
        String strAppendStrs = "";
        for (String str : arrayList) {
            strAppendStrs = appendStrs(strAppendStrs, str, map.get(str));
        }
        ALog.d(TAG, "sign(), toSignStr = " + strAppendStrs);
        try {
            ISecureSignatureComponent secureSignatureComp = SecurityGuardManager.getInstance(context).getSecureSignatureComp();
            HashMap map2 = new HashMap();
            map2.put("INPUT", strAppendStrs);
            SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
            securityGuardParamContext.appKey = appkey;
            securityGuardParamContext.paramMap = map2;
            securityGuardParamContext.requestType = 3;
            try {
                return secureSignatureComp.signRequest(securityGuardParamContext, authCode);
            } catch (SecException e2) {
                ALog.d(TAG, "sign(),signe req error,e" + e2.toString());
                e2.printStackTrace();
                return null;
            }
        } catch (SecException e3) {
            ALog.d(TAG, "sign(), create sg manager error, e" + e3.toString());
            e3.printStackTrace();
            return null;
        }
    }
}
