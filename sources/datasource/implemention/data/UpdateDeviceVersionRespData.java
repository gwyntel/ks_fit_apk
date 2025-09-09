package datasource.implemention.data;

import com.alibaba.ailabs.tg.mtop.data.BaseDataBean;
import mtopsdk.mtop.domain.IMTOPDataObject;

/* loaded from: classes4.dex */
public class UpdateDeviceVersionRespData extends BaseDataBean implements IMTOPDataObject {
    public boolean model;

    public boolean getModel() {
        return this.model;
    }

    public void setModel(boolean z2) {
        this.model = z2;
    }
}
