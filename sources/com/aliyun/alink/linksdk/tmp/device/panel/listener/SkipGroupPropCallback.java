package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import androidx.annotation.Nullable;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class SkipGroupPropCallback extends SetGroupPropCallback {
    private static final String TAG = "[Tmp]SkipGroupPropCallback";

    public SkipGroupPropCallback(IPanelCallback iPanelCallback) {
        super(iPanelCallback);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.SetGroupPropCallback, com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
    public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "SetGroupPropCallback onComplete isSuccess:" + z2 + " mCallback:" + this.mCallback);
    }
}
