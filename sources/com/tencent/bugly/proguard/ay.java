package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import android.os.FileObserver;
import android.os.Looper;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.anr.TraceFileHelper;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public final class ay {

    /* renamed from: f, reason: collision with root package name */
    public static ay f20904f;

    /* renamed from: b, reason: collision with root package name */
    public final ActivityManager f20906b;

    /* renamed from: c, reason: collision with root package name */
    final aa f20907c;

    /* renamed from: d, reason: collision with root package name */
    final ak f20908d;

    /* renamed from: e, reason: collision with root package name */
    String f20909e;

    /* renamed from: g, reason: collision with root package name */
    private final Context f20910g;

    /* renamed from: h, reason: collision with root package name */
    private final ac f20911h;

    /* renamed from: i, reason: collision with root package name */
    private final as f20912i;

    /* renamed from: k, reason: collision with root package name */
    private FileObserver f20914k;

    /* renamed from: m, reason: collision with root package name */
    private bg f20916m;

    /* renamed from: n, reason: collision with root package name */
    private int f20917n;

    /* renamed from: a, reason: collision with root package name */
    public final AtomicBoolean f20905a = new AtomicBoolean(false);

    /* renamed from: j, reason: collision with root package name */
    private final Object f20913j = new Object();

    /* renamed from: l, reason: collision with root package name */
    private boolean f20915l = true;

    /* renamed from: o, reason: collision with root package name */
    private long f20918o = 0;

    public ay(Context context, ac acVar, aa aaVar, ak akVar, as asVar) {
        Context contextA = ap.a(context);
        this.f20910g = contextA;
        this.f20906b = (ActivityManager) contextA.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (ap.b(NativeCrashHandler.getDumpFilePath())) {
            this.f20909e = context.getDir("bugly", 0).getAbsolutePath();
        } else {
            this.f20909e = NativeCrashHandler.getDumpFilePath();
        }
        this.f20907c = aaVar;
        this.f20908d = akVar;
        this.f20911h = acVar;
        this.f20912i = asVar;
    }

    private synchronized void c() {
        if (e()) {
            al.d("start when started!", new Object[0]);
            return;
        }
        FileObserver fileObserver = new FileObserver("/data/anr/") { // from class: com.tencent.bugly.proguard.ay.1
            @Override // android.os.FileObserver
            public final void onEvent(int i2, String str) {
                if (str == null) {
                    return;
                }
                final String strConcat = "/data/anr/".concat(str);
                al.d("watching file %s", strConcat);
                if (strConcat.contains(AgooConstants.MESSAGE_TRACE)) {
                    ay.this.f20908d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ay ayVar = ay.this;
                            String str2 = strConcat;
                            if (ayVar.a(true)) {
                                try {
                                    al.c("read trace first dump for create time!", new Object[0]);
                                    TraceFileHelper.a firstDumpInfo = TraceFileHelper.readFirstDumpInfo(str2, false);
                                    long jCurrentTimeMillis = firstDumpInfo != null ? firstDumpInfo.f20653c : -1L;
                                    if (jCurrentTimeMillis == -1) {
                                        al.d("trace dump fail could not get time!", new Object[0]);
                                        jCurrentTimeMillis = System.currentTimeMillis();
                                    }
                                    if (ayVar.a(jCurrentTimeMillis)) {
                                        return;
                                    }
                                    ayVar.a(jCurrentTimeMillis, str2);
                                } catch (Throwable th) {
                                    if (!al.a(th)) {
                                        th.printStackTrace();
                                    }
                                    al.e("handle anr error %s", th.getClass().toString());
                                }
                            }
                        }
                    });
                } else {
                    al.d("not anr file %s", strConcat);
                }
            }
        };
        this.f20914k = fileObserver;
        try {
            fileObserver.startWatching();
            al.a("start anr monitor!", new Object[0]);
            this.f20908d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.2
                @Override // java.lang.Runnable
                public final void run() {
                    ay.a(ay.this);
                }
            });
        } catch (Throwable th) {
            this.f20914k = null;
            al.d("start anr monitor failed!", new Object[0]);
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private synchronized void d() {
        if (!e()) {
            al.d("close when closed!", new Object[0]);
            return;
        }
        try {
            this.f20914k.stopWatching();
            this.f20914k = null;
            al.d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            al.d("stop anr monitor failed!", new Object[0]);
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private synchronized boolean e() {
        return this.f20914k != null;
    }

    private synchronized boolean f() {
        return this.f20915l;
    }

    private synchronized void g() {
        if (e()) {
            al.d("start when started!", new Object[0]);
            return;
        }
        if (TextUtils.isEmpty(this.f20909e)) {
            return;
        }
        synchronized (this.f20913j) {
            try {
                bg bgVar = this.f20916m;
                if (bgVar == null || !bgVar.isAlive()) {
                    bg bgVar2 = new bg();
                    this.f20916m = bgVar2;
                    boolean z2 = this.f20907c.S;
                    bgVar2.f20956b = z2;
                    al.c("set record stack trace enable:".concat(String.valueOf(z2)), new Object[0]);
                    bg bgVar3 = this.f20916m;
                    StringBuilder sb = new StringBuilder("Bugly-ThreadMonitor");
                    int i2 = this.f20917n;
                    this.f20917n = i2 + 1;
                    sb.append(i2);
                    bgVar3.setName(sb.toString());
                    this.f20916m.b();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        FileObserver fileObserver = new FileObserver(this.f20909e) { // from class: com.tencent.bugly.proguard.ay.3
            @Override // android.os.FileObserver
            public final void onEvent(int i3, String str) {
                if (str == null) {
                    return;
                }
                al.d("observe file, dir:%s fileName:%s", ay.this.f20909e, str);
                if (!str.startsWith("manual_bugly_trace_") || !str.endsWith(".txt")) {
                    al.c("not manual trace file, ignore.", new Object[0]);
                    return;
                }
                if (!ay.this.f20905a.get()) {
                    al.c("proc is not in anr, just ignore", new Object[0]);
                    return;
                }
                if (ay.this.f20907c.a()) {
                    al.c("Found foreground anr, resend sigquit immediately.", new Object[0]);
                    NativeCrashHandler.getInstance().resendSigquit();
                    long jA = am.a(str, "manual_bugly_trace_", ".txt");
                    ay.this.a(jA, ay.this.f20909e + "/" + str);
                    al.c("Finish handling one anr.", new Object[0]);
                    return;
                }
                al.c("Found background anr, resend sigquit later.", new Object[0]);
                long jA2 = am.a(str, "manual_bugly_trace_", ".txt");
                ay.this.a(jA2, ay.this.f20909e + "/" + str);
                al.c("Finish handling one anr, now resend sigquit.", new Object[0]);
                NativeCrashHandler.getInstance().resendSigquit();
            }
        };
        this.f20914k = fileObserver;
        try {
            fileObserver.startWatching();
            al.a("startWatchingPrivateAnrDir! dumFilePath is %s", this.f20909e);
            this.f20908d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.4
                @Override // java.lang.Runnable
                public final void run() {
                    ay.a(ay.this);
                }
            });
        } catch (Throwable th2) {
            this.f20914k = null;
            al.d("startWatchingPrivateAnrDir failed!", new Object[0]);
            if (al.a(th2)) {
                return;
            }
            th2.printStackTrace();
        }
    }

    private synchronized void h() {
        if (!e()) {
            al.d("close when closed!", new Object[0]);
            return;
        }
        synchronized (this.f20913j) {
            try {
                bg bgVar = this.f20916m;
                if (bgVar != null) {
                    bgVar.a();
                    this.f20916m = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        al.a("stopWatchingPrivateAnrDir", new Object[0]);
        try {
            this.f20914k.stopWatching();
            this.f20914k = null;
            al.d("close anr monitor!", new Object[0]);
        } catch (Throwable th2) {
            al.d("stop anr monitor failed!", new Object[0]);
            if (al.a(th2)) {
                return;
            }
            th2.printStackTrace();
        }
    }

    public final void b(boolean z2) {
        d(z2);
        boolean zF = f();
        ac acVarA = ac.a();
        if (acVarA != null) {
            zF = zF && acVarA.c().f20602f;
        }
        if (zF != e()) {
            al.a("anr changed to %b", Boolean.valueOf(zF));
            c(zF);
        }
    }

    public static synchronized ay a() {
        return f20904f;
    }

    private CrashDetailBean a(ax axVar) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.F = this.f20907c.k();
            crashDetailBean.G = this.f20907c.j();
            crashDetailBean.H = this.f20907c.l();
            crashDetailBean.I = ab.b(this.f20910g);
            crashDetailBean.J = ab.g();
            crashDetailBean.K = ab.h();
            crashDetailBean.f20621b = 3;
            crashDetailBean.f20624e = this.f20907c.g();
            aa aaVar = this.f20907c;
            crashDetailBean.f20625f = aaVar.f20690o;
            crashDetailBean.f20626g = aaVar.q();
            crashDetailBean.f20632m = this.f20907c.f();
            crashDetailBean.f20633n = "ANR_EXCEPTION";
            crashDetailBean.f20634o = axVar.f20902f;
            crashDetailBean.f20636q = axVar.f20903g;
            HashMap map = new HashMap();
            crashDetailBean.T = map;
            map.put("BUGLY_CR_01", axVar.f20901e);
            String str = crashDetailBean.f20636q;
            int iIndexOf = str != null ? str.indexOf("\n") : -1;
            crashDetailBean.f20635p = iIndexOf > 0 ? crashDetailBean.f20636q.substring(0, iIndexOf) : "GET_FAIL";
            crashDetailBean.f20637r = axVar.f20899c;
            String str2 = crashDetailBean.f20636q;
            if (str2 != null) {
                crashDetailBean.f20640u = ap.c(str2.getBytes());
            }
            crashDetailBean.f20645z = axVar.f20898b;
            crashDetailBean.A = axVar.f20897a;
            crashDetailBean.B = "main(1)";
            crashDetailBean.L = this.f20907c.s();
            crashDetailBean.f20627h = this.f20907c.p();
            crashDetailBean.f20628i = this.f20907c.A();
            crashDetailBean.f20641v = axVar.f20900d;
            aa aaVar2 = this.f20907c;
            crashDetailBean.P = aaVar2.f20696u;
            crashDetailBean.Q = aaVar2.f20676a;
            crashDetailBean.R = aaVar2.a();
            crashDetailBean.U = this.f20907c.z();
            aa aaVar3 = this.f20907c;
            crashDetailBean.V = aaVar3.f20699x;
            crashDetailBean.W = aaVar3.t();
            crashDetailBean.X = this.f20907c.y();
            crashDetailBean.f20644y = ao.a();
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    public final synchronized void b() {
        al.d("customer decides whether to open or close.", new Object[0]);
    }

    private synchronized void d(boolean z2) {
        if (this.f20915l != z2) {
            al.a("user change anr %b", Boolean.valueOf(z2));
            this.f20915l = z2;
        }
    }

    private synchronized void c(boolean z2) {
        if (z2) {
            g();
        } else {
            h();
        }
    }

    private static boolean a(String str, String str2, String str3) throws Throwable {
        Map<String, String[]> map;
        TraceFileHelper.a targetDumpInfo = TraceFileHelper.readTargetDumpInfo(str3, str, true);
        if (targetDumpInfo != null && (map = targetDumpInfo.f20654d) != null && !map.isEmpty()) {
            StringBuilder sb = new StringBuilder(1024);
            String[] strArr = targetDumpInfo.f20654d.get("main");
            if (strArr != null && strArr.length >= 3) {
                sb.append("\"main\" tid=");
                sb.append(strArr[2]);
                sb.append(" :\n");
                sb.append(strArr[0]);
                sb.append("\n");
                sb.append(strArr[1]);
                sb.append("\n\n");
            }
            for (Map.Entry<String, String[]> entry : targetDumpInfo.f20654d.entrySet()) {
                if (!entry.getKey().equals("main") && entry.getValue() != null && entry.getValue().length >= 3) {
                    sb.append("\"");
                    sb.append(entry.getKey());
                    sb.append("\" tid=");
                    sb.append(entry.getValue()[2]);
                    sb.append(" :\n");
                    sb.append(entry.getValue()[0]);
                    sb.append("\n");
                    sb.append(entry.getValue()[1]);
                    sb.append("\n\n");
                }
            }
            return am.a(str2, sb.toString(), sb.length() * 2);
        }
        al.e("not found trace dump for %s", str3);
        return false;
    }

    private static String a(List<ba> list, long j2) {
        if (list != null && !list.isEmpty()) {
            StringBuilder sb = new StringBuilder(4096);
            sb.append("\n>>>>> 以下为anr过程中主线程堆栈记录，可根据堆栈出现次数推测在该堆栈阻塞的时间，出现次数越多对anr贡献越大，越可能是造成anr的原因 >>>>>\n");
            sb.append("\n>>>>> Thread Stack Traces Records Start >>>>>\n");
            for (int i2 = 0; i2 < list.size(); i2++) {
                ba baVar = list.get(i2);
                sb.append("Thread name:");
                sb.append(baVar.f20925a);
                sb.append("\n");
                long j3 = baVar.f20926b - j2;
                String str = j3 <= 0 ? "before " : "after ";
                sb.append("Got ");
                sb.append(str);
                sb.append("anr:");
                sb.append(Math.abs(j3));
                sb.append("ms\n");
                sb.append(baVar.f20927c);
                sb.append("\n");
                if (sb.length() * 2 >= 101376) {
                    break;
                }
            }
            sb.append("\n<<<<< Thread Stack Traces Records End <<<<<\n");
            return sb.toString();
        }
        return "main thread stack not enable";
    }

    public final boolean a(boolean z2) {
        boolean zCompareAndSet = this.f20905a.compareAndSet(!z2, z2);
        al.c("tryChangeAnrState to %s, success:%s", Boolean.valueOf(z2), Boolean.valueOf(zCompareAndSet));
        return zCompareAndSet;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(long j2, String str) {
        ActivityManager.ProcessErrorStateInfo processErrorStateInfoA;
        List<ba> listC;
        try {
            al.c("anr time:%s", Long.valueOf(j2));
            synchronized (this.f20913j) {
                try {
                    if (this.f20916m != null) {
                        al.c("Disable record main stack trace.", new Object[0]);
                        this.f20916m.c();
                    }
                } finally {
                }
            }
            String strA = ap.a(Looper.getMainLooper().getThread());
            Map<String, String> mapA = ap.a(this.f20907c.R, at.f20844h);
            if (this.f20907c.a()) {
                boolean z2 = ab.o() || ab.p();
                al.c("isAnrCrashDevice:%s", Boolean.valueOf(z2));
                if (!z2) {
                    processErrorStateInfoA = az.a(this.f20906b, 21000L);
                } else {
                    processErrorStateInfoA = az.a(this.f20906b, 0L);
                }
            } else {
                processErrorStateInfoA = az.a(this.f20906b, 0L);
            }
            if (processErrorStateInfoA == null) {
                al.c("proc state is invisible or not my proc!", new Object[0]);
                return;
            }
            ax axVar = new ax();
            axVar.f20899c = j2;
            axVar.f20897a = processErrorStateInfoA.processName;
            axVar.f20902f = processErrorStateInfoA.shortMsg;
            axVar.f20901e = processErrorStateInfoA.longMsg;
            axVar.f20898b = mapA;
            axVar.f20903g = strA;
            if (TextUtils.isEmpty(strA)) {
                axVar.f20903g = "main stack is null , some error may be encountered.";
            }
            Long lValueOf = Long.valueOf(axVar.f20899c);
            String str2 = axVar.f20900d;
            String str3 = axVar.f20897a;
            String str4 = axVar.f20903g;
            String str5 = axVar.f20902f;
            String str6 = axVar.f20901e;
            Map<String, String> map = axVar.f20898b;
            al.c("anr time:%d\ntrace file:%s\nproc:%s\nmain stack:%s\nshort msg:%s\nlong msg:%s\n threads:%d", lValueOf, str2, str3, str4, str5, str6, Integer.valueOf(map == null ? 0 : map.size()));
            al.a("found visible anr , start to upload!", new Object[0]);
            al.c("trace file:%s", str);
            if (!TextUtils.isEmpty(str) && new File(str).exists()) {
                File file = new File(this.f20909e, "bugly_trace_" + j2 + ".txt");
                al.c("trace file exists", new Object[0]);
                if (str.startsWith("/data/anr/")) {
                    al.a("backup trace isOK:%s", Boolean.valueOf(a(str, file.getAbsolutePath(), axVar.f20897a)));
                } else {
                    al.a("trace file rename :%s", Boolean.valueOf(new File(str).renameTo(file)));
                }
                synchronized (this.f20913j) {
                    try {
                        bg bgVar = this.f20916m;
                        listC = bgVar != null ? bgVar.f20955a.c() : null;
                    } finally {
                    }
                }
                if (listC != null) {
                    String strA2 = a(listC, j2);
                    al.c("save main stack trace", new Object[0]);
                    am.a(file, strA2, 2147483647L, true);
                }
                axVar.f20900d = file.getAbsolutePath();
            } else {
                al.c("trace file is null or not exists, just ignore", new Object[0]);
            }
            CrashDetailBean crashDetailBeanA = a(axVar);
            at.a().a(crashDetailBeanA);
            if (crashDetailBeanA.f20620a >= 0) {
                al.a("backup anr record success!", new Object[0]);
            } else {
                al.d("backup anr record fail!", new Object[0]);
            }
            as.a("ANR", ap.a(j2), axVar.f20897a, "main", axVar.f20903g, crashDetailBeanA);
            if (!this.f20912i.a(crashDetailBeanA, !ab.r())) {
                this.f20912i.b(crashDetailBeanA, true);
            }
            this.f20912i.a(crashDetailBeanA);
            synchronized (this.f20913j) {
                try {
                    if (this.f20916m != null) {
                        al.c("Finish anr process.", new Object[0]);
                        this.f20916m.d();
                    }
                } finally {
                }
            }
        } catch (Throwable th) {
            try {
                al.b(th);
            } finally {
                a(false);
            }
        }
    }

    public final boolean a(long j2) {
        if (Math.abs(j2 - this.f20918o) < 10000) {
            al.d("should not process ANR too Fre in %dms", 10000);
            return true;
        }
        this.f20918o = j2;
        return false;
    }

    static /* synthetic */ void a(ay ayVar) {
        long jCurrentTimeMillis = (at.f20846j + System.currentTimeMillis()) - ap.b();
        am.a(ayVar.f20909e, "bugly_trace_", ".txt", jCurrentTimeMillis);
        am.a(ayVar.f20909e, "manual_bugly_trace_", ".txt", jCurrentTimeMillis);
        am.a(ayVar.f20909e, "main_stack_record_", ".txt", jCurrentTimeMillis);
        am.a(ayVar.f20909e, "main_stack_record_", ".txt.merged", jCurrentTimeMillis);
    }
}
