package com.fluttercandies.image_editor.option.draw;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/PathPart;", "Lcom/fluttercandies/image_editor/option/draw/TransferValue;", "map", "", "(Ljava/util/Map;)V", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class PathPart extends TransferValue {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PathPart(@NotNull Map<?, ?> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
    }
}
