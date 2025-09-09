package com.alibaba.sdk.android.openaccount.rpc.cloudapi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.signature.ISignerFactory;
import com.alibaba.cloudapi.sdk.signature.ISinger;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alibaba.wireless.security.open.staticdatastore.IStaticDataStoreComponent;
import com.aliyun.alink.linksdk.securesigner.SecurityImpl;
import com.aliyun.iot.aep.sdk.apiclient.adapter.APIGatewayHttpAdapterImpl;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class SecuritySignerFactory implements ISignerFactory {
    public static final String METHOD = "HmacSHA1";
    private static AtomicBoolean hasApiGatewayDep = new AtomicBoolean(false);
    private static AtomicBoolean hasCheckDep = new AtomicBoolean(false);
    private static ISinger singer;

    private static class SecuritySigner implements ISinger {
        private static SecurityImpl securityImpl;
        String appKey;
        Context applicationContext;

        public SecuritySigner(Context context) {
            securityImpl = new SecurityImpl();
            this.applicationContext = context;
            this.appKey = getAppKey(context);
        }

        static String getAppKey(Context context) {
            String appKey;
            SecurityGuardManager securityGuardManager;
            IStaticDataStoreComponent staticDataStoreComp;
            if (SecuritySignerFactory.hasApiGatewayDep() && SecuritySignerFactory.hasSecurityGuardDep()) {
                appKey = APIGatewayHttpAdapterImpl.getAppKey(context, ConfigManager.getInstance().getSecurityImagePostfix());
            } else if (SecuritySignerFactory.hasSecurityGuardDep()) {
                AliSDKLogger.d("OA-TAG", "no apigateway dep, donot support set index.");
                try {
                    securityGuardManager = SecurityGuardManager.getInstance(context);
                } catch (SecException e2) {
                    e2.printStackTrace();
                    securityGuardManager = null;
                }
                if (securityGuardManager == null || (staticDataStoreComp = securityGuardManager.getStaticDataStoreComp()) == null) {
                    appKey = "";
                } else {
                    try {
                        appKey = staticDataStoreComp.getAppKeyByIndex(0, ConfigManager.getInstance().getSecurityImagePostfix());
                    } catch (SecException e3) {
                        e3.printStackTrace();
                    }
                }
            } else {
                appKey = securityImpl.getAppKey();
            }
            if (TextUtils.isEmpty(appKey)) {
                throw new RuntimeException("can not read appKey");
            }
            return appKey;
        }

        public static String hexStr2Base64Str(String str) {
            int length = str.length() / 2;
            if (1 == length) {
                throw new RuntimeException("invalid hex string : " + str);
            }
            byte[] bArr = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = i2 * 2;
                bArr[i2] = (byte) (Integer.valueOf(str.substring(i3, i3 + 2), 16).intValue() & 255);
            }
            return new String(Base64.encode(bArr, 0), SdkConstant.CLOUDAPI_ENCODING);
        }

        @Override // com.alibaba.cloudapi.sdk.signature.ISinger
        public String sign(String str, String str2) {
            if (!SecuritySignerFactory.hasSecurityGuardDep()) {
                if (securityImpl.getAppKey() != null) {
                    return hexStr2Base64Str(securityImpl.sign(str, "HmacSHA1"));
                }
                throw new RuntimeException("can not get appKey or appSecret");
            }
            try {
                ISecureSignatureComponent secureSignatureComp = SecurityGuardManager.getInstance(this.applicationContext).getSecureSignatureComp();
                HashMap map = new HashMap();
                map.put("INPUT", str);
                SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
                securityGuardParamContext.appKey = this.appKey;
                securityGuardParamContext.paramMap = map;
                securityGuardParamContext.requestType = 3;
                Log.d("own_sign", "signRequest: " + ((String) null));
                try {
                    String strSignRequest = secureSignatureComp.signRequest(securityGuardParamContext, ConfigManager.getInstance().getSecurityImagePostfix());
                    Log.d("wxbb_sign", "signRequest: " + strSignRequest);
                    return hexStr2Base64Str(strSignRequest);
                } catch (SecException unused) {
                    throw new RuntimeException();
                }
            } catch (SecException unused2) {
                throw new RuntimeException();
            }
        }
    }

    public SecuritySignerFactory(Context context) {
        if (hasSecurityGuardDep()) {
            try {
                SecurityGuardManager.getInitializer().initialize(context);
            } catch (SecException e2) {
                e2.printStackTrace();
            }
        }
        singer = new SecuritySigner(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasApiGatewayDep() {
        if (hasCheckDep.get()) {
            return hasApiGatewayDep.get();
        }
        try {
            if (hasCheckDep.compareAndSet(false, true)) {
                SecurityImpl securityImpl = APIGatewayHttpAdapterImpl.f11582d;
            }
            return true;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return false;
        } catch (Exception e3) {
            e3.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasSecurityGuardDep() throws ClassNotFoundException {
        try {
            Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
            return true;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return false;
        } catch (Exception e3) {
            e3.printStackTrace();
            return false;
        }
    }

    @Override // com.alibaba.cloudapi.sdk.signature.ISignerFactory
    public ISinger getSigner() {
        return singer;
    }
}
