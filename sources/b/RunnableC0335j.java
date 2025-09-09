package b;

import android.annotation.SuppressLint;
import b.C0337l;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* renamed from: b.j, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0335j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0337l f7491a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0337l.a f7492b;

    public RunnableC0335j(C0337l.a aVar, C0337l c0337l) {
        this.f7492b = aVar;
        this.f7491a = c0337l;
    }

    @Override // java.lang.Runnable
    @SuppressLint({"DefaultLocale"})
    public void run() {
        C0337l.this.f7501h.b(this.f7492b.f7513e, this.f7492b.f7514f, this.f7492b.f7516h, this.f7492b.f7509a);
        Utils.notifyFailed(this.f7492b.f7511c, -13, String.format("Did not receive the expected response within %d seconds", Integer.valueOf(this.f7492b.f7515g)));
    }
}
