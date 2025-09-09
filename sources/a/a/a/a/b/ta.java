package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.SceneTransaction;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ta implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f1637a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SceneTransaction.SceneTransactionCallback f1638b;

    public ta(String str, SceneTransaction.SceneTransactionCallback sceneTransactionCallback) {
        this.f1637a = str;
        this.f1638b = sceneTransactionCallback;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(SceneTransaction.TAG, "On successful scene unbind, devId: " + this.f1637a + " ,result: " + bool.toString());
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1638b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onSuccess(this.f1637a, bool);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.b(SceneTransaction.TAG, "On Failed scene unbind, devId: " + this.f1637a + " ,errorCode: " + i2 + " ,desc: " + str);
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1638b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onFailure(this.f1637a, i2, str);
        }
    }
}
