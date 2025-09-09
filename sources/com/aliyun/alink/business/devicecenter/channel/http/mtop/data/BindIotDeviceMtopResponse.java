package com.aliyun.alink.business.devicecenter.channel.http.mtop.data;

import com.alibaba.ailabs.tg.mtop.data.BaseDataBean;
import java.io.Serializable;
import mtopsdk.mtop.domain.BaseOutDo;

/* loaded from: classes2.dex */
public class BindIotDeviceMtopResponse extends BaseOutDo implements Serializable {
    public Data data;

    public class Data extends BaseDataBean implements Serializable {
        public BindIotDeviceResult model;

        public Data() {
        }

        public BindIotDeviceResult getModel() {
            return this.model;
        }

        public void setModel(BindIotDeviceResult bindIotDeviceResult) {
            this.model = bindIotDeviceResult;
        }
    }

    public void setData(Data data) {
        this.data = data;
    }

    /* renamed from: getData, reason: merged with bridge method [inline-methods] */
    public Data m59getData() {
        return this.data;
    }
}
