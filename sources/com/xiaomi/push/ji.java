package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ji implements jy<ji, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f729a;

    /* renamed from: a, reason: collision with other field name */
    public jc f730a;

    /* renamed from: a, reason: collision with other field name */
    public String f731a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f733a;

    /* renamed from: b, reason: collision with other field name */
    public String f735b;

    /* renamed from: c, reason: collision with other field name */
    public String f736c;

    /* renamed from: d, reason: collision with other field name */
    public String f737d;

    /* renamed from: e, reason: collision with other field name */
    public String f738e;

    /* renamed from: f, reason: collision with other field name */
    public String f739f;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f728a = new ko("XmPushActionCommandResult");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24210a = new kg("", (byte) 12, 2);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24211b = new kg("", (byte) 11, 3);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24212c = new kg("", (byte) 11, 4);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24213d = new kg("", (byte) 11, 5);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24214e = new kg("", (byte) 10, 7);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24215f = new kg("", (byte) 11, 8);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24216g = new kg("", (byte) 11, 9);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24217h = new kg("", (byte) 15, 10);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24218i = new kg("", (byte) 11, 12);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24219j = new kg("", (byte) 2, 13);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f732a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    public boolean f734a = true;

    /* renamed from: a, reason: collision with other method in class */
    public boolean m589a() {
        return this.f730a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m591b() {
        return this.f731a != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m592c() {
        return this.f735b != null;
    }

    public boolean d() {
        return this.f736c != null;
    }

    public boolean e() {
        return this.f732a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ji)) {
            return m590a((ji) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f737d != null;
    }

    public boolean g() {
        return this.f738e != null;
    }

    public boolean h() {
        return this.f733a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f739f != null;
    }

    public boolean j() {
        return this.f732a.get(1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCommandResult(");
        if (m589a()) {
            sb.append("target:");
            jc jcVar = this.f730a;
            if (jcVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jcVar);
            }
            sb.append(", ");
        }
        sb.append("id:");
        String str = this.f731a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("appId:");
        String str2 = this.f735b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("cmdName:");
        String str3 = this.f736c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f729a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f737d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f738e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            List<String> list = this.f733a;
            if (list == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(list);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("category:");
            String str6 = this.f739f;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("response2Client:");
            sb.append(this.f734a);
        }
        sb.append(")");
        return sb.toString();
    }

    public String a() {
        return this.f731a;
    }

    public String b() {
        return this.f736c;
    }

    public String c() {
        return this.f739f;
    }

    public void a(boolean z2) {
        this.f732a.set(0, z2);
    }

    public void b(boolean z2) {
        this.f732a.set(1, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public List<String> m587a() {
        return this.f733a;
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m588a();
        kjVar.a(f728a);
        if (this.f730a != null && m589a()) {
            kjVar.a(f24210a);
            this.f730a.b(kjVar);
            kjVar.b();
        }
        if (this.f731a != null) {
            kjVar.a(f24211b);
            kjVar.a(this.f731a);
            kjVar.b();
        }
        if (this.f735b != null) {
            kjVar.a(f24212c);
            kjVar.a(this.f735b);
            kjVar.b();
        }
        if (this.f736c != null) {
            kjVar.a(f24213d);
            kjVar.a(this.f736c);
            kjVar.b();
        }
        kjVar.a(f24214e);
        kjVar.a(this.f729a);
        kjVar.b();
        if (this.f737d != null && f()) {
            kjVar.a(f24215f);
            kjVar.a(this.f737d);
            kjVar.b();
        }
        if (this.f738e != null && g()) {
            kjVar.a(f24216g);
            kjVar.a(this.f738e);
            kjVar.b();
        }
        if (this.f733a != null && h()) {
            kjVar.a(f24217h);
            kjVar.a(new kh((byte) 11, this.f733a.size()));
            Iterator<String> it = this.f733a.iterator();
            while (it.hasNext()) {
                kjVar.a(it.next());
            }
            kjVar.e();
            kjVar.b();
        }
        if (this.f739f != null && i()) {
            kjVar.a(f24218i);
            kjVar.a(this.f739f);
            kjVar.b();
        }
        if (j()) {
            kjVar.a(f24219j);
            kjVar.a(this.f734a);
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m590a(ji jiVar) {
        if (jiVar == null) {
            return false;
        }
        boolean zM589a = m589a();
        boolean zM589a2 = jiVar.m589a();
        if ((zM589a || zM589a2) && !(zM589a && zM589a2 && this.f730a.m571a(jiVar.f730a))) {
            return false;
        }
        boolean zM591b = m591b();
        boolean zM591b2 = jiVar.m591b();
        if ((zM591b || zM591b2) && !(zM591b && zM591b2 && this.f731a.equals(jiVar.f731a))) {
            return false;
        }
        boolean zM592c = m592c();
        boolean zM592c2 = jiVar.m592c();
        if ((zM592c || zM592c2) && !(zM592c && zM592c2 && this.f735b.equals(jiVar.f735b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jiVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f736c.equals(jiVar.f736c))) || this.f729a != jiVar.f729a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jiVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f737d.equals(jiVar.f737d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jiVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f738e.equals(jiVar.f738e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jiVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f733a.equals(jiVar.f733a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jiVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f739f.equals(jiVar.f739f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jiVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f734a == jiVar.f734a;
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ji jiVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        int iA10;
        if (!getClass().equals(jiVar.getClass())) {
            return getClass().getName().compareTo(jiVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m589a()).compareTo(Boolean.valueOf(jiVar.m589a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m589a() && (iA10 = jz.a(this.f730a, jiVar.f730a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(m591b()).compareTo(Boolean.valueOf(jiVar.m591b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m591b() && (iA9 = jz.a(this.f731a, jiVar.f731a)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(m592c()).compareTo(Boolean.valueOf(jiVar.m592c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m592c() && (iA8 = jz.a(this.f735b, jiVar.f735b)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jiVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jz.a(this.f736c, jiVar.f736c)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jiVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jz.a(this.f729a, jiVar.f729a)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jiVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jz.a(this.f737d, jiVar.f737d)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jiVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jz.a(this.f738e, jiVar.f738e)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jiVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jz.a(this.f733a, jiVar.f733a)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jiVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jz.a(this.f739f, jiVar.f739f)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jiVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jz.a(this.f734a, jiVar.f734a)) == 0) {
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
                if (e()) {
                    m588a();
                    return;
                }
                throw new kk("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (kgVarMo670a.f922a) {
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f730a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f731a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f735b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                    if (b2 == 11) {
                        this.f736c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 6:
                case 11:
                default:
                    km.a(kjVar, b2);
                    break;
                case 7:
                    if (b2 == 10) {
                        this.f729a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f737d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f738e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 15) {
                        kh khVarMo671a = kjVar.mo671a();
                        this.f733a = new ArrayList(khVarMo671a.f923a);
                        for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                            this.f733a.add(kjVar.mo675a());
                        }
                        kjVar.i();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 12:
                    if (b2 == 11) {
                        this.f739f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 13:
                    if (b2 == 2) {
                        this.f734a = kjVar.mo680a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m588a() throws kk {
        if (this.f731a != null) {
            if (this.f735b != null) {
                if (this.f736c != null) {
                    return;
                }
                throw new kk("Required field 'cmdName' was not present! Struct: " + toString());
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
