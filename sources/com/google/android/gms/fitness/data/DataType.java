package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "DataTypeCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class DataType extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final DataType AGGREGATE_ACTIVITY_SUMMARY;

    @NonNull
    public static final DataType AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY;

    @NonNull
    public static final DataType AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY;

    @NonNull
    public static final DataType AGGREGATE_CALORIES_EXPENDED;

    @NonNull
    public static final DataType AGGREGATE_DISTANCE_DELTA;

    @NonNull
    public static final DataType AGGREGATE_HEART_POINTS;

    @NonNull
    public static final DataType AGGREGATE_HEART_RATE_SUMMARY;

    @NonNull
    public static final DataType AGGREGATE_HEIGHT_SUMMARY;

    @NonNull
    public static final DataType AGGREGATE_HYDRATION;

    @NonNull
    public static final DataType AGGREGATE_LOCATION_BOUNDING_BOX;

    @NonNull
    public static final DataType AGGREGATE_MOVE_MINUTES;

    @NonNull
    public static final DataType AGGREGATE_NUTRITION_SUMMARY;

    @NonNull
    public static final DataType AGGREGATE_POWER_SUMMARY;

    @NonNull
    public static final DataType AGGREGATE_SPEED_SUMMARY;

    @NonNull
    public static final DataType AGGREGATE_STEP_COUNT_DELTA;

    @NonNull
    public static final DataType AGGREGATE_WEIGHT_SUMMARY;

    @NonNull
    public static final Parcelable.Creator<DataType> CREATOR = new zzk();

    @NonNull
    public static final String MIME_TYPE_PREFIX = "vnd.google.fitness.data_type/";

    @NonNull
    public static final DataType TYPE_ACTIVITY_SEGMENT;

    @NonNull
    public static final DataType TYPE_BASAL_METABOLIC_RATE;

    @NonNull
    public static final DataType TYPE_BODY_FAT_PERCENTAGE;

    @NonNull
    public static final DataType TYPE_CALORIES_EXPENDED;

    @NonNull
    public static final DataType TYPE_CYCLING_PEDALING_CADENCE;

    @NonNull
    public static final DataType TYPE_CYCLING_PEDALING_CUMULATIVE;

    @NonNull
    public static final DataType TYPE_CYCLING_WHEEL_REVOLUTION;

    @NonNull
    public static final DataType TYPE_CYCLING_WHEEL_RPM;

    @NonNull
    public static final DataType TYPE_DISTANCE_DELTA;

    @NonNull
    public static final DataType TYPE_HEART_POINTS;

    @NonNull
    public static final DataType TYPE_HEART_RATE_BPM;

    @NonNull
    public static final DataType TYPE_HEIGHT;

    @NonNull
    public static final DataType TYPE_HYDRATION;

    @NonNull
    public static final DataType TYPE_LOCATION_SAMPLE;

    @NonNull
    @Deprecated
    public static final DataType TYPE_LOCATION_TRACK;

    @NonNull
    public static final DataType TYPE_MOVE_MINUTES;

    @NonNull
    public static final DataType TYPE_NUTRITION;

    @NonNull
    public static final DataType TYPE_POWER_SAMPLE;

    @NonNull
    public static final DataType TYPE_SLEEP_SEGMENT;

    @NonNull
    public static final DataType TYPE_SPEED;

    @NonNull
    public static final DataType TYPE_STEP_COUNT_CADENCE;

    @NonNull
    @ShowFirstParty
    @KeepForSdk
    public static final DataType TYPE_STEP_COUNT_CUMULATIVE;

    @NonNull
    public static final DataType TYPE_STEP_COUNT_DELTA;

    @NonNull
    public static final DataType TYPE_WEIGHT;

    @NonNull
    public static final DataType TYPE_WORKOUT_EXERCISE;

    @NonNull
    @ShowFirstParty
    public static final DataType zza;

    @NonNull
    @ShowFirstParty
    public static final DataType zzb;

    @NonNull
    @ShowFirstParty
    public static final DataType zzc;

    @NonNull
    @ShowFirstParty
    public static final DataType zzd;

    @NonNull
    public static final DataType zze;

    @NonNull
    @ShowFirstParty
    public static final DataType zzf;

    @NonNull
    @ShowFirstParty
    public static final DataType zzg;

    @NonNull
    @ShowFirstParty
    public static final DataType zzh;

    @NonNull
    @ShowFirstParty
    public static final DataType zzi;

    @NonNull
    @ShowFirstParty
    public static final DataType zzj;

    @NonNull
    @ShowFirstParty
    public static final DataType zzk;

    @NonNull
    @ShowFirstParty
    public static final DataType zzl;

    @NonNull
    @ShowFirstParty
    public static final DataType zzm;

    @NonNull
    @ShowFirstParty
    public static final DataType zzn;

    @NonNull
    @ShowFirstParty
    public static final DataType zzo;

    @NonNull
    @ShowFirstParty
    public static final DataType zzp;

    @NonNull
    @ShowFirstParty
    public static final DataType zzq;

    @NonNull
    @ShowFirstParty
    public static final DataType zzr;

    @NonNull
    @ShowFirstParty
    public static final DataType zzs;

    @SafeParcelable.Field(getter = "getName", id = 1)
    private final String zzt;

    @SafeParcelable.Field(getter = "getFields", id = 2)
    private final List zzu;

    @Nullable
    @SafeParcelable.Field(getter = "getReadScope", id = 3)
    private final String zzv;

    @Nullable
    @SafeParcelable.Field(getter = "getWriteScope", id = 4)
    private final String zzw;

    static {
        Field field = Field.FIELD_STEPS;
        DataType dataType = new DataType("com.google.step_count.delta", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field);
        TYPE_STEP_COUNT_DELTA = dataType;
        TYPE_STEP_COUNT_CUMULATIVE = new DataType("com.google.step_count.cumulative", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field);
        Field field2 = Field.FIELD_RPM;
        TYPE_STEP_COUNT_CADENCE = new DataType("com.google.step_count.cadence", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field2);
        zza = new DataType("com.google.internal.goal", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zze);
        Field field3 = Field.FIELD_ACTIVITY;
        TYPE_ACTIVITY_SEGMENT = new DataType("com.google.activity.segment", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field3);
        TYPE_SLEEP_SEGMENT = new DataType("com.google.sleep.segment", 2, "https://www.googleapis.com/auth/fitness.sleep.read", "https://www.googleapis.com/auth/fitness.sleep.write", Field.FIELD_SLEEP_SEGMENT_TYPE);
        Field field4 = Field.FIELD_CALORIES;
        DataType dataType2 = new DataType("com.google.calories.expended", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field4);
        TYPE_CALORIES_EXPENDED = dataType2;
        TYPE_BASAL_METABOLIC_RATE = new DataType("com.google.calories.bmr", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field4);
        TYPE_POWER_SAMPLE = new DataType("com.google.power.sample", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_WATTS);
        zzb = new DataType("com.google.sensor.events", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzg, Field.zzh, Field.zzi);
        TYPE_HEART_RATE_BPM = new DataType("com.google.heart_rate.bpm", 1, "https://www.googleapis.com/auth/fitness.heart_rate.read", "https://www.googleapis.com/auth/fitness.heart_rate.write", Field.FIELD_BPM);
        zzc = new DataType("com.google.respiratory_rate", 1, "https://www.googleapis.com/auth/fitness.respiratory_rate.read", "https://www.googleapis.com/auth/fitness.respiratory_rate.write", Field.zzd);
        Field field5 = Field.FIELD_LATITUDE;
        Field field6 = Field.FIELD_LONGITUDE;
        Field field7 = Field.FIELD_ACCURACY;
        Field field8 = Field.FIELD_ALTITUDE;
        TYPE_LOCATION_SAMPLE = new DataType("com.google.location.sample", 1, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", field5, field6, field7, field8);
        TYPE_LOCATION_TRACK = new DataType("com.google.location.track", 2, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", field5, field6, field7, field8);
        DataType dataType3 = new DataType("com.google.distance.delta", 2, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_DISTANCE);
        TYPE_DISTANCE_DELTA = dataType3;
        TYPE_SPEED = new DataType("com.google.speed", 1, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_SPEED);
        Field field9 = Field.FIELD_REVOLUTIONS;
        TYPE_CYCLING_WHEEL_REVOLUTION = new DataType("com.google.cycling.wheel_revolution.cumulative", 1, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", field9);
        TYPE_CYCLING_WHEEL_RPM = new DataType("com.google.cycling.wheel_revolution.rpm", 1, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", field2);
        TYPE_CYCLING_PEDALING_CUMULATIVE = new DataType("com.google.cycling.pedaling.cumulative", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field9);
        TYPE_CYCLING_PEDALING_CADENCE = new DataType("com.google.cycling.pedaling.cadence", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field2);
        TYPE_HEIGHT = new DataType("com.google.height", 1, "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_HEIGHT);
        TYPE_WEIGHT = new DataType("com.google.weight", 1, "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_WEIGHT);
        TYPE_BODY_FAT_PERCENTAGE = new DataType("com.google.body.fat.percentage", 1, "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", Field.FIELD_PERCENTAGE);
        Field field10 = Field.FIELD_NUTRIENTS;
        Field field11 = Field.FIELD_MEAL_TYPE;
        TYPE_NUTRITION = new DataType("com.google.nutrition", 1, "https://www.googleapis.com/auth/fitness.nutrition.read", "https://www.googleapis.com/auth/fitness.nutrition.write", field10, field11, Field.FIELD_FOOD_ITEM);
        DataType dataType4 = new DataType("com.google.hydration", 1, "https://www.googleapis.com/auth/fitness.nutrition.read", "https://www.googleapis.com/auth/fitness.nutrition.write", Field.FIELD_VOLUME);
        TYPE_HYDRATION = dataType4;
        TYPE_WORKOUT_EXERCISE = new DataType("com.google.activity.exercise", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_EXERCISE, Field.FIELD_REPETITIONS, Field.zza, Field.FIELD_RESISTANCE_TYPE, Field.FIELD_RESISTANCE);
        Field field12 = Field.FIELD_DURATION;
        DataType dataType5 = new DataType("com.google.active_minutes", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field12);
        TYPE_MOVE_MINUTES = dataType5;
        AGGREGATE_MOVE_MINUTES = dataType5;
        zzd = new DataType("com.google.device_on_body", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzk);
        AGGREGATE_ACTIVITY_SUMMARY = new DataType("com.google.activity.summary", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field3, field12, Field.FIELD_NUM_SEGMENTS);
        Field field13 = Field.FIELD_AVERAGE;
        Field field14 = Field.FIELD_MAX;
        Field field15 = Field.FIELD_MIN;
        AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY = new DataType("com.google.calories.bmr.summary", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field13, field14, field15);
        AGGREGATE_STEP_COUNT_DELTA = dataType;
        AGGREGATE_DISTANCE_DELTA = dataType3;
        AGGREGATE_CALORIES_EXPENDED = dataType2;
        Field field16 = Field.FIELD_INTENSITY;
        TYPE_HEART_POINTS = new DataType("com.google.heart_minutes", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field16);
        AGGREGATE_HEART_POINTS = new DataType("com.google.heart_minutes.summary", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field16, field12);
        AGGREGATE_HEART_RATE_SUMMARY = new DataType("com.google.heart_rate.summary", 2, "https://www.googleapis.com/auth/fitness.heart_rate.read", "https://www.googleapis.com/auth/fitness.heart_rate.write", field13, field14, field15);
        AGGREGATE_LOCATION_BOUNDING_BOX = new DataType("com.google.location.bounding_box", 2, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.FIELD_LOW_LATITUDE, Field.FIELD_LOW_LONGITUDE, Field.FIELD_HIGH_LATITUDE, Field.FIELD_HIGH_LONGITUDE);
        AGGREGATE_POWER_SUMMARY = new DataType("com.google.power.summary", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", field13, field14, field15);
        AGGREGATE_SPEED_SUMMARY = new DataType("com.google.speed.summary", 2, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", field13, field14, field15);
        AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY = new DataType("com.google.body.fat.percentage.summary", 2, "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", field13, field14, field15);
        AGGREGATE_WEIGHT_SUMMARY = new DataType("com.google.weight.summary", 2, "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", field13, field14, field15);
        AGGREGATE_HEIGHT_SUMMARY = new DataType("com.google.height.summary", 2, "https://www.googleapis.com/auth/fitness.body.read", "https://www.googleapis.com/auth/fitness.body.write", field13, field14, field15);
        AGGREGATE_NUTRITION_SUMMARY = new DataType("com.google.nutrition.summary", 2, "https://www.googleapis.com/auth/fitness.nutrition.read", "https://www.googleapis.com/auth/fitness.nutrition.write", field10, field11);
        AGGREGATE_HYDRATION = dataType4;
        zze = new DataType("com.google.activity.samples", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzj);
        zzf = new DataType("com.google.internal.sleep_attributes", 2, "https://www.googleapis.com/auth/fitness.sleep.read", "https://www.googleapis.com/auth/fitness.sleep.write", Field.zzl);
        zzg = new DataType("com.google.internal.sleep_schedule", 1, "https://www.googleapis.com/auth/fitness.sleep.read", "https://www.googleapis.com/auth/fitness.sleep.write", Field.zzm);
        zzh = new DataType("com.google.internal.paced_walking_attributes", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzn);
        zzi = new DataType("com.google.time_zone_change", 1, "https://www.googleapis.com/auth/fitness.location.read", "https://www.googleapis.com/auth/fitness.location.write", Field.zzo);
        zzj = new DataType("com.google.internal.met", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzp);
        zzk = new DataType("com.google.internal.internal_device_temperature", 2, "https://www.googleapis.com/auth/fitness.body_temperature.read", "https://www.googleapis.com/auth/fitness.body_temperature.write", Field.zzq);
        zzl = new DataType("com.google.internal.skin_temperature", 2, "https://www.googleapis.com/auth/fitness.body_temperature.read", "https://www.googleapis.com/auth/fitness.body_temperature.write", Field.zzr);
        Field field17 = Field.FIELD_MIN_INT;
        zzm = new DataType("com.google.internal.custom_heart_rate_zone", 1, "https://www.googleapis.com/auth/fitness.heart_rate.read", "https://www.googleapis.com/auth/fitness.heart_rate.write", Field.zzs, field17, field17);
        zzn = new DataType("com.google.internal.active_minutes_combined", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzt, Field.zzu, Field.zzv);
        zzo = new DataType("com.google.internal.sedentary_time", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzw);
        zzp = new DataType("com.google.internal.custom_max_heart_rate", 1, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.FIELD_MAX_INT);
        zzq = new DataType("com.google.internal.momentary_stress_algorithm", 1, "https://www.googleapis.com/auth/fitness.heart_rate.read", "https://www.googleapis.com/auth/fitness.heart_rate.write", Field.zzx);
        zzr = new DataType("com.google.internal.magnetic_field_presence", 2, "https://www.googleapis.com/auth/fitness.activity.read", "https://www.googleapis.com/auth/fitness.activity.write", Field.zzy);
        zzs = new DataType("com.google.internal.momentary_stress_algorithm_windows", 2, "https://www.googleapis.com/auth/fitness.heart_rate.read", "https://www.googleapis.com/auth/fitness.heart_rate.write", Field.zzz);
    }

    @ShowFirstParty
    public DataType(@NonNull String str, int i2, @Nullable String str2, @Nullable String str3, @NonNull Field... fieldArr) {
        this.zzt = str;
        this.zzu = Collections.unmodifiableList(Arrays.asList(fieldArr));
        this.zzv = str2;
        this.zzw = str3;
    }

    @NonNull
    @Deprecated
    public static List<DataType> getAggregatesForInput(@NonNull DataType dataType) {
        DataType aggregateType = dataType.getAggregateType();
        return aggregateType == null ? Collections.emptyList() : Collections.singletonList(aggregateType);
    }

    @NonNull
    public static String getMimeType(@NonNull DataType dataType) {
        return MIME_TYPE_PREFIX.concat(String.valueOf(dataType.getName()));
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DataType)) {
            return false;
        }
        DataType dataType = (DataType) obj;
        return this.zzt.equals(dataType.zzt) && this.zzu.equals(dataType.zzu);
    }

    @Nullable
    public DataType getAggregateType() {
        return (DataType) zza.zza.get(this);
    }

    @NonNull
    public List<Field> getFields() {
        return this.zzu;
    }

    @NonNull
    public String getName() {
        return this.zzt;
    }

    public int hashCode() {
        return this.zzt.hashCode();
    }

    public int indexOf(@NonNull Field field) {
        int iIndexOf = this.zzu.indexOf(field);
        Preconditions.checkArgument(iIndexOf >= 0, "%s not a field of %s", field, this);
        return iIndexOf;
    }

    @NonNull
    public String toString() {
        return String.format("DataType{%s%s}", this.zzt, this.zzu);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getName(), false);
        SafeParcelWriter.writeTypedList(parcel, 2, getFields(), false);
        SafeParcelWriter.writeString(parcel, 3, this.zzv, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzw, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Nullable
    @ShowFirstParty
    public final String zza() {
        return this.zzv;
    }

    @Nullable
    @ShowFirstParty
    public final String zzb() {
        return this.zzw;
    }

    @NonNull
    @ShowFirstParty
    public final String zzc() {
        return this.zzt.startsWith("com.google.") ? this.zzt.substring(11) : this.zzt;
    }

    DataType(String str, List list, String str2, String str3) {
        this.zzt = str;
        this.zzu = Collections.unmodifiableList(list);
        this.zzv = str2;
        this.zzw = str3;
    }
}
