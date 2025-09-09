package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;

/* renamed from: a.a.a.a.b.i.m, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0244m implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1491a;

    public C0244m(FastProvisionManager fastProvisionManager) {
        this.f1491a = fastProvisionManager;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1491a.onProvisionFailed(i2, str);
    }
}
