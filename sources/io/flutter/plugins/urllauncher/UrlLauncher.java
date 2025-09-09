package io.flutter.plugins.urllauncher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import io.flutter.plugins.urllauncher.Messages;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
final class UrlLauncher implements Messages.UrlLauncherApi {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "UrlLauncher";

    @Nullable
    private Activity activity;

    @NonNull
    private final Context applicationContext;

    @NonNull
    private final IntentResolver intentResolver;

    @VisibleForTesting
    interface IntentResolver {
        String getHandlerComponentName(@NonNull Intent intent);
    }

    @VisibleForTesting
    UrlLauncher(@NonNull Context context, @NonNull IntentResolver intentResolver) {
        this.applicationContext = context;
        this.intentResolver = intentResolver;
    }

    private static boolean containsRestrictedHeader(Map<String, String> map) {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String lowerCase = it.next().toLowerCase(Locale.US);
            lowerCase.hashCode();
            switch (lowerCase) {
                case "accept":
                case "content-language":
                case "content-type":
                case "accept-language":
                default:
                    return true;
            }
        }
        return false;
    }

    private void ensureActivity() {
        if (this.activity == null) {
            throw new Messages.FlutterError("NO_ACTIVITY", "Launching a URL requires a foreground activity.", null);
        }
    }

    @NonNull
    private static Bundle extractBundle(Map<String, String> map) {
        Bundle bundle = new Bundle();
        for (String str : map.keySet()) {
            bundle.putString(str, map.get(str));
        }
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$new$0(Context context, Intent intent) {
        ComponentName componentNameResolveActivity = intent.resolveActivity(context.getPackageManager());
        if (componentNameResolveActivity == null) {
            return null;
        }
        return componentNameResolveActivity.toShortString();
    }

    private static boolean openCustomTab(@NonNull Context context, @NonNull Uri uri, @NonNull Bundle bundle, @NonNull Messages.BrowserOptions browserOptions) {
        CustomTabsIntent customTabsIntentBuild = new CustomTabsIntent.Builder().setShowTitle(browserOptions.getShowTitle().booleanValue()).build();
        customTabsIntentBuild.intent.putExtra("com.android.browser.headers", bundle);
        try {
            customTabsIntentBuild.launchUrl(context, uri);
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }

    @Override // io.flutter.plugins.urllauncher.Messages.UrlLauncherApi
    @NonNull
    public Boolean canLaunchUrl(@NonNull String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        return this.intentResolver.getHandlerComponentName(intent) == null ? Boolean.FALSE : Boolean.valueOf(!"{com.android.fallback/com.android.fallback.Fallback}".equals(r3));
    }

    @Override // io.flutter.plugins.urllauncher.Messages.UrlLauncherApi
    public void closeWebView() {
        this.applicationContext.sendBroadcast(new Intent(WebViewActivity.ACTION_CLOSE));
    }

    @Override // io.flutter.plugins.urllauncher.Messages.UrlLauncherApi
    @NonNull
    public Boolean launchUrl(@NonNull String str, @NonNull Map<String, String> map) {
        ensureActivity();
        try {
            this.activity.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(str)).putExtra("com.android.browser.headers", extractBundle(map)));
            return Boolean.TRUE;
        } catch (ActivityNotFoundException unused) {
            return Boolean.FALSE;
        }
    }

    @Override // io.flutter.plugins.urllauncher.Messages.UrlLauncherApi
    @NonNull
    public Boolean openUrlInApp(@NonNull String str, @NonNull Boolean bool, @NonNull Messages.WebViewOptions webViewOptions, @NonNull Messages.BrowserOptions browserOptions) {
        ensureActivity();
        Bundle bundleExtractBundle = extractBundle(webViewOptions.getHeaders());
        if (bool.booleanValue() && !containsRestrictedHeader(webViewOptions.getHeaders())) {
            if (openCustomTab(this.activity, Uri.parse(str), bundleExtractBundle, browserOptions)) {
                return Boolean.TRUE;
            }
        }
        try {
            this.activity.startActivity(WebViewActivity.createIntent(this.activity, str, webViewOptions.getEnableJavaScript().booleanValue(), webViewOptions.getEnableDomStorage().booleanValue(), bundleExtractBundle));
            return Boolean.TRUE;
        } catch (ActivityNotFoundException unused) {
            return Boolean.FALSE;
        }
    }

    void setActivity(@Nullable Activity activity) {
        this.activity = activity;
    }

    @Override // io.flutter.plugins.urllauncher.Messages.UrlLauncherApi
    @NonNull
    public Boolean supportsCustomTabs() {
        return Boolean.valueOf(CustomTabsClient.getPackageName(this.applicationContext, Collections.emptyList()) != null);
    }

    UrlLauncher(@NonNull final Context context) {
        this(context, new IntentResolver() { // from class: io.flutter.plugins.urllauncher.g
            @Override // io.flutter.plugins.urllauncher.UrlLauncher.IntentResolver
            public final String getHandlerComponentName(Intent intent) {
                return UrlLauncher.lambda$new$0(context, intent);
            }
        });
    }
}
