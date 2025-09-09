package com.xiaomi.push;

import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class cz {

    /* renamed from: a, reason: collision with other field name */
    private long f257a;

    /* renamed from: a, reason: collision with other field name */
    public String f258a;

    /* renamed from: b, reason: collision with other field name */
    public String f260b;

    /* renamed from: c, reason: collision with root package name */
    public String f23560c;

    /* renamed from: d, reason: collision with root package name */
    public String f23561d;

    /* renamed from: e, reason: collision with root package name */
    public String f23562e;

    /* renamed from: f, reason: collision with root package name */
    public String f23563f;

    /* renamed from: g, reason: collision with root package name */
    public String f23564g;

    /* renamed from: h, reason: collision with root package name */
    protected String f23565h;

    /* renamed from: i, reason: collision with root package name */
    private String f23566i;

    /* renamed from: a, reason: collision with other field name */
    private ArrayList<di> f259a = new ArrayList<>();

    /* renamed from: a, reason: collision with root package name */
    private double f23558a = 0.1d;

    /* renamed from: j, reason: collision with root package name */
    private String f23567j = "s.mi1.cc";

    /* renamed from: b, reason: collision with root package name */
    private long f23559b = 86400000;

    public cz(String str) {
        this.f258a = "";
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        }
        this.f257a = System.currentTimeMillis();
        this.f259a.add(new di(str, -1));
        this.f258a = dd.m268a();
        this.f260b = str;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m262a() {
        return TextUtils.equals(this.f258a, dd.m268a());
    }

    public boolean b() {
        return System.currentTimeMillis() - this.f257a < this.f23559b;
    }

    boolean c() {
        long j2 = this.f23559b;
        if (864000000 >= j2) {
            j2 = 864000000;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j3 = this.f257a;
        return jCurrentTimeMillis - j3 > j2 || (jCurrentTimeMillis - j3 > this.f23559b && this.f258a.startsWith("WIFI-"));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f258a);
        sb.append("\n");
        sb.append(a());
        Iterator<di> it = this.f259a.iterator();
        while (it.hasNext()) {
            di next = it.next();
            sb.append("\n");
            sb.append(next.toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    public boolean a(cz czVar) {
        return TextUtils.equals(this.f258a, czVar.f258a);
    }

    public void b(String str, long j2, long j3) {
        a(str, 0, j2, j3, null);
    }

    public void a(long j2) {
        if (j2 > 0) {
            this.f23559b = j2;
            return;
        }
        throw new IllegalArgumentException("the duration is invalid " + j2);
    }

    public void b(String str, long j2, long j3, Exception exc) {
        a(str, -1, j2, j3, exc);
    }

    public void b(String str) {
        this.f23567j = str;
    }

    private synchronized void c(String str) {
        Iterator<di> it = this.f259a.iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().f276a, str)) {
                it.remove();
            }
        }
    }

    public ArrayList<String> a(String str) {
        if (!TextUtils.isEmpty(str)) {
            URL url = new URL(str);
            if (TextUtils.equals(url.getHost(), this.f260b)) {
                ArrayList<String> arrayList = new ArrayList<>();
                Iterator<String> it = a(true).iterator();
                while (it.hasNext()) {
                    db dbVarA = db.a(it.next(), url.getPort());
                    arrayList.add(new URL(url.getProtocol(), dbVarA.m267a(), dbVarA.a(), url.getFile()).toString());
                }
                return arrayList;
            }
            throw new IllegalArgumentException("the url is not supported by the fallback");
        }
        throw new IllegalArgumentException("the url is empty.");
    }

    public void a(String str, long j2, long j3) {
        try {
            b(new URL(str).getHost(), j2, j3);
        } catch (MalformedURLException unused) {
        }
    }

    public void a(String str, long j2, long j3, Exception exc) {
        try {
            b(new URL(str).getHost(), j2, j3, exc);
        } catch (MalformedURLException unused) {
        }
    }

    public void a(String str, int i2, long j2, long j3, Exception exc) {
        a(str, new cy(i2, j2, j3, exc));
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
    
        r1.a(r5);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void a(java.lang.String r4, com.xiaomi.push.cy r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.ArrayList<com.xiaomi.push.di> r0 = r3.f259a     // Catch: java.lang.Throwable -> L1f
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L1f
        L7:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L1f
            if (r1 == 0) goto L21
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L1f
            com.xiaomi.push.di r1 = (com.xiaomi.push.di) r1     // Catch: java.lang.Throwable -> L1f
            java.lang.String r2 = r1.f276a     // Catch: java.lang.Throwable -> L1f
            boolean r2 = android.text.TextUtils.equals(r4, r2)     // Catch: java.lang.Throwable -> L1f
            if (r2 == 0) goto L7
            r1.a(r5)     // Catch: java.lang.Throwable -> L1f
            goto L21
        L1f:
            r4 = move-exception
            goto L23
        L21:
            monitor-exit(r3)
            return
        L23:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1f
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.cz.a(java.lang.String, com.xiaomi.push.cy):void");
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized ArrayList<String> m259a() {
        return a(false);
    }

    public synchronized ArrayList<String> a(boolean z2) {
        ArrayList<String> arrayList;
        try {
            int size = this.f259a.size();
            di[] diVarArr = new di[size];
            this.f259a.toArray(diVarArr);
            Arrays.sort(diVarArr);
            arrayList = new ArrayList<>();
            for (int i2 = 0; i2 < size; i2++) {
                di diVar = diVarArr[i2];
                if (z2) {
                    arrayList.add(diVar.f276a);
                } else {
                    int iIndexOf = diVar.f276a.indexOf(":");
                    if (iIndexOf != -1) {
                        arrayList.add(diVar.f276a.substring(0, iIndexOf));
                    } else {
                        arrayList.add(diVar.f276a);
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m261a(String str) {
        a(new di(str));
    }

    synchronized void a(di diVar) {
        c(diVar.f276a);
        this.f259a.add(diVar);
    }

    public synchronized void a(String[] strArr) {
        int i2;
        try {
            int size = this.f259a.size() - 1;
            while (true) {
                i2 = 0;
                if (size < 0) {
                    break;
                }
                int length = strArr.length;
                while (true) {
                    if (i2 < length) {
                        if (TextUtils.equals(this.f259a.get(size).f276a, strArr[i2])) {
                            this.f259a.remove(size);
                            break;
                        }
                        i2++;
                    }
                }
                size--;
            }
            Iterator<di> it = this.f259a.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                int i4 = it.next().f23578a;
                if (i4 > i3) {
                    i3 = i4;
                }
            }
            while (i2 < strArr.length) {
                a(new di(strArr[i2], (strArr.length + i3) - i2));
                i2++;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized String a() {
        if (!TextUtils.isEmpty(this.f23566i)) {
            return this.f23566i;
        }
        if (TextUtils.isEmpty(this.f23562e)) {
            return "hardcode_isp";
        }
        String strA = bp.a(new String[]{this.f23562e, this.f23560c, this.f23561d, this.f23564g, this.f23563f}, OpenAccountUIConstants.UNDER_LINE);
        this.f23566i = strA;
        return strA;
    }

    public void a(double d2) {
        this.f23558a = d2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized JSONObject m260a() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            jSONObject.put(com.alipay.sdk.m.k.b.f9362k, this.f258a);
            jSONObject.put(RemoteMessageConst.TTL, this.f23559b);
            jSONObject.put("pct", this.f23558a);
            jSONObject.put("ts", this.f257a);
            jSONObject.put("city", this.f23561d);
            jSONObject.put("prv", this.f23560c);
            jSONObject.put("cty", this.f23564g);
            jSONObject.put("isp", this.f23562e);
            jSONObject.put("ip", this.f23563f);
            jSONObject.put("host", this.f260b);
            jSONObject.put("xf", this.f23565h);
            JSONArray jSONArray = new JSONArray();
            Iterator<di> it = this.f259a.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().a());
            }
            jSONObject.put("fbs", jSONArray);
        } catch (Throwable th) {
            throw th;
        }
        return jSONObject;
    }

    public synchronized cz a(JSONObject jSONObject) {
        this.f258a = jSONObject.optString(com.alipay.sdk.m.k.b.f9362k);
        this.f23559b = jSONObject.getLong(RemoteMessageConst.TTL);
        this.f23558a = jSONObject.getDouble("pct");
        this.f257a = jSONObject.getLong("ts");
        this.f23561d = jSONObject.optString("city");
        this.f23560c = jSONObject.optString("prv");
        this.f23564g = jSONObject.optString("cty");
        this.f23562e = jSONObject.optString("isp");
        this.f23563f = jSONObject.optString("ip");
        this.f260b = jSONObject.optString("host");
        this.f23565h = jSONObject.optString("xf");
        JSONArray jSONArray = jSONObject.getJSONArray("fbs");
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            a(new di().a(jSONArray.getJSONObject(i2)));
        }
        return this;
    }
}
