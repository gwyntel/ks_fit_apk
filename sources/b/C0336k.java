package b;

import android.util.Pair;
import b.C0337l;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* renamed from: b.k, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0336k implements IActionListener<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0337l.a f7493a;

    public C0336k(C0337l.a aVar) {
        this.f7493a = aVar;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        if (this.f7493a.f7510b != null) {
            C0337l.this.f7507n.removeCallbacks(this.f7493a.f7510b);
        }
        Utils.notifyFailed(this.f7493a.f7511c, i2, str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onSuccess(Object obj) {
        if (this.f7493a.f7510b != null) {
            C0337l.this.f7507n.removeCallbacks(this.f7493a.f7510b);
        }
        if (this.f7493a.f7512d == null) {
            Utils.notifySuccess((IActionListener<Object>) this.f7493a.f7511c, obj);
            return;
        }
        Pair<Integer, ?> response = this.f7493a.f7512d.parseResponse(obj);
        if (response != null) {
            Integer num = (Integer) response.first;
            if (num.intValue() == 0) {
                Utils.notifySuccess((IActionListener<Object>) this.f7493a.f7511c, response.second);
            } else {
                Utils.notifyFailed(this.f7493a.f7511c, num.intValue(), (String) response.second);
            }
        }
    }
}
