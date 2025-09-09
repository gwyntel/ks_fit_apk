package com.aliyun.alink.linksdk.tmp.utils;

import android.util.Base64;
import com.aliyun.alink.linksdk.tmp.data.auth.AccessInfo;
import com.aliyun.alink.linksdk.tmp.data.auth.AuthPairData;
import com.aliyun.alink.linksdk.tmp.data.auth.ServerEncryptInfo;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class AuthInfoCreater {
    private static final int AUTH_CODE_LENGTH = 8;
    public static final String AUTH_DEFAULT_SEQ = "001";
    public static final String AUTH_VER = "1";
    private static final String ID_POST = "AuthInfoCreater";
    private static final int SECRET_LENGTH = 16;
    private static final String TAG = "[Tmp]AuthInfoCreater";
    private static Map<String, AuthPairData> mExistAuthPairs = new HashMap();

    private static class InstanceHolder {
        private static AuthInfoCreater mInstance = new AuthInfoCreater();

        private InstanceHolder() {
        }
    }

    private AuthPairData getExistedServerPair(String str) {
        AuthPairData authPairData = mExistAuthPairs.get(str);
        if (authPairData != null) {
            return authPairData;
        }
        ServerEncryptInfo serverEnptInfo = TmpStorage.getInstance().getServerEnptInfo(str + ID_POST, "local");
        AccessInfo accessInfo = TmpStorage.getInstance().getAccessInfo(str + ID_POST, "local");
        if (serverEnptInfo != null && accessInfo != null) {
            authPairData = new AuthPairData(accessInfo.mAccessKey, accessInfo.mAccessToken, serverEnptInfo.mPrefix, serverEnptInfo.mSecret);
        }
        mExistAuthPairs.put(str, authPairData);
        return authPairData;
    }

    public static AuthInfoCreater getInstance() {
        return InstanceHolder.mInstance;
    }

    private void saveAuthPair(String str, AuthPairData authPairData) throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
        mExistAuthPairs.put(str, authPairData);
        TmpStorage.getInstance().saveAccessInfo(str + ID_POST, authPairData.accessKey, authPairData.accessToken, true, "local");
        TmpStorage.getInstance().saveServerEnptInfo(str + ID_POST, authPairData.authCode, authPairData.authSecret, "local");
    }

    public AccessInfo createAccessInfo(String str, String str2, String str3) {
        AccessInfo accessInfo = new AccessInfo();
        String str4 = str + "1" + str3 + "001";
        accessInfo.mAccessKey = str4;
        accessInfo.mAccessToken = Base64.encodeToString(Secure.getHMacSha1Str(str4, str2), 2);
        return accessInfo;
    }

    public AuthPairData createAuthInfo(String str, String str2, String str3) throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
        String strCombineStr = TextHelper.combineStr(str, str2, str3);
        AuthPairData existedServerPair = getExistedServerPair(strCombineStr);
        if (existedServerPair != null) {
            return existedServerPair;
        }
        String randomString = TextHelper.getRandomString(8);
        String randomString2 = TextHelper.getRandomString(16);
        AccessInfo accessInfoCreateAccessInfo = createAccessInfo(randomString, randomString2, str3);
        AuthPairData authPairData = new AuthPairData(accessInfoCreateAccessInfo.mAccessKey, accessInfoCreateAccessInfo.mAccessToken, randomString, randomString2);
        ALog.d(TAG, "authCode:" + randomString + " secret:" + randomString2 + " ak:" + accessInfoCreateAccessInfo.mAccessKey + " at:" + accessInfoCreateAccessInfo.mAccessToken);
        saveAuthPair(strCombineStr, authPairData);
        return authPairData;
    }
}
