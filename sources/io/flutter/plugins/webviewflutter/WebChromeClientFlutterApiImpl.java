package io.flutter.plugins.webviewflutter;

import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WebChromeClientFlutterApiImpl extends GeneratedAndroidWebView.WebChromeClientFlutterApi {
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;
    private final WebViewFlutterApiImpl webViewFlutterApi;

    /* renamed from: io.flutter.plugins.webviewflutter.WebChromeClientFlutterApiImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$webkit$ConsoleMessage$MessageLevel;

        static {
            int[] iArr = new int[ConsoleMessage.MessageLevel.values().length];
            $SwitchMap$android$webkit$ConsoleMessage$MessageLevel = iArr;
            try {
                iArr[ConsoleMessage.MessageLevel.TIP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[ConsoleMessage.MessageLevel.LOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[ConsoleMessage.MessageLevel.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[ConsoleMessage.MessageLevel.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[ConsoleMessage.MessageLevel.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public WebChromeClientFlutterApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        super(binaryMessenger);
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
        this.webViewFlutterApi = new WebViewFlutterApiImpl(binaryMessenger, instanceManager);
    }

    private long getIdentifierForClient(WebChromeClient webChromeClient) {
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        if (identifierForStrongReference != null) {
            return identifierForStrongReference.longValue();
        }
        throw new IllegalStateException("Could not find identifier for WebChromeClient.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onGeolocationPermissionsShowPrompt$3(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPermissionRequest$4(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onProgressChanged$0(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onShowCustomView$5(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onShowCustomView$6(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onShowFileChooser$1(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onShowFileChooser$2(Void r02) {
    }

    private static GeneratedAndroidWebView.ConsoleMessageLevel toConsoleMessageLevel(ConsoleMessage.MessageLevel messageLevel) {
        int i2 = AnonymousClass1.$SwitchMap$android$webkit$ConsoleMessage$MessageLevel[messageLevel.ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? GeneratedAndroidWebView.ConsoleMessageLevel.UNKNOWN : GeneratedAndroidWebView.ConsoleMessageLevel.DEBUG : GeneratedAndroidWebView.ConsoleMessageLevel.ERROR : GeneratedAndroidWebView.ConsoleMessageLevel.WARNING : GeneratedAndroidWebView.ConsoleMessageLevel.LOG : GeneratedAndroidWebView.ConsoleMessageLevel.TIP;
    }

    public void onConsoleMessage(@NonNull WebChromeClient webChromeClient, @NonNull ConsoleMessage consoleMessage, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Void> reply) {
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        super.onConsoleMessage(identifierForStrongReference, new GeneratedAndroidWebView.ConsoleMessage.Builder().setLineNumber(Long.valueOf(consoleMessage.lineNumber())).setMessage(consoleMessage.message()).setLevel(toConsoleMessageLevel(consoleMessage.messageLevel())).setSourceId(consoleMessage.sourceId()).build(), reply);
    }

    public void onGeolocationPermissionsHidePrompt(@NonNull WebChromeClient webChromeClient, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Void> reply) {
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        super.onGeolocationPermissionsHidePrompt(identifierForStrongReference, reply);
    }

    public void onGeolocationPermissionsShowPrompt(@NonNull WebChromeClient webChromeClient, @NonNull String str, @NonNull GeolocationPermissions.Callback callback, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Void> reply) {
        new GeolocationPermissionsCallbackFlutterApiImpl(this.binaryMessenger, this.instanceManager).create(callback, new GeneratedAndroidWebView.GeolocationPermissionsCallbackFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.t3
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.GeolocationPermissionsCallbackFlutterApi.Reply
            public final void reply(Object obj) {
                WebChromeClientFlutterApiImpl.lambda$onGeolocationPermissionsShowPrompt$3((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        Long identifierForStrongReference2 = this.instanceManager.getIdentifierForStrongReference(callback);
        Objects.requireNonNull(identifierForStrongReference2);
        onGeolocationPermissionsShowPrompt(identifierForStrongReference, identifierForStrongReference2, str, reply);
    }

    public void onHideCustomView(@NonNull WebChromeClient webChromeClient, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Void> reply) {
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        super.onHideCustomView(identifierForStrongReference, reply);
    }

    public void onJsAlert(@NonNull WebChromeClient webChromeClient, @NonNull String str, @NonNull String str2, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Void> reply) {
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        super.onJsAlert(identifierForStrongReference, str, str2, reply);
    }

    public void onJsConfirm(@NonNull WebChromeClient webChromeClient, @NonNull String str, @NonNull String str2, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Boolean> reply) {
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        super.onJsConfirm(identifierForStrongReference, str, str2, reply);
    }

    public void onJsPrompt(@NonNull WebChromeClient webChromeClient, @NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<String> reply) {
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        super.onJsPrompt(identifierForStrongReference, str, str2, str3, reply);
    }

    @RequiresApi(api = 21)
    public void onPermissionRequest(@NonNull WebChromeClient webChromeClient, @NonNull PermissionRequest permissionRequest, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Void> reply) {
        new PermissionRequestFlutterApiImpl(this.binaryMessenger, this.instanceManager).create(permissionRequest, permissionRequest.getResources(), new GeneratedAndroidWebView.PermissionRequestFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.r3
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.PermissionRequestFlutterApi.Reply
            public final void reply(Object obj) {
                WebChromeClientFlutterApiImpl.lambda$onPermissionRequest$4((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        Long identifierForStrongReference2 = this.instanceManager.getIdentifierForStrongReference(permissionRequest);
        Objects.requireNonNull(identifierForStrongReference2);
        super.onPermissionRequest(identifierForStrongReference, identifierForStrongReference2, reply);
    }

    public void onProgressChanged(@NonNull WebChromeClient webChromeClient, @NonNull WebView webView, @NonNull Long l2, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Void> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.s3
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebChromeClientFlutterApiImpl.lambda$onProgressChanged$0((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        super.onProgressChanged(Long.valueOf(getIdentifierForClient(webChromeClient)), identifierForStrongReference, l2, reply);
    }

    public void onShowCustomView(@NonNull WebChromeClient webChromeClient, @NonNull View view, @NonNull WebChromeClient.CustomViewCallback customViewCallback, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<Void> reply) {
        new ViewFlutterApiImpl(this.binaryMessenger, this.instanceManager).create(view, new GeneratedAndroidWebView.ViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.u3
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.ViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebChromeClientFlutterApiImpl.lambda$onShowCustomView$5((Void) obj);
            }
        });
        new CustomViewCallbackFlutterApiImpl(this.binaryMessenger, this.instanceManager).create(customViewCallback, new GeneratedAndroidWebView.CustomViewCallbackFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.v3
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.CustomViewCallbackFlutterApi.Reply
            public final void reply(Object obj) {
                WebChromeClientFlutterApiImpl.lambda$onShowCustomView$6((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        Long identifierForStrongReference2 = this.instanceManager.getIdentifierForStrongReference(view);
        Objects.requireNonNull(identifierForStrongReference2);
        Long identifierForStrongReference3 = this.instanceManager.getIdentifierForStrongReference(customViewCallback);
        Objects.requireNonNull(identifierForStrongReference3);
        onShowCustomView(identifierForStrongReference, identifierForStrongReference2, identifierForStrongReference3, reply);
    }

    @RequiresApi(api = 21)
    public void onShowFileChooser(@NonNull WebChromeClient webChromeClient, @NonNull WebView webView, @NonNull WebChromeClient.FileChooserParams fileChooserParams, @NonNull GeneratedAndroidWebView.WebChromeClientFlutterApi.Reply<List<String>> reply) {
        this.webViewFlutterApi.create(webView, new GeneratedAndroidWebView.WebViewFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.p3
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewFlutterApi.Reply
            public final void reply(Object obj) {
                WebChromeClientFlutterApiImpl.lambda$onShowFileChooser$1((Void) obj);
            }
        });
        new FileChooserParamsFlutterApiImpl(this.binaryMessenger, this.instanceManager).create(fileChooserParams, new GeneratedAndroidWebView.FileChooserParamsFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.q3
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.FileChooserParamsFlutterApi.Reply
            public final void reply(Object obj) {
                WebChromeClientFlutterApiImpl.lambda$onShowFileChooser$2((Void) obj);
            }
        });
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webChromeClient);
        Objects.requireNonNull(identifierForStrongReference);
        Long identifierForStrongReference2 = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference2);
        Long identifierForStrongReference3 = this.instanceManager.getIdentifierForStrongReference(fileChooserParams);
        Objects.requireNonNull(identifierForStrongReference3);
        onShowFileChooser(identifierForStrongReference, identifierForStrongReference2, identifierForStrongReference3, reply);
    }
}
