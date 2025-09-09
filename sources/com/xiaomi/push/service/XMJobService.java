package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.xiaomi.push.fu;

/* loaded from: classes4.dex */
public class XMJobService extends Service {

    /* renamed from: a, reason: collision with root package name */
    static Service f24416a;

    /* renamed from: a, reason: collision with other field name */
    private IBinder f949a = null;

    @TargetApi(21)
    static class a extends JobService {

        /* renamed from: a, reason: collision with root package name */
        Binder f24417a;

        /* renamed from: a, reason: collision with other field name */
        private Handler f950a;

        /* renamed from: com.xiaomi.push.service.XMJobService$a$a, reason: collision with other inner class name */
        private static class HandlerC0193a extends Handler {

            /* renamed from: a, reason: collision with root package name */
            JobService f24418a;

            HandlerC0193a(JobService jobService) {
                super(jobService.getMainLooper());
                this.f24418a = jobService;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                JobParameters jobParameters = (JobParameters) message.obj;
                com.xiaomi.channel.commonutils.logger.b.m91a("Job finished " + jobParameters.getJobId());
                this.f24418a.jobFinished(jobParameters, false);
                if (jobParameters.getJobId() == 1) {
                    fu.a(false);
                }
            }
        }

        a(Service service) {
            this.f24417a = null;
            this.f24417a = (Binder) com.xiaomi.push.bk.a((Object) this, "onBind", new Intent());
            com.xiaomi.push.bk.a((Object) this, "attachBaseContext", service);
        }

        @Override // android.app.job.JobService
        public boolean onStartJob(JobParameters jobParameters) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Job started " + jobParameters.getJobId());
            Intent intent = new Intent(this, (Class<?>) XMPushService.class);
            intent.setAction("com.xiaomi.push.timer");
            intent.setPackage(getPackageName());
            startService(intent);
            if (this.f950a == null) {
                this.f950a = new HandlerC0193a(this);
            }
            Handler handler = this.f950a;
            handler.sendMessage(Message.obtain(handler, 1, jobParameters));
            return true;
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Job stop " + jobParameters.getJobId());
            return false;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        IBinder iBinder = this.f949a;
        return iBinder != null ? iBinder : new Binder();
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.f949a = new a(this).f24417a;
        f24416a = this;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        f24416a = null;
    }
}
