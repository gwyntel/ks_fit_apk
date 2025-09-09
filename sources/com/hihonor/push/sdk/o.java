package com.hihonor.push.sdk;

import com.hihonor.push.framework.aidl.entity.BooleanResult;
import com.hihonor.push.sdk.common.data.UpMsgType;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class o implements Callable<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ s f15523a;

    public o(s sVar) {
        this.f15523a = sVar;
    }

    @Override // java.util.concurrent.Callable
    public Boolean call() throws Exception {
        this.f15523a.f15537b.getClass();
        try {
            c1 c1Var = new c1(UpMsgType.QUERY_PUSH_STATUS, null);
            c1Var.f15492e = b.a();
            return Boolean.valueOf(((BooleanResult) b.a(z.f15569c.a(c1Var))).getStatus());
        } catch (Exception e2) {
            throw b.a(e2);
        }
    }
}
