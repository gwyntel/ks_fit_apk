package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import kotlin.UShort;

/* loaded from: classes.dex */
public final class ShortVector extends BaseVector {
    public ShortVector __assign(int i2, ByteBuffer byteBuffer) {
        b(i2, 2, byteBuffer);
        return this;
    }

    public short get(int i2) {
        return this.f4040a.getShort(a(i2));
    }

    public int getAsUnsigned(int i2) {
        return get(i2) & UShort.MAX_VALUE;
    }
}
