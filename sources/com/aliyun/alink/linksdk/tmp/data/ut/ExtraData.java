package com.aliyun.alink.linksdk.tmp.data.ut;

import com.aliyun.alink.linksdk.tmp.data.device.Option;
import com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;

/* loaded from: classes2.dex */
public class ExtraData {
    public Option option;
    public String performanceId;

    public ExtraData(Option option) {
        if (option == null) {
            this.option = new Option(TmpEnum.QoSLevel.QoS_CON);
        } else {
            this.option = option;
        }
    }

    public ExtraData(PanelMethodExtraData panelMethodExtraData) {
        if (panelMethodExtraData == null) {
            this.option = new Option(TmpEnum.QoSLevel.QoS_CON);
            return;
        }
        this.performanceId = panelMethodExtraData.performanceId;
        Option option = new Option(panelMethodExtraData.mQoSLevel);
        this.option = option;
        option.setNeedRsp(panelMethodExtraData.mNeedRsp);
    }
}
