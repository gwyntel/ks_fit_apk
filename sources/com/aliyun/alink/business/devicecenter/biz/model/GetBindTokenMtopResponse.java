package com.aliyun.alink.business.devicecenter.biz.model;

import com.alibaba.ailabs.tg.mtop.data.BaseDataBean;
import java.io.Serializable;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes2.dex */
public class GetBindTokenMtopResponse extends BaseOutDo implements Serializable {
    public Data data;

    public class Data extends BaseDataBean implements Serializable {
        public GetBindTokenResponse model;

        public Data() {
        }

        public GetBindTokenResponse getModel() {
            return this.model;
        }

        public void setModel(GetBindTokenResponse getBindTokenResponse) {
            this.model = getBindTokenResponse;
        }
    }

    public void setData(Data data) {
        this.data = data;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public Data m58getData() {
        return this.data;
    }
}
