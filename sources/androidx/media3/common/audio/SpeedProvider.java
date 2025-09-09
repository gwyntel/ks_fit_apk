package androidx.media3.common.audio;

import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public interface SpeedProvider {
    long getNextSpeedChangeTimeUs(long j2);

    float getSpeed(long j2);
}
