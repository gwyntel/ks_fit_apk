package aisble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl;

/* loaded from: classes.dex */
public final class DefaultMtuSplitter implements DataSplitter {
    @Override // aisble.data.DataSplitter
    @Nullable
    public byte[] chunk(@NonNull byte[] bArr, @IntRange(from = 0) int i2, @IntRange(from = DefaultLivePlaybackSpeedControl.DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED) int i3) {
        int i4 = i2 * i3;
        int iMin = Math.min(i3, bArr.length - i4);
        if (iMin <= 0) {
            return null;
        }
        byte[] bArr2 = new byte[iMin];
        System.arraycopy(bArr, i4, bArr2, 0, iMin);
        return bArr2;
    }
}
