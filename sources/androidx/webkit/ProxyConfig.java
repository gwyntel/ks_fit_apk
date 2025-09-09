package androidx.webkit;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class ProxyConfig {
    private static final String BYPASS_RULE_REMOVE_IMPLICIT = "<-loopback>";
    private static final String BYPASS_RULE_SIMPLE_NAMES = "<local>";
    private static final String DIRECT = "direct://";
    public static final String MATCH_ALL_SCHEMES = "*";
    public static final String MATCH_HTTP = "http";
    public static final String MATCH_HTTPS = "https";
    private List<String> mBypassRules;
    private List<ProxyRule> mProxyRules;
    private boolean mReverseBypass;

    public static final class Builder {
        private List<String> mBypassRules;
        private List<ProxyRule> mProxyRules;
        private boolean mReverseBypass;

        public Builder() {
            this.mReverseBypass = false;
            this.mProxyRules = new ArrayList();
            this.mBypassRules = new ArrayList();
        }

        @NonNull
        private List<String> bypassRules() {
            return this.mBypassRules;
        }

        @NonNull
        private List<ProxyRule> proxyRules() {
            return this.mProxyRules;
        }

        private boolean reverseBypass() {
            return this.mReverseBypass;
        }

        @NonNull
        public Builder addBypassRule(@NonNull String str) {
            this.mBypassRules.add(str);
            return this;
        }

        @NonNull
        public Builder addDirect(@NonNull String str) {
            this.mProxyRules.add(new ProxyRule(str, ProxyConfig.DIRECT));
            return this;
        }

        @NonNull
        public Builder addProxyRule(@NonNull String str) {
            this.mProxyRules.add(new ProxyRule(str));
            return this;
        }

        @NonNull
        public ProxyConfig build() {
            return new ProxyConfig(proxyRules(), bypassRules(), reverseBypass());
        }

        @NonNull
        public Builder bypassSimpleHostnames() {
            return addBypassRule(ProxyConfig.BYPASS_RULE_SIMPLE_NAMES);
        }

        @NonNull
        public Builder removeImplicitRules() {
            return addBypassRule(ProxyConfig.BYPASS_RULE_REMOVE_IMPLICIT);
        }

        @NonNull
        public Builder setReverseBypassEnabled(boolean z2) {
            this.mReverseBypass = z2;
            return this;
        }

        @NonNull
        public Builder addDirect() {
            return addDirect(ProxyConfig.MATCH_ALL_SCHEMES);
        }

        @NonNull
        public Builder addProxyRule(@NonNull String str, @NonNull String str2) {
            this.mProxyRules.add(new ProxyRule(str2, str));
            return this;
        }

        public Builder(@NonNull ProxyConfig proxyConfig) {
            this.mReverseBypass = false;
            this.mProxyRules = proxyConfig.getProxyRules();
            this.mBypassRules = proxyConfig.getBypassRules();
            this.mReverseBypass = proxyConfig.isReverseBypassEnabled();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ProxyScheme {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ProxyConfig(@NonNull List<ProxyRule> list, @NonNull List<String> list2, boolean z2) {
        this.mProxyRules = list;
        this.mBypassRules = list2;
        this.mReverseBypass = z2;
    }

    @NonNull
    public List<String> getBypassRules() {
        return Collections.unmodifiableList(this.mBypassRules);
    }

    @NonNull
    public List<ProxyRule> getProxyRules() {
        return Collections.unmodifiableList(this.mProxyRules);
    }

    public boolean isReverseBypassEnabled() {
        return this.mReverseBypass;
    }

    public static final class ProxyRule {
        private String mSchemeFilter;
        private String mUrl;

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public ProxyRule(@NonNull String str, @NonNull String str2) {
            this.mSchemeFilter = str;
            this.mUrl = str2;
        }

        @NonNull
        public String getSchemeFilter() {
            return this.mSchemeFilter;
        }

        @NonNull
        public String getUrl() {
            return this.mUrl;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public ProxyRule(@NonNull String str) {
            this(ProxyConfig.MATCH_ALL_SCHEMES, str);
        }
    }
}
