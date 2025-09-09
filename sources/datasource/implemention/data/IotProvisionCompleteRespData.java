package datasource.implemention.data;

import com.alibaba.ailabs.tg.mtop.data.BaseDataBean;
import datasource.bean.ConfigurationData;
import mtopsdk.mtop.domain.IMTOPDataObject;

/* loaded from: classes4.dex */
public class IotProvisionCompleteRespData extends BaseDataBean implements IMTOPDataObject {
    public ConfigurationData model;

    public ConfigurationData getModel() {
        return this.model;
    }

    public void setModel(ConfigurationData configurationData) {
        this.model = configurationData;
    }
}
