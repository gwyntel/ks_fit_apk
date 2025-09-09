package androidx.media3.exoplayer.dash;

import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.dash.manifest.EventStream;
import androidx.media3.exoplayer.source.SampleStream;
import androidx.media3.extractor.metadata.emsg.EventMessageEncoder;
import java.io.IOException;

/* loaded from: classes.dex */
final class EventSampleStream implements SampleStream {
    private int currentIndex;
    private EventStream eventStream;
    private boolean eventStreamAppendable;
    private long[] eventTimesUs;
    private boolean isFormatSentDownstream;
    private final Format upstreamFormat;
    private final EventMessageEncoder eventMessageEncoder = new EventMessageEncoder();
    private long pendingSeekPositionUs = C.TIME_UNSET;

    public EventSampleStream(EventStream eventStream, Format format, boolean z2) {
        this.upstreamFormat = format;
        this.eventStream = eventStream;
        this.eventTimesUs = eventStream.presentationTimesUs;
        updateEventStream(eventStream, z2);
    }

    public String eventStreamId() {
        return this.eventStream.id();
    }

    @Override // androidx.media3.exoplayer.source.SampleStream
    public boolean isReady() {
        return true;
    }

    @Override // androidx.media3.exoplayer.source.SampleStream
    public void maybeThrowError() throws IOException {
    }

    @Override // androidx.media3.exoplayer.source.SampleStream
    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) throws IOException {
        int i3 = this.currentIndex;
        boolean z2 = i3 == this.eventTimesUs.length;
        if (z2 && !this.eventStreamAppendable) {
            decoderInputBuffer.setFlags(4);
            return -4;
        }
        if ((i2 & 2) != 0 || !this.isFormatSentDownstream) {
            formatHolder.format = this.upstreamFormat;
            this.isFormatSentDownstream = true;
            return -5;
        }
        if (z2) {
            return -3;
        }
        if ((i2 & 1) == 0) {
            this.currentIndex = i3 + 1;
        }
        if ((i2 & 4) == 0) {
            byte[] bArrEncode = this.eventMessageEncoder.encode(this.eventStream.events[i3]);
            decoderInputBuffer.ensureSpaceForWrite(bArrEncode.length);
            decoderInputBuffer.data.put(bArrEncode);
        }
        decoderInputBuffer.timeUs = this.eventTimesUs[i3];
        decoderInputBuffer.setFlags(1);
        return -4;
    }

    public void seekToUs(long j2) {
        int iBinarySearchCeil = Util.binarySearchCeil(this.eventTimesUs, j2, true, false);
        this.currentIndex = iBinarySearchCeil;
        if (!this.eventStreamAppendable || iBinarySearchCeil != this.eventTimesUs.length) {
            j2 = C.TIME_UNSET;
        }
        this.pendingSeekPositionUs = j2;
    }

    @Override // androidx.media3.exoplayer.source.SampleStream
    public int skipData(long j2) {
        int iMax = Math.max(this.currentIndex, Util.binarySearchCeil(this.eventTimesUs, j2, true, false));
        int i2 = iMax - this.currentIndex;
        this.currentIndex = iMax;
        return i2;
    }

    public void updateEventStream(EventStream eventStream, boolean z2) {
        int i2 = this.currentIndex;
        long j2 = i2 == 0 ? -9223372036854775807L : this.eventTimesUs[i2 - 1];
        this.eventStreamAppendable = z2;
        this.eventStream = eventStream;
        long[] jArr = eventStream.presentationTimesUs;
        this.eventTimesUs = jArr;
        long j3 = this.pendingSeekPositionUs;
        if (j3 != C.TIME_UNSET) {
            seekToUs(j3);
        } else if (j2 != C.TIME_UNSET) {
            this.currentIndex = Util.binarySearchCeil(jArr, j2, false, false);
        }
    }
}
