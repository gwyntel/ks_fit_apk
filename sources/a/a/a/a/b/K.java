package a.a.a.a.b;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.ailabs.iot.mesh.MeshService;
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

/* loaded from: classes.dex */
public class K implements MeshConfigCallback<ConfigurationData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1208a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService f1209b;

    public K(MeshService meshService, IActionListener iActionListener) {
        this.f1209b = meshService;
        this.f1208a = iActionListener;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(ConfigurationData configurationData) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "provisionComplete request success");
        this.f1209b.mAppKeyQueue.clear();
        this.f1209b.mAppKeyIndexQueue.clear();
        if (configurationData == null || configurationData.getConfigResultMap() == null || configurationData.getConfigResultMap().getAddAppKey() == null) {
            return;
        }
        ConfigResultMap configResultMap = configurationData.getConfigResultMap();
        this.f1209b.mSubscribeGroupAddrs = configResultMap.getSubscribeGroupAddr();
        this.f1209b.mPublishGroupAddrs = configResultMap.getConfigModelPublication();
        List<Integer> appKeyIndexes = configResultMap.getAddAppKey().getAppKeyIndexes();
        SigmeshKey sigmeshKey = (SigmeshKey) this.f1209b.mSigmeshKeys.get(((Integer) this.f1209b.mNetKeyIndexes.get(0)).intValue());
        List<SigmeshKey> sigmeshKeys = configResultMap.getSigmeshKeys();
        if (!ListUtils.isEmpty(sigmeshKeys)) {
            sigmeshKey = sigmeshKeys.get(0);
        }
        List<ProvisionAppKey> provisionAppKeys = sigmeshKey.getProvisionAppKeys();
        if (a.a.a.a.b.d.a.f1334a || !this.f1209b.mUnprovisionedMeshNodeData.isFastProvisionMesh()) {
            Collections.reverse(provisionAppKeys);
        }
        for (ProvisionAppKey provisionAppKey : provisionAppKeys) {
            if (provisionAppKey != null && appKeyIndexes.contains(Integer.valueOf(provisionAppKey.getAppKeyIndex()))) {
                this.f1209b.mAppKeyQueue.add(provisionAppKey.getAppKey());
                this.f1209b.mAppKeyIndexQueue.add(Integer.valueOf(provisionAppKey.getAppKeyIndex()));
            }
        }
        this.f1209b.mBindModel.clear();
        if (configResultMap.getBindModel() != null) {
            this.f1209b.mBindModel.addAll(configResultMap.getBindModel());
        }
        this.f1209b.mIsProvisioningComplete = true;
        if (this.f1209b.mCurrentProvisionMeshNode != null && !this.f1209b.mCurrentProvisionMeshNode.getSupportFastProvision()) {
            a.a.a.a.b.m.a.a(MeshService.TAG, "IsProvisioningComplete: true");
            this.f1209b.sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_COMPLETE.getState());
        }
        if (this.f1209b.mDeviceIsReadyInConfigurationStep) {
            this.f1209b.mShouldAddAppKeyBeAdded = true;
            MeshService meshService = this.f1209b;
            meshService.onCompositionDataStatusReceived((ProvisionedMeshNode) meshService.mCurrentProvisionMeshNode);
        }
        this.f1208a.onSuccess(Boolean.TRUE);
        a.a.a.a.b.m.a.a(MeshService.TAG, "IsReconnecting: true");
        LocalBroadcastManager.getInstance(this.f1209b).sendBroadcast(new Intent(Utils.ACTION_IS_RECONNECTING).putExtra(Utils.EXTRA_DATA, true));
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(MeshService.TAG, "provisionComplete request failed, errorMessage: " + str2);
        this.f1208a.onFailure(-1, str + " " + str2);
    }
}
