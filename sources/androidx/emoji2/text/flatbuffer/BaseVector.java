package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class BaseVector {

    /* renamed from: a, reason: collision with root package name */
    protected ByteBuffer f4040a;
    private int element_size;
    private int length;
    private int vector;

    protected int a(int i2) {
        return this.vector + (i2 * this.element_size);
    }

    protected void b(int i2, int i3, ByteBuffer byteBuffer) {
        this.f4040a = byteBuffer;
        if (byteBuffer != null) {
            this.vector = i2;
            this.length = byteBuffer.getInt(i2 - 4);
            this.element_size = i3;
        } else {
            this.vector = 0;
            this.length = 0;
            this.element_size = 0;
        }
    }

    public int length() {
        return this.length;
    }

    public void reset() {
        b(0, 0, null);
    }
}
