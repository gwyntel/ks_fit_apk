package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class aa {

    /* renamed from: c, reason: collision with root package name */
    private static volatile aa f21276c;

    /* renamed from: a, reason: collision with root package name */
    private y f21277a = new z();

    /* renamed from: b, reason: collision with root package name */
    private String f21278b;

    /* renamed from: d, reason: collision with root package name */
    private List<a> f21279d;

    /* renamed from: e, reason: collision with root package name */
    private String f21280e;

    public interface a {
        void a(String str, long j2, long j3, long j4);

        void a(String str, String str2, long j2, long j3, long j4);
    }

    private aa() {
    }

    public static aa a() {
        if (f21276c == null) {
            synchronized (aa.class) {
                try {
                    if (f21276c == null) {
                        f21276c = new aa();
                    }
                } finally {
                }
            }
        }
        return f21276c;
    }

    private String f(Context context) {
        try {
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(context).edit();
            editorEdit.putString(w.f21936d, d(context));
            editorEdit.commit();
        } catch (Exception unused) {
        }
        long jH = h(context);
        long jI = i(context);
        String str = this.f21278b;
        long jA = w.a(context);
        long j2 = jA * 5000;
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>*** 读取 foreground count 值完成，count次数：" + jA);
        if (!FieldManager.allow(com.umeng.commonsdk.utils.d.E)) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>*** foreground count druation云控参数关闭。");
        } else if (UMWorkDispatch.eventHasExist()) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>*** 读取 foreground count druation值完成，终止checker timer.");
            UMWorkDispatch.removeEvent();
        } else {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>*** 读取 foreground count druation值完成，无未处理check timer事件。");
        }
        a(jI, jH, j2, str, false);
        this.f21278b = this.f21277a.a(context);
        a(jI, jH, j2, str, true);
        this.f21277a.a(context, this.f21278b);
        return this.f21278b;
    }

    private boolean g(Context context) {
        return !TextUtils.isEmpty(this.f21278b) && k.a(context).a(this.f21278b) > 0;
    }

    private long h(Context context) {
        return a(context, w.f21938f);
    }

    private long i(Context context) {
        return a(context, w.f21933a);
    }

    private boolean j(Context context) {
        Context appContext = UMGlobalContext.getAppContext(context);
        try {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(appContext);
            long j2 = sharedPreferences.getLong(w.f21937e, 0L);
            long j3 = sharedPreferences.getLong(w.f21938f, 0L);
            if (FieldManager.allow(com.umeng.commonsdk.utils.d.E) && j2 > 0 && j3 == 0) {
                long jA = w.a(appContext);
                if (jA > 0) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> last session end time stamp = 0, reconstruct it by foreground count value.");
                    j3 = j2 + (jA * 5000);
                }
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> interval of last session is: " + (j3 - j2));
            return this.f21277a.a(j2, j3);
        } catch (Exception unused) {
            return false;
        }
    }

    public long b() {
        return this.f21277a.a();
    }

    public String c(Context context) {
        Context appContext = UMGlobalContext.getAppContext(context);
        if (appContext == null) {
            return "";
        }
        try {
            this.f21278b = f(appContext);
        } catch (Exception unused) {
        }
        return this.f21278b;
    }

    public String d(Context context) {
        if (TextUtils.isEmpty(this.f21278b)) {
            try {
                this.f21278b = PreferenceWrapper.getDefault(context).getString("session_id", null);
            } catch (Exception unused) {
            }
        }
        return this.f21278b;
    }

    public boolean e(Context context) {
        if (TextUtils.isEmpty(this.f21278b)) {
            this.f21278b = d(context);
        }
        return TextUtils.isEmpty(this.f21278b) || j(context) || g(context);
    }

    public synchronized String b(Context context) {
        Context appContext = UMGlobalContext.getAppContext(context);
        if (appContext == null) {
            return "";
        }
        this.f21278b = d(appContext);
        if (e(appContext)) {
            try {
                this.f21278b = f(appContext);
            } catch (Exception unused) {
            }
        }
        return this.f21278b;
    }

    public void a(long j2) {
        this.f21277a.a(j2);
    }

    public String a(Context context) {
        Context appContext = UMGlobalContext.getAppContext(context);
        if (appContext == null) {
            return "";
        }
        String string = "";
        try {
            synchronized (aa.class) {
                string = PreferenceWrapper.getDefault(appContext).getString(w.f21936d, "");
            }
        } catch (Exception unused) {
        }
        return string;
    }

    public void b(a aVar) {
        List<a> list;
        if (aVar == null || (list = this.f21279d) == null || list.size() == 0) {
            return;
        }
        this.f21279d.remove(aVar);
    }

    public String a(Context context, long j2) {
        if (TextUtils.isEmpty(this.f21280e)) {
            String str = "SUB" + j2;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(String.format("%0" + (32 - str.length()) + "d", 0));
            this.f21280e = sb.toString();
        }
        return this.f21280e;
    }

    private long a(Context context, String str) {
        long j2;
        try {
            j2 = PreferenceWrapper.getDefault(context).getLong(str, 0L);
        } catch (Exception unused) {
            j2 = 0;
        }
        return j2 <= 0 ? System.currentTimeMillis() : j2;
    }

    private void a(long j2, long j3, long j4, String str, boolean z2) {
        List<a> list = this.f21279d;
        if (list != null) {
            for (a aVar : list) {
                if (z2) {
                    try {
                        aVar.a(str, this.f21278b, j2, j3, j4);
                    } catch (Exception unused) {
                    }
                } else {
                    aVar.a(this.f21278b, j2, j3, j4);
                }
            }
        }
    }

    public void a(a aVar) {
        if (aVar == null) {
            return;
        }
        if (this.f21279d == null) {
            this.f21279d = new ArrayList();
        }
        if (this.f21279d.contains(aVar)) {
            return;
        }
        this.f21279d.add(aVar);
    }
}
