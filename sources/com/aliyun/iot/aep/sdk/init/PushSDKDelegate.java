package com.aliyun.iot.aep.sdk.init;

import android.app.Application;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure;
import com.aliyun.iot.aep.sdk.framework.sdk.SimpleSDKDelegateImp;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes3.dex */
public class PushSDKDelegate extends SimpleSDKDelegateImp {
    @Override // com.aliyun.iot.aep.sdk.framework.sdk.ISDKDelegate
    public int init(Application application, SDKConfigure sDKConfigure, Map<String, String> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        PushManagerHelper.getInstance().init(application);
        return 0;
    }
}
