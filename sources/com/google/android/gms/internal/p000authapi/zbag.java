package com.google.android.gms.internal.p000authapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.auth.api.identity.CredentialSavingClient;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenResult;
import com.google.android.gms.auth.api.identity.SavePasswordRequest;
import com.google.android.gms.auth.api.identity.SavePasswordResult;
import com.google.android.gms.auth.api.identity.zbh;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public final class zbag extends GoogleApi implements CredentialSavingClient {
    private static final Api.ClientKey zba;
    private static final Api.AbstractClientBuilder zbb;
    private static final Api zbc;
    private final String zbd;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zba = clientKey;
        zbad zbadVar = new zbad();
        zbb = zbadVar;
        zbc = new Api("Auth.Api.Identity.CredentialSaving.API", zbadVar, clientKey);
    }

    public zbag(@NonNull Activity activity, @NonNull zbh zbhVar) {
        super(activity, (Api<zbh>) zbc, zbhVar, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zbd = zbat.zba();
    }

    @Override // com.google.android.gms.auth.api.identity.CredentialSavingClient
    public final Status getStatusFromIntent(@Nullable Intent intent) {
        if (intent == null) {
            return Status.RESULT_INTERNAL_ERROR;
        }
        Status status = (Status) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "status", Status.CREATOR);
        return status == null ? Status.RESULT_INTERNAL_ERROR : status;
    }

    @Override // com.google.android.gms.auth.api.identity.CredentialSavingClient
    public final Task<SaveAccountLinkingTokenResult> saveAccountLinkingToken(@NonNull SaveAccountLinkingTokenRequest saveAccountLinkingTokenRequest) {
        Preconditions.checkNotNull(saveAccountLinkingTokenRequest);
        SaveAccountLinkingTokenRequest.Builder builderZba = SaveAccountLinkingTokenRequest.zba(saveAccountLinkingTokenRequest);
        builderZba.zba(this.zbd);
        final SaveAccountLinkingTokenRequest saveAccountLinkingTokenRequestBuild = builderZba.build();
        return doRead(TaskApiCall.builder().setFeatures(zbas.zbg).run(new RemoteCall() { // from class: com.google.android.gms.internal.auth-api.zbab
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                ((zbn) ((zbh) obj).getService()).zbc(new zbae(this.zba, (TaskCompletionSource) obj2), (SaveAccountLinkingTokenRequest) Preconditions.checkNotNull(saveAccountLinkingTokenRequestBuild));
            }
        }).setAutoResolveMissingFeatures(false).setMethodKey(1535).build());
    }

    @Override // com.google.android.gms.auth.api.identity.CredentialSavingClient
    public final Task<SavePasswordResult> savePassword(@NonNull SavePasswordRequest savePasswordRequest) {
        Preconditions.checkNotNull(savePasswordRequest);
        SavePasswordRequest.Builder builderZba = SavePasswordRequest.zba(savePasswordRequest);
        builderZba.zba(this.zbd);
        final SavePasswordRequest savePasswordRequestBuild = builderZba.build();
        return doRead(TaskApiCall.builder().setFeatures(zbas.zbe).run(new RemoteCall() { // from class: com.google.android.gms.internal.auth-api.zbac
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                ((zbn) ((zbh) obj).getService()).zbd(new zbaf(this.zba, (TaskCompletionSource) obj2), (SavePasswordRequest) Preconditions.checkNotNull(savePasswordRequestBuild));
            }
        }).setAutoResolveMissingFeatures(false).setMethodKey(1536).build());
    }

    public zbag(@NonNull Context context, @NonNull zbh zbhVar) {
        super(context, (Api<zbh>) zbc, zbhVar, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zbd = zbat.zba();
    }
}
