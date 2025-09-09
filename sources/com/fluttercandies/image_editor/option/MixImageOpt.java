package com.fluttercandies.image_editor.option;

import android.graphics.PorterDuff;
import com.fluttercandies.image_editor.option.Option;
import com.fluttercandies.image_editor.util.ConvertUtils;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\bR\u0011\u0010\u0015\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\bR\u0011\u0010\u0017\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\b¨\u0006\u0019"}, d2 = {"Lcom/fluttercandies/image_editor/option/MixImageOpt;", "Lcom/fluttercandies/image_editor/option/Option;", "map", "", "(Ljava/util/Map;)V", "h", "", "getH", "()I", "img", "", "getImg", "()[B", "porterDuffMode", "Landroid/graphics/PorterDuff$Mode;", "getPorterDuffMode", "()Landroid/graphics/PorterDuff$Mode;", "type", "", "w", "getW", "x", "getX", "y", "getY", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MixImageOpt implements Option {
    private final int h;

    @NotNull
    private final byte[] img;

    @NotNull
    private final String type;
    private final int w;
    private final int x;
    private final int y;

    public MixImageOpt(@NotNull Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        Object obj = map.get("target");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        Object obj2 = ((Map) obj).get("memory");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.ByteArray");
        this.img = (byte[]) obj2;
        Object obj3 = map.get("x");
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
        this.x = ((Integer) obj3).intValue();
        Object obj4 = map.get("y");
        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Int");
        this.y = ((Integer) obj4).intValue();
        Object obj5 = map.get("w");
        Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Int");
        this.w = ((Integer) obj5).intValue();
        Object obj6 = map.get("h");
        Intrinsics.checkNotNull(obj6, "null cannot be cast to non-null type kotlin.Int");
        this.h = ((Integer) obj6).intValue();
        Object obj7 = map.get("mixMode");
        Intrinsics.checkNotNull(obj7, "null cannot be cast to non-null type kotlin.String");
        this.type = (String) obj7;
    }

    @Override // com.fluttercandies.image_editor.option.Option
    public boolean canIgnore() {
        return Option.DefaultImpls.canIgnore(this);
    }

    public final int getH() {
        return this.h;
    }

    @NotNull
    public final byte[] getImg() {
        return this.img;
    }

    @NotNull
    public final PorterDuff.Mode getPorterDuffMode() {
        return ConvertUtils.INSTANCE.convertToPorterDuffMode(this.type);
    }

    public final int getW() {
        return this.w;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }
}
