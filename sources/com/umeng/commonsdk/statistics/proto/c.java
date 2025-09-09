package com.umeng.commonsdk.statistics.proto;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.umeng.analytics.pro.bq;
import com.umeng.analytics.pro.bw;
import com.umeng.analytics.pro.bx;
import com.umeng.analytics.pro.cc;
import com.umeng.analytics.pro.cd;
import com.umeng.analytics.pro.ce;
import com.umeng.analytics.pro.cf;
import com.umeng.analytics.pro.ch;
import com.umeng.analytics.pro.cj;
import com.umeng.analytics.pro.ck;
import com.umeng.analytics.pro.cl;
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
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class c implements bq<c, e>, Serializable, Cloneable {

    /* renamed from: d, reason: collision with root package name */
    public static final Map<e, cc> f22477d;

    /* renamed from: e, reason: collision with root package name */
    private static final long f22478e = -5764118265293965743L;

    /* renamed from: f, reason: collision with root package name */
    private static final cu f22479f = new cu("IdTracking");

    /* renamed from: g, reason: collision with root package name */
    private static final ck f22480g = new ck("snapshots", (byte) 13, 1);

    /* renamed from: h, reason: collision with root package name */
    private static final ck f22481h = new ck("journals", (byte) 15, 2);

    /* renamed from: i, reason: collision with root package name */
    private static final ck f22482i = new ck("checksum", (byte) 11, 3);

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Class<? extends cx>, cy> f22483j;

    /* renamed from: a, reason: collision with root package name */
    public Map<String, com.umeng.commonsdk.statistics.proto.b> f22484a;

    /* renamed from: b, reason: collision with root package name */
    public List<com.umeng.commonsdk.statistics.proto.a> f22485b;

    /* renamed from: c, reason: collision with root package name */
    public String f22486c;

    /* renamed from: k, reason: collision with root package name */
    private e[] f22487k;

    private static class a extends cz<c> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(cp cpVar, c cVar) throws bw {
            cpVar.j();
            while (true) {
                ck ckVarL = cpVar.l();
                byte b2 = ckVarL.f21619b;
                if (b2 == 0) {
                    cpVar.k();
                    cVar.n();
                    return;
                }
                short s2 = ckVarL.f21620c;
                int i2 = 0;
                if (s2 != 1) {
                    if (s2 != 2) {
                        if (s2 != 3) {
                            cs.a(cpVar, b2);
                        } else if (b2 == 11) {
                            cVar.f22486c = cpVar.z();
                            cVar.c(true);
                        } else {
                            cs.a(cpVar, b2);
                        }
                    } else if (b2 == 15) {
                        cl clVarP = cpVar.p();
                        cVar.f22485b = new ArrayList(clVarP.f21622b);
                        while (i2 < clVarP.f21622b) {
                            com.umeng.commonsdk.statistics.proto.a aVar = new com.umeng.commonsdk.statistics.proto.a();
                            aVar.read(cpVar);
                            cVar.f22485b.add(aVar);
                            i2++;
                        }
                        cpVar.q();
                        cVar.b(true);
                    } else {
                        cs.a(cpVar, b2);
                    }
                } else if (b2 == 13) {
                    cm cmVarN = cpVar.n();
                    cVar.f22484a = new HashMap(cmVarN.f21625c * 2);
                    while (i2 < cmVarN.f21625c) {
                        String strZ = cpVar.z();
                        com.umeng.commonsdk.statistics.proto.b bVar = new com.umeng.commonsdk.statistics.proto.b();
                        bVar.read(cpVar);
                        cVar.f22484a.put(strZ, bVar);
                        i2++;
                    }
                    cpVar.o();
                    cVar.a(true);
                } else {
                    cs.a(cpVar, b2);
                }
                cpVar.m();
            }
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(cp cpVar, c cVar) throws bw {
            cVar.n();
            cpVar.a(c.f22479f);
            if (cVar.f22484a != null) {
                cpVar.a(c.f22480g);
                cpVar.a(new cm((byte) 11, (byte) 12, cVar.f22484a.size()));
                for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.b> entry : cVar.f22484a.entrySet()) {
                    cpVar.a(entry.getKey());
                    entry.getValue().write(cpVar);
                }
                cpVar.e();
                cpVar.c();
            }
            if (cVar.f22485b != null && cVar.j()) {
                cpVar.a(c.f22481h);
                cpVar.a(new cl((byte) 12, cVar.f22485b.size()));
                Iterator<com.umeng.commonsdk.statistics.proto.a> it = cVar.f22485b.iterator();
                while (it.hasNext()) {
                    it.next().write(cpVar);
                }
                cpVar.f();
                cpVar.c();
            }
            if (cVar.f22486c != null && cVar.m()) {
                cpVar.a(c.f22482i);
                cpVar.a(cVar.f22486c);
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

    /* renamed from: com.umeng.commonsdk.statistics.proto.c$c, reason: collision with other inner class name */
    private static class C0182c extends da<c> {
        private C0182c() {
        }

        @Override // com.umeng.analytics.pro.cx
        public void a(cp cpVar, c cVar) throws bw {
            cv cvVar = (cv) cpVar;
            cvVar.a(cVar.f22484a.size());
            for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.b> entry : cVar.f22484a.entrySet()) {
                cvVar.a(entry.getKey());
                entry.getValue().write(cvVar);
            }
            BitSet bitSet = new BitSet();
            if (cVar.j()) {
                bitSet.set(0);
            }
            if (cVar.m()) {
                bitSet.set(1);
            }
            cvVar.a(bitSet, 2);
            if (cVar.j()) {
                cvVar.a(cVar.f22485b.size());
                Iterator<com.umeng.commonsdk.statistics.proto.a> it = cVar.f22485b.iterator();
                while (it.hasNext()) {
                    it.next().write(cvVar);
                }
            }
            if (cVar.m()) {
                cvVar.a(cVar.f22486c);
            }
        }

        @Override // com.umeng.analytics.pro.cx
        public void b(cp cpVar, c cVar) throws bw {
            cv cvVar = (cv) cpVar;
            cm cmVar = new cm((byte) 11, (byte) 12, cvVar.w());
            cVar.f22484a = new HashMap(cmVar.f21625c * 2);
            for (int i2 = 0; i2 < cmVar.f21625c; i2++) {
                String strZ = cvVar.z();
                com.umeng.commonsdk.statistics.proto.b bVar = new com.umeng.commonsdk.statistics.proto.b();
                bVar.read(cvVar);
                cVar.f22484a.put(strZ, bVar);
            }
            cVar.a(true);
            BitSet bitSetB = cvVar.b(2);
            if (bitSetB.get(0)) {
                cl clVar = new cl((byte) 12, cvVar.w());
                cVar.f22485b = new ArrayList(clVar.f21622b);
                for (int i3 = 0; i3 < clVar.f21622b; i3++) {
                    com.umeng.commonsdk.statistics.proto.a aVar = new com.umeng.commonsdk.statistics.proto.a();
                    aVar.read(cvVar);
                    cVar.f22485b.add(aVar);
                }
                cVar.b(true);
            }
            if (bitSetB.get(1)) {
                cVar.f22486c = cvVar.z();
                cVar.c(true);
            }
        }
    }

    private static class d implements cy {
        private d() {
        }

        @Override // com.umeng.analytics.pro.cy
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public C0182c b() {
            return new C0182c();
        }
    }

    static {
        HashMap map = new HashMap();
        f22483j = map;
        map.put(cz.class, new b());
        map.put(da.class, new d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.SNAPSHOTS, (e) new cc("snapshots", (byte) 1, new cf((byte) 13, new cd((byte) 11), new ch((byte) 12, com.umeng.commonsdk.statistics.proto.b.class))));
        enumMap.put((EnumMap) e.JOURNALS, (e) new cc("journals", (byte) 2, new ce((byte) 15, new ch((byte) 12, com.umeng.commonsdk.statistics.proto.a.class))));
        enumMap.put((EnumMap) e.CHECKSUM, (e) new cc("checksum", (byte) 2, new cd((byte) 11)));
        Map<e, cc> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f22477d = mapUnmodifiableMap;
        cc.a(c.class, mapUnmodifiableMap);
    }

    public c() {
        this.f22487k = new e[]{e.JOURNALS, e.CHECKSUM};
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public c deepCopy() {
        return new c(this);
    }

    public int b() {
        Map<String, com.umeng.commonsdk.statistics.proto.b> map = this.f22484a;
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public Map<String, com.umeng.commonsdk.statistics.proto.b> c() {
        return this.f22484a;
    }

    @Override // com.umeng.analytics.pro.bq
    public void clear() {
        this.f22484a = null;
        this.f22485b = null;
        this.f22486c = null;
    }

    public void d() {
        this.f22484a = null;
    }

    public boolean e() {
        return this.f22484a != null;
    }

    public int f() {
        List<com.umeng.commonsdk.statistics.proto.a> list = this.f22485b;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public Iterator<com.umeng.commonsdk.statistics.proto.a> g() {
        List<com.umeng.commonsdk.statistics.proto.a> list = this.f22485b;
        if (list == null) {
            return null;
        }
        return list.iterator();
    }

    public List<com.umeng.commonsdk.statistics.proto.a> h() {
        return this.f22485b;
    }

    public void i() {
        this.f22485b = null;
    }

    public boolean j() {
        return this.f22485b != null;
    }

    public String k() {
        return this.f22486c;
    }

    public void l() {
        this.f22486c = null;
    }

    public boolean m() {
        return this.f22486c != null;
    }

    public void n() throws bw {
        if (this.f22484a != null) {
            return;
        }
        throw new cq("Required field 'snapshots' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.bq
    public void read(cp cpVar) throws bw {
        f22483j.get(cpVar.D()).b().b(cpVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IdTracking(");
        sb.append("snapshots:");
        Map<String, com.umeng.commonsdk.statistics.proto.b> map = this.f22484a;
        if (map == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(map);
        }
        if (j()) {
            sb.append(", ");
            sb.append("journals:");
            List<com.umeng.commonsdk.statistics.proto.a> list = this.f22485b;
            if (list == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(list);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("checksum:");
            String str = this.f22486c;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.bq
    public void write(cp cpVar) throws bw {
        f22483j.get(cpVar.D()).b().a(cpVar, this);
    }

    public enum e implements bx {
        SNAPSHOTS(1, "snapshots"),
        JOURNALS(2, "journals"),
        CHECKSUM(3, "checksum");


        /* renamed from: d, reason: collision with root package name */
        private static final Map<String, e> f22491d = new HashMap();

        /* renamed from: e, reason: collision with root package name */
        private final short f22493e;

        /* renamed from: f, reason: collision with root package name */
        private final String f22494f;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f22491d.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f22493e = s2;
            this.f22494f = str;
        }

        public static e a(int i2) {
            if (i2 == 1) {
                return SNAPSHOTS;
            }
            if (i2 == 2) {
                return JOURNALS;
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
            return this.f22494f;
        }

        public static e a(String str) {
            return f22491d.get(str);
        }

        @Override // com.umeng.analytics.pro.bx
        public short a() {
            return this.f22493e;
        }
    }

    public void a(String str, com.umeng.commonsdk.statistics.proto.b bVar) {
        if (this.f22484a == null) {
            this.f22484a = new HashMap();
        }
        this.f22484a.put(str, bVar);
    }

    public void b(boolean z2) {
        if (z2) {
            return;
        }
        this.f22485b = null;
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f22486c = null;
    }

    public c(Map<String, com.umeng.commonsdk.statistics.proto.b> map) {
        this();
        this.f22484a = map;
    }

    public c(c cVar) {
        this.f22487k = new e[]{e.JOURNALS, e.CHECKSUM};
        if (cVar.e()) {
            HashMap map = new HashMap();
            for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.b> entry : cVar.f22484a.entrySet()) {
                map.put(entry.getKey(), new com.umeng.commonsdk.statistics.proto.b(entry.getValue()));
            }
            this.f22484a = map;
        }
        if (cVar.j()) {
            ArrayList arrayList = new ArrayList();
            Iterator<com.umeng.commonsdk.statistics.proto.a> it = cVar.f22485b.iterator();
            while (it.hasNext()) {
                arrayList.add(new com.umeng.commonsdk.statistics.proto.a(it.next()));
            }
            this.f22485b = arrayList;
        }
        if (cVar.m()) {
            this.f22486c = cVar.f22486c;
        }
    }

    public c a(Map<String, com.umeng.commonsdk.statistics.proto.b> map) {
        this.f22484a = map;
        return this;
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f22484a = null;
    }

    public void a(com.umeng.commonsdk.statistics.proto.a aVar) {
        if (this.f22485b == null) {
            this.f22485b = new ArrayList();
        }
        this.f22485b.add(aVar);
    }

    public c a(List<com.umeng.commonsdk.statistics.proto.a> list) {
        this.f22485b = list;
        return this;
    }

    public c a(String str) {
        this.f22486c = str;
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

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            read(new cj(new db(objectInputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
