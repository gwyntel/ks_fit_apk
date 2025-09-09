package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class jf implements jy<jf, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f711a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f712a = new BitSet(2);

    /* renamed from: b, reason: collision with other field name */
    public int f713b;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f710a = new ko("XmPushActionCheckClientInfo");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24197a = new kg("", (byte) 8, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24198b = new kg("", (byte) 8, 2);

    public void a() {
    }

    public jf b(int i2) {
        this.f713b = i2;
        b(true);
        return this;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jf)) {
            return m580a((jf) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "XmPushActionCheckClientInfo(miscConfigVersion:" + this.f711a + ", pluginConfigVersion:" + this.f713b + ")";
    }

    public jf a(int i2) {
        this.f711a = i2;
        a(true);
        return this;
    }

    public boolean b() {
        return this.f712a.get(1);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m579a() {
        return this.f712a.get(0);
    }

    public void b(boolean z2) {
        this.f712a.set(1, z2);
    }

    public void a(boolean z2) {
        this.f712a.set(0, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) {
        a();
        kjVar.a(f710a);
        kjVar.a(f24197a);
        kjVar.mo679a(this.f711a);
        kjVar.b();
        kjVar.a(f24198b);
        kjVar.mo679a(this.f713b);
        kjVar.b();
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m580a(jf jfVar) {
        return jfVar != null && this.f711a == jfVar.f711a && this.f713b == jfVar.f713b;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jf jfVar) {
        int iA;
        int iA2;
        if (!getClass().equals(jfVar.getClass())) {
            return getClass().getName().compareTo(jfVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m579a()).compareTo(Boolean.valueOf(jfVar.m579a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m579a() && (iA2 = jz.a(this.f711a, jfVar.f711a)) != 0) {
            return iA2;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jfVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (!b() || (iA = jz.a(this.f713b, jfVar.f713b)) == 0) {
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
                    km.a(kjVar, b2);
                } else if (b2 == 8) {
                    this.f713b = kjVar.mo668a();
                    b(true);
                } else {
                    km.a(kjVar, b2);
                }
            } else if (b2 == 8) {
                this.f711a = kjVar.mo668a();
                a(true);
            } else {
                km.a(kjVar, b2);
            }
            kjVar.g();
        }
        kjVar.f();
        if (m579a()) {
            if (b()) {
                a();
                return;
            }
            throw new kk("Required field 'pluginConfigVersion' was not found in serialized data! Struct: " + toString());
        }
        throw new kk("Required field 'miscConfigVersion' was not found in serialized data! Struct: " + toString());
    }
}
