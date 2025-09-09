package com.huawei.hms.mlsdk.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import com.huawei.hms.ml.common.utils.StreamUtils;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.yc.utesdk.ble.close.KeyType;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    public static Bitmap cut(Bitmap bitmap, Point[] pointArr, int i2) {
        if (bitmap == null || pointArr == null || pointArr.length != 4) {
            return null;
        }
        for (Point point : pointArr) {
            if (point == null) {
                return null;
            }
        }
        Point point2 = pointArr[0];
        Point point3 = pointArr[1];
        Point point4 = pointArr[2];
        double dAtan2 = Math.atan2(point3.y - point2.y, point3.x - point2.x);
        float degrees = (float) Math.toDegrees(dAtan2);
        if (Math.abs((int) degrees) <= 0) {
            return cutPaddingExtendRect(bitmap, point2, point4, 0, 0, i2);
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i3 = width >> 1;
        int i4 = height >> 1;
        Point point5 = new Point(i3, i4);
        Matrix matrix = new Matrix();
        matrix.setRotate(-degrees, i3, i4);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return cutPaddingExtendRect(bitmapCreateBitmap, rotateCoordinate(bitmap, point5, point2, dAtan2), rotateCoordinate(bitmap, point5, point4, dAtan2), (bitmapCreateBitmap.getWidth() - width) >> 1, (bitmapCreateBitmap.getHeight() - height) >> 1, i2);
    }

    private static Bitmap cutPaddingExtendRect(Bitmap bitmap, Point point, Point point2, int i2, int i3, int i4) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i5 = (point.x + i2) - i4;
        if (i5 < 0) {
            i5 = 0;
        }
        int i6 = (point.y + i3) - i4;
        int i7 = i6 >= 0 ? i6 : 0;
        int i8 = point2.x + i2 + i4;
        if (i8 <= width) {
            width = i8;
        }
        int i9 = point2.y + i3 + i4;
        if (i9 <= height) {
            height = i9;
        }
        return Bitmap.createBitmap(bitmap, i5, i7, width - i5, height - i7);
    }

    public static Bitmap getBitmap(ByteBuffer byteBuffer, MLFrame.Property property) throws Throwable {
        YuvImage yuvImage;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            byteBuffer.rewind();
            int iLimit = byteBuffer.limit();
            byte[] bArr = new byte[iLimit];
            byteBuffer.get(bArr, 0, iLimit);
            yuvImage = new YuvImage(bArr, 17, property.getWidth(), property.getHeight(), null);
            byteArrayOutputStream = new ByteArrayOutputStream();
        } catch (Throwable th) {
            th = th;
        }
        try {
            yuvImage.compressToJpeg(new Rect(0, 0, property.getWidth(), property.getHeight()), 100, byteArrayOutputStream);
            Bitmap bitmapRotateBitmap = rotateBitmap(BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size()), property.getQuadrant());
            StreamUtils.closeStreams(byteArrayOutputStream);
            return bitmapRotateBitmap;
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream2 = byteArrayOutputStream;
            StreamUtils.closeStreams(byteArrayOutputStream2);
            throw th;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i2) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i2 != 1 ? i2 != 2 ? i2 != 3 ? 0 : KeyType.QUERY_BRIGHT_SCREEN_PARAM : 180 : 90);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static Point rotateCoordinate(Bitmap bitmap, Point point, Point point2, double d2) {
        int height = bitmap.getHeight();
        int i2 = point2.x;
        int i3 = height - point2.y;
        int i4 = point.x;
        int i5 = height - point.y;
        double d3 = i2 - i4;
        double d4 = i3 - i5;
        return new Point((int) (((Math.cos(d2) * d3) - (Math.sin(d2) * d4)) + i4), height - ((int) (((d3 * Math.sin(d2)) + (d4 * Math.cos(d2))) + i5)));
    }
}
