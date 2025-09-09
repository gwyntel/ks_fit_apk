package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import java.io.DataOutput;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public interface ByteArrayDataOutput extends DataOutput {
    byte[] toByteArray();

    @Override // java.io.DataOutput
    void write(int i2);

    @Override // java.io.DataOutput
    void write(byte[] bArr);

    @Override // java.io.DataOutput
    void write(byte[] bArr, int i2, int i3);

    @Override // java.io.DataOutput
    void writeBoolean(boolean z2);

    @Override // java.io.DataOutput
    void writeByte(int i2);

    @Override // java.io.DataOutput
    @Deprecated
    void writeBytes(String str);

    @Override // java.io.DataOutput
    void writeChar(int i2);

    @Override // java.io.DataOutput
    void writeChars(String str);

    @Override // java.io.DataOutput
    void writeDouble(double d2);

    @Override // java.io.DataOutput
    void writeFloat(float f2);

    @Override // java.io.DataOutput
    void writeInt(int i2);

    @Override // java.io.DataOutput
    void writeLong(long j2);

    @Override // java.io.DataOutput
    void writeShort(int i2);

    @Override // java.io.DataOutput
    void writeUTF(String str);
}
