package datasource.implemention.response;

import datasource.implemention.data.IotDeviceControlRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IotDeviceControlResp extends BaseOutDo {
    public IotDeviceControlRespData data;

    public void setData(IotDeviceControlRespData iotDeviceControlRespData) {
        this.data = iotDeviceControlRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IotDeviceControlRespData m819getData() {
        return this.data;
    }
}
