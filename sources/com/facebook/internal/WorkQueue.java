package com.facebook.internal;

import com.facebook.FacebookSdk;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class WorkQueue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DEFAULT_MAX_CONCURRENT = 8;
    private final Executor executor;
    private final int maxConcurrent;
    private WorkNode pendingJobs;
    private int runningCount;
    private WorkNode runningJobs;
    private final Object workLock;

    public interface WorkItem {
        boolean cancel();

        boolean isRunning();

        void moveToFront();
    }

    private class WorkNode implements WorkItem {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Runnable callback;
        private boolean isRunning;
        private WorkNode next;
        private WorkNode prev;

        WorkNode(Runnable runnable) {
            this.callback = runnable;
        }

        WorkNode addToList(WorkNode workNode, boolean z2) {
            if (workNode == null) {
                this.prev = this;
                this.next = this;
                workNode = this;
            } else {
                this.next = workNode;
                WorkNode workNode2 = workNode.prev;
                this.prev = workNode2;
                workNode2.next = this;
                workNode.prev = this;
            }
            return z2 ? this : workNode;
        }

        @Override // com.facebook.internal.WorkQueue.WorkItem
        public boolean cancel() {
            synchronized (WorkQueue.this.workLock) {
                try {
                    if (isRunning()) {
                        return false;
                    }
                    WorkQueue workQueue = WorkQueue.this;
                    workQueue.pendingJobs = removeFromList(workQueue.pendingJobs);
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        Runnable getCallback() {
            return this.callback;
        }

        WorkNode getNext() {
            return this.next;
        }

        @Override // com.facebook.internal.WorkQueue.WorkItem
        public boolean isRunning() {
            return this.isRunning;
        }

        @Override // com.facebook.internal.WorkQueue.WorkItem
        public void moveToFront() {
            synchronized (WorkQueue.this.workLock) {
                try {
                    if (!isRunning()) {
                        WorkQueue workQueue = WorkQueue.this;
                        workQueue.pendingJobs = removeFromList(workQueue.pendingJobs);
                        WorkQueue workQueue2 = WorkQueue.this;
                        workQueue2.pendingJobs = addToList(workQueue2.pendingJobs, true);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        WorkNode removeFromList(WorkNode workNode) {
            if (workNode == this && (workNode = this.next) == this) {
                workNode = null;
            }
            WorkNode workNode2 = this.next;
            workNode2.prev = this.prev;
            this.prev.next = workNode2;
            this.prev = null;
            this.next = null;
            return workNode;
        }

        void setIsRunning(boolean z2) {
            this.isRunning = z2;
        }

        void verify(boolean z2) {
        }
    }

    public WorkQueue() {
        this(8);
    }

    private void execute(final WorkNode workNode) {
        this.executor.execute(new Runnable() { // from class: com.facebook.internal.WorkQueue.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    workNode.getCallback().run();
                } finally {
                    WorkQueue.this.finishItemAndStartNew(workNode);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishItemAndStartNew(WorkNode workNode) {
        WorkNode workNode2;
        synchronized (this.workLock) {
            if (workNode != null) {
                try {
                    this.runningJobs = workNode.removeFromList(this.runningJobs);
                    this.runningCount--;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (this.runningCount < this.maxConcurrent) {
                workNode2 = this.pendingJobs;
                if (workNode2 != null) {
                    this.pendingJobs = workNode2.removeFromList(workNode2);
                    this.runningJobs = workNode2.addToList(this.runningJobs, false);
                    this.runningCount++;
                    workNode2.setIsRunning(true);
                }
            } else {
                workNode2 = null;
            }
        }
        if (workNode2 != null) {
            execute(workNode2);
        }
    }

    private void startItem() {
        finishItemAndStartNew(null);
    }

    public WorkItem addActiveWorkItem(Runnable runnable) {
        return addActiveWorkItem(runnable, true);
    }

    public void validate() {
        synchronized (this.workLock) {
            try {
                WorkNode next = this.runningJobs;
                if (next != null) {
                    do {
                        next.verify(true);
                        next = next.getNext();
                    } while (next != this.runningJobs);
                }
            } finally {
            }
        }
    }

    public WorkQueue(int i2) {
        this(i2, FacebookSdk.getExecutor());
    }

    public WorkItem addActiveWorkItem(Runnable runnable, boolean z2) {
        WorkNode workNode = new WorkNode(runnable);
        synchronized (this.workLock) {
            this.pendingJobs = workNode.addToList(this.pendingJobs, z2);
        }
        startItem();
        return workNode;
    }

    public WorkQueue(int i2, Executor executor) {
        this.workLock = new Object();
        this.runningJobs = null;
        this.runningCount = 0;
        this.maxConcurrent = i2;
        this.executor = executor;
    }
}
