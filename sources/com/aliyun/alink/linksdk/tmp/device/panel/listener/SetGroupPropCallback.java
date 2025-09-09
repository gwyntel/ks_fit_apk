package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import androidx.annotation.Nullable;
import com.aliyun.alink.linksdk.tmp.utils.ResponseUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SetGroupPropCallback implements IPanelCallback {
    private static final String TAG = "[Tmp]SetGroupPropCallback";
    protected volatile IPanelCallback mCallback;

    public SetGroupPropCallback(IPanelCallback iPanelCallback) {
        this.mCallback = iPanelCallback;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
    public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPanelCallback iPanelCallback = null;
        if (this.mCallback != null) {
            synchronized (this) {
                try {
                    if (this.mCallback != null) {
                        IPanelCallback iPanelCallback2 = this.mCallback;
                        this.mCallback = null;
                        iPanelCallback = iPanelCallback2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (iPanelCallback != null) {
                iPanelCallback.onComplete(z2, ResponseUtils.getSuccessRspJson(new JSONObject()));
            }
        }
        ALog.d(TAG, "SetGroupPropCallback onComplete isSuccess:" + z2 + " mCallback:" + this.mCallback + " iPanelCallback:" + iPanelCallback);
    }
}
