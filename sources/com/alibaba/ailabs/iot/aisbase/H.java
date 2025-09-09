package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;

/* loaded from: classes2.dex */
public class H implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IPlugin f8289a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f8290b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8291c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8292d;

    public H(AuthPluginBusinessProxy authPluginBusinessProxy, IPlugin iPlugin, byte[] bArr, IActionListener iActionListener) {
        this.f8292d = authPluginBusinessProxy;
        this.f8289a = iPlugin;
        this.f8290b = bArr;
        this.f8291c = iActionListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f8292d.canRetryAuthAndCheckCipher()) {
            this.f8292d.mAuthAndCheckCipherTimeoutTask = null;
            this.f8292d.authCheckAndGetBleKey(this.f8289a, this.f8290b, this.f8291c);
        }
    }
}
