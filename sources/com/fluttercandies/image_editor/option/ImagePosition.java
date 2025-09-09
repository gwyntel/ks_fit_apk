package com.fluttercandies.image_editor.option;

import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0011\u0010\r\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/fluttercandies/image_editor/option/ImagePosition;", "", "map", "", "(Ljava/util/Map;)V", ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "", "getHeight", "()I", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, "getWidth", "x", "getX", "y", "getY", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ImagePosition {
    private final int height;
    private final int width;
    private final int x;
    private final int y;

    public ImagePosition(@NotNull Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        Object obj = map.get("x");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        this.x = ((Integer) obj).intValue();
        Object obj2 = map.get("y");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
        this.y = ((Integer) obj2).intValue();
        Object obj3 = map.get("w");
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
        this.width = ((Integer) obj3).intValue();
        Object obj4 = map.get("h");
        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Int");
        this.height = ((Integer) obj4).intValue();
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }
}
