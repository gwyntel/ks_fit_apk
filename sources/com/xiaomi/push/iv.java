package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class iv implements jy<iv, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f610a;

    /* renamed from: a, reason: collision with other field name */
    public ip f611a;

    /* renamed from: a, reason: collision with other field name */
    public String f612a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f613a = new BitSet(1);

    /* renamed from: a, reason: collision with other field name */
    private static final ko f609a = new ko("DataCollectionItem");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24086a = new kg("", (byte) 10, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24087b = new kg("", (byte) 8, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24088c = new kg("", (byte) 11, 3);

    public iv a(long j2) {
        this.f610a = j2;
        a(true);
        return this;
    }

    public boolean b() {
        return this.f611a != null;
    }

    public boolean c() {
        return this.f612a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iv)) {
            return m528a((iv) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DataCollectionItem(");
        sb.append("collectedAt:");
        sb.append(this.f610a);
        sb.append(", ");
        sb.append("collectionType:");
        ip ipVar = this.f611a;
        if (ipVar == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(ipVar);
        }
        sb.append(", ");
        sb.append("content:");
        String str = this.f612a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m526a();
        kjVar.a(f609a);
        kjVar.a(f24086a);
        kjVar.a(this.f610a);
        kjVar.b();
        if (this.f611a != null) {
            kjVar.a(f24087b);
            kjVar.mo679a(this.f611a.a());
            kjVar.b();
        }
        if (this.f612a != null) {
            kjVar.a(f24088c);
            kjVar.a(this.f612a);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m527a() {
        return this.f613a.get(0);
    }

    public void a(boolean z2) {
        this.f613a.set(0, z2);
    }

    public iv a(ip ipVar) {
        this.f611a = ipVar;
        return this;
    }

    public String a() {
        return this.f612a;
    }

    public iv a(String str) {
        this.f612a = str;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m528a(iv ivVar) {
        if (ivVar == null || this.f610a != ivVar.f610a) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = ivVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f611a.equals(ivVar.f611a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = ivVar.c();
        if (zC || zC2) {
            return zC && zC2 && this.f612a.equals(ivVar.f612a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iv ivVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(ivVar.getClass())) {
            return getClass().getName().compareTo(ivVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m527a()).compareTo(Boolean.valueOf(ivVar.m527a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m527a() && (iA3 = jz.a(this.f610a, ivVar.f610a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ivVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = jz.a(this.f611a, ivVar.f611a)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ivVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = jz.a(this.f612a, ivVar.f612a)) == 0) {
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
                    } else if (b2 == 11) {
                        this.f612a = kjVar.mo675a();
                    } else {
                        km.a(kjVar, b2);
                    }
                } else if (b2 == 8) {
                    this.f611a = ip.a(kjVar.mo668a());
                } else {
                    km.a(kjVar, b2);
                }
            } else if (b2 == 10) {
                this.f610a = kjVar.mo669a();
                a(true);
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
        kjVar.f();
        if (m527a()) {
            m526a();
            return;
        }
        throw new kk("Required field 'collectedAt' was not found in serialized data! Struct: " + toString());
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m526a() throws kk {
        if (this.f611a != null) {
            if (this.f612a != null) {
                return;
            }
            throw new kk("Required field 'content' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'collectionType' was not present! Struct: " + toString());
    }
}
