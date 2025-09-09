package okhttp3;

import com.huawei.hms.feature.dynamic.f.e;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/* loaded from: classes5.dex */
public final class Cache implements Closeable, Flushable {
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;

    /* renamed from: a, reason: collision with root package name */
    final InternalCache f26078a;

    /* renamed from: b, reason: collision with root package name */
    final DiskLruCache f26079b;

    /* renamed from: c, reason: collision with root package name */
    int f26080c;

    /* renamed from: d, reason: collision with root package name */
    int f26081d;
    private int hitCount;
    private int networkCount;
    private int requestCount;

    private final class CacheRequestImpl implements CacheRequest {

        /* renamed from: a, reason: collision with root package name */
        boolean f26087a;
        private Sink body;
        private Sink cacheOut;
        private final DiskLruCache.Editor editor;

        CacheRequestImpl(final DiskLruCache.Editor editor) {
            this.editor = editor;
            Sink sinkNewSink = editor.newSink(1);
            this.cacheOut = sinkNewSink;
            this.body = new ForwardingSink(sinkNewSink) { // from class: okhttp3.Cache.CacheRequestImpl.1
                @Override // okio.ForwardingSink, okio.Sink, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    synchronized (Cache.this) {
                        try {
                            CacheRequestImpl cacheRequestImpl = CacheRequestImpl.this;
                            if (cacheRequestImpl.f26087a) {
                                return;
                            }
                            cacheRequestImpl.f26087a = true;
                            Cache.this.f26080c++;
                            super.close();
                            editor.commit();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
        }

        @Override // okhttp3.internal.cache.CacheRequest
        public void abort() {
            synchronized (Cache.this) {
                try {
                    if (this.f26087a) {
                        return;
                    }
                    this.f26087a = true;
                    Cache.this.f26081d++;
                    Util.closeQuietly(this.cacheOut);
                    try {
                        this.editor.abort();
                    } catch (IOException unused) {
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // okhttp3.internal.cache.CacheRequest
        public Sink body() {
            return this.body;
        }
    }

    private static class CacheResponseBody extends ResponseBody {

        /* renamed from: a, reason: collision with root package name */
        final DiskLruCache.Snapshot f26092a;
        private final BufferedSource bodySource;

        @Nullable
        private final String contentLength;

        @Nullable
        private final String contentType;

        CacheResponseBody(final DiskLruCache.Snapshot snapshot, String str, String str2) {
            this.f26092a = snapshot;
            this.contentType = str;
            this.contentLength = str2;
            this.bodySource = Okio.buffer(new ForwardingSource(snapshot.getSource(1)) { // from class: okhttp3.Cache.CacheResponseBody.1
                @Override // okio.ForwardingSource, okio.Source, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    snapshot.close();
                    super.close();
                }
            });
        }

        @Override // okhttp3.ResponseBody
        public long contentLength() {
            try {
                String str = this.contentLength;
                if (str != null) {
                    return Long.parseLong(str);
                }
                return -1L;
            } catch (NumberFormatException unused) {
                return -1L;
            }
        }

        @Override // okhttp3.ResponseBody
        public MediaType contentType() {
            String str = this.contentType;
            if (str != null) {
                return MediaType.parse(str);
            }
            return null;
        }

        @Override // okhttp3.ResponseBody
        public BufferedSource source() {
            return this.bodySource;
        }
    }

    public Cache(File file, long j2) {
        this(file, j2, FileSystem.SYSTEM);
    }

    private void abortQuietly(@Nullable DiskLruCache.Editor editor) {
        if (editor != null) {
            try {
                editor.abort();
            } catch (IOException unused) {
            }
        }
    }

    static int c(BufferedSource bufferedSource) throws IOException {
        try {
            long decimalLong = bufferedSource.readDecimalLong();
            String utf8LineStrict = bufferedSource.readUtf8LineStrict();
            if (decimalLong >= 0 && decimalLong <= 2147483647L && utf8LineStrict.isEmpty()) {
                return (int) decimalLong;
            }
            throw new IOException("expected an int but was \"" + decimalLong + utf8LineStrict + "\"");
        } catch (NumberFormatException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public static String key(HttpUrl httpUrl) {
        return ByteString.encodeUtf8(httpUrl.toString()).md5().hex();
    }

    Response a(Request request) {
        try {
            DiskLruCache.Snapshot snapshot = this.f26079b.get(key(request.url()));
            if (snapshot == null) {
                return null;
            }
            try {
                Entry entry = new Entry(snapshot.getSource(0));
                Response response = entry.response(snapshot);
                if (entry.matches(request, response)) {
                    return response;
                }
                Util.closeQuietly(response.body());
                return null;
            } catch (IOException unused) {
                Util.closeQuietly(snapshot);
                return null;
            }
        } catch (IOException unused2) {
        }
    }

    CacheRequest b(Response response) {
        DiskLruCache.Editor editorEdit;
        String strMethod = response.request().method();
        if (HttpMethod.invalidatesCache(response.request().method())) {
            try {
                d(response.request());
            } catch (IOException unused) {
            }
            return null;
        }
        if (!strMethod.equals("GET") || HttpHeaders.hasVaryAll(response)) {
            return null;
        }
        Entry entry = new Entry(response);
        try {
            editorEdit = this.f26079b.edit(key(response.request().url()));
            if (editorEdit == null) {
                return null;
            }
            try {
                entry.writeTo(editorEdit);
                return new CacheRequestImpl(editorEdit);
            } catch (IOException unused2) {
                abortQuietly(editorEdit);
                return null;
            }
        } catch (IOException unused3) {
            editorEdit = null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.f26079b.close();
    }

    void d(Request request) throws IOException {
        this.f26079b.remove(key(request.url()));
    }

    public void delete() throws IOException {
        this.f26079b.delete();
    }

    public File directory() {
        return this.f26079b.getDirectory();
    }

    synchronized void e() {
        this.hitCount++;
    }

    public void evictAll() throws IOException {
        this.f26079b.evictAll();
    }

    synchronized void f(CacheStrategy cacheStrategy) {
        try {
            this.requestCount++;
            if (cacheStrategy.networkRequest != null) {
                this.networkCount++;
            } else if (cacheStrategy.cacheResponse != null) {
                this.hitCount++;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        this.f26079b.flush();
    }

    void g(Response response, Response response2) {
        DiskLruCache.Editor editorEdit;
        Entry entry = new Entry(response2);
        try {
            editorEdit = ((CacheResponseBody) response.body()).f26092a.edit();
            if (editorEdit != null) {
                try {
                    entry.writeTo(editorEdit);
                    editorEdit.commit();
                } catch (IOException unused) {
                    abortQuietly(editorEdit);
                }
            }
        } catch (IOException unused2) {
            editorEdit = null;
        }
    }

    public synchronized int hitCount() {
        return this.hitCount;
    }

    public void initialize() throws IOException {
        this.f26079b.initialize();
    }

    public boolean isClosed() {
        return this.f26079b.isClosed();
    }

    public long maxSize() {
        return this.f26079b.getMaxSize();
    }

    public synchronized int networkCount() {
        return this.networkCount;
    }

    public synchronized int requestCount() {
        return this.requestCount;
    }

    public long size() throws IOException {
        return this.f26079b.size();
    }

    public Iterator<String> urls() throws IOException {
        return new Iterator<String>() { // from class: okhttp3.Cache.2

            /* renamed from: a, reason: collision with root package name */
            final Iterator f26083a;

            /* renamed from: b, reason: collision with root package name */
            String f26084b;

            /* renamed from: c, reason: collision with root package name */
            boolean f26085c;

            {
                this.f26083a = Cache.this.f26079b.snapshots();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.f26084b != null) {
                    return true;
                }
                this.f26085c = false;
                while (this.f26083a.hasNext()) {
                    DiskLruCache.Snapshot snapshot = (DiskLruCache.Snapshot) this.f26083a.next();
                    try {
                        this.f26084b = Okio.buffer(snapshot.getSource(0)).readUtf8LineStrict();
                        return true;
                    } catch (IOException unused) {
                    } finally {
                        snapshot.close();
                    }
                }
                return false;
            }

            @Override // java.util.Iterator
            public void remove() {
                if (!this.f26085c) {
                    throw new IllegalStateException("remove() before next()");
                }
                this.f26083a.remove();
            }

            @Override // java.util.Iterator
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String str = this.f26084b;
                this.f26084b = null;
                this.f26085c = true;
                return str;
            }
        };
    }

    public synchronized int writeAbortCount() {
        return this.f26081d;
    }

    public synchronized int writeSuccessCount() {
        return this.f26080c;
    }

    Cache(File file, long j2, FileSystem fileSystem) {
        this.f26078a = new InternalCache() { // from class: okhttp3.Cache.1
            @Override // okhttp3.internal.cache.InternalCache
            public Response get(Request request) throws IOException {
                return Cache.this.a(request);
            }

            @Override // okhttp3.internal.cache.InternalCache
            public CacheRequest put(Response response) throws IOException {
                return Cache.this.b(response);
            }

            @Override // okhttp3.internal.cache.InternalCache
            public void remove(Request request) throws IOException {
                Cache.this.d(request);
            }

            @Override // okhttp3.internal.cache.InternalCache
            public void trackConditionalCacheHit() {
                Cache.this.e();
            }

            @Override // okhttp3.internal.cache.InternalCache
            public void trackResponse(CacheStrategy cacheStrategy) {
                Cache.this.f(cacheStrategy);
            }

            @Override // okhttp3.internal.cache.InternalCache
            public void update(Response response, Response response2) {
                Cache.this.g(response, response2);
            }
        };
        this.f26079b = DiskLruCache.create(fileSystem, file, VERSION, 2, j2);
    }

    private static final class Entry {
        private final int code;

        @Nullable
        private final Handshake handshake;
        private final String message;
        private final Protocol protocol;
        private final long receivedResponseMillis;
        private final String requestMethod;
        private final Headers responseHeaders;
        private final long sentRequestMillis;
        private final String url;
        private final Headers varyHeaders;
        private static final String SENT_MILLIS = Platform.get().getPrefix() + "-Sent-Millis";
        private static final String RECEIVED_MILLIS = Platform.get().getPrefix() + "-Received-Millis";

        Entry(Source source) throws IOException {
            try {
                BufferedSource bufferedSourceBuffer = Okio.buffer(source);
                this.url = bufferedSourceBuffer.readUtf8LineStrict();
                this.requestMethod = bufferedSourceBuffer.readUtf8LineStrict();
                Headers.Builder builder = new Headers.Builder();
                int iC = Cache.c(bufferedSourceBuffer);
                for (int i2 = 0; i2 < iC; i2++) {
                    builder.a(bufferedSourceBuffer.readUtf8LineStrict());
                }
                this.varyHeaders = builder.build();
                StatusLine statusLine = StatusLine.parse(bufferedSourceBuffer.readUtf8LineStrict());
                this.protocol = statusLine.protocol;
                this.code = statusLine.code;
                this.message = statusLine.message;
                Headers.Builder builder2 = new Headers.Builder();
                int iC2 = Cache.c(bufferedSourceBuffer);
                for (int i3 = 0; i3 < iC2; i3++) {
                    builder2.a(bufferedSourceBuffer.readUtf8LineStrict());
                }
                String str = SENT_MILLIS;
                String str2 = builder2.get(str);
                String str3 = RECEIVED_MILLIS;
                String str4 = builder2.get(str3);
                builder2.removeAll(str);
                builder2.removeAll(str3);
                this.sentRequestMillis = str2 != null ? Long.parseLong(str2) : 0L;
                this.receivedResponseMillis = str4 != null ? Long.parseLong(str4) : 0L;
                this.responseHeaders = builder2.build();
                if (isHttps()) {
                    String utf8LineStrict = bufferedSourceBuffer.readUtf8LineStrict();
                    if (utf8LineStrict.length() > 0) {
                        throw new IOException("expected \"\" but was \"" + utf8LineStrict + "\"");
                    }
                    this.handshake = Handshake.get(!bufferedSourceBuffer.exhausted() ? TlsVersion.forJavaName(bufferedSourceBuffer.readUtf8LineStrict()) : TlsVersion.SSL_3_0, CipherSuite.forJavaName(bufferedSourceBuffer.readUtf8LineStrict()), readCertificateList(bufferedSourceBuffer), readCertificateList(bufferedSourceBuffer));
                } else {
                    this.handshake = null;
                }
                source.close();
            } catch (Throwable th) {
                source.close();
                throw th;
            }
        }

        private boolean isHttps() {
            return this.url.startsWith("https://");
        }

        private List<Certificate> readCertificateList(BufferedSource bufferedSource) throws IOException, CertificateException {
            int iC = Cache.c(bufferedSource);
            if (iC == -1) {
                return Collections.emptyList();
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance(e.f16169b);
                ArrayList arrayList = new ArrayList(iC);
                for (int i2 = 0; i2 < iC; i2++) {
                    String utf8LineStrict = bufferedSource.readUtf8LineStrict();
                    Buffer buffer = new Buffer();
                    buffer.write(ByteString.decodeBase64(utf8LineStrict));
                    arrayList.add(certificateFactory.generateCertificate(buffer.inputStream()));
                }
                return arrayList;
            } catch (CertificateException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        private void writeCertList(BufferedSink bufferedSink, List<Certificate> list) throws IOException {
            try {
                bufferedSink.writeDecimalLong(list.size()).writeByte(10);
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    bufferedSink.writeUtf8(ByteString.of(list.get(i2).getEncoded()).base64()).writeByte(10);
                }
            } catch (CertificateEncodingException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        public boolean matches(Request request, Response response) {
            return this.url.equals(request.url().toString()) && this.requestMethod.equals(request.method()) && HttpHeaders.varyMatches(response, this.varyHeaders, request);
        }

        public Response response(DiskLruCache.Snapshot snapshot) {
            String str = this.responseHeaders.get("Content-Type");
            String str2 = this.responseHeaders.get("Content-Length");
            return new Response.Builder().request(new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build()).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, str, str2)).handshake(this.handshake).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(this.receivedResponseMillis).build();
        }

        public void writeTo(DiskLruCache.Editor editor) throws IOException {
            BufferedSink bufferedSinkBuffer = Okio.buffer(editor.newSink(0));
            bufferedSinkBuffer.writeUtf8(this.url).writeByte(10);
            bufferedSinkBuffer.writeUtf8(this.requestMethod).writeByte(10);
            bufferedSinkBuffer.writeDecimalLong(this.varyHeaders.size()).writeByte(10);
            int size = this.varyHeaders.size();
            for (int i2 = 0; i2 < size; i2++) {
                bufferedSinkBuffer.writeUtf8(this.varyHeaders.name(i2)).writeUtf8(": ").writeUtf8(this.varyHeaders.value(i2)).writeByte(10);
            }
            bufferedSinkBuffer.writeUtf8(new StatusLine(this.protocol, this.code, this.message).toString()).writeByte(10);
            bufferedSinkBuffer.writeDecimalLong(this.responseHeaders.size() + 2).writeByte(10);
            int size2 = this.responseHeaders.size();
            for (int i3 = 0; i3 < size2; i3++) {
                bufferedSinkBuffer.writeUtf8(this.responseHeaders.name(i3)).writeUtf8(": ").writeUtf8(this.responseHeaders.value(i3)).writeByte(10);
            }
            bufferedSinkBuffer.writeUtf8(SENT_MILLIS).writeUtf8(": ").writeDecimalLong(this.sentRequestMillis).writeByte(10);
            bufferedSinkBuffer.writeUtf8(RECEIVED_MILLIS).writeUtf8(": ").writeDecimalLong(this.receivedResponseMillis).writeByte(10);
            if (isHttps()) {
                bufferedSinkBuffer.writeByte(10);
                bufferedSinkBuffer.writeUtf8(this.handshake.cipherSuite().javaName()).writeByte(10);
                writeCertList(bufferedSinkBuffer, this.handshake.peerCertificates());
                writeCertList(bufferedSinkBuffer, this.handshake.localCertificates());
                bufferedSinkBuffer.writeUtf8(this.handshake.tlsVersion().javaName()).writeByte(10);
            }
            bufferedSinkBuffer.close();
        }

        Entry(Response response) {
            this.url = response.request().url().toString();
            this.varyHeaders = HttpHeaders.varyHeaders(response);
            this.requestMethod = response.request().method();
            this.protocol = response.protocol();
            this.code = response.code();
            this.message = response.message();
            this.responseHeaders = response.headers();
            this.handshake = response.handshake();
            this.sentRequestMillis = response.sentRequestAtMillis();
            this.receivedResponseMillis = response.receivedResponseAtMillis();
        }
    }
}
