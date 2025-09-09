package com.umeng.analytics.pro;

import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class bd implements bq<bd, e>, Serializable, Cloneable {
    private static final int A = 2;
    private static final int B = 3;

    /* renamed from: k, reason: collision with root package name */
    public static final Map<e, cc> f21428k;

    /* renamed from: l, reason: collision with root package name */
    private static final long f21429l = 420342210744516016L;

    /* renamed from: m, reason: collision with root package name */
    private static final cu f21430m = new cu("UMEnvelope");

    /* renamed from: n, reason: collision with root package name */
    private static final ck f21431n = new ck("version", (byte) 11, 1);

    /* renamed from: o, reason: collision with root package name */
    private static final ck f21432o = new ck("address", (byte) 11, 2);

    /* renamed from: p, reason: collision with root package name */
    private static final ck f21433p = new ck(com.umeng.ccg.a.f22022x, (byte) 11, 3);

    /* renamed from: q, reason: collision with root package name */
    private static final ck f21434q = new ck("serial_num", (byte) 8, 4);

    /* renamed from: r, reason: collision with root package name */
    private static final ck f21435r = new ck("ts_secs", (byte) 8, 5);

    /* renamed from: s, reason: collision with root package name */
    private static final ck f21436s = new ck(SessionDescription.ATTR_LENGTH, (byte) 8, 6);

    /* renamed from: t, reason: collision with root package name */
    private static final ck f21437t = new ck("entity", (byte) 11, 7);

    /* renamed from: u, reason: collision with root package name */
    private static final ck f21438u = new ck("guid", (byte) 11, 8);

    /* renamed from: v, reason: collision with root package name */
    private static final ck f21439v = new ck("checksum", (byte) 11, 9);

    /* renamed from: w, reason: collision with root package name */
    private static final ck f21440w = new ck("codex", (byte) 8, 10);

    /* renamed from: x, reason: collision with root package name */
    private static final Map<Class<? extends cx>, cy> f21441x;

    /* renamed from: y, reason: collision with root package name */
    private static final int f21442y = 0;

    /* renamed from: z, reason: collision with root package name */
    private static final int f21443z = 1;
    private byte C;
    private e[] D;

    /* renamed from: a, reason: collision with root package name */
    public String f21444a;

    /* renamed from: b, reason: collision with root package name */
    public String f21445b;

    /* renamed from: c, reason: collision with root package name */
    public String f21446c;

    /* renamed from: d, reason: collision with root package name */
    public int f21447d;

    /* renamed from: e, reason: collision with root package name */
    public int f21448e;

    /* renamed from: f, reason: collision with root package name */
    public int f21449f;

    /* renamed from: g, reason: collision with root package name */
    public ByteBuffer f21450g;

    /* renamed from: h, reason: collision with root package name */
    public String f21451h;

    /* renamed from: i, reason: collision with root package name */
    public String f21452i;

    /* renamed from: j, reason: collision with root package name */
    public int f21453j;

    private static class a extends cz<bd> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(cp cpVar, bd bdVar) throws bw {
            cpVar.j();
            while (true) {
                ck ckVarL = cpVar.l();
                byte b2 = ckVarL.f21619b;
                if (b2 == 0) {
                    cpVar.k();
                    if (!bdVar.m()) {
                        throw new cq("Required field 'serial_num' was not found in serialized data! Struct: " + toString());
                    }
                    if (!bdVar.p()) {
                        throw new cq("Required field 'ts_secs' was not found in serialized data! Struct: " + toString());
                    }
                    if (bdVar.s()) {
                        bdVar.G();
                        return;
                    }
                    throw new cq("Required field 'length' was not found in serialized data! Struct: " + toString());
                }
                switch (ckVarL.f21620c) {
                    case 1:
                        if (b2 != 11) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21444a = cpVar.z();
                            bdVar.a(true);
                            break;
                        }
                    case 2:
                        if (b2 != 11) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21445b = cpVar.z();
                            bdVar.b(true);
                            break;
                        }
                    case 3:
                        if (b2 != 11) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21446c = cpVar.z();
                            bdVar.c(true);
                            break;
                        }
                    case 4:
                        if (b2 != 8) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21447d = cpVar.w();
                            bdVar.d(true);
                            break;
                        }
                    case 5:
                        if (b2 != 8) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21448e = cpVar.w();
                            bdVar.e(true);
                            break;
                        }
                    case 6:
                        if (b2 != 8) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21449f = cpVar.w();
                            bdVar.f(true);
                            break;
                        }
                    case 7:
                        if (b2 != 11) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21450g = cpVar.A();
                            bdVar.g(true);
                            break;
                        }
                    case 8:
                        if (b2 != 11) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21451h = cpVar.z();
                            bdVar.h(true);
                            break;
                        }
                    case 9:
                        if (b2 != 11) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21452i = cpVar.z();
                            bdVar.i(true);
                            break;
                        }
                    case 10:
                        if (b2 != 8) {
                            cs.a(cpVar, b2);
                            break;
                        } else {
                            bdVar.f21453j = cpVar.w();
                            bdVar.j(true);
                            break;
                        }
                    default:
                        cs.a(cpVar, b2);
                        break;
                }
                cpVar.m();
            }
        }

        @Override // com.umeng.analytics.pro.cx
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(cp cpVar, bd bdVar) throws bw {
            bdVar.G();
            cpVar.a(bd.f21430m);
            if (bdVar.f21444a != null) {
                cpVar.a(bd.f21431n);
                cpVar.a(bdVar.f21444a);
                cpVar.c();
            }
            if (bdVar.f21445b != null) {
                cpVar.a(bd.f21432o);
                cpVar.a(bdVar.f21445b);
                cpVar.c();
            }
            if (bdVar.f21446c != null) {
                cpVar.a(bd.f21433p);
                cpVar.a(bdVar.f21446c);
                cpVar.c();
            }
            cpVar.a(bd.f21434q);
            cpVar.a(bdVar.f21447d);
            cpVar.c();
            cpVar.a(bd.f21435r);
            cpVar.a(bdVar.f21448e);
            cpVar.c();
            cpVar.a(bd.f21436s);
            cpVar.a(bdVar.f21449f);
            cpVar.c();
            if (bdVar.f21450g != null) {
                cpVar.a(bd.f21437t);
                cpVar.a(bdVar.f21450g);
                cpVar.c();
            }
            if (bdVar.f21451h != null) {
                cpVar.a(bd.f21438u);
                cpVar.a(bdVar.f21451h);
                cpVar.c();
            }
            if (bdVar.f21452i != null) {
                cpVar.a(bd.f21439v);
                cpVar.a(bdVar.f21452i);
                cpVar.c();
            }
            if (bdVar.F()) {
                cpVar.a(bd.f21440w);
                cpVar.a(bdVar.f21453j);
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

    private static class c extends da<bd> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cx
        public void a(cp cpVar, bd bdVar) throws bw {
            cv cvVar = (cv) cpVar;
            cvVar.a(bdVar.f21444a);
            cvVar.a(bdVar.f21445b);
            cvVar.a(bdVar.f21446c);
            cvVar.a(bdVar.f21447d);
            cvVar.a(bdVar.f21448e);
            cvVar.a(bdVar.f21449f);
            cvVar.a(bdVar.f21450g);
            cvVar.a(bdVar.f21451h);
            cvVar.a(bdVar.f21452i);
            BitSet bitSet = new BitSet();
            if (bdVar.F()) {
                bitSet.set(0);
            }
            cvVar.a(bitSet, 1);
            if (bdVar.F()) {
                cvVar.a(bdVar.f21453j);
            }
        }

        @Override // com.umeng.analytics.pro.cx
        public void b(cp cpVar, bd bdVar) throws bw {
            cv cvVar = (cv) cpVar;
            bdVar.f21444a = cvVar.z();
            bdVar.a(true);
            bdVar.f21445b = cvVar.z();
            bdVar.b(true);
            bdVar.f21446c = cvVar.z();
            bdVar.c(true);
            bdVar.f21447d = cvVar.w();
            bdVar.d(true);
            bdVar.f21448e = cvVar.w();
            bdVar.e(true);
            bdVar.f21449f = cvVar.w();
            bdVar.f(true);
            bdVar.f21450g = cvVar.A();
            bdVar.g(true);
            bdVar.f21451h = cvVar.z();
            bdVar.h(true);
            bdVar.f21452i = cvVar.z();
            bdVar.i(true);
            if (cvVar.b(1).get(0)) {
                bdVar.f21453j = cvVar.w();
                bdVar.j(true);
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
        f21441x = map;
        map.put(cz.class, new b());
        map.put(da.class, new d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.VERSION, (e) new cc("version", (byte) 1, new cd((byte) 11)));
        enumMap.put((EnumMap) e.ADDRESS, (e) new cc("address", (byte) 1, new cd((byte) 11)));
        enumMap.put((EnumMap) e.SIGNATURE, (e) new cc(com.umeng.ccg.a.f22022x, (byte) 1, new cd((byte) 11)));
        enumMap.put((EnumMap) e.SERIAL_NUM, (e) new cc("serial_num", (byte) 1, new cd((byte) 8)));
        enumMap.put((EnumMap) e.TS_SECS, (e) new cc("ts_secs", (byte) 1, new cd((byte) 8)));
        enumMap.put((EnumMap) e.LENGTH, (e) new cc(SessionDescription.ATTR_LENGTH, (byte) 1, new cd((byte) 8)));
        enumMap.put((EnumMap) e.ENTITY, (e) new cc("entity", (byte) 1, new cd((byte) 11, true)));
        enumMap.put((EnumMap) e.GUID, (e) new cc("guid", (byte) 1, new cd((byte) 11)));
        enumMap.put((EnumMap) e.CHECKSUM, (e) new cc("checksum", (byte) 1, new cd((byte) 11)));
        enumMap.put((EnumMap) e.CODEX, (e) new cc("codex", (byte) 2, new cd((byte) 8)));
        Map<e, cc> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f21428k = mapUnmodifiableMap;
        cc.a(bd.class, mapUnmodifiableMap);
    }

    public bd() {
        this.C = (byte) 0;
        this.D = new e[]{e.CODEX};
    }

    public String A() {
        return this.f21452i;
    }

    public void B() {
        this.f21452i = null;
    }

    public boolean C() {
        return this.f21452i != null;
    }

    public int D() {
        return this.f21453j;
    }

    public void E() {
        this.C = bn.b(this.C, 3);
    }

    public boolean F() {
        return bn.a(this.C, 3);
    }

    public void G() throws bw {
        if (this.f21444a == null) {
            throw new cq("Required field 'version' was not present! Struct: " + toString());
        }
        if (this.f21445b == null) {
            throw new cq("Required field 'address' was not present! Struct: " + toString());
        }
        if (this.f21446c == null) {
            throw new cq("Required field 'signature' was not present! Struct: " + toString());
        }
        if (this.f21450g == null) {
            throw new cq("Required field 'entity' was not present! Struct: " + toString());
        }
        if (this.f21451h == null) {
            throw new cq("Required field 'guid' was not present! Struct: " + toString());
        }
        if (this.f21452i != null) {
            return;
        }
        throw new cq("Required field 'checksum' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public bd deepCopy() {
        return new bd(this);
    }

    public String b() {
        return this.f21444a;
    }

    public void c() {
        this.f21444a = null;
    }

    @Override // com.umeng.analytics.pro.bq
    public void clear() {
        this.f21444a = null;
        this.f21445b = null;
        this.f21446c = null;
        d(false);
        this.f21447d = 0;
        e(false);
        this.f21448e = 0;
        f(false);
        this.f21449f = 0;
        this.f21450g = null;
        this.f21451h = null;
        this.f21452i = null;
        j(false);
        this.f21453j = 0;
    }

    public boolean d() {
        return this.f21444a != null;
    }

    public String e() {
        return this.f21445b;
    }

    public void f() {
        this.f21445b = null;
    }

    public boolean g() {
        return this.f21445b != null;
    }

    public String h() {
        return this.f21446c;
    }

    public void i() {
        this.f21446c = null;
    }

    public boolean j() {
        return this.f21446c != null;
    }

    public int k() {
        return this.f21447d;
    }

    public void l() {
        this.C = bn.b(this.C, 0);
    }

    public boolean m() {
        return bn.a(this.C, 0);
    }

    public int n() {
        return this.f21448e;
    }

    public void o() {
        this.C = bn.b(this.C, 1);
    }

    public boolean p() {
        return bn.a(this.C, 1);
    }

    public int q() {
        return this.f21449f;
    }

    public void r() {
        this.C = bn.b(this.C, 2);
    }

    @Override // com.umeng.analytics.pro.bq
    public void read(cp cpVar) throws bw {
        f21441x.get(cpVar.D()).b().b(cpVar, this);
    }

    public boolean s() {
        return bn.a(this.C, 2);
    }

    public byte[] t() {
        a(br.c(this.f21450g));
        ByteBuffer byteBuffer = this.f21450g;
        if (byteBuffer == null) {
            return null;
        }
        return byteBuffer.array();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UMEnvelope(");
        sb.append("version:");
        String str = this.f21444a;
        if (str == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("address:");
        String str2 = this.f21445b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("signature:");
        String str3 = this.f21446c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("serial_num:");
        sb.append(this.f21447d);
        sb.append(", ");
        sb.append("ts_secs:");
        sb.append(this.f21448e);
        sb.append(", ");
        sb.append("length:");
        sb.append(this.f21449f);
        sb.append(", ");
        sb.append("entity:");
        ByteBuffer byteBuffer = this.f21450g;
        if (byteBuffer == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            br.a(byteBuffer, sb);
        }
        sb.append(", ");
        sb.append("guid:");
        String str4 = this.f21451h;
        if (str4 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str4);
        }
        sb.append(", ");
        sb.append("checksum:");
        String str5 = this.f21452i;
        if (str5 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str5);
        }
        if (F()) {
            sb.append(", ");
            sb.append("codex:");
            sb.append(this.f21453j);
        }
        sb.append(")");
        return sb.toString();
    }

    public ByteBuffer u() {
        return this.f21450g;
    }

    public void v() {
        this.f21450g = null;
    }

    public boolean w() {
        return this.f21450g != null;
    }

    @Override // com.umeng.analytics.pro.bq
    public void write(cp cpVar) throws bw {
        f21441x.get(cpVar.D()).b().a(cpVar, this);
    }

    public String x() {
        return this.f21451h;
    }

    public void y() {
        this.f21451h = null;
    }

    public boolean z() {
        return this.f21451h != null;
    }

    public enum e implements bx {
        VERSION(1, "version"),
        ADDRESS(2, "address"),
        SIGNATURE(3, com.umeng.ccg.a.f22022x),
        SERIAL_NUM(4, "serial_num"),
        TS_SECS(5, "ts_secs"),
        LENGTH(6, SessionDescription.ATTR_LENGTH),
        ENTITY(7, "entity"),
        GUID(8, "guid"),
        CHECKSUM(9, "checksum"),
        CODEX(10, "codex");


        /* renamed from: k, reason: collision with root package name */
        private static final Map<String, e> f21464k = new HashMap();

        /* renamed from: l, reason: collision with root package name */
        private final short f21466l;

        /* renamed from: m, reason: collision with root package name */
        private final String f21467m;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f21464k.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f21466l = s2;
            this.f21467m = str;
        }

        public static e a(int i2) {
            switch (i2) {
                case 1:
                    return VERSION;
                case 2:
                    return ADDRESS;
                case 3:
                    return SIGNATURE;
                case 4:
                    return SERIAL_NUM;
                case 5:
                    return TS_SECS;
                case 6:
                    return LENGTH;
                case 7:
                    return ENTITY;
                case 8:
                    return GUID;
                case 9:
                    return CHECKSUM;
                case 10:
                    return CODEX;
                default:
                    return null;
            }
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
            return this.f21467m;
        }

        public static e a(String str) {
            return f21464k.get(str);
        }

        @Override // com.umeng.analytics.pro.bx
        public short a() {
            return this.f21466l;
        }
    }

    public bd a(String str) {
        this.f21444a = str;
        return this;
    }

    public bd b(String str) {
        this.f21445b = str;
        return this;
    }

    public bd c(String str) {
        this.f21446c = str;
        return this;
    }

    public void d(boolean z2) {
        this.C = bn.a(this.C, 0, z2);
    }

    public void e(boolean z2) {
        this.C = bn.a(this.C, 1, z2);
    }

    public void f(boolean z2) {
        this.C = bn.a(this.C, 2, z2);
    }

    public void g(boolean z2) {
        if (z2) {
            return;
        }
        this.f21450g = null;
    }

    public void h(boolean z2) {
        if (z2) {
            return;
        }
        this.f21451h = null;
    }

    public void i(boolean z2) {
        if (z2) {
            return;
        }
        this.f21452i = null;
    }

    public void j(boolean z2) {
        this.C = bn.a(this.C, 3, z2);
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f21444a = null;
    }

    public void b(boolean z2) {
        if (z2) {
            return;
        }
        this.f21445b = null;
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f21446c = null;
    }

    public bd d(String str) {
        this.f21451h = str;
        return this;
    }

    public bd e(String str) {
        this.f21452i = str;
        return this;
    }

    public bd(String str, String str2, String str3, int i2, int i3, int i4, ByteBuffer byteBuffer, String str4, String str5) {
        this();
        this.f21444a = str;
        this.f21445b = str2;
        this.f21446c = str3;
        this.f21447d = i2;
        d(true);
        this.f21448e = i3;
        e(true);
        this.f21449f = i4;
        f(true);
        this.f21450g = byteBuffer;
        this.f21451h = str4;
        this.f21452i = str5;
    }

    public bd a(int i2) {
        this.f21447d = i2;
        d(true);
        return this;
    }

    public bd b(int i2) {
        this.f21448e = i2;
        e(true);
        return this;
    }

    public bd c(int i2) {
        this.f21449f = i2;
        f(true);
        return this;
    }

    public bd d(int i2) {
        this.f21453j = i2;
        j(true);
        return this;
    }

    @Override // com.umeng.analytics.pro.bq
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public e fieldForId(int i2) {
        return e.a(i2);
    }

    public bd a(byte[] bArr) {
        a(bArr == null ? null : ByteBuffer.wrap(bArr));
        return this;
    }

    public bd a(ByteBuffer byteBuffer) {
        this.f21450g = byteBuffer;
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
            this.C = (byte) 0;
            read(new cj(new db(objectInputStream)));
        } catch (bw e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public bd(bd bdVar) {
        this.C = (byte) 0;
        this.D = new e[]{e.CODEX};
        this.C = bdVar.C;
        if (bdVar.d()) {
            this.f21444a = bdVar.f21444a;
        }
        if (bdVar.g()) {
            this.f21445b = bdVar.f21445b;
        }
        if (bdVar.j()) {
            this.f21446c = bdVar.f21446c;
        }
        this.f21447d = bdVar.f21447d;
        this.f21448e = bdVar.f21448e;
        this.f21449f = bdVar.f21449f;
        if (bdVar.w()) {
            this.f21450g = br.d(bdVar.f21450g);
        }
        if (bdVar.z()) {
            this.f21451h = bdVar.f21451h;
        }
        if (bdVar.C()) {
            this.f21452i = bdVar.f21452i;
        }
        this.f21453j = bdVar.f21453j;
    }
}
