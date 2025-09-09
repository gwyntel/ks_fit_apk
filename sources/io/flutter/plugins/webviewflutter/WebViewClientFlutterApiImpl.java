package io.flutter.plugins.webviewflutter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.webkit.WebResourceErrorCompat;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WebViewClientFlutterApiImpl extends GeneratedAndroidWebView.WebViewClientFlutterApi {
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;
    private final WebViewFlutterApiImpl webViewFlutterApi;

    public WebViewClientFlutterApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        super(binaryMessenger);
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
        this.webViewFlutterApi = new WebViewFlutterApiImpl(binaryMessenger, instanceManager);
    }

    @RequiresApi(api = 23)
    static GeneratedAndroidWebView.WebResourceErrorData createWebResourceErrorData(WebResourceError webResourceError) {
        return new GeneratedAndroidWebView.WebResourceErrorData.Builder().setErrorCode(Long.valueOf(webResourceError.getErrorCode())).setDescription(webResourceError.getDescription().toString()).build();
    }

    @RequiresApi(api = 21)
    static GeneratedAndroidWebView.WebResourceRequestData createWebResourceRequestData(WebResourceRequest webResourceRequest) {
        GeneratedAndroidWebView.WebResourceRequestData.Builder requestHeaders = new GeneratedAndroidWebView.WebResourceRequestData.Builder().setUrl(webResourceRequest.getUrl().toString()).setIsForMainFrame(Boolean.valueOf(webResourceRequest.isForMainFrame())).setHasGesture(Boolean.valueOf(webResourceRequest.hasGesture())).setMethod(webResourceRequest.getMethod()).setRequestHeaders(webResourceRequest.getRequestHeaders() != null ? webResourceRequest.getRequestHeaders() : new HashMap<>());
        if (Build.VERSION.SDK_INT >= 24) {
            requestHeaders.setIsRedirect(Boolean.valueOf(webResourceRequest.isRedirect()));
        }
        return requestHeaders.build();
    }

    @RequiresApi(api = 21)
    static GeneratedAndroidWebView.WebResourceResponseData createWebResourceResponseData(WebResourceResponse webResourceResponse) {
        return new GeneratedAndroidWebView.WebResourceResponseData.Builder().setStatusCode(Long.valueOf(webResourceResponse.getStatusCode())).build();
    }

    private long getIdentifierForClient(WebViewClient webViewClient) {
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webViewClient);
        if (identifierForStrongReference != null) {
            return identifierForStrongReference.longValue();
        }
        throw new IllegalStateException("Could not find identifier for WebViewClient.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doUpdateVisitedHistory$8(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPageFinished$1(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPageStarted$0(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onReceivedError$5(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onReceivedHttpAuthRequest$9(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onReceivedHttpError$2(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onReceivedRequestError$3(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onReceivedRequestError$4(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$requestLoading$6(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$urlLoading$7(Void r02) {
    }

    public void doUpdateVisitedHistory(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull String str, boolean z2, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.p4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$doUpdateVisitedHistory$8((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        doUpdateVisitedHistory(Long.valueOf(getIdentifierForClient(webViewClient)), identifierForStrongReference, str, Boolean.valueOf(z2), reply);
    }

    public void onPageFinished(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull String str, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.i4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$onPageFinished$1((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        onPageFinished(Long.valueOf(getIdentifierForClient(webViewClient)), identifierForStrongReference, str, reply);
    }

    public void onPageStarted(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull String str, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.l4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$onPageStarted$0((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        onPageStarted(Long.valueOf(getIdentifierForClient(webViewClient)), identifierForStrongReference, str, reply);
    }

    public void onReceivedError(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull Long l2, @NonNull String str, @NonNull String str2, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.o4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$onReceivedError$5((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        onReceivedError(Long.valueOf(getIdentifierForClient(webViewClient)), identifierForStrongReference, l2, str, str2, reply);
    }

    public void onReceivedHttpAuthRequest(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull HttpAuthHandler httpAuthHandler, @NonNull String str, @NonNull String str2, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        new HttpAuthHandlerFlutterApiImpl(this.binaryMessenger, this.instanceManager).create(httpAuthHandler, new GeneratedAndroidWebView.HttpAuthHandlerFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.k4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.HttpAuthHandlerFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$onReceivedHttpAuthRequest$9((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webViewClient);
        Objects.requireNonNull(identifierForStrongReference);
        Long identifierForStrongReference2 = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference2);
        Long identifierForStrongReference3 = this.instanceManager.getIdentifierForStrongReference(httpAuthHandler);
        Objects.requireNonNull(identifierForStrongReference3);
        onReceivedHttpAuthRequest(identifierForStrongReference, identifierForStrongReference2, identifierForStrongReference3, str, str2, reply);
    }

    @RequiresApi(api = 21)
    public void onReceivedHttpError(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest, @NonNull WebResourceResponse webResourceResponse, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.h4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$onReceivedHttpError$2((Void) obj);
            }
        });
        onReceivedHttpError(Long.valueOf(getIdentifierForClient(webViewClient)), this.instanceManager.getIdentifierForStrongReference(webView), createWebResourceRequestData(webResourceRequest), createWebResourceResponseData(webResourceResponse), reply);
    }

    @RequiresApi(api = 23)
    public void onReceivedRequestError(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest, @NonNull WebResourceError webResourceError, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.n4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$onReceivedRequestError$3((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        onReceivedRequestError(Long.valueOf(getIdentifierForClient(webViewClient)), identifierForStrongReference, createWebResourceRequestData(webResourceRequest), createWebResourceErrorData(webResourceError), reply);
    }

    @RequiresApi(api = 21)
    public void requestLoading(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.j4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$requestLoading$6((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        requestLoading(Long.valueOf(getIdentifierForClient(webViewClient)), identifierForStrongReference, createWebResourceRequestData(webResourceRequest), reply);
    }

    public void urlLoading(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull String str, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.q4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$urlLoading$7((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        urlLoading(Long.valueOf(getIdentifierForClient(webViewClient)), identifierForStrongReference, str, reply);
    }

    @SuppressLint({"RequiresFeature"})
    static GeneratedAndroidWebView.WebResourceErrorData createWebResourceErrorData(WebResourceErrorCompat webResourceErrorCompat) {
        return new GeneratedAndroidWebView.WebResourceErrorData.Builder().setErrorCode(Long.valueOf(webResourceErrorCompat.getErrorCode())).setDescription(webResourceErrorCompat.getDescription().toString()).build();
    }

    @RequiresApi(api = 21)
    public void onReceivedRequestError(@NonNull WebViewClient webViewClient, @NonNull WebView webView, @NonNull WebResourceRequest webResourceRequest, @NonNull WebResourceErrorCompat webResourceErrorCompat, @NonNull GeneratedAndroidWebView.WebViewClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.m4
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewClientFlutterApiImpl.lambda$onReceivedRequestError$4((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        onReceivedRequestError(Long.valueOf(getIdentifierForClient(webViewClient)), identifierForStrongReference, createWebResourceRequestData(webResourceRequest), createWebResourceErrorData(webResourceErrorCompat), reply);
    }
}
