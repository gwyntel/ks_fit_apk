package com.huawei.hms.adapter;

import android.content.Context;
import com.huawei.hms.common.internal.Objects;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes.dex */
public class OuterBinderAdapter extends BinderAdapter {

    /* renamed from: j, reason: collision with root package name */
    private static final Object f15784j = new Object();

    /* renamed from: k, reason: collision with root package name */
    private static BinderAdapter f15785k;

    /* renamed from: l, reason: collision with root package name */
    private static String f15786l;

    /* renamed from: m, reason: collision with root package name */
    private static String f15787m;

    private OuterBinderAdapter(Context context, String str, String str2) {
        super(context, str, str2);
    }

    public static BinderAdapter getInstance(Context context, String str, String str2) {
        BinderAdapter binderAdapter;
        HMSLog.i("OuterBinderAdapter", "OuterBinderAdapter getInstance.");
        synchronized (f15784j) {
            try {
                if (f15785k == null) {
                    f15786l = str;
                    f15787m = str2;
                    f15785k = new OuterBinderAdapter(context, str, str2);
                } else if (!Objects.equal(f15786l, str) || !Objects.equal(f15787m, str2)) {
                    HMSLog.i("OuterBinderAdapter", "OuterBinderAdapter getInstance refresh adapter");
                    f15786l = str;
                    f15787m = str2;
                    f15785k.unBind();
                    f15785k = new OuterBinderAdapter(context, str, str2);
                }
                binderAdapter = f15785k;
            } catch (Throwable th) {
                throw th;
            }
        }
        return binderAdapter;
    }

    @Override // com.huawei.hms.adapter.BinderAdapter
    protected int getConnTimeOut() {
        return 1001;
    }

    @Override // com.huawei.hms.adapter.BinderAdapter
    protected int getMsgDelayDisconnect() {
        return 1002;
    }
}
