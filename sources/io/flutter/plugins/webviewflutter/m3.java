package io.flutter.plugins.webviewflutter;

/* loaded from: classes4.dex */
public final /* synthetic */ class m3 implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InstanceManager f25253a;

    public /* synthetic */ m3(InstanceManager instanceManager) {
        this.f25253a = instanceManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f25253a.releaseAllFinalizedInstances();
    }
}
