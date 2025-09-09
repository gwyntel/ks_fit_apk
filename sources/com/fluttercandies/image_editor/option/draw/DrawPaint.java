package com.fluttercandies.image_editor.option.draw;

import android.graphics.Paint;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/DrawPaint;", "Lcom/fluttercandies/image_editor/option/draw/TransferValue;", "map", "", "(Ljava/util/Map;)V", "getPaint", "Landroid/graphics/Paint;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DrawPaint extends TransferValue {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DrawPaint(@NotNull Map<?, ?> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
    }

    @NotNull
    public final Paint getPaint() {
        Paint paint = new Paint();
        paint.setColor(getColor("color"));
        Object obj = getMap().get("lineWeight");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
        paint.setStrokeWidth(((Number) obj).floatValue());
        Object obj2 = getMap().get("paintStyleFill");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
        paint.setStyle(((Boolean) obj2).booleanValue() ? Paint.Style.FILL : Paint.Style.STROKE);
        return paint;
    }
}
