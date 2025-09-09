package com.tekartik.sqflite;

import com.tekartik.sqflite.DatabaseWorkerPool;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
class DatabaseWorkerPoolImpl implements DatabaseWorkerPool {

    /* renamed from: a, reason: collision with root package name */
    final String f20468a;

    /* renamed from: b, reason: collision with root package name */
    final int f20469b;

    /* renamed from: c, reason: collision with root package name */
    final int f20470c;
    private final LinkedList<DatabaseTask> waitingList = new LinkedList<>();
    private final Set<DatabaseWorker> idleWorkers = new HashSet();
    private final Set<DatabaseWorker> busyWorkers = new HashSet();
    private final Map<Integer, DatabaseWorker> onlyEligibleWorkers = new HashMap();

    DatabaseWorkerPoolImpl(String str, int i2, int i3) {
        this.f20468a = str;
        this.f20469b = i2;
        this.f20470c = i3;
    }

    private synchronized DatabaseTask findTaskForWorker(DatabaseWorker databaseWorker) {
        DatabaseTask next;
        DatabaseWorker databaseWorker2;
        try {
            ListIterator<DatabaseTask> listIterator = this.waitingList.listIterator();
            do {
                if (!listIterator.hasNext()) {
                    return null;
                }
                next = listIterator.next();
                databaseWorker2 = next.getDatabaseId() != null ? this.onlyEligibleWorkers.get(next.getDatabaseId()) : null;
                if (databaseWorker2 == null) {
                    break;
                }
            } while (databaseWorker2 != databaseWorker);
            listIterator.remove();
            return next;
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onWorkerIdle, reason: merged with bridge method [inline-methods] */
    public synchronized void lambda$start$0(DatabaseWorker databaseWorker) {
        try {
            HashSet hashSet = new HashSet(this.idleWorkers);
            this.busyWorkers.remove(databaseWorker);
            this.idleWorkers.add(databaseWorker);
            if (!databaseWorker.b() && databaseWorker.c() != null) {
                this.onlyEligibleWorkers.remove(databaseWorker.c());
            }
            tryPostingTaskToWorker(databaseWorker);
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                tryPostingTaskToWorker((DatabaseWorker) it.next());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private synchronized void tryPostingTaskToWorker(DatabaseWorker databaseWorker) {
        try {
            DatabaseTask databaseTaskFindTaskForWorker = findTaskForWorker(databaseWorker);
            if (databaseTaskFindTaskForWorker != null) {
                this.busyWorkers.add(databaseWorker);
                this.idleWorkers.remove(databaseWorker);
                if (databaseTaskFindTaskForWorker.getDatabaseId() != null) {
                    this.onlyEligibleWorkers.put(databaseTaskFindTaskForWorker.getDatabaseId(), databaseWorker);
                }
                databaseWorker.d(databaseTaskFindTaskForWorker);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    protected DatabaseWorker b(String str, int i2) {
        return new DatabaseWorker(str, i2);
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public /* synthetic */ void post(Database database, Runnable runnable) {
        DatabaseWorkerPool.CC.a(this, database, runnable);
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public synchronized void quit() {
        try {
            Iterator<DatabaseWorker> it = this.idleWorkers.iterator();
            while (it.hasNext()) {
                it.next().e();
            }
            Iterator<DatabaseWorker> it2 = this.busyWorkers.iterator();
            while (it2.hasNext()) {
                it2.next().e();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public synchronized void start() {
        for (int i2 = 0; i2 < this.f20469b; i2++) {
            final DatabaseWorker databaseWorkerB = b(this.f20468a + i2, this.f20470c);
            databaseWorkerB.f(new Runnable() { // from class: com.tekartik.sqflite.k
                @Override // java.lang.Runnable
                public final void run() {
                    this.f20501a.lambda$start$0(databaseWorkerB);
                }
            });
            this.idleWorkers.add(databaseWorkerB);
        }
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public synchronized void post(DatabaseTask databaseTask) {
        this.waitingList.add(databaseTask);
        Iterator it = new HashSet(this.idleWorkers).iterator();
        while (it.hasNext()) {
            tryPostingTaskToWorker((DatabaseWorker) it.next());
        }
    }
}
