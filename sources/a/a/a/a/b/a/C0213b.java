package a.a.a.a.b.a;

import android.util.Pair;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* renamed from: a.a.a.a.b.a.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0213b implements IActionListener<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SIGMeshBizRequest f1258a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ I f1259b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1260c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ C0215d f1261d;

    public C0213b(C0215d c0215d, SIGMeshBizRequest sIGMeshBizRequest, I i2, IActionListener iActionListener) {
        this.f1261d = c0215d;
        this.f1258a = sIGMeshBizRequest;
        this.f1259b = i2;
        this.f1260c = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        if (i2 == -13) {
            this.f1261d.b(this.f1258a);
        } else {
            this.f1261d.c(this.f1258a);
            Utils.notifyFailed(this.f1260c, i2, str);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onSuccess(Object obj) {
        this.f1261d.c(this.f1258a);
        I i2 = this.f1259b;
        if (i2 == null) {
            Utils.notifySuccess((IActionListener<Object>) this.f1260c, obj);
            return;
        }
        Pair<Integer, ?> response = i2.parseResponse(obj);
        if (response != null) {
            Integer num = (Integer) response.first;
            if (num.intValue() == 0) {
                Utils.notifySuccess((IActionListener<Object>) this.f1260c, response.second);
            } else {
                Utils.notifyFailed(this.f1260c, num.intValue(), (String) response.second);
            }
        }
    }
}
