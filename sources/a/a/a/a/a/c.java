package a.a.a.a.a;

import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;

/* loaded from: classes.dex */
public class c implements BleAdvertiseCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ a.a.a.a.a.a.a.b.a f1160a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ g f1161b;

    public c(g gVar, a.a.a.a.a.a.a.b.a aVar) {
        this.f1161b = gVar;
        this.f1160a = aVar;
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.c("AdvertiseManager", "send message success, messageId = " + ((int) this.f1160a.c()) + ", networkId = " + ((int) this.f1160a.d()));
        BleAdvertiseCallback<Boolean> bleAdvertiseCallbackB = this.f1160a.b();
        if (bleAdvertiseCallbackB != null) {
            bleAdvertiseCallbackB.onSuccess(Boolean.TRUE);
        }
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.c("AdvertiseManager", "errorCodee = " + i2 + ", desc = " + str);
        this.f1161b.f1177l = false;
        BleAdvertiseCallback<Boolean> bleAdvertiseCallbackB = this.f1160a.b();
        if (bleAdvertiseCallbackB != null) {
            bleAdvertiseCallbackB.onFailure(i2, str);
        }
    }
}
