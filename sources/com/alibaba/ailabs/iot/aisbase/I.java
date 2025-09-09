package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.IDetailActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.IAuthPlugin;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* loaded from: classes2.dex */
public class I implements IActionListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ byte[] f8293a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ J f8294b;

    public I(J j2, byte[] bArr) {
        this.f8294b = j2;
        this.f8293a = bArr;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f8294b.f8297c.mTransmissionLayer.forwardInnerCastEvent(IAuthPlugin.EVENT_AUTH_FAILED);
        IActionListener iActionListener = this.f8294b.f8295a;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, str);
        } else {
            LogUtils.e(AuthPluginBusinessProxy.TAG, "sendVerifyResult onFailure: listener is null");
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onSuccess(Object obj) {
        IActionListener iActionListener = this.f8294b.f8295a;
        if (iActionListener != null) {
            iActionListener.onSuccess(this.f8293a);
            IActionListener iActionListener2 = this.f8294b.f8295a;
            if (iActionListener2 instanceof IDetailActionListener) {
                ((IDetailActionListener) iActionListener2).onState(3, "auth success", null);
            }
        } else {
            LogUtils.e(AuthPluginBusinessProxy.TAG, "sendVerifyResult onSuccess: listener is null");
        }
        this.f8294b.f8297c.mTransmissionLayer.forwardInnerCastEvent(IAuthPlugin.EVENT_AUTH_SUCCESS);
    }
}
