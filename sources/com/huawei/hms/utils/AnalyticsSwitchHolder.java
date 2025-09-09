package com.huawei.hms.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.AndroidException;
import com.huawei.hms.framework.network.grs.GrsApp;
import com.huawei.hms.support.hianalytics.HiAnalyticsUtils;
import com.huawei.hms.support.log.HMSLog;
import java.sql.Timestamp;

/* loaded from: classes4.dex */
public class AnalyticsSwitchHolder {
    public static final int ANALYTICS_DISABLED = 2;
    public static final int ANALYTICS_ENABLED = 1;

    /* renamed from: a, reason: collision with root package name */
    private static volatile int f18172a;

    /* renamed from: b, reason: collision with root package name */
    private static final Object f18173b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private static volatile Long f18174c = 0L;

    /* renamed from: d, reason: collision with root package name */
    private static volatile boolean f18175d = false;

    /* renamed from: e, reason: collision with root package name */
    private static volatile boolean f18176e = false;

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f18177a;

        a(Context context) {
            this.f18177a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            AnalyticsSwitchHolder.f(this.f18177a);
            HMSLog.i("AnalyticsSwitchHolder", "getStateForHmsAnalyticsProvider");
        }
    }

    class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f18178a;

        b(Context context) {
            this.f18178a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            HMSLog.i("AnalyticsSwitchHolder", "enter setAnalyticsStateAndTimestamp");
            AnalyticsSwitchHolder.f(this.f18178a);
            HMSLog.i("AnalyticsSwitchHolder", "quit setAnalyticsStateAndTimestamp");
        }
    }

    private static boolean b(Context context) {
        Bundle bundle;
        if (context == null) {
            HMSLog.e("AnalyticsSwitchHolder", "In getBiIsReportSetting, context is null.");
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                ApplicationInfo applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 128).applicationInfo;
                if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                    return bundle.getBoolean("com.huawei.hms.client.bireport.setting");
                }
            } catch (AndroidException unused) {
                HMSLog.e("AnalyticsSwitchHolder", "In getBiIsReportSetting, Failed to read meta data bi report setting.");
            } catch (RuntimeException e2) {
                HMSLog.e("AnalyticsSwitchHolder", "In getBiIsReportSetting, Failed to read meta data bi report setting.", e2);
            }
        }
        HMSLog.i("AnalyticsSwitchHolder", "In getBiIsReportSetting, configuration not found for bi report setting.");
        return false;
    }

    private static void c(Context context) {
        f18174c = Long.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
        new Thread(new a(context), "Thread-getStateForHmsAnalyticsProvider").start();
    }

    private static boolean d(Context context) {
        return "CN".equalsIgnoreCase(GrsApp.getInstance().getIssueCountryCode(context));
    }

    private static void e(Context context) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (timestamp.getTime() - f18174c.longValue() < 86400000 || f18174c.longValue() <= 0) {
            return;
        }
        f18174c = Long.valueOf(timestamp.getTime());
        new Thread(new b(context), "Thread-refreshOobeAnalyticsState").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void f(Context context) {
        if (context == null) {
            HMSLog.e("AnalyticsSwitchHolder", "In setAnalyticsState、, context is null.");
            return;
        }
        if (HiAnalyticsUtils.getInstance().getOobeAnalyticsState(context) != 1) {
            synchronized (f18173b) {
                f18172a = 2;
            }
            com.huawei.hms.stats.a.c().a();
            return;
        }
        synchronized (f18173b) {
            f18172a = 1;
        }
        if (HiAnalyticsUtils.getInstance().getInitFlag() || f18175d) {
            return;
        }
        HMSBIInitializer.getInstance(context).initHaSDK();
        f18175d = true;
    }

    public static int getAndRefreshAnalyticsState(Context context) {
        int i2;
        synchronized (f18173b) {
            isAnalyticsDisabled(context);
            i2 = f18172a;
        }
        return i2;
    }

    public static boolean getBiSetting(Context context) {
        Bundle bundle;
        if (context == null) {
            HMSLog.e("AnalyticsSwitchHolder", "In getBiSetting, context is null.");
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                ApplicationInfo applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 128).applicationInfo;
                if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                    return bundle.getBoolean("com.huawei.hms.client.bi.setting");
                }
            } catch (AndroidException unused) {
                HMSLog.e("AnalyticsSwitchHolder", "In getBiSetting, Failed to read meta data bisetting.");
            } catch (RuntimeException e2) {
                HMSLog.e("AnalyticsSwitchHolder", "In getBiSetting, Failed to read meta data bisetting.", e2);
            }
        }
        HMSLog.i("AnalyticsSwitchHolder", "In getBiSetting, configuration not found for bisetting.");
        return false;
    }

    public static boolean isAnalyticsDisabled(Context context) {
        synchronized (f18173b) {
            try {
                if (f18172a == 0) {
                    if (context == null) {
                        return true;
                    }
                    if (b(context)) {
                        HMSLog.i("AnalyticsSwitchHolder", "Builder->biReportSetting :true");
                        f18172a = 1;
                    } else if (getBiSetting(context)) {
                        HMSLog.i("AnalyticsSwitchHolder", "Builder->biSetting :true");
                        f18172a = 2;
                    } else if (d(context)) {
                        f18172a = 1;
                    } else {
                        HMSLog.i("AnalyticsSwitchHolder", "not ChinaROM");
                        f18172a = 3;
                        f18176e = true;
                        c(context);
                    }
                } else if (f18176e) {
                    e(context);
                }
                return f18172a != 1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
