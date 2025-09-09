package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.fastjson.JSON;
import datasource.MeshConfigCallback;
import datasource.bean.ProvisionInfo4Master;

/* loaded from: classes.dex */
public class Z implements MeshConfigCallback<ProvisionInfo4Master> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MeshService f1239a;

    public Z(MeshService meshService) {
        this.f1239a = meshService;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(ProvisionInfo4Master provisionInfo4Master) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "fullRefreshProvisionInfo request success:" + JSON.toJSONString(provisionInfo4Master));
        try {
            G.a().b(provisionInfo4Master);
        } catch (Exception unused) {
        }
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(MeshService.TAG, "fullRefreshProvisionInfo request failed, errorMessage: " + str2);
    }
}
