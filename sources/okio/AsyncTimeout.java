package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class AsyncTimeout extends Timeout {
    private static final long IDLE_TIMEOUT_MILLIS;
    private static final long IDLE_TIMEOUT_NANOS;
    private static final int TIMEOUT_WRITE_SIZE = 65536;

    /* renamed from: a, reason: collision with root package name */
    static AsyncTimeout f26464a;
    private boolean inQueue;

    @Nullable
    private AsyncTimeout next;
    private long timeoutAt;

    private static final class Watchdog extends Thread {
        Watchdog() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0017, code lost:
        
            r1.e();
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r3 = this;
            L0:
                java.lang.Class<okio.AsyncTimeout> r0 = okio.AsyncTimeout.class
                monitor-enter(r0)     // Catch: java.lang.InterruptedException -> L0
                okio.AsyncTimeout r1 = okio.AsyncTimeout.a()     // Catch: java.lang.Throwable -> Lb
                if (r1 != 0) goto Ld
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb
                goto L0
            Lb:
                r1 = move-exception
                goto L1b
            Ld:
                okio.AsyncTimeout r2 = okio.AsyncTimeout.f26464a     // Catch: java.lang.Throwable -> Lb
                if (r1 != r2) goto L16
                r1 = 0
                okio.AsyncTimeout.f26464a = r1     // Catch: java.lang.Throwable -> Lb
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb
                return
            L16:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb
                r1.e()     // Catch: java.lang.InterruptedException -> L0
                goto L0
            L1b:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb
                throw r1     // Catch: java.lang.InterruptedException -> L0
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.AsyncTimeout.Watchdog.run():void");
        }
    }

    static {
        long millis = TimeUnit.SECONDS.toMillis(60L);
        IDLE_TIMEOUT_MILLIS = millis;
        IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(millis);
    }

    static AsyncTimeout a() throws InterruptedException {
        AsyncTimeout asyncTimeout = f26464a.next;
        if (asyncTimeout == null) {
            long jNanoTime = System.nanoTime();
            AsyncTimeout.class.wait(IDLE_TIMEOUT_MILLIS);
            if (f26464a.next != null || System.nanoTime() - jNanoTime < IDLE_TIMEOUT_NANOS) {
                return null;
            }
            return f26464a;
        }
        long jRemainingNanos = asyncTimeout.remainingNanos(System.nanoTime());
        if (jRemainingNanos > 0) {
            long j2 = jRemainingNanos / 1000000;
            AsyncTimeout.class.wait(j2, (int) (jRemainingNanos - (1000000 * j2)));
            return null;
        }
        f26464a.next = asyncTimeout.next;
        asyncTimeout.next = null;
        return asyncTimeout;
    }

    private static synchronized boolean cancelScheduledTimeout(AsyncTimeout asyncTimeout) {
        AsyncTimeout asyncTimeout2 = f26464a;
        while (asyncTimeout2 != null) {
            AsyncTimeout asyncTimeout3 = asyncTimeout2.next;
            if (asyncTimeout3 == asyncTimeout) {
                asyncTimeout2.next = asyncTimeout.next;
                asyncTimeout.next = null;
                return false;
            }
            asyncTimeout2 = asyncTimeout3;
        }
        return true;
    }

    private long remainingNanos(long j2) {
        return this.timeoutAt - j2;
    }

    private static synchronized void scheduleTimeout(AsyncTimeout asyncTimeout, long j2, boolean z2) {
        try {
            if (f26464a == null) {
                f26464a = new AsyncTimeout();
                new Watchdog().start();
            }
            long jNanoTime = System.nanoTime();
            if (j2 != 0 && z2) {
                asyncTimeout.timeoutAt = Math.min(j2, asyncTimeout.deadlineNanoTime() - jNanoTime) + jNanoTime;
            } else if (j2 != 0) {
                asyncTimeout.timeoutAt = j2 + jNanoTime;
            } else {
                if (!z2) {
                    throw new AssertionError();
                }
                asyncTimeout.timeoutAt = asyncTimeout.deadlineNanoTime();
            }
            long jRemainingNanos = asyncTimeout.remainingNanos(jNanoTime);
            AsyncTimeout asyncTimeout2 = f26464a;
            while (true) {
                AsyncTimeout asyncTimeout3 = asyncTimeout2.next;
                if (asyncTimeout3 == null || jRemainingNanos < asyncTimeout3.remainingNanos(jNanoTime)) {
                    break;
                } else {
                    asyncTimeout2 = asyncTimeout2.next;
                }
            }
            asyncTimeout.next = asyncTimeout2.next;
            asyncTimeout2.next = asyncTimeout;
            if (asyncTimeout2 == f26464a) {
                AsyncTimeout.class.notify();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    final IOException b(IOException iOException) {
        return !exit() ? iOException : d(iOException);
    }

    final void c(boolean z2) throws IOException {
        if (exit() && z2) {
            throw d(null);
        }
    }

    protected IOException d(IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    protected void e() {
    }

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long jTimeoutNanos = timeoutNanos();
        boolean zHasDeadline = hasDeadline();
        if (jTimeoutNanos != 0 || zHasDeadline) {
            this.inQueue = true;
            scheduleTimeout(this, jTimeoutNanos, zHasDeadline);
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return cancelScheduledTimeout(this);
    }

    public final Sink sink(final Sink sink) {
        return new Sink() { // from class: okio.AsyncTimeout.1
            @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                AsyncTimeout.this.enter();
                try {
                    try {
                        sink.close();
                        AsyncTimeout.this.c(true);
                    } catch (IOException e2) {
                        throw AsyncTimeout.this.b(e2);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.c(false);
                    throw th;
                }
            }

            @Override // okio.Sink, java.io.Flushable
            public void flush() throws IOException {
                AsyncTimeout.this.enter();
                try {
                    try {
                        sink.flush();
                        AsyncTimeout.this.c(true);
                    } catch (IOException e2) {
                        throw AsyncTimeout.this.b(e2);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.c(false);
                    throw th;
                }
            }

            @Override // okio.Sink
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                return "AsyncTimeout.sink(" + sink + ")";
            }

            @Override // okio.Sink
            public void write(Buffer buffer, long j2) throws IOException {
                Util.checkOffsetAndCount(buffer.f26470b, 0L, j2);
                while (true) {
                    long j3 = 0;
                    if (j2 <= 0) {
                        return;
                    }
                    Segment segment = buffer.f26469a;
                    while (true) {
                        if (j3 >= PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                            break;
                        }
                        j3 += segment.f26495c - segment.f26494b;
                        if (j3 >= j2) {
                            j3 = j2;
                            break;
                        }
                        segment = segment.f26498f;
                    }
                    AsyncTimeout.this.enter();
                    try {
                        try {
                            sink.write(buffer, j3);
                            j2 -= j3;
                            AsyncTimeout.this.c(true);
                        } catch (IOException e2) {
                            throw AsyncTimeout.this.b(e2);
                        }
                    } catch (Throwable th) {
                        AsyncTimeout.this.c(false);
                        throw th;
                    }
                }
            }
        };
    }

    public final Source source(final Source source) {
        return new Source() { // from class: okio.AsyncTimeout.2
            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                try {
                    try {
                        source.close();
                        AsyncTimeout.this.c(true);
                    } catch (IOException e2) {
                        throw AsyncTimeout.this.b(e2);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.c(false);
                    throw th;
                }
            }

            @Override // okio.Source
            public long read(Buffer buffer, long j2) throws IOException {
                AsyncTimeout.this.enter();
                try {
                    try {
                        long j3 = source.read(buffer, j2);
                        AsyncTimeout.this.c(true);
                        return j3;
                    } catch (IOException e2) {
                        throw AsyncTimeout.this.b(e2);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.c(false);
                    throw th;
                }
            }

            @Override // okio.Source
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                return "AsyncTimeout.source(" + source + ")";
            }
        };
    }
}
