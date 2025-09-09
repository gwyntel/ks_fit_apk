package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class jr implements jy<jr, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public jc f848a;

    /* renamed from: a, reason: collision with other field name */
    public String f849a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f850a;

    /* renamed from: b, reason: collision with other field name */
    public String f851b;

    /* renamed from: c, reason: collision with other field name */
    public String f852c;

    /* renamed from: d, reason: collision with other field name */
    public String f853d;

    /* renamed from: e, reason: collision with other field name */
    public String f854e;

    /* renamed from: f, reason: collision with other field name */
    public String f855f;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f847a = new ko("XmPushActionSubscription");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24310a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24311b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24312c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24313d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24314e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24315f = new kg("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24316g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24317h = new kg("", (byte) 15, 8);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m640a() {
        return this.f849a != null;
    }

    public boolean b() {
        return this.f848a != null;
    }

    public boolean c() {
        return this.f851b != null;
    }

    public boolean d() {
        return this.f852c != null;
    }

    public boolean e() {
        return this.f853d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jr)) {
            return m641a((jr) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f854e != null;
    }

    public boolean g() {
        return this.f855f != null;
    }

    public boolean h() {
        return this.f850a != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSubscription(");
        boolean z3 = false;
        if (m640a()) {
            sb.append("debug:");
            String str = this.f849a;
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
            jc jcVar = this.f848a;
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
        String str2 = this.f851b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f852c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("topic:");
        String str4 = this.f853d;
        if (str4 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str4);
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f854e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str6 = this.f855f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("aliases:");
            List<String> list = this.f850a;
            if (list == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(list);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public jr a(String str) {
        this.f851b = str;
        return this;
    }

    public jr b(String str) {
        this.f852c = str;
        return this;
    }

    public jr c(String str) {
        this.f853d = str;
        return this;
    }

    public jr d(String str) {
        this.f854e = str;
        return this;
    }

    public jr e(String str) {
        this.f855f = str;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m641a(jr jrVar) {
        if (jrVar == null) {
            return false;
        }
        boolean zM640a = m640a();
        boolean zM640a2 = jrVar.m640a();
        if ((zM640a || zM640a2) && !(zM640a && zM640a2 && this.f849a.equals(jrVar.f849a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jrVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f848a.m571a(jrVar.f848a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jrVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f851b.equals(jrVar.f851b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jrVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f852c.equals(jrVar.f852c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jrVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f853d.equals(jrVar.f853d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jrVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f854e.equals(jrVar.f854e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jrVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f855f.equals(jrVar.f855f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jrVar.h();
        if (zH || zH2) {
            return zH && zH2 && this.f850a.equals(jrVar.f850a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        a();
        kjVar.a(f847a);
        if (this.f849a != null && m640a()) {
            kjVar.a(f24310a);
            kjVar.a(this.f849a);
            kjVar.b();
        }
        if (this.f848a != null && b()) {
            kjVar.a(f24311b);
            this.f848a.b(kjVar);
            kjVar.b();
        }
        if (this.f851b != null) {
            kjVar.a(f24312c);
            kjVar.a(this.f851b);
            kjVar.b();
        }
        if (this.f852c != null) {
            kjVar.a(f24313d);
            kjVar.a(this.f852c);
            kjVar.b();
        }
        if (this.f853d != null) {
            kjVar.a(f24314e);
            kjVar.a(this.f853d);
            kjVar.b();
        }
        if (this.f854e != null && f()) {
            kjVar.a(f24315f);
            kjVar.a(this.f854e);
            kjVar.b();
        }
        if (this.f855f != null && g()) {
            kjVar.a(f24316g);
            kjVar.a(this.f855f);
            kjVar.b();
        }
        if (this.f850a != null && h()) {
            kjVar.a(f24317h);
            kjVar.a(new kh((byte) 11, this.f850a.size()));
            Iterator<String> it = this.f850a.iterator();
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
    public int compareTo(jr jrVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(jrVar.getClass())) {
            return getClass().getName().compareTo(jrVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m640a()).compareTo(Boolean.valueOf(jrVar.m640a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m640a() && (iA8 = jz.a(this.f849a, jrVar.f849a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jrVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA7 = jz.a(this.f848a, jrVar.f848a)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jrVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA6 = jz.a(this.f851b, jrVar.f851b)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jrVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA5 = jz.a(this.f852c, jrVar.f852c)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jrVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA4 = jz.a(this.f853d, jrVar.f853d)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jrVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA3 = jz.a(this.f854e, jrVar.f854e)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jrVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA2 = jz.a(this.f855f, jrVar.f855f)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jrVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!h() || (iA = jz.a(this.f850a, jrVar.f850a)) == 0) {
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
                        this.f849a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f848a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f851b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f852c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f853d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 11) {
                        this.f854e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f855f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 15) {
                        kh khVarMo671a = kjVar.mo671a();
                        this.f850a = new ArrayList(khVarMo671a.f923a);
                        for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                            this.f850a.add(kjVar.mo675a());
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
        if (this.f851b != null) {
            if (this.f852c != null) {
                if (this.f853d != null) {
                    return;
                }
                throw new kk("Required field 'topic' was not present! Struct: " + toString());
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
