package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class gf implements jy<gf, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public byte f451a;

    /* renamed from: a, reason: collision with other field name */
    public int f452a;

    /* renamed from: a, reason: collision with other field name */
    public String f453a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f454a = new BitSet(6);

    /* renamed from: b, reason: collision with other field name */
    public int f455b;

    /* renamed from: b, reason: collision with other field name */
    public String f456b;

    /* renamed from: c, reason: collision with other field name */
    public int f457c;

    /* renamed from: c, reason: collision with other field name */
    public String f458c;

    /* renamed from: d, reason: collision with other field name */
    public int f459d;

    /* renamed from: d, reason: collision with other field name */
    public String f460d;

    /* renamed from: e, reason: collision with other field name */
    public int f461e;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f450a = new ko("StatsEvent");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f23815a = new kg("", (byte) 3, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f23816b = new kg("", (byte) 8, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f23817c = new kg("", (byte) 8, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f23818d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f23819e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f23820f = new kg("", (byte) 8, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f23821g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f23822h = new kg("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f23823i = new kg("", (byte) 8, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f23824j = new kg("", (byte) 8, 10);

    public gf a(byte b2) {
        this.f451a = b2;
        a(true);
        return this;
    }

    public boolean b() {
        return this.f454a.get(1);
    }

    public boolean c() {
        return this.f454a.get(2);
    }

    public boolean d() {
        return this.f453a != null;
    }

    public boolean e() {
        return this.f456b != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof gf)) {
            return m430a((gf) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f454a.get(3);
    }

    public boolean g() {
        return this.f458c != null;
    }

    public boolean h() {
        return this.f460d != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f454a.get(4);
    }

    public boolean j() {
        return this.f454a.get(5);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvent(");
        sb.append("chid:");
        sb.append((int) this.f451a);
        sb.append(", ");
        sb.append("type:");
        sb.append(this.f452a);
        sb.append(", ");
        sb.append("value:");
        sb.append(this.f455b);
        sb.append(", ");
        sb.append("connpt:");
        String str = this.f453a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        if (e()) {
            sb.append(", ");
            sb.append("host:");
            String str2 = this.f456b;
            if (str2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str2);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("subvalue:");
            sb.append(this.f457c);
        }
        if (g()) {
            sb.append(", ");
            sb.append("annotation:");
            String str3 = this.f458c;
            if (str3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str3);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("user:");
            String str4 = this.f460d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("time:");
            sb.append(this.f459d);
        }
        if (j()) {
            sb.append(", ");
            sb.append("clientIp:");
            sb.append(this.f461e);
        }
        sb.append(")");
        return sb.toString();
    }

    public void b(boolean z2) {
        this.f454a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f454a.set(2, z2);
    }

    public void d(boolean z2) {
        this.f454a.set(3, z2);
    }

    public void e(boolean z2) {
        this.f454a.set(4, z2);
    }

    public void f(boolean z2) {
        this.f454a.set(5, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m429a() {
        return this.f454a.get(0);
    }

    public gf b(int i2) {
        this.f455b = i2;
        c(true);
        return this;
    }

    public gf c(int i2) {
        this.f457c = i2;
        d(true);
        return this;
    }

    public gf d(String str) {
        this.f460d = str;
        return this;
    }

    public void a(boolean z2) {
        this.f454a.set(0, z2);
    }

    public gf d(int i2) {
        this.f459d = i2;
        e(true);
        return this;
    }

    public gf a(int i2) {
        this.f452a = i2;
        b(true);
        return this;
    }

    public gf b(String str) {
        this.f456b = str;
        return this;
    }

    public gf c(String str) {
        this.f458c = str;
        return this;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) {
        a();
        kjVar.a(f450a);
        kjVar.a(f23815a);
        kjVar.a(this.f451a);
        kjVar.b();
        kjVar.a(f23816b);
        kjVar.mo679a(this.f452a);
        kjVar.b();
        kjVar.a(f23817c);
        kjVar.mo679a(this.f455b);
        kjVar.b();
        if (this.f453a != null) {
            kjVar.a(f23818d);
            kjVar.a(this.f453a);
            kjVar.b();
        }
        if (this.f456b != null && e()) {
            kjVar.a(f23819e);
            kjVar.a(this.f456b);
            kjVar.b();
        }
        if (f()) {
            kjVar.a(f23820f);
            kjVar.mo679a(this.f457c);
            kjVar.b();
        }
        if (this.f458c != null && g()) {
            kjVar.a(f23821g);
            kjVar.a(this.f458c);
            kjVar.b();
        }
        if (this.f460d != null && h()) {
            kjVar.a(f23822h);
            kjVar.a(this.f460d);
            kjVar.b();
        }
        if (i()) {
            kjVar.a(f23823i);
            kjVar.mo679a(this.f459d);
            kjVar.b();
        }
        if (j()) {
            kjVar.a(f23824j);
            kjVar.mo679a(this.f461e);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public gf a(String str) {
        this.f453a = str;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m430a(gf gfVar) {
        if (gfVar == null || this.f451a != gfVar.f451a || this.f452a != gfVar.f452a || this.f455b != gfVar.f455b) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = gfVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f453a.equals(gfVar.f453a))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = gfVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f456b.equals(gfVar.f456b))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = gfVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f457c == gfVar.f457c)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = gfVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f458c.equals(gfVar.f458c))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = gfVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f460d.equals(gfVar.f460d))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = gfVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f459d == gfVar.f459d)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = gfVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f461e == gfVar.f461e;
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(gf gfVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        int iA10;
        if (!getClass().equals(gfVar.getClass())) {
            return getClass().getName().compareTo(gfVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m429a()).compareTo(Boolean.valueOf(gfVar.m429a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m429a() && (iA10 = jz.a(this.f451a, gfVar.f451a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(gfVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA9 = jz.a(this.f452a, gfVar.f452a)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(gfVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = jz.a(this.f455b, gfVar.f455b)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(gfVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jz.a(this.f453a, gfVar.f453a)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(gfVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jz.a(this.f456b, gfVar.f456b)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(gfVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jz.a(this.f457c, gfVar.f457c)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(gfVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jz.a(this.f458c, gfVar.f458c)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(gfVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jz.a(this.f460d, gfVar.f460d)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(gfVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jz.a(this.f459d, gfVar.f459d)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(gfVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jz.a(this.f461e, gfVar.f461e)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // com.xiaomi.push.jy
    public void a(kj kjVar) throws kk {
        kjVar.mo674a();
        while (true) {
            kg kgVarMo670a = kjVar.mo670a();
            byte b2 = kgVarMo670a.f24379a;
            if (b2 == 0) {
                kjVar.f();
                if (m429a()) {
                    if (b()) {
                        if (c()) {
                            a();
                            return;
                        }
                        throw new kk("Required field 'value' was not found in serialized data! Struct: " + toString());
                    }
                    throw new kk("Required field 'type' was not found in serialized data! Struct: " + toString());
                }
                throw new kk("Required field 'chid' was not found in serialized data! Struct: " + toString());
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 3) {
                        this.f451a = kjVar.a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 8) {
                        this.f452a = kjVar.mo668a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 8) {
                        this.f455b = kjVar.mo668a();
                        c(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f453a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f456b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 8) {
                        this.f457c = kjVar.mo668a();
                        d(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f458c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f460d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 8) {
                        this.f459d = kjVar.mo668a();
                        e(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 8) {
                        this.f461e = kjVar.mo668a();
                        f(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                default:
                    km.a(kjVar, b2);
                    break;
            }
            kjVar.g();
        }
    }

    public void a() throws kk {
        if (this.f453a != null) {
            return;
        }
        throw new kk("Required field 'connpt' was not present! Struct: " + toString());
    }
}
