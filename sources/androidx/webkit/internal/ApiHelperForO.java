package androidx.webkit.internal;

import android.content.pm.PackageInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(26)
/* loaded from: classes2.dex */
public class ApiHelperForO {
    private ApiHelperForO() {
    }

    @NonNull
    @DoNotInline
    public static PackageInfo getCurrentWebViewPackage() {
        return WebView.getCurrentWebViewPackage();
    }

    @DoNotInline
    public static boolean getSafeBrowsingEnabled(@NonNull WebSettings webSettings) {
        return webSettings.getSafeBrowsingEnabled();
    }

    @Nullable
    @DoNotInline
    public static WebChromeClient getWebChromeClient(@NonNull WebView webView) {
        return webView.getWebChromeClient();
    }

    @Nullable
    @DoNotInline
    public static WebViewClient getWebViewClient(@NonNull WebView webView) {
        return webView.getWebViewClient();
    }

    @DoNotInline
    public static void setSafeBrowsingEnabled(@NonNull WebSettings webSettings, boolean z2) {
        webSettings.setSafeBrowsingEnabled(z2);
    }
}
