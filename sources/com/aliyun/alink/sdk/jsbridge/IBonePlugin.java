package com.aliyun.alink.sdk.jsbridge;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes2.dex */
public interface IBonePlugin {
    boolean call(String str, Object[] objArr, BoneCallback boneCallback);

    void destroy();

    void onActivityResult(int i2, int i3, Intent intent);

    void onDestroy();

    void onInitialize(Context context, IJSBridge iJSBridge);

    void onPause();

    void onResume();
}
