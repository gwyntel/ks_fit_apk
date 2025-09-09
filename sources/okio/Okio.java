package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* loaded from: classes5.dex */
public final class Okio {

    /* renamed from: a, reason: collision with root package name */
    static final Logger f26473a = Logger.getLogger(Okio.class.getName());

    private Okio() {
    }

    static boolean a(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    public static Sink appendingSink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file, true));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Sink blackhole() {
        return new Sink() { // from class: okio.Okio.3
            @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
            }

            @Override // okio.Sink, java.io.Flushable
            public void flush() throws IOException {
            }

            @Override // okio.Sink
            public Timeout timeout() {
                return Timeout.NONE;
            }

            @Override // okio.Sink
            public void write(Buffer buffer, long j2) throws IOException {
                buffer.skip(j2);
            }
        };
    }

    public static BufferedSource buffer(Source source) {
        return new RealBufferedSource(source);
    }

    public static Sink sink(OutputStream outputStream) {
        return sink(outputStream, new Timeout());
    }

    public static Source source(InputStream inputStream) {
        return source(inputStream, new Timeout());
    }

    private static AsyncTimeout timeout(final Socket socket) {
        return new AsyncTimeout() { // from class: okio.Okio.4
            @Override // okio.AsyncTimeout
            protected IOException d(IOException iOException) {
                SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
                if (iOException != null) {
                    socketTimeoutException.initCause(iOException);
                }
                return socketTimeoutException;
            }

            @Override // okio.AsyncTimeout
            protected void e() throws IOException {
                try {
                    socket.close();
                } catch (AssertionError e2) {
                    if (!Okio.a(e2)) {
                        throw e2;
                    }
                    Okio.f26473a.log(Level.WARNING, "Failed to close timed out socket " + socket, (Throwable) e2);
                } catch (Exception e3) {
                    Okio.f26473a.log(Level.WARNING, "Failed to close timed out socket " + socket, (Throwable) e3);
                }
            }
        };
    }

    public static BufferedSink buffer(Sink sink) {
        return new RealBufferedSink(sink);
    }

    private static Sink sink(final OutputStream outputStream, final Timeout timeout) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        if (timeout != null) {
            return new Sink() { // from class: okio.Okio.1
                @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    outputStream.close();
                }

                @Override // okio.Sink, java.io.Flushable
                public void flush() throws IOException {
                    outputStream.flush();
                }

                @Override // okio.Sink
                public Timeout timeout() {
                    return timeout;
                }

                public String toString() {
                    return "sink(" + outputStream + ")";
                }

                @Override // okio.Sink
                public void write(Buffer buffer, long j2) throws IOException {
                    Util.checkOffsetAndCount(buffer.f26470b, 0L, j2);
                    while (j2 > 0) {
                        timeout.throwIfReached();
                        Segment segment = buffer.f26469a;
                        int iMin = (int) Math.min(j2, segment.f26495c - segment.f26494b);
                        outputStream.write(segment.f26493a, segment.f26494b, iMin);
                        int i2 = segment.f26494b + iMin;
                        segment.f26494b = i2;
                        long j3 = iMin;
                        j2 -= j3;
                        buffer.f26470b -= j3;
                        if (i2 == segment.f26495c) {
                            buffer.f26469a = segment.pop();
                            SegmentPool.a(segment);
                        }
                    }
                }
            };
        }
        throw new IllegalArgumentException("timeout == null");
    }

    private static Source source(final InputStream inputStream, final Timeout timeout) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (timeout != null) {
            return new Source() { // from class: okio.Okio.2
                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    inputStream.close();
                }

                @Override // okio.Source
                public long read(Buffer buffer, long j2) throws IOException {
                    if (j2 < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + j2);
                    }
                    if (j2 == 0) {
                        return 0L;
                    }
                    try {
                        timeout.throwIfReached();
                        Segment segmentD = buffer.d(1);
                        int i2 = inputStream.read(segmentD.f26493a, segmentD.f26495c, (int) Math.min(j2, 8192 - segmentD.f26495c));
                        if (i2 == -1) {
                            return -1L;
                        }
                        segmentD.f26495c += i2;
                        long j3 = i2;
                        buffer.f26470b += j3;
                        return j3;
                    } catch (AssertionError e2) {
                        if (Okio.a(e2)) {
                            throw new IOException(e2);
                        }
                        throw e2;
                    }
                }

                @Override // okio.Source
                public Timeout timeout() {
                    return timeout;
                }

                public String toString() {
                    return "source(" + inputStream + ")";
                }
            };
        }
        throw new IllegalArgumentException("timeout == null");
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket != null) {
            if (socket.getOutputStream() != null) {
                AsyncTimeout asyncTimeoutTimeout = timeout(socket);
                return asyncTimeoutTimeout.sink(sink(socket.getOutputStream(), asyncTimeoutTimeout));
            }
            throw new IOException("socket's output stream == null");
        }
        throw new IllegalArgumentException("socket == null");
    }

    public static Source source(File file) throws FileNotFoundException {
        if (file != null) {
            return source(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    @IgnoreJRERequirement
    public static Source source(Path path, OpenOption... openOptionArr) throws IOException {
        if (path != null) {
            return source(Files.newInputStream(path, openOptionArr));
        }
        throw new IllegalArgumentException("path == null");
    }

    public static Source source(Socket socket) throws IOException {
        if (socket != null) {
            if (socket.getInputStream() != null) {
                AsyncTimeout asyncTimeoutTimeout = timeout(socket);
                return asyncTimeoutTimeout.source(source(socket.getInputStream(), asyncTimeoutTimeout));
            }
            throw new IOException("socket's input stream == null");
        }
        throw new IllegalArgumentException("socket == null");
    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    @IgnoreJRERequirement
    public static Sink sink(Path path, OpenOption... openOptionArr) throws IOException {
        if (path != null) {
            return sink(Files.newOutputStream(path, openOptionArr));
        }
        throw new IllegalArgumentException("path == null");
    }
}
