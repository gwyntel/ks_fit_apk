package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class jj implements jy<jj, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public in f741a;

    /* renamed from: a, reason: collision with other field name */
    public ja f742a;

    /* renamed from: a, reason: collision with other field name */
    public jc f743a;

    /* renamed from: a, reason: collision with other field name */
    public String f744a;

    /* renamed from: a, reason: collision with other field name */
    public ByteBuffer f745a;

    /* renamed from: b, reason: collision with other field name */
    public String f748b;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f740a = new ko("XmPushActionContainer");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24220a = new kg("", (byte) 8, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24221b = new kg("", (byte) 2, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24222c = new kg("", (byte) 2, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24223d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24224e = new kg("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24225f = new kg("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24226g = new kg("", (byte) 12, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24227h = new kg("", (byte) 12, 8);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f746a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    public boolean f747a = true;

    /* renamed from: b, reason: collision with other field name */
    public boolean f749b = true;

    public in a() {
        return this.f741a;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m601b() {
        return this.f747a;
    }

    public boolean c() {
        return this.f746a.get(0);
    }

    public boolean d() {
        return this.f746a.get(1);
    }

    public boolean e() {
        return this.f745a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jj)) {
            return m598a((jj) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f744a != null;
    }

    public boolean g() {
        return this.f748b != null;
    }

    public boolean h() {
        return this.f743a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f742a != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionContainer(");
        sb.append("action:");
        in inVar = this.f741a;
        if (inVar == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(inVar);
        }
        sb.append(", ");
        sb.append("encryptAction:");
        sb.append(this.f747a);
        sb.append(", ");
        sb.append("isRequest:");
        sb.append(this.f749b);
        if (f()) {
            sb.append(", ");
            sb.append("appid:");
            String str = this.f744a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str2 = this.f748b;
            if (str2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str2);
            }
        }
        sb.append(", ");
        sb.append("target:");
        jc jcVar = this.f743a;
        if (jcVar == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(jcVar);
        }
        if (i()) {
            sb.append(", ");
            sb.append("metaInfo:");
            ja jaVar = this.f742a;
            if (jaVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jaVar);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public jj a(in inVar) {
        this.f741a = inVar;
        return this;
    }

    public jj b(boolean z2) {
        this.f749b = z2;
        m600b(true);
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m597a() {
        return this.f741a != null;
    }

    public jj a(boolean z2) {
        this.f747a = z2;
        m596a(true);
        return this;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m600b(boolean z2) {
        this.f746a.set(1, z2);
    }

    public String b() {
        return this.f748b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m596a(boolean z2) {
        this.f746a.set(0, z2);
    }

    public jj b(String str) {
        this.f748b = str;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m599a() {
        a(jz.a(this.f745a));
        return this.f745a.array();
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m595a();
        kjVar.a(f740a);
        if (this.f741a != null) {
            kjVar.a(f24220a);
            kjVar.mo679a(this.f741a.a());
            kjVar.b();
        }
        kjVar.a(f24221b);
        kjVar.a(this.f747a);
        kjVar.b();
        kjVar.a(f24222c);
        kjVar.a(this.f749b);
        kjVar.b();
        if (this.f745a != null) {
            kjVar.a(f24223d);
            kjVar.a(this.f745a);
            kjVar.b();
        }
        if (this.f744a != null && f()) {
            kjVar.a(f24224e);
            kjVar.a(this.f744a);
            kjVar.b();
        }
        if (this.f748b != null && g()) {
            kjVar.a(f24225f);
            kjVar.a(this.f748b);
            kjVar.b();
        }
        if (this.f743a != null) {
            kjVar.a(f24226g);
            this.f743a.b(kjVar);
            kjVar.b();
        }
        if (this.f742a != null && i()) {
            kjVar.a(f24227h);
            this.f742a.b(kjVar);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    public jj a(ByteBuffer byteBuffer) {
        this.f745a = byteBuffer;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m594a() {
        return this.f744a;
    }

    public jj a(String str) {
        this.f744a = str;
        return this;
    }

    public jj a(jc jcVar) {
        this.f743a = jcVar;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ja m593a() {
        return this.f742a;
    }

    public jj a(ja jaVar) {
        this.f742a = jaVar;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m598a(jj jjVar) {
        if (jjVar == null) {
            return false;
        }
        boolean zM597a = m597a();
        boolean zM597a2 = jjVar.m597a();
        if (((zM597a || zM597a2) && (!zM597a || !zM597a2 || !this.f741a.equals(jjVar.f741a))) || this.f747a != jjVar.f747a || this.f749b != jjVar.f749b) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jjVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f745a.equals(jjVar.f745a))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jjVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f744a.equals(jjVar.f744a))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jjVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f748b.equals(jjVar.f748b))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jjVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f743a.m571a(jjVar.f743a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jjVar.i();
        if (zI || zI2) {
            return zI && zI2 && this.f742a.m563a(jjVar.f742a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jj jjVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(jjVar.getClass())) {
            return getClass().getName().compareTo(jjVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m597a()).compareTo(Boolean.valueOf(jjVar.m597a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m597a() && (iA8 = jz.a(this.f741a, jjVar.f741a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jjVar.c()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (c() && (iA7 = jz.a(this.f747a, jjVar.f747a)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jjVar.d()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (d() && (iA6 = jz.a(this.f749b, jjVar.f749b)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jjVar.e()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (e() && (iA5 = jz.a(this.f745a, jjVar.f745a)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jjVar.f()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (f() && (iA4 = jz.a(this.f744a, jjVar.f744a)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jjVar.g()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (g() && (iA3 = jz.a(this.f748b, jjVar.f748b)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jjVar.h()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (h() && (iA2 = jz.a(this.f743a, jjVar.f743a)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jjVar.i()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!i() || (iA = jz.a(this.f742a, jjVar.f742a)) == 0) {
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
                if (c()) {
                    if (d()) {
                        m595a();
                        return;
                    }
                    throw new kk("Required field 'isRequest' was not found in serialized data! Struct: " + toString());
                }
                throw new kk("Required field 'encryptAction' was not found in serialized data! Struct: " + toString());
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 8) {
                        this.f741a = in.a(kjVar.mo668a());
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 2) {
                        this.f747a = kjVar.mo680a();
                        m596a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 2) {
                        this.f749b = kjVar.mo680a();
                        m600b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f745a = kjVar.mo676a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f744a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                    if (b2 == 11) {
                        this.f748b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f743a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 12) {
                        ja jaVar = new ja();
                        this.f742a = jaVar;
                        jaVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                default:
                    km.a(kjVar, b2);
                    break;
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m595a() throws kk {
        if (this.f741a != null) {
            if (this.f745a != null) {
                if (this.f743a != null) {
                    return;
                }
                throw new kk("Required field 'target' was not present! Struct: " + toString());
            }
            throw new kk("Required field 'pushAction' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'action' was not present! Struct: " + toString());
    }
}
