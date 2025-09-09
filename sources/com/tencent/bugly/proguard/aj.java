package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.util.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes4.dex */
public final class aj implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    protected int f20750a;

    /* renamed from: b, reason: collision with root package name */
    protected long f20751b;

    /* renamed from: c, reason: collision with root package name */
    protected long f20752c;

    /* renamed from: d, reason: collision with root package name */
    private int f20753d;

    /* renamed from: e, reason: collision with root package name */
    private int f20754e;

    /* renamed from: f, reason: collision with root package name */
    private final Context f20755f;

    /* renamed from: g, reason: collision with root package name */
    private final int f20756g;

    /* renamed from: h, reason: collision with root package name */
    private final byte[] f20757h;

    /* renamed from: i, reason: collision with root package name */
    private final aa f20758i;

    /* renamed from: j, reason: collision with root package name */
    private final ac f20759j;

    /* renamed from: k, reason: collision with root package name */
    private final af f20760k;

    /* renamed from: l, reason: collision with root package name */
    private final ai f20761l;

    /* renamed from: m, reason: collision with root package name */
    private final int f20762m;

    /* renamed from: n, reason: collision with root package name */
    private final ah f20763n;

    /* renamed from: o, reason: collision with root package name */
    private final ah f20764o;

    /* renamed from: p, reason: collision with root package name */
    private String f20765p;

    /* renamed from: q, reason: collision with root package name */
    private final String f20766q;

    /* renamed from: r, reason: collision with root package name */
    private final Map<String, String> f20767r;

    /* renamed from: s, reason: collision with root package name */
    private boolean f20768s;

    public aj(Context context, int i2, int i3, byte[] bArr, String str, String str2, ah ahVar, boolean z2) {
        this(context, i2, i3, bArr, str, str2, ahVar, 2, 30000, z2);
    }

    private static void a(String str) {
        al.e("[Upload] Failed to upload(%d): %s", 1, str);
    }

    public final void b(long j2) {
        this.f20752c += j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        ac acVar;
        Pair pair;
        boolean zBooleanValue;
        try {
            this.f20750a = 0;
            this.f20751b = 0L;
            this.f20752c = 0L;
            if (ab.c(this.f20755f) == null) {
                str = "network is not available";
            } else {
                byte[] bArr = this.f20757h;
                str = (bArr == null || bArr.length == 0) ? "request package is empty!" : (this.f20755f == null || this.f20758i == null || (acVar = this.f20759j) == null || this.f20760k == null) ? "illegal access error" : acVar.c() == null ? "illegal local strategy" : null;
            }
            if (str != null) {
                a(false, 0, str);
                return;
            }
            byte[] bArrA = ap.a(this.f20757h);
            if (bArrA == null) {
                a(false, 0, "failed to zip request body");
                return;
            }
            HashMap map = new HashMap(10);
            map.put("tls", "1");
            map.put("prodId", this.f20758i.e());
            map.put("bundleId", this.f20758i.f20678c);
            map.put("appVer", this.f20758i.f20690o);
            Map<String, String> map2 = this.f20767r;
            if (map2 != null) {
                map.putAll(map2);
            }
            map.put(com.taobao.agoo.a.a.b.JSON_CMD, Integer.toString(this.f20756g));
            map.put("platformId", Byte.toString((byte) 1));
            map.put("sdkVer", this.f20758i.f20683h);
            map.put("strategylastUpdateTime", Long.toString(this.f20759j.c().f20611o));
            this.f20761l.a(this.f20762m, System.currentTimeMillis());
            String strB = this.f20765p;
            this.f20759j.c();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                int i4 = i2 + 1;
                if (i2 >= this.f20753d) {
                    a(false, i3, "failed after many attempts");
                    return;
                }
                if (i4 > 1) {
                    al.d("[Upload] Failed to upload last time, wait and try(%d) again.", Integer.valueOf(i4));
                    ap.b(this.f20754e);
                    if (i4 == this.f20753d) {
                        al.d("[Upload] Use the back-up url at the last time: %s", this.f20766q);
                        strB = this.f20766q;
                    }
                }
                al.c("[Upload] Send %d bytes", Integer.valueOf(bArrA.length));
                strB = b(strB);
                al.c("[Upload] Upload to %s with cmd %d (pid=%d | tid=%d).", strB, Integer.valueOf(this.f20756g), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                byte[] bArrA2 = this.f20760k.a(strB, bArrA, this, map);
                Map<String, String> map3 = this.f20760k.f20717c;
                Pair<Boolean, Boolean> pairA = a(bArrA2, map3);
                if (((Boolean) pairA.first).booleanValue()) {
                    Pair<Boolean, Boolean> pairA2 = a(map3);
                    if (((Boolean) pairA2.first).booleanValue()) {
                        byte[] bArrB = ap.b(bArrA2);
                        if (bArrB != null) {
                            bArrA2 = bArrB;
                        }
                        br brVarA = ae.a(bArrA2);
                        if (brVarA == null) {
                            a(false, 1, "failed to decode response package");
                            Boolean bool = Boolean.FALSE;
                            pair = new Pair(bool, bool);
                        } else {
                            Integer numValueOf = Integer.valueOf(brVarA.f21033b);
                            byte[] bArr2 = brVarA.f21034c;
                            al.c("[Upload] Response cmd is: %d, length of sBuffer is: %d", numValueOf, Integer.valueOf(bArr2 == null ? 0 : bArr2.length));
                            if (a(brVarA, this.f20758i, this.f20759j)) {
                                a(true, 2, "successfully uploaded");
                                Boolean bool2 = Boolean.TRUE;
                                pair = new Pair(bool2, bool2);
                            } else {
                                a(false, 2, "failed to process response package");
                                Boolean bool3 = Boolean.FALSE;
                                pair = new Pair(bool3, bool3);
                            }
                        }
                        zBooleanValue = !((Boolean) pair.first).booleanValue() ? ((Boolean) pair.second).booleanValue() : false;
                    } else {
                        zBooleanValue = ((Boolean) pairA2.second).booleanValue();
                    }
                } else {
                    zBooleanValue = ((Boolean) pairA.second).booleanValue();
                }
                if (!zBooleanValue) {
                    return;
                }
                i3 = 1;
                i2 = i4;
            }
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public aj(Context context, int i2, int i3, byte[] bArr, String str, String str2, ah ahVar, int i4, int i5, boolean z2) {
        this.f20753d = 2;
        this.f20754e = 30000;
        this.f20765p = null;
        this.f20750a = 0;
        this.f20751b = 0L;
        this.f20752c = 0L;
        this.f20768s = false;
        this.f20755f = context;
        this.f20758i = aa.a(context);
        this.f20757h = bArr;
        this.f20759j = ac.a();
        if (af.f20715a == null) {
            af.f20715a = new af(context);
        }
        this.f20760k = af.f20715a;
        ai aiVarA = ai.a();
        this.f20761l = aiVarA;
        this.f20762m = i2;
        this.f20765p = str;
        this.f20766q = str2;
        this.f20763n = ahVar;
        this.f20764o = aiVarA.f20734a;
        this.f20756g = i3;
        if (i4 > 0) {
            this.f20753d = i4;
        }
        if (i5 > 0) {
            this.f20754e = i5;
        }
        this.f20768s = z2;
        this.f20767r = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(boolean r5, int r6, java.lang.String r7) {
        /*
            r4 = this;
            r0 = 0
            r1 = 1
            int r2 = r4.f20756g
            r3 = 630(0x276, float:8.83E-43)
            if (r2 == r3) goto L1c
            r3 = 640(0x280, float:8.97E-43)
            if (r2 == r3) goto L19
            r3 = 830(0x33e, float:1.163E-42)
            if (r2 == r3) goto L1c
            r3 = 840(0x348, float:1.177E-42)
            if (r2 == r3) goto L19
            java.lang.String r2 = java.lang.String.valueOf(r2)
            goto L1e
        L19:
            java.lang.String r2 = "userinfo"
            goto L1e
        L1c:
            java.lang.String r2 = "crash"
        L1e:
            if (r5 == 0) goto L2a
            java.lang.String r6 = "[Upload] Success: %s"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r1[r0] = r2
            com.tencent.bugly.proguard.al.a(r6, r1)
            goto L3d
        L2a:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r0] = r6
            r3[r1] = r2
            r6 = 2
            r3[r6] = r7
            java.lang.String r6 = "[Upload] Failed to upload(%d) %s: %s"
            com.tencent.bugly.proguard.al.e(r6, r3)
        L3d:
            long r0 = r4.f20751b
            long r2 = r4.f20752c
            long r0 = r0 + r2
            r2 = 0
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 <= 0) goto L5d
            com.tencent.bugly.proguard.ai r6 = r4.f20761l
            boolean r0 = r4.f20768s
            long r0 = r6.a(r0)
            long r2 = r4.f20751b
            long r0 = r0 + r2
            long r2 = r4.f20752c
            long r0 = r0 + r2
            com.tencent.bugly.proguard.ai r6 = r4.f20761l
            boolean r2 = r4.f20768s
            r6.a(r0, r2)
        L5d:
            com.tencent.bugly.proguard.ah r6 = r4.f20763n
            if (r6 == 0) goto L64
            r6.a(r5, r7)
        L64:
            com.tencent.bugly.proguard.ah r6 = r4.f20764o
            if (r6 == 0) goto L6b
            r6.a(r5, r7)
        L6b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.aj.a(boolean, int, java.lang.String):void");
    }

    private static String b(String str) {
        if (ap.b(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", str, UUID.randomUUID().toString());
        } catch (Throwable th) {
            al.a(th);
            return str;
        }
    }

    private static boolean a(br brVar, aa aaVar, ac acVar) throws NumberFormatException {
        if (brVar == null) {
            al.d("resp == null!", new Object[0]);
            return false;
        }
        byte b2 = brVar.f21032a;
        if (b2 != 0) {
            al.e("resp result error %d", Byte.valueOf(b2));
            return false;
        }
        try {
            if (!ap.b(brVar.f21038g) && !aa.b().i().equals(brVar.f21038g)) {
                w.a().a(ac.f20705a, "device", brVar.f21038g.getBytes("UTF-8"), true);
                aaVar.d(brVar.f21038g);
            }
        } catch (Throwable th) {
            al.a(th);
        }
        aaVar.f20688m = brVar.f21036e;
        int i2 = brVar.f21033b;
        if (i2 == 510) {
            byte[] bArr = brVar.f21034c;
            if (bArr == null) {
                al.e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(i2));
                return false;
            }
            bt btVar = (bt) ae.a(bArr, bt.class);
            if (btVar == null) {
                al.e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(brVar.f21033b));
                return false;
            }
            acVar.a(btVar);
        }
        return true;
    }

    private Pair<Boolean, Boolean> a(byte[] bArr, Map<String, String> map) {
        if (bArr == null) {
            a("Failed to upload for no response!");
            return new Pair<>(Boolean.FALSE, Boolean.TRUE);
        }
        al.c("[Upload] Received %d bytes", Integer.valueOf(bArr.length));
        if (bArr.length == 0) {
            a(false, 1, "response data from server is empty");
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    al.c("[Upload] HTTP headers from server: key = %s, value = %s", entry.getKey(), entry.getValue());
                }
            }
            Boolean bool = Boolean.FALSE;
            return new Pair<>(bool, bool);
        }
        Boolean bool2 = Boolean.TRUE;
        return new Pair<>(bool2, bool2);
    }

    public final void a(long j2) {
        this.f20750a++;
        this.f20751b += j2;
    }

    private Pair<Boolean, Boolean> a(Map<String, String> map) {
        int i2;
        if (map != null && map.size() != 0) {
            if (!map.containsKey("status")) {
                al.d("[Upload] Headers does not contain %s", "status");
            } else if (!map.containsKey("Bugly-Version")) {
                al.d("[Upload] Headers does not contain %s", "Bugly-Version");
            } else {
                String str = map.get("Bugly-Version");
                if (!str.contains("bugly")) {
                    al.d("[Upload] Bugly version is not valid: %s", str);
                } else {
                    al.c("[Upload] Bugly version from headers is: %s", str);
                    try {
                        i2 = Integer.parseInt(map.get("status"));
                        try {
                            al.c("[Upload] Status from server is %d (pid=%d | tid=%d).", Integer.valueOf(i2), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                            if (i2 != 0) {
                                a(false, 1, "status of server is ".concat(String.valueOf(i2)));
                                Boolean bool = Boolean.FALSE;
                                return new Pair<>(bool, bool);
                            }
                            Boolean bool2 = Boolean.TRUE;
                            return new Pair<>(bool2, bool2);
                        } catch (Throwable unused) {
                            a("[Upload] Failed to upload for format of status header is invalid: " + Integer.toString(i2));
                            return new Pair<>(Boolean.FALSE, Boolean.TRUE);
                        }
                    } catch (Throwable unused2) {
                        i2 = -1;
                    }
                }
            }
        } else {
            al.d("[Upload] Headers is empty.", new Object[0]);
        }
        al.c("[Upload] Headers from server is not valid, just try again (pid=%d | tid=%d).", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        a("[Upload] Failed to upload for no status header.");
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                al.c(String.format("[key]: %s, [value]: %s", entry.getKey(), entry.getValue()), new Object[0]);
            }
        }
        al.c("[Upload] Failed to upload for no status header.", new Object[0]);
        return new Pair<>(Boolean.FALSE, Boolean.TRUE);
    }
}
