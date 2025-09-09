package com.luck.picture.lib.engine;

import android.content.Context;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnCallbackIndexListener;

@Deprecated
/* loaded from: classes4.dex */
public interface SandboxFileEngine {
    void onStartSandboxFileTransform(Context context, boolean z2, int i2, LocalMedia localMedia, OnCallbackIndexListener<LocalMedia> onCallbackIndexListener);
}
