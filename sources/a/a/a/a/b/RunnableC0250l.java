package a.a.a.a.b;

import androidx.core.view.MotionEventCompat;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import datasource.bean.AddPublish;
import datasource.bean.SubscribeGroupAddr;
import java.util.List;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.ConfigModelPublicationSetParams;

/* renamed from: a.a.a.a.b.l, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0250l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1559a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f1560b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f1561c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1562d;

    public RunnableC0250l(DeviceProvisioningWorker deviceProvisioningWorker, ProvisionedMeshNode provisionedMeshNode, int i2, int i3) {
        this.f1562d = deviceProvisioningWorker;
        this.f1559a = provisionedMeshNode;
        this.f1560b = i2;
        this.f1561c = i3;
    }

    @Override // java.lang.Runnable
    public void run() {
        SubscribeGroupAddr subscribeGroupAddr;
        if (this.f1562d.f8714w.size() > 0) {
            this.f1562d.a(this.f1559a, AddressUtils.getUnicastAddressBytes(this.f1560b), this.f1561c, (List<Integer>) this.f1562d.f8714w);
            return;
        }
        if (this.f1562d.f8716y == null || this.f1562d.f8716y.size() <= 0) {
            if (this.f1562d.f8715x == null || this.f1562d.f8715x.size() <= 0 || (subscribeGroupAddr = (SubscribeGroupAddr) this.f1562d.f8715x.remove(0)) == null || subscribeGroupAddr.getGroupAddr() == null || subscribeGroupAddr.getModelId() == null) {
                return;
            }
            Integer groupAddr = subscribeGroupAddr.getGroupAddr();
            int iIntValue = subscribeGroupAddr.getModelElementAddr().intValue();
            this.f1562d.f8696e.a(this.f1559a, new byte[]{(byte) (iIntValue & 255), (byte) (iIntValue & MotionEventCompat.ACTION_POINTER_INDEX_MASK)}, new byte[]{(byte) ((groupAddr.intValue() >> 8) & 255), (byte) (groupAddr.intValue() & 255)}, subscribeGroupAddr.getModelId().intValue());
            return;
        }
        AddPublish addPublish = (AddPublish) this.f1562d.f8716y.remove(0);
        if (addPublish != null) {
            Integer publishAddr = addPublish.getPublishAddr();
            Integer modelElementAddr = addPublish.getModelElementAddr();
            ConfigModelPublicationSetParams configModelPublicationSetParams = new ConfigModelPublicationSetParams(this.f1559a, new byte[]{(byte) ((modelElementAddr.intValue() >> 8) & 255), (byte) (modelElementAddr.intValue() & 255)}, addPublish.getModelId().intValue(), new byte[]{(byte) ((publishAddr.intValue() >> 8) & 255), (byte) (publishAddr.intValue() & 255)}, addPublish.getAppKeyIndex().intValue());
            configModelPublicationSetParams.setPublishTtl(addPublish.getTtl().intValue());
            configModelPublicationSetParams.setPublicationResolution(addPublish.getPublishPeriod().intValue());
            configModelPublicationSetParams.setPublicationSteps(addPublish.getPublishRetransmitIntervalSteps().intValue());
            configModelPublicationSetParams.setPublishRetransmitCount(addPublish.getPublishRetransmitCount().intValue());
            this.f1562d.f8696e.a(configModelPublicationSetParams);
        }
    }
}
