package androidx.webkit;

import android.webkit.CookieManager;
import androidx.annotation.NonNull;
import androidx.webkit.internal.CookieManagerAdapter;
import androidx.webkit.internal.WebViewFeatureInternal;
import androidx.webkit.internal.WebViewGlueCommunicator;
import java.util.List;

/* loaded from: classes2.dex */
public class CookieManagerCompat {
    private CookieManagerCompat() {
    }

    private static CookieManagerAdapter getAdapter(CookieManager cookieManager) {
        return WebViewGlueCommunicator.getCompatConverter().convertCookieManager(cookieManager);
    }

    @NonNull
    public static List<String> getCookieInfo(@NonNull CookieManager cookieManager, @NonNull String str) {
        if (WebViewFeatureInternal.GET_COOKIE_INFO.isSupportedByWebView()) {
            return getAdapter(cookieManager).getCookieInfo(str);
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }
}
