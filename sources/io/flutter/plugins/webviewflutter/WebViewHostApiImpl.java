package io.flutter.plugins.webviewflutter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.flutter.embedding.android.FlutterView;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import io.flutter.plugins.webviewflutter.WebChromeClientHostApiImpl;
import io.flutter.plugins.webviewflutter.WebViewHostApiImpl;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WebViewHostApiImpl implements GeneratedAndroidWebView.WebViewHostApi {
    private final BinaryMessenger binaryMessenger;
    private Context context;
    private final InstanceManager instanceManager;
    private final WebViewProxy webViewProxy;

    @SuppressLint({"ViewConstructor"})
    public static class WebViewPlatformView extends WebView implements PlatformView {
        private WebViewFlutterApiImpl api;
        private WebChromeClientHostApiImpl.SecureWebChromeClient currentWebChromeClient;
        private WebViewClient currentWebViewClient;

        @NonNull
        private final AndroidSdkChecker sdkChecker;

        @VisibleForTesting
        interface AndroidSdkChecker {
            @ChecksSdkIntAtLeast(parameter = 0)
            boolean sdkIsAtLeast(int i2);
        }

        public WebViewPlatformView(@NonNull Context context, @NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
            this(context, binaryMessenger, instanceManager, new AndroidSdkChecker() { // from class: io.flutter.plugins.webviewflutter.o5
                @Override // io.flutter.plugins.webviewflutter.WebViewHostApiImpl.WebViewPlatformView.AndroidSdkChecker
                public final boolean sdkIsAtLeast(int i2) {
                    return WebViewHostApiImpl.WebViewPlatformView.lambda$new$0(i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$0(int i2) {
            return Build.VERSION.SDK_INT >= i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onScrollChanged$1(Void r02) {
        }

        private FlutterView tryFindFlutterView() {
            ViewParent parent = this;
            while (parent.getParent() != null) {
                parent = parent.getParent();
                if (parent instanceof FlutterView) {
                    return (FlutterView) parent;
                }
            }
            return null;
        }

        @Override // io.flutter.plugin.platform.PlatformView
        public void dispose() {
        }

        @Override // io.flutter.plugin.platform.PlatformView
        @Nullable
        public View getView() {
            return this;
        }

        @Override // android.webkit.WebView
        @Nullable
        public WebChromeClient getWebChromeClient() {
            return this.currentWebChromeClient;
        }

        @Override // android.webkit.WebView, android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            FlutterView flutterViewTryFindFlutterView;
            super.onAttachedToWindow();
            if (!this.sdkChecker.sdkIsAtLeast(26) || (flutterViewTryFindFlutterView = tryFindFlutterView()) == null) {
                return;
            }
            flutterViewTryFindFlutterView.setImportantForAutofill(1);
        }

        @Override // io.flutter.plugin.platform.PlatformView
        public /* synthetic */ void onFlutterViewAttached(View view) {
            io.flutter.plugin.platform.f.a(this, view);
        }

        @Override // io.flutter.plugin.platform.PlatformView
        public /* synthetic */ void onFlutterViewDetached() {
            io.flutter.plugin.platform.f.b(this);
        }

        @Override // io.flutter.plugin.platform.PlatformView
        public /* synthetic */ void onInputConnectionLocked() {
            io.flutter.plugin.platform.f.c(this);
        }

        @Override // io.flutter.plugin.platform.PlatformView
        public /* synthetic */ void onInputConnectionUnlocked() {
            io.flutter.plugin.platform.f.d(this);
        }

        @Override // android.webkit.WebView, android.view.View
        protected void onScrollChanged(int i2, int i3, int i4, int i5) {
            super.onScrollChanged(i2, i3, i4, i5);
            this.api.onScrollChanged(this, Long.valueOf(i2), Long.valueOf(i3), Long.valueOf(i4), Long.valueOf(i5), new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.p5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewHostApiImpl.WebViewPlatformView.lambda$onScrollChanged$1((Void) obj);
                }
            });
        }

        @VisibleForTesting
        void setApi(WebViewFlutterApiImpl webViewFlutterApiImpl) {
            this.api = webViewFlutterApiImpl;
        }

        @Override // android.webkit.WebView
        public void setWebChromeClient(@Nullable WebChromeClient webChromeClient) {
            super.setWebChromeClient(webChromeClient);
            if (!(webChromeClient instanceof WebChromeClientHostApiImpl.SecureWebChromeClient)) {
                throw new AssertionError("Client must be a SecureWebChromeClient.");
            }
            WebChromeClientHostApiImpl.SecureWebChromeClient secureWebChromeClient = (WebChromeClientHostApiImpl.SecureWebChromeClient) webChromeClient;
            this.currentWebChromeClient = secureWebChromeClient;
            secureWebChromeClient.setWebViewClient(this.currentWebViewClient);
        }

        @Override // android.webkit.WebView
        public void setWebViewClient(@NonNull WebViewClient webViewClient) {
            super.setWebViewClient(webViewClient);
            this.currentWebViewClient = webViewClient;
            this.currentWebChromeClient.setWebViewClient(webViewClient);
        }

        @VisibleForTesting
        WebViewPlatformView(@NonNull Context context, @NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager, @NonNull AndroidSdkChecker androidSdkChecker) {
            super(context);
            this.currentWebViewClient = new WebViewClient();
            this.currentWebChromeClient = new WebChromeClientHostApiImpl.SecureWebChromeClient();
            this.api = new WebViewFlutterApiImpl(binaryMessenger, instanceManager);
            this.sdkChecker = androidSdkChecker;
            setWebViewClient(this.currentWebViewClient);
            setWebChromeClient(this.currentWebChromeClient);
        }
    }

    public static class WebViewProxy {
        @NonNull
        public WebViewPlatformView createWebView(@NonNull Context context, @NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
            return new WebViewPlatformView(context, binaryMessenger, instanceManager);
        }

        public void setWebContentsDebuggingEnabled(boolean z2) {
            WebView.setWebContentsDebuggingEnabled(z2);
        }
    }

    public WebViewHostApiImpl(@NonNull InstanceManager instanceManager, @NonNull BinaryMessenger binaryMessenger, @NonNull WebViewProxy webViewProxy, @Nullable Context context) {
        this.instanceManager = instanceManager;
        this.binaryMessenger = binaryMessenger;
        this.webViewProxy = webViewProxy;
        this.context = context;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    @SuppressLint({"JavascriptInterface"})
    public void addJavaScriptChannel(@NonNull Long l2, @NonNull Long l3) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        JavaScriptChannel javaScriptChannel = (JavaScriptChannel) this.instanceManager.getInstance(l3.longValue());
        Objects.requireNonNull(javaScriptChannel);
        webView.addJavascriptInterface(javaScriptChannel, javaScriptChannel.javaScriptChannelName);
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    @NonNull
    public Boolean canGoBack(@NonNull Long l2) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        return Boolean.valueOf(webView.canGoBack());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    @NonNull
    public Boolean canGoForward(@NonNull Long l2) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        return Boolean.valueOf(webView.canGoForward());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void clearCache(@NonNull Long l2, @NonNull Boolean bool) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.clearCache(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void create(@NonNull Long l2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        DisplayListenerProxy displayListenerProxy = new DisplayListenerProxy();
        DisplayManager displayManager = (DisplayManager) this.context.getSystemService("display");
        displayListenerProxy.onPreWebViewInitialization(displayManager);
        WebViewPlatformView webViewPlatformViewCreateWebView = this.webViewProxy.createWebView(this.context, this.binaryMessenger, this.instanceManager);
        displayListenerProxy.onPostWebViewInitialization(displayManager);
        this.instanceManager.addDartCreatedInstance(webViewPlatformViewCreateWebView, l2.longValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void evaluateJavascript(@NonNull Long l2, @NonNull String str, @NonNull final GeneratedAndroidWebView.Result<String> result) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        Objects.requireNonNull(result);
        webView.evaluateJavascript(str, new ValueCallback() { // from class: io.flutter.plugins.webviewflutter.n5
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                result.success((String) obj);
            }
        });
    }

    @NonNull
    public InstanceManager getInstanceManager() {
        return this.instanceManager;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    @NonNull
    public GeneratedAndroidWebView.WebViewPoint getScrollPosition(@NonNull Long l2) {
        Objects.requireNonNull((WebView) this.instanceManager.getInstance(l2.longValue()));
        return new GeneratedAndroidWebView.WebViewPoint.Builder().setX(Long.valueOf(r4.getScrollX())).setY(Long.valueOf(r4.getScrollY())).build();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    @NonNull
    public Long getScrollX(@NonNull Long l2) {
        Objects.requireNonNull((WebView) this.instanceManager.getInstance(l2.longValue()));
        return Long.valueOf(r4.getScrollX());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    @NonNull
    public Long getScrollY(@NonNull Long l2) {
        Objects.requireNonNull((WebView) this.instanceManager.getInstance(l2.longValue()));
        return Long.valueOf(r4.getScrollY());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    @Nullable
    public String getTitle(@NonNull Long l2) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        return webView.getTitle();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    @Nullable
    public String getUrl(@NonNull Long l2) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        return webView.getUrl();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void goBack(@NonNull Long l2) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.goBack();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void goForward(@NonNull Long l2) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.goForward();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void loadData(@NonNull Long l2, @NonNull String str, @Nullable String str2, @Nullable String str3) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.loadData(str, str2, str3);
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void loadDataWithBaseUrl(@NonNull Long l2, @Nullable String str, @NonNull String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.loadDataWithBaseURL(str, str2, str3, str4, str5);
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void loadUrl(@NonNull Long l2, @NonNull String str, @NonNull Map<String, String> map) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.loadUrl(str, map);
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void postUrl(@NonNull Long l2, @NonNull String str, @NonNull byte[] bArr) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.postUrl(str, bArr);
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void reload(@NonNull Long l2) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.reload();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void removeJavaScriptChannel(@NonNull Long l2, @NonNull Long l3) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        JavaScriptChannel javaScriptChannel = (JavaScriptChannel) this.instanceManager.getInstance(l3.longValue());
        Objects.requireNonNull(javaScriptChannel);
        webView.removeJavascriptInterface(javaScriptChannel.javaScriptChannelName);
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void scrollBy(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.scrollBy(l3.intValue(), l4.intValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void scrollTo(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.scrollTo(l3.intValue(), l4.intValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void setBackgroundColor(@NonNull Long l2, @NonNull Long l3) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.setBackgroundColor(l3.intValue());
    }

    public void setContext(@Nullable Context context) {
        this.context = context;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void setDownloadListener(@NonNull Long l2, @Nullable Long l3) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        InstanceManager instanceManager = this.instanceManager;
        Objects.requireNonNull(l3);
        webView.setDownloadListener((DownloadListener) instanceManager.getInstance(l3.longValue()));
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void setWebChromeClient(@NonNull Long l2, @Nullable Long l3) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        InstanceManager instanceManager = this.instanceManager;
        Objects.requireNonNull(l3);
        webView.setWebChromeClient((WebChromeClient) instanceManager.getInstance(l3.longValue()));
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void setWebContentsDebuggingEnabled(@NonNull Boolean bool) {
        this.webViewProxy.setWebContentsDebuggingEnabled(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi
    public void setWebViewClient(@NonNull Long l2, @NonNull Long l3) {
        WebView webView = (WebView) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webView);
        webView.setWebViewClient((WebViewClient) this.instanceManager.getInstance(l3.longValue()));
    }
}
