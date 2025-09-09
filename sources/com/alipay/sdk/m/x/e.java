package com.alipay.sdk.m.x;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.media3.datasource.cache.CacheDataSink;
import com.alipay.sdk.m.u.k;
import com.alipay.sdk.m.u.n;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class e extends LinearLayout {

    /* renamed from: m, reason: collision with root package name */
    public static Handler f9908m = new Handler(Looper.getMainLooper());

    /* renamed from: a, reason: collision with root package name */
    public ImageView f9909a;

    /* renamed from: b, reason: collision with root package name */
    public TextView f9910b;

    /* renamed from: c, reason: collision with root package name */
    public ImageView f9911c;

    /* renamed from: d, reason: collision with root package name */
    public ProgressBar f9912d;

    /* renamed from: e, reason: collision with root package name */
    public WebView f9913e;

    /* renamed from: f, reason: collision with root package name */
    public final C0062e f9914f;

    /* renamed from: g, reason: collision with root package name */
    public f f9915g;

    /* renamed from: h, reason: collision with root package name */
    public g f9916h;

    /* renamed from: i, reason: collision with root package name */
    public h f9917i;

    /* renamed from: j, reason: collision with root package name */
    public final com.alipay.sdk.m.s.a f9918j;

    /* renamed from: k, reason: collision with root package name */
    public View.OnClickListener f9919k;

    /* renamed from: l, reason: collision with root package name */
    public final float f9920l;

    public class a implements View.OnClickListener {

        /* renamed from: com.alipay.sdk.m.x.e$a$a, reason: collision with other inner class name */
        public class RunnableC0061a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ View f9922a;

            public RunnableC0061a(View view) {
                this.f9922a = view;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.f9922a.setEnabled(true);
            }
        }

        public a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            h hVar = e.this.f9917i;
            if (hVar != null) {
                view.setEnabled(false);
                e.f9908m.postDelayed(new RunnableC0061a(view), 256L);
                if (view == e.this.f9909a) {
                    hVar.b(e.this);
                } else if (view == e.this.f9911c) {
                    hVar.a(e.this);
                }
            }
        }
    }

    public class b implements DownloadListener {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f9924a;

        public b(Context context) {
            this.f9924a = context;
        }

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(String str, String str2, String str3, String str4, long j2) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                intent.setFlags(268435456);
                this.f9924a.startActivity(intent);
            } catch (Throwable unused) {
            }
        }
    }

    public class c extends WebChromeClient {
        public c() {
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
            return e.this.f9915g.a(e.this, str, str2, str3, jsPromptResult);
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i2) {
            if (!e.this.f9914f.f9929b) {
                e.this.f9912d.setVisibility(8);
            } else {
                if (i2 > 90) {
                    e.this.f9912d.setVisibility(4);
                    return;
                }
                if (e.this.f9912d.getVisibility() == 4) {
                    e.this.f9912d.setVisibility(0);
                }
                e.this.f9912d.setProgress(i2);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView webView, String str) {
            e.this.f9915g.c(e.this, str);
        }
    }

    public class d extends WebViewClient {
        public d() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            if (e.this.f9916h.b(e.this, str)) {
                return;
            }
            super.onPageFinished(webView, str);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (e.this.f9916h.d(e.this, str)) {
                return;
            }
            super.onPageFinished(webView, str);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i2, String str, String str2) {
            if (e.this.f9916h.a(e.this, i2, str, str2)) {
                return;
            }
            super.onReceivedError(webView, i2, str, str2);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (e.this.f9916h.a(e.this, sslErrorHandler, sslError)) {
                return;
            }
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (e.this.f9916h.a(e.this, str)) {
                return true;
            }
            return super.shouldOverrideUrlLoading(webView, str);
        }
    }

    /* renamed from: com.alipay.sdk.m.x.e$e, reason: collision with other inner class name */
    public static final class C0062e {

        /* renamed from: a, reason: collision with root package name */
        public boolean f9928a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f9929b;

        public C0062e(boolean z2, boolean z3) {
            this.f9928a = z2;
            this.f9929b = z3;
        }
    }

    public interface f {
        boolean a(e eVar, String str, String str2, String str3, JsPromptResult jsPromptResult);

        void c(e eVar, String str);
    }

    public interface g {
        boolean a(e eVar, int i2, String str, String str2);

        boolean a(e eVar, SslErrorHandler sslErrorHandler, SslError sslError);

        boolean a(e eVar, String str);

        boolean b(e eVar, String str);

        boolean d(e eVar, String str);
    }

    public interface h {
        void a(e eVar);

        void b(e eVar);
    }

    public e(Context context, com.alipay.sdk.m.s.a aVar, C0062e c0062e) {
        this(context, null, aVar, c0062e);
    }

    public ImageView getBackButton() {
        return this.f9909a;
    }

    public ProgressBar getProgressbar() {
        return this.f9912d;
    }

    public ImageView getRefreshButton() {
        return this.f9911c;
    }

    public TextView getTitle() {
        return this.f9910b;
    }

    public String getUrl() {
        return this.f9913e.getUrl();
    }

    public WebView getWebView() {
        return this.f9913e;
    }

    public void setChromeProxy(f fVar) {
        this.f9915g = fVar;
        if (fVar == null) {
            this.f9913e.setWebChromeClient(null);
        } else {
            this.f9913e.setWebChromeClient(new c());
        }
    }

    public void setWebClientProxy(g gVar) {
        this.f9916h = gVar;
        if (gVar == null) {
            this.f9913e.setWebViewClient(null);
        } else {
            this.f9913e.setWebViewClient(new d());
        }
    }

    public void setWebEventProxy(h hVar) {
        this.f9917i = hVar;
    }

    public e(Context context, AttributeSet attributeSet, com.alipay.sdk.m.s.a aVar, C0062e c0062e) {
        super(context, attributeSet);
        this.f9919k = new a();
        this.f9914f = c0062e == null ? new C0062e(false, false) : c0062e;
        this.f9918j = aVar;
        this.f9920l = context.getResources().getDisplayMetrics().density;
        setOrientation(1);
        a(context);
        b(context);
        c(context);
    }

    private void a(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundColor(-218103809);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(16);
        linearLayout.setVisibility(this.f9914f.f9928a ? 0 : 8);
        ImageView imageView = new ImageView(context);
        this.f9909a = imageView;
        imageView.setOnClickListener(this.f9919k);
        ImageView imageView2 = this.f9909a;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageView2.setScaleType(scaleType);
        this.f9909a.setImageDrawable(k.a(k.f9809a, context));
        this.f9909a.setPadding(a(12), 0, a(12), 0);
        linearLayout.addView(this.f9909a, new LinearLayout.LayoutParams(-2, -2));
        View view = new View(context);
        view.setBackgroundColor(-2500135);
        linearLayout.addView(view, new LinearLayout.LayoutParams(a(1), a(25)));
        TextView textView = new TextView(context);
        this.f9910b = textView;
        textView.setTextColor(-15658735);
        this.f9910b.setTextSize(17.0f);
        this.f9910b.setMaxLines(1);
        this.f9910b.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(a(17), 0, 0, 0);
        layoutParams.weight = 1.0f;
        linearLayout.addView(this.f9910b, layoutParams);
        ImageView imageView3 = new ImageView(context);
        this.f9911c = imageView3;
        imageView3.setOnClickListener(this.f9919k);
        this.f9911c.setScaleType(scaleType);
        this.f9911c.setImageDrawable(k.a(k.f9810b, context));
        this.f9911c.setPadding(a(12), 0, a(12), 0);
        linearLayout.addView(this.f9911c, new LinearLayout.LayoutParams(-2, -2));
        addView(linearLayout, new LinearLayout.LayoutParams(-1, a(48)));
    }

    private void c(Context context) {
        WebView webView = new WebView(context);
        this.f9913e = webView;
        webView.setVerticalScrollbarOverlay(true);
        a(this.f9913e, context);
        WebSettings settings = this.f9913e.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setUseWideViewPort(true);
        settings.setAppCacheMaxSize(CacheDataSink.DEFAULT_FRAGMENT_SIZE);
        settings.setAppCachePath(context.getCacheDir().getAbsolutePath());
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(1);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(false);
        this.f9913e.setVerticalScrollbarOverlay(true);
        this.f9913e.setDownloadListener(new b(context));
        try {
            try {
                this.f9913e.removeJavascriptInterface("searchBoxJavaBridge_");
                this.f9913e.removeJavascriptInterface("accessibility");
                this.f9913e.removeJavascriptInterface("accessibilityTraversal");
            } catch (Exception unused) {
                Method method = this.f9913e.getClass().getMethod("removeJavascriptInterface", null);
                if (method != null) {
                    method.invoke(this.f9913e, "searchBoxJavaBridge_");
                    method.invoke(this.f9913e, "accessibility");
                    method.invoke(this.f9913e, "accessibilityTraversal");
                }
            }
        } catch (Throwable unused2) {
        }
        com.alipay.sdk.m.x.c.a(this.f9913e);
        addView(this.f9913e, new LinearLayout.LayoutParams(-1, -1));
    }

    private void b(Context context) {
        ProgressBar progressBar = new ProgressBar(context, null, R.style.Widget.ProgressBar.Horizontal);
        this.f9912d = progressBar;
        progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_horizontal));
        this.f9912d.setMax(100);
        this.f9912d.setBackgroundColor(-218103809);
        addView(this.f9912d, new LinearLayout.LayoutParams(-1, a(2)));
    }

    public void a(WebView webView, Context context) {
        String userAgentString = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(userAgentString + n.g(context));
    }

    public void a(String str) {
        this.f9913e.loadUrl(str);
        com.alipay.sdk.m.x.c.a(this.f9913e);
    }

    public void a(String str, byte[] bArr) {
        this.f9913e.postUrl(str, bArr);
    }

    public void a() {
        removeAllViews();
        this.f9913e.removeAllViews();
        this.f9913e.setWebViewClient(null);
        this.f9913e.setWebChromeClient(null);
        this.f9913e.destroy();
    }

    private int a(int i2) {
        return (int) (i2 * this.f9920l);
    }
}
