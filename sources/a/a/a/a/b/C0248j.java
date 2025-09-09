package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* renamed from: a.a.a.a.b.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0248j implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1530a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1531b;

    public C0248j(DeviceProvisioningWorker deviceProvisioningWorker, IActionListener iActionListener) {
        this.f1531b = deviceProvisioningWorker;
        this.f1530a = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        IActionListener iActionListener = this.f1530a;
        if (iActionListener != null) {
            iActionListener.onSuccess(bool);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        IActionListener iActionListener = this.f1530a;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, str);
        }
    }
}
