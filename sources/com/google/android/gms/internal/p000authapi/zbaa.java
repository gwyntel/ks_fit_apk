package com.google.android.gms.internal.p000authapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.auth.api.identity.AuthorizationClient;
import com.google.android.gms.auth.api.identity.AuthorizationRequest;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.auth.api.identity.zbb;
import com.google.android.gms.auth.api.identity.zbc;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public final class zbaa extends GoogleApi implements AuthorizationClient {
    private static final Api.ClientKey zba;
    private static final Api.AbstractClientBuilder zbb;
    private static final Api zbc;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zba = clientKey;
        zby zbyVar = new zby();
        zbb = zbyVar;
        zbc = new Api("Auth.Api.Identity.Authorization.API", zbyVar, clientKey);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public zbaa(@NonNull Activity activity, @NonNull zbc zbcVar) {
        Api api = zbc;
        zbb zbbVarZbc = zbb.zbc(zbcVar);
        zbbVarZbc.zba(zbat.zba());
        super(activity, (Api<zbc>) api, zbbVarZbc.zbb(), GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @Override // com.google.android.gms.auth.api.identity.AuthorizationClient
    public final Task<AuthorizationResult> authorize(@NonNull AuthorizationRequest authorizationRequest) {
        Preconditions.checkNotNull(authorizationRequest);
        AuthorizationRequest.Builder builderZba = AuthorizationRequest.zba(authorizationRequest);
        builderZba.zbb(((zbc) getApiOptions()).zbb());
        final AuthorizationRequest authorizationRequestBuild = builderZba.build();
        return doRead(TaskApiCall.builder().setFeatures(zbas.zbc).run(new RemoteCall() { // from class: com.google.android.gms.internal.auth-api.zbx
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                ((zbk) ((zbg) obj).getService()).zbc(new zbz(this.zba, (TaskCompletionSource) obj2), (AuthorizationRequest) Preconditions.checkNotNull(authorizationRequestBuild));
            }
        }).setAutoResolveMissingFeatures(false).setMethodKey(1534).build());
    }

    @Override // com.google.android.gms.auth.api.identity.AuthorizationClient
    public final AuthorizationResult getAuthorizationResultFromIntent(@Nullable Intent intent) throws ApiException {
        if (intent == null) {
            throw new ApiException(Status.RESULT_INTERNAL_ERROR);
        }
        Status status = (Status) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "status", Status.CREATOR);
        if (status == null) {
            throw new ApiException(Status.RESULT_CANCELED);
        }
        if (!status.isSuccess()) {
            throw new ApiException(status);
        }
        AuthorizationResult authorizationResult = (AuthorizationResult) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "authorization_result", AuthorizationResult.CREATOR);
        if (authorizationResult != null) {
            return authorizationResult;
        }
        throw new ApiException(Status.RESULT_INTERNAL_ERROR);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public zbaa(@NonNull Context context, @NonNull zbc zbcVar) {
        Api api = zbc;
        zbb zbbVarZbc = zbb.zbc(zbcVar);
        zbbVarZbc.zba(zbat.zba());
        super(context, (Api<zbc>) api, zbbVarZbc.zbb(), GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}
