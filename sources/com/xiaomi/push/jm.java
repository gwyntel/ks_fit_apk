package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class jm implements jy<jm, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f755a;

    /* renamed from: a, reason: collision with other field name */
    public jc f756a;

    /* renamed from: a, reason: collision with other field name */
    public String f757a;

    /* renamed from: a, reason: collision with other field name */
    public ByteBuffer f758a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f759a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f760a;

    /* renamed from: a, reason: collision with other field name */
    public boolean f761a;

    /* renamed from: b, reason: collision with other field name */
    public String f762b;

    /* renamed from: b, reason: collision with other field name */
    public boolean f763b;

    /* renamed from: c, reason: collision with other field name */
    public String f764c;

    /* renamed from: d, reason: collision with other field name */
    public String f765d;

    /* renamed from: e, reason: collision with other field name */
    public String f766e;

    /* renamed from: f, reason: collision with other field name */
    public String f767f;

    /* renamed from: g, reason: collision with other field name */
    public String f768g;

    /* renamed from: h, reason: collision with other field name */
    public String f769h;

    /* renamed from: i, reason: collision with other field name */
    public String f770i;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f754a = new ko("XmPushActionNotification");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24230a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24231b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24232c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24233d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24234e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24235f = new kg("", (byte) 2, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24236g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24237h = new kg("", (byte) 13, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24238i = new kg("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24239j = new kg("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24240k = new kg("", (byte) 11, 12);

    /* renamed from: l, reason: collision with root package name */
    private static final kg f24241l = new kg("", (byte) 11, 13);

    /* renamed from: m, reason: collision with root package name */
    private static final kg f24242m = new kg("", (byte) 11, 14);

    /* renamed from: n, reason: collision with root package name */
    private static final kg f24243n = new kg("", (byte) 10, 15);

    /* renamed from: o, reason: collision with root package name */
    private static final kg f24244o = new kg("", (byte) 2, 20);

    public jm() {
        this.f759a = new BitSet(3);
        this.f761a = true;
        this.f763b = false;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m612a() {
        return this.f757a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m615b() {
        return this.f756a != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m616c() {
        return this.f762b != null;
    }

    public boolean d() {
        return this.f764c != null;
    }

    public boolean e() {
        return this.f765d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jm)) {
            return m613a((jm) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f759a.get(0);
    }

    public boolean g() {
        return this.f766e != null;
    }

    public boolean h() {
        return this.f760a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f767f != null;
    }

    public boolean j() {
        return this.f768g != null;
    }

    public boolean k() {
        return this.f769h != null;
    }

    public boolean l() {
        return this.f770i != null;
    }

    public boolean m() {
        return this.f758a != null;
    }

    public boolean n() {
        return this.f759a.get(1);
    }

    public boolean o() {
        return this.f759a.get(2);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionNotification(");
        boolean z3 = false;
        if (m612a()) {
            sb.append("debug:");
            String str = this.f757a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m615b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            jc jcVar = this.f756a;
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
        String str2 = this.f762b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.f764c;
            if (str3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("type:");
            String str4 = this.f765d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        sb.append(", ");
        sb.append("requireAck:");
        sb.append(this.f761a);
        if (g()) {
            sb.append(", ");
            sb.append("payload:");
            String str5 = this.f766e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f760a;
            if (map == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f767f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f768g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("regId:");
            String str8 = this.f769h;
            if (str8 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str8);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.f770i;
            if (str9 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str9);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("binaryExtra:");
            ByteBuffer byteBuffer = this.f758a;
            if (byteBuffer == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                jz.a(byteBuffer, sb);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f755a);
        }
        if (o()) {
            sb.append(", ");
            sb.append("alreadyLogClickInXmq:");
            sb.append(this.f763b);
        }
        sb.append(")");
        return sb.toString();
    }

    public jc a() {
        return this.f756a;
    }

    public String b() {
        return this.f764c;
    }

    public jm c(String str) {
        this.f765d = str;
        return this;
    }

    public jm d(String str) {
        this.f767f = str;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m608a() {
        return this.f762b;
    }

    public jm b(String str) {
        this.f764c = str;
        return this;
    }

    public String c() {
        return this.f767f;
    }

    public jm a(String str) {
        this.f762b = str;
        return this;
    }

    public void b(boolean z2) {
        this.f759a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f759a.set(2, z2);
    }

    public jm(String str, boolean z2) {
        this();
        this.f762b = str;
        this.f761a = z2;
        m611a(true);
    }

    public jm a(boolean z2) {
        this.f761a = z2;
        m611a(true);
        return this;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m610a();
        kjVar.a(f754a);
        if (this.f757a != null && m612a()) {
            kjVar.a(f24230a);
            kjVar.a(this.f757a);
            kjVar.b();
        }
        if (this.f756a != null && m615b()) {
            kjVar.a(f24231b);
            this.f756a.b(kjVar);
            kjVar.b();
        }
        if (this.f762b != null) {
            kjVar.a(f24232c);
            kjVar.a(this.f762b);
            kjVar.b();
        }
        if (this.f764c != null && d()) {
            kjVar.a(f24233d);
            kjVar.a(this.f764c);
            kjVar.b();
        }
        if (this.f765d != null && e()) {
            kjVar.a(f24234e);
            kjVar.a(this.f765d);
            kjVar.b();
        }
        kjVar.a(f24235f);
        kjVar.a(this.f761a);
        kjVar.b();
        if (this.f766e != null && g()) {
            kjVar.a(f24236g);
            kjVar.a(this.f766e);
            kjVar.b();
        }
        if (this.f760a != null && h()) {
            kjVar.a(f24237h);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f760a.size()));
            for (Map.Entry<String, String> entry : this.f760a.entrySet()) {
                kjVar.a(entry.getKey());
                kjVar.a(entry.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        if (this.f767f != null && i()) {
            kjVar.a(f24238i);
            kjVar.a(this.f767f);
            kjVar.b();
        }
        if (this.f768g != null && j()) {
            kjVar.a(f24239j);
            kjVar.a(this.f768g);
            kjVar.b();
        }
        if (this.f769h != null && k()) {
            kjVar.a(f24240k);
            kjVar.a(this.f769h);
            kjVar.b();
        }
        if (this.f770i != null && l()) {
            kjVar.a(f24241l);
            kjVar.a(this.f770i);
            kjVar.b();
        }
        if (this.f758a != null && m()) {
            kjVar.a(f24242m);
            kjVar.a(this.f758a);
            kjVar.b();
        }
        if (n()) {
            kjVar.a(f24243n);
            kjVar.a(this.f755a);
            kjVar.b();
        }
        if (o()) {
            kjVar.a(f24244o);
            kjVar.a(this.f763b);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m611a(boolean z2) {
        this.f759a.set(0, z2);
    }

    public void a(String str, String str2) {
        if (this.f760a == null) {
            this.f760a = new HashMap();
        }
        this.f760a.put(str, str2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public Map<String, String> m609a() {
        return this.f760a;
    }

    public jm a(Map<String, String> map) {
        this.f760a = map;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m614a() {
        a(jz.a(this.f758a));
        return this.f758a.array();
    }

    public jm a(byte[] bArr) {
        a(ByteBuffer.wrap(bArr));
        return this;
    }

    public jm a(ByteBuffer byteBuffer) {
        this.f758a = byteBuffer;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m613a(jm jmVar) {
        if (jmVar == null) {
            return false;
        }
        boolean zM612a = m612a();
        boolean zM612a2 = jmVar.m612a();
        if ((zM612a || zM612a2) && !(zM612a && zM612a2 && this.f757a.equals(jmVar.f757a))) {
            return false;
        }
        boolean zM615b = m615b();
        boolean zM615b2 = jmVar.m615b();
        if ((zM615b || zM615b2) && !(zM615b && zM615b2 && this.f756a.m571a(jmVar.f756a))) {
            return false;
        }
        boolean zM616c = m616c();
        boolean zM616c2 = jmVar.m616c();
        if ((zM616c || zM616c2) && !(zM616c && zM616c2 && this.f762b.equals(jmVar.f762b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jmVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f764c.equals(jmVar.f764c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jmVar.e();
        if (((zE || zE2) && !(zE && zE2 && this.f765d.equals(jmVar.f765d))) || this.f761a != jmVar.f761a) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jmVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f766e.equals(jmVar.f766e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jmVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f760a.equals(jmVar.f760a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jmVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f767f.equals(jmVar.f767f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jmVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f768g.equals(jmVar.f768g))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jmVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f769h.equals(jmVar.f769h))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jmVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f770i.equals(jmVar.f770i))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = jmVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f758a.equals(jmVar.f758a))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = jmVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f755a == jmVar.f755a)) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = jmVar.o();
        if (zO || zO2) {
            return zO && zO2 && this.f763b == jmVar.f763b;
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jm jmVar) {
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
        if (!getClass().equals(jmVar.getClass())) {
            return getClass().getName().compareTo(jmVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m612a()).compareTo(Boolean.valueOf(jmVar.m612a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m612a() && (iA15 = jz.a(this.f757a, jmVar.f757a)) != 0) {
            return iA15;
        }
        int iCompareTo2 = Boolean.valueOf(m615b()).compareTo(Boolean.valueOf(jmVar.m615b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m615b() && (iA14 = jz.a(this.f756a, jmVar.f756a)) != 0) {
            return iA14;
        }
        int iCompareTo3 = Boolean.valueOf(m616c()).compareTo(Boolean.valueOf(jmVar.m616c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m616c() && (iA13 = jz.a(this.f762b, jmVar.f762b)) != 0) {
            return iA13;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jmVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA12 = jz.a(this.f764c, jmVar.f764c)) != 0) {
            return iA12;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jmVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA11 = jz.a(this.f765d, jmVar.f765d)) != 0) {
            return iA11;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jmVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA10 = jz.a(this.f761a, jmVar.f761a)) != 0) {
            return iA10;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jmVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA9 = jz.a(this.f766e, jmVar.f766e)) != 0) {
            return iA9;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jmVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA8 = jz.a(this.f760a, jmVar.f760a)) != 0) {
            return iA8;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jmVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA7 = jz.a(this.f767f, jmVar.f767f)) != 0) {
            return iA7;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jmVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA6 = jz.a(this.f768g, jmVar.f768g)) != 0) {
            return iA6;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jmVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA5 = jz.a(this.f769h, jmVar.f769h)) != 0) {
            return iA5;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jmVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA4 = jz.a(this.f770i, jmVar.f770i)) != 0) {
            return iA4;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(jmVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA3 = jz.a(this.f758a, jmVar.f758a)) != 0) {
            return iA3;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(jmVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA2 = jz.a(this.f755a, jmVar.f755a)) != 0) {
            return iA2;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(jmVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (!o() || (iA = jz.a(this.f763b, jmVar.f763b)) == 0) {
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
                if (f()) {
                    m610a();
                    return;
                }
                throw new kk("Required field 'requireAck' was not found in serialized data! Struct: " + toString());
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f757a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f756a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f762b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f764c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f765d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 2) {
                        this.f761a = kjVar.mo680a();
                        m611a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f766e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 13) {
                        ki kiVarMo672a = kjVar.mo672a();
                        this.f760a = new HashMap(kiVarMo672a.f924a * 2);
                        for (int i2 = 0; i2 < kiVarMo672a.f924a; i2++) {
                            this.f760a.put(kjVar.mo675a(), kjVar.mo675a());
                        }
                        kjVar.h();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f767f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 11) {
                        this.f768g = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                case 16:
                case 17:
                case 18:
                case 19:
                default:
                    km.a(kjVar, b2);
                    break;
                case 12:
                    if (b2 == 11) {
                        this.f769h = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 13:
                    if (b2 == 11) {
                        this.f770i = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 14:
                    if (b2 == 11) {
                        this.f758a = kjVar.mo676a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 15:
                    if (b2 == 10) {
                        this.f755a = kjVar.mo669a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 20:
                    if (b2 == 2) {
                        this.f763b = kjVar.mo680a();
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
    public void m610a() throws kk {
        if (this.f762b != null) {
            return;
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
