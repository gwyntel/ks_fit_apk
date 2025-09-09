package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* loaded from: classes5.dex */
public final class InflaterSource implements Source {
    private int bufferBytesHeldByInflater;
    private boolean closed;
    private final Inflater inflater;
    private final BufferedSource source;

    public InflaterSource(Source source, Inflater inflater) {
        this(Okio.buffer(source), inflater);
    }

    private void releaseInflatedBytes() throws IOException {
        int i2 = this.bufferBytesHeldByInflater;
        if (i2 == 0) {
            return;
        }
        int remaining = i2 - this.inflater.getRemaining();
        this.bufferBytesHeldByInflater -= remaining;
        this.source.skip(remaining);
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.inflater.end();
        this.closed = true;
        this.source.close();
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j2) throws DataFormatException, IOException {
        boolean zRefill;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (j2 == 0) {
            return 0L;
        }
        do {
            zRefill = refill();
            try {
                Segment segmentD = buffer.d(1);
                int iInflate = this.inflater.inflate(segmentD.f26493a, segmentD.f26495c, (int) Math.min(j2, 8192 - segmentD.f26495c));
                if (iInflate > 0) {
                    segmentD.f26495c += iInflate;
                    long j3 = iInflate;
                    buffer.f26470b += j3;
                    return j3;
                }
                if (!this.inflater.finished() && !this.inflater.needsDictionary()) {
                }
                releaseInflatedBytes();
                if (segmentD.f26494b != segmentD.f26495c) {
                    return -1L;
                }
                buffer.f26469a = segmentD.pop();
                SegmentPool.a(segmentD);
                return -1L;
            } catch (DataFormatException e2) {
                throw new IOException(e2);
            }
        } while (!zRefill);
        throw new EOFException("source exhausted prematurely");
    }

    public final boolean refill() throws IOException {
        if (!this.inflater.needsInput()) {
            return false;
        }
        releaseInflatedBytes();
        if (this.inflater.getRemaining() != 0) {
            throw new IllegalStateException("?");
        }
        if (this.source.exhausted()) {
            return true;
        }
        Segment segment = this.source.buffer().f26469a;
        int i2 = segment.f26495c;
        int i3 = segment.f26494b;
        int i4 = i2 - i3;
        this.bufferBytesHeldByInflater = i4;
        this.inflater.setInput(segment.f26493a, i3, i4);
        return false;
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.source.timeout();
    }

    InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        if (bufferedSource == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.source = bufferedSource;
        this.inflater = inflater;
    }
}
