package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class iw implements jy<iw, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f615a;

    /* renamed from: a, reason: collision with other field name */
    public it f616a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f617a = new BitSet(1);

    /* renamed from: a, reason: collision with other field name */
    public List<iy> f618a;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f614a = new ko("NormalConfig");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24089a = new kg("", (byte) 8, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24090b = new kg("", (byte) 15, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24091c = new kg("", (byte) 8, 3);

    public int a() {
        return this.f615a;
    }

    public boolean b() {
        return this.f618a != null;
    }

    public boolean c() {
        return this.f616a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iw)) {
            return m532a((iw) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NormalConfig(");
        sb.append("version:");
        sb.append(this.f615a);
        sb.append(", ");
        sb.append("configItems:");
        List<iy> list = this.f618a;
        if (list == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(list);
        }
        if (c()) {
            sb.append(", ");
            sb.append("type:");
            it itVar = this.f616a;
            if (itVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(itVar);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m531a() {
        return this.f617a.get(0);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m530a();
        kjVar.a(f614a);
        kjVar.a(f24089a);
        kjVar.mo679a(this.f615a);
        kjVar.b();
        if (this.f618a != null) {
            kjVar.a(f24090b);
            kjVar.a(new kh((byte) 12, this.f618a.size()));
            Iterator<iy> it = this.f618a.iterator();
            while (it.hasNext()) {
                it.next().b(kjVar);
            }
            kjVar.e();
            kjVar.b();
        }
        if (this.f616a != null && c()) {
            kjVar.a(f24091c);
            kjVar.mo679a(this.f616a.a());
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public void a(boolean z2) {
        this.f617a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public it m529a() {
        return this.f616a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m532a(iw iwVar) {
        if (iwVar == null || this.f615a != iwVar.f615a) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = iwVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f618a.equals(iwVar.f618a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = iwVar.c();
        if (zC || zC2) {
            return zC && zC2 && this.f616a.equals(iwVar.f616a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iw iwVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(iwVar.getClass())) {
            return getClass().getName().compareTo(iwVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m531a()).compareTo(Boolean.valueOf(iwVar.m531a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m531a() && (iA3 = jz.a(this.f615a, iwVar.f615a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(iwVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = jz.a(this.f618a, iwVar.f618a)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(iwVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = jz.a(this.f616a, iwVar.f616a)) == 0) {
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
                break;
            }
            short s2 = kgVarMo670a.f922a;
            if (s2 != 1) {
                if (s2 != 2) {
                    if (s2 != 3) {
                        km.a(kjVar, b2);
                    } else if (b2 == 8) {
                        this.f616a = it.a(kjVar.mo668a());
                    } else {
                        km.a(kjVar, b2);
                    }
                } else if (b2 == 15) {
                    kh khVarMo671a = kjVar.mo671a();
                    this.f618a = new ArrayList(khVarMo671a.f923a);
                    for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                        iy iyVar = new iy();
                        iyVar.a(kjVar);
                        this.f618a.add(iyVar);
                    }
                    kjVar.i();
                } else {
                    km.a(kjVar, b2);
                }
            } else if (b2 == 8) {
                this.f615a = kjVar.mo668a();
                a(true);
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
        kjVar.f();
        if (m531a()) {
            m530a();
            return;
        }
        throw new kk("Required field 'version' was not found in serialized data! Struct: " + toString());
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m530a() throws kk {
        if (this.f618a != null) {
            return;
        }
        throw new kk("Required field 'configItems' was not present! Struct: " + toString());
    }
}
