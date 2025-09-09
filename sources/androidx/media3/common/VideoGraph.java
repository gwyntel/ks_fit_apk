package androidx.media3.common;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public interface VideoGraph {

    @UnstableApi
    public interface Listener {
        void onEnded(long j2);

        void onError(VideoFrameProcessingException videoFrameProcessingException);

        void onOutputFrameAvailableForRendering(long j2);

        void onOutputSizeChanged(int i2, int i3);
    }

    VideoFrameProcessor getProcessor(int i2);

    boolean hasProducedFrameWithTimestampZero();

    void initialize() throws VideoFrameProcessingException;

    void registerInput(@IntRange(from = 0) int i2) throws VideoFrameProcessingException;

    void release();

    void setOutputSurfaceInfo(@Nullable SurfaceInfo surfaceInfo);
}
