package datasource.implemention.response;

import datasource.implemention.data.ProvisionConfirmRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class ProvisionConfirmResp extends BaseOutDo {
    public ProvisionConfirmRespData data;

    public void setData(ProvisionConfirmRespData provisionConfirmRespData) {
        this.data = provisionConfirmRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public ProvisionConfirmRespData m826getData() {
        return this.data;
    }
}
