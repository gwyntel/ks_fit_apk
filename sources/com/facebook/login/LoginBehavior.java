package com.facebook.login;

/* loaded from: classes3.dex */
public enum LoginBehavior {
    NATIVE_WITH_FALLBACK(true, true, true, false, true, true),
    NATIVE_ONLY(true, true, false, false, false, true),
    KATANA_ONLY(false, true, false, false, false, false),
    WEB_ONLY(false, false, true, false, true, false),
    WEB_VIEW_ONLY(false, false, true, false, false, false),
    DIALOG_ONLY(false, true, true, false, true, true),
    DEVICE_AUTH(false, false, false, true, false, false);

    private final boolean allowsCustomTabAuth;
    private final boolean allowsDeviceAuth;
    private final boolean allowsFacebookLiteAuth;
    private final boolean allowsGetTokenAuth;
    private final boolean allowsKatanaAuth;
    private final boolean allowsWebViewAuth;

    LoginBehavior(boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        this.allowsGetTokenAuth = z2;
        this.allowsKatanaAuth = z3;
        this.allowsWebViewAuth = z4;
        this.allowsDeviceAuth = z5;
        this.allowsCustomTabAuth = z6;
        this.allowsFacebookLiteAuth = z7;
    }

    boolean allowsCustomTabAuth() {
        return this.allowsCustomTabAuth;
    }

    boolean allowsDeviceAuth() {
        return this.allowsDeviceAuth;
    }

    boolean allowsFacebookLiteAuth() {
        return this.allowsFacebookLiteAuth;
    }

    boolean allowsGetTokenAuth() {
        return this.allowsGetTokenAuth;
    }

    boolean allowsKatanaAuth() {
        return this.allowsKatanaAuth;
    }

    boolean allowsWebViewAuth() {
        return this.allowsWebViewAuth;
    }
}
