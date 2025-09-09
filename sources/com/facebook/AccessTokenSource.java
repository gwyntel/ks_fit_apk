package com.facebook;

/* loaded from: classes3.dex */
public enum AccessTokenSource {
    NONE(false),
    FACEBOOK_APPLICATION_WEB(true),
    FACEBOOK_APPLICATION_NATIVE(true),
    FACEBOOK_APPLICATION_SERVICE(true),
    WEB_VIEW(true),
    CHROME_CUSTOM_TAB(true),
    TEST_USER(true),
    CLIENT_TOKEN(true),
    DEVICE_AUTH(true);

    private final boolean canExtendToken;

    AccessTokenSource(boolean z2) {
        this.canExtendToken = z2;
    }

    boolean canExtendToken() {
        return this.canExtendToken;
    }
}
