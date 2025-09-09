package androidx.media3.exoplayer.source.preload;

import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.source.MediaPeriod;
import androidx.media3.exoplayer.source.SampleStream;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.source.o;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
final class PreloadMediaPeriod implements MediaPeriod {

    @Nullable
    private MediaPeriod.Callback callback;
    public final MediaPeriod mediaPeriod;

    @Nullable
    private PreloadTrackSelectionHolder preloadTrackSelectionHolder;
    private boolean prepareInternalCalled;
    private boolean prepared;

    private static class PreloadTrackSelectionHolder {
        public final boolean[] mayRetainStreamFlags;
        public final ExoTrackSelection[] selections;
        public final boolean[] streamResetFlags;
        public final SampleStream[] streams;
        public final long trackSelectionPositionUs;

        public PreloadTrackSelectionHolder(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
            this.selections = exoTrackSelectionArr;
            this.mayRetainStreamFlags = zArr;
            this.streams = sampleStreamArr;
            this.streamResetFlags = zArr2;
            this.trackSelectionPositionUs = j2;
        }
    }

    public PreloadMediaPeriod(MediaPeriod mediaPeriod) {
        this.mediaPeriod = mediaPeriod;
    }

    private static boolean isSameAdaptionSet(ExoTrackSelection exoTrackSelection, ExoTrackSelection exoTrackSelection2) {
        if (!Objects.equals(exoTrackSelection.getTrackGroup(), exoTrackSelection2.getTrackGroup()) || exoTrackSelection.length() != exoTrackSelection2.length()) {
            return false;
        }
        for (int i2 = 0; i2 < exoTrackSelection.length(); i2++) {
            if (exoTrackSelection.getIndexInTrackGroup(i2) != exoTrackSelection2.getIndexInTrackGroup(i2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean maybeUpdatePreloadTrackSelectionHolderForReselection(ExoTrackSelection[] exoTrackSelectionArr, PreloadTrackSelectionHolder preloadTrackSelectionHolder) {
        ExoTrackSelection[] exoTrackSelectionArr2 = ((PreloadTrackSelectionHolder) Assertions.checkNotNull(preloadTrackSelectionHolder)).selections;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < exoTrackSelectionArr.length) {
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i2];
            ExoTrackSelection exoTrackSelection2 = exoTrackSelectionArr2[i2];
            if (exoTrackSelection != null || exoTrackSelection2 != null) {
                preloadTrackSelectionHolder.mayRetainStreamFlags[i2] = false;
                if (exoTrackSelection == null) {
                    preloadTrackSelectionHolder.selections[i2] = null;
                } else if (exoTrackSelection2 == null || !isSameAdaptionSet(exoTrackSelection, exoTrackSelection2)) {
                    preloadTrackSelectionHolder.selections[i2] = exoTrackSelection;
                } else if (exoTrackSelection.getTrackGroup().type == 2 || exoTrackSelection.getTrackGroup().type == 1 || exoTrackSelection.getSelectedIndexInTrackGroup() == exoTrackSelection2.getSelectedIndexInTrackGroup()) {
                    preloadTrackSelectionHolder.mayRetainStreamFlags[i2] = true;
                } else {
                    preloadTrackSelectionHolder.selections[i2] = exoTrackSelection;
                }
                z2 = true;
            }
            i2++;
        }
        return z2;
    }

    private void prepareInternal(long j2) {
        this.prepareInternalCalled = true;
        this.mediaPeriod.prepare(new MediaPeriod.Callback() { // from class: androidx.media3.exoplayer.source.preload.PreloadMediaPeriod.1
            @Override // androidx.media3.exoplayer.source.MediaPeriod.Callback
            public void onPrepared(MediaPeriod mediaPeriod) {
                PreloadMediaPeriod.this.prepared = true;
                ((MediaPeriod.Callback) Assertions.checkNotNull(PreloadMediaPeriod.this.callback)).onPrepared(PreloadMediaPeriod.this);
            }

            @Override // androidx.media3.exoplayer.source.SequenceableLoader.Callback
            public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
                ((MediaPeriod.Callback) Assertions.checkNotNull(PreloadMediaPeriod.this.callback)).onContinueLoadingRequested(PreloadMediaPeriod.this);
            }
        }, j2);
    }

    private long selectTracksInternal(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        PreloadTrackSelectionHolder preloadTrackSelectionHolder = this.preloadTrackSelectionHolder;
        if (preloadTrackSelectionHolder == null) {
            return this.mediaPeriod.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j2);
        }
        Assertions.checkState(sampleStreamArr.length == preloadTrackSelectionHolder.streams.length);
        PreloadTrackSelectionHolder preloadTrackSelectionHolder2 = this.preloadTrackSelectionHolder;
        if (j2 == preloadTrackSelectionHolder2.trackSelectionPositionUs) {
            PreloadTrackSelectionHolder preloadTrackSelectionHolder3 = (PreloadTrackSelectionHolder) Assertions.checkNotNull(preloadTrackSelectionHolder2);
            long jSelectTracks = preloadTrackSelectionHolder3.trackSelectionPositionUs;
            boolean[] zArr3 = preloadTrackSelectionHolder3.streamResetFlags;
            if (maybeUpdatePreloadTrackSelectionHolderForReselection(exoTrackSelectionArr, preloadTrackSelectionHolder3)) {
                zArr3 = new boolean[zArr3.length];
                jSelectTracks = this.mediaPeriod.selectTracks(preloadTrackSelectionHolder3.selections, preloadTrackSelectionHolder3.mayRetainStreamFlags, preloadTrackSelectionHolder3.streams, zArr3, preloadTrackSelectionHolder3.trackSelectionPositionUs);
                int i2 = 0;
                while (true) {
                    boolean[] zArr4 = preloadTrackSelectionHolder3.mayRetainStreamFlags;
                    if (i2 >= zArr4.length) {
                        break;
                    }
                    if (zArr4[i2]) {
                        zArr3[i2] = true;
                    }
                    i2++;
                }
            }
            SampleStream[] sampleStreamArr2 = preloadTrackSelectionHolder3.streams;
            System.arraycopy(sampleStreamArr2, 0, sampleStreamArr, 0, sampleStreamArr2.length);
            System.arraycopy(zArr3, 0, zArr2, 0, zArr3.length);
            this.preloadTrackSelectionHolder = null;
            return jSelectTracks;
        }
        int i3 = 0;
        while (true) {
            SampleStream[] sampleStreamArr3 = this.preloadTrackSelectionHolder.streams;
            if (i3 >= sampleStreamArr3.length) {
                this.preloadTrackSelectionHolder = null;
                return this.mediaPeriod.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j2);
            }
            SampleStream sampleStream = sampleStreamArr3[i3];
            if (sampleStream != null) {
                sampleStreamArr[i3] = sampleStream;
                zArr[i3] = false;
            }
            i3++;
        }
    }

    void c(MediaPeriod.Callback callback, long j2) {
        this.callback = callback;
        if (this.prepared) {
            callback.onPrepared(this);
        }
        if (this.prepareInternalCalled) {
            return;
        }
        prepareInternal(j2);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean continueLoading(LoadingInfo loadingInfo) {
        return this.mediaPeriod.continueLoading(loadingInfo);
    }

    long d(ExoTrackSelection[] exoTrackSelectionArr, long j2) {
        SampleStream[] sampleStreamArr = new SampleStream[exoTrackSelectionArr.length];
        boolean[] zArr = new boolean[exoTrackSelectionArr.length];
        boolean[] zArr2 = new boolean[exoTrackSelectionArr.length];
        long jSelectTracksInternal = selectTracksInternal(exoTrackSelectionArr, zArr2, sampleStreamArr, zArr, j2);
        this.preloadTrackSelectionHolder = new PreloadTrackSelectionHolder(exoTrackSelectionArr, zArr2, sampleStreamArr, zArr, jSelectTracksInternal);
        return jSelectTracksInternal;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
        this.mediaPeriod.discardBuffer(j2, z2);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return this.mediaPeriod.getAdjustedSeekPositionUs(j2, seekParameters);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.mediaPeriod.getBufferedPositionUs();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.mediaPeriod.getNextLoadPositionUs();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public /* synthetic */ List getStreamKeys(List list) {
        return o.a(this, list);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.mediaPeriod.getTrackGroups();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean isLoading() {
        return this.mediaPeriod.isLoading();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        this.mediaPeriod.maybeThrowPrepareError();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.callback = callback;
        if (this.prepared) {
            callback.onPrepared(this);
        } else {
            if (this.prepareInternalCalled) {
                return;
            }
            prepareInternal(j2);
        }
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long readDiscontinuity() {
        return this.mediaPeriod.readDiscontinuity();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
        this.mediaPeriod.reevaluateBuffer(j2);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long seekToUs(long j2) {
        return this.mediaPeriod.seekToUs(j2);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        return selectTracksInternal(exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j2);
    }
}
