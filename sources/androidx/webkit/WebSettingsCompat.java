package androidx.webkit;

import android.webkit.WebSettings;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.webkit.internal.ApiFeature;
import androidx.webkit.internal.ApiHelperForM;
import androidx.webkit.internal.ApiHelperForN;
import androidx.webkit.internal.ApiHelperForO;
import androidx.webkit.internal.ApiHelperForQ;
import androidx.webkit.internal.WebSettingsAdapter;
import androidx.webkit.internal.WebViewFeatureInternal;
import androidx.webkit.internal.WebViewGlueCommunicator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

/* loaded from: classes2.dex */
public class WebSettingsCompat {
    public static final int ATTRIBUTION_BEHAVIOR_APP_SOURCE_AND_APP_TRIGGER = 3;
    public static final int ATTRIBUTION_BEHAVIOR_APP_SOURCE_AND_WEB_TRIGGER = 1;
    public static final int ATTRIBUTION_BEHAVIOR_DISABLED = 0;
    public static final int ATTRIBUTION_BEHAVIOR_WEB_SOURCE_AND_WEB_TRIGGER = 2;

    @Deprecated
    public static final int DARK_STRATEGY_PREFER_WEB_THEME_OVER_USER_AGENT_DARKENING = 2;

    @Deprecated
    public static final int DARK_STRATEGY_USER_AGENT_DARKENING_ONLY = 0;

    @Deprecated
    public static final int DARK_STRATEGY_WEB_THEME_DARKENING_ONLY = 1;

    @Deprecated
    public static final int FORCE_DARK_AUTO = 1;

    @Deprecated
    public static final int FORCE_DARK_OFF = 0;

    @Deprecated
    public static final int FORCE_DARK_ON = 2;

    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ForceDark {
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ForceDarkStrategy {
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface MenuItemFlags {
    }

    private WebSettingsCompat() {
    }

    private static WebSettingsAdapter getAdapter(WebSettings webSettings) {
        return WebViewGlueCommunicator.getCompatConverter().convertSettings(webSettings);
    }

    public static int getAttributionRegistrationBehavior(@NonNull WebSettings webSettings) {
        if (WebViewFeatureInternal.ATTRIBUTION_REGISTRATION_BEHAVIOR.isSupportedByWebView()) {
            return getAdapter(webSettings).getAttributionRegistrationBehavior();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    public static int getDisabledActionModeMenuItems(@NonNull WebSettings webSettings) {
        ApiFeature.N n2 = WebViewFeatureInternal.DISABLED_ACTION_MODE_MENU_ITEMS;
        if (n2.isSupportedByFramework()) {
            return ApiHelperForN.getDisabledActionModeMenuItems(webSettings);
        }
        if (n2.isSupportedByWebView()) {
            return getAdapter(webSettings).getDisabledActionModeMenuItems();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    public static boolean getEnterpriseAuthenticationAppLinkPolicyEnabled(@NonNull WebSettings webSettings) {
        if (WebViewFeatureInternal.ENTERPRISE_AUTHENTICATION_APP_LINK_POLICY.isSupportedByWebView()) {
            return getAdapter(webSettings).getEnterpriseAuthenticationAppLinkPolicyEnabled();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    @Deprecated
    public static int getForceDark(@NonNull WebSettings webSettings) {
        ApiFeature.Q q2 = WebViewFeatureInternal.FORCE_DARK;
        if (q2.isSupportedByFramework()) {
            return ApiHelperForQ.getForceDark(webSettings);
        }
        if (q2.isSupportedByWebView()) {
            return getAdapter(webSettings).getForceDark();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    @Deprecated
    public static int getForceDarkStrategy(@NonNull WebSettings webSettings) {
        if (WebViewFeatureInternal.FORCE_DARK_STRATEGY.isSupportedByWebView()) {
            return getAdapter(webSettings).getForceDark();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    public static boolean getOffscreenPreRaster(@NonNull WebSettings webSettings) {
        ApiFeature.M m2 = WebViewFeatureInternal.OFF_SCREEN_PRERASTER;
        if (m2.isSupportedByFramework()) {
            return ApiHelperForM.getOffscreenPreRaster(webSettings);
        }
        if (m2.isSupportedByWebView()) {
            return getAdapter(webSettings).getOffscreenPreRaster();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    @NonNull
    public static Set<String> getRequestedWithHeaderOriginAllowList(@NonNull WebSettings webSettings) {
        if (WebViewFeatureInternal.REQUESTED_WITH_HEADER_ALLOW_LIST.isSupportedByWebView()) {
            return getAdapter(webSettings).getRequestedWithHeaderOriginAllowList();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    public static boolean getSafeBrowsingEnabled(@NonNull WebSettings webSettings) {
        ApiFeature.O o2 = WebViewFeatureInternal.SAFE_BROWSING_ENABLE;
        if (o2.isSupportedByFramework()) {
            return ApiHelperForO.getSafeBrowsingEnabled(webSettings);
        }
        if (o2.isSupportedByWebView()) {
            return getAdapter(webSettings).getSafeBrowsingEnabled();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    @NonNull
    public static UserAgentMetadata getUserAgentMetadata(@NonNull WebSettings webSettings) {
        if (WebViewFeatureInternal.USER_AGENT_METADATA.isSupportedByWebView()) {
            return getAdapter(webSettings).getUserAgentMetadata();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    @NonNull
    public static WebViewMediaIntegrityApiStatusConfig getWebViewMediaIntegrityApiStatus(@NonNull WebSettings webSettings) {
        if (WebViewFeatureInternal.WEBVIEW_MEDIA_INTEGRITY_API_STATUS.isSupportedByWebView()) {
            return getAdapter(webSettings).getWebViewMediaIntegrityApiStatus();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    public static boolean isAlgorithmicDarkeningAllowed(@NonNull WebSettings webSettings) {
        if (WebViewFeatureInternal.ALGORITHMIC_DARKENING.isSupportedByWebView()) {
            return getAdapter(webSettings).isAlgorithmicDarkeningAllowed();
        }
        throw WebViewFeatureInternal.getUnsupportedOperationException();
    }

    public static void setAlgorithmicDarkeningAllowed(@NonNull WebSettings webSettings, boolean z2) {
        if (!WebViewFeatureInternal.ALGORITHMIC_DARKENING.isSupportedByWebView()) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        getAdapter(webSettings).setAlgorithmicDarkeningAllowed(z2);
    }

    public static void setAttributionRegistrationBehavior(@NonNull WebSettings webSettings, int i2) {
        if (!WebViewFeatureInternal.ATTRIBUTION_REGISTRATION_BEHAVIOR.isSupportedByWebView()) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        getAdapter(webSettings).setAttributionRegistrationBehavior(i2);
    }

    public static void setDisabledActionModeMenuItems(@NonNull WebSettings webSettings, int i2) {
        ApiFeature.N n2 = WebViewFeatureInternal.DISABLED_ACTION_MODE_MENU_ITEMS;
        if (n2.isSupportedByFramework()) {
            ApiHelperForN.setDisabledActionModeMenuItems(webSettings, i2);
        } else {
            if (!n2.isSupportedByWebView()) {
                throw WebViewFeatureInternal.getUnsupportedOperationException();
            }
            getAdapter(webSettings).setDisabledActionModeMenuItems(i2);
        }
    }

    public static void setEnterpriseAuthenticationAppLinkPolicyEnabled(@NonNull WebSettings webSettings, boolean z2) {
        if (!WebViewFeatureInternal.ENTERPRISE_AUTHENTICATION_APP_LINK_POLICY.isSupportedByWebView()) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        getAdapter(webSettings).setEnterpriseAuthenticationAppLinkPolicyEnabled(z2);
    }

    @Deprecated
    public static void setForceDark(@NonNull WebSettings webSettings, int i2) {
        ApiFeature.Q q2 = WebViewFeatureInternal.FORCE_DARK;
        if (q2.isSupportedByFramework()) {
            ApiHelperForQ.setForceDark(webSettings, i2);
        } else {
            if (!q2.isSupportedByWebView()) {
                throw WebViewFeatureInternal.getUnsupportedOperationException();
            }
            getAdapter(webSettings).setForceDark(i2);
        }
    }

    @Deprecated
    public static void setForceDarkStrategy(@NonNull WebSettings webSettings, int i2) {
        if (!WebViewFeatureInternal.FORCE_DARK_STRATEGY.isSupportedByWebView()) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        getAdapter(webSettings).setForceDarkStrategy(i2);
    }

    public static void setOffscreenPreRaster(@NonNull WebSettings webSettings, boolean z2) {
        ApiFeature.M m2 = WebViewFeatureInternal.OFF_SCREEN_PRERASTER;
        if (m2.isSupportedByFramework()) {
            ApiHelperForM.setOffscreenPreRaster(webSettings, z2);
        } else {
            if (!m2.isSupportedByWebView()) {
                throw WebViewFeatureInternal.getUnsupportedOperationException();
            }
            getAdapter(webSettings).setOffscreenPreRaster(z2);
        }
    }

    public static void setRequestedWithHeaderOriginAllowList(@NonNull WebSettings webSettings, @NonNull Set<String> set) {
        if (!WebViewFeatureInternal.REQUESTED_WITH_HEADER_ALLOW_LIST.isSupportedByWebView()) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        getAdapter(webSettings).setRequestedWithHeaderOriginAllowList(set);
    }

    public static void setSafeBrowsingEnabled(@NonNull WebSettings webSettings, boolean z2) {
        ApiFeature.O o2 = WebViewFeatureInternal.SAFE_BROWSING_ENABLE;
        if (o2.isSupportedByFramework()) {
            ApiHelperForO.setSafeBrowsingEnabled(webSettings, z2);
        } else {
            if (!o2.isSupportedByWebView()) {
                throw WebViewFeatureInternal.getUnsupportedOperationException();
            }
            getAdapter(webSettings).setSafeBrowsingEnabled(z2);
        }
    }

    public static void setUserAgentMetadata(@NonNull WebSettings webSettings, @NonNull UserAgentMetadata userAgentMetadata) {
        if (!WebViewFeatureInternal.USER_AGENT_METADATA.isSupportedByWebView()) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        getAdapter(webSettings).setUserAgentMetadata(userAgentMetadata);
    }

    public static void setWebViewMediaIntegrityApiStatus(@NonNull WebSettings webSettings, @NonNull WebViewMediaIntegrityApiStatusConfig webViewMediaIntegrityApiStatusConfig) {
        if (!WebViewFeatureInternal.WEBVIEW_MEDIA_INTEGRITY_API_STATUS.isSupportedByWebView()) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        getAdapter(webSettings).setWebViewMediaIntegrityApiStatus(webViewMediaIntegrityApiStatusConfig);
    }
}
