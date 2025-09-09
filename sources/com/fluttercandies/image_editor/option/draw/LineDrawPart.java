package com.fluttercandies.image_editor.option.draw;

import android.graphics.Paint;
import android.graphics.Point;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.fluttercandies.image_editor.option.draw.IHavePaint;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\f"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/LineDrawPart;", "Lcom/fluttercandies/image_editor/option/draw/DrawPart;", "Lcom/fluttercandies/image_editor/option/draw/IHavePaint;", "map", "", "(Ljava/util/Map;)V", TtmlNode.END, "Landroid/graphics/Point;", "getEnd", "()Landroid/graphics/Point;", "start", "getStart", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class LineDrawPart extends DrawPart implements IHavePaint {

    @NotNull
    private final Point end;

    @NotNull
    private final Point start;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LineDrawPart(@NotNull Map<?, ?> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
        this.start = getOffset("start");
        this.end = getOffset(TtmlNode.END);
    }

    @NotNull
    public final Point getEnd() {
        return this.end;
    }

    @Override // com.fluttercandies.image_editor.option.draw.IHavePaint
    @NotNull
    public Paint getPaint() {
        return IHavePaint.DefaultImpls.getPaint(this);
    }

    @NotNull
    public final Point getStart() {
        return this.start;
    }
}
