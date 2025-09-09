package com.fluttercandies.image_editor.core;

import android.graphics.Bitmap;
import com.fluttercandies.image_editor.option.FlipOption;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/fluttercandies/image_editor/core/BitmapWrapper;", "", "bitmap", "Landroid/graphics/Bitmap;", "degree", "", "flipOption", "Lcom/fluttercandies/image_editor/option/FlipOption;", "(Landroid/graphics/Bitmap;ILcom/fluttercandies/image_editor/option/FlipOption;)V", "getBitmap", "()Landroid/graphics/Bitmap;", "getDegree", "()I", "getFlipOption", "()Lcom/fluttercandies/image_editor/option/FlipOption;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class BitmapWrapper {

    @NotNull
    private final Bitmap bitmap;
    private final int degree;

    @NotNull
    private final FlipOption flipOption;

    public BitmapWrapper(@NotNull Bitmap bitmap, int i2, @NotNull FlipOption flipOption) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(flipOption, "flipOption");
        this.bitmap = bitmap;
        this.degree = i2;
        this.flipOption = flipOption;
    }

    public static /* synthetic */ BitmapWrapper copy$default(BitmapWrapper bitmapWrapper, Bitmap bitmap, int i2, FlipOption flipOption, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            bitmap = bitmapWrapper.bitmap;
        }
        if ((i3 & 2) != 0) {
            i2 = bitmapWrapper.degree;
        }
        if ((i3 & 4) != 0) {
            flipOption = bitmapWrapper.flipOption;
        }
        return bitmapWrapper.copy(bitmap, i2, flipOption);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    /* renamed from: component2, reason: from getter */
    public final int getDegree() {
        return this.degree;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final FlipOption getFlipOption() {
        return this.flipOption;
    }

    @NotNull
    public final BitmapWrapper copy(@NotNull Bitmap bitmap, int degree, @NotNull FlipOption flipOption) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(flipOption, "flipOption");
        return new BitmapWrapper(bitmap, degree, flipOption);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BitmapWrapper)) {
            return false;
        }
        BitmapWrapper bitmapWrapper = (BitmapWrapper) other;
        return Intrinsics.areEqual(this.bitmap, bitmapWrapper.bitmap) && this.degree == bitmapWrapper.degree && Intrinsics.areEqual(this.flipOption, bitmapWrapper.flipOption);
    }

    @NotNull
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final int getDegree() {
        return this.degree;
    }

    @NotNull
    public final FlipOption getFlipOption() {
        return this.flipOption;
    }

    public int hashCode() {
        return (((this.bitmap.hashCode() * 31) + this.degree) * 31) + this.flipOption.hashCode();
    }

    @NotNull
    public String toString() {
        return "BitmapWrapper(bitmap=" + this.bitmap + ", degree=" + this.degree + ", flipOption=" + this.flipOption + ")";
    }
}
