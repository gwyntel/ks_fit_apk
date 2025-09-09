package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.fastjson.JSON;
import datasource.MeshConfigCallback;
import datasource.bean.IotDevice;

/* loaded from: classes.dex */
public class U implements MeshConfigCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IotDevice f1231a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService f1232b;

    public U(MeshService meshService, IotDevice iotDevice) {
        this.f1232b = meshService;
        this.f1231a = iotDevice;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "getInfoByAuthInfo request success");
        this.f1232b.mConnectToMeshNetwork = true;
        this.f1232b.sendBroadcastIsConnected(true, null);
        this.f1232b.mHandler.removeCallbacks(this.f1232b.mProvisionTimeout);
        this.f1232b.sendBroadcastBindState(1, JSON.toJSONString(this.f1231a));
        this.f1232b.configProxyFilter();
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "getInfoByAuthInfo request failed, errorMessage: " + str2);
        this.f1232b.sendBroadcastBindState(-1, str2);
    }
}
