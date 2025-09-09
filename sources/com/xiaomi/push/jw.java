package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class jw implements jy<jw, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f902a;

    /* renamed from: a, reason: collision with other field name */
    public jc f903a;

    /* renamed from: a, reason: collision with other field name */
    public String f904a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f905a = new BitSet(1);

    /* renamed from: b, reason: collision with other field name */
    public String f906b;

    /* renamed from: c, reason: collision with other field name */
    public String f907c;

    /* renamed from: d, reason: collision with other field name */
    public String f908d;

    /* renamed from: e, reason: collision with other field name */
    public String f909e;

    /* renamed from: f, reason: collision with other field name */
    public String f910f;

    /* renamed from: g, reason: collision with other field name */
    public String f911g;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f901a = new ko("XmPushActionUnSubscriptionResult");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24356a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24357b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24358c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24359d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24360e = new kg("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24361f = new kg("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24362g = new kg("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24363h = new kg("", (byte) 11, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24364i = new kg("", (byte) 11, 10);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m655a() {
        return this.f904a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m657b() {
        return this.f903a != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m658c() {
        return this.f906b != null;
    }

    public boolean d() {
        return this.f907c != null;
    }

    public boolean e() {
        return this.f905a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jw)) {
            return m656a((jw) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f908d != null;
    }

    public boolean g() {
        return this.f909e != null;
    }

    public boolean h() {
        return this.f910f != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f911g != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionUnSubscriptionResult(");
        boolean z3 = false;
        if (m655a()) {
            sb.append("debug:");
            String str = this.f904a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m657b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            jc jcVar = this.f903a;
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
        String str2 = this.f906b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.f907c;
            if (str3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.f902a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f908d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("topic:");
            String str5 = this.f909e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f910f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f911g;
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
        return this.f906b;
    }

    public String b() {
        return this.f909e;
    }

    public String c() {
        return this.f911g;
    }

    public void a(boolean z2) {
        this.f905a.set(0, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m654a();
        kjVar.a(f901a);
        if (this.f904a != null && m655a()) {
            kjVar.a(f24356a);
            kjVar.a(this.f904a);
            kjVar.b();
        }
        if (this.f903a != null && m657b()) {
            kjVar.a(f24357b);
            this.f903a.b(kjVar);
            kjVar.b();
        }
        if (this.f906b != null) {
            kjVar.a(f24358c);
            kjVar.a(this.f906b);
            kjVar.b();
        }
        if (this.f907c != null && d()) {
            kjVar.a(f24359d);
            kjVar.a(this.f907c);
            kjVar.b();
        }
        if (e()) {
            kjVar.a(f24360e);
            kjVar.a(this.f902a);
            kjVar.b();
        }
        if (this.f908d != null && f()) {
            kjVar.a(f24361f);
            kjVar.a(this.f908d);
            kjVar.b();
        }
        if (this.f909e != null && g()) {
            kjVar.a(f24362g);
            kjVar.a(this.f909e);
            kjVar.b();
        }
        if (this.f910f != null && h()) {
            kjVar.a(f24363h);
            kjVar.a(this.f910f);
            kjVar.b();
        }
        if (this.f911g != null && i()) {
            kjVar.a(f24364i);
            kjVar.a(this.f911g);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m656a(jw jwVar) {
        if (jwVar == null) {
            return false;
        }
        boolean zM655a = m655a();
        boolean zM655a2 = jwVar.m655a();
        if ((zM655a || zM655a2) && !(zM655a && zM655a2 && this.f904a.equals(jwVar.f904a))) {
            return false;
        }
        boolean zM657b = m657b();
        boolean zM657b2 = jwVar.m657b();
        if ((zM657b || zM657b2) && !(zM657b && zM657b2 && this.f903a.m571a(jwVar.f903a))) {
            return false;
        }
        boolean zM658c = m658c();
        boolean zM658c2 = jwVar.m658c();
        if ((zM658c || zM658c2) && !(zM658c && zM658c2 && this.f906b.equals(jwVar.f906b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jwVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f907c.equals(jwVar.f907c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jwVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f902a == jwVar.f902a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jwVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f908d.equals(jwVar.f908d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jwVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f909e.equals(jwVar.f909e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jwVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f910f.equals(jwVar.f910f))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jwVar.i();
        if (zI || zI2) {
            return zI && zI2 && this.f911g.equals(jwVar.f911g);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jw jwVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        if (!getClass().equals(jwVar.getClass())) {
            return getClass().getName().compareTo(jwVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m655a()).compareTo(Boolean.valueOf(jwVar.m655a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m655a() && (iA9 = jz.a(this.f904a, jwVar.f904a)) != 0) {
            return iA9;
        }
        int iCompareTo2 = Boolean.valueOf(m657b()).compareTo(Boolean.valueOf(jwVar.m657b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m657b() && (iA8 = jz.a(this.f903a, jwVar.f903a)) != 0) {
            return iA8;
        }
        int iCompareTo3 = Boolean.valueOf(m658c()).compareTo(Boolean.valueOf(jwVar.m658c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m658c() && (iA7 = jz.a(this.f906b, jwVar.f906b)) != 0) {
            return iA7;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jwVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA6 = jz.a(this.f907c, jwVar.f907c)) != 0) {
            return iA6;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jwVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA5 = jz.a(this.f902a, jwVar.f902a)) != 0) {
            return iA5;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jwVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA4 = jz.a(this.f908d, jwVar.f908d)) != 0) {
            return iA4;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jwVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA3 = jz.a(this.f909e, jwVar.f909e)) != 0) {
            return iA3;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jwVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA2 = jz.a(this.f910f, jwVar.f910f)) != 0) {
            return iA2;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jwVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (!i() || (iA = jz.a(this.f911g, jwVar.f911g)) == 0) {
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
                m654a();
                return;
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f904a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f903a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f906b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f907c = kjVar.mo675a();
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
                        this.f902a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f908d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f909e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f910f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 11) {
                        this.f911g = kjVar.mo675a();
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
    public void m654a() throws kk {
        if (this.f906b != null) {
            return;
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
