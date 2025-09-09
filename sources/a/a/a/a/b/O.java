package a.a.a.a.b;

import androidx.core.view.MotionEventCompat;
import com.alibaba.ailabs.iot.mesh.MeshService;
import datasource.bean.AddPublish;
import datasource.bean.SubscribeGroupAddr;
import java.util.List;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.ConfigModelPublicationSetParams;

/* loaded from: classes.dex */
public class O implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1218a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f1219b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f1220c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ MeshService f1221d;

    public O(MeshService meshService, ProvisionedMeshNode provisionedMeshNode, int i2, int i3) {
        this.f1221d = meshService;
        this.f1218a = provisionedMeshNode;
        this.f1219b = i2;
        this.f1220c = i3;
    }

    @Override // java.lang.Runnable
    public void run() {
        SubscribeGroupAddr subscribeGroupAddr;
        if (this.f1221d.mModelIds.size() > 0) {
            this.f1221d.bindAppKey(this.f1218a, AddressUtils.getUnicastAddressBytes(this.f1219b), this.f1220c, (List<Integer>) this.f1221d.mModelIds);
            return;
        }
        if (this.f1221d.mPublishGroupAddrs == null || this.f1221d.mPublishGroupAddrs.size() <= 0) {
            if (this.f1221d.mSubscribeGroupAddrs == null || this.f1221d.mSubscribeGroupAddrs.size() <= 0 || (subscribeGroupAddr = (SubscribeGroupAddr) this.f1221d.mSubscribeGroupAddrs.remove(0)) == null || subscribeGroupAddr.getGroupAddr() == null || subscribeGroupAddr.getModelId() == null) {
                return;
            }
            Integer groupAddr = subscribeGroupAddr.getGroupAddr();
            int iIntValue = subscribeGroupAddr.getModelElementAddr().intValue();
            this.f1221d.mMeshManagerApi.a(this.f1218a, new byte[]{(byte) (iIntValue & 255), (byte) (iIntValue & MotionEventCompat.ACTION_POINTER_INDEX_MASK)}, new byte[]{(byte) ((groupAddr.intValue() >> 8) & 255), (byte) (groupAddr.intValue() & 255)}, subscribeGroupAddr.getModelId().intValue());
            return;
        }
        AddPublish addPublish = (AddPublish) this.f1221d.mPublishGroupAddrs.remove(0);
        if (addPublish != null) {
            Integer publishAddr = addPublish.getPublishAddr();
            Integer modelElementAddr = addPublish.getModelElementAddr();
            ConfigModelPublicationSetParams configModelPublicationSetParams = new ConfigModelPublicationSetParams(this.f1218a, new byte[]{(byte) ((modelElementAddr.intValue() >> 8) & 255), (byte) (modelElementAddr.intValue() & 255)}, addPublish.getModelId().intValue(), new byte[]{(byte) ((publishAddr.intValue() >> 8) & 255), (byte) (publishAddr.intValue() & 255)}, addPublish.getAppKeyIndex().intValue());
            configModelPublicationSetParams.setPublishTtl(addPublish.getTtl().intValue());
            configModelPublicationSetParams.setPublicationResolution(addPublish.getPublishPeriod().intValue());
            configModelPublicationSetParams.setPublicationSteps(addPublish.getPublishRetransmitIntervalSteps().intValue());
            configModelPublicationSetParams.setPublishRetransmitCount(addPublish.getPublishRetransmitCount().intValue());
            this.f1221d.mMeshManagerApi.a(configModelPublicationSetParams);
        }
    }
}
