package com.squareup.okhttp;

import androidx.core.app.NotificationCompat;
import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.RequestException;
import com.squareup.okhttp.internal.http.RouteException;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.logging.Level;

/* loaded from: classes4.dex */
public class Call {

    /* renamed from: a, reason: collision with root package name */
    volatile boolean f19878a;

    /* renamed from: b, reason: collision with root package name */
    Request f19879b;

    /* renamed from: c, reason: collision with root package name */
    HttpEngine f19880c;
    private final OkHttpClient client;
    private boolean executed;

    class ApplicationInterceptorChain implements Interceptor.Chain {
        private final boolean forWebSocket;
        private final int index;
        private final Request request;

        ApplicationInterceptorChain(int i2, Request request, boolean z2) {
            this.index = i2;
            this.request = request;
            this.forWebSocket = z2;
        }

        @Override // com.squareup.okhttp.Interceptor.Chain
        public Connection connection() {
            return null;
        }

        @Override // com.squareup.okhttp.Interceptor.Chain
        public Response proceed(Request request) throws IOException {
            if (this.index >= Call.this.client.interceptors().size()) {
                return Call.this.e(request, this.forWebSocket);
            }
            return Call.this.client.interceptors().get(this.index).intercept(Call.this.new ApplicationInterceptorChain(this.index + 1, request, this.forWebSocket));
        }

        @Override // com.squareup.okhttp.Interceptor.Chain
        public Request request() {
            return this.request;
        }
    }

    final class AsyncCall extends NamedRunnable {
        private final boolean forWebSocket;
        private final Callback responseCallback;

        void a() {
            Call.this.cancel();
        }

        Call b() {
            return Call.this;
        }

        String c() {
            return Call.this.f19879b.httpUrl().host();
        }

        Object d() {
            return Call.this.f19879b.tag();
        }

        @Override // com.squareup.okhttp.internal.NamedRunnable
        protected void execute() {
            boolean z2;
            IOException e2;
            try {
                try {
                    Response responseWithInterceptorChain = Call.this.getResponseWithInterceptorChain(this.forWebSocket);
                    z2 = true;
                    try {
                        if (Call.this.f19878a) {
                            this.responseCallback.onFailure(Call.this.f19879b, new IOException("Canceled"));
                        } else {
                            this.responseCallback.onResponse(responseWithInterceptorChain);
                        }
                    } catch (IOException e3) {
                        e2 = e3;
                        if (z2) {
                            Internal.logger.log(Level.INFO, "Callback failure for " + Call.this.toLoggableString(), (Throwable) e2);
                        } else {
                            this.responseCallback.onFailure(Call.this.f19880c.getRequest(), e2);
                        }
                        Call.this.client.getDispatcher().c(this);
                    }
                } catch (IOException e4) {
                    z2 = false;
                    e2 = e4;
                }
                Call.this.client.getDispatcher().c(this);
            } catch (Throwable th) {
                Call.this.client.getDispatcher().c(this);
                throw th;
            }
        }

        private AsyncCall(Callback callback, boolean z2) {
            super("OkHttp %s", Call.this.f19879b.urlString());
            this.responseCallback = callback;
            this.forWebSocket = z2;
        }
    }

    protected Call(OkHttpClient okHttpClient, Request request) {
        this.client = okHttpClient.c();
        this.f19879b = request;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Response getResponseWithInterceptorChain(boolean z2) throws IOException {
        return new ApplicationInterceptorChain(0, this.f19879b, z2).proceed(this.f19879b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String toLoggableString() {
        return (this.f19878a ? "canceled call" : NotificationCompat.CATEGORY_CALL) + " to " + this.f19879b.httpUrl().resolve("/...");
    }

    public void cancel() {
        this.f19878a = true;
        HttpEngine httpEngine = this.f19880c;
        if (httpEngine != null) {
            httpEngine.disconnect();
        }
    }

    void d(Callback callback, boolean z2) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.client.getDispatcher().a(new AsyncCall(callback, z2));
    }

    Response e(Request request, boolean z2) throws IOException {
        Response response;
        Request requestFollowUpRequest;
        RequestBody requestBodyBody = request.body();
        if (requestBodyBody != null) {
            Request.Builder builderNewBuilder = request.newBuilder();
            MediaType mediaTypeContentType = requestBodyBody.contentType();
            if (mediaTypeContentType != null) {
                builderNewBuilder.header("Content-Type", mediaTypeContentType.toString());
            }
            long jContentLength = requestBodyBody.contentLength();
            if (jContentLength != -1) {
                builderNewBuilder.header("Content-Length", Long.toString(jContentLength));
                builderNewBuilder.removeHeader(HttpHeaders.TRANSFER_ENCODING);
            } else {
                builderNewBuilder.header(HttpHeaders.TRANSFER_ENCODING, "chunked");
                builderNewBuilder.removeHeader("Content-Length");
            }
            request = builderNewBuilder.build();
        }
        this.f19880c = new HttpEngine(this.client, request, false, false, z2, null, null, null, null);
        int i2 = 0;
        while (!this.f19878a) {
            try {
                this.f19880c.sendRequest();
                this.f19880c.readResponse();
                response = this.f19880c.getResponse();
                requestFollowUpRequest = this.f19880c.followUpRequest();
            } catch (RequestException e2) {
                throw e2.getCause();
            } catch (RouteException e3) {
                HttpEngine httpEngineRecover = this.f19880c.recover(e3);
                if (httpEngineRecover == null) {
                    throw e3.getLastConnectException();
                }
                this.f19880c = httpEngineRecover;
            } catch (IOException e4) {
                HttpEngine httpEngineRecover2 = this.f19880c.recover(e4, null);
                if (httpEngineRecover2 == null) {
                    throw e4;
                }
                this.f19880c = httpEngineRecover2;
            }
            if (requestFollowUpRequest == null) {
                if (!z2) {
                    this.f19880c.releaseConnection();
                }
                return response;
            }
            i2++;
            if (i2 > 20) {
                throw new ProtocolException("Too many follow-up requests: " + i2);
            }
            if (!this.f19880c.sameConnection(requestFollowUpRequest.httpUrl())) {
                this.f19880c.releaseConnection();
            }
            this.f19880c = new HttpEngine(this.client, requestFollowUpRequest, false, false, z2, this.f19880c.close(), null, null, response);
        }
        this.f19880c.releaseConnection();
        throw new IOException("Canceled");
    }

    public void enqueue(Callback callback) {
        d(callback, false);
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        try {
            this.client.getDispatcher().b(this);
            Response responseWithInterceptorChain = getResponseWithInterceptorChain(false);
            if (responseWithInterceptorChain != null) {
                return responseWithInterceptorChain;
            }
            throw new IOException("Canceled");
        } finally {
            this.client.getDispatcher().d(this);
        }
    }

    Object f() {
        return this.f19879b.tag();
    }

    public boolean isCanceled() {
        return this.f19878a;
    }
}
