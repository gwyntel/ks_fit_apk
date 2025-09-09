package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class iz implements jy<iz, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f631a;

    /* renamed from: a, reason: collision with other field name */
    public ja f632a;

    /* renamed from: a, reason: collision with other field name */
    public jc f633a;

    /* renamed from: a, reason: collision with other field name */
    public String f634a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f635a = new BitSet(4);

    /* renamed from: a, reason: collision with other field name */
    public boolean f636a = false;

    /* renamed from: b, reason: collision with other field name */
    public long f637b;

    /* renamed from: b, reason: collision with other field name */
    public String f638b;

    /* renamed from: c, reason: collision with other field name */
    public long f639c;

    /* renamed from: c, reason: collision with other field name */
    public String f640c;

    /* renamed from: d, reason: collision with other field name */
    public String f641d;

    /* renamed from: e, reason: collision with other field name */
    public String f642e;

    /* renamed from: f, reason: collision with other field name */
    public String f643f;

    /* renamed from: g, reason: collision with other field name */
    public String f644g;

    /* renamed from: h, reason: collision with other field name */
    public String f645h;

    /* renamed from: i, reason: collision with other field name */
    public String f646i;

    /* renamed from: j, reason: collision with other field name */
    public String f647j;

    /* renamed from: k, reason: collision with other field name */
    public String f648k;

    /* renamed from: l, reason: collision with other field name */
    public String f649l;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f630a = new ko("PushMessage");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24125a = new kg("", (byte) 12, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24126b = new kg("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24127c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24128d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24129e = new kg("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24130f = new kg("", (byte) 10, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24131g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24132h = new kg("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24133i = new kg("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24134j = new kg("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24135k = new kg("", (byte) 11, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final kg f24136l = new kg("", (byte) 12, 12);

    /* renamed from: m, reason: collision with root package name */
    private static final kg f24137m = new kg("", (byte) 11, 13);

    /* renamed from: n, reason: collision with root package name */
    private static final kg f24138n = new kg("", (byte) 2, 14);

    /* renamed from: o, reason: collision with root package name */
    private static final kg f24139o = new kg("", (byte) 11, 15);

    /* renamed from: p, reason: collision with root package name */
    private static final kg f24140p = new kg("", (byte) 10, 16);

    /* renamed from: q, reason: collision with root package name */
    private static final kg f24141q = new kg("", (byte) 11, 20);

    /* renamed from: r, reason: collision with root package name */
    private static final kg f24142r = new kg("", (byte) 11, 21);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m542a() {
        return this.f633a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m544b() {
        return this.f634a != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m545c() {
        return this.f638b != null;
    }

    public boolean d() {
        return this.f640c != null;
    }

    public boolean e() {
        return this.f635a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iz)) {
            return m543a((iz) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f635a.get(1);
    }

    public boolean g() {
        return this.f641d != null;
    }

    public boolean h() {
        return this.f642e != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f643f != null;
    }

    public boolean j() {
        return this.f644g != null;
    }

    public boolean k() {
        return this.f645h != null;
    }

    public boolean l() {
        return this.f632a != null;
    }

    public boolean m() {
        return this.f646i != null;
    }

    public boolean n() {
        return this.f635a.get(2);
    }

    public boolean o() {
        return this.f647j != null;
    }

    public boolean p() {
        return this.f635a.get(3);
    }

    public boolean q() {
        return this.f648k != null;
    }

    public boolean r() {
        return this.f649l != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PushMessage(");
        if (m542a()) {
            sb.append("to:");
            jc jcVar = this.f633a;
            if (jcVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jcVar);
            }
            sb.append(", ");
        }
        sb.append("id:");
        String str = this.f634a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("appId:");
        String str2 = this.f638b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("payload:");
        String str3 = this.f640c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("createAt:");
            sb.append(this.f631a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("ttl:");
            sb.append(this.f637b);
        }
        if (g()) {
            sb.append(", ");
            sb.append("collapseKey:");
            String str4 = this.f641d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f642e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("regId:");
            String str6 = this.f643f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f644g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("topic:");
            String str8 = this.f645h;
            if (str8 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str8);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("metaInfo:");
            ja jaVar = this.f632a;
            if (jaVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jaVar);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.f646i;
            if (str9 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str9);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("isOnline:");
            sb.append(this.f636a);
        }
        if (o()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str10 = this.f647j;
            if (str10 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str10);
            }
        }
        if (p()) {
            sb.append(", ");
            sb.append("miid:");
            sb.append(this.f639c);
        }
        if (q()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            String str11 = this.f648k;
            if (str11 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str11);
            }
        }
        if (r()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str12 = this.f649l;
            if (str12 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str12);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m540a() {
        return this.f634a;
    }

    public String b() {
        return this.f638b;
    }

    public String c() {
        return this.f640c;
    }

    public void d(boolean z2) {
        this.f635a.set(3, z2);
    }

    public long a() {
        return this.f631a;
    }

    public void b(boolean z2) {
        this.f635a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f635a.set(2, z2);
    }

    public void a(boolean z2) {
        this.f635a.set(0, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m541a();
        kjVar.a(f630a);
        if (this.f633a != null && m542a()) {
            kjVar.a(f24125a);
            this.f633a.b(kjVar);
            kjVar.b();
        }
        if (this.f634a != null) {
            kjVar.a(f24126b);
            kjVar.a(this.f634a);
            kjVar.b();
        }
        if (this.f638b != null) {
            kjVar.a(f24127c);
            kjVar.a(this.f638b);
            kjVar.b();
        }
        if (this.f640c != null) {
            kjVar.a(f24128d);
            kjVar.a(this.f640c);
            kjVar.b();
        }
        if (e()) {
            kjVar.a(f24129e);
            kjVar.a(this.f631a);
            kjVar.b();
        }
        if (f()) {
            kjVar.a(f24130f);
            kjVar.a(this.f637b);
            kjVar.b();
        }
        if (this.f641d != null && g()) {
            kjVar.a(f24131g);
            kjVar.a(this.f641d);
            kjVar.b();
        }
        if (this.f642e != null && h()) {
            kjVar.a(f24132h);
            kjVar.a(this.f642e);
            kjVar.b();
        }
        if (this.f643f != null && i()) {
            kjVar.a(f24133i);
            kjVar.a(this.f643f);
            kjVar.b();
        }
        if (this.f644g != null && j()) {
            kjVar.a(f24134j);
            kjVar.a(this.f644g);
            kjVar.b();
        }
        if (this.f645h != null && k()) {
            kjVar.a(f24135k);
            kjVar.a(this.f645h);
            kjVar.b();
        }
        if (this.f632a != null && l()) {
            kjVar.a(f24136l);
            this.f632a.b(kjVar);
            kjVar.b();
        }
        if (this.f646i != null && m()) {
            kjVar.a(f24137m);
            kjVar.a(this.f646i);
            kjVar.b();
        }
        if (n()) {
            kjVar.a(f24138n);
            kjVar.a(this.f636a);
            kjVar.b();
        }
        if (this.f647j != null && o()) {
            kjVar.a(f24139o);
            kjVar.a(this.f647j);
            kjVar.b();
        }
        if (p()) {
            kjVar.a(f24140p);
            kjVar.a(this.f639c);
            kjVar.b();
        }
        if (this.f648k != null && q()) {
            kjVar.a(f24141q);
            kjVar.a(this.f648k);
            kjVar.b();
        }
        if (this.f649l != null && r()) {
            kjVar.a(f24142r);
            kjVar.a(this.f649l);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m543a(iz izVar) {
        if (izVar == null) {
            return false;
        }
        boolean zM542a = m542a();
        boolean zM542a2 = izVar.m542a();
        if ((zM542a || zM542a2) && !(zM542a && zM542a2 && this.f633a.m571a(izVar.f633a))) {
            return false;
        }
        boolean zM544b = m544b();
        boolean zM544b2 = izVar.m544b();
        if ((zM544b || zM544b2) && !(zM544b && zM544b2 && this.f634a.equals(izVar.f634a))) {
            return false;
        }
        boolean zM545c = m545c();
        boolean zM545c2 = izVar.m545c();
        if ((zM545c || zM545c2) && !(zM545c && zM545c2 && this.f638b.equals(izVar.f638b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = izVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f640c.equals(izVar.f640c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = izVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f631a == izVar.f631a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = izVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f637b == izVar.f637b)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = izVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f641d.equals(izVar.f641d))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = izVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f642e.equals(izVar.f642e))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = izVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f643f.equals(izVar.f643f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = izVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f644g.equals(izVar.f644g))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = izVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f645h.equals(izVar.f645h))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = izVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f632a.m563a(izVar.f632a))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = izVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f646i.equals(izVar.f646i))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = izVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f636a == izVar.f636a)) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = izVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.f647j.equals(izVar.f647j))) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = izVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.f639c == izVar.f639c)) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = izVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.f648k.equals(izVar.f648k))) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = izVar.r();
        if (zR || zR2) {
            return zR && zR2 && this.f649l.equals(izVar.f649l);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iz izVar) {
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
        int iA13;
        int iA14;
        int iA15;
        int iA16;
        int iA17;
        int iA18;
        if (!getClass().equals(izVar.getClass())) {
            return getClass().getName().compareTo(izVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m542a()).compareTo(Boolean.valueOf(izVar.m542a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m542a() && (iA18 = jz.a(this.f633a, izVar.f633a)) != 0) {
            return iA18;
        }
        int iCompareTo2 = Boolean.valueOf(m544b()).compareTo(Boolean.valueOf(izVar.m544b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m544b() && (iA17 = jz.a(this.f634a, izVar.f634a)) != 0) {
            return iA17;
        }
        int iCompareTo3 = Boolean.valueOf(m545c()).compareTo(Boolean.valueOf(izVar.m545c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m545c() && (iA16 = jz.a(this.f638b, izVar.f638b)) != 0) {
            return iA16;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(izVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA15 = jz.a(this.f640c, izVar.f640c)) != 0) {
            return iA15;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(izVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA14 = jz.a(this.f631a, izVar.f631a)) != 0) {
            return iA14;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(izVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA13 = jz.a(this.f637b, izVar.f637b)) != 0) {
            return iA13;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(izVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA12 = jz.a(this.f641d, izVar.f641d)) != 0) {
            return iA12;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(izVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA11 = jz.a(this.f642e, izVar.f642e)) != 0) {
            return iA11;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(izVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA10 = jz.a(this.f643f, izVar.f643f)) != 0) {
            return iA10;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(izVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA9 = jz.a(this.f644g, izVar.f644g)) != 0) {
            return iA9;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(izVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA8 = jz.a(this.f645h, izVar.f645h)) != 0) {
            return iA8;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(izVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA7 = jz.a(this.f632a, izVar.f632a)) != 0) {
            return iA7;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(izVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA6 = jz.a(this.f646i, izVar.f646i)) != 0) {
            return iA6;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(izVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA5 = jz.a(this.f636a, izVar.f636a)) != 0) {
            return iA5;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(izVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA4 = jz.a(this.f647j, izVar.f647j)) != 0) {
            return iA4;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(izVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA3 = jz.a(this.f639c, izVar.f639c)) != 0) {
            return iA3;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(izVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA2 = jz.a(this.f648k, izVar.f648k)) != 0) {
            return iA2;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(izVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (!r() || (iA = jz.a(this.f649l, izVar.f649l)) == 0) {
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
                m541a();
                return;
            }
            short s2 = kgVarMo670a.f922a;
            if (s2 != 20) {
                if (s2 != 21) {
                    switch (s2) {
                        case 1:
                            if (b2 == 12) {
                                jc jcVar = new jc();
                                this.f633a = jcVar;
                                jcVar.a(kjVar);
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 2:
                            if (b2 == 11) {
                                this.f634a = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 3:
                            if (b2 == 11) {
                                this.f638b = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 4:
                            if (b2 == 11) {
                                this.f640c = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 5:
                            if (b2 == 10) {
                                this.f631a = kjVar.mo669a();
                                a(true);
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 6:
                            if (b2 == 10) {
                                this.f637b = kjVar.mo669a();
                                b(true);
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 7:
                            if (b2 == 11) {
                                this.f641d = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 8:
                            if (b2 == 11) {
                                this.f642e = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 9:
                            if (b2 == 11) {
                                this.f643f = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 10:
                            if (b2 == 11) {
                                this.f644g = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 11:
                            if (b2 == 11) {
                                this.f645h = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 12:
                            if (b2 == 12) {
                                ja jaVar = new ja();
                                this.f632a = jaVar;
                                jaVar.a(kjVar);
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 13:
                            if (b2 == 11) {
                                this.f646i = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 14:
                            if (b2 == 2) {
                                this.f636a = kjVar.mo680a();
                                c(true);
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 15:
                            if (b2 == 11) {
                                this.f647j = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 16:
                            if (b2 == 10) {
                                this.f639c = kjVar.mo669a();
                                d(true);
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        default:
                            km.a(kjVar, b2);
                            break;
                    }
                } else if (b2 == 11) {
                    this.f649l = kjVar.mo675a();
                } else {
                    km.a(kjVar, b2);
                }
            } else if (b2 == 11) {
                this.f648k = kjVar.mo675a();
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m541a() throws kk {
        if (this.f634a != null) {
            if (this.f638b != null) {
                if (this.f640c != null) {
                    return;
                }
                throw new kk("Required field 'payload' was not present! Struct: " + toString());
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
