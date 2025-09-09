package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
/* loaded from: classes3.dex */
public class BaseImplementation {

    @KeepForSdk
    public interface ResultHolder<R> {
        @KeepForSdk
        void setFailedResult(@NonNull Status status);

        @KeepForSdk
        void setResult(@NonNull R r2);
    }

    @KeepForSdk
    public static abstract class ApiMethodImpl<R extends Result, A extends Api.AnyClient> extends BasePendingResult<R> implements ResultHolder<R> {

        @Nullable
        @KeepForSdk
        private final Api<?> api;

        @KeepForSdk
        private final Api.AnyClientKey<A> clientKey;

        protected ApiMethodImpl(Api api, GoogleApiClient googleApiClient) {
            super((GoogleApiClient) Preconditions.checkNotNull(googleApiClient, "GoogleApiClient must not be null"));
            Preconditions.checkNotNull(api, "Api must not be null");
            this.clientKey = api.zab();
            this.api = api;
        }

        @KeepForSdk
        private void setFailedResult(@NonNull RemoteException remoteException) {
            setFailedResult(new Status(8, remoteException.getLocalizedMessage(), (PendingIntent) null));
        }

        protected abstract void b(Api.AnyClient anyClient);

        protected void c(Result result) {
        }

        @Nullable
        @KeepForSdk
        public final Api<?> getApi() {
            return this.api;
        }

        @NonNull
        @KeepForSdk
        public final Api.AnyClientKey<A> getClientKey() {
            return this.clientKey;
        }

        @KeepForSdk
        public final void run(@NonNull A a2) throws DeadObjectException {
            try {
                b(a2);
            } catch (DeadObjectException e2) {
                setFailedResult(e2);
                throw e2;
            } catch (RemoteException e3) {
                setFailedResult(e3);
            }
        }

        @KeepForSdk
        public /* bridge */ /* synthetic */ void setResult(@NonNull Object obj) {
            super.setResult((ApiMethodImpl<R, A>) obj);
        }

        @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
        @KeepForSdk
        public final void setFailedResult(@NonNull Status status) {
            Preconditions.checkArgument(!status.isSuccess(), "Failed result must not be success");
            Result resultCreateFailedResult = createFailedResult(status);
            setResult((ApiMethodImpl<R, A>) resultCreateFailedResult);
            c(resultCreateFailedResult);
        }
    }
}
