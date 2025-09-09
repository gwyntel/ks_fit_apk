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
public class e implements bq<e, EnumC0184e>, Serializable, Cloneable {

    /* renamed from: d, reason: collision with root package name */
    public static final Map<EnumC0184e, cc> f22514d;

    /* renamed from: e, reason: collision with root package name */
    private static final long f22515e = 7501688097813630241L;

    /* renamed from: f, reason: collision with root package name */
    private static final cu f22516f = new cu("ImprintValue");

    /* renamed from: g, reason: collision with root package name */
    private static final ck f22517g = new ck("value", (byte) 11, 1);

    /* renamed from: h, reason: collision with root package name */
    private static final ck f22518h = new ck("ts", (byte) 10, 2);

    /* renamed from: i, reason: collision with root package name */
    private static final ck f22519i = new ck("guid", (byte) 11, 3);

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Class<? extends cx>, cy> f22520j;

    /* renamed from: k, reason: collision with root package name */
    private static final int f22521k = 0;

    /* renamed from: a, reason: collision with root package name */
    public String f22522a;

    /* renamed from: b, reason: collision with root package name */
    public long f22523b;

    /* renamed from: c, reason: collision with root package name */
    public String f22524c;

    /* renamed from: l, reason: collision with root package name */
    private byte f22525l;

    /* renamed from: m, reason: collision with root package name */
    private EnumC0184e[] f22526m;

    private static class a extends cz<e> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(cp cpVar, e eVar) throws bw {
            cpVar.j();
            while (true) {
                ck ckVarL = cpVar.l();
                byte b2 = ckVarL.f21619b;
                if (b2 == 0) {
                    cpVar.k();
                    eVar.k();
                    return;
                }
                short s2 = ckVarL.f21620c;
                if (s2 != 1) {
                    if (s2 != 2) {
                        if (s2 != 3) {
                            cs.a(cpVar, b2);
                        } else if (b2 == 11) {
                            eVar.f22524c = cpVar.z();
                            eVar.c(true);
                        } else {
                            cs.a(cpVar, b2);
                        }
                    } else if (b2 == 10) {
                        eVar.f22523b = cpVar.x();
                        eVar.b(true);
                    } else {
                        cs.a(cpVar, b2);
                    }
                } else if (b2 == 11) {
                    eVar.f22522a = cpVar.z();
                    eVar.a(true);
                } else {
                    cs.a(cpVar, b2);
                }
                cpVar.m();
            }
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(cp cpVar, e eVar) throws bw {
            eVar.k();
            cpVar.a(e.f22516f);
            if (eVar.f22522a != null && eVar.d()) {
                cpVar.a(e.f22517g);
                cpVar.a(eVar.f22522a);
                cpVar.c();
            }
            if (eVar.g()) {
                cpVar.a(e.f22518h);
                cpVar.a(eVar.f22523b);
                cpVar.c();
            }
            if (eVar.f22524c != null && eVar.j()) {
                cpVar.a(e.f22519i);
                cpVar.a(eVar.f22524c);
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

    private static class c extends da<e> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cx
        public void a(cp cpVar, e eVar) throws bw {
            cv cvVar = (cv) cpVar;
            BitSet bitSet = new BitSet();
            if (eVar.d()) {
                bitSet.set(0);
            }
            if (eVar.g()) {
                bitSet.set(1);
            }
            if (eVar.j()) {
                bitSet.set(2);
            }
            cvVar.a(bitSet, 3);
            if (eVar.d()) {
                cvVar.a(eVar.f22522a);
            }
            if (eVar.g()) {
                cvVar.a(eVar.f22523b);
            }
            if (eVar.j()) {
                cvVar.a(eVar.f22524c);
            }
        }

        @Override // com.umeng.analytics.pro.cx
        public void b(cp cpVar, e eVar) throws bw {
            cv cvVar = (cv) cpVar;
            BitSet bitSetB = cvVar.b(3);
            if (bitSetB.get(0)) {
                eVar.f22522a = cvVar.z();
                eVar.a(true);
            }
            if (bitSetB.get(1)) {
                eVar.f22523b = cvVar.x();
                eVar.b(true);
            }
            if (bitSetB.get(2)) {
                eVar.f22524c = cvVar.z();
                eVar.c(true);
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
        f22520j = map;
        map.put(cz.class, new b());
        map.put(da.class, new d());
        EnumMap enumMap = new EnumMap(EnumC0184e.class);
        enumMap.put((EnumMap) EnumC0184e.VALUE, (EnumC0184e) new cc("value", (byte) 2, new cd((byte) 11)));
        enumMap.put((EnumMap) EnumC0184e.TS, (EnumC0184e) new cc("ts", (byte) 2, new cd((byte) 10)));
        enumMap.put((EnumMap) EnumC0184e.GUID, (EnumC0184e) new cc("guid", (byte) 2, new cd((byte) 11)));
        Map<EnumC0184e, cc> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f22514d = mapUnmodifiableMap;
        cc.a(e.class, mapUnmodifiableMap);
    }

    public e() {
        this.f22525l = (byte) 0;
        this.f22526m = new EnumC0184e[]{EnumC0184e.VALUE, EnumC0184e.TS, EnumC0184e.GUID};
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public e deepCopy() {
        return new e(this);
    }

    public String b() {
        return this.f22522a;
    }

    public void c() {
        this.f22522a = null;
    }

    @Override // com.umeng.analytics.pro.bq
    public void clear() {
        this.f22522a = null;
        b(false);
        this.f22523b = 0L;
        this.f22524c = null;
    }

    public boolean d() {
        return this.f22522a != null;
    }

    public long e() {
        return this.f22523b;
    }

    public void f() {
        this.f22525l = bn.b(this.f22525l, 0);
    }

    public boolean g() {
        return bn.a(this.f22525l, 0);
    }

    public String h() {
        return this.f22524c;
    }

    public void i() {
        this.f22524c = null;
    }

    public boolean j() {
        return this.f22524c != null;
    }

    public void k() throws bw {
    }

    @Override // com.umeng.analytics.pro.bq
    public void read(cp cpVar) throws bw {
        f22520j.get(cpVar.D()).b().b(cpVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ImprintValue(");
        if (d()) {
            sb.append("value:");
            String str = this.f22522a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            sb.append(", ");
        }
        sb.append("ts:");
        sb.append(this.f22523b);
        sb.append(", ");
        sb.append("guid:");
        String str2 = this.f22524c;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.bq
    public void write(cp cpVar) throws bw {
        f22520j.get(cpVar.D()).b().a(cpVar, this);
    }

    /* renamed from: com.umeng.commonsdk.statistics.proto.e$e, reason: collision with other inner class name */
    public enum EnumC0184e implements bx {
        VALUE(1, "value"),
        TS(2, "ts"),
        GUID(3, "guid");


        /* renamed from: d, reason: collision with root package name */
        private static final Map<String, EnumC0184e> f22530d = new HashMap();

        /* renamed from: e, reason: collision with root package name */
        private final short f22532e;

        /* renamed from: f, reason: collision with root package name */
        private final String f22533f;

        static {
            Iterator it = EnumSet.allOf(EnumC0184e.class).iterator();
            while (it.hasNext()) {
                EnumC0184e enumC0184e = (EnumC0184e) it.next();
                f22530d.put(enumC0184e.b(), enumC0184e);
            }
        }

        EnumC0184e(short s2, String str) {
            this.f22532e = s2;
            this.f22533f = str;
        }

        public static EnumC0184e a(int i2) {
            if (i2 == 1) {
                return VALUE;
            }
            if (i2 == 2) {
                return TS;
            }
            if (i2 != 3) {
                return null;
            }
            return GUID;
        }

        public static EnumC0184e b(int i2) {
            EnumC0184e enumC0184eA = a(i2);
            if (enumC0184eA != null) {
                return enumC0184eA;
            }
            throw new IllegalArgumentException("Field " + i2 + " doesn't exist!");
        }

        @Override // com.umeng.analytics.pro.bx
        public String b() {
            return this.f22533f;
        }

        public static EnumC0184e a(String str) {
            return f22530d.get(str);
        }

        @Override // com.umeng.analytics.pro.bx
        public short a() {
            return this.f22532e;
        }
    }

    public e a(String str) {
        this.f22522a = str;
        return this;
    }

    public void b(boolean z2) {
        this.f22525l = bn.a(this.f22525l, 0, z2);
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f22524c = null;
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f22522a = null;
    }

    public e b(String str) {
        this.f22524c = str;
        return this;
    }

    public e(long j2, String str) {
        this();
        this.f22523b = j2;
        b(true);
        this.f22524c = str;
    }

    public e a(long j2) {
        this.f22523b = j2;
        b(true);
        return this;
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public EnumC0184e fieldForId(int i2) {
        return EnumC0184e.a(i2);
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new cj(new db(objectOutputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public e(e eVar) {
        this.f22525l = (byte) 0;
        this.f22526m = new EnumC0184e[]{EnumC0184e.VALUE, EnumC0184e.TS, EnumC0184e.GUID};
        this.f22525l = eVar.f22525l;
        if (eVar.d()) {
            this.f22522a = eVar.f22522a;
        }
        this.f22523b = eVar.f22523b;
        if (eVar.j()) {
            this.f22524c = eVar.f22524c;
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f22525l = (byte) 0;
            read(new cj(new db(objectInputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
