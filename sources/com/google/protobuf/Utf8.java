package com.google.protobuf;

import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.mozilla.javascript.typedarrays.Conversions;

/* loaded from: classes2.dex */
final class Utf8 {
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    private static final Processor processor;

    private static class DecodeUtil {
        private DecodeUtil() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleFourBytes(byte b2, byte b3, byte b4, byte b5, char[] cArr, int i2) throws InvalidProtocolBufferException {
            if (isNotTrailingByte(b3) || (((b2 << Ascii.FS) + (b3 + 112)) >> 30) != 0 || isNotTrailingByte(b4) || isNotTrailingByte(b5)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            int iTrailingByteValue = ((b2 & 7) << 18) | (trailingByteValue(b3) << 12) | (trailingByteValue(b4) << 6) | trailingByteValue(b5);
            cArr[i2] = highSurrogate(iTrailingByteValue);
            cArr[i2 + 1] = lowSurrogate(iTrailingByteValue);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleOneByte(byte b2, char[] cArr, int i2) {
            cArr[i2] = (char) b2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleThreeBytes(byte b2, byte b3, byte b4, char[] cArr, int i2) throws InvalidProtocolBufferException {
            if (isNotTrailingByte(b3) || ((b2 == -32 && b3 < -96) || ((b2 == -19 && b3 >= -96) || isNotTrailingByte(b4)))) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            cArr[i2] = (char) (((b2 & 15) << 12) | (trailingByteValue(b3) << 6) | trailingByteValue(b4));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleTwoBytes(byte b2, byte b3, char[] cArr, int i2) throws InvalidProtocolBufferException {
            if (b2 < -62 || isNotTrailingByte(b3)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            cArr[i2] = (char) (((b2 & Ascii.US) << 6) | trailingByteValue(b3));
        }

        private static char highSurrogate(int i2) {
            return (char) ((i2 >>> 10) + 55232);
        }

        private static boolean isNotTrailingByte(byte b2) {
            return b2 > -65;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isOneByte(byte b2) {
            return b2 >= 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isThreeBytes(byte b2) {
            return b2 < -16;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isTwoBytes(byte b2) {
            return b2 < -32;
        }

        private static char lowSurrogate(int i2) {
            return (char) ((i2 & 1023) + 56320);
        }

        private static int trailingByteValue(byte b2) {
            return b2 & 63;
        }
    }

    static abstract class Processor {
        Processor() {
        }

        private static int partialIsValidUtf8(ByteBuffer byteBuffer, int i2, int i3) {
            int iEstimateConsecutiveAscii = i2 + Utf8.estimateConsecutiveAscii(byteBuffer, i2, i3);
            while (iEstimateConsecutiveAscii < i3) {
                int i4 = iEstimateConsecutiveAscii + 1;
                byte b2 = byteBuffer.get(iEstimateConsecutiveAscii);
                if (b2 >= 0) {
                    iEstimateConsecutiveAscii = i4;
                } else if (b2 < -32) {
                    if (i4 >= i3) {
                        return b2;
                    }
                    if (b2 < -62 || byteBuffer.get(i4) > -65) {
                        return -1;
                    }
                    iEstimateConsecutiveAscii += 2;
                } else {
                    if (b2 >= -16) {
                        if (i4 >= i3 - 2) {
                            return Utf8.incompleteStateFor(byteBuffer, b2, i4, i3 - i4);
                        }
                        int i5 = iEstimateConsecutiveAscii + 2;
                        byte b3 = byteBuffer.get(i4);
                        if (b3 <= -65 && (((b2 << Ascii.FS) + (b3 + 112)) >> 30) == 0) {
                            int i6 = iEstimateConsecutiveAscii + 3;
                            if (byteBuffer.get(i5) <= -65) {
                                iEstimateConsecutiveAscii += 4;
                                if (byteBuffer.get(i6) > -65) {
                                }
                            }
                        }
                        return -1;
                    }
                    if (i4 >= i3 - 1) {
                        return Utf8.incompleteStateFor(byteBuffer, b2, i4, i3 - i4);
                    }
                    int i7 = iEstimateConsecutiveAscii + 2;
                    byte b4 = byteBuffer.get(i4);
                    if (b4 > -65 || ((b2 == -32 && b4 < -96) || ((b2 == -19 && b4 >= -96) || byteBuffer.get(i7) > -65))) {
                        return -1;
                    }
                    iEstimateConsecutiveAscii += 3;
                }
            }
            return 0;
        }

        final String a(ByteBuffer byteBuffer, int i2, int i3) {
            if (byteBuffer.hasArray()) {
                return b(byteBuffer.array(), byteBuffer.arrayOffset() + i2, i3);
            }
            return byteBuffer.isDirect() ? d(byteBuffer, i2, i3) : c(byteBuffer, i2, i3);
        }

        abstract String b(byte[] bArr, int i2, int i3);

        final String c(ByteBuffer byteBuffer, int i2, int i3) throws InvalidProtocolBufferException {
            if ((i2 | i3 | ((byteBuffer.limit() - i2) - i3)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            int i4 = i2 + i3;
            char[] cArr = new char[i3];
            int i5 = 0;
            while (i2 < i4) {
                byte b2 = byteBuffer.get(i2);
                if (!DecodeUtil.isOneByte(b2)) {
                    break;
                }
                i2++;
                DecodeUtil.handleOneByte(b2, cArr, i5);
                i5++;
            }
            int i6 = i5;
            while (i2 < i4) {
                int i7 = i2 + 1;
                byte b3 = byteBuffer.get(i2);
                if (DecodeUtil.isOneByte(b3)) {
                    int i8 = i6 + 1;
                    DecodeUtil.handleOneByte(b3, cArr, i6);
                    while (i7 < i4) {
                        byte b4 = byteBuffer.get(i7);
                        if (!DecodeUtil.isOneByte(b4)) {
                            break;
                        }
                        i7++;
                        DecodeUtil.handleOneByte(b4, cArr, i8);
                        i8++;
                    }
                    i6 = i8;
                    i2 = i7;
                } else if (DecodeUtil.isTwoBytes(b3)) {
                    if (i7 >= i4) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    i2 += 2;
                    DecodeUtil.handleTwoBytes(b3, byteBuffer.get(i7), cArr, i6);
                    i6++;
                } else if (DecodeUtil.isThreeBytes(b3)) {
                    if (i7 >= i4 - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i9 = i2 + 2;
                    i2 += 3;
                    DecodeUtil.handleThreeBytes(b3, byteBuffer.get(i7), byteBuffer.get(i9), cArr, i6);
                    i6++;
                } else {
                    if (i7 >= i4 - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    byte b5 = byteBuffer.get(i7);
                    int i10 = i2 + 3;
                    byte b6 = byteBuffer.get(i2 + 2);
                    i2 += 4;
                    DecodeUtil.handleFourBytes(b3, b5, b6, byteBuffer.get(i10), cArr, i6);
                    i6 += 2;
                }
            }
            return new String(cArr, 0, i6);
        }

        abstract String d(ByteBuffer byteBuffer, int i2, int i3);

        abstract int e(CharSequence charSequence, byte[] bArr, int i2, int i3);

        final void f(CharSequence charSequence, ByteBuffer byteBuffer) {
            if (byteBuffer.hasArray()) {
                int iArrayOffset = byteBuffer.arrayOffset();
                Java8Compatibility.e(byteBuffer, Utf8.i(charSequence, byteBuffer.array(), byteBuffer.position() + iArrayOffset, byteBuffer.remaining()) - iArrayOffset);
            } else if (byteBuffer.isDirect()) {
                h(charSequence, byteBuffer);
            } else {
                g(charSequence, byteBuffer);
            }
        }

        final void g(CharSequence charSequence, ByteBuffer byteBuffer) {
            int length = charSequence.length();
            int iPosition = byteBuffer.position();
            int i2 = 0;
            while (i2 < length) {
                try {
                    char cCharAt = charSequence.charAt(i2);
                    if (cCharAt >= 128) {
                        break;
                    }
                    byteBuffer.put(iPosition + i2, (byte) cCharAt);
                    i2++;
                } catch (IndexOutOfBoundsException unused) {
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i2) + " at index " + (byteBuffer.position() + Math.max(i2, (iPosition - byteBuffer.position()) + 1)));
                }
            }
            if (i2 == length) {
                Java8Compatibility.e(byteBuffer, iPosition + i2);
                return;
            }
            iPosition += i2;
            while (i2 < length) {
                char cCharAt2 = charSequence.charAt(i2);
                if (cCharAt2 < 128) {
                    byteBuffer.put(iPosition, (byte) cCharAt2);
                } else if (cCharAt2 < 2048) {
                    int i3 = iPosition + 1;
                    try {
                        byteBuffer.put(iPosition, (byte) ((cCharAt2 >>> 6) | 192));
                        byteBuffer.put(i3, (byte) ((cCharAt2 & '?') | 128));
                        iPosition = i3;
                    } catch (IndexOutOfBoundsException unused2) {
                        iPosition = i3;
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i2) + " at index " + (byteBuffer.position() + Math.max(i2, (iPosition - byteBuffer.position()) + 1)));
                    }
                } else {
                    if (cCharAt2 >= 55296 && 57343 >= cCharAt2) {
                        int i4 = i2 + 1;
                        if (i4 != length) {
                            try {
                                char cCharAt3 = charSequence.charAt(i4);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    int i5 = iPosition + 1;
                                    try {
                                        byteBuffer.put(iPosition, (byte) ((codePoint >>> 18) | 240));
                                        int i6 = iPosition + 2;
                                        try {
                                            byteBuffer.put(i5, (byte) (((codePoint >>> 12) & 63) | 128));
                                            iPosition += 3;
                                            byteBuffer.put(i6, (byte) (((codePoint >>> 6) & 63) | 128));
                                            byteBuffer.put(iPosition, (byte) ((codePoint & 63) | 128));
                                            i2 = i4;
                                        } catch (IndexOutOfBoundsException unused3) {
                                            i2 = i4;
                                            iPosition = i6;
                                            throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i2) + " at index " + (byteBuffer.position() + Math.max(i2, (iPosition - byteBuffer.position()) + 1)));
                                        }
                                    } catch (IndexOutOfBoundsException unused4) {
                                        iPosition = i5;
                                        i2 = i4;
                                        throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i2) + " at index " + (byteBuffer.position() + Math.max(i2, (iPosition - byteBuffer.position()) + 1)));
                                    }
                                } else {
                                    i2 = i4;
                                }
                            } catch (IndexOutOfBoundsException unused5) {
                            }
                        }
                        throw new UnpairedSurrogateException(i2, length);
                    }
                    int i7 = iPosition + 1;
                    byteBuffer.put(iPosition, (byte) ((cCharAt2 >>> '\f') | 224));
                    iPosition += 2;
                    byteBuffer.put(i7, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                    byteBuffer.put(iPosition, (byte) ((cCharAt2 & '?') | 128));
                }
                i2++;
                iPosition++;
            }
            Java8Compatibility.e(byteBuffer, iPosition);
        }

        abstract void h(CharSequence charSequence, ByteBuffer byteBuffer);

        final boolean i(ByteBuffer byteBuffer, int i2, int i3) {
            return k(0, byteBuffer, i2, i3) == 0;
        }

        final boolean j(byte[] bArr, int i2, int i3) {
            return l(0, bArr, i2, i3) == 0;
        }

        final int k(int i2, ByteBuffer byteBuffer, int i3, int i4) {
            if (!byteBuffer.hasArray()) {
                return byteBuffer.isDirect() ? n(i2, byteBuffer, i3, i4) : m(i2, byteBuffer, i3, i4);
            }
            int iArrayOffset = byteBuffer.arrayOffset();
            return l(i2, byteBuffer.array(), i3 + iArrayOffset, iArrayOffset + i4);
        }

        abstract int l(int i2, byte[] bArr, int i3, int i4);

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
        
            if (r8.get(r9) > (-65)) goto L13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x004c, code lost:
        
            if (r8.get(r9) > (-65)) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x008f, code lost:
        
            if (r8.get(r7) > (-65)) goto L53;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final int m(int r7, java.nio.ByteBuffer r8, int r9, int r10) {
            /*
                r6 = this;
                if (r7 == 0) goto L92
                if (r9 < r10) goto L5
                return r7
            L5:
                byte r0 = (byte) r7
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L1e
                r7 = -62
                if (r0 < r7) goto L1d
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r9 <= r3) goto L1a
                goto L1d
            L1a:
                r9 = r7
                goto L92
            L1d:
                return r2
            L1e:
                r4 = -16
                if (r0 >= r4) goto L4f
                int r7 = r7 >> 8
                int r7 = ~r7
                byte r7 = (byte) r7
                if (r7 != 0) goto L38
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r7 < r10) goto L35
                int r7 = com.google.protobuf.Utf8.a(r0, r9)
                return r7
            L35:
                r5 = r9
                r9 = r7
                r7 = r5
            L38:
                if (r7 > r3) goto L4e
                r4 = -96
                if (r0 != r1) goto L40
                if (r7 < r4) goto L4e
            L40:
                r1 = -19
                if (r0 != r1) goto L46
                if (r7 >= r4) goto L4e
            L46:
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r9 <= r3) goto L1a
            L4e:
                return r2
            L4f:
                int r1 = r7 >> 8
                int r1 = ~r1
                byte r1 = (byte) r1
                if (r1 != 0) goto L64
                int r7 = r9 + 1
                byte r1 = r8.get(r9)
                if (r7 < r10) goto L62
                int r7 = com.google.protobuf.Utf8.a(r0, r1)
                return r7
            L62:
                r9 = 0
                goto L6a
            L64:
                int r7 = r7 >> 16
                byte r7 = (byte) r7
                r5 = r9
                r9 = r7
                r7 = r5
            L6a:
                if (r9 != 0) goto L7c
                int r9 = r7 + 1
                byte r7 = r8.get(r7)
                if (r9 < r10) goto L79
                int r7 = com.google.protobuf.Utf8.b(r0, r1, r7)
                return r7
            L79:
                r5 = r9
                r9 = r7
                r7 = r5
            L7c:
                if (r1 > r3) goto L91
                int r0 = r0 << 28
                int r1 = r1 + 112
                int r0 = r0 + r1
                int r0 = r0 >> 30
                if (r0 != 0) goto L91
                if (r9 > r3) goto L91
                int r9 = r7 + 1
                byte r7 = r8.get(r7)
                if (r7 <= r3) goto L92
            L91:
                return r2
            L92:
                int r7 = partialIsValidUtf8(r8, r9, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.Processor.m(int, java.nio.ByteBuffer, int, int):int");
        }

        abstract int n(int i2, ByteBuffer byteBuffer, int i3, int i4);
    }

    static final class SafeProcessor extends Processor {
        SafeProcessor() {
        }

        private static int partialIsValidUtf8(byte[] bArr, int i2, int i3) {
            while (i2 < i3 && bArr[i2] >= 0) {
                i2++;
            }
            if (i2 >= i3) {
                return 0;
            }
            return partialIsValidUtf8NonAscii(bArr, i2, i3);
        }

        private static int partialIsValidUtf8NonAscii(byte[] bArr, int i2, int i3) {
            while (i2 < i3) {
                int i4 = i2 + 1;
                byte b2 = bArr[i2];
                if (b2 < 0) {
                    if (b2 < -32) {
                        if (i4 >= i3) {
                            return b2;
                        }
                        if (b2 >= -62) {
                            i2 += 2;
                            if (bArr[i4] > -65) {
                            }
                        }
                        return -1;
                    }
                    if (b2 >= -16) {
                        if (i4 >= i3 - 2) {
                            return Utf8.incompleteStateFor(bArr, i4, i3);
                        }
                        int i5 = i2 + 2;
                        byte b3 = bArr[i4];
                        if (b3 <= -65 && (((b2 << Ascii.FS) + (b3 + 112)) >> 30) == 0) {
                            int i6 = i2 + 3;
                            if (bArr[i5] <= -65) {
                                i2 += 4;
                                if (bArr[i6] > -65) {
                                }
                            }
                        }
                        return -1;
                    }
                    if (i4 >= i3 - 1) {
                        return Utf8.incompleteStateFor(bArr, i4, i3);
                    }
                    int i7 = i2 + 2;
                    byte b4 = bArr[i4];
                    if (b4 <= -65 && ((b2 != -32 || b4 >= -96) && (b2 != -19 || b4 < -96))) {
                        i2 += 3;
                        if (bArr[i7] > -65) {
                        }
                    }
                    return -1;
                }
                i2 = i4;
            }
            return 0;
        }

        @Override // com.google.protobuf.Utf8.Processor
        String b(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
            if ((i2 | i3 | ((bArr.length - i2) - i3)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            int i4 = i2 + i3;
            char[] cArr = new char[i3];
            int i5 = 0;
            while (i2 < i4) {
                byte b2 = bArr[i2];
                if (!DecodeUtil.isOneByte(b2)) {
                    break;
                }
                i2++;
                DecodeUtil.handleOneByte(b2, cArr, i5);
                i5++;
            }
            int i6 = i5;
            while (i2 < i4) {
                int i7 = i2 + 1;
                byte b3 = bArr[i2];
                if (DecodeUtil.isOneByte(b3)) {
                    int i8 = i6 + 1;
                    DecodeUtil.handleOneByte(b3, cArr, i6);
                    while (i7 < i4) {
                        byte b4 = bArr[i7];
                        if (!DecodeUtil.isOneByte(b4)) {
                            break;
                        }
                        i7++;
                        DecodeUtil.handleOneByte(b4, cArr, i8);
                        i8++;
                    }
                    i6 = i8;
                    i2 = i7;
                } else if (DecodeUtil.isTwoBytes(b3)) {
                    if (i7 >= i4) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    i2 += 2;
                    DecodeUtil.handleTwoBytes(b3, bArr[i7], cArr, i6);
                    i6++;
                } else if (DecodeUtil.isThreeBytes(b3)) {
                    if (i7 >= i4 - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i9 = i2 + 2;
                    i2 += 3;
                    DecodeUtil.handleThreeBytes(b3, bArr[i7], bArr[i9], cArr, i6);
                    i6++;
                } else {
                    if (i7 >= i4 - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    byte b5 = bArr[i7];
                    int i10 = i2 + 3;
                    byte b6 = bArr[i2 + 2];
                    i2 += 4;
                    DecodeUtil.handleFourBytes(b3, b5, b6, bArr[i10], cArr, i6);
                    i6 += 2;
                }
            }
            return new String(cArr, 0, i6);
        }

        @Override // com.google.protobuf.Utf8.Processor
        String d(ByteBuffer byteBuffer, int i2, int i3) {
            return c(byteBuffer, i2, i3);
        }

        @Override // com.google.protobuf.Utf8.Processor
        int e(CharSequence charSequence, byte[] bArr, int i2, int i3) {
            int i4;
            int i5;
            char cCharAt;
            int length = charSequence.length();
            int i6 = i3 + i2;
            int i7 = 0;
            while (i7 < length && (i5 = i7 + i2) < i6 && (cCharAt = charSequence.charAt(i7)) < 128) {
                bArr[i5] = (byte) cCharAt;
                i7++;
            }
            if (i7 == length) {
                return i2 + length;
            }
            int i8 = i2 + i7;
            while (i7 < length) {
                char cCharAt2 = charSequence.charAt(i7);
                if (cCharAt2 < 128 && i8 < i6) {
                    bArr[i8] = (byte) cCharAt2;
                    i8++;
                } else if (cCharAt2 < 2048 && i8 <= i6 - 2) {
                    int i9 = i8 + 1;
                    bArr[i8] = (byte) ((cCharAt2 >>> 6) | 960);
                    i8 += 2;
                    bArr[i9] = (byte) ((cCharAt2 & '?') | 128);
                } else {
                    if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || i8 > i6 - 3) {
                        if (i8 > i6 - 4) {
                            if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i4 = i7 + 1) == charSequence.length() || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i4)))) {
                                throw new UnpairedSurrogateException(i7, length);
                            }
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + cCharAt2 + " at index " + i8);
                        }
                        int i10 = i7 + 1;
                        if (i10 != charSequence.length()) {
                            char cCharAt3 = charSequence.charAt(i10);
                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                bArr[i8] = (byte) ((codePoint >>> 18) | 240);
                                bArr[i8 + 1] = (byte) (((codePoint >>> 12) & 63) | 128);
                                int i11 = i8 + 3;
                                bArr[i8 + 2] = (byte) (((codePoint >>> 6) & 63) | 128);
                                i8 += 4;
                                bArr[i11] = (byte) ((codePoint & 63) | 128);
                                i7 = i10;
                            } else {
                                i7 = i10;
                            }
                        }
                        throw new UnpairedSurrogateException(i7 - 1, length);
                    }
                    bArr[i8] = (byte) ((cCharAt2 >>> '\f') | 480);
                    int i12 = i8 + 2;
                    bArr[i8 + 1] = (byte) (((cCharAt2 >>> 6) & 63) | 128);
                    i8 += 3;
                    bArr[i12] = (byte) ((cCharAt2 & '?') | 128);
                }
                i7++;
            }
            return i8;
        }

        @Override // com.google.protobuf.Utf8.Processor
        void h(CharSequence charSequence, ByteBuffer byteBuffer) {
            g(charSequence, byteBuffer);
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
        
            if (r8[r9] > (-65)) goto L13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0046, code lost:
        
            if (r8[r9] > (-65)) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x0083, code lost:
        
            if (r8[r7] > (-65)) goto L53;
         */
        @Override // com.google.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int l(int r7, byte[] r8, int r9, int r10) {
            /*
                r6 = this;
                if (r7 == 0) goto L86
                if (r9 < r10) goto L5
                return r7
            L5:
                byte r0 = (byte) r7
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L1c
                r7 = -62
                if (r0 < r7) goto L1b
                int r7 = r9 + 1
                r9 = r8[r9]
                if (r9 <= r3) goto L18
                goto L1b
            L18:
                r9 = r7
                goto L86
            L1b:
                return r2
            L1c:
                r4 = -16
                if (r0 >= r4) goto L49
                int r7 = r7 >> 8
                int r7 = ~r7
                byte r7 = (byte) r7
                if (r7 != 0) goto L34
                int r7 = r9 + 1
                r9 = r8[r9]
                if (r7 < r10) goto L31
                int r7 = com.google.protobuf.Utf8.a(r0, r9)
                return r7
            L31:
                r5 = r9
                r9 = r7
                r7 = r5
            L34:
                if (r7 > r3) goto L48
                r4 = -96
                if (r0 != r1) goto L3c
                if (r7 < r4) goto L48
            L3c:
                r1 = -19
                if (r0 != r1) goto L42
                if (r7 >= r4) goto L48
            L42:
                int r7 = r9 + 1
                r9 = r8[r9]
                if (r9 <= r3) goto L18
            L48:
                return r2
            L49:
                int r1 = r7 >> 8
                int r1 = ~r1
                byte r1 = (byte) r1
                if (r1 != 0) goto L5c
                int r7 = r9 + 1
                r1 = r8[r9]
                if (r7 < r10) goto L5a
                int r7 = com.google.protobuf.Utf8.a(r0, r1)
                return r7
            L5a:
                r9 = 0
                goto L62
            L5c:
                int r7 = r7 >> 16
                byte r7 = (byte) r7
                r5 = r9
                r9 = r7
                r7 = r5
            L62:
                if (r9 != 0) goto L72
                int r9 = r7 + 1
                r7 = r8[r7]
                if (r9 < r10) goto L6f
                int r7 = com.google.protobuf.Utf8.b(r0, r1, r7)
                return r7
            L6f:
                r5 = r9
                r9 = r7
                r7 = r5
            L72:
                if (r1 > r3) goto L85
                int r0 = r0 << 28
                int r1 = r1 + 112
                int r0 = r0 + r1
                int r0 = r0 >> 30
                if (r0 != 0) goto L85
                if (r9 > r3) goto L85
                int r9 = r7 + 1
                r7 = r8[r7]
                if (r7 <= r3) goto L86
            L85:
                return r2
            L86:
                int r7 = partialIsValidUtf8(r8, r9, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.SafeProcessor.l(int, byte[], int, int):int");
        }

        @Override // com.google.protobuf.Utf8.Processor
        int n(int i2, ByteBuffer byteBuffer, int i3, int i4) {
            return m(i2, byteBuffer, i3, i4);
        }
    }

    static class UnpairedSurrogateException extends IllegalArgumentException {
        UnpairedSurrogateException(int i2, int i3) {
            super("Unpaired surrogate at index " + i2 + " of " + i3);
        }
    }

    static {
        processor = (!UnsafeProcessor.o() || Android.b()) ? new SafeProcessor() : new UnsafeProcessor();
    }

    private Utf8() {
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt < 2048) {
                i3 += (127 - cCharAt) >>> 31;
            } else {
                i3 += 2;
                if (55296 <= cCharAt && cCharAt <= 57343) {
                    if (Character.codePointAt(charSequence, i2) < 65536) {
                        throw new UnpairedSurrogateException(i2, length);
                    }
                    i2++;
                }
            }
            i2++;
        }
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int estimateConsecutiveAscii(ByteBuffer byteBuffer, int i2, int i3) {
        int i4 = i3 - 7;
        int i5 = i2;
        while (i5 < i4 && (byteBuffer.getLong(i5) & ASCII_MASK_LONG) == 0) {
            i5 += 8;
        }
        return i5 - i2;
    }

    static String g(ByteBuffer byteBuffer, int i2, int i3) {
        return processor.a(byteBuffer, i2, i3);
    }

    static String h(byte[] bArr, int i2, int i3) {
        return processor.b(bArr, i2, i3);
    }

    static int i(CharSequence charSequence, byte[] bArr, int i2, int i3) {
        return processor.e(charSequence, bArr, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i2) {
        if (i2 > -12) {
            return -1;
        }
        return i2;
    }

    static void j(CharSequence charSequence, ByteBuffer byteBuffer) {
        processor.f(charSequence, byteBuffer);
    }

    static int k(CharSequence charSequence) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int iEncodedLengthGeneral = length;
        while (true) {
            if (i2 < length) {
                char cCharAt = charSequence.charAt(i2);
                if (cCharAt >= 2048) {
                    iEncodedLengthGeneral += encodedLengthGeneral(charSequence, i2);
                    break;
                }
                iEncodedLengthGeneral += (127 - cCharAt) >>> 31;
                i2++;
            } else {
                break;
            }
        }
        if (iEncodedLengthGeneral >= length) {
            return iEncodedLengthGeneral;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (iEncodedLengthGeneral + Conversions.THIRTYTWO_BIT));
    }

    static boolean l(ByteBuffer byteBuffer) {
        return processor.i(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
    }

    static boolean m(byte[] bArr) {
        return processor.j(bArr, 0, bArr.length);
    }

    static boolean n(byte[] bArr, int i2, int i3) {
        return processor.j(bArr, i2, i3);
    }

    static int o(int i2, ByteBuffer byteBuffer, int i3, int i4) {
        return processor.k(i2, byteBuffer, i3, i4);
    }

    static int p(int i2, byte[] bArr, int i3, int i4) {
        return processor.l(i2, bArr, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i2, int i3) {
        if (i2 > -12 || i3 > -65) {
            return -1;
        }
        return i2 ^ (i3 << 8);
    }

    static final class UnsafeProcessor extends Processor {
        UnsafeProcessor() {
        }

        static boolean o() {
            return UnsafeUtil.A() && UnsafeUtil.B();
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0039, code lost:
        
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0064, code lost:
        
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static int partialIsValidUtf8(byte[] r10, long r11, int r13) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r10, r11, r13)
                int r13 = r13 - r0
                long r0 = (long) r0
                long r11 = r11 + r0
            L7:
                r0 = 0
                r1 = r0
            L9:
                r2 = 1
                if (r13 <= 0) goto L1a
                long r4 = r11 + r2
                byte r1 = com.google.protobuf.UnsafeUtil.r(r10, r11)
                if (r1 < 0) goto L19
                int r13 = r13 + (-1)
                r11 = r4
                goto L9
            L19:
                r11 = r4
            L1a:
                if (r13 != 0) goto L1d
                return r0
            L1d:
                int r0 = r13 + (-1)
                r4 = -32
                r5 = -1
                r6 = -65
                if (r1 >= r4) goto L3a
                if (r0 != 0) goto L29
                return r1
            L29:
                int r13 = r13 + (-2)
                r0 = -62
                if (r1 < r0) goto L39
                long r2 = r2 + r11
                byte r11 = com.google.protobuf.UnsafeUtil.r(r10, r11)
                if (r11 <= r6) goto L37
                goto L39
            L37:
                r11 = r2
                goto L7
            L39:
                return r5
            L3a:
                r7 = -16
                r8 = 2
                if (r1 >= r7) goto L65
                r7 = 2
                if (r0 >= r7) goto L48
                int r10 = unsafeIncompleteStateFor(r10, r1, r11, r0)
                return r10
            L48:
                int r13 = r13 + (-3)
                long r2 = r2 + r11
                byte r0 = com.google.protobuf.UnsafeUtil.r(r10, r11)
                if (r0 > r6) goto L64
                r7 = -96
                if (r1 != r4) goto L57
                if (r0 < r7) goto L64
            L57:
                r4 = -19
                if (r1 != r4) goto L5d
                if (r0 >= r7) goto L64
            L5d:
                long r11 = r11 + r8
                byte r0 = com.google.protobuf.UnsafeUtil.r(r10, r2)
                if (r0 <= r6) goto L7
            L64:
                return r5
            L65:
                r4 = 3
                if (r0 >= r4) goto L6d
                int r10 = unsafeIncompleteStateFor(r10, r1, r11, r0)
                return r10
            L6d:
                int r13 = r13 + (-4)
                long r2 = r2 + r11
                byte r0 = com.google.protobuf.UnsafeUtil.r(r10, r11)
                if (r0 > r6) goto L8f
                int r1 = r1 << 28
                int r0 = r0 + 112
                int r1 = r1 + r0
                int r0 = r1 >> 30
                if (r0 != 0) goto L8f
                long r8 = r8 + r11
                byte r0 = com.google.protobuf.UnsafeUtil.r(r10, r2)
                if (r0 > r6) goto L8f
                r0 = 3
                long r11 = r11 + r0
                byte r0 = com.google.protobuf.UnsafeUtil.r(r10, r8)
                if (r0 <= r6) goto L7
            L8f:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(byte[], long, int):int");
        }

        private static int unsafeEstimateConsecutiveAscii(byte[] bArr, long j2, int i2) {
            int i3 = 0;
            if (i2 < 16) {
                return 0;
            }
            int i4 = 8 - (((int) j2) & 7);
            while (i3 < i4) {
                long j3 = 1 + j2;
                if (UnsafeUtil.r(bArr, j2) < 0) {
                    return i3;
                }
                i3++;
                j2 = j3;
            }
            while (true) {
                int i5 = i3 + 8;
                if (i5 > i2 || (UnsafeUtil.w(bArr, UnsafeUtil.f15364a + j2) & Utf8.ASCII_MASK_LONG) != 0) {
                    break;
                }
                j2 += 8;
                i3 = i5;
            }
            while (i3 < i2) {
                long j4 = j2 + 1;
                if (UnsafeUtil.r(bArr, j2) < 0) {
                    return i3;
                }
                i3++;
                j2 = j4;
            }
            return i2;
        }

        private static int unsafeIncompleteStateFor(byte[] bArr, int i2, long j2, int i3) {
            if (i3 == 0) {
                return Utf8.incompleteStateFor(i2);
            }
            if (i3 == 1) {
                return Utf8.incompleteStateFor(i2, UnsafeUtil.r(bArr, j2));
            }
            if (i3 == 2) {
                return Utf8.incompleteStateFor(i2, UnsafeUtil.r(bArr, j2), UnsafeUtil.r(bArr, j2 + 1));
            }
            throw new AssertionError();
        }

        @Override // com.google.protobuf.Utf8.Processor
        String b(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
            Charset charset = Internal.f15296b;
            String str = new String(bArr, i2, i3, charset);
            if (str.contains("�") && !Arrays.equals(str.getBytes(charset), Arrays.copyOfRange(bArr, i2, i3 + i2))) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            return str;
        }

        @Override // com.google.protobuf.Utf8.Processor
        String d(ByteBuffer byteBuffer, int i2, int i3) throws InvalidProtocolBufferException {
            if ((i2 | i3 | ((byteBuffer.limit() - i2) - i3)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            long jK = UnsafeUtil.k(byteBuffer) + i2;
            long j2 = i3 + jK;
            char[] cArr = new char[i3];
            int i4 = 0;
            while (jK < j2) {
                byte bQ = UnsafeUtil.q(jK);
                if (!DecodeUtil.isOneByte(bQ)) {
                    break;
                }
                jK++;
                DecodeUtil.handleOneByte(bQ, cArr, i4);
                i4++;
            }
            while (jK < j2) {
                long j3 = jK + 1;
                byte bQ2 = UnsafeUtil.q(jK);
                if (DecodeUtil.isOneByte(bQ2)) {
                    int i5 = i4 + 1;
                    DecodeUtil.handleOneByte(bQ2, cArr, i4);
                    while (j3 < j2) {
                        byte bQ3 = UnsafeUtil.q(j3);
                        if (!DecodeUtil.isOneByte(bQ3)) {
                            break;
                        }
                        j3++;
                        DecodeUtil.handleOneByte(bQ3, cArr, i5);
                        i5++;
                    }
                    i4 = i5;
                    jK = j3;
                } else if (DecodeUtil.isTwoBytes(bQ2)) {
                    if (j3 >= j2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    jK += 2;
                    DecodeUtil.handleTwoBytes(bQ2, UnsafeUtil.q(j3), cArr, i4);
                    i4++;
                } else if (DecodeUtil.isThreeBytes(bQ2)) {
                    if (j3 >= j2 - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    long j4 = 2 + jK;
                    jK += 3;
                    DecodeUtil.handleThreeBytes(bQ2, UnsafeUtil.q(j3), UnsafeUtil.q(j4), cArr, i4);
                    i4++;
                } else {
                    if (j3 >= j2 - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    byte bQ4 = UnsafeUtil.q(j3);
                    long j5 = jK + 3;
                    byte bQ5 = UnsafeUtil.q(2 + jK);
                    jK += 4;
                    DecodeUtil.handleFourBytes(bQ2, bQ4, bQ5, UnsafeUtil.q(j5), cArr, i4);
                    i4 += 2;
                }
            }
            return new String(cArr, 0, i4);
        }

        @Override // com.google.protobuf.Utf8.Processor
        int e(CharSequence charSequence, byte[] bArr, int i2, int i3) {
            long j2;
            String str;
            String str2;
            int i4;
            long j3;
            long j4;
            char cCharAt;
            long j5 = i2;
            long j6 = i3 + j5;
            int length = charSequence.length();
            String str3 = " at index ";
            String str4 = "Failed writing ";
            if (length > i3 || bArr.length - i3 < i2) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + (i2 + i3));
            }
            int i5 = 0;
            while (true) {
                j2 = 1;
                if (i5 >= length || (cCharAt = charSequence.charAt(i5)) >= 128) {
                    break;
                }
                UnsafeUtil.F(bArr, j5, (byte) cCharAt);
                i5++;
                j5 = 1 + j5;
            }
            if (i5 == length) {
                return (int) j5;
            }
            while (i5 < length) {
                char cCharAt2 = charSequence.charAt(i5);
                if (cCharAt2 >= 128 || j5 >= j6) {
                    if (cCharAt2 >= 2048 || j5 > j6 - 2) {
                        str = str3;
                        str2 = str4;
                        if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || j5 > j6 - 3) {
                            if (j5 > j6 - 4) {
                                if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i4 = i5 + 1) == length || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i4)))) {
                                    throw new UnpairedSurrogateException(i5, length);
                                }
                                throw new ArrayIndexOutOfBoundsException(str2 + cCharAt2 + str + j5);
                            }
                            int i6 = i5 + 1;
                            if (i6 != length) {
                                char cCharAt3 = charSequence.charAt(i6);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    j3 = 1;
                                    UnsafeUtil.F(bArr, j5, (byte) ((codePoint >>> 18) | 240));
                                    j4 = j6;
                                    UnsafeUtil.F(bArr, j5 + 1, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j7 = j5 + 3;
                                    UnsafeUtil.F(bArr, j5 + 2, (byte) (((codePoint >>> 6) & 63) | 128));
                                    j5 += 4;
                                    UnsafeUtil.F(bArr, j7, (byte) ((codePoint & 63) | 128));
                                    i5 = i6;
                                } else {
                                    i5 = i6;
                                }
                            }
                            throw new UnpairedSurrogateException(i5 - 1, length);
                        }
                        UnsafeUtil.F(bArr, j5, (byte) ((cCharAt2 >>> '\f') | 480));
                        long j8 = j5 + 2;
                        UnsafeUtil.F(bArr, j5 + 1, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                        j5 += 3;
                        UnsafeUtil.F(bArr, j8, (byte) ((cCharAt2 & '?') | 128));
                    } else {
                        str = str3;
                        str2 = str4;
                        long j9 = j5 + j2;
                        UnsafeUtil.F(bArr, j5, (byte) ((cCharAt2 >>> 6) | 960));
                        j5 += 2;
                        UnsafeUtil.F(bArr, j9, (byte) ((cCharAt2 & '?') | 128));
                    }
                    j4 = j6;
                    j3 = 1;
                } else {
                    UnsafeUtil.F(bArr, j5, (byte) cCharAt2);
                    j4 = j6;
                    str2 = str4;
                    j3 = j2;
                    j5 += j2;
                    str = str3;
                }
                i5++;
                str3 = str;
                str4 = str2;
                j2 = j3;
                j6 = j4;
            }
            return (int) j5;
        }

        @Override // com.google.protobuf.Utf8.Processor
        void h(CharSequence charSequence, ByteBuffer byteBuffer) {
            long j2;
            char c2;
            long j3;
            int i2;
            int i3;
            char c3;
            char cCharAt;
            long jK = UnsafeUtil.k(byteBuffer);
            long jPosition = byteBuffer.position() + jK;
            long jLimit = byteBuffer.limit() + jK;
            int length = charSequence.length();
            if (length > jLimit - jPosition) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + byteBuffer.limit());
            }
            int i4 = 0;
            while (true) {
                j2 = 1;
                c2 = 128;
                if (i4 >= length || (cCharAt = charSequence.charAt(i4)) >= 128) {
                    break;
                }
                UnsafeUtil.E(jPosition, (byte) cCharAt);
                i4++;
                jPosition = 1 + jPosition;
            }
            if (i4 == length) {
                Java8Compatibility.e(byteBuffer, (int) (jPosition - jK));
                return;
            }
            while (i4 < length) {
                char cCharAt2 = charSequence.charAt(i4);
                if (cCharAt2 >= c2 || jPosition >= jLimit) {
                    if (cCharAt2 >= 2048 || jPosition > jLimit - 2) {
                        j3 = jK;
                        if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || jPosition > jLimit - 3) {
                            if (jPosition > jLimit - 4) {
                                if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i2 = i4 + 1) == length || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i2)))) {
                                    throw new UnpairedSurrogateException(i4, length);
                                }
                                throw new ArrayIndexOutOfBoundsException("Failed writing " + cCharAt2 + " at index " + jPosition);
                            }
                            i3 = i4 + 1;
                            if (i3 != length) {
                                char cCharAt3 = charSequence.charAt(i3);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    UnsafeUtil.E(jPosition, (byte) ((codePoint >>> 18) | 240));
                                    c3 = 128;
                                    UnsafeUtil.E(jPosition + 1, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j4 = jPosition + 3;
                                    UnsafeUtil.E(jPosition + 2, (byte) (((codePoint >>> 6) & 63) | 128));
                                    jPosition += 4;
                                    UnsafeUtil.E(j4, (byte) ((codePoint & 63) | 128));
                                } else {
                                    i4 = i3;
                                }
                            }
                            throw new UnpairedSurrogateException(i4 - 1, length);
                        }
                        long j5 = jPosition + j2;
                        UnsafeUtil.E(jPosition, (byte) ((cCharAt2 >>> '\f') | 480));
                        long j6 = jPosition + 2;
                        UnsafeUtil.E(j5, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                        jPosition += 3;
                        UnsafeUtil.E(j6, (byte) ((cCharAt2 & '?') | 128));
                    } else {
                        j3 = jK;
                        long j7 = jPosition + j2;
                        UnsafeUtil.E(jPosition, (byte) ((cCharAt2 >>> 6) | 960));
                        jPosition += 2;
                        UnsafeUtil.E(j7, (byte) ((cCharAt2 & '?') | 128));
                    }
                    i3 = i4;
                    c3 = 128;
                } else {
                    UnsafeUtil.E(jPosition, (byte) cCharAt2);
                    j3 = jK;
                    i3 = i4;
                    c3 = c2;
                    jPosition += j2;
                }
                c2 = c3;
                jK = j3;
                j2 = 1;
                i4 = i3 + 1;
            }
            Java8Compatibility.e(byteBuffer, (int) (jPosition - jK));
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x0059, code lost:
        
            if (com.google.protobuf.UnsafeUtil.r(r13, r1) > (-65)) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x009e, code lost:
        
            if (com.google.protobuf.UnsafeUtil.r(r13, r1) > (-65)) goto L59;
         */
        @Override // com.google.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int l(int r12, byte[] r13, int r14, int r15) {
            /*
                Method dump skipped, instructions count: 204
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.l(int, byte[], int, int):int");
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
        
            if (com.google.protobuf.UnsafeUtil.q(r1) > (-65)) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0062, code lost:
        
            if (com.google.protobuf.UnsafeUtil.q(r1) > (-65)) goto L36;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00a3, code lost:
        
            if (com.google.protobuf.UnsafeUtil.q(r1) > (-65)) goto L57;
         */
        @Override // com.google.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int n(int r11, java.nio.ByteBuffer r12, int r13, int r14) {
            /*
                Method dump skipped, instructions count: 212
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.n(int, java.nio.ByteBuffer, int, int):int");
        }

        private static int unsafeEstimateConsecutiveAscii(long j2, int i2) {
            if (i2 < 16) {
                return 0;
            }
            int i3 = (int) ((-j2) & 7);
            int i4 = i3;
            while (i4 > 0) {
                long j3 = 1 + j2;
                if (UnsafeUtil.q(j2) < 0) {
                    return i3 - i4;
                }
                i4--;
                j2 = j3;
            }
            int i5 = i2 - i3;
            while (i5 >= 8 && (UnsafeUtil.v(j2) & Utf8.ASCII_MASK_LONG) == 0) {
                j2 += 8;
                i5 -= 8;
            }
            return i2 - i5;
        }

        private static int unsafeIncompleteStateFor(long j2, int i2, int i3) {
            if (i3 == 0) {
                return Utf8.incompleteStateFor(i2);
            }
            if (i3 == 1) {
                return Utf8.incompleteStateFor(i2, UnsafeUtil.q(j2));
            }
            if (i3 == 2) {
                return Utf8.incompleteStateFor(i2, UnsafeUtil.q(j2), UnsafeUtil.q(j2 + 1));
            }
            throw new AssertionError();
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0039, code lost:
        
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0064, code lost:
        
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static int partialIsValidUtf8(long r10, int r12) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r10, r12)
                long r1 = (long) r0
                long r10 = r10 + r1
                int r12 = r12 - r0
            L7:
                r0 = 0
                r1 = r0
            L9:
                r2 = 1
                if (r12 <= 0) goto L1a
                long r4 = r10 + r2
                byte r1 = com.google.protobuf.UnsafeUtil.q(r10)
                if (r1 < 0) goto L19
                int r12 = r12 + (-1)
                r10 = r4
                goto L9
            L19:
                r10 = r4
            L1a:
                if (r12 != 0) goto L1d
                return r0
            L1d:
                int r0 = r12 + (-1)
                r4 = -32
                r5 = -1
                r6 = -65
                if (r1 >= r4) goto L3a
                if (r0 != 0) goto L29
                return r1
            L29:
                int r12 = r12 + (-2)
                r0 = -62
                if (r1 < r0) goto L39
                long r2 = r2 + r10
                byte r10 = com.google.protobuf.UnsafeUtil.q(r10)
                if (r10 <= r6) goto L37
                goto L39
            L37:
                r10 = r2
                goto L7
            L39:
                return r5
            L3a:
                r7 = -16
                r8 = 2
                if (r1 >= r7) goto L65
                r7 = 2
                if (r0 >= r7) goto L48
                int r10 = unsafeIncompleteStateFor(r10, r1, r0)
                return r10
            L48:
                int r12 = r12 + (-3)
                long r2 = r2 + r10
                byte r0 = com.google.protobuf.UnsafeUtil.q(r10)
                if (r0 > r6) goto L64
                r7 = -96
                if (r1 != r4) goto L57
                if (r0 < r7) goto L64
            L57:
                r4 = -19
                if (r1 != r4) goto L5d
                if (r0 >= r7) goto L64
            L5d:
                long r10 = r10 + r8
                byte r0 = com.google.protobuf.UnsafeUtil.q(r2)
                if (r0 <= r6) goto L7
            L64:
                return r5
            L65:
                r4 = 3
                if (r0 >= r4) goto L6d
                int r10 = unsafeIncompleteStateFor(r10, r1, r0)
                return r10
            L6d:
                int r12 = r12 + (-4)
                long r2 = r2 + r10
                byte r0 = com.google.protobuf.UnsafeUtil.q(r10)
                if (r0 > r6) goto L8f
                int r1 = r1 << 28
                int r0 = r0 + 112
                int r1 = r1 + r0
                int r0 = r1 >> 30
                if (r0 != 0) goto L8f
                long r8 = r8 + r10
                byte r0 = com.google.protobuf.UnsafeUtil.q(r2)
                if (r0 > r6) goto L8f
                r0 = 3
                long r10 = r10 + r0
                byte r0 = com.google.protobuf.UnsafeUtil.q(r8)
                if (r0 <= r6) goto L7
            L8f:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(long, int):int");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i2, int i3, int i4) {
        if (i2 > -12 || i3 > -65 || i4 > -65) {
            return -1;
        }
        return (i2 ^ (i3 << 8)) ^ (i4 << 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(byte[] bArr, int i2, int i3) {
        byte b2 = bArr[i2 - 1];
        int i4 = i3 - i2;
        if (i4 == 0) {
            return incompleteStateFor(b2);
        }
        if (i4 == 1) {
            return incompleteStateFor(b2, bArr[i2]);
        }
        if (i4 == 2) {
            return incompleteStateFor(b2, bArr[i2], bArr[i2 + 1]);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(ByteBuffer byteBuffer, int i2, int i3, int i4) {
        if (i4 == 0) {
            return incompleteStateFor(i2);
        }
        if (i4 == 1) {
            return incompleteStateFor(i2, byteBuffer.get(i3));
        }
        if (i4 == 2) {
            return incompleteStateFor(i2, byteBuffer.get(i3), byteBuffer.get(i3 + 1));
        }
        throw new AssertionError();
    }
}
