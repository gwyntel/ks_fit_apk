package com.google.common.escape;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class UnicodeEscaper extends Escaper {
    private static final int DEST_PAD = 32;

    protected UnicodeEscaper() {
    }

    protected static int a(CharSequence charSequence, int i2, int i3) {
        Preconditions.checkNotNull(charSequence);
        if (i2 >= i3) {
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
        int i4 = i2 + 1;
        char cCharAt = charSequence.charAt(i2);
        if (cCharAt < 55296 || cCharAt > 57343) {
            return cCharAt;
        }
        if (cCharAt > 56319) {
            throw new IllegalArgumentException("Unexpected low surrogate character '" + cCharAt + "' with value " + ((int) cCharAt) + " at index " + i2 + " in '" + ((Object) charSequence) + "'");
        }
        if (i4 == i3) {
            return -cCharAt;
        }
        char cCharAt2 = charSequence.charAt(i4);
        if (Character.isLowSurrogate(cCharAt2)) {
            return Character.toCodePoint(cCharAt, cCharAt2);
        }
        throw new IllegalArgumentException("Expected low surrogate but got char '" + cCharAt2 + "' with value " + ((int) cCharAt2) + " at index " + i4 + " in '" + ((Object) charSequence) + "'");
    }

    private static char[] growBuffer(char[] cArr, int i2, int i3) {
        if (i3 < 0) {
            throw new AssertionError("Cannot increase internal buffer any further");
        }
        char[] cArr2 = new char[i3];
        if (i2 > 0) {
            System.arraycopy(cArr, 0, cArr2, 0, i2);
        }
        return cArr2;
    }

    protected abstract char[] b(int i2);

    protected final String c(String str, int i2) {
        int length = str.length();
        char[] cArrA = Platform.a();
        int i3 = 0;
        int length2 = 0;
        while (i2 < length) {
            int iA = a(str, i2, length);
            if (iA < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] cArrB = b(iA);
            int i4 = (Character.isSupplementaryCodePoint(iA) ? 2 : 1) + i2;
            if (cArrB != null) {
                int i5 = i2 - i3;
                int i6 = length2 + i5;
                int length3 = cArrB.length + i6;
                if (cArrA.length < length3) {
                    cArrA = growBuffer(cArrA, length2, length3 + (length - i2) + 32);
                }
                if (i5 > 0) {
                    str.getChars(i3, i2, cArrA, length2);
                    length2 = i6;
                }
                if (cArrB.length > 0) {
                    System.arraycopy(cArrB, 0, cArrA, length2, cArrB.length);
                    length2 += cArrB.length;
                }
                i3 = i4;
            }
            i2 = d(str, i4, length);
        }
        int i7 = length - i3;
        if (i7 > 0) {
            int i8 = i7 + length2;
            if (cArrA.length < i8) {
                cArrA = growBuffer(cArrA, length2, i8);
            }
            str.getChars(i3, length, cArrA, length2);
            length2 = i8;
        }
        return new String(cArrA, 0, length2);
    }

    protected int d(CharSequence charSequence, int i2, int i3) {
        while (i2 < i3) {
            int iA = a(charSequence, i2, i3);
            if (iA < 0 || b(iA) != null) {
                break;
            }
            i2 += Character.isSupplementaryCodePoint(iA) ? 2 : 1;
        }
        return i2;
    }

    @Override // com.google.common.escape.Escaper
    public String escape(String str) {
        Preconditions.checkNotNull(str);
        int length = str.length();
        int iD = d(str, 0, length);
        return iD == length ? str : c(str, iD);
    }
}
