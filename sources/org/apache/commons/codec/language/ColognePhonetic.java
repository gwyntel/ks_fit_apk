package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
public class ColognePhonetic implements StringEncoder {
    private static final char[][] PREPROCESS_MAP = {new char[]{196, 'A'}, new char[]{220, 'U'}, new char[]{214, 'O'}, new char[]{223, 'S'}};

    private class CologneInputBuffer extends CologneBuffer {
        public CologneInputBuffer(char[] cArr) {
            super(cArr);
        }

        @Override // org.apache.commons.codec.language.ColognePhonetic.CologneBuffer
        protected char[] a(int i2, int i3) {
            char[] cArr = new char[i3];
            char[] cArr2 = this.f26568a;
            System.arraycopy(cArr2, (cArr2.length - this.f26569b) + i2, cArr, 0, i3);
            return cArr;
        }

        public void addLeft(char c2) {
            this.f26569b++;
            this.f26568a[b()] = c2;
        }

        protected int b() {
            return this.f26568a.length - this.f26569b;
        }

        public char getNextChar() {
            return this.f26568a[b()];
        }

        public char removeNext() {
            this.f26569b--;
            return getNextChar();
        }
    }

    private class CologneOutputBuffer extends CologneBuffer {
        public CologneOutputBuffer(int i2) {
            super(i2);
        }

        @Override // org.apache.commons.codec.language.ColognePhonetic.CologneBuffer
        protected char[] a(int i2, int i3) {
            char[] cArr = new char[i3];
            System.arraycopy(this.f26568a, i2, cArr, 0, i3);
            return cArr;
        }

        public void addRight(char c2) {
            char[] cArr = this.f26568a;
            int i2 = this.f26569b;
            cArr[i2] = c2;
            this.f26569b = i2 + 1;
        }
    }

    private static boolean arrayContains(char[] cArr, char c2) {
        for (char c3 : cArr) {
            if (c3 == c2) {
                return true;
            }
        }
        return false;
    }

    private String preprocess(String str) {
        char[] charArray = str.toUpperCase(Locale.GERMAN).toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (charArray[i2] > 'Z') {
                char[][] cArr = PREPROCESS_MAP;
                int length = cArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        char[] cArr2 = cArr[i3];
                        if (charArray[i2] == cArr2[0]) {
                            charArray[i2] = cArr2[1];
                            break;
                        }
                        i3++;
                    }
                }
            }
        }
        return new String(charArray);
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e2, code lost:
    
        if (arrayContains(new char[]{'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X'}, r13) != false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0104, code lost:
    
        if (arrayContains(new char[]{'A', 'H', 'O', 'U', 'K', 'Q', 'X'}, r13) == false) goto L56;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String colognePhonetic(java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.ColognePhonetic.colognePhonetic(java.lang.String):java.lang.String");
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + obj.getClass().getName() + ".");
    }

    public boolean isEncodeEqual(String str, String str2) {
        return colognePhonetic(str).equals(colognePhonetic(str2));
    }

    private abstract class CologneBuffer {

        /* renamed from: a, reason: collision with root package name */
        protected final char[] f26568a;

        /* renamed from: b, reason: collision with root package name */
        protected int f26569b;

        public CologneBuffer(char[] cArr) {
            this.f26569b = 0;
            this.f26568a = cArr;
            this.f26569b = cArr.length;
        }

        protected abstract char[] a(int i2, int i3);

        public int length() {
            return this.f26569b;
        }

        public String toString() {
            return new String(a(0, this.f26569b));
        }

        public CologneBuffer(int i2) {
            this.f26569b = 0;
            this.f26568a = new char[i2];
            this.f26569b = 0;
        }
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return colognePhonetic(str);
    }
}
