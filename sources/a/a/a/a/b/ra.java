package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.SceneTransaction;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ra implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f1630a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SceneTransaction.SceneTransactionCallback f1631b;

    public ra(String str, SceneTransaction.SceneTransactionCallback sceneTransactionCallback) {
        this.f1630a = str;
        this.f1631b = sceneTransactionCallback;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(SceneTransaction.TAG, "On successful scene bind, devId:" + this.f1630a + " ,result:" + bool.toString());
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1631b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onSuccess(this.f1630a, bool);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.b(SceneTransaction.TAG, "On Failed scene bind, devId:" + this.f1630a + " ,errorCode: " + i2 + " ,desc: " + str);
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1631b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onFailure(this.f1630a, i2, str);
        }
    }
}
