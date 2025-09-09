package androidx.core.util;

import android.util.SizeF;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.facebook.appevents.internal.ViewHierarchyConstants;

/* loaded from: classes.dex */
public final class SizeFCompat {
    private final float mHeight;
    private final float mWidth;

    @RequiresApi(21)
    private static final class Api21Impl {
        private Api21Impl() {
        }

        @NonNull
        @DoNotInline
        static SizeF a(@NonNull SizeFCompat sizeFCompat) {
            Preconditions.checkNotNull(sizeFCompat);
            return new SizeF(sizeFCompat.getWidth(), sizeFCompat.getHeight());
        }

        @NonNull
        @DoNotInline
        static SizeFCompat b(@NonNull SizeF sizeF) {
            Preconditions.checkNotNull(sizeF);
            return new SizeFCompat(sizeF.getWidth(), sizeF.getHeight());
        }
    }

    public SizeFCompat(float f2, float f3) {
        this.mWidth = Preconditions.checkArgumentFinite(f2, ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
        this.mHeight = Preconditions.checkArgumentFinite(f3, ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
    }

    @NonNull
    @RequiresApi(21)
    public static SizeFCompat toSizeFCompat(@NonNull SizeF sizeF) {
        return Api21Impl.b(sizeF);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizeFCompat)) {
            return false;
        }
        SizeFCompat sizeFCompat = (SizeFCompat) obj;
        return sizeFCompat.mWidth == this.mWidth && sizeFCompat.mHeight == this.mHeight;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.mWidth) ^ Float.floatToIntBits(this.mHeight);
    }

    @NonNull
    @RequiresApi(21)
    public SizeF toSizeF() {
        return Api21Impl.a(this);
    }

    @NonNull
    public String toString() {
        return this.mWidth + "x" + this.mHeight;
    }
}
