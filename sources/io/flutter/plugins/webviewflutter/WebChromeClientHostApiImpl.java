package io.flutter.plugins.webviewflutter;

import android.content.Context;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import io.flutter.plugins.webviewflutter.WebChromeClientHostApiImpl;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WebChromeClientHostApiImpl implements GeneratedAndroidWebView.WebChromeClientHostApi {
    private Context context;
    private final WebChromeClientFlutterApiImpl flutterApi;
    private final InstanceManager instanceManager;
    private final WebChromeClientCreator webChromeClientCreator;

    public static class SecureWebChromeClient extends WebChromeClient {

        @Nullable
        WebViewClient webViewClient;

        @Override // android.webkit.WebChromeClient
        public boolean onCreateWindow(@NonNull WebView webView, boolean z2, boolean z3, @NonNull Message message) {
            return onCreateWindow(webView, message, new WebView(webView.getContext()));
        }

        public void setWebViewClient(@NonNull WebViewClient webViewClient) {
            this.webViewClient = webViewClient;
        }

        @VisibleForTesting
        boolean onCreateWindow(@NonNull final WebView webView, @NonNull Message message, @Nullable WebView webView2) {
            if (this.webViewClient == null) {
                return false;
            }
            WebViewClient webViewClient = new WebViewClient() { // from class: io.flutter.plugins.webviewflutter.WebChromeClientHostApiImpl.SecureWebChromeClient.1
                @Override // android.webkit.WebViewClient
                @RequiresApi(api = 24)
                public boolean shouldOverrideUrlLoading(@NonNull WebView webView3, @NonNull WebResourceRequest webResourceRequest) {
                    if (SecureWebChromeClient.this.webViewClient.shouldOverrideUrlLoading(webView, webResourceRequest)) {
                        return true;
                    }
                    webView.loadUrl(webResourceRequest.getUrl().toString());
                    return true;
                }

                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView3, String str) {
                    if (SecureWebChromeClient.this.webViewClient.shouldOverrideUrlLoading(webView, str)) {
                        return true;
                    }
                    webView.loadUrl(str);
                    return true;
                }
            };
            if (webView2 == null) {
                webView2 = new WebView(webView.getContext());
            }
            webView2.setWebViewClient(webViewClient);
            ((WebView.WebViewTransport) message.obj).setWebView(webView2);
            message.sendToTarget();
            return true;
        }
    }

    public static class WebChromeClientCreator {
        @NonNull
        public WebChromeClientImpl createWebChromeClient(@NonNull WebChromeClientFlutterApiImpl webChromeClientFlutterApiImpl) {
            return new WebChromeClientImpl(webChromeClientFlutterApiImpl);
        }
    }

    public static class WebChromeClientImpl extends SecureWebChromeClient {
        private final WebChromeClientFlutterApiImpl flutterApi;
        private boolean returnValueForOnShowFileChooser = false;
        private boolean returnValueForOnConsoleMessage = false;
        private boolean returnValueForOnJsAlert = false;
        private boolean returnValueForOnJsConfirm = false;
        private boolean returnValueForOnJsPrompt = false;

        public WebChromeClientImpl(@NonNull WebChromeClientFlutterApiImpl webChromeClientFlutterApiImpl) {
            this.flutterApi = webChromeClientFlutterApiImpl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onConsoleMessage$7(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onGeolocationPermissionsHidePrompt$4(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onGeolocationPermissionsShowPrompt$3(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onHideCustomView$2(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onJsConfirm$9(JsResult jsResult, Boolean bool) {
            if (bool.booleanValue()) {
                jsResult.confirm();
            } else {
                jsResult.cancel();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onJsPrompt$10(JsPromptResult jsPromptResult, String str) {
            if (str != null) {
                jsPromptResult.confirm(str);
            } else {
                jsPromptResult.cancel();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onPermissionRequest$6(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onProgressChanged$0(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onShowCustomView$1(Void r02) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onShowFileChooser$5(boolean z2, ValueCallback valueCallback, List list) {
            if (z2) {
                Uri[] uriArr = new Uri[list.size()];
                for (int i2 = 0; i2 < list.size(); i2++) {
                    uriArr[i2] = Uri.parse((String) list.get(i2));
                }
                valueCallback.onReceiveValue(uriArr);
            }
        }

        @Override // android.webkit.WebChromeClient
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            this.flutterApi.onConsoleMessage(this, consoleMessage, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.z3
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onConsoleMessage$7((Void) obj);
                }
            });
            return this.returnValueForOnConsoleMessage;
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsHidePrompt() {
            this.flutterApi.onGeolocationPermissionsHidePrompt(this, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.f4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onGeolocationPermissionsHidePrompt$4((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(@NonNull String str, @NonNull GeolocationPermissions.Callback callback) {
            this.flutterApi.onGeolocationPermissionsShowPrompt(this, str, callback, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.e4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onGeolocationPermissionsShowPrompt$3((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebChromeClient
        public void onHideCustomView() {
            this.flutterApi.onHideCustomView(this, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.g4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onHideCustomView$2((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
            if (!this.returnValueForOnJsAlert) {
                return false;
            }
            this.flutterApi.onJsAlert(this, str, str2, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.b4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    jsResult.confirm();
                }
            });
            return true;
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
            if (!this.returnValueForOnJsConfirm) {
                return false;
            }
            this.flutterApi.onJsConfirm(this, str, str2, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.x3
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onJsConfirm$9(jsResult, (Boolean) obj);
                }
            });
            return true;
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsPrompt(WebView webView, String str, String str2, String str3, final JsPromptResult jsPromptResult) {
            if (!this.returnValueForOnJsPrompt) {
                return false;
            }
            this.flutterApi.onJsPrompt(this, str, str2, str3, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.a4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onJsPrompt$10(jsPromptResult, (String) obj);
                }
            });
            return true;
        }

        @Override // android.webkit.WebChromeClient
        @RequiresApi(api = 21)
        public void onPermissionRequest(@NonNull PermissionRequest permissionRequest) {
            this.flutterApi.onPermissionRequest(this, permissionRequest, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.y3
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onPermissionRequest$6((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(@NonNull WebView webView, int i2) {
            this.flutterApi.onProgressChanged(this, webView, Long.valueOf(i2), new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.d4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onProgressChanged$0((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebChromeClient
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
            this.flutterApi.onShowCustomView(this, view, customViewCallback, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.w3
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onShowCustomView$1((Void) obj);
                }
            });
        }

        @Override // android.webkit.WebChromeClient
        @RequiresApi(api = 21)
        public boolean onShowFileChooser(@NonNull WebView webView, @NonNull final ValueCallback<Uri[]> valueCallback, @NonNull WebChromeClient.FileChooserParams fileChooserParams) {
            final boolean z2 = this.returnValueForOnShowFileChooser;
            this.flutterApi.onShowFileChooser(this, webView, fileChooserParams, new GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.c4
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply
                public final void reply(Object obj) {
                    WebChromeClientHostApiImpl.WebChromeClientImpl.lambda$onShowFileChooser$5(z2, valueCallback, (List) obj);
                }
            });
            return z2;
        }

        public void setReturnValueForOnConsoleMessage(boolean z2) {
            this.returnValueForOnConsoleMessage = z2;
        }

        public void setReturnValueForOnJsAlert(boolean z2) {
            this.returnValueForOnJsAlert = z2;
        }

        public void setReturnValueForOnJsConfirm(boolean z2) {
            this.returnValueForOnJsConfirm = z2;
        }

        public void setReturnValueForOnJsPrompt(boolean z2) {
            this.returnValueForOnJsPrompt = z2;
        }

        public void setReturnValueForOnShowFileChooser(boolean z2) {
            this.returnValueForOnShowFileChooser = z2;
        }
    }

    public WebChromeClientHostApiImpl(@NonNull InstanceManager instanceManager, @NonNull WebChromeClientCreator webChromeClientCreator, @NonNull WebChromeClientFlutterApiImpl webChromeClientFlutterApiImpl) {
        this.instanceManager = instanceManager;
        this.webChromeClientCreator = webChromeClientCreator;
        this.flutterApi = webChromeClientFlutterApiImpl;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientHostApi
    public void create(@NonNull Long l2) {
        this.instanceManager.addDartCreatedInstance(this.webChromeClientCreator.createWebChromeClient(this.flutterApi), l2.longValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientHostApi
    public void setSynchronousReturnValueForOnConsoleMessage(@NonNull Long l2, @NonNull Boolean bool) {
        WebChromeClientImpl webChromeClientImpl = (WebChromeClientImpl) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webChromeClientImpl);
        webChromeClientImpl.setReturnValueForOnConsoleMessage(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientHostApi
    public void setSynchronousReturnValueForOnJsAlert(@NonNull Long l2, @NonNull Boolean bool) {
        WebChromeClientImpl webChromeClientImpl = (WebChromeClientImpl) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webChromeClientImpl);
        webChromeClientImpl.setReturnValueForOnJsAlert(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientHostApi
    public void setSynchronousReturnValueForOnJsConfirm(@NonNull Long l2, @NonNull Boolean bool) {
        WebChromeClientImpl webChromeClientImpl = (WebChromeClientImpl) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webChromeClientImpl);
        webChromeClientImpl.setReturnValueForOnJsConfirm(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientHostApi
    public void setSynchronousReturnValueForOnJsPrompt(@NonNull Long l2, @NonNull Boolean bool) {
        WebChromeClientImpl webChromeClientImpl = (WebChromeClientImpl) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webChromeClientImpl);
        webChromeClientImpl.setReturnValueForOnJsPrompt(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebChromeClientHostApi
    public void setSynchronousReturnValueForOnShowFileChooser(@NonNull Long l2, @NonNull Boolean bool) {
        WebChromeClientImpl webChromeClientImpl = (WebChromeClientImpl) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webChromeClientImpl);
        webChromeClientImpl.setReturnValueForOnShowFileChooser(bool.booleanValue());
    }
}
