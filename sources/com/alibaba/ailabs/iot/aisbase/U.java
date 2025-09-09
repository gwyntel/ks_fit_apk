package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.basic.BasicProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* loaded from: classes2.dex */
public class U implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f8335a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8336b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ BasicProxy f8337c;

    public U(BasicProxy basicProxy, int i2, IActionListener iActionListener) {
        this.f8337c = basicProxy;
        this.f8335a = i2;
        this.f8336b = iActionListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        LogUtils.w(BasicProxy.f8457a, "Timeout for key: " + this.f8335a);
        this.f8336b.onFailure(-5, "Command timeout");
        this.f8337c.f8461e.remove(this.f8335a);
    }
}
