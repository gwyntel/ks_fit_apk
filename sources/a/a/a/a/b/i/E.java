package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* loaded from: classes.dex */
public class E implements IActionListener<byte[]> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ J f1366a;

    public E(J j2) {
        this.f1366a = j2;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(byte[] bArr) {
        a.a.a.a.b.m.a.c(this.f1366a.f1373a, "Advertising network data, begin scan");
        J j2 = this.f1366a;
        j2.a(j2.f1374b);
        this.f1366a.f1394v.postDelayed(new D(this), 1500L);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1366a.onProvisionFailed(-1, "failed to advertise network data");
    }
}
