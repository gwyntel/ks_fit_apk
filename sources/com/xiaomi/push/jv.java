package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class jv implements jy<jv, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public jc f893a;

    /* renamed from: a, reason: collision with other field name */
    public String f894a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f895a;

    /* renamed from: b, reason: collision with other field name */
    public String f896b;

    /* renamed from: c, reason: collision with other field name */
    public String f897c;

    /* renamed from: d, reason: collision with other field name */
    public String f898d;

    /* renamed from: e, reason: collision with other field name */
    public String f899e;

    /* renamed from: f, reason: collision with other field name */
    public String f900f;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f892a = new ko("XmPushActionUnSubscription");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24348a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24349b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24350c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24351d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24352e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24353f = new kg("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24354g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24355h = new kg("", (byte) 15, 8);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m652a() {
        return this.f894a != null;
    }

    public boolean b() {
        return this.f893a != null;
    }

    public boolean c() {
        return this.f896b != null;
    }

    public boolean d() {
        return this.f897c != null;
    }

    public boolean e() {
        return this.f898d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jv)) {
            return m653a((jv) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f899e != null;
    }

    public boolean g() {
        return this.f900f != null;
    }

    public boolean h() {
        return this.f895a != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionUnSubscription(");
        boolean z3 = false;
        if (m652a()) {
            sb.append("debug:");
            String str = this.f894a;
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
            jc jcVar = this.f893a;
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
        String str2 = this.f896b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f897c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("topic:");
        String str4 = this.f898d;
        if (str4 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str4);
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f899e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str6 = this.f900f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("aliases:");
            List<String> list = this.f895a;
            if (list == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(list);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public jv a(String str) {
        this.f896b = str;
        return this;
    }

    public jv b(String str) {
        this.f897c = str;
        return this;
    }

    public jv c(String str) {
        this.f898d = str;
        return this;
    }

    public jv d(String str) {
        this.f899e = str;
        return this;
    }

    public jv e(String str) {
        this.f900f = str;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m653a(jv jvVar) {
        if (jvVar == null) {
            return false;
        }
        boolean zM652a = m652a();
        boolean zM652a2 = jvVar.m652a();
        if ((zM652a || zM652a2) && !(zM652a && zM652a2 && this.f894a.equals(jvVar.f894a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jvVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f893a.m571a(jvVar.f893a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jvVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f896b.equals(jvVar.f896b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jvVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f897c.equals(jvVar.f897c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jvVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f898d.equals(jvVar.f898d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jvVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f899e.equals(jvVar.f899e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jvVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f900f.equals(jvVar.f900f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jvVar.h();
        if (zH || zH2) {
            return zH && zH2 && this.f895a.equals(jvVar.f895a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        a();
        kjVar.a(f892a);
        if (this.f894a != null && m652a()) {
            kjVar.a(f24348a);
            kjVar.a(this.f894a);
            kjVar.b();
        }
        if (this.f893a != null && b()) {
            kjVar.a(f24349b);
            this.f893a.b(kjVar);
            kjVar.b();
        }
        if (this.f896b != null) {
            kjVar.a(f24350c);
            kjVar.a(this.f896b);
            kjVar.b();
        }
        if (this.f897c != null) {
            kjVar.a(f24351d);
            kjVar.a(this.f897c);
            kjVar.b();
        }
        if (this.f898d != null) {
            kjVar.a(f24352e);
            kjVar.a(this.f898d);
            kjVar.b();
        }
        if (this.f899e != null && f()) {
            kjVar.a(f24353f);
            kjVar.a(this.f899e);
            kjVar.b();
        }
        if (this.f900f != null && g()) {
            kjVar.a(f24354g);
            kjVar.a(this.f900f);
            kjVar.b();
        }
        if (this.f895a != null && h()) {
            kjVar.a(f24355h);
            kjVar.a(new kh((byte) 11, this.f895a.size()));
            Iterator<String> it = this.f895a.iterator();
            while (it.hasNext()) {
                kjVar.a(it.next());
            }
            kjVar.e();
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jv jvVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(jvVar.getClass())) {
            return getClass().getName().compareTo(jvVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m652a()).compareTo(Boolean.valueOf(jvVar.m652a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m652a() && (iA8 = jz.a(this.f894a, jvVar.f894a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jvVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA7 = jz.a(this.f893a, jvVar.f893a)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jvVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA6 = jz.a(this.f896b, jvVar.f896b)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jvVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA5 = jz.a(this.f897c, jvVar.f897c)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jvVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA4 = jz.a(this.f898d, jvVar.f898d)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jvVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA3 = jz.a(this.f899e, jvVar.f899e)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jvVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA2 = jz.a(this.f900f, jvVar.f900f)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jvVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!h() || (iA = jz.a(this.f895a, jvVar.f895a)) == 0) {
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
                        this.f894a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f893a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f896b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f897c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f898d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 11) {
                        this.f899e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f900f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 15) {
                        kh khVarMo671a = kjVar.mo671a();
                        this.f895a = new ArrayList(khVarMo671a.f923a);
                        for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                            this.f895a.add(kjVar.mo675a());
                        }
                        kjVar.i();
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
        if (this.f896b != null) {
            if (this.f897c != null) {
                if (this.f898d != null) {
                    return;
                }
                throw new kk("Required field 'topic' was not present! Struct: " + toString());
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
