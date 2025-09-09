package com.fluttercandies.image_editor.option;

import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.fluttercandies.image_editor.option.Option;
import kotlin.Metadata;
import m.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001a"}, d2 = {"Lcom/fluttercandies/image_editor/option/ScaleOption;", "Lcom/fluttercandies/image_editor/option/Option;", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, "", ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "keepRatio", "", "keepWidthFirst", "(IIZZ)V", "getHeight", "()I", "getKeepRatio", "()Z", "getKeepWidthFirst", "getWidth", "component1", "component2", "component3", "component4", "copy", "equals", "other", "", "hashCode", "toString", "", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ScaleOption implements Option {
    private final int height;
    private final boolean keepRatio;
    private final boolean keepWidthFirst;
    private final int width;

    public ScaleOption(int i2, int i3, boolean z2, boolean z3) {
        this.width = i2;
        this.height = i3;
        this.keepRatio = z2;
        this.keepWidthFirst = z3;
    }

    public static /* synthetic */ ScaleOption copy$default(ScaleOption scaleOption, int i2, int i3, boolean z2, boolean z3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = scaleOption.width;
        }
        if ((i4 & 2) != 0) {
            i3 = scaleOption.height;
        }
        if ((i4 & 4) != 0) {
            z2 = scaleOption.keepRatio;
        }
        if ((i4 & 8) != 0) {
            z3 = scaleOption.keepWidthFirst;
        }
        return scaleOption.copy(i2, i3, z2, z3);
    }

    @Override // com.fluttercandies.image_editor.option.Option
    public boolean canIgnore() {
        return Option.DefaultImpls.canIgnore(this);
    }

    /* renamed from: component1, reason: from getter */
    public final int getWidth() {
        return this.width;
    }

    /* renamed from: component2, reason: from getter */
    public final int getHeight() {
        return this.height;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getKeepRatio() {
        return this.keepRatio;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getKeepWidthFirst() {
        return this.keepWidthFirst;
    }

    @NotNull
    public final ScaleOption copy(int width, int height, boolean keepRatio, boolean keepWidthFirst) {
        return new ScaleOption(width, height, keepRatio, keepWidthFirst);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScaleOption)) {
            return false;
        }
        ScaleOption scaleOption = (ScaleOption) other;
        return this.width == scaleOption.width && this.height == scaleOption.height && this.keepRatio == scaleOption.keepRatio && this.keepWidthFirst == scaleOption.keepWidthFirst;
    }

    public final int getHeight() {
        return this.height;
    }

    public final boolean getKeepRatio() {
        return this.keepRatio;
    }

    public final boolean getKeepWidthFirst() {
        return this.keepWidthFirst;
    }

    public final int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return (((((this.width * 31) + this.height) * 31) + c.a(this.keepRatio)) * 31) + c.a(this.keepWidthFirst);
    }

    @NotNull
    public String toString() {
        return "ScaleOption(width=" + this.width + ", height=" + this.height + ", keepRatio=" + this.keepRatio + ", keepWidthFirst=" + this.keepWidthFirst + ")";
    }
}
