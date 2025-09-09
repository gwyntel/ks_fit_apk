package b;

import java.util.List;
import meshprovisioner.BaseMeshNode;

/* renamed from: b.f, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0331f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ List f7462a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BaseMeshNode f7463b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C0337l f7464c;

    public RunnableC0331f(C0337l c0337l, List list, BaseMeshNode baseMeshNode) {
        this.f7464c = c0337l;
        this.f7462a = list;
        this.f7463b = baseMeshNode;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        for (byte[] bArr : this.f7462a) {
            try {
                Thread.sleep(150L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.f7464c.f7507n.post(new RunnableC0330e(this, bArr));
        }
    }
}
