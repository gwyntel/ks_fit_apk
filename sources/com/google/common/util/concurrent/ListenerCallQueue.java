package com.google.common.util.concurrent;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import com.google.firebase.messaging.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
@J2ktIncompatible
/* loaded from: classes3.dex */
final class ListenerCallQueue<L> {
    private static final LazyLogger logger = new LazyLogger(ListenerCallQueue.class);
    private final List<PerListenerQueue<L>> listeners = Collections.synchronizedList(new ArrayList());

    interface Event<L> {
        void call(L l2);
    }

    private static final class PerListenerQueue<L> implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final Object f14877a;

        /* renamed from: b, reason: collision with root package name */
        final Executor f14878b;

        /* renamed from: c, reason: collision with root package name */
        final Queue f14879c = Queues.newArrayDeque();

        /* renamed from: d, reason: collision with root package name */
        final Queue f14880d = Queues.newArrayDeque();

        /* renamed from: e, reason: collision with root package name */
        boolean f14881e;

        PerListenerQueue(Object obj, Executor executor) {
            this.f14877a = Preconditions.checkNotNull(obj);
            this.f14878b = (Executor) Preconditions.checkNotNull(executor);
        }

        synchronized void a(Event event, Object obj) {
            this.f14879c.add(event);
            this.f14880d.add(obj);
        }

        void b() throws Exception {
            boolean z2;
            synchronized (this) {
                try {
                    if (this.f14881e) {
                        z2 = false;
                    } else {
                        z2 = true;
                        this.f14881e = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z2) {
                try {
                    this.f14878b.execute(this);
                } catch (Exception e2) {
                    synchronized (this) {
                        this.f14881e = false;
                        ListenerCallQueue.logger.a().log(Level.SEVERE, "Exception while running callbacks for " + this.f14877a + " on " + this.f14878b, (Throwable) e2);
                        throw e2;
                    }
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0025, code lost:
        
            r2.call(r9.f14877a);
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x002d, code lost:
        
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x002e, code lost:
        
            com.google.common.util.concurrent.ListenerCallQueue.logger.a().log(java.util.logging.Level.SEVERE, "Exception while executing callback: " + r9.f14877a + " " + r3, (java.lang.Throwable) r2);
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0062  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() throws java.lang.Throwable {
            /*
                r9 = this;
            L0:
                r0 = 0
                r1 = 1
                monitor-enter(r9)     // Catch: java.lang.Throwable -> L2b
                boolean r2 = r9.f14881e     // Catch: java.lang.Throwable -> L1f
                com.google.common.base.Preconditions.checkState(r2)     // Catch: java.lang.Throwable -> L1f
                java.util.Queue r2 = r9.f14879c     // Catch: java.lang.Throwable -> L1f
                java.lang.Object r2 = r2.poll()     // Catch: java.lang.Throwable -> L1f
                com.google.common.util.concurrent.ListenerCallQueue$Event r2 = (com.google.common.util.concurrent.ListenerCallQueue.Event) r2     // Catch: java.lang.Throwable -> L1f
                java.util.Queue r3 = r9.f14880d     // Catch: java.lang.Throwable -> L1f
                java.lang.Object r3 = r3.poll()     // Catch: java.lang.Throwable -> L1f
                if (r2 != 0) goto L24
                r9.f14881e = r0     // Catch: java.lang.Throwable -> L1f
                monitor-exit(r9)     // Catch: java.lang.Throwable -> L1c
                return
            L1c:
                r1 = move-exception
                r2 = r0
                goto L57
            L1f:
                r2 = move-exception
                r8 = r2
                r2 = r1
                r1 = r8
                goto L57
            L24:
                monitor-exit(r9)     // Catch: java.lang.Throwable -> L1f
                java.lang.Object r4 = r9.f14877a     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
                r2.call(r4)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
                goto L0
            L2b:
                r2 = move-exception
                goto L60
            L2d:
                r2 = move-exception
                com.google.common.util.concurrent.LazyLogger r4 = com.google.common.util.concurrent.ListenerCallQueue.a()     // Catch: java.lang.Throwable -> L2b
                java.util.logging.Logger r4 = r4.a()     // Catch: java.lang.Throwable -> L2b
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch: java.lang.Throwable -> L2b
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2b
                r6.<init>()     // Catch: java.lang.Throwable -> L2b
                java.lang.String r7 = "Exception while executing callback: "
                r6.append(r7)     // Catch: java.lang.Throwable -> L2b
                java.lang.Object r7 = r9.f14877a     // Catch: java.lang.Throwable -> L2b
                r6.append(r7)     // Catch: java.lang.Throwable -> L2b
                java.lang.String r7 = " "
                r6.append(r7)     // Catch: java.lang.Throwable -> L2b
                r6.append(r3)     // Catch: java.lang.Throwable -> L2b
                java.lang.String r3 = r6.toString()     // Catch: java.lang.Throwable -> L2b
                r4.log(r5, r3, r2)     // Catch: java.lang.Throwable -> L2b
                goto L0
            L57:
                monitor-exit(r9)     // Catch: java.lang.Throwable -> L5e
                throw r1     // Catch: java.lang.Throwable -> L59
            L59:
                r1 = move-exception
                r8 = r2
                r2 = r1
                r1 = r8
                goto L60
            L5e:
                r1 = move-exception
                goto L57
            L60:
                if (r1 == 0) goto L6a
                monitor-enter(r9)
                r9.f14881e = r0     // Catch: java.lang.Throwable -> L67
                monitor-exit(r9)     // Catch: java.lang.Throwable -> L67
                goto L6a
            L67:
                r0 = move-exception
                monitor-exit(r9)     // Catch: java.lang.Throwable -> L67
                throw r0
            L6a:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ListenerCallQueue.PerListenerQueue.run():void");
        }
    }

    ListenerCallQueue() {
    }

    private void enqueueHelper(Event<L> event, Object obj) {
        Preconditions.checkNotNull(event, NotificationCompat.CATEGORY_EVENT);
        Preconditions.checkNotNull(obj, Constants.ScionAnalytics.PARAM_LABEL);
        synchronized (this.listeners) {
            try {
                Iterator<PerListenerQueue<L>> it = this.listeners.iterator();
                while (it.hasNext()) {
                    it.next().a(event, obj);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void addListener(L l2, Executor executor) {
        Preconditions.checkNotNull(l2, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        Preconditions.checkNotNull(executor, "executor");
        this.listeners.add(new PerListenerQueue<>(l2, executor));
    }

    public void dispatch() throws Exception {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).b();
        }
    }

    public void enqueue(Event<L> event) {
        enqueueHelper(event, event);
    }

    public void enqueue(Event<L> event, String str) {
        enqueueHelper(event, str);
    }
}
