package com.fluttercandies.image_editor.option;

import com.alibaba.sdk.android.oss.common.RequestParameters;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lcom/fluttercandies/image_editor/option/MergeImage;", "", "map", "", "(Ljava/util/Map;)V", "byteArray", "", "getByteArray", "()[B", RequestParameters.POSITION, "Lcom/fluttercandies/image_editor/option/ImagePosition;", "getPosition", "()Lcom/fluttercandies/image_editor/option/ImagePosition;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MergeImage {

    @NotNull
    private final byte[] byteArray;

    @NotNull
    private final ImagePosition position;

    public MergeImage(@NotNull Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        Object obj = map.get("src");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        Object obj2 = ((Map) obj).get("memory");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.ByteArray");
        this.byteArray = (byte[]) obj2;
        Object obj3 = map.get(RequestParameters.POSITION);
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        this.position = new ImagePosition((Map) obj3);
    }

    @NotNull
    public final byte[] getByteArray() {
        return this.byteArray;
    }

    @NotNull
    public final ImagePosition getPosition() {
        return this.position;
    }
}
