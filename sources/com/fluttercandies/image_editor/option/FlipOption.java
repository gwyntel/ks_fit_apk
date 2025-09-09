package com.fluttercandies.image_editor.option;

import com.facebook.share.internal.MessengerShareContentUtility;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import m.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\u0003H\u0016J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/fluttercandies/image_editor/option/FlipOption;", "Lcom/fluttercandies/image_editor/option/Option;", MessengerShareContentUtility.IMAGE_RATIO_HORIZONTAL, "", "vertical", "(ZZ)V", "getHorizontal", "()Z", "getVertical", "canIgnore", "component1", "component2", "copy", "equals", "other", "", "hashCode", "", "toString", "", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class FlipOption implements Option {
    private final boolean horizontal;
    private final boolean vertical;

    /* JADX WARN: Illegal instructions before constructor call */
    public FlipOption() {
        boolean z2 = false;
        this(z2, z2, 3, null);
    }

    public static /* synthetic */ FlipOption copy$default(FlipOption flipOption, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = flipOption.horizontal;
        }
        if ((i2 & 2) != 0) {
            z3 = flipOption.vertical;
        }
        return flipOption.copy(z2, z3);
    }

    @Override // com.fluttercandies.image_editor.option.Option
    public boolean canIgnore() {
        return (this.horizontal && this.vertical) ? false : true;
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getHorizontal() {
        return this.horizontal;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getVertical() {
        return this.vertical;
    }

    @NotNull
    public final FlipOption copy(boolean horizontal, boolean vertical) {
        return new FlipOption(horizontal, vertical);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FlipOption)) {
            return false;
        }
        FlipOption flipOption = (FlipOption) other;
        return this.horizontal == flipOption.horizontal && this.vertical == flipOption.vertical;
    }

    public final boolean getHorizontal() {
        return this.horizontal;
    }

    public final boolean getVertical() {
        return this.vertical;
    }

    public int hashCode() {
        return (c.a(this.horizontal) * 31) + c.a(this.vertical);
    }

    @NotNull
    public String toString() {
        return "FlipOption(horizontal=" + this.horizontal + ", vertical=" + this.vertical + ")";
    }

    public FlipOption(boolean z2, boolean z3) {
        this.horizontal = z2;
        this.vertical = z3;
    }

    public /* synthetic */ FlipOption(boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2, (i2 & 2) != 0 ? false : z3);
    }
}
