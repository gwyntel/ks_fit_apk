package datasource.implemention.response;

import datasource.implemention.data.IotGetUserDeviceListRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IotGetUserDeviceListResponse extends BaseOutDo {
    public IotGetUserDeviceListRespData data;

    public void setData(IotGetUserDeviceListRespData iotGetUserDeviceListRespData) {
        this.data = iotGetUserDeviceListRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IotGetUserDeviceListRespData m822getData() {
        return this.data;
    }
}
