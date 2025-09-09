package com.google.android.gms.fitness.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.zzu;
import com.google.android.gms.fitness.data.zzv;
import java.util.concurrent.TimeUnit;

@SafeParcelable.Class(creator = "FitnessSensorServiceRequestCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class FitnessSensorServiceRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<FitnessSensorServiceRequest> CREATOR = new zzc();
    public static final int UNSPECIFIED = -1;

    @SafeParcelable.Field(getter = "getDataSource", id = 1)
    private final DataSource zza;

    @SafeParcelable.Field(getter = "getListenerBinder", id = 2, type = "android.os.IBinder")
    private final zzv zzb;

    @SafeParcelable.Field(getter = "getSamplingRateMicros", id = 3)
    private final long zzc;

    @SafeParcelable.Field(getter = "getBatchIntervalMicros", id = 4)
    private final long zzd;

    FitnessSensorServiceRequest(DataSource dataSource, IBinder iBinder, long j2, long j3) {
        this.zza = dataSource;
        this.zzb = zzu.zzb(iBinder);
        this.zzc = j2;
        this.zzd = j3;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FitnessSensorServiceRequest)) {
            return false;
        }
        FitnessSensorServiceRequest fitnessSensorServiceRequest = (FitnessSensorServiceRequest) obj;
        return Objects.equal(this.zza, fitnessSensorServiceRequest.zza) && this.zzc == fitnessSensorServiceRequest.zzc && this.zzd == fitnessSensorServiceRequest.zzd;
    }

    public long getBatchInterval(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzd, TimeUnit.MICROSECONDS);
    }

    @NonNull
    public DataSource getDataSource() {
        return this.zza;
    }

    @NonNull
    public SensorEventDispatcher getDispatcher() {
        return new zzd(this.zzb);
    }

    public long getSamplingRate(@NonNull TimeUnit timeUnit) {
        long j2 = this.zzc;
        if (j2 == -1) {
            return -1L;
        }
        return timeUnit.convert(j2, TimeUnit.MICROSECONDS);
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, Long.valueOf(this.zzc), Long.valueOf(this.zzd));
    }

    @NonNull
    public String toString() {
        return String.format("FitnessSensorServiceRequest{%s}", this.zza);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getDataSource(), i2, false);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zzb.asBinder(), false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzc);
        SafeParcelWriter.writeLong(parcel, 4, this.zzd);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
