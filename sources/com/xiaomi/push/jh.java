package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class jh implements jy<jh, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f717a;

    /* renamed from: a, reason: collision with other field name */
    public jc f718a;

    /* renamed from: a, reason: collision with other field name */
    public String f719a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f721a;

    /* renamed from: b, reason: collision with other field name */
    public String f723b;

    /* renamed from: c, reason: collision with other field name */
    public String f725c;

    /* renamed from: d, reason: collision with other field name */
    public String f726d;

    /* renamed from: e, reason: collision with other field name */
    public String f727e;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f716a = new ko("XmPushActionCommand");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24200a = new kg("", (byte) 12, 2);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24201b = new kg("", (byte) 11, 3);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24202c = new kg("", (byte) 11, 4);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24203d = new kg("", (byte) 11, 5);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24204e = new kg("", (byte) 15, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24205f = new kg("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24206g = new kg("", (byte) 11, 9);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24207h = new kg("", (byte) 2, 10);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24208i = new kg("", (byte) 2, 11);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24209j = new kg("", (byte) 10, 12);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f720a = new BitSet(3);

    /* renamed from: a, reason: collision with other field name */
    public boolean f722a = false;

    /* renamed from: b, reason: collision with other field name */
    public boolean f724b = true;

    /* renamed from: a, reason: collision with other method in class */
    public boolean m585a() {
        return this.f718a != null;
    }

    public boolean b() {
        return this.f719a != null;
    }

    public boolean c() {
        return this.f723b != null;
    }

    public boolean d() {
        return this.f725c != null;
    }

    public boolean e() {
        return this.f721a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jh)) {
            return m586a((jh) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f726d != null;
    }

    public boolean g() {
        return this.f727e != null;
    }

    public boolean h() {
        return this.f720a.get(0);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f720a.get(1);
    }

    public boolean j() {
        return this.f720a.get(2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCommand(");
        if (m585a()) {
            sb.append("target:");
            jc jcVar = this.f718a;
            if (jcVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jcVar);
            }
            sb.append(", ");
        }
        sb.append("id:");
        String str = this.f719a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("appId:");
        String str2 = this.f723b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("cmdName:");
        String str3 = this.f725c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            List<String> list = this.f721a;
            if (list == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(list);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str4 = this.f726d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str5 = this.f727e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("updateCache:");
            sb.append(this.f722a);
        }
        if (i()) {
            sb.append(", ");
            sb.append("response2Client:");
            sb.append(this.f724b);
        }
        if (j()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f717a);
        }
        sb.append(")");
        return sb.toString();
    }

    public jh a(String str) {
        this.f719a = str;
        return this;
    }

    public jh b(String str) {
        this.f723b = str;
        return this;
    }

    public jh c(String str) {
        this.f725c = str;
        return this;
    }

    public jh d(String str) {
        this.f726d = str;
        return this;
    }

    public jh e(String str) {
        this.f727e = str;
        return this;
    }

    public String a() {
        return this.f725c;
    }

    public void b(boolean z2) {
        this.f720a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f720a.set(2, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m584a(String str) {
        if (this.f721a == null) {
            this.f721a = new ArrayList();
        }
        this.f721a.add(str);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m583a();
        kjVar.a(f716a);
        if (this.f718a != null && m585a()) {
            kjVar.a(f24200a);
            this.f718a.b(kjVar);
            kjVar.b();
        }
        if (this.f719a != null) {
            kjVar.a(f24201b);
            kjVar.a(this.f719a);
            kjVar.b();
        }
        if (this.f723b != null) {
            kjVar.a(f24202c);
            kjVar.a(this.f723b);
            kjVar.b();
        }
        if (this.f725c != null) {
            kjVar.a(f24203d);
            kjVar.a(this.f725c);
            kjVar.b();
        }
        if (this.f721a != null && e()) {
            kjVar.a(f24204e);
            kjVar.a(new kh((byte) 11, this.f721a.size()));
            Iterator<String> it = this.f721a.iterator();
            while (it.hasNext()) {
                kjVar.a(it.next());
            }
            kjVar.e();
            kjVar.b();
        }
        if (this.f726d != null && f()) {
            kjVar.a(f24205f);
            kjVar.a(this.f726d);
            kjVar.b();
        }
        if (this.f727e != null && g()) {
            kjVar.a(f24206g);
            kjVar.a(this.f727e);
            kjVar.b();
        }
        if (h()) {
            kjVar.a(f24207h);
            kjVar.a(this.f722a);
            kjVar.b();
        }
        if (i()) {
            kjVar.a(f24208i);
            kjVar.a(this.f724b);
            kjVar.b();
        }
        if (j()) {
            kjVar.a(f24209j);
            kjVar.a(this.f717a);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public void a(boolean z2) {
        this.f720a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m586a(jh jhVar) {
        if (jhVar == null) {
            return false;
        }
        boolean zM585a = m585a();
        boolean zM585a2 = jhVar.m585a();
        if ((zM585a || zM585a2) && !(zM585a && zM585a2 && this.f718a.m571a(jhVar.f718a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jhVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f719a.equals(jhVar.f719a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jhVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f723b.equals(jhVar.f723b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jhVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f725c.equals(jhVar.f725c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jhVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f721a.equals(jhVar.f721a))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jhVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f726d.equals(jhVar.f726d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jhVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f727e.equals(jhVar.f727e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jhVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f722a == jhVar.f722a)) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jhVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f724b == jhVar.f724b)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jhVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f717a == jhVar.f717a;
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jh jhVar) {
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
        if (!getClass().equals(jhVar.getClass())) {
            return getClass().getName().compareTo(jhVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m585a()).compareTo(Boolean.valueOf(jhVar.m585a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m585a() && (iA10 = jz.a(this.f718a, jhVar.f718a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jhVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA9 = jz.a(this.f719a, jhVar.f719a)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jhVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = jz.a(this.f723b, jhVar.f723b)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jhVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jz.a(this.f725c, jhVar.f725c)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jhVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jz.a(this.f721a, jhVar.f721a)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jhVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jz.a(this.f726d, jhVar.f726d)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jhVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jz.a(this.f727e, jhVar.f727e)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jhVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jz.a(this.f722a, jhVar.f722a)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jhVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jz.a(this.f724b, jhVar.f724b)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jhVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jz.a(this.f717a, jhVar.f717a)) == 0) {
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
                m583a();
                return;
            }
            switch (kgVarMo670a.f922a) {
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f718a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f719a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f723b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f725c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 15) {
                        kh khVarMo671a = kjVar.mo671a();
                        this.f721a = new ArrayList(khVarMo671a.f923a);
                        for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                            this.f721a.add(kjVar.mo675a());
                        }
                        kjVar.i();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f726d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                default:
                    km.a(kjVar, b2);
                    break;
                case 9:
                    if (b2 == 11) {
                        this.f727e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 2) {
                        this.f722a = kjVar.mo680a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 2) {
                        this.f724b = kjVar.mo680a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 12:
                    if (b2 == 10) {
                        this.f717a = kjVar.mo669a();
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
    public void m583a() throws kk {
        if (this.f719a != null) {
            if (this.f723b != null) {
                if (this.f725c != null) {
                    return;
                }
                throw new kk("Required field 'cmdName' was not present! Struct: " + toString());
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
