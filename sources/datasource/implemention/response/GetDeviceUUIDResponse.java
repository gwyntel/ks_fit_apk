package datasource.implemention.response;

import datasource.implemention.data.GetDeviceUUIDRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class GetDeviceUUIDResponse extends BaseOutDo {
    public GetDeviceUUIDRespData data;

    public Object getData() {
        return this.data;
    }

    public void setData(GetDeviceUUIDRespData getDeviceUUIDRespData) {
        this.data = getDeviceUUIDRespData;
    }
}
