package com.fluttercandies.image_editor.option.draw;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.kingsmith.miot.KsProperty;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0006\u001a\u00020\u00072\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003H\u0016J\u0012\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u001a\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u000f"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/ITransferValue;", "", "map", "", "getMap", "()Ljava/util/Map;", "convertMapToOffset", "Landroid/graphics/Point;", "getColor", "", KsProperty.Key, "", "getOffset", "getRect", "Landroid/graphics/Rect;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface ITransferValue {

    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @NotNull
        public static Point convertMapToOffset(@NotNull ITransferValue iTransferValue, @NotNull Map<?, ?> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("x");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue = ((Integer) obj).intValue();
            Object obj2 = map.get("y");
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
            return new Point(iIntValue, ((Integer) obj2).intValue());
        }

        public static int getColor(@NotNull ITransferValue iTransferValue, @NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = iTransferValue.getMap().get("color");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
            Map map = (Map) obj;
            Object obj2 = map.get("r");
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue = ((Integer) obj2).intValue();
            Object obj3 = map.get("g");
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue2 = ((Integer) obj3).intValue();
            Object obj4 = map.get("b");
            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue3 = ((Integer) obj4).intValue();
            Object obj5 = map.get("a");
            Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Int");
            return Color.argb(((Integer) obj5).intValue(), iIntValue, iIntValue2, iIntValue3);
        }

        public static /* synthetic */ int getColor$default(ITransferValue iTransferValue, String str, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getColor");
            }
            if ((i2 & 1) != 0) {
                str = "color";
            }
            return iTransferValue.getColor(str);
        }

        @NotNull
        public static Point getOffset(@NotNull ITransferValue iTransferValue, @NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = iTransferValue.getMap().get(key);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
            return iTransferValue.convertMapToOffset((Map) obj);
        }

        @NotNull
        public static Rect getRect(@NotNull ITransferValue iTransferValue, @NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = iTransferValue.getMap().get(key);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
            Map map = (Map) obj;
            Object obj2 = map.get("left");
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue = ((Integer) obj2).intValue();
            Object obj3 = map.get(ViewHierarchyConstants.DIMENSION_TOP_KEY);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue2 = ((Integer) obj3).intValue();
            Object obj4 = map.get(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue3 = ((Integer) obj4).intValue() + iIntValue;
            Object obj5 = map.get(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
            Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Int");
            return new Rect(iIntValue, iIntValue2, iIntValue3, ((Integer) obj5).intValue() + iIntValue2);
        }
    }

    @NotNull
    Point convertMapToOffset(@NotNull Map<?, ?> map);

    int getColor(@NotNull String key);

    @NotNull
    Map<?, ?> getMap();

    @NotNull
    Point getOffset(@NotNull String key);

    @NotNull
    Rect getRect(@NotNull String key);
}
