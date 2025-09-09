package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class UnionVector extends BaseVector {
    public UnionVector __assign(int i2, int i3, ByteBuffer byteBuffer) {
        b(i2, i3, byteBuffer);
        return this;
    }

    public Table get(Table table, int i2) {
        return Table.g(table, a(i2), this.f4040a);
    }
}
