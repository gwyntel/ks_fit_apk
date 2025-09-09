package com.aliyun.alink.linksdk.tmp.resource;

import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectPublishResourceListener;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class f implements IConnectPublishResourceListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11423a = "[Tmp]TResPublicWrapper";

    /* renamed from: b, reason: collision with root package name */
    private IPublishResourceListener f11424b;

    /* renamed from: c, reason: collision with root package name */
    private String f11425c;

    public f(String str, IPublishResourceListener iPublishResourceListener) {
        this.f11424b = iPublishResourceListener;
        this.f11425c = str;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectPublishResourceListener
    public void onFailure(AResource aResource, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11423a, "onSuccess mResId:" + this.f11425c + " mListener:" + this.f11424b + " aResource:" + aResource);
        if (this.f11424b == null || aError == null || aError.getCode() == 517) {
            return;
        }
        this.f11424b.onError(this.f11425c, aError);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectPublishResourceListener
    public void onSuccess(AResource aResource) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11423a, "onSuccess mResId:" + this.f11425c + " mListener:" + this.f11424b + " aResource:" + aResource);
        IPublishResourceListener iPublishResourceListener = this.f11424b;
        if (iPublishResourceListener != null) {
            iPublishResourceListener.onSuccess(this.f11425c, null);
        }
    }
}
