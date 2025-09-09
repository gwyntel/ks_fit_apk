package b;

import b.K;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;

/* loaded from: classes2.dex */
public class L implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f7359a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ K.d f7360b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ M f7361c;

    public L(M m2, String str, K.d dVar) {
        this.f7361c = m2;
        this.f7359a = str;
        this.f7360b = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f7359a.equalsIgnoreCase(this.f7360b.f7355d.getAddress())) {
            this.f7360b.a(BleMeshManager.WriteReadType.WRITE);
        }
    }
}
