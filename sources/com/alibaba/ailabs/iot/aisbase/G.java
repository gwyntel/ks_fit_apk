package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.IDetailActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* loaded from: classes2.dex */
public class G implements IActionListener<byte[]> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8284a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IPlugin f8285b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8286c;

    public G(AuthPluginBusinessProxy authPluginBusinessProxy, IActionListener iActionListener, IPlugin iPlugin) {
        this.f8286c = authPluginBusinessProxy;
        this.f8284a = iActionListener;
        this.f8285b = iPlugin;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(byte[] bArr) {
        String lowerCase = ConvertUtils.bytes2HexString(bArr).toLowerCase();
        LogUtils.d(AuthPluginBusinessProxy.TAG, "Delivery random success, received digest: " + lowerCase);
        IActionListener iActionListener = this.f8284a;
        if (iActionListener instanceof IDetailActionListener) {
            ((IDetailActionListener) iActionListener).onState(1, "start get auth ble key", null);
        }
        this.f8286c.authCheckAndGetBleKey(this.f8285b, bArr, this.f8284a);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        LogUtils.e(AuthPluginBusinessProxy.TAG, "Delivery random failed, error code(" + i2 + "), error message(" + str + ")");
        IActionListener iActionListener = this.f8284a;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, str);
        }
    }
}
