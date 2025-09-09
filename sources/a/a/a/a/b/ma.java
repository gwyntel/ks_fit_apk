package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import meshprovisioner.configuration.bean.CfgMsgModelSubscriptionStatus;

/* loaded from: classes.dex */
public class ma implements IActionListener<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Runnable f1605a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1606b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ MeshService.b f1607c;

    public ma(MeshService.b bVar, Runnable runnable, IActionListener iActionListener) {
        this.f1607c = bVar;
        this.f1605a = runnable;
        this.f1606b = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onSuccess(Object obj) {
        MeshService.this.mHandler.removeCallbacks(this.f1605a);
        if (!(obj instanceof CfgMsgModelSubscriptionStatus)) {
            Utils.notifyFailed(this.f1606b, -30, "internal error");
            return;
        }
        CfgMsgModelSubscriptionStatus cfgMsgModelSubscriptionStatus = (CfgMsgModelSubscriptionStatus) obj;
        if (cfgMsgModelSubscriptionStatus.isSuccessful()) {
            Utils.notifySuccess((IActionListener<Boolean>) this.f1606b, Boolean.TRUE);
        } else {
            Utils.notifyFailed(this.f1606b, -40, CfgMsgModelSubscriptionStatus.parseStatusMessage(MeshService.this.getApplicationContext(), cfgMsgModelSubscriptionStatus.getStatus()));
        }
    }
}
