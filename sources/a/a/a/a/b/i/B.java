package a.a.a.a.b.i;

import meshprovisioner.states.UnprovisionedMeshNodeData;

/* loaded from: classes.dex */
public class B implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedMeshNodeData f1361a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ String f1362b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ J f1363c;

    public B(J j2, UnprovisionedMeshNodeData unprovisionedMeshNodeData, String str) {
        this.f1363c = j2;
        this.f1361a = unprovisionedMeshNodeData;
        this.f1362b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1363c.onReceiveConfirmationFromCloud(this.f1361a, this.f1362b);
    }
}
