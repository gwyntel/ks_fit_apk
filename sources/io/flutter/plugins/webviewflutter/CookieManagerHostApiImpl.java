package io.flutter.plugins.webviewflutter;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Objects;

/* loaded from: classes4.dex */
public class CookieManagerHostApiImpl implements GeneratedAndroidWebView.CookieManagerHostApi {
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;
    private final CookieManagerProxy proxy;

    @NonNull
    private final AndroidSdkChecker sdkChecker;

    @VisibleForTesting
    interface AndroidSdkChecker {
        @ChecksSdkIntAtLeast(parameter = 0)
        boolean sdkIsAtLeast(int i2);
    }

    @VisibleForTesting
    static class CookieManagerProxy {
        CookieManagerProxy() {
        }

        @NonNull
        public CookieManager getInstance() {
            return CookieManager.getInstance();
        }
    }

    public CookieManagerHostApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this(binaryMessenger, instanceManager, new CookieManagerProxy());
    }

    @NonNull
    private CookieManager getCookieManagerInstance(@NonNull Long l2) {
        CookieManager cookieManager = (CookieManager) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(cookieManager);
        return cookieManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$0(int i2) {
        return Build.VERSION.SDK_INT >= i2;
    }

    private boolean removeCookiesPreL(CookieManager cookieManager) {
        boolean zHasCookies = cookieManager.hasCookies();
        if (zHasCookies) {
            cookieManager.removeAllCookie();
        }
        return zHasCookies;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.CookieManagerHostApi
    public void attachInstance(@NonNull Long l2) {
        this.instanceManager.addDartCreatedInstance(this.proxy.getInstance(), l2.longValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.CookieManagerHostApi
    public void removeAllCookies(@NonNull Long l2, @NonNull final GeneratedAndroidWebView.Result<Boolean> result) {
        if (!this.sdkChecker.sdkIsAtLeast(21)) {
            result.success(Boolean.valueOf(removeCookiesPreL(getCookieManagerInstance(l2))));
            return;
        }
        CookieManager cookieManagerInstance = getCookieManagerInstance(l2);
        Objects.requireNonNull(result);
        cookieManagerInstance.removeAllCookies(new ValueCallback() { // from class: io.flutter.plugins.webviewflutter.b
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                result.success((Boolean) obj);
            }
        });
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.CookieManagerHostApi
    public void setAcceptThirdPartyCookies(@NonNull Long l2, @NonNull Long l3, @NonNull Boolean bool) {
        if (!this.sdkChecker.sdkIsAtLeast(21)) {
            throw new UnsupportedOperationException("`setAcceptThirdPartyCookies` is unsupported on versions below `Build.VERSION_CODES.LOLLIPOP`.");
        }
        CookieManager cookieManagerInstance = getCookieManagerInstance(l2);
        WebView webView = (WebView) this.instanceManager.getInstance(l3.longValue());
        Objects.requireNonNull(webView);
        cookieManagerInstance.setAcceptThirdPartyCookies(webView, bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.CookieManagerHostApi
    public void setCookie(@NonNull Long l2, @NonNull String str, @NonNull String str2) {
        getCookieManagerInstance(l2).setCookie(str, str2);
    }

    @VisibleForTesting
    CookieManagerHostApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager, @NonNull CookieManagerProxy cookieManagerProxy) {
        this(binaryMessenger, instanceManager, cookieManagerProxy, new AndroidSdkChecker() { // from class: io.flutter.plugins.webviewflutter.a
            @Override // io.flutter.plugins.webviewflutter.CookieManagerHostApiImpl.AndroidSdkChecker
            public final boolean sdkIsAtLeast(int i2) {
                return CookieManagerHostApiImpl.lambda$new$0(i2);
            }
        });
    }

    @VisibleForTesting
    CookieManagerHostApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager, @NonNull CookieManagerProxy cookieManagerProxy, @NonNull AndroidSdkChecker androidSdkChecker) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
        this.proxy = cookieManagerProxy;
        this.sdkChecker = androidSdkChecker;
    }
}
