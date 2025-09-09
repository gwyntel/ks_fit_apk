package datasource.implemention.response;

import datasource.implemention.data.UpdateDeviceVersionRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class UpdateDeviceVersionResponse extends BaseOutDo {
    public UpdateDeviceVersionRespData data;

    public Object getData() {
        return this.data;
    }

    public void setData(UpdateDeviceVersionRespData updateDeviceVersionRespData) {
        this.data = updateDeviceVersionRespData;
    }
}
