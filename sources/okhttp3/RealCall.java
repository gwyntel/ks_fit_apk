package okhttp3;

import androidx.core.app.NotificationCompat;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;
import okio.AsyncTimeout;
import okio.Timeout;

/* loaded from: classes5.dex */
final class RealCall implements Call {

    /* renamed from: a, reason: collision with root package name */
    final OkHttpClient f26197a;

    /* renamed from: b, reason: collision with root package name */
    final RetryAndFollowUpInterceptor f26198b;

    /* renamed from: c, reason: collision with root package name */
    final AsyncTimeout f26199c;

    /* renamed from: d, reason: collision with root package name */
    final Request f26200d;

    /* renamed from: e, reason: collision with root package name */
    final boolean f26201e;

    @Nullable
    private EventListener eventListener;
    private boolean executed;

    final class AsyncCall extends NamedRunnable {
        private final Callback responseCallback;

        AsyncCall(Callback callback) {
            super("OkHttp %s", RealCall.this.d());
            this.responseCallback = callback;
        }

        void a(ExecutorService executorService) {
            try {
                try {
                    executorService.execute(this);
                } catch (RejectedExecutionException e2) {
                    InterruptedIOException interruptedIOException = new InterruptedIOException("executor rejected");
                    interruptedIOException.initCause(e2);
                    RealCall.this.eventListener.callFailed(RealCall.this, interruptedIOException);
                    this.responseCallback.onFailure(RealCall.this, interruptedIOException);
                    RealCall.this.f26197a.dispatcher().c(this);
                }
            } catch (Throwable th) {
                RealCall.this.f26197a.dispatcher().c(this);
                throw th;
            }
        }

        RealCall b() {
            return RealCall.this;
        }

        String c() {
            return RealCall.this.f26200d.url().host();
        }

        @Override // okhttp3.internal.NamedRunnable
        protected void execute() {
            boolean z2;
            Throwable th;
            IOException e2;
            RealCall.this.f26199c.enter();
            try {
                try {
                    z2 = true;
                } catch (IOException e3) {
                    z2 = false;
                    e2 = e3;
                } catch (Throwable th2) {
                    z2 = false;
                    th = th2;
                }
                try {
                    this.responseCallback.onResponse(RealCall.this, RealCall.this.b());
                } catch (IOException e4) {
                    e2 = e4;
                    IOException iOExceptionF = RealCall.this.f(e2);
                    if (z2) {
                        Platform.get().log(4, "Callback failure for " + RealCall.this.g(), iOExceptionF);
                    } else {
                        RealCall.this.eventListener.callFailed(RealCall.this, iOExceptionF);
                        this.responseCallback.onFailure(RealCall.this, iOExceptionF);
                    }
                    RealCall.this.f26197a.dispatcher().c(this);
                } catch (Throwable th3) {
                    th = th3;
                    RealCall.this.cancel();
                    if (!z2) {
                        this.responseCallback.onFailure(RealCall.this, new IOException("canceled due to " + th));
                    }
                    throw th;
                }
                RealCall.this.f26197a.dispatcher().c(this);
            } catch (Throwable th4) {
                RealCall.this.f26197a.dispatcher().c(this);
                throw th4;
            }
        }
    }

    private RealCall(OkHttpClient okHttpClient, Request request, boolean z2) {
        this.f26197a = okHttpClient;
        this.f26200d = request;
        this.f26201e = z2;
        this.f26198b = new RetryAndFollowUpInterceptor(okHttpClient, z2);
        AsyncTimeout asyncTimeout = new AsyncTimeout() { // from class: okhttp3.RealCall.1
            @Override // okio.AsyncTimeout
            protected void e() {
                RealCall.this.cancel();
            }
        };
        this.f26199c = asyncTimeout;
        asyncTimeout.timeout(okHttpClient.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    static RealCall c(OkHttpClient okHttpClient, Request request, boolean z2) {
        RealCall realCall = new RealCall(okHttpClient, request, z2);
        realCall.eventListener = okHttpClient.eventListenerFactory().create(realCall);
        return realCall;
    }

    private void captureCallStackTrace() {
        this.f26198b.setCallStackTrace(Platform.get().getStackTraceForCloseable("response.body().close()"));
    }

    Response b() throws IOException {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.f26197a.interceptors());
        arrayList.add(this.f26198b);
        arrayList.add(new BridgeInterceptor(this.f26197a.cookieJar()));
        arrayList.add(new CacheInterceptor(this.f26197a.a()));
        arrayList.add(new ConnectInterceptor(this.f26197a));
        if (!this.f26201e) {
            arrayList.addAll(this.f26197a.networkInterceptors());
        }
        arrayList.add(new CallServerInterceptor(this.f26201e));
        Response responseProceed = new RealInterceptorChain(arrayList, null, null, null, 0, this.f26200d, this, this.eventListener, this.f26197a.connectTimeoutMillis(), this.f26197a.readTimeoutMillis(), this.f26197a.writeTimeoutMillis()).proceed(this.f26200d);
        if (!this.f26198b.isCanceled()) {
            return responseProceed;
        }
        Util.closeQuietly(responseProceed);
        throw new IOException("Canceled");
    }

    @Override // okhttp3.Call
    public void cancel() {
        this.f26198b.cancel();
    }

    String d() {
        return this.f26200d.url().redact();
    }

    StreamAllocation e() {
        return this.f26198b.streamAllocation();
    }

    @Override // okhttp3.Call
    public void enqueue(Callback callback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        captureCallStackTrace();
        this.eventListener.callStart(this);
        this.f26197a.dispatcher().a(new AsyncCall(callback));
    }

    @Override // okhttp3.Call
    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        captureCallStackTrace();
        this.f26199c.enter();
        this.eventListener.callStart(this);
        try {
            try {
                this.f26197a.dispatcher().b(this);
                Response responseB = b();
                if (responseB != null) {
                    return responseB;
                }
                throw new IOException("Canceled");
            } catch (IOException e2) {
                IOException iOExceptionF = f(e2);
                this.eventListener.callFailed(this, iOExceptionF);
                throw iOExceptionF;
            }
        } finally {
            this.f26197a.dispatcher().d(this);
        }
    }

    IOException f(IOException iOException) {
        if (!this.f26199c.exit()) {
            return iOException;
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    String g() {
        StringBuilder sb = new StringBuilder();
        sb.append(isCanceled() ? "canceled " : "");
        sb.append(this.f26201e ? "web socket" : NotificationCompat.CATEGORY_CALL);
        sb.append(" to ");
        sb.append(d());
        return sb.toString();
    }

    @Override // okhttp3.Call
    public boolean isCanceled() {
        return this.f26198b.isCanceled();
    }

    @Override // okhttp3.Call
    public synchronized boolean isExecuted() {
        return this.executed;
    }

    @Override // okhttp3.Call
    public Request request() {
        return this.f26200d;
    }

    @Override // okhttp3.Call
    public Timeout timeout() {
        return this.f26199c;
    }

    @Override // okhttp3.Call
    public RealCall clone() {
        return c(this.f26197a, this.f26200d, this.f26201e);
    }
}
