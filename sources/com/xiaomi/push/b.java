package com.xiaomi.push;

import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.google.common.base.Ascii;
import java.io.InputStream;
import java.util.Vector;

/* loaded from: classes4.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private int f23470a;

    /* renamed from: a, reason: collision with other field name */
    private final InputStream f203a;

    /* renamed from: a, reason: collision with other field name */
    private final byte[] f204a;

    /* renamed from: b, reason: collision with root package name */
    private int f23471b;

    /* renamed from: c, reason: collision with root package name */
    private int f23472c;

    /* renamed from: d, reason: collision with root package name */
    private int f23473d;

    /* renamed from: e, reason: collision with root package name */
    private int f23474e;

    /* renamed from: f, reason: collision with root package name */
    private int f23475f;

    /* renamed from: g, reason: collision with root package name */
    private int f23476g;

    /* renamed from: h, reason: collision with root package name */
    private int f23477h;

    /* renamed from: i, reason: collision with root package name */
    private int f23478i;

    private b(byte[] bArr, int i2, int i3) {
        this.f23475f = Integer.MAX_VALUE;
        this.f23477h = 64;
        this.f23478i = AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL;
        this.f204a = bArr;
        this.f23470a = i3 + i2;
        this.f23472c = i2;
        this.f203a = null;
    }

    public static b a(InputStream inputStream) {
        return new b(inputStream);
    }

    /* renamed from: b, reason: collision with other method in class */
    public long m197b() {
        return m199c();
    }

    public int c() {
        return d();
    }

    public int d() throws d {
        int i2;
        byte bA = a();
        if (bA >= 0) {
            return bA;
        }
        int i3 = bA & Byte.MAX_VALUE;
        byte bA2 = a();
        if (bA2 >= 0) {
            i2 = bA2 << 7;
        } else {
            i3 |= (bA2 & Byte.MAX_VALUE) << 7;
            byte bA3 = a();
            if (bA3 >= 0) {
                i2 = bA3 << 14;
            } else {
                i3 |= (bA3 & Byte.MAX_VALUE) << 14;
                byte bA4 = a();
                if (bA4 < 0) {
                    int i4 = i3 | ((bA4 & Byte.MAX_VALUE) << 21);
                    byte bA5 = a();
                    int i5 = i4 | (bA5 << Ascii.FS);
                    if (bA5 >= 0) {
                        return i5;
                    }
                    for (int i6 = 0; i6 < 5; i6++) {
                        if (a() >= 0) {
                            return i5;
                        }
                    }
                    throw d.c();
                }
                i2 = bA4 << Ascii.NAK;
            }
        }
        return i3 | i2;
    }

    public int e() throws d {
        return (a() & 255) | ((a() & 255) << 8) | ((a() & 255) << 16) | ((a() & 255) << 24);
    }

    public static b a(byte[] bArr, int i2, int i3) {
        return new b(bArr, i2, i3);
    }

    /* renamed from: b, reason: collision with other method in class */
    public int m196b() {
        return d();
    }

    /* renamed from: c, reason: collision with other method in class */
    public long m199c() throws d {
        long j2 = 0;
        for (int i2 = 0; i2 < 64; i2 += 7) {
            j2 |= (r3 & Byte.MAX_VALUE) << i2;
            if ((a() & 128) == 0) {
                return j2;
            }
        }
        throw d.c();
    }

    private void b() {
        int i2 = this.f23470a + this.f23471b;
        this.f23470a = i2;
        int i3 = this.f23474e + i2;
        int i4 = this.f23475f;
        if (i3 > i4) {
            int i5 = i3 - i4;
            this.f23471b = i5;
            this.f23470a = i2 - i5;
            return;
        }
        this.f23471b = 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public int m187a() throws d {
        if (m198b()) {
            this.f23473d = 0;
            return 0;
        }
        int iD = d();
        this.f23473d = iD;
        if (iD != 0) {
            return iD;
        }
        throw d.d();
    }

    public void c(int i2) throws d {
        if (i2 >= 0) {
            int i3 = this.f23474e;
            int i4 = this.f23472c;
            int i5 = i3 + i4 + i2;
            int i6 = this.f23475f;
            if (i5 <= i6) {
                int i7 = this.f23470a;
                if (i2 <= i7 - i4) {
                    this.f23472c = i4 + i2;
                    return;
                }
                int i8 = i7 - i4;
                this.f23474e = i3 + i7;
                this.f23472c = 0;
                this.f23470a = 0;
                while (i8 < i2) {
                    InputStream inputStream = this.f203a;
                    int iSkip = inputStream == null ? -1 : (int) inputStream.skip(i2 - i8);
                    if (iSkip > 0) {
                        i8 += iSkip;
                        this.f23474e += iSkip;
                    } else {
                        throw d.a();
                    }
                }
                return;
            }
            c((i6 - i3) - i4);
            throw d.a();
        }
        throw d.b();
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m192a(int i2) throws d {
        if (this.f23473d != i2) {
            throw d.e();
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    public long m200d() throws d {
        return ((a() & 255) << 8) | (a() & 255) | ((a() & 255) << 16) | ((a() & 255) << 24) | ((a() & 255) << 32) | ((a() & 255) << 40) | ((a() & 255) << 48) | ((a() & 255) << 56);
    }

    private b(InputStream inputStream) {
        this.f23475f = Integer.MAX_VALUE;
        this.f23477h = 64;
        this.f23478i = AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL;
        this.f204a = new byte[4096];
        this.f23470a = 0;
        this.f23472c = 0;
        this.f203a = inputStream;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m194a(int i2) throws d {
        int iA = f.a(i2);
        if (iA == 0) {
            m196b();
            return true;
        }
        if (iA == 1) {
            m200d();
            return true;
        }
        if (iA == 2) {
            c(d());
            return true;
        }
        if (iA == 3) {
            m191a();
            m192a(f.a(f.b(i2), 4));
            return true;
        }
        if (iA == 4) {
            return false;
        }
        if (iA == 5) {
            e();
            return true;
        }
        throw d.f();
    }

    public void b(int i2) {
        this.f23475f = i2;
        b();
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m198b() {
        return this.f23472c == this.f23470a && !a(false);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m191a() throws d {
        int iM187a;
        do {
            iM187a = m187a();
            if (iM187a == 0) {
                return;
            }
        } while (m194a(iM187a));
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m188a() {
        return m199c();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m193a() {
        return d() != 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m190a() throws d {
        int iD = d();
        int i2 = this.f23470a;
        int i3 = this.f23472c;
        if (iD <= i2 - i3 && iD > 0) {
            String str = new String(this.f204a, i3, iD, "UTF-8");
            this.f23472c += iD;
            return str;
        }
        return new String(m195a(iD), "UTF-8");
    }

    public void a(e eVar) throws d {
        int iD = d();
        if (this.f23476g < this.f23477h) {
            int iA = a(iD);
            this.f23476g++;
            eVar.a(this);
            m192a(0);
            this.f23476g--;
            b(iA);
            return;
        }
        throw d.g();
    }

    /* renamed from: a, reason: collision with other method in class */
    public a m189a() throws d {
        int iD = d();
        int i2 = this.f23470a;
        int i3 = this.f23472c;
        if (iD <= i2 - i3 && iD > 0) {
            a aVarA = a.a(this.f204a, i3, iD);
            this.f23472c += iD;
            return aVarA;
        }
        return a.a(m195a(iD));
    }

    public int a(int i2) throws d {
        if (i2 >= 0) {
            int i3 = i2 + this.f23474e + this.f23472c;
            int i4 = this.f23475f;
            if (i3 <= i4) {
                this.f23475f = i3;
                b();
                return i4;
            }
            throw d.a();
        }
        throw d.b();
    }

    private boolean a(boolean z2) throws d {
        int i2 = this.f23472c;
        int i3 = this.f23470a;
        if (i2 >= i3) {
            int i4 = this.f23474e;
            if (i4 + i3 == this.f23475f) {
                if (z2) {
                    throw d.a();
                }
                return false;
            }
            this.f23474e = i4 + i3;
            this.f23472c = 0;
            InputStream inputStream = this.f203a;
            int i5 = inputStream == null ? -1 : inputStream.read(this.f204a);
            this.f23470a = i5;
            if (i5 == 0 || i5 < -1) {
                throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + this.f23470a + "\nThe InputStream implementation is buggy.");
            }
            if (i5 == -1) {
                this.f23470a = 0;
                if (z2) {
                    throw d.a();
                }
                return false;
            }
            b();
            int i6 = this.f23474e + this.f23470a + this.f23471b;
            if (i6 > this.f23478i || i6 < 0) {
                throw d.h();
            }
            return true;
        }
        throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
    }

    public byte a() throws d {
        if (this.f23472c == this.f23470a) {
            a(true);
        }
        byte[] bArr = this.f204a;
        int i2 = this.f23472c;
        this.f23472c = i2 + 1;
        return bArr[i2];
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m195a(int i2) throws d {
        if (i2 >= 0) {
            int i3 = this.f23474e;
            int i4 = this.f23472c;
            int i5 = i3 + i4 + i2;
            int i6 = this.f23475f;
            if (i5 <= i6) {
                int i7 = this.f23470a;
                if (i2 <= i7 - i4) {
                    byte[] bArr = new byte[i2];
                    System.arraycopy(this.f204a, i4, bArr, 0, i2);
                    this.f23472c += i2;
                    return bArr;
                }
                if (i2 < 4096) {
                    byte[] bArr2 = new byte[i2];
                    int i8 = i7 - i4;
                    System.arraycopy(this.f204a, i4, bArr2, 0, i8);
                    this.f23472c = this.f23470a;
                    a(true);
                    while (true) {
                        int i9 = i2 - i8;
                        int i10 = this.f23470a;
                        if (i9 > i10) {
                            System.arraycopy(this.f204a, 0, bArr2, i8, i10);
                            int i11 = this.f23470a;
                            i8 += i11;
                            this.f23472c = i11;
                            a(true);
                        } else {
                            System.arraycopy(this.f204a, 0, bArr2, i8, i9);
                            this.f23472c = i9;
                            return bArr2;
                        }
                    }
                } else {
                    this.f23474e = i3 + i7;
                    this.f23472c = 0;
                    this.f23470a = 0;
                    int length = i7 - i4;
                    int i12 = i2 - length;
                    Vector vector = new Vector();
                    while (i12 > 0) {
                        int iMin = Math.min(i12, 4096);
                        byte[] bArr3 = new byte[iMin];
                        int i13 = 0;
                        while (i13 < iMin) {
                            InputStream inputStream = this.f203a;
                            int i14 = inputStream == null ? -1 : inputStream.read(bArr3, i13, iMin - i13);
                            if (i14 == -1) {
                                throw d.a();
                            }
                            this.f23474e += i14;
                            i13 += i14;
                        }
                        i12 -= iMin;
                        vector.addElement(bArr3);
                    }
                    byte[] bArr4 = new byte[i2];
                    System.arraycopy(this.f204a, i4, bArr4, 0, length);
                    for (int i15 = 0; i15 < vector.size(); i15++) {
                        byte[] bArr5 = (byte[]) vector.elementAt(i15);
                        System.arraycopy(bArr5, 0, bArr4, length, bArr5.length);
                        length += bArr5.length;
                    }
                    return bArr4;
                }
            } else {
                c((i6 - i3) - i4);
                throw d.a();
            }
        } else {
            throw d.b();
        }
    }
}
