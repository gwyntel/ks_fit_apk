package androidx.media3.exoplayer.trackselection;

import androidx.annotation.Nullable;
import androidx.media3.common.Format;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.chunk.Chunk;
import androidx.media3.exoplayer.source.chunk.MediaChunk;
import androidx.media3.exoplayer.source.chunk.MediaChunkIterator;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public interface ExoTrackSelection extends TrackSelection {

    public static final class Definition {
        private static final String TAG = "ETSDefinition";
        public final TrackGroup group;
        public final int[] tracks;
        public final int type;

        public Definition(TrackGroup trackGroup, int... iArr) {
            this(trackGroup, iArr, 0);
        }

        public Definition(TrackGroup trackGroup, int[] iArr, int i2) {
            if (iArr.length == 0) {
                Log.e(TAG, "Empty tracks are not allowed", new IllegalArgumentException());
            }
            this.group = trackGroup;
            this.tracks = iArr;
            this.type = i2;
        }
    }

    public interface Factory {
        ExoTrackSelection[] createTrackSelections(Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline);
    }

    void disable();

    void enable();

    int evaluateQueueSize(long j2, List<? extends MediaChunk> list);

    boolean excludeTrack(int i2, long j2);

    long getLatestBitrateEstimate();

    Format getSelectedFormat();

    int getSelectedIndex();

    int getSelectedIndexInTrackGroup();

    @Nullable
    Object getSelectionData();

    int getSelectionReason();

    boolean isTrackExcluded(int i2, long j2);

    void onDiscontinuity();

    void onPlayWhenReadyChanged(boolean z2);

    void onPlaybackSpeed(float f2);

    void onRebuffer();

    boolean shouldCancelChunkLoad(long j2, Chunk chunk, List<? extends MediaChunk> list);

    void updateSelectedTrack(long j2, long j3, long j4, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr);
}
