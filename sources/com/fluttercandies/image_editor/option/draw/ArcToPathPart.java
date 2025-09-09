package com.fluttercandies.image_editor.option.draw;

import android.graphics.Rect;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/ArcToPathPart;", "Lcom/fluttercandies/image_editor/option/draw/PathPart;", "map", "", "(Ljava/util/Map;)V", "rect", "Landroid/graphics/Rect;", "getRect", "()Landroid/graphics/Rect;", "start", "", "getStart", "()Ljava/lang/Number;", "sweep", "getSweep", "useCenter", "", "getUseCenter", "()Z", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ArcToPathPart extends PathPart {

    @NotNull
    private final Rect rect;

    @NotNull
    private final Number start;

    @NotNull
    private final Number sweep;
    private final boolean useCenter;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArcToPathPart(@NotNull Map<?, ?> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
        this.rect = getRect("rect");
        Object obj = map.get("start");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
        this.start = (Number) obj;
        Object obj2 = map.get("sweep");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Number");
        this.sweep = (Number) obj2;
        Object obj3 = map.get("useCenter");
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Boolean");
        this.useCenter = ((Boolean) obj3).booleanValue();
    }

    @NotNull
    public final Rect getRect() {
        return this.rect;
    }

    @NotNull
    public final Number getStart() {
        return this.start;
    }

    @NotNull
    public final Number getSweep() {
        return this.sweep;
    }

    public final boolean getUseCenter() {
        return this.useCenter;
    }
}
