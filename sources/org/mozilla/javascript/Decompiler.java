package org.mozilla.javascript;

import javax.jmdns.impl.constants.DNSRecordClass;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;

/* loaded from: classes5.dex */
public class Decompiler {
    public static final int CASE_GAP_PROP = 3;
    private static final int FUNCTION_END = 167;
    public static final int INDENT_GAP_PROP = 2;
    public static final int INITIAL_INDENT_PROP = 1;
    public static final int ONLY_BODY_FLAG = 1;
    public static final int TO_SOURCE_FLAG = 2;
    private static final boolean printSource = false;
    private char[] sourceBuffer = new char[128];
    private int sourceTop;

    private void append(char c2) throws RuntimeException {
        int i2 = this.sourceTop;
        if (i2 == this.sourceBuffer.length) {
            increaseSourceCapacity(i2 + 1);
        }
        char[] cArr = this.sourceBuffer;
        int i3 = this.sourceTop;
        cArr[i3] = c2;
        this.sourceTop = i3 + 1;
    }

    private void appendString(String str) throws RuntimeException {
        int length = str.length();
        int i2 = this.sourceTop + (length >= 32768 ? 2 : 1) + length;
        if (i2 > this.sourceBuffer.length) {
            increaseSourceCapacity(i2);
        }
        if (length >= 32768) {
            char[] cArr = this.sourceBuffer;
            int i3 = this.sourceTop;
            cArr[i3] = (char) (32768 | (length >>> 16));
            this.sourceTop = i3 + 1;
        }
        char[] cArr2 = this.sourceBuffer;
        int i4 = this.sourceTop;
        cArr2[i4] = (char) length;
        int i5 = i4 + 1;
        this.sourceTop = i5;
        str.getChars(0, length, cArr2, i5);
        this.sourceTop = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:134:0x0234  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String decompile(java.lang.String r18, int r19, org.mozilla.javascript.UintMap r20) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 1402
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Decompiler.decompile(java.lang.String, int, org.mozilla.javascript.UintMap):java.lang.String");
    }

    private static int getNext(String str, int i2, int i3) {
        int i4 = i3 + 1;
        if (i4 < i2) {
            return str.charAt(i4);
        }
        return 0;
    }

    private static int getSourceStringEnd(String str, int i2) {
        return printSourceString(str, i2, false, null);
    }

    private void increaseSourceCapacity(int i2) throws RuntimeException {
        if (i2 <= this.sourceBuffer.length) {
            Kit.codeBug();
        }
        char[] cArr = this.sourceBuffer;
        int length = cArr.length * 2;
        if (length >= i2) {
            i2 = length;
        }
        char[] cArr2 = new char[i2];
        System.arraycopy(cArr, 0, cArr2, 0, this.sourceTop);
        this.sourceBuffer = cArr2;
    }

    private static int printSourceNumber(String str, int i2, StringBuilder sb) {
        int i3;
        char cCharAt = str.charAt(i2);
        int i4 = i2 + 1;
        if (cCharAt == 'S') {
            dCharAt = sb != null ? str.charAt(i4) : 0.0d;
            i3 = i2 + 2;
        } else {
            if (cCharAt != 'J' && cCharAt != 'D') {
                throw new RuntimeException();
            }
            if (sb != null) {
                long jCharAt = (str.charAt(i4) << 48) | (str.charAt(i2 + 2) << 32) | (str.charAt(i2 + 3) << 16) | str.charAt(i2 + 4);
                dCharAt = cCharAt == 'J' ? jCharAt : Double.longBitsToDouble(jCharAt);
            }
            i3 = i2 + 5;
        }
        if (sb != null) {
            sb.append(ScriptRuntime.numberToString(dCharAt, 10));
        }
        return i3;
    }

    private static int printSourceString(String str, int i2, boolean z2, StringBuilder sb) {
        int iCharAt = str.charAt(i2);
        int i3 = i2 + 1;
        if ((32768 & iCharAt) != 0) {
            iCharAt = ((iCharAt & DNSRecordClass.CLASS_MASK) << 16) | str.charAt(i3);
            i3 = i2 + 2;
        }
        if (sb != null) {
            String strSubstring = str.substring(i3, i3 + iCharAt);
            if (z2) {
                sb.append(Typography.quote);
                sb.append(ScriptRuntime.escapeString(strSubstring));
                sb.append(Typography.quote);
            } else {
                sb.append(strSubstring);
            }
        }
        return i3 + iCharAt;
    }

    private String sourceToString(int i2) throws RuntimeException {
        if (i2 < 0 || this.sourceTop < i2) {
            Kit.codeBug();
        }
        return new String(this.sourceBuffer, i2, this.sourceTop - i2);
    }

    void addEOL(int i2) throws RuntimeException {
        if (i2 < 0 || i2 > 166) {
            throw new IllegalArgumentException();
        }
        append((char) i2);
        append((char) 1);
    }

    void addName(String str) throws RuntimeException {
        addToken(39);
        appendString(str);
    }

    void addNumber(double d2) throws RuntimeException {
        addToken(40);
        long j2 = (long) d2;
        if (j2 != d2) {
            long jDoubleToLongBits = Double.doubleToLongBits(d2);
            append('D');
            append((char) (jDoubleToLongBits >> 48));
            append((char) (jDoubleToLongBits >> 32));
            append((char) (jDoubleToLongBits >> 16));
            append((char) jDoubleToLongBits);
            return;
        }
        if (j2 < 0) {
            Kit.codeBug();
        }
        if (j2 <= 65535) {
            append('S');
            append((char) j2);
            return;
        }
        append('J');
        append((char) (j2 >> 48));
        append((char) (j2 >> 32));
        append((char) (j2 >> 16));
        append((char) j2);
    }

    void addRegexp(String str, String str2) throws RuntimeException {
        addToken(48);
        appendString(IOUtils.DIR_SEPARATOR_UNIX + str + IOUtils.DIR_SEPARATOR_UNIX + str2);
    }

    void addString(String str) throws RuntimeException {
        addToken(41);
        appendString(str);
    }

    void addToken(int i2) throws RuntimeException {
        if (i2 < 0 || i2 > 166) {
            throw new IllegalArgumentException();
        }
        append((char) i2);
    }

    int getCurrentOffset() {
        return this.sourceTop;
    }

    String getEncodedSource() {
        return sourceToString(0);
    }

    int markFunctionEnd(int i2) throws RuntimeException {
        int currentOffset = getCurrentOffset();
        append(Typography.section);
        return currentOffset;
    }

    int markFunctionStart(int i2) throws RuntimeException {
        int currentOffset = getCurrentOffset();
        if (i2 != 4) {
            addToken(110);
            append((char) i2);
        }
        return currentOffset;
    }
}
