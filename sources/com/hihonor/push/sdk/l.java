package com.hihonor.push.sdk;

import android.content.Context;
import com.hihonor.push.sdk.internal.HonorPushErrorEnum;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class l {

    /* renamed from: e, reason: collision with root package name */
    public static final l f15511e = new l();

    /* renamed from: a, reason: collision with root package name */
    public WeakReference<Context> f15512a;

    /* renamed from: b, reason: collision with root package name */
    public volatile boolean f15513b = false;

    /* renamed from: c, reason: collision with root package name */
    public volatile boolean f15514c = false;

    /* renamed from: d, reason: collision with root package name */
    public s f15515d;

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Runnable f15516a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ HonorPushCallback f15517b;

        public a(Runnable runnable, HonorPushCallback honorPushCallback) {
            this.f15516a = runnable;
            this.f15517b = honorPushCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (l.this.f15513b) {
                this.f15516a.run();
                return;
            }
            HonorPushCallback honorPushCallback = this.f15517b;
            if (honorPushCallback != null) {
                HonorPushErrorEnum honorPushErrorEnum = HonorPushErrorEnum.ERROR_NOT_INITIALIZED;
                honorPushCallback.onFailure(honorPushErrorEnum.getErrorCode(), honorPushErrorEnum.getMessage());
            }
        }
    }

    public boolean a(Context context) {
        return HonorPushErrorEnum.SUCCESS.statusCode == b.b(context);
    }

    public Context a() {
        return this.f15512a.get();
    }

    public final void a(Runnable runnable, HonorPushCallback<?> honorPushCallback) {
        b1.a(new a(runnable, honorPushCallback));
    }
}
