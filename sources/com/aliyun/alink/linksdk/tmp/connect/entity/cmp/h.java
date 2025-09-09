package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.cmp.api.CommonRequest;
import com.aliyun.alink.linksdk.tmp.data.device.Option;

/* loaded from: classes2.dex */
public class h extends com.aliyun.alink.linksdk.tmp.connect.d<CommonRequest> {
    public h(CommonRequest commonRequest) {
        super(commonRequest);
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.d
    public boolean a() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.tmp.connect.d
    public String d() {
        return ((CommonRequest) this.f11143a).topic;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(Option option) {
        T t2 = this.f11143a;
        if (t2 == 0) {
            return;
        }
        if (option == null) {
            ((CommonRequest) t2).type = AlcsCoAPConstant.Type.CON;
            ((CommonRequest) t2).rspType = 0;
        } else {
            ((CommonRequest) t2).type = AlcsCoAPConstant.Type.valueOf(option.getQoSLevel().getValue());
            ((CommonRequest) this.f11143a).rspType = Integer.valueOf(!option.isNeedRsp() ? 1 : 0);
        }
    }
}
