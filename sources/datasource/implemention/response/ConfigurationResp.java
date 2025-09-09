package datasource.implemention.response;

import datasource.implemention.data.ConfigurationRespData;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes4.dex */
public class ConfigurationResp extends BaseOutDo {
    public ConfigurationRespData data;

    public void setData(ConfigurationRespData configurationRespData) {
        this.data = configurationRespData;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public ConfigurationRespData m813getData() {
        return this.data;
    }
}
