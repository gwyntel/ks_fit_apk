package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import datasource.bean.AddPublish;
import datasource.bean.SubscribeGroupAddr;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.ConfigModelPublicationSetParams;

/* loaded from: classes.dex */
public class P implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1222a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1223b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ MeshService f1224c;

    public P(MeshService meshService, ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
        this.f1224c = meshService;
        this.f1222a = provisionedMeshNode;
        this.f1223b = bArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        SubscribeGroupAddr subscribeGroupAddr;
        if (this.f1224c.mPublishGroupAddrs == null || this.f1224c.mPublishGroupAddrs.size() <= 0) {
            if (this.f1224c.mSubscribeGroupAddrs == null || this.f1224c.mSubscribeGroupAddrs.size() <= 0 || (subscribeGroupAddr = (SubscribeGroupAddr) this.f1224c.mSubscribeGroupAddrs.remove(0)) == null || subscribeGroupAddr.getGroupAddr() == null || subscribeGroupAddr.getModelId() == null) {
                return;
            }
            Integer groupAddr = subscribeGroupAddr.getGroupAddr();
            this.f1224c.mMeshManagerApi.a(this.f1222a, this.f1223b, new byte[]{(byte) ((groupAddr.intValue() >> 8) & 255), (byte) (groupAddr.intValue() & 255)}, subscribeGroupAddr.getModelId().intValue());
            return;
        }
        AddPublish addPublish = (AddPublish) this.f1224c.mPublishGroupAddrs.remove(0);
        if (addPublish != null) {
            Integer publishAddr = addPublish.getPublishAddr();
            Integer modelElementAddr = addPublish.getModelElementAddr();
            ConfigModelPublicationSetParams configModelPublicationSetParams = new ConfigModelPublicationSetParams(this.f1222a, new byte[]{(byte) ((modelElementAddr.intValue() >> 8) & 255), (byte) (modelElementAddr.intValue() & 255)}, addPublish.getModelId().intValue(), new byte[]{(byte) ((publishAddr.intValue() >> 8) & 255), (byte) (publishAddr.intValue() & 255)}, addPublish.getAppKeyIndex().intValue());
            configModelPublicationSetParams.setPublishTtl(addPublish.getTtl().intValue());
            configModelPublicationSetParams.setPublicationResolution(addPublish.getPublishPeriod().intValue());
            configModelPublicationSetParams.setPublicationSteps(addPublish.getPublishRetransmitIntervalSteps().intValue());
            configModelPublicationSetParams.setPublishRetransmitCount(addPublish.getPublishRetransmitCount().intValue());
            this.f1224c.mMeshManagerApi.a(configModelPublicationSetParams);
        }
    }
}
