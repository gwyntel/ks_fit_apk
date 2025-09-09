package com.alibaba.sdk.android.openaccount.ui.module;

import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.Environment;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountFaceLoginService;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountFaceLoginServiceImpl;
import com.alibaba.sdk.android.pluto.meta.ModuleInfo;
import com.alibaba.sdk.android.pluto.meta.ModuleInfoBuilder;
import com.alibaba.security.rp.RPSDK;
import java.util.Map;

/* loaded from: classes2.dex */
public class OpenAccountFaceModule {
    private static boolean supportRpSDK;
    private static boolean supportWindvane;

    /* renamed from: com.alibaba.sdk.android.openaccount.ui.module.OpenAccountFaceModule$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
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

    static {
        try {
            Class.forName("android.taobao.windvane.WindVaneSDK");
            supportWindvane = true;
        } catch (Throwable unused) {
        }
        try {
            Class.forName("com.alibaba.security.rp.RPSDK");
            supportRpSDK = true;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static ModuleInfo getModuleInfo() {
        ModuleInfoBuilder moduleInfoBuilder = new ModuleInfoBuilder("openAccountFaceModule");
        if (supportRpSDK && supportWindvane) {
            int i2 = AnonymousClass1.$SwitchMap$com$alibaba$sdk$android$openaccount$Environment[ConfigManager.getInstance().getEnvironment().ordinal()];
            if (i2 == 1) {
                RPSDK.initialize(RPSDK.RPSDKEnv.RPSDKEnv_ONLINE, OpenAccountSDK.getAndroidContext());
            } else if (i2 == 2) {
                RPSDK.initialize(RPSDK.RPSDKEnv.RPSDKEnv_PRE, OpenAccountSDK.getAndroidContext());
            } else if (i2 == 3) {
                RPSDK.initialize(RPSDK.RPSDKEnv.RPSDKEnv_DAILY, OpenAccountSDK.getAndroidContext());
            }
            moduleInfoBuilder.addBeanInfo(OpenAccountFaceLoginService.class, OpenAccountFaceLoginServiceImpl.class, "init", (Map<String, String>) null);
        }
        return moduleInfoBuilder.build();
    }
}
