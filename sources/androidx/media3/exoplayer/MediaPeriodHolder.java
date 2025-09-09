package androidx.media3.exoplayer;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.source.ClippingMediaPeriod;
import androidx.media3.exoplayer.source.EmptySampleStream;
import androidx.media3.exoplayer.source.MediaPeriod;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.SampleStream;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.trackselection.TrackSelector;
import androidx.media3.exoplayer.trackselection.TrackSelectorResult;
import androidx.media3.exoplayer.upstream.Allocator;

/* loaded from: classes.dex */
final class MediaPeriodHolder {
    private static final String TAG = "MediaPeriodHolder";
    public boolean allRenderersInCorrectState;
    public boolean hasEnabledTracks;

    /* renamed from: info, reason: collision with root package name */
    public MediaPeriodInfo f4928info;
    private final boolean[] mayRetainStreamFlags;
    public final MediaPeriod mediaPeriod;
    private final MediaSourceList mediaSourceList;

    @Nullable
    private MediaPeriodHolder next;
    public boolean prepared;
    private final RendererCapabilities[] rendererCapabilities;
    private long rendererPositionOffsetUs;
    public final SampleStream[] sampleStreams;
    private TrackGroupArray trackGroups;
    private final TrackSelector trackSelector;
    private TrackSelectorResult trackSelectorResult;
    public final Object uid;

    interface Factory {
        MediaPeriodHolder create(MediaPeriodInfo mediaPeriodInfo, long j2);
    }

    public MediaPeriodHolder(RendererCapabilities[] rendererCapabilitiesArr, long j2, TrackSelector trackSelector, Allocator allocator, MediaSourceList mediaSourceList, MediaPeriodInfo mediaPeriodInfo, TrackSelectorResult trackSelectorResult) {
        this.rendererCapabilities = rendererCapabilitiesArr;
        this.rendererPositionOffsetUs = j2;
        this.trackSelector = trackSelector;
        this.mediaSourceList = mediaSourceList;
        MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodInfo.id;
        this.uid = mediaPeriodId.periodUid;
        this.f4928info = mediaPeriodInfo;
        this.trackGroups = TrackGroupArray.EMPTY;
        this.trackSelectorResult = trackSelectorResult;
        this.sampleStreams = new SampleStream[rendererCapabilitiesArr.length];
        this.mayRetainStreamFlags = new boolean[rendererCapabilitiesArr.length];
        this.mediaPeriod = createMediaPeriod(mediaPeriodId, mediaSourceList, allocator, mediaPeriodInfo.startPositionUs, mediaPeriodInfo.endPositionUs);
    }

    private void associateNoSampleRenderersWithEmptySampleStream(SampleStream[] sampleStreamArr) {
        int i2 = 0;
        while (true) {
            RendererCapabilities[] rendererCapabilitiesArr = this.rendererCapabilities;
            if (i2 >= rendererCapabilitiesArr.length) {
                return;
            }
            if (rendererCapabilitiesArr[i2].getTrackType() == -2 && this.trackSelectorResult.isRendererEnabled(i2)) {
                sampleStreamArr[i2] = new EmptySampleStream();
            }
            i2++;
        }
    }

    private static MediaPeriod createMediaPeriod(MediaSource.MediaPeriodId mediaPeriodId, MediaSourceList mediaSourceList, Allocator allocator, long j2, long j3) {
        MediaPeriod mediaPeriodCreatePeriod = mediaSourceList.createPeriod(mediaPeriodId, allocator, j2);
        return j3 != C.TIME_UNSET ? new ClippingMediaPeriod(mediaPeriodCreatePeriod, true, 0L, j3) : mediaPeriodCreatePeriod;
    }

    private void disableTrackSelectionsInResult() {
        if (!isLoadingMediaPeriod()) {
            return;
        }
        int i2 = 0;
        while (true) {
            TrackSelectorResult trackSelectorResult = this.trackSelectorResult;
            if (i2 >= trackSelectorResult.length) {
                return;
            }
            boolean zIsRendererEnabled = trackSelectorResult.isRendererEnabled(i2);
            ExoTrackSelection exoTrackSelection = this.trackSelectorResult.selections[i2];
            if (zIsRendererEnabled && exoTrackSelection != null) {
                exoTrackSelection.disable();
            }
            i2++;
        }
    }

    private void disassociateNoSampleRenderersWithEmptySampleStream(SampleStream[] sampleStreamArr) {
        int i2 = 0;
        while (true) {
            RendererCapabilities[] rendererCapabilitiesArr = this.rendererCapabilities;
            if (i2 >= rendererCapabilitiesArr.length) {
                return;
            }
            if (rendererCapabilitiesArr[i2].getTrackType() == -2) {
                sampleStreamArr[i2] = null;
            }
            i2++;
        }
    }

    private void enableTrackSelectionsInResult() {
        if (!isLoadingMediaPeriod()) {
            return;
        }
        int i2 = 0;
        while (true) {
            TrackSelectorResult trackSelectorResult = this.trackSelectorResult;
            if (i2 >= trackSelectorResult.length) {
                return;
            }
            boolean zIsRendererEnabled = trackSelectorResult.isRendererEnabled(i2);
            ExoTrackSelection exoTrackSelection = this.trackSelectorResult.selections[i2];
            if (zIsRendererEnabled && exoTrackSelection != null) {
                exoTrackSelection.enable();
            }
            i2++;
        }
    }

    private boolean isLoadingMediaPeriod() {
        return this.next == null;
    }

    private static void releaseMediaPeriod(MediaSourceList mediaSourceList, MediaPeriod mediaPeriod) {
        try {
            if (mediaPeriod instanceof ClippingMediaPeriod) {
                mediaSourceList.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
            } else {
                mediaSourceList.releasePeriod(mediaPeriod);
            }
        } catch (RuntimeException e2) {
            Log.e(TAG, "Period release failed.", e2);
        }
    }

    public long applyTrackSelection(TrackSelectorResult trackSelectorResult, long j2, boolean z2) {
        return applyTrackSelection(trackSelectorResult, j2, z2, new boolean[this.rendererCapabilities.length]);
    }

    public boolean canBeUsedForMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo) {
        if (MediaPeriodQueue.b(this.f4928info.durationUs, mediaPeriodInfo.durationUs)) {
            MediaPeriodInfo mediaPeriodInfo2 = this.f4928info;
            if (mediaPeriodInfo2.startPositionUs == mediaPeriodInfo.startPositionUs && mediaPeriodInfo2.id.equals(mediaPeriodInfo.id)) {
                return true;
            }
        }
        return false;
    }

    public void continueLoading(long j2, float f2, long j3) {
        Assertions.checkState(isLoadingMediaPeriod());
        this.mediaPeriod.continueLoading(new LoadingInfo.Builder().setPlaybackPositionUs(toPeriodTime(j2)).setPlaybackSpeed(f2).setLastRebufferRealtimeMs(j3).build());
    }

    public long getBufferedPositionUs() {
        if (!this.prepared) {
            return this.f4928info.startPositionUs;
        }
        long bufferedPositionUs = this.hasEnabledTracks ? this.mediaPeriod.getBufferedPositionUs() : Long.MIN_VALUE;
        return bufferedPositionUs == Long.MIN_VALUE ? this.f4928info.durationUs : bufferedPositionUs;
    }

    @Nullable
    public MediaPeriodHolder getNext() {
        return this.next;
    }

    public long getNextLoadPositionUs() {
        if (this.prepared) {
            return this.mediaPeriod.getNextLoadPositionUs();
        }
        return 0L;
    }

    public long getRendererOffset() {
        return this.rendererPositionOffsetUs;
    }

    public long getStartPositionRendererTime() {
        return this.f4928info.startPositionUs + this.rendererPositionOffsetUs;
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public TrackSelectorResult getTrackSelectorResult() {
        return this.trackSelectorResult;
    }

    public void handlePrepared(float f2, Timeline timeline) throws ExoPlaybackException {
        this.prepared = true;
        this.trackGroups = this.mediaPeriod.getTrackGroups();
        TrackSelectorResult trackSelectorResultSelectTracks = selectTracks(f2, timeline);
        MediaPeriodInfo mediaPeriodInfo = this.f4928info;
        long jMax = mediaPeriodInfo.startPositionUs;
        long j2 = mediaPeriodInfo.durationUs;
        if (j2 != C.TIME_UNSET && jMax >= j2) {
            jMax = Math.max(0L, j2 - 1);
        }
        long jApplyTrackSelection = applyTrackSelection(trackSelectorResultSelectTracks, jMax, false);
        long j3 = this.rendererPositionOffsetUs;
        MediaPeriodInfo mediaPeriodInfo2 = this.f4928info;
        this.rendererPositionOffsetUs = j3 + (mediaPeriodInfo2.startPositionUs - jApplyTrackSelection);
        this.f4928info = mediaPeriodInfo2.copyWithStartPositionUs(jApplyTrackSelection);
    }

    public boolean isFullyBuffered() {
        return this.prepared && (!this.hasEnabledTracks || this.mediaPeriod.getBufferedPositionUs() == Long.MIN_VALUE);
    }

    public void reevaluateBuffer(long j2) {
        Assertions.checkState(isLoadingMediaPeriod());
        if (this.prepared) {
            this.mediaPeriod.reevaluateBuffer(toPeriodTime(j2));
        }
    }

    public void release() {
        disableTrackSelectionsInResult();
        releaseMediaPeriod(this.mediaSourceList, this.mediaPeriod);
    }

    public TrackSelectorResult selectTracks(float f2, Timeline timeline) throws ExoPlaybackException {
        TrackSelectorResult trackSelectorResultSelectTracks = this.trackSelector.selectTracks(this.rendererCapabilities, getTrackGroups(), this.f4928info.id, timeline);
        for (int i2 = 0; i2 < trackSelectorResultSelectTracks.length; i2++) {
            if (trackSelectorResultSelectTracks.isRendererEnabled(i2)) {
                if (trackSelectorResultSelectTracks.selections[i2] == null && this.rendererCapabilities[i2].getTrackType() != -2) {
                    z = false;
                }
                Assertions.checkState(z);
            } else {
                Assertions.checkState(trackSelectorResultSelectTracks.selections[i2] == null);
            }
        }
        for (ExoTrackSelection exoTrackSelection : trackSelectorResultSelectTracks.selections) {
            if (exoTrackSelection != null) {
                exoTrackSelection.onPlaybackSpeed(f2);
            }
        }
        return trackSelectorResultSelectTracks;
    }

    public void setNext(@Nullable MediaPeriodHolder mediaPeriodHolder) {
        if (mediaPeriodHolder == this.next) {
            return;
        }
        disableTrackSelectionsInResult();
        this.next = mediaPeriodHolder;
        enableTrackSelectionsInResult();
    }

    public void setRendererOffset(long j2) {
        this.rendererPositionOffsetUs = j2;
    }

    public long toPeriodTime(long j2) {
        return j2 - getRendererOffset();
    }

    public long toRendererTime(long j2) {
        return j2 + getRendererOffset();
    }

    public void updateClipping() {
        MediaPeriod mediaPeriod = this.mediaPeriod;
        if (mediaPeriod instanceof ClippingMediaPeriod) {
            long j2 = this.f4928info.endPositionUs;
            if (j2 == C.TIME_UNSET) {
                j2 = Long.MIN_VALUE;
            }
            ((ClippingMediaPeriod) mediaPeriod).updateClipping(0L, j2);
        }
    }

    public long applyTrackSelection(TrackSelectorResult trackSelectorResult, long j2, boolean z2, boolean[] zArr) {
        int i2 = 0;
        while (true) {
            boolean z3 = true;
            if (i2 >= trackSelectorResult.length) {
                break;
            }
            boolean[] zArr2 = this.mayRetainStreamFlags;
            if (z2 || !trackSelectorResult.isEquivalent(this.trackSelectorResult, i2)) {
                z3 = false;
            }
            zArr2[i2] = z3;
            i2++;
        }
        disassociateNoSampleRenderersWithEmptySampleStream(this.sampleStreams);
        disableTrackSelectionsInResult();
        this.trackSelectorResult = trackSelectorResult;
        enableTrackSelectionsInResult();
        long jSelectTracks = this.mediaPeriod.selectTracks(trackSelectorResult.selections, this.mayRetainStreamFlags, this.sampleStreams, zArr, j2);
        associateNoSampleRenderersWithEmptySampleStream(this.sampleStreams);
        this.hasEnabledTracks = false;
        int i3 = 0;
        while (true) {
            SampleStream[] sampleStreamArr = this.sampleStreams;
            if (i3 >= sampleStreamArr.length) {
                return jSelectTracks;
            }
            if (sampleStreamArr[i3] != null) {
                Assertions.checkState(trackSelectorResult.isRendererEnabled(i3));
                if (this.rendererCapabilities[i3].getTrackType() != -2) {
                    this.hasEnabledTracks = true;
                }
            } else {
                Assertions.checkState(trackSelectorResult.selections[i3] == null);
            }
            i3++;
        }
    }
}
