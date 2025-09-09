package com.squareup.okhttp.internal.http;

import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import okio.Okio;
import okio.Sink;
import okio.Source;

/* loaded from: classes4.dex */
public final class HttpTransport implements Transport {
    private final HttpConnection httpConnection;
    private final HttpEngine httpEngine;

    public HttpTransport(HttpEngine httpEngine, HttpConnection httpConnection) {
        this.httpEngine = httpEngine;
        this.httpConnection = httpConnection;
    }

    private Source getTransferStream(Response response) throws IOException {
        if (!HttpEngine.hasBody(response)) {
            return this.httpConnection.newFixedLengthSource(0L);
        }
        if ("chunked".equalsIgnoreCase(response.header(HttpHeaders.TRANSFER_ENCODING))) {
            return this.httpConnection.newChunkedSource(this.httpEngine);
        }
        long jContentLength = OkHeaders.contentLength(response);
        return jContentLength != -1 ? this.httpConnection.newFixedLengthSource(jContentLength) : this.httpConnection.newUnknownLengthSource();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public boolean canReuseConnection() {
        return ("close".equalsIgnoreCase(this.httpEngine.getRequest().header("Connection")) || "close".equalsIgnoreCase(this.httpEngine.getResponse().header("Connection")) || this.httpConnection.isClosed()) ? false : true;
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Sink createRequestBody(Request request, long j2) throws IOException {
        if ("chunked".equalsIgnoreCase(request.header(HttpHeaders.TRANSFER_ENCODING))) {
            return this.httpConnection.newChunkedSink();
        }
        if (j2 != -1) {
            return this.httpConnection.newFixedLengthSink(j2);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void disconnect(HttpEngine httpEngine) throws IOException {
        this.httpConnection.closeIfOwnedBy(httpEngine);
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void finishRequest() throws IOException {
        this.httpConnection.flush();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public ResponseBody openResponseBody(Response response) throws IOException {
        return new RealResponseBody(response.headers(), Okio.buffer(getTransferStream(response)));
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Response.Builder readResponseHeaders() throws IOException {
        return this.httpConnection.readResponse();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void releaseConnectionOnIdle() throws IOException {
        if (canReuseConnection()) {
            this.httpConnection.poolOnIdle();
        } else {
            this.httpConnection.closeOnIdle();
        }
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        this.httpConnection.writeRequestBody(retryableSink);
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void writeRequestHeaders(Request request) throws IOException {
        this.httpEngine.writingRequestHeaders();
        this.httpConnection.writeRequest(request.headers(), RequestLine.a(request, this.httpEngine.getConnection().getRoute().getProxy().type(), this.httpEngine.getConnection().getProtocol()));
    }
}
