package a.a.a.a.b.a;

import a.a.a.a.b.m.l;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import java.util.List;

/* renamed from: a.a.a.a.b.a.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0217f implements l.a<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ List f1265a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0218g f1266b;

    public C0217f(C0218g c0218g, List list) {
        this.f1266b = c0218g;
        this.f1265a = list;
    }

    @Override // a.a.a.a.b.m.l.a
    public Object a(l.b bVar) throws InterruptedException {
        int i2;
        for (int i3 = 0; i3 < this.f1265a.size(); i3 = i2) {
            i2 = i3 + 1;
            SIGMeshBizRequest sIGMeshBizRequest = (SIGMeshBizRequest) this.f1265a.get(i3);
            this.f1266b.f1271e.a(sIGMeshBizRequest);
            while (i2 < this.f1265a.size() && ((SIGMeshBizRequest) this.f1265a.get(i2)).n()) {
                this.f1266b.f1271e.a(sIGMeshBizRequest);
                i2++;
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
