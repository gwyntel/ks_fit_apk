package com.xiaomi.push;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.push.fu;
import com.xiaomi.push.service.XMJobService;

@TargetApi(21)
/* loaded from: classes4.dex */
public class fw implements fu.a {

    /* renamed from: a, reason: collision with root package name */
    JobScheduler f23770a;

    /* renamed from: a, reason: collision with other field name */
    Context f441a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f442a = false;

    fw(Context context) {
        this.f441a = context;
        this.f23770a = (JobScheduler) context.getSystemService("jobscheduler");
    }

    void a(long j2) {
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this.f441a.getPackageName(), XMJobService.class.getName()));
        builder.setMinimumLatency(j2);
        builder.setOverrideDeadline(j2);
        builder.setRequiredNetworkType(1);
        builder.setPersisted(false);
        com.xiaomi.channel.commonutils.logger.b.c("schedule Job = " + builder.build().getId() + " in " + j2);
        this.f23770a.schedule(builder.build());
    }

    @Override // com.xiaomi.push.fu.a
    public void a(boolean z2) {
        if (z2 || this.f442a) {
            long jB = hh.b();
            if (z2) {
                a();
                jB -= SystemClock.elapsedRealtime() % jB;
            }
            this.f442a = true;
            a(jB);
        }
    }

    @Override // com.xiaomi.push.fu.a
    public void a() {
        this.f442a = false;
        this.f23770a.cancel(1);
    }

    @Override // com.xiaomi.push.fu.a
    /* renamed from: a */
    public boolean mo419a() {
        return this.f442a;
    }
}
