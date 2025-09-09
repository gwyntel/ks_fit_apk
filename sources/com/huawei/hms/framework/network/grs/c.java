package com.huawei.hms.framework.network.grs;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.framework.common.ExecutorsUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.g.g;
import com.huawei.hms.framework.network.grs.g.h;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: i, reason: collision with root package name */
    private static final String f16192i = "c";

    /* renamed from: j, reason: collision with root package name */
    private static final ExecutorService f16193j = ExecutorsUtils.newSingleThreadExecutor("GRS_GrsClient-Init");

    /* renamed from: k, reason: collision with root package name */
    private static long f16194k = 0;

    /* renamed from: a, reason: collision with root package name */
    private GrsBaseInfo f16195a;

    /* renamed from: b, reason: collision with root package name */
    private Context f16196b;

    /* renamed from: c, reason: collision with root package name */
    private g f16197c;

    /* renamed from: d, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.a f16198d;

    /* renamed from: e, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.c f16199e;

    /* renamed from: f, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.c f16200f;

    /* renamed from: g, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.a f16201g;

    /* renamed from: h, reason: collision with root package name */
    private FutureTask<Boolean> f16202h;

    class a implements Callable<Boolean> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f16203a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ GrsBaseInfo f16204b;

        a(Context context, GrsBaseInfo grsBaseInfo) {
            this.f16203a = context;
            this.f16204b = grsBaseInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Boolean call() throws NumberFormatException {
            c.this.f16197c = new g();
            c.this.f16199e = new com.huawei.hms.framework.network.grs.e.c(this.f16203a, GrsApp.getInstance().getBrand(OpenAccountUIConstants.UNDER_LINE) + "share_pre_grs_conf_");
            c.this.f16200f = new com.huawei.hms.framework.network.grs.e.c(this.f16203a, GrsApp.getInstance().getBrand(OpenAccountUIConstants.UNDER_LINE) + "share_pre_grs_services_");
            c cVar = c.this;
            cVar.f16198d = new com.huawei.hms.framework.network.grs.e.a(cVar.f16199e, c.this.f16200f, c.this.f16197c);
            c cVar2 = c.this;
            cVar2.f16201g = new com.huawei.hms.framework.network.grs.a(cVar2.f16195a, c.this.f16198d, c.this.f16197c, c.this.f16200f);
            if (com.huawei.hms.framework.network.grs.f.b.a(this.f16203a.getPackageName()) == null) {
                new com.huawei.hms.framework.network.grs.f.b(this.f16203a, true);
            }
            String strC = new com.huawei.hms.framework.network.grs.g.j.c(this.f16204b, this.f16203a).c();
            Logger.v(c.f16192i, "scan serviceSet is: " + strC);
            String strA = c.this.f16200f.a(TmpConstant.DEVICE_MODEL_SERVICES, "");
            String strA2 = h.a(strA, strC);
            if (!TextUtils.isEmpty(strA2)) {
                c.this.f16200f.b(TmpConstant.DEVICE_MODEL_SERVICES, strA2);
                Logger.i(c.f16192i, "postList is:" + StringUtils.anonymizeMessage(strA2));
                Logger.d(c.f16192i, "currentServices:" + StringUtils.anonymizeMessage(strA));
                if (!strA2.equals(strA)) {
                    c.this.f16197c.a(c.this.f16195a.getGrsParasKey(true, true, this.f16203a));
                    c.this.f16197c.a(new com.huawei.hms.framework.network.grs.g.j.c(this.f16204b, this.f16203a), null, null, c.this.f16200f, c.this.f16195a.getQueryTimeout());
                }
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime() - c.f16194k;
            if (c.f16194k == 0 || TimeUnit.MILLISECONDS.toHours(jElapsedRealtime) > 24) {
                Logger.i(c.f16192i, "Try to clear unUsed sp data.");
                long unused = c.f16194k = SystemClock.elapsedRealtime();
                c cVar3 = c.this;
                cVar3.a(cVar3.f16199e.a());
            }
            c.this.f16198d.b(this.f16204b, this.f16203a);
            return Boolean.TRUE;
        }
    }

    c(Context context, GrsBaseInfo grsBaseInfo) {
        this.f16202h = null;
        this.f16196b = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        a(grsBaseInfo);
        GrsBaseInfo grsBaseInfo2 = this.f16195a;
        FutureTask<Boolean> futureTask = new FutureTask<>(new a(this.f16196b, grsBaseInfo2));
        this.f16202h = futureTask;
        f16193j.execute(futureTask);
        Logger.i(f16192i, "GrsClient Instance is init, GRS SDK version: %s, GrsBaseInfoParam: app_name=%s, reg_country=%s, ser_country=%s, issue_country=%s ,queryTimeout=%d", com.huawei.hms.framework.network.grs.h.a.a(), grsBaseInfo2.getAppName(), grsBaseInfo.getRegCountry(), grsBaseInfo.getSerCountry(), grsBaseInfo.getIssueCountry(), Integer.valueOf(grsBaseInfo.getQueryTimeout()));
    }

    c(GrsBaseInfo grsBaseInfo) {
        this.f16202h = null;
        a(grsBaseInfo);
    }

    private boolean e() {
        String str;
        String str2;
        FutureTask<Boolean> futureTask = this.f16202h;
        if (futureTask == null) {
            return false;
        }
        try {
            return futureTask.get(8L, TimeUnit.SECONDS).booleanValue();
        } catch (InterruptedException e2) {
            e = e2;
            str = f16192i;
            str2 = "init compute task interrupted.";
            Logger.w(str, str2, e);
            return false;
        } catch (CancellationException unused) {
            Logger.i(f16192i, "init compute task canceled.");
            return false;
        } catch (ExecutionException e3) {
            e = e3;
            str = f16192i;
            str2 = "init compute task failed.";
            Logger.w(str, str2, e);
            return false;
        } catch (TimeoutException unused2) {
            Logger.w(f16192i, "init compute task timed out");
            return false;
        } catch (Exception e4) {
            e = e4;
            str = f16192i;
            str2 = "init compute task occur unknown Exception";
            Logger.w(str, str2, e);
            return false;
        }
    }

    boolean b() {
        GrsBaseInfo grsBaseInfo;
        Context context;
        if (!e() || (grsBaseInfo = this.f16195a) == null || (context = this.f16196b) == null) {
            return false;
        }
        this.f16198d.a(grsBaseInfo, context);
        return true;
    }

    private boolean b(long j2) {
        return System.currentTimeMillis() - j2 <= 604800000;
    }

    String a(String str, String str2, int i2) {
        if (this.f16195a == null || str == null || str2 == null) {
            Logger.w(f16192i, "invalid para!");
            return null;
        }
        if (e()) {
            return this.f16201g.a(str, str2, this.f16196b, i2);
        }
        return null;
    }

    Map<String, String> a(String str, int i2) {
        if (this.f16195a != null && str != null) {
            return e() ? this.f16201g.a(str, this.f16196b, i2) : new HashMap();
        }
        Logger.w(f16192i, "invalid para!");
        return new HashMap();
    }

    void a() {
        if (e()) {
            String grsParasKey = this.f16195a.getGrsParasKey(true, true, this.f16196b);
            this.f16199e.a(grsParasKey);
            this.f16199e.a(grsParasKey + "time");
            this.f16199e.a(grsParasKey + "ETag");
            this.f16197c.a(grsParasKey);
        }
    }

    private void a(GrsBaseInfo grsBaseInfo) {
        try {
            this.f16195a = grsBaseInfo.m71clone();
        } catch (CloneNotSupportedException e2) {
            Logger.w(f16192i, "GrsClient catch CloneNotSupportedException", e2);
            this.f16195a = grsBaseInfo.copy();
        }
    }

    void a(String str, IQueryUrlsCallBack iQueryUrlsCallBack, int i2) {
        if (iQueryUrlsCallBack == null) {
            Logger.w(f16192i, "IQueryUrlsCallBack is must not null for process continue.");
            return;
        }
        if (this.f16195a == null || str == null) {
            iQueryUrlsCallBack.onCallBackFail(-6);
        } else if (e()) {
            this.f16201g.a(str, iQueryUrlsCallBack, this.f16196b, i2);
        } else {
            Logger.i(f16192i, "grs init task has not completed.");
            iQueryUrlsCallBack.onCallBackFail(-7);
        }
    }

    void a(String str, String str2, IQueryUrlCallBack iQueryUrlCallBack, int i2) {
        if (iQueryUrlCallBack == null) {
            Logger.w(f16192i, "IQueryUrlCallBack is must not null for process continue.");
            return;
        }
        if (this.f16195a == null || str == null || str2 == null) {
            iQueryUrlCallBack.onCallBackFail(-6);
        } else if (e()) {
            this.f16201g.a(str, str2, iQueryUrlCallBack, this.f16196b, i2);
        } else {
            Logger.i(f16192i, "grs init task has not completed.");
            iQueryUrlCallBack.onCallBackFail(-7);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map<String, ?> map) throws NumberFormatException {
        if (map == null || map.isEmpty()) {
            Logger.v(f16192i, "sp's content is empty.");
            return;
        }
        Set<String> setKeySet = map.keySet();
        for (String str : setKeySet) {
            if (str.endsWith(this.f16196b.getPackageName() + "time")) {
                String strA = this.f16199e.a(str, "");
                long j2 = 0;
                if (!TextUtils.isEmpty(strA) && strA.matches("\\d+")) {
                    try {
                        j2 = Long.parseLong(strA);
                    } catch (NumberFormatException e2) {
                        Logger.w(f16192i, "convert expire time from String to Long catch NumberFormatException.", e2);
                    }
                }
                String strSubstring = str.substring(0, str.length() - 4);
                String str2 = strSubstring + "ETag";
                if (!b(j2) || !setKeySet.contains(strSubstring) || !setKeySet.contains(str2)) {
                    Logger.i(f16192i, "init interface auto clear some invalid sp's data: " + str);
                    this.f16199e.a(strSubstring);
                    this.f16199e.a(str);
                    this.f16199e.a(str2);
                }
            }
        }
    }

    boolean a(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && c.class == obj.getClass() && (obj instanceof c)) {
            return this.f16195a.compare(((c) obj).f16195a);
        }
        return false;
    }
}
