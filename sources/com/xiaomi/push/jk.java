package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class jk implements jy<jk, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public List<iy> f751a;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f750a = new ko("XmPushActionCustomConfig");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24228a = new kg("", (byte) 15, 1);

    public List<iy> a() {
        return this.f751a;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m602a();
        kjVar.a(f750a);
        if (this.f751a != null) {
            kjVar.a(f24228a);
            kjVar.a(new kh((byte) 12, this.f751a.size()));
            Iterator<iy> it = this.f751a.iterator();
            while (it.hasNext()) {
                it.next().b(kjVar);
            }
            kjVar.e();
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jk)) {
            return m604a((jk) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCustomConfig(");
        sb.append("customConfigs:");
        List<iy> list = this.f751a;
        if (list == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m603a() {
        return this.f751a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m604a(jk jkVar) {
        if (jkVar == null) {
            return false;
        }
        boolean zM603a = m603a();
        boolean zM603a2 = jkVar.m603a();
        if (zM603a || zM603a2) {
            return zM603a && zM603a2 && this.f751a.equals(jkVar.f751a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jk jkVar) {
        int iA;
        if (!getClass().equals(jkVar.getClass())) {
            return getClass().getName().compareTo(jkVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m603a()).compareTo(Boolean.valueOf(jkVar.m603a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m603a() || (iA = jz.a(this.f751a, jkVar.f751a)) == 0) {
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
                m602a();
                return;
            }
            if (kgVarMo670a.f922a != 1) {
                km.a(kjVar, b2);
            } else if (b2 == 15) {
                kh khVarMo671a = kjVar.mo671a();
                this.f751a = new ArrayList(khVarMo671a.f923a);
                for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                    iy iyVar = new iy();
                    iyVar.a(kjVar);
                    this.f751a.add(iyVar);
                }
                kjVar.i();
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m602a() throws kk {
        if (this.f751a != null) {
            return;
        }
        throw new kk("Required field 'customConfigs' was not present! Struct: " + toString());
    }
}
