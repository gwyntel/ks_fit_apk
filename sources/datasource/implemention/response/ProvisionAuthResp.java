package datasource.implemention.response;

import datasource.implemention.data.ProvisionAuthRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class ProvisionAuthResp extends BaseOutDo {
    public ProvisionAuthRespData data;

    public void setData(ProvisionAuthRespData provisionAuthRespData) {
        this.data = provisionAuthRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public ProvisionAuthRespData m825getData() {
        return this.data;
    }
}
