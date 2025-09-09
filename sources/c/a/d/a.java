package c.a.d;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: c.a.d.a$a, reason: collision with other inner class name */
    public static class C0020a<T> implements Iterator<T> {

        /* renamed from: a, reason: collision with root package name */
        public final T[] f8114a;

        /* renamed from: b, reason: collision with root package name */
        public int f8115b = 0;

        public C0020a(T[] tArr) {
            this.f8114a = tArr;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f8115b < this.f8114a.length;
        }

        @Override // java.util.Iterator
        public T next() {
            int i2 = this.f8115b;
            T[] tArr = this.f8114a;
            if (i2 != tArr.length) {
                this.f8115b = i2 + 1;
                return tArr[i2];
            }
            throw new NoSuchElementException("Out of elements: " + this.f8115b);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove element from an Array.");
        }
    }

    public static boolean a(char[] cArr, char[] cArr2) {
        if (cArr == cArr2) {
            return true;
        }
        if (cArr == null || cArr2 == null || cArr.length != cArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 != cArr.length; i2++) {
            if (cArr[i2] != cArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static int b(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        int length = bArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            i2 = (i2 * 257) ^ bArr[length];
        }
    }

    public static boolean c(byte[] bArr, byte[] bArr2) {
        if (bArr == bArr2) {
            return true;
        }
        if (bArr == null || bArr2 == null) {
            return false;
        }
        if (bArr.length != bArr2.length) {
            return !c(bArr, bArr);
        }
        int i2 = 0;
        for (int i3 = 0; i3 != bArr.length; i3++) {
            i2 |= bArr[i3] ^ bArr2[i3];
        }
        return i2 == 0;
    }

    public static int b(int[] iArr) {
        if (iArr == null) {
            return 0;
        }
        int length = iArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            i2 = (i2 * 257) ^ iArr[length];
        }
    }

    public static boolean a(byte[] bArr, byte[] bArr2) {
        if (bArr == bArr2) {
            return true;
        }
        if (bArr == null || bArr2 == null || bArr.length != bArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 != bArr.length; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static int b(int[] iArr, int i2, int i3) {
        if (iArr == null) {
            return 0;
        }
        int i4 = i3 + 1;
        while (true) {
            i3--;
            if (i3 < 0) {
                return i4;
            }
            i4 = (i4 * 257) ^ iArr[i2 + i3];
        }
    }

    public static int[] c(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        int[] iArr2 = new int[length];
        int i2 = 0;
        while (true) {
            length--;
            if (length < 0) {
                return iArr2;
            }
            iArr2[length] = iArr[i2];
            i2++;
        }
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            if (bArr2 != null) {
                return a(bArr2);
            }
            return a(bArr);
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static boolean a(int[] iArr, int[] iArr2) {
        if (iArr == iArr2) {
            return true;
        }
        if (iArr == null || iArr2 == null || iArr.length != iArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 != iArr.length; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static void a(byte[] bArr, byte b2) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = b2;
        }
    }

    public static int a(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            i2 = (i2 * 257) ^ cArr[length];
        }
    }

    public static byte[] a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public static int[] a(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    public static long[] a(long[] jArr) {
        if (jArr == null) {
            return null;
        }
        long[] jArr2 = new long[jArr.length];
        System.arraycopy(jArr, 0, jArr2, 0, jArr.length);
        return jArr2;
    }

    public static byte[] a(byte[] bArr, int i2, int i3) {
        int iA = a(i2, i3);
        byte[] bArr2 = new byte[iA];
        if (bArr.length - i2 < iA) {
            System.arraycopy(bArr, i2, bArr2, 0, bArr.length - i2);
        } else {
            System.arraycopy(bArr, i2, bArr2, 0, iA);
        }
        return bArr2;
    }

    public static int[] a(int[] iArr, int i2, int i3) {
        int iA = a(i2, i3);
        int[] iArr2 = new int[iA];
        if (iArr.length - i2 < iA) {
            System.arraycopy(iArr, i2, iArr2, 0, iArr.length - i2);
        } else {
            System.arraycopy(iArr, i2, iArr2, 0, iA);
        }
        return iArr2;
    }

    public static int a(int i2, int i3) {
        int i4 = i3 - i2;
        if (i4 >= 0) {
            return i4;
        }
        StringBuffer stringBuffer = new StringBuffer(i2);
        stringBuffer.append(" > ");
        stringBuffer.append(i3);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr == null || bArr2 == null || bArr3 == null) {
            if (bArr == null) {
                return b(bArr2, bArr3);
            }
            if (bArr2 == null) {
                return b(bArr, bArr3);
            }
            return b(bArr, bArr2);
        }
        byte[] bArr4 = new byte[bArr.length + bArr2.length + bArr3.length];
        System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr4, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr4, bArr.length + bArr2.length, bArr3.length);
        return bArr4;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        if (bArr != null && bArr2 != null && bArr3 != null && bArr4 != null) {
            byte[] bArr5 = new byte[bArr.length + bArr2.length + bArr3.length + bArr4.length];
            System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
            System.arraycopy(bArr2, 0, bArr5, bArr.length, bArr2.length);
            System.arraycopy(bArr3, 0, bArr5, bArr.length + bArr2.length, bArr3.length);
            System.arraycopy(bArr4, 0, bArr5, bArr.length + bArr2.length + bArr3.length, bArr4.length);
            return bArr5;
        }
        if (bArr4 == null) {
            return a(bArr, bArr2, bArr3);
        }
        if (bArr3 == null) {
            return a(bArr, bArr2, bArr4);
        }
        if (bArr2 == null) {
            return a(bArr, bArr3, bArr4);
        }
        return a(bArr2, bArr3, bArr4);
    }
}
