package a.a.a.a.b.l;

import com.alibaba.ailabs.iot.mesh.ut.UtTraceInfo;

/* loaded from: classes.dex */
public class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f1566a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ UtTraceInfo f1567b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ f f1568c;

    public e(f fVar, String str, UtTraceInfo utTraceInfo) {
        this.f1568c = fVar;
        this.f1566a = str;
        this.f1567b = utTraceInfo;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1568c.a(this.f1566a, this.f1567b);
    }
}
