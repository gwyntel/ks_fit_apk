package org.mozilla.javascript;

import java.io.IOException;
import java.io.Reader;
import org.mozilla.javascript.Token;

/* loaded from: classes5.dex */
class TokenStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char BYTE_ORDER_MARK = 65279;
    private static final int EOF_CHAR = -1;
    Token.CommentType commentType;
    int cursor;
    private boolean dirtyLine;
    private boolean isBinary;
    private boolean isHex;
    private boolean isOctal;
    private boolean isOldOctal;
    int lineno;
    private double number;
    private Parser parser;
    private int quoteChar;
    String regExpFlags;
    private char[] sourceBuffer;
    int sourceCursor;
    private int sourceEnd;
    private Reader sourceReader;
    private String sourceString;
    private int stringBufferTop;
    int tokenBeg;
    int tokenEnd;
    private int ungetCursor;
    private boolean xmlIsAttribute;
    private boolean xmlIsTagContent;
    private int xmlOpenTagsCount;
    private String string = "";
    private char[] stringBuffer = new char[128];
    private ObjToIntMap allStrings = new ObjToIntMap(50);
    private final int[] ungetBuffer = new int[3];
    private boolean hitEOF = false;
    private int lineStart = 0;
    private int lineEndChar = -1;
    private String commentPrefix = "";
    private int commentCursor = -1;

    TokenStream(Parser parser, Reader reader, String str, int i2) throws RuntimeException {
        this.parser = parser;
        this.lineno = i2;
        if (reader != null) {
            if (str != null) {
                Kit.codeBug();
            }
            this.sourceReader = reader;
            this.sourceBuffer = new char[512];
            this.sourceEnd = 0;
        } else {
            if (str == null) {
                Kit.codeBug();
            }
            this.sourceString = str;
            this.sourceEnd = str.length();
        }
        this.cursor = 0;
        this.sourceCursor = 0;
    }

    private void addToString(int i2) {
        int i3 = this.stringBufferTop;
        char[] cArr = this.stringBuffer;
        if (i3 == cArr.length) {
            char[] cArr2 = new char[cArr.length * 2];
            System.arraycopy(cArr, 0, cArr2, 0, i3);
            this.stringBuffer = cArr2;
        }
        this.stringBuffer[i3] = (char) i2;
        this.stringBufferTop = i3 + 1;
    }

    private boolean canUngetChar() {
        int i2 = this.ungetCursor;
        return i2 == 0 || this.ungetBuffer[i2 - 1] != 10;
    }

    private final int charAt(int i2) {
        if (i2 < 0) {
            return -1;
        }
        String str = this.sourceString;
        if (str != null) {
            if (i2 >= this.sourceEnd) {
                return -1;
            }
            return str.charAt(i2);
        }
        if (i2 >= this.sourceEnd) {
            int i3 = this.sourceCursor;
            try {
                if (!fillSourceBuffer()) {
                    return -1;
                }
                i2 -= i3 - this.sourceCursor;
            } catch (IOException unused) {
                return -1;
            }
        }
        return this.sourceBuffer[i2];
    }

    private String convertLastCharToHex(String str) {
        int length = str.length() - 1;
        StringBuilder sb = new StringBuilder(str.substring(0, length));
        sb.append("\\u");
        String hexString = Integer.toHexString(str.charAt(length));
        for (int i2 = 0; i2 < 4 - hexString.length(); i2++) {
            sb.append('0');
        }
        sb.append(hexString);
        return sb.toString();
    }

    private boolean fillSourceBuffer() throws IOException, RuntimeException {
        if (this.sourceString != null) {
            Kit.codeBug();
        }
        if (this.sourceEnd == this.sourceBuffer.length) {
            if (this.lineStart == 0 || isMarkingComment()) {
                char[] cArr = this.sourceBuffer;
                char[] cArr2 = new char[cArr.length * 2];
                System.arraycopy(cArr, 0, cArr2, 0, this.sourceEnd);
                this.sourceBuffer = cArr2;
            } else {
                char[] cArr3 = this.sourceBuffer;
                int i2 = this.lineStart;
                System.arraycopy(cArr3, i2, cArr3, 0, this.sourceEnd - i2);
                int i3 = this.sourceEnd;
                int i4 = this.lineStart;
                this.sourceEnd = i3 - i4;
                this.sourceCursor -= i4;
                this.lineStart = 0;
            }
        }
        Reader reader = this.sourceReader;
        char[] cArr4 = this.sourceBuffer;
        int i5 = this.sourceEnd;
        int i6 = reader.read(cArr4, i5, cArr4.length - i5);
        if (i6 < 0) {
            return false;
        }
        this.sourceEnd += i6;
        return true;
    }

    private int getChar() throws IOException {
        return getChar(true);
    }

    private int getCharIgnoreLineEnd() throws IOException {
        char cCharAt;
        int i2 = this.ungetCursor;
        if (i2 != 0) {
            this.cursor++;
            int[] iArr = this.ungetBuffer;
            int i3 = i2 - 1;
            this.ungetCursor = i3;
            return iArr[i3];
        }
        while (true) {
            String str = this.sourceString;
            if (str != null) {
                int i4 = this.sourceCursor;
                if (i4 == this.sourceEnd) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                this.sourceCursor = i4 + 1;
                cCharAt = str.charAt(i4);
            } else {
                if (this.sourceCursor == this.sourceEnd && !fillSourceBuffer()) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                char[] cArr = this.sourceBuffer;
                int i5 = this.sourceCursor;
                this.sourceCursor = i5 + 1;
                cCharAt = cArr[i5];
            }
            if (cCharAt <= 127) {
                if (cCharAt != '\n' && cCharAt != '\r') {
                    return cCharAt;
                }
                this.lineEndChar = cCharAt;
            } else {
                if (cCharAt == 65279) {
                    return cCharAt;
                }
                if (!isJSFormatChar(cCharAt)) {
                    if (!ScriptRuntime.isJSLineTerminator(cCharAt)) {
                        return cCharAt;
                    }
                    this.lineEndChar = cCharAt;
                }
            }
        }
        return 10;
    }

    private String getStringFromBuffer() {
        this.tokenEnd = this.cursor;
        return new String(this.stringBuffer, 0, this.stringBufferTop);
    }

    private static boolean isAlpha(int i2) {
        return i2 <= 90 ? 65 <= i2 : 97 <= i2 && i2 <= 122;
    }

    static boolean isDigit(int i2) {
        return 48 <= i2 && i2 <= 57;
    }

    private static boolean isJSFormatChar(int i2) {
        return i2 > 127 && Character.getType((char) i2) == 16;
    }

    static boolean isJSSpace(int i2) {
        return i2 <= 127 ? i2 == 32 || i2 == 9 || i2 == 12 || i2 == 11 : i2 == 160 || i2 == 65279 || Character.getType((char) i2) == 12;
    }

    static boolean isKeyword(String str, int i2, boolean z2) {
        return stringToKeyword(str, i2, z2) != 0;
    }

    private boolean isMarkingComment() {
        return this.commentCursor != -1;
    }

    private void markCommentStart() {
        markCommentStart("");
    }

    private boolean matchChar(int i2) throws IOException {
        int charIgnoreLineEnd = getCharIgnoreLineEnd();
        if (charIgnoreLineEnd == i2) {
            this.tokenEnd = this.cursor;
            return true;
        }
        ungetCharIgnoreLineEnd(charIgnoreLineEnd);
        return false;
    }

    private int peekChar() throws IOException, RuntimeException {
        int i2 = getChar();
        ungetChar(i2);
        return i2;
    }

    private boolean readCDATA() throws IOException {
        int i2 = getChar();
        while (i2 != -1) {
            addToString(i2);
            if (i2 == 93 && peekChar() == 93) {
                i2 = getChar();
                addToString(i2);
                if (peekChar() == 62) {
                    addToString(getChar());
                    return true;
                }
            } else {
                i2 = getChar();
            }
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readEntity() throws IOException {
        int i2 = getChar();
        int i3 = 1;
        while (i2 != -1) {
            addToString(i2);
            if (i2 == 60) {
                i3++;
            } else if (i2 == 62 && i3 - 1 == 0) {
                return true;
            }
            i2 = getChar();
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readPI() throws IOException {
        while (true) {
            int i2 = getChar();
            if (i2 == -1) {
                this.stringBufferTop = 0;
                this.string = null;
                this.parser.addError("msg.XML.bad.form");
                return false;
            }
            addToString(i2);
            if (i2 == 63 && peekChar() == 62) {
                addToString(getChar());
                return true;
            }
        }
    }

    private boolean readQuotedString(int i2) throws IOException {
        int i3;
        do {
            i3 = getChar();
            if (i3 == -1) {
                this.stringBufferTop = 0;
                this.string = null;
                this.parser.addError("msg.XML.bad.form");
                return false;
            }
            addToString(i3);
        } while (i3 != i2);
        return true;
    }

    private boolean readXmlComment() throws IOException {
        int i2 = getChar();
        while (i2 != -1) {
            addToString(i2);
            if (i2 == 45 && peekChar() == 45) {
                i2 = getChar();
                addToString(i2);
                if (peekChar() == 62) {
                    addToString(getChar());
                    return true;
                }
            } else {
                i2 = getChar();
            }
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private void skipLine() throws IOException, RuntimeException {
        int i2;
        do {
            i2 = getChar();
            if (i2 == -1) {
                break;
            }
        } while (i2 != 10);
        ungetChar(i2);
        this.tokenEnd = this.cursor;
    }

    private static int stringToKeyword(String str, int i2, boolean z2) {
        return i2 < 200 ? stringToKeywordForJS(str) : stringToKeywordForES(str, z2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x01a5, code lost:
    
        if (r17.charAt(1) == 'l') goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0079, code lost:
    
        if (r1 != 'x') goto L187;
     */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x026c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int stringToKeywordForES(java.lang.String r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 668
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.stringToKeywordForES(java.lang.String, boolean):int");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0203, code lost:
    
        if (r17.charAt(1) == 'n') goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0205, code lost:
    
        r2 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x021d, code lost:
    
        if (r17.charAt(1) == 'a') goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x021f, code lost:
    
        r2 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0230, code lost:
    
        if (r17.charAt(1) == 'h') goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x029d, code lost:
    
        if (r17.charAt(1) == 'n') goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x02d3, code lost:
    
        if (r17.charAt(0) == 'd') goto L159;
     */
    /* JADX WARN: Removed duplicated region for block: B:219:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x02db A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int stringToKeywordForJS(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 792
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.stringToKeywordForJS(java.lang.String):int");
    }

    private final String substring(int i2, int i3) {
        String str = this.sourceString;
        if (str != null) {
            return str.substring(i2, i3);
        }
        return new String(this.sourceBuffer, i2, i3 - i2);
    }

    private void ungetChar(int i2) throws RuntimeException {
        int i3 = this.ungetCursor;
        if (i3 != 0 && this.ungetBuffer[i3 - 1] == 10) {
            Kit.codeBug();
        }
        int[] iArr = this.ungetBuffer;
        int i4 = this.ungetCursor;
        this.ungetCursor = i4 + 1;
        iArr[i4] = i2;
        this.cursor--;
    }

    private void ungetCharIgnoreLineEnd(int i2) {
        int[] iArr = this.ungetBuffer;
        int i3 = this.ungetCursor;
        this.ungetCursor = i3 + 1;
        iArr[i3] = i2;
        this.cursor--;
    }

    final boolean eof() {
        return this.hitEOF;
    }

    final String getAndResetCurrentComment() {
        if (this.sourceString != null) {
            if (isMarkingComment()) {
                Kit.codeBug();
            }
            return this.sourceString.substring(this.tokenBeg, this.tokenEnd);
        }
        if (!isMarkingComment()) {
            Kit.codeBug();
        }
        StringBuilder sb = new StringBuilder(this.commentPrefix);
        sb.append(this.sourceBuffer, this.commentCursor, getTokenLength() - this.commentPrefix.length());
        this.commentCursor = -1;
        return sb.toString();
    }

    public Token.CommentType getCommentType() {
        return this.commentType;
    }

    public int getCursor() {
        return this.cursor;
    }

    int getFirstXMLToken() throws IOException {
        this.xmlOpenTagsCount = 0;
        this.xmlIsAttribute = false;
        this.xmlIsTagContent = false;
        if (!canUngetChar()) {
            return -1;
        }
        ungetChar(60);
        return getNextXMLToken();
    }

    final String getLine() {
        int i2;
        int i3 = this.sourceCursor;
        int i4 = this.lineEndChar;
        if (i4 >= 0) {
            i2 = i3 - 1;
            if (i4 == 10 && charAt(i3 - 2) == 13) {
                i2 = i3 - 2;
            }
        } else {
            int i5 = i3 - this.lineStart;
            while (true) {
                int iCharAt = charAt(this.lineStart + i5);
                if (iCharAt == -1 || ScriptRuntime.isJSLineTerminator(iCharAt)) {
                    break;
                }
                i5++;
            }
            i2 = this.lineStart + i5;
        }
        return substring(this.lineStart, i2);
    }

    final int getLineno() {
        return this.lineno;
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x0154, code lost:
    
        r10.stringBufferTop = 0;
        r10.string = null;
        r10.parser.addError("msg.XML.bad.form");
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x015d, code lost:
    
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int getNextXMLToken() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 397
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.getNextXMLToken():int");
    }

    final double getNumber() {
        return this.number;
    }

    final int getOffset() {
        int i2 = this.sourceCursor - this.lineStart;
        return this.lineEndChar >= 0 ? i2 - 1 : i2;
    }

    final char getQuoteChar() {
        return (char) this.quoteChar;
    }

    final String getSourceString() {
        return this.sourceString;
    }

    final String getString() {
        return this.string;
    }

    /* JADX WARN: Removed duplicated region for block: B:214:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:492:0x0290 A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:215:0x029f -> B:207:0x0283). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int getToken() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.getToken():int");
    }

    public int getTokenBeg() {
        return this.tokenBeg;
    }

    public int getTokenEnd() {
        return this.tokenEnd;
    }

    public int getTokenLength() {
        return this.tokenEnd - this.tokenBeg;
    }

    final boolean isNumberBinary() {
        return this.isBinary;
    }

    final boolean isNumberHex() {
        return this.isHex;
    }

    final boolean isNumberOctal() {
        return this.isOctal;
    }

    final boolean isNumberOldOctal() {
        return this.isOldOctal;
    }

    boolean isXMLAttribute() {
        return this.xmlIsAttribute;
    }

    String readAndClearRegExpFlags() {
        String str = this.regExpFlags;
        this.regExpFlags = null;
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a7, code lost:
    
        ungetChar(r2);
        r5.tokenEnd = r5.cursor - 1;
        r5.string = new java.lang.String(r5.stringBuffer, 0, r5.stringBufferTop);
        r5.parser.reportError("msg.unterminated.re.lit");
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00c1, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void readRegExp(int r6) throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.tokenBeg
            r1 = 0
            r5.stringBufferTop = r1
            r2 = 101(0x65, float:1.42E-43)
            if (r6 != r2) goto Lf
            r6 = 61
            r5.addToString(r6)
            goto L16
        Lf:
            r2 = 24
            if (r6 == r2) goto L16
            org.mozilla.javascript.Kit.codeBug()
        L16:
            r6 = r1
        L17:
            int r2 = r5.getChar()
            r3 = 47
            if (r2 != r3) goto L82
            if (r6 == 0) goto L22
            goto L82
        L22:
            int r6 = r5.stringBufferTop
        L24:
            r2 = 103(0x67, float:1.44E-43)
            boolean r3 = r5.matchChar(r2)
            if (r3 == 0) goto L30
            r5.addToString(r2)
            goto L24
        L30:
            r2 = 105(0x69, float:1.47E-43)
            boolean r3 = r5.matchChar(r2)
            if (r3 == 0) goto L3c
            r5.addToString(r2)
            goto L24
        L3c:
            r2 = 109(0x6d, float:1.53E-43)
            boolean r3 = r5.matchChar(r2)
            if (r3 == 0) goto L48
            r5.addToString(r2)
            goto L24
        L48:
            r2 = 121(0x79, float:1.7E-43)
            boolean r3 = r5.matchChar(r2)
            if (r3 == 0) goto L54
            r5.addToString(r2)
            goto L24
        L54:
            int r2 = r5.stringBufferTop
            int r0 = r0 + r2
            int r0 = r0 + 2
            r5.tokenEnd = r0
            int r0 = r5.peekChar()
            boolean r0 = isAlpha(r0)
            if (r0 == 0) goto L6c
            org.mozilla.javascript.Parser r0 = r5.parser
            java.lang.String r2 = "msg.invalid.re.flag"
            r0.reportError(r2)
        L6c:
            java.lang.String r0 = new java.lang.String
            char[] r2 = r5.stringBuffer
            r0.<init>(r2, r1, r6)
            r5.string = r0
            java.lang.String r0 = new java.lang.String
            char[] r1 = r5.stringBuffer
            int r2 = r5.stringBufferTop
            int r2 = r2 - r6
            r0.<init>(r1, r6, r2)
            r5.regExpFlags = r0
            return
        L82:
            r3 = 10
            r4 = 1
            if (r2 == r3) goto La7
            r3 = -1
            if (r2 != r3) goto L8b
            goto La7
        L8b:
            r3 = 92
            if (r2 != r3) goto L97
            r5.addToString(r2)
            int r2 = r5.getChar()
            goto La2
        L97:
            r3 = 91
            if (r2 != r3) goto L9d
            r6 = r4
            goto La2
        L9d:
            r3 = 93
            if (r2 != r3) goto La2
            r6 = r1
        La2:
            r5.addToString(r2)
            goto L17
        La7:
            r5.ungetChar(r2)
            int r6 = r5.cursor
            int r6 = r6 - r4
            r5.tokenEnd = r6
            java.lang.String r6 = new java.lang.String
            char[] r0 = r5.stringBuffer
            int r2 = r5.stringBufferTop
            r6.<init>(r0, r1, r2)
            r5.string = r6
            org.mozilla.javascript.Parser r6 = r5.parser
            java.lang.String r0 = "msg.unterminated.re.lit"
            r6.reportError(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.readRegExp(int):void");
    }

    String tokenToString(int i2) {
        return "";
    }

    private int getChar(boolean z2) throws IOException {
        char cCharAt;
        int i2 = this.ungetCursor;
        if (i2 != 0) {
            this.cursor++;
            int[] iArr = this.ungetBuffer;
            int i3 = i2 - 1;
            this.ungetCursor = i3;
            return iArr[i3];
        }
        while (true) {
            String str = this.sourceString;
            if (str != null) {
                int i4 = this.sourceCursor;
                if (i4 == this.sourceEnd) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                this.sourceCursor = i4 + 1;
                cCharAt = str.charAt(i4);
            } else {
                if (this.sourceCursor == this.sourceEnd && !fillSourceBuffer()) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                char[] cArr = this.sourceBuffer;
                int i5 = this.sourceCursor;
                this.sourceCursor = i5 + 1;
                cCharAt = cArr[i5];
            }
            int i6 = this.lineEndChar;
            if (i6 >= 0) {
                if (i6 == 13 && cCharAt == '\n') {
                    this.lineEndChar = 10;
                } else {
                    this.lineEndChar = -1;
                    this.lineStart = this.sourceCursor - 1;
                    this.lineno++;
                }
            }
            if (cCharAt <= 127) {
                if (cCharAt != '\n' && cCharAt != '\r') {
                    return cCharAt;
                }
                this.lineEndChar = cCharAt;
            } else {
                if (cCharAt == 65279) {
                    return cCharAt;
                }
                if (!z2 || !isJSFormatChar(cCharAt)) {
                    break;
                }
            }
        }
        if (!ScriptRuntime.isJSLineTerminator(cCharAt)) {
            return cCharAt;
        }
        this.lineEndChar = cCharAt;
        return 10;
    }

    private void markCommentStart(String str) {
        if (!this.parser.compilerEnv.isRecordingComments() || this.sourceReader == null) {
            return;
        }
        this.commentPrefix = str;
        this.commentCursor = this.sourceCursor - 1;
    }

    final String getLine(int i2, int[] iArr) {
        int i3 = (this.cursor + this.ungetCursor) - i2;
        int i4 = this.sourceCursor;
        if (i3 > i4) {
            return null;
        }
        int i5 = 0;
        int i6 = 0;
        while (i3 > 0) {
            int iCharAt = charAt(i4 - 1);
            if (ScriptRuntime.isJSLineTerminator(iCharAt)) {
                if (iCharAt == 10 && charAt(i4 - 2) == 13) {
                    i3--;
                    i4--;
                }
                i5++;
                i6 = i4 - 1;
            }
            i3--;
            i4--;
        }
        int i7 = 0;
        while (true) {
            if (i4 <= 0) {
                i4 = 0;
                break;
            }
            if (ScriptRuntime.isJSLineTerminator(charAt(i4 - 1))) {
                break;
            }
            i4--;
            i7++;
        }
        iArr[0] = (this.lineno - i5) + (this.lineEndChar >= 0 ? 1 : 0);
        iArr[1] = i7;
        if (i5 == 0) {
            return getLine();
        }
        return substring(i4, i6);
    }
}
