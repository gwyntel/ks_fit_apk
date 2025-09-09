package androidx.core.graphics;

import android.graphics.Rect;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

/* loaded from: classes.dex */
public final class Insets {

    @NonNull
    public static final Insets NONE = new Insets(0, 0, 0, 0);
    public final int bottom;
    public final int left;
    public final int right;

    /* renamed from: top, reason: collision with root package name */
    public final int f3560top;

    @RequiresApi(29)
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static android.graphics.Insets a(int i2, int i3, int i4, int i5) {
            return android.graphics.Insets.of(i2, i3, i4, i5);
        }
    }

    private Insets(int i2, int i3, int i4, int i5) {
        this.left = i2;
        this.f3560top = i3;
        this.right = i4;
        this.bottom = i5;
    }

    @NonNull
    public static Insets add(@NonNull Insets insets, @NonNull Insets insets2) {
        return of(insets.left + insets2.left, insets.f3560top + insets2.f3560top, insets.right + insets2.right, insets.bottom + insets2.bottom);
    }

    @NonNull
    public static Insets max(@NonNull Insets insets, @NonNull Insets insets2) {
        return of(Math.max(insets.left, insets2.left), Math.max(insets.f3560top, insets2.f3560top), Math.max(insets.right, insets2.right), Math.max(insets.bottom, insets2.bottom));
    }

    @NonNull
    public static Insets min(@NonNull Insets insets, @NonNull Insets insets2) {
        return of(Math.min(insets.left, insets2.left), Math.min(insets.f3560top, insets2.f3560top), Math.min(insets.right, insets2.right), Math.min(insets.bottom, insets2.bottom));
    }

    @NonNull
    public static Insets of(int i2, int i3, int i4, int i5) {
        return (i2 == 0 && i3 == 0 && i4 == 0 && i5 == 0) ? NONE : new Insets(i2, i3, i4, i5);
    }

    @NonNull
    public static Insets subtract(@NonNull Insets insets, @NonNull Insets insets2) {
        return of(insets.left - insets2.left, insets.f3560top - insets2.f3560top, insets.right - insets2.right, insets.bottom - insets2.bottom);
    }

    @NonNull
    @RequiresApi(api = 29)
    public static Insets toCompatInsets(@NonNull android.graphics.Insets insets) {
        return of(insets.left, insets.top, insets.right, insets.bottom);
    }

    @NonNull
    @Deprecated
    @RequiresApi(api = 29)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Insets wrap(@NonNull android.graphics.Insets insets) {
        return toCompatInsets(insets);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Insets.class != obj.getClass()) {
            return false;
        }
        Insets insets = (Insets) obj;
        return this.bottom == insets.bottom && this.left == insets.left && this.right == insets.right && this.f3560top == insets.f3560top;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.f3560top) * 31) + this.right) * 31) + this.bottom;
    }

    @NonNull
    @RequiresApi(29)
    public android.graphics.Insets toPlatformInsets() {
        return Api29Impl.a(this.left, this.f3560top, this.right, this.bottom);
    }

    @NonNull
    public String toString() {
        return "Insets{left=" + this.left + ", top=" + this.f3560top + ", right=" + this.right + ", bottom=" + this.bottom + '}';
    }

    @NonNull
    public static Insets of(@NonNull Rect rect) {
        return of(rect.left, rect.top, rect.right, rect.bottom);
    }
}
