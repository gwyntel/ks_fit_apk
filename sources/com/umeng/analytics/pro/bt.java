package com.umeng.analytics.pro;

import com.umeng.analytics.pro.cj;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class bt {

    /* renamed from: a, reason: collision with root package name */
    private final cp f21538a;

    /* renamed from: b, reason: collision with root package name */
    private final dc f21539b;

    public bt() {
        this(new cj.a());
    }

    private ck j(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        this.f21539b.a(bArr);
        int length = bxVarArr.length + 1;
        bx[] bxVarArr2 = new bx[length];
        int i2 = 0;
        bxVarArr2[0] = bxVar;
        int i3 = 0;
        while (i3 < bxVarArr.length) {
            int i4 = i3 + 1;
            bxVarArr2[i4] = bxVarArr[i3];
            i3 = i4;
        }
        this.f21538a.j();
        ck ckVarL = null;
        while (i2 < length) {
            ckVarL = this.f21538a.l();
            if (ckVarL.f21619b == 0 || ckVarL.f21620c > bxVarArr2[i2].a()) {
                return null;
            }
            if (ckVarL.f21620c != bxVarArr2[i2].a()) {
                cs.a(this.f21538a, ckVarL.f21619b);
                this.f21538a.m();
            } else {
                i2++;
                if (i2 < length) {
                    this.f21538a.j();
                }
            }
        }
        return ckVarL;
    }

    public void a(bq bqVar, byte[] bArr) throws bw {
        try {
            this.f21539b.a(bArr);
            bqVar.read(this.f21538a);
        } finally {
            this.f21539b.e();
            this.f21538a.B();
        }
    }

    public Byte b(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        return (Byte) a((byte) 3, bArr, bxVar, bxVarArr);
    }

    public Double c(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        return (Double) a((byte) 4, bArr, bxVar, bxVarArr);
    }

    public Short d(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        return (Short) a((byte) 6, bArr, bxVar, bxVarArr);
    }

    public Integer e(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        return (Integer) a((byte) 8, bArr, bxVar, bxVarArr);
    }

    public Long f(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        return (Long) a((byte) 10, bArr, bxVar, bxVarArr);
    }

    public String g(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        return (String) a((byte) 11, bArr, bxVar, bxVarArr);
    }

    public ByteBuffer h(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        return (ByteBuffer) a((byte) 100, bArr, bxVar, bxVarArr);
    }

    public Short i(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        try {
            try {
                if (j(bArr, bxVar, bxVarArr) != null) {
                    this.f21538a.j();
                    return Short.valueOf(this.f21538a.l().f21620c);
                }
                this.f21539b.e();
                this.f21538a.B();
                return null;
            } catch (Exception e2) {
                throw new bw(e2);
            }
        } finally {
            this.f21539b.e();
            this.f21538a.B();
        }
    }

    public bt(cr crVar) {
        dc dcVar = new dc();
        this.f21539b = dcVar;
        this.f21538a = crVar.a(dcVar);
    }

    public void a(bq bqVar, String str, String str2) throws bw {
        try {
            try {
                a(bqVar, str.getBytes(str2));
            } catch (UnsupportedEncodingException unused) {
                throw new bw("JVM DOES NOT SUPPORT ENCODING: " + str2);
            }
        } finally {
            this.f21538a.B();
        }
    }

    public void a(bq bqVar, byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        try {
            try {
                if (j(bArr, bxVar, bxVarArr) != null) {
                    bqVar.read(this.f21538a);
                }
            } catch (Exception e2) {
                throw new bw(e2);
            }
        } finally {
            this.f21539b.e();
            this.f21538a.B();
        }
    }

    public Boolean a(byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        return (Boolean) a((byte) 2, bArr, bxVar, bxVarArr);
    }

    private Object a(byte b2, byte[] bArr, bx bxVar, bx... bxVarArr) throws bw {
        try {
            try {
                ck ckVarJ = j(bArr, bxVar, bxVarArr);
                if (ckVarJ != null) {
                    if (b2 != 2) {
                        if (b2 != 3) {
                            if (b2 != 4) {
                                if (b2 != 6) {
                                    if (b2 != 8) {
                                        if (b2 != 100) {
                                            if (b2 != 10) {
                                                if (b2 == 11 && ckVarJ.f21619b == 11) {
                                                    return this.f21538a.z();
                                                }
                                            } else if (ckVarJ.f21619b == 10) {
                                                return Long.valueOf(this.f21538a.x());
                                            }
                                        } else if (ckVarJ.f21619b == 11) {
                                            return this.f21538a.A();
                                        }
                                    } else if (ckVarJ.f21619b == 8) {
                                        return Integer.valueOf(this.f21538a.w());
                                    }
                                } else if (ckVarJ.f21619b == 6) {
                                    return Short.valueOf(this.f21538a.v());
                                }
                            } else if (ckVarJ.f21619b == 4) {
                                return Double.valueOf(this.f21538a.y());
                            }
                        } else if (ckVarJ.f21619b == 3) {
                            return Byte.valueOf(this.f21538a.u());
                        }
                    } else if (ckVarJ.f21619b == 2) {
                        return Boolean.valueOf(this.f21538a.t());
                    }
                }
                this.f21539b.e();
                this.f21538a.B();
                return null;
            } catch (Exception e2) {
                throw new bw(e2);
            }
        } finally {
            this.f21539b.e();
            this.f21538a.B();
        }
    }

    public void a(bq bqVar, String str) throws bw {
        a(bqVar, str.getBytes());
    }
}
