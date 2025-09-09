package datasource.implemention.response;

import datasource.implemention.data.IoTGatewayInvokeData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class IotGatewayInvokeEventResp extends BaseOutDo {
    public IoTGatewayInvokeData data;

    public void setData(IoTGatewayInvokeData ioTGatewayInvokeData) {
        this.data = ioTGatewayInvokeData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public IoTGatewayInvokeData m820getData() {
        return this.data;
    }
}
