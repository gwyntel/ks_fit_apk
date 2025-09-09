package com.google.android.gms.fido.fido2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialCreationOptions;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialRequestOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public class Fido2ApiClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    private static final Api.ClientKey zza;
    private static final Api zzb;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zza = clientKey;
        zzb = new Api("Fido.FIDO2_API", new com.google.android.gms.internal.fido.zzo(), clientKey);
    }

    @Deprecated
    public Fido2ApiClient(@NonNull Activity activity) {
        super(activity, (Api<Api.ApiOptions.NoOptions>) zzb, Api.ApiOptions.NO_OPTIONS, (StatusExceptionMapper) new ApiExceptionMapper());
    }

    @NonNull
    @Deprecated
    public Task<Fido2PendingIntent> getRegisterIntent(@NonNull final PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions) {
        return doRead(TaskApiCall.builder().setMethodKey(5409).run(new RemoteCall() { // from class: com.google.android.gms.fido.fido2.zzd
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                Fido2ApiClient fido2ApiClient = this.zza;
                PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions2 = publicKeyCredentialCreationOptions;
                ((com.google.android.gms.internal.fido.zzs) ((com.google.android.gms.internal.fido.zzp) obj).getService()).zzc(new zzh(fido2ApiClient, (TaskCompletionSource) obj2), publicKeyCredentialCreationOptions2);
            }
        }).build());
    }

    @NonNull
    public Task<PendingIntent> getRegisterPendingIntent(@NonNull final PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.fido.fido2.zzc
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                Fido2ApiClient fido2ApiClient = this.zza;
                PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions2 = publicKeyCredentialCreationOptions;
                ((com.google.android.gms.internal.fido.zzs) ((com.google.android.gms.internal.fido.zzp) obj).getService()).zzc(new zzf(fido2ApiClient, (TaskCompletionSource) obj2), publicKeyCredentialCreationOptions2);
            }
        }).setMethodKey(5407).build());
    }

    @NonNull
    @Deprecated
    public Task<Fido2PendingIntent> getSignIntent(@NonNull final PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions) {
        return doRead(TaskApiCall.builder().setMethodKey(5410).run(new RemoteCall() { // from class: com.google.android.gms.fido.fido2.zza
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                Fido2ApiClient fido2ApiClient = this.zza;
                PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions2 = publicKeyCredentialRequestOptions;
                ((com.google.android.gms.internal.fido.zzs) ((com.google.android.gms.internal.fido.zzp) obj).getService()).zzd(new zzi(fido2ApiClient, (TaskCompletionSource) obj2), publicKeyCredentialRequestOptions2);
            }
        }).build());
    }

    @NonNull
    public Task<PendingIntent> getSignPendingIntent(@NonNull final PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.fido.fido2.zze
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                Fido2ApiClient fido2ApiClient = this.zza;
                PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions2 = publicKeyCredentialRequestOptions;
                ((com.google.android.gms.internal.fido.zzs) ((com.google.android.gms.internal.fido.zzp) obj).getService()).zzd(new zzg(fido2ApiClient, (TaskCompletionSource) obj2), publicKeyCredentialRequestOptions2);
            }
        }).setMethodKey(5408).build());
    }

    @NonNull
    public Task<Boolean> isUserVerifyingPlatformAuthenticatorAvailable() {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.fido.fido2.zzb
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                ((com.google.android.gms.internal.fido.zzs) ((com.google.android.gms.internal.fido.zzp) obj).getService()).zze(new zzj(this.zza, (TaskCompletionSource) obj2));
            }
        }).setFeatures(com.google.android.gms.fido.zza.zzh).setMethodKey(5411).build());
    }

    @Deprecated
    public Fido2ApiClient(@NonNull Context context) {
        super(context, (Api<Api.ApiOptions.NoOptions>) zzb, Api.ApiOptions.NO_OPTIONS, new ApiExceptionMapper());
    }
}
