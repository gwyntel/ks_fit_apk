package androidx.media3.extractor.mp4;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.SniffFailure;

@UnstableApi
/* loaded from: classes2.dex */
public final class AtomSizeTooSmallSniffFailure implements SniffFailure {
    public final long atomSize;
    public final int atomType;
    public final int minimumHeaderSize;

    public AtomSizeTooSmallSniffFailure(int i2, long j2, int i3) {
        this.atomType = i2;
        this.atomSize = j2;
        this.minimumHeaderSize = i3;
    }
}
