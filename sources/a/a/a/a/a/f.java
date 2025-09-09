package a.a.a.a.a;

import a.a.a.a.a.g;
import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;

/* loaded from: classes.dex */
public class f implements BleAdvertiseCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ g.a f1165a;

    public f(g.a aVar) {
        this.f1165a = aVar;
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.c("AdvertiseManager", "sendSinglePayloadTask: onSuccess");
        if (this.f1165a.f1181c != null && this.f1165a.f1182d == this.f1165a.f1181c.size() - 1) {
            g.a.c(this.f1165a);
            if (this.f1165a.f1184f == 1) {
                this.f1165a.f1183e.onSuccess(Boolean.TRUE);
            }
        }
        if (this.f1165a.f1181c == null || this.f1165a.f1181c.size() <= 1) {
            return;
        }
        g.this.f1170e.postDelayed(this.f1165a, 200L);
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.c("AdvertiseManager", "sendSinglePayloadTask: errorCode " + i2 + ", desc " + str);
    }
}
