package androidx.media3.exoplayer.trackselection;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.chunk.MediaChunk;
import androidx.media3.exoplayer.source.chunk.MediaChunkIterator;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public class AdaptiveTrackSelection extends BaseTrackSelection {
    public static final float DEFAULT_BANDWIDTH_FRACTION = 0.7f;
    public static final float DEFAULT_BUFFERED_FRACTION_TO_LIVE_EDGE_FOR_QUALITY_INCREASE = 0.75f;
    public static final int DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS = 25000;
    public static final int DEFAULT_MAX_HEIGHT_TO_DISCARD = 719;
    public static final int DEFAULT_MAX_WIDTH_TO_DISCARD = 1279;
    public static final int DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS = 10000;
    public static final int DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS = 25000;
    private static final long MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS = 1000;
    private static final String TAG = "AdaptiveTrackSelection";
    private final ImmutableList<AdaptationCheckpoint> adaptationCheckpoints;
    private final float bandwidthFraction;
    private final BandwidthMeter bandwidthMeter;
    private final float bufferedFractionToLiveEdgeForQualityIncrease;
    private final Clock clock;

    @Nullable
    private MediaChunk lastBufferEvaluationMediaChunk;
    private long lastBufferEvaluationMs;
    private long latestBitrateEstimate;
    private final long maxDurationForQualityDecreaseUs;
    private final int maxHeightToDiscard;
    private final int maxWidthToDiscard;
    private final long minDurationForQualityIncreaseUs;
    private final long minDurationToRetainAfterDiscardUs;
    private float playbackSpeed;
    private int reason;
    private int selectedIndex;

    public static final class AdaptationCheckpoint {
        public final long allocatedBandwidth;
        public final long totalBandwidth;

        public AdaptationCheckpoint(long j2, long j3) {
            this.totalBandwidth = j2;
            this.allocatedBandwidth = j3;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdaptationCheckpoint)) {
                return false;
            }
            AdaptationCheckpoint adaptationCheckpoint = (AdaptationCheckpoint) obj;
            return this.totalBandwidth == adaptationCheckpoint.totalBandwidth && this.allocatedBandwidth == adaptationCheckpoint.allocatedBandwidth;
        }

        public int hashCode() {
            return (((int) this.totalBandwidth) * 31) + ((int) this.allocatedBandwidth);
        }
    }

    public static class Factory implements ExoTrackSelection.Factory {
        private final float bandwidthFraction;
        private final float bufferedFractionToLiveEdgeForQualityIncrease;
        private final Clock clock;
        private final int maxDurationForQualityDecreaseMs;
        private final int maxHeightToDiscard;
        private final int maxWidthToDiscard;
        private final int minDurationForQualityIncreaseMs;
        private final int minDurationToRetainAfterDiscardMs;

        public Factory() {
            this(10000, 25000, 25000, 0.7f);
        }

        protected AdaptiveTrackSelection a(TrackGroup trackGroup, int[] iArr, int i2, BandwidthMeter bandwidthMeter, ImmutableList immutableList) {
            return new AdaptiveTrackSelection(trackGroup, iArr, i2, bandwidthMeter, this.minDurationForQualityIncreaseMs, this.maxDurationForQualityDecreaseMs, this.minDurationToRetainAfterDiscardMs, this.maxWidthToDiscard, this.maxHeightToDiscard, this.bandwidthFraction, this.bufferedFractionToLiveEdgeForQualityIncrease, immutableList, this.clock);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection.Factory
        public final ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
            ImmutableList adaptationCheckpoints = AdaptiveTrackSelection.getAdaptationCheckpoints(definitionArr);
            ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr.length];
            for (int i2 = 0; i2 < definitionArr.length; i2++) {
                ExoTrackSelection.Definition definition = definitionArr[i2];
                if (definition != null) {
                    int[] iArr = definition.tracks;
                    if (iArr.length != 0) {
                        exoTrackSelectionArr[i2] = iArr.length == 1 ? new FixedTrackSelection(definition.group, iArr[0], definition.type) : a(definition.group, iArr, definition.type, bandwidthMeter, (ImmutableList) adaptationCheckpoints.get(i2));
                    }
                }
            }
            return exoTrackSelectionArr;
        }

        public Factory(int i2, int i3, int i4, float f2) {
            this(i2, i3, i4, AdaptiveTrackSelection.DEFAULT_MAX_WIDTH_TO_DISCARD, AdaptiveTrackSelection.DEFAULT_MAX_HEIGHT_TO_DISCARD, f2, 0.75f, Clock.DEFAULT);
        }

        public Factory(int i2, int i3, int i4, int i5, int i6, float f2) {
            this(i2, i3, i4, i5, i6, f2, 0.75f, Clock.DEFAULT);
        }

        public Factory(int i2, int i3, int i4, float f2, float f3, Clock clock) {
            this(i2, i3, i4, AdaptiveTrackSelection.DEFAULT_MAX_WIDTH_TO_DISCARD, AdaptiveTrackSelection.DEFAULT_MAX_HEIGHT_TO_DISCARD, f2, f3, clock);
        }

        public Factory(int i2, int i3, int i4, int i5, int i6, float f2, float f3, Clock clock) {
            this.minDurationForQualityIncreaseMs = i2;
            this.maxDurationForQualityDecreaseMs = i3;
            this.minDurationToRetainAfterDiscardMs = i4;
            this.maxWidthToDiscard = i5;
            this.maxHeightToDiscard = i6;
            this.bandwidthFraction = f2;
            this.bufferedFractionToLiveEdgeForQualityIncrease = f3;
            this.clock = clock;
        }
    }

    public AdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, BandwidthMeter bandwidthMeter) {
        this(trackGroup, iArr, 0, bandwidthMeter, 10000L, 25000L, 25000L, DEFAULT_MAX_WIDTH_TO_DISCARD, DEFAULT_MAX_HEIGHT_TO_DISCARD, 0.7f, 0.75f, ImmutableList.of(), Clock.DEFAULT);
    }

    private static void addCheckpoint(List<ImmutableList.Builder<AdaptationCheckpoint>> list, long[] jArr) {
        long j2 = 0;
        for (long j3 : jArr) {
            j2 += j3;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ImmutableList.Builder<AdaptationCheckpoint> builder = list.get(i2);
            if (builder != null) {
                builder.add((ImmutableList.Builder<AdaptationCheckpoint>) new AdaptationCheckpoint(j2, jArr[i2]));
            }
        }
    }

    private int determineIdealSelectedIndex(long j2, long j3) {
        long allocatedBandwidth = getAllocatedBandwidth(j3);
        int i2 = 0;
        for (int i3 = 0; i3 < this.f5504b; i3++) {
            if (j2 == Long.MIN_VALUE || !isTrackExcluded(i3, j2)) {
                Format format = getFormat(i3);
                if (c(format, format.bitrate, allocatedBandwidth)) {
                    return i3;
                }
                i2 = i3;
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImmutableList<ImmutableList<AdaptationCheckpoint>> getAdaptationCheckpoints(ExoTrackSelection.Definition[] definitionArr) {
        ArrayList arrayList = new ArrayList();
        for (ExoTrackSelection.Definition definition : definitionArr) {
            if (definition == null || definition.tracks.length <= 1) {
                arrayList.add(null);
            } else {
                ImmutableList.Builder builder = ImmutableList.builder();
                builder.add((ImmutableList.Builder) new AdaptationCheckpoint(0L, 0L));
                arrayList.add(builder);
            }
        }
        long[][] sortedTrackBitrates = getSortedTrackBitrates(definitionArr);
        int[] iArr = new int[sortedTrackBitrates.length];
        long[] jArr = new long[sortedTrackBitrates.length];
        for (int i2 = 0; i2 < sortedTrackBitrates.length; i2++) {
            long[] jArr2 = sortedTrackBitrates[i2];
            jArr[i2] = jArr2.length == 0 ? 0L : jArr2[0];
        }
        addCheckpoint(arrayList, jArr);
        ImmutableList<Integer> switchOrder = getSwitchOrder(sortedTrackBitrates);
        for (int i3 = 0; i3 < switchOrder.size(); i3++) {
            int iIntValue = switchOrder.get(i3).intValue();
            int i4 = iArr[iIntValue] + 1;
            iArr[iIntValue] = i4;
            jArr[iIntValue] = sortedTrackBitrates[iIntValue][i4];
            addCheckpoint(arrayList, jArr);
        }
        for (int i5 = 0; i5 < definitionArr.length; i5++) {
            if (arrayList.get(i5) != null) {
                jArr[i5] = jArr[i5] * 2;
            }
        }
        addCheckpoint(arrayList, jArr);
        ImmutableList.Builder builder2 = ImmutableList.builder();
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            ImmutableList.Builder builder3 = (ImmutableList.Builder) arrayList.get(i6);
            builder2.add((ImmutableList.Builder) (builder3 == null ? ImmutableList.of() : builder3.build()));
        }
        return builder2.build();
    }

    private long getAllocatedBandwidth(long j2) {
        long totalAllocatableBandwidth = getTotalAllocatableBandwidth(j2);
        if (this.adaptationCheckpoints.isEmpty()) {
            return totalAllocatableBandwidth;
        }
        int i2 = 1;
        while (i2 < this.adaptationCheckpoints.size() - 1 && this.adaptationCheckpoints.get(i2).totalBandwidth < totalAllocatableBandwidth) {
            i2++;
        }
        AdaptationCheckpoint adaptationCheckpoint = this.adaptationCheckpoints.get(i2 - 1);
        AdaptationCheckpoint adaptationCheckpoint2 = this.adaptationCheckpoints.get(i2);
        long j3 = adaptationCheckpoint.totalBandwidth;
        float f2 = (totalAllocatableBandwidth - j3) / (adaptationCheckpoint2.totalBandwidth - j3);
        return adaptationCheckpoint.allocatedBandwidth + ((long) (f2 * (adaptationCheckpoint2.allocatedBandwidth - r2)));
    }

    private long getLastChunkDurationUs(List<? extends MediaChunk> list) {
        if (list.isEmpty()) {
            return C.TIME_UNSET;
        }
        MediaChunk mediaChunk = (MediaChunk) Iterables.getLast(list);
        long j2 = mediaChunk.startTimeUs;
        if (j2 == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long j3 = mediaChunk.endTimeUs;
        return j3 != C.TIME_UNSET ? j3 - j2 : C.TIME_UNSET;
    }

    private long getNextChunkDurationUs(MediaChunkIterator[] mediaChunkIteratorArr, List<? extends MediaChunk> list) {
        int i2 = this.selectedIndex;
        if (i2 < mediaChunkIteratorArr.length && mediaChunkIteratorArr[i2].next()) {
            MediaChunkIterator mediaChunkIterator = mediaChunkIteratorArr[this.selectedIndex];
            return mediaChunkIterator.getChunkEndTimeUs() - mediaChunkIterator.getChunkStartTimeUs();
        }
        for (MediaChunkIterator mediaChunkIterator2 : mediaChunkIteratorArr) {
            if (mediaChunkIterator2.next()) {
                return mediaChunkIterator2.getChunkEndTimeUs() - mediaChunkIterator2.getChunkStartTimeUs();
            }
        }
        return getLastChunkDurationUs(list);
    }

    private static long[][] getSortedTrackBitrates(ExoTrackSelection.Definition[] definitionArr) {
        long[][] jArr = new long[definitionArr.length][];
        for (int i2 = 0; i2 < definitionArr.length; i2++) {
            ExoTrackSelection.Definition definition = definitionArr[i2];
            if (definition == null) {
                jArr[i2] = new long[0];
            } else {
                jArr[i2] = new long[definition.tracks.length];
                int i3 = 0;
                while (true) {
                    int[] iArr = definition.tracks;
                    if (i3 >= iArr.length) {
                        break;
                    }
                    long j2 = definition.group.getFormat(iArr[i3]).bitrate;
                    long[] jArr2 = jArr[i2];
                    if (j2 == -1) {
                        j2 = 0;
                    }
                    jArr2[i3] = j2;
                    i3++;
                }
                Arrays.sort(jArr[i2]);
            }
        }
        return jArr;
    }

    private static ImmutableList<Integer> getSwitchOrder(long[][] jArr) {
        Multimap multimapBuild = MultimapBuilder.treeKeys().arrayListValues().build();
        for (int i2 = 0; i2 < jArr.length; i2++) {
            long[] jArr2 = jArr[i2];
            if (jArr2.length > 1) {
                int length = jArr2.length;
                double[] dArr = new double[length];
                int i3 = 0;
                while (true) {
                    long[] jArr3 = jArr[i2];
                    double dLog = 0.0d;
                    if (i3 >= jArr3.length) {
                        break;
                    }
                    long j2 = jArr3[i3];
                    if (j2 != -1) {
                        dLog = Math.log(j2);
                    }
                    dArr[i3] = dLog;
                    i3++;
                }
                int i4 = length - 1;
                double d2 = dArr[i4] - dArr[0];
                int i5 = 0;
                while (i5 < i4) {
                    double d3 = dArr[i5];
                    i5++;
                    multimapBuild.put(Double.valueOf(d2 == 0.0d ? 1.0d : (((d3 + dArr[i5]) * 0.5d) - dArr[0]) / d2), Integer.valueOf(i2));
                }
            }
        }
        return ImmutableList.copyOf(multimapBuild.values());
    }

    private long getTotalAllocatableBandwidth(long j2) {
        long bitrateEstimate = this.bandwidthMeter.getBitrateEstimate();
        this.latestBitrateEstimate = bitrateEstimate;
        long j3 = (long) (bitrateEstimate * this.bandwidthFraction);
        long timeToFirstByteEstimateUs = this.bandwidthMeter.getTimeToFirstByteEstimateUs();
        if (timeToFirstByteEstimateUs == C.TIME_UNSET || j2 == C.TIME_UNSET) {
            return (long) (j3 / this.playbackSpeed);
        }
        float f2 = j2;
        return (long) ((j3 * Math.max((f2 / this.playbackSpeed) - timeToFirstByteEstimateUs, 0.0f)) / f2);
    }

    private long minDurationForQualityIncreaseUs(long j2, long j3) {
        if (j2 == C.TIME_UNSET) {
            return this.minDurationForQualityIncreaseUs;
        }
        if (j3 != C.TIME_UNSET) {
            j2 -= j3;
        }
        return Math.min((long) (j2 * this.bufferedFractionToLiveEdgeForQualityIncrease), this.minDurationForQualityIncreaseUs);
    }

    protected boolean c(Format format, int i2, long j2) {
        return ((long) i2) <= j2;
    }

    protected long d() {
        return this.minDurationToRetainAfterDiscardUs;
    }

    @Override // androidx.media3.exoplayer.trackselection.BaseTrackSelection, androidx.media3.exoplayer.trackselection.ExoTrackSelection
    @CallSuper
    public void disable() {
        this.lastBufferEvaluationMediaChunk = null;
    }

    protected boolean e(long j2, List list) {
        long j3 = this.lastBufferEvaluationMs;
        return j3 == C.TIME_UNSET || j2 - j3 >= 1000 || !(list.isEmpty() || ((MediaChunk) Iterables.getLast(list)).equals(this.lastBufferEvaluationMediaChunk));
    }

    @Override // androidx.media3.exoplayer.trackselection.BaseTrackSelection, androidx.media3.exoplayer.trackselection.ExoTrackSelection
    @CallSuper
    public void enable() {
        this.lastBufferEvaluationMs = C.TIME_UNSET;
        this.lastBufferEvaluationMediaChunk = null;
    }

    @Override // androidx.media3.exoplayer.trackselection.BaseTrackSelection, androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public int evaluateQueueSize(long j2, List<? extends MediaChunk> list) {
        int i2;
        int i3;
        long jElapsedRealtime = this.clock.elapsedRealtime();
        if (!e(jElapsedRealtime, list)) {
            return list.size();
        }
        this.lastBufferEvaluationMs = jElapsedRealtime;
        this.lastBufferEvaluationMediaChunk = list.isEmpty() ? null : (MediaChunk) Iterables.getLast(list);
        if (list.isEmpty()) {
            return 0;
        }
        int size = list.size();
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(list.get(size - 1).startTimeUs - j2, this.playbackSpeed);
        long jD = d();
        if (playoutDurationForMediaDuration < jD) {
            return size;
        }
        Format format = getFormat(determineIdealSelectedIndex(jElapsedRealtime, getLastChunkDurationUs(list)));
        for (int i4 = 0; i4 < size; i4++) {
            MediaChunk mediaChunk = list.get(i4);
            Format format2 = mediaChunk.trackFormat;
            if (Util.getPlayoutDurationForMediaDuration(mediaChunk.startTimeUs - j2, this.playbackSpeed) >= jD && format2.bitrate < format.bitrate && (i2 = format2.height) != -1 && i2 <= this.maxHeightToDiscard && (i3 = format2.width) != -1 && i3 <= this.maxWidthToDiscard && i2 < format.height) {
                return i4;
            }
        }
        return size;
    }

    @Override // androidx.media3.exoplayer.trackselection.BaseTrackSelection, androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public long getLatestBitrateEstimate() {
        return this.latestBitrateEstimate;
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    @Nullable
    public Object getSelectionData() {
        return null;
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public int getSelectionReason() {
        return this.reason;
    }

    @Override // androidx.media3.exoplayer.trackselection.BaseTrackSelection, androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public void onPlaybackSpeed(float f2) {
        this.playbackSpeed = f2;
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public void updateSelectedTrack(long j2, long j3, long j4, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
        long jElapsedRealtime = this.clock.elapsedRealtime();
        long nextChunkDurationUs = getNextChunkDurationUs(mediaChunkIteratorArr, list);
        int i2 = this.reason;
        if (i2 == 0) {
            this.reason = 1;
            this.selectedIndex = determineIdealSelectedIndex(jElapsedRealtime, nextChunkDurationUs);
            return;
        }
        int i3 = this.selectedIndex;
        int iIndexOf = list.isEmpty() ? -1 : indexOf(((MediaChunk) Iterables.getLast(list)).trackFormat);
        if (iIndexOf != -1) {
            i2 = ((MediaChunk) Iterables.getLast(list)).trackSelectionReason;
            i3 = iIndexOf;
        }
        int iDetermineIdealSelectedIndex = determineIdealSelectedIndex(jElapsedRealtime, nextChunkDurationUs);
        if (iDetermineIdealSelectedIndex != i3 && !isTrackExcluded(i3, jElapsedRealtime)) {
            Format format = getFormat(i3);
            Format format2 = getFormat(iDetermineIdealSelectedIndex);
            long jMinDurationForQualityIncreaseUs = minDurationForQualityIncreaseUs(j4, nextChunkDurationUs);
            int i4 = format2.bitrate;
            int i5 = format.bitrate;
            if ((i4 > i5 && j3 < jMinDurationForQualityIncreaseUs) || (i4 < i5 && j3 >= this.maxDurationForQualityDecreaseUs)) {
                iDetermineIdealSelectedIndex = i3;
            }
        }
        if (iDetermineIdealSelectedIndex != i3) {
            i2 = 3;
        }
        this.reason = i2;
        this.selectedIndex = iDetermineIdealSelectedIndex;
    }

    protected AdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, int i2, BandwidthMeter bandwidthMeter, long j2, long j3, long j4, int i3, int i4, float f2, float f3, List list, Clock clock) {
        BandwidthMeter bandwidthMeter2;
        long j5;
        super(trackGroup, iArr, i2);
        if (j4 < j2) {
            Log.w(TAG, "Adjusting minDurationToRetainAfterDiscardMs to be at least minDurationForQualityIncreaseMs");
            bandwidthMeter2 = bandwidthMeter;
            j5 = j2;
        } else {
            bandwidthMeter2 = bandwidthMeter;
            j5 = j4;
        }
        this.bandwidthMeter = bandwidthMeter2;
        this.minDurationForQualityIncreaseUs = j2 * 1000;
        this.maxDurationForQualityDecreaseUs = j3 * 1000;
        this.minDurationToRetainAfterDiscardUs = j5 * 1000;
        this.maxWidthToDiscard = i3;
        this.maxHeightToDiscard = i4;
        this.bandwidthFraction = f2;
        this.bufferedFractionToLiveEdgeForQualityIncrease = f3;
        this.adaptationCheckpoints = ImmutableList.copyOf((Collection) list);
        this.clock = clock;
        this.playbackSpeed = 1.0f;
        this.reason = 0;
        this.lastBufferEvaluationMs = C.TIME_UNSET;
        this.latestBitrateEstimate = -2147483647L;
    }
}
