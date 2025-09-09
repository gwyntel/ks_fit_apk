package com.aliyun.iot.aep.sdk.credential.IotCredentialManager;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.aliyun.alink.linksdk.securesigner.crypto.KeystoreSecureStorage;
import com.aliyun.alink.linksdk.securesigner.crypto.SecureStorageException;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.credential.oa.OADepBiz;
import com.aliyun.iot.aep.sdk.credential.utils.ReflectUtils;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.facebook.share.internal.ShareConstants;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes3.dex */
public class IoTCredentialUtils {
    public static final String[] CREATE_IOTTOKEN_REQUEST_PATH_ARRAY = {"/user/account/employee/session/create", "/account/createsessionbyauthcodeforbiz", "/account/createSessionByAuthCode"};

    public static String dataEncrypt(Context context, String str, String str2, String str3) {
        if (Utils.hasSecurityGuardDep()) {
            if (TextUtils.isEmpty(IoTCredentialManageImpl.f11721a)) {
                IoTCredentialManageImpl.f11721a = "114d";
            }
            try {
                String strStaticSafeEncrypt = SecurityGuardManager.getInstance(context).getStaticDataEncryptComp().staticSafeEncrypt(16, IoTCredentialManageImpl.appKey, "identityId=" + str3 + "&iotToken=" + str + "&iotRefreshToken=" + str2, IoTCredentialManageImpl.f11721a);
                StringBuilder sb = new StringBuilder();
                sb.append("mobile_token=");
                sb.append(strStaticSafeEncrypt);
                sb.append("&appKey=");
                sb.append(IoTCredentialManageImpl.appKey);
                ALog.i("IoTCredential", sb.toString());
                return "mobile_token=" + strStaticSafeEncrypt + "&appKey=" + IoTCredentialManageImpl.appKey;
            } catch (SecException e2) {
                ALog.i("IoTCredential", "encrypt iottoken error:" + e2.toString());
            }
        }
        return "";
    }

    public static IoTRequest getCreateIoTCredentialRequest(String str, String str2, String str3, String str4) {
        IoTRequestBuilder ioTRequestBuilder = new IoTRequestBuilder();
        ioTRequestBuilder.setScheme(Scheme.HTTPS);
        if (TextUtils.equals(IoTCredentialManageImpl.COMPANY_TYPE, str3)) {
            if (TextUtils.isEmpty(str4)) {
                return null;
            }
            ioTRequestBuilder.addParam("authCode", str);
            ioTRequestBuilder.addParam("appKey", str2);
            ioTRequestBuilder.addParam("authCodeType", "ALIYUN_SESSION");
            ioTRequestBuilder.addParam("companyId", str4);
            ioTRequestBuilder.setPath("/user/account/employee/session/create");
            ioTRequestBuilder.setApiVersion("1.0.0");
        } else if (TextUtils.equals("aliyun", str3)) {
            if (!TextUtils.isEmpty(IoTCredentialManageImpl.DefaultDailyALiYunCreateIotTokenRequestHost) && ReflectUtils.hasOADep() && "TEST".equalsIgnoreCase(OADepBiz.getEnv())) {
                ioTRequestBuilder.setHost(IoTCredentialManageImpl.DefaultDailyALiYunCreateIotTokenRequestHost);
            }
            ioTRequestBuilder.addParam("authCode", str);
            ioTRequestBuilder.addParam("appKey", str2);
            ioTRequestBuilder.addParam("authCodeType", "ALIYUN_SESSION");
            ioTRequestBuilder.setPath("/account/createsessionbyauthcodeforbiz");
            ioTRequestBuilder.setApiVersion("1.0.0");
        } else {
            HashMap map = new HashMap();
            map.put("authCode", str);
            map.put("appKey", str2);
            map.put("accountType", "OA_SESSION");
            ioTRequestBuilder.setPath("account/createSessionByAuthCode");
            ioTRequestBuilder.setApiVersion("1.0.4").addParam(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, (Map) map);
        }
        return ioTRequestBuilder.build();
    }

    public static IoTRequest getInvalidSessionRequest(String str, String str2, String str3) {
        HashMap map = new HashMap();
        map.put("iotToken", str);
        map.put(IoTCredentialManageImpl.AUTH_IOT_TOKEN_IDENTITY_ID_KEY, str2);
        return new IoTRequestBuilder().setPath("/iotx/account/invalidSession").setApiVersion("1.0.4").addParam(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, (Map) map).build();
    }

    public static IoTCredentialData getIoTCredentialData(Context context) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        if (!Utils.hasSecurityGuardDep()) {
            try {
                String str = KeystoreSecureStorage.getInstance(context).get("KEY_IOT_CREDENTIAL_DATA");
                ALog.i("IoTCredential", "getTokenData():" + str);
                return !TextUtils.isEmpty(str) ? (IoTCredentialData) JSON.parseObject(str, IoTCredentialData.class) : new IoTCredentialData();
            } catch (SecureStorageException e2) {
                throw new RuntimeException(e2);
            }
        }
        try {
            SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
            if (securityGuardManager != null && (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) != null) {
                String string = dynamicDataStoreComp.getString("KEY_IOT_CREDENTIAL_DATA");
                ALog.i("IoTCredential", "getTokenData():" + string);
                if (!TextUtils.isEmpty(string)) {
                    return (IoTCredentialData) JSON.parseObject(string, IoTCredentialData.class);
                }
            }
        } catch (Exception e3) {
            ALog.i("IoTCredential", "getIoTCredentialData() : get iotCredentialData error:" + e3.toString());
        }
        return new IoTCredentialData();
    }

    public static IoTRequest getRefreshIoTCredentialRequest(String str, String str2) {
        HashMap map = new HashMap();
        map.put("refreshToken", str);
        map.put(IoTCredentialManageImpl.AUTH_IOT_TOKEN_IDENTITY_ID_KEY, str2);
        return new IoTRequestBuilder().setPath("account/checkOrRefreshSession").setApiVersion("1.0.4").addParam(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, (Map) map).build();
    }

    public static void saveIoTCredentialData(Context context, IoTCredentialData ioTCredentialData) {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        String jSONString = JSON.toJSONString(ioTCredentialData);
        if (!Utils.hasSecurityGuardDep()) {
            try {
                KeystoreSecureStorage.getInstance(context).put("KEY_IOT_CREDENTIAL_DATA", jSONString);
                return;
            } catch (SecureStorageException e2) {
                throw new RuntimeException(e2);
            }
        }
        if (ioTCredentialData != null) {
            try {
                SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
                if (securityGuardManager == null || (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) == null) {
                    return;
                }
                ALog.i("IoTCredential", "saveIoTCredentialData()" + jSONString);
                dynamicDataStoreComp.putString("KEY_IOT_CREDENTIAL_DATA", jSONString);
            } catch (Exception e3) {
                ALog.i("IoTCredential", "saveIoTCredentialData() error:" + e3.toString());
            }
        }
    }
}
