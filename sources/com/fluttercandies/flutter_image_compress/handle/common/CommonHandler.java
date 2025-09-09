package com.fluttercandies.flutter_image_compress.handle.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.fluttercandies.flutter_image_compress.exif.ExifKeeper;
import com.fluttercandies.flutter_image_compress.ext.BitmapCompressExtKt;
import com.fluttercandies.flutter_image_compress.handle.FormatHandler;
import com.fluttercandies.flutter_image_compress.logger.LogExtKt;
import com.umeng.analytics.pro.f;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J:\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0003H\u0002JP\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u0003H\u0016JX\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006!"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/handle/common/CommonHandler;", "Lcom/fluttercandies/flutter_image_compress/handle/FormatHandler;", "type", "", "(I)V", "bitmapFormat", "Landroid/graphics/Bitmap$CompressFormat;", "getType", "()I", "typeName", "", "getTypeName", "()Ljava/lang/String;", "compress", "", "arr", "minWidth", "minHeight", "quality", "rotate", "inSampleSize", "handleByteArray", "", f.X, "Landroid/content/Context;", "byteArray", "outputStream", "Ljava/io/OutputStream;", "keepExif", "", "handleFile", "path", "numberOfRetries", "flutter_image_compress_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CommonHandler implements FormatHandler {

    @NotNull
    private final Bitmap.CompressFormat bitmapFormat;
    private final int type;

    @NotNull
    private final String typeName;

    public CommonHandler(int i2) {
        this.type = i2;
        int type = getType();
        this.typeName = type != 1 ? type != 3 ? "jpeg" : "webp" : "png";
        int type2 = getType();
        this.bitmapFormat = type2 != 1 ? type2 != 3 ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.WEBP : Bitmap.CompressFormat.PNG;
    }

    private final byte[] compress(byte[] arr, int minWidth, int minHeight, int quality, int rotate, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = inSampleSize;
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        float width = bitmapDecodeByteArray.getWidth();
        float height = bitmapDecodeByteArray.getHeight();
        LogExtKt.log("src width = " + width);
        LogExtKt.log("src height = " + height);
        Intrinsics.checkNotNull(bitmapDecodeByteArray);
        float fCalcScale = BitmapCompressExtKt.calcScale(bitmapDecodeByteArray, minWidth, minHeight);
        LogExtKt.log("scale = " + fCalcScale);
        float f2 = width / fCalcScale;
        float f3 = height / fCalcScale;
        LogExtKt.log("dst width = " + f2);
        LogExtKt.log("dst height = " + f3);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeByteArray, (int) f2, (int) f3, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
        BitmapCompressExtKt.rotate(bitmapCreateScaledBitmap, rotate).compress(this.bitmapFormat, quality, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }

    @Override // com.fluttercandies.flutter_image_compress.handle.FormatHandler
    public int getType() {
        return this.type;
    }

    @Override // com.fluttercandies.flutter_image_compress.handle.FormatHandler
    @NotNull
    public String getTypeName() {
        return this.typeName;
    }

    @Override // com.fluttercandies.flutter_image_compress.handle.FormatHandler
    public void handleByteArray(@NotNull Context context, @NotNull byte[] byteArray, @NotNull OutputStream outputStream, int minWidth, int minHeight, int quality, int rotate, boolean keepExif, int inSampleSize) throws IOException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        byte[] bArrCompress = compress(byteArray, minWidth, minHeight, quality, rotate, inSampleSize);
        if (!keepExif || this.bitmapFormat != Bitmap.CompressFormat.JPEG) {
            outputStream.write(bArrCompress);
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(bArrCompress);
        outputStream.write(new ExifKeeper(byteArray).writeToOutputStream(context, byteArrayOutputStream).toByteArray());
    }

    @Override // com.fluttercandies.flutter_image_compress.handle.FormatHandler
    public void handleFile(@NotNull Context context, @NotNull String path, @NotNull OutputStream outputStream, int minWidth, int minHeight, int quality, int rotate, boolean keepExif, int inSampleSize, int numberOfRetries) throws IOException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        if (numberOfRetries <= 0) {
            return;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = inSampleSize;
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(path, options);
            Intrinsics.checkNotNull(bitmapDecodeFile);
            byte[] bArrCompress = BitmapCompressExtKt.compress(bitmapDecodeFile, minWidth, minHeight, quality, rotate, getType());
            if (keepExif) {
                try {
                    if (this.bitmapFormat == Bitmap.CompressFormat.JPEG) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byteArrayOutputStream.write(bArrCompress);
                        outputStream.write(new ExifKeeper(path).writeToOutputStream(context, byteArrayOutputStream).toByteArray());
                    }
                } catch (OutOfMemoryError unused) {
                    System.gc();
                    handleFile(context, path, outputStream, minWidth, minHeight, quality, rotate, keepExif, inSampleSize * 2, numberOfRetries - 1);
                    return;
                }
            }
            outputStream.write(bArrCompress);
        } catch (OutOfMemoryError unused2) {
        }
    }
}
