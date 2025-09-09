package datasource.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class SigmeshKey {
    public List<ProvisionAppKey> provisionAppKeys = null;
    public ProvisionNetKey provisionNetKey;

    public List<ProvisionAppKey> getProvisionAppKeys() {
        return this.provisionAppKeys;
    }

    public ProvisionNetKey getProvisionNetKey() {
        return this.provisionNetKey;
    }

    public void setProvisionAppKeys(List<ProvisionAppKey> list) {
        this.provisionAppKeys = list;
    }

    public void setProvisionNetKey(ProvisionNetKey provisionNetKey) {
        this.provisionNetKey = provisionNetKey;
    }
}
