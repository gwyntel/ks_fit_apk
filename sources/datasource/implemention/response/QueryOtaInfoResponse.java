package datasource.implemention.response;

import datasource.implemention.data.DeviceVersionInfo;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class QueryOtaInfoResponse extends BaseOutDo {
    public DeviceVersionInfo data;

    public Object getData() {
        return this.data;
    }

    public void setData(DeviceVersionInfo deviceVersionInfo) {
        this.data = deviceVersionInfo;
    }
}
