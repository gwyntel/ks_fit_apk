package io.flutter.plugins.webviewflutter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.webkit.WebResourceErrorCompat;
import androidx.webkit.WebViewClientCompat;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import io.flutter.plugins.webviewflutter.WebViewClientHostApiImpl;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WebViewClientHostApiImpl implements GeneratedAndroidWebView.WebViewClientHostApi {
    private final WebViewClientFlutterApiImpl flutterApi;
    private final InstanceManager instanceManager;
    private final WebViewClientCreator webViewClientCreator;

    public static class WebViewClientCompatImpl extends WebViewClientCompat {
        private final WebViewClientFlutterApiImpl flutterApi;
        private boolean returnValueForShouldOverrideUrlLoading = false;

        public WebViewClientCompatImpl(@NonNull WebViewClientFlutterApiImpl webViewClientFlutterApiImpl) {
            this.flutterApi = webViewClientFlutterApiImpl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$doUpdateVisitedHistory$7(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onPageFinished$1(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onPageStarted$0(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceivedError$3(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceivedError$4(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceivedHttpAuthRequest$8(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceivedHttpError$2(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$shouldOverrideUrlLoading$5(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$shouldOverrideUrlLoading$6(Void r02) {
        }

        @Override // android.webkit.WebViewClient
        public void doUpdateVisitedHistory(@NonNull WebView webView, @NonNull String str, boolean z2) {
            this.flutterApi.doUpdateVisitedHistory(this, webView, str, z2, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.u4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$doUpdateVisitedHistory$7((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(@NonNull WebView webView, @NonNull String str) {
            this.flutterApi.onPageFinished(this, webView, str, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.x4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$onPageFinished$1((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(@NonNull WebView webView, @NonNull String str, @NonNull Bitmap bitmap) {
            this.flutterApi.onPageStarted(this, webView, str, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.v4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$onPageStarted$0((Void) obj);
                }
            });
        }

        @Override // androidx.webkit.WebViewClientCompat
        @RequiresApi(api = 21)
        @SuppressLint({"RequiresFeature"})
        public void onReceivedError(@NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest, @NonNull WebResourceErrorCompat webResourceErrorCompat) {
            this.flutterApi.onReceivedRequestError(this, webView, webResourceRequest, webResourceErrorCompat, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.z4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$onReceivedError$3((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpAuthRequest(@NonNull WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
            this.flutterApi.onReceivedHttpAuthRequest(this, webView, httpAuthHandler, str, str2, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.y4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$onReceivedHttpAuthRequest$8((Void) obj);
                }
            });
        }

        @Override // androidx.webkit.WebViewClientCompat, android.webkit.WebViewClient, org.chromium.support_lib_boundary.WebViewClientBoundaryInterface
        @RequiresApi(api = 21)
        public void onReceivedHttpError(@NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest, @NonNull WebResourceResponse webResourceResponse) {
            this.flutterApi.onReceivedHttpError(this, webView, webResourceRequest, webResourceResponse, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.s4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$onReceivedHttpError$2((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onUnhandledKeyEvent(@NonNull WebView webView, @NonNull KeyEvent keyEvent) {
        }

        public void setReturnValueForShouldOverrideUrlLoading(boolean z2) {
            this.returnValueForShouldOverrideUrlLoading = z2;
        }

        @Override // androidx.webkit.WebViewClientCompat, android.webkit.WebViewClient, org.chromium.support_lib_boundary.WebViewClientBoundaryInterface
        @TargetApi(21)
        public boolean shouldOverrideUrlLoading(@NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest) {
            this.flutterApi.requestLoading(this, webView, webResourceRequest, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.w4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$shouldOverrideUrlLoading$5((Void) obj);
                }
            });
            return webResourceRequest.isForMainFrame() && this.returnValueForShouldOverrideUrlLoading;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(@NonNull WebView webView, int i2, @NonNull String str, @NonNull String str2) {
            this.flutterApi.onReceivedError(this, webView, Long.valueOf(i2), str, str2, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.t4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$onReceivedError$4((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(@NonNull WebView webView, @NonNull String str) {
            this.flutterApi.urlLoading(this, webView, str, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.r4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientCompatImpl.lambda$shouldOverrideUrlLoading$6((Void) obj);
                }
            });
            return this.returnValueForShouldOverrideUrlLoading;
        }
    }

    public static class WebViewClientCreator {
        @NonNull
        public WebViewClient createWebViewClient(@NonNull WebViewClientFlutterApiImpl webViewClientFlutterApiImpl) {
            return Build.VERSION.SDK_INT >= 24 ? new WebViewClientImpl(webViewClientFlutterApiImpl) : new WebViewClientCompatImpl(webViewClientFlutterApiImpl);
        }
    }

    @RequiresApi(24)
    public static class WebViewClientImpl extends WebViewClient {
        private final WebViewClientFlutterApiImpl flutterApi;
        private boolean returnValueForShouldOverrideUrlLoading = false;

        public WebViewClientImpl(@NonNull WebViewClientFlutterApiImpl webViewClientFlutterApiImpl) {
            this.flutterApi = webViewClientFlutterApiImpl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$doUpdateVisitedHistory$7(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onPageFinished$1(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onPageStarted$0(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceivedError$3(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceivedError$4(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceivedHttpAuthRequest$8(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceivedHttpError$2(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$shouldOverrideUrlLoading$5(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$shouldOverrideUrlLoading$6(Void r02) {
        }

        @Override // android.webkit.WebViewClient
        public void doUpdateVisitedHistory(@NonNull WebView webView, @NonNull String str, boolean z2) {
            this.flutterApi.doUpdateVisitedHistory(this, webView, str, z2, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.i5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$doUpdateVisitedHistory$7((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(@NonNull WebView webView, @NonNull String str) {
            this.flutterApi.onPageFinished(this, webView, str, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.c5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$onPageFinished$1((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(@NonNull WebView webView, @NonNull String str, @NonNull Bitmap bitmap) {
            this.flutterApi.onPageStarted(this, webView, str, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.f5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$onPageStarted$0((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(@NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest, @NonNull WebResourceError webResourceError) {
            this.flutterApi.onReceivedRequestError(this, webView, webResourceRequest, webResourceError, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.a5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$onReceivedError$3((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpAuthRequest(@NonNull WebView webView, @NonNull HttpAuthHandler httpAuthHandler, @NonNull String str, @NonNull String str2) {
            this.flutterApi.onReceivedHttpAuthRequest(this, webView, httpAuthHandler, str, str2, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.h5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$onReceivedHttpAuthRequest$8((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpError(@NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest, @NonNull WebResourceResponse webResourceResponse) {
            this.flutterApi.onReceivedHttpError(this, webView, webResourceRequest, webResourceResponse, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.e5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$onReceivedHttpError$2((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onUnhandledKeyEvent(@NonNull WebView webView, @NonNull KeyEvent keyEvent) {
        }

        public void setReturnValueForShouldOverrideUrlLoading(boolean z2) {
            this.returnValueForShouldOverrideUrlLoading = z2;
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(@NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest) {
            this.flutterApi.requestLoading(this, webView, webResourceRequest, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.b5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$shouldOverrideUrlLoading$5((Void) obj);
                }
            });
            return webResourceRequest.isForMainFrame() && this.returnValueForShouldOverrideUrlLoading;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(@NonNull WebView webView, int i2, @NonNull String str, @NonNull String str2) {
            this.flutterApi.onReceivedError(this, webView, Long.valueOf(i2), str, str2, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.g5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$onReceivedError$4((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(@NonNull WebView webView, @NonNull String str) {
            this.flutterApi.urlLoading(this, webView, str, new GeneratedAndroidWebView.WebViewClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.d5
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebViewClientHostApiImpl.WebViewClientImpl.lambda$shouldOverrideUrlLoading$6((Void) obj);
                }
            });
            return this.returnValueForShouldOverrideUrlLoading;
        }
    }

    public WebViewClientHostApiImpl(@NonNull InstanceManager instanceManager, @NonNull WebViewClientCreator webViewClientCreator, @NonNull WebViewClientFlutterApiImpl webViewClientFlutterApiImpl) {
        this.instanceManager = instanceManager;
        this.webViewClientCreator = webViewClientCreator;
        this.flutterApi = webViewClientFlutterApiImpl;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientHostApi
    public void create(@NonNull Long l2) {
        this.instanceManager.addDartCreatedInstance(this.webViewClientCreator.createWebViewClient(this.flutterApi), l2.longValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientHostApi
    public void setSynchronousReturnValueForShouldOverrideUrlLoading(@NonNull Long l2, @NonNull Boolean bool) {
        WebViewClient webViewClient = (WebViewClient) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webViewClient);
        if (webViewClient instanceof WebViewClientCompatImpl) {
            ((WebViewClientCompatImpl) webViewClient).setReturnValueForShouldOverrideUrlLoading(bool.booleanValue());
        } else {
            if (Build.VERSION.SDK_INT < 24 || !(webViewClient instanceof WebViewClientImpl)) {
                throw new IllegalStateException("This WebViewClient doesn't support setting the returnValueForShouldOverrideUrlLoading.");
            }
            ((WebViewClientImpl) webViewClient).setReturnValueForShouldOverrideUrlLoading(bool.booleanValue());
        }
    }
}
