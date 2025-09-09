package okhttp3.internal.cache2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/* loaded from: classes5.dex */
final class Relay {
    private static final long FILE_HEADER_SIZE = 32;
    private static final int SOURCE_FILE = 2;
    private static final int SOURCE_UPSTREAM = 1;

    /* renamed from: j, reason: collision with root package name */
    static final ByteString f26295j = ByteString.encodeUtf8("OkHttp cache v1\n");

    /* renamed from: k, reason: collision with root package name */
    static final ByteString f26296k = ByteString.encodeUtf8("OkHttp DIRTY :(\n");

    /* renamed from: a, reason: collision with root package name */
    RandomAccessFile f26297a;

    /* renamed from: b, reason: collision with root package name */
    Thread f26298b;

    /* renamed from: c, reason: collision with root package name */
    Source f26299c;

    /* renamed from: e, reason: collision with root package name */
    long f26301e;

    /* renamed from: f, reason: collision with root package name */
    boolean f26302f;

    /* renamed from: h, reason: collision with root package name */
    final long f26304h;

    /* renamed from: i, reason: collision with root package name */
    int f26305i;
    private final ByteString metadata;

    /* renamed from: d, reason: collision with root package name */
    final Buffer f26300d = new Buffer();

    /* renamed from: g, reason: collision with root package name */
    final Buffer f26303g = new Buffer();

    class RelaySource implements Source {
        private FileOperator fileOperator;
        private long sourcePos;
        private final Timeout timeout = new Timeout();

        RelaySource() {
            this.fileOperator = new FileOperator(Relay.this.f26297a.getChannel());
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.fileOperator == null) {
                return;
            }
            RandomAccessFile randomAccessFile = null;
            this.fileOperator = null;
            synchronized (Relay.this) {
                try {
                    Relay relay = Relay.this;
                    int i2 = relay.f26305i - 1;
                    relay.f26305i = i2;
                    if (i2 == 0) {
                        RandomAccessFile randomAccessFile2 = relay.f26297a;
                        relay.f26297a = null;
                        randomAccessFile = randomAccessFile2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (randomAccessFile != null) {
                Util.closeQuietly(randomAccessFile);
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            Relay relay;
            if (this.fileOperator == null) {
                throw new IllegalStateException("closed");
            }
            synchronized (Relay.this) {
                while (true) {
                    try {
                        long j3 = this.sourcePos;
                        Relay relay2 = Relay.this;
                        long j4 = relay2.f26301e;
                        if (j3 != j4) {
                            long size = j4 - relay2.f26303g.size();
                            long j5 = this.sourcePos;
                            if (j5 < size) {
                                long jMin = Math.min(j2, j4 - j5);
                                this.fileOperator.read(this.sourcePos + 32, buffer, jMin);
                                this.sourcePos += jMin;
                                return jMin;
                            }
                            long jMin2 = Math.min(j2, j4 - j5);
                            Relay.this.f26303g.copyTo(buffer, this.sourcePos - size, jMin2);
                            this.sourcePos += jMin2;
                            return jMin2;
                        }
                        if (relay2.f26302f) {
                            return -1L;
                        }
                        if (relay2.f26298b == null) {
                            relay2.f26298b = Thread.currentThread();
                            try {
                                Relay relay3 = Relay.this;
                                long j6 = relay3.f26299c.read(relay3.f26300d, relay3.f26304h);
                                if (j6 == -1) {
                                    Relay.this.a(j4);
                                    synchronized (Relay.this) {
                                        Relay relay4 = Relay.this;
                                        relay4.f26298b = null;
                                        relay4.notifyAll();
                                    }
                                    return -1L;
                                }
                                long jMin3 = Math.min(j6, j2);
                                Relay.this.f26300d.copyTo(buffer, 0L, jMin3);
                                this.sourcePos += jMin3;
                                this.fileOperator.write(j4 + 32, Relay.this.f26300d.clone(), j6);
                                synchronized (Relay.this) {
                                    try {
                                        Relay relay5 = Relay.this;
                                        relay5.f26303g.write(relay5.f26300d, j6);
                                        long size2 = Relay.this.f26303g.size();
                                        Relay relay6 = Relay.this;
                                        if (size2 > relay6.f26304h) {
                                            Buffer buffer2 = relay6.f26303g;
                                            buffer2.skip(buffer2.size() - Relay.this.f26304h);
                                        }
                                        relay = Relay.this;
                                        relay.f26301e += j6;
                                    } finally {
                                    }
                                }
                                synchronized (relay) {
                                    Relay relay7 = Relay.this;
                                    relay7.f26298b = null;
                                    relay7.notifyAll();
                                }
                                return jMin3;
                            } catch (Throwable th) {
                                synchronized (Relay.this) {
                                    Relay relay8 = Relay.this;
                                    relay8.f26298b = null;
                                    relay8.notifyAll();
                                    throw th;
                                }
                            }
                        }
                        this.timeout.waitUntilNotified(relay2);
                    } finally {
                    }
                }
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.timeout;
        }
    }

    private Relay(RandomAccessFile randomAccessFile, Source source, long j2, ByteString byteString, long j3) {
        this.f26297a = randomAccessFile;
        this.f26299c = source;
        this.f26302f = source == null;
        this.f26301e = j2;
        this.metadata = byteString;
        this.f26304h = j3;
    }

    public static Relay edit(File file, Source source, ByteString byteString, long j2) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        Relay relay = new Relay(randomAccessFile, source, 0L, byteString, j2);
        randomAccessFile.setLength(0L);
        relay.writeHeader(f26296k, -1L, -1L);
        return relay;
    }

    public static Relay read(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileOperator fileOperator = new FileOperator(randomAccessFile.getChannel());
        Buffer buffer = new Buffer();
        fileOperator.read(0L, buffer, 32L);
        if (!buffer.readByteString(r2.size()).equals(f26295j)) {
            throw new IOException("unreadable cache file");
        }
        long j2 = buffer.readLong();
        long j3 = buffer.readLong();
        Buffer buffer2 = new Buffer();
        fileOperator.read(j2 + 32, buffer2, j3);
        return new Relay(randomAccessFile, null, j2, buffer2.readByteString(), 0L);
    }

    private void writeHeader(ByteString byteString, long j2, long j3) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(byteString);
        buffer.writeLong(j2);
        buffer.writeLong(j3);
        if (buffer.size() != 32) {
            throw new IllegalArgumentException();
        }
        new FileOperator(this.f26297a.getChannel()).write(0L, buffer, 32L);
    }

    private void writeMetadata(long j2) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(this.metadata);
        new FileOperator(this.f26297a.getChannel()).write(32 + j2, buffer, this.metadata.size());
    }

    void a(long j2) throws IOException {
        writeMetadata(j2);
        this.f26297a.getChannel().force(false);
        writeHeader(f26295j, j2, this.metadata.size());
        this.f26297a.getChannel().force(false);
        synchronized (this) {
            this.f26302f = true;
        }
        Util.closeQuietly(this.f26299c);
        this.f26299c = null;
    }

    public ByteString metadata() {
        return this.metadata;
    }

    public Source newSource() {
        synchronized (this) {
            try {
                if (this.f26297a == null) {
                    return null;
                }
                this.f26305i++;
                return new RelaySource();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
