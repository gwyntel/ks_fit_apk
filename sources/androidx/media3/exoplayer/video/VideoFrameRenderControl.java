package androidx.media3.exoplayer.video;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.LongArrayQueue;
import androidx.media3.common.util.TimedValueQueue;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.video.VideoFrameReleaseControl;

/* loaded from: classes2.dex */
final class VideoFrameRenderControl {
    private final FrameRenderer frameRenderer;
    private long outputStreamOffsetUs;

    @Nullable
    private VideoSize pendingOutputVideoSize;
    private final VideoFrameReleaseControl videoFrameReleaseControl;
    private final VideoFrameReleaseControl.FrameReleaseInfo videoFrameReleaseInfo = new VideoFrameReleaseControl.FrameReleaseInfo();
    private final TimedValueQueue<VideoSize> videoSizeChanges = new TimedValueQueue<>();
    private final TimedValueQueue<Long> streamOffsets = new TimedValueQueue<>();
    private final LongArrayQueue presentationTimestampsUs = new LongArrayQueue();
    private VideoSize reportedVideoSize = VideoSize.UNKNOWN;
    private long lastPresentationTimeUs = C.TIME_UNSET;

    interface FrameRenderer {
        void dropFrame();

        void onVideoSizeChanged(VideoSize videoSize);

        void renderFrame(long j2, long j3, long j4, boolean z2);
    }

    public VideoFrameRenderControl(FrameRenderer frameRenderer, VideoFrameReleaseControl videoFrameReleaseControl) {
        this.frameRenderer = frameRenderer;
        this.videoFrameReleaseControl = videoFrameReleaseControl;
    }

    private void dropFrame() {
        Assertions.checkStateNotNull(Long.valueOf(this.presentationTimestampsUs.remove()));
        this.frameRenderer.dropFrame();
    }

    private static <T> T getLastAndClear(TimedValueQueue<T> timedValueQueue) {
        Assertions.checkArgument(timedValueQueue.size() > 0);
        while (timedValueQueue.size() > 1) {
            timedValueQueue.pollFirst();
        }
        return (T) Assertions.checkNotNull(timedValueQueue.pollFirst());
    }

    private boolean maybeUpdateOutputStreamOffset(long j2) {
        Long lPollFloor = this.streamOffsets.pollFloor(j2);
        if (lPollFloor == null || lPollFloor.longValue() == this.outputStreamOffsetUs) {
            return false;
        }
        this.outputStreamOffsetUs = lPollFloor.longValue();
        return true;
    }

    private boolean maybeUpdateVideoSize(long j2) {
        VideoSize videoSizePollFloor = this.videoSizeChanges.pollFloor(j2);
        if (videoSizePollFloor == null || videoSizePollFloor.equals(VideoSize.UNKNOWN) || videoSizePollFloor.equals(this.reportedVideoSize)) {
            return false;
        }
        this.reportedVideoSize = videoSizePollFloor;
        return true;
    }

    private void renderFrame(boolean z2) {
        long jLongValue = ((Long) Assertions.checkStateNotNull(Long.valueOf(this.presentationTimestampsUs.remove()))).longValue();
        if (maybeUpdateVideoSize(jLongValue)) {
            this.frameRenderer.onVideoSizeChanged(this.reportedVideoSize);
        }
        this.frameRenderer.renderFrame(z2 ? -1L : this.videoFrameReleaseInfo.getReleaseTimeNs(), jLongValue, this.outputStreamOffsetUs, this.videoFrameReleaseControl.onFrameReleasedIsFirstFrame());
    }

    public void flush() {
        this.presentationTimestampsUs.clear();
        this.lastPresentationTimeUs = C.TIME_UNSET;
        if (this.streamOffsets.size() > 0) {
            Long l2 = (Long) getLastAndClear(this.streamOffsets);
            l2.longValue();
            this.streamOffsets.add(0L, l2);
        }
        if (this.pendingOutputVideoSize != null) {
            this.videoSizeChanges.clear();
        } else if (this.videoSizeChanges.size() > 0) {
            this.pendingOutputVideoSize = (VideoSize) getLastAndClear(this.videoSizeChanges);
        }
    }

    public boolean hasReleasedFrame(long j2) {
        long j3 = this.lastPresentationTimeUs;
        return j3 != C.TIME_UNSET && j3 >= j2;
    }

    public boolean isReady() {
        return this.videoFrameReleaseControl.isReady(true);
    }

    public void onOutputFrameAvailableForRendering(long j2) {
        VideoSize videoSize = this.pendingOutputVideoSize;
        if (videoSize != null) {
            this.videoSizeChanges.add(j2, videoSize);
            this.pendingOutputVideoSize = null;
        }
        this.presentationTimestampsUs.add(j2);
    }

    public void onOutputSizeChanged(int i2, int i3) {
        VideoSize videoSize = new VideoSize(i2, i3);
        if (Util.areEqual(this.pendingOutputVideoSize, videoSize)) {
            return;
        }
        this.pendingOutputVideoSize = videoSize;
    }

    public void onStreamOffsetChange(long j2, long j3) {
        this.streamOffsets.add(j2, Long.valueOf(j3));
    }

    public void render(long j2, long j3) throws ExoPlaybackException {
        while (!this.presentationTimestampsUs.isEmpty()) {
            long jElement = this.presentationTimestampsUs.element();
            if (maybeUpdateOutputStreamOffset(jElement)) {
                this.videoFrameReleaseControl.onProcessedStreamChange();
            }
            int frameReleaseAction = this.videoFrameReleaseControl.getFrameReleaseAction(jElement, j2, j3, this.outputStreamOffsetUs, false, this.videoFrameReleaseInfo);
            if (frameReleaseAction == 0 || frameReleaseAction == 1) {
                this.lastPresentationTimeUs = jElement;
                renderFrame(frameReleaseAction == 0);
            } else if (frameReleaseAction != 2 && frameReleaseAction != 3 && frameReleaseAction != 4) {
                if (frameReleaseAction != 5) {
                    throw new IllegalStateException(String.valueOf(frameReleaseAction));
                }
                return;
            } else {
                this.lastPresentationTimeUs = jElement;
                dropFrame();
            }
        }
    }

    public void setPlaybackSpeed(@FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        Assertions.checkArgument(f2 > 0.0f);
        this.videoFrameReleaseControl.setPlaybackSpeed(f2);
    }
}
