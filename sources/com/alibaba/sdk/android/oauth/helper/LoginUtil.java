package com.alibaba.sdk.android.oauth.helper;

import com.alibaba.sdk.android.openaccount.Environment;
import com.alibaba.sdk.android.openaccount.config.OpenAccountProvider;
import com.taobao.login4android.constants.LoginEnvType;
import com.taobao.login4android.login.DefaultTaobaoAppProvider;

/* loaded from: classes2.dex */
public class LoginUtil {

    /* renamed from: com.alibaba.sdk.android.oauth.helper.LoginUtil$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$sdk$android$openaccount$Environment;

        static {
            int[] iArr = new int[Environment.values().length];
            $SwitchMap$com$alibaba$sdk$android$openaccount$Environment = iArr;
            try {
                iArr[Environment.TEST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$openaccount$Environment[Environment.PRE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$openaccount$Environment[Environment.SANDBOX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static DefaultTaobaoAppProvider convert(OpenAccountProvider openAccountProvider) {
        DefaultTaobaoAppProvider defaultTaobaoAppProvider = new DefaultTaobaoAppProvider();
        if (openAccountProvider != null) {
            defaultTaobaoAppProvider.setContext(openAccountProvider.getContext());
            defaultTaobaoAppProvider.setAppkey(openAccountProvider.getAppKey());
            defaultTaobaoAppProvider.setDeviceId(openAccountProvider.getDeviceId());
            defaultTaobaoAppProvider.setProductVersion(openAccountProvider.getProductVersion());
            defaultTaobaoAppProvider.setTTID(openAccountProvider.getTTID());
            defaultTaobaoAppProvider.setTID(openAccountProvider.getTID());
            defaultTaobaoAppProvider.setThreadPoolExecutor(openAccountProvider.getThreadPool());
            defaultTaobaoAppProvider.setForceShowPwdInAlert(openAccountProvider.showPWDInAlert());
            defaultTaobaoAppProvider.setNeedWindVaneInit(openAccountProvider.isNeedWindVaneInit());
            defaultTaobaoAppProvider.setSite(openAccountProvider.getSite());
            defaultTaobaoAppProvider.setIsTaobaoApp(openAccountProvider.isTaobaoApp());
            defaultTaobaoAppProvider.setUseSeparateThreadPool(openAccountProvider.isUseSeparateThreadPool());
            defaultTaobaoAppProvider.setAlipaySsoDesKey(openAccountProvider.getAlipaySsoDesKey());
        }
        return defaultTaobaoAppProvider;
    }

    public static LoginEnvType getEnv(Environment environment) {
        int i2 = AnonymousClass1.$SwitchMap$com$alibaba$sdk$android$openaccount$Environment[environment.ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? LoginEnvType.ONLINE : LoginEnvType.DEV : LoginEnvType.PRE : LoginEnvType.DEV;
    }
}
