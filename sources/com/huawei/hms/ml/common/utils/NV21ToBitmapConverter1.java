package com.huawei.hms.ml.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import java.util.Locale;

/* loaded from: classes4.dex */
public class NV21ToBitmapConverter1 {
    private Context applicationContext;
    private Allocation in;
    private Allocation out;
    private RenderScript renderScript;
    private Type.Builder rgbaType;
    private ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic;
    private Type.Builder yuvType;
    private int width = -1;
    private int height = -1;
    private int length = -1;

    public NV21ToBitmapConverter1(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        Context applicationContext = context.getApplicationContext();
        if (applicationContext == null) {
            this.applicationContext = context;
        } else {
            this.applicationContext = applicationContext;
        }
        RenderScript renderScriptCreate = RenderScript.create(this.applicationContext);
        this.renderScript = renderScriptCreate;
        if (renderScriptCreate != null) {
            this.yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
        }
    }

    private void recreateIfNeed(byte[] bArr, int i2, int i3, int i4) {
        if (this.width == i2 && this.height == i3 && this.length == bArr.length) {
            return;
        }
        this.width = i2;
        this.height = i3;
        this.length = bArr.length;
        this.yuvType = null;
        this.rgbaType = null;
    }

    public Bitmap convert(byte[] bArr, int i2, int i3, int i4, int i5, int i6, boolean z2, boolean z3, boolean z4) {
        recreateIfNeed(bArr, i2, i3, i6);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
        new Canvas(bitmapCreateBitmap).drawBitmap(convertYUVtoRGB(bArr, i2, i3), getTransformationMatrix(i2, i3, i4, i5, i6, z2, z3, z4), null);
        return bitmapCreateBitmap;
    }

    public Bitmap convertYUVtoRGB(byte[] bArr, int i2, int i3) {
        if (this.yuvType == null) {
            RenderScript renderScript = this.renderScript;
            Type.Builder x2 = new Type.Builder(renderScript, Element.U8(renderScript)).setX(bArr.length);
            this.yuvType = x2;
            this.in = Allocation.createTyped(this.renderScript, x2.create(), 1);
            RenderScript renderScript2 = this.renderScript;
            Type.Builder y2 = new Type.Builder(renderScript2, Element.RGBA_8888(renderScript2)).setX(i2).setY(i3);
            this.rgbaType = y2;
            this.out = Allocation.createTyped(this.renderScript, y2.create(), 1);
        }
        this.in.copyFrom(bArr);
        ScriptIntrinsicYuvToRGB scriptIntrinsicYuvToRGB = this.yuvToRgbIntrinsic;
        if (scriptIntrinsicYuvToRGB != null) {
            scriptIntrinsicYuvToRGB.setInput(this.in);
            this.yuvToRgbIntrinsic.forEach(this.out);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        this.out.copyTo(bitmapCreateBitmap);
        return bitmapCreateBitmap;
    }

    public Context getApplicationContext() {
        Context context = this.applicationContext;
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("initial must be called first");
    }

    public Matrix getTransformationMatrix(int i2, int i3, int i4, int i5, int i6, boolean z2, boolean z3, boolean z4) {
        Matrix matrix = new Matrix();
        if (i6 != 0) {
            if (i6 % 90 != 0) {
                throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Rotation of %d", Integer.valueOf(i6)));
            }
            matrix.postTranslate((-i2) / 2.0f, (-i3) / 2.0f);
            matrix.postRotate(i6);
        }
        boolean z5 = (Math.abs(i6) + 90) % 180 == 0;
        int i7 = z5 ? i3 : i2;
        if (!z5) {
            i2 = i3;
        }
        int i8 = z2 ? -1 : 1;
        int i9 = z3 ? -1 : 1;
        if (i7 != i4 || i2 != i5) {
            float f2 = (i8 * i4) / i7;
            float f3 = (i9 * i5) / i2;
            if (z4) {
                float fMax = Math.max(Math.abs(f2), Math.abs(f3));
                matrix.postScale(fMax, fMax);
            } else {
                matrix.postScale(f2, f3);
            }
        }
        if (i6 != 0) {
            float f4 = i4 / 2.0f;
            float f5 = i5 / 2.0f;
            matrix.postTranslate(f4, f5);
            matrix.postScale(i8, i9, f4, f5);
        }
        return matrix;
    }

    public Bitmap convert(byte[] bArr, int i2, int i3, int i4, int i5, int i6) {
        return convert(bArr, i2, i3, i4, i5, i6, false, false, false);
    }
}
