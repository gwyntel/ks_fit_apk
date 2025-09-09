package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.util.Base64;
import androidx.annotation.RequiresApi;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.logging.Logging;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.util.PriorityMapping;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.zip.Adler32;

@RequiresApi(api = 21)
/* loaded from: classes3.dex */
public class JobInfoScheduler implements WorkScheduler {
    private static final String LOG_TAG = "JobInfoScheduler";
    private final SchedulerConfig config;
    private final Context context;
    private final EventStore eventStore;

    public JobInfoScheduler(Context context, EventStore eventStore, SchedulerConfig schedulerConfig) {
        this.context = context;
        this.eventStore = eventStore;
        this.config = schedulerConfig;
    }

    private boolean isJobServiceOn(JobScheduler jobScheduler, int i2, int i3) {
        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            int i4 = jobInfo.getExtras().getInt("attemptNumber");
            if (jobInfo.getId() == i2) {
                return i4 >= i3;
            }
        }
        return false;
    }

    int a(TransportContext transportContext) {
        Adler32 adler32 = new Adler32();
        adler32.update(this.context.getPackageName().getBytes(Charset.forName("UTF-8")));
        adler32.update(transportContext.getBackendName().getBytes(Charset.forName("UTF-8")));
        adler32.update(ByteBuffer.allocate(4).putInt(PriorityMapping.toInt(transportContext.getPriority())).array());
        if (transportContext.getExtras() != null) {
            adler32.update(transportContext.getExtras());
        }
        return (int) adler32.getValue();
    }

    @Override // com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkScheduler
    public void schedule(TransportContext transportContext, int i2) {
        schedule(transportContext, i2, false);
    }

    @Override // com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkScheduler
    public void schedule(TransportContext transportContext, int i2, boolean z2) {
        ComponentName componentName = new ComponentName(this.context, (Class<?>) JobInfoSchedulerService.class);
        JobScheduler jobScheduler = (JobScheduler) this.context.getSystemService("jobscheduler");
        int iA = a(transportContext);
        if (!z2 && isJobServiceOn(jobScheduler, iA, i2)) {
            Logging.d(LOG_TAG, "Upload for context %s is already scheduled. Returning...", transportContext);
            return;
        }
        long nextCallTime = this.eventStore.getNextCallTime(transportContext);
        JobInfo.Builder builderConfigureJob = this.config.configureJob(new JobInfo.Builder(iA, componentName), transportContext.getPriority(), nextCallTime, i2);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt("attemptNumber", i2);
        persistableBundle.putString("backendName", transportContext.getBackendName());
        persistableBundle.putInt(RemoteMessageConst.Notification.PRIORITY, PriorityMapping.toInt(transportContext.getPriority()));
        if (transportContext.getExtras() != null) {
            persistableBundle.putString("extras", Base64.encodeToString(transportContext.getExtras(), 0));
        }
        builderConfigureJob.setExtras(persistableBundle);
        Logging.d(LOG_TAG, "Scheduling upload for context %s with jobId=%d in %dms(Backend next call timestamp %d). Attempt %d", transportContext, Integer.valueOf(iA), Long.valueOf(this.config.getScheduleDelay(transportContext.getPriority(), nextCallTime, i2)), Long.valueOf(nextCallTime), Integer.valueOf(i2));
        jobScheduler.schedule(builderConfigureJob.build());
    }
}
