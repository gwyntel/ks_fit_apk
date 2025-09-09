package com.aliyun.alink.linksdk.tmp.resource;

import com.aliyun.alink.linksdk.cmp.api.ResourceRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceResponseListener;
import com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;

/* loaded from: classes2.dex */
public class d implements ITResResponseCallback {

    /* renamed from: a, reason: collision with root package name */
    protected IResourceResponseListener f11417a;

    /* renamed from: b, reason: collision with root package name */
    protected ResourceRequest f11418b;

    /* renamed from: c, reason: collision with root package name */
    protected AResource f11419c;

    public d(ResourceRequest resourceRequest, AResource aResource, IResourceResponseListener iResourceResponseListener) {
        this.f11418b = resourceRequest;
        this.f11417a = iResourceResponseListener;
        this.f11419c = aResource;
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback
    public void onComplete(String str, ErrorInfo errorInfo, Object obj) {
        if (errorInfo != null) {
            errorInfo.isSuccess();
        }
        new AResponse().data = obj;
        this.f11417a.onResponse(this.f11419c, this.f11418b, null);
    }
}
