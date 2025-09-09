package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* loaded from: classes.dex */
public class L implements IActionListener<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ P f1400a;

    public L(P p2) {
        this.f1400a = p2;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1400a.a(false, i2, 0, str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onSuccess(Object obj) {
        this.f1400a.f1406c.postDelayed(this.f1400a.f1411h = new K(this), 40000L);
    }
}
