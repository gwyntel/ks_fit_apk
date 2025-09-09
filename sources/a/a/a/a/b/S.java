package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* loaded from: classes.dex */
public class S implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1227a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService f1228b;

    public S(MeshService meshService, IActionListener iActionListener) {
        this.f1228b = meshService;
        this.f1227a = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        IActionListener iActionListener = this.f1227a;
        if (iActionListener != null) {
            iActionListener.onSuccess(bool);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        IActionListener iActionListener = this.f1227a;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, str);
        }
    }
}
