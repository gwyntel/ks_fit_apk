package com.squareup.okhttp;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class Dispatcher {
    private ExecutorService executorService;
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;
    private final Deque<Call.AsyncCall> readyCalls = new ArrayDeque();
    private final Deque<Call.AsyncCall> runningCalls = new ArrayDeque();
    private final Deque<Call> executedCalls = new ArrayDeque();

    public Dispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private void promoteCalls() {
        if (this.runningCalls.size() < this.maxRequests && !this.readyCalls.isEmpty()) {
            Iterator<Call.AsyncCall> it = this.readyCalls.iterator();
            while (it.hasNext()) {
                Call.AsyncCall next = it.next();
                if (runningCallsForHost(next) < this.maxRequestsPerHost) {
                    it.remove();
                    this.runningCalls.add(next);
                    getExecutorService().execute(next);
                }
                if (this.runningCalls.size() >= this.maxRequests) {
                    return;
                }
            }
        }
    }

    private int runningCallsForHost(Call.AsyncCall asyncCall) {
        Iterator<Call.AsyncCall> it = this.runningCalls.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().c().equals(asyncCall.c())) {
                i2++;
            }
        }
        return i2;
    }

    synchronized void a(Call.AsyncCall asyncCall) {
        try {
            if (this.runningCalls.size() >= this.maxRequests || runningCallsForHost(asyncCall) >= this.maxRequestsPerHost) {
                this.readyCalls.add(asyncCall);
            } else {
                this.runningCalls.add(asyncCall);
                getExecutorService().execute(asyncCall);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    synchronized void b(Call call) {
        this.executedCalls.add(call);
    }

    synchronized void c(Call.AsyncCall asyncCall) {
        if (!this.runningCalls.remove(asyncCall)) {
            throw new AssertionError("AsyncCall wasn't running!");
        }
        promoteCalls();
    }

    public synchronized void cancel(Object obj) {
        try {
            for (Call.AsyncCall asyncCall : this.readyCalls) {
                if (Util.equal(obj, asyncCall.d())) {
                    asyncCall.a();
                }
            }
            for (Call.AsyncCall asyncCall2 : this.runningCalls) {
                if (Util.equal(obj, asyncCall2.d())) {
                    asyncCall2.b().f19878a = true;
                    HttpEngine httpEngine = asyncCall2.b().f19880c;
                    if (httpEngine != null) {
                        httpEngine.disconnect();
                    }
                }
            }
            for (Call call : this.executedCalls) {
                if (Util.equal(obj, call.f())) {
                    call.cancel();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    synchronized void d(Call call) {
        if (!this.executedCalls.remove(call)) {
            throw new AssertionError("Call wasn't in-flight!");
        }
    }

    public synchronized ExecutorService getExecutorService() {
        try {
            if (this.executorService == null) {
                this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Dispatcher", false));
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.executorService;
    }

    public synchronized int getMaxRequests() {
        return this.maxRequests;
    }

    public synchronized int getMaxRequestsPerHost() {
        return this.maxRequestsPerHost;
    }

    public synchronized int getQueuedCallCount() {
        return this.readyCalls.size();
    }

    public synchronized int getRunningCallCount() {
        return this.runningCalls.size();
    }

    public synchronized void setMaxRequests(int i2) {
        if (i2 < 1) {
            throw new IllegalArgumentException("max < 1: " + i2);
        }
        this.maxRequests = i2;
        promoteCalls();
    }

    public synchronized void setMaxRequestsPerHost(int i2) {
        if (i2 < 1) {
            throw new IllegalArgumentException("max < 1: " + i2);
        }
        this.maxRequestsPerHost = i2;
        promoteCalls();
    }

    public Dispatcher() {
    }
}
