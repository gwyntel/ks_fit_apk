package org.mozilla.javascript.tools.idswitch;

import kotlin.text.Typography;

/* loaded from: classes5.dex */
class CodePrinter {
    private static final int LITERAL_CHAR_MAX_SIZE = 6;
    private int offset;
    private String lineTerminator = "\n";
    private int indentStep = 4;
    private int indentTabSize = 8;
    private char[] buffer = new char[4096];

    CodePrinter() {
    }

    private int add_area(int i2) {
        int iEnsure_area = ensure_area(i2);
        this.offset = i2 + iEnsure_area;
        return iEnsure_area;
    }

    private static char digit_to_hex_letter(int i2) {
        return (char) (i2 < 10 ? i2 + 48 : i2 + 55);
    }

    private int ensure_area(int i2) {
        int i3 = this.offset;
        int i4 = i2 + i3;
        char[] cArr = this.buffer;
        if (i4 > cArr.length) {
            int length = cArr.length * 2;
            if (i4 <= length) {
                i4 = length;
            }
            char[] cArr2 = new char[i4];
            System.arraycopy(cArr, 0, cArr2, 0, i3);
            this.buffer = cArr2;
        }
        return i3;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int put_string_literal_char(int r3, int r4, boolean r5) {
        /*
            r2 = this;
            r0 = 12
            r1 = 1
            if (r4 == r0) goto L26
            r0 = 13
            if (r4 == r0) goto L23
            r0 = 34
            if (r4 == r0) goto L29
            r0 = 39
            if (r4 == r0) goto L20
            switch(r4) {
                case 8: goto L1d;
                case 9: goto L1a;
                case 10: goto L16;
                default: goto L14;
            }
        L14:
            r5 = 0
            goto L29
        L16:
            r4 = 110(0x6e, float:1.54E-43)
        L18:
            r5 = r1
            goto L29
        L1a:
            r4 = 116(0x74, float:1.63E-43)
            goto L18
        L1d:
            r4 = 98
            goto L18
        L20:
            r5 = r5 ^ 1
            goto L29
        L23:
            r4 = 114(0x72, float:1.6E-43)
            goto L18
        L26:
            r4 = 102(0x66, float:1.43E-43)
            goto L18
        L29:
            r0 = 92
            if (r5 == 0) goto L39
            char[] r5 = r2.buffer
            r5[r3] = r0
            int r0 = r3 + 1
            char r4 = (char) r4
            r5[r0] = r4
            int r3 = r3 + 2
            goto L88
        L39:
            r5 = 32
            if (r5 > r4) goto L48
            r5 = 126(0x7e, float:1.77E-43)
            if (r4 > r5) goto L48
            char[] r5 = r2.buffer
            char r4 = (char) r4
            r5[r3] = r4
            int r3 = r3 + r1
            goto L88
        L48:
            char[] r5 = r2.buffer
            r5[r3] = r0
            int r0 = r3 + 1
            r1 = 117(0x75, float:1.64E-43)
            r5[r0] = r1
            int r0 = r3 + 2
            int r1 = r4 >> 12
            r1 = r1 & 15
            char r1 = digit_to_hex_letter(r1)
            r5[r0] = r1
            char[] r5 = r2.buffer
            int r0 = r3 + 3
            int r1 = r4 >> 8
            r1 = r1 & 15
            char r1 = digit_to_hex_letter(r1)
            r5[r0] = r1
            char[] r5 = r2.buffer
            int r0 = r3 + 4
            int r1 = r4 >> 4
            r1 = r1 & 15
            char r1 = digit_to_hex_letter(r1)
            r5[r0] = r1
            char[] r5 = r2.buffer
            int r0 = r3 + 5
            r4 = r4 & 15
            char r4 = digit_to_hex_letter(r4)
            r5[r0] = r4
            int r3 = r3 + 6
        L88:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.idswitch.CodePrinter.put_string_literal_char(int, int, boolean):int");
    }

    public void clear() {
        this.offset = 0;
    }

    public void erase(int i2, int i3) {
        char[] cArr = this.buffer;
        System.arraycopy(cArr, i3, cArr, i2, this.offset - i3);
        this.offset -= i3 - i2;
    }

    public int getIndentStep() {
        return this.indentStep;
    }

    public int getIndentTabSize() {
        return this.indentTabSize;
    }

    public int getLastChar() {
        int i2 = this.offset;
        if (i2 == 0) {
            return -1;
        }
        return this.buffer[i2 - 1];
    }

    public String getLineTerminator() {
        return this.lineTerminator;
    }

    public int getOffset() {
        return this.offset;
    }

    public void indent(int i2) {
        int i3;
        int i4 = this.indentStep * i2;
        int i5 = this.indentTabSize;
        if (i5 <= 0) {
            i3 = 0;
        } else {
            int i6 = i4 / i5;
            i4 = (i4 % i5) + i6;
            i3 = i6;
        }
        int iAdd_area = add_area(i4);
        int i7 = i3 + iAdd_area;
        int i8 = i4 + iAdd_area;
        while (iAdd_area != i7) {
            this.buffer[iAdd_area] = '\t';
            iAdd_area++;
        }
        while (iAdd_area != i8) {
            this.buffer[iAdd_area] = ' ';
            iAdd_area++;
        }
    }

    public void line(int i2, String str) {
        indent(i2);
        p(str);
        nl();
    }

    public void nl() {
        p('\n');
    }

    public void p(char c2) {
        this.buffer[add_area(1)] = c2;
    }

    public void qchar(int i2) {
        int iEnsure_area = ensure_area(8);
        this.buffer[iEnsure_area] = '\'';
        int iPut_string_literal_char = put_string_literal_char(iEnsure_area + 1, i2, false);
        this.buffer[iPut_string_literal_char] = '\'';
        this.offset = iPut_string_literal_char + 1;
    }

    public void qstring(String str) {
        int length = str.length();
        int iEnsure_area = ensure_area((length * 6) + 2);
        this.buffer[iEnsure_area] = Typography.quote;
        int iPut_string_literal_char = iEnsure_area + 1;
        for (int i2 = 0; i2 != length; i2++) {
            iPut_string_literal_char = put_string_literal_char(iPut_string_literal_char, str.charAt(i2), true);
        }
        this.buffer[iPut_string_literal_char] = Typography.quote;
        this.offset = iPut_string_literal_char + 1;
    }

    public void setIndentStep(int i2) {
        this.indentStep = i2;
    }

    public void setIndentTabSize(int i2) {
        this.indentTabSize = i2;
    }

    public void setLineTerminator(String str) {
        this.lineTerminator = str;
    }

    public String toString() {
        return new String(this.buffer, 0, this.offset);
    }

    public void p(String str) {
        int length = str.length();
        str.getChars(0, length, this.buffer, add_area(length));
    }

    public final void p(char[] cArr) {
        p(cArr, 0, cArr.length);
    }

    public void p(char[] cArr, int i2, int i3) {
        int i4 = i3 - i2;
        System.arraycopy(cArr, i2, this.buffer, add_area(i4), i4);
    }

    public void p(int i2) {
        p(Integer.toString(i2));
    }
}
