package androidx.media3.exoplayer.source;

import androidx.media3.common.C;
import androidx.media3.common.StreamKey;
import androidx.media3.common.util.Assertions;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.source.MediaPeriod;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import java.io.IOException;
import java.util.List;

/* loaded from: classes2.dex */
final class TimeOffsetMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
    private MediaPeriod.Callback callback;
    private final MediaPeriod mediaPeriod;
    private final long timeOffsetUs;

    private static final class TimeOffsetSampleStream implements SampleStream {
        private final SampleStream sampleStream;
        private final long timeOffsetUs;

        public TimeOffsetSampleStream(SampleStream sampleStream, long j2) {
            this.sampleStream = sampleStream;
            this.timeOffsetUs = j2;
        }

        public SampleStream getChildStream() {
            return this.sampleStream;
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public boolean isReady() {
            return this.sampleStream.isReady();
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public void maybeThrowError() throws IOException {
            this.sampleStream.maybeThrowError();
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            int data = this.sampleStream.readData(formatHolder, decoderInputBuffer, i2);
            if (data == -4) {
                decoderInputBuffer.timeUs += this.timeOffsetUs;
            }
            return data;
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public int skipData(long j2) {
            return this.sampleStream.skipData(j2 - this.timeOffsetUs);
        }
    }

    public TimeOffsetMediaPeriod(MediaPeriod mediaPeriod, long j2) {
        this.mediaPeriod = mediaPeriod;
        this.timeOffsetUs = j2;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean continueLoading(LoadingInfo loadingInfo) {
        return this.mediaPeriod.continueLoading(loadingInfo.buildUpon().setPlaybackPositionUs(loadingInfo.playbackPositionUs - this.timeOffsetUs).build());
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
        this.mediaPeriod.discardBuffer(j2 - this.timeOffsetUs, z2);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return this.mediaPeriod.getAdjustedSeekPositionUs(j2 - this.timeOffsetUs, seekParameters) + this.timeOffsetUs;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getBufferedPositionUs() {
        long bufferedPositionUs = this.mediaPeriod.getBufferedPositionUs();
        if (bufferedPositionUs == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        return this.timeOffsetUs + bufferedPositionUs;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        long nextLoadPositionUs = this.mediaPeriod.getNextLoadPositionUs();
        if (nextLoadPositionUs == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        return this.timeOffsetUs + nextLoadPositionUs;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        return this.mediaPeriod.getStreamKeys(list);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.mediaPeriod.getTrackGroups();
    }

    public MediaPeriod getWrappedMediaPeriod() {
        return this.mediaPeriod;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean isLoading() {
        return this.mediaPeriod.isLoading();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        this.mediaPeriod.maybeThrowPrepareError();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.callback = callback;
        this.mediaPeriod.prepare(this, j2 - this.timeOffsetUs);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long readDiscontinuity() {
        long discontinuity = this.mediaPeriod.readDiscontinuity();
        return discontinuity == C.TIME_UNSET ? C.TIME_UNSET : this.timeOffsetUs + discontinuity;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
        this.mediaPeriod.reevaluateBuffer(j2 - this.timeOffsetUs);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long seekToUs(long j2) {
        return this.mediaPeriod.seekToUs(j2 - this.timeOffsetUs) + this.timeOffsetUs;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        SampleStream[] sampleStreamArr2 = new SampleStream[sampleStreamArr.length];
        int i2 = 0;
        while (true) {
            SampleStream childStream = null;
            if (i2 >= sampleStreamArr.length) {
                break;
            }
            TimeOffsetSampleStream timeOffsetSampleStream = (TimeOffsetSampleStream) sampleStreamArr[i2];
            if (timeOffsetSampleStream != null) {
                childStream = timeOffsetSampleStream.getChildStream();
            }
            sampleStreamArr2[i2] = childStream;
            i2++;
        }
        long jSelectTracks = this.mediaPeriod.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr2, zArr2, j2 - this.timeOffsetUs);
        for (int i3 = 0; i3 < sampleStreamArr.length; i3++) {
            SampleStream sampleStream = sampleStreamArr2[i3];
            if (sampleStream == null) {
                sampleStreamArr[i3] = null;
            } else {
                SampleStream sampleStream2 = sampleStreamArr[i3];
                if (sampleStream2 == null || ((TimeOffsetSampleStream) sampleStream2).getChildStream() != sampleStream) {
                    sampleStreamArr[i3] = new TimeOffsetSampleStream(sampleStream, this.timeOffsetUs);
                }
            }
        }
        return jSelectTracks + this.timeOffsetUs;
    }

    @Override // androidx.media3.exoplayer.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
    }
}
