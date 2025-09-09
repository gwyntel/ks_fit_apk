package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class WorkInitializer {
    private final Executor executor;
    private final SynchronizationGuard guard;
    private final WorkScheduler scheduler;
    private final EventStore store;

    WorkInitializer(Executor executor, EventStore eventStore, WorkScheduler workScheduler, SynchronizationGuard synchronizationGuard) {
        this.executor = executor;
        this.store = eventStore;
        this.scheduler = workScheduler;
        this.guard = synchronizationGuard;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$ensureContextsScheduled$0() {
        Iterator<TransportContext> it = this.store.loadActiveContexts().iterator();
        while (it.hasNext()) {
            this.scheduler.schedule(it.next(), 1);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$ensureContextsScheduled$1() {
        this.guard.runCriticalSection(new SynchronizationGuard.CriticalSection() { // from class: com.google.android.datatransport.runtime.scheduling.jobscheduling.o
            @Override // com.google.android.datatransport.runtime.synchronization.SynchronizationGuard.CriticalSection
            public final Object execute() {
                return this.f12557a.lambda$ensureContextsScheduled$0();
            }
        });
    }

    public void ensureContextsScheduled() {
        this.executor.execute(new Runnable() { // from class: com.google.android.datatransport.runtime.scheduling.jobscheduling.n
            @Override // java.lang.Runnable
            public final void run() {
                this.f12556a.lambda$ensureContextsScheduled$1();
            }
        });
    }
}
