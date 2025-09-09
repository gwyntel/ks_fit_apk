package a.a.a.a.b;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.ListUtils;
import datasource.MeshConfigCallback;
import datasource.bean.ConfigResultMap;
import datasource.bean.ConfigurationData;
import datasource.bean.ProvisionAppKey;
import datasource.bean.SigmeshKey;
import java.util.Collections;
import java.util.List;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* renamed from: a.a.a.a.b.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0229g implements MeshConfigCallback<ConfigurationData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1345a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1346b;

    public C0229g(DeviceProvisioningWorker deviceProvisioningWorker, IActionListener iActionListener) {
        this.f1346b = deviceProvisioningWorker;
        this.f1345a = iActionListener;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(ConfigurationData configurationData) {
        a.a.a.a.b.m.a.a(this.f1346b.f8693b, "provisionComplete request success");
        this.f1346b.f8706o.clear();
        this.f1346b.f8705n.clear();
        if (configurationData == null || configurationData.getConfigResultMap() == null || configurationData.getConfigResultMap().getAddAppKey() == null) {
            return;
        }
        ConfigResultMap configResultMap = configurationData.getConfigResultMap();
        this.f1346b.f8715x = configResultMap.getSubscribeGroupAddr();
        this.f1346b.f8716y = configResultMap.getConfigModelPublication();
        List<Integer> appKeyIndexes = configResultMap.getAddAppKey().getAppKeyIndexes();
        SigmeshKey sigmeshKey = (SigmeshKey) this.f1346b.f8711t.get(((Integer) this.f1346b.f8713v.get(0)).intValue());
        List<SigmeshKey> sigmeshKeys = configResultMap.getSigmeshKeys();
        if (!ListUtils.isEmpty(sigmeshKeys)) {
            sigmeshKey = sigmeshKeys.get(0);
        }
        List<ProvisionAppKey> provisionAppKeys = sigmeshKey.getProvisionAppKeys();
        Collections.sort(provisionAppKeys);
        for (ProvisionAppKey provisionAppKey : provisionAppKeys) {
            if (provisionAppKey != null && (this.f1346b.f8700i instanceof ProvisionedMeshNode)) {
                ((ProvisionedMeshNode) this.f1346b.f8700i).setAddedAppKey(provisionAppKey.getAppKeyIndex(), provisionAppKey.getAppKey());
            }
            if (this.f1346b.f8717z.getFeatureFlag1() != -109 || provisionAppKey == null || provisionAppKey.getAppKeyIndex() == 0) {
                if (provisionAppKey != null && appKeyIndexes.contains(Integer.valueOf(provisionAppKey.getAppKeyIndex()))) {
                    this.f1346b.f8706o.add(provisionAppKey.getAppKey());
                    this.f1346b.f8705n.add(Integer.valueOf(provisionAppKey.getAppKeyIndex()));
                }
            }
        }
        this.f1346b.f8712u.clear();
        if (configResultMap.getBindModel() != null) {
            this.f1346b.f8712u.addAll(configResultMap.getBindModel());
        }
        this.f1346b.f8698g = true;
        if (this.f1346b.f8700i != null && !this.f1346b.f8700i.getSupportFastProvision()) {
            a.a.a.a.b.m.a.a(this.f1346b.f8693b, "IsProvisioningComplete: true");
            this.f1346b.c(MeshNodeStatus.PROVISIONING_COMPLETE.getState());
        }
        if (this.f1346b.f8701j) {
            this.f1346b.f8707p = true;
            DeviceProvisioningWorker deviceProvisioningWorker = this.f1346b;
            deviceProvisioningWorker.onCompositionDataStatusReceived((ProvisionedMeshNode) deviceProvisioningWorker.f8700i);
        }
        this.f1345a.onSuccess(Boolean.TRUE);
        a.a.a.a.b.m.a.a(this.f1346b.f8693b, "IsReconnecting: true");
        LocalBroadcastManager.getInstance(this.f1346b.f8694c).sendBroadcast(new Intent(Utils.ACTION_IS_RECONNECTING).putExtra(Utils.EXTRA_DATA, true));
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(this.f1346b.f8693b, "provisionComplete request failed, errorMessage: " + str2);
        this.f1345a.onFailure(-1, str + " " + str2);
    }
}
