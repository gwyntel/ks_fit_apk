package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.List;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RouteSelector;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

/* loaded from: classes5.dex */
public final class StreamAllocation {
    public final Address address;
    public final Call call;
    private final Object callStackTrace;
    private boolean canceled;
    private HttpCodec codec;
    private RealConnection connection;
    private final ConnectionPool connectionPool;
    public final EventListener eventListener;
    private int refusedStreamCount;
    private boolean released;
    private boolean reportedAcquired;
    private Route route;
    private RouteSelector.Selection routeSelection;
    private final RouteSelector routeSelector;

    public static final class StreamAllocationReference extends WeakReference<StreamAllocation> {
        public final Object callStackTrace;

        StreamAllocationReference(StreamAllocation streamAllocation, Object obj) {
            super(streamAllocation);
            this.callStackTrace = obj;
        }
    }

    public StreamAllocation(ConnectionPool connectionPool, Address address, Call call, EventListener eventListener, Object obj) {
        this.connectionPool = connectionPool;
        this.address = address;
        this.call = call;
        this.eventListener = eventListener;
        this.routeSelector = new RouteSelector(address, routeDatabase(), call, eventListener);
        this.callStackTrace = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.net.Socket deallocate(boolean r2, boolean r3, boolean r4) {
        /*
            r1 = this;
            r0 = 0
            if (r4 == 0) goto L5
            r1.codec = r0
        L5:
            r4 = 1
            if (r3 == 0) goto La
            r1.released = r4
        La:
            okhttp3.internal.connection.RealConnection r3 = r1.connection
            if (r3 == 0) goto L4a
            if (r2 == 0) goto L12
            r3.noNewStreams = r4
        L12:
            okhttp3.internal.http.HttpCodec r2 = r1.codec
            if (r2 != 0) goto L4a
            boolean r2 = r1.released
            if (r2 != 0) goto L1e
            boolean r2 = r3.noNewStreams
            if (r2 == 0) goto L4a
        L1e:
            r1.release(r3)
            okhttp3.internal.connection.RealConnection r2 = r1.connection
            java.util.List<java.lang.ref.Reference<okhttp3.internal.connection.StreamAllocation>> r2 = r2.allocations
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L46
            okhttp3.internal.connection.RealConnection r2 = r1.connection
            long r3 = java.lang.System.nanoTime()
            r2.idleAtNanos = r3
            okhttp3.internal.Internal r2 = okhttp3.internal.Internal.instance
            okhttp3.ConnectionPool r3 = r1.connectionPool
            okhttp3.internal.connection.RealConnection r4 = r1.connection
            boolean r2 = r2.connectionBecameIdle(r3, r4)
            if (r2 == 0) goto L46
            okhttp3.internal.connection.RealConnection r2 = r1.connection
            java.net.Socket r2 = r2.socket()
            goto L47
        L46:
            r2 = r0
        L47:
            r1.connection = r0
            r0 = r2
        L4a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.StreamAllocation.deallocate(boolean, boolean, boolean):java.net.Socket");
    }

    private RealConnection findConnection(int i2, int i3, int i4, int i5, boolean z2) throws Throwable {
        RealConnection realConnection;
        Socket socketReleaseIfNoNewStreams;
        RealConnection realConnection2;
        Socket socketDeduplicate;
        Route next;
        boolean z3;
        boolean z4;
        RouteSelector.Selection selection;
        synchronized (this.connectionPool) {
            try {
                if (this.released) {
                    throw new IllegalStateException("released");
                }
                if (this.codec != null) {
                    throw new IllegalStateException("codec != null");
                }
                if (this.canceled) {
                    throw new IOException("Canceled");
                }
                realConnection = this.connection;
                socketReleaseIfNoNewStreams = releaseIfNoNewStreams();
                realConnection2 = this.connection;
                socketDeduplicate = null;
                if (realConnection2 != null) {
                    realConnection = null;
                } else {
                    realConnection2 = null;
                }
                if (!this.reportedAcquired) {
                    realConnection = null;
                }
                if (realConnection2 == null) {
                    Internal.instance.get(this.connectionPool, this.address, this, null);
                    RealConnection realConnection3 = this.connection;
                    if (realConnection3 != null) {
                        z3 = true;
                        realConnection2 = realConnection3;
                        next = null;
                    } else {
                        next = this.route;
                    }
                } else {
                    next = null;
                }
                z3 = false;
            } finally {
            }
        }
        Util.closeQuietly(socketReleaseIfNoNewStreams);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
        if (z3) {
            this.eventListener.connectionAcquired(this.call, realConnection2);
        }
        if (realConnection2 != null) {
            this.route = this.connection.route();
            return realConnection2;
        }
        if (next != null || ((selection = this.routeSelection) != null && selection.hasNext())) {
            z4 = false;
        } else {
            this.routeSelection = this.routeSelector.next();
            z4 = true;
        }
        synchronized (this.connectionPool) {
            try {
                if (this.canceled) {
                    throw new IOException("Canceled");
                }
                if (z4) {
                    List<Route> all = this.routeSelection.getAll();
                    int size = all.size();
                    int i6 = 0;
                    while (true) {
                        if (i6 >= size) {
                            break;
                        }
                        Route route = all.get(i6);
                        Internal.instance.get(this.connectionPool, this.address, this, route);
                        RealConnection realConnection4 = this.connection;
                        if (realConnection4 != null) {
                            this.route = route;
                            z3 = true;
                            realConnection2 = realConnection4;
                            break;
                        }
                        i6++;
                    }
                }
                if (!z3) {
                    if (next == null) {
                        next = this.routeSelection.next();
                    }
                    this.route = next;
                    this.refusedStreamCount = 0;
                    realConnection2 = new RealConnection(this.connectionPool, next);
                    acquire(realConnection2, false);
                }
            } finally {
            }
        }
        if (z3) {
            this.eventListener.connectionAcquired(this.call, realConnection2);
            return realConnection2;
        }
        realConnection2.connect(i2, i3, i4, i5, z2, this.call, this.eventListener);
        routeDatabase().connected(realConnection2.route());
        synchronized (this.connectionPool) {
            try {
                this.reportedAcquired = true;
                Internal.instance.put(this.connectionPool, realConnection2);
                if (realConnection2.isMultiplexed()) {
                    socketDeduplicate = Internal.instance.deduplicate(this.connectionPool, this.address, this);
                    realConnection2 = this.connection;
                }
            } finally {
            }
        }
        Util.closeQuietly(socketDeduplicate);
        this.eventListener.connectionAcquired(this.call, realConnection2);
        return realConnection2;
    }

    private RealConnection findHealthyConnection(int i2, int i3, int i4, int i5, boolean z2, boolean z3) throws Throwable {
        while (true) {
            RealConnection realConnectionFindConnection = findConnection(i2, i3, i4, i5, z2);
            synchronized (this.connectionPool) {
                try {
                    if (realConnectionFindConnection.successCount == 0 && !realConnectionFindConnection.isMultiplexed()) {
                        return realConnectionFindConnection;
                    }
                    if (realConnectionFindConnection.isHealthy(z3)) {
                        return realConnectionFindConnection;
                    }
                    noNewStreams();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private Socket releaseIfNoNewStreams() {
        RealConnection realConnection = this.connection;
        if (realConnection == null || !realConnection.noNewStreams) {
            return null;
        }
        return deallocate(false, false, true);
    }

    private RouteDatabase routeDatabase() {
        return Internal.instance.routeDatabase(this.connectionPool);
    }

    public void acquire(RealConnection realConnection, boolean z2) {
        if (this.connection != null) {
            throw new IllegalStateException();
        }
        this.connection = realConnection;
        this.reportedAcquired = z2;
        realConnection.allocations.add(new StreamAllocationReference(this, this.callStackTrace));
    }

    public void cancel() throws IOException {
        HttpCodec httpCodec;
        RealConnection realConnection;
        synchronized (this.connectionPool) {
            this.canceled = true;
            httpCodec = this.codec;
            realConnection = this.connection;
        }
        if (httpCodec != null) {
            httpCodec.cancel();
        } else if (realConnection != null) {
            realConnection.cancel();
        }
    }

    public HttpCodec codec() {
        HttpCodec httpCodec;
        synchronized (this.connectionPool) {
            httpCodec = this.codec;
        }
        return httpCodec;
    }

    public synchronized RealConnection connection() {
        return this.connection;
    }

    public boolean hasMoreRoutes() {
        RouteSelector.Selection selection;
        return this.route != null || ((selection = this.routeSelection) != null && selection.hasNext()) || this.routeSelector.hasNext();
    }

    public HttpCodec newStream(OkHttpClient okHttpClient, Interceptor.Chain chain, boolean z2) {
        try {
            HttpCodec httpCodecNewCodec = findHealthyConnection(chain.connectTimeoutMillis(), chain.readTimeoutMillis(), chain.writeTimeoutMillis(), okHttpClient.pingIntervalMillis(), okHttpClient.retryOnConnectionFailure(), z2).newCodec(okHttpClient, chain, this);
            synchronized (this.connectionPool) {
                this.codec = httpCodecNewCodec;
            }
            return httpCodecNewCodec;
        } catch (IOException e2) {
            throw new RouteException(e2);
        }
    }

    public void noNewStreams() throws IOException {
        RealConnection realConnection;
        Socket socketDeallocate;
        synchronized (this.connectionPool) {
            realConnection = this.connection;
            socketDeallocate = deallocate(true, false, false);
            if (this.connection != null) {
                realConnection = null;
            }
        }
        Util.closeQuietly(socketDeallocate);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
    }

    public void release() throws IOException {
        RealConnection realConnection;
        Socket socketDeallocate;
        synchronized (this.connectionPool) {
            realConnection = this.connection;
            socketDeallocate = deallocate(false, true, false);
            if (this.connection != null) {
                realConnection = null;
            }
        }
        Util.closeQuietly(socketDeallocate);
        if (realConnection != null) {
            Internal.instance.timeoutExit(this.call, null);
            this.eventListener.connectionReleased(this.call, realConnection);
            this.eventListener.callEnd(this.call);
        }
    }

    public Socket releaseAndAcquire(RealConnection realConnection) {
        if (this.codec != null || this.connection.allocations.size() != 1) {
            throw new IllegalStateException();
        }
        Reference<StreamAllocation> reference = this.connection.allocations.get(0);
        Socket socketDeallocate = deallocate(true, false, false);
        this.connection = realConnection;
        realConnection.allocations.add(reference);
        return socketDeallocate;
    }

    public Route route() {
        return this.route;
    }

    public void streamFailed(IOException iOException) throws IOException {
        RealConnection realConnection;
        boolean z2;
        Socket socketDeallocate;
        synchronized (this.connectionPool) {
            try {
                realConnection = null;
                if (iOException instanceof StreamResetException) {
                    ErrorCode errorCode = ((StreamResetException) iOException).errorCode;
                    if (errorCode == ErrorCode.REFUSED_STREAM) {
                        int i2 = this.refusedStreamCount + 1;
                        this.refusedStreamCount = i2;
                        if (i2 > 1) {
                            this.route = null;
                            z2 = true;
                        }
                        z2 = false;
                    } else {
                        if (errorCode != ErrorCode.CANCEL) {
                            this.route = null;
                            z2 = true;
                        }
                        z2 = false;
                    }
                } else {
                    RealConnection realConnection2 = this.connection;
                    if (realConnection2 != null && (!realConnection2.isMultiplexed() || (iOException instanceof ConnectionShutdownException))) {
                        if (this.connection.successCount == 0) {
                            Route route = this.route;
                            if (route != null && iOException != null) {
                                this.routeSelector.connectFailed(route, iOException);
                            }
                            this.route = null;
                        }
                        z2 = true;
                    }
                    z2 = false;
                }
                RealConnection realConnection3 = this.connection;
                socketDeallocate = deallocate(z2, false, true);
                if (this.connection == null && this.reportedAcquired) {
                    realConnection = realConnection3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Util.closeQuietly(socketDeallocate);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
    }

    public void streamFinished(boolean z2, HttpCodec httpCodec, long j2, IOException iOException) {
        RealConnection realConnection;
        Socket socketDeallocate;
        boolean z3;
        this.eventListener.responseBodyEnd(this.call, j2);
        synchronized (this.connectionPool) {
            if (httpCodec != null) {
                try {
                    if (httpCodec == this.codec) {
                        if (!z2) {
                            this.connection.successCount++;
                        }
                        realConnection = this.connection;
                        socketDeallocate = deallocate(z2, false, true);
                        if (this.connection != null) {
                            realConnection = null;
                        }
                        z3 = this.released;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            throw new IllegalStateException("expected " + this.codec + " but was " + httpCodec);
        }
        Util.closeQuietly(socketDeallocate);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
        if (iOException != null) {
            this.eventListener.callFailed(this.call, Internal.instance.timeoutExit(this.call, iOException));
        } else if (z3) {
            Internal.instance.timeoutExit(this.call, null);
            this.eventListener.callEnd(this.call);
        }
    }

    public String toString() {
        RealConnection realConnectionConnection = connection();
        return realConnectionConnection != null ? realConnectionConnection.toString() : this.address.toString();
    }

    private void release(RealConnection realConnection) {
        int size = realConnection.allocations.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (realConnection.allocations.get(i2).get() == this) {
                realConnection.allocations.remove(i2);
                return;
            }
        }
        throw new IllegalStateException();
    }
}
