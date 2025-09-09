package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ja implements jy<ja, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f652a;

    /* renamed from: a, reason: collision with other field name */
    public long f653a;

    /* renamed from: a, reason: collision with other field name */
    public String f654a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f655a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f656a;

    /* renamed from: a, reason: collision with other field name */
    public boolean f657a;

    /* renamed from: b, reason: collision with other field name */
    public int f658b;

    /* renamed from: b, reason: collision with other field name */
    public String f659b;

    /* renamed from: b, reason: collision with other field name */
    public Map<String, String> f660b;

    /* renamed from: c, reason: collision with other field name */
    public int f661c;

    /* renamed from: c, reason: collision with other field name */
    public String f662c;

    /* renamed from: c, reason: collision with other field name */
    public Map<String, String> f663c;

    /* renamed from: d, reason: collision with other field name */
    public String f664d;

    /* renamed from: e, reason: collision with other field name */
    public String f665e;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f651a = new ko("PushMetaInfo");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24145a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24146b = new kg("", (byte) 10, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24147c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24148d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24149e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24150f = new kg("", (byte) 8, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24151g = new kg("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24152h = new kg("", (byte) 8, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24153i = new kg("", (byte) 8, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24154j = new kg("", (byte) 13, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24155k = new kg("", (byte) 13, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final kg f24156l = new kg("", (byte) 2, 12);

    /* renamed from: m, reason: collision with root package name */
    private static final kg f24157m = new kg("", (byte) 13, 13);

    public ja() {
        this.f655a = new BitSet(5);
        this.f657a = false;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ja m558a() {
        return new ja(this);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m566b() {
        return this.f655a.get(0);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m568c() {
        return this.f659b != null;
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m569d() {
        return this.f662c != null;
    }

    public boolean e() {
        return this.f664d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ja)) {
            return m563a((ja) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f655a.get(1);
    }

    public boolean g() {
        return this.f665e != null;
    }

    public boolean h() {
        return this.f655a.get(2);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f655a.get(3);
    }

    public boolean j() {
        return this.f656a != null;
    }

    public boolean k() {
        return this.f660b != null;
    }

    public boolean l() {
        return this.f657a;
    }

    public boolean m() {
        return this.f655a.get(4);
    }

    public boolean n() {
        return this.f663c != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PushMetaInfo(");
        sb.append("id:");
        String str = this.f654a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(com.xiaomi.push.service.bc.a(str));
        }
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.f653a);
        if (m568c()) {
            sb.append(", ");
            sb.append("topic:");
            String str2 = this.f659b;
            if (str2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str2);
            }
        }
        if (m569d()) {
            sb.append(", ");
            sb.append("title:");
            String str3 = this.f662c;
            if (str3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("description:");
            String str4 = this.f664d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("notifyType:");
            sb.append(this.f652a);
        }
        if (g()) {
            sb.append(", ");
            sb.append("url:");
            String str5 = this.f665e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.f658b);
        }
        if (i()) {
            sb.append(", ");
            sb.append("notifyId:");
            sb.append(this.f661c);
        }
        if (j()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f656a;
            if (map == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("internal:");
            Map<String, String> map2 = this.f660b;
            if (map2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map2);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("ignoreRegInfo:");
            sb.append(this.f657a);
        }
        if (n()) {
            sb.append(", ");
            sb.append("apsProperFields:");
            Map<String, String> map3 = this.f663c;
            if (map3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map3);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m559a() {
        return this.f654a;
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m564b() {
        return this.f659b;
    }

    /* renamed from: c, reason: collision with other method in class */
    public String m567c() {
        return this.f662c;
    }

    public String d() {
        return this.f664d;
    }

    public void e(boolean z2) {
        this.f655a.set(4, z2);
    }

    public ja a(String str) {
        this.f654a = str;
        return this;
    }

    public ja b(String str) {
        this.f659b = str;
        return this;
    }

    public ja c(String str) {
        this.f662c = str;
        return this;
    }

    public ja d(String str) {
        this.f664d = str;
        return this;
    }

    public ja(ja jaVar) {
        BitSet bitSet = new BitSet(5);
        this.f655a = bitSet;
        bitSet.clear();
        this.f655a.or(jaVar.f655a);
        if (jaVar.m562a()) {
            this.f654a = jaVar.f654a;
        }
        this.f653a = jaVar.f653a;
        if (jaVar.m568c()) {
            this.f659b = jaVar.f659b;
        }
        if (jaVar.m569d()) {
            this.f662c = jaVar.f662c;
        }
        if (jaVar.e()) {
            this.f664d = jaVar.f664d;
        }
        this.f652a = jaVar.f652a;
        if (jaVar.g()) {
            this.f665e = jaVar.f665e;
        }
        this.f658b = jaVar.f658b;
        this.f661c = jaVar.f661c;
        if (jaVar.j()) {
            HashMap map = new HashMap();
            for (Map.Entry<String, String> entry : jaVar.f656a.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            this.f656a = map;
        }
        if (jaVar.k()) {
            HashMap map2 = new HashMap();
            for (Map.Entry<String, String> entry2 : jaVar.f660b.entrySet()) {
                map2.put(entry2.getKey(), entry2.getValue());
            }
            this.f660b = map2;
        }
        this.f657a = jaVar.f657a;
        if (jaVar.n()) {
            HashMap map3 = new HashMap();
            for (Map.Entry<String, String> entry3 : jaVar.f663c.entrySet()) {
                map3.put(entry3.getKey(), entry3.getValue());
            }
            this.f663c = map3;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m562a() {
        return this.f654a != null;
    }

    public void b(boolean z2) {
        this.f655a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f655a.set(2, z2);
    }

    public void d(boolean z2) {
        this.f655a.set(3, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m557a() {
        return this.f653a;
    }

    public int b() {
        return this.f658b;
    }

    public int c() {
        return this.f661c;
    }

    public void a(boolean z2) {
        this.f655a.set(0, z2);
    }

    public ja b(int i2) {
        this.f658b = i2;
        c(true);
        return this;
    }

    public ja c(int i2) {
        this.f661c = i2;
        d(true);
        return this;
    }

    public int a() {
        return this.f652a;
    }

    public ja a(int i2) {
        this.f652a = i2;
        b(true);
        return this;
    }

    public void b(String str, String str2) {
        if (this.f660b == null) {
            this.f660b = new HashMap();
        }
        this.f660b.put(str, str2);
    }

    public void a(String str, String str2) {
        if (this.f656a == null) {
            this.f656a = new HashMap();
        }
        this.f656a.put(str, str2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public Map<String, String> m565b() {
        return this.f660b;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m561a();
        kjVar.a(f651a);
        if (this.f654a != null) {
            kjVar.a(f24145a);
            kjVar.a(this.f654a);
            kjVar.b();
        }
        kjVar.a(f24146b);
        kjVar.a(this.f653a);
        kjVar.b();
        if (this.f659b != null && m568c()) {
            kjVar.a(f24147c);
            kjVar.a(this.f659b);
            kjVar.b();
        }
        if (this.f662c != null && m569d()) {
            kjVar.a(f24148d);
            kjVar.a(this.f662c);
            kjVar.b();
        }
        if (this.f664d != null && e()) {
            kjVar.a(f24149e);
            kjVar.a(this.f664d);
            kjVar.b();
        }
        if (f()) {
            kjVar.a(f24150f);
            kjVar.mo679a(this.f652a);
            kjVar.b();
        }
        if (this.f665e != null && g()) {
            kjVar.a(f24151g);
            kjVar.a(this.f665e);
            kjVar.b();
        }
        if (h()) {
            kjVar.a(f24152h);
            kjVar.mo679a(this.f658b);
            kjVar.b();
        }
        if (i()) {
            kjVar.a(f24153i);
            kjVar.mo679a(this.f661c);
            kjVar.b();
        }
        if (this.f656a != null && j()) {
            kjVar.a(f24154j);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f656a.size()));
            for (Map.Entry<String, String> entry : this.f656a.entrySet()) {
                kjVar.a(entry.getKey());
                kjVar.a(entry.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        if (this.f660b != null && k()) {
            kjVar.a(f24155k);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f660b.size()));
            for (Map.Entry<String, String> entry2 : this.f660b.entrySet()) {
                kjVar.a(entry2.getKey());
                kjVar.a(entry2.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        if (m()) {
            kjVar.a(f24156l);
            kjVar.a(this.f657a);
            kjVar.b();
        }
        if (this.f663c != null && n()) {
            kjVar.a(f24157m);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f663c.size()));
            for (Map.Entry<String, String> entry3 : this.f663c.entrySet()) {
                kjVar.a(entry3.getKey());
                kjVar.a(entry3.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public Map<String, String> m560a() {
        return this.f656a;
    }

    public ja a(Map<String, String> map) {
        this.f656a = map;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m563a(ja jaVar) {
        if (jaVar == null) {
            return false;
        }
        boolean zM562a = m562a();
        boolean zM562a2 = jaVar.m562a();
        if (((zM562a || zM562a2) && !(zM562a && zM562a2 && this.f654a.equals(jaVar.f654a))) || this.f653a != jaVar.f653a) {
            return false;
        }
        boolean zM568c = m568c();
        boolean zM568c2 = jaVar.m568c();
        if ((zM568c || zM568c2) && !(zM568c && zM568c2 && this.f659b.equals(jaVar.f659b))) {
            return false;
        }
        boolean zM569d = m569d();
        boolean zM569d2 = jaVar.m569d();
        if ((zM569d || zM569d2) && !(zM569d && zM569d2 && this.f662c.equals(jaVar.f662c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jaVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f664d.equals(jaVar.f664d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jaVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f652a == jaVar.f652a)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jaVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f665e.equals(jaVar.f665e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jaVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f658b == jaVar.f658b)) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jaVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f661c == jaVar.f661c)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jaVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f656a.equals(jaVar.f656a))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jaVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f660b.equals(jaVar.f660b))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = jaVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f657a == jaVar.f657a)) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = jaVar.n();
        if (zN || zN2) {
            return zN && zN2 && this.f663c.equals(jaVar.f663c);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ja jaVar) {
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
        if (!getClass().equals(jaVar.getClass())) {
            return getClass().getName().compareTo(jaVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m562a()).compareTo(Boolean.valueOf(jaVar.m562a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m562a() && (iA13 = jz.a(this.f654a, jaVar.f654a)) != 0) {
            return iA13;
        }
        int iCompareTo2 = Boolean.valueOf(m566b()).compareTo(Boolean.valueOf(jaVar.m566b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m566b() && (iA12 = jz.a(this.f653a, jaVar.f653a)) != 0) {
            return iA12;
        }
        int iCompareTo3 = Boolean.valueOf(m568c()).compareTo(Boolean.valueOf(jaVar.m568c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m568c() && (iA11 = jz.a(this.f659b, jaVar.f659b)) != 0) {
            return iA11;
        }
        int iCompareTo4 = Boolean.valueOf(m569d()).compareTo(Boolean.valueOf(jaVar.m569d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (m569d() && (iA10 = jz.a(this.f662c, jaVar.f662c)) != 0) {
            return iA10;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jaVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA9 = jz.a(this.f664d, jaVar.f664d)) != 0) {
            return iA9;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jaVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA8 = jz.a(this.f652a, jaVar.f652a)) != 0) {
            return iA8;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jaVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA7 = jz.a(this.f665e, jaVar.f665e)) != 0) {
            return iA7;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jaVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA6 = jz.a(this.f658b, jaVar.f658b)) != 0) {
            return iA6;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jaVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA5 = jz.a(this.f661c, jaVar.f661c)) != 0) {
            return iA5;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jaVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA4 = jz.a(this.f656a, jaVar.f656a)) != 0) {
            return iA4;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jaVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA3 = jz.a(this.f660b, jaVar.f660b)) != 0) {
            return iA3;
        }
        int iCompareTo12 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(jaVar.m()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (m() && (iA2 = jz.a(this.f657a, jaVar.f657a)) != 0) {
            return iA2;
        }
        int iCompareTo13 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(jaVar.n()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (!n() || (iA = jz.a(this.f663c, jaVar.f663c)) == 0) {
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
                if (m566b()) {
                    m561a();
                    return;
                }
                throw new kk("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
            }
            int i2 = 0;
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f654a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 10) {
                        this.f653a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f659b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f662c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f664d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 8) {
                        this.f652a = kjVar.mo668a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f665e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 8) {
                        this.f658b = kjVar.mo668a();
                        c(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 8) {
                        this.f661c = kjVar.mo668a();
                        d(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 13) {
                        ki kiVarMo672a = kjVar.mo672a();
                        this.f656a = new HashMap(kiVarMo672a.f924a * 2);
                        while (i2 < kiVarMo672a.f924a) {
                            this.f656a.put(kjVar.mo675a(), kjVar.mo675a());
                            i2++;
                        }
                        kjVar.h();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 13) {
                        ki kiVarMo672a2 = kjVar.mo672a();
                        this.f660b = new HashMap(kiVarMo672a2.f924a * 2);
                        while (i2 < kiVarMo672a2.f924a) {
                            this.f660b.put(kjVar.mo675a(), kjVar.mo675a());
                            i2++;
                        }
                        kjVar.h();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 12:
                    if (b2 == 2) {
                        this.f657a = kjVar.mo680a();
                        e(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 13:
                    if (b2 == 13) {
                        ki kiVarMo672a3 = kjVar.mo672a();
                        this.f663c = new HashMap(kiVarMo672a3.f924a * 2);
                        while (i2 < kiVarMo672a3.f924a) {
                            this.f663c.put(kjVar.mo675a(), kjVar.mo675a());
                            i2++;
                        }
                        kjVar.h();
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
    public void m561a() throws kk {
        if (this.f654a != null) {
            return;
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
