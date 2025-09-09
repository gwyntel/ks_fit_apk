package com.fluttercandies.image_editor.option.draw;

import android.graphics.Paint;
import android.graphics.Point;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.fluttercandies.image_editor.option.draw.IHavePaint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/PointsDrawPart;", "Lcom/fluttercandies/image_editor/option/draw/DrawPart;", "Lcom/fluttercandies/image_editor/option/draw/IHavePaint;", "map", "", "(Ljava/util/Map;)V", "offsets", "", "Landroid/graphics/Point;", "getOffsets", "()Ljava/util/List;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PointsDrawPart extends DrawPart implements IHavePaint {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PointsDrawPart(@NotNull Map<?, ?> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
    }

    @NotNull
    public final List<Point> getOffsets() {
        ArrayList arrayList = new ArrayList();
        Object obj = getMap().get(TypedValues.CycleType.S_WAVE_OFFSET);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<*>");
        for (Object obj2 : (List) obj) {
            if (obj2 instanceof Map) {
                arrayList.add(convertMapToOffset((Map) obj2));
            }
        }
        return arrayList;
    }

    @Override // com.fluttercandies.image_editor.option.draw.IHavePaint
    @NotNull
    public Paint getPaint() {
        return IHavePaint.DefaultImpls.getPaint(this);
    }
}
