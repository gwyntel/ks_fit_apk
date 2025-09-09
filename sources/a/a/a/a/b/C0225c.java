package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* renamed from: a.a.a.a.b.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0225c implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0226d f1321a;

    public C0225c(C0226d c0226d) {
        this.f1321a = c0226d;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a("BleMeshHeartReportManager", "updateMeshNetworkParameters onSuccess() called with: result = [" + bool + "]");
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.a("BleMeshHeartReportManager", "updateMeshNetworkParameters onFailure() called with: errorCode = [" + i2 + "], desc = [" + str + "]");
    }
}
