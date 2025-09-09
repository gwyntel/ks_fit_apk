package com.umeng.commonsdk.statistics.proto;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.umeng.analytics.pro.bn;
import com.umeng.analytics.pro.bq;
import com.umeng.analytics.pro.bw;
import com.umeng.analytics.pro.bx;
import com.umeng.analytics.pro.cc;
import com.umeng.analytics.pro.cd;
import com.umeng.analytics.pro.cj;
import com.umeng.analytics.pro.ck;
import com.umeng.analytics.pro.cp;
import com.umeng.analytics.pro.cq;
import com.umeng.analytics.pro.cs;
import com.umeng.analytics.pro.cu;
import com.umeng.analytics.pro.cv;
import com.umeng.analytics.pro.cx;
import com.umeng.analytics.pro.cy;
import com.umeng.analytics.pro.cz;
import com.umeng.analytics.pro.da;
import com.umeng.analytics.pro.db;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class a implements bq<a, e>, Serializable, Cloneable {

    /* renamed from: e, reason: collision with root package name */
    public static final Map<e, cc> f22434e;

    /* renamed from: f, reason: collision with root package name */
    private static final long f22435f = 9132678615281394583L;

    /* renamed from: g, reason: collision with root package name */
    private static final cu f22436g = new cu("IdJournal");

    /* renamed from: h, reason: collision with root package name */
    private static final ck f22437h = new ck("domain", (byte) 11, 1);

    /* renamed from: i, reason: collision with root package name */
    private static final ck f22438i = new ck("old_id", (byte) 11, 2);

    /* renamed from: j, reason: collision with root package name */
    private static final ck f22439j = new ck("new_id", (byte) 11, 3);

    /* renamed from: k, reason: collision with root package name */
    private static final ck f22440k = new ck("ts", (byte) 10, 4);

    /* renamed from: l, reason: collision with root package name */
    private static final Map<Class<? extends cx>, cy> f22441l;

    /* renamed from: m, reason: collision with root package name */
    private static final int f22442m = 0;

    /* renamed from: a, reason: collision with root package name */
    public String f22443a;

    /* renamed from: b, reason: collision with root package name */
    public String f22444b;

    /* renamed from: c, reason: collision with root package name */
    public String f22445c;

    /* renamed from: d, reason: collision with root package name */
    public long f22446d;

    /* renamed from: n, reason: collision with root package name */
    private byte f22447n;

    /* renamed from: o, reason: collision with root package name */
    private e[] f22448o;

    /* renamed from: com.umeng.commonsdk.statistics.proto.a$a, reason: collision with other inner class name */
    private static class C0180a extends cz<a> {
        private C0180a() {
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(cp cpVar, a aVar) throws bw {
            cpVar.j();
            while (true) {
                ck ckVarL = cpVar.l();
                byte b2 = ckVarL.f21619b;
                if (b2 == 0) {
                    break;
                }
                short s2 = ckVarL.f21620c;
                if (s2 != 1) {
                    if (s2 != 2) {
                        if (s2 != 3) {
                            if (s2 != 4) {
                                cs.a(cpVar, b2);
                            } else if (b2 == 10) {
                                aVar.f22446d = cpVar.x();
                                aVar.d(true);
                            } else {
                                cs.a(cpVar, b2);
                            }
                        } else if (b2 == 11) {
                            aVar.f22445c = cpVar.z();
                            aVar.c(true);
                        } else {
                            cs.a(cpVar, b2);
                        }
                    } else if (b2 == 11) {
                        aVar.f22444b = cpVar.z();
                        aVar.b(true);
                    } else {
                        cs.a(cpVar, b2);
                    }
                } else if (b2 == 11) {
                    aVar.f22443a = cpVar.z();
                    aVar.a(true);
                } else {
                    cs.a(cpVar, b2);
                }
                cpVar.m();
            }
            cpVar.k();
            if (aVar.m()) {
                aVar.n();
                return;
            }
            throw new cq("Required field 'ts' was not found in serialized data! Struct: " + toString());
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(cp cpVar, a aVar) throws bw {
            aVar.n();
            cpVar.a(a.f22436g);
            if (aVar.f22443a != null) {
                cpVar.a(a.f22437h);
                cpVar.a(aVar.f22443a);
                cpVar.c();
            }
            if (aVar.f22444b != null && aVar.g()) {
                cpVar.a(a.f22438i);
                cpVar.a(aVar.f22444b);
                cpVar.c();
            }
            if (aVar.f22445c != null) {
                cpVar.a(a.f22439j);
                cpVar.a(aVar.f22445c);
                cpVar.c();
            }
            cpVar.a(a.f22440k);
            cpVar.a(aVar.f22446d);
            cpVar.c();
            cpVar.d();
            cpVar.b();
        }
    }

    private static class b implements cy {
        private b() {
        }

        @Override // com.umeng.analytics.pro.cy
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public C0180a b() {
            return new C0180a();
        }
    }

    private static class c extends da<a> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cx
        public void a(cp cpVar, a aVar) throws bw {
            cv cvVar = (cv) cpVar;
            cvVar.a(aVar.f22443a);
            cvVar.a(aVar.f22445c);
            cvVar.a(aVar.f22446d);
            BitSet bitSet = new BitSet();
            if (aVar.g()) {
                bitSet.set(0);
            }
            cvVar.a(bitSet, 1);
            if (aVar.g()) {
                cvVar.a(aVar.f22444b);
            }
        }

        @Override // com.umeng.analytics.pro.cx
        public void b(cp cpVar, a aVar) throws bw {
            cv cvVar = (cv) cpVar;
            aVar.f22443a = cvVar.z();
            aVar.a(true);
            aVar.f22445c = cvVar.z();
            aVar.c(true);
            aVar.f22446d = cvVar.x();
            aVar.d(true);
            if (cvVar.b(1).get(0)) {
                aVar.f22444b = cvVar.z();
                aVar.b(true);
            }
        }
    }

    private static class d implements cy {
        private d() {
        }

        @Override // com.umeng.analytics.pro.cy
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c b() {
            return new c();
        }
    }

    static {
        HashMap map = new HashMap();
        f22441l = map;
        map.put(cz.class, new b());
        map.put(da.class, new d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.DOMAIN, (e) new cc("domain", (byte) 1, new cd((byte) 11)));
        enumMap.put((EnumMap) e.OLD_ID, (e) new cc("old_id", (byte) 2, new cd((byte) 11)));
        enumMap.put((EnumMap) e.NEW_ID, (e) new cc("new_id", (byte) 1, new cd((byte) 11)));
        enumMap.put((EnumMap) e.TS, (e) new cc("ts", (byte) 1, new cd((byte) 10)));
        Map<e, cc> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f22434e = mapUnmodifiableMap;
        cc.a(a.class, mapUnmodifiableMap);
    }

    public a() {
        this.f22447n = (byte) 0;
        this.f22448o = new e[]{e.OLD_ID};
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public a deepCopy() {
        return new a(this);
    }

    public String b() {
        return this.f22443a;
    }

    public void c() {
        this.f22443a = null;
    }

    @Override // com.umeng.analytics.pro.bq
    public void clear() {
        this.f22443a = null;
        this.f22444b = null;
        this.f22445c = null;
        d(false);
        this.f22446d = 0L;
    }

    public boolean d() {
        return this.f22443a != null;
    }

    public String e() {
        return this.f22444b;
    }

    public void f() {
        this.f22444b = null;
    }

    public boolean g() {
        return this.f22444b != null;
    }

    public String h() {
        return this.f22445c;
    }

    public void i() {
        this.f22445c = null;
    }

    public boolean j() {
        return this.f22445c != null;
    }

    public long k() {
        return this.f22446d;
    }

    public void l() {
        this.f22447n = bn.b(this.f22447n, 0);
    }

    public boolean m() {
        return bn.a(this.f22447n, 0);
    }

    public void n() throws bw {
        if (this.f22443a == null) {
            throw new cq("Required field 'domain' was not present! Struct: " + toString());
        }
        if (this.f22445c != null) {
            return;
        }
        throw new cq("Required field 'new_id' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.bq
    public void read(cp cpVar) throws bw {
        f22441l.get(cpVar.D()).b().b(cpVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IdJournal(");
        sb.append("domain:");
        String str = this.f22443a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        if (g()) {
            sb.append(", ");
            sb.append("old_id:");
            String str2 = this.f22444b;
            if (str2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str2);
            }
        }
        sb.append(", ");
        sb.append("new_id:");
        String str3 = this.f22445c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("ts:");
        sb.append(this.f22446d);
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.bq
    public void write(cp cpVar) throws bw {
        f22441l.get(cpVar.D()).b().a(cpVar, this);
    }

    public enum e implements bx {
        DOMAIN(1, "domain"),
        OLD_ID(2, "old_id"),
        NEW_ID(3, "new_id"),
        TS(4, "ts");


        /* renamed from: e, reason: collision with root package name */
        private static final Map<String, e> f22453e = new HashMap();

        /* renamed from: f, reason: collision with root package name */
        private final short f22455f;

        /* renamed from: g, reason: collision with root package name */
        private final String f22456g;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f22453e.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f22455f = s2;
            this.f22456g = str;
        }

        public static e a(int i2) {
            if (i2 == 1) {
                return DOMAIN;
            }
            if (i2 == 2) {
                return OLD_ID;
            }
            if (i2 == 3) {
                return NEW_ID;
            }
            if (i2 != 4) {
                return null;
            }
            return TS;
        }

        public static e b(int i2) {
            e eVarA = a(i2);
            if (eVarA != null) {
                return eVarA;
            }
            throw new IllegalArgumentException("Field " + i2 + " doesn't exist!");
        }

        @Override // com.umeng.analytics.pro.bx
        public String b() {
            return this.f22456g;
        }

        public static e a(String str) {
            return f22453e.get(str);
        }

        @Override // com.umeng.analytics.pro.bx
        public short a() {
            return this.f22455f;
        }
    }

    public a a(String str) {
        this.f22443a = str;
        return this;
    }

    public a b(String str) {
        this.f22444b = str;
        return this;
    }

    public a c(String str) {
        this.f22445c = str;
        return this;
    }

    public void d(boolean z2) {
        this.f22447n = bn.a(this.f22447n, 0, z2);
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f22443a = null;
    }

    public void b(boolean z2) {
        if (z2) {
            return;
        }
        this.f22444b = null;
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f22445c = null;
    }

    public a(String str, String str2, long j2) {
        this();
        this.f22443a = str;
        this.f22445c = str2;
        this.f22446d = j2;
        d(true);
    }

    public a a(long j2) {
        this.f22446d = j2;
        d(true);
        return this;
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public e fieldForId(int i2) {
        return e.a(i2);
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new cj(new db(objectOutputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public a(a aVar) {
        this.f22447n = (byte) 0;
        this.f22448o = new e[]{e.OLD_ID};
        this.f22447n = aVar.f22447n;
        if (aVar.d()) {
            this.f22443a = aVar.f22443a;
        }
        if (aVar.g()) {
            this.f22444b = aVar.f22444b;
        }
        if (aVar.j()) {
            this.f22445c = aVar.f22445c;
        }
        this.f22446d = aVar.f22446d;
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f22447n = (byte) 0;
            read(new cj(new db(objectInputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
