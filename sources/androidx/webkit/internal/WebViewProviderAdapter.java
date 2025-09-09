package androidx.webkit.internal;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.webkit.Profile;
import androidx.webkit.WebMessageCompat;
import androidx.webkit.WebMessagePortCompat;
import androidx.webkit.WebViewCompat;
import androidx.webkit.WebViewRenderProcess;
import androidx.webkit.WebViewRenderProcessClient;
import java.lang.reflect.InvocationHandler;
import java.util.concurrent.Executor;
import org.chromium.support_lib_boundary.ProfileBoundaryInterface;
import org.chromium.support_lib_boundary.WebViewProviderBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;

/* loaded from: classes2.dex */
public class WebViewProviderAdapter {

    /* renamed from: a, reason: collision with root package name */
    WebViewProviderBoundaryInterface f6534a;

    public WebViewProviderAdapter(@NonNull WebViewProviderBoundaryInterface webViewProviderBoundaryInterface) {
        this.f6534a = webViewProviderBoundaryInterface;
    }

    @NonNull
    public ScriptHandlerImpl addDocumentStartJavaScript(@NonNull String str, @NonNull String[] strArr) {
        return ScriptHandlerImpl.toScriptHandler(this.f6534a.addDocumentStartJavaScript(str, strArr));
    }

    public void addWebMessageListener(@NonNull String str, @NonNull String[] strArr, @NonNull WebViewCompat.WebMessageListener webMessageListener) {
        this.f6534a.addWebMessageListener(str, strArr, BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(new WebMessageListenerAdapter(webMessageListener)));
    }

    @NonNull
    public WebMessagePortCompat[] createWebMessageChannel() {
        InvocationHandler[] invocationHandlerArrCreateWebMessageChannel = this.f6534a.createWebMessageChannel();
        WebMessagePortCompat[] webMessagePortCompatArr = new WebMessagePortCompat[invocationHandlerArrCreateWebMessageChannel.length];
        for (int i2 = 0; i2 < invocationHandlerArrCreateWebMessageChannel.length; i2++) {
            webMessagePortCompatArr[i2] = new WebMessagePortImpl(invocationHandlerArrCreateWebMessageChannel[i2]);
        }
        return webMessagePortCompatArr;
    }

    @NonNull
    public Profile getProfile() {
        return new ProfileImpl((ProfileBoundaryInterface) BoundaryInterfaceReflectionUtil.castToSuppLibClass(ProfileBoundaryInterface.class, this.f6534a.getProfile()));
    }

    @Nullable
    public WebChromeClient getWebChromeClient() {
        return this.f6534a.getWebChromeClient();
    }

    @NonNull
    public WebViewClient getWebViewClient() {
        return this.f6534a.getWebViewClient();
    }

    @Nullable
    public WebViewRenderProcess getWebViewRenderProcess() {
        return WebViewRenderProcessImpl.forInvocationHandler(this.f6534a.getWebViewRenderer());
    }

    @Nullable
    public WebViewRenderProcessClient getWebViewRenderProcessClient() {
        InvocationHandler webViewRendererClient = this.f6534a.getWebViewRendererClient();
        if (webViewRendererClient == null) {
            return null;
        }
        return ((WebViewRenderProcessClientAdapter) BoundaryInterfaceReflectionUtil.getDelegateFromInvocationHandler(webViewRendererClient)).getWebViewRenderProcessClient();
    }

    public void insertVisualStateCallback(long j2, @NonNull WebViewCompat.VisualStateCallback visualStateCallback) {
        this.f6534a.insertVisualStateCallback(j2, BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(new VisualStateCallbackAdapter(visualStateCallback)));
    }

    public boolean isAudioMuted() {
        return this.f6534a.isAudioMuted();
    }

    public void postWebMessage(@NonNull WebMessageCompat webMessageCompat, @NonNull Uri uri) {
        this.f6534a.postMessageToMainFrame(BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(new WebMessageAdapter(webMessageCompat)), uri);
    }

    public void removeWebMessageListener(@NonNull String str) {
        this.f6534a.removeWebMessageListener(str);
    }

    public void setAudioMuted(boolean z2) {
        this.f6534a.setAudioMuted(z2);
    }

    public void setProfileWithName(@NonNull String str) {
        this.f6534a.setProfile(str);
    }

    @SuppressLint({"LambdaLast"})
    public void setWebViewRenderProcessClient(@Nullable Executor executor, @Nullable WebViewRenderProcessClient webViewRenderProcessClient) {
        this.f6534a.setWebViewRendererClient(webViewRenderProcessClient != null ? BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(new WebViewRenderProcessClientAdapter(executor, webViewRenderProcessClient)) : null);
    }
}
