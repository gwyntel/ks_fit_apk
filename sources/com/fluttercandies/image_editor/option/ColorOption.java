package com.fluttercandies.image_editor.option;

import com.fluttercandies.image_editor.option.Option;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lcom/fluttercandies/image_editor/option/ColorOption;", "Lcom/fluttercandies/image_editor/option/Option;", "matrix", "", "([F)V", "getMatrix", "()[F", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Companion", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ColorOption implements Option {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final ColorOption src = new ColorOption(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});

    @NotNull
    private final float[] matrix;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/fluttercandies/image_editor/option/ColorOption$Companion;", "", "()V", "src", "Lcom/fluttercandies/image_editor/option/ColorOption;", "getSrc", "()Lcom/fluttercandies/image_editor/option/ColorOption;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ColorOption getSrc() {
            return ColorOption.src;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ColorOption(@NotNull float[] matrix) {
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        this.matrix = matrix;
    }

    public static /* synthetic */ ColorOption copy$default(ColorOption colorOption, float[] fArr, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            fArr = colorOption.matrix;
        }
        return colorOption.copy(fArr);
    }

    @Override // com.fluttercandies.image_editor.option.Option
    public boolean canIgnore() {
        return Option.DefaultImpls.canIgnore(this);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final float[] getMatrix() {
        return this.matrix;
    }

    @NotNull
    public final ColorOption copy(@NotNull float[] matrix) {
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        return new ColorOption(matrix);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(ColorOption.class, other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.fluttercandies.image_editor.option.ColorOption");
        return Arrays.equals(this.matrix, ((ColorOption) other).matrix);
    }

    @NotNull
    public final float[] getMatrix() {
        return this.matrix;
    }

    public int hashCode() {
        return Arrays.hashCode(this.matrix);
    }

    @NotNull
    public String toString() {
        return "ColorOption(matrix=" + Arrays.toString(this.matrix) + ")";
    }
}
