package com.aliyun.alink.business.devicecenter.biz.model;

import com.alibaba.ailabs.tg.mtop.data.BaseDataBean;
import java.io.Serializable;
import java.util.List;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes2.dex */
public class CheckBindTokenMtopResponse extends BaseOutDo implements Serializable {
    public Data data;

    public class Data extends BaseDataBean implements Serializable {
        public List<CheckBindTokenResponse> model;

        public Data() {
        }

        public List<CheckBindTokenResponse> getModel() {
            return this.model;
        }

        public void setModel(List<CheckBindTokenResponse> list) {
            this.model = list;
        }

        public String toString() {
            return "CheckTokenResponseData{model=" + this.model + '}';
        }
    }

    public void setData(Data data) {
        this.data = data;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public Data m57getData() {
        return this.data;
    }
}
