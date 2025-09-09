package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import datasource.bean.AddPublish;
import datasource.bean.SubscribeGroupAddr;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.ConfigModelPublicationSetParams;

/* renamed from: a.a.a.a.b.m, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0251m implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1581a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1582b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1583c;

    public RunnableC0251m(DeviceProvisioningWorker deviceProvisioningWorker, ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
        this.f1583c = deviceProvisioningWorker;
        this.f1581a = provisionedMeshNode;
        this.f1582b = bArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        SubscribeGroupAddr subscribeGroupAddr;
        if (this.f1583c.f8716y == null || this.f1583c.f8716y.size() <= 0) {
            if (this.f1583c.f8715x == null || this.f1583c.f8715x.size() <= 0 || (subscribeGroupAddr = (SubscribeGroupAddr) this.f1583c.f8715x.remove(0)) == null || subscribeGroupAddr.getGroupAddr() == null || subscribeGroupAddr.getModelId() == null) {
                return;
            }
            Integer groupAddr = subscribeGroupAddr.getGroupAddr();
            this.f1583c.f8696e.a(this.f1581a, this.f1582b, new byte[]{(byte) ((groupAddr.intValue() >> 8) & 255), (byte) (groupAddr.intValue() & 255)}, subscribeGroupAddr.getModelId().intValue());
            return;
        }
        AddPublish addPublish = (AddPublish) this.f1583c.f8716y.remove(0);
        if (addPublish != null) {
            Integer publishAddr = addPublish.getPublishAddr();
            Integer modelElementAddr = addPublish.getModelElementAddr();
            ConfigModelPublicationSetParams configModelPublicationSetParams = new ConfigModelPublicationSetParams(this.f1581a, new byte[]{(byte) ((modelElementAddr.intValue() >> 8) & 255), (byte) (modelElementAddr.intValue() & 255)}, addPublish.getModelId().intValue(), new byte[]{(byte) ((publishAddr.intValue() >> 8) & 255), (byte) (publishAddr.intValue() & 255)}, addPublish.getAppKeyIndex().intValue());
            configModelPublicationSetParams.setPublishTtl(addPublish.getTtl().intValue());
            configModelPublicationSetParams.setPublicationResolution(addPublish.getPublishPeriod().intValue());
            configModelPublicationSetParams.setPublicationSteps(addPublish.getPublishRetransmitIntervalSteps().intValue());
            configModelPublicationSetParams.setPublishRetransmitCount(addPublish.getPublishRetransmitCount().intValue());
            this.f1583c.f8696e.a(configModelPublicationSetParams);
        }
    }
}
