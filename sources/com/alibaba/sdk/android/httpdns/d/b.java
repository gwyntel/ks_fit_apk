package com.alibaba.sdk.android.httpdns.d;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.utils.AlicloudTracker;
import com.alibaba.sdk.android.utils.AlicloudTrackerManager;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static volatile b f8816b;

    /* renamed from: a, reason: collision with root package name */
    private AlicloudTracker f8817a;

    /* renamed from: a, reason: collision with other field name */
    private AlicloudTrackerManager f15a;

    /* renamed from: q, reason: collision with root package name */
    private boolean f8818q = true;

    /* renamed from: r, reason: collision with root package name */
    private boolean f8819r = false;

    /* renamed from: b, reason: collision with other field name */
    private a f16b = new a();

    private b(Context context) {
        this.f8817a = null;
        this.f15a = null;
        if (context == null || !(context.getApplicationContext() instanceof Application)) {
            return;
        }
        AlicloudTrackerManager alicloudTrackerManager = AlicloudTrackerManager.getInstance((Application) context.getApplicationContext());
        this.f15a = alicloudTrackerManager;
        if (alicloudTrackerManager != null) {
            this.f8817a = alicloudTrackerManager.getTracker("httpdns", "1.3.2.3-no-bssid-ssid");
        }
    }

    public static b a() {
        return f8816b;
    }

    public void b(String str, int i2, int i3, int i4) {
        String str2;
        if (!this.f8818q) {
            str2 = "report is disabled";
        } else {
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report http dns succes failed due to tacker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && ((i2 == 0 || i2 == 1) && ((i3 == 0 || i3 == 1) && (i4 == 0 || i4 == 1)))) {
                final HashMap map = new HashMap();
                map.put("host", str);
                map.put("success", String.valueOf(i2));
                map.put("ipv6", String.valueOf(i3));
                map.put("cacheOpen", String.valueOf(i4));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.6
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("perf_user_getip", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str2 = "report http dns success failed due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void c(int i2) {
        String str;
        if (!this.f8818q) {
            str = "report is disabled";
        } else if (this.f8817a == null) {
            Log.e("HttpDns:ReportManager", "report cache failed due to tracker is null");
            return;
        } else {
            if (i2 == 0 || i2 == 1) {
                final HashMap map = new HashMap();
                map.put("enable", String.valueOf(i2));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.10
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("biz_cache", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str = "report cache failed, due to invalid param enable, enable can only be 0 or 1";
        }
        Log.e("HttpDns:ReportManager", str);
    }

    public void d(int i2) {
        String str;
        if (!this.f8818q) {
            str = "report is disabled";
        } else if (this.f8817a == null) {
            Log.e("HttpDns:ReportManager", "report set expired ip enabled failed due to tracker is null");
            return;
        } else {
            if (i2 == 0 || i2 == 1) {
                final HashMap map = new HashMap();
                map.put("enable", String.valueOf(i2));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.11
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("biz_expired_ip", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str = "report set expired ip enabled failed, due to invalid param enable, enable can only be 0 or 1";
        }
        Log.e("HttpDns:ReportManager", str);
    }

    public void e(int i2) {
        try {
            if (!this.f8818q) {
                Log.e("HttpDns:ReportManager", "report is disabled");
                return;
            }
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report ipv6 failed due to tracker is null");
                return;
            }
            if (i2 != 0 && i2 != 1) {
                Log.e("HttpDns:ReportManager", "report ipv6 failed, due to invalid param enable, enable can only be 0 or 1");
                return;
            }
            final HashMap map = new HashMap();
            map.put("enable", String.valueOf(i2));
            this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        b.this.f8817a.sendCustomHit("biz_ipv6_enable", map);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void k() {
        synchronized (b.class) {
            this.f8819r = true;
            this.f8818q = false;
        }
    }

    public void l() {
        if (!this.f8818q) {
            Log.e("HttpDns:ReportManager", "report is disabled");
        } else if (this.f8817a != null) {
            this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        b.this.f8817a.sendCustomHit("biz_active", null);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        } else {
            Log.e("HttpDns:ReportManager", "report sdk start failed due to tracker is null");
        }
    }

    public void setAccountId(String str) {
        AlicloudTracker alicloudTracker = this.f8817a;
        if (alicloudTracker != null) {
            alicloudTracker.setGlobalProperty("accountId", str);
        } else {
            Log.e("HttpDns:ReportManager", "tracker null, set global properties failed");
        }
    }

    public static b a(Context context) {
        if (f8816b == null) {
            synchronized (b.class) {
                try {
                    if (f8816b == null) {
                        f8816b = new b(context);
                    }
                } finally {
                }
            }
        }
        return f8816b;
    }

    public void b(String str, long j2, int i2) {
        String str2;
        if (!this.f8818q) {
            str2 = "report is disabled";
        } else {
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report http dns request time cost failed due to tacker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && j2 > 0 && (i2 == 0 || i2 == 1)) {
                if (j2 > 30000) {
                    j2 = 30000;
                }
                final HashMap map = new HashMap();
                map.put("srvAddr", str);
                map.put("cost", String.valueOf(j2));
                map.put("ipv6", String.valueOf(i2));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("perf_srv", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str2 = "report http dns request time cost failed due to invalid param";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void e(boolean z2) {
        synchronized (b.class) {
            try {
                if (!this.f8819r) {
                    this.f8818q = z2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void k(String str) {
        String str2;
        if (!this.f8818q) {
            str2 = "report is disabled";
        } else if (this.f8817a == null) {
            Log.e("HttpDns:ReportManager", "report uncaught exception failed due to tacker is null");
            return;
        } else {
            if (!TextUtils.isEmpty(str)) {
                final HashMap map = new HashMap();
                map.put("exception", str);
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("err_uncaught_exception", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str2 = "report uncaught exception failed due to exception msg is null";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void b(String str, String str2, String str3) {
        String str4;
        if (!this.f8818q) {
            str4 = "report is disabled";
        } else {
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report local disable failed due to tracker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                final HashMap map = new HashMap();
                map.put("host", str);
                map.put("scAddr", str2);
                map.put("srvAddr", str3);
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.9
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("biz_local_disable", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str4 = "report local disable failed due to missing params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public void a(String str, int i2, int i3, int i4) {
        String str2;
        if (!this.f8818q) {
            str2 = "report is disabled";
        } else {
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report http dns succes failed due to tacker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && ((i2 == 0 || i2 == 1) && ((i3 == 0 || i3 == 1) && (i4 == 0 || i4 == 1)))) {
                final HashMap map = new HashMap();
                map.put("host", str);
                map.put("success", String.valueOf(i2));
                map.put("ipv6", String.valueOf(i3));
                map.put("cacheOpen", String.valueOf(i4));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.5
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("perf_getip", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str2 = "report http dns success failed due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void a(String str, long j2, int i2) {
        String str2;
        if (!this.f8818q) {
            str2 = "report is disabled";
        } else {
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report sc request time cost failed due to tacker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && j2 > 0 && (i2 == 0 || i2 == 1)) {
                if (j2 > 30000) {
                    j2 = 30000;
                }
                final HashMap map = new HashMap();
                map.put("scAddr", str);
                map.put("cost", String.valueOf(j2));
                map.put("ipv6", String.valueOf(i2));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("perf_sc", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str2 = "report sc request time cost failed due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void a(String str, String str2, String str3) {
        String str4;
        if (!this.f8818q) {
            str4 = "report is disabled";
        } else {
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report sniffer failed due to tracker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                final HashMap map = new HashMap();
                map.put("host", str);
                map.put("scAddr", str2);
                map.put("srvAddr", str3);
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.8
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("biz_sniffer", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str4 = "report sniffer failed due to missing params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public void a(String str, String str2, String str3, int i2) {
        String str4;
        if (!this.f8818q) {
            str4 = "report is disabled";
        } else {
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report error sc failed due to tacker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && (i2 == 0 || i2 == 1)) {
                final HashMap map = new HashMap();
                map.put("scAddr", str);
                map.put("errCode", str2);
                map.put(AlinkConstants.KEY_ERR_MSG, str3);
                map.put("ipv6", String.valueOf(i2));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.13
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("err_sc", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str4 = "report error sc failed, due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public void a(String str, String str2, String str3, int i2, int i3) {
        try {
            if (!this.f8818q) {
                Log.e("HttpDns:ReportManager", "report is disabled");
                return;
            }
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report error http dns request failed due to tacker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && ((i2 == 0 || i2 == 1) && (i3 == 0 || i3 == 1))) {
                final HashMap map = new HashMap();
                map.put("srvAddr", str);
                map.put("errCode", str2);
                map.put(AlinkConstants.KEY_ERR_MSG, str3);
                map.put("ipv6", String.valueOf(i2));
                map.put("ipv6_srv", String.valueOf(i3));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.14
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("err_srv", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            Log.e("HttpDns:ReportManager", "report error http dns request failed, due to invalid params");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, long j2, long j3, int i2) {
        String str4;
        if (!this.f8818q) {
            str4 = "report is disabled";
        } else {
            if (this.f8817a == null) {
                Log.e("HttpDns:ReportManager", "report ip selection failed due to tacker is null");
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && i2 > 0) {
                if (j2 > 5000) {
                    j2 = 5000;
                }
                if (j3 > 5000) {
                    j3 = 5000;
                }
                final HashMap map = new HashMap();
                map.put("host", str);
                map.put("defaultIp", str2);
                map.put("selectedIp", str3);
                map.put("defaultIpCost", String.valueOf(j2));
                map.put("selectedIpCost", String.valueOf(j3));
                map.put("ipCount", String.valueOf(i2));
                this.f16b.b().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.b.7
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            b.this.f8817a.sendCustomHit("perf_ipselection", map);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return;
            }
            str4 = "report ip selection failed due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public boolean a(SDKMessageCallback sDKMessageCallback) {
        try {
            AlicloudTrackerManager alicloudTrackerManager = this.f15a;
            if (alicloudTrackerManager != null) {
                return alicloudTrackerManager.registerCrashDefend("httpdns", "1.3.2.3-no-bssid-ssid", 2, 7, sDKMessageCallback);
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
