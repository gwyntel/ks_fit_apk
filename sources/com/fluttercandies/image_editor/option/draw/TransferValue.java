package com.fluttercandies.image_editor.option.draw;

import android.graphics.Point;
import android.graphics.Rect;
import com.fluttercandies.image_editor.option.draw.ITransferValue;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u001c\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/fluttercandies/image_editor/option/draw/TransferValue;", "Lcom/fluttercandies/image_editor/option/draw/ITransferValue;", "map", "", "(Ljava/util/Map;)V", "getMap", "()Ljava/util/Map;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class TransferValue implements ITransferValue {

    @NotNull
    private final Map<?, ?> map;

    public TransferValue(@NotNull Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.map = map;
    }

    @Override // com.fluttercandies.image_editor.option.draw.ITransferValue
    @NotNull
    public Point convertMapToOffset(@NotNull Map<?, ?> map) {
        return ITransferValue.DefaultImpls.convertMapToOffset(this, map);
    }

    @Override // com.fluttercandies.image_editor.option.draw.ITransferValue
    public int getColor(@NotNull String str) {
        return ITransferValue.DefaultImpls.getColor(this, str);
    }

    @Override // com.fluttercandies.image_editor.option.draw.ITransferValue
    @NotNull
    public Map<?, ?> getMap() {
        return this.map;
    }

    @Override // com.fluttercandies.image_editor.option.draw.ITransferValue
    @NotNull
    public Point getOffset(@NotNull String str) {
        return ITransferValue.DefaultImpls.getOffset(this, str);
    }

    @Override // com.fluttercandies.image_editor.option.draw.ITransferValue
    @NotNull
    public Rect getRect(@NotNull String str) {
        return ITransferValue.DefaultImpls.getRect(this, str);
    }
}
