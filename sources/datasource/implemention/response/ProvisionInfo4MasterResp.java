package datasource.implemention.response;

import datasource.implemention.data.ProvisionInfo4MasterRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class ProvisionInfo4MasterResp extends BaseOutDo {
    public ProvisionInfo4MasterRespData data;

    public void setData(ProvisionInfo4MasterRespData provisionInfo4MasterRespData) {
        this.data = provisionInfo4MasterRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public ProvisionInfo4MasterRespData m827getData() {
        return this.data;
    }
}
