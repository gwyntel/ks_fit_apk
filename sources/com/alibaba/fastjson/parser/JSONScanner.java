package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public final class JSONScanner extends JSONLexerBase {
    private final int len;
    private final String text;

    public JSONScanner(String str) {
        this(str, JSON.DEFAULT_PARSER_FEATURE);
    }

    static boolean charArrayCompare(String str, int i2, char[] cArr) {
        int length = cArr.length;
        if (length + i2 > str.length()) {
            return false;
        }
        for (int i3 = 0; i3 < length; i3++) {
            if (cArr[i3] != str.charAt(i2 + i3)) {
                return false;
            }
        }
        return true;
    }

    static boolean checkDate(char c2, char c3, char c4, char c5, char c6, char c7, int i2, int i3) {
        if (c2 >= '0' && c2 <= '9' && c3 >= '0' && c3 <= '9' && c4 >= '0' && c4 <= '9' && c5 >= '0' && c5 <= '9') {
            if (c6 == '0') {
                if (c7 < '1' || c7 > '9') {
                    return false;
                }
            } else if (c6 != '1' || (c7 != '0' && c7 != '1' && c7 != '2')) {
                return false;
            }
            if (i2 == 48) {
                return i3 >= 49 && i3 <= 57;
            }
            if (i2 != 49 && i2 != 50) {
                return i2 == 51 && (i3 == 48 || i3 == 49);
            }
            if (i3 >= 48 && i3 <= 57) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTime(char c2, char c3, char c4, char c5, char c6, char c7) {
        if (c2 == '0') {
            if (c3 < '0' || c3 > '9') {
                return false;
            }
        } else {
            if (c2 != '1') {
                if (c2 == '2' && c3 >= '0' && c3 <= '4') {
                }
                return false;
            }
            if (c3 < '0' || c3 > '9') {
                return false;
            }
        }
        if (c4 < '0' || c4 > '5') {
            if (c4 != '6' || c5 != '0') {
                return false;
            }
        } else if (c5 < '0' || c5 > '9') {
            return false;
        }
        return (c6 < '0' || c6 > '5') ? c6 == '6' && c7 == '0' : c7 >= '0' && c7 <= '9';
    }

    private void setCalendar(char c2, char c3, char c4, char c5, char c6, char c7, char c8, char c9) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar = calendar;
        calendar.set(1, ((c2 - '0') * 1000) + ((c3 - '0') * 100) + ((c4 - '0') * 10) + (c5 - '0'));
        this.calendar.set(2, (((c6 - '0') * 10) + (c7 - '0')) - 1);
        this.calendar.set(5, ((c8 - '0') * 10) + (c9 - '0'));
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int i2, int i3, int i4, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, i2, i3, i4);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void arrayCopy(int i2, char[] cArr, int i3, int i4) {
        this.text.getChars(i2, i4 + i2, cArr, i3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        if (this.token != 26) {
            return !this.hasSpecial ? IOUtils.decodeBase64(this.text, this.np + 1, this.sp) : IOUtils.decodeBase64(new String(this.sbuf, 0, this.sp));
        }
        int i2 = this.np + 1;
        int i3 = this.sp;
        if (i3 % 2 != 0) {
            throw new JSONException("illegal state. " + i3);
        }
        int i4 = i3 / 2;
        byte[] bArr = new byte[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = (i5 * 2) + i2;
            char cCharAt = this.text.charAt(i6);
            char cCharAt2 = this.text.charAt(i6 + 1);
            char c2 = '7';
            int i7 = cCharAt - (cCharAt <= '9' ? '0' : '7');
            if (cCharAt2 <= '9') {
                c2 = '0';
            }
            bArr[i5] = (byte) ((i7 << 4) | (cCharAt2 - c2));
        }
        return bArr;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int i2) {
        return i2 >= this.len ? JSONLexer.EOI : this.text.charAt(i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void copyTo(int i2, int i3, char[] cArr) {
        this.text.getChars(i2, i3 + i2, cArr, 0);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        char cCharAt = charAt((this.np + this.sp) - 1);
        int i2 = this.sp;
        if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B' || cCharAt == 'F' || cCharAt == 'D') {
            i2--;
        }
        if (i2 > 65535) {
            throw new JSONException("decimal overflow");
        }
        int i3 = this.np;
        char[] cArr = this.sbuf;
        if (i2 < cArr.length) {
            this.text.getChars(i3, i3 + i2, cArr, 0);
            return new BigDecimal(this.sbuf, 0, i2, MathContext.UNLIMITED);
        }
        char[] cArr2 = new char[i2];
        this.text.getChars(i3, i3 + i2, cArr2, 0);
        return new BigDecimal(cArr2, 0, i2, MathContext.UNLIMITED);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char c2, int i2) {
        return this.text.indexOf(c2, i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        StringBuilder sb = new StringBuilder();
        int i2 = 1;
        int i3 = 1;
        int i4 = 0;
        while (i4 < this.bp) {
            if (this.text.charAt(i4) == '\n') {
                i2++;
                i3 = 1;
            }
            i4++;
            i3++;
        }
        sb.append("pos ");
        sb.append(this.bp);
        sb.append(", line ");
        sb.append(i2);
        sb.append(", column ");
        sb.append(i3);
        if (this.text.length() < 65535) {
            sb.append(this.text);
        } else {
            sb.append(this.text.substring(0, 65535));
        }
        return sb.toString();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        int i2 = this.bp;
        int i3 = this.len;
        if (i2 != i3) {
            return this.ch == 26 && i2 + 1 >= i3;
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean matchField2(char[] cArr) {
        while (JSONLexerBase.isWhitespace(this.ch)) {
            next();
        }
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = this.bp + cArr.length;
        int i2 = length + 1;
        char cCharAt = this.text.charAt(length);
        while (JSONLexerBase.isWhitespace(cCharAt)) {
            cCharAt = this.text.charAt(i2);
            i2++;
        }
        if (cCharAt != ':') {
            this.matchStat = -2;
            return false;
        }
        this.bp = i2;
        this.ch = charAt(i2);
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int i2 = this.bp + 1;
        this.bp = i2;
        char cCharAt = i2 >= this.len ? JSONLexer.EOI : this.text.charAt(i2);
        this.ch = cCharAt;
        return cCharAt;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        char cCharAt = charAt((this.np + this.sp) - 1);
        int i2 = this.sp;
        if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B' || cCharAt == 'F' || cCharAt == 'D') {
            i2--;
        }
        return subString(this.np, i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanDate(char c2) {
        char cCharAt;
        long j2;
        Date date;
        char cCharAt2;
        boolean z2 = false;
        this.matchStat = 0;
        int i2 = this.bp;
        char c3 = this.ch;
        int i3 = i2 + 1;
        char cCharAt3 = charAt(i2);
        if (cCharAt3 == '\"') {
            int iIndexOf = indexOf(Typography.quote, i3);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            this.bp = i3;
            if (!scanISO8601DateIfMatch(false, iIndexOf - i3)) {
                this.bp = i2;
                this.ch = c3;
                this.matchStat = -1;
                return null;
            }
            date = this.calendar.getTime();
            cCharAt = charAt(iIndexOf + 1);
            this.bp = i2;
            while (cCharAt != ',' && cCharAt != ']') {
                if (!JSONLexerBase.isWhitespace(cCharAt)) {
                    this.bp = i2;
                    this.ch = c3;
                    this.matchStat = -1;
                    return null;
                }
                int i4 = iIndexOf + 1;
                char cCharAt4 = charAt(iIndexOf + 2);
                iIndexOf = i4;
                cCharAt = cCharAt4;
            }
            this.bp = iIndexOf + 1;
            this.ch = cCharAt;
        } else {
            char c4 = '9';
            char c5 = '0';
            if (cCharAt3 != '-' && (cCharAt3 < '0' || cCharAt3 > '9')) {
                if (cCharAt3 == 'n') {
                    int i5 = i2 + 2;
                    if (charAt(i3) == 'u') {
                        int i6 = i2 + 3;
                        if (charAt(i5) == 'l') {
                            int i7 = i2 + 4;
                            if (charAt(i6) == 'l') {
                                cCharAt = charAt(i7);
                                this.bp = i7;
                                date = null;
                            }
                        }
                    }
                }
                this.bp = i2;
                this.ch = c3;
                this.matchStat = -1;
                return null;
            }
            if (cCharAt3 == '-') {
                cCharAt3 = charAt(i3);
                i3 = i2 + 2;
                z2 = true;
            }
            if (cCharAt3 < '0' || cCharAt3 > '9') {
                cCharAt = cCharAt3;
                j2 = 0;
            } else {
                j2 = cCharAt3 - '0';
                while (true) {
                    int i8 = i3 + 1;
                    cCharAt2 = charAt(i3);
                    if (cCharAt2 < c5 || cCharAt2 > c4) {
                        break;
                    }
                    j2 = (j2 * 10) + (cCharAt2 - '0');
                    i3 = i8;
                    c4 = '9';
                    c5 = '0';
                }
                if (cCharAt2 == ',' || cCharAt2 == ']') {
                    this.bp = i3;
                }
                cCharAt = cCharAt2;
            }
            if (j2 < 0) {
                this.bp = i2;
                this.ch = c3;
                this.matchStat = -1;
                return null;
            }
            if (z2) {
                j2 = -j2;
            }
            date = new Date(j2);
        }
        if (cCharAt == ',') {
            int i9 = this.bp + 1;
            this.bp = i9;
            this.ch = charAt(i9);
            this.matchStat = 3;
            return date;
        }
        int i10 = this.bp + 1;
        this.bp = i10;
        char cCharAt5 = charAt(i10);
        if (cCharAt5 == ',') {
            this.token = 16;
            int i11 = this.bp + 1;
            this.bp = i11;
            this.ch = charAt(i11);
        } else if (cCharAt5 == ']') {
            this.token = 15;
            int i12 = this.bp + 1;
            this.bp = i12;
            this.ch = charAt(i12);
        } else if (cCharAt5 == '}') {
            this.token = 13;
            int i13 = this.bp + 1;
            this.bp = i13;
            this.ch = charAt(i13);
        } else {
            if (cCharAt5 != 26) {
                this.bp = i2;
                this.ch = c3;
                this.matchStat = -1;
                return null;
            }
            this.ch = JSONLexer.EOI;
            this.token = 20;
        }
        this.matchStat = 4;
        return date;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public double scanDouble(char c2) throws NumberFormatException {
        int i2;
        char cCharAt;
        long j2;
        int i3;
        int i4;
        double d2;
        char cCharAt2;
        this.matchStat = 0;
        int i5 = this.bp;
        int i6 = i5 + 1;
        char cCharAt3 = charAt(i5);
        boolean z2 = cCharAt3 == '\"';
        if (z2) {
            cCharAt3 = charAt(i6);
            i6 = i5 + 2;
        }
        boolean z3 = cCharAt3 == '-';
        if (z3) {
            cCharAt3 = charAt(i6);
            i6++;
        }
        if (cCharAt3 >= '0') {
            char c3 = '9';
            if (cCharAt3 <= '9') {
                long j3 = cCharAt3 - '0';
                while (true) {
                    i2 = i6 + 1;
                    cCharAt = charAt(i6);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    j3 = (j3 * 10) + (cCharAt - '0');
                    i6 = i2;
                }
                if (cCharAt == '.') {
                    int i7 = i6 + 2;
                    char cCharAt4 = charAt(i2);
                    if (cCharAt4 < '0' || cCharAt4 > '9') {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    j3 = (j3 * 10) + (cCharAt4 - '0');
                    long j4 = 10;
                    while (true) {
                        i2 = i7 + 1;
                        cCharAt2 = charAt(i7);
                        if (cCharAt2 < '0' || cCharAt2 > c3) {
                            break;
                        }
                        j3 = (j3 * 10) + (cCharAt2 - '0');
                        j4 *= 10;
                        i7 = i2;
                        c3 = '9';
                    }
                    long j5 = j4;
                    cCharAt = cCharAt2;
                    j2 = j5;
                } else {
                    j2 = 1;
                }
                boolean z4 = cCharAt == 'e' || cCharAt == 'E';
                if (z4) {
                    int i8 = i2 + 1;
                    char cCharAt5 = charAt(i2);
                    if (cCharAt5 == '+' || cCharAt5 == '-') {
                        i2 += 2;
                        cCharAt = charAt(i8);
                    } else {
                        i2 = i8;
                        cCharAt = cCharAt5;
                    }
                    while (cCharAt >= '0' && cCharAt <= '9') {
                        int i9 = i2 + 1;
                        char cCharAt6 = charAt(i2);
                        i2 = i9;
                        cCharAt = cCharAt6;
                    }
                }
                if (!z2) {
                    i3 = this.bp;
                    i4 = (i2 - i3) - 1;
                } else {
                    if (cCharAt != '\"') {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    int i10 = i2 + 1;
                    char cCharAt7 = charAt(i2);
                    i3 = this.bp + 1;
                    i4 = (i10 - i3) - 2;
                    i2 = i10;
                    cCharAt = cCharAt7;
                }
                if (z4 || i4 >= 18) {
                    d2 = Double.parseDouble(subString(i3, i4));
                } else {
                    d2 = j3 / j2;
                    if (z3) {
                        d2 = -d2;
                    }
                }
                if (cCharAt != c2) {
                    this.matchStat = -1;
                    return d2;
                }
                this.bp = i2;
                this.ch = charAt(i2);
                this.matchStat = 3;
                this.token = 16;
                return d2;
            }
        }
        if (cCharAt3 == 'n') {
            int i11 = i6 + 1;
            if (charAt(i6) == 'u') {
                int i12 = i6 + 2;
                if (charAt(i11) == 'l') {
                    int i13 = i6 + 3;
                    if (charAt(i12) == 'l') {
                        this.matchStat = 5;
                        int i14 = i6 + 4;
                        char cCharAt8 = charAt(i13);
                        if (z2 && cCharAt8 == '\"') {
                            cCharAt8 = charAt(i14);
                            i14 = i6 + 5;
                        }
                        while (cCharAt8 != ',') {
                            if (cCharAt8 == ']') {
                                this.bp = i14;
                                this.ch = charAt(i14);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0.0d;
                            }
                            if (!JSONLexerBase.isWhitespace(cCharAt8)) {
                                this.matchStat = -1;
                                return 0.0d;
                            }
                            char cCharAt9 = charAt(i14);
                            i14++;
                            cCharAt8 = cCharAt9;
                        }
                        this.bp = i14;
                        this.ch = charAt(i14);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0.0d;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0.0d;
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x00f3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0104  */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean scanFieldBoolean(char[] r12) {
        /*
            Method dump skipped, instructions count: 385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldBoolean(char[]):boolean");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanFieldDate(char[] cArr) {
        long j2;
        Date date;
        char cCharAt;
        char cCharAt2;
        boolean z2 = false;
        this.matchStat = 0;
        int i2 = this.bp;
        char c2 = this.ch;
        if (!charArrayCompare(this.text, i2, cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = this.bp + cArr.length;
        int i3 = length + 1;
        char cCharAt3 = charAt(length);
        if (cCharAt3 == '\"') {
            int iIndexOf = indexOf(Typography.quote, i3);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            this.bp = i3;
            if (!scanISO8601DateIfMatch(false, iIndexOf - i3)) {
                this.bp = i2;
                this.matchStat = -1;
                return null;
            }
            date = this.calendar.getTime();
            cCharAt = charAt(iIndexOf + 1);
            this.bp = i2;
            while (cCharAt != ',' && cCharAt != '}') {
                if (!JSONLexerBase.isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return null;
                }
                int i4 = iIndexOf + 1;
                char cCharAt4 = charAt(iIndexOf + 2);
                iIndexOf = i4;
                cCharAt = cCharAt4;
            }
            this.bp = iIndexOf + 1;
            this.ch = cCharAt;
        } else {
            char c3 = '9';
            char c4 = '0';
            if (cCharAt3 != '-' && (cCharAt3 < '0' || cCharAt3 > '9')) {
                this.matchStat = -1;
                return null;
            }
            if (cCharAt3 == '-') {
                cCharAt3 = charAt(i3);
                i3 = length + 2;
                z2 = true;
            }
            if (cCharAt3 < '0' || cCharAt3 > '9') {
                j2 = 0;
            } else {
                long j3 = cCharAt3 - '0';
                while (true) {
                    int i5 = i3 + 1;
                    cCharAt2 = charAt(i3);
                    if (cCharAt2 < c4 || cCharAt2 > c3) {
                        break;
                    }
                    j3 = (j3 * 10) + (cCharAt2 - '0');
                    i3 = i5;
                    c3 = '9';
                    c4 = '0';
                }
                if (cCharAt2 == ',' || cCharAt2 == '}') {
                    this.bp = i3;
                }
                long j4 = j3;
                cCharAt3 = cCharAt2;
                j2 = j4;
            }
            if (j2 < 0) {
                this.matchStat = -1;
                return null;
            }
            if (z2) {
                j2 = -j2;
            }
            date = new Date(j2);
            cCharAt = cCharAt3;
        }
        if (cCharAt == ',') {
            int i6 = this.bp + 1;
            this.bp = i6;
            this.ch = charAt(i6);
            this.matchStat = 3;
            this.token = 16;
            return date;
        }
        int i7 = this.bp + 1;
        this.bp = i7;
        char cCharAt5 = charAt(i7);
        if (cCharAt5 == ',') {
            this.token = 16;
            int i8 = this.bp + 1;
            this.bp = i8;
            this.ch = charAt(i8);
        } else if (cCharAt5 == ']') {
            this.token = 15;
            int i9 = this.bp + 1;
            this.bp = i9;
            this.ch = charAt(i9);
        } else if (cCharAt5 == '}') {
            this.token = 13;
            int i10 = this.bp + 1;
            this.bp = i10;
            this.ch = charAt(i10);
        } else {
            if (cCharAt5 != 26) {
                this.bp = i2;
                this.ch = c2;
                this.matchStat = -1;
                return null;
            }
            this.token = 20;
        }
        this.matchStat = 4;
        return date;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0063, code lost:
    
        if (r12 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0065, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0067, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0068, code lost:
    
        if (r4 >= 0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006a, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x006c, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x006d, code lost:
    
        if (r7 == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x006f, code lost:
    
        if (r12 == '\"') goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0071, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0073, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0074, code lost:
    
        r15 = r15 + 2;
        r12 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x007a, code lost:
    
        r11 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x007f, code lost:
    
        if (r12 == ',') goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0081, code lost:
    
        if (r12 != '}') goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0088, code lost:
    
        if (com.alibaba.fastjson.parser.JSONLexerBase.isWhitespace(r12) == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x008a, code lost:
    
        r15 = r11 + 1;
        r12 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0091, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0093, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0094, code lost:
    
        r14.bp = r11 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x009a, code lost:
    
        if (r12 != ',') goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009c, code lost:
    
        r14.bp = r11;
        r14.ch = charAt(r11);
        r14.matchStat = 3;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00a9, code lost:
    
        if (r3 == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ac, code lost:
    
        return -r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00ad, code lost:
    
        if (r12 != '}') goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00af, code lost:
    
        r14.bp = r11;
        r8 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b5, code lost:
    
        if (r8 != ',') goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00b7, code lost:
    
        r14.token = 16;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c7, code lost:
    
        if (r8 != ']') goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c9, code lost:
    
        r14.token = 15;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00d9, code lost:
    
        if (r8 != '}') goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00db, code lost:
    
        r14.token = 13;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00ed, code lost:
    
        if (r8 != 26) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00ef, code lost:
    
        r14.token = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00f3, code lost:
    
        r14.matchStat = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00fb, code lost:
    
        if (com.alibaba.fastjson.parser.JSONLexerBase.isWhitespace(r8) == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00fd, code lost:
    
        r8 = r14.bp + 1;
        r14.bp = r8;
        r8 = charAt(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0107, code lost:
    
        r14.bp = r1;
        r14.ch = r2;
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x010d, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x010e, code lost:
    
        if (r3 == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0111, code lost:
    
        return -r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x007a, code lost:
    
        r11 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:?, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:?, code lost:
    
        return r4;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int scanFieldInt(char[] r15) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldInt(char[]):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldLong(char[] cArr) {
        int i2;
        char cCharAt;
        boolean z2 = false;
        this.matchStat = 0;
        int i3 = this.bp;
        char c2 = this.ch;
        if (!charArrayCompare(this.text, i3, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i4 = length + 1;
        char cCharAt2 = charAt(length);
        boolean z3 = cCharAt2 == '\"';
        if (z3) {
            cCharAt2 = charAt(i4);
            i4 = length + 2;
        }
        if (cCharAt2 == '-') {
            cCharAt2 = charAt(i4);
            i4++;
            z2 = true;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            this.bp = i3;
            this.ch = c2;
            this.matchStat = -1;
            return 0L;
        }
        long j2 = cCharAt2 - '0';
        while (true) {
            i2 = i4 + 1;
            cCharAt = charAt(i4);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j2 = (j2 * 10) + (cCharAt - '0');
            i4 = i2;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return 0L;
        }
        if (z3) {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return 0L;
            }
            cCharAt = charAt(i2);
            i2 = i4 + 2;
        }
        if (cCharAt == ',' || cCharAt == '}') {
            this.bp = i2 - 1;
        }
        if (j2 < 0 && (j2 != Long.MIN_VALUE || !z2)) {
            this.bp = i3;
            this.ch = c2;
            this.matchStat = -1;
            return 0L;
        }
        while (cCharAt != ',') {
            if (cCharAt == '}') {
                int i5 = 1;
                int i6 = this.bp + 1;
                this.bp = i6;
                char cCharAt3 = charAt(i6);
                while (true) {
                    if (cCharAt3 == ',') {
                        this.token = 16;
                        int i7 = this.bp + i5;
                        this.bp = i7;
                        this.ch = charAt(i7);
                        break;
                    }
                    if (cCharAt3 == ']') {
                        this.token = 15;
                        int i8 = this.bp + i5;
                        this.bp = i8;
                        this.ch = charAt(i8);
                        break;
                    }
                    if (cCharAt3 == '}') {
                        this.token = 13;
                        int i9 = this.bp + i5;
                        this.bp = i9;
                        this.ch = charAt(i9);
                        break;
                    }
                    if (cCharAt3 == 26) {
                        this.token = 20;
                        break;
                    }
                    if (!JSONLexerBase.isWhitespace(cCharAt3)) {
                        this.bp = i3;
                        this.ch = c2;
                        this.matchStat = -1;
                        return 0L;
                    }
                    int i10 = this.bp + 1;
                    this.bp = i10;
                    cCharAt3 = charAt(i10);
                    i5 = 1;
                }
                this.matchStat = 4;
                return z2 ? -j2 : j2;
            }
            if (!JSONLexerBase.isWhitespace(cCharAt)) {
                this.matchStat = -1;
                return 0L;
            }
            this.bp = i2;
            cCharAt = charAt(i2);
            i2++;
        }
        int i11 = this.bp + 1;
        this.bp = i11;
        this.ch = charAt(i11);
        this.matchStat = 3;
        this.token = 16;
        return z2 ? -j2 : j2;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldString(char[] cArr) {
        this.matchStat = 0;
        int i2 = this.bp;
        char c2 = this.ch;
        while (!charArrayCompare(this.text, this.bp, cArr)) {
            if (!JSONLexerBase.isWhitespace(this.ch)) {
                this.matchStat = -2;
                return stringDefaultValue();
            }
            next();
            while (JSONLexerBase.isWhitespace(this.ch)) {
                next();
            }
        }
        int length = this.bp + cArr.length;
        int i3 = length + 1;
        char cCharAt = charAt(length);
        int i4 = 0;
        if (cCharAt != '\"') {
            while (JSONLexerBase.isWhitespace(cCharAt)) {
                i4++;
                int i5 = i3 + 1;
                char cCharAt2 = charAt(i3);
                i3 = i5;
                cCharAt = cCharAt2;
            }
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        int iIndexOf = indexOf(Typography.quote, i3);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        String strSubString = subString(i3, iIndexOf - i3);
        if (strSubString.indexOf(92) != -1) {
            while (true) {
                int i6 = 0;
                for (int i7 = iIndexOf - 1; i7 >= 0 && charAt(i7) == '\\'; i7--) {
                    i6++;
                }
                if (i6 % 2 == 0) {
                    break;
                }
                iIndexOf = indexOf(Typography.quote, iIndexOf + 1);
            }
            int i8 = this.bp;
            int length2 = iIndexOf - (((cArr.length + i8) + 1) + i4);
            strSubString = JSONLexerBase.readString(sub_chars(i8 + cArr.length + 1 + i4, length2), length2);
        }
        if ((this.features & Feature.TrimStringFieldValue.mask) != 0) {
            strSubString = strSubString.trim();
        }
        char cCharAt3 = charAt(iIndexOf + 1);
        while (cCharAt3 != ',' && cCharAt3 != '}') {
            if (!JSONLexerBase.isWhitespace(cCharAt3)) {
                this.matchStat = -1;
                return stringDefaultValue();
            }
            char cCharAt4 = charAt(iIndexOf + 2);
            iIndexOf++;
            cCharAt3 = cCharAt4;
        }
        this.bp = iIndexOf + 1;
        this.ch = cCharAt3;
        if (cCharAt3 == ',') {
            int i9 = iIndexOf + 2;
            this.bp = i9;
            this.ch = charAt(i9);
            this.matchStat = 3;
            return strSubString;
        }
        int i10 = iIndexOf + 2;
        this.bp = i10;
        char cCharAt5 = charAt(i10);
        if (cCharAt5 == ',') {
            this.token = 16;
            int i11 = this.bp + 1;
            this.bp = i11;
            this.ch = charAt(i11);
        } else if (cCharAt5 == ']') {
            this.token = 15;
            int i12 = this.bp + 1;
            this.bp = i12;
            this.ch = charAt(i12);
        } else if (cCharAt5 == '}') {
            this.token = 13;
            int i13 = this.bp + 1;
            this.bp = i13;
            this.ch = charAt(i13);
        } else {
            if (cCharAt5 != 26) {
                this.bp = i2;
                this.ch = c2;
                this.matchStat = -1;
                return stringDefaultValue();
            }
            this.token = 20;
        }
        this.matchStat = 4;
        return strSubString;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d9, code lost:
    
        if (r9 != ']') goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00df, code lost:
    
        if (r3.size() != 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e1, code lost:
    
        r2 = charAt(r1);
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ed, code lost:
    
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ef, code lost:
    
        return null;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r18, java.lang.Class<?> r19) {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldSymbol(char[] cArr) {
        this.matchStat = 0;
        while (!charArrayCompare(this.text, this.bp, cArr)) {
            if (!JSONLexerBase.isWhitespace(this.ch)) {
                this.matchStat = -2;
                return 0L;
            }
            next();
            while (JSONLexerBase.isWhitespace(this.ch)) {
                next();
            }
        }
        int length = this.bp + cArr.length;
        int i2 = length + 1;
        char cCharAt = charAt(length);
        if (cCharAt != '\"') {
            while (JSONLexerBase.isWhitespace(cCharAt)) {
                cCharAt = charAt(i2);
                i2++;
            }
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return 0L;
            }
        }
        long j2 = TypeUtils.fnv1a_64_magic_hashcode;
        while (true) {
            int i3 = i2 + 1;
            char cCharAt2 = charAt(i2);
            if (cCharAt2 == '\"') {
                this.bp = i3;
                char cCharAt3 = charAt(i3);
                this.ch = cCharAt3;
                while (cCharAt3 != ',') {
                    if (cCharAt3 == '}') {
                        next();
                        skipWhitespace();
                        char current = getCurrent();
                        if (current == ',') {
                            this.token = 16;
                            int i4 = this.bp + 1;
                            this.bp = i4;
                            this.ch = charAt(i4);
                        } else if (current == ']') {
                            this.token = 15;
                            int i5 = this.bp + 1;
                            this.bp = i5;
                            this.ch = charAt(i5);
                        } else if (current == '}') {
                            this.token = 13;
                            int i6 = this.bp + 1;
                            this.bp = i6;
                            this.ch = charAt(i6);
                        } else {
                            if (current != 26) {
                                this.matchStat = -1;
                                return 0L;
                            }
                            this.token = 20;
                        }
                        this.matchStat = 4;
                        return j2;
                    }
                    if (!JSONLexerBase.isWhitespace(cCharAt3)) {
                        this.matchStat = -1;
                        return 0L;
                    }
                    int i7 = this.bp + 1;
                    this.bp = i7;
                    cCharAt3 = charAt(i7);
                }
                int i8 = this.bp + 1;
                this.bp = i8;
                this.ch = charAt(i8);
                this.matchStat = 3;
                return j2;
            }
            if (i3 > this.len) {
                this.matchStat = -1;
                return 0L;
            }
            j2 = (j2 ^ cCharAt2) * TypeUtils.fnv1a_64_magic_prime;
            i2 = i3;
        }
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x007e, code lost:
    
        if (r13 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0080, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0082, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0083, code lost:
    
        if (r7 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0085, code lost:
    
        if (r13 == '\"') goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0087, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0089, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x008a, code lost:
    
        r13 = charAt(r12);
        r12 = r3 + 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0091, code lost:
    
        if (r4 >= 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0093, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0095, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0098, code lost:
    
        if (r13 != r17) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009a, code lost:
    
        r16.bp = r12;
        r16.ch = charAt(r12);
        r16.matchStat = 3;
        r16.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a7, code lost:
    
        if (r6 == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00aa, code lost:
    
        return -r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00af, code lost:
    
        if (com.alibaba.fastjson.parser.JSONLexerBase.isWhitespace(r13) == false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b1, code lost:
    
        r13 = charAt(r12);
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b9, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00bb, code lost:
    
        if (r6 == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00be, code lost:
    
        return -r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:?, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:?, code lost:
    
        return r4;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int scanInt(char r17) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanInt(char):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char c2) {
        int i2;
        char cCharAt;
        this.matchStat = 0;
        int i3 = this.bp;
        int i4 = i3 + 1;
        char cCharAt2 = charAt(i3);
        boolean z2 = cCharAt2 == '\"';
        if (z2) {
            cCharAt2 = charAt(i4);
            i4 = i3 + 2;
        }
        boolean z3 = cCharAt2 == '-';
        if (z3) {
            cCharAt2 = charAt(i4);
            i4++;
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            long j2 = cCharAt2 - '0';
            while (true) {
                i2 = i4 + 1;
                cCharAt = charAt(i4);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                j2 = (j2 * 10) + (cCharAt - '0');
                i4 = i2;
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (z2) {
                if (cCharAt != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                cCharAt = charAt(i2);
                i2 = i4 + 2;
            }
            if (j2 < 0 && (j2 != Long.MIN_VALUE || !z3)) {
                this.matchStat = -1;
                return 0L;
            }
            while (cCharAt != c2) {
                if (!JSONLexerBase.isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return j2;
                }
                cCharAt = charAt(i2);
                i2++;
            }
            this.bp = i2;
            this.ch = charAt(i2);
            this.matchStat = 3;
            this.token = 16;
            return z3 ? -j2 : j2;
        }
        if (cCharAt2 == 'n') {
            int i5 = i4 + 1;
            if (charAt(i4) == 'u') {
                int i6 = i4 + 2;
                if (charAt(i5) == 'l') {
                    int i7 = i4 + 3;
                    if (charAt(i6) == 'l') {
                        this.matchStat = 5;
                        int i8 = i4 + 4;
                        char cCharAt3 = charAt(i7);
                        if (z2 && cCharAt3 == '\"') {
                            cCharAt3 = charAt(i8);
                            i8 = i4 + 5;
                        }
                        while (cCharAt3 != ',') {
                            if (cCharAt3 == ']') {
                                this.bp = i8;
                                this.ch = charAt(i8);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0L;
                            }
                            if (!JSONLexerBase.isWhitespace(cCharAt3)) {
                                this.matchStat = -1;
                                return 0L;
                            }
                            char cCharAt4 = charAt(i8);
                            i8++;
                            cCharAt3 = cCharAt4;
                        }
                        this.bp = i8;
                        this.ch = charAt(i8);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0L;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String scanTypeName(SymbolTable symbolTable) {
        int iIndexOf;
        if (!this.text.startsWith("\"@type\":\"", this.bp) || (iIndexOf = this.text.indexOf(34, this.bp + 9)) == -1) {
            return null;
        }
        int i2 = this.bp + 9;
        this.bp = i2;
        int iCharAt = 0;
        while (i2 < iIndexOf) {
            iCharAt = (iCharAt * 31) + this.text.charAt(i2);
            i2++;
        }
        int i3 = this.bp;
        String strAddSymbol = addSymbol(i3, iIndexOf - i3, iCharAt, symbolTable);
        char cCharAt = this.text.charAt(iIndexOf + 1);
        if (cCharAt != ',' && cCharAt != ']') {
            return null;
        }
        int i4 = iIndexOf + 2;
        this.bp = i4;
        this.ch = this.text.charAt(i4);
        return strAddSymbol;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean seekArrayToItem(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("index must > 0, but " + i2);
        }
        int i3 = this.token;
        if (i3 == 20) {
            return false;
        }
        if (i3 != 14) {
            throw new UnsupportedOperationException();
        }
        for (int i4 = 0; i4 < i2; i4++) {
            skipWhitespace();
            char c2 = this.ch;
            if (c2 == '\"' || c2 == '\'') {
                skipString();
                char c3 = this.ch;
                if (c3 != ',') {
                    if (c3 != ']') {
                        throw new JSONException("illegal json.");
                    }
                    next();
                    nextToken(16);
                    return false;
                }
                next();
            } else {
                if (c2 == '{') {
                    next();
                    this.token = 12;
                    skipObject(false);
                } else {
                    if (c2 != '[') {
                        for (int i5 = this.bp + 1; i5 < this.text.length(); i5++) {
                            char cCharAt = this.text.charAt(i5);
                            if (cCharAt == ',') {
                                int i6 = i5 + 1;
                                this.bp = i6;
                                this.ch = charAt(i6);
                            } else {
                                if (cCharAt == ']') {
                                    int i7 = i5 + 1;
                                    this.bp = i7;
                                    this.ch = charAt(i7);
                                    nextToken();
                                    return false;
                                }
                            }
                        }
                        throw new JSONException("illegal json.");
                    }
                    next();
                    this.token = 14;
                    skipArray(false);
                }
                int i8 = this.token;
                if (i8 != 16) {
                    if (i8 == 15) {
                        return false;
                    }
                    throw new UnsupportedOperationException();
                }
            }
        }
        nextToken();
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int seekObjectToField(long j2, boolean z2) {
        char c2;
        int i2 = this.token;
        int i3 = -1;
        if (i2 == 20) {
            return -1;
        }
        if (i2 != 13) {
            int i4 = 15;
            if (i2 != 15) {
                int i5 = 16;
                if (i2 != 12 && i2 != 16) {
                    throw new UnsupportedOperationException(JSONToken.name(this.token));
                }
                while (true) {
                    char c3 = this.ch;
                    if (c3 == '}') {
                        next();
                        nextToken();
                        return i3;
                    }
                    if (c3 == 26) {
                        return i3;
                    }
                    if (c3 != '\"') {
                        skipWhitespace();
                    }
                    if (this.ch != '\"') {
                        throw new UnsupportedOperationException();
                    }
                    int i6 = this.bp + 1;
                    long j3 = TypeUtils.fnv1a_64_magic_hashcode;
                    while (true) {
                        if (i6 >= this.text.length()) {
                            break;
                        }
                        char cCharAt = this.text.charAt(i6);
                        if (cCharAt == '\\') {
                            i6++;
                            if (i6 == this.text.length()) {
                                throw new JSONException("unclosed str, " + info());
                            }
                            cCharAt = this.text.charAt(i6);
                        }
                        if (cCharAt == '\"') {
                            int i7 = i6 + 1;
                            this.bp = i7;
                            this.ch = i7 >= this.text.length() ? (char) 26 : this.text.charAt(this.bp);
                        } else {
                            j3 = (j3 ^ cCharAt) * TypeUtils.fnv1a_64_magic_prime;
                            i6++;
                        }
                    }
                    if (j3 == j2) {
                        if (this.ch != ':') {
                            skipWhitespace();
                        }
                        if (this.ch != ':') {
                            return 3;
                        }
                        int i8 = this.bp + 1;
                        this.bp = i8;
                        char cCharAt2 = i8 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i8);
                        this.ch = cCharAt2;
                        if (cCharAt2 == ',') {
                            int i9 = this.bp + 1;
                            this.bp = i9;
                            this.ch = i9 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i9);
                            this.token = i5;
                            return 3;
                        }
                        if (cCharAt2 == ']') {
                            int i10 = this.bp + 1;
                            this.bp = i10;
                            this.ch = i10 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i10);
                            this.token = i4;
                            return 3;
                        }
                        if (cCharAt2 == '}') {
                            int i11 = this.bp + 1;
                            this.bp = i11;
                            this.ch = i11 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i11);
                            this.token = 13;
                            return 3;
                        }
                        if (cCharAt2 < '0' || cCharAt2 > '9') {
                            nextToken(2);
                            return 3;
                        }
                        this.sp = 0;
                        this.pos = this.bp;
                        scanNumber();
                        return 3;
                    }
                    if (this.ch != ':') {
                        skipWhitespace();
                    }
                    if (this.ch != ':') {
                        throw new JSONException("illegal json, " + info());
                    }
                    int i12 = this.bp + 1;
                    this.bp = i12;
                    char cCharAt3 = i12 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i12);
                    this.ch = cCharAt3;
                    if (cCharAt3 != '\"' && cCharAt3 != '\'' && cCharAt3 != '{' && cCharAt3 != '[' && cCharAt3 != '0' && cCharAt3 != '1' && cCharAt3 != '2' && cCharAt3 != '3' && cCharAt3 != '4' && cCharAt3 != '5' && cCharAt3 != '6' && cCharAt3 != '7' && cCharAt3 != '8' && cCharAt3 != '9' && cCharAt3 != '+' && cCharAt3 != '-') {
                        skipWhitespace();
                    }
                    char c4 = this.ch;
                    if (c4 == '-' || c4 == '+' || (c4 >= '0' && c4 <= '9')) {
                        next();
                        while (true) {
                            c2 = this.ch;
                            if (c2 < '0' || c2 > '9') {
                                break;
                            }
                            next();
                        }
                        if (c2 == '.') {
                            next();
                            while (true) {
                                char c5 = this.ch;
                                if (c5 < '0' || c5 > '9') {
                                    break;
                                }
                                next();
                            }
                        }
                        char c6 = this.ch;
                        if (c6 == 'E' || c6 == 'e') {
                            next();
                            char c7 = this.ch;
                            if (c7 == '-' || c7 == '+') {
                                next();
                            }
                            while (true) {
                                char c8 = this.ch;
                                if (c8 < '0' || c8 > '9') {
                                    break;
                                }
                                next();
                            }
                        }
                        if (this.ch != ',') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c4 == '\"') {
                        skipString();
                        char c9 = this.ch;
                        if (c9 != ',' && c9 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c4 == 't') {
                        next();
                        if (this.ch == 'r') {
                            next();
                            if (this.ch == 'u') {
                                next();
                                if (this.ch == 'e') {
                                    next();
                                }
                            }
                        }
                        char c10 = this.ch;
                        if (c10 != ',' && c10 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c4 == 'n') {
                        next();
                        if (this.ch == 'u') {
                            next();
                            if (this.ch == 'l') {
                                next();
                                if (this.ch == 'l') {
                                    next();
                                }
                            }
                        }
                        char c11 = this.ch;
                        if (c11 != ',' && c11 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c4 == 'f') {
                        next();
                        if (this.ch == 'a') {
                            next();
                            if (this.ch == 'l') {
                                next();
                                if (this.ch == 's') {
                                    next();
                                    if (this.ch == 'e') {
                                        next();
                                    }
                                }
                            }
                        }
                        char c12 = this.ch;
                        if (c12 != ',' && c12 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c4 == '{') {
                        int i13 = this.bp + 1;
                        this.bp = i13;
                        this.ch = i13 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i13);
                        if (z2) {
                            this.token = 12;
                            return 1;
                        }
                        skipObject(false);
                        if (this.token == 13) {
                            return -1;
                        }
                    } else {
                        if (c4 != '[') {
                            throw new UnsupportedOperationException();
                        }
                        next();
                        if (z2) {
                            this.token = 14;
                            return 2;
                        }
                        skipArray(false);
                        if (this.token == 13) {
                            return -1;
                        }
                    }
                    i3 = -1;
                    i4 = 15;
                    i5 = 16;
                }
            }
        }
        nextToken();
        return -1;
    }

    protected void setTime(char c2, char c3, char c4, char c5, char c6, char c7) {
        this.calendar.set(11, ((c2 - '0') * 10) + (c3 - '0'));
        this.calendar.set(12, ((c4 - '0') * 10) + (c5 - '0'));
        this.calendar.set(13, ((c6 - '0') * 10) + (c7 - '0'));
    }

    protected void setTimeZone(char c2, char c3, char c4) {
        setTimeZone(c2, c3, c4, '0', '0');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipArray() {
        skipArray(false);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject() {
        skipObject(false);
    }

    public final void skipString() {
        if (this.ch != '\"') {
            throw new UnsupportedOperationException();
        }
        int i2 = this.bp;
        while (true) {
            i2++;
            if (i2 >= this.text.length()) {
                throw new JSONException("unclosed str");
            }
            char cCharAt = this.text.charAt(i2);
            if (cCharAt == '\\') {
                if (i2 < this.len - 1) {
                    i2++;
                }
            } else if (cCharAt == '\"') {
                String str = this.text;
                int i3 = i2 + 1;
                this.bp = i3;
                this.ch = str.charAt(i3);
                return;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        return !this.hasSpecial ? subString(this.np + 1, this.sp) : new String(this.sbuf, 0, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int i2, int i3) {
        if (!ASMUtils.IS_ANDROID) {
            return this.text.substring(i2, i3 + i2);
        }
        char[] cArr = this.sbuf;
        if (i3 < cArr.length) {
            this.text.getChars(i2, i2 + i3, cArr, 0);
            return new String(this.sbuf, 0, i3);
        }
        char[] cArr2 = new char[i3];
        this.text.getChars(i2, i3 + i2, cArr2, 0);
        return new String(cArr2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char[] sub_chars(int i2, int i3) {
        if (ASMUtils.IS_ANDROID) {
            char[] cArr = this.sbuf;
            if (i3 < cArr.length) {
                this.text.getChars(i2, i3 + i2, cArr, 0);
                return this.sbuf;
            }
        }
        char[] cArr2 = new char[i3];
        this.text.getChars(i2, i3 + i2, cArr2, 0);
        return cArr2;
    }

    public JSONScanner(String str, int i2) {
        super(i2);
        this.text = str;
        this.len = str.length();
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    public boolean scanISO8601DateIfMatch(boolean z2) {
        return scanISO8601DateIfMatch(z2, this.len - this.bp);
    }

    protected void setTimeZone(char c2, char c3, char c4, char c5, char c6) {
        int i2 = ((((c3 - '0') * 10) + (c4 - '0')) * 3600000) + ((((c5 - '0') * 10) + (c6 - '0')) * 60000);
        if (c2 == '-') {
            i2 = -i2;
        }
        if (this.calendar.getTimeZone().getRawOffset() != i2) {
            this.calendar.setTimeZone(new SimpleTimeZone(i2, Integer.toString(i2)));
        }
    }

    public final void skipArray(boolean z2) {
        int i2 = this.bp;
        boolean z3 = false;
        int i3 = 0;
        while (i2 < this.text.length()) {
            char cCharAt = this.text.charAt(i2);
            if (cCharAt == '\\') {
                if (i2 >= this.len - 1) {
                    this.ch = cCharAt;
                    this.bp = i2;
                    throw new JSONException("illegal str, " + info());
                }
                i2++;
            } else if (cCharAt == '\"') {
                z3 = !z3;
            } else if (cCharAt != '[') {
                char cCharAt2 = JSONLexer.EOI;
                if (cCharAt == '{' && z2) {
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    if (i4 < this.text.length()) {
                        cCharAt2 = this.text.charAt(i4);
                    }
                    this.ch = cCharAt2;
                    skipObject(z2);
                } else if (cCharAt == ']' && !z3 && i3 - 1 == -1) {
                    int i5 = i2 + 1;
                    this.bp = i5;
                    if (i5 == this.text.length()) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    } else {
                        this.ch = this.text.charAt(this.bp);
                        nextToken(16);
                        return;
                    }
                }
            } else if (!z3) {
                i3++;
            }
            i2++;
        }
        if (i2 != this.text.length()) {
            return;
        }
        throw new JSONException("illegal str, " + info());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject(boolean z2) {
        int i2 = this.bp;
        boolean z3 = false;
        int i3 = 0;
        while (i2 < this.text.length()) {
            char cCharAt = this.text.charAt(i2);
            if (cCharAt == '\\') {
                if (i2 >= this.len - 1) {
                    this.ch = cCharAt;
                    this.bp = i2;
                    throw new JSONException("illegal str, " + info());
                }
                i2++;
            } else if (cCharAt == '\"') {
                z3 = !z3;
            } else if (cCharAt != '{') {
                if (cCharAt == '}' && !z3 && i3 - 1 == -1) {
                    int i4 = i2 + 1;
                    this.bp = i4;
                    int length = this.text.length();
                    char cCharAt2 = JSONLexer.EOI;
                    if (i4 == length) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    }
                    char cCharAt3 = this.text.charAt(this.bp);
                    this.ch = cCharAt3;
                    if (cCharAt3 == ',') {
                        this.token = 16;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        if (i5 < this.text.length()) {
                            cCharAt2 = this.text.charAt(i5);
                        }
                        this.ch = cCharAt2;
                        return;
                    }
                    if (cCharAt3 == '}') {
                        this.token = 13;
                        next();
                        return;
                    } else if (cCharAt3 != ']') {
                        nextToken(16);
                        return;
                    } else {
                        this.token = 15;
                        next();
                        return;
                    }
                }
            } else if (!z3) {
                i3++;
            }
            i2++;
        }
        for (int i6 = 0; i6 < this.bp; i6++) {
            if (i6 < this.text.length() && this.text.charAt(i6) == ' ') {
                i2++;
            }
        }
        if (i2 != this.text.length()) {
            return;
        }
        throw new JSONException("illegal str, " + info());
    }

    private boolean scanISO8601DateIfMatch(boolean z2, int i2) throws NumberFormatException {
        int i3;
        boolean z3;
        char c2;
        char cCharAt;
        char c3;
        char c4;
        char c5;
        int i4;
        int i5;
        int i6;
        int i7;
        char c6;
        char c7;
        char cCharAt2;
        char c8;
        char c9;
        char c10;
        int i8;
        char c11;
        char c12;
        char c13;
        char c14;
        int i9;
        char c15;
        char c16;
        char c17;
        int i10;
        int i11;
        char cCharAt3;
        char cCharAt4;
        char cCharAt5;
        char cCharAt6;
        char cCharAt7;
        char cCharAt8;
        if (i2 < 8) {
            return false;
        }
        char cCharAt9 = charAt(this.bp);
        char cCharAt10 = charAt(this.bp + 1);
        char cCharAt11 = charAt(this.bp + 2);
        int i12 = 3;
        char cCharAt12 = charAt(this.bp + 3);
        char cCharAt13 = charAt(this.bp + 4);
        int i13 = 5;
        char cCharAt14 = charAt(this.bp + 5);
        char cCharAt15 = charAt(this.bp + 6);
        char cCharAt16 = charAt(this.bp + 7);
        if (!z2) {
            if (i2 > 13) {
                char cCharAt17 = charAt((this.bp + i2) - 1);
                char cCharAt18 = charAt((this.bp + i2) - 2);
                if (cCharAt9 == '/' && cCharAt10 == 'D' && cCharAt11 == 'a' && cCharAt12 == 't' && cCharAt13 == 'e' && cCharAt14 == '(' && cCharAt17 == '/' && cCharAt18 == ')') {
                    int i14 = -1;
                    for (int i15 = 6; i15 < i2; i15++) {
                        char cCharAt19 = charAt(this.bp + i15);
                        if (cCharAt19 != '+') {
                            if (cCharAt19 < '0' || cCharAt19 > '9') {
                                break;
                            }
                        } else {
                            i14 = i15;
                        }
                    }
                    if (i14 == -1) {
                        return false;
                    }
                    int i16 = this.bp;
                    int i17 = i16 + 6;
                    long j2 = Long.parseLong(subString(i17, (i16 + i14) - i17));
                    Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
                    this.calendar = calendar;
                    calendar.setTimeInMillis(j2);
                    this.token = 5;
                    return true;
                }
            }
            i13 = 5;
        }
        if (i2 == 8 || i2 == 14) {
            i3 = i13;
            z3 = false;
            c2 = ':';
        } else {
            if (!(i2 == 16 && ((cCharAt8 = charAt(this.bp + 10)) == 'T' || cCharAt8 == ' ')) && (i2 != 17 || charAt(this.bp + 6) == '-')) {
                if (i2 < 9) {
                    return false;
                }
                char cCharAt20 = charAt(this.bp + 8);
                char cCharAt21 = charAt(this.bp + 9);
                if ((cCharAt13 == '-' && cCharAt16 == '-') || (cCharAt13 == '/' && cCharAt16 == '/')) {
                    if (cCharAt21 == ' ') {
                        c11 = cCharAt14;
                        c12 = cCharAt12;
                        c13 = cCharAt9;
                        c14 = cCharAt10;
                        c15 = '0';
                        i9 = 9;
                        cCharAt10 = cCharAt20;
                        c17 = cCharAt15;
                        c16 = cCharAt11;
                    } else {
                        c12 = cCharAt12;
                        c13 = cCharAt9;
                        i9 = 10;
                        c15 = cCharAt20;
                        c17 = cCharAt15;
                        c16 = cCharAt11;
                        c11 = cCharAt14;
                        c14 = cCharAt10;
                        cCharAt10 = cCharAt21;
                    }
                } else if (cCharAt13 == '-' && cCharAt15 == '-') {
                    if (cCharAt20 == ' ') {
                        c16 = cCharAt11;
                        c17 = cCharAt14;
                        c13 = cCharAt9;
                        c14 = cCharAt10;
                        c11 = '0';
                        i9 = 8;
                        cCharAt10 = cCharAt16;
                        c12 = cCharAt12;
                        c15 = '0';
                    } else {
                        c16 = cCharAt11;
                        c13 = cCharAt9;
                        c11 = '0';
                        i9 = 9;
                        c17 = cCharAt14;
                        c14 = cCharAt10;
                        cCharAt10 = cCharAt20;
                        c15 = cCharAt16;
                        c12 = cCharAt12;
                    }
                } else if ((cCharAt11 == '.' && cCharAt14 == '.') || (cCharAt11 == '-' && cCharAt14 == '-')) {
                    c14 = cCharAt16;
                    c12 = cCharAt21;
                    c11 = cCharAt12;
                    c15 = cCharAt9;
                    i9 = 10;
                    c17 = cCharAt13;
                    c13 = cCharAt15;
                    c16 = cCharAt20;
                } else if (cCharAt20 == 'T') {
                    c11 = cCharAt13;
                    c17 = cCharAt14;
                    c13 = cCharAt9;
                    c14 = cCharAt10;
                    i9 = 8;
                    cCharAt10 = cCharAt16;
                    c12 = cCharAt12;
                    c15 = cCharAt15;
                    c16 = cCharAt11;
                } else {
                    if (cCharAt13 != 24180 && cCharAt13 != 45380) {
                        return false;
                    }
                    if (cCharAt16 != 26376 && cCharAt16 != 50900) {
                        if (cCharAt15 != 26376 && cCharAt15 != 50900) {
                            return false;
                        }
                        if (cCharAt20 == 26085 || cCharAt20 == 51068) {
                            c16 = cCharAt11;
                            c17 = cCharAt14;
                            c13 = cCharAt9;
                            c14 = cCharAt10;
                            i9 = 10;
                            c11 = '0';
                            cCharAt10 = cCharAt16;
                            c12 = cCharAt12;
                            c15 = '0';
                        } else {
                            if (cCharAt21 != 26085 && cCharAt21 != 51068) {
                                return false;
                            }
                            c16 = cCharAt11;
                            c13 = cCharAt9;
                            i9 = 10;
                            c11 = '0';
                            c17 = cCharAt14;
                            c14 = cCharAt10;
                            cCharAt10 = cCharAt20;
                            c15 = cCharAt16;
                            c12 = cCharAt12;
                        }
                    } else if (cCharAt21 == 26085 || cCharAt21 == 51068) {
                        c11 = cCharAt14;
                        c12 = cCharAt12;
                        c13 = cCharAt9;
                        c14 = cCharAt10;
                        i9 = 10;
                        c15 = '0';
                        cCharAt10 = cCharAt20;
                        c17 = cCharAt15;
                        c16 = cCharAt11;
                    } else {
                        if (charAt(this.bp + 10) != 26085 && charAt(this.bp + 10) != 51068) {
                            return false;
                        }
                        c12 = cCharAt12;
                        c13 = cCharAt9;
                        i9 = 11;
                        c15 = cCharAt20;
                        c17 = cCharAt15;
                        c16 = cCharAt11;
                        c11 = cCharAt14;
                        c14 = cCharAt10;
                        cCharAt10 = cCharAt21;
                    }
                }
                if (!checkDate(c13, c14, c16, c12, c11, c17, c15, cCharAt10)) {
                    return false;
                }
                setCalendar(c13, c14, c16, c12, c11, c17, c15, cCharAt10);
                char cCharAt22 = charAt(this.bp + i9);
                char c18 = 'T';
                if (cCharAt22 == 'T') {
                    if (i2 == 16 && i9 == 8 && charAt(this.bp + 15) == 'Z') {
                        char cCharAt23 = charAt(this.bp + i9 + 1);
                        char cCharAt24 = charAt(this.bp + i9 + 2);
                        char cCharAt25 = charAt(this.bp + i9 + 3);
                        char cCharAt26 = charAt(this.bp + i9 + 4);
                        char cCharAt27 = charAt(this.bp + i9 + 5);
                        char cCharAt28 = charAt(this.bp + i9 + 6);
                        if (!checkTime(cCharAt23, cCharAt24, cCharAt25, cCharAt26, cCharAt27, cCharAt28)) {
                            return false;
                        }
                        setTime(cCharAt23, cCharAt24, cCharAt25, cCharAt26, cCharAt27, cCharAt28);
                        this.calendar.set(14, 0);
                        if (this.calendar.getTimeZone().getRawOffset() != 0) {
                            String[] availableIDs = TimeZone.getAvailableIDs(0);
                            if (availableIDs.length > 0) {
                                this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs[0]));
                            }
                        }
                        this.token = 5;
                        return true;
                    }
                    c18 = 'T';
                }
                if (cCharAt22 != c18 && (cCharAt22 != ' ' || z2)) {
                    if (cCharAt22 != '\"' && cCharAt22 != 26 && cCharAt22 != 26085 && cCharAt22 != 51068) {
                        if ((cCharAt22 != '+' && cCharAt22 != '-') || this.len != i9 + 6 || charAt(this.bp + i9 + 3) != ':' || charAt(this.bp + i9 + 4) != '0' || charAt(this.bp + i9 + 5) != '0') {
                            return false;
                        }
                        setTime('0', '0', '0', '0', '0', '0');
                        this.calendar.set(14, 0);
                        setTimeZone(cCharAt22, charAt(this.bp + i9 + 1), charAt(this.bp + i9 + 2));
                        return true;
                    }
                    this.calendar.set(11, 0);
                    this.calendar.set(12, 0);
                    this.calendar.set(13, 0);
                    this.calendar.set(14, 0);
                    int i18 = this.bp + i9;
                    this.bp = i18;
                    this.ch = charAt(i18);
                    this.token = 5;
                    return true;
                }
                if (i2 < i9 + 9 || charAt(this.bp + i9 + 3) != ':' || charAt(this.bp + i9 + 6) != ':') {
                    return false;
                }
                char cCharAt29 = charAt(this.bp + i9 + 1);
                char cCharAt30 = charAt(this.bp + i9 + 2);
                char cCharAt31 = charAt(this.bp + i9 + 4);
                char cCharAt32 = charAt(this.bp + i9 + 5);
                char cCharAt33 = charAt(this.bp + i9 + 7);
                char cCharAt34 = charAt(this.bp + i9 + 8);
                if (!checkTime(cCharAt29, cCharAt30, cCharAt31, cCharAt32, cCharAt33, cCharAt34)) {
                    return false;
                }
                setTime(cCharAt29, cCharAt30, cCharAt31, cCharAt32, cCharAt33, cCharAt34);
                if (charAt(this.bp + i9 + 9) == '.') {
                    int i19 = i9 + 11;
                    if (i2 < i19 || (cCharAt5 = charAt(this.bp + i9 + 10)) < '0' || cCharAt5 > '9') {
                        return false;
                    }
                    int i20 = cCharAt5 - '0';
                    if (i2 <= i19 || (cCharAt7 = charAt(this.bp + i9 + 11)) < '0' || cCharAt7 > '9') {
                        i10 = 1;
                    } else {
                        i20 = (i20 * 10) + (cCharAt7 - '0');
                        i10 = 2;
                    }
                    if (i10 != 2 || (cCharAt6 = charAt(this.bp + i9 + 12)) < '0' || cCharAt6 > '9') {
                        i11 = i20;
                    } else {
                        i11 = (cCharAt6 - '0') + (i20 * 10);
                        i10 = 3;
                    }
                } else {
                    i10 = -1;
                    i11 = 0;
                }
                this.calendar.set(14, i11);
                char cCharAt35 = charAt(this.bp + i9 + 10 + i10);
                if (cCharAt35 == ' ') {
                    i10++;
                    cCharAt35 = charAt(this.bp + i9 + 10 + i10);
                }
                char c19 = cCharAt35;
                if (c19 == '+' || c19 == '-') {
                    char cCharAt36 = charAt(this.bp + i9 + 10 + i10 + 1);
                    if (cCharAt36 < '0' || cCharAt36 > '1' || (cCharAt3 = charAt(this.bp + i9 + 10 + i10 + 2)) < '0' || cCharAt3 > '9') {
                        return false;
                    }
                    char cCharAt37 = charAt(this.bp + i9 + 10 + i10 + 3);
                    char c20 = '3';
                    if (cCharAt37 == ':') {
                        char cCharAt38 = charAt(this.bp + i9 + 10 + i10 + 4);
                        cCharAt4 = charAt(this.bp + i9 + 10 + i10 + 5);
                        if (cCharAt38 == '4' && cCharAt4 == '5') {
                            if (cCharAt36 != '1' || (cCharAt3 != '2' && cCharAt3 != '3')) {
                                if (cCharAt36 != '0') {
                                    return false;
                                }
                                if (cCharAt3 != '5' && cCharAt3 != '8') {
                                    return false;
                                }
                            }
                        } else if ((cCharAt38 != '0' && cCharAt38 != '3') || cCharAt4 != '0') {
                            return false;
                        }
                        c20 = cCharAt38;
                        i12 = 6;
                    } else {
                        if (cCharAt37 == '0') {
                            char cCharAt39 = charAt(this.bp + i9 + 10 + i10 + 4);
                            if (cCharAt39 != '0' && cCharAt39 != '3') {
                                return false;
                            }
                            c20 = cCharAt39;
                        } else {
                            if (cCharAt37 != '3' || charAt(this.bp + i9 + 10 + i10 + 4) != '0') {
                                if (cCharAt37 == '4' && charAt(this.bp + i9 + 10 + i10 + 4) == '5') {
                                    cCharAt4 = '5';
                                    i12 = 5;
                                    c20 = '4';
                                } else {
                                    c20 = '0';
                                }
                            }
                            cCharAt4 = '0';
                        }
                        i12 = 5;
                        cCharAt4 = '0';
                    }
                    setTimeZone(c19, cCharAt36, cCharAt3, c20, cCharAt4);
                } else if (c19 == 'Z') {
                    if (this.calendar.getTimeZone().getRawOffset() != 0) {
                        String[] availableIDs2 = TimeZone.getAvailableIDs(0);
                        if (availableIDs2.length > 0) {
                            this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs2[0]));
                        }
                    }
                    i12 = 1;
                } else {
                    i12 = 0;
                }
                int i21 = i9 + 10 + i10 + i12;
                char cCharAt40 = charAt(this.bp + i21);
                if (cCharAt40 != 26 && cCharAt40 != '\"') {
                    return false;
                }
                int i22 = this.bp + i21;
                this.bp = i22;
                this.ch = charAt(i22);
                this.token = 5;
                return true;
            }
            z3 = false;
            c2 = ':';
            i3 = 5;
        }
        if (z2) {
            return z3;
        }
        char cCharAt41 = charAt(this.bp + 8);
        boolean z4 = cCharAt13 == '-' && cCharAt16 == '-';
        boolean z5 = z4 && i2 == 16;
        boolean z6 = z4 && i2 == 17;
        if (z6 || z5) {
            cCharAt = charAt(this.bp + 9);
            c3 = cCharAt14;
            c4 = cCharAt15;
            c5 = cCharAt41;
        } else if (cCharAt13 == '-' && cCharAt15 == '-') {
            c4 = cCharAt14;
            cCharAt = cCharAt16;
            c3 = '0';
            c5 = '0';
        } else {
            c3 = cCharAt13;
            c4 = cCharAt14;
            c5 = cCharAt15;
            cCharAt = cCharAt16;
        }
        if (!checkDate(cCharAt9, cCharAt10, cCharAt11, cCharAt12, c3, c4, c5, cCharAt)) {
            return false;
        }
        setCalendar(cCharAt9, cCharAt10, cCharAt11, cCharAt12, c3, c4, c5, cCharAt);
        if (i2 != 8) {
            char cCharAt42 = charAt(this.bp + 9);
            char cCharAt43 = charAt(this.bp + 10);
            char cCharAt44 = charAt(this.bp + 11);
            char cCharAt45 = charAt(this.bp + 12);
            char cCharAt46 = charAt(this.bp + 13);
            if ((z6 && cCharAt43 == 'T' && cCharAt46 == c2 && charAt(this.bp + 16) == 'Z') || (z5 && ((cCharAt43 == ' ' || cCharAt43 == 'T') && cCharAt46 == c2))) {
                char cCharAt47 = charAt(this.bp + 14);
                cCharAt2 = charAt(this.bp + 15);
                c7 = cCharAt47;
                c9 = cCharAt44;
                c6 = cCharAt45;
                c10 = '0';
                c8 = '0';
            } else {
                c6 = cCharAt42;
                c7 = cCharAt43;
                cCharAt2 = cCharAt44;
                c8 = cCharAt46;
                c9 = cCharAt41;
                c10 = cCharAt45;
            }
            if (!checkTime(c9, c6, c7, cCharAt2, c10, c8)) {
                return false;
            }
            if (i2 != 17 || z6) {
                i8 = 0;
            } else {
                char cCharAt48 = charAt(this.bp + 14);
                char cCharAt49 = charAt(this.bp + 15);
                char cCharAt50 = charAt(this.bp + 16);
                if (cCharAt48 < '0' || cCharAt48 > '9' || cCharAt49 < '0' || cCharAt49 > '9' || cCharAt50 < '0' || cCharAt50 > '9') {
                    return false;
                }
                i8 = ((cCharAt48 - '0') * 100) + ((cCharAt49 - '0') * 10) + (cCharAt50 - '0');
            }
            i5 = ((c10 - '0') * 10) + (c8 - '0');
            i7 = ((c9 - '0') * 10) + (c6 - '0');
            i6 = i8;
            i4 = ((c7 - '0') * 10) + (cCharAt2 - '0');
        } else {
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
        }
        this.calendar.set(11, i7);
        this.calendar.set(12, i4);
        this.calendar.set(13, i5);
        this.calendar.set(14, i6);
        this.token = i3;
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] cArr) {
        return charArrayCompare(this.text, this.bp, cArr);
    }

    public JSONScanner(char[] cArr, int i2) {
        this(cArr, i2, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] cArr, int i2, int i3) {
        this(new String(cArr, 0, i2), i3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String[] scanFieldStringArray(char[] cArr, int i2, SymbolTable symbolTable) throws NumberFormatException {
        int i3;
        char cCharAt;
        int i4 = this.bp;
        char c2 = this.ch;
        while (JSONLexerBase.isWhitespace(this.ch)) {
            next();
        }
        if (cArr != null) {
            this.matchStat = 0;
            if (!charArrayCompare(cArr)) {
                this.matchStat = -2;
                return null;
            }
            int length = this.bp + cArr.length;
            int i5 = length + 1;
            char cCharAt2 = this.text.charAt(length);
            while (JSONLexerBase.isWhitespace(cCharAt2)) {
                cCharAt2 = this.text.charAt(i5);
                i5++;
            }
            if (cCharAt2 == ':') {
                i3 = i5 + 1;
                cCharAt = this.text.charAt(i5);
                while (JSONLexerBase.isWhitespace(cCharAt)) {
                    cCharAt = this.text.charAt(i3);
                    i3++;
                }
            } else {
                this.matchStat = -1;
                return null;
            }
        } else {
            i3 = this.bp + 1;
            cCharAt = this.ch;
        }
        if (cCharAt == '[') {
            this.bp = i3;
            this.ch = this.text.charAt(i3);
            String[] strArr = i2 >= 0 ? new String[i2] : new String[4];
            int i6 = 0;
            while (true) {
                if (JSONLexerBase.isWhitespace(this.ch)) {
                    next();
                } else {
                    if (this.ch != '\"') {
                        this.bp = i4;
                        this.ch = c2;
                        this.matchStat = -1;
                        return null;
                    }
                    String strScanSymbol = scanSymbol(symbolTable, Typography.quote);
                    if (i6 == strArr.length) {
                        String[] strArr2 = new String[strArr.length + (strArr.length >> 1) + 1];
                        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                        strArr = strArr2;
                    }
                    int i7 = i6 + 1;
                    strArr[i6] = strScanSymbol;
                    while (JSONLexerBase.isWhitespace(this.ch)) {
                        next();
                    }
                    if (this.ch == ',') {
                        next();
                        i6 = i7;
                    } else {
                        if (strArr.length != i7) {
                            String[] strArr3 = new String[i7];
                            System.arraycopy(strArr, 0, strArr3, 0, i7);
                            strArr = strArr3;
                        }
                        while (JSONLexerBase.isWhitespace(this.ch)) {
                            next();
                        }
                        if (this.ch == ']') {
                            next();
                            return strArr;
                        }
                        this.bp = i4;
                        this.ch = c2;
                        this.matchStat = -1;
                        return null;
                    }
                }
            }
        } else {
            if (cCharAt == 'n' && this.text.startsWith("ull", this.bp + 1)) {
                int i8 = this.bp + 4;
                this.bp = i8;
                this.ch = this.text.charAt(i8);
                return null;
            }
            this.matchStat = -1;
            return null;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int seekObjectToField(long[] jArr) {
        char c2;
        int i2 = this.token;
        if (i2 != 12 && i2 != 16) {
            throw new UnsupportedOperationException();
        }
        while (true) {
            char c3 = this.ch;
            if (c3 == '}') {
                next();
                nextToken();
                this.matchStat = -1;
                return -1;
            }
            char cCharAt = JSONLexer.EOI;
            if (c3 == 26) {
                this.matchStat = -1;
                return -1;
            }
            if (c3 != '\"') {
                skipWhitespace();
            }
            if (this.ch == '\"') {
                int i3 = this.bp + 1;
                long j2 = TypeUtils.fnv1a_64_magic_hashcode;
                while (true) {
                    if (i3 >= this.text.length()) {
                        break;
                    }
                    char cCharAt2 = this.text.charAt(i3);
                    if (cCharAt2 == '\\') {
                        i3++;
                        if (i3 != this.text.length()) {
                            cCharAt2 = this.text.charAt(i3);
                        } else {
                            throw new JSONException("unclosed str, " + info());
                        }
                    }
                    if (cCharAt2 == '\"') {
                        int i4 = i3 + 1;
                        this.bp = i4;
                        this.ch = i4 >= this.text.length() ? (char) 26 : this.text.charAt(this.bp);
                    } else {
                        j2 = (j2 ^ cCharAt2) * TypeUtils.fnv1a_64_magic_prime;
                        i3++;
                    }
                }
                int i5 = 0;
                while (true) {
                    if (i5 >= jArr.length) {
                        i5 = -1;
                        break;
                    }
                    if (j2 == jArr[i5]) {
                        break;
                    }
                    i5++;
                }
                if (i5 != -1) {
                    if (this.ch != ':') {
                        skipWhitespace();
                    }
                    if (this.ch == ':') {
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        char cCharAt3 = i6 >= this.text.length() ? (char) 26 : this.text.charAt(i6);
                        this.ch = cCharAt3;
                        if (cCharAt3 == ',') {
                            int i7 = this.bp + 1;
                            this.bp = i7;
                            if (i7 < this.text.length()) {
                                cCharAt = this.text.charAt(i7);
                            }
                            this.ch = cCharAt;
                            this.token = 16;
                        } else if (cCharAt3 == ']') {
                            int i8 = this.bp + 1;
                            this.bp = i8;
                            if (i8 < this.text.length()) {
                                cCharAt = this.text.charAt(i8);
                            }
                            this.ch = cCharAt;
                            this.token = 15;
                        } else if (cCharAt3 == '}') {
                            int i9 = this.bp + 1;
                            this.bp = i9;
                            if (i9 < this.text.length()) {
                                cCharAt = this.text.charAt(i9);
                            }
                            this.ch = cCharAt;
                            this.token = 13;
                        } else if (cCharAt3 >= '0' && cCharAt3 <= '9') {
                            this.sp = 0;
                            this.pos = this.bp;
                            scanNumber();
                        } else {
                            nextToken(2);
                        }
                    }
                    this.matchStat = 3;
                    return i5;
                }
                if (this.ch != ':') {
                    skipWhitespace();
                }
                if (this.ch == ':') {
                    int i10 = this.bp + 1;
                    this.bp = i10;
                    char cCharAt4 = i10 >= this.text.length() ? (char) 26 : this.text.charAt(i10);
                    this.ch = cCharAt4;
                    if (cCharAt4 != '\"' && cCharAt4 != '\'' && cCharAt4 != '{' && cCharAt4 != '[' && cCharAt4 != '0' && cCharAt4 != '1' && cCharAt4 != '2' && cCharAt4 != '3' && cCharAt4 != '4' && cCharAt4 != '5' && cCharAt4 != '6' && cCharAt4 != '7' && cCharAt4 != '8' && cCharAt4 != '9' && cCharAt4 != '+' && cCharAt4 != '-') {
                        skipWhitespace();
                    }
                    char c4 = this.ch;
                    if (c4 == '-' || c4 == '+' || (c4 >= '0' && c4 <= '9')) {
                        next();
                        while (true) {
                            c2 = this.ch;
                            if (c2 < '0' || c2 > '9') {
                                break;
                            }
                            next();
                        }
                        if (c2 == '.') {
                            next();
                            while (true) {
                                char c5 = this.ch;
                                if (c5 < '0' || c5 > '9') {
                                    break;
                                }
                                next();
                            }
                        }
                        char c6 = this.ch;
                        if (c6 == 'E' || c6 == 'e') {
                            next();
                            char c7 = this.ch;
                            if (c7 == '-' || c7 == '+') {
                                next();
                            }
                            while (true) {
                                char c8 = this.ch;
                                if (c8 < '0' || c8 > '9') {
                                    break;
                                }
                                next();
                            }
                        }
                        if (this.ch != ',') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c4 == '\"') {
                        skipString();
                        char c9 = this.ch;
                        if (c9 != ',' && c9 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c4 == '{') {
                        int i11 = this.bp + 1;
                        this.bp = i11;
                        if (i11 < this.text.length()) {
                            cCharAt = this.text.charAt(i11);
                        }
                        this.ch = cCharAt;
                        skipObject(false);
                    } else if (c4 == '[') {
                        next();
                        skipArray(false);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else {
                    throw new JSONException("illegal json, " + info());
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }
}
