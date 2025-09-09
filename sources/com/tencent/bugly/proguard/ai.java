package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes4.dex */
public final class ai {

    /* renamed from: b, reason: collision with root package name */
    private static ai f20733b;

    /* renamed from: a, reason: collision with root package name */
    public ah f20734a;

    /* renamed from: d, reason: collision with root package name */
    private final Context f20736d;

    /* renamed from: f, reason: collision with root package name */
    private long f20738f;

    /* renamed from: g, reason: collision with root package name */
    private long f20739g;

    /* renamed from: e, reason: collision with root package name */
    private Map<Integer, Long> f20737e = new HashMap();

    /* renamed from: h, reason: collision with root package name */
    private LinkedBlockingQueue<Runnable> f20740h = new LinkedBlockingQueue<>();

    /* renamed from: i, reason: collision with root package name */
    private LinkedBlockingQueue<Runnable> f20741i = new LinkedBlockingQueue<>();

    /* renamed from: j, reason: collision with root package name */
    private final Object f20742j = new Object();

    /* renamed from: k, reason: collision with root package name */
    private long f20743k = 0;

    /* renamed from: l, reason: collision with root package name */
    private int f20744l = 0;

    /* renamed from: c, reason: collision with root package name */
    private final w f20735c = w.a();

    private ai(Context context) {
        this.f20736d = context;
    }

    static /* synthetic */ int b(ai aiVar) {
        int i2 = aiVar.f20744l - 1;
        aiVar.f20744l = i2;
        return i2;
    }

    public static synchronized ai a(Context context) {
        try {
            if (f20733b == null) {
                f20733b = new ai(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f20733b;
    }

    public final boolean b(int i2) {
        if (p.f21112c) {
            al.c("Uploading frequency will not be checked if SDK is in debug mode.", new Object[0]);
            return true;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - a(i2);
        al.c("[UploadManager] Time interval is %d seconds since last uploading(ID: %d).", Long.valueOf(jCurrentTimeMillis / 1000), Integer.valueOf(i2));
        if (jCurrentTimeMillis >= 30000) {
            return true;
        }
        al.a("[UploadManager] Data only be uploaded once in %d seconds.", 30L);
        return false;
    }

    public static synchronized ai a() {
        return f20733b;
    }

    public final void a(int i2, bq bqVar, String str, String str2, ah ahVar, long j2, boolean z2) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            a(new aj(this.f20736d, i2, bqVar.f21012g, ae.a((Object) bqVar), str, str2, ahVar, z2), true, true, j2);
        } catch (Throwable th2) {
            th = th2;
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private void b() {
        ak akVarA = ak.a();
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
        final LinkedBlockingQueue linkedBlockingQueue2 = new LinkedBlockingQueue();
        synchronized (this.f20742j) {
            try {
                al.c("[UploadManager] Try to poll all upload task need and put them into temp queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                int size = this.f20740h.size();
                final int size2 = this.f20741i.size();
                if (size == 0 && size2 == 0) {
                    al.c("[UploadManager] There is no upload task in queue.", new Object[0]);
                    return;
                }
                if (akVarA == null || !akVarA.c()) {
                    size2 = 0;
                }
                a(this.f20740h, linkedBlockingQueue, size);
                a(this.f20741i, linkedBlockingQueue2, size2);
                a(size, linkedBlockingQueue);
                if (size2 > 0) {
                    al.c("[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(size2), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                }
                ak akVarA2 = ak.a();
                if (akVarA2 != null) {
                    akVarA2.a(new Runnable() { // from class: com.tencent.bugly.proguard.ai.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Runnable runnable;
                            for (int i2 = 0; i2 < size2 && (runnable = (Runnable) linkedBlockingQueue2.poll()) != null; i2++) {
                                runnable.run();
                            }
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void a(int i2, int i3, byte[] bArr, String str, String str2, ah ahVar, boolean z2) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            a(new aj(this.f20736d, i2, i3, bArr, str, str2, ahVar, 0, 0, false), z2, false, 0L);
        } catch (Throwable th2) {
            th = th2;
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public final void a(int i2, bq bqVar, String str, String str2, ah ahVar, boolean z2) {
        a(i2, bqVar.f21012g, ae.a((Object) bqVar), str, str2, ahVar, z2);
    }

    public final long a(boolean z2) {
        long jD;
        long jB = ap.b();
        int i2 = z2 ? 5 : 3;
        List<y> listA = this.f20735c.a(i2);
        if (listA != null && listA.size() > 0) {
            jD = 0;
            try {
                y yVar = listA.get(0);
                if (yVar.f21192e >= jB) {
                    jD = ap.d(yVar.f21194g);
                    if (i2 == 3) {
                        this.f20738f = jD;
                    } else {
                        this.f20739g = jD;
                    }
                    listA.remove(yVar);
                }
            } catch (Throwable th) {
                al.a(th);
            }
            if (listA.size() > 0) {
                this.f20735c.a(listA);
            }
        } else {
            jD = z2 ? this.f20739g : this.f20738f;
        }
        al.c("[UploadManager] Local network consume: %d KB", Long.valueOf(jD / 1024));
        return jD;
    }

    protected final synchronized void a(long j2, boolean z2) {
        int i2 = z2 ? 5 : 3;
        try {
            y yVar = new y();
            yVar.f21189b = i2;
            yVar.f21192e = ap.b();
            yVar.f21190c = "";
            yVar.f21191d = "";
            yVar.f21194g = ap.c(j2);
            this.f20735c.b(i2);
            this.f20735c.a(yVar);
            if (z2) {
                this.f20739g = j2;
            } else {
                this.f20738f = j2;
            }
            al.c("[UploadManager] Network total consume: %d KB", Long.valueOf(j2 / 1024));
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void a(int i2, long j2) {
        if (i2 >= 0) {
            this.f20737e.put(Integer.valueOf(i2), Long.valueOf(j2));
            y yVar = new y();
            yVar.f21189b = i2;
            yVar.f21192e = j2;
            yVar.f21190c = "";
            yVar.f21191d = "";
            yVar.f21194g = new byte[0];
            this.f20735c.b(i2);
            this.f20735c.a(yVar);
            al.c("[UploadManager] Uploading(ID:%d) time: %s", Integer.valueOf(i2), ap.a(j2));
            return;
        }
        al.e("[UploadManager] Unknown uploading ID: %d", Integer.valueOf(i2));
    }

    public final synchronized long a(int i2) {
        if (i2 >= 0) {
            Long l2 = this.f20737e.get(Integer.valueOf(i2));
            if (l2 != null) {
                return l2.longValue();
            }
        } else {
            al.e("[UploadManager] Unknown upload ID: %d", Integer.valueOf(i2));
        }
        return 0L;
    }

    private static void a(LinkedBlockingQueue<Runnable> linkedBlockingQueue, LinkedBlockingQueue<Runnable> linkedBlockingQueue2, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            Runnable runnablePeek = linkedBlockingQueue.peek();
            if (runnablePeek == null) {
                return;
            }
            try {
                linkedBlockingQueue2.put(runnablePeek);
                linkedBlockingQueue.poll();
            } catch (Throwable th) {
                al.e("[UploadManager] Failed to add upload task to temp urgent queue: %s", th.getMessage());
            }
        }
    }

    private void a(int i2, LinkedBlockingQueue<Runnable> linkedBlockingQueue) {
        ak akVarA = ak.a();
        if (i2 > 0) {
            al.c("[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(i2), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        }
        for (int i3 = 0; i3 < i2; i3++) {
            final Runnable runnablePoll = linkedBlockingQueue.poll();
            if (runnablePoll == null) {
                return;
            }
            synchronized (this.f20742j) {
                try {
                    if (this.f20744l >= 2 && akVarA != null) {
                        akVarA.a(runnablePoll);
                    } else {
                        al.a("[UploadManager] Create and start a new thread to execute a upload task: %s", "BUGLY_ASYNC_UPLOAD");
                        if (ap.a(new Runnable() { // from class: com.tencent.bugly.proguard.ai.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                runnablePoll.run();
                                synchronized (ai.this.f20742j) {
                                    ai.b(ai.this);
                                }
                            }
                        }, "BUGLY_ASYNC_UPLOAD") != null) {
                            synchronized (this.f20742j) {
                                this.f20744l++;
                            }
                        } else {
                            al.d("[UploadManager] Failed to start a thread to execute asynchronous upload task,will try again next time.", new Object[0]);
                            a(runnablePoll, true);
                        }
                    }
                } finally {
                }
            }
        }
    }

    private boolean a(Runnable runnable, boolean z2) {
        if (runnable == null) {
            al.a("[UploadManager] Upload task should not be null", new Object[0]);
            return false;
        }
        try {
            al.c("[UploadManager] Add upload task to queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            synchronized (this.f20742j) {
                try {
                    if (z2) {
                        this.f20740h.put(runnable);
                    } else {
                        this.f20741i.put(runnable);
                    }
                } finally {
                }
            }
            return true;
        } catch (Throwable th) {
            al.e("[UploadManager] Failed to add upload task to queue: %s", th.getMessage());
            return false;
        }
    }

    private void a(Runnable runnable, long j2) {
        if (runnable == null) {
            al.d("[UploadManager] Upload task should not be null", new Object[0]);
            return;
        }
        al.c("[UploadManager] Execute synchronized upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        Thread threadA = ap.a(runnable, "BUGLY_SYNC_UPLOAD");
        if (threadA == null) {
            al.e("[UploadManager] Failed to start a thread to execute synchronized upload task, add it to queue.", new Object[0]);
            a(runnable, true);
            return;
        }
        try {
            threadA.join(j2);
        } catch (Throwable th) {
            al.e("[UploadManager] Failed to join upload synchronized task with message: %s. Add it to queue.", th.getMessage());
            a(runnable, true);
            b();
        }
    }

    private void a(Runnable runnable, boolean z2, boolean z3, long j2) {
        al.c("[UploadManager] Add upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (z3) {
            a(runnable, j2);
        } else {
            a(runnable, z2);
            b();
        }
    }
}
