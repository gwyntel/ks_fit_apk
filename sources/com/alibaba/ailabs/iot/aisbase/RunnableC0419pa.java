package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* renamed from: com.alibaba.ailabs.iot.aisbase.pa, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0419pa implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f8451a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8452b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8453c;

    public RunnableC0419pa(OTAPluginProxy oTAPluginProxy, int i2, IActionListener iActionListener) {
        this.f8453c = oTAPluginProxy;
        this.f8451a = i2;
        this.f8452b = iActionListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        LogUtils.w(this.f8453c.f8485a, "Timeout for key: " + this.f8451a);
        AISCommand aISCommand = (AISCommand) this.f8453c.J.get(this.f8451a);
        if (aISCommand != null) {
            this.f8453c.J.remove(this.f8451a);
            this.f8453c.a(this.f8451a, this.f8452b, aISCommand);
        } else {
            this.f8452b.onFailure(-5, String.format("Command %d timeout", Integer.valueOf(this.f8451a)));
            this.f8453c.f8490f.remove(this.f8451a);
        }
    }
}
