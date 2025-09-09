package com.aliyun.iot.aep.sdk.framework.sdk;

import android.app.Application;
import java.util.Map;

/* loaded from: classes3.dex */
public interface ISDKDelegate {
    boolean canBeenInitialized(Map<String, String> map);

    int init(Application application, SDKConfigure sDKConfigure, Map<String, String> map);
}
