package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;

/* loaded from: classes.dex */
public class ga implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MeshService f1348a;

    public ga(MeshService meshService) {
        this.f1348a = meshService;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f1348a.mIsScanning) {
            return;
        }
        this.f1348a.startScan();
    }
}
