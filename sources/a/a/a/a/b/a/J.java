package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;

/* loaded from: classes.dex */
public class J implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ K f1243a;

    public J(K k2) {
        this.f1243a = k2;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        K k2 = this.f1243a;
        k2.f1249g = 0;
        k2.f1248f = Math.min(K.f1244b, k2.f1247e.size());
        for (int i2 = 0; i2 < K.f1244b && this.f1243a.f1247e.size() > 0; i2++) {
            SIGMeshBizRequest sIGMeshBizRequest = (SIGMeshBizRequest) this.f1243a.f1247e.poll();
            if (sIGMeshBizRequest != null) {
                if (i2 != 0) {
                    try {
                        Thread.sleep(K.f1245c);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                this.f1243a.a(sIGMeshBizRequest);
            }
        }
    }
}
