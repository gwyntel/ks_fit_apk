package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class gg implements jy<gg, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public String f463a;

    /* renamed from: a, reason: collision with other field name */
    public List<gf> f464a;

    /* renamed from: b, reason: collision with other field name */
    public String f465b;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f462a = new ko("StatsEvents");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f23825a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f23826b = new kg("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f23827c = new kg("", (byte) 15, 3);

    public gg() {
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m431a() {
        return this.f463a != null;
    }

    public boolean b() {
        return this.f465b != null;
    }

    public boolean c() {
        return this.f464a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof gg)) {
            return m432a((gg) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvents(");
        sb.append("uuid:");
        String str = this.f463a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        if (b()) {
            sb.append(", ");
            sb.append("operator:");
            String str2 = this.f465b;
            if (str2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str2);
            }
        }
        sb.append(", ");
        sb.append("events:");
        List<gf> list = this.f464a;
        if (list == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }

    public gg(String str, List<gf> list) {
        this();
        this.f463a = str;
        this.f464a = list;
    }

    public gg a(String str) {
        this.f465b = str;
        return this;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) {
        a();
        kjVar.a(f462a);
        if (this.f463a != null) {
            kjVar.a(f23825a);
            kjVar.a(this.f463a);
            kjVar.b();
        }
        if (this.f465b != null && b()) {
            kjVar.a(f23826b);
            kjVar.a(this.f465b);
            kjVar.b();
        }
        if (this.f464a != null) {
            kjVar.a(f23827c);
            kjVar.a(new kh((byte) 12, this.f464a.size()));
            Iterator<gf> it = this.f464a.iterator();
            while (it.hasNext()) {
                it.next().b(kjVar);
            }
            kjVar.e();
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m432a(gg ggVar) {
        if (ggVar == null) {
            return false;
        }
        boolean zM431a = m431a();
        boolean zM431a2 = ggVar.m431a();
        if ((zM431a || zM431a2) && !(zM431a && zM431a2 && this.f463a.equals(ggVar.f463a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = ggVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f465b.equals(ggVar.f465b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = ggVar.c();
        if (zC || zC2) {
            return zC && zC2 && this.f464a.equals(ggVar.f464a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(gg ggVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(ggVar.getClass())) {
            return getClass().getName().compareTo(ggVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m431a()).compareTo(Boolean.valueOf(ggVar.m431a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m431a() && (iA3 = jz.a(this.f463a, ggVar.f463a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ggVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = jz.a(this.f465b, ggVar.f465b)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ggVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = jz.a(this.f464a, ggVar.f464a)) == 0) {
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
            short s2 = kgVarMo670a.f922a;
            if (s2 != 1) {
                if (s2 != 2) {
                    if (s2 != 3) {
                        km.a(kjVar, b2);
                    } else if (b2 == 15) {
                        kh khVarMo671a = kjVar.mo671a();
                        this.f464a = new ArrayList(khVarMo671a.f923a);
                        for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                            gf gfVar = new gf();
                            gfVar.a(kjVar);
                            this.f464a.add(gfVar);
                        }
                        kjVar.i();
                    } else {
                        km.a(kjVar, b2);
                    }
                } else if (b2 == 11) {
                    this.f465b = kjVar.mo675a();
                } else {
                    km.a(kjVar, b2);
                }
            } else if (b2 == 11) {
                this.f463a = kjVar.mo675a();
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
    }

    public void a() throws kk {
        if (this.f463a != null) {
            if (this.f464a != null) {
                return;
            }
            throw new kk("Required field 'events' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'uuid' was not present! Struct: " + toString());
    }
}
