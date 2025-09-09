package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.kingsmith.miot.KsProperty;
import com.meizu.cloud.pushsdk.constants.PushConstants;

@SafeParcelable.Class(creator = "FieldCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class Field extends AbstractSafeParcelable {
    public static final int FORMAT_FLOAT = 2;
    public static final int FORMAT_INT32 = 1;
    public static final int FORMAT_MAP = 4;
    public static final int FORMAT_STRING = 3;
    public static final int MEAL_TYPE_BREAKFAST = 1;
    public static final int MEAL_TYPE_DINNER = 3;
    public static final int MEAL_TYPE_LUNCH = 2;
    public static final int MEAL_TYPE_SNACK = 4;
    public static final int MEAL_TYPE_UNKNOWN = 0;

    @NonNull
    public static final String NUTRIENT_CALCIUM = "calcium";

    @NonNull
    public static final String NUTRIENT_CHOLESTEROL = "cholesterol";

    @NonNull
    public static final String NUTRIENT_DIETARY_FIBER = "dietary_fiber";

    @NonNull
    public static final String NUTRIENT_IRON = "iron";

    @NonNull
    public static final String NUTRIENT_MONOUNSATURATED_FAT = "fat.monounsaturated";

    @NonNull
    public static final String NUTRIENT_POLYUNSATURATED_FAT = "fat.polyunsaturated";

    @NonNull
    public static final String NUTRIENT_POTASSIUM = "potassium";

    @NonNull
    public static final String NUTRIENT_PROTEIN = "protein";

    @NonNull
    public static final String NUTRIENT_SATURATED_FAT = "fat.saturated";

    @NonNull
    public static final String NUTRIENT_SODIUM = "sodium";

    @NonNull
    public static final String NUTRIENT_SUGAR = "sugar";

    @NonNull
    public static final String NUTRIENT_TOTAL_CARBS = "carbs.total";

    @NonNull
    public static final String NUTRIENT_TOTAL_FAT = "fat.total";

    @NonNull
    public static final String NUTRIENT_TRANS_FAT = "fat.trans";

    @NonNull
    public static final String NUTRIENT_UNSATURATED_FAT = "fat.unsaturated";

    @NonNull
    public static final String NUTRIENT_VITAMIN_A = "vitamin_a";

    @NonNull
    public static final String NUTRIENT_VITAMIN_C = "vitamin_c";
    public static final int RESISTANCE_TYPE_BARBELL = 1;
    public static final int RESISTANCE_TYPE_BODY = 6;
    public static final int RESISTANCE_TYPE_CABLE = 2;
    public static final int RESISTANCE_TYPE_DUMBBELL = 3;
    public static final int RESISTANCE_TYPE_KETTLEBELL = 4;
    public static final int RESISTANCE_TYPE_MACHINE = 5;
    public static final int RESISTANCE_TYPE_UNKNOWN = 0;

    @SafeParcelable.Field(getter = "getName", id = 1)
    private final String zzA;

    @SafeParcelable.Field(getter = "getFormat", id = 2)
    private final int zzB;

    @Nullable
    @SafeParcelable.Field(getter = "isOptional", id = 3)
    private final Boolean zzC;

    @NonNull
    public static final Parcelable.Creator<Field> CREATOR = new zzq();

    @NonNull
    public static final Field FIELD_ACTIVITY = zzd(PushConstants.INTENT_ACTIVITY_NAME);

    @NonNull
    public static final Field FIELD_SLEEP_SEGMENT_TYPE = zzd("sleep_segment_type");

    @NonNull
    public static final Field FIELD_CONFIDENCE = zzb("confidence");

    @NonNull
    public static final Field FIELD_STEPS = zzd("steps");

    @NonNull
    @Deprecated
    public static final Field FIELD_STEP_LENGTH = zzb("step_length");

    @NonNull
    public static final Field FIELD_DURATION = zzd("duration");

    @NonNull
    @ShowFirstParty
    public static final Field zza = zzf("duration");

    @NonNull
    @ShowFirstParty
    public static final Field zzb = zzc("activity_duration.ascending");

    @NonNull
    @ShowFirstParty
    public static final Field zzc = zzc("activity_duration.descending");

    @NonNull
    public static final Field FIELD_BPM = zzb("bpm");

    @NonNull
    @ShowFirstParty
    public static final Field zzd = zzb("respiratory_rate");

    @NonNull
    public static final Field FIELD_LATITUDE = zzb("latitude");

    @NonNull
    public static final Field FIELD_LONGITUDE = zzb("longitude");

    @NonNull
    public static final Field FIELD_ACCURACY = zzb("accuracy");

    @NonNull
    public static final Field FIELD_ALTITUDE = zze("altitude");

    @NonNull
    public static final Field FIELD_DISTANCE = zzb("distance");

    @NonNull
    public static final Field FIELD_HEIGHT = zzb(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);

    @NonNull
    public static final Field FIELD_WEIGHT = zzb("weight");

    @NonNull
    public static final Field FIELD_PERCENTAGE = zzb("percentage");

    @NonNull
    public static final Field FIELD_SPEED = zzb(KsProperty.Speed);

    @NonNull
    public static final Field FIELD_RPM = zzb("rpm");

    @NonNull
    @ShowFirstParty
    public static final Field zze = zza("google.android.fitness.GoalV2");

    @NonNull
    @ShowFirstParty
    public static final Field zzf = zza("google.android.fitness.Device");

    @NonNull
    public static final Field FIELD_REVOLUTIONS = zzd("revolutions");

    @NonNull
    public static final String NUTRIENT_CALORIES = "calories";

    @NonNull
    public static final Field FIELD_CALORIES = zzb(NUTRIENT_CALORIES);

    @NonNull
    public static final Field FIELD_WATTS = zzb("watts");

    @NonNull
    public static final Field FIELD_VOLUME = zzb("volume");

    @NonNull
    public static final Field FIELD_MEAL_TYPE = zzf("meal_type");

    @NonNull
    public static final Field FIELD_FOOD_ITEM = new Field("food_item", 3, Boolean.TRUE);

    @NonNull
    public static final Field FIELD_NUTRIENTS = zzc("nutrients");

    @NonNull
    public static final Field FIELD_EXERCISE = zzg("exercise");

    @NonNull
    public static final Field FIELD_REPETITIONS = zzf("repetitions");

    @NonNull
    public static final Field FIELD_RESISTANCE = zze("resistance");

    @NonNull
    public static final Field FIELD_RESISTANCE_TYPE = zzf("resistance_type");

    @NonNull
    public static final Field FIELD_NUM_SEGMENTS = zzd("num_segments");

    @NonNull
    public static final Field FIELD_AVERAGE = zzb("average");

    @NonNull
    public static final Field FIELD_MAX = zzb(KsProperty.Max);

    @NonNull
    public static final Field FIELD_MIN = zzb("min");

    @NonNull
    public static final Field FIELD_LOW_LATITUDE = zzb("low_latitude");

    @NonNull
    public static final Field FIELD_LOW_LONGITUDE = zzb("low_longitude");

    @NonNull
    public static final Field FIELD_HIGH_LATITUDE = zzb("high_latitude");

    @NonNull
    public static final Field FIELD_HIGH_LONGITUDE = zzb("high_longitude");

    @NonNull
    public static final Field FIELD_OCCURRENCES = zzd("occurrences");

    @NonNull
    @ShowFirstParty
    public static final Field zzg = zzd("sensor_type");

    @NonNull
    @ShowFirstParty
    public static final Field zzh = new Field("timestamps", 5, null);

    @NonNull
    @ShowFirstParty
    public static final Field zzi = new Field("sensor_values", 6, null);

    @NonNull
    public static final Field FIELD_INTENSITY = zzb("intensity");

    @NonNull
    @ShowFirstParty
    public static final Field zzj = zzc("activity_confidence");

    @NonNull
    @ShowFirstParty
    public static final Field zzk = zzb("probability");

    @NonNull
    @ShowFirstParty
    public static final Field zzl = zza("google.android.fitness.SleepAttributes");

    @NonNull
    @ShowFirstParty
    public static final Field zzm = zza("google.android.fitness.SleepSchedule");

    @NonNull
    @Deprecated
    public static final Field FIELD_CIRCUMFERENCE = zzb("circumference");

    @NonNull
    @ShowFirstParty
    public static final Field zzn = zza("google.android.fitness.PacedWalkingAttributes");

    @NonNull
    @ShowFirstParty
    public static final Field zzo = zzg("zone_id");

    @NonNull
    @ShowFirstParty
    public static final Field zzp = zzb("met");

    @NonNull
    @ShowFirstParty
    public static final Field zzq = zzb("internal_device_temperature");

    @NonNull
    @ShowFirstParty
    public static final Field zzr = zzb("skin_temperature");

    @NonNull
    @ShowFirstParty
    public static final Field zzs = zzd("custom_heart_rate_zone_status");

    @NonNull
    public static final Field FIELD_MIN_INT = zzd("min_int");

    @NonNull
    public static final Field FIELD_MAX_INT = zzd("max_int");

    @NonNull
    @ShowFirstParty
    public static final Field zzt = zzf("lightly_active_duration");

    @NonNull
    @ShowFirstParty
    public static final Field zzu = zzf("moderately_active_duration");

    @NonNull
    @ShowFirstParty
    public static final Field zzv = zzf("very_active_duration");

    @NonNull
    @ShowFirstParty
    public static final Field zzw = zza("google.android.fitness.SedentaryTime");

    @NonNull
    @ShowFirstParty
    public static final Field zzx = zza("google.android.fitness.MomentaryStressAlgorithm");

    @NonNull
    @ShowFirstParty
    public static final Field zzy = zzd("magnet_presence");

    @NonNull
    @ShowFirstParty
    public static final Field zzz = zza("google.android.fitness.MomentaryStressAlgorithmWindows");

    @ShowFirstParty
    @SafeParcelable.Constructor
    public Field(@NonNull @SafeParcelable.Param(id = 1) String str, @SafeParcelable.Param(id = 2) int i2, @Nullable @SafeParcelable.Param(id = 3) Boolean bool) {
        this.zzA = (String) Preconditions.checkNotNull(str);
        this.zzB = i2;
        this.zzC = bool;
    }

    @NonNull
    @ShowFirstParty
    public static Field zza(@NonNull String str) {
        return new Field(str, 7, null);
    }

    @NonNull
    @ShowFirstParty
    public static Field zzb(@NonNull String str) {
        return new Field(str, 2, null);
    }

    @NonNull
    @ShowFirstParty
    public static Field zzc(@NonNull String str) {
        return new Field(str, 4, null);
    }

    @NonNull
    @ShowFirstParty
    public static Field zzd(@NonNull String str) {
        return new Field(str, 1, null);
    }

    @NonNull
    @ShowFirstParty
    public static Field zze(@NonNull String str) {
        return new Field(str, 2, Boolean.TRUE);
    }

    @NonNull
    @ShowFirstParty
    public static Field zzf(@NonNull String str) {
        return new Field(str, 1, Boolean.TRUE);
    }

    @NonNull
    @ShowFirstParty
    public static Field zzg(@NonNull String str) {
        return new Field(str, 3, null);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Field)) {
            return false;
        }
        Field field = (Field) obj;
        return this.zzA.equals(field.zzA) && this.zzB == field.zzB;
    }

    public int getFormat() {
        return this.zzB;
    }

    @NonNull
    public String getName() {
        return this.zzA;
    }

    public int hashCode() {
        return this.zzA.hashCode();
    }

    @Nullable
    public Boolean isOptional() {
        return this.zzC;
    }

    @NonNull
    public String toString() {
        return String.format("%s(%s)", this.zzA, this.zzB == 1 ? "i" : "f");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getName(), false);
        SafeParcelWriter.writeInt(parcel, 2, getFormat());
        SafeParcelWriter.writeBooleanObject(parcel, 3, isOptional(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
