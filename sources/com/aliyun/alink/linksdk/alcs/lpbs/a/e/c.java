package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalSdk;
import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class c implements PalDiscoveryListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10717a = "[AlcsLPBS]DiscoveryForceStopListener";

    /* renamed from: b, reason: collision with root package name */
    private PalDiscoveryListener f10718b;

    /* renamed from: d, reason: collision with root package name */
    private int f10720d;

    /* renamed from: c, reason: collision with root package name */
    private AtomicInteger f10719c = new AtomicInteger(PluginMgr.getInstance().getPluginCount());

    /* renamed from: e, reason: collision with root package name */
    private a f10721e = new a();

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.a();
        }
    }

    public c(int i2, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f10718b = palDiscoveryListener;
        this.f10720d = i2;
        if (AlcsPalSdk.getHandler() != null) {
            AlcsPalSdk.getHandler().postDelayed(this.f10721e, this.f10720d + 1000);
        }
        ALog.d(f10717a, "DiscoveryForceStopListener mFinishedPluginCount:" + this.f10719c.get());
    }

    public void a() {
        this.f10721e = null;
        this.f10719c.set(0);
        PalDiscoveryListener palDiscoveryListener = this.f10718b;
        if (palDiscoveryListener != null) {
            palDiscoveryListener.onDiscoveryFinish();
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener
    public void onDiscoveryDevice(PalDiscoveryDeviceInfo palDiscoveryDeviceInfo) {
        PalDiscoveryListener palDiscoveryListener = this.f10718b;
        if (palDiscoveryListener != null) {
            palDiscoveryListener.onDiscoveryDevice(palDiscoveryDeviceInfo);
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener
    public void onDiscoveryFinish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int iDecrementAndGet = this.f10719c.decrementAndGet();
        ALog.d(f10717a, "onDiscoveryFinish count:" + iDecrementAndGet);
        if (iDecrementAndGet == 0) {
            if (this.f10721e != null) {
                AlcsPalSdk.getHandler().removeCallbacks(this.f10721e);
            }
            this.f10721e = null;
            PalDiscoveryListener palDiscoveryListener = this.f10718b;
            if (palDiscoveryListener != null) {
                palDiscoveryListener.onDiscoveryFinish();
            }
        }
    }
}
