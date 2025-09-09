package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class jn implements jy<jn, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f772a;

    /* renamed from: a, reason: collision with other field name */
    public long f773a;

    /* renamed from: a, reason: collision with other field name */
    public jb f774a;

    /* renamed from: a, reason: collision with other field name */
    public jc f775a;

    /* renamed from: a, reason: collision with other field name */
    public String f776a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f778a;

    /* renamed from: b, reason: collision with other field name */
    public int f780b;

    /* renamed from: b, reason: collision with other field name */
    public long f781b;

    /* renamed from: b, reason: collision with other field name */
    public String f782b;

    /* renamed from: c, reason: collision with other field name */
    public int f784c;

    /* renamed from: c, reason: collision with other field name */
    public String f785c;

    /* renamed from: d, reason: collision with other field name */
    public String f787d;

    /* renamed from: e, reason: collision with other field name */
    public String f788e;

    /* renamed from: f, reason: collision with other field name */
    public String f789f;

    /* renamed from: g, reason: collision with other field name */
    public String f790g;

    /* renamed from: h, reason: collision with other field name */
    public String f791h;

    /* renamed from: i, reason: collision with other field name */
    public String f792i;

    /* renamed from: j, reason: collision with other field name */
    public String f793j;

    /* renamed from: k, reason: collision with other field name */
    public String f794k;

    /* renamed from: l, reason: collision with other field name */
    public String f795l;

    /* renamed from: m, reason: collision with other field name */
    public String f796m;

    /* renamed from: n, reason: collision with other field name */
    public String f797n;

    /* renamed from: o, reason: collision with other field name */
    public String f798o;

    /* renamed from: p, reason: collision with other field name */
    public String f799p;

    /* renamed from: q, reason: collision with other field name */
    public String f800q;

    /* renamed from: r, reason: collision with other field name */
    public String f801r;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f771a = new ko("XmPushActionRegistration");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24245a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24246b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24247c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24248d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24249e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24250f = new kg("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24251g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24252h = new kg("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24253i = new kg("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24254j = new kg("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24255k = new kg("", (byte) 11, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final kg f24256l = new kg("", (byte) 11, 12);

    /* renamed from: m, reason: collision with root package name */
    private static final kg f24257m = new kg("", (byte) 8, 13);

    /* renamed from: n, reason: collision with root package name */
    private static final kg f24258n = new kg("", (byte) 8, 14);

    /* renamed from: o, reason: collision with root package name */
    private static final kg f24259o = new kg("", (byte) 11, 15);

    /* renamed from: p, reason: collision with root package name */
    private static final kg f24260p = new kg("", (byte) 11, 16);

    /* renamed from: q, reason: collision with root package name */
    private static final kg f24261q = new kg("", (byte) 11, 17);

    /* renamed from: r, reason: collision with root package name */
    private static final kg f24262r = new kg("", (byte) 11, 18);

    /* renamed from: s, reason: collision with root package name */
    private static final kg f24263s = new kg("", (byte) 8, 19);

    /* renamed from: t, reason: collision with root package name */
    private static final kg f24264t = new kg("", (byte) 8, 20);

    /* renamed from: u, reason: collision with root package name */
    private static final kg f24265u = new kg("", (byte) 2, 21);

    /* renamed from: v, reason: collision with root package name */
    private static final kg f24266v = new kg("", (byte) 10, 22);

    /* renamed from: w, reason: collision with root package name */
    private static final kg f24267w = new kg("", (byte) 10, 23);

    /* renamed from: x, reason: collision with root package name */
    private static final kg f24268x = new kg("", (byte) 11, 24);

    /* renamed from: y, reason: collision with root package name */
    private static final kg f24269y = new kg("", (byte) 11, 25);

    /* renamed from: z, reason: collision with root package name */
    private static final kg f24270z = new kg("", (byte) 2, 26);
    private static final kg A = new kg("", (byte) 13, 100);
    private static final kg B = new kg("", (byte) 2, 101);
    private static final kg C = new kg("", (byte) 11, 102);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f777a = new BitSet(8);

    /* renamed from: a, reason: collision with other field name */
    public boolean f779a = true;

    /* renamed from: c, reason: collision with other field name */
    public boolean f786c = false;

    /* renamed from: b, reason: collision with other field name */
    public boolean f783b = false;

    public boolean A() {
        return this.f778a != null;
    }

    public boolean B() {
        return this.f777a.get(7);
    }

    public boolean C() {
        return this.f801r != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m618a() {
        return this.f776a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m620b() {
        return this.f775a != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m621c() {
        return this.f782b != null;
    }

    public boolean d() {
        return this.f785c != null;
    }

    public boolean e() {
        return this.f787d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jn)) {
            return m619a((jn) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f788e != null;
    }

    public boolean g() {
        return this.f789f != null;
    }

    public boolean h() {
        return this.f790g != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f791h != null;
    }

    public boolean j() {
        return this.f792i != null;
    }

    public boolean k() {
        return this.f793j != null;
    }

    public boolean l() {
        return this.f794k != null;
    }

    public boolean m() {
        return this.f777a.get(0);
    }

    public boolean n() {
        return this.f777a.get(1);
    }

    public boolean o() {
        return this.f795l != null;
    }

    public boolean p() {
        return this.f796m != null;
    }

    public boolean q() {
        return this.f797n != null;
    }

    public boolean r() {
        return this.f798o != null;
    }

    public boolean s() {
        return this.f777a.get(2);
    }

    public boolean t() {
        return this.f774a != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionRegistration(");
        boolean z3 = false;
        if (m618a()) {
            sb.append("debug:");
            String str = this.f776a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m620b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            jc jcVar = this.f775a;
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
        String str2 = this.f782b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(com.xiaomi.push.service.bc.a(str2));
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f785c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str4 = this.f787d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f788e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        sb.append(", ");
        sb.append("token:");
        String str6 = this.f789f;
        if (str6 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str6);
        }
        if (h()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str7 = this.f790g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str8 = this.f791h;
            if (str8 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str8);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("sdkVersion:");
            String str9 = this.f792i;
            if (str9 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str9);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("regId:");
            String str10 = this.f793j;
            if (str10 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str10);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("pushSdkVersionName:");
            String str11 = this.f794k;
            if (str11 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str11);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("pushSdkVersionCode:");
            sb.append(this.f772a);
        }
        if (n()) {
            sb.append(", ");
            sb.append("appVersionCode:");
            sb.append(this.f780b);
        }
        if (o()) {
            sb.append(", ");
            sb.append("androidId:");
            String str12 = this.f795l;
            if (str12 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str12);
            }
        }
        if (p()) {
            sb.append(", ");
            sb.append("imei:");
            String str13 = this.f796m;
            if (str13 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str13);
            }
        }
        if (q()) {
            sb.append(", ");
            sb.append("serial:");
            String str14 = this.f797n;
            if (str14 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str14);
            }
        }
        if (r()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            String str15 = this.f798o;
            if (str15 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str15);
            }
        }
        if (s()) {
            sb.append(", ");
            sb.append("spaceId:");
            sb.append(this.f784c);
        }
        if (t()) {
            sb.append(", ");
            sb.append("reason:");
            jb jbVar = this.f774a;
            if (jbVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jbVar);
            }
        }
        if (u()) {
            sb.append(", ");
            sb.append("validateToken:");
            sb.append(this.f779a);
        }
        if (v()) {
            sb.append(", ");
            sb.append("miid:");
            sb.append(this.f773a);
        }
        if (w()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f781b);
        }
        if (x()) {
            sb.append(", ");
            sb.append("subImei:");
            String str16 = this.f799p;
            if (str16 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str16);
            }
        }
        if (y()) {
            sb.append(", ");
            sb.append("subImeiMd5:");
            String str17 = this.f800q;
            if (str17 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str17);
            }
        }
        if (z()) {
            sb.append(", ");
            sb.append("isHybridFrame:");
            sb.append(this.f783b);
        }
        if (A()) {
            sb.append(", ");
            sb.append("connectionAttrs:");
            Map<String, String> map = this.f778a;
            if (map == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map);
            }
        }
        if (B()) {
            sb.append(", ");
            sb.append("cleanOldRegInfo:");
            sb.append(this.f786c);
        }
        if (C()) {
            sb.append(", ");
            sb.append("oldRegId:");
            String str18 = this.f801r;
            if (str18 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str18);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public boolean u() {
        return this.f777a.get(3);
    }

    public boolean v() {
        return this.f777a.get(4);
    }

    public boolean w() {
        return this.f777a.get(5);
    }

    public boolean x() {
        return this.f799p != null;
    }

    public boolean y() {
        return this.f800q != null;
    }

    public boolean z() {
        return this.f777a.get(6);
    }

    public String a() {
        return this.f782b;
    }

    public String b() {
        return this.f785c;
    }

    public jn c(String str) {
        this.f787d = str;
        return this;
    }

    public jn d(String str) {
        this.f788e = str;
        return this;
    }

    public jn e(String str) {
        this.f789f = str;
        return this;
    }

    public jn f(String str) {
        this.f790g = str;
        return this;
    }

    public jn g(String str) {
        this.f791h = str;
        return this;
    }

    public jn h(String str) {
        this.f794k = str;
        return this;
    }

    public jn i(String str) {
        this.f798o = str;
        return this;
    }

    public jn a(String str) {
        this.f782b = str;
        return this;
    }

    public jn b(String str) {
        this.f785c = str;
        return this;
    }

    public String c() {
        return this.f789f;
    }

    public void d(boolean z2) {
        this.f777a.set(3, z2);
    }

    public void e(boolean z2) {
        this.f777a.set(4, z2);
    }

    public void f(boolean z2) {
        this.f777a.set(5, z2);
    }

    public void g(boolean z2) {
        this.f777a.set(6, z2);
    }

    public void h(boolean z2) {
        this.f777a.set(7, z2);
    }

    public jn a(int i2) {
        this.f772a = i2;
        a(true);
        return this;
    }

    public jn b(int i2) {
        this.f780b = i2;
        b(true);
        return this;
    }

    public jn c(int i2) {
        this.f784c = i2;
        c(true);
        return this;
    }

    public void a(boolean z2) {
        this.f777a.set(0, z2);
    }

    public void b(boolean z2) {
        this.f777a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f777a.set(2, z2);
    }

    public jn a(jb jbVar) {
        this.f774a = jbVar;
        return this;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m617a();
        kjVar.a(f771a);
        if (this.f776a != null && m618a()) {
            kjVar.a(f24245a);
            kjVar.a(this.f776a);
            kjVar.b();
        }
        if (this.f775a != null && m620b()) {
            kjVar.a(f24246b);
            this.f775a.b(kjVar);
            kjVar.b();
        }
        if (this.f782b != null) {
            kjVar.a(f24247c);
            kjVar.a(this.f782b);
            kjVar.b();
        }
        if (this.f785c != null) {
            kjVar.a(f24248d);
            kjVar.a(this.f785c);
            kjVar.b();
        }
        if (this.f787d != null && e()) {
            kjVar.a(f24249e);
            kjVar.a(this.f787d);
            kjVar.b();
        }
        if (this.f788e != null && f()) {
            kjVar.a(f24250f);
            kjVar.a(this.f788e);
            kjVar.b();
        }
        if (this.f789f != null) {
            kjVar.a(f24251g);
            kjVar.a(this.f789f);
            kjVar.b();
        }
        if (this.f790g != null && h()) {
            kjVar.a(f24252h);
            kjVar.a(this.f790g);
            kjVar.b();
        }
        if (this.f791h != null && i()) {
            kjVar.a(f24253i);
            kjVar.a(this.f791h);
            kjVar.b();
        }
        if (this.f792i != null && j()) {
            kjVar.a(f24254j);
            kjVar.a(this.f792i);
            kjVar.b();
        }
        if (this.f793j != null && k()) {
            kjVar.a(f24255k);
            kjVar.a(this.f793j);
            kjVar.b();
        }
        if (this.f794k != null && l()) {
            kjVar.a(f24256l);
            kjVar.a(this.f794k);
            kjVar.b();
        }
        if (m()) {
            kjVar.a(f24257m);
            kjVar.mo679a(this.f772a);
            kjVar.b();
        }
        if (n()) {
            kjVar.a(f24258n);
            kjVar.mo679a(this.f780b);
            kjVar.b();
        }
        if (this.f795l != null && o()) {
            kjVar.a(f24259o);
            kjVar.a(this.f795l);
            kjVar.b();
        }
        if (this.f796m != null && p()) {
            kjVar.a(f24260p);
            kjVar.a(this.f796m);
            kjVar.b();
        }
        if (this.f797n != null && q()) {
            kjVar.a(f24261q);
            kjVar.a(this.f797n);
            kjVar.b();
        }
        if (this.f798o != null && r()) {
            kjVar.a(f24262r);
            kjVar.a(this.f798o);
            kjVar.b();
        }
        if (s()) {
            kjVar.a(f24263s);
            kjVar.mo679a(this.f784c);
            kjVar.b();
        }
        if (this.f774a != null && t()) {
            kjVar.a(f24264t);
            kjVar.mo679a(this.f774a.a());
            kjVar.b();
        }
        if (u()) {
            kjVar.a(f24265u);
            kjVar.a(this.f779a);
            kjVar.b();
        }
        if (v()) {
            kjVar.a(f24266v);
            kjVar.a(this.f773a);
            kjVar.b();
        }
        if (w()) {
            kjVar.a(f24267w);
            kjVar.a(this.f781b);
            kjVar.b();
        }
        if (this.f799p != null && x()) {
            kjVar.a(f24268x);
            kjVar.a(this.f799p);
            kjVar.b();
        }
        if (this.f800q != null && y()) {
            kjVar.a(f24269y);
            kjVar.a(this.f800q);
            kjVar.b();
        }
        if (z()) {
            kjVar.a(f24270z);
            kjVar.a(this.f783b);
            kjVar.b();
        }
        if (this.f778a != null && A()) {
            kjVar.a(A);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f778a.size()));
            for (Map.Entry<String, String> entry : this.f778a.entrySet()) {
                kjVar.a(entry.getKey());
                kjVar.a(entry.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        if (B()) {
            kjVar.a(B);
            kjVar.a(this.f786c);
            kjVar.b();
        }
        if (this.f801r != null && C()) {
            kjVar.a(C);
            kjVar.a(this.f801r);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m619a(jn jnVar) {
        if (jnVar == null) {
            return false;
        }
        boolean zM618a = m618a();
        boolean zM618a2 = jnVar.m618a();
        if ((zM618a || zM618a2) && !(zM618a && zM618a2 && this.f776a.equals(jnVar.f776a))) {
            return false;
        }
        boolean zM620b = m620b();
        boolean zM620b2 = jnVar.m620b();
        if ((zM620b || zM620b2) && !(zM620b && zM620b2 && this.f775a.m571a(jnVar.f775a))) {
            return false;
        }
        boolean zM621c = m621c();
        boolean zM621c2 = jnVar.m621c();
        if ((zM621c || zM621c2) && !(zM621c && zM621c2 && this.f782b.equals(jnVar.f782b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jnVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f785c.equals(jnVar.f785c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jnVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f787d.equals(jnVar.f787d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jnVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f788e.equals(jnVar.f788e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jnVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f789f.equals(jnVar.f789f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jnVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f790g.equals(jnVar.f790g))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jnVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f791h.equals(jnVar.f791h))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jnVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f792i.equals(jnVar.f792i))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jnVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f793j.equals(jnVar.f793j))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jnVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f794k.equals(jnVar.f794k))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = jnVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f772a == jnVar.f772a)) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = jnVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f780b == jnVar.f780b)) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = jnVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.f795l.equals(jnVar.f795l))) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = jnVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.f796m.equals(jnVar.f796m))) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = jnVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.f797n.equals(jnVar.f797n))) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = jnVar.r();
        if ((zR || zR2) && !(zR && zR2 && this.f798o.equals(jnVar.f798o))) {
            return false;
        }
        boolean zS = s();
        boolean zS2 = jnVar.s();
        if ((zS || zS2) && !(zS && zS2 && this.f784c == jnVar.f784c)) {
            return false;
        }
        boolean zT = t();
        boolean zT2 = jnVar.t();
        if ((zT || zT2) && !(zT && zT2 && this.f774a.equals(jnVar.f774a))) {
            return false;
        }
        boolean zU = u();
        boolean zU2 = jnVar.u();
        if ((zU || zU2) && !(zU && zU2 && this.f779a == jnVar.f779a)) {
            return false;
        }
        boolean zV = v();
        boolean zV2 = jnVar.v();
        if ((zV || zV2) && !(zV && zV2 && this.f773a == jnVar.f773a)) {
            return false;
        }
        boolean zW = w();
        boolean zW2 = jnVar.w();
        if ((zW || zW2) && !(zW && zW2 && this.f781b == jnVar.f781b)) {
            return false;
        }
        boolean zX = x();
        boolean zX2 = jnVar.x();
        if ((zX || zX2) && !(zX && zX2 && this.f799p.equals(jnVar.f799p))) {
            return false;
        }
        boolean zY = y();
        boolean zY2 = jnVar.y();
        if ((zY || zY2) && !(zY && zY2 && this.f800q.equals(jnVar.f800q))) {
            return false;
        }
        boolean z2 = z();
        boolean z3 = jnVar.z();
        if ((z2 || z3) && !(z2 && z3 && this.f783b == jnVar.f783b)) {
            return false;
        }
        boolean zA = A();
        boolean zA2 = jnVar.A();
        if ((zA || zA2) && !(zA && zA2 && this.f778a.equals(jnVar.f778a))) {
            return false;
        }
        boolean zB = B();
        boolean zB2 = jnVar.B();
        if ((zB || zB2) && !(zB && zB2 && this.f786c == jnVar.f786c)) {
            return false;
        }
        boolean zC = C();
        boolean zC2 = jnVar.C();
        if (zC || zC2) {
            return zC && zC2 && this.f801r.equals(jnVar.f801r);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jn jnVar) {
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
        int iA21;
        int iA22;
        int iA23;
        int iA24;
        int iA25;
        int iA26;
        int iA27;
        int iA28;
        int iA29;
        if (!getClass().equals(jnVar.getClass())) {
            return getClass().getName().compareTo(jnVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m618a()).compareTo(Boolean.valueOf(jnVar.m618a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m618a() && (iA29 = jz.a(this.f776a, jnVar.f776a)) != 0) {
            return iA29;
        }
        int iCompareTo2 = Boolean.valueOf(m620b()).compareTo(Boolean.valueOf(jnVar.m620b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m620b() && (iA28 = jz.a(this.f775a, jnVar.f775a)) != 0) {
            return iA28;
        }
        int iCompareTo3 = Boolean.valueOf(m621c()).compareTo(Boolean.valueOf(jnVar.m621c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m621c() && (iA27 = jz.a(this.f782b, jnVar.f782b)) != 0) {
            return iA27;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jnVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA26 = jz.a(this.f785c, jnVar.f785c)) != 0) {
            return iA26;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jnVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA25 = jz.a(this.f787d, jnVar.f787d)) != 0) {
            return iA25;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jnVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA24 = jz.a(this.f788e, jnVar.f788e)) != 0) {
            return iA24;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jnVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA23 = jz.a(this.f789f, jnVar.f789f)) != 0) {
            return iA23;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jnVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA22 = jz.a(this.f790g, jnVar.f790g)) != 0) {
            return iA22;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jnVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA21 = jz.a(this.f791h, jnVar.f791h)) != 0) {
            return iA21;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jnVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA20 = jz.a(this.f792i, jnVar.f792i)) != 0) {
            return iA20;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jnVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA19 = jz.a(this.f793j, jnVar.f793j)) != 0) {
            return iA19;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jnVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA18 = jz.a(this.f794k, jnVar.f794k)) != 0) {
            return iA18;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(jnVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA17 = jz.a(this.f772a, jnVar.f772a)) != 0) {
            return iA17;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(jnVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA16 = jz.a(this.f780b, jnVar.f780b)) != 0) {
            return iA16;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(jnVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA15 = jz.a(this.f795l, jnVar.f795l)) != 0) {
            return iA15;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(jnVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA14 = jz.a(this.f796m, jnVar.f796m)) != 0) {
            return iA14;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(jnVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA13 = jz.a(this.f797n, jnVar.f797n)) != 0) {
            return iA13;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(jnVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (r() && (iA12 = jz.a(this.f798o, jnVar.f798o)) != 0) {
            return iA12;
        }
        int iCompareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(jnVar.s()));
        if (iCompareTo19 != 0) {
            return iCompareTo19;
        }
        if (s() && (iA11 = jz.a(this.f784c, jnVar.f784c)) != 0) {
            return iA11;
        }
        int iCompareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(jnVar.t()));
        if (iCompareTo20 != 0) {
            return iCompareTo20;
        }
        if (t() && (iA10 = jz.a(this.f774a, jnVar.f774a)) != 0) {
            return iA10;
        }
        int iCompareTo21 = Boolean.valueOf(u()).compareTo(Boolean.valueOf(jnVar.u()));
        if (iCompareTo21 != 0) {
            return iCompareTo21;
        }
        if (u() && (iA9 = jz.a(this.f779a, jnVar.f779a)) != 0) {
            return iA9;
        }
        int iCompareTo22 = Boolean.valueOf(v()).compareTo(Boolean.valueOf(jnVar.v()));
        if (iCompareTo22 != 0) {
            return iCompareTo22;
        }
        if (v() && (iA8 = jz.a(this.f773a, jnVar.f773a)) != 0) {
            return iA8;
        }
        int iCompareTo23 = Boolean.valueOf(w()).compareTo(Boolean.valueOf(jnVar.w()));
        if (iCompareTo23 != 0) {
            return iCompareTo23;
        }
        if (w() && (iA7 = jz.a(this.f781b, jnVar.f781b)) != 0) {
            return iA7;
        }
        int iCompareTo24 = Boolean.valueOf(x()).compareTo(Boolean.valueOf(jnVar.x()));
        if (iCompareTo24 != 0) {
            return iCompareTo24;
        }
        if (x() && (iA6 = jz.a(this.f799p, jnVar.f799p)) != 0) {
            return iA6;
        }
        int iCompareTo25 = Boolean.valueOf(y()).compareTo(Boolean.valueOf(jnVar.y()));
        if (iCompareTo25 != 0) {
            return iCompareTo25;
        }
        if (y() && (iA5 = jz.a(this.f800q, jnVar.f800q)) != 0) {
            return iA5;
        }
        int iCompareTo26 = Boolean.valueOf(z()).compareTo(Boolean.valueOf(jnVar.z()));
        if (iCompareTo26 != 0) {
            return iCompareTo26;
        }
        if (z() && (iA4 = jz.a(this.f783b, jnVar.f783b)) != 0) {
            return iA4;
        }
        int iCompareTo27 = Boolean.valueOf(A()).compareTo(Boolean.valueOf(jnVar.A()));
        if (iCompareTo27 != 0) {
            return iCompareTo27;
        }
        if (A() && (iA3 = jz.a(this.f778a, jnVar.f778a)) != 0) {
            return iA3;
        }
        int iCompareTo28 = Boolean.valueOf(B()).compareTo(Boolean.valueOf(jnVar.B()));
        if (iCompareTo28 != 0) {
            return iCompareTo28;
        }
        if (B() && (iA2 = jz.a(this.f786c, jnVar.f786c)) != 0) {
            return iA2;
        }
        int iCompareTo29 = Boolean.valueOf(C()).compareTo(Boolean.valueOf(jnVar.C()));
        if (iCompareTo29 != 0) {
            return iCompareTo29;
        }
        if (!C() || (iA = jz.a(this.f801r, jnVar.f801r)) == 0) {
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
                m617a();
                return;
            }
            short s2 = kgVarMo670a.f922a;
            switch (s2) {
                case 1:
                    if (b2 == 11) {
                        this.f776a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f775a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f782b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f785c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f787d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 11) {
                        this.f788e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f789f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f790g = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f791h = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 11) {
                        this.f792i = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 11) {
                        this.f793j = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 12:
                    if (b2 == 11) {
                        this.f794k = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 13:
                    if (b2 == 8) {
                        this.f772a = kjVar.mo668a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 14:
                    if (b2 == 8) {
                        this.f780b = kjVar.mo668a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 15:
                    if (b2 == 11) {
                        this.f795l = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 16:
                    if (b2 == 11) {
                        this.f796m = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 17:
                    if (b2 == 11) {
                        this.f797n = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 18:
                    if (b2 == 11) {
                        this.f798o = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 19:
                    if (b2 == 8) {
                        this.f784c = kjVar.mo668a();
                        c(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 20:
                    if (b2 == 8) {
                        this.f774a = jb.a(kjVar.mo668a());
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 21:
                    if (b2 == 2) {
                        this.f779a = kjVar.mo680a();
                        d(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 22:
                    if (b2 == 10) {
                        this.f773a = kjVar.mo669a();
                        e(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 23:
                    if (b2 == 10) {
                        this.f781b = kjVar.mo669a();
                        f(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 24:
                    if (b2 == 11) {
                        this.f799p = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 25:
                    if (b2 == 11) {
                        this.f800q = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 26:
                    if (b2 == 2) {
                        this.f783b = kjVar.mo680a();
                        g(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                default:
                    switch (s2) {
                        case 100:
                            if (b2 == 13) {
                                ki kiVarMo672a = kjVar.mo672a();
                                this.f778a = new HashMap(kiVarMo672a.f924a * 2);
                                for (int i2 = 0; i2 < kiVarMo672a.f924a; i2++) {
                                    this.f778a.put(kjVar.mo675a(), kjVar.mo675a());
                                }
                                kjVar.h();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 101:
                            if (b2 == 2) {
                                this.f786c = kjVar.mo680a();
                                h(true);
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        case 102:
                            if (b2 == 11) {
                                this.f801r = kjVar.mo675a();
                                break;
                            } else {
                                km.a(kjVar, b2);
                                break;
                            }
                        default:
                            km.a(kjVar, b2);
                            break;
                    }
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m617a() throws kk {
        if (this.f782b != null) {
            if (this.f785c != null) {
                if (this.f789f != null) {
                    return;
                }
                throw new kk("Required field 'token' was not present! Struct: " + toString());
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
