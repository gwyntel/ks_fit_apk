package com.fluttercandies.flutter_image_compress.ext;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.fluttercandies.flutter_image_compress.ImageCompressPlugin;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002\u001a\u001a\u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003\u001a>\u0010\r\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u001a4\u0010\r\u001a\u00020\u0013*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0003\u001a\u0012\u0010\u000f\u001a\u00020\n*\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0003¨\u0006\u0014"}, d2 = {"convertFormatIndexToFormat", "Landroid/graphics/Bitmap$CompressFormat;", "type", "", "log", "", Languages.ANY, "", "calcScale", "", "Landroid/graphics/Bitmap;", "minWidth", "minHeight", "compress", "quality", "rotate", "outputStream", "Ljava/io/OutputStream;", "format", "", "flutter_image_compress_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BitmapCompressExtKt {
    public static final float calcScale(@NotNull Bitmap bitmap, int i2, int i3) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        float width = bitmap.getWidth() / i2;
        float height = bitmap.getHeight() / i3;
        log("width scale = " + width);
        log("height scale = " + height);
        return Math.max(1.0f, Math.min(width, height));
    }

    @NotNull
    public static final byte[] compress(@NotNull Bitmap bitmap, int i2, int i3, int i4, int i5, int i6) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        compress(bitmap, i2, i3, i4, i5, byteArrayOutputStream, i6);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }

    public static /* synthetic */ byte[] compress$default(Bitmap bitmap, int i2, int i3, int i4, int i5, int i6, int i7, Object obj) {
        if ((i7 & 8) != 0) {
            i5 = 0;
        }
        return compress(bitmap, i2, i3, i4, i5, i6);
    }

    @NotNull
    public static final Bitmap.CompressFormat convertFormatIndexToFormat(int i2) {
        return i2 != 1 ? i2 != 3 ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.WEBP : Bitmap.CompressFormat.PNG;
    }

    private static final void log(Object obj) {
        if (ImageCompressPlugin.INSTANCE.getShowLog()) {
            if (obj == null) {
                obj = TmpConstant.GROUP_ROLE_UNKNOWN;
            }
            System.out.println(obj);
        }
    }

    @NotNull
    public static final Bitmap rotate(@NotNull Bitmap bitmap, int i2) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        if (i2 % 360 == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(i2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        Intrinsics.checkNotNull(bitmapCreateBitmap);
        return bitmapCreateBitmap;
    }

    public static final void compress(@NotNull Bitmap bitmap, int i2, int i3, int i4, int i5, @NotNull OutputStream outputStream, int i6) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        log("src width = " + width);
        log("src height = " + height);
        float fCalcScale = calcScale(bitmap, i2, i3);
        log("scale = " + fCalcScale);
        float f2 = width / fCalcScale;
        float f3 = height / fCalcScale;
        log("dst width = " + f2);
        log("dst height = " + f3);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) f2, (int) f3, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
        rotate(bitmapCreateScaledBitmap, i5).compress(convertFormatIndexToFormat(i6), i4, outputStream);
    }
}
