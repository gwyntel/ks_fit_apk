package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* loaded from: classes2.dex */
public class M implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f8304a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8305b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8306c;

    public M(AuthPluginBusinessProxy authPluginBusinessProxy, int i2, IActionListener iActionListener) {
        this.f8306c = authPluginBusinessProxy;
        this.f8304a = i2;
        this.f8305b = iActionListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        LogUtils.w(AuthPluginBusinessProxy.TAG, "Timeout for key: " + this.f8304a);
        AISCommand aISCommand = (AISCommand) this.f8306c.mReTransmissionArray.get(this.f8304a);
        if (aISCommand == null) {
            this.f8305b.onFailure(-5, "Command timeout");
        } else {
            this.f8306c.mReTransmissionArray.remove(this.f8304a);
            this.f8306c.reTransmissionCommand(this.f8304a, this.f8305b, aISCommand);
        }
    }
}
