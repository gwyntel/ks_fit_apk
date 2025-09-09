package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class jl implements jy<jl, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public List<iw> f753a;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f752a = new ko("XmPushActionNormalConfig");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24229a = new kg("", (byte) 15, 1);

    public List<iw> a() {
        return this.f753a;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m605a();
        kjVar.a(f752a);
        if (this.f753a != null) {
            kjVar.a(f24229a);
            kjVar.a(new kh((byte) 12, this.f753a.size()));
            Iterator<iw> it = this.f753a.iterator();
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
        if (obj != null && (obj instanceof jl)) {
            return m607a((jl) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionNormalConfig(");
        sb.append("normalConfigs:");
        List<iw> list = this.f753a;
        if (list == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m606a() {
        return this.f753a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m607a(jl jlVar) {
        if (jlVar == null) {
            return false;
        }
        boolean zM606a = m606a();
        boolean zM606a2 = jlVar.m606a();
        if (zM606a || zM606a2) {
            return zM606a && zM606a2 && this.f753a.equals(jlVar.f753a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jl jlVar) {
        int iA;
        if (!getClass().equals(jlVar.getClass())) {
            return getClass().getName().compareTo(jlVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m606a()).compareTo(Boolean.valueOf(jlVar.m606a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m606a() || (iA = jz.a(this.f753a, jlVar.f753a)) == 0) {
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
                m605a();
                return;
            }
            if (kgVarMo670a.f922a != 1) {
                km.a(kjVar, b2);
            } else if (b2 == 15) {
                kh khVarMo671a = kjVar.mo671a();
                this.f753a = new ArrayList(khVarMo671a.f923a);
                for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                    iw iwVar = new iw();
                    iwVar.a(kjVar);
                    this.f753a.add(iwVar);
                }
                kjVar.i();
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m605a() throws kk {
        if (this.f753a != null) {
            return;
        }
        throw new kk("Required field 'normalConfigs' was not present! Struct: " + toString());
    }
}
