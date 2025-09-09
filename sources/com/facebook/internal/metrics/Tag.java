package com.facebook.internal.metrics;

/* loaded from: classes3.dex */
public enum Tag {
    FACEBOOK_CORE_STARTUP("facebook_core_startup");

    private final String suffix;

    Tag(String str) {
        this.suffix = str;
    }

    public String getSuffix() {
        return this.suffix;
    }
}
