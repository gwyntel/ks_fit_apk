package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class jc implements jy<jc, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public String f670a;

    /* renamed from: d, reason: collision with other field name */
    public String f675d;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f668a = new ko("Target");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24161a = new kg("", (byte) 10, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24162b = new kg("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24163c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24164d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24165e = new kg("", (byte) 2, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24166f = new kg("", (byte) 11, 7);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f671a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    public long f669a = 5;

    /* renamed from: b, reason: collision with other field name */
    public String f673b = "xiaomi.com";

    /* renamed from: c, reason: collision with other field name */
    public String f674c = "";

    /* renamed from: a, reason: collision with other field name */
    public boolean f672a = false;

    /* renamed from: a, reason: collision with other method in class */
    public boolean m570a() {
        return this.f671a.get(0);
    }

    public boolean b() {
        return this.f670a != null;
    }

    public boolean c() {
        return this.f673b != null;
    }

    public boolean d() {
        return this.f674c != null;
    }

    public boolean e() {
        return this.f671a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jc)) {
            return m571a((jc) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f675d != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Target(");
        sb.append("channelId:");
        sb.append(this.f669a);
        sb.append(", ");
        sb.append("userId:");
        String str = this.f670a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        if (c()) {
            sb.append(", ");
            sb.append("server:");
            String str2 = this.f673b;
            if (str2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str2);
            }
        }
        if (d()) {
            sb.append(", ");
            sb.append("resource:");
            String str3 = this.f674c;
            if (str3 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("isPreview:");
            sb.append(this.f672a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("token:");
            String str4 = this.f675d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public void a(boolean z2) {
        this.f671a.set(0, z2);
    }

    public void b(boolean z2) {
        this.f671a.set(1, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m571a(jc jcVar) {
        if (jcVar == null || this.f669a != jcVar.f669a) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jcVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f670a.equals(jcVar.f670a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jcVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f673b.equals(jcVar.f673b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jcVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f674c.equals(jcVar.f674c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jcVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f672a == jcVar.f672a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jcVar.f();
        if (zF || zF2) {
            return zF && zF2 && this.f675d.equals(jcVar.f675d);
        }
        return true;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) {
        a();
        kjVar.a(f668a);
        kjVar.a(f24161a);
        kjVar.a(this.f669a);
        kjVar.b();
        if (this.f670a != null) {
            kjVar.a(f24162b);
            kjVar.a(this.f670a);
            kjVar.b();
        }
        if (this.f673b != null && c()) {
            kjVar.a(f24163c);
            kjVar.a(this.f673b);
            kjVar.b();
        }
        if (this.f674c != null && d()) {
            kjVar.a(f24164d);
            kjVar.a(this.f674c);
            kjVar.b();
        }
        if (e()) {
            kjVar.a(f24165e);
            kjVar.a(this.f672a);
            kjVar.b();
        }
        if (this.f675d != null && f()) {
            kjVar.a(f24166f);
            kjVar.a(this.f675d);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jc jcVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        if (!getClass().equals(jcVar.getClass())) {
            return getClass().getName().compareTo(jcVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m570a()).compareTo(Boolean.valueOf(jcVar.m570a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m570a() && (iA6 = jz.a(this.f669a, jcVar.f669a)) != 0) {
            return iA6;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jcVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA5 = jz.a(this.f670a, jcVar.f670a)) != 0) {
            return iA5;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jcVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA4 = jz.a(this.f673b, jcVar.f673b)) != 0) {
            return iA4;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jcVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA3 = jz.a(this.f674c, jcVar.f674c)) != 0) {
            return iA3;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jcVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA2 = jz.a(this.f672a, jcVar.f672a)) != 0) {
            return iA2;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jcVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (!f() || (iA = jz.a(this.f675d, jcVar.f675d)) == 0) {
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
                break;
            }
            short s2 = kgVarMo670a.f922a;
            if (s2 != 1) {
                if (s2 != 2) {
                    if (s2 != 3) {
                        if (s2 != 4) {
                            if (s2 != 5) {
                                if (s2 != 7) {
                                    km.a(kjVar, b2);
                                } else if (b2 == 11) {
                                    this.f675d = kjVar.mo675a();
                                } else {
                                    km.a(kjVar, b2);
                                }
                            } else if (b2 == 2) {
                                this.f672a = kjVar.mo680a();
                                b(true);
                            } else {
                                km.a(kjVar, b2);
                            }
                        } else if (b2 == 11) {
                            this.f674c = kjVar.mo675a();
                        } else {
                            km.a(kjVar, b2);
                        }
                    } else if (b2 == 11) {
                        this.f673b = kjVar.mo675a();
                    } else {
                        km.a(kjVar, b2);
                    }
                } else if (b2 == 11) {
                    this.f670a = kjVar.mo675a();
                } else {
                    km.a(kjVar, b2);
                }
            } else if (b2 == 10) {
                this.f669a = kjVar.mo669a();
                a(true);
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
        kjVar.f();
        if (m570a()) {
            a();
            return;
        }
        throw new kk("Required field 'channelId' was not found in serialized data! Struct: " + toString());
    }

    public void a() throws kk {
        if (this.f670a != null) {
            return;
        }
        throw new kk("Required field 'userId' was not present! Struct: " + toString());
    }
}
