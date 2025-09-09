package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.SceneTransaction;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class sa implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Map f1633a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SceneTransaction.SceneTransactionCallback f1634b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f1635c;

    public sa(Map map, SceneTransaction.SceneTransactionCallback sceneTransactionCallback, String str) {
        this.f1633a = map;
        this.f1634b = sceneTransactionCallback;
        this.f1635c = str;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(SceneTransaction.TAG, "On successful scene bind, devIdInfo:" + this.f1633a + " ,result:" + bool.toString());
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1634b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onSuccess(this.f1635c, bool);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.b(SceneTransaction.TAG, "On Failed scene bind, devIdInfo:" + this.f1633a + " ,errorCode: " + i2 + " ,desc: " + str);
        SceneTransaction.SceneTransactionCallback sceneTransactionCallback = this.f1634b;
        if (sceneTransactionCallback != null) {
            sceneTransactionCallback.onFailure(this.f1635c, i2, str);
        }
    }
}
