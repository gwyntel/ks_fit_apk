package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import datasource.bean.BindModel;
import java.util.List;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.AddressUtils;

/* loaded from: classes.dex */
public class N implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1215a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f1216b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ MeshService f1217c;

    public N(MeshService meshService, ProvisionedMeshNode provisionedMeshNode, int i2) {
        this.f1217c = meshService;
        this.f1215a = provisionedMeshNode;
        this.f1216b = i2;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f1217c.mBindModel.size() == 0) {
            if (this.f1217c.mAppKeyQueue.isEmpty()) {
                this.f1217c.getInfoByAuthInfo(this.f1215a.getUnicastAddress());
                return;
            } else {
                this.f1217c.addShareAppKey(this.f1215a);
                return;
            }
        }
        BindModel bindModel = (BindModel) this.f1217c.mBindModel.get(0);
        if (bindModel == null || bindModel.getModelElementAddr() == null) {
            return;
        }
        Integer modelElementAddr = bindModel.getModelElementAddr();
        this.f1217c.mModelIds = bindModel.getModelIds();
        if (modelElementAddr == null || this.f1217c.mModelIds == null) {
            return;
        }
        this.f1217c.bindAppKey(this.f1215a, AddressUtils.getUnicastAddressBytes(modelElementAddr.intValue()), this.f1216b, (List<Integer>) this.f1217c.mModelIds);
    }
}
