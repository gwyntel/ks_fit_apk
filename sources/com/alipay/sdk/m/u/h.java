package com.alipay.sdk.m.u;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.app.APayEntranceActivity;
import com.alipay.sdk.app.AlipayResultActivity;
import com.alipay.sdk.m.m.a;
import com.alipay.sdk.m.s.a;
import com.alipay.sdk.m.u.n;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.vivo.push.PushClientConstants;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class h {

    /* renamed from: j, reason: collision with root package name */
    public static final String f9784j = "failed";

    /* renamed from: k, reason: collision with root package name */
    public static final String f9785k = "scheme_failed";

    /* renamed from: a, reason: collision with root package name */
    public Activity f9786a;

    /* renamed from: b, reason: collision with root package name */
    public volatile IAlixPay f9787b;

    /* renamed from: d, reason: collision with root package name */
    public boolean f9789d;

    /* renamed from: e, reason: collision with root package name */
    public e f9790e;

    /* renamed from: f, reason: collision with root package name */
    public final com.alipay.sdk.m.s.a f9791f;

    /* renamed from: c, reason: collision with root package name */
    public final Object f9788c = IAlixPay.class;

    /* renamed from: g, reason: collision with root package name */
    public boolean f9792g = false;

    /* renamed from: h, reason: collision with root package name */
    public String f9793h = null;

    /* renamed from: i, reason: collision with root package name */
    public String f9794i = null;

    public class a implements AlipayResultActivity.a {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ CountDownLatch f9795a;

        public a(CountDownLatch countDownLatch) {
            this.f9795a = countDownLatch;
        }

        @Override // com.alipay.sdk.app.AlipayResultActivity.a
        public void a(int i2, String str, String str2) {
            h.this.f9793h = com.alipay.sdk.m.j.b.a(i2, str, str2);
            this.f9795a.countDown();
        }
    }

    public class b implements APayEntranceActivity.a {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Object f9797a;

        public b(Object obj) {
            this.f9797a = obj;
        }

        @Override // com.alipay.sdk.app.APayEntranceActivity.a
        public void a(String str) {
            h.this.f9794i = str;
            synchronized (this.f9797a) {
                try {
                    this.f9797a.notify();
                } finally {
                }
            }
        }
    }

    public class c extends IRemoteServiceCallback.Stub {
        public c() {
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public int getVersion() throws RemoteException {
            return 4;
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public boolean isHideLoadingScreen() throws RemoteException {
            return false;
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public void payEnd(boolean z2, String str) throws RemoteException {
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public void r03(String str, String str2, Map map) throws RemoteException {
            com.alipay.sdk.m.k.a.a(h.this.f9791f, com.alipay.sdk.m.k.b.f9372p, str, str2);
            if (TextUtils.equals(str2, "ActivityStartSuccess")) {
                if (h.this.f9790e != null) {
                    h.this.f9790e.a();
                }
                if (h.this.f9791f != null) {
                    h.this.f9791f.a(true);
                }
            }
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public void startActivity(String str, String str2, int i2, Bundle bundle) throws RemoteException {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            if (bundle == null) {
                bundle = new Bundle();
            }
            try {
                bundle.putInt("CallingPid", i2);
                intent.putExtras(bundle);
            } catch (Exception e2) {
                com.alipay.sdk.m.k.a.a(h.this.f9791f, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.Z, e2);
            }
            intent.setClassName(str, str2);
            try {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
                ActivityManager.getMyMemoryState(runningAppProcessInfo);
                com.alipay.sdk.m.k.a.a(h.this.f9791f, com.alipay.sdk.m.k.b.f9364l, "isFg", runningAppProcessInfo.processName + "|" + runningAppProcessInfo.importance + "|");
            } catch (Throwable unused) {
            }
            try {
                if (h.this.f9786a == null) {
                    com.alipay.sdk.m.k.a.b(h.this.f9791f, com.alipay.sdk.m.k.b.f9364l, "ErrActNull", "");
                    Context contextA = h.this.f9791f.a();
                    if (contextA != null) {
                        contextA.startActivity(intent);
                        return;
                    }
                    return;
                }
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                h.this.f9786a.startActivity(intent);
                com.alipay.sdk.m.k.a.a(h.this.f9791f, com.alipay.sdk.m.k.b.f9364l, "stAct2", "" + (SystemClock.elapsedRealtime() - jElapsedRealtime));
            } catch (Throwable th) {
                com.alipay.sdk.m.k.a.a(h.this.f9791f, com.alipay.sdk.m.k.b.f9364l, "ErrActNull", th);
                throw th;
            }
        }

        public /* synthetic */ c(h hVar, a aVar) {
            this();
        }
    }

    public class d implements ServiceConnection {
        public d() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            com.alipay.sdk.m.k.a.a(h.this.f9791f, com.alipay.sdk.m.k.b.f9364l, "srvCon");
            synchronized (h.this.f9788c) {
                h.this.f9787b = IAlixPay.Stub.asInterface(iBinder);
                h.this.f9788c.notify();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            com.alipay.sdk.m.k.a.a(h.this.f9791f, com.alipay.sdk.m.k.b.f9364l, "srvDis");
            h.this.f9787b = null;
        }

        public /* synthetic */ d(h hVar, a aVar) {
            this();
        }
    }

    public interface e {
        void a();

        void b();
    }

    public h(Activity activity, com.alipay.sdk.m.s.a aVar, e eVar) {
        this.f9786a = activity;
        this.f9791f = aVar;
        this.f9790e = eVar;
        com.alipay.sdk.m.u.e.d(com.alipay.sdk.m.l.a.f9433z, "alipaySdk");
    }

    private String b(String str, String str2) {
        JSONObject jSONObject;
        Object obj = new Object();
        String strA = n.a(32);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSAStart", strA + "|" + jElapsedRealtime);
        a.C0055a.a(this.f9791f, strA);
        APayEntranceActivity.f9104h.put(strA, new b(obj));
        try {
            HashMap<String, String> mapA = com.alipay.sdk.m.s.a.a(this.f9791f);
            mapA.put("ts_intent", String.valueOf(jElapsedRealtime));
            jSONObject = new JSONObject(mapA);
        } catch (Throwable th) {
            try {
                com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSALocEx", th);
                jSONObject = null;
            } catch (InterruptedException e2) {
                com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSAWaiting", e2);
                com.alipay.sdk.m.j.c cVar = com.alipay.sdk.m.j.c.PAY_WAITTING;
                return com.alipay.sdk.m.j.b.a(cVar.b(), cVar.a(), "");
            } catch (Throwable th2) {
                com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSAEx", th2);
                n.a("alipaySdk", com.alipay.sdk.m.l.b.f9450q, this.f9786a, this.f9791f);
                return f9785k;
            }
        }
        Intent intent = new Intent(this.f9786a, (Class<?>) APayEntranceActivity.class);
        intent.putExtra(APayEntranceActivity.f9100d, str);
        intent.putExtra(APayEntranceActivity.f9101e, str2);
        intent.putExtra(APayEntranceActivity.f9102f, strA);
        if (jSONObject != null) {
            intent.putExtra(APayEntranceActivity.f9103g, jSONObject.toString());
        }
        Activity activity = this.f9786a;
        com.alipay.sdk.m.s.a aVar = this.f9791f;
        com.alipay.sdk.m.k.a.a(activity, aVar, str, aVar.f9727d);
        try {
            Activity activity2 = this.f9786a;
            if (activity2 != null) {
                activity2.startActivity(intent);
            } else {
                com.alipay.sdk.m.k.a.b(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "ErrActNull", "");
                Context contextA = this.f9791f.a();
                if (contextA != null) {
                    contextA.startActivity(intent);
                }
            }
            synchronized (obj) {
                obj.wait();
            }
            String str3 = this.f9794i;
            String str4 = "unknown";
            try {
                String str5 = l.a(this.f9791f, str3).get(l.f9812a);
                str4 = str5 == null ? TmpConstant.GROUP_ROLE_UNKNOWN : str5;
            } catch (Throwable th3) {
                com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSAStatEx", th3);
            }
            com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSADone-" + str4);
            if (!TextUtils.isEmpty(str3)) {
                return str3;
            }
            com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSAEmpty");
            return f9785k;
        } catch (Throwable th4) {
            com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "ErrActNull", th4);
            throw th4;
        }
    }

    public String a(String str, boolean z2) {
        n.c cVarA;
        String strB = "";
        try {
            List<a.b> listL = com.alipay.sdk.m.m.a.z().l();
            if (!com.alipay.sdk.m.m.a.z().f9518g || listL == null) {
                listL = com.alipay.sdk.m.j.a.f9316d;
            }
            cVarA = n.a(this.f9791f, this.f9786a, listL);
        } catch (Throwable th) {
            th = th;
            cVarA = null;
        }
        if (cVarA != null) {
            try {
            } catch (Throwable th2) {
                th = th2;
                com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.N, th);
                boolean zB = n.b(this.f9791f);
                return z2 ? a(str, strB, packageInfo) : a(str, strB, packageInfo);
                return a(str, strB, packageInfo, cVarA);
            }
            if (cVarA.a(this.f9791f) || cVarA.a() || n.a(cVarA.f9826a)) {
                return f9784j;
            }
            PackageInfo packageInfo = cVarA.f9826a;
            if (packageInfo != null && !n.f9817b.equals(packageInfo.packageName)) {
                strB = cVarA.f9826a.packageName;
            } else {
                strB = n.b();
            }
            PackageInfo packageInfo2 = cVarA.f9826a;
            packageInfo = packageInfo2 != null ? packageInfo2 : null;
            String strC = com.alipay.sdk.m.m.a.z().c();
            if (strC != null) {
                if (strC.length() > 0) {
                    try {
                        JSONObject jSONObjectOptJSONObject = new JSONObject(strC).optJSONObject(strB);
                        if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.length() > 0) {
                            Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                            while (itKeys.hasNext()) {
                                String next = itKeys.next();
                                int i2 = Integer.parseInt(next);
                                if (packageInfo != null && packageInfo.versionCode >= i2) {
                                    try {
                                        boolean zA = com.alipay.sdk.m.m.a.z().a(this.f9786a, Integer.parseInt(jSONObjectOptJSONObject.getString(next)));
                                        this.f9792g = zA;
                                        if (zA) {
                                            break;
                                        }
                                    } catch (Exception unused) {
                                        continue;
                                    }
                                }
                            }
                        }
                    } catch (Throwable unused2) {
                    }
                }
            }
            boolean zB2 = n.b(this.f9791f);
            if ((z2 && !this.f9792g) || zB2 || !b(strB, this.f9786a, this.f9791f)) {
                return a(str, strB, packageInfo, cVarA);
            }
        }
        return f9784j;
    }

    private void a(n.c cVar) throws InterruptedException {
        PackageInfo packageInfo;
        if (cVar == null || (packageInfo = cVar.f9826a) == null) {
            return;
        }
        String str = packageInfo.packageName;
        Intent intent = new Intent();
        intent.setClassName(str, "com.alipay.android.app.TransProcessPayActivity");
        try {
            this.f9786a.startActivity(intent);
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9355d0, th);
        }
        Thread.sleep(200L);
    }

    private String a(String str, String str2, PackageInfo packageInfo) {
        String str3 = packageInfo != null ? packageInfo.versionName : "";
        com.alipay.sdk.m.u.e.d(com.alipay.sdk.m.l.a.f9433z, "pay payInvokeAct");
        com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.X, str2 + "|" + str3);
        Activity activity = this.f9786a;
        com.alipay.sdk.m.s.a aVar = this.f9791f;
        com.alipay.sdk.m.k.a.a(activity, aVar, str, aVar.f9727d);
        return b(str, str2);
    }

    public static boolean b(String str, Context context, com.alipay.sdk.m.s.a aVar) {
        try {
            Intent intent = new Intent();
            intent.setClassName(str, "com.alipay.android.app.flybird.ui.window.FlyBirdWindowActivity");
            if (intent.resolveActivityInfo(context.getPackageManager(), 0) != null) {
                return true;
            }
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "BSADetectFail");
            return false;
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "BSADetectFail", th);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(java.lang.String r7, java.lang.String r8, android.content.pm.PackageInfo r9, com.alipay.sdk.m.u.n.c r10) {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.u.h.a(java.lang.String, java.lang.String, android.content.pm.PackageInfo, com.alipay.sdk.m.u.n$c):java.lang.String");
    }

    private String a(String str, String str2) {
        String str3;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String strA = n.a(32);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSPStart", strA + "|" + jElapsedRealtime);
        a.C0055a.a(this.f9791f, strA);
        AlipayResultActivity.f9108a.put(strA, new a(countDownLatch));
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sourcePid", Binder.getCallingPid());
            jSONObject.put(com.alipay.sdk.m.l.b.f9437d, str);
            jSONObject.put(PushClientConstants.TAG_PKG_NAME, this.f9786a.getPackageName());
            jSONObject.put("session", strA);
            String strEncodeToString = Base64.encodeToString(jSONObject.toString().getBytes("UTF-8"), 2);
            Uri.Builder builderAppendQueryParameter = new Uri.Builder().scheme("alipays").authority("platformapi").path("startapp").appendQueryParameter("appId", "20000125");
            builderAppendQueryParameter.appendQueryParameter("mqpSchemePay", strEncodeToString);
            try {
                HashMap<String, String> mapA = com.alipay.sdk.m.s.a.a(this.f9791f);
                mapA.put("ts_scheme", String.valueOf(jElapsedRealtime));
                builderAppendQueryParameter.appendQueryParameter("mqpLoc", new JSONObject(mapA).toString());
            } catch (Throwable th) {
                com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSPLocEx", th);
            }
            String string = builderAppendQueryParameter.build().toString();
            Intent intent = new Intent();
            intent.setPackage(str2);
            intent.addFlags(268435456);
            intent.setData(Uri.parse(string));
            Activity activity = this.f9786a;
            com.alipay.sdk.m.s.a aVar = this.f9791f;
            com.alipay.sdk.m.k.a.a(activity, aVar, str, aVar.f9727d);
            this.f9786a.startActivity(intent);
            com.alipay.sdk.m.u.e.d(com.alipay.sdk.m.l.a.f9433z, "pay scheme waiting " + string);
            countDownLatch.await();
            String str4 = this.f9793h;
            try {
                str3 = l.a(this.f9791f, str4).get(l.f9812a);
                if (str3 == null) {
                    str3 = TmpConstant.GROUP_ROLE_UNKNOWN;
                }
            } catch (Throwable th2) {
                com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSPStatEx", th2);
                str3 = "unknown";
            }
            com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSPDone-" + str3);
            if (!TextUtils.isEmpty(str4)) {
                return str4;
            }
            com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSPEmpty");
            return f9785k;
        } catch (InterruptedException e2) {
            com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSPWaiting", e2);
            com.alipay.sdk.m.j.c cVar = com.alipay.sdk.m.j.c.PAY_WAITTING;
            return com.alipay.sdk.m.j.b.a(cVar.b(), cVar.a(), "");
        } catch (Throwable th3) {
            com.alipay.sdk.m.k.a.a(this.f9791f, com.alipay.sdk.m.k.b.f9364l, "BSPEx", th3);
            return f9785k;
        }
    }

    public static boolean a(String str, Context context, com.alipay.sdk.m.s.a aVar) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.setClassName(str, "com.alipay.android.msp.ui.views.MspContainerActivity");
            if (intent.resolveActivityInfo(context.getPackageManager(), 0) != null) {
                return true;
            }
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "BSPDetectFail");
            return false;
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "BSPDetectFail", th);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:188:0x0271 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.util.Pair<java.lang.String, java.lang.Boolean> a(java.lang.String r18, java.lang.String r19, com.alipay.sdk.m.s.a r20) {
        /*
            Method dump skipped, instructions count: 819
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.u.h.a(java.lang.String, java.lang.String, com.alipay.sdk.m.s.a):android.util.Pair");
    }

    public void a() {
        this.f9786a = null;
        this.f9790e = null;
    }
}
