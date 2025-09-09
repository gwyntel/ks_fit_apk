package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;

/* renamed from: a.a.a.a.b.i.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0235d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1478a;

    public RunnableC0235d(FastProvisionManager fastProvisionManager) {
        this.f1478a = fastProvisionManager;
    }

    @Override // java.lang.Runnable
    public void run() {
        a.a.a.a.b.m.a.a(FastProvisionManager.TAG, "provision success, delay stop scan");
        this.f1478a.stopScan();
    }
}
