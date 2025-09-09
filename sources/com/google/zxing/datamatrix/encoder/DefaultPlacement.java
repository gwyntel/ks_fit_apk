package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

/* loaded from: classes3.dex */
public class DefaultPlacement {
    private final byte[] bits;
    private final CharSequence codewords;
    private final int numcols;
    private final int numrows;

    public DefaultPlacement(CharSequence charSequence, int i2, int i3) {
        this.codewords = charSequence;
        this.numcols = i2;
        this.numrows = i3;
        byte[] bArr = new byte[i2 * i3];
        this.bits = bArr;
        Arrays.fill(bArr, (byte) -1);
    }

    private void corner1(int i2) {
        module(this.numrows - 1, 0, i2, 1);
        module(this.numrows - 1, 1, i2, 2);
        module(this.numrows - 1, 2, i2, 3);
        module(0, this.numcols - 2, i2, 4);
        module(0, this.numcols - 1, i2, 5);
        module(1, this.numcols - 1, i2, 6);
        module(2, this.numcols - 1, i2, 7);
        module(3, this.numcols - 1, i2, 8);
    }

    private void corner2(int i2) {
        module(this.numrows - 3, 0, i2, 1);
        module(this.numrows - 2, 0, i2, 2);
        module(this.numrows - 1, 0, i2, 3);
        module(0, this.numcols - 4, i2, 4);
        module(0, this.numcols - 3, i2, 5);
        module(0, this.numcols - 2, i2, 6);
        module(0, this.numcols - 1, i2, 7);
        module(1, this.numcols - 1, i2, 8);
    }

    private void corner3(int i2) {
        module(this.numrows - 3, 0, i2, 1);
        module(this.numrows - 2, 0, i2, 2);
        module(this.numrows - 1, 0, i2, 3);
        module(0, this.numcols - 2, i2, 4);
        module(0, this.numcols - 1, i2, 5);
        module(1, this.numcols - 1, i2, 6);
        module(2, this.numcols - 1, i2, 7);
        module(3, this.numcols - 1, i2, 8);
    }

    private void corner4(int i2) {
        module(this.numrows - 1, 0, i2, 1);
        module(this.numrows - 1, this.numcols - 1, i2, 2);
        module(0, this.numcols - 3, i2, 3);
        module(0, this.numcols - 2, i2, 4);
        module(0, this.numcols - 1, i2, 5);
        module(1, this.numcols - 3, i2, 6);
        module(1, this.numcols - 2, i2, 7);
        module(1, this.numcols - 1, i2, 8);
    }

    private boolean hasBit(int i2, int i3) {
        return this.bits[(i3 * this.numcols) + i2] >= 0;
    }

    private void module(int i2, int i3, int i4, int i5) {
        if (i2 < 0) {
            int i6 = this.numrows;
            i2 += i6;
            i3 += 4 - ((i6 + 4) % 8);
        }
        if (i3 < 0) {
            int i7 = this.numcols;
            i3 += i7;
            i2 += 4 - ((i7 + 4) % 8);
        }
        setBit(i3, i2, (this.codewords.charAt(i4) & (1 << (8 - i5))) != 0);
    }

    private void setBit(int i2, int i3, boolean z2) {
        this.bits[(i3 * this.numcols) + i2] = z2 ? (byte) 1 : (byte) 0;
    }

    private void utah(int i2, int i3, int i4) {
        int i5 = i2 - 2;
        int i6 = i3 - 2;
        module(i5, i6, i4, 1);
        int i7 = i3 - 1;
        module(i5, i7, i4, 2);
        int i8 = i2 - 1;
        module(i8, i6, i4, 3);
        module(i8, i7, i4, 4);
        module(i8, i3, i4, 5);
        module(i2, i6, i4, 6);
        module(i2, i7, i4, 7);
        module(i2, i3, i4, 8);
    }

    public final boolean getBit(int i2, int i3) {
        return this.bits[(i3 * this.numcols) + i2] == 1;
    }

    public final void place() {
        int i2;
        int i3;
        int i4 = 0;
        int i5 = 0;
        int i6 = 4;
        while (true) {
            if (i6 == this.numrows && i4 == 0) {
                corner1(i5);
                i5++;
            }
            if (i6 == this.numrows - 2 && i4 == 0 && this.numcols % 4 != 0) {
                corner2(i5);
                i5++;
            }
            if (i6 == this.numrows - 2 && i4 == 0 && this.numcols % 8 == 4) {
                corner3(i5);
                i5++;
            }
            if (i6 == this.numrows + 4 && i4 == 2 && this.numcols % 8 == 0) {
                corner4(i5);
                i5++;
            }
            while (true) {
                if (i6 < this.numrows && i4 >= 0 && !hasBit(i4, i6)) {
                    utah(i6, i4, i5);
                    i5++;
                }
                int i7 = i6 - 2;
                int i8 = i4 + 2;
                if (i7 < 0 || i8 >= this.numcols) {
                    break;
                }
                i6 = i7;
                i4 = i8;
            }
            int i9 = i6 - 1;
            int i10 = i4 + 5;
            while (true) {
                if (i9 >= 0 && i10 < this.numcols && !hasBit(i10, i9)) {
                    utah(i9, i10, i5);
                    i5++;
                }
                int i11 = i9 + 2;
                int i12 = i10 - 2;
                i2 = this.numrows;
                if (i11 >= i2 || i12 < 0) {
                    break;
                }
                i9 = i11;
                i10 = i12;
            }
            i6 = i9 + 5;
            i4 = i10 - 1;
            if (i6 >= i2 && i4 >= (i3 = this.numcols)) {
                break;
            }
        }
        if (hasBit(i3 - 1, i2 - 1)) {
            return;
        }
        setBit(this.numcols - 1, this.numrows - 1, true);
        setBit(this.numcols - 2, this.numrows - 2, true);
    }
}
