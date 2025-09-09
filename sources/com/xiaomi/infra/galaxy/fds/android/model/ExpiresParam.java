package com.xiaomi.infra.galaxy.fds.android.model;

/* loaded from: classes4.dex */
public class ExpiresParam extends UserParam {
    private static final String EXPIRES = "expires";

    public ExpiresParam(long j2) {
        this.params.put(EXPIRES, Long.toString(j2));
    }
}
