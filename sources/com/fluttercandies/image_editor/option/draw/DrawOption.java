package com.fluttercandies.image_editor.option.draw;

import com.fluttercandies.image_editor.option.Option;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/DrawOption;", "Lcom/fluttercandies/image_editor/option/draw/TransferValue;", "Lcom/fluttercandies/image_editor/option/Option;", "map", "", "(Ljava/util/Map;)V", "drawPart", "", "Lcom/fluttercandies/image_editor/option/draw/DrawPart;", "getDrawPart", "()Ljava/util/List;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DrawOption extends TransferValue implements Option {

    @NotNull
    private final List<DrawPart> drawPart;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DrawOption(@NotNull Map<?, ?> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
        ArrayList arrayList = new ArrayList();
        Object obj = map.get("parts");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<*>");
        for (Object obj2 : (List) obj) {
            if (obj2 instanceof Map) {
                Map map2 = (Map) obj2;
                Object obj3 = map2.get(KsProperty.Key);
                Object obj4 = map2.get("value");
                Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
                Map map3 = (Map) obj4;
                IHavePaint rectDrawPart = Intrinsics.areEqual(obj3, "rect") ? new RectDrawPart(map3) : Intrinsics.areEqual(obj3, "oval") ? new OvalDrawPart(map3) : Intrinsics.areEqual(obj3, "line") ? new LineDrawPart(map3) : Intrinsics.areEqual(obj3, "point") ? new PointsDrawPart(map3) : Intrinsics.areEqual(obj3, "path") ? new PathDrawPart(map3) : null;
                if (rectDrawPart != null) {
                    arrayList.add(rectDrawPart);
                }
            }
        }
        this.drawPart = arrayList;
    }

    @Override // com.fluttercandies.image_editor.option.Option
    public boolean canIgnore() {
        return Option.DefaultImpls.canIgnore(this);
    }

    @NotNull
    public final List<DrawPart> getDrawPart() {
        return this.drawPart;
    }
}
