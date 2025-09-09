package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class Struct {

    /* renamed from: a, reason: collision with root package name */
    protected int f4067a;

    /* renamed from: b, reason: collision with root package name */
    protected ByteBuffer f4068b;

    public void __reset() {
        a(0, null);
    }

    protected void a(int i2, ByteBuffer byteBuffer) {
        this.f4068b = byteBuffer;
        if (byteBuffer != null) {
            this.f4067a = i2;
        } else {
            this.f4067a = 0;
        }
    }
}
