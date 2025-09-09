package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public interface PrimitiveSink {
    @CanIgnoreReturnValue
    PrimitiveSink putBoolean(boolean z2);

    @CanIgnoreReturnValue
    PrimitiveSink putByte(byte b2);

    @CanIgnoreReturnValue
    PrimitiveSink putBytes(ByteBuffer byteBuffer);

    @CanIgnoreReturnValue
    PrimitiveSink putBytes(byte[] bArr);

    @CanIgnoreReturnValue
    PrimitiveSink putBytes(byte[] bArr, int i2, int i3);

    @CanIgnoreReturnValue
    PrimitiveSink putChar(char c2);

    @CanIgnoreReturnValue
    PrimitiveSink putDouble(double d2);

    @CanIgnoreReturnValue
    PrimitiveSink putFloat(float f2);

    @CanIgnoreReturnValue
    PrimitiveSink putInt(int i2);

    @CanIgnoreReturnValue
    PrimitiveSink putLong(long j2);

    @CanIgnoreReturnValue
    PrimitiveSink putShort(short s2);

    @CanIgnoreReturnValue
    PrimitiveSink putString(CharSequence charSequence, Charset charset);

    @CanIgnoreReturnValue
    PrimitiveSink putUnencodedChars(CharSequence charSequence);
}
