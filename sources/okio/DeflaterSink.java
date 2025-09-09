package okio;

import java.io.IOException;
import java.util.zip.Deflater;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* loaded from: classes5.dex */
public final class DeflaterSink implements Sink {
    private boolean closed;
    private final Deflater deflater;
    private final BufferedSink sink;

    public DeflaterSink(Sink sink, Deflater deflater) {
        this(Okio.buffer(sink), deflater);
    }

    @IgnoreJRERequirement
    private void deflate(boolean z2) throws IOException {
        Segment segmentD;
        int iDeflate;
        Buffer buffer = this.sink.buffer();
        while (true) {
            segmentD = buffer.d(1);
            if (z2) {
                Deflater deflater = this.deflater;
                byte[] bArr = segmentD.f26493a;
                int i2 = segmentD.f26495c;
                iDeflate = deflater.deflate(bArr, i2, 8192 - i2, 2);
            } else {
                Deflater deflater2 = this.deflater;
                byte[] bArr2 = segmentD.f26493a;
                int i3 = segmentD.f26495c;
                iDeflate = deflater2.deflate(bArr2, i3, 8192 - i3);
            }
            if (iDeflate > 0) {
                segmentD.f26495c += iDeflate;
                buffer.f26470b += iDeflate;
                this.sink.emitCompleteSegments();
            } else if (this.deflater.needsInput()) {
                break;
            }
        }
        if (segmentD.f26494b == segmentD.f26495c) {
            buffer.f26469a = segmentD.pop();
            SegmentPool.a(segmentD);
        }
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.closed) {
            return;
        }
        try {
            e();
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.deflater.end();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        try {
            this.sink.close();
        } catch (Throwable th3) {
            if (th == null) {
                th = th3;
            }
        }
        this.closed = true;
        if (th != null) {
            Util.sneakyRethrow(th);
        }
    }

    void e() throws IOException {
        this.deflater.finish();
        deflate(false);
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        deflate(true);
        this.sink.flush();
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "DeflaterSink(" + this.sink + ")";
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j2) throws IOException {
        Util.checkOffsetAndCount(buffer.f26470b, 0L, j2);
        while (j2 > 0) {
            Segment segment = buffer.f26469a;
            int iMin = (int) Math.min(j2, segment.f26495c - segment.f26494b);
            this.deflater.setInput(segment.f26493a, segment.f26494b, iMin);
            deflate(false);
            long j3 = iMin;
            buffer.f26470b -= j3;
            int i2 = segment.f26494b + iMin;
            segment.f26494b = i2;
            if (i2 == segment.f26495c) {
                buffer.f26469a = segment.pop();
                SegmentPool.a(segment);
            }
            j2 -= j3;
        }
    }

    DeflaterSink(BufferedSink bufferedSink, Deflater deflater) {
        if (bufferedSink == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (deflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.sink = bufferedSink;
        this.deflater = deflater;
    }
}
