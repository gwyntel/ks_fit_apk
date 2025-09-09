package com.alibaba.sdk.android.openaccount.security.impl;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.Environment;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.config.ConfigService;
import com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener;
import com.alibaba.sdk.android.openaccount.message.MessageConstants;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.OAWSecurityData;
import com.alibaba.sdk.android.openaccount.model.OAWUAData;
import com.alibaba.sdk.android.openaccount.model.ResultCode;
import com.alibaba.sdk.android.openaccount.security.SecRuntimeException;
import com.alibaba.sdk.android.openaccount.security.SecurityGuardService;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ut.UTConstants;
import com.alibaba.sdk.android.openaccount.ut.UserTrackerService;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.dynamicdataencrypt.IDynamicDataEncryptComponent;
import com.alibaba.wireless.security.open.umid.IUMIDInitListenerEx;
import com.aliyun.alink.linksdk.securesigner.SecurityImpl;
import com.aliyun.alink.linksdk.securesigner.crypto.KeystoreSecureStorage;
import com.aliyun.alink.linksdk.securesigner.crypto.SecureStorageException;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.kingsmith.miot.KsProperty;
import com.taobao.wireless.security.sdk.securitybody.ISecurityBodyComponent;
import com.umeng.analytics.pro.f;
import com.umeng.commonsdk.framework.UMModuleRegister;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class SecurityGuardWrapper implements SecurityGuardService, EnvironmentChangeListener {
    public static final SecurityGuardWrapper INSTANCE = new SecurityGuardWrapper();
    private static final String TAG = "oa_security";

    @Autowired
    private ConfigService configService;
    private Context context;

    @Autowired
    private UserTrackerService userTrackerService;

    /* renamed from: com.alibaba.sdk.android.openaccount.security.impl.SecurityGuardWrapper$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$sdk$android$openaccount$Environment;

        static {
            int[] iArr = new int[Environment.values().length];
            $SwitchMap$com$alibaba$sdk$android$openaccount$Environment = iArr;
            try {
                iArr[Environment.ONLINE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$openaccount$Environment[Environment.PRE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$openaccount$Environment[Environment.TEST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private SecurityGuardWrapper() {
    }

    private static int convertEnvToMtop() {
        int i2 = AnonymousClass2.$SwitchMap$com$alibaba$sdk$android$openaccount$Environment[ConfigManager.getInstance().getEnvironment().ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                return 1;
            }
            if (i2 == 3) {
                return 2;
            }
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.alibaba.sdk.android.openaccount.model.ResultCode createResultCode(int r5, java.lang.Exception r6) {
        /*
            r4 = this;
            r0 = 1
            r1 = 0
            com.alibaba.sdk.android.openaccount.ConfigManager r2 = com.alibaba.sdk.android.openaccount.ConfigManager.getInstance()
            java.lang.String r2 = r2.getSecurityImagePostfix()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L13
            java.lang.String r2 = ""
            goto L2c
        L13:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "_"
            r2.append(r3)
            com.alibaba.sdk.android.openaccount.ConfigManager r3 = com.alibaba.sdk.android.openaccount.ConfigManager.getInstance()
            java.lang.String r3 = r3.getSecurityImagePostfix()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
        L2c:
            r3 = 102(0x66, float:1.43E-43)
            if (r5 == r3) goto L83
            r3 = 103(0x67, float:1.44E-43)
            if (r5 == r3) goto L83
            r3 = 106(0x6a, float:1.49E-43)
            if (r5 == r3) goto L83
            r3 = 212(0xd4, float:2.97E-43)
            if (r5 == r3) goto L78
            switch(r5) {
                case 202: goto L6d;
                case 203: goto L62;
                case 204: goto L78;
                case 205: goto L78;
                default: goto L3f;
            }
        L3f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r6.getMessage()
            r2.append(r3)
            java.lang.String r3 = " sec code "
            r2.append(r3)
            r2.append(r5)
            java.lang.String r5 = r2.toString()
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r1] = r5
            r5 = 10010(0x271a, float:1.4027E-41)
            com.alibaba.sdk.android.openaccount.message.Message r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.createMessage(r5, r0)
            goto L8b
        L62:
            r5 = 702(0x2be, float:9.84E-43)
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r1] = r2
            com.alibaba.sdk.android.openaccount.message.Message r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.createMessage(r5, r0)
            goto L8b
        L6d:
            r5 = 701(0x2bd, float:9.82E-43)
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r1] = r2
            com.alibaba.sdk.android.openaccount.message.Message r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.createMessage(r5, r0)
            goto L8b
        L78:
            r5 = 705(0x2c1, float:9.88E-43)
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r1] = r2
            com.alibaba.sdk.android.openaccount.message.Message r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.createMessage(r5, r0)
            goto L8b
        L83:
            java.lang.Object[] r5 = new java.lang.Object[r1]
            r0 = 704(0x2c0, float:9.87E-43)
            com.alibaba.sdk.android.openaccount.message.Message r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.createMessage(r0, r5)
        L8b:
            java.lang.String r0 = "security"
            com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.log(r0, r5, r6)
            com.alibaba.sdk.android.openaccount.model.ResultCode r5 = com.alibaba.sdk.android.openaccount.model.ResultCode.create(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.openaccount.security.impl.SecurityGuardWrapper.createResultCode(int, java.lang.Exception):com.alibaba.sdk.android.openaccount.model.ResultCode");
    }

    private SecurityGuardManager getSecurityGuardManager() {
        try {
            return SecurityGuardManager.getInstance(this.context);
        } catch (SecException e2) {
            e2.printStackTrace();
            throw new SecRuntimeException(e2.getErrorCode(), e2);
        }
    }

    private boolean isWeakSecurity() {
        return getSecurityGuardManager().getSDKVerison().contains("weak");
    }

    private void logSecurityGuardUTMessage(String str, boolean z2, String... strArr) {
        if ("true".equals(OpenAccountSDK.getProperty("disableSecurityGuardUT"))) {
            return;
        }
        try {
            if (this.userTrackerService != null) {
                HashMap map = new HashMap();
                map.put(UMModuleRegister.PROCESS, CommonUtils.getCurrentProcessName());
                if (strArr.length > 1) {
                    int length = strArr.length;
                    for (int i2 = 0; i2 < length; i2 += 2) {
                        map.put(strArr[i2], strArr[i2 + 1]);
                    }
                }
                map.put(f.M, getProviderName());
                this.userTrackerService.sendCustomHit(str, 0L, z2 ? "success" : "error", map);
            }
        } catch (Throwable unused) {
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public OAWSecurityData buildWSecurityData() {
        OAWSecurityData oAWSecurityData = new OAWSecurityData();
        OAWUAData wua = getWUA();
        if (wua != null) {
            oAWSecurityData.wua = wua.wua;
            oAWSecurityData.f8929t = wua.f8930t;
        }
        oAWSecurityData.umidToken = getSecurityToken();
        return oAWSecurityData;
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public String decrypt(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                IDynamicDataEncryptComponent dynamicDataEncryptComp = getSecurityGuardManager().getDynamicDataEncryptComp();
                if (dynamicDataEncryptComp != null) {
                    String strDynamicDecrypt = dynamicDataEncryptComp.dynamicDecrypt(str);
                    return TextUtils.isEmpty(strDynamicDecrypt) ? str : strDynamicDecrypt;
                }
            } catch (Exception unused) {
            }
        }
        return str;
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public String encode(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                IDynamicDataEncryptComponent dynamicDataEncryptComp = getSecurityGuardManager().getDynamicDataEncryptComp();
                if (dynamicDataEncryptComp != null) {
                    String strDynamicEncryptDDp = dynamicDataEncryptComp.dynamicEncryptDDp(str);
                    return TextUtils.isEmpty(strDynamicEncryptDDp) ? str : strDynamicEncryptDDp;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return str;
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public String getAppKey() {
        if (!TextUtils.isEmpty(ConfigManager.getInstance().getAppKey())) {
            return ConfigManager.getInstance().getAppKey();
        }
        if (!Utils.hasSecurityGuardDep()) {
            return new SecurityImpl().getAppKey();
        }
        try {
            return getSecurityGuardManager().getStaticDataStoreComp().getAppKeyByIndex(ConfigManager.getInstance().getAppKeyIndex(), ConfigManager.getInstance().getSecurityImagePostfix());
        } catch (SecException e2) {
            e2.printStackTrace();
            logSecurityGuardUTMessage(UTConstants.GET_APPKEY, false, "code", String.valueOf(e2.getErrorCode()));
            throw new SecRuntimeException(e2.getErrorCode(), e2);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    @RequiresApi(api = 19)
    public synchronized byte[] getByteArrayFromDynamicDataStore(String str) {
        try {
            if (!Utils.hasSecurityGuardDep()) {
                try {
                    return KeystoreSecureStorage.getInstance(this.context).get(str).getBytes(StandardCharsets.UTF_8);
                } catch (SecureStorageException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
            try {
                byte[] byteArray = getSecurityGuardManager().getDynamicDataStoreComp().getByteArray(str);
                logSecurityGuardUTMessage(UTConstants.GET_DYNAMIC_DATA_STORE, true, KsProperty.Key, str, "ret", byteArray == null ? "NULL" : "N-NULL");
                return byteArray;
            } catch (SecException e3) {
                e3.printStackTrace();
                logSecurityGuardUTMessage(UTConstants.GET_DYNAMIC_DATA_STORE, false, KsProperty.Key, str, "code", String.valueOf(e3.getErrorCode()));
                AliSDKLogger.e(TAG, "Sec Exception, the code = " + e3.getErrorCode(), e3);
                return null;
            }
        } catch (Throwable th) {
            throw th;
        }
        throw th;
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public String getProviderName() {
        return isWeakSecurity() ? "mini" : MessengerShareContentUtility.WEBVIEW_RATIO_FULL;
    }

    public String getSecurityBodyData(String str, String str2) {
        ISecurityBodyComponent securityBodyComp;
        try {
            com.taobao.wireless.security.sdk.SecurityGuardManager securityGuardManager = com.taobao.wireless.security.sdk.SecurityGuardManager.getInstance(this.context);
            if (securityGuardManager == null || (securityBodyComp = securityGuardManager.getSecurityBodyComp()) == null) {
                return null;
            }
            return securityBodyComp.getSecurityBodyData(str, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public String getSecurityToken() {
        return "";
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public synchronized String getValueFromDynamicDataStore(String str) {
        try {
            if (!Utils.hasSecurityGuardDep()) {
                try {
                    return KeystoreSecureStorage.getInstance(this.context).get(str);
                } catch (SecureStorageException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
            try {
                String string = getSecurityGuardManager().getDynamicDataStoreComp().getString(str);
                logSecurityGuardUTMessage(UTConstants.GET_DYNAMIC_DATA_STORE, true, KsProperty.Key, str, "ret", string == null ? "NULL" : "N-NULL");
                return string;
            } catch (SecException e3) {
                logSecurityGuardUTMessage(UTConstants.GET_DYNAMIC_DATA_STORE, false, KsProperty.Key, str, "code", String.valueOf(e3.getErrorCode()));
                AliSDKLogger.e(TAG, "Sec Exception, the code = " + e3.getErrorCode(), e3);
                return null;
            }
        } catch (Throwable th) {
            throw th;
        }
        throw th;
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public synchronized String getValueFromStaticDataStore(String str) {
        try {
            if (!Utils.hasSecurityGuardDep()) {
                try {
                    return KeystoreSecureStorage.getInstance(this.context).get(str);
                } catch (SecureStorageException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
            try {
                String extraData = getSecurityGuardManager().getStaticDataStoreComp().getExtraData(str, ConfigManager.getInstance().getSecurityImagePostfix());
                logSecurityGuardUTMessage(UTConstants.GET_STATIC_DATA_STORE, true, KsProperty.Key, str, "ret", extraData == null ? "NULL" : "N-NULL");
                return extraData;
            } catch (SecException e3) {
                e3.printStackTrace();
                logSecurityGuardUTMessage(UTConstants.GET_STATIC_DATA_STORE, false, KsProperty.Key, str, "code", String.valueOf(e3.getErrorCode()));
                AliSDKLogger.e(TAG, "Sec Exception, the code = " + e3.getErrorCode(), e3);
                return null;
            }
        } catch (Throwable th) {
            throw th;
        }
        throw th;
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public OAWUAData getWUA() {
        com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent iSecurityBodyComponent;
        if (Utils.hasSecurityGuardDep()) {
            try {
                SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(new ContextWrapper(OpenAccountSDK.getAndroidContext()));
                if (securityGuardManager == null || (iSecurityBodyComponent = (com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent) securityGuardManager.getInterface(com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent.class)) == null) {
                    return null;
                }
                String strValueOf = String.valueOf(System.currentTimeMillis());
                String appKey = INSTANCE.getAppKey();
                String securityBodyDataEx = iSecurityBodyComponent.getSecurityBodyDataEx(strValueOf, appKey, (String) null, (HashMap) null, 4, convertEnvToMtop());
                if (TextUtils.isEmpty(securityBodyDataEx)) {
                    securityBodyDataEx = getSecurityBodyData(strValueOf, appKey);
                }
                return new OAWUAData(getAppKey(), strValueOf, securityBodyDataEx);
            } catch (Error e2) {
                e2.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public ResultCode init(Context context) {
        this.context = context;
        if (Utils.hasSecurityGuardDep()) {
            try {
                if (!this.configService.getBooleanProperty("disableSecurityGuardInit", false)) {
                    SecurityGuardManager.getInitializer().initialize(context);
                    SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
                    if (securityGuardManager == null) {
                        return new ResultCode(MessageUtils.createMessage(MessageConstants.SECURITY_GUARD_INIT_EXCEPTION, new Object[0]));
                    }
                    if (ConfigManager.getInstance().getEnvironment().equals(Environment.TEST)) {
                        securityGuardManager.getUMIDComp().initUMID(INSTANCE.getAppKey(), 2, ConfigManager.getInstance().getSecurityImagePostfix(), new IUMIDInitListenerEx() { // from class: com.alibaba.sdk.android.openaccount.security.impl.SecurityGuardWrapper.1
                            public void onUMIDInitFinishedEx(String str, int i2) {
                            }
                        });
                    }
                }
                OpenAccountSDK.setProperty(OpenAccountConstants.APP_KEY, INSTANCE.getAppKey());
            } catch (SecRuntimeException e2) {
                e2.printStackTrace();
                return createResultCode(e2.getErrorCode(), e2);
            } catch (SecException e3) {
                e3.printStackTrace();
                return createResultCode(e3.getErrorCode(), e3);
            }
        }
        return ResultCode.create(MessageUtils.createMessage(100, new Object[0]));
    }

    @Override // com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener
    public void onEnvironmentChange(Environment environment, Environment environment2) {
        OpenAccountSDK.setProperty(OpenAccountConstants.APP_KEY, INSTANCE.getAppKey());
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public synchronized void putValueInDynamicDataStore(String str, String str2) {
        try {
            if (Utils.hasSecurityGuardDep()) {
                try {
                    logSecurityGuardUTMessage(UTConstants.PUT_DYNAMIC_DATA_STORE, true, KsProperty.Key, str, "value", str2 == null ? "NULL" : "N-NULL", "res", String.valueOf(getSecurityGuardManager().getDynamicDataStoreComp().putString(str, str2)));
                } catch (SecException e2) {
                    e2.printStackTrace();
                    logSecurityGuardUTMessage(UTConstants.PUT_DYNAMIC_DATA_STORE, false, KsProperty.Key, str, "value", str2 == null ? "NULL" : "N-NULL", "code", String.valueOf(e2.getErrorCode()));
                    throw new SecRuntimeException(e2.getErrorCode(), e2);
                }
            } else {
                try {
                    KeystoreSecureStorage.getInstance(this.context).put(str, str2);
                } catch (SecureStorageException e3) {
                    e3.printStackTrace();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public synchronized void removeValueFromDynamicDataStore(String str) {
        if (Utils.hasSecurityGuardDep()) {
            try {
                getSecurityGuardManager().getDynamicDataStoreComp().removeString(str);
                logSecurityGuardUTMessage(UTConstants.REMOVE_DYNAMIC_DATA_STORE, true, KsProperty.Key, str);
            } catch (SecException e2) {
                e2.printStackTrace();
                logSecurityGuardUTMessage(UTConstants.REMOVE_DYNAMIC_DATA_STORE, false, KsProperty.Key, str, "code", String.valueOf(e2.getErrorCode()));
                throw new SecRuntimeException(e2.getErrorCode(), e2);
            }
        } else {
            try {
                KeystoreSecureStorage.getInstance(this.context).remove(str);
            } catch (SecureStorageException e3) {
                e3.printStackTrace();
            }
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    public String signRequest(String str, int i2) {
        if (!Utils.hasSecurityGuardDep()) {
            return new SecurityImpl().sign(str, "");
        }
        SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
        securityGuardParamContext.paramMap.put("INPUT", str);
        securityGuardParamContext.appKey = OpenAccountSDK.getProperty(OpenAccountConstants.APP_KEY);
        securityGuardParamContext.requestType = i2;
        try {
            return getSecurityGuardManager().getSecureSignatureComp().signRequest(securityGuardParamContext, ConfigManager.getInstance().getSecurityImagePostfix());
        } catch (SecException e2) {
            e2.printStackTrace();
            logSecurityGuardUTMessage(UTConstants.SIGN_REQUEST, false, "type", String.valueOf(i2), "inputStr", str == null ? "NULL" : "N-NULL", "code", String.valueOf(e2.getErrorCode()));
            throw new SecRuntimeException(e2.getErrorCode(), e2);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.security.SecurityGuardService
    @RequiresApi(api = 19)
    public synchronized void putValueInDynamicDataStore(String str, byte[] bArr) {
        try {
            if (Utils.hasSecurityGuardDep()) {
                try {
                    logSecurityGuardUTMessage(UTConstants.PUT_DYNAMIC_DATA_STORE, true, KsProperty.Key, str, "value", bArr == null ? "NULL" : "N-NULL", "res", String.valueOf(getSecurityGuardManager().getDynamicDataStoreComp().putByteArray(str, bArr)));
                } catch (SecException e2) {
                    e2.printStackTrace();
                    logSecurityGuardUTMessage(UTConstants.PUT_DYNAMIC_DATA_STORE, false, KsProperty.Key, str, "value", bArr == null ? "NULL" : "N-NULL", "code", String.valueOf(e2.getErrorCode()));
                    throw new SecRuntimeException(e2.getErrorCode(), e2);
                }
            } else {
                try {
                    KeystoreSecureStorage.getInstance(this.context).put(str, new String(bArr, StandardCharsets.UTF_8));
                } catch (SecureStorageException e3) {
                    e3.printStackTrace();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
