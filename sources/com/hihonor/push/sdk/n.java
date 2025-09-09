package com.hihonor.push.sdk;

import android.content.Context;
import com.hihonor.push.sdk.common.data.UpMsgType;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class n implements Callable<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ s f15521a;

    public n(s sVar) {
        this.f15521a = sVar;
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        s sVar = this.f15521a;
        a0 a0Var = sVar.f15537b;
        Context context = sVar.f15536a;
        a0Var.getClass();
        try {
            d1 d1Var = new d1(UpMsgType.UNREGISTER_PUSH_TOKEN, null);
            d1Var.f15492e = b.a();
            b.a(z.f15569c.a(d1Var));
            d.f15472b.a(context, null);
            return null;
        } catch (Exception e2) {
            throw b.a(e2);
        }
    }
}
