package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public final class LittleEndianDataOutputStream extends FilterOutputStream implements DataOutput {
    public LittleEndianDataOutputStream(OutputStream outputStream) {
        super(new DataOutputStream((OutputStream) Preconditions.checkNotNull(outputStream)));
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        ((FilterOutputStream) this).out.close();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.DataOutput
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        ((FilterOutputStream) this).out.write(bArr, i2, i3);
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean z2) throws IOException {
        ((DataOutputStream) ((FilterOutputStream) this).out).writeBoolean(z2);
    }

    @Override // java.io.DataOutput
    public void writeByte(int i2) throws IOException {
        ((DataOutputStream) ((FilterOutputStream) this).out).writeByte(i2);
    }

    @Override // java.io.DataOutput
    @Deprecated
    public void writeBytes(String str) throws IOException {
        ((DataOutputStream) ((FilterOutputStream) this).out).writeBytes(str);
    }

    @Override // java.io.DataOutput
    public void writeChar(int i2) throws IOException {
        writeShort(i2);
    }

    @Override // java.io.DataOutput
    public void writeChars(String str) throws IOException {
        for (int i2 = 0; i2 < str.length(); i2++) {
            writeChar(str.charAt(i2));
        }
    }

    @Override // java.io.DataOutput
    public void writeDouble(double d2) throws IOException {
        writeLong(Double.doubleToLongBits(d2));
    }

    @Override // java.io.DataOutput
    public void writeFloat(float f2) throws IOException {
        writeInt(Float.floatToIntBits(f2));
    }

    @Override // java.io.DataOutput
    public void writeInt(int i2) throws IOException {
        ((FilterOutputStream) this).out.write(i2 & 255);
        ((FilterOutputStream) this).out.write((i2 >> 8) & 255);
        ((FilterOutputStream) this).out.write((i2 >> 16) & 255);
        ((FilterOutputStream) this).out.write((i2 >> 24) & 255);
    }

    @Override // java.io.DataOutput
    public void writeLong(long j2) throws IOException {
        byte[] byteArray = Longs.toByteArray(Long.reverseBytes(j2));
        write(byteArray, 0, byteArray.length);
    }

    @Override // java.io.DataOutput
    public void writeShort(int i2) throws IOException {
        ((FilterOutputStream) this).out.write(i2 & 255);
        ((FilterOutputStream) this).out.write((i2 >> 8) & 255);
    }

    @Override // java.io.DataOutput
    public void writeUTF(String str) throws IOException {
        ((DataOutputStream) ((FilterOutputStream) this).out).writeUTF(str);
    }
}
