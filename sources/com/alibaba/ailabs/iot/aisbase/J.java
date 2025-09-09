package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.IDetailActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.IAuthPlugin;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import datasource.NetworkCallback;

/* loaded from: classes2.dex */
public class J implements NetworkCallback<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8295a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IPlugin f8296b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8297c;

    public J(AuthPluginBusinessProxy authPluginBusinessProxy, IActionListener iActionListener, IPlugin iPlugin) {
        this.f8297c = authPluginBusinessProxy;
        this.f8295a = iActionListener;
        this.f8296b = iPlugin;
    }

    @Override // datasource.NetworkCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(String str) {
        this.f8297c.cancelAuthAndCheckCipherTimeoutTask();
        LogUtils.d(AuthPluginBusinessProxy.TAG, "authCheckAndGetBleKey success: " + str);
        byte[] bArrHexString2Bytes = ConvertUtils.hexString2Bytes(str);
        IActionListener iActionListener = this.f8295a;
        if (iActionListener instanceof IDetailActionListener) {
            ((IDetailActionListener) iActionListener).onState(2, "get auth ble key success", null);
        }
        this.f8297c.sendVerifyResult(this.f8296b, new I(this, bArrHexString2Bytes), true);
    }

    @Override // datasource.NetworkCallback
    public void onFailure(String str, String str2) {
        UTLogUtils.updateBusInfo("gma_auth", UTLogUtils.buildDeviceInfo(this.f8296b.getBluetoothDeviceWrapper()), UTLogUtils.buildAuthBusInfo("error", this.f8297c.mProductId, this.f8297c.mDeviceAddress, 1, "authAndCheckCipher failed: " + str2));
        this.f8297c.cancelAuthAndCheckCipherTimeoutTask();
        LogUtils.e(AuthPluginBusinessProxy.TAG, String.format("authCheckAndGetBleKey failed, errCode: %s, desc: %s", str, str2));
        this.f8297c.mTransmissionLayer.forwardInnerCastEvent(IAuthPlugin.EVENT_AUTH_FAILED);
        IActionListener iActionListener = this.f8295a;
        if (iActionListener != null) {
            iActionListener.onFailure(-300, str2);
        }
    }
}
