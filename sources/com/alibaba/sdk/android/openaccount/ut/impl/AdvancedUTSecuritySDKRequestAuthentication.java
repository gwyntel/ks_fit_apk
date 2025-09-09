package com.alibaba.sdk.android.openaccount.ut.impl;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes2.dex */
public class AdvancedUTSecuritySDKRequestAuthentication implements IUTRequestAuthentication {
    private static final String TAG = "ut";
    private String appKey;
    private Context context;
    private boolean initialized;
    private Object securityGuardManager = null;
    private Object securitySignatureComponent = null;
    private Class<?> securityGuardParamContextClazz = null;
    private Field contextAppKeyField = null;
    private Field contextParamMapField = null;
    private Field contextRequestTypeField = null;
    private Method signRequestMethod = null;

    public AdvancedUTSecuritySDKRequestAuthentication(String str, Context context) {
        this.appKey = str;
        this.context = context;
        init();
    }

    private void init() {
        try {
            Class<?> cls = Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
            this.securityGuardManager = cls.getMethod("getInstance", Context.class).invoke(null, this.context);
            this.securitySignatureComponent = cls.getMethod("getSecureSignatureComp", null).invoke(this.securityGuardManager, null);
            try {
                Class<?> cls2 = Class.forName("com.alibaba.wireless.security.open.SecurityGuardParamContext");
                this.securityGuardParamContextClazz = cls2;
                this.contextAppKeyField = cls2.getDeclaredField("appKey");
                this.contextParamMapField = this.securityGuardParamContextClazz.getDeclaredField("paramMap");
                this.contextRequestTypeField = this.securityGuardParamContextClazz.getDeclaredField("requestType");
                this.signRequestMethod = Class.forName("com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent").getMethod("signRequest", this.securityGuardParamContextClazz, String.class);
                this.initialized = true;
            } catch (Throwable th) {
                Log.e(TAG, "Fail to init UT, the error message is " + th.getMessage());
                th.printStackTrace();
            }
        } catch (Throwable th2) {
            Log.e(TAG, "Fail to load security signature component", th2);
        }
    }

    public String getAppkey() {
        return this.appKey;
    }

    public String getSign(String str) throws IllegalAccessException, InstantiationException, IllegalArgumentException {
        if (this.appKey == null) {
            Log.e(TAG, "UTSecuritySDKRequestAuthentication:getSign, There is no appkey,please check it!");
            return null;
        }
        if (str == null || !this.initialized) {
            return null;
        }
        try {
            Object objNewInstance = this.securityGuardParamContextClazz.newInstance();
            this.contextAppKeyField.set(objNewInstance, this.appKey);
            ((Map) this.contextParamMapField.get(objNewInstance)).put("INPUT", str);
            this.contextRequestTypeField.set(objNewInstance, 1);
            return (String) this.signRequestMethod.invoke(this.securitySignatureComponent, objNewInstance, ConfigManager.getInstance().getSecurityImagePostfix());
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
