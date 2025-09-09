package okhttp3;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.platform.Platform;

/* loaded from: classes5.dex */
public final class ConnectionPool {
    private static final Executor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp ConnectionPool", true));

    /* renamed from: a, reason: collision with root package name */
    final RouteDatabase f26110a;

    /* renamed from: b, reason: collision with root package name */
    boolean f26111b;
    private final Runnable cleanupRunnable;
    private final Deque<RealConnection> connections;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    public ConnectionPool() {
        this(5, 5L, TimeUnit.MINUTES);
    }

    private int pruneAndGetAllocationCount(RealConnection realConnection, long j2) {
        List<Reference<StreamAllocation>> list = realConnection.allocations;
        int i2 = 0;
        while (i2 < list.size()) {
            Reference<StreamAllocation> reference = list.get(i2);
            if (reference.get() != null) {
                i2++;
            } else {
                Platform.get().logCloseableLeak("A connection to " + realConnection.route().address().url() + " was leaked. Did you forget to close a response body?", ((StreamAllocation.StreamAllocationReference) reference).callStackTrace);
                list.remove(i2);
                realConnection.noNewStreams = true;
                if (list.isEmpty()) {
                    realConnection.idleAtNanos = j2 - this.keepAliveDurationNs;
                    return 0;
                }
            }
        }
        return list.size();
    }

    long a(long j2) throws IOException {
        synchronized (this) {
            try {
                RealConnection realConnection = null;
                long j3 = Long.MIN_VALUE;
                int i2 = 0;
                int i3 = 0;
                for (RealConnection realConnection2 : this.connections) {
                    if (pruneAndGetAllocationCount(realConnection2, j2) > 0) {
                        i3++;
                    } else {
                        i2++;
                        long j4 = j2 - realConnection2.idleAtNanos;
                        if (j4 > j3) {
                            realConnection = realConnection2;
                            j3 = j4;
                        }
                    }
                }
                long j5 = this.keepAliveDurationNs;
                if (j3 < j5 && i2 <= this.maxIdleConnections) {
                    if (i2 > 0) {
                        return j5 - j3;
                    }
                    if (i3 > 0) {
                        return j5;
                    }
                    this.f26111b = false;
                    return -1L;
                }
                this.connections.remove(realConnection);
                Util.closeQuietly(realConnection.socket());
                return 0L;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    boolean b(RealConnection realConnection) {
        if (realConnection.noNewStreams || this.maxIdleConnections == 0) {
            this.connections.remove(realConnection);
            return true;
        }
        notifyAll();
        return false;
    }

    Socket c(Address address, StreamAllocation streamAllocation) {
        for (RealConnection realConnection : this.connections) {
            if (realConnection.isEligible(address, null) && realConnection.isMultiplexed() && realConnection != streamAllocation.connection()) {
                return streamAllocation.releaseAndAcquire(realConnection);
            }
        }
        return null;
    }

    public synchronized int connectionCount() {
        return this.connections.size();
    }

    RealConnection d(Address address, StreamAllocation streamAllocation, Route route) {
        for (RealConnection realConnection : this.connections) {
            if (realConnection.isEligible(address, route)) {
                streamAllocation.acquire(realConnection, true);
                return realConnection;
            }
        }
        return null;
    }

    void e(RealConnection realConnection) {
        if (!this.f26111b) {
            this.f26111b = true;
            executor.execute(this.cleanupRunnable);
        }
        this.connections.add(realConnection);
    }

    public void evictAll() throws IOException {
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            try {
                Iterator<RealConnection> it = this.connections.iterator();
                while (it.hasNext()) {
                    RealConnection next = it.next();
                    if (next.allocations.isEmpty()) {
                        next.noNewStreams = true;
                        arrayList.add(next);
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Util.closeQuietly(((RealConnection) it2.next()).socket());
        }
    }

    public synchronized int idleConnectionCount() {
        int i2;
        Iterator<RealConnection> it = this.connections.iterator();
        i2 = 0;
        while (it.hasNext()) {
            if (it.next().allocations.isEmpty()) {
                i2++;
            }
        }
        return i2;
    }

    public ConnectionPool(int i2, long j2, TimeUnit timeUnit) {
        this.cleanupRunnable = new Runnable() { // from class: okhttp3.ConnectionPool.1
            @Override // java.lang.Runnable
            public void run() throws IOException {
                while (true) {
                    long jA = ConnectionPool.this.a(System.nanoTime());
                    if (jA == -1) {
                        return;
                    }
                    if (jA > 0) {
                        long j3 = jA / 1000000;
                        long j4 = jA - (1000000 * j3);
                        synchronized (ConnectionPool.this) {
                            try {
                                ConnectionPool.this.wait(j3, (int) j4);
                            } catch (InterruptedException unused) {
                            }
                        }
                    }
                }
            }
        };
        this.connections = new ArrayDeque();
        this.f26110a = new RouteDatabase();
        this.maxIdleConnections = i2;
        this.keepAliveDurationNs = timeUnit.toNanos(j2);
        if (j2 > 0) {
            return;
        }
        throw new IllegalArgumentException("keepAliveDuration <= 0: " + j2);
    }
}
