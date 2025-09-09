package datasource.implemention.response;

import datasource.implemention.data.IotReportDevicesStatusRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IotReportDevicesStatusResp extends BaseOutDo {
    public IotReportDevicesStatusRespData data;

    public void setData(IotReportDevicesStatusRespData iotReportDevicesStatusRespData) {
        this.data = iotReportDevicesStatusRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IotReportDevicesStatusRespData m824getData() {
        return this.data;
    }
}
