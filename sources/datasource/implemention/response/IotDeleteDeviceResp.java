package datasource.implemention.response;

import datasource.implemention.data.IotDeleteDeviceRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IotDeleteDeviceResp extends BaseOutDo {
    public IotDeleteDeviceRespData data;

    public void setData(IotDeleteDeviceRespData iotDeleteDeviceRespData) {
        this.data = iotDeleteDeviceRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IotDeleteDeviceRespData m818getData() {
        return this.data;
    }
}
