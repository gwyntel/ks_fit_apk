package a.a.a.a.b.i.c;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* loaded from: classes.dex */
public class e implements BleAdvertiseCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1442a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ g f1443b;

    public e(g gVar, IActionListener iActionListener) {
        this.f1443b = gVar;
        this.f1442a = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        Utils.notifySuccess((IActionListener<Object>) this.f1442a, (Object) null);
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    public void onFailure(int i2, String str) {
        Utils.notifyFailed(this.f1442a, -1, "failed to advertise network data");
    }
}
