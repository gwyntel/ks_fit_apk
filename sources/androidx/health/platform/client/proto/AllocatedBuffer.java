package androidx.health.platform.client.proto;

import java.nio.ByteBuffer;

@CheckReturnValue
/* loaded from: classes.dex */
abstract class AllocatedBuffer {
    AllocatedBuffer() {
    }

    public static AllocatedBuffer wrap(byte[] bArr) {
        return wrapNoCheck(bArr, 0, bArr.length);
    }

    private static AllocatedBuffer wrapNoCheck(final byte[] bArr, final int i2, final int i3) {
        return new AllocatedBuffer() { // from class: androidx.health.platform.client.proto.AllocatedBuffer.2
            private int position;

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public byte[] array() {
                return bArr;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public int arrayOffset() {
                return i2;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public boolean hasArray() {
                return true;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public boolean hasNioBuffer() {
                return false;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public int limit() {
                return i3;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public ByteBuffer nioBuffer() {
                throw new UnsupportedOperationException();
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public int position() {
                return this.position;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public int remaining() {
                return i3 - this.position;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public AllocatedBuffer position(int i4) {
                if (i4 >= 0 && i4 <= i3) {
                    this.position = i4;
                    return this;
                }
                throw new IllegalArgumentException("Invalid position: " + i4);
            }
        };
    }

    public abstract byte[] array();

    public abstract int arrayOffset();

    public abstract boolean hasArray();

    public abstract boolean hasNioBuffer();

    public abstract int limit();

    public abstract ByteBuffer nioBuffer();

    public abstract int position();

    @CanIgnoreReturnValue
    public abstract AllocatedBuffer position(int i2);

    public abstract int remaining();

    public static AllocatedBuffer wrap(byte[] bArr, int i2, int i3) {
        if (i2 < 0 || i3 < 0 || i2 + i3 > bArr.length) {
            throw new IndexOutOfBoundsException(String.format("bytes.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        return wrapNoCheck(bArr, i2, i3);
    }

    public static AllocatedBuffer wrap(final ByteBuffer byteBuffer) {
        Internal.b(byteBuffer, "buffer");
        return new AllocatedBuffer() { // from class: androidx.health.platform.client.proto.AllocatedBuffer.1
            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public byte[] array() {
                return byteBuffer.array();
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public int arrayOffset() {
                return byteBuffer.arrayOffset();
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public boolean hasArray() {
                return byteBuffer.hasArray();
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public boolean hasNioBuffer() {
                return true;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public int limit() {
                return byteBuffer.limit();
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public ByteBuffer nioBuffer() {
                return byteBuffer;
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public int position() {
                return byteBuffer.position();
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public int remaining() {
                return byteBuffer.remaining();
            }

            @Override // androidx.health.platform.client.proto.AllocatedBuffer
            public AllocatedBuffer position(int i2) {
                byteBuffer.position(i2);
                return this;
            }
        };
    }
}
