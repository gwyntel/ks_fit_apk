package com.umeng.analytics.pro;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.k;
import com.umeng.analytics.vshelper.PageNameMonitor;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class x {

    /* renamed from: c, reason: collision with root package name */
    private static final int f21947c = 5;

    /* renamed from: d, reason: collision with root package name */
    private static JSONArray f21948d = new JSONArray();

    /* renamed from: e, reason: collision with root package name */
    private static Object f21949e = new Object();

    /* renamed from: f, reason: collision with root package name */
    private final Map<String, Long> f21952f = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    Stack<String> f21950a = new Stack<>();

    /* renamed from: b, reason: collision with root package name */
    com.umeng.analytics.vshelper.a f21951b = PageNameMonitor.getInstance();

    protected int a() {
        return 2;
    }

    public void b(String str) {
        Long l2;
        Context appContext;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!this.f21952f.containsKey(str)) {
            if (UMConfigure.isDebugLog() && this.f21950a.size() == 0) {
                UMLog.aq(l.G, 0, "\\|", new String[]{"@"}, new String[]{str}, null, null);
                return;
            }
            return;
        }
        synchronized (this.f21952f) {
            l2 = this.f21952f.get(str);
            this.f21952f.remove(str);
        }
        if (l2 == null) {
            return;
        }
        if (UMConfigure.isDebugLog() && this.f21950a.size() > 0 && str.equals(this.f21950a.peek())) {
            this.f21950a.pop();
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - l2.longValue();
        synchronized (f21949e) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(f.f21700v, str);
                jSONObject.put("duration", jCurrentTimeMillis);
                jSONObject.put(f.f21702x, l2);
                jSONObject.put("type", a());
                f21948d.put(jSONObject);
                if (f21948d.length() >= 5 && (appContext = UMGlobalContext.getAppContext(null)) != null) {
                    UMWorkDispatch.sendEvent(appContext, 4099, CoreProtocol.getInstance(appContext), null);
                }
            } catch (Throwable unused) {
            }
        }
        if (!UMConfigure.isDebugLog() || this.f21950a.size() == 0) {
            return;
        }
        UMLog.aq(l.E, 0, "\\|", new String[]{"@"}, new String[]{str}, null, null);
    }

    public static void a(Context context) {
        String string;
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                synchronized (f21949e) {
                    string = f21948d.toString();
                    f21948d = new JSONArray();
                }
                if (string.length() > 0) {
                    jSONObject.put("__a", new JSONArray(string));
                    if (jSONObject.length() > 0) {
                        k.a(context).a(w.a().c(), jSONObject, k.a.PAGE);
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (UMConfigure.isDebugLog() && this.f21950a.size() != 0) {
            String[] strArr = {this.f21950a.peek()};
            UMLog.aq(l.F, 0, "\\|", new String[]{"@"}, strArr, null, null);
        }
        this.f21951b.customPageBegin(str);
        synchronized (this.f21952f) {
            try {
                this.f21952f.put(str, Long.valueOf(System.currentTimeMillis()));
                if (UMConfigure.isDebugLog()) {
                    this.f21950a.push(str);
                }
            } finally {
            }
        }
    }

    public void b() {
        String key;
        synchronized (this.f21952f) {
            try {
                key = null;
                long j2 = 0;
                for (Map.Entry<String, Long> entry : this.f21952f.entrySet()) {
                    if (entry.getValue().longValue() > j2) {
                        long jLongValue = entry.getValue().longValue();
                        key = entry.getKey();
                        j2 = jLongValue;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (key != null) {
            b(key);
        }
    }
}
