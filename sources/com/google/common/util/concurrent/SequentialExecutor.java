package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
final class SequentialExecutor implements Executor {
    private static final LazyLogger log = new LazyLogger(SequentialExecutor.class);
    private final Executor executor;

    @GuardedBy("queue")
    private final Deque<Runnable> queue = new ArrayDeque();

    @GuardedBy("queue")
    @LazyInit
    private WorkerRunningState workerRunningState = WorkerRunningState.IDLE;

    @GuardedBy("queue")
    private long workerRunCount = 0;

    @RetainedWith
    private final QueueWorker worker = new QueueWorker();

    private final class QueueWorker implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        Runnable f14902a;

        private QueueWorker() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x004e, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0054, code lost:
        
            r1 = r1 | java.lang.Thread.interrupted();
            r2 = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:
        
            r8.f14902a.run();
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0060, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0062, code lost:
        
            r3 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0063, code lost:
        
            com.google.common.util.concurrent.SequentialExecutor.log.a().log(java.util.logging.Level.SEVERE, "Exception while executing runnable " + r8.f14902a, (java.lang.Throwable) r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0084, code lost:
        
            r8.f14902a = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0086, code lost:
        
            throw r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void workOnQueue() {
            /*
                r8 = this;
                r0 = 0
                r1 = r0
            L2:
                com.google.common.util.concurrent.SequentialExecutor r2 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L5e
                java.util.Deque r2 = com.google.common.util.concurrent.SequentialExecutor.a(r2)     // Catch: java.lang.Throwable -> L5e
                monitor-enter(r2)     // Catch: java.lang.Throwable -> L5e
                if (r0 != 0) goto L2d
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L20
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r0 = com.google.common.util.concurrent.SequentialExecutor.b(r0)     // Catch: java.lang.Throwable -> L20
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch: java.lang.Throwable -> L20
                if (r0 != r3) goto L22
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L20
                if (r1 == 0) goto L1f
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L1f:
                return
            L20:
                r0 = move-exception
                goto L87
            L22:
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L20
                com.google.common.util.concurrent.SequentialExecutor.d(r0)     // Catch: java.lang.Throwable -> L20
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L20
                com.google.common.util.concurrent.SequentialExecutor.c(r0, r3)     // Catch: java.lang.Throwable -> L20
                r0 = 1
            L2d:
                com.google.common.util.concurrent.SequentialExecutor r3 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L20
                java.util.Deque r3 = com.google.common.util.concurrent.SequentialExecutor.a(r3)     // Catch: java.lang.Throwable -> L20
                java.lang.Object r3 = r3.poll()     // Catch: java.lang.Throwable -> L20
                java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch: java.lang.Throwable -> L20
                r8.f14902a = r3     // Catch: java.lang.Throwable -> L20
                if (r3 != 0) goto L4f
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L20
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE     // Catch: java.lang.Throwable -> L20
                com.google.common.util.concurrent.SequentialExecutor.c(r0, r3)     // Catch: java.lang.Throwable -> L20
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L20
                if (r1 == 0) goto L4e
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L4e:
                return
            L4f:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L20
                boolean r2 = java.lang.Thread.interrupted()     // Catch: java.lang.Throwable -> L5e
                r1 = r1 | r2
                r2 = 0
                java.lang.Runnable r3 = r8.f14902a     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
                r3.run()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            L5b:
                r8.f14902a = r2     // Catch: java.lang.Throwable -> L5e
                goto L2
            L5e:
                r0 = move-exception
                goto L89
            L60:
                r0 = move-exception
                goto L84
            L62:
                r3 = move-exception
                com.google.common.util.concurrent.LazyLogger r4 = com.google.common.util.concurrent.SequentialExecutor.e()     // Catch: java.lang.Throwable -> L60
                java.util.logging.Logger r4 = r4.a()     // Catch: java.lang.Throwable -> L60
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch: java.lang.Throwable -> L60
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L60
                r6.<init>()     // Catch: java.lang.Throwable -> L60
                java.lang.String r7 = "Exception while executing runnable "
                r6.append(r7)     // Catch: java.lang.Throwable -> L60
                java.lang.Runnable r7 = r8.f14902a     // Catch: java.lang.Throwable -> L60
                r6.append(r7)     // Catch: java.lang.Throwable -> L60
                java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L60
                r4.log(r5, r6, r3)     // Catch: java.lang.Throwable -> L60
                goto L5b
            L84:
                r8.f14902a = r2     // Catch: java.lang.Throwable -> L5e
                throw r0     // Catch: java.lang.Throwable -> L5e
            L87:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L20
                throw r0     // Catch: java.lang.Throwable -> L5e
            L89:
                if (r1 == 0) goto L92
                java.lang.Thread r1 = java.lang.Thread.currentThread()
                r1.interrupt()
            L92:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SequentialExecutor.QueueWorker.workOnQueue():void");
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                workOnQueue();
            } catch (Error e2) {
                synchronized (SequentialExecutor.this.queue) {
                    SequentialExecutor.this.workerRunningState = WorkerRunningState.IDLE;
                    throw e2;
                }
            }
        }

        public String toString() {
            Runnable runnable = this.f14902a;
            if (runnable != null) {
                return "SequentialExecutorWorker{running=" + runnable + com.alipay.sdk.m.u.i.f9804d;
            }
            return "SequentialExecutorWorker{state=" + SequentialExecutor.this.workerRunningState + com.alipay.sdk.m.u.i.f9804d;
        }
    }

    enum WorkerRunningState {
        IDLE,
        QUEUING,
        QUEUED,
        RUNNING
    }

    SequentialExecutor(Executor executor) {
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    static /* synthetic */ long d(SequentialExecutor sequentialExecutor) {
        long j2 = sequentialExecutor.workerRunCount;
        sequentialExecutor.workerRunCount = 1 + j2;
        return j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x005f  */
    @Override // java.util.concurrent.Executor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void execute(final java.lang.Runnable r8) {
        /*
            r7 = this;
            com.google.common.base.Preconditions.checkNotNull(r8)
            java.util.Deque<java.lang.Runnable> r0 = r7.queue
            monitor-enter(r0)
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r1 = r7.workerRunningState     // Catch: java.lang.Throwable -> L6b
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r2 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch: java.lang.Throwable -> L6b
            if (r1 == r2) goto L6d
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r2 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUED     // Catch: java.lang.Throwable -> L6b
            if (r1 != r2) goto L11
            goto L6d
        L11:
            long r3 = r7.workerRunCount     // Catch: java.lang.Throwable -> L6b
            com.google.common.util.concurrent.SequentialExecutor$1 r1 = new com.google.common.util.concurrent.SequentialExecutor$1     // Catch: java.lang.Throwable -> L6b
            r1.<init>(r7)     // Catch: java.lang.Throwable -> L6b
            java.util.Deque<java.lang.Runnable> r8 = r7.queue     // Catch: java.lang.Throwable -> L6b
            r8.add(r1)     // Catch: java.lang.Throwable -> L6b
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r8 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING     // Catch: java.lang.Throwable -> L6b
            r7.workerRunningState = r8     // Catch: java.lang.Throwable -> L6b
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6b
            java.util.concurrent.Executor r0 = r7.executor     // Catch: java.lang.Throwable -> L44
            com.google.common.util.concurrent.SequentialExecutor$QueueWorker r5 = r7.worker     // Catch: java.lang.Throwable -> L44
            r0.execute(r5)     // Catch: java.lang.Throwable -> L44
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r0 = r7.workerRunningState
            if (r0 == r8) goto L2e
            return
        L2e:
            java.util.Deque<java.lang.Runnable> r0 = r7.queue
            monitor-enter(r0)
            long r5 = r7.workerRunCount     // Catch: java.lang.Throwable -> L3e
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 != 0) goto L40
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r1 = r7.workerRunningState     // Catch: java.lang.Throwable -> L3e
            if (r1 != r8) goto L40
            r7.workerRunningState = r2     // Catch: java.lang.Throwable -> L3e
            goto L40
        L3e:
            r8 = move-exception
            goto L42
        L40:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            return
        L42:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            throw r8
        L44:
            r8 = move-exception
            java.util.Deque<java.lang.Runnable> r2 = r7.queue
            monitor-enter(r2)
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r0 = r7.workerRunningState     // Catch: java.lang.Throwable -> L53
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE     // Catch: java.lang.Throwable -> L53
            if (r0 == r3) goto L55
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING     // Catch: java.lang.Throwable -> L53
            if (r0 != r3) goto L5f
            goto L55
        L53:
            r8 = move-exception
            goto L69
        L55:
            java.util.Deque<java.lang.Runnable> r0 = r7.queue     // Catch: java.lang.Throwable -> L53
            boolean r0 = r0.removeLastOccurrence(r1)     // Catch: java.lang.Throwable -> L53
            if (r0 == 0) goto L5f
            r0 = 1
            goto L60
        L5f:
            r0 = 0
        L60:
            boolean r1 = r8 instanceof java.util.concurrent.RejectedExecutionException     // Catch: java.lang.Throwable -> L53
            if (r1 == 0) goto L68
            if (r0 != 0) goto L68
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L53
            return
        L68:
            throw r8     // Catch: java.lang.Throwable -> L53
        L69:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L53
            throw r8
        L6b:
            r8 = move-exception
            goto L74
        L6d:
            java.util.Deque<java.lang.Runnable> r1 = r7.queue     // Catch: java.lang.Throwable -> L6b
            r1.add(r8)     // Catch: java.lang.Throwable -> L6b
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6b
            return
        L74:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6b
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SequentialExecutor.execute(java.lang.Runnable):void");
    }

    public String toString() {
        return "SequentialExecutor@" + System.identityHashCode(this) + "{" + this.executor + com.alipay.sdk.m.u.i.f9804d;
    }
}
