package com.alibaba.fastjson.util;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/* loaded from: classes2.dex */
public class UTF8Decoder extends CharsetDecoder {
    private static final Charset charset = Charset.forName("UTF-8");

    public UTF8Decoder() {
        super(charset, 1.0f, 1.0f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0090, code lost:
    
        return xflow(r13, r5, r6, r14, r8, 2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c7, code lost:
    
        return xflow(r13, r5, r6, r14, r8, 3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x012c, code lost:
    
        return xflow(r13, r5, r6, r14, r8, 4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.nio.charset.CoderResult decodeArrayLoop(java.nio.ByteBuffer r13, java.nio.CharBuffer r14) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.UTF8Decoder.decodeArrayLoop(java.nio.ByteBuffer, java.nio.CharBuffer):java.nio.charset.CoderResult");
    }

    private static boolean isMalformed2(int i2, int i3) {
        return (i2 & 30) == 0 || (i3 & 192) != 128;
    }

    private static boolean isMalformed3(int i2, int i3, int i4) {
        return ((i2 != -32 || (i3 & 224) != 128) && (i3 & 192) == 128 && (i4 & 192) == 128) ? false : true;
    }

    private static boolean isMalformed4(int i2, int i3, int i4) {
        return ((i2 & 192) == 128 && (i3 & 192) == 128 && (i4 & 192) == 128) ? false : true;
    }

    private static boolean isNotContinuation(int i2) {
        return (i2 & 192) != 128;
    }

    private static CoderResult lookupN(ByteBuffer byteBuffer, int i2) {
        for (int i3 = 1; i3 < i2; i3++) {
            if (isNotContinuation(byteBuffer.get())) {
                return CoderResult.malformedForLength(i3);
            }
        }
        return CoderResult.malformedForLength(i2);
    }

    private static CoderResult malformed(ByteBuffer byteBuffer, int i2, CharBuffer charBuffer, int i3, int i4) {
        byteBuffer.position(i2 - byteBuffer.arrayOffset());
        CoderResult coderResultMalformedN = malformedN(byteBuffer, i4);
        byteBuffer.position(i2);
        charBuffer.position(i3);
        return coderResultMalformedN;
    }

    public static CoderResult malformedN(ByteBuffer byteBuffer, int i2) {
        int i3 = 1;
        if (i2 == 1) {
            byte b2 = byteBuffer.get();
            return (b2 >> 2) == -2 ? byteBuffer.remaining() < 4 ? CoderResult.UNDERFLOW : lookupN(byteBuffer, 5) : (b2 >> 1) == -2 ? byteBuffer.remaining() < 5 ? CoderResult.UNDERFLOW : lookupN(byteBuffer, 6) : CoderResult.malformedForLength(1);
        }
        if (i2 == 2) {
            return CoderResult.malformedForLength(1);
        }
        if (i2 != 3) {
            if (i2 != 4) {
                throw new IllegalStateException();
            }
            int i4 = byteBuffer.get() & 255;
            byte b3 = byteBuffer.get();
            int i5 = b3 & 255;
            return (i4 > 244 || (i4 == 240 && (i5 < 144 || i5 > 191)) || ((i4 == 244 && (b3 & 240) != 128) || isNotContinuation(i5))) ? CoderResult.malformedForLength(1) : isNotContinuation(byteBuffer.get()) ? CoderResult.malformedForLength(2) : CoderResult.malformedForLength(3);
        }
        byte b4 = byteBuffer.get();
        byte b5 = byteBuffer.get();
        if ((b4 != -32 || (b5 & 224) != 128) && !isNotContinuation(b5)) {
            i3 = 2;
        }
        return CoderResult.malformedForLength(i3);
    }

    private static CoderResult xflow(Buffer buffer, int i2, int i3, Buffer buffer2, int i4, int i5) {
        buffer.position(i2);
        buffer2.position(i4);
        return (i5 == 0 || i3 - i2 < i5) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
    }

    @Override // java.nio.charset.CharsetDecoder
    protected CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
        return decodeArrayLoop(byteBuffer, charBuffer);
    }
}
