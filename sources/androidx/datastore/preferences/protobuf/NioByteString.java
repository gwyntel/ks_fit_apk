package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
final class NioByteString extends ByteString.LeafByteString {
    private final ByteBuffer buffer;

    NioByteString(ByteBuffer byteBuffer) {
        Internal.b(byteBuffer, "buffer");
        this.buffer = byteBuffer.slice().order(ByteOrder.nativeOrder());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("NioByteString instances are not to be serialized directly");
    }

    private ByteBuffer slice(int i2, int i3) {
        if (i2 < this.buffer.position() || i3 > this.buffer.limit() || i2 > i3) {
            throw new IllegalArgumentException(String.format("Invalid indices [%d, %d]", Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        ByteBuffer byteBufferSlice = this.buffer.slice();
        byteBufferSlice.position(i2 - this.buffer.position());
        byteBufferSlice.limit(i3 - this.buffer.position());
        return byteBufferSlice;
    }

    private Object writeReplace() {
        return ByteString.copyFrom(this.buffer.slice());
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public ByteBuffer asReadOnlyByteBuffer() {
        return this.buffer.asReadOnlyBuffer();
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        return Collections.singletonList(asReadOnlyByteBuffer());
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public byte byteAt(int i2) {
        try {
            return this.buffer.get(i2);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw e2;
        } catch (IndexOutOfBoundsException e3) {
            throw new ArrayIndexOutOfBoundsException(e3.getMessage());
        }
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public void copyTo(ByteBuffer byteBuffer) {
        byteBuffer.put(this.buffer.slice());
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    protected void copyToInternal(byte[] bArr, int i2, int i3, int i4) {
        ByteBuffer byteBufferSlice = this.buffer.slice();
        byteBufferSlice.position(i2);
        byteBufferSlice.get(bArr, i3, i4);
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ByteString)) {
            return false;
        }
        ByteString byteString = (ByteString) obj;
        if (size() != byteString.size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        return obj instanceof NioByteString ? this.buffer.equals(((NioByteString) obj).buffer) : obj instanceof RopeByteString ? obj.equals(this) : this.buffer.equals(byteString.asReadOnlyByteBuffer());
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString.LeafByteString
    boolean equalsRange(ByteString byteString, int i2, int i3) {
        return substring(0, i3).equals(byteString.substring(i2, i3 + i2));
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public byte internalByteAt(int i2) {
        return byteAt(i2);
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public boolean isValidUtf8() {
        return Utf8.l(this.buffer);
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public CodedInputStream newCodedInput() {
        return CodedInputStream.b(this.buffer, true);
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public InputStream newInput() {
        return new InputStream() { // from class: androidx.datastore.preferences.protobuf.NioByteString.1
            private final ByteBuffer buf;

            {
                this.buf = NioByteString.this.buffer.slice();
            }

            @Override // java.io.InputStream
            public int available() throws IOException {
                return this.buf.remaining();
            }

            @Override // java.io.InputStream
            public void mark(int i2) {
                this.buf.mark();
            }

            @Override // java.io.InputStream
            public boolean markSupported() {
                return true;
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                if (this.buf.hasRemaining()) {
                    return this.buf.get() & 255;
                }
                return -1;
            }

            @Override // java.io.InputStream
            public void reset() throws IOException {
                try {
                    this.buf.reset();
                } catch (InvalidMarkException e2) {
                    throw new IOException(e2);
                }
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i2, int i3) throws IOException {
                if (!this.buf.hasRemaining()) {
                    return -1;
                }
                int iMin = Math.min(i3, this.buf.remaining());
                this.buf.get(bArr, i2, iMin);
                return iMin;
            }
        };
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    protected int partialHash(int i2, int i3, int i4) {
        for (int i5 = i3; i5 < i3 + i4; i5++) {
            i2 = (i2 * 31) + this.buffer.get(i5);
        }
        return i2;
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    protected int partialIsValidUtf8(int i2, int i3, int i4) {
        return Utf8.m(i2, this.buffer, i3, i4 + i3);
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public int size() {
        return this.buffer.remaining();
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public ByteString substring(int i2, int i3) {
        try {
            return new NioByteString(slice(i2, i3));
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw e2;
        } catch (IndexOutOfBoundsException e3) {
            throw new ArrayIndexOutOfBoundsException(e3.getMessage());
        }
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    protected String toStringInternal(Charset charset) {
        byte[] byteArray;
        int length;
        int iArrayOffset;
        if (this.buffer.hasArray()) {
            byteArray = this.buffer.array();
            iArrayOffset = this.buffer.arrayOffset() + this.buffer.position();
            length = this.buffer.remaining();
        } else {
            byteArray = toByteArray();
            length = byteArray.length;
            iArrayOffset = 0;
        }
        return new String(byteArray, iArrayOffset, length, charset);
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(toByteArray());
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    void writeToInternal(OutputStream outputStream, int i2, int i3) throws IOException {
        if (!this.buffer.hasArray()) {
            ByteBufferWriter.a(slice(i2, i3 + i2), outputStream);
        } else {
            outputStream.write(this.buffer.array(), this.buffer.arrayOffset() + this.buffer.position() + i2, i3);
        }
    }

    @Override // androidx.datastore.preferences.protobuf.ByteString
    void writeTo(ByteOutput byteOutput) throws IOException {
        byteOutput.writeLazy(this.buffer.slice());
    }
}
