package com.fluttercandies.image_editor.option.draw;

import android.graphics.Paint;
import android.graphics.Rect;
import com.fluttercandies.image_editor.option.draw.IHavePaint;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/RectDrawPart;", "Lcom/fluttercandies/image_editor/option/draw/DrawPart;", "Lcom/fluttercandies/image_editor/option/draw/IHavePaint;", "map", "", "(Ljava/util/Map;)V", "rect", "Landroid/graphics/Rect;", "getRect", "()Landroid/graphics/Rect;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class RectDrawPart extends DrawPart implements IHavePaint {

    @NotNull
    private final Rect rect;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RectDrawPart(@NotNull Map<?, ?> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
        this.rect = getRect("rect");
    }

    @Override // com.fluttercandies.image_editor.option.draw.IHavePaint
    @NotNull
    public Paint getPaint() {
        return IHavePaint.DefaultImpls.getPaint(this);
    }

    @NotNull
    public final Rect getRect() {
        return this.rect;
    }
}
