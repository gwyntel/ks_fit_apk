package com.alipay.sdk.m.x;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import com.alipay.sdk.m.j.d;
import com.alipay.sdk.m.u.n;
import com.alipay.sdk.m.x.e;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class d extends com.alipay.sdk.m.x.c implements e.f, e.g, e.h {
    public static final String A = "action";
    public static final String B = "pushWindow";
    public static final String C = "h5JsFuncCallback";
    public static final String D = "sdkInfo";
    public static final String E = "canUseTaoLogin";
    public static final String F = "taoLogin";

    /* renamed from: l, reason: collision with root package name */
    public static final String f9869l = "sdk_result_code:";

    /* renamed from: m, reason: collision with root package name */
    public static final String f9870m = "alipayjsbridge://";

    /* renamed from: n, reason: collision with root package name */
    public static final String f9871n = "onBack";

    /* renamed from: o, reason: collision with root package name */
    public static final String f9872o = "setTitle";

    /* renamed from: p, reason: collision with root package name */
    public static final String f9873p = "onRefresh";

    /* renamed from: q, reason: collision with root package name */
    public static final String f9874q = "showBackButton";

    /* renamed from: r, reason: collision with root package name */
    public static final String f9875r = "onExit";

    /* renamed from: s, reason: collision with root package name */
    public static final String f9876s = "onLoadJs";

    /* renamed from: t, reason: collision with root package name */
    public static final String f9877t = "callNativeFunc";

    /* renamed from: u, reason: collision with root package name */
    public static final String f9878u = "back";

    /* renamed from: v, reason: collision with root package name */
    public static final String f9879v = "title";

    /* renamed from: w, reason: collision with root package name */
    public static final String f9880w = "refresh";

    /* renamed from: x, reason: collision with root package name */
    public static final String f9881x = "backButton";

    /* renamed from: y, reason: collision with root package name */
    public static final String f9882y = "refreshButton";

    /* renamed from: z, reason: collision with root package name */
    public static final String f9883z = "exit";

    /* renamed from: e, reason: collision with root package name */
    public boolean f9884e;

    /* renamed from: f, reason: collision with root package name */
    public String f9885f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f9886g;

    /* renamed from: h, reason: collision with root package name */
    public final com.alipay.sdk.m.s.a f9887h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f9888i;

    /* renamed from: j, reason: collision with root package name */
    public com.alipay.sdk.m.x.e f9889j;

    /* renamed from: k, reason: collision with root package name */
    public com.alipay.sdk.m.x.f f9890k;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            d.this.f9867a.finish();
        }
    }

    public class b extends e {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ com.alipay.sdk.m.x.e f9892a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(com.alipay.sdk.m.x.e eVar) {
            super(null);
            this.f9892a = eVar;
        }

        @Override // com.alipay.sdk.m.x.d.e, android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            this.f9892a.a();
            d.this.f9886g = false;
        }
    }

    public class c extends e {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ com.alipay.sdk.m.x.e f9894a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f9895b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(com.alipay.sdk.m.x.e eVar, String str) {
            super(null);
            this.f9894a = eVar;
            this.f9895b = str;
        }

        @Override // com.alipay.sdk.m.x.d.e, android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            d.this.removeView(this.f9894a);
            d.this.f9889j.a(this.f9895b);
            d.this.f9886g = false;
        }
    }

    /* renamed from: com.alipay.sdk.m.x.d$d, reason: collision with other inner class name */
    public class RunnableC0060d implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Activity f9897a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ SslErrorHandler f9898b;

        /* renamed from: com.alipay.sdk.m.x.d$d$a */
        public class a implements DialogInterface.OnClickListener {
            public a() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                RunnableC0060d.this.f9898b.cancel();
                com.alipay.sdk.m.k.a.b(d.this.f9887h, com.alipay.sdk.m.k.b.f9362k, com.alipay.sdk.m.k.b.A, "2");
                com.alipay.sdk.m.j.b.a(com.alipay.sdk.m.j.b.a());
                RunnableC0060d.this.f9897a.finish();
            }
        }

        public RunnableC0060d(Activity activity, SslErrorHandler sslErrorHandler) {
            this.f9897a = activity;
            this.f9898b = sslErrorHandler;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.alipay.sdk.m.x.b.a(this.f9897a, "安全警告", "安全连接证书校验无效，将无法保证访问数据的安全性，请安装支付宝后重试。", "确定", new a(), null, null);
        }
    }

    public static abstract class e implements Animation.AnimationListener {
        public e() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        public /* synthetic */ e(a aVar) {
            this();
        }
    }

    public static class g implements d.a {

        /* renamed from: a, reason: collision with root package name */
        public final f f9906a;

        /* renamed from: b, reason: collision with root package name */
        public final String f9907b;

        public g(f fVar, String str) {
            this.f9906a = fVar;
            this.f9907b = str;
        }

        @Override // com.alipay.sdk.m.j.d.a
        public void a(boolean z2, JSONObject jSONObject, String str) throws JSONException {
            try {
                this.f9906a.a(new JSONObject().put("success", z2).put(AlinkConstants.KEY_RANDOM, this.f9907b).put("code", jSONObject).put("status", str));
            } catch (JSONException unused) {
            }
        }
    }

    public d(Activity activity, com.alipay.sdk.m.s.a aVar, String str) {
        super(activity, str);
        this.f9884e = true;
        this.f9885f = "GET";
        this.f9886g = false;
        this.f9889j = null;
        this.f9890k = new com.alipay.sdk.m.x.f();
        this.f9887h = aVar;
        g();
    }

    private synchronized boolean e() {
        try {
            if (this.f9890k.b()) {
                this.f9867a.finish();
            } else {
                this.f9886g = true;
                com.alipay.sdk.m.x.e eVar = this.f9889j;
                this.f9889j = this.f9890k.c();
                TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 1.0f, 1, 0.0f, 1, 0.0f);
                translateAnimation.setDuration(400L);
                translateAnimation.setFillAfter(false);
                translateAnimation.setAnimationListener(new b(eVar));
                eVar.setAnimation(translateAnimation);
                removeView(eVar);
                addView(this.f9889j);
            }
        } catch (Throwable th) {
            throw th;
        }
        return true;
    }

    private synchronized void f() {
        try {
            Activity activity = this.f9867a;
            com.alipay.sdk.m.x.e eVar = this.f9889j;
            if (activity != null && eVar != null) {
                if (this.f9884e) {
                    activity.finish();
                } else {
                    eVar.a("javascript:window.AlipayJSBridge.callListener('h5BackAction');");
                }
            }
        } finally {
        }
    }

    private synchronized boolean g() {
        try {
            com.alipay.sdk.m.x.e eVar = new com.alipay.sdk.m.x.e(this.f9867a, this.f9887h, new e.C0062e(!a(), !a()));
            this.f9889j = eVar;
            eVar.setChromeProxy(this);
            this.f9889j.setWebClientProxy(this);
            this.f9889j.setWebEventProxy(this);
            addView(this.f9889j);
        } catch (Exception unused) {
            return false;
        }
        return true;
    }

    private void h() {
        com.alipay.sdk.m.x.e eVar = this.f9889j;
        if (eVar != null) {
            eVar.getWebView().loadUrl("javascript:(function() {\n    if (window.AlipayJSBridge) {\n        return\n    }\n\n    function alipayjsbridgeFunc(url) {\n        var iframe = document.createElement(\"iframe\");\n        iframe.style.width = \"1px\";\n        iframe.style.height = \"1px\";\n        iframe.style.display = \"none\";\n        iframe.src = url;\n        document.body.appendChild(iframe);\n        setTimeout(function() {\n            document.body.removeChild(iframe)\n        }, 100)\n    }\n    window.alipayjsbridgeSetTitle = function(title) {\n        document.title = title;\n        alipayjsbridgeFunc(\"alipayjsbridge://setTitle?title=\" + encodeURIComponent(title))\n    };\n    window.alipayjsbridgeRefresh = function() {\n        alipayjsbridgeFunc(\"alipayjsbridge://onRefresh?\")\n    };\n    window.alipayjsbridgeBack = function() {\n        alipayjsbridgeFunc(\"alipayjsbridge://onBack?\")\n    };\n    window.alipayjsbridgeExit = function(bsucc) {\n        alipayjsbridgeFunc(\"alipayjsbridge://onExit?bsucc=\" + bsucc)\n    };\n    window.alipayjsbridgeShowBackButton = function(bshow) {\n        alipayjsbridgeFunc(\"alipayjsbridge://showBackButton?bshow=\" + bshow)\n    };\n    window.AlipayJSBridge = {\n        version: \"2.0\",\n        addListener: addListener,\n        hasListener: hasListener,\n        callListener: callListener,\n        callNativeFunc: callNativeFunc,\n        callBackFromNativeFunc: callBackFromNativeFunc\n    };\n    var uniqueId = 1;\n    var h5JsCallbackMap = {};\n\n    function iframeCall(paramStr) {\n        setTimeout(function() {\n        \tvar iframe = document.createElement(\"iframe\");\n        \tiframe.style.width = \"1px\";\n        \tiframe.style.height = \"1px\";\n        \tiframe.style.display = \"none\";\n        \tiframe.src = \"alipayjsbridge://callNativeFunc?\" + paramStr;\n        \tvar parent = document.body || document.documentElement;\n        \tparent.appendChild(iframe);\n        \tsetTimeout(function() {\n            \tparent.removeChild(iframe)\n        \t}, 0)\n        }, 0)\n    }\n\n    function callNativeFunc(nativeFuncName, data, h5JsCallback) {\n        var h5JsCallbackId = \"\";\n        if (h5JsCallback) {\n            h5JsCallbackId = \"cb_\" + (uniqueId++) + \"_\" + new Date().getTime();\n            h5JsCallbackMap[h5JsCallbackId] = h5JsCallback\n        }\n        var dataStr = \"\";\n        if (data) {\n            dataStr = encodeURIComponent(JSON.stringify(data))\n        }\n        var paramStr = \"func=\" + nativeFuncName + \"&cbId=\" + h5JsCallbackId + \"&data=\" + dataStr;\n        iframeCall(paramStr)\n    }\n\n    function callBackFromNativeFunc(h5JsCallbackId, data) {\n        var h5JsCallback = h5JsCallbackMap[h5JsCallbackId];\n        if (h5JsCallback) {\n            h5JsCallback(data);\n            delete h5JsCallbackMap[h5JsCallbackId]\n        }\n    }\n    var h5ListenerMap = {};\n\n    function addListener(jsFuncName, jsFunc) {\n        h5ListenerMap[jsFuncName] = jsFunc\n    }\n\n    function hasListener(jsFuncName) {\n        var jsFunc = h5ListenerMap[jsFuncName];\n        if (!jsFunc) {\n            return false\n        }\n        return true\n    }\n\n    function callListener(h5JsFuncName, data, nativeCallbackId) {\n        var responseCallback;\n        if (nativeCallbackId) {\n            responseCallback = function(responseData) {\n                var dataStr = \"\";\n                if (responseData) {\n                    dataStr = encodeURIComponent(JSON.stringify(responseData))\n                }\n                var paramStr = \"func=h5JsFuncCallback\" + \"&cbId=\" + nativeCallbackId + \"&data=\" + dataStr;\n                iframeCall(paramStr)\n            }\n        }\n        var h5JsFunc = h5ListenerMap[h5JsFuncName];\n        if (h5JsFunc) {\n            h5JsFunc(data, responseCallback)\n        } else if (h5JsFuncName == \"h5BackAction\") {\n            if (!window.alipayjsbridgeH5BackAction || !alipayjsbridgeH5BackAction()) {\n                var paramStr = \"func=back\";\n                iframeCall(paramStr)\n            }\n        } else {\n            console.log(\"AlipayJSBridge: no h5JsFunc \" + h5JsFuncName + data)\n        }\n    }\n    var event;\n    if (window.CustomEvent) {\n        event = new CustomEvent(\"alipayjsbridgeready\")\n    } else {\n        event = document.createEvent(\"Event\");\n        event.initEvent(\"alipayjsbridgeready\", true, true)\n    }\n    document.dispatchEvent(event);\n    setTimeout(excuteH5InitFuncs, 0);\n\n    function excuteH5InitFuncs() {\n        if (window.AlipayJSBridgeInitArray) {\n            var h5InitFuncs = window.AlipayJSBridgeInitArray;\n            delete window.AlipayJSBridgeInitArray;\n            for (var i = 0; i < h5InitFuncs.length; i++) {\n                try {\n                    h5InitFuncs[i](AlipayJSBridge)\n                } catch (e) {\n                    setTimeout(function() {\n                        throw e\n                    })\n                }\n            }\n        }\n    }\n})();\n;window.AlipayJSBridge.callListener('h5PageFinished');");
        }
    }

    private synchronized void i() {
        try {
            WebView webView = this.f9889j.getWebView();
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                com.alipay.sdk.m.x.f fVar = this.f9890k;
                if (fVar == null || fVar.b()) {
                    a(false);
                } else {
                    e();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.alipay.sdk.m.x.c
    public synchronized void c() {
        this.f9889j.a();
        this.f9890k.a();
    }

    @Override // com.alipay.sdk.m.x.e.g
    public synchronized boolean d(com.alipay.sdk.m.x.e eVar, String str) {
        com.alipay.sdk.m.k.a.a(this.f9887h, com.alipay.sdk.m.k.b.f9364l, "h5ld", SystemClock.elapsedRealtime() + "|" + n.i(str));
        if (!TextUtils.isEmpty(str) && !str.endsWith(".apk")) {
            h();
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public synchronized boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.f9886g ? true : super.onInterceptTouchEvent(motionEvent);
    }

    @Override // com.alipay.sdk.m.x.c
    public synchronized boolean b() {
        try {
            Activity activity = this.f9867a;
            if (activity == null) {
                return true;
            }
            if (!a()) {
                if (!this.f9886g) {
                    f();
                }
                return true;
            }
            com.alipay.sdk.m.x.e eVar = this.f9889j;
            if (eVar != null && eVar.getWebView() != null) {
                if (!eVar.getWebView().canGoBack()) {
                    com.alipay.sdk.m.j.b.a(com.alipay.sdk.m.j.b.a());
                    activity.finish();
                } else if (d()) {
                    com.alipay.sdk.m.j.c cVarB = com.alipay.sdk.m.j.c.b(com.alipay.sdk.m.j.c.NETWORK_ERROR.b());
                    com.alipay.sdk.m.j.b.a(com.alipay.sdk.m.j.b.a(cVarB.b(), cVarB.a(), ""));
                    activity.finish();
                }
                return true;
            }
            activity.finish();
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void a(String str, String str2, boolean z2) {
        this.f9885f = str2;
        this.f9889j.getTitle().setText(str);
        this.f9884e = z2;
    }

    @Override // com.alipay.sdk.m.x.e.f
    public synchronized void c(com.alipay.sdk.m.x.e eVar, String str) {
        if (!str.startsWith("http") && !eVar.getUrl().endsWith(str)) {
            this.f9889j.getTitle().setText(str);
        }
    }

    public boolean d() {
        return this.f9888i;
    }

    private synchronized void a(boolean z2) {
        com.alipay.sdk.m.j.b.a(z2);
        this.f9867a.finish();
    }

    public static class f {

        /* renamed from: a, reason: collision with root package name */
        public final WeakReference<com.alipay.sdk.m.x.e> f9901a;

        /* renamed from: b, reason: collision with root package name */
        public final String f9902b;

        /* renamed from: c, reason: collision with root package name */
        public final String f9903c;

        /* renamed from: d, reason: collision with root package name */
        public final JSONObject f9904d;

        /* renamed from: e, reason: collision with root package name */
        public boolean f9905e = false;

        public f(com.alipay.sdk.m.x.e eVar, String str, String str2, JSONObject jSONObject) {
            this.f9901a = new WeakReference<>(eVar);
            this.f9902b = str;
            this.f9903c = str2;
            this.f9904d = jSONObject;
        }

        public void a(JSONObject jSONObject) {
            com.alipay.sdk.m.x.e eVar;
            if (this.f9905e || (eVar = (com.alipay.sdk.m.x.e) n.a(this.f9901a)) == null) {
                return;
            }
            this.f9905e = true;
            eVar.a(String.format("javascript:window.AlipayJSBridge.callBackFromNativeFunc('%s','%s');", a(this.f9903c), a(jSONObject.toString())));
        }

        public static String a(String str) {
            return TextUtils.isEmpty(str) ? "" : str.replace("'", "");
        }
    }

    @Override // com.alipay.sdk.m.x.c
    public synchronized void a(String str) {
        try {
            if ("POST".equals(this.f9885f)) {
                this.f9889j.a(str, (byte[]) null);
            } else {
                this.f9889j.a(str);
            }
            com.alipay.sdk.m.x.c.a(this.f9889j.getWebView());
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.alipay.sdk.m.x.e.f
    public synchronized boolean a(com.alipay.sdk.m.x.e eVar, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        try {
            if (str2.startsWith("<head>") && str2.contains(f9869l)) {
                this.f9867a.runOnUiThread(new a());
            }
            jsPromptResult.cancel();
        } catch (Throwable th) {
            throw th;
        }
        return true;
    }

    private synchronized boolean b(String str, String str2) {
        com.alipay.sdk.m.x.e eVar = this.f9889j;
        try {
            com.alipay.sdk.m.x.e eVar2 = new com.alipay.sdk.m.x.e(this.f9867a, this.f9887h, new e.C0062e(!a(), !a()));
            this.f9889j = eVar2;
            eVar2.setChromeProxy(this);
            this.f9889j.setWebClientProxy(this);
            this.f9889j.setWebEventProxy(this);
            if (!TextUtils.isEmpty(str2)) {
                this.f9889j.getTitle().setText(str2);
            }
            this.f9886g = true;
            this.f9890k.a(eVar);
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            translateAnimation.setDuration(400L);
            translateAnimation.setFillAfter(false);
            translateAnimation.setAnimationListener(new c(eVar, str));
            this.f9889j.setAnimation(translateAnimation);
            addView(this.f9889j);
        } catch (Throwable unused) {
            return false;
        }
        return true;
    }

    @Override // com.alipay.sdk.m.x.e.g
    public synchronized boolean a(com.alipay.sdk.m.x.e eVar, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            Activity activity = this.f9867a;
            if (activity == null) {
                return true;
            }
            if (n.a(this.f9887h, str, activity)) {
                return true;
            }
            if (str.startsWith(f9870m)) {
                b(str.substring(17));
            } else if (TextUtils.equals(str, com.alipay.sdk.m.l.a.f9423p)) {
                a(false);
            } else if (!str.startsWith("http://") && !str.startsWith("https://")) {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse(str));
                    activity.startActivity(intent);
                } catch (Throwable th) {
                    com.alipay.sdk.m.k.a.a(this.f9887h, com.alipay.sdk.m.k.b.f9364l, th);
                }
            } else {
                this.f9889j.a(str);
            }
            return true;
        } catch (Throwable th2) {
            throw th2;
        }
    }

    @Override // com.alipay.sdk.m.x.e.g
    public synchronized boolean a(com.alipay.sdk.m.x.e eVar, int i2, String str, String str2) {
        this.f9888i = true;
        com.alipay.sdk.m.k.a.b(this.f9887h, com.alipay.sdk.m.k.b.f9362k, com.alipay.sdk.m.k.b.f9390y, "onReceivedError:" + i2 + "|" + str2);
        eVar.getRefreshButton().setVisibility(0);
        return false;
    }

    @Override // com.alipay.sdk.m.x.e.g
    public synchronized boolean b(com.alipay.sdk.m.x.e eVar, String str) {
        com.alipay.sdk.m.k.a.a(this.f9887h, com.alipay.sdk.m.k.b.f9364l, "h5ldd", SystemClock.elapsedRealtime() + "|" + n.i(str));
        h();
        eVar.getRefreshButton().setVisibility(0);
        return true;
    }

    @Override // com.alipay.sdk.m.x.e.g
    public synchronized boolean a(com.alipay.sdk.m.x.e eVar, SslErrorHandler sslErrorHandler, SslError sslError) {
        Activity activity = this.f9867a;
        if (activity == null) {
            return true;
        }
        com.alipay.sdk.m.k.a.b(this.f9887h, com.alipay.sdk.m.k.b.f9362k, com.alipay.sdk.m.k.b.f9392z, "2-" + sslError);
        activity.runOnUiThread(new RunnableC0060d(activity, sslErrorHandler));
        return true;
    }

    private synchronized void b(String str) {
        try {
            Map<String, String> mapB = n.b(this.f9887h, str);
            if (str.startsWith(f9877t)) {
                a(mapB.get("func"), mapB.get("cbId"), mapB.get("data"));
            } else if (str.startsWith(f9871n)) {
                i();
            } else if (str.startsWith(f9872o) && mapB.containsKey("title")) {
                this.f9889j.getTitle().setText(mapB.get("title"));
            } else if (str.startsWith(f9873p)) {
                this.f9889j.getWebView().reload();
            } else if (str.startsWith(f9874q) && mapB.containsKey("bshow")) {
                this.f9889j.getBackButton().setVisibility(TextUtils.equals("true", mapB.get("bshow")) ? 0 : 4);
            } else if (str.startsWith(f9875r)) {
                com.alipay.sdk.m.j.b.a(mapB.get("result"));
                a(TextUtils.equals("true", mapB.get("bsucc")));
            } else if (str.startsWith(f9876s)) {
                this.f9889j.a("javascript:(function() {\n    if (window.AlipayJSBridge) {\n        return\n    }\n\n    function alipayjsbridgeFunc(url) {\n        var iframe = document.createElement(\"iframe\");\n        iframe.style.width = \"1px\";\n        iframe.style.height = \"1px\";\n        iframe.style.display = \"none\";\n        iframe.src = url;\n        document.body.appendChild(iframe);\n        setTimeout(function() {\n            document.body.removeChild(iframe)\n        }, 100)\n    }\n    window.alipayjsbridgeSetTitle = function(title) {\n        document.title = title;\n        alipayjsbridgeFunc(\"alipayjsbridge://setTitle?title=\" + encodeURIComponent(title))\n    };\n    window.alipayjsbridgeRefresh = function() {\n        alipayjsbridgeFunc(\"alipayjsbridge://onRefresh?\")\n    };\n    window.alipayjsbridgeBack = function() {\n        alipayjsbridgeFunc(\"alipayjsbridge://onBack?\")\n    };\n    window.alipayjsbridgeExit = function(bsucc) {\n        alipayjsbridgeFunc(\"alipayjsbridge://onExit?bsucc=\" + bsucc)\n    };\n    window.alipayjsbridgeShowBackButton = function(bshow) {\n        alipayjsbridgeFunc(\"alipayjsbridge://showBackButton?bshow=\" + bshow)\n    };\n    window.AlipayJSBridge = {\n        version: \"2.0\",\n        addListener: addListener,\n        hasListener: hasListener,\n        callListener: callListener,\n        callNativeFunc: callNativeFunc,\n        callBackFromNativeFunc: callBackFromNativeFunc\n    };\n    var uniqueId = 1;\n    var h5JsCallbackMap = {};\n\n    function iframeCall(paramStr) {\n        setTimeout(function() {\n        \tvar iframe = document.createElement(\"iframe\");\n        \tiframe.style.width = \"1px\";\n        \tiframe.style.height = \"1px\";\n        \tiframe.style.display = \"none\";\n        \tiframe.src = \"alipayjsbridge://callNativeFunc?\" + paramStr;\n        \tvar parent = document.body || document.documentElement;\n        \tparent.appendChild(iframe);\n        \tsetTimeout(function() {\n            \tparent.removeChild(iframe)\n        \t}, 0)\n        }, 0)\n    }\n\n    function callNativeFunc(nativeFuncName, data, h5JsCallback) {\n        var h5JsCallbackId = \"\";\n        if (h5JsCallback) {\n            h5JsCallbackId = \"cb_\" + (uniqueId++) + \"_\" + new Date().getTime();\n            h5JsCallbackMap[h5JsCallbackId] = h5JsCallback\n        }\n        var dataStr = \"\";\n        if (data) {\n            dataStr = encodeURIComponent(JSON.stringify(data))\n        }\n        var paramStr = \"func=\" + nativeFuncName + \"&cbId=\" + h5JsCallbackId + \"&data=\" + dataStr;\n        iframeCall(paramStr)\n    }\n\n    function callBackFromNativeFunc(h5JsCallbackId, data) {\n        var h5JsCallback = h5JsCallbackMap[h5JsCallbackId];\n        if (h5JsCallback) {\n            h5JsCallback(data);\n            delete h5JsCallbackMap[h5JsCallbackId]\n        }\n    }\n    var h5ListenerMap = {};\n\n    function addListener(jsFuncName, jsFunc) {\n        h5ListenerMap[jsFuncName] = jsFunc\n    }\n\n    function hasListener(jsFuncName) {\n        var jsFunc = h5ListenerMap[jsFuncName];\n        if (!jsFunc) {\n            return false\n        }\n        return true\n    }\n\n    function callListener(h5JsFuncName, data, nativeCallbackId) {\n        var responseCallback;\n        if (nativeCallbackId) {\n            responseCallback = function(responseData) {\n                var dataStr = \"\";\n                if (responseData) {\n                    dataStr = encodeURIComponent(JSON.stringify(responseData))\n                }\n                var paramStr = \"func=h5JsFuncCallback\" + \"&cbId=\" + nativeCallbackId + \"&data=\" + dataStr;\n                iframeCall(paramStr)\n            }\n        }\n        var h5JsFunc = h5ListenerMap[h5JsFuncName];\n        if (h5JsFunc) {\n            h5JsFunc(data, responseCallback)\n        } else if (h5JsFuncName == \"h5BackAction\") {\n            if (!window.alipayjsbridgeH5BackAction || !alipayjsbridgeH5BackAction()) {\n                var paramStr = \"func=back\";\n                iframeCall(paramStr)\n            }\n        } else {\n            console.log(\"AlipayJSBridge: no h5JsFunc \" + h5JsFuncName + data)\n        }\n    }\n    var event;\n    if (window.CustomEvent) {\n        event = new CustomEvent(\"alipayjsbridgeready\")\n    } else {\n        event = document.createEvent(\"Event\");\n        event.initEvent(\"alipayjsbridgeready\", true, true)\n    }\n    document.dispatchEvent(event);\n    setTimeout(excuteH5InitFuncs, 0);\n\n    function excuteH5InitFuncs() {\n        if (window.AlipayJSBridgeInitArray) {\n            var h5InitFuncs = window.AlipayJSBridgeInitArray;\n            delete window.AlipayJSBridgeInitArray;\n            for (var i = 0; i < h5InitFuncs.length; i++) {\n                try {\n                    h5InitFuncs[i](AlipayJSBridge)\n                } catch (e) {\n                    setTimeout(function() {\n                        throw e\n                    })\n                }\n            }\n        }\n    }\n})();\n");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void a(java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.x.d.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    @Override // com.alipay.sdk.m.x.e.h
    public synchronized void b(com.alipay.sdk.m.x.e eVar) {
        f();
    }

    @Override // com.alipay.sdk.m.x.e.h
    public synchronized void a(com.alipay.sdk.m.x.e eVar) {
        eVar.getWebView().reload();
        eVar.getRefreshButton().setVisibility(4);
    }
}
