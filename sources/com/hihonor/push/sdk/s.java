package com.hihonor.push.sdk;

import android.content.Context;
import com.hihonor.push.sdk.common.data.ApiException;
import com.hihonor.push.sdk.internal.HonorPushErrorEnum;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class s {

    /* renamed from: a, reason: collision with root package name */
    public final Context f15536a;

    /* renamed from: b, reason: collision with root package name */
    public a0 f15537b = new a0();

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Callable f15538a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ HonorPushCallback f15539b;

        public a(Callable callable, HonorPushCallback honorPushCallback) {
            this.f15538a = callable;
            this.f15539b = honorPushCallback;
        }

        @Override // java.lang.Runnable
        public void run() throws Exception {
            try {
                Object objCall = this.f15538a.call();
                s sVar = s.this;
                HonorPushCallback honorPushCallback = this.f15539b;
                sVar.getClass();
                b1.a(new t(sVar, honorPushCallback, objCall));
            } catch (ApiException e2) {
                s.a(s.this, this.f15539b, e2.getErrorCode(), e2.getMessage());
            } catch (Exception unused) {
                s sVar2 = s.this;
                HonorPushCallback honorPushCallback2 = this.f15539b;
                HonorPushErrorEnum honorPushErrorEnum = HonorPushErrorEnum.ERROR_INTERNAL_ERROR;
                s.a(sVar2, honorPushCallback2, honorPushErrorEnum.getErrorCode(), honorPushErrorEnum.getMessage());
            }
        }
    }

    public s(Context context) {
        this.f15536a = context;
    }

    public static void a(s sVar, HonorPushCallback honorPushCallback, int i2, String str) {
        sVar.getClass();
        b1.a(new u(sVar, honorPushCallback, i2, str));
    }

    public final <T> void a(Callable<T> callable, HonorPushCallback<T> honorPushCallback) {
        a aVar = new a(callable, honorPushCallback);
        b1 b1Var = b1.f15466d;
        if (b1Var.f15468b == null) {
            synchronized (b1Var.f15469c) {
                try {
                    if (b1Var.f15468b == null) {
                        b1Var.f15468b = b1Var.b();
                    }
                } finally {
                }
            }
        }
        b1Var.f15468b.execute(aVar);
    }
}
