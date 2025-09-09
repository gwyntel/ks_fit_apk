package com.taobao.accs.net;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import com.taobao.accs.internal.AccsJobService;

/* loaded from: classes4.dex */
class f extends g {

    /* renamed from: c, reason: collision with root package name */
    private JobScheduler f20216c;

    f(Context context) {
        super(context);
    }

    @Override // com.taobao.accs.net.g
    @SuppressLint({"NewApi"})
    protected void a(int i2) {
        if (this.f20216c == null) {
            this.f20216c = (JobScheduler) this.f20219a.getSystemService("jobscheduler");
        }
        this.f20216c.cancel(2050);
        long j2 = i2 * 1000;
        this.f20216c.schedule(new JobInfo.Builder(2050, new ComponentName(this.f20219a.getPackageName(), AccsJobService.class.getName())).setMinimumLatency(j2).setOverrideDeadline(j2).setRequiredNetworkType(1).build());
    }
}
