package com.google.common.io;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public abstract class BaseEncoding {
    private static final BaseEncoding BASE16;
    private static final BaseEncoding BASE32;
    private static final BaseEncoding BASE32_HEX;
    private static final BaseEncoding BASE64;
    private static final BaseEncoding BASE64_URL;

    static final class Alphabet {

        /* renamed from: a, reason: collision with root package name */
        final int f14639a;

        /* renamed from: b, reason: collision with root package name */
        final int f14640b;

        /* renamed from: c, reason: collision with root package name */
        final int f14641c;
        private final char[] chars;

        /* renamed from: d, reason: collision with root package name */
        final int f14642d;
        private final byte[] decodabet;
        private final boolean ignoreCase;
        private final String name;
        private final boolean[] validPadding;

        Alphabet(String str, char[] cArr) {
            this(str, cArr, decodabetFor(cArr), false);
        }

        private static byte[] decodabetFor(char[] cArr) {
            byte[] bArr = new byte[128];
            Arrays.fill(bArr, (byte) -1);
            for (int i2 = 0; i2 < cArr.length; i2++) {
                char c2 = cArr[i2];
                boolean z2 = true;
                Preconditions.checkArgument(c2 < 128, "Non-ASCII character: %s", c2);
                if (bArr[c2] != -1) {
                    z2 = false;
                }
                Preconditions.checkArgument(z2, "Duplicate character: %s", c2);
                bArr[c2] = (byte) i2;
            }
            return bArr;
        }

        private boolean hasLowerCase() {
            for (char c2 : this.chars) {
                if (Ascii.isLowerCase(c2)) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasUpperCase() {
            for (char c2 : this.chars) {
                if (Ascii.isUpperCase(c2)) {
                    return true;
                }
            }
            return false;
        }

        boolean b(char c2) {
            return c2 <= 127 && this.decodabet[c2] != -1;
        }

        int c(char c2) throws DecodingException {
            if (c2 > 127) {
                throw new DecodingException("Unrecognized character: 0x" + Integer.toHexString(c2));
            }
            byte b2 = this.decodabet[c2];
            if (b2 != -1) {
                return b2;
            }
            if (c2 <= ' ' || c2 == 127) {
                throw new DecodingException("Unrecognized character: 0x" + Integer.toHexString(c2));
            }
            throw new DecodingException("Unrecognized character: " + c2);
        }

        char d(int i2) {
            return this.chars[i2];
        }

        Alphabet e() {
            if (this.ignoreCase) {
                return this;
            }
            byte[] bArr = this.decodabet;
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            int i2 = 65;
            while (true) {
                if (i2 > 90) {
                    return new Alphabet(this.name + ".ignoreCase()", this.chars, bArrCopyOf, true);
                }
                int i3 = i2 | 32;
                byte[] bArr2 = this.decodabet;
                byte b2 = bArr2[i2];
                byte b3 = bArr2[i3];
                if (b2 == -1) {
                    bArrCopyOf[i2] = b3;
                } else {
                    Preconditions.checkState(b3 == -1, "Can't ignoreCase() since '%s' and '%s' encode different values", (char) i2, (char) i3);
                    bArrCopyOf[i3] = b2;
                }
                i2++;
            }
        }

        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof Alphabet)) {
                return false;
            }
            Alphabet alphabet = (Alphabet) obj;
            return this.ignoreCase == alphabet.ignoreCase && Arrays.equals(this.chars, alphabet.chars);
        }

        boolean f(int i2) {
            return this.validPadding[i2 % this.f14641c];
        }

        Alphabet g() {
            if (!hasUpperCase()) {
                return this;
            }
            Preconditions.checkState(!hasLowerCase(), "Cannot call lowerCase() on a mixed-case alphabet");
            char[] cArr = new char[this.chars.length];
            int i2 = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i2 >= cArr2.length) {
                    break;
                }
                cArr[i2] = Ascii.toLowerCase(cArr2[i2]);
                i2++;
            }
            Alphabet alphabet = new Alphabet(this.name + ".lowerCase()", cArr);
            return this.ignoreCase ? alphabet.e() : alphabet;
        }

        Alphabet h() {
            if (!hasLowerCase()) {
                return this;
            }
            Preconditions.checkState(!hasUpperCase(), "Cannot call upperCase() on a mixed-case alphabet");
            char[] cArr = new char[this.chars.length];
            int i2 = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i2 >= cArr2.length) {
                    break;
                }
                cArr[i2] = Ascii.toUpperCase(cArr2[i2]);
                i2++;
            }
            Alphabet alphabet = new Alphabet(this.name + ".upperCase()", cArr);
            return this.ignoreCase ? alphabet.e() : alphabet;
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars) + (this.ignoreCase ? 1231 : 1237);
        }

        public boolean matches(char c2) {
            byte[] bArr = this.decodabet;
            return c2 < bArr.length && bArr[c2] != -1;
        }

        public String toString() {
            return this.name;
        }

        private Alphabet(String str, char[] cArr, byte[] bArr, boolean z2) {
            this.name = (String) Preconditions.checkNotNull(str);
            this.chars = (char[]) Preconditions.checkNotNull(cArr);
            try {
                int iLog2 = IntMath.log2(cArr.length, RoundingMode.UNNECESSARY);
                this.f14640b = iLog2;
                int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(iLog2);
                int i2 = 1 << (3 - iNumberOfTrailingZeros);
                this.f14641c = i2;
                this.f14642d = iLog2 >> iNumberOfTrailingZeros;
                this.f14639a = cArr.length - 1;
                this.decodabet = bArr;
                boolean[] zArr = new boolean[i2];
                for (int i3 = 0; i3 < this.f14642d; i3++) {
                    zArr[IntMath.divide(i3 * 8, this.f14640b, RoundingMode.CEILING)] = true;
                }
                this.validPadding = zArr;
                this.ignoreCase = z2;
            } catch (ArithmeticException e2) {
                throw new IllegalArgumentException("Illegal alphabet length " + cArr.length, e2);
            }
        }
    }

    private static final class Base16Encoding extends StandardBaseEncoding {

        /* renamed from: c, reason: collision with root package name */
        final char[] f14643c;

        Base16Encoding(String str, String str2) {
            this(new Alphabet(str, str2.toCharArray()));
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        int b(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            if (charSequence.length() % 2 == 1) {
                throw new DecodingException("Invalid input length " + charSequence.length());
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < charSequence.length()) {
                bArr[i3] = (byte) ((this.f14644a.c(charSequence.charAt(i2)) << 4) | this.f14644a.c(charSequence.charAt(i2 + 1)));
                i2 += 2;
                i3++;
            }
            return i3;
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        void c(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = bArr[i2 + i4] & 255;
                appendable.append(this.f14643c[i5]);
                appendable.append(this.f14643c[i5 | 256]);
            }
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding
        BaseEncoding k(Alphabet alphabet, Character ch) {
            return new Base16Encoding(alphabet);
        }

        private Base16Encoding(Alphabet alphabet) {
            super(alphabet, null);
            this.f14643c = new char[512];
            Preconditions.checkArgument(alphabet.chars.length == 16);
            for (int i2 = 0; i2 < 256; i2++) {
                this.f14643c[i2] = alphabet.d(i2 >>> 4);
                this.f14643c[i2 | 256] = alphabet.d(i2 & 15);
            }
        }
    }

    private static final class Base64Encoding extends StandardBaseEncoding {
        Base64Encoding(String str, String str2, Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        int b(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            CharSequence charSequenceI = i(charSequence);
            if (!this.f14644a.f(charSequenceI.length())) {
                throw new DecodingException("Invalid input length " + charSequenceI.length());
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < charSequenceI.length()) {
                int i4 = i2 + 2;
                int iC = (this.f14644a.c(charSequenceI.charAt(i2)) << 18) | (this.f14644a.c(charSequenceI.charAt(i2 + 1)) << 12);
                int i5 = i3 + 1;
                bArr[i3] = (byte) (iC >>> 16);
                if (i4 < charSequenceI.length()) {
                    int i6 = i2 + 3;
                    int iC2 = iC | (this.f14644a.c(charSequenceI.charAt(i4)) << 6);
                    int i7 = i3 + 2;
                    bArr[i5] = (byte) ((iC2 >>> 8) & 255);
                    if (i6 < charSequenceI.length()) {
                        i2 += 4;
                        i3 += 3;
                        bArr[i7] = (byte) ((iC2 | this.f14644a.c(charSequenceI.charAt(i6))) & 255);
                    } else {
                        i3 = i7;
                        i2 = i6;
                    }
                } else {
                    i3 = i5;
                    i2 = i4;
                }
            }
            return i3;
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        void c(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            Preconditions.checkNotNull(appendable);
            int i4 = i2 + i3;
            Preconditions.checkPositionIndexes(i2, i4, bArr.length);
            while (i3 >= 3) {
                int i5 = i2 + 2;
                int i6 = ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2] & 255) << 16);
                i2 += 3;
                int i7 = i6 | (bArr[i5] & 255);
                appendable.append(this.f14644a.d(i7 >>> 18));
                appendable.append(this.f14644a.d((i7 >>> 12) & 63));
                appendable.append(this.f14644a.d((i7 >>> 6) & 63));
                appendable.append(this.f14644a.d(i7 & 63));
                i3 -= 3;
            }
            if (i2 < i4) {
                j(appendable, bArr, i2, i4 - i2);
            }
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding
        BaseEncoding k(Alphabet alphabet, Character ch) {
            return new Base64Encoding(alphabet, ch);
        }

        private Base64Encoding(Alphabet alphabet, @CheckForNull Character ch) {
            super(alphabet, ch);
            Preconditions.checkArgument(alphabet.chars.length == 64);
        }
    }

    public static final class DecodingException extends IOException {
        DecodingException(String str) {
            super(str);
        }
    }

    static final class SeparatedBaseEncoding extends BaseEncoding {
        private final int afterEveryChars;
        private final BaseEncoding delegate;
        private final String separator;

        SeparatedBaseEncoding(BaseEncoding baseEncoding, String str, int i2) {
            this.delegate = (BaseEncoding) Preconditions.checkNotNull(baseEncoding);
            this.separator = (String) Preconditions.checkNotNull(str);
            this.afterEveryChars = i2;
            Preconditions.checkArgument(i2 > 0, "Cannot add a separator after every %s chars", i2);
        }

        @Override // com.google.common.io.BaseEncoding
        int b(byte[] bArr, CharSequence charSequence) {
            StringBuilder sb = new StringBuilder(charSequence.length());
            for (int i2 = 0; i2 < charSequence.length(); i2++) {
                char cCharAt = charSequence.charAt(i2);
                if (this.separator.indexOf(cCharAt) < 0) {
                    sb.append(cCharAt);
                }
            }
            return this.delegate.b(bArr, sb);
        }

        @Override // com.google.common.io.BaseEncoding
        void c(Appendable appendable, byte[] bArr, int i2, int i3) {
            this.delegate.c(BaseEncoding.g(appendable, this.separator, this.afterEveryChars), bArr, i2, i3);
        }

        @Override // com.google.common.io.BaseEncoding
        public boolean canDecode(CharSequence charSequence) {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < charSequence.length(); i2++) {
                char cCharAt = charSequence.charAt(i2);
                if (this.separator.indexOf(cCharAt) < 0) {
                    sb.append(cCharAt);
                }
            }
            return this.delegate.canDecode(sb);
        }

        @Override // com.google.common.io.BaseEncoding
        @J2ktIncompatible
        @GwtIncompatible
        public InputStream decodingStream(Reader reader) {
            return this.delegate.decodingStream(BaseEncoding.d(reader, this.separator));
        }

        @Override // com.google.common.io.BaseEncoding
        int e(int i2) {
            return this.delegate.e(i2);
        }

        @Override // com.google.common.io.BaseEncoding
        @J2ktIncompatible
        @GwtIncompatible
        public OutputStream encodingStream(Writer writer) {
            return this.delegate.encodingStream(BaseEncoding.h(writer, this.separator, this.afterEveryChars));
        }

        @Override // com.google.common.io.BaseEncoding
        int f(int i2) {
            int iF = this.delegate.f(i2);
            return iF + (this.separator.length() * IntMath.divide(Math.max(0, iF - 1), this.afterEveryChars, RoundingMode.FLOOR));
        }

        @Override // com.google.common.io.BaseEncoding
        CharSequence i(CharSequence charSequence) {
            return this.delegate.i(charSequence);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding ignoreCase() {
            return this.delegate.ignoreCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding lowerCase() {
            return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding omitPadding() {
            return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
        }

        public String toString() {
            return this.delegate + ".withSeparator(\"" + this.separator + "\", " + this.afterEveryChars + ")";
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding upperCase() {
            return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withPadChar(char c2) {
            return this.delegate.withPadChar(c2).withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withSeparator(String str, int i2) {
            throw new UnsupportedOperationException("Already have a separator");
        }
    }

    private static class StandardBaseEncoding extends BaseEncoding {

        /* renamed from: a, reason: collision with root package name */
        final Alphabet f14644a;

        /* renamed from: b, reason: collision with root package name */
        final Character f14645b;

        @CheckForNull
        @LazyInit
        private volatile BaseEncoding ignoreCase;

        @CheckForNull
        @LazyInit
        private volatile BaseEncoding lowerCase;

        @CheckForNull
        @LazyInit
        private volatile BaseEncoding upperCase;

        StandardBaseEncoding(String str, String str2, Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        @Override // com.google.common.io.BaseEncoding
        int b(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Alphabet alphabet;
            Preconditions.checkNotNull(bArr);
            CharSequence charSequenceI = i(charSequence);
            if (!this.f14644a.f(charSequenceI.length())) {
                throw new DecodingException("Invalid input length " + charSequenceI.length());
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < charSequenceI.length()) {
                long jC = 0;
                int i4 = 0;
                int i5 = 0;
                while (true) {
                    alphabet = this.f14644a;
                    if (i4 >= alphabet.f14641c) {
                        break;
                    }
                    jC <<= alphabet.f14640b;
                    if (i2 + i4 < charSequenceI.length()) {
                        jC |= this.f14644a.c(charSequenceI.charAt(i5 + i2));
                        i5++;
                    }
                    i4++;
                }
                int i6 = alphabet.f14642d;
                int i7 = (i6 * 8) - (i5 * alphabet.f14640b);
                int i8 = (i6 - 1) * 8;
                while (i8 >= i7) {
                    bArr[i3] = (byte) ((jC >>> i8) & 255);
                    i8 -= 8;
                    i3++;
                }
                i2 += this.f14644a.f14641c;
            }
            return i3;
        }

        @Override // com.google.common.io.BaseEncoding
        void c(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
            int i4 = 0;
            while (i4 < i3) {
                j(appendable, bArr, i2 + i4, Math.min(this.f14644a.f14642d, i3 - i4));
                i4 += this.f14644a.f14642d;
            }
        }

        @Override // com.google.common.io.BaseEncoding
        public boolean canDecode(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            CharSequence charSequenceI = i(charSequence);
            if (!this.f14644a.f(charSequenceI.length())) {
                return false;
            }
            for (int i2 = 0; i2 < charSequenceI.length(); i2++) {
                if (!this.f14644a.b(charSequenceI.charAt(i2))) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.BaseEncoding
        @J2ktIncompatible
        @GwtIncompatible
        public InputStream decodingStream(final Reader reader) {
            Preconditions.checkNotNull(reader);
            return new InputStream(this) { // from class: com.google.common.io.BaseEncoding.StandardBaseEncoding.2

                /* renamed from: a, reason: collision with root package name */
                int f14651a = 0;

                /* renamed from: b, reason: collision with root package name */
                int f14652b = 0;

                /* renamed from: c, reason: collision with root package name */
                int f14653c = 0;

                /* renamed from: d, reason: collision with root package name */
                boolean f14654d = false;

                /* renamed from: f, reason: collision with root package name */
                final /* synthetic */ StandardBaseEncoding f14656f;

                {
                    this.f14656f = this;
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    reader.close();
                }

                /* JADX WARN: Code restructure failed: missing block: B:24:0x0074, code lost:
                
                    throw new com.google.common.io.BaseEncoding.DecodingException("Padding cannot start at index " + r4.f14653c);
                 */
                @Override // java.io.InputStream
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public int read() throws java.io.IOException {
                    /*
                        r4 = this;
                    L0:
                        java.io.Reader r0 = r2
                        int r0 = r0.read()
                        r1 = -1
                        if (r0 != r1) goto L34
                        boolean r0 = r4.f14654d
                        if (r0 != 0) goto L33
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r0 = r4.f14656f
                        com.google.common.io.BaseEncoding$Alphabet r0 = r0.f14644a
                        int r2 = r4.f14653c
                        boolean r0 = r0.f(r2)
                        if (r0 == 0) goto L1a
                        goto L33
                    L1a:
                        com.google.common.io.BaseEncoding$DecodingException r0 = new com.google.common.io.BaseEncoding$DecodingException
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "Invalid input length "
                        r1.append(r2)
                        int r2 = r4.f14653c
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        r0.<init>(r1)
                        throw r0
                    L33:
                        return r1
                    L34:
                        int r1 = r4.f14653c
                        r2 = 1
                        int r1 = r1 + r2
                        r4.f14653c = r1
                        char r0 = (char) r0
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r1 = r4.f14656f
                        java.lang.Character r1 = r1.f14645b
                        if (r1 == 0) goto L78
                        char r1 = r1.charValue()
                        if (r1 != r0) goto L78
                        boolean r0 = r4.f14654d
                        if (r0 != 0) goto L75
                        int r0 = r4.f14653c
                        if (r0 == r2) goto L5c
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r1 = r4.f14656f
                        com.google.common.io.BaseEncoding$Alphabet r1 = r1.f14644a
                        int r0 = r0 + (-1)
                        boolean r0 = r1.f(r0)
                        if (r0 == 0) goto L5c
                        goto L75
                    L5c:
                        com.google.common.io.BaseEncoding$DecodingException r0 = new com.google.common.io.BaseEncoding$DecodingException
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "Padding cannot start at index "
                        r1.append(r2)
                        int r2 = r4.f14653c
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        r0.<init>(r1)
                        throw r0
                    L75:
                        r4.f14654d = r2
                        goto L0
                    L78:
                        boolean r1 = r4.f14654d
                        if (r1 != 0) goto La4
                        int r1 = r4.f14651a
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r2 = r4.f14656f
                        com.google.common.io.BaseEncoding$Alphabet r2 = r2.f14644a
                        int r3 = r2.f14640b
                        int r1 = r1 << r3
                        r4.f14651a = r1
                        int r0 = r2.c(r0)
                        r0 = r0 | r1
                        r4.f14651a = r0
                        int r1 = r4.f14652b
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r2 = r4.f14656f
                        com.google.common.io.BaseEncoding$Alphabet r2 = r2.f14644a
                        int r2 = r2.f14640b
                        int r1 = r1 + r2
                        r4.f14652b = r1
                        r2 = 8
                        if (r1 < r2) goto L0
                        int r1 = r1 - r2
                        r4.f14652b = r1
                        int r0 = r0 >> r1
                        r0 = r0 & 255(0xff, float:3.57E-43)
                        return r0
                    La4:
                        com.google.common.io.BaseEncoding$DecodingException r1 = new com.google.common.io.BaseEncoding$DecodingException
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        java.lang.String r3 = "Expected padding character but found '"
                        r2.append(r3)
                        r2.append(r0)
                        java.lang.String r0 = "' at index "
                        r2.append(r0)
                        int r0 = r4.f14653c
                        r2.append(r0)
                        java.lang.String r0 = r2.toString()
                        r1.<init>(r0)
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.BaseEncoding.StandardBaseEncoding.AnonymousClass2.read():int");
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr, int i2, int i3) throws IOException {
                    int i4 = i3 + i2;
                    Preconditions.checkPositionIndexes(i2, i4, bArr.length);
                    int i5 = i2;
                    while (i5 < i4) {
                        int i6 = read();
                        if (i6 == -1) {
                            int i7 = i5 - i2;
                            if (i7 == 0) {
                                return -1;
                            }
                            return i7;
                        }
                        bArr[i5] = (byte) i6;
                        i5++;
                    }
                    return i5 - i2;
                }
            };
        }

        @Override // com.google.common.io.BaseEncoding
        int e(int i2) {
            return (int) (((this.f14644a.f14640b * i2) + 7) / 8);
        }

        @Override // com.google.common.io.BaseEncoding
        @J2ktIncompatible
        @GwtIncompatible
        public OutputStream encodingStream(final Writer writer) {
            Preconditions.checkNotNull(writer);
            return new OutputStream(this) { // from class: com.google.common.io.BaseEncoding.StandardBaseEncoding.1

                /* renamed from: a, reason: collision with root package name */
                int f14646a = 0;

                /* renamed from: b, reason: collision with root package name */
                int f14647b = 0;

                /* renamed from: c, reason: collision with root package name */
                int f14648c = 0;

                /* renamed from: e, reason: collision with root package name */
                final /* synthetic */ StandardBaseEncoding f14650e;

                {
                    this.f14650e = this;
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    int i2 = this.f14647b;
                    if (i2 > 0) {
                        int i3 = this.f14646a;
                        Alphabet alphabet = this.f14650e.f14644a;
                        writer.write(alphabet.d((i3 << (alphabet.f14640b - i2)) & alphabet.f14639a));
                        this.f14648c++;
                        if (this.f14650e.f14645b != null) {
                            while (true) {
                                int i4 = this.f14648c;
                                StandardBaseEncoding standardBaseEncoding = this.f14650e;
                                if (i4 % standardBaseEncoding.f14644a.f14641c == 0) {
                                    break;
                                }
                                writer.write(standardBaseEncoding.f14645b.charValue());
                                this.f14648c++;
                            }
                        }
                    }
                    writer.close();
                }

                @Override // java.io.OutputStream, java.io.Flushable
                public void flush() throws IOException {
                    writer.flush();
                }

                @Override // java.io.OutputStream
                public void write(int i2) throws IOException {
                    this.f14646a = (i2 & 255) | (this.f14646a << 8);
                    this.f14647b += 8;
                    while (true) {
                        int i3 = this.f14647b;
                        Alphabet alphabet = this.f14650e.f14644a;
                        int i4 = alphabet.f14640b;
                        if (i3 < i4) {
                            return;
                        }
                        writer.write(alphabet.d((this.f14646a >> (i3 - i4)) & alphabet.f14639a));
                        this.f14648c++;
                        this.f14647b -= this.f14650e.f14644a.f14640b;
                    }
                }
            };
        }

        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof StandardBaseEncoding)) {
                return false;
            }
            StandardBaseEncoding standardBaseEncoding = (StandardBaseEncoding) obj;
            return this.f14644a.equals(standardBaseEncoding.f14644a) && Objects.equals(this.f14645b, standardBaseEncoding.f14645b);
        }

        @Override // com.google.common.io.BaseEncoding
        int f(int i2) {
            Alphabet alphabet = this.f14644a;
            return alphabet.f14641c * IntMath.divide(i2, alphabet.f14642d, RoundingMode.CEILING);
        }

        public int hashCode() {
            return this.f14644a.hashCode() ^ Objects.hashCode(this.f14645b);
        }

        @Override // com.google.common.io.BaseEncoding
        CharSequence i(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            Character ch = this.f14645b;
            if (ch == null) {
                return charSequence;
            }
            char cCharValue = ch.charValue();
            int length = charSequence.length() - 1;
            while (length >= 0 && charSequence.charAt(length) == cCharValue) {
                length--;
            }
            return charSequence.subSequence(0, length + 1);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding ignoreCase() {
            BaseEncoding baseEncodingK = this.ignoreCase;
            if (baseEncodingK == null) {
                Alphabet alphabetE = this.f14644a.e();
                baseEncodingK = alphabetE == this.f14644a ? this : k(alphabetE, this.f14645b);
                this.ignoreCase = baseEncodingK;
            }
            return baseEncodingK;
        }

        void j(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
            int i4 = 0;
            Preconditions.checkArgument(i3 <= this.f14644a.f14642d);
            long j2 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                j2 = (j2 | (bArr[i2 + i5] & 255)) << 8;
            }
            int i6 = ((i3 + 1) * 8) - this.f14644a.f14640b;
            while (i4 < i3 * 8) {
                Alphabet alphabet = this.f14644a;
                appendable.append(alphabet.d(((int) (j2 >>> (i6 - i4))) & alphabet.f14639a));
                i4 += this.f14644a.f14640b;
            }
            if (this.f14645b != null) {
                while (i4 < this.f14644a.f14642d * 8) {
                    appendable.append(this.f14645b.charValue());
                    i4 += this.f14644a.f14640b;
                }
            }
        }

        BaseEncoding k(Alphabet alphabet, Character ch) {
            return new StandardBaseEncoding(alphabet, ch);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding lowerCase() {
            BaseEncoding baseEncodingK = this.lowerCase;
            if (baseEncodingK == null) {
                Alphabet alphabetG = this.f14644a.g();
                baseEncodingK = alphabetG == this.f14644a ? this : k(alphabetG, this.f14645b);
                this.lowerCase = baseEncodingK;
            }
            return baseEncodingK;
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding omitPadding() {
            return this.f14645b == null ? this : k(this.f14644a, null);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("BaseEncoding.");
            sb.append(this.f14644a);
            if (8 % this.f14644a.f14640b != 0) {
                if (this.f14645b == null) {
                    sb.append(".omitPadding()");
                } else {
                    sb.append(".withPadChar('");
                    sb.append(this.f14645b);
                    sb.append("')");
                }
            }
            return sb.toString();
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding upperCase() {
            BaseEncoding baseEncodingK = this.upperCase;
            if (baseEncodingK == null) {
                Alphabet alphabetH = this.f14644a.h();
                baseEncodingK = alphabetH == this.f14644a ? this : k(alphabetH, this.f14645b);
                this.upperCase = baseEncodingK;
            }
            return baseEncodingK;
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withPadChar(char c2) {
            Character ch;
            return (8 % this.f14644a.f14640b == 0 || ((ch = this.f14645b) != null && ch.charValue() == c2)) ? this : k(this.f14644a, Character.valueOf(c2));
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withSeparator(String str, int i2) {
            for (int i3 = 0; i3 < str.length(); i3++) {
                Preconditions.checkArgument(!this.f14644a.matches(str.charAt(i3)), "Separator (%s) cannot contain alphabet characters", str);
            }
            Character ch = this.f14645b;
            if (ch != null) {
                Preconditions.checkArgument(str.indexOf(ch.charValue()) < 0, "Separator (%s) cannot contain padding character", str);
            }
            return new SeparatedBaseEncoding(this, str, i2);
        }

        StandardBaseEncoding(Alphabet alphabet, Character ch) {
            this.f14644a = (Alphabet) Preconditions.checkNotNull(alphabet);
            Preconditions.checkArgument(ch == null || !alphabet.matches(ch.charValue()), "Padding character %s was already in alphabet", ch);
            this.f14645b = ch;
        }
    }

    static {
        Character chValueOf = Character.valueOf(com.alipay.sdk.m.n.a.f9565h);
        BASE64 = new Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", chValueOf);
        BASE64_URL = new Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", chValueOf);
        BASE32 = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", chValueOf);
        BASE32_HEX = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", chValueOf);
        BASE16 = new Base16Encoding("base16()", "0123456789ABCDEF");
    }

    BaseEncoding() {
    }

    public static BaseEncoding base16() {
        return BASE16;
    }

    public static BaseEncoding base32() {
        return BASE32;
    }

    public static BaseEncoding base32Hex() {
        return BASE32_HEX;
    }

    public static BaseEncoding base64() {
        return BASE64;
    }

    public static BaseEncoding base64Url() {
        return BASE64_URL;
    }

    static Reader d(final Reader reader, final String str) {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(str);
        return new Reader() { // from class: com.google.common.io.BaseEncoding.3
            @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                reader.close();
            }

            @Override // java.io.Reader
            public int read() throws IOException {
                int i2;
                do {
                    i2 = reader.read();
                    if (i2 == -1) {
                        break;
                    }
                } while (str.indexOf((char) i2) >= 0);
                return i2;
            }

            @Override // java.io.Reader
            public int read(char[] cArr, int i2, int i3) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static byte[] extract(byte[] bArr, int i2) {
        if (i2 == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, 0, bArr2, 0, i2);
        return bArr2;
    }

    static Appendable g(Appendable appendable, String str, int i2) {
        Preconditions.checkNotNull(appendable);
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(i2 > 0);
        return new Appendable(i2, appendable, str) { // from class: com.google.common.io.BaseEncoding.4

            /* renamed from: a, reason: collision with root package name */
            int f14633a;

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ int f14634b;

            /* renamed from: c, reason: collision with root package name */
            final /* synthetic */ Appendable f14635c;

            /* renamed from: d, reason: collision with root package name */
            final /* synthetic */ String f14636d;

            {
                this.f14634b = i2;
                this.f14635c = appendable;
                this.f14636d = str;
                this.f14633a = i2;
            }

            @Override // java.lang.Appendable
            public Appendable append(char c2) throws IOException {
                if (this.f14633a == 0) {
                    this.f14635c.append(this.f14636d);
                    this.f14633a = this.f14634b;
                }
                this.f14635c.append(c2);
                this.f14633a--;
                return this;
            }

            @Override // java.lang.Appendable
            public Appendable append(@CheckForNull CharSequence charSequence, int i3, int i4) {
                throw new UnsupportedOperationException();
            }

            @Override // java.lang.Appendable
            public Appendable append(@CheckForNull CharSequence charSequence) {
                throw new UnsupportedOperationException();
            }
        };
    }

    static Writer h(final Writer writer, String str, int i2) {
        final Appendable appendableG = g(writer, str, i2);
        return new Writer() { // from class: com.google.common.io.BaseEncoding.5
            @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                writer.close();
            }

            @Override // java.io.Writer, java.io.Flushable
            public void flush() throws IOException {
                writer.flush();
            }

            @Override // java.io.Writer
            public void write(int i3) throws IOException {
                appendableG.append((char) i3);
            }

            @Override // java.io.Writer
            public void write(char[] cArr, int i3, int i4) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    final byte[] a(CharSequence charSequence) {
        CharSequence charSequenceI = i(charSequence);
        byte[] bArr = new byte[e(charSequenceI.length())];
        return extract(bArr, b(bArr, charSequenceI));
    }

    abstract int b(byte[] bArr, CharSequence charSequence);

    abstract void c(Appendable appendable, byte[] bArr, int i2, int i3);

    public abstract boolean canDecode(CharSequence charSequence);

    public final byte[] decode(CharSequence charSequence) {
        try {
            return a(charSequence);
        } catch (DecodingException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    @J2ktIncompatible
    @GwtIncompatible
    public final ByteSource decodingSource(final CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource(this) { // from class: com.google.common.io.BaseEncoding.2

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ BaseEncoding f14630b;

            {
                this.f14630b = this;
            }

            @Override // com.google.common.io.ByteSource
            public InputStream openStream() throws IOException {
                return this.f14630b.decodingStream(charSource.openStream());
            }
        };
    }

    @J2ktIncompatible
    @GwtIncompatible
    public abstract InputStream decodingStream(Reader reader);

    abstract int e(int i2);

    public String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    @J2ktIncompatible
    @GwtIncompatible
    public final ByteSink encodingSink(final CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink(this) { // from class: com.google.common.io.BaseEncoding.1

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ BaseEncoding f14628b;

            {
                this.f14628b = this;
            }

            @Override // com.google.common.io.ByteSink
            public OutputStream openStream() throws IOException {
                return this.f14628b.encodingStream(charSink.openStream());
            }
        };
    }

    @J2ktIncompatible
    @GwtIncompatible
    public abstract OutputStream encodingStream(Writer writer);

    abstract int f(int i2);

    CharSequence i(CharSequence charSequence) {
        return (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    public abstract BaseEncoding ignoreCase();

    public abstract BaseEncoding lowerCase();

    public abstract BaseEncoding omitPadding();

    public abstract BaseEncoding upperCase();

    public abstract BaseEncoding withPadChar(char c2);

    public abstract BaseEncoding withSeparator(String str, int i2);

    public final String encode(byte[] bArr, int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
        StringBuilder sb = new StringBuilder(f(i3));
        try {
            c(sb, bArr, i2, i3);
            return sb.toString();
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }
}
