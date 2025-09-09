package com.umeng.commonsdk.statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.pro.bt;
import com.umeng.analytics.pro.ci;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.idtracking.e;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.statistics.internal.StatTracer;
import com.umeng.commonsdk.statistics.internal.d;
import com.umeng.commonsdk.statistics.noise.ABTest;
import com.umeng.commonsdk.statistics.noise.Defcon;
import com.umeng.commonsdk.statistics.proto.Response;
import java.io.File;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: b, reason: collision with root package name */
    private static final int f22334b = 1;

    /* renamed from: c, reason: collision with root package name */
    private static final int f22335c = 2;

    /* renamed from: d, reason: collision with root package name */
    private static final int f22336d = 3;

    /* renamed from: o, reason: collision with root package name */
    private static final String f22337o = "thtstart";

    /* renamed from: p, reason: collision with root package name */
    private static final String f22338p = "gkvc";

    /* renamed from: q, reason: collision with root package name */
    private static final String f22339q = "ekvc";

    /* renamed from: a, reason: collision with root package name */
    String f22340a;

    /* renamed from: f, reason: collision with root package name */
    private com.umeng.commonsdk.statistics.internal.c f22342f;

    /* renamed from: g, reason: collision with root package name */
    private ImprintHandler f22343g;

    /* renamed from: h, reason: collision with root package name */
    private e f22344h;

    /* renamed from: i, reason: collision with root package name */
    private ImprintHandler.a f22345i;

    /* renamed from: k, reason: collision with root package name */
    private Defcon f22347k;

    /* renamed from: l, reason: collision with root package name */
    private long f22348l;

    /* renamed from: m, reason: collision with root package name */
    private int f22349m;

    /* renamed from: n, reason: collision with root package name */
    private int f22350n;

    /* renamed from: r, reason: collision with root package name */
    private Context f22351r;

    /* renamed from: e, reason: collision with root package name */
    private final int f22341e = 1;

    /* renamed from: j, reason: collision with root package name */
    private ABTest f22346j = null;

    public c(Context context) {
        this.f22344h = null;
        this.f22345i = null;
        this.f22347k = null;
        this.f22348l = 0L;
        this.f22349m = 0;
        this.f22350n = 0;
        this.f22340a = null;
        this.f22351r = context;
        this.f22345i = ImprintHandler.getImprintService(context).c();
        this.f22347k = Defcon.getService(this.f22351r);
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(this.f22351r);
        this.f22348l = sharedPreferences.getLong(f22337o, 0L);
        this.f22349m = sharedPreferences.getInt(f22338p, 0);
        this.f22350n = sharedPreferences.getInt(f22339q, 0);
        this.f22340a = UMEnvelopeBuild.imprintProperty(this.f22351r, "track_list", null);
        ImprintHandler imprintService = ImprintHandler.getImprintService(this.f22351r);
        this.f22343g = imprintService;
        imprintService.a(new d() { // from class: com.umeng.commonsdk.statistics.c.1
            @Override // com.umeng.commonsdk.statistics.internal.d
            public void onImprintChanged(ImprintHandler.a aVar) {
                c.this.f22347k.onImprintChanged(aVar);
                c cVar = c.this;
                cVar.f22340a = UMEnvelopeBuild.imprintProperty(cVar.f22351r, "track_list", null);
            }
        });
        if (!UMConfigure.needSendZcfgEnv(this.f22351r)) {
            this.f22344h = e.a(this.f22351r);
        }
        com.umeng.commonsdk.statistics.internal.c cVar = new com.umeng.commonsdk.statistics.internal.c(this.f22351r);
        this.f22342f = cVar;
        cVar.a(StatTracer.getInstance(this.f22351r));
    }

    public boolean a(File file) {
        if (file == null) {
            return false;
        }
        try {
            byte[] byteArray = UMFrUtils.toByteArray(file.getPath());
            if (byteArray == null) {
                return false;
            }
            String name = file.getName();
            if (TextUtils.isEmpty(name)) {
                return false;
            }
            com.umeng.commonsdk.statistics.internal.a aVarA = com.umeng.commonsdk.statistics.internal.a.a(this.f22351r);
            aVarA.e(name);
            boolean zA = aVarA.a(name);
            boolean zB = aVarA.b(name);
            boolean zC = aVarA.c(name);
            boolean zD = aVarA.d(name);
            String strD = com.umeng.commonsdk.stateless.d.d(name);
            byte[] bArrA = this.f22342f.a(byteArray, zA, zC, !TextUtils.isEmpty(strD) ? com.umeng.commonsdk.stateless.d.c(strD) : zD ? UMServerURL.SILENT_HEART_BEAT : zC ? UMServerURL.ZCFG_PATH : UMServerURL.PATH_ANALYTICS);
            int iA = bArrA == null ? 1 : a(bArrA);
            if (UMConfigure.isDebugLog()) {
                if (zD && iA == 2) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "heart beat req: succeed.");
                } else if (zC && iA == 2) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Zero req: succeed.");
                } else if (zB && iA == 2) {
                    MLog.d("本次启动数据: 发送成功!");
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Send instant data: succeed.");
                } else if (zA && iA == 2) {
                    MLog.d("普通统计数据: 发送成功!");
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Send analytics data: succeed.");
                } else if (iA == 2) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Inner req: succeed.");
                }
            }
            if (iA == 2) {
                e eVar = this.f22344h;
                if (eVar != null) {
                    eVar.e();
                }
                StatTracer.getInstance(this.f22351r).saveSate();
                if (zD) {
                    String strImprintProperty = UMEnvelopeBuild.imprintProperty(this.f22351r, "iss", "");
                    if (!TextUtils.isEmpty(strImprintProperty)) {
                        if ("1".equalsIgnoreCase(strImprintProperty)) {
                            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 更新静默心跳最后一次成功请求时间.");
                            com.umeng.commonsdk.utils.c.a(this.f22351r, System.currentTimeMillis());
                        } else if ("0".equalsIgnoreCase(strImprintProperty)) {
                            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 静默模式 -> 正常模式。重置 last req time");
                            com.umeng.commonsdk.utils.c.a(this.f22351r, 0L);
                            com.umeng.commonsdk.utils.c.d(this.f22351r);
                        }
                    }
                }
            } else if (iA == 3) {
                StatTracer.getInstance(this.f22351r).saveSate();
                if (zC) {
                    FieldManager.a().a(this.f22351r);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 零号报文应答内容报错!!! ，特殊处理!，继续正常流程。");
                    Context context = this.f22351r;
                    UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.f22255s, com.umeng.commonsdk.internal.b.a(context).a(), null);
                    return true;
                }
            }
            return iA == 2;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(this.f22351r, th);
            return false;
        }
    }

    private int a(byte[] bArr) {
        Response response = new Response();
        try {
            new bt(new ci.a()).a(response, bArr);
            if (response.resp_code == 1) {
                this.f22343g.b(response.getImprint());
                this.f22343g.d();
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(this.f22351r, th);
        }
        return response.resp_code == 1 ? 2 : 3;
    }
}
