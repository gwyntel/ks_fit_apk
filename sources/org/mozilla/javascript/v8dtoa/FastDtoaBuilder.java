package org.mozilla.javascript.v8dtoa;

import java.util.Arrays;

/* loaded from: classes5.dex */
public class FastDtoaBuilder {
    static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    final char[] chars = new char[25];
    int end = 0;
    boolean formatted = false;
    int point;

    private void toExponentialFormat(int i2, int i3) {
        char c2;
        int i4 = this.end;
        if (i4 - i2 > 1) {
            int i5 = i2 + 1;
            char[] cArr = this.chars;
            System.arraycopy(cArr, i5, cArr, i2 + 2, i4 - i5);
            this.chars[i5] = '.';
            this.end++;
        }
        char[] cArr2 = this.chars;
        int i6 = this.end;
        int i7 = i6 + 1;
        this.end = i7;
        cArr2[i6] = 'e';
        int i8 = i3 - 1;
        if (i8 < 0) {
            i8 = -i8;
            c2 = '-';
        } else {
            c2 = '+';
        }
        int i9 = i6 + 2;
        this.end = i9;
        cArr2[i7] = c2;
        if (i8 > 99) {
            i9 = i6 + 4;
        } else if (i8 > 9) {
            i9 = i6 + 3;
        }
        this.end = i9 + 1;
        while (true) {
            int i10 = i9 - 1;
            this.chars[i9] = digits[i8 % 10];
            i8 /= 10;
            if (i8 == 0) {
                return;
            } else {
                i9 = i10;
            }
        }
    }

    private void toFixedFormat(int i2, int i3) {
        int i4 = this.point;
        int i5 = this.end;
        if (i4 >= i5) {
            if (i4 > i5) {
                Arrays.fill(this.chars, i5, i4, '0');
                int i6 = this.end;
                this.end = i6 + (this.point - i6);
                return;
            }
            return;
        }
        if (i3 > 0) {
            char[] cArr = this.chars;
            System.arraycopy(cArr, i4, cArr, i4 + 1, i5 - i4);
            this.chars[this.point] = '.';
            this.end++;
            return;
        }
        int i7 = i2 + 2;
        int i8 = i7 - i3;
        char[] cArr2 = this.chars;
        System.arraycopy(cArr2, i2, cArr2, i8, i5 - i2);
        char[] cArr3 = this.chars;
        cArr3[i2] = '0';
        cArr3[i2 + 1] = '.';
        if (i3 < 0) {
            Arrays.fill(cArr3, i7, i8, '0');
        }
        this.end += 2 - i3;
    }

    void append(char c2) {
        char[] cArr = this.chars;
        int i2 = this.end;
        this.end = i2 + 1;
        cArr[i2] = c2;
    }

    void decreaseLast() {
        this.chars[this.end - 1] = (char) (r0[r1] - 1);
    }

    public String format() {
        if (!this.formatted) {
            int i2 = this.chars[0] == '-' ? 1 : 0;
            int i3 = this.point - i2;
            if (i3 < -5 || i3 > 21) {
                toExponentialFormat(i2, i3);
            } else {
                toFixedFormat(i2, i3);
            }
            this.formatted = true;
        }
        return new String(this.chars, 0, this.end);
    }

    public void reset() {
        this.end = 0;
        this.formatted = false;
    }

    public String toString() {
        return "[chars:" + new String(this.chars, 0, this.end) + ", point:" + this.point + "]";
    }
}
