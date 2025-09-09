package com.huawei.hms.push.task;

import android.content.Context;
import android.content.Intent;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.push.utils.PushBiUtil;
import com.huawei.hms.support.api.entity.push.PushNaming;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public class IntentCallable implements Callable<Void> {

    /* renamed from: a, reason: collision with root package name */
    private Context f16730a;

    /* renamed from: b, reason: collision with root package name */
    private Intent f16731b;

    /* renamed from: c, reason: collision with root package name */
    private String f16732c;

    public IntentCallable(Context context, Intent intent, String str) {
        this.f16730a = context;
        this.f16731b = intent;
        this.f16732c = str;
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        this.f16730a.sendBroadcast(this.f16731b);
        PushBiUtil.reportExit(this.f16730a, PushNaming.SET_NOTIFY_FLAG, this.f16732c, ErrorEnum.SUCCESS);
        return null;
    }
}
