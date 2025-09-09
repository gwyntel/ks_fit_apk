package datasource.implemention.response;

import datasource.implemention.data.GetProvisionInfoRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class GetProvisionInfoResp extends BaseOutDo {
    public GetProvisionInfoRespData data;

    public void setData(GetProvisionInfoRespData getProvisionInfoRespData) {
        this.data = getProvisionInfoRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public GetProvisionInfoRespData m815getData() {
        return this.data;
    }
}
