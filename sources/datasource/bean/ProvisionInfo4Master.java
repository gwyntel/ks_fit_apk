package datasource.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class ProvisionInfo4Master {
    public ActivePayload activePayload;
    public int nodeMaxSize;
    public int provisionerAddr;
    public List<SigmeshIotDev> sigmeshIotDevList = null;
    public List<SigmeshIotDev> shareDeviceList = null;
    public List<SigmeshKey> sigmeshKeys = null;
    public List<AddModelClient> addModelClient = null;
    public List<SubscribeGroupAddr_t> subscribeGroupAddr = null;

    public ActivePayload getActivePayload() {
        return this.activePayload;
    }

    public List<AddModelClient> getAddModelClient() {
        return this.addModelClient;
    }

    public int getNodeMaxSize() {
        return this.nodeMaxSize;
    }

    public int getProvisionerAddr() {
        return this.provisionerAddr;
    }

    public List<SigmeshIotDev> getShareDeviceList() {
        return this.shareDeviceList;
    }

    public List<SigmeshIotDev> getSigmeshIotDevList() {
        return this.sigmeshIotDevList;
    }

    public List<SigmeshKey> getSigmeshKeys() {
        return this.sigmeshKeys;
    }

    public List<SubscribeGroupAddr_t> getSubscribeGroupAddr() {
        return this.subscribeGroupAddr;
    }

    public void setActivePayload(ActivePayload activePayload) {
        this.activePayload = activePayload;
    }

    public void setAddModelClient(List<AddModelClient> list) {
        this.addModelClient = list;
    }

    public void setNodeMaxSize(int i2) {
        this.nodeMaxSize = i2;
    }

    public void setProvisionerAddr(int i2) {
        this.provisionerAddr = i2;
    }

    public void setShareDeviceList(List<SigmeshIotDev> list) {
        this.shareDeviceList = list;
    }

    public void setSigmeshIotDevList(List<SigmeshIotDev> list) {
        this.sigmeshIotDevList = list;
    }

    public void setSigmeshKeys(List<SigmeshKey> list) {
        this.sigmeshKeys = list;
    }

    public void setSubscribeGroupAddr(List<SubscribeGroupAddr_t> list) {
        this.subscribeGroupAddr = list;
    }
}
