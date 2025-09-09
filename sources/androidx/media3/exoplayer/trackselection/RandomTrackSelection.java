package androidx.media3.exoplayer.trackselection;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.chunk.MediaChunk;
import androidx.media3.exoplayer.source.chunk.MediaChunkIterator;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.trackselection.TrackSelectionUtil;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import java.util.List;
import java.util.Random;

@UnstableApi
/* loaded from: classes2.dex */
public final class RandomTrackSelection extends BaseTrackSelection {
    private final Random random;
    private int selectedIndex;

    public RandomTrackSelection(TrackGroup trackGroup, int[] iArr, int i2, Random random) {
        super(trackGroup, iArr, i2);
        this.random = random;
        this.selectedIndex = random.nextInt(this.f5504b);
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
        return 3;
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public void updateSelectedTrack(long j2, long j3, long j4, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int i2 = 0;
        for (int i3 = 0; i3 < this.f5504b; i3++) {
            if (!isTrackExcluded(i3, jElapsedRealtime)) {
                i2++;
            }
        }
        this.selectedIndex = this.random.nextInt(i2);
        if (i2 != this.f5504b) {
            int i4 = 0;
            for (int i5 = 0; i5 < this.f5504b; i5++) {
                if (!isTrackExcluded(i5, jElapsedRealtime)) {
                    int i6 = i4 + 1;
                    if (this.selectedIndex == i4) {
                        this.selectedIndex = i5;
                        return;
                    }
                    i4 = i6;
                }
            }
        }
    }

    public static final class Factory implements ExoTrackSelection.Factory {
        private final Random random;

        public Factory() {
            this.random = new Random();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ExoTrackSelection lambda$createTrackSelections$0(ExoTrackSelection.Definition definition) {
            return new RandomTrackSelection(definition.group, definition.tracks, definition.type, this.random);
        }

        @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection.Factory
        public ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
            return TrackSelectionUtil.createTrackSelectionsForDefinitions(definitionArr, new TrackSelectionUtil.AdaptiveTrackSelectionFactory() { // from class: androidx.media3.exoplayer.trackselection.z
                @Override // androidx.media3.exoplayer.trackselection.TrackSelectionUtil.AdaptiveTrackSelectionFactory
                public final ExoTrackSelection createAdaptiveTrackSelection(ExoTrackSelection.Definition definition) {
                    return this.f5519a.lambda$createTrackSelections$0(definition);
                }
            });
        }

        public Factory(int i2) {
            this.random = new Random(i2);
        }
    }
}
