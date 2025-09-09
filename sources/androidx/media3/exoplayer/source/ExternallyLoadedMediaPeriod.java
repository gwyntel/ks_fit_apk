package androidx.media3.exoplayer.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.TrackGroup;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.source.ExternalLoader;
import androidx.media3.exoplayer.source.MediaPeriod;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes2.dex */
final class ExternallyLoadedMediaPeriod implements MediaPeriod {
    private final ExternalLoader externalLoader;
    private final AtomicBoolean loadingFinished;
    private ListenableFuture<?> loadingFuture;
    private final AtomicReference<Throwable> loadingThrowable;
    private final byte[] sampleData;
    private final TrackGroupArray tracks;
    private final Uri uri;

    private final class SampleStreamImpl implements SampleStream {
        private static final int STREAM_STATE_END_OF_STREAM = 2;
        private static final int STREAM_STATE_SEND_FORMAT = 0;
        private static final int STREAM_STATE_SEND_SAMPLE = 1;
        private int streamState = 0;

        public SampleStreamImpl() {
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public boolean isReady() {
            return ExternallyLoadedMediaPeriod.this.loadingFinished.get();
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public void maybeThrowError() throws IOException {
            Throwable th = (Throwable) ExternallyLoadedMediaPeriod.this.loadingThrowable.get();
            if (th != null) {
                throw new IOException(th);
            }
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            int i3 = this.streamState;
            if (i3 == 2) {
                decoderInputBuffer.addFlag(4);
                return -4;
            }
            if ((i2 & 2) != 0 || i3 == 0) {
                formatHolder.format = ExternallyLoadedMediaPeriod.this.tracks.get(0).getFormat(0);
                this.streamState = 1;
                return -5;
            }
            if (!ExternallyLoadedMediaPeriod.this.loadingFinished.get()) {
                return -3;
            }
            int length = ExternallyLoadedMediaPeriod.this.sampleData.length;
            decoderInputBuffer.addFlag(1);
            decoderInputBuffer.timeUs = 0L;
            if ((i2 & 4) == 0) {
                decoderInputBuffer.ensureSpaceForWrite(length);
                decoderInputBuffer.data.put(ExternallyLoadedMediaPeriod.this.sampleData, 0, length);
            }
            if ((i2 & 1) == 0) {
                this.streamState = 2;
            }
            return -4;
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public int skipData(long j2) {
            return 0;
        }
    }

    public ExternallyLoadedMediaPeriod(Uri uri, String str, ExternalLoader externalLoader) {
        this.uri = uri;
        Format formatBuild = new Format.Builder().setSampleMimeType(str).build();
        this.externalLoader = externalLoader;
        this.tracks = new TrackGroupArray(new TrackGroup(formatBuild));
        this.sampleData = uri.toString().getBytes(Charsets.UTF_8);
        this.loadingFinished = new AtomicBoolean();
        this.loadingThrowable = new AtomicReference<>();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean continueLoading(LoadingInfo loadingInfo) {
        return !this.loadingFinished.get();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return j2;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.loadingFinished.get() ? Long.MIN_VALUE : 0L;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.loadingFinished.get() ? Long.MIN_VALUE : 0L;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public /* synthetic */ List getStreamKeys(List list) {
        return o.a(this, list);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.tracks;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean isLoading() {
        return !this.loadingFinished.get();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void maybeThrowPrepareError() {
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) {
        callback.onPrepared(this);
        ListenableFuture<?> listenableFutureLoad = this.externalLoader.load(new ExternalLoader.LoadRequest(this.uri));
        this.loadingFuture = listenableFutureLoad;
        Futures.addCallback(listenableFutureLoad, new FutureCallback<Object>() { // from class: androidx.media3.exoplayer.source.ExternallyLoadedMediaPeriod.1
            @Override // com.google.common.util.concurrent.FutureCallback
            public void onFailure(Throwable th) {
                ExternallyLoadedMediaPeriod.this.loadingThrowable.set(th);
            }

            @Override // com.google.common.util.concurrent.FutureCallback
            public void onSuccess(@Nullable Object obj) {
                ExternallyLoadedMediaPeriod.this.loadingFinished.set(true);
            }
        }, MoreExecutors.directExecutor());
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
    }

    public void releasePeriod() {
        ListenableFuture<?> listenableFuture = this.loadingFuture;
        if (listenableFuture != null) {
            listenableFuture.cancel(false);
        }
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long seekToUs(long j2) {
        return j2;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            if (sampleStreamArr[i2] != null && (exoTrackSelectionArr[i2] == null || !zArr[i2])) {
                sampleStreamArr[i2] = null;
            }
            if (sampleStreamArr[i2] == null && exoTrackSelectionArr[i2] != null) {
                sampleStreamArr[i2] = new SampleStreamImpl();
                zArr2[i2] = true;
            }
        }
        return j2;
    }
}
