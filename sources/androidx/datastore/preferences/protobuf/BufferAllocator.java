package androidx.datastore.preferences.protobuf;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
abstract class BufferAllocator {
    private static final BufferAllocator UNPOOLED = new BufferAllocator() { // from class: androidx.datastore.preferences.protobuf.BufferAllocator.1
        @Override // androidx.datastore.preferences.protobuf.BufferAllocator
        public AllocatedBuffer allocateDirectBuffer(int i2) {
            return AllocatedBuffer.wrap(ByteBuffer.allocateDirect(i2));
        }

        @Override // androidx.datastore.preferences.protobuf.BufferAllocator
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
