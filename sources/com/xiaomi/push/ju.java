package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class ju implements jy<ju, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f882a;

    /* renamed from: a, reason: collision with other field name */
    public jc f883a;

    /* renamed from: a, reason: collision with other field name */
    public String f884a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f885a = new BitSet(3);

    /* renamed from: b, reason: collision with other field name */
    public long f886b;

    /* renamed from: b, reason: collision with other field name */
    public String f887b;

    /* renamed from: c, reason: collision with other field name */
    public long f888c;

    /* renamed from: c, reason: collision with other field name */
    public String f889c;

    /* renamed from: d, reason: collision with other field name */
    public String f890d;

    /* renamed from: e, reason: collision with other field name */
    public String f891e;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f881a = new ko("XmPushActionUnRegistrationResult");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24339a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24340b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24341c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24342d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24343e = new kg("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24344f = new kg("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24345g = new kg("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24346h = new kg("", (byte) 10, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24347i = new kg("", (byte) 10, 10);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m650a() {
        return this.f884a != null;
    }

    public boolean b() {
        return this.f883a != null;
    }

    public boolean c() {
        return this.f887b != null;
    }

    public boolean d() {
        return this.f889c != null;
    }

    public boolean e() {
        return this.f885a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ju)) {
            return m651a((ju) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f890d != null;
    }

    public boolean g() {
        return this.f891e != null;
    }

    public boolean h() {
        return this.f885a.get(1);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f885a.get(2);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistrationResult(");
        boolean z3 = false;
        if (m650a()) {
            sb.append("debug:");
            String str = this.f884a;
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
            jc jcVar = this.f883a;
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
        String str2 = this.f887b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f889c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f882a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f890d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f891e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("unRegisteredAt:");
            sb.append(this.f886b);
        }
        if (i()) {
            sb.append(", ");
            sb.append("costTime:");
            sb.append(this.f888c);
        }
        sb.append(")");
        return sb.toString();
    }

    public void a(boolean z2) {
        this.f885a.set(0, z2);
    }

    public void b(boolean z2) {
        this.f885a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f885a.set(2, z2);
    }

    public String a() {
        return this.f891e;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m649a();
        kjVar.a(f881a);
        if (this.f884a != null && m650a()) {
            kjVar.a(f24339a);
            kjVar.a(this.f884a);
            kjVar.b();
        }
        if (this.f883a != null && b()) {
            kjVar.a(f24340b);
            this.f883a.b(kjVar);
            kjVar.b();
        }
        if (this.f887b != null) {
            kjVar.a(f24341c);
            kjVar.a(this.f887b);
            kjVar.b();
        }
        if (this.f889c != null) {
            kjVar.a(f24342d);
            kjVar.a(this.f889c);
            kjVar.b();
        }
        kjVar.a(f24343e);
        kjVar.a(this.f882a);
        kjVar.b();
        if (this.f890d != null && f()) {
            kjVar.a(f24344f);
            kjVar.a(this.f890d);
            kjVar.b();
        }
        if (this.f891e != null && g()) {
            kjVar.a(f24345g);
            kjVar.a(this.f891e);
            kjVar.b();
        }
        if (h()) {
            kjVar.a(f24346h);
            kjVar.a(this.f886b);
            kjVar.b();
        }
        if (i()) {
            kjVar.a(f24347i);
            kjVar.a(this.f888c);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m651a(ju juVar) {
        if (juVar == null) {
            return false;
        }
        boolean zM650a = m650a();
        boolean zM650a2 = juVar.m650a();
        if ((zM650a || zM650a2) && !(zM650a && zM650a2 && this.f884a.equals(juVar.f884a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = juVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f883a.m571a(juVar.f883a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = juVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f887b.equals(juVar.f887b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = juVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f889c.equals(juVar.f889c))) || this.f882a != juVar.f882a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = juVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f890d.equals(juVar.f890d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = juVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f891e.equals(juVar.f891e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = juVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f886b == juVar.f886b)) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = juVar.i();
        if (zI || zI2) {
            return zI && zI2 && this.f888c == juVar.f888c;
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ju juVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        if (!getClass().equals(juVar.getClass())) {
            return getClass().getName().compareTo(juVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m650a()).compareTo(Boolean.valueOf(juVar.m650a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m650a() && (iA9 = jz.a(this.f884a, juVar.f884a)) != 0) {
            return iA9;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(juVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA8 = jz.a(this.f883a, juVar.f883a)) != 0) {
            return iA8;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(juVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA7 = jz.a(this.f887b, juVar.f887b)) != 0) {
            return iA7;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(juVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA6 = jz.a(this.f889c, juVar.f889c)) != 0) {
            return iA6;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(juVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA5 = jz.a(this.f882a, juVar.f882a)) != 0) {
            return iA5;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(juVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA4 = jz.a(this.f890d, juVar.f890d)) != 0) {
            return iA4;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(juVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA3 = jz.a(this.f891e, juVar.f891e)) != 0) {
            return iA3;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(juVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA2 = jz.a(this.f886b, juVar.f886b)) != 0) {
            return iA2;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(juVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (!i() || (iA = jz.a(this.f888c, juVar.f888c)) == 0) {
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
                if (e()) {
                    m649a();
                    return;
                }
                throw new kk("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f884a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f883a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f887b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f889c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                default:
                    km.a(kjVar, b2);
                    break;
                case 6:
                    if (b2 == 10) {
                        this.f882a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f890d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f891e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 10) {
                        this.f886b = kjVar.mo669a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 10) {
                        this.f888c = kjVar.mo669a();
                        c(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m649a() throws kk {
        if (this.f887b != null) {
            if (this.f889c != null) {
                return;
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
