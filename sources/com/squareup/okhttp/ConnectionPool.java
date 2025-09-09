package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class ConnectionPool {
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
    private static final ConnectionPool systemDefault;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;
    private final LinkedList<Connection> connections = new LinkedList<>();
    private Executor executor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
    private final Runnable connectionsCleanupRunnable = new Runnable() { // from class: com.squareup.okhttp.ConnectionPool.1
        @Override // java.lang.Runnable
        public void run() {
            ConnectionPool.this.runCleanupUntilPoolIsEmpty();
        }
    };

    static {
        String property = System.getProperty("http.keepAlive");
        String property2 = System.getProperty("http.keepAliveDuration");
        String property3 = System.getProperty("http.maxConnections");
        long j2 = property2 != null ? Long.parseLong(property2) : 300000L;
        if (property != null && !Boolean.parseBoolean(property)) {
            systemDefault = new ConnectionPool(0, j2);
        } else if (property3 != null) {
            systemDefault = new ConnectionPool(Integer.parseInt(property3), j2);
        } else {
            systemDefault = new ConnectionPool(5, j2);
        }
    }

    public ConnectionPool(int i2, long j2) {
        this.maxIdleConnections = i2;
        this.keepAliveDurationNs = j2 * 1000000;
    }

    private void addConnection(Connection connection) {
        boolean zIsEmpty = this.connections.isEmpty();
        this.connections.addFirst(connection);
        if (zIsEmpty) {
            this.executor.execute(this.connectionsCleanupRunnable);
        } else {
            notifyAll();
        }
    }

    public static ConnectionPool getDefault() {
        return systemDefault;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runCleanupUntilPoolIsEmpty() {
        while (b()) {
        }
    }

    boolean b() throws IOException {
        synchronized (this) {
            try {
                if (this.connections.isEmpty()) {
                    return false;
                }
                ArrayList arrayList = new ArrayList();
                long jNanoTime = System.nanoTime();
                long jMin = this.keepAliveDurationNs;
                LinkedList<Connection> linkedList = this.connections;
                ListIterator<Connection> listIterator = linkedList.listIterator(linkedList.size());
                int i2 = 0;
                while (listIterator.hasPrevious()) {
                    Connection connectionPrevious = listIterator.previous();
                    long jE = (connectionPrevious.e() + this.keepAliveDurationNs) - jNanoTime;
                    if (jE <= 0 || !connectionPrevious.g()) {
                        listIterator.remove();
                        arrayList.add(connectionPrevious);
                    } else if (connectionPrevious.j()) {
                        i2++;
                        jMin = Math.min(jMin, jE);
                    }
                }
                LinkedList<Connection> linkedList2 = this.connections;
                ListIterator<Connection> listIterator2 = linkedList2.listIterator(linkedList2.size());
                while (listIterator2.hasPrevious() && i2 > this.maxIdleConnections) {
                    Connection connectionPrevious2 = listIterator2.previous();
                    if (connectionPrevious2.j()) {
                        arrayList.add(connectionPrevious2);
                        listIterator2.remove();
                        i2--;
                    }
                }
                if (arrayList.isEmpty()) {
                    try {
                        long j2 = jMin / 1000000;
                        Long.signum(j2);
                        wait(j2, (int) (jMin - (1000000 * j2)));
                        return true;
                    } catch (InterruptedException unused) {
                    }
                }
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    Util.closeQuietly(((Connection) arrayList.get(i3)).getSocket());
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void c(Connection connection) {
        if (!connection.i() && connection.a()) {
            if (!connection.g()) {
                Util.closeQuietly(connection.getSocket());
                return;
            }
            try {
                Platform.get().untagSocket(connection.getSocket());
                synchronized (this) {
                    addConnection(connection);
                    connection.f();
                    connection.p();
                }
            } catch (SocketException e2) {
                Platform.get().logW("Unable to untagSocket(): " + e2);
                Util.closeQuietly(connection.getSocket());
            }
        }
    }

    void d(Connection connection) {
        if (!connection.i()) {
            throw new IllegalArgumentException();
        }
        if (connection.g()) {
            synchronized (this) {
                addConnection(connection);
            }
        }
    }

    public void evictAll() throws IOException {
        ArrayList arrayList;
        synchronized (this) {
            arrayList = new ArrayList(this.connections);
            this.connections.clear();
            notifyAll();
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Util.closeQuietly(((Connection) arrayList.get(i2)).getSocket());
        }
    }

    public synchronized Connection get(Address address) {
        Connection connectionPrevious;
        LinkedList<Connection> linkedList = this.connections;
        ListIterator<Connection> listIterator = linkedList.listIterator(linkedList.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                connectionPrevious = null;
                break;
            }
            connectionPrevious = listIterator.previous();
            if (connectionPrevious.getRoute().getAddress().equals(address) && connectionPrevious.g() && System.nanoTime() - connectionPrevious.e() < this.keepAliveDurationNs) {
                listIterator.remove();
                if (connectionPrevious.i()) {
                    break;
                }
                try {
                    Platform.get().tagSocket(connectionPrevious.getSocket());
                    break;
                } catch (SocketException e2) {
                    Util.closeQuietly(connectionPrevious.getSocket());
                    Platform.get().logW("Unable to tagSocket(): " + e2);
                }
            }
        }
        if (connectionPrevious != null && connectionPrevious.i()) {
            this.connections.addFirst(connectionPrevious);
        }
        return connectionPrevious;
    }

    public synchronized int getConnectionCount() {
        return this.connections.size();
    }

    public synchronized int getHttpConnectionCount() {
        return this.connections.size() - getMultiplexedConnectionCount();
    }

    public synchronized int getMultiplexedConnectionCount() {
        int i2;
        Iterator<Connection> it = this.connections.iterator();
        i2 = 0;
        while (it.hasNext()) {
            if (it.next().i()) {
                i2++;
            }
        }
        return i2;
    }

    @Deprecated
    public synchronized int getSpdyConnectionCount() {
        return getMultiplexedConnectionCount();
    }
}
