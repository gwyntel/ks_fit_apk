package datasource.implemention.response;

import datasource.implemention.data.IotGetListDeviceBaseInfoWithStatusRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IotGetListDeviceBaseInfoWithStatusResp extends BaseOutDo {
    public IotGetListDeviceBaseInfoWithStatusRespData data;

    public void setData(IotGetListDeviceBaseInfoWithStatusRespData iotGetListDeviceBaseInfoWithStatusRespData) {
        this.data = iotGetListDeviceBaseInfoWithStatusRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IotGetListDeviceBaseInfoWithStatusRespData m821getData() {
        return this.data;
    }
}
