package com.google.zxing.pdf417.decoder;

import java.util.Formatter;

/* loaded from: classes3.dex */
class DetectionResultColumn {
    private static final int MAX_NEARBY_DISTANCE = 5;
    private final BoundingBox boundingBox;
    private final Codeword[] codewords;

    DetectionResultColumn(BoundingBox boundingBox) {
        this.boundingBox = new BoundingBox(boundingBox);
        this.codewords = new Codeword[(boundingBox.e() - boundingBox.g()) + 1];
    }

    final BoundingBox a() {
        return this.boundingBox;
    }

    final Codeword b(int i2) {
        return this.codewords[e(i2)];
    }

    final Codeword c(int i2) {
        Codeword codeword;
        Codeword codeword2;
        Codeword codewordB = b(i2);
        if (codewordB != null) {
            return codewordB;
        }
        for (int i3 = 1; i3 < 5; i3++) {
            int iE = e(i2) - i3;
            if (iE >= 0 && (codeword2 = this.codewords[iE]) != null) {
                return codeword2;
            }
            int iE2 = e(i2) + i3;
            Codeword[] codewordArr = this.codewords;
            if (iE2 < codewordArr.length && (codeword = codewordArr[iE2]) != null) {
                return codeword;
            }
        }
        return null;
    }

    final Codeword[] d() {
        return this.codewords;
    }

    final int e(int i2) {
        return i2 - this.boundingBox.g();
    }

    final void f(int i2, Codeword codeword) {
        this.codewords[e(i2)] = codeword;
    }

    public String toString() {
        Formatter formatter = new Formatter();
        try {
            int i2 = 0;
            for (Codeword codeword : this.codewords) {
                if (codeword == null) {
                    formatter.format("%3d:    |   %n", Integer.valueOf(i2));
                    i2++;
                } else {
                    formatter.format("%3d: %3d|%3d%n", Integer.valueOf(i2), Integer.valueOf(codeword.c()), Integer.valueOf(codeword.e()));
                    i2++;
                }
            }
            String string = formatter.toString();
            formatter.close();
            return string;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    formatter.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }
}
