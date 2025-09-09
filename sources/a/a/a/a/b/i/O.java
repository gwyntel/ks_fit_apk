package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.provision.WiFiConfigReplyParser;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* loaded from: classes.dex */
public class O implements WiFiConfigReplyParser.a<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ P f1403a;

    public O(P p2) {
        this.f1403a = p2;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.WiFiConfigReplyParser.a
    public void a(WiFiConfigReplyParser.Status status) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        Utils.notifyFailed(this.f1403a.f1409f, i2, str);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onSuccess(Object obj) {
        this.f1403a.a();
        Utils.notifySuccess((IActionListener<String>) this.f1403a.f1409f, "");
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.WiFiConfigReplyParser.a
    public void a(int i2, int i3, String str) {
        this.f1403a.a(false, i2, i3, str);
    }
}
