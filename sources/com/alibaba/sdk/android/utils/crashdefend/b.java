package com.alibaba.sdk.android.utils.crashdefend;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static b f8946b;

    /* renamed from: a, reason: collision with root package name */
    private com.alibaba.sdk.android.utils.a f8947a;

    /* renamed from: a, reason: collision with other field name */
    private c f46a;

    /* renamed from: a, reason: collision with other field name */
    private ExecutorService f48a;
    private Context context;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.utils.crashdefend.a f45a = new com.alibaba.sdk.android.utils.crashdefend.a();

    /* renamed from: a, reason: collision with other field name */
    private final List<c> f47a = new ArrayList();

    /* renamed from: d, reason: collision with root package name */
    private Map<String, String> f8948d = new HashMap();

    /* renamed from: a, reason: collision with other field name */
    private final int[] f49a = new int[5];

    private class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private d f8949a;

        a(d dVar) {
            this.f8949a = dVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            d dVar;
            int i2;
            do {
                try {
                    Thread.sleep(1000L);
                    dVar = this.f8949a;
                    i2 = dVar.f8955d - 1;
                    dVar.f8955d = i2;
                } catch (InterruptedException unused) {
                    return;
                } catch (Exception e2) {
                    Log.d("UtilsSDK", e2.getMessage(), e2);
                    return;
                }
            } while (i2 > 0);
            if (i2 <= 0) {
                b.this.b(dVar.f8954b);
                e.a(b.this.context, b.this.f45a, (List<c>) b.this.f47a);
            }
        }
    }

    private b(Context context, com.alibaba.sdk.android.utils.a aVar) {
        this.f48a = null;
        this.context = context;
        this.f8947a = aVar;
        this.f48a = new f().a();
        for (int i2 = 0; i2 < 5; i2++) {
            this.f49a[i2] = (i2 * 5) + 5;
        }
        this.f8948d.put("sdkId", "utils");
        this.f8948d.put("sdkVersion", "2.0.0");
        try {
            a();
            b();
        } catch (Exception e2) {
            Log.d("UtilsSDK", e2.getMessage(), e2);
        }
    }

    public void b(String str, String str2) {
    }

    private void b() {
        this.f46a = null;
        ArrayList arrayList = new ArrayList();
        synchronized (this.f47a) {
            try {
                for (c cVar : this.f47a) {
                    if (cVar.crashCount >= cVar.f8951a) {
                        arrayList.add(cVar);
                    }
                }
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    c cVar2 = (c) it.next();
                    if (cVar2.f8953c < 5) {
                        long j2 = this.f45a.f8945a - this.f49a[r3];
                        g.a("UtilsSDK", "after restart " + ((cVar2.f50a - j2) + 1) + " times, sdk will be restore");
                        if (cVar2.f50a < j2) {
                            this.f46a = cVar2;
                            break;
                        }
                    } else {
                        Log.i("UtilsSDK", "SDK " + cVar2.f52a + " has been closed");
                    }
                }
                c cVar3 = this.f46a;
                if (cVar3 == null) {
                    Log.i("UtilsSDK", "NO SDK restore");
                } else {
                    cVar3.f8953c++;
                    Log.i("UtilsSDK", this.f46a.f52a + " will restore --- startSerialNumber:" + this.f46a.f50a + "   crashCount:" + this.f46a.crashCount);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static synchronized b a(Context context, com.alibaba.sdk.android.utils.a aVar) {
        try {
            if (f8946b == null) {
                f8946b = new b(context, aVar);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f8946b;
    }

    private void a() {
        if (e.m54a(this.context, this.f45a, this.f47a)) {
            this.f45a.f8945a++;
        } else {
            this.f45a.f8945a = 1L;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m52a(c cVar, SDKMessageCallback sDKMessageCallback) {
        c cVarA;
        if (cVar != null && sDKMessageCallback != null) {
            try {
                if (TextUtils.isEmpty(cVar.f54b) || TextUtils.isEmpty(cVar.f52a) || (cVarA = a(cVar, sDKMessageCallback)) == null) {
                    return false;
                }
                boolean zM51a = m51a(cVarA);
                int i2 = cVarA.crashCount;
                int i3 = cVarA.f8951a;
                if (i2 == i3) {
                    a(cVarA.f52a, cVarA.f54b, i2, i3);
                }
                cVarA.crashCount++;
                e.a(this.context, this.f45a, this.f47a);
                if (zM51a) {
                    a(cVarA);
                    Log.i("UtilsSDK", "START:" + cVarA.f52a + " --- limit:" + cVarA.f8951a + "  count:" + (cVarA.crashCount - 1) + "  restore:" + cVarA.f8953c + "  startSerialNumber:" + cVarA.f50a + "  registerSerialNumber:" + cVarA.f53b);
                } else {
                    sDKMessageCallback.crashDefendMessage(cVarA.f8951a, cVarA.crashCount - 1);
                    Log.i("UtilsSDK", "STOP:" + cVarA.f52a + " --- limit:" + cVarA.f8951a + "  count:" + (cVarA.crashCount - 1) + "  restore:" + cVarA.f8953c + "  startSerialNumber:" + cVarA.f50a + "  registerSerialNumber:" + cVarA.f53b);
                }
                return true;
            } catch (Exception e2) {
                Log.d("UtilsSDK", e2.getMessage(), e2);
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(c cVar) {
        if (cVar == null) {
            return;
        }
        int i2 = cVar.f8953c;
        if (i2 > 0) {
            b(cVar.f52a, cVar.f54b, i2, 5);
        }
        cVar.crashCount = 0;
        cVar.f8953c = 0;
    }

    private void b(String str, String str2, int i2, int i3) {
        if (this.f8947a == null) {
            return;
        }
        HashMap map = new HashMap();
        map.putAll(this.f8948d);
        map.put("crashSdkId", str);
        map.put("crashSdkVer", str2);
        map.put("recoverCount", String.valueOf(i2));
        map.put("recoverThreshold", String.valueOf(i3));
        this.f8947a.sendCustomHit("utils_biz_recover", 0L, map);
    }

    private c a(c cVar, SDKMessageCallback sDKMessageCallback) {
        synchronized (this.f47a) {
            try {
                List<c> list = this.f47a;
                c cVar2 = null;
                if (list != null && list.size() > 0) {
                    Iterator<c> it = this.f47a.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        c next = it.next();
                        if (next != null && next.f52a.equals(cVar.f52a)) {
                            if (!next.f54b.equals(cVar.f54b)) {
                                next.f54b = cVar.f54b;
                                next.f8951a = cVar.f8951a;
                                next.f8952b = cVar.f8952b;
                                next.crashCount = 0;
                                next.f8953c = 0;
                            }
                            if (next.f55c) {
                                Log.i("UtilsSDK", "SDK " + cVar.f52a + " has been registered");
                                return null;
                            }
                            next.f55c = true;
                            next.f51a = sDKMessageCallback;
                            next.f53b = this.f45a.f8945a;
                            cVar2 = next;
                        }
                    }
                }
                if (cVar2 == null) {
                    cVar2 = (c) cVar.clone();
                    cVar2.f55c = true;
                    cVar2.f51a = sDKMessageCallback;
                    cVar2.crashCount = 0;
                    cVar2.f53b = this.f45a.f8945a;
                    this.f47a.add(cVar2);
                }
                return cVar2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m51a(c cVar) {
        if (cVar.crashCount < cVar.f8951a) {
            cVar.f50a = cVar.f53b;
            return true;
        }
        c cVar2 = this.f46a;
        if (cVar2 == null || !cVar2.f52a.equals(cVar.f52a)) {
            return false;
        }
        cVar.crashCount = cVar.f8951a - 1;
        cVar.f50a = cVar.f53b;
        return true;
    }

    private void a(c cVar) {
        if (cVar == null) {
            return;
        }
        d dVar = new d();
        dVar.f8954b = cVar;
        dVar.f8955d = cVar.f8952b;
        a(dVar);
        SDKMessageCallback sDKMessageCallback = cVar.f51a;
        if (sDKMessageCallback != null) {
            sDKMessageCallback.crashDefendMessage(cVar.f8951a, cVar.crashCount - 1);
        }
    }

    private void a(d dVar) {
        if (dVar == null || dVar.f8954b == null) {
            return;
        }
        this.f48a.execute(new a(dVar));
    }

    private void a(String str, String str2, int i2, int i3) {
        if (this.f8947a == null) {
            return;
        }
        HashMap map = new HashMap();
        map.putAll(this.f8948d);
        map.put("crashSdkId", str);
        map.put("crashSdkVer", str2);
        map.put("curCrashCount", String.valueOf(i2));
        map.put("crashThreshold", String.valueOf(i3));
        this.f8947a.sendCustomHit("utils_biz_crash", 0L, map);
    }
}
