package com.huawei.hms.ml.common.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class ImageConvertUtils {
    public static final int INDEX_PLANE_U = 1;
    public static final int INDEX_PLANE_V = 2;
    public static final int INDEX_PLANE_Y = 0;
    private static final ImageConvertUtils INSTANCE = new ImageConvertUtils();
    public static final int YUV_FORMAT_I420 = 1;
    public static final int YUV_FORMAT_NV21 = 2;

    private ImageConvertUtils() {
    }

    private static byte[] argbToNv21(int[] iArr, int i2, int i3) {
        int i4 = i2 * i3;
        int i5 = (i4 * 3) / 2;
        byte[] bArr = new byte[i5];
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < i3; i8++) {
            int i9 = 0;
            while (i9 < i2) {
                int i10 = iArr[i7];
                int i11 = (16711680 & i10) >> 16;
                int i12 = (65280 & i10) >> 8;
                int i13 = 255;
                int i14 = i10 & 255;
                int i15 = (((((i11 * 66) + (i12 * 129)) + (i14 * 25)) + 128) >> 8) + 16;
                int i16 = (((((i11 * (-38)) - (i12 * 74)) + (i14 * 112)) + 128) >> 8) + 128;
                int i17 = (((((i11 * 112) - (i12 * 94)) - (i14 * 18)) + 128) >> 8) + 128;
                int i18 = i6 + 1;
                if (i15 < 0) {
                    i15 = 0;
                } else if (i15 > 255) {
                    i15 = 255;
                }
                bArr[i6] = (byte) i15;
                if (i8 % 2 == 0 && i7 % 2 == 0 && i4 < i5 - 2) {
                    int i19 = i4 + 1;
                    if (i17 < 0) {
                        i17 = 0;
                    } else if (i17 > 255) {
                        i17 = 255;
                    }
                    bArr[i4] = (byte) i17;
                    i4 += 2;
                    if (i16 < 0) {
                        i13 = 0;
                    } else if (i16 <= 255) {
                        i13 = i16;
                    }
                    bArr[i19] = (byte) i13;
                }
                i7++;
                i9++;
                i6 = i18;
            }
        }
        return bArr;
    }

    public static byte[] bitmap2Jpeg(Bitmap bitmap, int i2) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (IOException unused) {
            return new byte[0];
        }
    }

    public static byte[] bitmapToNv21(Bitmap bitmap, int i2, int i3) {
        if (bitmap == null || bitmap.getWidth() < i2 || bitmap.getHeight() < i3) {
            return new byte[0];
        }
        int[] iArr = new int[i2 * i3];
        bitmap.getPixels(iArr, 0, i2, 0, 0, i2, i3);
        return argbToNv21(iArr, i2, i3);
    }

    public static byte[] buffer2Byte(ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        int iLimit = byteBuffer.limit();
        byte[] bArr = new byte[iLimit];
        byteBuffer.get(bArr, 0, iLimit);
        return bArr;
    }

    public static byte[] byteToNv21(byte[] bArr) {
        int length = bArr.length;
        int i2 = (length * 2) / 3;
        int i3 = length - i2;
        int i4 = length / 6;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, i2);
        for (int i5 = 0; i5 < i3; i5++) {
            if (i5 % 2 == 0) {
                bArr2[i2 + i5] = bArr[(i5 / 2) + i2];
            } else {
                bArr2[i2 + i5] = bArr[i2 + i4 + (i5 / 2)];
            }
        }
        return bArr2;
    }

    private static void checkFormat(Image image, int i2) {
        if (i2 != 1 && i2 != 2) {
            throw new IllegalArgumentException("only support YUV_FORMAT_I420 and YUV_FORMAT_NV21");
        }
        if (isImageFormatSupported(image)) {
            return;
        }
        throw new RuntimeException("can't convert Image to byte array, format " + image.getFormat());
    }

    private static int getChannelOffset(int i2, int i3, int i4) {
        if (i2 != 0) {
            return i2 != 1 ? i3 == 1 ? (int) (i4 * 1.25d) : i4 : i3 == 1 ? i4 : i4 + 1;
        }
        return 0;
    }

    @TargetApi(21)
    public static byte[] getDataFromImage(Image image, int i2) {
        Rect rect;
        int i3;
        int i4 = i2;
        checkFormat(image, i2);
        Rect cropRect = image.getCropRect();
        int format = image.getFormat();
        int iWidth = cropRect.width();
        int iHeight = cropRect.height();
        Image.Plane[] planes = image.getPlanes();
        int i5 = iWidth * iHeight;
        byte[] bArr = new byte[(ImageFormat.getBitsPerPixel(format) * i5) / 8];
        int i6 = 0;
        byte[] bArr2 = new byte[planes[0].getRowStride()];
        int i7 = 0;
        while (i7 < planes.length) {
            ByteBuffer buffer = planes[i7].getBuffer();
            int rowStride = planes[i7].getRowStride();
            int pixelStride = planes[i7].getPixelStride();
            int channelOffset = getChannelOffset(i7, i4, i5);
            int outputStride = getOutputStride(i7, i4);
            int i8 = i7 == 0 ? i6 : 1;
            int i9 = iWidth >> i8;
            int i10 = iHeight >> i8;
            int i11 = iWidth;
            buffer.position(((cropRect.top >> i8) * rowStride) + ((cropRect.left >> i8) * pixelStride));
            int i12 = 0;
            while (i12 < i10) {
                if (pixelStride == 1 && outputStride == 1) {
                    buffer.get(bArr, channelOffset, i9);
                    channelOffset += i9;
                    rect = cropRect;
                    i3 = i9;
                } else {
                    rect = cropRect;
                    i3 = ((i9 - 1) * pixelStride) + 1;
                    buffer.get(bArr2, 0, i3);
                    for (int i13 = 0; i13 < i9; i13++) {
                        bArr[channelOffset] = bArr2[i13 * pixelStride];
                        channelOffset += outputStride;
                    }
                }
                if (i12 < i10 - 1) {
                    buffer.position((buffer.position() + rowStride) - i3);
                }
                i12++;
                cropRect = rect;
            }
            i7++;
            i4 = i2;
            iWidth = i11;
            i6 = 0;
        }
        return bArr;
    }

    public static ImageConvertUtils getInstance() {
        return INSTANCE;
    }

    private static int getOutputStride(int i2, int i3) {
        return (i2 == 0 || i3 == 1) ? 1 : 2;
    }

    private static boolean isImageFormatSupported(Image image) {
        int format = image.getFormat();
        return format == 35 || format == 17 || format == 842094169;
    }

    public static byte[] nv21ToGray(byte[] bArr, int i2, int i3) {
        int i4 = i2 * i3;
        int i5 = i4 / 2;
        byte[] bArr2 = new byte[i5];
        Arrays.fill(bArr2, Byte.MIN_VALUE);
        System.arraycopy(bArr2, 0, bArr, i4, i5);
        return bArr;
    }

    public static byte[] nv21ToJpeg(byte[] bArr, int i2, int i3) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                new YuvImage(bArr, 17, i2, i3, null).compressToJpeg(new Rect(0, 0, i2, i3), 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (IOException unused) {
            return new byte[0];
        }
    }

    public static byte[] yuv2Buffer(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        ByteBuffer buffer2 = planes[1].getBuffer();
        ByteBuffer buffer3 = planes[2].getBuffer();
        int iRemaining = buffer.remaining();
        int iRemaining2 = buffer2.remaining();
        int iRemaining3 = buffer3.remaining();
        int i2 = iRemaining + iRemaining2 + iRemaining3;
        byte[] bArr = new byte[i2];
        buffer.get(bArr, 0, iRemaining);
        buffer3.get(bArr, iRemaining, iRemaining3);
        buffer2.get(bArr, i2 - iRemaining2, iRemaining2);
        return bArr;
    }
}
