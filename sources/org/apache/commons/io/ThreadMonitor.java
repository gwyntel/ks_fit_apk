package org.apache.commons.io;

/* loaded from: classes5.dex */
class ThreadMonitor implements Runnable {
    private final Thread thread;
    private final long timeout;

    private ThreadMonitor(Thread thread, long j2) {
        this.thread = thread;
        this.timeout = j2;
    }

    private static void sleep(long j2) throws InterruptedException {
        long jCurrentTimeMillis = System.currentTimeMillis() + j2;
        do {
            Thread.sleep(j2);
            j2 = jCurrentTimeMillis - System.currentTimeMillis();
        } while (j2 > 0);
    }

    public static Thread start(long j2) {
        return start(Thread.currentThread(), j2);
    }

    public static void stop(Thread thread) {
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            sleep(this.timeout);
            this.thread.interrupt();
        } catch (InterruptedException unused) {
        }
    }

    public static Thread start(Thread thread, long j2) {
        if (j2 <= 0) {
            return null;
        }
        Thread thread2 = new Thread(new ThreadMonitor(thread, j2), ThreadMonitor.class.getSimpleName());
        thread2.setDaemon(true);
        thread2.start();
        return thread2;
    }
}
