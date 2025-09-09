package okio;

import java.io.IOException;

/* loaded from: classes5.dex */
public final class Pipe {

    /* renamed from: a, reason: collision with root package name */
    final long f26481a;

    /* renamed from: c, reason: collision with root package name */
    boolean f26483c;

    /* renamed from: d, reason: collision with root package name */
    boolean f26484d;

    /* renamed from: b, reason: collision with root package name */
    final Buffer f26482b = new Buffer();
    private final Sink sink = new PipeSink();
    private final Source source = new PipeSource();

    final class PipeSink implements Sink {

        /* renamed from: a, reason: collision with root package name */
        final Timeout f26485a = new Timeout();

        PipeSink() {
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            synchronized (Pipe.this.f26482b) {
                try {
                    Pipe pipe = Pipe.this;
                    if (pipe.f26483c) {
                        return;
                    }
                    if (pipe.f26484d && pipe.f26482b.size() > 0) {
                        throw new IOException("source is closed");
                    }
                    Pipe pipe2 = Pipe.this;
                    pipe2.f26483c = true;
                    pipe2.f26482b.notifyAll();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            synchronized (Pipe.this.f26482b) {
                try {
                    Pipe pipe = Pipe.this;
                    if (pipe.f26483c) {
                        throw new IllegalStateException("closed");
                    }
                    if (pipe.f26484d && pipe.f26482b.size() > 0) {
                        throw new IOException("source is closed");
                    }
                } finally {
                }
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.f26485a;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            synchronized (Pipe.this.f26482b) {
                try {
                    if (Pipe.this.f26483c) {
                        throw new IllegalStateException("closed");
                    }
                    while (j2 > 0) {
                        Pipe pipe = Pipe.this;
                        if (pipe.f26484d) {
                            throw new IOException("source is closed");
                        }
                        long size = pipe.f26481a - pipe.f26482b.size();
                        if (size == 0) {
                            this.f26485a.waitUntilNotified(Pipe.this.f26482b);
                        } else {
                            long jMin = Math.min(size, j2);
                            Pipe.this.f26482b.write(buffer, jMin);
                            j2 -= jMin;
                            Pipe.this.f26482b.notifyAll();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class PipeSource implements Source {

        /* renamed from: a, reason: collision with root package name */
        final Timeout f26487a = new Timeout();

        PipeSource() {
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            synchronized (Pipe.this.f26482b) {
                Pipe pipe = Pipe.this;
                pipe.f26484d = true;
                pipe.f26482b.notifyAll();
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            synchronized (Pipe.this.f26482b) {
                try {
                    if (Pipe.this.f26484d) {
                        throw new IllegalStateException("closed");
                    }
                    while (Pipe.this.f26482b.size() == 0) {
                        Pipe pipe = Pipe.this;
                        if (pipe.f26483c) {
                            return -1L;
                        }
                        this.f26487a.waitUntilNotified(pipe.f26482b);
                    }
                    long j3 = Pipe.this.f26482b.read(buffer, j2);
                    Pipe.this.f26482b.notifyAll();
                    return j3;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.f26487a;
        }
    }

    public Pipe(long j2) {
        if (j2 >= 1) {
            this.f26481a = j2;
            return;
        }
        throw new IllegalArgumentException("maxBufferSize < 1: " + j2);
    }

    public final Sink sink() {
        return this.sink;
    }

    public final Source source() {
        return this.source;
    }
}
