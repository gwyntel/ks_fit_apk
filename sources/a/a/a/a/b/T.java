package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.fastjson.JSON;
import datasource.MeshConfigCallback;
import datasource.bean.IotDevice;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes.dex */
public class T implements MeshConfigCallback<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IotDevice f1229a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService f1230b;

    public T(MeshService meshService, IotDevice iotDevice) {
        this.f1230b = meshService;
        this.f1229a = iotDevice;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(String str) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "getInfoByAuthInfo request success");
        if (this.f1230b.mCurrentProvisionMeshNode != null && !this.f1230b.mCurrentProvisionMeshNode.getSupportFastProvision()) {
            this.f1230b.mConnectToMeshNetwork = true;
            this.f1230b.sendBroadcastIsConnected(true, null);
        }
        this.f1230b.mHandler.removeCallbacks(this.f1230b.mProvisionTimeout);
        this.f1230b.sendBroadcastBindState(1, JSON.toJSONString(this.f1229a));
        this.f1230b.configProxyFilter();
        if (this.f1230b.mFastProvisionWorker != null) {
            this.f1230b.mFastProvisionWorker.l();
        }
        if (this.f1230b.mCurrentProvisionMeshNode == null || this.f1230b.mUnprovisionedMeshNodeData == null) {
            return;
        }
        try {
            a.a.a.a.b.e.a.a(String.valueOf(this.f1230b.mUnprovisionedMeshNodeData.getProductId()), MeshParserUtils.bytesToHex(this.f1230b.mUnprovisionedMeshNodeData.getDeviceUuid(), false), this.f1230b.mUnprovisionedMeshNodeData.getDeviceMac(), this.f1230b.mCurrentProvisionMeshNode.getUnicastAddressInt(), MeshParserUtils.bytesToHex(this.f1230b.mCurrentProvisionMeshNode.getDeviceKey(), false));
        } catch (Exception unused) {
        }
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "getInfoByAuthInfo request failed, errorMessage: " + str2);
        this.f1230b.sendBroadcastBindState(-1, str2);
    }
}
