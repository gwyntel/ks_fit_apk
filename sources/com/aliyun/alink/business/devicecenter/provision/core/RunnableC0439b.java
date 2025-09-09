package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.utils.NetworkEnvironmentUtils;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.b, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0439b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0440c f10567a;

    public RunnableC0439b(C0440c c0440c) {
        this.f10567a = c0440c;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.f10567a.f10569a.provisionHasStopped.get()) {
                return;
            }
            this.f10567a.f10569a.pingEnvInfo = NetworkEnvironmentUtils.ping();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
