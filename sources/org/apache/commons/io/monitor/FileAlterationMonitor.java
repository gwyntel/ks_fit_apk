package org.apache.commons.io.monitor;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes5.dex */
public final class FileAlterationMonitor implements Runnable {
    private final long interval;
    private final List<FileAlterationObserver> observers;
    private volatile boolean running;
    private Thread thread;
    private ThreadFactory threadFactory;

    public FileAlterationMonitor() {
        this(10000L);
    }

    public void addObserver(FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            this.observers.add(fileAlterationObserver);
        }
    }

    public long getInterval() {
        return this.interval;
    }

    public Iterable<FileAlterationObserver> getObservers() {
        return this.observers;
    }

    public void removeObserver(FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            while (this.observers.remove(fileAlterationObserver)) {
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        while (this.running) {
            Iterator<FileAlterationObserver> it = this.observers.iterator();
            while (it.hasNext()) {
                it.next().checkAndNotify();
            }
            if (!this.running) {
                return;
            } else {
                try {
                    Thread.sleep(this.interval);
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public synchronized void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public synchronized void start() throws Exception {
        try {
            if (this.running) {
                throw new IllegalStateException("Monitor is already running");
            }
            Iterator<FileAlterationObserver> it = this.observers.iterator();
            while (it.hasNext()) {
                it.next().initialize();
            }
            this.running = true;
            ThreadFactory threadFactory = this.threadFactory;
            if (threadFactory != null) {
                this.thread = threadFactory.newThread(this);
            } else {
                this.thread = new Thread(this);
            }
            this.thread.start();
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void stop() throws Exception {
        stop(this.interval);
    }

    public FileAlterationMonitor(long j2) {
        this.observers = new CopyOnWriteArrayList();
        this.thread = null;
        this.running = false;
        this.interval = j2;
    }

    public synchronized void stop(long j2) throws Exception {
        if (this.running) {
            this.running = false;
            try {
                this.thread.join(j2);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            Iterator<FileAlterationObserver> it = this.observers.iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
        } else {
            throw new IllegalStateException("Monitor is not running");
        }
    }

    public FileAlterationMonitor(long j2, FileAlterationObserver... fileAlterationObserverArr) {
        this(j2);
        if (fileAlterationObserverArr != null) {
            for (FileAlterationObserver fileAlterationObserver : fileAlterationObserverArr) {
                addObserver(fileAlterationObserver);
            }
        }
    }
}
