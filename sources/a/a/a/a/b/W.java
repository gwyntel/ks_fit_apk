package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* loaded from: classes.dex */
public class W implements IActionListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MeshService f1235a;

    public W(MeshService meshService) {
        this.f1235a = meshService;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "WiFi config failure errorCode = " + i2 + "; message = " + str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onSuccess(Object obj) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "WiFi config result = " + obj.toString());
    }
}
