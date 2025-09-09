package datasource.implemention.response;

import datasource.implemention.data.AuthRandomIdRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class AuthRandomIdResponse extends BaseOutDo {
    public AuthRandomIdRespData data;

    public void setData(AuthRandomIdRespData authRandomIdRespData) {
        this.data = authRandomIdRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public AuthRandomIdRespData m812getData() {
        return this.data;
    }
}
