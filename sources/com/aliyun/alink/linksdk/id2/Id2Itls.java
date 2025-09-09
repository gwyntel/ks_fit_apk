package com.aliyun.alink.linksdk.id2;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class Id2Itls {
    static final String ARM64_SO_PATH = "/system/lib64/";
    static final String ARM_SO_PATH = "/system/lib/";
    static final String TAG = "Id2Itls";
    static final String libName = "android_id2";
    private AtomicBoolean id2ClientInited = new AtomicBoolean(false);

    static {
        try {
            ALog.d(TAG, "id2 sdk version 1.0.2.");
            System.loadLibrary(libName);
        } catch (Exception e2) {
            ALog.e(TAG, e2.toString());
            e2.printStackTrace();
        }
        initItlsSo();
    }

    protected static void initItlsSo() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            ClassLoader classLoader = Id2ItlsSdk.mContext.getClassLoader();
            Method declaredMethod = Class.forName("java.lang.ClassLoader").getDeclaredMethod("findLibrary", String.class);
            declaredMethod.setAccessible(true);
            String str = (String) declaredMethod.invoke(classLoader, libName);
            ALog.d(TAG, "soPath:" + str + " mSoPath:" + Id2ItlsSdk.mSoPath);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            String strSubstring = str.substring(0, str.lastIndexOf("/") + 1);
            String str2 = Id2ItlsSdk.mSoPath;
            if (TextUtils.isEmpty(str2)) {
                str2 = strSubstring.contains("arm64") ? ARM64_SO_PATH : ARM_SO_PATH;
            }
            nativeInitSo(strSubstring, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.d(TAG, e2.toString());
        }
    }

    private native void nativeDestroyItls(long j2);

    private native long nativeEstablishItls(String str, int i2, String str2, String str3);

    private native int nativeGetAlertType();

    private native String nativeGetId();

    private native String nativeGetTimestampAuthCode(String str, String str2);

    private native void nativeId2Init();

    private static native void nativeInitSo(String str, String str2);

    private native int nativeRead(long j2, byte[] bArr, int i2, int i3);

    private native void nativeSetItlsDebugLevel(int i2);

    private native void nativeSetLogLevel(int i2);

    private native int nativeWrite(long j2, byte[] bArr, int i2, int i3);

    public void destroyItls(long j2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "destroyItls handle:" + j2);
        this.id2ClientInited.set(false);
        nativeDestroyItls(j2);
    }

    public long establishItls(String str, int i2, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long jNativeEstablishItls = nativeEstablishItls(str, i2, str2, str3);
        ALog.d(TAG, "establishItls host:" + str + " port:" + i2 + " productKey:" + str2 + " handle:" + jNativeEstablishItls);
        return jNativeEstablishItls;
    }

    public int getAlertType() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int iNativeGetAlertType = nativeGetAlertType();
        ALog.d(TAG, "getAlertType alertType:" + iNativeGetAlertType);
        return iNativeGetAlertType;
    }

    public String getID2Id() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getID2Id() called");
        String strNativeGetId = null;
        try {
            if (this.id2ClientInited.compareAndSet(false, true)) {
                ALog.d(TAG, "id2 client init() called.");
                nativeId2Init();
            }
            strNativeGetId = nativeGetId();
            ALog.d(TAG, "id2=" + strNativeGetId);
            return strNativeGetId;
        } catch (Throwable th) {
            ALog.w(TAG, "getID2Id exception=" + th);
            th.printStackTrace();
            return strNativeGetId;
        }
    }

    public String getTimestampAuthCode(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String strNativeGetTimestampAuthCode;
        ALog.d(TAG, "getTimestampAuthCode() called");
        try {
            strNativeGetTimestampAuthCode = nativeGetTimestampAuthCode(str, str2);
        } catch (Throwable th) {
            th = th;
            strNativeGetTimestampAuthCode = null;
        }
        try {
            ALog.d(TAG, "authCode=" + strNativeGetTimestampAuthCode);
        } catch (Throwable th2) {
            th = th2;
            ALog.w(TAG, "getTimestampAuthCode exception=" + th);
            th.printStackTrace();
            return strNativeGetTimestampAuthCode;
        }
        return strNativeGetTimestampAuthCode;
    }

    public int itlsRead(long j2, byte[] bArr, int i2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "itlsRead handle:" + j2 + " buf:" + bArr + " len:" + i2 + " timeout_ms:" + i3);
        return nativeRead(j2, bArr, i2, i3);
    }

    public int itlsWrite(long j2, byte[] bArr, int i2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "itlsWrite handle:" + j2 + " buf:" + bArr + " len:" + i2 + " timeout_ms:" + i3);
        return nativeWrite(j2, bArr, i2, i3);
    }

    public void setItlsDebugLevel(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setItlsDebugLevel debugLevel:" + i2);
        nativeSetItlsDebugLevel(i2);
    }

    public void setJniDebugLevel(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setJniDebugLevel level:" + i2);
        nativeSetLogLevel(i2);
    }
}
