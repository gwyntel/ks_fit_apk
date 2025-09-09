package com.fluttercandies.image_editor.option;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u000b"}, d2 = {"Lcom/fluttercandies/image_editor/option/FormatOption;", "", "fmtMap", "", "(Ljava/util/Map;)V", "format", "", "getFormat", "()I", "quality", "getQuality", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FormatOption {
    private final int format;
    private final int quality;

    public FormatOption(@NotNull Map<?, ?> fmtMap) {
        Intrinsics.checkNotNullParameter(fmtMap, "fmtMap");
        Object obj = fmtMap.get("format");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        this.format = ((Integer) obj).intValue();
        Object obj2 = fmtMap.get("quality");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
        this.quality = ((Integer) obj2).intValue();
    }

    public final int getFormat() {
        return this.format;
    }

    public final int getQuality() {
        return this.quality;
    }
}
