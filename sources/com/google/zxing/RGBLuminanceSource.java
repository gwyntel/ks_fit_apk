package com.google.zxing;

import androidx.constraintlayout.core.motion.utils.TypedValues;

/* loaded from: classes3.dex */
public final class RGBLuminanceSource extends LuminanceSource {
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final byte[] luminances;

    /* renamed from: top, reason: collision with root package name */
    private final int f15396top;

    public RGBLuminanceSource(int i2, int i3, int[] iArr) {
        super(i2, i3);
        this.dataWidth = i2;
        this.dataHeight = i3;
        this.left = 0;
        this.f15396top = 0;
        int i4 = i2 * i3;
        this.luminances = new byte[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = iArr[i5];
            this.luminances[i5] = (byte) (((((i6 >> 16) & 255) + ((i6 >> 7) & TypedValues.PositionType.TYPE_POSITION_TYPE)) + (i6 & 255)) / 4);
        }
    }

    @Override // com.google.zxing.LuminanceSource
    public LuminanceSource crop(int i2, int i3, int i4, int i5) {
        return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + i2, this.f15396top + i3, i4, i5);
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int i2 = this.dataWidth;
        if (width == i2 && height == this.dataHeight) {
            return this.luminances;
        }
        int i3 = width * height;
        byte[] bArr = new byte[i3];
        int i4 = (this.f15396top * i2) + this.left;
        if (width == i2) {
            System.arraycopy(this.luminances, i4, bArr, 0, i3);
            return bArr;
        }
        for (int i5 = 0; i5 < height; i5++) {
            System.arraycopy(this.luminances, i4, bArr, i5 * width, width);
            i4 += this.dataWidth;
        }
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getRow(int i2, byte[] bArr) {
        if (i2 < 0 || i2 >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: ".concat(String.valueOf(i2)));
        }
        int width = getWidth();
        if (bArr == null || bArr.length < width) {
            bArr = new byte[width];
        }
        System.arraycopy(this.luminances, ((i2 + this.f15396top) * this.dataWidth) + this.left, bArr, 0, width);
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public boolean isCropSupported() {
        return true;
    }

    private RGBLuminanceSource(byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7) {
        super(i6, i7);
        if (i6 + i4 <= i2 && i7 + i5 <= i3) {
            this.luminances = bArr;
            this.dataWidth = i2;
            this.dataHeight = i3;
            this.left = i4;
            this.f15396top = i5;
            return;
        }
        throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
    }
}
