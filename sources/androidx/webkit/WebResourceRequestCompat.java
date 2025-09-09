package androidx.webkit;

import android.webkit.WebResourceRequest;
import androidx.annotation.NonNull;
import androidx.webkit.internal.ApiFeature;
import androidx.webkit.internal.ApiHelperForN;
import androidx.webkit.internal.WebResourceRequestAdapter;
import androidx.webkit.internal.WebViewFeatureInternal;
import androidx.webkit.internal.WebViewGlueCommunicator;

/* loaded from: classes2.dex */
public class WebResourceRequestCompat {
    private WebResourceRequestCompat() {
    }

    private static WebResourceRequestAdapter getAdapter(WebResourceRequest webResourceRequest) {
        return WebViewGlueCommunicator.getCompatConverter().convertWebResourceRequest(webResourceRequest);
    }

    public static boolean isRedirect(@NonNull WebResourceRequest webResourceRequest) {
        ApiFeature.N n2 = WebViewFeatureInternal.WEB_RESOURCE_REQUEST_IS_REDIRECT;
        if (n2.isSupportedByFramework()) {
            return ApiHelperForN.isRedirect(webResourceRequest);
        }
        if (n2.isSupportedByWebView()) {
            return getAdapter(webResourceRequest).isRedirect();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }
}
