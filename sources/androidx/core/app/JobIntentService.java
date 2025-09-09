package androidx.core.app;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
/* loaded from: classes.dex */
public abstract class JobIntentService extends Service {

    /* renamed from: h, reason: collision with root package name */
    static final Object f3391h = new Object();

    /* renamed from: i, reason: collision with root package name */
    static final HashMap f3392i = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    CompatJobEngine f3393a;

    /* renamed from: b, reason: collision with root package name */
    WorkEnqueuer f3394b;

    /* renamed from: c, reason: collision with root package name */
    CommandProcessor f3395c;

    /* renamed from: d, reason: collision with root package name */
    boolean f3396d = false;

    /* renamed from: e, reason: collision with root package name */
    boolean f3397e = false;

    /* renamed from: f, reason: collision with root package name */
    boolean f3398f = false;

    /* renamed from: g, reason: collision with root package name */
    final ArrayList f3399g;

    final class CommandProcessor extends AsyncTask<Void, Void, Void> {
        CommandProcessor() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void doInBackground(Void... voidArr) {
            while (true) {
                GenericWorkItem genericWorkItemA = JobIntentService.this.a();
                if (genericWorkItemA == null) {
                    return null;
                }
                JobIntentService.this.e(genericWorkItemA.getIntent());
                genericWorkItemA.complete();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onCancelled(Void r1) {
            JobIntentService.this.f();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Void r1) {
            JobIntentService.this.f();
        }
    }

    interface CompatJobEngine {
        IBinder compatGetBinder();

        GenericWorkItem dequeueWork();
    }

    static final class CompatWorkEnqueuer extends WorkEnqueuer {

        /* renamed from: d, reason: collision with root package name */
        boolean f3401d;

        /* renamed from: e, reason: collision with root package name */
        boolean f3402e;
        private final Context mContext;
        private final PowerManager.WakeLock mLaunchWakeLock;
        private final PowerManager.WakeLock mRunWakeLock;

        CompatWorkEnqueuer(Context context, ComponentName componentName) {
            super(componentName);
            this.mContext = context.getApplicationContext();
            PowerManager powerManager = (PowerManager) context.getSystemService(KsProperty.Power);
            PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(1, componentName.getClassName() + ":launch");
            this.mLaunchWakeLock = wakeLockNewWakeLock;
            wakeLockNewWakeLock.setReferenceCounted(false);
            PowerManager.WakeLock wakeLockNewWakeLock2 = powerManager.newWakeLock(1, componentName.getClassName() + ":run");
            this.mRunWakeLock = wakeLockNewWakeLock2;
            wakeLockNewWakeLock2.setReferenceCounted(false);
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        void a(Intent intent) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(this.f3411a);
            if (this.mContext.startService(intent2) != null) {
                synchronized (this) {
                    try {
                        if (!this.f3401d) {
                            this.f3401d = true;
                            if (!this.f3402e) {
                                this.mLaunchWakeLock.acquire(60000L);
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        public void serviceProcessingFinished() {
            synchronized (this) {
                try {
                    if (this.f3402e) {
                        if (this.f3401d) {
                            this.mLaunchWakeLock.acquire(60000L);
                        }
                        this.f3402e = false;
                        this.mRunWakeLock.release();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        public void serviceProcessingStarted() {
            synchronized (this) {
                try {
                    if (!this.f3402e) {
                        this.f3402e = true;
                        this.mRunWakeLock.acquire(600000L);
                        this.mLaunchWakeLock.release();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        public void serviceStartReceived() {
            synchronized (this) {
                this.f3401d = false;
            }
        }
    }

    final class CompatWorkItem implements GenericWorkItem {

        /* renamed from: a, reason: collision with root package name */
        final Intent f3403a;

        /* renamed from: b, reason: collision with root package name */
        final int f3404b;

        CompatWorkItem(Intent intent, int i2) {
            this.f3403a = intent;
            this.f3404b = i2;
        }

        @Override // androidx.core.app.JobIntentService.GenericWorkItem
        public void complete() {
            JobIntentService.this.stopSelf(this.f3404b);
        }

        @Override // androidx.core.app.JobIntentService.GenericWorkItem
        public Intent getIntent() {
            return this.f3403a;
        }
    }

    interface GenericWorkItem {
        void complete();

        Intent getIntent();
    }

    @RequiresApi(26)
    static final class JobServiceEngineImpl extends JobServiceEngine implements CompatJobEngine {

        /* renamed from: a, reason: collision with root package name */
        final JobIntentService f3406a;

        /* renamed from: b, reason: collision with root package name */
        final Object f3407b;

        /* renamed from: c, reason: collision with root package name */
        JobParameters f3408c;

        final class WrapperWorkItem implements GenericWorkItem {

            /* renamed from: a, reason: collision with root package name */
            final JobWorkItem f3409a;

            WrapperWorkItem(JobWorkItem jobWorkItem) {
                this.f3409a = jobWorkItem;
            }

            @Override // androidx.core.app.JobIntentService.GenericWorkItem
            public void complete() {
                synchronized (JobServiceEngineImpl.this.f3407b) {
                    try {
                        JobParameters jobParameters = JobServiceEngineImpl.this.f3408c;
                        if (jobParameters != null) {
                            jobParameters.completeWork(this.f3409a);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // androidx.core.app.JobIntentService.GenericWorkItem
            public Intent getIntent() {
                return this.f3409a.getIntent();
            }
        }

        JobServiceEngineImpl(JobIntentService jobIntentService) {
            super(jobIntentService);
            this.f3407b = new Object();
            this.f3406a = jobIntentService;
        }

        @Override // androidx.core.app.JobIntentService.CompatJobEngine
        public IBinder compatGetBinder() {
            return getBinder();
        }

        @Override // androidx.core.app.JobIntentService.CompatJobEngine
        public GenericWorkItem dequeueWork() {
            synchronized (this.f3407b) {
                try {
                    JobParameters jobParameters = this.f3408c;
                    if (jobParameters == null) {
                        return null;
                    }
                    JobWorkItem jobWorkItemDequeueWork = jobParameters.dequeueWork();
                    if (jobWorkItemDequeueWork == null) {
                        return null;
                    }
                    jobWorkItemDequeueWork.getIntent().setExtrasClassLoader(this.f3406a.getClassLoader());
                    return new WrapperWorkItem(jobWorkItemDequeueWork);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public boolean onStartJob(JobParameters jobParameters) {
            this.f3408c = jobParameters;
            this.f3406a.c(false);
            return true;
        }

        public boolean onStopJob(JobParameters jobParameters) {
            boolean zB = this.f3406a.b();
            synchronized (this.f3407b) {
                this.f3408c = null;
            }
            return zB;
        }
    }

    @RequiresApi(26)
    static final class JobWorkEnqueuer extends WorkEnqueuer {
        private final JobInfo mJobInfo;
        private final JobScheduler mJobScheduler;

        JobWorkEnqueuer(Context context, ComponentName componentName, int i2) {
            super(componentName);
            b(i2);
            this.mJobInfo = new JobInfo.Builder(i2, this.f3411a).setOverrideDeadline(0L).build();
            this.mJobScheduler = (JobScheduler) context.getApplicationContext().getSystemService("jobscheduler");
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        void a(Intent intent) {
            this.mJobScheduler.enqueue(this.mJobInfo, h.a(intent));
        }
    }

    static abstract class WorkEnqueuer {

        /* renamed from: a, reason: collision with root package name */
        final ComponentName f3411a;

        /* renamed from: b, reason: collision with root package name */
        boolean f3412b;

        /* renamed from: c, reason: collision with root package name */
        int f3413c;

        WorkEnqueuer(ComponentName componentName) {
            this.f3411a = componentName;
        }

        abstract void a(Intent intent);

        void b(int i2) {
            if (!this.f3412b) {
                this.f3412b = true;
                this.f3413c = i2;
            } else {
                if (this.f3413c == i2) {
                    return;
                }
                throw new IllegalArgumentException("Given job ID " + i2 + " is different than previous " + this.f3413c);
            }
        }

        public void serviceProcessingFinished() {
        }

        public void serviceProcessingStarted() {
        }

        public void serviceStartReceived() {
        }
    }

    public JobIntentService() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.f3399g = null;
        } else {
            this.f3399g = new ArrayList();
        }
    }

    static WorkEnqueuer d(Context context, ComponentName componentName, boolean z2, int i2) {
        WorkEnqueuer compatWorkEnqueuer;
        HashMap map = f3392i;
        WorkEnqueuer workEnqueuer = (WorkEnqueuer) map.get(componentName);
        if (workEnqueuer == null) {
            if (Build.VERSION.SDK_INT < 26) {
                compatWorkEnqueuer = new CompatWorkEnqueuer(context, componentName);
            } else {
                if (!z2) {
                    throw new IllegalArgumentException("Can't be here without a job id");
                }
                compatWorkEnqueuer = new JobWorkEnqueuer(context, componentName, i2);
            }
            workEnqueuer = compatWorkEnqueuer;
            map.put(componentName, workEnqueuer);
        }
        return workEnqueuer;
    }

    public static void enqueueWork(@NonNull Context context, @NonNull Class<?> cls, int i2, @NonNull Intent intent) {
        enqueueWork(context, new ComponentName(context, cls), i2, intent);
    }

    GenericWorkItem a() {
        CompatJobEngine compatJobEngine = this.f3393a;
        if (compatJobEngine != null) {
            return compatJobEngine.dequeueWork();
        }
        synchronized (this.f3399g) {
            try {
                if (this.f3399g.size() <= 0) {
                    return null;
                }
                return (GenericWorkItem) this.f3399g.remove(0);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    boolean b() {
        CommandProcessor commandProcessor = this.f3395c;
        if (commandProcessor != null) {
            commandProcessor.cancel(this.f3396d);
        }
        this.f3397e = true;
        return onStopCurrentWork();
    }

    void c(boolean z2) {
        if (this.f3395c == null) {
            this.f3395c = new CommandProcessor();
            WorkEnqueuer workEnqueuer = this.f3394b;
            if (workEnqueuer != null && z2) {
                workEnqueuer.serviceProcessingStarted();
            }
            this.f3395c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    protected abstract void e(Intent intent);

    void f() {
        ArrayList arrayList = this.f3399g;
        if (arrayList != null) {
            synchronized (arrayList) {
                try {
                    this.f3395c = null;
                    ArrayList arrayList2 = this.f3399g;
                    if (arrayList2 != null && arrayList2.size() > 0) {
                        c(false);
                    } else if (!this.f3398f) {
                        this.f3394b.serviceProcessingFinished();
                    }
                } finally {
                }
            }
        }
    }

    public boolean isStopped() {
        return this.f3397e;
    }

    @Override // android.app.Service
    public IBinder onBind(@NonNull Intent intent) {
        CompatJobEngine compatJobEngine = this.f3393a;
        if (compatJobEngine != null) {
            return compatJobEngine.compatGetBinder();
        }
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            this.f3393a = new JobServiceEngineImpl(this);
            this.f3394b = null;
        } else {
            this.f3393a = null;
            this.f3394b = d(this, new ComponentName(this, getClass()), false, 0);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        ArrayList arrayList = this.f3399g;
        if (arrayList != null) {
            synchronized (arrayList) {
                this.f3398f = true;
                this.f3394b.serviceProcessingFinished();
            }
        }
    }

    @Override // android.app.Service
    public int onStartCommand(@Nullable Intent intent, int i2, int i3) {
        if (this.f3399g == null) {
            return 2;
        }
        this.f3394b.serviceStartReceived();
        synchronized (this.f3399g) {
            ArrayList arrayList = this.f3399g;
            if (intent == null) {
                intent = new Intent();
            }
            arrayList.add(new CompatWorkItem(intent, i3));
            c(true);
        }
        return 3;
    }

    public boolean onStopCurrentWork() {
        return true;
    }

    public void setInterruptIfStopped(boolean z2) {
        this.f3396d = z2;
    }

    public static void enqueueWork(@NonNull Context context, @NonNull ComponentName componentName, int i2, @NonNull Intent intent) {
        if (intent == null) {
            throw new IllegalArgumentException("work must not be null");
        }
        synchronized (f3391h) {
            WorkEnqueuer workEnqueuerD = d(context, componentName, true, i2);
            workEnqueuerD.b(i2);
            workEnqueuerD.a(intent);
        }
    }
}
