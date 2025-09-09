package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class iy implements jy<iy, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f622a;

    /* renamed from: a, reason: collision with other field name */
    public long f623a;

    /* renamed from: a, reason: collision with other field name */
    public String f624a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f625a = new BitSet(6);

    /* renamed from: a, reason: collision with other field name */
    public boolean f626a;

    /* renamed from: b, reason: collision with other field name */
    public int f627b;

    /* renamed from: b, reason: collision with other field name */
    public boolean f628b;

    /* renamed from: c, reason: collision with other field name */
    public int f629c;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f621a = new ko("OnlineConfigItem");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24118a = new kg("", (byte) 8, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24119b = new kg("", (byte) 8, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24120c = new kg("", (byte) 2, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24121d = new kg("", (byte) 8, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24122e = new kg("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24123f = new kg("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24124g = new kg("", (byte) 2, 7);

    /* renamed from: a, reason: collision with other method in class */
    public void m535a() {
    }

    public int b() {
        return this.f627b;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m539c() {
        return this.f625a.get(2);
    }

    public boolean d() {
        return this.f625a.get(3);
    }

    public boolean e() {
        return this.f625a.get(4);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iy)) {
            return m537a((iy) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f624a != null;
    }

    public boolean g() {
        return this.f628b;
    }

    public boolean h() {
        return this.f625a.get(5);
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("OnlineConfigItem(");
        boolean z3 = false;
        if (m536a()) {
            sb.append("key:");
            sb.append(this.f622a);
            z2 = false;
        } else {
            z2 = true;
        }
        if (m538b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("type:");
            sb.append(this.f627b);
            z2 = false;
        }
        if (m539c()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("clear:");
            sb.append(this.f626a);
            z2 = false;
        }
        if (d()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("intValue:");
            sb.append(this.f629c);
            z2 = false;
        }
        if (e()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("longValue:");
            sb.append(this.f623a);
            z2 = false;
        }
        if (f()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("stringValue:");
            String str = this.f624a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
        } else {
            z3 = z2;
        }
        if (h()) {
            if (!z3) {
                sb.append(", ");
            }
            sb.append("boolValue:");
            sb.append(this.f628b);
        }
        sb.append(")");
        return sb.toString();
    }

    public int a() {
        return this.f622a;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m538b() {
        return this.f625a.get(1);
    }

    public void c(boolean z2) {
        this.f625a.set(2, z2);
    }

    public void d(boolean z2) {
        this.f625a.set(3, z2);
    }

    public void e(boolean z2) {
        this.f625a.set(4, z2);
    }

    public void f(boolean z2) {
        this.f625a.set(5, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m536a() {
        return this.f625a.get(0);
    }

    public void b(boolean z2) {
        this.f625a.set(1, z2);
    }

    public int c() {
        return this.f629c;
    }

    public void a(boolean z2) {
        this.f625a.set(0, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) {
        m535a();
        kjVar.a(f621a);
        if (m536a()) {
            kjVar.a(f24118a);
            kjVar.mo679a(this.f622a);
            kjVar.b();
        }
        if (m538b()) {
            kjVar.a(f24119b);
            kjVar.mo679a(this.f627b);
            kjVar.b();
        }
        if (m539c()) {
            kjVar.a(f24120c);
            kjVar.a(this.f626a);
            kjVar.b();
        }
        if (d()) {
            kjVar.a(f24121d);
            kjVar.mo679a(this.f629c);
            kjVar.b();
        }
        if (e()) {
            kjVar.a(f24122e);
            kjVar.a(this.f623a);
            kjVar.b();
        }
        if (this.f624a != null && f()) {
            kjVar.a(f24123f);
            kjVar.a(this.f624a);
            kjVar.b();
        }
        if (h()) {
            kjVar.a(f24124g);
            kjVar.a(this.f628b);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m533a() {
        return this.f623a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m534a() {
        return this.f624a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m537a(iy iyVar) {
        if (iyVar == null) {
            return false;
        }
        boolean zM536a = m536a();
        boolean zM536a2 = iyVar.m536a();
        if ((zM536a || zM536a2) && !(zM536a && zM536a2 && this.f622a == iyVar.f622a)) {
            return false;
        }
        boolean zM538b = m538b();
        boolean zM538b2 = iyVar.m538b();
        if ((zM538b || zM538b2) && !(zM538b && zM538b2 && this.f627b == iyVar.f627b)) {
            return false;
        }
        boolean zM539c = m539c();
        boolean zM539c2 = iyVar.m539c();
        if ((zM539c || zM539c2) && !(zM539c && zM539c2 && this.f626a == iyVar.f626a)) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = iyVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f629c == iyVar.f629c)) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = iyVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f623a == iyVar.f623a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = iyVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f624a.equals(iyVar.f624a))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = iyVar.h();
        if (zH || zH2) {
            return zH && zH2 && this.f628b == iyVar.f628b;
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iy iyVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        if (!getClass().equals(iyVar.getClass())) {
            return getClass().getName().compareTo(iyVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m536a()).compareTo(Boolean.valueOf(iyVar.m536a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m536a() && (iA7 = jz.a(this.f622a, iyVar.f622a)) != 0) {
            return iA7;
        }
        int iCompareTo2 = Boolean.valueOf(m538b()).compareTo(Boolean.valueOf(iyVar.m538b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m538b() && (iA6 = jz.a(this.f627b, iyVar.f627b)) != 0) {
            return iA6;
        }
        int iCompareTo3 = Boolean.valueOf(m539c()).compareTo(Boolean.valueOf(iyVar.m539c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m539c() && (iA5 = jz.a(this.f626a, iyVar.f626a)) != 0) {
            return iA5;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(iyVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA4 = jz.a(this.f629c, iyVar.f629c)) != 0) {
            return iA4;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(iyVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA3 = jz.a(this.f623a, iyVar.f623a)) != 0) {
            return iA3;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(iyVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA2 = jz.a(this.f624a, iyVar.f624a)) != 0) {
            return iA2;
        }
        int iCompareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(iyVar.h()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (!h() || (iA = jz.a(this.f628b, iyVar.f628b)) == 0) {
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
                m535a();
                return;
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 8) {
                        this.f622a = kjVar.mo668a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 8) {
                        this.f627b = kjVar.mo668a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 2) {
                        this.f626a = kjVar.mo680a();
                        c(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 8) {
                        this.f629c = kjVar.mo668a();
                        d(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 10) {
                        this.f623a = kjVar.mo669a();
                        e(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 11) {
                        this.f624a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 2) {
                        this.f628b = kjVar.mo680a();
                        f(true);
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
