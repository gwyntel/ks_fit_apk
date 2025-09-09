package c.a.a;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: c.a.a.q, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0367q {

    /* renamed from: a, reason: collision with root package name */
    public OutputStream f7970a;

    /* renamed from: c.a.a.q$a */
    private class a extends C0367q {

        /* renamed from: b, reason: collision with root package name */
        public boolean f7971b;

        public a(OutputStream outputStream) {
            super(outputStream);
            this.f7971b = true;
        }

        @Override // c.a.a.C0367q
        public void a(int i2) {
            if (this.f7971b) {
                this.f7971b = false;
            } else {
                super.a(i2);
            }
        }
    }

    public C0367q(OutputStream outputStream) {
        this.f7970a = outputStream;
    }

    public void a(int i2) {
        this.f7970a.write(i2);
    }

    public void b(int i2) {
        if (i2 <= 127) {
            a((byte) i2);
            return;
        }
        int i3 = i2;
        int i4 = 1;
        while (true) {
            i3 >>>= 8;
            if (i3 == 0) {
                break;
            } else {
                i4++;
            }
        }
        a((byte) (i4 | 128));
        for (int i5 = (i4 - 1) * 8; i5 >= 0; i5 -= 8) {
            a((byte) (i2 >> i5));
        }
    }

    public void a(byte[] bArr) {
        this.f7970a.write(bArr);
    }

    public void a(byte[] bArr, int i2, int i3) throws IOException {
        this.f7970a.write(bArr, i2, i3);
    }

    public void a(int i2, byte[] bArr) {
        a(i2);
        b(bArr.length);
        a(bArr);
    }

    public C0367q b() {
        return new qa(this.f7970a);
    }

    public void a(int i2, int i3) throws IOException {
        if (i3 < 31) {
            a(i2 | i3);
            return;
        }
        a(i2 | 31);
        if (i3 < 128) {
            a(i3);
            return;
        }
        byte[] bArr = new byte[5];
        int i4 = 4;
        bArr[4] = (byte) (i3 & 127);
        do {
            i3 >>= 7;
            i4--;
            bArr[i4] = (byte) ((i3 & 127) | 128);
        } while (i3 > 127);
        a(bArr, i4, 5 - i4);
    }

    public void a(int i2, int i3, byte[] bArr) throws IOException {
        a(i2, i3);
        b(bArr.length);
        a(bArr);
    }

    public void a(InterfaceC0346f interfaceC0346f) {
        if (interfaceC0346f != null) {
            interfaceC0346f.toASN1Primitive().a(this);
            return;
        }
        throw new IOException("null object detected");
    }

    public void a(r rVar) throws IOException {
        if (rVar != null) {
            rVar.a(new a(this.f7970a));
            return;
        }
        throw new IOException("null object detected");
    }

    public C0367q a() {
        return new da(this.f7970a);
    }
}
