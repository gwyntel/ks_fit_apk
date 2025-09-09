package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public final class BitmapCompat {

    @RequiresApi(27)
    static class Api27Impl {
        private Api27Impl() {
        }

        @DoNotInline
        static Bitmap a(Bitmap bitmap) {
            if (bitmap.getConfig() != Bitmap.Config.HARDWARE) {
                return bitmap;
            }
            Bitmap.Config configA = Bitmap.Config.ARGB_8888;
            if (Build.VERSION.SDK_INT >= 31) {
                configA = Api31Impl.a(bitmap);
            }
            return bitmap.copy(configA, true);
        }

        @DoNotInline
        static Bitmap b(int i2, int i3, Bitmap bitmap, boolean z2) {
            Bitmap.Config config = bitmap.getConfig();
            ColorSpace colorSpace = bitmap.getColorSpace();
            ColorSpace colorSpace2 = ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
            if (z2 && !bitmap.getColorSpace().equals(colorSpace2)) {
                config = Bitmap.Config.RGBA_F16;
                colorSpace = colorSpace2;
            } else if (bitmap.getConfig() == Bitmap.Config.HARDWARE) {
                config = Bitmap.Config.ARGB_8888;
                if (Build.VERSION.SDK_INT >= 31) {
                    config = Api31Impl.a(bitmap);
                }
            }
            return Bitmap.createBitmap(i2, i3, config, bitmap.hasAlpha(), colorSpace);
        }

        @DoNotInline
        static boolean c(Bitmap bitmap) {
            return bitmap.getConfig() == Bitmap.Config.RGBA_F16 && bitmap.getColorSpace().equals(ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB));
        }
    }

    @RequiresApi(29)
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static void a(Paint paint) {
            paint.setBlendMode(BlendMode.SRC);
        }
    }

    @RequiresApi(31)
    static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        static Bitmap.Config a(Bitmap bitmap) {
            return bitmap.getHardwareBuffer().getFormat() == 22 ? Bitmap.Config.RGBA_F16 : Bitmap.Config.ARGB_8888;
        }
    }

    private BitmapCompat() {
    }

    static int a(int i2, int i3, int i4, int i5) {
        return i4 == 0 ? i3 : i4 > 0 ? i2 * (1 << (i5 - i4)) : i3 << ((-i4) - 1);
    }

    @NonNull
    public static Bitmap createScaledBitmap(@NonNull Bitmap bitmap, int i2, int i3, @Nullable Rect rect, boolean z2) {
        Paint paint;
        double dFloor;
        Paint paint2;
        Bitmap bitmap2;
        int i4;
        Rect rect2;
        Bitmap bitmapCreateBitmap;
        if (i2 <= 0 || i3 <= 0) {
            throw new IllegalArgumentException("dstW and dstH must be > 0!");
        }
        if (rect != null && (rect.isEmpty() || rect.left < 0 || rect.right > bitmap.getWidth() || rect.top < 0 || rect.bottom > bitmap.getHeight())) {
            throw new IllegalArgumentException("srcRect must be contained by srcBm!");
        }
        int i5 = Build.VERSION.SDK_INT;
        Bitmap bitmapA = i5 >= 27 ? Api27Impl.a(bitmap) : bitmap;
        int iWidth = rect != null ? rect.width() : bitmap.getWidth();
        int iHeight = rect != null ? rect.height() : bitmap.getHeight();
        float f2 = i2 / iWidth;
        float f3 = i3 / iHeight;
        int i6 = rect != null ? rect.left : 0;
        int i7 = rect != null ? rect.top : 0;
        if (i6 == 0 && i7 == 0 && i2 == bitmap.getWidth() && i3 == bitmap.getHeight()) {
            return (bitmap.isMutable() && bitmap == bitmapA) ? bitmap.copy(bitmap.getConfig(), true) : bitmapA;
        }
        Paint paint3 = new Paint(1);
        paint3.setFilterBitmap(true);
        if (i5 >= 29) {
            Api29Impl.a(paint3);
        } else {
            paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        }
        if (iWidth == i2 && iHeight == i3) {
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(i2, i3, bitmapA.getConfig());
            new Canvas(bitmapCreateBitmap2).drawBitmap(bitmapA, -i6, -i7, paint3);
            return bitmapCreateBitmap2;
        }
        double dLog = Math.log(2.0d);
        if (f2 > 1.0f) {
            paint = paint3;
            dFloor = Math.ceil(Math.log(f2) / dLog);
        } else {
            paint = paint3;
            dFloor = Math.floor(Math.log(f2) / dLog);
        }
        int i8 = (int) dFloor;
        int iCeil = (int) (f3 > 1.0f ? Math.ceil(Math.log(f3) / dLog) : Math.floor(Math.log(f3) / dLog));
        if (!z2 || i5 < 27 || Api27Impl.c(bitmap)) {
            paint2 = paint;
            bitmap2 = null;
            i4 = 0;
        } else {
            Bitmap bitmapB = Api27Impl.b(i8 > 0 ? a(iWidth, i2, 1, i8) : iWidth, iCeil > 0 ? a(iHeight, i3, 1, iCeil) : iHeight, bitmap, true);
            paint2 = paint;
            new Canvas(bitmapB).drawBitmap(bitmapA, -i6, -i7, paint2);
            i4 = 1;
            i7 = 0;
            i6 = 0;
            bitmap2 = bitmapA;
            bitmapA = bitmapB;
        }
        Rect rect3 = new Rect(i6, i7, iWidth, iHeight);
        Rect rect4 = new Rect();
        int i9 = i8;
        int i10 = iCeil;
        while (true) {
            if (i9 == 0 && i10 == 0) {
                break;
            }
            if (i9 < 0) {
                i9++;
            } else if (i9 > 0) {
                i9--;
            }
            if (i10 < 0) {
                i10++;
            } else if (i10 > 0) {
                i10--;
            }
            int i11 = i10;
            Paint paint4 = paint2;
            Rect rect5 = rect3;
            rect4.set(0, 0, a(iWidth, i2, i9, i8), a(iHeight, i3, i11, iCeil));
            boolean z3 = i9 == 0 && i11 == 0;
            boolean z4 = bitmap2 != null && bitmap2.getWidth() == i2 && bitmap2.getHeight() == i3;
            if (bitmap2 == null || bitmap2 == bitmap) {
                rect2 = rect4;
            } else {
                if (z2) {
                    rect2 = rect4;
                    if (Build.VERSION.SDK_INT < 27 || Api27Impl.c(bitmap2)) {
                    }
                    Rect rect6 = rect2;
                    new Canvas(bitmapCreateBitmap).drawBitmap(bitmapA, rect5, rect6, paint4);
                    rect5.set(rect6);
                    i10 = i11;
                    Bitmap bitmap3 = bitmapA;
                    bitmapA = bitmapCreateBitmap;
                    rect4 = rect6;
                    rect3 = rect5;
                    paint2 = paint4;
                    bitmap2 = bitmap3;
                } else {
                    rect2 = rect4;
                }
                if (!z3 || (z4 && i4 == 0)) {
                    bitmapCreateBitmap = bitmap2;
                }
                Rect rect62 = rect2;
                new Canvas(bitmapCreateBitmap).drawBitmap(bitmapA, rect5, rect62, paint4);
                rect5.set(rect62);
                i10 = i11;
                Bitmap bitmap32 = bitmapA;
                bitmapA = bitmapCreateBitmap;
                rect4 = rect62;
                rect3 = rect5;
                paint2 = paint4;
                bitmap2 = bitmap32;
            }
            if (bitmap2 != bitmap && bitmap2 != null) {
                bitmap2.recycle();
            }
            int iA = a(iWidth, i2, i9 > 0 ? i4 : i9, i8);
            int iA2 = a(iHeight, i3, i11 > 0 ? i4 : i11, iCeil);
            if (Build.VERSION.SDK_INT >= 27) {
                bitmapCreateBitmap = Api27Impl.b(iA, iA2, bitmap, z2 && !z3);
            } else {
                bitmapCreateBitmap = Bitmap.createBitmap(iA, iA2, bitmapA.getConfig());
            }
            Rect rect622 = rect2;
            new Canvas(bitmapCreateBitmap).drawBitmap(bitmapA, rect5, rect622, paint4);
            rect5.set(rect622);
            i10 = i11;
            Bitmap bitmap322 = bitmapA;
            bitmapA = bitmapCreateBitmap;
            rect4 = rect622;
            rect3 = rect5;
            paint2 = paint4;
            bitmap2 = bitmap322;
        }
        if (bitmap2 != bitmap && bitmap2 != null) {
            bitmap2.recycle();
        }
        return bitmapA;
    }

    public static int getAllocationByteCount(@NonNull Bitmap bitmap) {
        return bitmap.getAllocationByteCount();
    }

    public static boolean hasMipMap(@NonNull Bitmap bitmap) {
        return bitmap.hasMipMap();
    }

    public static void setHasMipMap(@NonNull Bitmap bitmap, boolean z2) {
        bitmap.setHasMipMap(z2);
    }
}
