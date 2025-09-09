package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class js implements jy<js, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f857a;

    /* renamed from: a, reason: collision with other field name */
    public jc f858a;

    /* renamed from: a, reason: collision with other field name */
    public String f859a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f860a = new BitSet(1);

    /* renamed from: b, reason: collision with other field name */
    public String f861b;

    /* renamed from: c, reason: collision with other field name */
    public String f862c;

    /* renamed from: d, reason: collision with other field name */
    public String f863d;

    /* renamed from: e, reason: collision with other field name */
    public String f864e;

    /* renamed from: f, reason: collision with other field name */
    public String f865f;

    /* renamed from: g, reason: collision with other field name */
    public String f866g;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f856a = new ko("XmPushActionSubscriptionResult");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24318a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24319b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24320c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24321d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24322e = new kg("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24323f = new kg("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24324g = new kg("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24325h = new kg("", (byte) 11, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24326i = new kg("", (byte) 11, 10);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m643a() {
        return this.f859a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m645b() {
        return this.f858a != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m646c() {
        return this.f861b != null;
    }

    public boolean d() {
        return this.f862c != null;
    }

    public boolean e() {
        return this.f860a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof js)) {
            return m644a((js) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f863d != null;
    }

    public boolean g() {
        return this.f864e != null;
    }

    public boolean h() {
        return this.f865f != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f866g != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSubscriptionResult(");
        boolean z3 = false;
        if (m643a()) {
            sb.append("debug:");
            String str = this.f859a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m645b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            jc jcVar = this.f858a;
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
        String str2 = this.f861b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.f862c;
            if (str3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.f857a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f863d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("topic:");
            String str5 = this.f864e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f865f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f866g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public String a() {
        return this.f861b;
    }

    public String b() {
        return this.f864e;
    }

    public String c() {
        return this.f866g;
    }

    public void a(boolean z2) {
        this.f860a.set(0, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m642a();
        kjVar.a(f856a);
        if (this.f859a != null && m643a()) {
            kjVar.a(f24318a);
            kjVar.a(this.f859a);
            kjVar.b();
        }
        if (this.f858a != null && m645b()) {
            kjVar.a(f24319b);
            this.f858a.b(kjVar);
            kjVar.b();
        }
        if (this.f861b != null) {
            kjVar.a(f24320c);
            kjVar.a(this.f861b);
            kjVar.b();
        }
        if (this.f862c != null && d()) {
            kjVar.a(f24321d);
            kjVar.a(this.f862c);
            kjVar.b();
        }
        if (e()) {
            kjVar.a(f24322e);
            kjVar.a(this.f857a);
            kjVar.b();
        }
        if (this.f863d != null && f()) {
            kjVar.a(f24323f);
            kjVar.a(this.f863d);
            kjVar.b();
        }
        if (this.f864e != null && g()) {
            kjVar.a(f24324g);
            kjVar.a(this.f864e);
            kjVar.b();
        }
        if (this.f865f != null && h()) {
            kjVar.a(f24325h);
            kjVar.a(this.f865f);
            kjVar.b();
        }
        if (this.f866g != null && i()) {
            kjVar.a(f24326i);
            kjVar.a(this.f866g);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m644a(js jsVar) {
        if (jsVar == null) {
            return false;
        }
        boolean zM643a = m643a();
        boolean zM643a2 = jsVar.m643a();
        if ((zM643a || zM643a2) && !(zM643a && zM643a2 && this.f859a.equals(jsVar.f859a))) {
            return false;
        }
        boolean zM645b = m645b();
        boolean zM645b2 = jsVar.m645b();
        if ((zM645b || zM645b2) && !(zM645b && zM645b2 && this.f858a.m571a(jsVar.f858a))) {
            return false;
        }
        boolean zM646c = m646c();
        boolean zM646c2 = jsVar.m646c();
        if ((zM646c || zM646c2) && !(zM646c && zM646c2 && this.f861b.equals(jsVar.f861b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jsVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f862c.equals(jsVar.f862c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jsVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f857a == jsVar.f857a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jsVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f863d.equals(jsVar.f863d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jsVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f864e.equals(jsVar.f864e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jsVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f865f.equals(jsVar.f865f))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jsVar.i();
        if (zI || zI2) {
            return zI && zI2 && this.f866g.equals(jsVar.f866g);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(js jsVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        if (!getClass().equals(jsVar.getClass())) {
            return getClass().getName().compareTo(jsVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m643a()).compareTo(Boolean.valueOf(jsVar.m643a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m643a() && (iA9 = jz.a(this.f859a, jsVar.f859a)) != 0) {
            return iA9;
        }
        int iCompareTo2 = Boolean.valueOf(m645b()).compareTo(Boolean.valueOf(jsVar.m645b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m645b() && (iA8 = jz.a(this.f858a, jsVar.f858a)) != 0) {
            return iA8;
        }
        int iCompareTo3 = Boolean.valueOf(m646c()).compareTo(Boolean.valueOf(jsVar.m646c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m646c() && (iA7 = jz.a(this.f861b, jsVar.f861b)) != 0) {
            return iA7;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jsVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA6 = jz.a(this.f862c, jsVar.f862c)) != 0) {
            return iA6;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jsVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA5 = jz.a(this.f857a, jsVar.f857a)) != 0) {
            return iA5;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jsVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA4 = jz.a(this.f863d, jsVar.f863d)) != 0) {
            return iA4;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jsVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA3 = jz.a(this.f864e, jsVar.f864e)) != 0) {
            return iA3;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jsVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA2 = jz.a(this.f865f, jsVar.f865f)) != 0) {
            return iA2;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jsVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (!i() || (iA = jz.a(this.f866g, jsVar.f866g)) == 0) {
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
                m642a();
                return;
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f859a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f858a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f861b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f862c = kjVar.mo675a();
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
                        this.f857a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f863d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f864e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f865f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 11) {
                        this.f866g = kjVar.mo675a();
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
    public void m642a() throws kk {
        if (this.f861b != null) {
            return;
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
