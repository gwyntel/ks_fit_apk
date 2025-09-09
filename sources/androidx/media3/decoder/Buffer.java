package androidx.media3.decoder;

import androidx.annotation.CallSuper;
import androidx.media3.common.C;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public abstract class Buffer {
    private int flags;

    protected final boolean a(int i2) {
        return (this.flags & i2) == i2;
    }

    public final void addFlag(int i2) {
        this.flags = i2 | this.flags;
    }

    @CallSuper
    public void clear() {
        this.flags = 0;
    }

    public final void clearFlag(int i2) {
        this.flags = (~i2) & this.flags;
    }

    public final boolean hasSupplementalData() {
        return a(268435456);
    }

    public final boolean isEndOfStream() {
        return a(4);
    }

    public final boolean isFirstSample() {
        return a(C.BUFFER_FLAG_FIRST_SAMPLE);
    }

    public final boolean isKeyFrame() {
        return a(1);
    }

    public final boolean isLastSample() {
        return a(C.BUFFER_FLAG_LAST_SAMPLE);
    }

    public final void setFlags(int i2) {
        this.flags = i2;
    }
}
