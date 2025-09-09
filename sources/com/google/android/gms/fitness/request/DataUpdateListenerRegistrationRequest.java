package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;

@SafeParcelable.Class(creator = "DataUpdateListenerRegistrationRequestCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class DataUpdateListenerRegistrationRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<DataUpdateListenerRegistrationRequest> CREATOR = new zzr();

    @Nullable
    @SafeParcelable.Field(getter = "getDataSource", id = 1)
    private final DataSource zza;

    @Nullable
    @SafeParcelable.Field(getter = "getDataType", id = 2)
    private final DataType zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getIntent", id = 3)
    private final PendingIntent zzc;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 4, type = "android.os.IBinder")
    private final zzcp zzd;

    public static class Builder {
        private DataSource zza;
        private DataType zzb;
        private PendingIntent zzc;

        @NonNull
        public DataUpdateListenerRegistrationRequest build() {
            boolean z2 = true;
            if (this.zza == null && this.zzb == null) {
                z2 = false;
            }
            Preconditions.checkState(z2, "Set either dataSource or dataTYpe");
            Preconditions.checkNotNull(this.zzc, "pendingIntent must be set");
            return new DataUpdateListenerRegistrationRequest(this.zza, this.zzb, this.zzc, null);
        }

        @NonNull
        public Builder setDataSource(@NonNull DataSource dataSource) {
            Preconditions.checkNotNull(dataSource);
            this.zza = dataSource;
            return this;
        }

        @NonNull
        public Builder setDataType(@NonNull DataType dataType) {
            Preconditions.checkNotNull(dataType);
            this.zzb = dataType;
            return this;
        }

        @NonNull
        public Builder setPendingIntent(@NonNull PendingIntent pendingIntent) {
            Preconditions.checkNotNull(pendingIntent);
            this.zzc = pendingIntent;
            return this;
        }
    }

    @SafeParcelable.Constructor
    public DataUpdateListenerRegistrationRequest(@Nullable @SafeParcelable.Param(id = 1) DataSource dataSource, @Nullable @SafeParcelable.Param(id = 2) DataType dataType, @Nullable @SafeParcelable.Param(id = 3) PendingIntent pendingIntent, @Nullable @SafeParcelable.Param(id = 4) IBinder iBinder) {
        this.zza = dataSource;
        this.zzb = dataType;
        this.zzc = pendingIntent;
        this.zzd = iBinder == null ? null : zzco.zzb(iBinder);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataUpdateListenerRegistrationRequest)) {
            return false;
        }
        DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest = (DataUpdateListenerRegistrationRequest) obj;
        return Objects.equal(this.zza, dataUpdateListenerRegistrationRequest.zza) && Objects.equal(this.zzb, dataUpdateListenerRegistrationRequest.zzb) && Objects.equal(this.zzc, dataUpdateListenerRegistrationRequest.zzc);
    }

    @Nullable
    public DataSource getDataSource() {
        return this.zza;
    }

    @Nullable
    public DataType getDataType() {
        return this.zzb;
    }

    @Nullable
    public PendingIntent getIntent() {
        return this.zzc;
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc);
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("dataSource", this.zza).add("dataType", this.zzb).add(BaseGmsClient.KEY_PENDING_INTENT, this.zzc).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getDataSource(), i2, false);
        SafeParcelWriter.writeParcelable(parcel, 2, getDataType(), i2, false);
        SafeParcelWriter.writeParcelable(parcel, 3, getIntent(), i2, false);
        zzcp zzcpVar = this.zzd;
        SafeParcelWriter.writeIBinder(parcel, 4, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public DataUpdateListenerRegistrationRequest(@NonNull DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest, @Nullable IBinder iBinder) {
        this(dataUpdateListenerRegistrationRequest.zza, dataUpdateListenerRegistrationRequest.zzb, dataUpdateListenerRegistrationRequest.zzc, iBinder);
    }
}
