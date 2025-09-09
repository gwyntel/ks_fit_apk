package com.meizu.cloud.pushsdk.f.c;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.alibaba.cloudapi.sdk.constant.HttpConstant;
import com.meizu.cloud.pushsdk.e.d.i;
import com.meizu.cloud.pushsdk.e.d.j;
import com.meizu.cloud.pushsdk.e.d.k;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    private final String f19527a;

    /* renamed from: b, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.e.d.g f19528b;

    /* renamed from: c, reason: collision with root package name */
    protected final Context f19529c;

    /* renamed from: d, reason: collision with root package name */
    private Uri.Builder f19530d;

    /* renamed from: e, reason: collision with root package name */
    protected final f f19531e;

    /* renamed from: f, reason: collision with root package name */
    private d f19532f;

    /* renamed from: g, reason: collision with root package name */
    private b f19533g;

    /* renamed from: h, reason: collision with root package name */
    private final SSLSocketFactory f19534h;

    /* renamed from: i, reason: collision with root package name */
    private final HostnameVerifier f19535i;

    /* renamed from: j, reason: collision with root package name */
    private String f19536j;

    /* renamed from: k, reason: collision with root package name */
    protected final int f19537k;

    /* renamed from: l, reason: collision with root package name */
    protected final int f19538l;

    /* renamed from: m, reason: collision with root package name */
    protected final int f19539m;

    /* renamed from: n, reason: collision with root package name */
    private final long f19540n;

    /* renamed from: o, reason: collision with root package name */
    private final long f19541o;

    /* renamed from: p, reason: collision with root package name */
    protected final TimeUnit f19542p;

    /* renamed from: q, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.e.d.a f19543q;

    /* renamed from: r, reason: collision with root package name */
    protected final AtomicBoolean f19544r;

    /* renamed from: com.meizu.cloud.pushsdk.f.c.a$a, reason: collision with other inner class name */
    public static class C0155a {

        /* renamed from: a, reason: collision with root package name */
        protected final String f19545a;

        /* renamed from: b, reason: collision with root package name */
        protected final Context f19546b;

        /* renamed from: l, reason: collision with root package name */
        protected SSLSocketFactory f19556l;

        /* renamed from: m, reason: collision with root package name */
        protected HostnameVerifier f19557m;

        /* renamed from: c, reason: collision with root package name */
        protected f f19547c = null;

        /* renamed from: d, reason: collision with root package name */
        protected d f19548d = d.POST;

        /* renamed from: e, reason: collision with root package name */
        protected b f19549e = b.Single;

        /* renamed from: f, reason: collision with root package name */
        protected int f19550f = 5;

        /* renamed from: g, reason: collision with root package name */
        protected int f19551g = 250;

        /* renamed from: h, reason: collision with root package name */
        protected int f19552h = 5;

        /* renamed from: i, reason: collision with root package name */
        protected long f19553i = 40000;

        /* renamed from: j, reason: collision with root package name */
        protected long f19554j = 40000;

        /* renamed from: k, reason: collision with root package name */
        protected TimeUnit f19555k = TimeUnit.SECONDS;

        /* renamed from: n, reason: collision with root package name */
        protected com.meizu.cloud.pushsdk.e.d.a f19558n = new com.meizu.cloud.pushsdk.e.d.e();

        public C0155a(String str, Context context, Class<? extends a> cls) {
            this.f19545a = str;
            this.f19546b = context;
        }

        public C0155a a(int i2) {
            this.f19552h = i2;
            return this;
        }

        public C0155a b(int i2) {
            this.f19551g = i2;
            return this;
        }

        public C0155a c(int i2) {
            this.f19550f = i2;
            return this;
        }

        public C0155a a(com.meizu.cloud.pushsdk.e.d.a aVar) {
            if (aVar != null) {
                this.f19558n = aVar;
                com.meizu.cloud.pushsdk.f.g.c.c(C0155a.class.getSimpleName(), "set new call " + aVar, new Object[0]);
            }
            return this;
        }

        public C0155a a(b bVar) {
            this.f19549e = bVar;
            return this;
        }

        public C0155a a(f fVar) {
            this.f19547c = fVar;
            return this;
        }
    }

    public a(C0155a c0155a) {
        String simpleName = a.class.getSimpleName();
        this.f19527a = simpleName;
        this.f19528b = com.meizu.cloud.pushsdk.e.d.g.a(HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON);
        this.f19544r = new AtomicBoolean(false);
        this.f19532f = c0155a.f19548d;
        this.f19531e = c0155a.f19547c;
        this.f19529c = c0155a.f19546b;
        this.f19533g = c0155a.f19549e;
        this.f19534h = c0155a.f19556l;
        this.f19535i = c0155a.f19557m;
        this.f19537k = c0155a.f19550f;
        this.f19538l = c0155a.f19552h;
        this.f19539m = c0155a.f19551g;
        this.f19540n = c0155a.f19553i;
        this.f19541o = c0155a.f19554j;
        this.f19536j = c0155a.f19545a;
        this.f19542p = c0155a.f19555k;
        this.f19543q = c0155a.f19558n;
        a();
        com.meizu.cloud.pushsdk.f.g.c.c(simpleName, "Emitter created successfully!", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int a(i iVar) {
        k kVarA = null;
        try {
            try {
                com.meizu.cloud.pushsdk.f.g.c.a(this.f19527a, "Sending request: %s", iVar);
                kVarA = this.f19543q.a(iVar);
                return kVarA.b();
            } catch (IOException e2) {
                com.meizu.cloud.pushsdk.f.g.c.b(this.f19527a, "Request sending failed: %s", Log.getStackTraceString(e2));
                a(kVarA);
                return -1;
            }
        } finally {
            a(kVarA);
        }
    }

    public abstract void a(com.meizu.cloud.pushsdk.f.b.a aVar, boolean z2);

    public abstract void b();

    public String c() {
        return this.f19530d.clearQuery().build().toString();
    }

    private i a(com.meizu.cloud.pushsdk.f.b.a aVar) {
        a(aVar, "");
        this.f19530d.clearQuery();
        HashMap map = (HashMap) aVar.a();
        for (String str : map.keySet()) {
            this.f19530d.appendQueryParameter(str, (String) map.get(str));
        }
        return new i.b().a(this.f19530d.build().toString()).b().a();
    }

    private i a(ArrayList<com.meizu.cloud.pushsdk.f.b.a> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator<com.meizu.cloud.pushsdk.f.b.a> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next().a());
        }
        com.meizu.cloud.pushsdk.f.b.b bVar = new com.meizu.cloud.pushsdk.f.b.b("push_group_data", arrayList2);
        com.meizu.cloud.pushsdk.f.g.c.a(this.f19527a, "final SelfDescribingJson " + bVar, new Object[0]);
        return new i.b().a(this.f19530d.build().toString()).c(j.a(this.f19528b, bVar.toString())).a();
    }

    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v9 */
    protected LinkedList<e> a(c cVar) {
        int size = cVar.b().size();
        LinkedList<Long> linkedListA = cVar.a();
        LinkedList<e> linkedList = new LinkedList<>();
        long j2 = 22;
        boolean z2 = true;
        if (this.f19532f == d.GET) {
            for (int i2 = 0; i2 < size; i2++) {
                LinkedList linkedList2 = new LinkedList();
                linkedList2.add(linkedListA.get(i2));
                com.meizu.cloud.pushsdk.f.b.a aVar = cVar.b().get(i2);
                linkedList.add(new e(aVar.b() + 22 > this.f19540n, a(aVar), linkedList2));
            }
        } else {
            int iA = 0;
            while (iA < size) {
                LinkedList linkedList3 = new LinkedList();
                ArrayList<com.meizu.cloud.pushsdk.f.b.a> arrayList = new ArrayList<>();
                long j3 = 0;
                int i3 = iA;
                ?? r8 = z2;
                while (i3 < this.f19533g.a() + iA && i3 < size) {
                    com.meizu.cloud.pushsdk.f.b.a aVar2 = cVar.b().get(i3);
                    long jB = aVar2.b();
                    long j4 = jB + j2;
                    if (jB + 110 > this.f19541o) {
                        ArrayList<com.meizu.cloud.pushsdk.f.b.a> arrayList2 = new ArrayList<>();
                        LinkedList linkedList4 = new LinkedList();
                        arrayList2.add(aVar2);
                        linkedList4.add(linkedListA.get(i3));
                        linkedList.add(new e(r8, a(arrayList2), linkedList4));
                    } else {
                        j3 += j4;
                        if (88 + j3 + (arrayList.size() - r8) > this.f19541o) {
                            linkedList.add(new e(false, a(arrayList), linkedList3));
                            ArrayList<com.meizu.cloud.pushsdk.f.b.a> arrayList3 = new ArrayList<>();
                            LinkedList linkedList5 = new LinkedList();
                            arrayList3.add(aVar2);
                            linkedList5.add(linkedListA.get(i3));
                            arrayList = arrayList3;
                            linkedList3 = linkedList5;
                            j3 = j4;
                        } else {
                            arrayList.add(aVar2);
                            linkedList3.add(linkedListA.get(i3));
                        }
                    }
                    i3++;
                    j2 = 22;
                    r8 = 1;
                }
                if (!arrayList.isEmpty()) {
                    linkedList.add(new e(false, a(arrayList), linkedList3));
                }
                iA += this.f19533g.a();
                j2 = 22;
                z2 = true;
            }
        }
        return linkedList;
    }

    private void a() {
        Uri.Builder builderBuildUpon = Uri.parse("https://" + this.f19536j).buildUpon();
        this.f19530d = builderBuildUpon;
        if (this.f19532f == d.GET) {
            builderBuildUpon.appendPath("i");
        } else {
            builderBuildUpon.appendEncodedPath("push_data_report/mobile");
        }
    }

    private void a(k kVar) {
        if (kVar != null) {
            try {
                if (kVar.a() != null) {
                    kVar.a().close();
                }
            } catch (Exception unused) {
                com.meizu.cloud.pushsdk.f.g.c.a(this.f19527a, "Unable to close source data", new Object[0]);
            }
        }
    }

    private void a(com.meizu.cloud.pushsdk.f.b.a aVar, String str) {
        if ("".equals(str)) {
            str = com.meizu.cloud.pushsdk.f.g.e.b();
        }
        aVar.a("stm", str);
    }

    protected boolean a(int i2) {
        return i2 >= 200 && i2 < 300;
    }
}
