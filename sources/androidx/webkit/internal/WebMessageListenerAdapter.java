package androidx.webkit.internal;

import android.net.Uri;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.webkit.WebMessageCompat;
import androidx.webkit.WebViewCompat;
import java.lang.reflect.InvocationHandler;
import org.chromium.support_lib_boundary.WebMessageBoundaryInterface;
import org.chromium.support_lib_boundary.WebMessageListenerBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;

/* loaded from: classes2.dex */
public class WebMessageListenerAdapter implements WebMessageListenerBoundaryInterface {
    private WebViewCompat.WebMessageListener mWebMessageListener;

    public WebMessageListenerAdapter(@NonNull WebViewCompat.WebMessageListener webMessageListener) {
        this.mWebMessageListener = webMessageListener;
    }

    @Override // org.chromium.support_lib_boundary.FeatureFlagHolderBoundaryInterface
    @NonNull
    public String[] getSupportedFeatures() {
        return new String[]{"WEB_MESSAGE_LISTENER", "WEB_MESSAGE_ARRAY_BUFFER"};
    }

    @Override // org.chromium.support_lib_boundary.WebMessageListenerBoundaryInterface
    public void onPostMessage(@NonNull WebView webView, @NonNull InvocationHandler invocationHandler, @NonNull Uri uri, boolean z2, @NonNull InvocationHandler invocationHandler2) {
        WebMessageCompat webMessageCompatWebMessageCompatFromBoundaryInterface = WebMessageAdapter.webMessageCompatFromBoundaryInterface((WebMessageBoundaryInterface) BoundaryInterfaceReflectionUtil.castToSuppLibClass(WebMessageBoundaryInterface.class, invocationHandler));
        if (webMessageCompatWebMessageCompatFromBoundaryInterface != null) {
            this.mWebMessageListener.onPostMessage(webView, webMessageCompatWebMessageCompatFromBoundaryInterface, uri, z2, JavaScriptReplyProxyImpl.forInvocationHandler(invocationHandler2));
        }
    }
}
