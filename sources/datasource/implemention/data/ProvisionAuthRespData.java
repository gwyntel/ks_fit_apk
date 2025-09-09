package datasource.implemention.data;

import com.alibaba.ailabs.tg.mtop.data.BaseDataBean;
import mtopsdk.mtop.domain.IMTOPDataObject;

/* loaded from: classes4.dex */
public class ProvisionAuthRespData extends BaseDataBean implements IMTOPDataObject {
    public Boolean model;

    public Boolean getModel() {
        return this.model;
    }

    public void setModel(Boolean bool) {
        this.model = bool;
    }
}
