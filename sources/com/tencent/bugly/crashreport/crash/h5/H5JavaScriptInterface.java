package com.tencent.bugly.crashreport.crash.h5;

import android.webkit.JavascriptInterface;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.inner.InnerApi;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.bb;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.f;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class H5JavaScriptInterface {

    /* renamed from: a, reason: collision with root package name */
    private static HashSet<Integer> f20655a = new HashSet<>();

    /* renamed from: b, reason: collision with root package name */
    private String f20656b = null;

    /* renamed from: c, reason: collision with root package name */
    private Thread f20657c = null;

    /* renamed from: d, reason: collision with root package name */
    private String f20658d = null;

    /* renamed from: e, reason: collision with root package name */
    private Map<String, String> f20659e = null;

    private H5JavaScriptInterface() {
    }

    private static bb a(String str) {
        String string;
        if (str != null && str.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                bb bbVar = new bb();
                String string2 = jSONObject.getString("projectRoot");
                bbVar.f20930a = string2;
                if (string2 == null) {
                    return null;
                }
                String string3 = jSONObject.getString(f.X);
                bbVar.f20931b = string3;
                if (string3 == null) {
                    return null;
                }
                String string4 = jSONObject.getString("url");
                bbVar.f20932c = string4;
                if (string4 == null) {
                    return null;
                }
                String string5 = jSONObject.getString("userAgent");
                bbVar.f20933d = string5;
                if (string5 == null) {
                    return null;
                }
                String string6 = jSONObject.getString(bc.N);
                bbVar.f20934e = string6;
                if (string6 == null) {
                    return null;
                }
                String string7 = jSONObject.getString("name");
                bbVar.f20935f = string7;
                if (string7 == null || string7.equals(TmpConstant.GROUP_ROLE_UNKNOWN) || (string = jSONObject.getString("stacktrace")) == null) {
                    return null;
                }
                int iIndexOf = string.indexOf("\n");
                if (iIndexOf < 0) {
                    al.d("H5 crash stack's format is wrong!", new Object[0]);
                    return null;
                }
                bbVar.f20937h = string.substring(iIndexOf + 1);
                String strSubstring = string.substring(0, iIndexOf);
                bbVar.f20936g = strSubstring;
                int iIndexOf2 = strSubstring.indexOf(":");
                if (iIndexOf2 > 0) {
                    bbVar.f20936g = bbVar.f20936g.substring(iIndexOf2 + 1);
                }
                bbVar.f20938i = jSONObject.getString("file");
                if (bbVar.f20935f == null) {
                    return null;
                }
                long j2 = jSONObject.getLong("lineNumber");
                bbVar.f20939j = j2;
                if (j2 < 0) {
                    return null;
                }
                long j3 = jSONObject.getLong("columnNumber");
                bbVar.f20940k = j3;
                if (j3 < 0) {
                    return null;
                }
                al.a("H5 crash information is following: ", new Object[0]);
                al.a("[projectRoot]: " + bbVar.f20930a, new Object[0]);
                al.a("[context]: " + bbVar.f20931b, new Object[0]);
                al.a("[url]: " + bbVar.f20932c, new Object[0]);
                al.a("[userAgent]: " + bbVar.f20933d, new Object[0]);
                al.a("[language]: " + bbVar.f20934e, new Object[0]);
                al.a("[name]: " + bbVar.f20935f, new Object[0]);
                al.a("[message]: " + bbVar.f20936g, new Object[0]);
                al.a("[stacktrace]: \n" + bbVar.f20937h, new Object[0]);
                al.a("[file]: " + bbVar.f20938i, new Object[0]);
                al.a("[lineNumber]: " + bbVar.f20939j, new Object[0]);
                al.a("[columnNumber]: " + bbVar.f20940k, new Object[0]);
                return bbVar;
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static H5JavaScriptInterface getInstance(CrashReport.a aVar) {
        String string = null;
        if (aVar == null || f20655a.contains(Integer.valueOf(aVar.hashCode()))) {
            return null;
        }
        H5JavaScriptInterface h5JavaScriptInterface = new H5JavaScriptInterface();
        f20655a.add(Integer.valueOf(aVar.hashCode()));
        Thread threadCurrentThread = Thread.currentThread();
        h5JavaScriptInterface.f20657c = threadCurrentThread;
        if (threadCurrentThread != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (int i2 = 2; i2 < threadCurrentThread.getStackTrace().length; i2++) {
                StackTraceElement stackTraceElement = threadCurrentThread.getStackTrace()[i2];
                if (!stackTraceElement.toString().contains("crashreport")) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
            string = sb.toString();
        }
        h5JavaScriptInterface.f20658d = string;
        HashMap map = new HashMap();
        StringBuilder sb2 = new StringBuilder();
        sb2.append((Object) aVar.c());
        map.put("[WebView] ContentDescription", sb2.toString());
        h5JavaScriptInterface.f20659e = map;
        return h5JavaScriptInterface;
    }

    @JavascriptInterface
    public void printLog(String str) {
        al.d("Log from js: %s", str);
    }

    @JavascriptInterface
    public void reportJSException(String str) {
        if (str == null) {
            al.d("Payload from JS is null.", new Object[0]);
            return;
        }
        String strC = ap.c(str.getBytes());
        String str2 = this.f20656b;
        if (str2 != null && str2.equals(strC)) {
            al.d("Same payload from js. Please check whether you've injected bugly.js more than one times.", new Object[0]);
            return;
        }
        this.f20656b = strC;
        al.d("Handling JS exception ...", new Object[0]);
        bb bbVarA = a(str);
        if (bbVarA == null) {
            al.d("Failed to parse payload.", new Object[0]);
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        String str3 = bbVarA.f20930a;
        if (str3 != null) {
            linkedHashMap2.put("[JS] projectRoot", str3);
        }
        String str4 = bbVarA.f20931b;
        if (str4 != null) {
            linkedHashMap2.put("[JS] context", str4);
        }
        String str5 = bbVarA.f20932c;
        if (str5 != null) {
            linkedHashMap2.put("[JS] url", str5);
        }
        String str6 = bbVarA.f20933d;
        if (str6 != null) {
            linkedHashMap2.put("[JS] userAgent", str6);
        }
        String str7 = bbVarA.f20938i;
        if (str7 != null) {
            linkedHashMap2.put("[JS] file", str7);
        }
        long j2 = bbVarA.f20939j;
        if (j2 != 0) {
            linkedHashMap2.put("[JS] lineNumber", Long.toString(j2));
        }
        linkedHashMap.putAll(linkedHashMap2);
        linkedHashMap.putAll(this.f20659e);
        linkedHashMap.put("Java Stack", this.f20658d);
        InnerApi.postH5CrashAsync(this.f20657c, bbVarA.f20935f, bbVarA.f20936g, bbVarA.f20937h, linkedHashMap);
    }
}
