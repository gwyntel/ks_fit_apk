package a.a.a.a.b.a;

import b.C0337l;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/* renamed from: a.a.a.a.b.a.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0218g {

    /* renamed from: a, reason: collision with root package name */
    public static String f1267a = "" + C0218g.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public final Set<SIGMeshBizRequest> f1268b = new HashSet();

    /* renamed from: c, reason: collision with root package name */
    public final Deque<SIGMeshBizRequest> f1269c;

    /* renamed from: d, reason: collision with root package name */
    public K f1270d;

    /* renamed from: e, reason: collision with root package name */
    public C0215d f1271e;

    /* renamed from: f, reason: collision with root package name */
    public long f1272f;

    public C0218g(C0337l c0337l) {
        LinkedList linkedList = new LinkedList();
        this.f1269c = linkedList;
        this.f1272f = 0L;
        this.f1271e = new C0215d(c0337l);
        this.f1270d = new K(c0337l, linkedList);
    }

    public void d() {
        this.f1270d.d();
    }

    public List<SIGMeshBizRequest> b() {
        return (List) this.f1269c;
    }

    public void c() {
        this.f1270d.c();
    }

    public void a(List<SIGMeshBizRequest> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        synchronized (this.f1268b) {
            this.f1268b.addAll(list);
        }
        b(list);
    }

    public final void b(List<SIGMeshBizRequest> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (list.get(0).f8735e) {
            this.f1269c.addAll(list);
            if (this.f1270d.a()) {
                return;
            }
            a.a.a.a.b.m.a.c(f1267a, "Start dispatcher, delay: " + this.f1272f + ", pending request size: " + this.f1269c.size());
            new Thread(new RunnableC0216e(this)).start();
            return;
        }
        a.a.a.a.b.m.l.a().a(new C0217f(this, list));
    }

    public C0218g(C0337l c0337l, String str) {
        LinkedList linkedList = new LinkedList();
        this.f1269c = linkedList;
        this.f1272f = 0L;
        f1267a += str;
        this.f1271e = new C0215d(c0337l);
        this.f1270d = new K(c0337l, linkedList, str);
    }

    public void a(boolean z2) {
        a.a.a.a.b.m.a.c(f1267a, "Cancel RequestQueue, all? " + z2);
        if (z2) {
            synchronized (this.f1269c) {
                this.f1269c.clear();
            }
        }
    }

    public void a(long j2) {
        this.f1272f = j2;
    }

    public void a(int i2) {
        this.f1270d.b((int) (400.0d / i2));
    }
}
