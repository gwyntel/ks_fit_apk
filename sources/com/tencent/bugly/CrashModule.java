package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.aa;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.aq;
import com.tencent.bugly.proguard.at;
import com.tencent.bugly.proguard.au;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.z;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;

/* loaded from: classes4.dex */
public class CrashModule extends o {
    public static final int MODULE_ID = 1004;

    /* renamed from: c, reason: collision with root package name */
    private static int f20567c;

    /* renamed from: e, reason: collision with root package name */
    private static CrashModule f20568e = new CrashModule();

    /* renamed from: a, reason: collision with root package name */
    private long f20569a;

    /* renamed from: b, reason: collision with root package name */
    private BuglyStrategy.a f20570b;

    /* renamed from: d, reason: collision with root package name */
    private boolean f20571d = false;

    private synchronized void a(Context context, BuglyStrategy buglyStrategy) {
        if (buglyStrategy == null) {
            return;
        }
        try {
            String libBuglySOFilePath = buglyStrategy.getLibBuglySOFilePath();
            if (!TextUtils.isEmpty(libBuglySOFilePath)) {
                aa.a(context).f20695t = libBuglySOFilePath;
                al.a("setted libBugly.so file path :%s", libBuglySOFilePath);
            }
            if (buglyStrategy.getCrashHandleCallback() != null) {
                this.f20570b = buglyStrategy.getCrashHandleCallback();
                al.a("setted CrashHanldeCallback", new Object[0]);
            }
            if (buglyStrategy.getAppReportDelay() > 0) {
                long appReportDelay = buglyStrategy.getAppReportDelay();
                this.f20569a = appReportDelay;
                al.a("setted delay: %d", Long.valueOf(appReportDelay));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static CrashModule getInstance() {
        CrashModule crashModule = f20568e;
        crashModule.id = 1004;
        return crashModule;
    }

    @Override // com.tencent.bugly.proguard.o
    public String[] getTables() {
        return new String[]{"t_cr"};
    }

    public synchronized boolean hasInitialized() {
        return this.f20571d;
    }

    @Override // com.tencent.bugly.proguard.o
    public synchronized void init(Context context, boolean z2, BuglyStrategy buglyStrategy) {
        if (context != null) {
            try {
                if (!this.f20571d) {
                    al.a("Initializing crash module.", new Object[0]);
                    u uVarA = u.a();
                    int i2 = f20567c + 1;
                    f20567c = i2;
                    uVarA.a(i2);
                    this.f20571d = true;
                    CrashReport.setContext(context);
                    a(context, buglyStrategy);
                    at atVarA = at.a(context, z2, this.f20570b);
                    atVarA.f20857t.a();
                    if (buglyStrategy != null) {
                        atVarA.B = buglyStrategy.getCallBackType();
                        atVarA.C = buglyStrategy.getCloseErrorCallback();
                        at.f20851o = buglyStrategy.isUploadSpotCrash();
                        aa.a(context).S = buglyStrategy.isEnableRecordAnrMainStack();
                        if (buglyStrategy.isEnableCatchAnrTrace()) {
                            atVarA.f20858u.enableCatchAnrTrace();
                        } else {
                            atVarA.f20858u.disableCatchAnrTrace();
                        }
                    } else {
                        atVarA.f20858u.enableCatchAnrTrace();
                    }
                    if (aa.b().f20679d.equals(z.a(atVarA.f20855c))) {
                        atVarA.f20858u.removeEmptyNativeRecordFiles();
                    }
                    if (buglyStrategy == null || buglyStrategy.isEnableNativeCrashMonitor()) {
                        atVarA.e();
                    } else {
                        al.a("[crash] Closed native crash monitor!", new Object[0]);
                        atVarA.d();
                    }
                    if (buglyStrategy == null || buglyStrategy.isEnableANRCrashMonitor()) {
                        atVarA.f();
                    } else {
                        al.a("[crash] Closed ANR monitor!", new Object[0]);
                        atVarA.g();
                    }
                    if (buglyStrategy != null) {
                        at.f20841e = buglyStrategy.isMerged();
                    }
                    atVarA.a(buglyStrategy != null ? buglyStrategy.getAppReportDelay() : 0L);
                    atVarA.f20858u.checkUploadRecordCrash();
                    au.a(context);
                    aq aqVarA = aq.a();
                    aqVarA.a(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION);
                    aqVarA.a(context);
                    u uVarA2 = u.a();
                    int i3 = f20567c - 1;
                    f20567c = i3;
                    uVarA2.a(i3);
                }
            } finally {
            }
        }
    }

    @Override // com.tencent.bugly.proguard.o
    public void onServerStrategyChanged(StrategyBean strategyBean) {
        at atVarA;
        if (strategyBean == null || (atVarA = at.a()) == null) {
            return;
        }
        atVarA.f20857t.a(strategyBean);
        atVarA.f20858u.onStrategyChanged(strategyBean);
        atVarA.f20861x.b();
    }
}
