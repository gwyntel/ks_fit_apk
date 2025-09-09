package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class jd implements jy<jd, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f677a;

    /* renamed from: a, reason: collision with other field name */
    public long f678a;

    /* renamed from: a, reason: collision with other field name */
    public jc f679a;

    /* renamed from: a, reason: collision with other field name */
    public jq f680a;

    /* renamed from: a, reason: collision with other field name */
    public String f681a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f683a;

    /* renamed from: a, reason: collision with other field name */
    public short f684a;

    /* renamed from: b, reason: collision with other field name */
    public String f686b;

    /* renamed from: b, reason: collision with other field name */
    public short f687b;

    /* renamed from: c, reason: collision with other field name */
    public String f688c;

    /* renamed from: d, reason: collision with other field name */
    public String f689d;

    /* renamed from: e, reason: collision with other field name */
    public String f690e;

    /* renamed from: f, reason: collision with other field name */
    public String f691f;

    /* renamed from: g, reason: collision with other field name */
    public String f692g;

    /* renamed from: h, reason: collision with other field name */
    public String f693h;

    /* renamed from: i, reason: collision with other field name */
    public String f694i;

    /* renamed from: j, reason: collision with other field name */
    public String f695j;

    /* renamed from: k, reason: collision with other field name */
    public String f696k;

    /* renamed from: l, reason: collision with other field name */
    public String f697l;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f676a = new ko("XmPushActionAckMessage");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24167a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24168b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24169c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24170d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24171e = new kg("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24172f = new kg("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24173g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24174h = new kg("", (byte) 12, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24175i = new kg("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24176j = new kg("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24177k = new kg("", (byte) 2, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final kg f24178l = new kg("", (byte) 11, 12);

    /* renamed from: m, reason: collision with root package name */
    private static final kg f24179m = new kg("", (byte) 11, 13);

    /* renamed from: n, reason: collision with root package name */
    private static final kg f24180n = new kg("", (byte) 11, 14);

    /* renamed from: o, reason: collision with root package name */
    private static final kg f24181o = new kg("", (byte) 6, 15);

    /* renamed from: p, reason: collision with root package name */
    private static final kg f24182p = new kg("", (byte) 6, 16);

    /* renamed from: q, reason: collision with root package name */
    private static final kg f24183q = new kg("", (byte) 11, 20);

    /* renamed from: r, reason: collision with root package name */
    private static final kg f24184r = new kg("", (byte) 11, 21);

    /* renamed from: s, reason: collision with root package name */
    private static final kg f24185s = new kg("", (byte) 8, 22);

    /* renamed from: t, reason: collision with root package name */
    private static final kg f24186t = new kg("", (byte) 13, 23);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f682a = new BitSet(5);

    /* renamed from: a, reason: collision with other field name */
    public boolean f685a = false;

    /* renamed from: a, reason: collision with other method in class */
    public boolean m572a() {
        return this.f681a != null;
    }

    public boolean b() {
        return this.f679a != null;
    }

    public boolean c() {
        return this.f686b != null;
    }

    public boolean d() {
        return this.f688c != null;
    }

    public boolean e() {
        return this.f682a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jd)) {
            return m573a((jd) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f689d != null;
    }

    public boolean g() {
        return this.f690e != null;
    }

    public boolean h() {
        return this.f680a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f691f != null;
    }

    public boolean j() {
        return this.f692g != null;
    }

    public boolean k() {
        return this.f682a.get(1);
    }

    public boolean l() {
        return this.f693h != null;
    }

    public boolean m() {
        return this.f694i != null;
    }

    public boolean n() {
        return this.f695j != null;
    }

    public boolean o() {
        return this.f682a.get(2);
    }

    public boolean p() {
        return this.f682a.get(3);
    }

    public boolean q() {
        return this.f696k != null;
    }

    public boolean r() {
        return this.f697l != null;
    }

    public boolean s() {
        return this.f682a.get(4);
    }

    public boolean t() {
        return this.f683a != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionAckMessage(");
        boolean z3 = false;
        if (m572a()) {
            sb.append("debug:");
            String str = this.f681a;
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
            jc jcVar = this.f679a;
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
        String str2 = this.f686b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f688c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.f678a);
        if (f()) {
            sb.append(", ");
            sb.append("topic:");
            String str4 = this.f689d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str5 = this.f690e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("request:");
            jq jqVar = this.f680a;
            if (jqVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jqVar);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f691f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f692g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("isOnline:");
            sb.append(this.f685a);
        }
        if (l()) {
            sb.append(", ");
            sb.append("regId:");
            String str8 = this.f693h;
            if (str8 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str8);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("callbackUrl:");
            String str9 = this.f694i;
            if (str9 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str9);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str10 = this.f695j;
            if (str10 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str10);
            }
        }
        if (o()) {
            sb.append(", ");
            sb.append("deviceStatus:");
            sb.append((int) this.f684a);
        }
        if (p()) {
            sb.append(", ");
            sb.append("geoMsgStatus:");
            sb.append((int) this.f687b);
        }
        if (q()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            String str11 = this.f696k;
            if (str11 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str11);
            }
        }
        if (r()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str12 = this.f697l;
            if (str12 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str12);
            }
        }
        if (s()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.f677a);
        }
        if (t()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f683a;
            if (map == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public jd a(String str) {
        this.f686b = str;
        return this;
    }

    public jd b(String str) {
        this.f688c = str;
        return this;
    }

    public jd c(String str) {
        this.f689d = str;
        return this;
    }

    public jd d(String str) {
        this.f690e = str;
        return this;
    }

    public void e(boolean z2) {
        this.f682a.set(4, z2);
    }

    public jd a(long j2) {
        this.f678a = j2;
        a(true);
        return this;
    }

    public void b(boolean z2) {
        this.f682a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f682a.set(2, z2);
    }

    public void d(boolean z2) {
        this.f682a.set(3, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        a();
        kjVar.a(f676a);
        if (this.f681a != null && m572a()) {
            kjVar.a(f24167a);
            kjVar.a(this.f681a);
            kjVar.b();
        }
        if (this.f679a != null && b()) {
            kjVar.a(f24168b);
            this.f679a.b(kjVar);
            kjVar.b();
        }
        if (this.f686b != null) {
            kjVar.a(f24169c);
            kjVar.a(this.f686b);
            kjVar.b();
        }
        if (this.f688c != null) {
            kjVar.a(f24170d);
            kjVar.a(this.f688c);
            kjVar.b();
        }
        kjVar.a(f24171e);
        kjVar.a(this.f678a);
        kjVar.b();
        if (this.f689d != null && f()) {
            kjVar.a(f24172f);
            kjVar.a(this.f689d);
            kjVar.b();
        }
        if (this.f690e != null && g()) {
            kjVar.a(f24173g);
            kjVar.a(this.f690e);
            kjVar.b();
        }
        if (this.f680a != null && h()) {
            kjVar.a(f24174h);
            this.f680a.b(kjVar);
            kjVar.b();
        }
        if (this.f691f != null && i()) {
            kjVar.a(f24175i);
            kjVar.a(this.f691f);
            kjVar.b();
        }
        if (this.f692g != null && j()) {
            kjVar.a(f24176j);
            kjVar.a(this.f692g);
            kjVar.b();
        }
        if (k()) {
            kjVar.a(f24177k);
            kjVar.a(this.f685a);
            kjVar.b();
        }
        if (this.f693h != null && l()) {
            kjVar.a(f24178l);
            kjVar.a(this.f693h);
            kjVar.b();
        }
        if (this.f694i != null && m()) {
            kjVar.a(f24179m);
            kjVar.a(this.f694i);
            kjVar.b();
        }
        if (this.f695j != null && n()) {
            kjVar.a(f24180n);
            kjVar.a(this.f695j);
            kjVar.b();
        }
        if (o()) {
            kjVar.a(f24181o);
            kjVar.a(this.f684a);
            kjVar.b();
        }
        if (p()) {
            kjVar.a(f24182p);
            kjVar.a(this.f687b);
            kjVar.b();
        }
        if (this.f696k != null && q()) {
            kjVar.a(f24183q);
            kjVar.a(this.f696k);
            kjVar.b();
        }
        if (this.f697l != null && r()) {
            kjVar.a(f24184r);
            kjVar.a(this.f697l);
            kjVar.b();
        }
        if (s()) {
            kjVar.a(f24185s);
            kjVar.mo679a(this.f677a);
            kjVar.b();
        }
        if (this.f683a != null && t()) {
            kjVar.a(f24186t);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f683a.size()));
            for (Map.Entry<String, String> entry : this.f683a.entrySet()) {
                kjVar.a(entry.getKey());
                kjVar.a(entry.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public void a(boolean z2) {
        this.f682a.set(0, z2);
    }

    public jd a(short s2) {
        this.f684a = s2;
        c(true);
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m573a(jd jdVar) {
        if (jdVar == null) {
            return false;
        }
        boolean zM572a = m572a();
        boolean zM572a2 = jdVar.m572a();
        if ((zM572a || zM572a2) && !(zM572a && zM572a2 && this.f681a.equals(jdVar.f681a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jdVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f679a.m571a(jdVar.f679a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jdVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f686b.equals(jdVar.f686b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jdVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f688c.equals(jdVar.f688c))) || this.f678a != jdVar.f678a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jdVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f689d.equals(jdVar.f689d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jdVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f690e.equals(jdVar.f690e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jdVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f680a.m634a(jdVar.f680a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jdVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f691f.equals(jdVar.f691f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jdVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f692g.equals(jdVar.f692g))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jdVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f685a == jdVar.f685a)) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jdVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f693h.equals(jdVar.f693h))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = jdVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f694i.equals(jdVar.f694i))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = jdVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f695j.equals(jdVar.f695j))) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = jdVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.f684a == jdVar.f684a)) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = jdVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.f687b == jdVar.f687b)) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = jdVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.f696k.equals(jdVar.f696k))) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = jdVar.r();
        if ((zR || zR2) && !(zR && zR2 && this.f697l.equals(jdVar.f697l))) {
            return false;
        }
        boolean zS = s();
        boolean zS2 = jdVar.s();
        if ((zS || zS2) && !(zS && zS2 && this.f677a == jdVar.f677a)) {
            return false;
        }
        boolean zT = t();
        boolean zT2 = jdVar.t();
        if (zT || zT2) {
            return zT && zT2 && this.f683a.equals(jdVar.f683a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jd jdVar) {
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
        int iA19;
        int iA20;
        if (!getClass().equals(jdVar.getClass())) {
            return getClass().getName().compareTo(jdVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m572a()).compareTo(Boolean.valueOf(jdVar.m572a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m572a() && (iA20 = jz.a(this.f681a, jdVar.f681a)) != 0) {
            return iA20;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jdVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA19 = jz.a(this.f679a, jdVar.f679a)) != 0) {
            return iA19;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jdVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA18 = jz.a(this.f686b, jdVar.f686b)) != 0) {
            return iA18;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jdVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA17 = jz.a(this.f688c, jdVar.f688c)) != 0) {
            return iA17;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jdVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA16 = jz.a(this.f678a, jdVar.f678a)) != 0) {
            return iA16;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jdVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA15 = jz.a(this.f689d, jdVar.f689d)) != 0) {
            return iA15;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jdVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA14 = jz.a(this.f690e, jdVar.f690e)) != 0) {
            return iA14;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jdVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA13 = jz.a(this.f680a, jdVar.f680a)) != 0) {
            return iA13;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jdVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA12 = jz.a(this.f691f, jdVar.f691f)) != 0) {
            return iA12;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jdVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA11 = jz.a(this.f692g, jdVar.f692g)) != 0) {
            return iA11;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jdVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA10 = jz.a(this.f685a, jdVar.f685a)) != 0) {
            return iA10;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jdVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA9 = jz.a(this.f693h, jdVar.f693h)) != 0) {
            return iA9;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(jdVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA8 = jz.a(this.f694i, jdVar.f694i)) != 0) {
            return iA8;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(jdVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA7 = jz.a(this.f695j, jdVar.f695j)) != 0) {
            return iA7;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(jdVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA6 = jz.a(this.f684a, jdVar.f684a)) != 0) {
            return iA6;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(jdVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA5 = jz.a(this.f687b, jdVar.f687b)) != 0) {
            return iA5;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(jdVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA4 = jz.a(this.f696k, jdVar.f696k)) != 0) {
            return iA4;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(jdVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (r() && (iA3 = jz.a(this.f697l, jdVar.f697l)) != 0) {
            return iA3;
        }
        int iCompareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(jdVar.s()));
        if (iCompareTo19 != 0) {
            return iCompareTo19;
        }
        if (s() && (iA2 = jz.a(this.f677a, jdVar.f677a)) != 0) {
            return iA2;
        }
        int iCompareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(jdVar.t()));
        if (iCompareTo20 != 0) {
            return iCompareTo20;
        }
        if (!t() || (iA = jz.a(this.f683a, jdVar.f683a)) == 0) {
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
                    a();
                    return;
                }
                throw new kk("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f681a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f679a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f686b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f688c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 10) {
                        this.f678a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 11) {
                        this.f689d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f690e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 12) {
                        jq jqVar = new jq();
                        this.f680a = jqVar;
                        jqVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f691f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 11) {
                        this.f692g = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 2) {
                        this.f685a = kjVar.mo680a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 12:
                    if (b2 == 11) {
                        this.f693h = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 13:
                    if (b2 == 11) {
                        this.f694i = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 14:
                    if (b2 == 11) {
                        this.f695j = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 15:
                    if (b2 == 6) {
                        this.f684a = kjVar.mo677a();
                        c(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 16:
                    if (b2 == 6) {
                        this.f687b = kjVar.mo677a();
                        d(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 17:
                case 18:
                case 19:
                default:
                    km.a(kjVar, b2);
                    break;
                case 20:
                    if (b2 == 11) {
                        this.f696k = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 21:
                    if (b2 == 11) {
                        this.f697l = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 22:
                    if (b2 == 8) {
                        this.f677a = kjVar.mo668a();
                        e(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 23:
                    if (b2 == 13) {
                        ki kiVarMo672a = kjVar.mo672a();
                        this.f683a = new HashMap(kiVarMo672a.f924a * 2);
                        for (int i2 = 0; i2 < kiVarMo672a.f924a; i2++) {
                            this.f683a.put(kjVar.mo675a(), kjVar.mo675a());
                        }
                        kjVar.h();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
            }
            kjVar.g();
        }
    }

    public void a() throws kk {
        if (this.f686b != null) {
            if (this.f688c != null) {
                return;
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
