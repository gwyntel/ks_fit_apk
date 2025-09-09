package com.fluttercandies.image_editor.option;

import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\f¨\u0006\u0014"}, d2 = {"Lcom/fluttercandies/image_editor/option/MergeOption;", "", "map", "", "(Ljava/util/Map;)V", "formatOption", "Lcom/fluttercandies/image_editor/option/FormatOption;", "getFormatOption", "()Lcom/fluttercandies/image_editor/option/FormatOption;", ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "", "getHeight", "()I", "images", "", "Lcom/fluttercandies/image_editor/option/MergeImage;", "getImages", "()Ljava/util/List;", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, "getWidth", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MergeOption {

    @NotNull
    private final FormatOption formatOption;
    private final int height;

    @NotNull
    private final List<MergeImage> images;
    private final int width;

    public MergeOption(@NotNull Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        Object obj = map.get("fmt");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        this.formatOption = new FormatOption((Map) obj);
        Object obj2 = map.get("w");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
        this.width = ((Integer) obj2).intValue();
        Object obj3 = map.get("h");
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
        this.height = ((Integer) obj3).intValue();
        ArrayList arrayList = new ArrayList();
        Object obj4 = map.get("images");
        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.collections.List<*>");
        for (Object obj5 : (List) obj4) {
            if (obj5 instanceof Map) {
                arrayList.add(new MergeImage((Map) obj5));
            }
        }
        this.images = arrayList;
    }

    @NotNull
    public final FormatOption getFormatOption() {
        return this.formatOption;
    }

    public final int getHeight() {
        return this.height;
    }

    @NotNull
    public final List<MergeImage> getImages() {
        return this.images;
    }

    public final int getWidth() {
        return this.width;
    }
}
