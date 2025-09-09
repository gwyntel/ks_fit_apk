package com.fluttercandies.image_editor.option.draw;

import android.graphics.Point;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/LineToPathPart;", "Lcom/fluttercandies/image_editor/option/draw/PathPart;", "map", "", "(Ljava/util/Map;)V", TypedValues.CycleType.S_WAVE_OFFSET, "Landroid/graphics/Point;", "getOffset", "()Landroid/graphics/Point;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class LineToPathPart extends PathPart {

    @NotNull
    private final Point offset;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LineToPathPart(@NotNull Map<?, ?> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
        this.offset = getOffset(TypedValues.CycleType.S_WAVE_OFFSET);
    }

    @NotNull
    public final Point getOffset() {
        return this.offset;
    }
}
