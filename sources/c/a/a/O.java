package c.a.a;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class O extends InputStream {

    /* renamed from: a, reason: collision with root package name */
    public final C0372w f7675a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f7676b = true;

    /* renamed from: c, reason: collision with root package name */
    public InputStream f7677c;

    public O(C0372w c0372w) {
        this.f7675a = c0372w;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        InterfaceC0366p interfaceC0366p;
        int i4 = 0;
        if (this.f7677c == null) {
            if (!this.f7676b || (interfaceC0366p = (InterfaceC0366p) this.f7675a.a()) == null) {
                return -1;
            }
            this.f7676b = false;
            this.f7677c = interfaceC0366p.b();
        }
        while (true) {
            int i5 = this.f7677c.read(bArr, i2 + i4, i3 - i4);
            if (i5 >= 0) {
                i4 += i5;
                if (i4 == i3) {
                    return i4;
                }
            } else {
                InterfaceC0366p interfaceC0366p2 = (InterfaceC0366p) this.f7675a.a();
                if (interfaceC0366p2 == null) {
                    this.f7677c = null;
                    if (i4 < 1) {
                        return -1;
                    }
                    return i4;
                }
                this.f7677c = interfaceC0366p2.b();
            }
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        InterfaceC0366p interfaceC0366p;
        if (this.f7677c == null) {
            if (!this.f7676b || (interfaceC0366p = (InterfaceC0366p) this.f7675a.a()) == null) {
                return -1;
            }
            this.f7676b = false;
            this.f7677c = interfaceC0366p.b();
        }
        while (true) {
            int i2 = this.f7677c.read();
            if (i2 >= 0) {
                return i2;
            }
            InterfaceC0366p interfaceC0366p2 = (InterfaceC0366p) this.f7675a.a();
            if (interfaceC0366p2 == null) {
                this.f7677c = null;
                return -1;
            }
            this.f7677c = interfaceC0366p2.b();
        }
    }
}
