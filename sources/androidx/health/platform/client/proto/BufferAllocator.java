package androidx.health.platform.client.proto;

import java.nio.ByteBuffer;

@CheckReturnValue
/* loaded from: classes.dex */
abstract class BufferAllocator {
    private static final BufferAllocator UNPOOLED = new BufferAllocator() { // from class: androidx.health.platform.client.proto.BufferAllocator.1
        @Override // androidx.health.platform.client.proto.BufferAllocator
        public AllocatedBuffer allocateDirectBuffer(int i2) {
            return AllocatedBuffer.wrap(ByteBuffer.allocateDirect(i2));
        }

        @Override // androidx.health.platform.client.proto.BufferAllocator
        public AllocatedBuffer allocateHeapBuffer(int i2) {
            return AllocatedBuffer.wrap(new byte[i2]);
        }
    };

    BufferAllocator() {
    }

    public static BufferAllocator unpooled() {
        return UNPOOLED;
    }

    public abstract AllocatedBuffer allocateDirectBuffer(int i2);

    public abstract AllocatedBuffer allocateHeapBuffer(int i2);
}
