package com.fluttercandies.image_editor.option;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/fluttercandies/image_editor/option/RotateOption;", "Lcom/fluttercandies/image_editor/option/Option;", "angle", "", "(I)V", "getAngle", "()I", "canIgnore", "", "component1", "copy", "equals", "other", "", "hashCode", "toString", "", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class RotateOption implements Option {
    private final int angle;

    public RotateOption(int i2) {
        this.angle = i2;
    }

    public static /* synthetic */ RotateOption copy$default(RotateOption rotateOption, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = rotateOption.angle;
        }
        return rotateOption.copy(i2);
    }

    @Override // com.fluttercandies.image_editor.option.Option
    public boolean canIgnore() {
        return this.angle % 360 == 0;
    }

    /* renamed from: component1, reason: from getter */
    public final int getAngle() {
        return this.angle;
    }

    @NotNull
    public final RotateOption copy(int angle) {
        return new RotateOption(angle);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof RotateOption) && this.angle == ((RotateOption) other).angle;
    }

    public final int getAngle() {
        return this.angle;
    }

    public int hashCode() {
        return this.angle;
    }

    @NotNull
    public String toString() {
        return "RotateOption(angle=" + this.angle + ")";
    }
}
