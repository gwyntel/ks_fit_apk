package retrofit;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/* loaded from: classes5.dex */
final class OkHttpCall<T> implements Call<T> {
    private final Object[] args;
    private volatile boolean canceled;
    private boolean executed;
    private volatile com.squareup.okhttp.Call rawCall;
    private final RequestFactory requestFactory;
    private final Converter<ResponseBody, T> responseConverter;

    /* renamed from: retrofit, reason: collision with root package name */
    private final Retrofit f26824retrofit;

    static final class ExceptionCatchingRequestBody extends ResponseBody {
        private final ResponseBody delegate;
        private IOException thrownException;

        ExceptionCatchingRequestBody(ResponseBody responseBody) {
            this.delegate = responseBody;
        }

        @Override // com.squareup.okhttp.ResponseBody, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.delegate.close();
        }

        @Override // com.squareup.okhttp.ResponseBody
        public long contentLength() throws IOException {
            try {
                return this.delegate.contentLength();
            } catch (IOException e2) {
                this.thrownException = e2;
                throw e2;
            }
        }

        @Override // com.squareup.okhttp.ResponseBody
        public MediaType contentType() {
            return this.delegate.contentType();
        }

        void e() throws IOException {
            IOException iOException = this.thrownException;
            if (iOException != null) {
                throw iOException;
            }
        }

        @Override // com.squareup.okhttp.ResponseBody
        public BufferedSource source() throws IOException {
            try {
                return Okio.buffer(new ForwardingSource(this.delegate.source()) { // from class: retrofit.OkHttpCall.ExceptionCatchingRequestBody.1
                    @Override // okio.ForwardingSource, okio.Source
                    public long read(Buffer buffer, long j2) throws IOException {
                        try {
                            return super.read(buffer, j2);
                        } catch (IOException e2) {
                            ExceptionCatchingRequestBody.this.thrownException = e2;
                            throw e2;
                        }
                    }
                });
            } catch (IOException e2) {
                this.thrownException = e2;
                throw e2;
            }
        }
    }

    static final class NoContentResponseBody extends ResponseBody {
        private final long contentLength;
        private final MediaType contentType;

        NoContentResponseBody(MediaType mediaType, long j2) {
            this.contentType = mediaType;
            this.contentLength = j2;
        }

        @Override // com.squareup.okhttp.ResponseBody
        public long contentLength() throws IOException {
            return this.contentLength;
        }

        @Override // com.squareup.okhttp.ResponseBody
        public MediaType contentType() {
            return this.contentType;
        }

        @Override // com.squareup.okhttp.ResponseBody
        public BufferedSource source() throws IOException {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

    OkHttpCall(Retrofit retrofit3, RequestFactory requestFactory, Converter converter, Object[] objArr) {
        this.f26824retrofit = retrofit3;
        this.requestFactory = requestFactory;
        this.responseConverter = converter;
        this.args = objArr;
    }

    private com.squareup.okhttp.Call createRawCall() {
        return this.f26824retrofit.client().newCall(this.requestFactory.a(this.args));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Response<T> parseResponse(com.squareup.okhttp.Response response) throws IOException {
        ResponseBody responseBodyBody = response.body();
        com.squareup.okhttp.Response responseBuild = response.newBuilder().body(new NoContentResponseBody(responseBodyBody.contentType(), responseBodyBody.contentLength())).build();
        int iCode = responseBuild.code();
        if (iCode < 200 || iCode >= 300) {
            try {
                return Response.error(Utils.g(responseBodyBody), responseBuild);
            } finally {
                Utils.b(responseBodyBody);
            }
        }
        if (iCode == 204 || iCode == 205) {
            return Response.success(null, responseBuild);
        }
        ExceptionCatchingRequestBody exceptionCatchingRequestBody = new ExceptionCatchingRequestBody(responseBodyBody);
        try {
            return Response.success(this.responseConverter.convert(exceptionCatchingRequestBody), responseBuild);
        } catch (RuntimeException e2) {
            exceptionCatchingRequestBody.e();
            throw e2;
        }
    }

    @Override // retrofit.Call
    public void cancel() {
        this.canceled = true;
        com.squareup.okhttp.Call call = this.rawCall;
        if (call != null) {
            call.cancel();
        }
    }

    @Override // retrofit.Call
    public void enqueue(final Callback<T> callback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed");
            }
            this.executed = true;
        }
        try {
            com.squareup.okhttp.Call callCreateRawCall = createRawCall();
            if (this.canceled) {
                callCreateRawCall.cancel();
            }
            this.rawCall = callCreateRawCall;
            callCreateRawCall.enqueue(new com.squareup.okhttp.Callback() { // from class: retrofit.OkHttpCall.1
                private void callFailure(Throwable th) {
                    try {
                        callback.onFailure(th);
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                    }
                }

                private void callSuccess(Response<T> response) {
                    try {
                        callback.onResponse(response, OkHttpCall.this.f26824retrofit);
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override // com.squareup.okhttp.Callback
                public void onFailure(Request request, IOException iOException) {
                    callFailure(iOException);
                }

                @Override // com.squareup.okhttp.Callback
                public void onResponse(com.squareup.okhttp.Response response) {
                    try {
                        callSuccess(OkHttpCall.this.parseResponse(response));
                    } catch (Throwable th) {
                        callFailure(th);
                    }
                }
            });
        } catch (Throwable th) {
            callback.onFailure(th);
        }
    }

    @Override // retrofit.Call
    public Response<T> execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed");
            }
            this.executed = true;
        }
        com.squareup.okhttp.Call callCreateRawCall = createRawCall();
        if (this.canceled) {
            callCreateRawCall.cancel();
        }
        this.rawCall = callCreateRawCall;
        return parseResponse(callCreateRawCall.execute());
    }

    @Override // retrofit.Call
    public OkHttpCall<T> clone() {
        return new OkHttpCall<>(this.f26824retrofit, this.requestFactory, this.responseConverter, this.args);
    }
}
