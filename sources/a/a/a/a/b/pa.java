package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshSceneJob;
import com.alibaba.ailabs.iot.mesh.SceneTransaction;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* loaded from: classes.dex */
public class pa implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f1618a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SceneTransaction.SceneTransactionCallback f1619b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ MeshSceneJob f1620c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ SceneTransaction f1621d;

    public pa(SceneTransaction sceneTransaction, String str, SceneTransaction.SceneTransactionCallback sceneTransactionCallback, MeshSceneJob meshSceneJob) {
        this.f1621d = sceneTransaction;
        this.f1618a = str;
        this.f1619b = sceneTransactionCallback;
        this.f1620c = meshSceneJob;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(SceneTransaction.TAG, "On successful scene store, devId: " + this.f1618a + " ,result:" + bool.toString());
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1619b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onSuccess(this.f1618a, bool);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1620c.addDevice(this.f1618a);
        a.a.a.a.b.m.a.b(SceneTransaction.TAG, "On Failed scene store, devId: " + this.f1618a + ", errorCode: " + i2 + " ,desc: " + str);
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1619b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onFailure(this.f1618a, i2, str);
        }
    }
}
