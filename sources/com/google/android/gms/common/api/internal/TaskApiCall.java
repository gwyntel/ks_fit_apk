package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class TaskApiCall<A extends Api.AnyClient, ResultT> {

    @Nullable
    private final Feature[] zaa;
    private final boolean zab;
    private final int zac;

    @KeepForSdk
    public static class Builder<A extends Api.AnyClient, ResultT> {
        private RemoteCall zaa;
        private Feature[] zac;
        private boolean zab = true;
        private int zad = 0;

        private Builder() {
        }

        @NonNull
        @KeepForSdk
        public TaskApiCall<A, ResultT> build() {
            Preconditions.checkArgument(this.zaa != null, "execute parameter required");
            return new zacv(this, this.zac, this.zab, this.zad);
        }

        @NonNull
        @Deprecated
        @CanIgnoreReturnValue
        @KeepForSdk
        public Builder<A, ResultT> execute(@NonNull final BiConsumer<A, TaskCompletionSource<ResultT>> biConsumer) {
            this.zaa = new RemoteCall() { // from class: com.google.android.gms.common.api.internal.zacu
                @Override // com.google.android.gms.common.api.internal.RemoteCall
                public final void accept(Object obj, Object obj2) {
                    biConsumer.accept((Api.AnyClient) obj, (TaskCompletionSource) obj2);
                }
            };
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        @KeepForSdk
        public Builder<A, ResultT> run(@NonNull RemoteCall<A, TaskCompletionSource<ResultT>> remoteCall) {
            this.zaa = remoteCall;
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        @KeepForSdk
        public Builder<A, ResultT> setAutoResolveMissingFeatures(boolean z2) {
            this.zab = z2;
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        @KeepForSdk
        public Builder<A, ResultT> setFeatures(@NonNull Feature... featureArr) {
            this.zac = featureArr;
            return this;
        }

        @NonNull
        @CanIgnoreReturnValue
        @KeepForSdk
        public Builder<A, ResultT> setMethodKey(int i2) {
            this.zad = i2;
            return this;
        }

        /* synthetic */ Builder(zacw zacwVar) {
        }
    }

    @KeepForSdk
    @Deprecated
    public TaskApiCall() {
        this.zaa = null;
        this.zab = false;
        this.zac = 0;
    }

    @NonNull
    @KeepForSdk
    public static <A extends Api.AnyClient, ResultT> Builder<A, ResultT> builder() {
        return new Builder<>(null);
    }

    protected abstract void a(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource);

    @KeepForSdk
    public boolean shouldAutoResolveMissingFeatures() {
        return this.zab;
    }

    public final int zaa() {
        return this.zac;
    }

    @Nullable
    public final Feature[] zab() {
        return this.zaa;
    }

    protected TaskApiCall(Feature[] featureArr, boolean z2, int i2) {
        this.zaa = featureArr;
        boolean z3 = false;
        if (featureArr != null && z2) {
            z3 = true;
        }
        this.zab = z3;
        this.zac = i2;
    }
}
