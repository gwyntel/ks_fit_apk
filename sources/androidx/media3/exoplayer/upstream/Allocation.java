package androidx.media3.exoplayer.upstream;

import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes2.dex */
public final class Allocation {
    public final byte[] data;
    public final int offset;

    public Allocation(byte[] bArr, int i2) {
        this.data = bArr;
        this.offset = i2;
    }
}
