package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class jt implements jy<jt, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f868a;

    /* renamed from: a, reason: collision with other field name */
    public jc f869a;

    /* renamed from: a, reason: collision with other field name */
    public String f870a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f871a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    public boolean f872a = true;

    /* renamed from: b, reason: collision with other field name */
    public String f873b;

    /* renamed from: c, reason: collision with other field name */
    public String f874c;

    /* renamed from: d, reason: collision with other field name */
    public String f875d;

    /* renamed from: e, reason: collision with other field name */
    public String f876e;

    /* renamed from: f, reason: collision with other field name */
    public String f877f;

    /* renamed from: g, reason: collision with other field name */
    public String f878g;

    /* renamed from: h, reason: collision with other field name */
    public String f879h;

    /* renamed from: i, reason: collision with other field name */
    public String f880i;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f867a = new ko("XmPushActionUnRegistration");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24327a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24328b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24329c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24330d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24331e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24332f = new kg("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24333g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24334h = new kg("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24335i = new kg("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24336j = new kg("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24337k = new kg("", (byte) 2, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final kg f24338l = new kg("", (byte) 10, 12);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m647a() {
        return this.f870a != null;
    }

    public boolean b() {
        return this.f869a != null;
    }

    public boolean c() {
        return this.f873b != null;
    }

    public boolean d() {
        return this.f874c != null;
    }

    public boolean e() {
        return this.f875d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jt)) {
            return m648a((jt) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f876e != null;
    }

    public boolean g() {
        return this.f877f != null;
    }

    public boolean h() {
        return this.f878g != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f879h != null;
    }

    public boolean j() {
        return this.f880i != null;
    }

    public boolean k() {
        return this.f871a.get(0);
    }

    public boolean l() {
        return this.f871a.get(1);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistration(");
        boolean z3 = false;
        if (m647a()) {
            sb.append("debug:");
            String str = this.f870a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            jc jcVar = this.f869a;
            if (jcVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jcVar);
            }
        } else {
            z3 = z2;
        }
        if (!z3) {
            sb.append(", ");
        }
        sb.append("id:");
        String str2 = this.f873b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f874c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("regId:");
            String str4 = this.f875d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str5 = this.f876e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f877f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("token:");
            String str7 = this.f878g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str8 = this.f879h;
            if (str8 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str8);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.f880i;
            if (str9 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str9);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("needAck:");
            sb.append(this.f872a);
        }
        if (l()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f868a);
        }
        sb.append(")");
        return sb.toString();
    }

    public jt a(String str) {
        this.f873b = str;
        return this;
    }

    public jt b(String str) {
        this.f874c = str;
        return this;
    }

    public jt c(String str) {
        this.f875d = str;
        return this;
    }

    public jt d(String str) {
        this.f877f = str;
        return this;
    }

    public jt e(String str) {
        this.f878g = str;
        return this;
    }

    public void a(boolean z2) {
        this.f871a.set(0, z2);
    }

    public void b(boolean z2) {
        this.f871a.set(1, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m648a(jt jtVar) {
        if (jtVar == null) {
            return false;
        }
        boolean zM647a = m647a();
        boolean zM647a2 = jtVar.m647a();
        if ((zM647a || zM647a2) && !(zM647a && zM647a2 && this.f870a.equals(jtVar.f870a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jtVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f869a.m571a(jtVar.f869a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jtVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f873b.equals(jtVar.f873b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jtVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f874c.equals(jtVar.f874c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jtVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f875d.equals(jtVar.f875d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jtVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f876e.equals(jtVar.f876e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jtVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f877f.equals(jtVar.f877f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jtVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f878g.equals(jtVar.f878g))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jtVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f879h.equals(jtVar.f879h))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jtVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f880i.equals(jtVar.f880i))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jtVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f872a == jtVar.f872a)) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jtVar.l();
        if (zL || zL2) {
            return zL && zL2 && this.f868a == jtVar.f868a;
        }
        return true;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        a();
        kjVar.a(f867a);
        if (this.f870a != null && m647a()) {
            kjVar.a(f24327a);
            kjVar.a(this.f870a);
            kjVar.b();
        }
        if (this.f869a != null && b()) {
            kjVar.a(f24328b);
            this.f869a.b(kjVar);
            kjVar.b();
        }
        if (this.f873b != null) {
            kjVar.a(f24329c);
            kjVar.a(this.f873b);
            kjVar.b();
        }
        if (this.f874c != null) {
            kjVar.a(f24330d);
            kjVar.a(this.f874c);
            kjVar.b();
        }
        if (this.f875d != null && e()) {
            kjVar.a(f24331e);
            kjVar.a(this.f875d);
            kjVar.b();
        }
        if (this.f876e != null && f()) {
            kjVar.a(f24332f);
            kjVar.a(this.f876e);
            kjVar.b();
        }
        if (this.f877f != null && g()) {
            kjVar.a(f24333g);
            kjVar.a(this.f877f);
            kjVar.b();
        }
        if (this.f878g != null && h()) {
            kjVar.a(f24334h);
            kjVar.a(this.f878g);
            kjVar.b();
        }
        if (this.f879h != null && i()) {
            kjVar.a(f24335i);
            kjVar.a(this.f879h);
            kjVar.b();
        }
        if (this.f880i != null && j()) {
            kjVar.a(f24336j);
            kjVar.a(this.f880i);
            kjVar.b();
        }
        if (k()) {
            kjVar.a(f24337k);
            kjVar.a(this.f872a);
            kjVar.b();
        }
        if (l()) {
            kjVar.a(f24338l);
            kjVar.a(this.f868a);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jt jtVar) {
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
        int iA11;
        int iA12;
        if (!getClass().equals(jtVar.getClass())) {
            return getClass().getName().compareTo(jtVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m647a()).compareTo(Boolean.valueOf(jtVar.m647a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m647a() && (iA12 = jz.a(this.f870a, jtVar.f870a)) != 0) {
            return iA12;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jtVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA11 = jz.a(this.f869a, jtVar.f869a)) != 0) {
            return iA11;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jtVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA10 = jz.a(this.f873b, jtVar.f873b)) != 0) {
            return iA10;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jtVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA9 = jz.a(this.f874c, jtVar.f874c)) != 0) {
            return iA9;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jtVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA8 = jz.a(this.f875d, jtVar.f875d)) != 0) {
            return iA8;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jtVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA7 = jz.a(this.f876e, jtVar.f876e)) != 0) {
            return iA7;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jtVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA6 = jz.a(this.f877f, jtVar.f877f)) != 0) {
            return iA6;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jtVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA5 = jz.a(this.f878g, jtVar.f878g)) != 0) {
            return iA5;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jtVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA4 = jz.a(this.f879h, jtVar.f879h)) != 0) {
            return iA4;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jtVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA3 = jz.a(this.f880i, jtVar.f880i)) != 0) {
            return iA3;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jtVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA2 = jz.a(this.f872a, jtVar.f872a)) != 0) {
            return iA2;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jtVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (!l() || (iA = jz.a(this.f868a, jtVar.f868a)) == 0) {
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
                a();
                return;
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f870a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f869a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f873b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f874c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f875d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 11) {
                        this.f876e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f877f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f878g = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f879h = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 11) {
                        this.f880i = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 2) {
                        this.f872a = kjVar.mo680a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 12:
                    if (b2 == 10) {
                        this.f868a = kjVar.mo669a();
                        b(true);
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
        if (this.f873b != null) {
            if (this.f874c != null) {
                return;
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
