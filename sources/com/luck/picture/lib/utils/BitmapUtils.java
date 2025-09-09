package com.luck.picture.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import com.luck.picture.lib.basic.PictureContentResolver;
import com.luck.picture.lib.config.PictureMimeType;
import com.yc.utesdk.ble.close.KeyType;
import io.flutter.plugin.platform.PlatformPlugin;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class BitmapUtils {
    private static final int ARGB_8888_MEMORY_BYTE = 4;
    private static final int MAX_BITMAP_SIZE = 104857600;

    public static int computeSize(int i2, int i3) {
        if (i2 % 2 == 1) {
            i2++;
        }
        if (i3 % 2 == 1) {
            i3++;
        }
        int iMax = Math.max(i2, i3);
        float fMin = Math.min(i2, i3) / iMax;
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

    public static int[] getMaxImageSize(int i2, int i3) {
        int i4 = -1;
        if (i2 == 0 && i3 == 0) {
            return new int[]{-1, -1};
        }
        int iComputeSize = computeSize(i2, i3);
        long totalMemory = getTotalMemory();
        boolean z2 = false;
        int i5 = iComputeSize;
        int i6 = -1;
        while (!z2) {
            i4 = i2 / i5;
            i6 = i3 / i5;
            if (i4 * i6 * 4 > totalMemory) {
                i5 *= 2;
            } else {
                z2 = true;
            }
        }
        return new int[]{i4, i6};
    }

    public static long getTotalMemory() {
        long j2 = Runtime.getRuntime().totalMemory();
        if (j2 > 104857600) {
            return 104857600L;
        }
        return j2;
    }

    public static int readPictureDegree(Context context, String str) {
        ExifInterface exifInterface;
        InputStream inputStreamOpenInputStream = null;
        try {
            try {
                if (PictureMimeType.isContent(str)) {
                    inputStreamOpenInputStream = PictureContentResolver.openInputStream(context, Uri.parse(str));
                    exifInterface = new ExifInterface(inputStreamOpenInputStream);
                } else {
                    exifInterface = new ExifInterface(str);
                }
                int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                if (attributeInt == 3) {
                    PictureFileUtils.close(inputStreamOpenInputStream);
                    return 180;
                }
                if (attributeInt == 6) {
                    PictureFileUtils.close(inputStreamOpenInputStream);
                    return 90;
                }
                if (attributeInt != 8) {
                    PictureFileUtils.close(inputStreamOpenInputStream);
                    return 0;
                }
                PictureFileUtils.close(inputStreamOpenInputStream);
                return KeyType.QUERY_BRIGHT_SCREEN_PARAM;
            } catch (Exception e2) {
                e2.printStackTrace();
                PictureFileUtils.close(inputStreamOpenInputStream);
                return 0;
            }
        } catch (Throwable th) {
            PictureFileUtils.close(inputStreamOpenInputStream);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void rotateImage(android.content.Context r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.utils.BitmapUtils.rotateImage(android.content.Context, java.lang.String):void");
    }

    public static Bitmap rotatingImage(Bitmap bitmap, int i2) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static void saveBitmapFile(Bitmap bitmap, FileOutputStream fileOutputStream) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream);
                    fileOutputStream.write(byteArrayOutputStream2.toByteArray());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    PictureFileUtils.close(fileOutputStream);
                    PictureFileUtils.close(byteArrayOutputStream2);
                } catch (Exception e2) {
                    e = e2;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    e.printStackTrace();
                    PictureFileUtils.close(fileOutputStream);
                    PictureFileUtils.close(byteArrayOutputStream);
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    PictureFileUtils.close(fileOutputStream);
                    PictureFileUtils.close(byteArrayOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }
}
