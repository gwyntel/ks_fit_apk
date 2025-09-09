package datasource.implemention.response;

import datasource.implemention.data.GetInfoByAuthInfoRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class GetInfoByAuthInfoResp extends BaseOutDo {
    public GetInfoByAuthInfoRespData data;

    public void setData(GetInfoByAuthInfoRespData getInfoByAuthInfoRespData) {
        this.data = getInfoByAuthInfoRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public GetInfoByAuthInfoRespData m814getData() {
        return this.data;
    }
}
