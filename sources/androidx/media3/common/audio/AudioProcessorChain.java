package androidx.media3.common.audio;

import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public interface AudioProcessorChain {
    PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters);

    boolean applySkipSilenceEnabled(boolean z2);

    AudioProcessor[] getAudioProcessors();

    long getMediaDuration(long j2);

    long getSkippedOutputFrameCount();
}
