package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* loaded from: classes.dex */
public class H implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ J f1371a;

    public H(J j2) {
        this.f1371a = j2;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1371a.onProvisionFailed(i2, str);
    }
}
