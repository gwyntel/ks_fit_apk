package datasource.implemention.response;

import datasource.implemention.data.IotProvisionCompleteRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IotProvisionCompleteResp extends BaseOutDo {
    public IotProvisionCompleteRespData data;

    public void setData(IotProvisionCompleteRespData iotProvisionCompleteRespData) {
        this.data = iotProvisionCompleteRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IotProvisionCompleteRespData m823getData() {
        return this.data;
    }
}
