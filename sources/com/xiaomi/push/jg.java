package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class jg implements jy<jg, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public List<iv> f715a;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f714a = new ko("XmPushActionCollectData");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24199a = new kg("", (byte) 15, 1);

    public jg a(List<iv> list) {
        this.f715a = list;
        return this;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        a();
        kjVar.a(f714a);
        if (this.f715a != null) {
            kjVar.a(f24199a);
            kjVar.a(new kh((byte) 12, this.f715a.size()));
            Iterator<iv> it = this.f715a.iterator();
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
        if (obj != null && (obj instanceof jg)) {
            return m582a((jg) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCollectData(");
        sb.append("dataCollectionItems:");
        List<iv> list = this.f715a;
        if (list == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m581a() {
        return this.f715a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m582a(jg jgVar) {
        if (jgVar == null) {
            return false;
        }
        boolean zM581a = m581a();
        boolean zM581a2 = jgVar.m581a();
        if (zM581a || zM581a2) {
            return zM581a && zM581a2 && this.f715a.equals(jgVar.f715a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jg jgVar) {
        int iA;
        if (!getClass().equals(jgVar.getClass())) {
            return getClass().getName().compareTo(jgVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m581a()).compareTo(Boolean.valueOf(jgVar.m581a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m581a() || (iA = jz.a(this.f715a, jgVar.f715a)) == 0) {
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
                a();
                return;
            }
            if (kgVarMo670a.f922a != 1) {
                km.a(kjVar, b2);
            } else if (b2 == 15) {
                kh khVarMo671a = kjVar.mo671a();
                this.f715a = new ArrayList(khVarMo671a.f923a);
                for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                    iv ivVar = new iv();
                    ivVar.a(kjVar);
                    this.f715a.add(ivVar);
                }
                kjVar.i();
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
    }

    public void a() throws kk {
        if (this.f715a != null) {
            return;
        }
        throw new kk("Required field 'dataCollectionItems' was not present! Struct: " + toString());
    }
}
