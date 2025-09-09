package aisble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl;

/* loaded from: classes.dex */
public interface DataSplitter {
    @Nullable
    byte[] chunk(@NonNull byte[] bArr, @IntRange(from = 0) int i2, @IntRange(from = DefaultLivePlaybackSpeedControl.DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED) int i3);
}
