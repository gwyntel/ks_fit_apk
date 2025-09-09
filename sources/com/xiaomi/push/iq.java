package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class iq implements jy<iq, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public List<ir> f589a;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f588a = new ko("ClientUploadData");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24042a = new kg("", (byte) 15, 1);

    public int a() {
        List<ir> list = this.f589a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m513a();
        kjVar.a(f588a);
        if (this.f589a != null) {
            kjVar.a(f24042a);
            kjVar.a(new kh((byte) 12, this.f589a.size()));
            Iterator<ir> it = this.f589a.iterator();
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
        if (obj != null && (obj instanceof iq)) {
            return m515a((iq) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ClientUploadData(");
        sb.append("uploadDataItems:");
        List<ir> list = this.f589a;
        if (list == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }

    public void a(ir irVar) {
        if (this.f589a == null) {
            this.f589a = new ArrayList();
        }
        this.f589a.add(irVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m514a() {
        return this.f589a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m515a(iq iqVar) {
        if (iqVar == null) {
            return false;
        }
        boolean zM514a = m514a();
        boolean zM514a2 = iqVar.m514a();
        if (zM514a || zM514a2) {
            return zM514a && zM514a2 && this.f589a.equals(iqVar.f589a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iq iqVar) {
        int iA;
        if (!getClass().equals(iqVar.getClass())) {
            return getClass().getName().compareTo(iqVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m514a()).compareTo(Boolean.valueOf(iqVar.m514a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m514a() || (iA = jz.a(this.f589a, iqVar.f589a)) == 0) {
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
                m513a();
                return;
            }
            if (kgVarMo670a.f922a != 1) {
                km.a(kjVar, b2);
            } else if (b2 == 15) {
                kh khVarMo671a = kjVar.mo671a();
                this.f589a = new ArrayList(khVarMo671a.f923a);
                for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                    ir irVar = new ir();
                    irVar.a(kjVar);
                    this.f589a.add(irVar);
                }
                kjVar.i();
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m513a() throws kk {
        if (this.f589a != null) {
            return;
        }
        throw new kk("Required field 'uploadDataItems' was not present! Struct: " + toString());
    }
}
