package datasource.implemention.response;

import datasource.implemention.data.IoTWakeUpData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IoTWakeUpResp extends BaseOutDo {
    public IoTWakeUpData data;

    public void setData(IoTWakeUpData ioTWakeUpData) {
        this.data = ioTWakeUpData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IoTWakeUpData m816getData() {
        return this.data;
    }
}
