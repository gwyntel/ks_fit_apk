package androidx.webkit.internal;

import android.content.Context;
import android.net.Uri;
import android.webkit.SafeBrowsingResponse;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.List;

@RequiresApi(27)
/* loaded from: classes2.dex */
public class ApiHelperForOMR1 {
    private ApiHelperForOMR1() {
    }

    @DoNotInline
    public static void backToSafety(@NonNull SafeBrowsingResponse safeBrowsingResponse, boolean z2) {
        safeBrowsingResponse.backToSafety(z2);
    }

    @NonNull
    @DoNotInline
    public static Uri getSafeBrowsingPrivacyPolicyUrl() {
        return WebView.getSafeBrowsingPrivacyPolicyUrl();
    }

    @DoNotInline
    public static void proceed(@NonNull SafeBrowsingResponse safeBrowsingResponse, boolean z2) {
        safeBrowsingResponse.proceed(z2);
    }

    @DoNotInline
    public static void setSafeBrowsingWhitelist(@NonNull List<String> list, @Nullable ValueCallback<Boolean> valueCallback) {
        WebView.setSafeBrowsingWhitelist(list, valueCallback);
    }

    @DoNotInline
    public static void showInterstitial(@NonNull SafeBrowsingResponse safeBrowsingResponse, boolean z2) {
        safeBrowsingResponse.showInterstitial(z2);
    }

    @DoNotInline
    public static void startSafeBrowsing(@NonNull Context context, @Nullable ValueCallback<Boolean> valueCallback) {
        WebView.startSafeBrowsing(context, valueCallback);
    }
}
