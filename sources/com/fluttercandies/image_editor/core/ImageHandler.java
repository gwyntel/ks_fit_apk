package com.fluttercandies.image_editor.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.fluttercandies.image_editor.common.font.FontUtils;
import com.fluttercandies.image_editor.option.AddTextOpt;
import com.fluttercandies.image_editor.option.ClipOption;
import com.fluttercandies.image_editor.option.ColorOption;
import com.fluttercandies.image_editor.option.FlipOption;
import com.fluttercandies.image_editor.option.FormatOption;
import com.fluttercandies.image_editor.option.MixImageOpt;
import com.fluttercandies.image_editor.option.Option;
import com.fluttercandies.image_editor.option.RotateOption;
import com.fluttercandies.image_editor.option.ScaleOption;
import com.fluttercandies.image_editor.option.Text;
import com.fluttercandies.image_editor.option.draw.DrawOption;
import com.tekartik.sqflite.Constant;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0014\u0010\u0011\u001a\u00020\u00062\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013J\u0010\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020#H\u0002J\u0018\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0002J\u000e\u0010)\u001a\u00020*2\u0006\u0010'\u001a\u00020(J\u0016\u0010+\u001a\u00020\u00062\u0006\u0010,\u001a\u00020-2\u0006\u0010'\u001a\u00020(R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/fluttercandies/image_editor/core/ImageHandler;", "", "bitmap", "Landroid/graphics/Bitmap;", "(Landroid/graphics/Bitmap;)V", "drawText", "", "text", "Lcom/fluttercandies/image_editor/option/Text;", "canvas", "Landroid/graphics/Canvas;", "getStaticLayout", "Landroid/text/StaticLayout;", "textPaint", "Landroid/text/TextPaint;", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, "", "handle", Constant.METHOD_OPTIONS, "", "Lcom/fluttercandies/image_editor/option/Option;", "handleClip", "option", "Lcom/fluttercandies/image_editor/option/ClipOption;", "handleColor", "Lcom/fluttercandies/image_editor/option/ColorOption;", "handleFlip", "Lcom/fluttercandies/image_editor/option/FlipOption;", "handleMixImage", "Lcom/fluttercandies/image_editor/option/MixImageOpt;", "handleRotate", "Lcom/fluttercandies/image_editor/option/RotateOption;", "handleScale", "Lcom/fluttercandies/image_editor/option/ScaleOption;", "handleText", "Lcom/fluttercandies/image_editor/option/AddTextOpt;", "output", "outputStream", "Ljava/io/OutputStream;", "formatOption", "Lcom/fluttercandies/image_editor/option/FormatOption;", "outputByteArray", "", "outputToFile", "dstPath", "", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ImageHandler {

    @NotNull
    private Bitmap bitmap;

    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Paint.Align.values().length];
            try {
                iArr[Paint.Align.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Paint.Align.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ImageHandler(@NotNull Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.bitmap = bitmap;
    }

    private final void drawText(Text text, Canvas canvas) {
        TextPaint textPaint = new TextPaint(1);
        textPaint.setColor(Color.argb(text.getA(), text.getR(), text.getG(), text.getB()));
        textPaint.setTextSize(text.getFontSizePx());
        if (text.getFontName().length() > 0) {
            try {
                textPaint.setTypeface(FontUtils.getFont(text.getFontName()));
            } catch (Exception unused) {
            }
        }
        StaticLayout staticLayout = getStaticLayout(text, textPaint, canvas.getWidth() - text.getX());
        canvas.translate(text.getX(), text.getY());
        int lineCount = staticLayout.getLineCount();
        int i2 = 0;
        while (i2 < lineCount) {
            String string = staticLayout.getText().subSequence(staticLayout.getLineStart(i2), staticLayout.getLineEnd(i2)).toString();
            float fMeasureText = textPaint.measureText(string);
            i2++;
            int y2 = text.getY() + (text.getFontSizePx() * i2);
            int i3 = WhenMappings.$EnumSwitchMapping$0[text.getTextAlign().ordinal()];
            canvas.drawText(string, (i3 != 1 ? i3 != 2 ? Integer.valueOf(text.getX()) : Float.valueOf(staticLayout.getWidth() - fMeasureText) : Float.valueOf((staticLayout.getWidth() - fMeasureText) / 2)).floatValue(), y2, textPaint);
        }
        canvas.translate(-text.getX(), -text.getY());
    }

    private final StaticLayout getStaticLayout(Text text, TextPaint textPaint, int width) {
        StaticLayout staticLayoutBuild = StaticLayout.Builder.obtain(text.getText(), 0, text.getText().length(), textPaint, width).build();
        Intrinsics.checkNotNull(staticLayoutBuild);
        return staticLayoutBuild;
    }

    private final Bitmap handleClip(ClipOption option) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.bitmap, option.getX(), option.getY(), option.getWidth(), option.getHeight(), (Matrix) null, false);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        return bitmapCreateBitmap;
    }

    private final Bitmap handleColor(ColorOption option) {
        Bitmap bitmap = this.bitmap;
        Bitmap bitmapCreateNewBitmap = HandleExtensionKt.createNewBitmap(bitmap, bitmap.getWidth(), this.bitmap.getHeight());
        Canvas canvas = new Canvas(bitmapCreateNewBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(option.getMatrix()));
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, paint);
        return bitmapCreateNewBitmap;
    }

    private final Bitmap handleFlip(FlipOption option) {
        Matrix matrix = new Matrix();
        matrix.postScale(option.getHorizontal() ? -1.0f : 1.0f, option.getVertical() ? -1.0f : 1.0f);
        Bitmap bitmap = this.bitmap;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.bitmap.getHeight(), matrix, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        new Canvas().drawBitmap(bitmapCreateBitmap, matrix, null);
        return bitmapCreateBitmap;
    }

    private final Bitmap handleMixImage(MixImageOpt option) {
        Bitmap bitmap = this.bitmap;
        Bitmap bitmapCreateNewBitmap = HandleExtensionKt.createNewBitmap(bitmap, bitmap.getWidth(), this.bitmap.getHeight());
        Canvas canvas = new Canvas(bitmapCreateNewBitmap);
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, (Paint) null);
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(option.getImg(), 0, option.getImg().length);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(option.getPorterDuffMode()));
        canvas.drawBitmap(bitmapDecodeByteArray, (Rect) null, new Rect(option.getX(), option.getY(), option.getX() + option.getW(), option.getY() + option.getH()), paint);
        return bitmapCreateNewBitmap;
    }

    private final Bitmap handleRotate(RotateOption option) {
        Matrix matrix = new Matrix();
        matrix.postRotate(option.getAngle());
        Bitmap bitmap = this.bitmap;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.bitmap.getHeight(), matrix, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        new Canvas().drawBitmap(bitmapCreateBitmap, matrix, null);
        return bitmapCreateBitmap;
    }

    private final Bitmap handleScale(ScaleOption option) {
        int width = option.getWidth();
        int height = option.getHeight();
        if (option.getKeepRatio()) {
            float width2 = this.bitmap.getWidth() / this.bitmap.getHeight();
            if (option.getKeepWidthFirst()) {
                height = (int) (width / width2);
            } else {
                width = (int) (width2 * height);
            }
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        Matrix matrix = new Matrix();
        int width3 = this.bitmap.getWidth();
        int height2 = this.bitmap.getHeight();
        if (width3 != width || height2 != height) {
            matrix.setScale(width / width3, height / height2);
        }
        canvas.drawBitmap(this.bitmap, matrix, paint);
        return bitmapCreateBitmap;
    }

    private final Bitmap handleText(AddTextOpt option) {
        Bitmap bitmap = this.bitmap;
        Bitmap bitmapCreateNewBitmap = HandleExtensionKt.createNewBitmap(bitmap, bitmap.getWidth(), this.bitmap.getHeight());
        Canvas canvas = new Canvas(bitmapCreateNewBitmap);
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, new Paint());
        Iterator<Text> it = option.getTexts().iterator();
        while (it.hasNext()) {
            Text next = it.next();
            Intrinsics.checkNotNull(next);
            drawText(next, canvas);
        }
        return bitmapCreateNewBitmap;
    }

    private final void output(OutputStream outputStream, FormatOption formatOption) {
        try {
            if (formatOption.getFormat() == 0) {
                this.bitmap.compress(Bitmap.CompressFormat.PNG, formatOption.getQuality(), outputStream);
            } else {
                this.bitmap.compress(Bitmap.CompressFormat.JPEG, formatOption.getQuality(), outputStream);
            }
            CloseableKt.closeFinally(outputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(outputStream, th);
                throw th2;
            }
        }
    }

    public final void handle(@NotNull List<? extends Option> options) {
        Intrinsics.checkNotNullParameter(options, "options");
        for (Option option : options) {
            if (option instanceof ColorOption) {
                this.bitmap = handleColor((ColorOption) option);
            } else if (option instanceof ScaleOption) {
                this.bitmap = handleScale((ScaleOption) option);
            } else if (option instanceof FlipOption) {
                this.bitmap = handleFlip((FlipOption) option);
            } else if (option instanceof ClipOption) {
                this.bitmap = handleClip((ClipOption) option);
            } else if (option instanceof RotateOption) {
                this.bitmap = handleRotate((RotateOption) option);
            } else if (option instanceof AddTextOpt) {
                this.bitmap = handleText((AddTextOpt) option);
            } else if (option instanceof MixImageOpt) {
                this.bitmap = handleMixImage((MixImageOpt) option);
            } else if (option instanceof DrawOption) {
                this.bitmap = HandleExtensionKt.draw(this.bitmap, (DrawOption) option);
            }
        }
    }

    @NotNull
    public final byte[] outputByteArray(@NotNull FormatOption formatOption) {
        Intrinsics.checkNotNullParameter(formatOption, "formatOption");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        output(byteArrayOutputStream, formatOption);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }

    public final void outputToFile(@NotNull String dstPath, @NotNull FormatOption formatOption) {
        Intrinsics.checkNotNullParameter(dstPath, "dstPath");
        Intrinsics.checkNotNullParameter(formatOption, "formatOption");
        output(new FileOutputStream(dstPath), formatOption);
    }
}
