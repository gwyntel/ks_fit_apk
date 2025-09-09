package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* renamed from: a.a.a.a.b.a.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0220i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Runnable f1273a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SIGMeshBizRequest f1274b;

    public RunnableC0220i(SIGMeshBizRequest sIGMeshBizRequest, Runnable runnable) {
        this.f1274b = sIGMeshBizRequest;
        this.f1273a = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        Runnable runnable = this.f1273a;
        if (runnable != null) {
            runnable.run();
        }
        Utils.notifyFailed(this.f1274b.f8733c, -13, "timeout");
    }
}
