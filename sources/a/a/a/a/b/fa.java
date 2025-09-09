package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;

/* loaded from: classes.dex */
public class fa implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MeshService f1344a;

    public fa(MeshService meshService) {
        this.f1344a = meshService;
    }

    @Override // java.lang.Runnable
    public void run() {
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.TIMEOUT_ERROR;
        this.f1344a.handleProvisionFailed(meshUtConst$MeshErrorEnum, meshUtConst$MeshErrorEnum.getErrorMsg());
    }
}
