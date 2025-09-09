package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    private static volatile n f8868a = null;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f8869d = false;

    /* renamed from: e, reason: collision with root package name */
    private static long f8870e = 0;

    /* renamed from: g, reason: collision with root package name */
    private static String f8871g = "https://";

    /* renamed from: h, reason: collision with root package name */
    private static String f8872h = null;

    /* renamed from: h, reason: collision with other field name */
    public static boolean f24h = false;

    /* renamed from: c, reason: collision with root package name */
    private String f8873c;

    /* renamed from: e, reason: collision with other field name */
    private boolean f28e;

    /* renamed from: e, reason: collision with other field name */
    private int f27e = 0;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f25a = null;

    /* renamed from: f, reason: collision with other field name */
    private long f29f = 0;

    /* renamed from: g, reason: collision with other field name */
    private long f31g = 0;

    /* renamed from: f, reason: collision with root package name */
    private int f8874f = 0;

    /* renamed from: f, reason: collision with other field name */
    private boolean f30f = false;

    /* renamed from: g, reason: collision with other field name */
    private boolean f32g = true;

    /* renamed from: i, reason: collision with root package name */
    private String f8875i = null;

    /* renamed from: a, reason: collision with other field name */
    private Handler f26a = null;

    private n() {
    }

    public static n a() {
        if (f8868a == null) {
            synchronized (n.class) {
                try {
                    if (f8868a == null) {
                        f8868a = new n();
                    }
                } finally {
                }
            }
        }
        return f8868a;
    }

    private String e() {
        return (this.f28e || this.f30f) ? f.f8853b[this.f8874f] : f.f19a[this.f27e];
    }

    private void f() {
        int i2 = this.f8874f;
        this.f8874f = i2 < f.f8853b.length + (-1) ? i2 + 1 : 0;
    }

    synchronized void b(Context context, String str) {
        try {
            try {
                if (str.equals(this.f8875i)) {
                    i.e("region should be different");
                } else {
                    this.f8875i = str;
                    if (System.currentTimeMillis() - this.f31g >= 300000) {
                        d();
                    } else {
                        long jCurrentTimeMillis = 300000 - (System.currentTimeMillis() - this.f31g);
                        i.e("The call time should be greater than 5 minutes. SDK will initiate an update request after " + (jCurrentTimeMillis / 60000) + " minutes.");
                        if (this.f26a == null) {
                            Handler handler = new Handler();
                            this.f26a = handler;
                            handler.postDelayed(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.n.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    n.this.d();
                                }
                            }, jCurrentTimeMillis);
                        }
                    }
                    if (this.f25a == null) {
                        if (context == null) {
                            i.f("sp failed to save, does not affect the current settings");
                            return;
                        }
                        this.f25a = context.getSharedPreferences("httpdns_config_cache", 0);
                    }
                    SharedPreferences.Editor editorEdit = this.f25a.edit();
                    editorEdit.putString("httpdns_region", this.f8875i);
                    editorEdit.putBoolean("httpdns_first_start", true);
                    editorEdit.putLong("schedule_center_last_request_time", 0L);
                    editorEdit.commit();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    synchronized void c() {
        m mVarA;
        int length;
        try {
            if (System.currentTimeMillis() - this.f29f >= 300000) {
                i.d("update server ips from StartIp schedule center.");
                this.f27e = 0;
                this.f8874f = 0;
                this.f30f = false;
                this.f32g = true;
                f24h = false;
                if (this.f28e) {
                    mVarA = m.a();
                    length = f.f8853b.length;
                } else {
                    mVarA = m.a();
                    length = f.f19a.length;
                }
                mVarA.a(length - 1);
                c.a().submit(m.a());
            } else {
                i.d("update server ips from StartIp schedule center too often, give up. ");
                u.j();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    synchronized String m46d() {
        StringBuilder sb;
        String str;
        try {
            try {
                sb = new StringBuilder();
                sb.append(f8871g);
                sb.append(e());
                sb.append("/");
                String str2 = this.f8873c;
                if (str2 == null) {
                    str2 = f.f8854c;
                }
                sb.append(str2);
                sb.append("/ss?platform=android&sdk_version=");
                sb.append("1.3.2.3-no-bssid-ssid");
                sb.append("&sid=");
                sb.append(com.alibaba.sdk.android.httpdns.e.a.a().getSessionId());
                sb.append("&net=");
                sb.append(com.alibaba.sdk.android.httpdns.e.a.a().l());
                if (TextUtils.isEmpty(this.f8875i)) {
                    str = "";
                } else {
                    str = "&region=" + this.f8875i;
                }
                sb.append(str);
            } catch (Exception e2) {
                e2.printStackTrace();
                return "";
            }
        } catch (Throwable th) {
            throw th;
        }
        return sb.toString();
    }

    public void setAccountId(String str) {
        this.f8873c = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        i.d("update server ips from StartIp schedule center.");
        this.f31g = System.currentTimeMillis();
        this.f27e = 0;
        this.f8874f = 0;
        this.f30f = false;
        this.f28e = true;
        this.f32g = true;
        f24h = false;
        m.a().a(f.f8853b.length - 1);
        c.a().submit(m.a());
        this.f26a = null;
    }

    /* renamed from: e, reason: collision with other method in class */
    private void m45e() {
        int i2 = this.f27e;
        this.f27e = i2 < f.f19a.length + (-1) ? i2 + 1 : 0;
    }

    synchronized void a(Context context, String str) {
        try {
            if (!f8869d) {
                synchronized (n.class) {
                    try {
                        if (!f8869d) {
                            setAccountId(str);
                            if (context != null) {
                                this.f25a = context.getSharedPreferences("httpdns_config_cache", 0);
                            }
                            this.f28e = this.f25a.getBoolean("httpdns_first_start", true);
                            f8872h = this.f25a.getString("httpdns_server_ips", null);
                            this.f8875i = this.f25a.getString("httpdns_region", null);
                            String str2 = f8872h;
                            if (str2 != null) {
                                f.a(str2.split(com.alipay.sdk.m.u.i.f9802b));
                            }
                            long j2 = this.f25a.getLong("schedule_center_last_request_time", 0L);
                            f8870e = j2;
                            if (j2 == 0 || System.currentTimeMillis() - f8870e >= 86400000) {
                                t.a().c(false);
                                c();
                            }
                            f8869d = true;
                        }
                    } finally {
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    synchronized void c(Throwable th) {
        try {
            try {
                f24h = false;
                d(th);
                if (this.f28e) {
                    f();
                } else {
                    if (!this.f30f) {
                        m45e();
                    }
                    if (this.f27e == 0) {
                        this.f30f = true;
                        if (this.f32g) {
                            this.f32g = false;
                            this.f8874f = 0;
                            i.d("StartIp Scheduler center update from StartIp");
                            m.a().a(f.f8853b.length - 1);
                            c.a().submit(m.a());
                        } else {
                            f();
                            if (this.f8874f == 0) {
                                this.f29f = System.currentTimeMillis();
                                i.f("StartIp Scheduler center update failed");
                                u.j();
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    private void d(Throwable th) {
        try {
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                int iA = com.alibaba.sdk.android.httpdns.d.c.a(th);
                bVarA.a(m46d(), String.valueOf(iA), th.getMessage(), com.alibaba.sdk.android.httpdns.d.c.a());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    synchronized void a(o oVar, long j2) {
        try {
            a(m46d(), j2);
            this.f27e = 0;
            this.f8874f = 0;
            this.f30f = false;
            this.f32g = true;
            HttpDns.switchDnsService(oVar.isEnabled());
            if (a(oVar.b())) {
                i.d("StartIp Scheduler center update success    StartIp isFirstStart：" + this.f28e);
                f24h = true;
                this.f29f = System.currentTimeMillis();
                u.i();
                if (this.f28e) {
                    SharedPreferences.Editor editorEdit = this.f25a.edit();
                    editorEdit.putBoolean("httpdns_first_start", false);
                    editorEdit.commit();
                    this.f28e = false;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void a(String str, long j2) {
        try {
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                bVarA.a(str, j2, com.alibaba.sdk.android.httpdns.d.c.a());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    synchronized boolean a(String[] strArr) {
        try {
            try {
                if (!f.a(strArr)) {
                    return false;
                }
                StringBuilder sb = new StringBuilder();
                for (String str : strArr) {
                    sb.append(str);
                    sb.append(com.alipay.sdk.m.u.i.f9802b);
                }
                sb.deleteCharAt(sb.length() - 1);
                SharedPreferences sharedPreferences = this.f25a;
                if (sharedPreferences == null) {
                    return false;
                }
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putString("httpdns_server_ips", sb.toString());
                editorEdit.putLong("schedule_center_last_request_time", System.currentTimeMillis());
                editorEdit.commit();
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
