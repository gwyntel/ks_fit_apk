package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fitness.zzfv;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SafeParcelable.Class(creator = "GoalCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class Goal extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<Goal> CREATOR = new zzs();
    public static final int OBJECTIVE_TYPE_DURATION = 2;
    public static final int OBJECTIVE_TYPE_FREQUENCY = 3;
    public static final int OBJECTIVE_TYPE_METRIC = 1;

    @SafeParcelable.Field(getter = "getCreateTimeNanos", id = 1)
    private final long zza;

    @SafeParcelable.Field(getter = "getExpireTimeNanos", id = 2)
    private final long zzb;

    @SafeParcelable.Field(getter = "getActivities", id = 3, type = "java.util.List")
    private final List zzc;

    @SafeParcelable.Field(getter = "getRecurrence", id = 4)
    private final Recurrence zzd;

    @SafeParcelable.Field(getter = "getObjectiveType", id = 5)
    private final int zze;

    @SafeParcelable.Field(getter = "getMetricObjectiveWithOutChecking", id = 6)
    private final MetricObjective zzf;

    @SafeParcelable.Field(getter = "getDurationObjectiveWithOutChecking", id = 7)
    private final DurationObjective zzg;

    @SafeParcelable.Field(getter = "getFrequencyObjectiveWithOutChecking", id = 8)
    private final FrequencyObjective zzh;

    @SafeParcelable.Class(creator = "DurationObjectiveCreator")
    @SafeParcelable.Reserved({1000})
    public static class DurationObjective extends AbstractSafeParcelable {

        @NonNull
        public static final Parcelable.Creator<DurationObjective> CREATOR = new zzp();

        @SafeParcelable.Field(getter = "getDuration", id = 1)
        private final long zza;

        DurationObjective(long j2) {
            this.zza = j2;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof DurationObjective) && this.zza == ((DurationObjective) obj).zza;
        }

        public long getDuration(@NonNull TimeUnit timeUnit) {
            return timeUnit.convert(this.zza, TimeUnit.NANOSECONDS);
        }

        public int hashCode() {
            return (int) this.zza;
        }

        @NonNull
        public String toString() {
            return Objects.toStringHelper(this).add("duration", Long.valueOf(this.zza)).toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i2) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeLong(parcel, 1, this.zza);
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }

        public DurationObjective(long j2, @NonNull TimeUnit timeUnit) {
            this.zza = timeUnit.toNanos(j2);
        }
    }

    @SafeParcelable.Class(creator = "FrequencyObjectiveCreator")
    @SafeParcelable.Reserved({1000})
    public static class FrequencyObjective extends AbstractSafeParcelable {

        @NonNull
        public static final Parcelable.Creator<FrequencyObjective> CREATOR = new zzr();

        @SafeParcelable.Field(getter = "getFrequency", id = 1)
        private final int zza;

        @SafeParcelable.Constructor
        public FrequencyObjective(@SafeParcelable.Param(id = 1) int i2) {
            this.zza = i2;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof FrequencyObjective) && this.zza == ((FrequencyObjective) obj).zza;
        }

        public int getFrequency() {
            return this.zza;
        }

        public int hashCode() {
            return this.zza;
        }

        @NonNull
        public String toString() {
            return Objects.toStringHelper(this).add("frequency", Integer.valueOf(this.zza)).toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i2) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, getFrequency());
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "MetricObjectiveCreator")
    @SafeParcelable.Reserved({1000})
    public static class MetricObjective extends AbstractSafeParcelable {

        @NonNull
        public static final Parcelable.Creator<MetricObjective> CREATOR = new zzx();

        @SafeParcelable.Field(getter = "getDataTypeName", id = 1)
        private final String zza;

        @SafeParcelable.Field(getter = "getValue", id = 2)
        private final double zzb;

        @SafeParcelable.Field(getter = "getInitialValue", id = 3)
        private final double zzc;

        public MetricObjective(@NonNull String str, double d2) {
            this(str, d2, 0.0d);
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MetricObjective)) {
                return false;
            }
            MetricObjective metricObjective = (MetricObjective) obj;
            return Objects.equal(this.zza, metricObjective.zza) && this.zzb == metricObjective.zzb && this.zzc == metricObjective.zzc;
        }

        @NonNull
        public String getDataTypeName() {
            return this.zza;
        }

        public double getValue() {
            return this.zzb;
        }

        public int hashCode() {
            return this.zza.hashCode();
        }

        @NonNull
        public String toString() {
            return Objects.toStringHelper(this).add("dataTypeName", this.zza).add("value", Double.valueOf(this.zzb)).add("initialValue", Double.valueOf(this.zzc)).toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i2) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeString(parcel, 1, getDataTypeName(), false);
            SafeParcelWriter.writeDouble(parcel, 2, getValue());
            SafeParcelWriter.writeDouble(parcel, 3, this.zzc);
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }

        @SafeParcelable.Constructor
        public MetricObjective(@NonNull @SafeParcelable.Param(id = 1) String str, @SafeParcelable.Param(id = 2) double d2, @SafeParcelable.Param(id = 3) double d3) {
            this.zza = str;
            this.zzb = d2;
            this.zzc = d3;
        }
    }

    public static class MismatchedGoalException extends IllegalStateException {
        public MismatchedGoalException(@NonNull String str) {
            super(str);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ObjectiveType {
    }

    @SafeParcelable.Class(creator = "RecurrenceCreator")
    @SafeParcelable.Reserved({1000})
    public static class Recurrence extends AbstractSafeParcelable {

        @NonNull
        public static final Parcelable.Creator<Recurrence> CREATOR = new zzab();
        public static final int UNIT_DAY = 1;
        public static final int UNIT_MONTH = 3;
        public static final int UNIT_WEEK = 2;

        @SafeParcelable.Field(getter = "getCount", id = 1)
        private final int zza;

        @SafeParcelable.Field(getter = "getUnit", id = 2)
        private final int zzb;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RecurrenceUnit {
        }

        @SafeParcelable.Constructor
        public Recurrence(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) int i3) {
            this.zza = i2;
            boolean z2 = false;
            if (i3 > 0 && i3 <= 3) {
                z2 = true;
            }
            Preconditions.checkState(z2);
            this.zzb = i3;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Recurrence)) {
                return false;
            }
            Recurrence recurrence = (Recurrence) obj;
            return this.zza == recurrence.zza && this.zzb == recurrence.zzb;
        }

        public int getCount() {
            return this.zza;
        }

        public int getUnit() {
            return this.zzb;
        }

        public int hashCode() {
            return this.zzb;
        }

        @NonNull
        public String toString() {
            String str;
            Objects.ToStringHelper toStringHelperAdd = Objects.toStringHelper(this).add("count", Integer.valueOf(this.zza));
            int i2 = this.zzb;
            if (i2 == 1) {
                str = "day";
            } else if (i2 == 2) {
                str = "week";
            } else {
                if (i2 != 3) {
                    throw new IllegalArgumentException("invalid unit value");
                }
                str = "month";
            }
            return toStringHelperAdd.add("unit", str).toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i2) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, getCount());
            SafeParcelWriter.writeInt(parcel, 2, getUnit());
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }
    }

    Goal(long j2, long j3, List list, Recurrence recurrence, int i2, MetricObjective metricObjective, DurationObjective durationObjective, FrequencyObjective frequencyObjective) {
        this.zza = j2;
        this.zzb = j3;
        this.zzc = list;
        this.zzd = recurrence;
        this.zze = i2;
        this.zzf = metricObjective;
        this.zzg = durationObjective;
        this.zzh = frequencyObjective;
    }

    private static String zza(int i2) {
        if (i2 == 0) {
            return "unknown";
        }
        if (i2 == 1) {
            return "metric";
        }
        if (i2 == 2) {
            return "duration";
        }
        if (i2 == 3) {
            return "frequency";
        }
        throw new IllegalArgumentException("invalid objective type value");
    }

    private final void zzb(int i2) {
        int i3 = this.zze;
        if (i2 != i3) {
            throw new MismatchedGoalException(String.format("%s goal does not have %s objective", zza(i3), zza(i2)));
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Goal)) {
            return false;
        }
        Goal goal = (Goal) obj;
        return this.zza == goal.zza && this.zzb == goal.zzb && Objects.equal(this.zzc, goal.zzc) && Objects.equal(this.zzd, goal.zzd) && this.zze == goal.zze && Objects.equal(this.zzf, goal.zzf) && Objects.equal(this.zzg, goal.zzg) && Objects.equal(this.zzh, goal.zzh);
    }

    @Nullable
    public String getActivityName() {
        if (this.zzc.isEmpty() || this.zzc.size() > 1) {
            return null;
        }
        return zzfv.zzb(((Integer) this.zzc.get(0)).intValue());
    }

    public long getCreateTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zza, TimeUnit.NANOSECONDS);
    }

    @NonNull
    public DurationObjective getDurationObjective() {
        zzb(2);
        return this.zzg;
    }

    public long getEndTime(@NonNull Calendar calendar, @NonNull TimeUnit timeUnit) {
        if (this.zzd == null) {
            return timeUnit.convert(this.zzb, TimeUnit.NANOSECONDS);
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(calendar.getTime());
        int i2 = this.zzd.zzb;
        if (i2 == 1) {
            calendar2.add(5, 1);
            calendar2.set(11, 0);
            return timeUnit.convert(calendar2.getTimeInMillis(), TimeUnit.MILLISECONDS);
        }
        if (i2 == 2) {
            calendar2.add(4, 1);
            calendar2.set(7, 2);
            calendar2.set(11, 0);
            return timeUnit.convert(calendar2.getTimeInMillis(), TimeUnit.MILLISECONDS);
        }
        if (i2 == 3) {
            calendar2.add(2, 1);
            calendar2.set(5, 1);
            calendar2.set(11, 0);
            return timeUnit.convert(calendar2.getTimeInMillis(), TimeUnit.MILLISECONDS);
        }
        throw new IllegalArgumentException("Invalid unit " + this.zzd.zzb);
    }

    @NonNull
    public FrequencyObjective getFrequencyObjective() {
        zzb(3);
        return this.zzh;
    }

    @NonNull
    public MetricObjective getMetricObjective() {
        zzb(1);
        return this.zzf;
    }

    public int getObjectiveType() {
        return this.zze;
    }

    @Nullable
    public Recurrence getRecurrence() {
        return this.zzd;
    }

    public long getStartTime(@NonNull Calendar calendar, @NonNull TimeUnit timeUnit) {
        if (this.zzd == null) {
            return timeUnit.convert(this.zza, TimeUnit.NANOSECONDS);
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(calendar.getTime());
        int i2 = this.zzd.zzb;
        if (i2 == 1) {
            calendar2.set(11, 0);
            return timeUnit.convert(calendar2.getTimeInMillis(), TimeUnit.MILLISECONDS);
        }
        if (i2 == 2) {
            calendar2.set(7, 2);
            calendar2.set(11, 0);
            return timeUnit.convert(calendar2.getTimeInMillis(), TimeUnit.MILLISECONDS);
        }
        if (i2 == 3) {
            calendar2.set(5, 1);
            calendar2.set(11, 0);
            return timeUnit.convert(calendar2.getTimeInMillis(), TimeUnit.MILLISECONDS);
        }
        throw new IllegalArgumentException("Invalid unit " + this.zzd.zzb);
    }

    public int hashCode() {
        return this.zze;
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add(PushConstants.INTENT_ACTIVITY_NAME, getActivityName()).add("recurrence", this.zzd).add("metricObjective", this.zzf).add("durationObjective", this.zzg).add("frequencyObjective", this.zzh).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.zza);
        SafeParcelWriter.writeLong(parcel, 2, this.zzb);
        SafeParcelWriter.writeList(parcel, 3, this.zzc, false);
        SafeParcelWriter.writeParcelable(parcel, 4, getRecurrence(), i2, false);
        SafeParcelWriter.writeInt(parcel, 5, getObjectiveType());
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzf, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzg, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzh, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
