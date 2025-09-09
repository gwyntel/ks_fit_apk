package androidx.media3.common.util;

import androidx.annotation.Nullable;

@UnstableApi
/* loaded from: classes.dex */
public final class Size {
    public static final Size UNKNOWN = new Size(-1, -1);
    public static final Size ZERO = new Size(0, 0);
    private final int height;
    private final int width;

    public Size(int i2, int i3) {
        Assertions.checkArgument((i2 == -1 || i2 >= 0) && (i3 == -1 || i3 >= 0));
        this.width = i2;
        this.height = i3;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Size)) {
            return false;
        }
        Size size = (Size) obj;
        return this.width == size.width && this.height == size.height;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        int i2 = this.height;
        int i3 = this.width;
        return i2 ^ ((i3 >>> 16) | (i3 << 16));
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}
