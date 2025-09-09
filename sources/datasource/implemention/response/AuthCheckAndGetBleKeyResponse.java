package datasource.implemention.response;

import datasource.implemention.data.AuthCheckAndGetBleKeyRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class AuthCheckAndGetBleKeyResponse extends BaseOutDo {
    public AuthCheckAndGetBleKeyRespData data;

    public void setData(AuthCheckAndGetBleKeyRespData authCheckAndGetBleKeyRespData) {
        this.data = authCheckAndGetBleKeyRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public AuthCheckAndGetBleKeyRespData m811getData() {
        return this.data;
    }
}
