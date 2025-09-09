package com.google.android.gms.auth.api;

import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.ShowFirstParty;

/* loaded from: classes3.dex */
public final class Auth {

    @NonNull
    public static final Api<GoogleSignInOptions> GOOGLE_SIGN_IN_API;

    @NonNull
    public static final GoogleSignInApi GoogleSignInApi;

    @NonNull
    @Deprecated
    @ShowFirstParty
    @KeepForSdk
    public static final Api<AuthProxyOptions> PROXY_API;

    @NonNull
    @Deprecated
    @ShowFirstParty
    @KeepForSdk
    public static final ProxyApi ProxyApi;

    @NonNull
    public static final Api.ClientKey zba;

    @NonNull
    public static final Api.ClientKey zbb;

    @NonNull
    public static final Api zbc;
    public static final com.google.android.gms.internal.p000authapi.zbd zbd;
    private static final Api.AbstractClientBuilder zbe;
    private static final Api.AbstractClientBuilder zbf;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zba = clientKey;
        Api.ClientKey clientKey2 = new Api.ClientKey();
        zbb = clientKey2;
        zba zbaVar = new zba();
        zbe = zbaVar;
        zbb zbbVar = new zbb();
        zbf = zbbVar;
        PROXY_API = AuthProxy.API;
        zbc = new Api("Auth.CREDENTIALS_API", zbaVar, clientKey);
        GOOGLE_SIGN_IN_API = new Api<>("Auth.GOOGLE_SIGN_IN_API", zbbVar, clientKey2);
        ProxyApi = AuthProxy.ProxyApi;
        zbd = new com.google.android.gms.internal.p000authapi.zbd();
        GoogleSignInApi = new com.google.android.gms.auth.api.signin.internal.zbd();
    }

    private Auth() {
    }
}
