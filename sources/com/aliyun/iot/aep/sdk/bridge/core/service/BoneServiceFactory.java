package com.aliyun.iot.aep.sdk.bridge.core.service;

import android.content.Context;
import java.util.List;

/* loaded from: classes3.dex */
public interface BoneServiceFactory {
    BoneService generateInstance(Context context, String str);

    List<BoneMethodSpec> getMethods(String str);

    BoneServiceMode getMode(String str);

    String getSDKName();

    String getSDKVersion();

    List<String> getServiceNameList();
}
