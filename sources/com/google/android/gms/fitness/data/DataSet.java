package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fitness.zzfw;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SafeParcelable.Class(creator = "DataSetCreator")
@SafeParcelable.Reserved({2, 5})
/* loaded from: classes3.dex */
public final class DataSet extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final Parcelable.Creator<DataSet> CREATOR = new zzi();

    @SafeParcelable.VersionField(getter = "getVersionCode", id = 1000)
    private final int zza;

    @SafeParcelable.Field(getter = "getDataSource", id = 1)
    private final DataSource zzb;

    @SafeParcelable.Field(getter = "getRawDataPoints", id = 3, type = "java.util.List")
    private final List zzc;

    @SafeParcelable.Field(getter = "getUniqueDataSources", id = 4)
    private final List zzd;

    public static class Builder {
        private final DataSet zza;
        private boolean zzb = false;

        /* synthetic */ Builder(DataSource dataSource, zzh zzhVar) {
            this.zza = DataSet.create(dataSource);
        }

        @NonNull
        public Builder add(@NonNull DataPoint dataPoint) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.add(dataPoint);
            return this;
        }

        @NonNull
        public Builder addAll(@NonNull Iterable<DataPoint> iterable) {
            Preconditions.checkState(!this.zzb, "Builder should not be mutated after calling #build.");
            this.zza.addAll(iterable);
            return this;
        }

        @NonNull
        public DataSet build() {
            Preconditions.checkState(!this.zzb, "DataSet#build() should only be called once.");
            this.zzb = true;
            return this.zza;
        }
    }

    DataSet(int i2, DataSource dataSource, List list, List list2) {
        this.zza = i2;
        this.zzb = dataSource;
        this.zzc = new ArrayList(list.size());
        this.zzd = i2 < 2 ? Collections.singletonList(dataSource) : list2;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.zzc.add(new DataPoint(this.zzd, (RawDataPoint) it.next()));
        }
    }

    @NonNull
    public static Builder builder(@NonNull DataSource dataSource) {
        Preconditions.checkNotNull(dataSource, "DataSource should be specified");
        return new Builder(dataSource, null);
    }

    @NonNull
    public static DataSet create(@NonNull DataSource dataSource) {
        Preconditions.checkNotNull(dataSource, "DataSource should be specified");
        return new DataSet(dataSource);
    }

    public static void zzc(@NonNull DataPoint dataPoint) {
        double dAsFloat;
        String strConcat = null;
        if (zzl.zza(dataPoint.getDataType().getName()) != null) {
            DataType dataType = dataPoint.getDataType();
            int i2 = 0;
            while (true) {
                if (i2 < dataType.getFields().size()) {
                    String name = dataType.getFields().get(i2).getName();
                    if (!dataPoint.zzc(i2).isSet()) {
                        if (!Boolean.TRUE.equals(dataType.getFields().get(i2).isOptional()) && !zzaj.zzd.contains(name)) {
                            strConcat = String.valueOf(name).concat(" not set");
                            break;
                        }
                        i2++;
                    } else {
                        double format = dataType.getFields().get(i2).getFormat();
                        if (format == 1.0d) {
                            dAsFloat = dataPoint.zzc(i2).asInt();
                        } else if (format == 2.0d) {
                            dAsFloat = dataPoint.zzc(i2).asFloat();
                        } else {
                            continue;
                            i2++;
                        }
                        zzai zzaiVarZza = zzaj.zzc().zza(name);
                        if (zzaiVarZza != null && !zzaiVarZza.zza(dAsFloat)) {
                            strConcat = "Field out of range";
                            break;
                        }
                        zzai zzaiVarZzb = zzaj.zzc().zzb(dataType.getName(), name);
                        if (zzaiVarZzb != null) {
                            TimeUnit timeUnit = TimeUnit.NANOSECONDS;
                            long endTime = dataPoint.getEndTime(timeUnit) - dataPoint.getStartTime(timeUnit);
                            if (endTime == 0) {
                                if (dAsFloat != 0.0d) {
                                    break;
                                }
                            } else if (!zzaiVarZzb.zza(dAsFloat / endTime)) {
                                break;
                            }
                        } else {
                            continue;
                        }
                        i2++;
                    }
                } else if ("com.google.activity.segment".equals(dataPoint.getDataSource().getDataType().getName())) {
                    Value value = dataPoint.getValue(Field.FIELD_ACTIVITY);
                    if (value == null) {
                        strConcat = "activity is not set";
                    } else if (zzfw.zza(value.asInt(), zzfw.UNKNOWN).zzb()) {
                        strConcat = "Sleep types are not a valid activity for com.google.activity.segment";
                    }
                }
            }
            strConcat = "DataPoint out of range";
        }
        if (strConcat == null) {
            return;
        }
        Log.w("Fitness", "Invalid data point: ".concat(String.valueOf(dataPoint)));
        throw new IllegalArgumentException(strConcat);
    }

    final List a(List list) {
        ArrayList arrayList = new ArrayList(this.zzc.size());
        Iterator it = this.zzc.iterator();
        while (it.hasNext()) {
            arrayList.add(new RawDataPoint((DataPoint) it.next(), list));
        }
        return arrayList;
    }

    @Deprecated
    public void add(@NonNull DataPoint dataPoint) {
        DataSource dataSource = dataPoint.getDataSource();
        Preconditions.checkArgument(dataSource.getStreamIdentifier().equals(this.zzb.getStreamIdentifier()), "Conflicting data sources found %s vs %s", dataSource, this.zzb);
        dataPoint.zzd();
        zzc(dataPoint);
        zzb(dataPoint);
    }

    @Deprecated
    public void addAll(@NonNull Iterable<DataPoint> iterable) {
        Iterator<DataPoint> it = iterable.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    @NonNull
    public DataPoint createDataPoint() {
        return DataPoint.create(this.zzb);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DataSet)) {
            return false;
        }
        DataSet dataSet = (DataSet) obj;
        return Objects.equal(this.zzb, dataSet.zzb) && Objects.equal(this.zzc, dataSet.zzc);
    }

    @NonNull
    public List<DataPoint> getDataPoints() {
        return Collections.unmodifiableList(this.zzc);
    }

    @NonNull
    public DataSource getDataSource() {
        return this.zzb;
    }

    @NonNull
    public DataType getDataType() {
        return this.zzb.getDataType();
    }

    public int hashCode() {
        return Objects.hashCode(this.zzb);
    }

    public boolean isEmpty() {
        return this.zzc.isEmpty();
    }

    @NonNull
    public String toString() {
        List listA = a(this.zzd);
        Locale locale = Locale.US;
        String strZzb = this.zzb.zzb();
        Object obj = listA;
        if (this.zzc.size() >= 10) {
            obj = String.format(locale, "%d data points, first 5: %s", Integer.valueOf(this.zzc.size()), listA.subList(0, 5));
        }
        return String.format(locale, "DataSet{%s %s}", strZzb, obj);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getDataSource(), i2, false);
        SafeParcelWriter.writeList(parcel, 3, a(this.zzd), false);
        SafeParcelWriter.writeTypedList(parcel, 4, this.zzd, false);
        SafeParcelWriter.writeInt(parcel, 1000, this.zza);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @ShowFirstParty
    @Deprecated
    public final void zzb(@NonNull DataPoint dataPoint) {
        this.zzc.add(dataPoint);
        DataSource originalDataSource = dataPoint.getOriginalDataSource();
        if (originalDataSource == null || this.zzd.contains(originalDataSource)) {
            return;
        }
        this.zzd.add(originalDataSource);
    }

    @ShowFirstParty
    public DataSet(@NonNull DataSource dataSource) {
        this.zza = 3;
        DataSource dataSource2 = (DataSource) Preconditions.checkNotNull(dataSource);
        this.zzb = dataSource2;
        this.zzc = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.zzd = arrayList;
        arrayList.add(dataSource2);
    }

    public DataSet(@NonNull RawDataSet rawDataSet, @NonNull List list) {
        this.zza = 3;
        this.zzb = (DataSource) list.get(rawDataSet.zza);
        this.zzd = list;
        List list2 = rawDataSet.zzb;
        this.zzc = new ArrayList(list2.size());
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            this.zzc.add(new DataPoint(this.zzd, (RawDataPoint) it.next()));
        }
    }
}
