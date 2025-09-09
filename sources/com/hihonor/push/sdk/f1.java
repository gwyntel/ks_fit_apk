package com.hihonor.push.sdk;

import com.hihonor.push.framework.aidl.IMessageEntity;
import com.hihonor.push.framework.aidl.entity.RequestHeader;
import com.hihonor.push.sdk.common.data.ApiException;

/* loaded from: classes3.dex */
public abstract class f1<TResult> {

    /* renamed from: a, reason: collision with root package name */
    public n0<TResult> f15488a;

    /* renamed from: b, reason: collision with root package name */
    public final String f15489b;

    /* renamed from: c, reason: collision with root package name */
    public final IMessageEntity f15490c;

    /* renamed from: d, reason: collision with root package name */
    public final w f15491d;

    /* renamed from: e, reason: collision with root package name */
    public RequestHeader f15492e;

    public f1(String str, IMessageEntity iMessageEntity) {
        this.f15489b = str;
        this.f15490c = iMessageEntity;
        this.f15491d = w.a(str);
    }

    public abstract void a(ApiException apiException, Object obj);

    public final void b(ApiException apiException, Object obj) {
        if (this.f15488a != null) {
            a(apiException, obj);
        }
    }
}
