package top.zibin.luban;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import io.flutter.plugin.platform.PlatformPlugin;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
class Engine {
    private boolean focusAlpha;
    private int srcHeight;
    private InputStreamProvider srcImg;
    private int srcWidth;
    private File tagImg;

    Engine(InputStreamProvider inputStreamProvider, File file, boolean z2) {
        this.tagImg = file;
        this.srcImg = inputStreamProvider;
        this.focusAlpha = z2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeStream(inputStreamProvider.open(), null, options);
        this.srcWidth = options.outWidth;
        this.srcHeight = options.outHeight;
    }

    private int computeSize() {
        int i2 = this.srcWidth;
        if (i2 % 2 == 1) {
            i2++;
        }
        this.srcWidth = i2;
        int i3 = this.srcHeight;
        if (i3 % 2 == 1) {
            i3++;
        }
        this.srcHeight = i3;
        int iMax = Math.max(i2, i3);
        float fMin = Math.min(this.srcWidth, this.srcHeight) / iMax;
        if (fMin > 1.0f || fMin <= 0.5625d) {
            double d2 = fMin;
            if (d2 > 0.5625d || d2 <= 0.5d) {
                return (int) Math.ceil(iMax / (1280.0d / d2));
            }
            int i4 = iMax / PlatformPlugin.DEFAULT_SYSTEM_UI;
            if (i4 == 0) {
                return 1;
            }
            return i4;
        }
        if (iMax < 1664) {
            return 1;
        }
        if (iMax < 4990) {
            return 2;
        }
        if (iMax <= 4990 || iMax >= 10240) {
            return iMax / PlatformPlugin.DEFAULT_SYSTEM_UI;
        }
        return 4;
    }

    private Bitmap rotatingImage(Bitmap bitmap, int i2) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    File a() throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = computeSize();
        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(this.srcImg.open(), null, options);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Checker checker = Checker.SINGLE;
        if (checker.isJPG(this.srcImg.open())) {
            bitmapDecodeStream = rotatingImage(bitmapDecodeStream, checker.getOrientation(this.srcImg.open()));
        }
        bitmapDecodeStream.compress((this.focusAlpha || bitmapDecodeStream.hasAlpha()) ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        bitmapDecodeStream.recycle();
        FileOutputStream fileOutputStream = new FileOutputStream(this.tagImg);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
        byteArrayOutputStream.close();
        return this.tagImg;
    }
}
