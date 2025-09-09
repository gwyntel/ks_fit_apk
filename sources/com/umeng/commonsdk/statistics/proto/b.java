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
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class b implements bq<b, e>, Serializable, Cloneable {

    /* renamed from: d, reason: collision with root package name */
    public static final Map<e, cc> f22457d;

    /* renamed from: e, reason: collision with root package name */
    private static final long f22458e = -6496538196005191531L;

    /* renamed from: f, reason: collision with root package name */
    private static final cu f22459f = new cu("IdSnapshot");

    /* renamed from: g, reason: collision with root package name */
    private static final ck f22460g = new ck("identity", (byte) 11, 1);

    /* renamed from: h, reason: collision with root package name */
    private static final ck f22461h = new ck("ts", (byte) 10, 2);

    /* renamed from: i, reason: collision with root package name */
    private static final ck f22462i = new ck("version", (byte) 8, 3);

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Class<? extends cx>, cy> f22463j;

    /* renamed from: k, reason: collision with root package name */
    private static final int f22464k = 0;

    /* renamed from: l, reason: collision with root package name */
    private static final int f22465l = 1;

    /* renamed from: a, reason: collision with root package name */
    public String f22466a;

    /* renamed from: b, reason: collision with root package name */
    public long f22467b;

    /* renamed from: c, reason: collision with root package name */
    public int f22468c;

    /* renamed from: m, reason: collision with root package name */
    private byte f22469m;

    private static class a extends cz<b> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(cp cpVar, b bVar) throws bw {
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
                            cs.a(cpVar, b2);
                        } else if (b2 == 8) {
                            bVar.f22468c = cpVar.w();
                            bVar.c(true);
                        } else {
                            cs.a(cpVar, b2);
                        }
                    } else if (b2 == 10) {
                        bVar.f22467b = cpVar.x();
                        bVar.b(true);
                    } else {
                        cs.a(cpVar, b2);
                    }
                } else if (b2 == 11) {
                    bVar.f22466a = cpVar.z();
                    bVar.a(true);
                } else {
                    cs.a(cpVar, b2);
                }
                cpVar.m();
            }
            cpVar.k();
            if (!bVar.g()) {
                throw new cq("Required field 'ts' was not found in serialized data! Struct: " + toString());
            }
            if (bVar.j()) {
                bVar.k();
                return;
            }
            throw new cq("Required field 'version' was not found in serialized data! Struct: " + toString());
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(cp cpVar, b bVar) throws bw {
            bVar.k();
            cpVar.a(b.f22459f);
            if (bVar.f22466a != null) {
                cpVar.a(b.f22460g);
                cpVar.a(bVar.f22466a);
                cpVar.c();
            }
            cpVar.a(b.f22461h);
            cpVar.a(bVar.f22467b);
            cpVar.c();
            cpVar.a(b.f22462i);
            cpVar.a(bVar.f22468c);
            cpVar.c();
            cpVar.d();
            cpVar.b();
        }
    }

    /* renamed from: com.umeng.commonsdk.statistics.proto.b$b, reason: collision with other inner class name */
    private static class C0181b implements cy {
        private C0181b() {
        }

        @Override // com.umeng.analytics.pro.cy
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a b() {
            return new a();
        }
    }

    private static class c extends da<b> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cx
        public void a(cp cpVar, b bVar) throws bw {
            cv cvVar = (cv) cpVar;
            cvVar.a(bVar.f22466a);
            cvVar.a(bVar.f22467b);
            cvVar.a(bVar.f22468c);
        }

        @Override // com.umeng.analytics.pro.cx
        public void b(cp cpVar, b bVar) throws bw {
            cv cvVar = (cv) cpVar;
            bVar.f22466a = cvVar.z();
            bVar.a(true);
            bVar.f22467b = cvVar.x();
            bVar.b(true);
            bVar.f22468c = cvVar.w();
            bVar.c(true);
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
        f22463j = map;
        map.put(cz.class, new C0181b());
        map.put(da.class, new d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.IDENTITY, (e) new cc("identity", (byte) 1, new cd((byte) 11)));
        enumMap.put((EnumMap) e.TS, (e) new cc("ts", (byte) 1, new cd((byte) 10)));
        enumMap.put((EnumMap) e.VERSION, (e) new cc("version", (byte) 1, new cd((byte) 8)));
        Map<e, cc> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f22457d = mapUnmodifiableMap;
        cc.a(b.class, mapUnmodifiableMap);
    }

    public b() {
        this.f22469m = (byte) 0;
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public b deepCopy() {
        return new b(this);
    }

    public String b() {
        return this.f22466a;
    }

    public void c() {
        this.f22466a = null;
    }

    @Override // com.umeng.analytics.pro.bq
    public void clear() {
        this.f22466a = null;
        b(false);
        this.f22467b = 0L;
        c(false);
        this.f22468c = 0;
    }

    public boolean d() {
        return this.f22466a != null;
    }

    public long e() {
        return this.f22467b;
    }

    public void f() {
        this.f22469m = bn.b(this.f22469m, 0);
    }

    public boolean g() {
        return bn.a(this.f22469m, 0);
    }

    public int h() {
        return this.f22468c;
    }

    public void i() {
        this.f22469m = bn.b(this.f22469m, 1);
    }

    public boolean j() {
        return bn.a(this.f22469m, 1);
    }

    public void k() throws bw {
        if (this.f22466a != null) {
            return;
        }
        throw new cq("Required field 'identity' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.bq
    public void read(cp cpVar) throws bw {
        f22463j.get(cpVar.D()).b().b(cpVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IdSnapshot(");
        sb.append("identity:");
        String str = this.f22466a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("ts:");
        sb.append(this.f22467b);
        sb.append(", ");
        sb.append("version:");
        sb.append(this.f22468c);
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.bq
    public void write(cp cpVar) throws bw {
        f22463j.get(cpVar.D()).b().a(cpVar, this);
    }

    public enum e implements bx {
        IDENTITY(1, "identity"),
        TS(2, "ts"),
        VERSION(3, "version");


        /* renamed from: d, reason: collision with root package name */
        private static final Map<String, e> f22473d = new HashMap();

        /* renamed from: e, reason: collision with root package name */
        private final short f22475e;

        /* renamed from: f, reason: collision with root package name */
        private final String f22476f;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f22473d.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f22475e = s2;
            this.f22476f = str;
        }

        public static e a(int i2) {
            if (i2 == 1) {
                return IDENTITY;
            }
            if (i2 == 2) {
                return TS;
            }
            if (i2 != 3) {
                return null;
            }
            return VERSION;
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
            return this.f22476f;
        }

        public static e a(String str) {
            return f22473d.get(str);
        }

        @Override // com.umeng.analytics.pro.bx
        public short a() {
            return this.f22475e;
        }
    }

    public b a(String str) {
        this.f22466a = str;
        return this;
    }

    public void b(boolean z2) {
        this.f22469m = bn.a(this.f22469m, 0, z2);
    }

    public void c(boolean z2) {
        this.f22469m = bn.a(this.f22469m, 1, z2);
    }

    public b(String str, long j2, int i2) {
        this();
        this.f22466a = str;
        this.f22467b = j2;
        b(true);
        this.f22468c = i2;
        c(true);
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f22466a = null;
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public e fieldForId(int i2) {
        return e.a(i2);
    }

    public b a(long j2) {
        this.f22467b = j2;
        b(true);
        return this;
    }

    public b a(int i2) {
        this.f22468c = i2;
        c(true);
        return this;
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new cj(new db(objectOutputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public b(b bVar) {
        this.f22469m = (byte) 0;
        this.f22469m = bVar.f22469m;
        if (bVar.d()) {
            this.f22466a = bVar.f22466a;
        }
        this.f22467b = bVar.f22467b;
        this.f22468c = bVar.f22468c;
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f22469m = (byte) 0;
            read(new cj(new db(objectInputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
