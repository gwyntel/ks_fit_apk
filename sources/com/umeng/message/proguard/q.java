package com.umeng.message.proguard;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import com.kingsmith.miot.KsProperty;
import com.umeng.message.common.UPLog;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes4.dex */
public abstract class q extends Service {
    private static final String TAG = "BaseService";
    private static final int jobIndex = 21000;
    final ArrayList<d> mCompatQueue;
    h mCompatWorkEnqueuer;
    a mCurProcessor;
    b mJobImpl;
    static final Object sLock = new Object();
    static final HashMap<ComponentName, h> sClassWorkEnqueuer = new HashMap<>();
    private static final HashMap<Class<?>, Integer> jobMap = new HashMap<>();
    private static final Object mRealTimeModeLock = new Object();
    boolean mInterruptIfStopped = false;
    boolean mStopped = false;
    boolean mDestroyed = false;

    static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private q f22892a;

        a(q qVar) {
            this.f22892a = qVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            while (true) {
                try {
                    e eVarDequeueWork = this.f22892a.dequeueWork();
                    if (eVarDequeueWork != null) {
                        this.f22892a.onHandleWork(eVarDequeueWork.a());
                        eVarDequeueWork.b();
                    }
                } catch (Throwable th) {
                    UPLog.e(q.TAG, th);
                }
                try {
                    break;
                } catch (Throwable th2) {
                    UPLog.e(q.TAG, th2);
                }
            }
            this.f22892a.processorFinished();
            this.f22892a = null;
        }
    }

    interface b {
        IBinder a();

        e b();
    }

    final class d implements e {

        /* renamed from: a, reason: collision with root package name */
        final Intent f22898a;

        /* renamed from: b, reason: collision with root package name */
        final int f22899b;

        d(Intent intent, int i2) {
            this.f22898a = intent;
            this.f22899b = i2;
        }

        @Override // com.umeng.message.proguard.q.e
        public final Intent a() {
            return this.f22898a;
        }

        @Override // com.umeng.message.proguard.q.e
        public final void b() {
            try {
                q.this.stopSelf(this.f22899b);
            } catch (Throwable th) {
                UPLog.e(q.TAG, th);
            }
        }
    }

    interface e {
        Intent a();

        void b();
    }

    @TargetApi(26)
    static final class f extends JobServiceEngine implements b {

        /* renamed from: a, reason: collision with root package name */
        final q f22901a;

        /* renamed from: b, reason: collision with root package name */
        final Object f22902b;

        /* renamed from: c, reason: collision with root package name */
        JobParameters f22903c;

        final class a implements e {

            /* renamed from: a, reason: collision with root package name */
            final JobWorkItem f22904a;

            a(JobWorkItem jobWorkItem) {
                this.f22904a = jobWorkItem;
            }

            @Override // com.umeng.message.proguard.q.e
            public final Intent a() {
                return this.f22904a.getIntent();
            }

            @Override // com.umeng.message.proguard.q.e
            public final void b() {
                try {
                    synchronized (f.this.f22902b) {
                        try {
                            JobParameters jobParameters = f.this.f22903c;
                            if (jobParameters != null) {
                                jobParameters.completeWork(this.f22904a);
                            }
                        } catch (Throwable th) {
                            UPLog.e(q.TAG, th);
                        } finally {
                        }
                    }
                } catch (Throwable th2) {
                    UPLog.e(q.TAG, th2);
                }
            }
        }

        f(q qVar) {
            super(qVar);
            this.f22902b = new Object();
            this.f22901a = qVar;
        }

        @Override // com.umeng.message.proguard.q.b
        public final IBinder a() {
            return getBinder();
        }

        @Override // com.umeng.message.proguard.q.b
        public final e b() {
            try {
                synchronized (this.f22902b) {
                    try {
                        JobParameters jobParameters = this.f22903c;
                        if (jobParameters == null) {
                            return null;
                        }
                        JobWorkItem jobWorkItemDequeueWork = jobParameters.dequeueWork();
                        if (jobWorkItemDequeueWork == null) {
                            return null;
                        }
                        jobWorkItemDequeueWork.getIntent().setExtrasClassLoader(this.f22901a.getClassLoader());
                        return new a(jobWorkItemDequeueWork);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                UPLog.e(q.TAG, th2);
                return null;
            }
        }

        public final boolean onStartJob(JobParameters jobParameters) {
            this.f22903c = jobParameters;
            this.f22901a.ensureProcessorRunningLocked(false);
            return true;
        }

        public final boolean onStopJob(JobParameters jobParameters) {
            boolean zDoStopCurrentWork = this.f22901a.doStopCurrentWork();
            synchronized (this.f22902b) {
                this.f22903c = null;
            }
            return zDoStopCurrentWork;
        }
    }

    @TargetApi(21)
    static final class g extends h {

        /* renamed from: a, reason: collision with root package name */
        private final JobInfo f22906a;

        /* renamed from: b, reason: collision with root package name */
        private JobScheduler f22907b;

        g(Context context, ComponentName componentName, int i2) {
            super(componentName);
            a(i2);
            this.f22906a = new JobInfo.Builder(i2, this.f22908c).setOverrideDeadline(0L).build();
            try {
                this.f22907b = (JobScheduler) context.getApplicationContext().getSystemService("jobscheduler");
            } catch (Throwable th) {
                UPLog.e(q.TAG, th);
            }
        }

        @Override // com.umeng.message.proguard.q.h
        final void a(Intent intent) {
            try {
                if (Build.VERSION.SDK_INT >= 26) {
                    JobScheduler jobScheduler = this.f22907b;
                    JobInfo jobInfo = this.f22906a;
                    b0.a();
                    jobScheduler.enqueue(jobInfo, androidx.core.app.h.a(intent));
                }
            } catch (Throwable th) {
                UPLog.e(q.TAG, th);
            }
        }
    }

    static abstract class h {

        /* renamed from: c, reason: collision with root package name */
        final ComponentName f22908c;

        /* renamed from: d, reason: collision with root package name */
        boolean f22909d;

        /* renamed from: e, reason: collision with root package name */
        int f22910e;

        h(ComponentName componentName) {
            this.f22908c = componentName;
        }

        public void a() {
        }

        abstract void a(Intent intent);

        public void b() {
        }

        public void c() {
        }

        final void a(int i2) {
            if (!this.f22909d) {
                this.f22909d = true;
                this.f22910e = i2;
            } else {
                if (this.f22910e == i2) {
                    return;
                }
                throw new IllegalArgumentException("Given job ID " + i2 + " is different than previous " + this.f22910e);
            }
        }
    }

    public q() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mCompatQueue = null;
        } else {
            this.mCompatQueue = new ArrayList<>();
        }
    }

    private static void enqueueWork(Context context, Class<?> cls, int i2, Intent intent) {
        if (context == null || cls == null || intent == null) {
            return;
        }
        try {
            enqueueWork(context, new ComponentName(context, cls), i2, intent);
        } catch (Throwable th) {
            UPLog.e(TAG, "jobId:", Integer.valueOf(i2), "failed:", th.getMessage());
        }
    }

    static h getWorkEnqueuer(Context context, ComponentName componentName, boolean z2, int i2) {
        h cVar;
        HashMap<ComponentName, h> map = sClassWorkEnqueuer;
        h hVar = map.get(componentName);
        if (hVar == null) {
            if (Build.VERSION.SDK_INT < 26) {
                cVar = new c(context, componentName);
            } else {
                if (!z2) {
                    throw new IllegalArgumentException("Can't be here without a job id");
                }
                cVar = new g(context, componentName, i2);
            }
            hVar = cVar;
            map.put(componentName, hVar);
        }
        return hVar;
    }

    private void init() {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                this.mJobImpl = new f(this);
                this.mCompatWorkEnqueuer = null;
            } else {
                this.mJobImpl = null;
                this.mCompatWorkEnqueuer = getWorkEnqueuer(this, new ComponentName(this, getClass()), false, 0);
            }
        } catch (Throwable th) {
            UPLog.e(TAG, th);
        }
    }

    e dequeueWork() {
        d dVarRemove;
        b bVar = this.mJobImpl;
        if (bVar != null) {
            return bVar.b();
        }
        ArrayList<d> arrayList = this.mCompatQueue;
        if (arrayList == null) {
            return null;
        }
        synchronized (arrayList) {
            try {
                dVarRemove = this.mCompatQueue.size() > 0 ? this.mCompatQueue.remove(0) : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dVarRemove;
    }

    boolean doStopCurrentWork() {
        this.mStopped = true;
        return onStopCurrentWork();
    }

    void ensureProcessorRunningLocked(boolean z2) {
        try {
            if (this.mCurProcessor == null) {
                this.mCurProcessor = new a(this);
                h hVar = this.mCompatWorkEnqueuer;
                if (hVar != null && z2) {
                    hVar.b();
                }
                com.umeng.message.proguard.b.a(this.mCurProcessor);
            }
        } catch (Throwable th) {
            UPLog.e(TAG, th);
        }
    }

    public boolean isStopped() {
        return this.mStopped;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (intent != null) {
            try {
                b bVar = this.mJobImpl;
                if (bVar != null) {
                    return bVar.a();
                }
                return null;
            } catch (Throwable th) {
                UPLog.e(TAG, th);
            }
        }
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override // android.app.Service
    public void onDestroy() {
        try {
            super.onDestroy();
            ArrayList<d> arrayList = this.mCompatQueue;
            if (arrayList != null) {
                synchronized (arrayList) {
                    this.mDestroyed = true;
                    this.mCompatWorkEnqueuer.c();
                }
            }
        } catch (Throwable th) {
            UPLog.e(TAG, th);
        }
    }

    protected void onHandleWork(Intent intent) {
        UPLog.i(TAG, "onHandleWork");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        if (intent != null) {
            try {
                if (this.mCompatQueue == null) {
                    return 2;
                }
                if (this.mCompatWorkEnqueuer == null) {
                    init();
                }
                this.mCompatWorkEnqueuer.a();
                synchronized (this.mCompatQueue) {
                    this.mCompatQueue.add(new d(intent, i3));
                    ensureProcessorRunningLocked(true);
                }
                return 3;
            } catch (Throwable th) {
                UPLog.e(TAG, th);
            }
        }
        return 2;
    }

    public boolean onStopCurrentWork() {
        return true;
    }

    void processorFinished() {
        try {
            ArrayList<d> arrayList = this.mCompatQueue;
            if (arrayList != null) {
                synchronized (arrayList) {
                    try {
                        this.mCurProcessor = null;
                        if (this.mCompatQueue.size() > 0) {
                            ensureProcessorRunningLocked(false);
                        } else if (!this.mDestroyed) {
                            this.mCompatWorkEnqueuer.c();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        } catch (Throwable th2) {
            UPLog.e(TAG, th2);
        }
    }

    public void setInterruptIfStopped(boolean z2) {
        this.mInterruptIfStopped = z2;
    }

    private static void enqueueWork(Context context, ComponentName componentName, int i2, Intent intent) {
        if (intent == null) {
            throw new IllegalArgumentException("work must not be null");
        }
        if (context == null || componentName == null) {
            return;
        }
        synchronized (sLock) {
            h workEnqueuer = getWorkEnqueuer(context, componentName, true, i2);
            workEnqueuer.a(i2);
            workEnqueuer.a(intent);
        }
    }

    public static <T extends q> void enqueueWork(Context context, Class<T> cls, Intent intent) {
        int iIntValue;
        synchronized (mRealTimeModeLock) {
            try {
                if (context == null || cls == null || intent == null) {
                    return;
                }
                UPLog.i(TAG, "enqueue cls:", cls.getSimpleName());
                HashMap<Class<?>, Integer> map = jobMap;
                if (!map.containsKey(cls)) {
                    iIntValue = map.size() + jobIndex;
                    map.put(cls, Integer.valueOf(iIntValue));
                } else {
                    Integer num = map.get(cls);
                    if (num == null) {
                        return;
                    } else {
                        iIntValue = num.intValue();
                    }
                }
                UPLog.i(TAG, "jobId:", Integer.valueOf(iIntValue));
                enqueueWork(context, (Class<?>) cls, iIntValue, intent);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static final class c extends h {

        /* renamed from: a, reason: collision with root package name */
        boolean f22893a;

        /* renamed from: b, reason: collision with root package name */
        boolean f22894b;

        /* renamed from: f, reason: collision with root package name */
        private final Context f22895f;

        /* renamed from: g, reason: collision with root package name */
        private final PowerManager.WakeLock f22896g;

        /* renamed from: h, reason: collision with root package name */
        private final PowerManager.WakeLock f22897h;

        c(Context context, ComponentName componentName) {
            super(componentName);
            this.f22895f = context.getApplicationContext();
            PowerManager powerManager = (PowerManager) context.getSystemService(KsProperty.Power);
            PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(1, componentName.getClassName() + ":launch");
            this.f22896g = wakeLockNewWakeLock;
            wakeLockNewWakeLock.setReferenceCounted(false);
            PowerManager.WakeLock wakeLockNewWakeLock2 = powerManager.newWakeLock(1, componentName.getClassName() + ":run");
            this.f22897h = wakeLockNewWakeLock2;
            wakeLockNewWakeLock2.setReferenceCounted(false);
        }

        @Override // com.umeng.message.proguard.q.h
        final void a(Intent intent) {
            try {
                Intent intent2 = new Intent(intent);
                intent2.setComponent(this.f22908c);
                if (this.f22895f.startService(intent2) != null) {
                    synchronized (this) {
                        try {
                            if (!this.f22893a) {
                                this.f22893a = true;
                                if (!this.f22894b) {
                                    this.f22896g.acquire(60000L);
                                }
                            }
                        } finally {
                        }
                    }
                }
            } catch (Throwable th) {
                UPLog.e(q.TAG, th);
            }
        }

        @Override // com.umeng.message.proguard.q.h
        public final void b() {
            try {
                synchronized (this) {
                    try {
                        if (!this.f22894b) {
                            this.f22894b = true;
                            this.f22897h.acquire(600000L);
                            this.f22896g.release();
                        }
                    } finally {
                    }
                }
            } catch (Throwable th) {
                UPLog.e(q.TAG, th);
            }
        }

        @Override // com.umeng.message.proguard.q.h
        public final void c() {
            try {
                synchronized (this) {
                    try {
                        if (this.f22894b) {
                            if (this.f22893a) {
                                this.f22896g.acquire(60000L);
                            }
                            this.f22894b = false;
                            this.f22897h.release();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                UPLog.e(q.TAG, th2);
            }
        }

        @Override // com.umeng.message.proguard.q.h
        public final void a() {
            synchronized (this) {
                this.f22893a = false;
            }
        }
    }
}
