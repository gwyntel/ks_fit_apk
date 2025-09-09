package com.alibaba.ailabs.iot.aisbase;

import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;
import datasource.NetworkCallback;

/* loaded from: classes2.dex */
public class F implements NetworkCallback<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IPlugin f8278a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8279b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ byte[] f8280c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ String f8281d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8282e;

    public F(AuthPluginBusinessProxy authPluginBusinessProxy, IPlugin iPlugin, IActionListener iActionListener, byte[] bArr, String str) {
        this.f8282e = authPluginBusinessProxy;
        this.f8278a = iPlugin;
        this.f8279b = iActionListener;
        this.f8280c = bArr;
        this.f8281d = str;
    }

    @Override // datasource.NetworkCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(String str) {
        this.f8282e.cancelGetAuthRandomTimeoutTask();
        LogUtils.d(AuthPluginBusinessProxy.TAG, "Get auth random success: " + str);
        if (str.length() > 0) {
            this.f8282e.deliveryRandomId(this.f8278a, this.f8279b, str);
            return;
        }
        IActionListener iActionListener = this.f8279b;
        if (iActionListener != null) {
            iActionListener.onFailure(-206, "");
        }
    }

    @Override // datasource.NetworkCallback
    public void onFailure(String str, String str2) {
        LogUtils.e(AuthPluginBusinessProxy.TAG, String.format("getAuthRandomId failed, errCode: %s, desc: %s", str, str2));
        UTLogUtils.updateBusInfo("gma_auth", UTLogUtils.buildDeviceInfo(this.f8278a.getBluetoothDeviceWrapper()), UTLogUtils.buildAuthBusInfo("error", this.f8282e.mProductId, this.f8282e.mDeviceAddress, 1, "getAuthRandom failed: " + str2));
        if (this.f8282e.canRetryGetAuthRandom()) {
            if (this.f8282e.mGetAuthRandomTimeoutTask == null) {
                this.f8282e.mGetAuthRandomTimeoutTask = new E(this);
            }
            this.f8282e.mHandler.postDelayed(this.f8282e.mGetAuthRandomTimeoutTask, 3000L);
            return;
        }
        LogUtils.i(AuthPluginBusinessProxy.TAG, "cancel get auth random timeout task");
        this.f8282e.cancelGetAuthRandomTimeoutTask();
        if (this.f8279b != null) {
            if (TextUtils.equals(str, "2064") || TextUtils.equals(str, "28612")) {
                AuthPluginBusinessProxy.isAuthAndBind.set(true);
            }
            this.f8279b.onFailure(-300, str2);
        }
    }
}
