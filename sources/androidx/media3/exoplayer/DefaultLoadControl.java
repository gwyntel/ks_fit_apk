package androidx.media3.exoplayer;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.LoadControl;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.exoplayer.upstream.DefaultAllocator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Iterator;

@UnstableApi
/* loaded from: classes.dex */
public class DefaultLoadControl implements LoadControl {
    public static final int DEFAULT_AUDIO_BUFFER_SIZE = 13107200;
    public static final int DEFAULT_BACK_BUFFER_DURATION_MS = 0;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 5000;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_MS = 2500;
    public static final int DEFAULT_CAMERA_MOTION_BUFFER_SIZE = 131072;
    public static final int DEFAULT_IMAGE_BUFFER_SIZE = 131072;
    public static final int DEFAULT_MAX_BUFFER_MS = 50000;
    public static final int DEFAULT_METADATA_BUFFER_SIZE = 131072;
    public static final int DEFAULT_MIN_BUFFER_MS = 50000;
    public static final int DEFAULT_MIN_BUFFER_SIZE = 13107200;
    public static final int DEFAULT_MUXED_BUFFER_SIZE = 144310272;
    public static final boolean DEFAULT_PRIORITIZE_TIME_OVER_SIZE_THRESHOLDS = false;
    public static final boolean DEFAULT_RETAIN_BACK_BUFFER_FROM_KEYFRAME = false;
    public static final int DEFAULT_TARGET_BUFFER_BYTES = -1;
    public static final int DEFAULT_TEXT_BUFFER_SIZE = 131072;
    public static final int DEFAULT_VIDEO_BUFFER_SIZE = 131072000;
    private final DefaultAllocator allocator;
    private final long backBufferDurationUs;
    private final long bufferForPlaybackAfterRebufferUs;
    private final long bufferForPlaybackUs;
    private final HashMap<PlayerId, PlayerLoadingState> loadingStates;
    private final long maxBufferUs;
    private final long minBufferUs;
    private final boolean prioritizeTimeOverSizeThresholds;
    private final boolean retainBackBufferFromKeyframe;
    private final int targetBufferBytesOverwrite;
    private long threadId;

    public static final class Builder {

        @Nullable
        private DefaultAllocator allocator;
        private boolean buildCalled;
        private int minBufferMs = 50000;
        private int maxBufferMs = 50000;
        private int bufferForPlaybackMs = DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS;
        private int bufferForPlaybackAfterRebufferMs = 5000;
        private int targetBufferBytes = -1;
        private boolean prioritizeTimeOverSizeThresholds = false;
        private int backBufferDurationMs = 0;
        private boolean retainBackBufferFromKeyframe = false;

        public DefaultLoadControl build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            if (this.allocator == null) {
                this.allocator = new DefaultAllocator(true, 65536);
            }
            return new DefaultLoadControl(this.allocator, this.minBufferMs, this.maxBufferMs, this.bufferForPlaybackMs, this.bufferForPlaybackAfterRebufferMs, this.targetBufferBytes, this.prioritizeTimeOverSizeThresholds, this.backBufferDurationMs, this.retainBackBufferFromKeyframe);
        }

        @CanIgnoreReturnValue
        public Builder setAllocator(DefaultAllocator defaultAllocator) {
            Assertions.checkState(!this.buildCalled);
            this.allocator = defaultAllocator;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setBackBuffer(int i2, boolean z2) {
            Assertions.checkState(!this.buildCalled);
            DefaultLoadControl.assertGreaterOrEqual(i2, 0, "backBufferDurationMs", "0");
            this.backBufferDurationMs = i2;
            this.retainBackBufferFromKeyframe = z2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setBufferDurationsMs(int i2, int i3, int i4, int i5) {
            Assertions.checkState(!this.buildCalled);
            DefaultLoadControl.assertGreaterOrEqual(i4, 0, "bufferForPlaybackMs", "0");
            DefaultLoadControl.assertGreaterOrEqual(i5, 0, "bufferForPlaybackAfterRebufferMs", "0");
            DefaultLoadControl.assertGreaterOrEqual(i2, i4, "minBufferMs", "bufferForPlaybackMs");
            DefaultLoadControl.assertGreaterOrEqual(i2, i5, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
            DefaultLoadControl.assertGreaterOrEqual(i3, i2, "maxBufferMs", "minBufferMs");
            this.minBufferMs = i2;
            this.maxBufferMs = i3;
            this.bufferForPlaybackMs = i4;
            this.bufferForPlaybackAfterRebufferMs = i5;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPrioritizeTimeOverSizeThresholds(boolean z2) {
            Assertions.checkState(!this.buildCalled);
            this.prioritizeTimeOverSizeThresholds = z2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setTargetBufferBytes(int i2) {
            Assertions.checkState(!this.buildCalled);
            this.targetBufferBytes = i2;
            return this;
        }
    }

    private static class PlayerLoadingState {
        public boolean isLoading;
        public int targetBufferBytes;

        private PlayerLoadingState() {
        }
    }

    public DefaultLoadControl() {
        this(new DefaultAllocator(true, 65536), 50000, 50000, DEFAULT_BUFFER_FOR_PLAYBACK_MS, 5000, -1, false, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void assertGreaterOrEqual(int i2, int i3, String str, String str2) {
        Assertions.checkArgument(i2 >= i3, str + " cannot be less than " + str2);
    }

    private static int getDefaultBufferSize(int i2) {
        switch (i2) {
            case -2:
                return 0;
            case -1:
            default:
                throw new IllegalArgumentException();
            case 0:
                return DEFAULT_MUXED_BUFFER_SIZE;
            case 1:
                return 13107200;
            case 2:
                return DEFAULT_VIDEO_BUFFER_SIZE;
            case 3:
            case 4:
            case 5:
            case 6:
                return 131072;
        }
    }

    private void removePlayer(PlayerId playerId) {
        if (this.loadingStates.remove(playerId) != null) {
            updateAllocator();
        }
    }

    private void resetPlayerLoadingState(PlayerId playerId) {
        PlayerLoadingState playerLoadingState = (PlayerLoadingState) Assertions.checkNotNull(this.loadingStates.get(playerId));
        int i2 = this.targetBufferBytesOverwrite;
        if (i2 == -1) {
            i2 = 13107200;
        }
        playerLoadingState.targetBufferBytes = i2;
        playerLoadingState.isLoading = false;
    }

    private void updateAllocator() {
        if (this.loadingStates.isEmpty()) {
            this.allocator.reset();
        } else {
            this.allocator.setTargetBufferSize(c());
        }
    }

    protected int b(Renderer[] rendererArr, ExoTrackSelection[] exoTrackSelectionArr) {
        int defaultBufferSize = 0;
        for (int i2 = 0; i2 < rendererArr.length; i2++) {
            if (exoTrackSelectionArr[i2] != null) {
                defaultBufferSize += getDefaultBufferSize(rendererArr[i2].getTrackType());
            }
        }
        return Math.max(13107200, defaultBufferSize);
    }

    int c() {
        Iterator<PlayerLoadingState> it = this.loadingStates.values().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            i2 += it.next().targetBufferBytes;
        }
        return i2;
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public Allocator getAllocator() {
        return this.allocator;
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ long getBackBufferDurationUs() {
        return c2.a(this);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ void onPrepared() {
        c2.c(this);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ void onReleased() {
        c2.e(this);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ void onStopped() {
        c2.g(this);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ void onTracksSelected(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        c2.i(this, timeline, mediaPeriodId, rendererArr, trackGroupArray, exoTrackSelectionArr);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ boolean retainBackBufferFromKeyframe() {
        return c2.l(this);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ boolean shouldContinueLoading(long j2, long j3, float f2) {
        return c2.n(this, j2, j3, f2);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ boolean shouldStartPlayback(long j2, float f2, boolean z2, long j3) {
        return c2.p(this, j2, f2, z2, j3);
    }

    protected DefaultLoadControl(DefaultAllocator defaultAllocator, int i2, int i3, int i4, int i5, int i6, boolean z2, int i7, boolean z3) {
        assertGreaterOrEqual(i4, 0, "bufferForPlaybackMs", "0");
        assertGreaterOrEqual(i5, 0, "bufferForPlaybackAfterRebufferMs", "0");
        assertGreaterOrEqual(i2, i4, "minBufferMs", "bufferForPlaybackMs");
        assertGreaterOrEqual(i2, i5, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
        assertGreaterOrEqual(i3, i2, "maxBufferMs", "minBufferMs");
        assertGreaterOrEqual(i7, 0, "backBufferDurationMs", "0");
        this.allocator = defaultAllocator;
        this.minBufferUs = Util.msToUs(i2);
        this.maxBufferUs = Util.msToUs(i3);
        this.bufferForPlaybackUs = Util.msToUs(i4);
        this.bufferForPlaybackAfterRebufferUs = Util.msToUs(i5);
        this.targetBufferBytesOverwrite = i6;
        this.prioritizeTimeOverSizeThresholds = z2;
        this.backBufferDurationUs = Util.msToUs(i7);
        this.retainBackBufferFromKeyframe = z3;
        this.loadingStates = new HashMap<>();
        this.threadId = -1L;
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public long getBackBufferDurationUs(PlayerId playerId) {
        return this.backBufferDurationUs;
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public void onPrepared(PlayerId playerId) {
        long id = Thread.currentThread().getId();
        long j2 = this.threadId;
        Assertions.checkState(j2 == -1 || j2 == id, "Players that share the same LoadControl must share the same playback thread. See ExoPlayer.Builder.setPlaybackLooper(Looper).");
        this.threadId = id;
        if (!this.loadingStates.containsKey(playerId)) {
            this.loadingStates.put(playerId, new PlayerLoadingState());
        }
        resetPlayerLoadingState(playerId);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public void onReleased(PlayerId playerId) {
        removePlayer(playerId);
        if (this.loadingStates.isEmpty()) {
            this.threadId = -1L;
        }
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public void onStopped(PlayerId playerId) {
        removePlayer(playerId);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ void onTracksSelected(Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        c2.k(this, rendererArr, trackGroupArray, exoTrackSelectionArr);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public boolean retainBackBufferFromKeyframe(PlayerId playerId) {
        return this.retainBackBufferFromKeyframe;
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public boolean shouldContinueLoading(LoadControl.Parameters parameters) {
        PlayerLoadingState playerLoadingState = (PlayerLoadingState) Assertions.checkNotNull(this.loadingStates.get(parameters.playerId));
        boolean z2 = true;
        boolean z3 = this.allocator.getTotalBytesAllocated() >= c();
        long jMin = this.minBufferUs;
        float f2 = parameters.playbackSpeed;
        if (f2 > 1.0f) {
            jMin = Math.min(Util.getMediaDurationForPlayoutDuration(jMin, f2), this.maxBufferUs);
        }
        long jMax = Math.max(jMin, 500000L);
        long j2 = parameters.bufferedDurationUs;
        if (j2 < jMax) {
            if (!this.prioritizeTimeOverSizeThresholds && z3) {
                z2 = false;
            }
            playerLoadingState.isLoading = z2;
            if (!z2 && j2 < 500000) {
                Log.w("DefaultLoadControl", "Target buffer size reached with less than 500ms of buffered media data.");
            }
        } else if (j2 >= this.maxBufferUs || z3) {
            playerLoadingState.isLoading = false;
        }
        return playerLoadingState.isLoading;
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public /* synthetic */ boolean shouldStartPlayback(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j2, float f2, boolean z2, long j3) {
        return c2.q(this, timeline, mediaPeriodId, j2, f2, z2, j3);
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public void onTracksSelected(PlayerId playerId, Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        PlayerLoadingState playerLoadingState = (PlayerLoadingState) Assertions.checkNotNull(this.loadingStates.get(playerId));
        int iB = this.targetBufferBytesOverwrite;
        if (iB == -1) {
            iB = b(rendererArr, exoTrackSelectionArr);
        }
        playerLoadingState.targetBufferBytes = iB;
        updateAllocator();
    }

    @Override // androidx.media3.exoplayer.LoadControl
    public boolean shouldStartPlayback(LoadControl.Parameters parameters) {
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(parameters.bufferedDurationUs, parameters.playbackSpeed);
        long jMin = parameters.rebuffering ? this.bufferForPlaybackAfterRebufferUs : this.bufferForPlaybackUs;
        long j2 = parameters.targetLiveOffsetUs;
        if (j2 != C.TIME_UNSET) {
            jMin = Math.min(j2 / 2, jMin);
        }
        return jMin <= 0 || playoutDurationForMediaDuration >= jMin || (!this.prioritizeTimeOverSizeThresholds && this.allocator.getTotalBytesAllocated() >= c());
    }
}
