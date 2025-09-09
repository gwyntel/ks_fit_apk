package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes.dex */
public class Y implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1237a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService f1238b;

    public Y(MeshService meshService, ProvisionedMeshNode provisionedMeshNode) {
        this.f1238b = meshService;
        this.f1237a = provisionedMeshNode;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str = (String) this.f1238b.mAppKeyQueue.poll();
        Integer num = (Integer) this.f1238b.mAppKeyIndexQueue.poll();
        if (str != null) {
            if (num == null) {
                num = 0;
            }
            a.a.a.a.b.m.a.c(MeshService.TAG, "try to add app key: appKeyIndex = " + num + ", mAppKey = " + str);
            this.f1238b.mMeshManagerApi.a(this.f1237a, num.intValue(), str);
        }
        a.a.a.a.b.m.a.a(MeshService.TAG, "addAppKey");
    }
}
