package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshSceneJob;
import com.alibaba.ailabs.iot.mesh.SceneTransaction;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* loaded from: classes.dex */
public class qa implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f1624a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SceneTransaction.SceneTransactionCallback f1625b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ MeshSceneJob f1626c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ SceneTransaction f1627d;

    public qa(SceneTransaction sceneTransaction, String str, SceneTransaction.SceneTransactionCallback sceneTransactionCallback, MeshSceneJob meshSceneJob) {
        this.f1627d = sceneTransaction;
        this.f1624a = str;
        this.f1625b = sceneTransactionCallback;
        this.f1626c = meshSceneJob;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(SceneTransaction.TAG, "On successful scene delete, devId: " + this.f1624a + " ,result:" + bool.toString());
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1625b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onSuccess(this.f1624a, bool);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1626c.addDevice(this.f1624a);
        a.a.a.a.b.m.a.b(SceneTransaction.TAG, "On Failed scene delete, devId: " + this.f1624a + ", errorCode: " + i2 + " ,desc: " + str);
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1625b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onFailure(this.f1624a, i2, str);
        }
    }
}
