package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class jp implements jy<jp, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f825a;

    /* renamed from: a, reason: collision with other field name */
    public jc f826a;

    /* renamed from: a, reason: collision with other field name */
    public String f827a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f828a = new BitSet(1);

    /* renamed from: b, reason: collision with other field name */
    public String f829b;

    /* renamed from: c, reason: collision with other field name */
    public String f830c;

    /* renamed from: d, reason: collision with other field name */
    public String f831d;

    /* renamed from: e, reason: collision with other field name */
    public String f832e;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f824a = new ko("XmPushActionSendFeedbackResult");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24291a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24292b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24293c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24294d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24295e = new kg("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24296f = new kg("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24297g = new kg("", (byte) 11, 8);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m629a() {
        return this.f827a != null;
    }

    public boolean b() {
        return this.f826a != null;
    }

    public boolean c() {
        return this.f829b != null;
    }

    public boolean d() {
        return this.f830c != null;
    }

    public boolean e() {
        return this.f828a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jp)) {
            return m630a((jp) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f831d != null;
    }

    public boolean g() {
        return this.f832e != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSendFeedbackResult(");
        boolean z3 = false;
        if (m629a()) {
            sb.append("debug:");
            String str = this.f827a;
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
            jc jcVar = this.f826a;
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
        String str2 = this.f829b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f830c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f825a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f831d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str5 = this.f832e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public void a(boolean z2) {
        this.f828a.set(0, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        a();
        kjVar.a(f824a);
        if (this.f827a != null && m629a()) {
            kjVar.a(f24291a);
            kjVar.a(this.f827a);
            kjVar.b();
        }
        if (this.f826a != null && b()) {
            kjVar.a(f24292b);
            this.f826a.b(kjVar);
            kjVar.b();
        }
        if (this.f829b != null) {
            kjVar.a(f24293c);
            kjVar.a(this.f829b);
            kjVar.b();
        }
        if (this.f830c != null) {
            kjVar.a(f24294d);
            kjVar.a(this.f830c);
            kjVar.b();
        }
        kjVar.a(f24295e);
        kjVar.a(this.f825a);
        kjVar.b();
        if (this.f831d != null && f()) {
            kjVar.a(f24296f);
            kjVar.a(this.f831d);
            kjVar.b();
        }
        if (this.f832e != null && g()) {
            kjVar.a(f24297g);
            kjVar.a(this.f832e);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m630a(jp jpVar) {
        if (jpVar == null) {
            return false;
        }
        boolean zM629a = m629a();
        boolean zM629a2 = jpVar.m629a();
        if ((zM629a || zM629a2) && !(zM629a && zM629a2 && this.f827a.equals(jpVar.f827a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jpVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f826a.m571a(jpVar.f826a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jpVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f829b.equals(jpVar.f829b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jpVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f830c.equals(jpVar.f830c))) || this.f825a != jpVar.f825a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jpVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f831d.equals(jpVar.f831d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jpVar.g();
        if (zG || zG2) {
            return zG && zG2 && this.f832e.equals(jpVar.f832e);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jp jpVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        if (!getClass().equals(jpVar.getClass())) {
            return getClass().getName().compareTo(jpVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m629a()).compareTo(Boolean.valueOf(jpVar.m629a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m629a() && (iA7 = jz.a(this.f827a, jpVar.f827a)) != 0) {
            return iA7;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jpVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA6 = jz.a(this.f826a, jpVar.f826a)) != 0) {
            return iA6;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jpVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA5 = jz.a(this.f829b, jpVar.f829b)) != 0) {
            return iA5;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jpVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA4 = jz.a(this.f830c, jpVar.f830c)) != 0) {
            return iA4;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jpVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA3 = jz.a(this.f825a, jpVar.f825a)) != 0) {
            return iA3;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jpVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA2 = jz.a(this.f831d, jpVar.f831d)) != 0) {
            return iA2;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jpVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (!g() || (iA = jz.a(this.f832e, jpVar.f832e)) == 0) {
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
                throw new kk("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f827a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f826a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f829b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f830c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                default:
                    km.a(kjVar, b2);
                    break;
                case 6:
                    if (b2 == 10) {
                        this.f825a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f831d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f832e = kjVar.mo675a();
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
        if (this.f829b != null) {
            if (this.f830c != null) {
                return;
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
