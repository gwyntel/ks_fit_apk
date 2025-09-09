package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ir implements jy<ir, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f591a;

    /* renamed from: a, reason: collision with other field name */
    public String f592a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f593a = new BitSet(3);

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f594a;

    /* renamed from: a, reason: collision with other field name */
    public boolean f595a;

    /* renamed from: b, reason: collision with other field name */
    public long f596b;

    /* renamed from: b, reason: collision with other field name */
    public String f597b;

    /* renamed from: c, reason: collision with other field name */
    public String f598c;

    /* renamed from: d, reason: collision with other field name */
    public String f599d;

    /* renamed from: e, reason: collision with other field name */
    public String f600e;

    /* renamed from: f, reason: collision with other field name */
    public String f601f;

    /* renamed from: g, reason: collision with other field name */
    public String f602g;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f590a = new ko("ClientUploadDataItem");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24043a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24044b = new kg("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24045c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24046d = new kg("", (byte) 10, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24047e = new kg("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24048f = new kg("", (byte) 2, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24049g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24050h = new kg("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24051i = new kg("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24052j = new kg("", (byte) 13, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24053k = new kg("", (byte) 11, 11);

    /* renamed from: a, reason: collision with other method in class */
    public void m518a() {
    }

    public ir b(String str) {
        this.f597b = str;
        return this;
    }

    public ir c(String str) {
        this.f598c = str;
        return this;
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m524d() {
        return this.f593a.get(0);
    }

    /* renamed from: e, reason: collision with other method in class */
    public boolean m525e() {
        return this.f593a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ir)) {
            return m521a((ir) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f593a.get(2);
    }

    public boolean g() {
        return this.f599d != null;
    }

    public boolean h() {
        return this.f600e != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f601f != null;
    }

    public boolean j() {
        return this.f594a != null;
    }

    public boolean k() {
        return this.f602g != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("ClientUploadDataItem(");
        boolean z3 = false;
        if (m520a()) {
            sb.append("channel:");
            String str = this.f592a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m522b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("data:");
            String str2 = this.f597b;
            if (str2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str2);
            }
            z2 = false;
        }
        if (m523c()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("name:");
            String str3 = this.f598c;
            if (str3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str3);
            }
            z2 = false;
        }
        if (m524d()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("counter:");
            sb.append(this.f591a);
            z2 = false;
        }
        if (m525e()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("timestamp:");
            sb.append(this.f596b);
            z2 = false;
        }
        if (f()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("fromSdk:");
            sb.append(this.f595a);
            z2 = false;
        }
        if (g()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("category:");
            String str4 = this.f599d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
            z2 = false;
        }
        if (h()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("sourcePackage:");
            String str5 = this.f600e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
            z2 = false;
        }
        if (i()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("id:");
            String str6 = this.f601f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
            z2 = false;
        }
        if (j()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("extra:");
            Map<String, String> map = this.f594a;
            if (map == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map);
            }
        } else {
            z3 = z2;
        }
        if (k()) {
            if (!z3) {
                sb.append(", ");
            }
            sb.append("pkgName:");
            String str7 = this.f602g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m516a() {
        return this.f592a;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m522b() {
        return this.f597b != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m523c() {
        return this.f598c != null;
    }

    public ir d(String str) {
        this.f599d = str;
        return this;
    }

    public ir e(String str) {
        this.f600e = str;
        return this;
    }

    public ir f(String str) {
        this.f601f = str;
        return this;
    }

    public ir g(String str) {
        this.f602g = str;
        return this;
    }

    public ir a(String str) {
        this.f592a = str;
        return this;
    }

    public String b() {
        return this.f598c;
    }

    public void c(boolean z2) {
        this.f593a.set(2, z2);
    }

    public String d() {
        return this.f601f;
    }

    public String e() {
        return this.f602g;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m520a() {
        return this.f592a != null;
    }

    public ir b(long j2) {
        this.f596b = j2;
        b(true);
        return this;
    }

    public String c() {
        return this.f600e;
    }

    public ir a(long j2) {
        this.f591a = j2;
        m519a(true);
        return this;
    }

    public void b(boolean z2) {
        this.f593a.set(1, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m519a(boolean z2) {
        this.f593a.set(0, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) {
        m518a();
        kjVar.a(f590a);
        if (this.f592a != null && m520a()) {
            kjVar.a(f24043a);
            kjVar.a(this.f592a);
            kjVar.b();
        }
        if (this.f597b != null && m522b()) {
            kjVar.a(f24044b);
            kjVar.a(this.f597b);
            kjVar.b();
        }
        if (this.f598c != null && m523c()) {
            kjVar.a(f24045c);
            kjVar.a(this.f598c);
            kjVar.b();
        }
        if (m524d()) {
            kjVar.a(f24046d);
            kjVar.a(this.f591a);
            kjVar.b();
        }
        if (m525e()) {
            kjVar.a(f24047e);
            kjVar.a(this.f596b);
            kjVar.b();
        }
        if (f()) {
            kjVar.a(f24048f);
            kjVar.a(this.f595a);
            kjVar.b();
        }
        if (this.f599d != null && g()) {
            kjVar.a(f24049g);
            kjVar.a(this.f599d);
            kjVar.b();
        }
        if (this.f600e != null && h()) {
            kjVar.a(f24050h);
            kjVar.a(this.f600e);
            kjVar.b();
        }
        if (this.f601f != null && i()) {
            kjVar.a(f24051i);
            kjVar.a(this.f601f);
            kjVar.b();
        }
        if (this.f594a != null && j()) {
            kjVar.a(f24052j);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f594a.size()));
            for (Map.Entry<String, String> entry : this.f594a.entrySet()) {
                kjVar.a(entry.getKey());
                kjVar.a(entry.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        if (this.f602g != null && k()) {
            kjVar.a(f24053k);
            kjVar.a(this.f602g);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public long a() {
        return this.f596b;
    }

    public ir a(boolean z2) {
        this.f595a = z2;
        c(true);
        return this;
    }

    public void a(String str, String str2) {
        if (this.f594a == null) {
            this.f594a = new HashMap();
        }
        this.f594a.put(str, str2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public Map<String, String> m517a() {
        return this.f594a;
    }

    public ir a(Map<String, String> map) {
        this.f594a = map;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m521a(ir irVar) {
        if (irVar == null) {
            return false;
        }
        boolean zM520a = m520a();
        boolean zM520a2 = irVar.m520a();
        if ((zM520a || zM520a2) && !(zM520a && zM520a2 && this.f592a.equals(irVar.f592a))) {
            return false;
        }
        boolean zM522b = m522b();
        boolean zM522b2 = irVar.m522b();
        if ((zM522b || zM522b2) && !(zM522b && zM522b2 && this.f597b.equals(irVar.f597b))) {
            return false;
        }
        boolean zM523c = m523c();
        boolean zM523c2 = irVar.m523c();
        if ((zM523c || zM523c2) && !(zM523c && zM523c2 && this.f598c.equals(irVar.f598c))) {
            return false;
        }
        boolean zM524d = m524d();
        boolean zM524d2 = irVar.m524d();
        if ((zM524d || zM524d2) && !(zM524d && zM524d2 && this.f591a == irVar.f591a)) {
            return false;
        }
        boolean zM525e = m525e();
        boolean zM525e2 = irVar.m525e();
        if ((zM525e || zM525e2) && !(zM525e && zM525e2 && this.f596b == irVar.f596b)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = irVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f595a == irVar.f595a)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = irVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f599d.equals(irVar.f599d))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = irVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f600e.equals(irVar.f600e))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = irVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f601f.equals(irVar.f601f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = irVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f594a.equals(irVar.f594a))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = irVar.k();
        if (zK || zK2) {
            return zK && zK2 && this.f602g.equals(irVar.f602g);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ir irVar) {
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
        if (!getClass().equals(irVar.getClass())) {
            return getClass().getName().compareTo(irVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m520a()).compareTo(Boolean.valueOf(irVar.m520a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m520a() && (iA11 = jz.a(this.f592a, irVar.f592a)) != 0) {
            return iA11;
        }
        int iCompareTo2 = Boolean.valueOf(m522b()).compareTo(Boolean.valueOf(irVar.m522b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m522b() && (iA10 = jz.a(this.f597b, irVar.f597b)) != 0) {
            return iA10;
        }
        int iCompareTo3 = Boolean.valueOf(m523c()).compareTo(Boolean.valueOf(irVar.m523c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m523c() && (iA9 = jz.a(this.f598c, irVar.f598c)) != 0) {
            return iA9;
        }
        int iCompareTo4 = Boolean.valueOf(m524d()).compareTo(Boolean.valueOf(irVar.m524d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (m524d() && (iA8 = jz.a(this.f591a, irVar.f591a)) != 0) {
            return iA8;
        }
        int iCompareTo5 = Boolean.valueOf(m525e()).compareTo(Boolean.valueOf(irVar.m525e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (m525e() && (iA7 = jz.a(this.f596b, irVar.f596b)) != 0) {
            return iA7;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(irVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA6 = jz.a(this.f595a, irVar.f595a)) != 0) {
            return iA6;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(irVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA5 = jz.a(this.f599d, irVar.f599d)) != 0) {
            return iA5;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(irVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA4 = jz.a(this.f600e, irVar.f600e)) != 0) {
            return iA4;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(irVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA3 = jz.a(this.f601f, irVar.f601f)) != 0) {
            return iA3;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(irVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA2 = jz.a(this.f594a, irVar.f594a)) != 0) {
            return iA2;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(irVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (!k() || (iA = jz.a(this.f602g, irVar.f602g)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // com.xiaomi.push.jy
    public void a(kj kjVar) {
        kjVar.mo674a();
        while (true) {
            kg kgVarMo670a = kjVar.mo670a();
            byte b2 = kgVarMo670a.f24379a;
            if (b2 == 0) {
                kjVar.f();
                m518a();
                return;
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f592a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 11) {
                        this.f597b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f598c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 10) {
                        this.f591a = kjVar.mo669a();
                        m519a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 10) {
                        this.f596b = kjVar.mo669a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 2) {
                        this.f595a = kjVar.mo680a();
                        c(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f599d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f600e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f601f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 13) {
                        ki kiVarMo672a = kjVar.mo672a();
                        this.f594a = new HashMap(kiVarMo672a.f924a * 2);
                        for (int i2 = 0; i2 < kiVarMo672a.f924a; i2++) {
                            this.f594a.put(kjVar.mo675a(), kjVar.mo675a());
                        }
                        kjVar.h();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 11) {
                        this.f602g = kjVar.mo675a();
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
}
