package com.google.android.gms.fitness.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.internal.fitness.zzfv;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SafeParcelable.Class(creator = "DataPointCreator")
@SafeParcelable.Reserved({1000, 8})
/* loaded from: classes3.dex */
public final class DataPoint extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    @ShowFirstParty
    public static final Parcelable.Creator<DataPoint> CREATOR = new zzg();

    @SafeParcelable.Field(getter = "getDataSource", id = 1)
    private final DataSource zza;

    @SafeParcelable.Field(getter = "getTimestampNanos", id = 3)
    private long zzb;

    @SafeParcelable.Field(getter = "getStartTimeNanos", id = 4)
    private long zzc;

    @SafeParcelable.Field(getter = "getValues", id = 5)
    private final Value[] zzd;

    @Nullable
    @SafeParcelable.Field(getter = "getOriginalDataSourceIfSet", id = 6)
    private DataSource zze;

    @SafeParcelable.Field(getter = "getRawTimestamp", id = 7)
    private final long zzf;

    private DataPoint(DataSource dataSource) {
        this.zza = (DataSource) Preconditions.checkNotNull(dataSource, "Data source cannot be null");
        List<Field> fields = dataSource.getDataType().getFields();
        this.zzd = new Value[fields.size()];
        Iterator<Field> it = fields.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            this.zzd[i2] = new Value(it.next().getFormat(), false, 0.0f, null, null, null, null, null);
            i2++;
        }
        this.zzf = 0L;
    }

    @NonNull
    public static Builder builder(@NonNull DataSource dataSource) {
        Preconditions.checkNotNull(dataSource, "DataSource should be specified");
        return new Builder(dataSource, null);
    }

    @NonNull
    @Deprecated
    public static DataPoint create(@NonNull DataSource dataSource) {
        return new DataPoint(dataSource);
    }

    @Nullable
    public static DataPoint extract(@NonNull Intent intent) {
        if (intent == null) {
            return null;
        }
        return (DataPoint) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.android.gms.fitness.EXTRA_DATA_POINT", CREATOR);
    }

    @Nullable
    private static DataSource zzf(List list, int i2) {
        if (i2 < 0 || i2 >= list.size()) {
            return null;
        }
        return (DataSource) list.get(i2);
    }

    private final void zzg(int i2) {
        List<Field> fields = getDataType().getFields();
        int size = fields.size();
        Preconditions.checkArgument(i2 == size, "Attempting to insert %s values, but needed %s: %s", Integer.valueOf(i2), Integer.valueOf(size), fields);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataPoint)) {
            return false;
        }
        DataPoint dataPoint = (DataPoint) obj;
        return Objects.equal(this.zza, dataPoint.zza) && this.zzb == dataPoint.zzb && this.zzc == dataPoint.zzc && Arrays.equals(this.zzd, dataPoint.zzd) && Objects.equal(getOriginalDataSource(), dataPoint.getOriginalDataSource());
    }

    @NonNull
    public DataSource getDataSource() {
        return this.zza;
    }

    @NonNull
    public DataType getDataType() {
        return this.zza.getDataType();
    }

    public long getEndTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzb, TimeUnit.NANOSECONDS);
    }

    @NonNull
    public DataSource getOriginalDataSource() {
        DataSource dataSource = this.zze;
        return dataSource != null ? dataSource : this.zza;
    }

    public long getStartTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzc, TimeUnit.NANOSECONDS);
    }

    public long getTimestamp(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzb, TimeUnit.NANOSECONDS);
    }

    @NonNull
    public Value getValue(@NonNull Field field) {
        return this.zzd[getDataType().indexOf(field)];
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, Long.valueOf(this.zzb), Long.valueOf(this.zzc));
    }

    @NonNull
    @Deprecated
    public DataPoint setFloatValues(@NonNull float... fArr) {
        zzg(fArr.length);
        for (int i2 = 0; i2 < fArr.length; i2++) {
            this.zzd[i2].setFloat(fArr[i2]);
        }
        return this;
    }

    @NonNull
    @Deprecated
    public DataPoint setIntValues(@NonNull int... iArr) {
        zzg(iArr.length);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            this.zzd[i2].setInt(iArr[i2]);
        }
        return this;
    }

    @NonNull
    @Deprecated
    public DataPoint setTimeInterval(long j2, long j3, @NonNull TimeUnit timeUnit) {
        this.zzc = timeUnit.toNanos(j2);
        this.zzb = timeUnit.toNanos(j3);
        return this;
    }

    @NonNull
    @Deprecated
    public DataPoint setTimestamp(long j2, @NonNull TimeUnit timeUnit) {
        this.zzb = timeUnit.toNanos(j2);
        return this;
    }

    @NonNull
    public String toString() {
        String string = Arrays.toString(this.zzd);
        Long lValueOf = Long.valueOf(this.zzc);
        Long lValueOf2 = Long.valueOf(this.zzb);
        Long lValueOf3 = Long.valueOf(this.zzf);
        String strZzb = this.zza.zzb();
        DataSource dataSource = this.zze;
        return String.format("DataPoint{%s@[%s, %s,raw=%s](%s %s)}", string, lValueOf, lValueOf2, lValueOf3, strZzb, dataSource != null ? dataSource.zzb() : "N/A");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getDataSource(), i2, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzb);
        SafeParcelWriter.writeLong(parcel, 4, this.zzc);
        SafeParcelWriter.writeTypedArray(parcel, 5, this.zzd, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zze, i2, false);
        SafeParcelWriter.writeLong(parcel, 7, this.zzf);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @ShowFirstParty
    public final long zza() {
        return this.zzf;
    }

    @Nullable
    @ShowFirstParty
    public final DataSource zzb() {
        return this.zze;
    }

    @NonNull
    public final Value zzc(int i2) {
        DataType dataType = getDataType();
        Preconditions.checkArgument(i2 >= 0 && i2 < dataType.getFields().size(), "fieldIndex %s is out of range for %s", Integer.valueOf(i2), dataType);
        return this.zzd[i2];
    }

    public final void zzd() {
        Preconditions.checkArgument(getDataType().getName().equals(getDataSource().getDataType().getName()), "Conflicting data types found %s vs %s", getDataType(), getDataType());
        Preconditions.checkArgument(this.zzb > 0, "Data point does not have the timestamp set: %s", this);
        Preconditions.checkArgument(this.zzc <= this.zzb, "Data point with start time greater than end time found: %s", this);
    }

    @NonNull
    @ShowFirstParty
    public final Value[] zze() {
        return this.zzd;
    }

    public static class Builder {
        private final DataPoint zza;
        private boolean zzb = false;

        /* synthetic */ Builder(DataSource dataSource, zzf zzfVar) {
            this.zza = DataPoint.create(dataSource);
        }

        @NonNull
        public DataPoint build() {
            Preconditions.checkState(!this.zzb, "DataPoint#build should not be called multiple times.");
            this.zzb = true;
            return this.zza;
        }

        @NonNull
        public Builder setActivityField(@NonNull Field field, @NonNull String str) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.getValue(field).setInt(zzfv.zza(str));
            return this;
        }

        @NonNull
        public Builder setField(@NonNull Field field, float f2) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.getValue(field).setFloat(f2);
            return this;
        }

        @NonNull
        public Builder setFloatValues(@NonNull float... fArr) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.setFloatValues(fArr);
            return this;
        }

        @NonNull
        public Builder setIntValues(@NonNull int... iArr) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.setIntValues(iArr);
            return this;
        }

        @NonNull
        public Builder setTimeInterval(long j2, long j3, @NonNull TimeUnit timeUnit) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.setTimeInterval(j2, j3, timeUnit);
            return this;
        }

        @NonNull
        public Builder setTimestamp(long j2, @NonNull TimeUnit timeUnit) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.setTimestamp(j2, timeUnit);
            return this;
        }

        @NonNull
        public Builder setField(@NonNull Field field, int i2) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.getValue(field).setInt(i2);
            return this;
        }

        @NonNull
        public Builder setField(@NonNull Field field, @NonNull String str) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.getValue(field).setString(str);
            return this;
        }

        @NonNull
        public Builder setField(@NonNull Field field, @NonNull Map<String, Float> map) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.getValue(field).zza(map);
            return this;
        }
    }

    @SafeParcelable.Constructor
    public DataPoint(@NonNull @SafeParcelable.Param(id = 1) DataSource dataSource, @SafeParcelable.Param(id = 3) long j2, @SafeParcelable.Param(id = 4) long j3, @NonNull @SafeParcelable.Param(id = 5) Value[] valueArr, @Nullable @SafeParcelable.Param(id = 6) DataSource dataSource2, @SafeParcelable.Param(id = 7) long j4) {
        this.zza = dataSource;
        this.zze = dataSource2;
        this.zzb = j2;
        this.zzc = j3;
        this.zzd = valueArr;
        this.zzf = j4;
    }

    DataPoint(List list, RawDataPoint rawDataPoint) {
        this((DataSource) Preconditions.checkNotNull(zzf(list, rawDataPoint.zza())), rawDataPoint.zzc(), rawDataPoint.zze(), rawDataPoint.zzf(), zzf(list, rawDataPoint.zzb()), rawDataPoint.zzd());
    }
}
