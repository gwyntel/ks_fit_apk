package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ShowFirstParty;

/* loaded from: classes3.dex */
public final class zad {

    /* renamed from: a, reason: collision with root package name */
    static final Api.AbstractClientBuilder f13335a;
    public static final Api.ClientKey zaa;

    @ShowFirstParty
    public static final Api.ClientKey zab;
    public static final Api.AbstractClientBuilder zac;
    public static final Scope zae;
    public static final Scope zaf;
    public static final Api zag;
    public static final Api zah;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zaa = clientKey;
        Api.ClientKey clientKey2 = new Api.ClientKey();
        zab = clientKey2;
        zaa zaaVar = new zaa();
        zac = zaaVar;
        zab zabVar = new zab();
        f13335a = zabVar;
        zae = new Scope("profile");
        zaf = new Scope("email");
        zag = new Api("SignIn.API", zaaVar, clientKey);
        zah = new Api("SignIn.INTERNAL_API", zabVar, clientKey2);
    }
}
