package datasource.implemention.response;

import datasource.implemention.data.OtaProgressRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class OtaProgressReportResponse extends BaseOutDo {
    public OtaProgressRespData data;

    public Object getData() {
        return this.data;
    }

    public void setData(OtaProgressRespData otaProgressRespData) {
        this.data = otaProgressRespData;
    }
}
