package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ShowFirstParty
@SafeParcelable.Class(creator = "SensorRegistrationRequestCreator")
@SafeParcelable.Reserved({4, 5, 11, 14, 1000})
/* loaded from: classes3.dex */
public final class zzak extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzak> CREATOR = new zzal();

    @Nullable
    @SafeParcelable.Field(getter = "getDataSource", id = 1)
    private final DataSource zza;

    @Nullable
    @SafeParcelable.Field(getter = "getDataType", id = 2)
    private final DataType zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getListenerBinder", id = 3, type = "android.os.IBinder")
    private final com.google.android.gms.fitness.data.zzv zzc;

    @SafeParcelable.Field(getter = "getSamplingRateMicros", id = 6)
    private final long zzd;

    @SafeParcelable.Field(getter = "getMaxDeliveryLatencyMicros", id = 7)
    private final long zze;

    @Nullable
    @SafeParcelable.Field(getter = "getIntent", id = 8)
    private final PendingIntent zzf;

    @SafeParcelable.Field(getter = "getFastestRateMicros", id = 9)
    private final long zzg;

    @SafeParcelable.Field(getter = "getAccuracyMode", id = 10)
    private final int zzh;

    @SafeParcelable.Field(getter = "getRegistrationTimeOutMicros", id = 12)
    private final long zzi;
    private final List zzj;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 13, type = "android.os.IBinder")
    private final zzcp zzk;

    zzak(DataSource dataSource, DataType dataType, IBinder iBinder, long j2, long j3, PendingIntent pendingIntent, long j4, int i2, long j5, IBinder iBinder2) {
        this.zza = dataSource;
        this.zzb = dataType;
        this.zzc = iBinder == null ? null : com.google.android.gms.fitness.data.zzu.zzb(iBinder);
        this.zzd = j2;
        this.zzg = j4;
        this.zze = j3;
        this.zzf = pendingIntent;
        this.zzh = i2;
        this.zzj = Collections.emptyList();
        this.zzi = j5;
        this.zzk = iBinder2 != null ? zzco.zzb(iBinder2) : null;
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzak)) {
            return false;
        }
        zzak zzakVar = (zzak) obj;
        return Objects.equal(this.zza, zzakVar.zza) && Objects.equal(this.zzb, zzakVar.zzb) && Objects.equal(this.zzc, zzakVar.zzc) && this.zzd == zzakVar.zzd && this.zzg == zzakVar.zzg && this.zze == zzakVar.zze && this.zzh == zzakVar.zzh;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, Long.valueOf(this.zzd), Long.valueOf(this.zzg), Long.valueOf(this.zze), Integer.valueOf(this.zzh));
    }

    public final String toString() {
        return String.format("SensorRegistrationRequest{type %s source %s interval %s fastest %s latency %s}", this.zzb, this.zza, Long.valueOf(this.zzd), Long.valueOf(this.zzg), Long.valueOf(this.zze));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i2, false);
        com.google.android.gms.fitness.data.zzv zzvVar = this.zzc;
        SafeParcelWriter.writeIBinder(parcel, 3, zzvVar == null ? null : zzvVar.asBinder(), false);
        SafeParcelWriter.writeLong(parcel, 6, this.zzd);
        SafeParcelWriter.writeLong(parcel, 7, this.zze);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzf, i2, false);
        SafeParcelWriter.writeLong(parcel, 9, this.zzg);
        SafeParcelWriter.writeInt(parcel, 10, this.zzh);
        SafeParcelWriter.writeLong(parcel, 12, this.zzi);
        zzcp zzcpVar = this.zzk;
        SafeParcelWriter.writeIBinder(parcel, 13, zzcpVar != null ? zzcpVar.asBinder() : null, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzak(SensorRequest sensorRequest, @Nullable com.google.android.gms.fitness.data.zzv zzvVar, @Nullable PendingIntent pendingIntent, zzcp zzcpVar) {
        DataSource dataSource = sensorRequest.getDataSource();
        DataType dataType = sensorRequest.getDataType();
        TimeUnit timeUnit = TimeUnit.MICROSECONDS;
        long samplingRate = sensorRequest.getSamplingRate(timeUnit);
        long fastestRate = sensorRequest.getFastestRate(timeUnit);
        long maxDeliveryLatency = sensorRequest.getMaxDeliveryLatency(timeUnit);
        int accuracyMode = sensorRequest.getAccuracyMode();
        List listEmptyList = Collections.emptyList();
        long jZza = sensorRequest.zza();
        this.zza = dataSource;
        this.zzb = dataType;
        this.zzc = zzvVar;
        this.zzf = pendingIntent;
        this.zzd = samplingRate;
        this.zzg = fastestRate;
        this.zze = maxDeliveryLatency;
        this.zzh = accuracyMode;
        this.zzj = listEmptyList;
        this.zzi = jZza;
        this.zzk = zzcpVar;
    }
}
