package com.fluttercandies.image_editor.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.fluttercandies.image_editor.option.FormatOption;
import com.fluttercandies.image_editor.option.ImagePosition;
import com.fluttercandies.image_editor.option.MergeImage;
import com.fluttercandies.image_editor.option.MergeOption;
import com.umeng.commonsdk.framework.UMModuleRegister;
import java.io.ByteArrayOutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/fluttercandies/image_editor/core/ImageMerger;", "", "mergeOption", "Lcom/fluttercandies/image_editor/option/MergeOption;", "(Lcom/fluttercandies/image_editor/option/MergeOption;)V", UMModuleRegister.PROCESS, "", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ImageMerger {

    @NotNull
    private final MergeOption mergeOption;

    public ImageMerger(@NotNull MergeOption mergeOption) {
        Intrinsics.checkNotNullParameter(mergeOption, "mergeOption");
        this.mergeOption = mergeOption;
    }

    @Nullable
    public final byte[] process() {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mergeOption.getWidth(), this.mergeOption.getHeight(), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        for (MergeImage mergeImage : this.mergeOption.getImages()) {
            Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(mergeImage.getByteArray(), 0, mergeImage.getByteArray().length);
            ImagePosition position = mergeImage.getPosition();
            canvas.drawBitmap(bitmapDecodeByteArray, (Rect) null, new Rect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight()), (Paint) null);
        }
        FormatOption formatOption = this.mergeOption.getFormatOption();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapCreateBitmap.compress(formatOption.getFormat() == 1 ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, formatOption.getQuality(), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
