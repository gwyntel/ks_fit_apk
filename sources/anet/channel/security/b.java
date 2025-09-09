package anet.channel.security;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.util.ALog;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
class b implements ISecurity {

    /* renamed from: a, reason: collision with root package name */
    private static String f6892a = "awcn.DefaultSecurityGuard";

    /* renamed from: b, reason: collision with root package name */
    private static boolean f6893b = false;

    /* renamed from: c, reason: collision with root package name */
    private static Map<String, Integer> f6894c;

    /* renamed from: d, reason: collision with root package name */
    private String f6895d;

    static {
        try {
            Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
            f6893b = true;
            HashMap map = new HashMap();
            f6894c = map;
            map.put(ISecurity.SIGN_ALGORITHM_HMAC_SHA1, 3);
            f6894c.put(ISecurity.CIPHER_ALGORITHM_AES128, 16);
        } catch (Throwable unused) {
            f6893b = false;
        }
    }

    b(String str) {
        this.f6895d = str;
    }

    @Override // anet.channel.security.ISecurity
    public byte[] decrypt(Context context, String str, String str2, byte[] bArr) {
        Integer num;
        IStaticDataEncryptComponent staticDataEncryptComp;
        if (!f6893b || context == null || bArr == null || TextUtils.isEmpty(str2) || !f6894c.containsKey(str) || (num = f6894c.get(str)) == null) {
            return null;
        }
        try {
            SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
            if (securityGuardManager != null && (staticDataEncryptComp = securityGuardManager.getStaticDataEncryptComp()) != null) {
                return staticDataEncryptComp.staticBinarySafeDecryptNoB64(num.intValue(), str2, bArr, this.f6895d);
            }
        } catch (Throwable th) {
            ALog.e(f6892a, "staticBinarySafeDecryptNoB64", null, th, new Object[0]);
        }
        return null;
    }

    @Override // anet.channel.security.ISecurity
    public byte[] getBytes(Context context, String str) {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
            if (securityGuardManager == null || (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) == null) {
                return null;
            }
            return dynamicDataStoreComp.getByteArray(str);
        } catch (Throwable th) {
            ALog.e(f6892a, "getBytes", null, th, new Object[0]);
            return null;
        }
    }

    @Override // anet.channel.security.ISecurity
    public boolean isSecOff() {
        return false;
    }

    @Override // anet.channel.security.ISecurity
    public boolean saveBytes(Context context, String str, byte[] bArr) {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        if (context == null || bArr == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
            if (securityGuardManager == null || (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) == null) {
                return false;
            }
            return dynamicDataStoreComp.putByteArray(str, bArr) != 0;
        } catch (Throwable th) {
            ALog.e(f6892a, "saveBytes", null, th, new Object[0]);
            return false;
        }
    }

    @Override // anet.channel.security.ISecurity
    public String sign(Context context, String str, String str2, String str3) {
        if (f6893b && context != null && !TextUtils.isEmpty(str2) && f6894c.containsKey(str)) {
            try {
                ISecureSignatureComponent secureSignatureComp = SecurityGuardManager.getInstance(context).getSecureSignatureComp();
                if (secureSignatureComp != null) {
                    SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
                    securityGuardParamContext.appKey = str2;
                    securityGuardParamContext.paramMap.put("INPUT", str3);
                    securityGuardParamContext.requestType = f6894c.get(str).intValue();
                    return secureSignatureComp.signRequest(securityGuardParamContext, this.f6895d);
                }
            } catch (Throwable th) {
                ALog.e(f6892a, "Securityguard sign request failed.", null, th, new Object[0]);
            }
        }
        return null;
    }
}
