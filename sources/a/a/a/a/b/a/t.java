package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class t implements IActionListener<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1290a;

    public t(IActionListener iActionListener) {
        this.f1290a = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        Utils.notifyFailed(this.f1290a, i2, str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onSuccess(Object obj) {
        Utils.notifySuccess((IActionListener<Boolean>) this.f1290a, Boolean.TRUE);
    }
}
