package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class je implements jy<je, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public jc f700a;

    /* renamed from: a, reason: collision with other field name */
    public String f701a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f703a;

    /* renamed from: b, reason: collision with other field name */
    public String f704b;

    /* renamed from: c, reason: collision with other field name */
    public String f705c;

    /* renamed from: d, reason: collision with other field name */
    public String f706d;

    /* renamed from: e, reason: collision with other field name */
    public String f707e;

    /* renamed from: f, reason: collision with other field name */
    public String f708f;

    /* renamed from: g, reason: collision with other field name */
    public String f709g;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f698a = new ko("XmPushActionAckNotification");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24187a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24188b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24189c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24190d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24191e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24192f = new kg("", (byte) 10, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24193g = new kg("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24194h = new kg("", (byte) 13, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24195i = new kg("", (byte) 11, 10);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24196j = new kg("", (byte) 11, 11);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f702a = new BitSet(1);

    /* renamed from: a, reason: collision with other field name */
    public long f699a = 0;

    /* renamed from: a, reason: collision with other method in class */
    public boolean m576a() {
        return this.f701a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m578b() {
        return this.f700a != null;
    }

    public boolean c() {
        return this.f704b != null;
    }

    public boolean d() {
        return this.f705c != null;
    }

    public boolean e() {
        return this.f706d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof je)) {
            return m577a((je) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f702a.get(0);
    }

    public boolean g() {
        return this.f707e != null;
    }

    public boolean h() {
        return this.f703a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f708f != null;
    }

    public boolean j() {
        return this.f709g != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionAckNotification(");
        boolean z3 = false;
        if (m576a()) {
            sb.append("debug:");
            String str = this.f701a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m578b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            jc jcVar = this.f700a;
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
        String str2 = this.f704b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.f705c;
            if (str3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("type:");
            String str4 = this.f706d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.f699a);
        }
        if (g()) {
            sb.append(", ");
            sb.append("reason:");
            String str5 = this.f707e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f703a;
            if (map == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(map);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f708f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f709g;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public je a(jc jcVar) {
        this.f700a = jcVar;
        return this;
    }

    public je b(String str) {
        this.f705c = str;
        return this;
    }

    public je c(String str) {
        this.f706d = str;
        return this;
    }

    public je d(String str) {
        this.f707e = str;
        return this;
    }

    public je e(String str) {
        this.f708f = str;
        return this;
    }

    public String a() {
        return this.f704b;
    }

    public String b() {
        return this.f706d;
    }

    public je a(String str) {
        this.f704b = str;
        return this;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m575a();
        kjVar.a(f698a);
        if (this.f701a != null && m576a()) {
            kjVar.a(f24187a);
            kjVar.a(this.f701a);
            kjVar.b();
        }
        if (this.f700a != null && m578b()) {
            kjVar.a(f24188b);
            this.f700a.b(kjVar);
            kjVar.b();
        }
        if (this.f704b != null) {
            kjVar.a(f24189c);
            kjVar.a(this.f704b);
            kjVar.b();
        }
        if (this.f705c != null && d()) {
            kjVar.a(f24190d);
            kjVar.a(this.f705c);
            kjVar.b();
        }
        if (this.f706d != null && e()) {
            kjVar.a(f24191e);
            kjVar.a(this.f706d);
            kjVar.b();
        }
        if (f()) {
            kjVar.a(f24192f);
            kjVar.a(this.f699a);
            kjVar.b();
        }
        if (this.f707e != null && g()) {
            kjVar.a(f24193g);
            kjVar.a(this.f707e);
            kjVar.b();
        }
        if (this.f703a != null && h()) {
            kjVar.a(f24194h);
            kjVar.a(new ki((byte) 11, (byte) 11, this.f703a.size()));
            for (Map.Entry<String, String> entry : this.f703a.entrySet()) {
                kjVar.a(entry.getKey());
                kjVar.a(entry.getValue());
            }
            kjVar.d();
            kjVar.b();
        }
        if (this.f708f != null && i()) {
            kjVar.a(f24195i);
            kjVar.a(this.f708f);
            kjVar.b();
        }
        if (this.f709g != null && j()) {
            kjVar.a(f24196j);
            kjVar.a(this.f709g);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public je a(long j2) {
        this.f699a = j2;
        a(true);
        return this;
    }

    public void a(boolean z2) {
        this.f702a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public Map<String, String> m574a() {
        return this.f703a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m577a(je jeVar) {
        if (jeVar == null) {
            return false;
        }
        boolean zM576a = m576a();
        boolean zM576a2 = jeVar.m576a();
        if ((zM576a || zM576a2) && !(zM576a && zM576a2 && this.f701a.equals(jeVar.f701a))) {
            return false;
        }
        boolean zM578b = m578b();
        boolean zM578b2 = jeVar.m578b();
        if ((zM578b || zM578b2) && !(zM578b && zM578b2 && this.f700a.m571a(jeVar.f700a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jeVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f704b.equals(jeVar.f704b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jeVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f705c.equals(jeVar.f705c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jeVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f706d.equals(jeVar.f706d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jeVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f699a == jeVar.f699a)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jeVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f707e.equals(jeVar.f707e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jeVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f703a.equals(jeVar.f703a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jeVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f708f.equals(jeVar.f708f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jeVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f709g.equals(jeVar.f709g);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(je jeVar) {
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
        if (!getClass().equals(jeVar.getClass())) {
            return getClass().getName().compareTo(jeVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m576a()).compareTo(Boolean.valueOf(jeVar.m576a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m576a() && (iA10 = jz.a(this.f701a, jeVar.f701a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(m578b()).compareTo(Boolean.valueOf(jeVar.m578b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m578b() && (iA9 = jz.a(this.f700a, jeVar.f700a)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jeVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = jz.a(this.f704b, jeVar.f704b)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jeVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jz.a(this.f705c, jeVar.f705c)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jeVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jz.a(this.f706d, jeVar.f706d)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jeVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jz.a(this.f699a, jeVar.f699a)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jeVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jz.a(this.f707e, jeVar.f707e)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jeVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jz.a(this.f703a, jeVar.f703a)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jeVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jz.a(this.f708f, jeVar.f708f)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jeVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jz.a(this.f709g, jeVar.f709g)) == 0) {
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
                m575a();
                return;
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f701a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f700a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f704b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f705c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f706d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                default:
                    km.a(kjVar, b2);
                    break;
                case 7:
                    if (b2 == 10) {
                        this.f699a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f707e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 13) {
                        ki kiVarMo672a = kjVar.mo672a();
                        this.f703a = new HashMap(kiVarMo672a.f924a * 2);
                        for (int i2 = 0; i2 < kiVarMo672a.f924a; i2++) {
                            this.f703a.put(kjVar.mo675a(), kjVar.mo675a());
                        }
                        kjVar.h();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 11) {
                        this.f708f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 11) {
                        this.f709g = kjVar.mo675a();
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
    public void m575a() throws kk {
        if (this.f704b != null) {
            return;
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
