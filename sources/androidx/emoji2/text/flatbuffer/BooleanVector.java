package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class BooleanVector extends BaseVector {
    public BooleanVector __assign(int i2, ByteBuffer byteBuffer) {
        b(i2, 1, byteBuffer);
        return this;
    }

    public boolean get(int i2) {
        return this.f4040a.get(a(i2)) != 0;
    }
}
