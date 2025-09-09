package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class jq implements jy<jq, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public iz f834a;

    /* renamed from: a, reason: collision with other field name */
    public jc f835a;

    /* renamed from: a, reason: collision with other field name */
    public String f836a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f838a;

    /* renamed from: b, reason: collision with other field name */
    public String f840b;

    /* renamed from: c, reason: collision with other field name */
    public String f841c;

    /* renamed from: d, reason: collision with other field name */
    public String f842d;

    /* renamed from: e, reason: collision with other field name */
    public String f843e;

    /* renamed from: f, reason: collision with other field name */
    public String f844f;

    /* renamed from: g, reason: collision with other field name */
    public String f845g;

    /* renamed from: h, reason: collision with other field name */
    public String f846h;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f833a = new ko("XmPushActionSendMessage");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24298a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24299b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24300c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24301d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24302e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24303f = new kg("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24304g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24305h = new kg("", (byte) 12, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24306i = new kg("", (byte) 2, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24307j = new kg("", (byte) 13, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24308k = new kg("", (byte) 11, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final kg f24309l = new kg("", (byte) 11, 12);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f837a = new BitSet(1);

    /* renamed from: a, reason: collision with other field name */
    public boolean f839a = true;

    /* renamed from: a, reason: collision with other method in class */
    public boolean m633a() {
        return this.f836a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m635b() {
        return this.f835a != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m636c() {
        return this.f840b != null;
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m637d() {
        return this.f841c != null;
    }

    /* renamed from: e, reason: collision with other method in class */
    public boolean m638e() {
        return this.f842d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jq)) {
            return m634a((jq) obj);
        }
        return false;
    }

    /* renamed from: f, reason: collision with other method in class */
    public boolean m639f() {
        return this.f843e != null;
    }

    public boolean g() {
        return this.f844f != null;
    }

    public boolean h() {
        return this.f834a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f837a.get(0);
    }

    public boolean j() {
        return this.f838a != null;
    }

    public boolean k() {
        return this.f845g != null;
    }

    public boolean l() {
        return this.f846h != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSendMessage(");
        boolean z3 = false;
        if (m633a()) {
            sb.append("debug:");
            String str = this.f836a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m635b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            jc jcVar = this.f835a;
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
        String str2 = this.f840b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f841c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        if (m638e()) {
            sb.append(", ");
            sb.append("packageName:");
            String str4 = this.f842d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (m639f()) {
            sb.append(", ");
            sb.append("topic:");
            String str5 = this.f843e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str6 = this.f844f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("message:");
            iz izVar = this.f834a;
            if (izVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(izVar);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("needAck:");
            sb.append(this.f839a);
        }
        if (j()) {
            sb.append(", ");
            sb.append("params:");
            Map<String, String> map = this.f838a;
            if (map == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f845g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str8 = this.f846h;
            if (str8 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str8);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m631a() {
        return this.f840b;
    }

    public String b() {
        return this.f841c;
    }

    public String c() {
        return this.f843e;
    }

    public String d() {
        return this.f844f;
    }

    public String e() {
        return this.f845g;
    }

    public String f() {
        return this.f846h;
    }

    public iz a() {
        return this.f834a;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m632a();
        kjVar.a(f833a);
        if (this.f836a != null && m633a()) {
            kjVar.a(f24298a);
            kjVar.a(this.f836a);
            kjVar.b();
        }
        if (this.f835a != null && m635b()) {
            kjVar.a(f24299b);
            this.f835a.b(kjVar);
            kjVar.b();
        }
        if (this.f840b != null) {
            kjVar.a(f24300c);
            kjVar.a(this.f840b);
            kjVar.b();
        }
        if (this.f841c != null) {
            kjVar.a(f24301d);
            kjVar.a(this.f841c);
            kjVar.b();
        }
        if (this.f842d != null && m638e()) {
            kjVar.a(f24302e);
            kjVar.a(this.f842d);
            kjVar.b();
        }
        if (this.f843e != null && m639f()) {
            kjVar.a(f24303f);
            kjVar.a(this.f843e);
            kjVar.b();
        }
        if (this.f844f != null && g()) {
            kjVar.a(f24304g);
            kjVar.a(this.f844f);
            kjVar.b();
        }
        if (this.f834a != null && h()) {
            kjVar.a(f24305h);
            this.f834a.b(kjVar);
            kjVar.b();
        }
        if (i()) {
            kjVar.a(f24306i);
            kjVar.a(this.f839a);
            kjVar.b();
        }
        if (this.f838a != null && j()) {
            kjVar.a(f24307j);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f838a.size()));
            for (Map.Entry<String, String> entry : this.f838a.entrySet()) {
                kjVar.a(entry.getKey());
                kjVar.a(entry.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        if (this.f845g != null && k()) {
            kjVar.a(f24308k);
            kjVar.a(this.f845g);
            kjVar.b();
        }
        if (this.f846h != null && l()) {
            kjVar.a(f24309l);
            kjVar.a(this.f846h);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public void a(boolean z2) {
        this.f837a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m634a(jq jqVar) {
        if (jqVar == null) {
            return false;
        }
        boolean zM633a = m633a();
        boolean zM633a2 = jqVar.m633a();
        if ((zM633a || zM633a2) && !(zM633a && zM633a2 && this.f836a.equals(jqVar.f836a))) {
            return false;
        }
        boolean zM635b = m635b();
        boolean zM635b2 = jqVar.m635b();
        if ((zM635b || zM635b2) && !(zM635b && zM635b2 && this.f835a.m571a(jqVar.f835a))) {
            return false;
        }
        boolean zM636c = m636c();
        boolean zM636c2 = jqVar.m636c();
        if ((zM636c || zM636c2) && !(zM636c && zM636c2 && this.f840b.equals(jqVar.f840b))) {
            return false;
        }
        boolean zM637d = m637d();
        boolean zM637d2 = jqVar.m637d();
        if ((zM637d || zM637d2) && !(zM637d && zM637d2 && this.f841c.equals(jqVar.f841c))) {
            return false;
        }
        boolean zM638e = m638e();
        boolean zM638e2 = jqVar.m638e();
        if ((zM638e || zM638e2) && !(zM638e && zM638e2 && this.f842d.equals(jqVar.f842d))) {
            return false;
        }
        boolean zM639f = m639f();
        boolean zM639f2 = jqVar.m639f();
        if ((zM639f || zM639f2) && !(zM639f && zM639f2 && this.f843e.equals(jqVar.f843e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jqVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f844f.equals(jqVar.f844f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jqVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f834a.m543a(jqVar.f834a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jqVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f839a == jqVar.f839a)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jqVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f838a.equals(jqVar.f838a))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jqVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f845g.equals(jqVar.f845g))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jqVar.l();
        if (zL || zL2) {
            return zL && zL2 && this.f846h.equals(jqVar.f846h);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jq jqVar) {
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
        if (!getClass().equals(jqVar.getClass())) {
            return getClass().getName().compareTo(jqVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m633a()).compareTo(Boolean.valueOf(jqVar.m633a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m633a() && (iA12 = jz.a(this.f836a, jqVar.f836a)) != 0) {
            return iA12;
        }
        int iCompareTo2 = Boolean.valueOf(m635b()).compareTo(Boolean.valueOf(jqVar.m635b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m635b() && (iA11 = jz.a(this.f835a, jqVar.f835a)) != 0) {
            return iA11;
        }
        int iCompareTo3 = Boolean.valueOf(m636c()).compareTo(Boolean.valueOf(jqVar.m636c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m636c() && (iA10 = jz.a(this.f840b, jqVar.f840b)) != 0) {
            return iA10;
        }
        int iCompareTo4 = Boolean.valueOf(m637d()).compareTo(Boolean.valueOf(jqVar.m637d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (m637d() && (iA9 = jz.a(this.f841c, jqVar.f841c)) != 0) {
            return iA9;
        }
        int iCompareTo5 = Boolean.valueOf(m638e()).compareTo(Boolean.valueOf(jqVar.m638e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (m638e() && (iA8 = jz.a(this.f842d, jqVar.f842d)) != 0) {
            return iA8;
        }
        int iCompareTo6 = Boolean.valueOf(m639f()).compareTo(Boolean.valueOf(jqVar.m639f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (m639f() && (iA7 = jz.a(this.f843e, jqVar.f843e)) != 0) {
            return iA7;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jqVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA6 = jz.a(this.f844f, jqVar.f844f)) != 0) {
            return iA6;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jqVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA5 = jz.a(this.f834a, jqVar.f834a)) != 0) {
            return iA5;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jqVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA4 = jz.a(this.f839a, jqVar.f839a)) != 0) {
            return iA4;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jqVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA3 = jz.a(this.f838a, jqVar.f838a)) != 0) {
            return iA3;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jqVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA2 = jz.a(this.f845g, jqVar.f845g)) != 0) {
            return iA2;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jqVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (!l() || (iA = jz.a(this.f846h, jqVar.f846h)) == 0) {
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
                m632a();
                return;
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f836a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f835a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f840b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f841c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f842d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 11) {
                        this.f843e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f844f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 12) {
                        iz izVar = new iz();
                        this.f834a = izVar;
                        izVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 2) {
                        this.f839a = kjVar.mo680a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 13) {
                        ki kiVarMo672a = kjVar.mo672a();
                        this.f838a = new HashMap(kiVarMo672a.f924a * 2);
                        for (int i2 = 0; i2 < kiVarMo672a.f924a; i2++) {
                            this.f838a.put(kjVar.mo675a(), kjVar.mo675a());
                        }
                        kjVar.h();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 11) {
                        this.f845g = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 12:
                    if (b2 == 11) {
                        this.f846h = kjVar.mo675a();
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

    /* renamed from: a, reason: collision with other method in class */
    public void m632a() throws kk {
        if (this.f840b != null) {
            if (this.f841c != null) {
                return;
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
