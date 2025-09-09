package com.fluttercandies.image_editor.option.draw;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import com.fluttercandies.image_editor.option.draw.ITransferValue;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/IHavePaint;", "Lcom/fluttercandies/image_editor/option/draw/ITransferValue;", "getPaint", "Landroid/graphics/Paint;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface IHavePaint extends ITransferValue {

    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @NotNull
        public static Point convertMapToOffset(@NotNull IHavePaint iHavePaint, @NotNull Map<?, ?> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            return ITransferValue.DefaultImpls.convertMapToOffset(iHavePaint, map);
        }

        public static int getColor(@NotNull IHavePaint iHavePaint, @NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return ITransferValue.DefaultImpls.getColor(iHavePaint, key);
        }

        @NotNull
        public static Point getOffset(@NotNull IHavePaint iHavePaint, @NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return ITransferValue.DefaultImpls.getOffset(iHavePaint, key);
        }

        @NotNull
        public static Paint getPaint(@NotNull IHavePaint iHavePaint) {
            Object obj = iHavePaint.getMap().get("paint");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
            return new DrawPaint((Map) obj).getPaint();
        }

        @NotNull
        public static Rect getRect(@NotNull IHavePaint iHavePaint, @NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return ITransferValue.DefaultImpls.getRect(iHavePaint, key);
        }
    }

    @NotNull
    Paint getPaint();
}
