package com.huawei.hms.scankit.util;

/* loaded from: classes4.dex */
public class LoadOpencvJNIUtil {
    private static final String TAG = "LoadOpencvJNIUtil";

    public static byte[] QRCornerDetect(byte[] bArr, int i2, int i3, float[] fArr) {
        if (bArr != null) {
            return OpencvJNI.QRCornerDetect(bArr, i2, i3, fArr);
        }
        return null;
    }

    public static float[] QuadFitting(float[] fArr, int i2, float[] fArr2) {
        if (fArr2 != null) {
            return OpencvJNI.QuadFitting(fArr, i2, fArr2);
        }
        return null;
    }

    public static byte[] adaptivebinary(byte[] bArr, int i2, int i3, int i4, boolean z2) {
        if (bArr == null || i2 < i4 || i3 < i4) {
            return null;
        }
        return OpencvJNI.adaptiveBinary(bArr, i2, i3, i4, z2);
    }

    public static byte[] imageResize(byte[] bArr, int i2, int i3, int i4, int i5) {
        if (bArr != null) {
            return OpencvJNI.imageResize(bArr, i2, i3, i4, i5);
        }
        return null;
    }

    public static byte[] imageRotate(byte[] bArr, int i2, int i3, int i4, int i5, float f2, double d2) {
        if (bArr != null) {
            return OpencvJNI.imageRotate(bArr, i2, i3, i4, i5, f2, d2);
        }
        return null;
    }

    public static float[] multiBarcodeDetect(byte[] bArr, int i2, int i3, int i4, boolean z2) {
        if (bArr != null) {
            return OpencvJNI.multiBarcodeDetect(bArr, i2, i3, i4, z2);
        }
        return null;
    }

    public static byte[] removeMoirePattern(byte[] bArr, int i2, int i3) {
        if (bArr != null) {
            return OpencvJNI.removeMoirePattern(bArr, i2, i3);
        }
        return null;
    }

    public static void setModel(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, int i4) {
        if (bArr != null) {
            OpencvJNI.setModel(bArr, i2, bArr2, i3, bArr3, i4);
        }
    }

    public static byte[] sharpen(byte[] bArr, int i2, int i3) {
        if (bArr != null) {
            return OpencvJNI.sharpen(bArr, i2, i3);
        }
        return null;
    }
}
