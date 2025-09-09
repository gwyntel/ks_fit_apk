package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;
import java.util.concurrent.TimeUnit;

@SafeParcelable.Class(creator = "DataUpdateRequestCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class DataUpdateRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<DataUpdateRequest> CREATOR = new zzu();

    @SafeParcelable.Field(getter = "getStartTimeMillis", id = 1)
    private final long zza;

    @SafeParcelable.Field(getter = "getEndTimeMillis", id = 2)
    private final long zzb;

    @SafeParcelable.Field(getter = "getDataSet", id = 3)
    private final DataSet zzc;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 4, type = "android.os.IBinder")
    private final zzcp zzd;

    public static class Builder {
        private long zza;
        private long zzb;
        private DataSet zzc;

        @NonNull
        public DataUpdateRequest build() {
            Preconditions.checkNotZero(this.zza, "Must set a non-zero value for startTimeMillis/startTime");
            Preconditions.checkNotZero(this.zzb, "Must set a non-zero value for endTimeMillis/endTime");
            Preconditions.checkNotNull(this.zzc, "Must set the data set");
            for (DataPoint dataPoint : this.zzc.getDataPoints()) {
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                long startTime = dataPoint.getStartTime(timeUnit);
                long endTime = dataPoint.getEndTime(timeUnit);
                Preconditions.checkState(startTime <= endTime && (startTime == 0 || startTime >= this.zza) && ((startTime == 0 || startTime <= this.zzb) && endTime <= this.zzb && endTime >= this.zza), "Data Point's startTimeMillis %d, endTimeMillis %d should lie between timeRange provided in the request. StartTimeMillis %d, EndTimeMillis: %d", Long.valueOf(startTime), Long.valueOf(endTime), Long.valueOf(this.zza), Long.valueOf(this.zzb));
            }
            return new DataUpdateRequest(this.zza, this.zzb, this.zzc, null);
        }

        @NonNull
        public Builder setDataSet(@NonNull DataSet dataSet) {
            Preconditions.checkNotNull(dataSet, "Must set the data set");
            this.zzc = dataSet;
            return this;
        }

        @NonNull
        public Builder setTimeInterval(long j2, long j3, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(j2 > 0, "Invalid start time :%d", Long.valueOf(j2));
            Preconditions.checkArgument(j3 >= j2, "Invalid end time :%d", Long.valueOf(j3));
            this.zza = timeUnit.toMillis(j2);
            this.zzb = timeUnit.toMillis(j3);
            return this;
        }
    }

    @SafeParcelable.Constructor
    public DataUpdateRequest(@SafeParcelable.Param(id = 1) long j2, @SafeParcelable.Param(id = 2) long j3, @NonNull @SafeParcelable.Param(id = 3) DataSet dataSet, @Nullable @SafeParcelable.Param(id = 4) IBinder iBinder) {
        this.zza = j2;
        this.zzb = j3;
        this.zzc = dataSet;
        this.zzd = iBinder == null ? null : zzco.zzb(iBinder);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DataUpdateRequest)) {
            return false;
        }
        DataUpdateRequest dataUpdateRequest = (DataUpdateRequest) obj;
        return this.zza == dataUpdateRequest.zza && this.zzb == dataUpdateRequest.zzb && Objects.equal(this.zzc, dataUpdateRequest.zzc);
    }

    @NonNull
    public DataSet getDataSet() {
        return this.zzc;
    }

    public long getEndTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzb, TimeUnit.MILLISECONDS);
    }

    public long getStartTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zza, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.zza), Long.valueOf(this.zzb), this.zzc);
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("startTimeMillis", Long.valueOf(this.zza)).add("endTimeMillis", Long.valueOf(this.zzb)).add("dataSet", this.zzc).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.zza);
        SafeParcelWriter.writeLong(parcel, 2, this.zzb);
        SafeParcelWriter.writeParcelable(parcel, 3, getDataSet(), i2, false);
        zzcp zzcpVar = this.zzd;
        SafeParcelWriter.writeIBinder(parcel, 4, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final long zza() {
        return this.zzb;
    }

    public final long zzb() {
        return this.zza;
    }

    public DataUpdateRequest(@NonNull DataUpdateRequest dataUpdateRequest, @NonNull IBinder iBinder) {
        this(dataUpdateRequest.zza, dataUpdateRequest.zzb, dataUpdateRequest.getDataSet(), iBinder);
    }
}
