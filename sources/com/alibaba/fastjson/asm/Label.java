package com.alibaba.fastjson.asm;

/* loaded from: classes2.dex */
public class Label {
    static final int FORWARD_REFERENCE_HANDLE_MASK = 268435455;
    static final int FORWARD_REFERENCE_TYPE_MASK = -268435456;
    static final int FORWARD_REFERENCE_TYPE_SHORT = 268435456;
    static final int FORWARD_REFERENCE_TYPE_WIDE = 536870912;
    int inputStackTop;
    Label next;
    int outputStackMax;
    int position;
    private int referenceCount;
    private int[] srcAndRefPositions;
    int status;
    Label successor;

    private void addReference(int i2, int i3, int i4) {
        if (this.srcAndRefPositions == null) {
            this.srcAndRefPositions = new int[6];
        }
        int i5 = this.referenceCount;
        int[] iArr = this.srcAndRefPositions;
        if (i5 >= iArr.length) {
            int[] iArr2 = new int[iArr.length + 6];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.srcAndRefPositions = iArr2;
        }
        int[] iArr3 = this.srcAndRefPositions;
        int i6 = this.referenceCount;
        int i7 = i6 + 1;
        this.referenceCount = i7;
        iArr3[i6] = i2;
        this.referenceCount = i6 + 2;
        iArr3[i7] = i3 | i4;
    }

    void put(MethodWriter methodWriter, ByteVector byteVector, int i2, boolean z2) {
        if ((this.status & 2) != 0) {
            if (z2) {
                byteVector.putInt(this.position - i2);
                return;
            } else {
                byteVector.putShort(this.position - i2);
                return;
            }
        }
        if (z2) {
            addReference(i2, byteVector.length, 536870912);
            byteVector.putInt(-1);
        } else {
            addReference(i2, byteVector.length, 268435456);
            byteVector.putShort(-1);
        }
    }

    void resolve(MethodWriter methodWriter, int i2, byte[] bArr) {
        this.status |= 2;
        this.position = i2;
        int i3 = 0;
        while (i3 < this.referenceCount) {
            int[] iArr = this.srcAndRefPositions;
            int i4 = i3 + 1;
            int i5 = iArr[i3];
            i3 += 2;
            int i6 = iArr[i4];
            int i7 = FORWARD_REFERENCE_HANDLE_MASK & i6;
            int i8 = i2 - i5;
            if ((i6 & FORWARD_REFERENCE_TYPE_MASK) == 268435456) {
                bArr[i7] = (byte) (i8 >>> 8);
                bArr[i7 + 1] = (byte) i8;
            } else {
                bArr[i7] = (byte) (i8 >>> 24);
                bArr[i7 + 1] = (byte) (i8 >>> 16);
                bArr[i7 + 2] = (byte) (i8 >>> 8);
                bArr[i7 + 3] = (byte) i8;
            }
        }
    }
}
