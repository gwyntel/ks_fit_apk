package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;

/* loaded from: classes.dex */
public class ka implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MeshService.b f1558a;

    public ka(MeshService.b bVar) {
        this.f1558a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        b.u uVarD = G.a().d();
        uVarD.a(MeshService.this.getApplicationContext(), MeshService.this);
        uVarD.f();
    }
}
