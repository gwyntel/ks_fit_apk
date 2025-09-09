package com.meizu.cloud.pushsdk.e.b;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import com.alibaba.cloudapi.sdk.constant.HttpConstant;
import com.alipay.sdk.m.u.i;
import com.meizu.cloud.pushsdk.e.b.b;
import com.meizu.cloud.pushsdk.e.d.b;
import com.meizu.cloud.pushsdk.e.d.c;
import com.meizu.cloud.pushsdk.e.d.f;
import com.meizu.cloud.pushsdk.e.d.g;
import com.meizu.cloud.pushsdk.e.d.h;
import com.meizu.cloud.pushsdk.e.d.j;
import com.meizu.cloud.pushsdk.e.d.k;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b<T extends b> {

    /* renamed from: a, reason: collision with root package name */
    private static final g f19278a = g.a(HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON);

    /* renamed from: b, reason: collision with root package name */
    private static final g f19279b = g.a("text/x-markdown; charset=utf-8");

    /* renamed from: c, reason: collision with root package name */
    private static final Object f19280c = new Object();
    private int A;
    private boolean B;
    private int C;
    private com.meizu.cloud.pushsdk.e.e.a D;
    private Bitmap.Config E;
    private int F;
    private int G;
    private ImageView.ScaleType H;
    private final Executor I;
    private String J;
    private Type K;

    /* renamed from: d, reason: collision with root package name */
    private final int f19281d;

    /* renamed from: e, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.e.b.d f19282e;

    /* renamed from: f, reason: collision with root package name */
    private final int f19283f;

    /* renamed from: g, reason: collision with root package name */
    private final String f19284g;

    /* renamed from: h, reason: collision with root package name */
    private int f19285h;

    /* renamed from: i, reason: collision with root package name */
    private final Object f19286i;

    /* renamed from: j, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.e.b.e f19287j;

    /* renamed from: k, reason: collision with root package name */
    private final HashMap<String, String> f19288k;

    /* renamed from: l, reason: collision with root package name */
    private HashMap<String, String> f19289l;

    /* renamed from: m, reason: collision with root package name */
    private HashMap<String, String> f19290m;

    /* renamed from: n, reason: collision with root package name */
    private HashMap<String, String> f19291n;

    /* renamed from: o, reason: collision with root package name */
    private final HashMap<String, String> f19292o;

    /* renamed from: p, reason: collision with root package name */
    private final HashMap<String, String> f19293p;

    /* renamed from: q, reason: collision with root package name */
    private HashMap<String, File> f19294q;

    /* renamed from: r, reason: collision with root package name */
    private String f19295r;

    /* renamed from: s, reason: collision with root package name */
    private String f19296s;

    /* renamed from: t, reason: collision with root package name */
    private JSONObject f19297t;

    /* renamed from: u, reason: collision with root package name */
    private JSONArray f19298u;

    /* renamed from: v, reason: collision with root package name */
    private String f19299v;

    /* renamed from: w, reason: collision with root package name */
    private byte[] f19300w;

    /* renamed from: x, reason: collision with root package name */
    private File f19301x;

    /* renamed from: y, reason: collision with root package name */
    private g f19302y;

    /* renamed from: z, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.e.d.a f19303z;

    class a implements com.meizu.cloud.pushsdk.e.e.a {
        a() {
        }

        @Override // com.meizu.cloud.pushsdk.e.e.a
        public void a(long j2, long j3) {
            b.this.A = (int) ((100 * j2) / j3);
            if (b.this.D == null || b.this.B) {
                return;
            }
            b.this.D.a(j2, j3);
        }
    }

    /* renamed from: com.meizu.cloud.pushsdk.e.b.b$b, reason: collision with other inner class name */
    static /* synthetic */ class C0152b {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f19305a;

        static {
            int[] iArr = new int[com.meizu.cloud.pushsdk.e.b.e.values().length];
            f19305a = iArr;
            try {
                iArr[com.meizu.cloud.pushsdk.e.b.e.JSON_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f19305a[com.meizu.cloud.pushsdk.e.b.e.JSON_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f19305a[com.meizu.cloud.pushsdk.e.b.e.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f19305a[com.meizu.cloud.pushsdk.e.b.e.BITMAP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f19305a[com.meizu.cloud.pushsdk.e.b.e.PREFETCH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static class c<T extends c> {

        /* renamed from: b, reason: collision with root package name */
        private final String f19307b;

        /* renamed from: c, reason: collision with root package name */
        private Object f19308c;

        /* renamed from: g, reason: collision with root package name */
        private final String f19312g;

        /* renamed from: h, reason: collision with root package name */
        private final String f19313h;

        /* renamed from: j, reason: collision with root package name */
        private Executor f19315j;

        /* renamed from: k, reason: collision with root package name */
        private String f19316k;

        /* renamed from: a, reason: collision with root package name */
        private com.meizu.cloud.pushsdk.e.b.d f19306a = com.meizu.cloud.pushsdk.e.b.d.MEDIUM;

        /* renamed from: d, reason: collision with root package name */
        private final HashMap<String, String> f19309d = new HashMap<>();

        /* renamed from: e, reason: collision with root package name */
        private final HashMap<String, String> f19310e = new HashMap<>();

        /* renamed from: f, reason: collision with root package name */
        private final HashMap<String, String> f19311f = new HashMap<>();

        /* renamed from: i, reason: collision with root package name */
        private int f19314i = 0;

        public c(String str, String str2, String str3) {
            this.f19307b = str;
            this.f19312g = str2;
            this.f19313h = str3;
        }

        public b a() {
            return new b(this);
        }
    }

    public static class d<T extends d> {

        /* renamed from: c, reason: collision with root package name */
        private final String f19319c;

        /* renamed from: d, reason: collision with root package name */
        private Object f19320d;

        /* renamed from: e, reason: collision with root package name */
        private Bitmap.Config f19321e;

        /* renamed from: f, reason: collision with root package name */
        private int f19322f;

        /* renamed from: g, reason: collision with root package name */
        private int f19323g;

        /* renamed from: h, reason: collision with root package name */
        private ImageView.ScaleType f19324h;

        /* renamed from: l, reason: collision with root package name */
        private Executor f19328l;

        /* renamed from: m, reason: collision with root package name */
        private String f19329m;

        /* renamed from: a, reason: collision with root package name */
        private com.meizu.cloud.pushsdk.e.b.d f19317a = com.meizu.cloud.pushsdk.e.b.d.MEDIUM;

        /* renamed from: i, reason: collision with root package name */
        private final HashMap<String, String> f19325i = new HashMap<>();

        /* renamed from: j, reason: collision with root package name */
        private final HashMap<String, String> f19326j = new HashMap<>();

        /* renamed from: k, reason: collision with root package name */
        private final HashMap<String, String> f19327k = new HashMap<>();

        /* renamed from: b, reason: collision with root package name */
        private final int f19318b = 0;

        public d(String str) {
            this.f19319c = str;
        }

        public T a(HashMap<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.f19326j.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public static class e<T extends e> {

        /* renamed from: b, reason: collision with root package name */
        private final String f19331b;

        /* renamed from: c, reason: collision with root package name */
        private Object f19332c;

        /* renamed from: j, reason: collision with root package name */
        private Executor f19339j;

        /* renamed from: k, reason: collision with root package name */
        private String f19340k;

        /* renamed from: l, reason: collision with root package name */
        private String f19341l;

        /* renamed from: a, reason: collision with root package name */
        private com.meizu.cloud.pushsdk.e.b.d f19330a = com.meizu.cloud.pushsdk.e.b.d.MEDIUM;

        /* renamed from: d, reason: collision with root package name */
        private final HashMap<String, String> f19333d = new HashMap<>();

        /* renamed from: e, reason: collision with root package name */
        private final HashMap<String, String> f19334e = new HashMap<>();

        /* renamed from: f, reason: collision with root package name */
        private final HashMap<String, String> f19335f = new HashMap<>();

        /* renamed from: g, reason: collision with root package name */
        private final HashMap<String, String> f19336g = new HashMap<>();

        /* renamed from: h, reason: collision with root package name */
        private final HashMap<String, File> f19337h = new HashMap<>();

        /* renamed from: i, reason: collision with root package name */
        private int f19338i = 0;

        public e(String str) {
            this.f19331b = str;
        }

        public T a(String str, File file) {
            this.f19337h.put(str, file);
            return this;
        }

        public T a(HashMap<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.f19334e.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public static class f<T extends f> {

        /* renamed from: c, reason: collision with root package name */
        private final String f19344c;

        /* renamed from: d, reason: collision with root package name */
        private Object f19345d;

        /* renamed from: o, reason: collision with root package name */
        private Executor f19356o;

        /* renamed from: p, reason: collision with root package name */
        private String f19357p;

        /* renamed from: q, reason: collision with root package name */
        private String f19358q;

        /* renamed from: a, reason: collision with root package name */
        private com.meizu.cloud.pushsdk.e.b.d f19342a = com.meizu.cloud.pushsdk.e.b.d.MEDIUM;

        /* renamed from: e, reason: collision with root package name */
        private JSONObject f19346e = null;

        /* renamed from: f, reason: collision with root package name */
        private JSONArray f19347f = null;

        /* renamed from: g, reason: collision with root package name */
        private String f19348g = null;

        /* renamed from: h, reason: collision with root package name */
        private byte[] f19349h = null;

        /* renamed from: i, reason: collision with root package name */
        private File f19350i = null;

        /* renamed from: j, reason: collision with root package name */
        private final HashMap<String, String> f19351j = new HashMap<>();

        /* renamed from: k, reason: collision with root package name */
        private final HashMap<String, String> f19352k = new HashMap<>();

        /* renamed from: l, reason: collision with root package name */
        private final HashMap<String, String> f19353l = new HashMap<>();

        /* renamed from: m, reason: collision with root package name */
        private final HashMap<String, String> f19354m = new HashMap<>();

        /* renamed from: n, reason: collision with root package name */
        private final HashMap<String, String> f19355n = new HashMap<>();

        /* renamed from: b, reason: collision with root package name */
        private final int f19343b = 1;

        public f(String str) {
            this.f19344c = str;
        }

        public T a(HashMap<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.f19352k.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public b(c cVar) {
        this.f19289l = new HashMap<>();
        this.f19290m = new HashMap<>();
        this.f19291n = new HashMap<>();
        this.f19294q = new HashMap<>();
        this.f19297t = null;
        this.f19298u = null;
        this.f19299v = null;
        this.f19300w = null;
        this.f19301x = null;
        this.f19302y = null;
        this.C = 0;
        this.K = null;
        this.f19283f = 1;
        this.f19281d = 0;
        this.f19282e = cVar.f19306a;
        this.f19284g = cVar.f19307b;
        this.f19286i = cVar.f19308c;
        this.f19295r = cVar.f19312g;
        this.f19296s = cVar.f19313h;
        this.f19288k = cVar.f19309d;
        this.f19292o = cVar.f19310e;
        this.f19293p = cVar.f19311f;
        this.C = cVar.f19314i;
        this.I = cVar.f19315j;
        this.J = cVar.f19316k;
    }

    public com.meizu.cloud.pushsdk.e.b.c b() {
        return com.meizu.cloud.pushsdk.e.f.c.a(this);
    }

    public com.meizu.cloud.pushsdk.e.b.c c() {
        this.f19287j = com.meizu.cloud.pushsdk.e.b.e.JSON_OBJECT;
        return com.meizu.cloud.pushsdk.e.f.c.a(this);
    }

    public com.meizu.cloud.pushsdk.e.b.c d() {
        this.f19287j = com.meizu.cloud.pushsdk.e.b.e.STRING;
        return com.meizu.cloud.pushsdk.e.f.c.a(this);
    }

    public com.meizu.cloud.pushsdk.e.d.a e() {
        return this.f19303z;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String f() {
        return this.f19295r;
    }

    public String g() {
        return this.f19296s;
    }

    public com.meizu.cloud.pushsdk.e.d.c h() {
        c.b bVar = new c.b();
        try {
            for (Map.Entry<String, String> entry : this.f19288k.entrySet()) {
                bVar.a(entry.getKey(), entry.getValue());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return bVar.a();
    }

    public int hashCode() {
        return super.hashCode();
    }

    public int i() {
        return this.f19281d;
    }

    public j j() {
        h.a aVarA = new h.a().a(h.f19422e);
        try {
            for (Map.Entry<String, String> entry : this.f19291n.entrySet()) {
                aVarA.a(com.meizu.cloud.pushsdk.e.d.c.a("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), j.a((g) null, entry.getValue()));
            }
            for (Map.Entry<String, File> entry2 : this.f19294q.entrySet()) {
                if (entry2.getValue() != null) {
                    String name = entry2.getValue().getName();
                    aVarA.a(com.meizu.cloud.pushsdk.e.d.c.a("Content-Disposition", "form-data; name=\"" + entry2.getKey() + "\"; filename=\"" + name + "\""), j.a(g.a(com.meizu.cloud.pushsdk.e.i.b.a(name)), entry2.getValue()));
                    g gVar = this.f19302y;
                    if (gVar != null) {
                        aVarA.a(gVar);
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return aVarA.a();
    }

    public j k() {
        JSONObject jSONObject = this.f19297t;
        if (jSONObject != null) {
            g gVar = this.f19302y;
            return gVar != null ? j.a(gVar, jSONObject.toString()) : j.a(f19278a, jSONObject.toString());
        }
        JSONArray jSONArray = this.f19298u;
        if (jSONArray != null) {
            g gVar2 = this.f19302y;
            return gVar2 != null ? j.a(gVar2, jSONArray.toString()) : j.a(f19278a, jSONArray.toString());
        }
        String str = this.f19299v;
        if (str != null) {
            g gVar3 = this.f19302y;
            return gVar3 != null ? j.a(gVar3, str) : j.a(f19279b, str);
        }
        File file = this.f19301x;
        if (file != null) {
            g gVar4 = this.f19302y;
            return gVar4 != null ? j.a(gVar4, file) : j.a(f19279b, file);
        }
        byte[] bArr = this.f19300w;
        if (bArr != null) {
            g gVar5 = this.f19302y;
            return gVar5 != null ? j.a(gVar5, bArr) : j.a(f19279b, bArr);
        }
        b.C0153b c0153b = new b.C0153b();
        try {
            for (Map.Entry<String, String> entry : this.f19289l.entrySet()) {
                if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                    c0153b.a(entry.getKey(), entry.getValue());
                }
            }
            for (Map.Entry<String, String> entry2 : this.f19290m.entrySet()) {
                if (!TextUtils.isEmpty(entry2.getKey()) && !TextUtils.isEmpty(entry2.getValue())) {
                    c0153b.b(entry2.getKey(), entry2.getValue());
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return c0153b.a();
    }

    public int l() {
        return this.f19283f;
    }

    public com.meizu.cloud.pushsdk.e.b.e m() {
        return this.f19287j;
    }

    public com.meizu.cloud.pushsdk.e.e.a n() {
        return new a();
    }

    public String o() {
        String strReplace = this.f19284g;
        for (Map.Entry<String, String> entry : this.f19293p.entrySet()) {
            strReplace = strReplace.replace("{" + entry.getKey() + i.f9804d, String.valueOf(entry.getValue()));
        }
        f.b bVarF = com.meizu.cloud.pushsdk.e.d.f.b(strReplace).f();
        for (Map.Entry<String, String> entry2 : this.f19292o.entrySet()) {
            bVarF.a(entry2.getKey(), entry2.getValue());
        }
        return bVarF.a().toString();
    }

    public String p() {
        return this.J;
    }

    public String toString() {
        return "ANRequest{sequenceNumber='" + this.f19285h + ", mMethod=" + this.f19281d + ", mPriority=" + this.f19282e + ", mRequestType=" + this.f19283f + ", mUrl=" + this.f19284g + '}';
    }

    public b(d dVar) {
        this.f19289l = new HashMap<>();
        this.f19290m = new HashMap<>();
        this.f19291n = new HashMap<>();
        this.f19294q = new HashMap<>();
        this.f19297t = null;
        this.f19298u = null;
        this.f19299v = null;
        this.f19300w = null;
        this.f19301x = null;
        this.f19302y = null;
        this.C = 0;
        this.K = null;
        this.f19283f = 0;
        this.f19281d = dVar.f19318b;
        this.f19282e = dVar.f19317a;
        this.f19284g = dVar.f19319c;
        this.f19286i = dVar.f19320d;
        this.f19288k = dVar.f19325i;
        this.E = dVar.f19321e;
        this.G = dVar.f19323g;
        this.F = dVar.f19322f;
        this.H = dVar.f19324h;
        this.f19292o = dVar.f19326j;
        this.f19293p = dVar.f19327k;
        this.I = dVar.f19328l;
        this.J = dVar.f19329m;
    }

    public com.meizu.cloud.pushsdk.e.b.c a() {
        this.f19287j = com.meizu.cloud.pushsdk.e.b.e.BITMAP;
        return com.meizu.cloud.pushsdk.e.f.c.a(this);
    }

    public b(e eVar) {
        this.f19289l = new HashMap<>();
        this.f19290m = new HashMap<>();
        this.f19291n = new HashMap<>();
        this.f19294q = new HashMap<>();
        this.f19297t = null;
        this.f19298u = null;
        this.f19299v = null;
        this.f19300w = null;
        this.f19301x = null;
        this.f19302y = null;
        this.C = 0;
        this.K = null;
        this.f19283f = 2;
        this.f19281d = 1;
        this.f19282e = eVar.f19330a;
        this.f19284g = eVar.f19331b;
        this.f19286i = eVar.f19332c;
        this.f19288k = eVar.f19333d;
        this.f19292o = eVar.f19335f;
        this.f19293p = eVar.f19336g;
        this.f19291n = eVar.f19334e;
        this.f19294q = eVar.f19337h;
        this.C = eVar.f19338i;
        this.I = eVar.f19339j;
        this.J = eVar.f19340k;
        if (eVar.f19341l != null) {
            this.f19302y = g.a(eVar.f19341l);
        }
    }

    public com.meizu.cloud.pushsdk.e.b.c a(k kVar) {
        com.meizu.cloud.pushsdk.e.b.c<Bitmap> cVarA;
        int i2 = C0152b.f19305a[this.f19287j.ordinal()];
        if (i2 == 1) {
            try {
                return com.meizu.cloud.pushsdk.e.b.c.a(new JSONArray(com.meizu.cloud.pushsdk.e.h.g.a(kVar.a().f()).a()));
            } catch (Exception e2) {
                return com.meizu.cloud.pushsdk.e.b.c.a(com.meizu.cloud.pushsdk.e.i.b.b(new com.meizu.cloud.pushsdk.e.c.a(e2)));
            }
        }
        if (i2 == 2) {
            try {
                return com.meizu.cloud.pushsdk.e.b.c.a(new JSONObject(com.meizu.cloud.pushsdk.e.h.g.a(kVar.a().f()).a()));
            } catch (Exception e3) {
                return com.meizu.cloud.pushsdk.e.b.c.a(com.meizu.cloud.pushsdk.e.i.b.b(new com.meizu.cloud.pushsdk.e.c.a(e3)));
            }
        }
        if (i2 == 3) {
            try {
                return com.meizu.cloud.pushsdk.e.b.c.a(com.meizu.cloud.pushsdk.e.h.g.a(kVar.a().f()).a());
            } catch (Exception e4) {
                return com.meizu.cloud.pushsdk.e.b.c.a(com.meizu.cloud.pushsdk.e.i.b.b(new com.meizu.cloud.pushsdk.e.c.a(e4)));
            }
        }
        if (i2 != 4) {
            if (i2 != 5) {
                return null;
            }
            return com.meizu.cloud.pushsdk.e.b.c.a("prefetch");
        }
        synchronized (f19280c) {
            try {
                try {
                    cVarA = com.meizu.cloud.pushsdk.e.i.b.a(kVar, this.F, this.G, this.E, this.H);
                } catch (Exception e5) {
                    return com.meizu.cloud.pushsdk.e.b.c.a(com.meizu.cloud.pushsdk.e.i.b.b(new com.meizu.cloud.pushsdk.e.c.a(e5)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return cVarA;
    }

    public b(f fVar) {
        this.f19289l = new HashMap<>();
        this.f19290m = new HashMap<>();
        this.f19291n = new HashMap<>();
        this.f19294q = new HashMap<>();
        this.f19297t = null;
        this.f19298u = null;
        this.f19299v = null;
        this.f19300w = null;
        this.f19301x = null;
        this.f19302y = null;
        this.C = 0;
        this.K = null;
        this.f19283f = 0;
        this.f19281d = fVar.f19343b;
        this.f19282e = fVar.f19342a;
        this.f19284g = fVar.f19344c;
        this.f19286i = fVar.f19345d;
        this.f19288k = fVar.f19351j;
        this.f19289l = fVar.f19352k;
        this.f19290m = fVar.f19353l;
        this.f19292o = fVar.f19354m;
        this.f19293p = fVar.f19355n;
        this.f19297t = fVar.f19346e;
        this.f19298u = fVar.f19347f;
        this.f19299v = fVar.f19348g;
        this.f19301x = fVar.f19350i;
        this.f19300w = fVar.f19349h;
        this.I = fVar.f19356o;
        this.J = fVar.f19357p;
        if (fVar.f19358q != null) {
            this.f19302y = g.a(fVar.f19358q);
        }
    }

    public com.meizu.cloud.pushsdk.e.c.a a(com.meizu.cloud.pushsdk.e.c.a aVar) {
        try {
            if (aVar.c() != null && aVar.c().a() != null && aVar.c().a().f() != null) {
                aVar.a(com.meizu.cloud.pushsdk.e.h.g.a(aVar.c().a().f()).a());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return aVar;
    }

    public void a(com.meizu.cloud.pushsdk.e.d.a aVar) {
        this.f19303z = aVar;
    }

    public void a(String str) {
        this.J = str;
    }
}
