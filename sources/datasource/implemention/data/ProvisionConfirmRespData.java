package datasource.implemention.data;

import com.alibaba.ailabs.tg.mtop.data.BaseDataBean;
import datasource.bean.ServerConfirmation;
import mtopsdk.mtop.domain.IMTOPDataObject;

/* loaded from: classes4.dex */
public class ProvisionConfirmRespData extends BaseDataBean implements IMTOPDataObject {
    public ServerConfirmation model;

    public ServerConfirmation getModel() {
        return this.model;
    }

    public void setModel(ServerConfirmation serverConfirmation) {
        this.model = serverConfirmation;
    }
}
