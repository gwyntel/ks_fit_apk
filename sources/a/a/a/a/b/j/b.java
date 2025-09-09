package a.a.a.a.b.j;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.task.bean.MeshControlDevice;

/* loaded from: classes.dex */
public class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public String f1536a = b.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public MeshService.b f1537b;

    /* renamed from: c, reason: collision with root package name */
    public MeshControlDevice f1538c;

    public b(MeshService.b bVar, MeshControlDevice meshControlDevice) {
        this.f1537b = bVar;
        this.f1538c = meshControlDevice;
    }

    public final void a(String str) {
        a.a.a.a.b.m.a.a(this.f1536a, str);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f1537b != null) {
            a("LowPowerMeshRunnable send msg:" + this.f1538c.f());
            this.f1537b.a(this.f1538c.b(), this.f1538c.c(), this.f1538c.f(), this.f1538c.d(), this.f1538c.e(), this.f1538c.a());
        }
    }
}
