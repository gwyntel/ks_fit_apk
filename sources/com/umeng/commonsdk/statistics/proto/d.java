package com.umeng.commonsdk.statistics.proto;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.umeng.analytics.pro.bn;
import com.umeng.analytics.pro.bq;
import com.umeng.analytics.pro.bw;
import com.umeng.analytics.pro.bx;
import com.umeng.analytics.pro.cc;
import com.umeng.analytics.pro.cd;
import com.umeng.analytics.pro.cf;
import com.umeng.analytics.pro.ch;
import com.umeng.analytics.pro.cj;
import com.umeng.analytics.pro.ck;
import com.umeng.analytics.pro.cm;
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
public class d implements bq<d, e>, Serializable, Cloneable {

    /* renamed from: d, reason: collision with root package name */
    public static final Map<e, cc> f22495d;

    /* renamed from: e, reason: collision with root package name */
    private static final long f22496e = 2846460275012375038L;

    /* renamed from: f, reason: collision with root package name */
    private static final cu f22497f = new cu("Imprint");

    /* renamed from: g, reason: collision with root package name */
    private static final ck f22498g = new ck("property", (byte) 13, 1);

    /* renamed from: h, reason: collision with root package name */
    private static final ck f22499h = new ck("version", (byte) 8, 2);

    /* renamed from: i, reason: collision with root package name */
    private static final ck f22500i = new ck("checksum", (byte) 11, 3);

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Class<? extends cx>, cy> f22501j;

    /* renamed from: k, reason: collision with root package name */
    private static final int f22502k = 0;

    /* renamed from: a, reason: collision with root package name */
    public Map<String, com.umeng.commonsdk.statistics.proto.e> f22503a;

    /* renamed from: b, reason: collision with root package name */
    public int f22504b;

    /* renamed from: c, reason: collision with root package name */
    public String f22505c;

    /* renamed from: l, reason: collision with root package name */
    private byte f22506l;

    private static class a extends cz<d> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(cp cpVar, d dVar) throws bw {
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
                        } else if (b2 == 11) {
                            dVar.f22505c = cpVar.z();
                            dVar.c(true);
                        } else {
                            cs.a(cpVar, b2);
                        }
                    } else if (b2 == 8) {
                        dVar.f22504b = cpVar.w();
                        dVar.b(true);
                    } else {
                        cs.a(cpVar, b2);
                    }
                } else if (b2 == 13) {
                    cm cmVarN = cpVar.n();
                    dVar.f22503a = new HashMap(cmVarN.f21625c * 2);
                    for (int i2 = 0; i2 < cmVarN.f21625c; i2++) {
                        String strZ = cpVar.z();
                        com.umeng.commonsdk.statistics.proto.e eVar = new com.umeng.commonsdk.statistics.proto.e();
                        eVar.read(cpVar);
                        dVar.f22503a.put(strZ, eVar);
                    }
                    cpVar.o();
                    dVar.a(true);
                } else {
                    cs.a(cpVar, b2);
                }
                cpVar.m();
            }
            cpVar.k();
            if (dVar.h()) {
                dVar.l();
                return;
            }
            throw new cq("Required field 'version' was not found in serialized data! Struct: " + toString());
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(cp cpVar, d dVar) throws bw {
            dVar.l();
            cpVar.a(d.f22497f);
            if (dVar.f22503a != null) {
                cpVar.a(d.f22498g);
                cpVar.a(new cm((byte) 11, (byte) 12, dVar.f22503a.size()));
                for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.e> entry : dVar.f22503a.entrySet()) {
                    cpVar.a(entry.getKey());
                    entry.getValue().write(cpVar);
                }
                cpVar.e();
                cpVar.c();
            }
            cpVar.a(d.f22499h);
            cpVar.a(dVar.f22504b);
            cpVar.c();
            if (dVar.f22505c != null) {
                cpVar.a(d.f22500i);
                cpVar.a(dVar.f22505c);
                cpVar.c();
            }
            cpVar.d();
            cpVar.b();
        }
    }

    private static class b implements cy {
        private b() {
        }

        @Override // com.umeng.analytics.pro.cy
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a b() {
            return new a();
        }
    }

    private static class c extends da<d> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cx
        public void a(cp cpVar, d dVar) throws bw {
            cv cvVar = (cv) cpVar;
            cvVar.a(dVar.f22503a.size());
            for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.e> entry : dVar.f22503a.entrySet()) {
                cvVar.a(entry.getKey());
                entry.getValue().write(cvVar);
            }
            cvVar.a(dVar.f22504b);
            cvVar.a(dVar.f22505c);
        }

        @Override // com.umeng.analytics.pro.cx
        public void b(cp cpVar, d dVar) throws bw {
            cv cvVar = (cv) cpVar;
            cm cmVar = new cm((byte) 11, (byte) 12, cvVar.w());
            dVar.f22503a = new HashMap(cmVar.f21625c * 2);
            for (int i2 = 0; i2 < cmVar.f21625c; i2++) {
                String strZ = cvVar.z();
                com.umeng.commonsdk.statistics.proto.e eVar = new com.umeng.commonsdk.statistics.proto.e();
                eVar.read(cvVar);
                dVar.f22503a.put(strZ, eVar);
            }
            dVar.a(true);
            dVar.f22504b = cvVar.w();
            dVar.b(true);
            dVar.f22505c = cvVar.z();
            dVar.c(true);
        }
    }

    /* renamed from: com.umeng.commonsdk.statistics.proto.d$d, reason: collision with other inner class name */
    private static class C0183d implements cy {
        private C0183d() {
        }

        @Override // com.umeng.analytics.pro.cy
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c b() {
            return new c();
        }
    }

    static {
        HashMap map = new HashMap();
        f22501j = map;
        map.put(cz.class, new b());
        map.put(da.class, new C0183d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.PROPERTY, (e) new cc("property", (byte) 1, new cf((byte) 13, new cd((byte) 11), new ch((byte) 12, com.umeng.commonsdk.statistics.proto.e.class))));
        enumMap.put((EnumMap) e.VERSION, (e) new cc("version", (byte) 1, new cd((byte) 8)));
        enumMap.put((EnumMap) e.CHECKSUM, (e) new cc("checksum", (byte) 1, new cd((byte) 11)));
        Map<e, cc> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f22495d = mapUnmodifiableMap;
        cc.a(d.class, mapUnmodifiableMap);
    }

    public d() {
        this.f22506l = (byte) 0;
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public d deepCopy() {
        return new d(this);
    }

    public int b() {
        Map<String, com.umeng.commonsdk.statistics.proto.e> map = this.f22503a;
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public Map<String, com.umeng.commonsdk.statistics.proto.e> c() {
        return this.f22503a;
    }

    @Override // com.umeng.analytics.pro.bq
    public void clear() {
        this.f22503a = null;
        b(false);
        this.f22504b = 0;
        this.f22505c = null;
    }

    public void d() {
        this.f22503a = null;
    }

    public boolean e() {
        return this.f22503a != null;
    }

    public int f() {
        return this.f22504b;
    }

    public void g() {
        this.f22506l = bn.b(this.f22506l, 0);
    }

    public boolean h() {
        return bn.a(this.f22506l, 0);
    }

    public String i() {
        return this.f22505c;
    }

    public void j() {
        this.f22505c = null;
    }

    public boolean k() {
        return this.f22505c != null;
    }

    public void l() throws bw {
        if (this.f22503a == null) {
            throw new cq("Required field 'property' was not present! Struct: " + toString());
        }
        if (this.f22505c != null) {
            return;
        }
        throw new cq("Required field 'checksum' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.bq
    public void read(cp cpVar) throws bw {
        f22501j.get(cpVar.D()).b().b(cpVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Imprint(");
        sb.append("property:");
        Map<String, com.umeng.commonsdk.statistics.proto.e> map = this.f22503a;
        if (map == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(map);
        }
        sb.append(", ");
        sb.append("version:");
        sb.append(this.f22504b);
        sb.append(", ");
        sb.append("checksum:");
        String str = this.f22505c;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.bq
    public void write(cp cpVar) throws bw {
        f22501j.get(cpVar.D()).b().a(cpVar, this);
    }

    public enum e implements bx {
        PROPERTY(1, "property"),
        VERSION(2, "version"),
        CHECKSUM(3, "checksum");


        /* renamed from: d, reason: collision with root package name */
        private static final Map<String, e> f22510d = new HashMap();

        /* renamed from: e, reason: collision with root package name */
        private final short f22512e;

        /* renamed from: f, reason: collision with root package name */
        private final String f22513f;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f22510d.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f22512e = s2;
            this.f22513f = str;
        }

        public static e a(int i2) {
            if (i2 == 1) {
                return PROPERTY;
            }
            if (i2 == 2) {
                return VERSION;
            }
            if (i2 != 3) {
                return null;
            }
            return CHECKSUM;
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
            return this.f22513f;
        }

        public static e a(String str) {
            return f22510d.get(str);
        }

        @Override // com.umeng.analytics.pro.bx
        public short a() {
            return this.f22512e;
        }
    }

    public void a(String str, com.umeng.commonsdk.statistics.proto.e eVar) {
        if (this.f22503a == null) {
            this.f22503a = new HashMap();
        }
        this.f22503a.put(str, eVar);
    }

    public void b(boolean z2) {
        this.f22506l = bn.a(this.f22506l, 0, z2);
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f22505c = null;
    }

    public d(Map<String, com.umeng.commonsdk.statistics.proto.e> map, int i2, String str) {
        this();
        this.f22503a = map;
        this.f22504b = i2;
        b(true);
        this.f22505c = str;
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public e fieldForId(int i2) {
        return e.a(i2);
    }

    public d a(Map<String, com.umeng.commonsdk.statistics.proto.e> map) {
        this.f22503a = map;
        return this;
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f22503a = null;
    }

    public d a(int i2) {
        this.f22504b = i2;
        b(true);
        return this;
    }

    public d(d dVar) {
        this.f22506l = (byte) 0;
        this.f22506l = dVar.f22506l;
        if (dVar.e()) {
            HashMap map = new HashMap();
            for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.e> entry : dVar.f22503a.entrySet()) {
                map.put(entry.getKey(), new com.umeng.commonsdk.statistics.proto.e(entry.getValue()));
            }
            this.f22503a = map;
        }
        this.f22504b = dVar.f22504b;
        if (dVar.k()) {
            this.f22505c = dVar.f22505c;
        }
    }

    public d a(String str) {
        this.f22505c = str;
        return this;
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new cj(new db(objectOutputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f22506l = (byte) 0;
            read(new cj(new db(objectInputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
