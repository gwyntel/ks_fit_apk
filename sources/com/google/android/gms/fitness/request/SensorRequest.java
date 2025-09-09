package com.google.android.gms.fitness.request;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SensorRequest {
    public static final int ACCURACY_MODE_DEFAULT = 2;
    public static final int ACCURACY_MODE_HIGH = 3;
    public static final int ACCURACY_MODE_LOW = 1;

    @Nullable
    private final DataSource zza;

    @Nullable
    private final DataType zzb;
    private final long zzc;
    private final long zzd;
    private final long zze;
    private final int zzf;
    private final long zzg;

    public static class Builder {
        private DataSource zza;
        private DataType zzb;
        private long zzc = -1;
        private long zzd = 0;
        private long zze = 0;
        private boolean zzf = false;
        private int zzg = 2;
        private long zzh = Long.MAX_VALUE;

        @NonNull
        public SensorRequest build() {
            DataSource dataSource;
            Preconditions.checkState((this.zza == null && this.zzb == null) ? false : true, "Must call setDataSource() or setDataType()");
            DataType dataType = this.zzb;
            Preconditions.checkState(dataType == null || (dataSource = this.zza) == null || dataType.equals(dataSource.getDataType()), "Specified data type is incompatible with specified data source");
            return new SensorRequest(this, null);
        }

        @NonNull
        public Builder setAccuracyMode(int i2) {
            if (i2 != 1 && i2 != 3) {
                i2 = 2;
            }
            this.zzg = i2;
            return this;
        }

        @NonNull
        public Builder setDataSource(@NonNull DataSource dataSource) {
            this.zza = dataSource;
            return this;
        }

        @NonNull
        public Builder setDataType(@NonNull DataType dataType) {
            this.zzb = dataType;
            return this;
        }

        @NonNull
        public Builder setFastestRate(int i2, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(i2 >= 0, "Cannot use a negative interval");
            this.zzf = true;
            this.zzd = timeUnit.toMicros(i2);
            return this;
        }

        @NonNull
        public Builder setMaxDeliveryLatency(int i2, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(i2 >= 0, "Cannot use a negative delivery interval");
            this.zze = timeUnit.toMicros(i2);
            return this;
        }

        @NonNull
        public Builder setSamplingRate(long j2, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(j2 >= 0, "Cannot use a negative sampling interval");
            long micros = timeUnit.toMicros(j2);
            this.zzc = micros;
            if (!this.zzf) {
                this.zzd = micros / 2;
            }
            return this;
        }

        @NonNull
        public Builder setTimeout(long j2, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(j2 > 0, "Invalid time out value specified: %d", Long.valueOf(j2));
            Preconditions.checkArgument(timeUnit != null, "Invalid time unit specified");
            this.zzh = timeUnit.toMicros(j2);
            return this;
        }
    }

    /* synthetic */ SensorRequest(Builder builder, zzam zzamVar) {
        this.zza = builder.zza;
        this.zzb = builder.zzb;
        this.zzc = builder.zzc;
        this.zzd = builder.zzd;
        this.zze = builder.zze;
        this.zzf = builder.zzg;
        this.zzg = builder.zzh;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SensorRequest)) {
            return false;
        }
        SensorRequest sensorRequest = (SensorRequest) obj;
        return Objects.equal(this.zza, sensorRequest.zza) && Objects.equal(this.zzb, sensorRequest.zzb) && this.zzc == sensorRequest.zzc && this.zzd == sensorRequest.zzd && this.zze == sensorRequest.zze && this.zzf == sensorRequest.zzf && this.zzg == sensorRequest.zzg;
    }

    public int getAccuracyMode() {
        return this.zzf;
    }

    @Nullable
    public DataSource getDataSource() {
        return this.zza;
    }

    @Nullable
    public DataType getDataType() {
        return this.zzb;
    }

    public long getFastestRate(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzd, TimeUnit.MICROSECONDS);
    }

    public long getMaxDeliveryLatency(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zze, TimeUnit.MICROSECONDS);
    }

    public long getSamplingRate(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzc, TimeUnit.MICROSECONDS);
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, Long.valueOf(this.zzc), Long.valueOf(this.zzd), Long.valueOf(this.zze), Integer.valueOf(this.zzf), Long.valueOf(this.zzg));
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("dataSource", this.zza).add("dataType", this.zzb).add("samplingRateMicros", Long.valueOf(this.zzc)).add("deliveryLatencyMicros", Long.valueOf(this.zze)).add("timeOutMicros", Long.valueOf(this.zzg)).toString();
    }

    public final long zza() {
        return this.zzg;
    }
}
