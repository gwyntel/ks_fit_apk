package datasource.implemention.response;

import datasource.implemention.data.IotCustomGroupControllRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IotCustomGroupControlResp extends BaseOutDo {
    public IotCustomGroupControllRespData data;

    public void setData(IotCustomGroupControllRespData iotCustomGroupControllRespData) {
        this.data = iotCustomGroupControllRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IotCustomGroupControllRespData m817getData() {
        return this.data;
    }
}
