package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import com.aliyun.alink.linksdk.cmp.manager.connect.IRegisterConnectListener;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public class c implements IRegisterConnectListener {

    /* renamed from: a, reason: collision with root package name */
    protected IDevListener f11157a;

    /* renamed from: b, reason: collision with root package name */
    protected String f11158b;

    public c(String str, IDevListener iDevListener) {
        this.f11157a = iDevListener;
        this.f11158b = str;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
    public void onFailure(AError aError) {
        final ErrorInfo errorInfo = aError == null ? new ErrorInfo(300, "param is invalid") : new ErrorInfo(aError.getSubCode(), aError.getMsg());
        if (this.f11157a != null) {
            com.aliyun.alink.linksdk.tmp.connect.f.f11183e.post(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.connect.entity.cmp.c.2
                @Override // java.lang.Runnable
                public void run() {
                    c.this.f11157a.onFail(null, errorInfo);
                }
            });
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
    public void onSuccess() {
        final OutputParams outputParams = new OutputParams(com.aliyun.alink.linksdk.tmp.connect.a.f11115b, new ValueWrapper.StringValueWrapper(this.f11158b));
        if (this.f11157a != null) {
            com.aliyun.alink.linksdk.tmp.connect.f.f11183e.post(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.connect.entity.cmp.c.1
                @Override // java.lang.Runnable
                public void run() {
                    c.this.f11157a.onSuccess(null, outputParams);
                }
            });
        }
    }
}
