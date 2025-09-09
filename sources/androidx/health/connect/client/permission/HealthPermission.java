package androidx.health.connect.client.permission;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord;
import androidx.health.connect.client.records.BasalBodyTemperatureRecord;
import androidx.health.connect.client.records.BasalMetabolicRateRecord;
import androidx.health.connect.client.records.BloodGlucoseRecord;
import androidx.health.connect.client.records.BloodPressureRecord;
import androidx.health.connect.client.records.BodyFatRecord;
import androidx.health.connect.client.records.BodyTemperatureRecord;
import androidx.health.connect.client.records.BodyWaterMassRecord;
import androidx.health.connect.client.records.BoneMassRecord;
import androidx.health.connect.client.records.CervicalMucusRecord;
import androidx.health.connect.client.records.CyclingPedalingCadenceRecord;
import androidx.health.connect.client.records.DistanceRecord;
import androidx.health.connect.client.records.ElevationGainedRecord;
import androidx.health.connect.client.records.ExerciseSessionRecord;
import androidx.health.connect.client.records.FloorsClimbedRecord;
import androidx.health.connect.client.records.HeartRateRecord;
import androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord;
import androidx.health.connect.client.records.HeightRecord;
import androidx.health.connect.client.records.HydrationRecord;
import androidx.health.connect.client.records.IntermenstrualBleedingRecord;
import androidx.health.connect.client.records.LeanBodyMassRecord;
import androidx.health.connect.client.records.MenstruationFlowRecord;
import androidx.health.connect.client.records.MenstruationPeriodRecord;
import androidx.health.connect.client.records.NutritionRecord;
import androidx.health.connect.client.records.OvulationTestRecord;
import androidx.health.connect.client.records.OxygenSaturationRecord;
import androidx.health.connect.client.records.PowerRecord;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.records.RespiratoryRateRecord;
import androidx.health.connect.client.records.RestingHeartRateRecord;
import androidx.health.connect.client.records.SexualActivityRecord;
import androidx.health.connect.client.records.SleepSessionRecord;
import androidx.health.connect.client.records.SleepStageRecord;
import androidx.health.connect.client.records.SpeedRecord;
import androidx.health.connect.client.records.StepsCadenceRecord;
import androidx.health.connect.client.records.StepsRecord;
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord;
import androidx.health.connect.client.records.Vo2MaxRecord;
import androidx.health.connect.client.records.WeightRecord;
import androidx.health.connect.client.records.WheelchairPushesRecord;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u001f\b\u0000\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0006H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0013"}, d2 = {"Landroidx/health/connect/client/permission/HealthPermission;", "", "recordType", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "accessType", "", "(Lkotlin/reflect/KClass;I)V", "getAccessType$connect_client_release$annotations", "()V", "getAccessType$connect_client_release", "()I", "getRecordType$connect_client_release", "()Lkotlin/reflect/KClass;", "equals", "", "other", "hashCode", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class HealthPermission {

    @NotNull
    public static final String PERMISSION_PREFIX = "android.permission.health.";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String PERMISSION_READ_HEALTH_DATA_IN_BACKGROUND = "android.permission.health.READ_HEALTH_DATA_IN_BACKGROUND";

    @NotNull
    public static final String PERMISSION_WRITE_EXERCISE_ROUTE = "android.permission.health.WRITE_EXERCISE_ROUTE";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String READ_HIP_CIRCUMFERENCE = "android.permission.health.READ_HIP_CIRCUMFERENCE";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String READ_WAIST_CIRCUMFERENCE = "android.permission.health.READ_WAIST_CIRCUMFERENCE";

    @NotNull
    public static final String WRITE_ACTIVE_CALORIES_BURNED = "android.permission.health.WRITE_ACTIVE_CALORIES_BURNED";

    @NotNull
    public static final String WRITE_BASAL_BODY_TEMPERATURE = "android.permission.health.WRITE_BASAL_BODY_TEMPERATURE";

    @NotNull
    public static final String WRITE_BASAL_METABOLIC_RATE = "android.permission.health.WRITE_BASAL_METABOLIC_RATE";

    @NotNull
    public static final String WRITE_BLOOD_GLUCOSE = "android.permission.health.WRITE_BLOOD_GLUCOSE";

    @NotNull
    public static final String WRITE_BLOOD_PRESSURE = "android.permission.health.WRITE_BLOOD_PRESSURE";

    @NotNull
    public static final String WRITE_BODY_FAT = "android.permission.health.WRITE_BODY_FAT";

    @NotNull
    public static final String WRITE_BODY_TEMPERATURE = "android.permission.health.WRITE_BODY_TEMPERATURE";

    @NotNull
    public static final String WRITE_BODY_WATER_MASS = "android.permission.health.WRITE_BODY_WATER_MASS";

    @NotNull
    public static final String WRITE_BONE_MASS = "android.permission.health.WRITE_BONE_MASS";

    @NotNull
    public static final String WRITE_CERVICAL_MUCUS = "android.permission.health.WRITE_CERVICAL_MUCUS";

    @NotNull
    public static final String WRITE_DISTANCE = "android.permission.health.WRITE_DISTANCE";

    @NotNull
    public static final String WRITE_ELEVATION_GAINED = "android.permission.health.WRITE_ELEVATION_GAINED";

    @NotNull
    public static final String WRITE_EXERCISE = "android.permission.health.WRITE_EXERCISE";

    @NotNull
    public static final String WRITE_FLOORS_CLIMBED = "android.permission.health.WRITE_FLOORS_CLIMBED";

    @NotNull
    public static final String WRITE_HEART_RATE = "android.permission.health.WRITE_HEART_RATE";

    @NotNull
    public static final String WRITE_HEART_RATE_VARIABILITY = "android.permission.health.WRITE_HEART_RATE_VARIABILITY";

    @NotNull
    public static final String WRITE_HEIGHT = "android.permission.health.WRITE_HEIGHT";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String WRITE_HIP_CIRCUMFERENCE = "android.permission.health.WRITE_HIP_CIRCUMFERENCE";

    @NotNull
    public static final String WRITE_HYDRATION = "android.permission.health.WRITE_HYDRATION";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String WRITE_INTERMENSTRUAL_BLEEDING = "android.permission.health.WRITE_INTERMENSTRUAL_BLEEDING";

    @NotNull
    public static final String WRITE_LEAN_BODY_MASS = "android.permission.health.WRITE_LEAN_BODY_MASS";

    @NotNull
    public static final String WRITE_MENSTRUATION = "android.permission.health.WRITE_MENSTRUATION";

    @NotNull
    public static final String WRITE_NUTRITION = "android.permission.health.WRITE_NUTRITION";

    @NotNull
    public static final String WRITE_OVULATION_TEST = "android.permission.health.WRITE_OVULATION_TEST";

    @NotNull
    public static final String WRITE_OXYGEN_SATURATION = "android.permission.health.WRITE_OXYGEN_SATURATION";

    @NotNull
    public static final String WRITE_PERMISSION_PREFIX = "android.permission.health.WRITE_";

    @NotNull
    public static final String WRITE_POWER = "android.permission.health.WRITE_POWER";

    @NotNull
    public static final String WRITE_RESPIRATORY_RATE = "android.permission.health.WRITE_RESPIRATORY_RATE";

    @NotNull
    public static final String WRITE_RESTING_HEART_RATE = "android.permission.health.WRITE_RESTING_HEART_RATE";

    @NotNull
    public static final String WRITE_SEXUAL_ACTIVITY = "android.permission.health.WRITE_SEXUAL_ACTIVITY";

    @NotNull
    public static final String WRITE_SLEEP = "android.permission.health.WRITE_SLEEP";

    @NotNull
    public static final String WRITE_SPEED = "android.permission.health.WRITE_SPEED";

    @NotNull
    public static final String WRITE_STEPS = "android.permission.health.WRITE_STEPS";

    @NotNull
    public static final String WRITE_TOTAL_CALORIES_BURNED = "android.permission.health.WRITE_TOTAL_CALORIES_BURNED";

    @NotNull
    public static final String WRITE_VO2_MAX = "android.permission.health.WRITE_VO2_MAX";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String WRITE_WAIST_CIRCUMFERENCE = "android.permission.health.WRITE_WAIST_CIRCUMFERENCE";

    @NotNull
    public static final String WRITE_WEIGHT = "android.permission.health.WRITE_WEIGHT";

    @NotNull
    public static final String WRITE_WHEELCHAIR_PUSHES = "android.permission.health.WRITE_WHEELCHAIR_PUSHES";
    private final int accessType;

    @NotNull
    private final KClass<? extends Record> recordType;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String READ_ACTIVE_CALORIES_BURNED = "android.permission.health.READ_ACTIVE_CALORIES_BURNED";

    @NotNull
    public static final String READ_PERMISSION_PREFIX = "android.permission.health.READ_";

    @NotNull
    public static final String READ_BASAL_BODY_TEMPERATURE = "android.permission.health.READ_BASAL_BODY_TEMPERATURE";

    @NotNull
    public static final String READ_BASAL_METABOLIC_RATE = "android.permission.health.READ_BASAL_METABOLIC_RATE";

    @NotNull
    public static final String READ_BLOOD_GLUCOSE = "android.permission.health.READ_BLOOD_GLUCOSE";

    @NotNull
    public static final String READ_BLOOD_PRESSURE = "android.permission.health.READ_BLOOD_PRESSURE";

    @NotNull
    public static final String READ_BODY_FAT = "android.permission.health.READ_BODY_FAT";

    @NotNull
    public static final String READ_BODY_TEMPERATURE = "android.permission.health.READ_BODY_TEMPERATURE";

    @NotNull
    public static final String READ_BODY_WATER_MASS = "android.permission.health.READ_BODY_WATER_MASS";

    @NotNull
    public static final String READ_BONE_MASS = "android.permission.health.READ_BONE_MASS";

    @NotNull
    public static final String READ_CERVICAL_MUCUS = "android.permission.health.READ_CERVICAL_MUCUS";

    @NotNull
    public static final String READ_EXERCISE = "android.permission.health.READ_EXERCISE";

    @NotNull
    public static final String READ_DISTANCE = "android.permission.health.READ_DISTANCE";

    @NotNull
    public static final String READ_ELEVATION_GAINED = "android.permission.health.READ_ELEVATION_GAINED";

    @NotNull
    public static final String READ_FLOORS_CLIMBED = "android.permission.health.READ_FLOORS_CLIMBED";

    @NotNull
    public static final String READ_HEART_RATE = "android.permission.health.READ_HEART_RATE";

    @NotNull
    public static final String READ_HEART_RATE_VARIABILITY = "android.permission.health.READ_HEART_RATE_VARIABILITY";

    @NotNull
    public static final String READ_HEIGHT = "android.permission.health.READ_HEIGHT";

    @NotNull
    public static final String READ_HYDRATION = "android.permission.health.READ_HYDRATION";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String READ_INTERMENSTRUAL_BLEEDING = "android.permission.health.READ_INTERMENSTRUAL_BLEEDING";

    @NotNull
    public static final String READ_LEAN_BODY_MASS = "android.permission.health.READ_LEAN_BODY_MASS";

    @NotNull
    public static final String READ_MENSTRUATION = "android.permission.health.READ_MENSTRUATION";

    @NotNull
    public static final String READ_NUTRITION = "android.permission.health.READ_NUTRITION";

    @NotNull
    public static final String READ_OVULATION_TEST = "android.permission.health.READ_OVULATION_TEST";

    @NotNull
    public static final String READ_OXYGEN_SATURATION = "android.permission.health.READ_OXYGEN_SATURATION";

    @NotNull
    public static final String READ_POWER = "android.permission.health.READ_POWER";

    @NotNull
    public static final String READ_RESPIRATORY_RATE = "android.permission.health.READ_RESPIRATORY_RATE";

    @NotNull
    public static final String READ_RESTING_HEART_RATE = "android.permission.health.READ_RESTING_HEART_RATE";

    @NotNull
    public static final String READ_SEXUAL_ACTIVITY = "android.permission.health.READ_SEXUAL_ACTIVITY";

    @NotNull
    public static final String READ_SLEEP = "android.permission.health.READ_SLEEP";

    @NotNull
    public static final String READ_SPEED = "android.permission.health.READ_SPEED";

    @NotNull
    public static final String READ_STEPS = "android.permission.health.READ_STEPS";

    @NotNull
    public static final String READ_TOTAL_CALORIES_BURNED = "android.permission.health.READ_TOTAL_CALORIES_BURNED";

    @NotNull
    public static final String READ_VO2_MAX = "android.permission.health.READ_VO2_MAX";

    @NotNull
    public static final String READ_WEIGHT = "android.permission.health.READ_WEIGHT";

    @NotNull
    public static final String READ_WHEELCHAIR_PUSHES = "android.permission.health.READ_WHEELCHAIR_PUSHES";

    @NotNull
    private static final Map<KClass<? extends Record>, String> RECORD_TYPE_TO_PERMISSION = MapsKt.mapOf(TuplesKt.to(Reflection.getOrCreateKotlinClass(ActiveCaloriesBurnedRecord.class), StringsKt.substringAfter$default(READ_ACTIVE_CALORIES_BURNED, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(BasalBodyTemperatureRecord.class), StringsKt.substringAfter$default(READ_BASAL_BODY_TEMPERATURE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(BasalMetabolicRateRecord.class), StringsKt.substringAfter$default(READ_BASAL_METABOLIC_RATE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(BloodGlucoseRecord.class), StringsKt.substringAfter$default(READ_BLOOD_GLUCOSE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(BloodPressureRecord.class), StringsKt.substringAfter$default(READ_BLOOD_PRESSURE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(BodyFatRecord.class), StringsKt.substringAfter$default(READ_BODY_FAT, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(BodyTemperatureRecord.class), StringsKt.substringAfter$default(READ_BODY_TEMPERATURE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(BodyWaterMassRecord.class), StringsKt.substringAfter$default(READ_BODY_WATER_MASS, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(BoneMassRecord.class), StringsKt.substringAfter$default(READ_BONE_MASS, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(CervicalMucusRecord.class), StringsKt.substringAfter$default(READ_CERVICAL_MUCUS, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(CyclingPedalingCadenceRecord.class), StringsKt.substringAfter$default(READ_EXERCISE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(DistanceRecord.class), StringsKt.substringAfter$default(READ_DISTANCE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(ElevationGainedRecord.class), StringsKt.substringAfter$default(READ_ELEVATION_GAINED, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(ExerciseSessionRecord.class), StringsKt.substringAfter$default(READ_EXERCISE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(FloorsClimbedRecord.class), StringsKt.substringAfter$default(READ_FLOORS_CLIMBED, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(HeartRateRecord.class), StringsKt.substringAfter$default(READ_HEART_RATE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(HeartRateVariabilityRmssdRecord.class), StringsKt.substringAfter$default(READ_HEART_RATE_VARIABILITY, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(HeightRecord.class), StringsKt.substringAfter$default(READ_HEIGHT, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(HydrationRecord.class), StringsKt.substringAfter$default(READ_HYDRATION, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(IntermenstrualBleedingRecord.class), StringsKt.substringAfter$default(READ_INTERMENSTRUAL_BLEEDING, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(LeanBodyMassRecord.class), StringsKt.substringAfter$default(READ_LEAN_BODY_MASS, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(MenstruationFlowRecord.class), StringsKt.substringAfter$default(READ_MENSTRUATION, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(MenstruationPeriodRecord.class), StringsKt.substringAfter$default(READ_MENSTRUATION, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(NutritionRecord.class), StringsKt.substringAfter$default(READ_NUTRITION, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(OvulationTestRecord.class), StringsKt.substringAfter$default(READ_OVULATION_TEST, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(OxygenSaturationRecord.class), StringsKt.substringAfter$default(READ_OXYGEN_SATURATION, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(PowerRecord.class), StringsKt.substringAfter$default(READ_POWER, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(RespiratoryRateRecord.class), StringsKt.substringAfter$default(READ_RESPIRATORY_RATE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(RestingHeartRateRecord.class), StringsKt.substringAfter$default(READ_RESTING_HEART_RATE, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(SexualActivityRecord.class), StringsKt.substringAfter$default(READ_SEXUAL_ACTIVITY, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(SleepSessionRecord.class), StringsKt.substringAfter$default(READ_SLEEP, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(SleepStageRecord.class), StringsKt.substringAfter$default(READ_SLEEP, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(SpeedRecord.class), StringsKt.substringAfter$default(READ_SPEED, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(StepsCadenceRecord.class), StringsKt.substringAfter$default(READ_STEPS, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(StepsRecord.class), StringsKt.substringAfter$default(READ_STEPS, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class), StringsKt.substringAfter$default(READ_TOTAL_CALORIES_BURNED, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(Vo2MaxRecord.class), StringsKt.substringAfter$default(READ_VO2_MAX, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(WeightRecord.class), StringsKt.substringAfter$default(READ_WEIGHT, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)), TuplesKt.to(Reflection.getOrCreateKotlinClass(WheelchairPushesRecord.class), StringsKt.substringAfter$default(READ_WHEELCHAIR_PUSHES, READ_PERMISSION_PREFIX, (String) null, 2, (Object) null)));

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b)\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b)\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010Y\u001a\u00020Z2\u000e\u0010[\u001a\n\u0012\u0006\b\u0001\u0012\u0002000/H\u0007J\u0018\u0010\\\u001a\u00020Z2\u000e\u0010[\u001a\n\u0012\u0006\b\u0001\u0012\u0002000/H\u0007J\u0018\u0010]\u001a\u00020\u00042\u000e\u0010[\u001a\n\u0012\u0006\b\u0001\u0012\u0002000/H\u0007J\u0018\u0010^\u001a\u00020\u00042\u000e\u0010[\u001a\n\u0012\u0006\b\u0001\u0012\u0002000/H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u00020\u00048\u0000X\u0081T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u00020\u00048\u0000X\u0081T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u00020\u00048\u0000X\u0081T¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R(\u0010-\u001a\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u0002000/\u0012\u0004\u0012\u00020\u00040.X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u000e\u00103\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u00020\u00048\u0000X\u0081T¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010F\u001a\u00020\u00048\u0000X\u0081T¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010V\u001a\u00020\u00048\u0000X\u0081T¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006_"}, d2 = {"Landroidx/health/connect/client/permission/HealthPermission$Companion;", "", "()V", "PERMISSION_PREFIX", "", "PERMISSION_READ_HEALTH_DATA_IN_BACKGROUND", "PERMISSION_WRITE_EXERCISE_ROUTE", "READ_ACTIVE_CALORIES_BURNED", "READ_BASAL_BODY_TEMPERATURE", "READ_BASAL_METABOLIC_RATE", "READ_BLOOD_GLUCOSE", "READ_BLOOD_PRESSURE", "READ_BODY_FAT", "READ_BODY_TEMPERATURE", "READ_BODY_WATER_MASS", "READ_BONE_MASS", "READ_CERVICAL_MUCUS", "READ_DISTANCE", "READ_ELEVATION_GAINED", "READ_EXERCISE", "READ_FLOORS_CLIMBED", "READ_HEART_RATE", "READ_HEART_RATE_VARIABILITY", "READ_HEIGHT", "READ_HIP_CIRCUMFERENCE", "READ_HYDRATION", "READ_INTERMENSTRUAL_BLEEDING", "READ_LEAN_BODY_MASS", "READ_MENSTRUATION", "READ_NUTRITION", "READ_OVULATION_TEST", "READ_OXYGEN_SATURATION", "READ_PERMISSION_PREFIX", "READ_POWER", "READ_RESPIRATORY_RATE", "READ_RESTING_HEART_RATE", "READ_SEXUAL_ACTIVITY", "READ_SLEEP", "READ_SPEED", "READ_STEPS", "READ_TOTAL_CALORIES_BURNED", "READ_VO2_MAX", "READ_WAIST_CIRCUMFERENCE", "READ_WEIGHT", "READ_WHEELCHAIR_PUSHES", "RECORD_TYPE_TO_PERMISSION", "", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "getRECORD_TYPE_TO_PERMISSION$connect_client_release", "()Ljava/util/Map;", "WRITE_ACTIVE_CALORIES_BURNED", "WRITE_BASAL_BODY_TEMPERATURE", "WRITE_BASAL_METABOLIC_RATE", "WRITE_BLOOD_GLUCOSE", "WRITE_BLOOD_PRESSURE", "WRITE_BODY_FAT", "WRITE_BODY_TEMPERATURE", "WRITE_BODY_WATER_MASS", "WRITE_BONE_MASS", "WRITE_CERVICAL_MUCUS", "WRITE_DISTANCE", "WRITE_ELEVATION_GAINED", "WRITE_EXERCISE", "WRITE_FLOORS_CLIMBED", "WRITE_HEART_RATE", "WRITE_HEART_RATE_VARIABILITY", "WRITE_HEIGHT", "WRITE_HIP_CIRCUMFERENCE", "WRITE_HYDRATION", "WRITE_INTERMENSTRUAL_BLEEDING", "WRITE_LEAN_BODY_MASS", "WRITE_MENSTRUATION", "WRITE_NUTRITION", "WRITE_OVULATION_TEST", "WRITE_OXYGEN_SATURATION", "WRITE_PERMISSION_PREFIX", "WRITE_POWER", "WRITE_RESPIRATORY_RATE", "WRITE_RESTING_HEART_RATE", "WRITE_SEXUAL_ACTIVITY", "WRITE_SLEEP", "WRITE_SPEED", "WRITE_STEPS", "WRITE_TOTAL_CALORIES_BURNED", "WRITE_VO2_MAX", "WRITE_WAIST_CIRCUMFERENCE", "WRITE_WEIGHT", "WRITE_WHEELCHAIR_PUSHES", "createReadPermissionLegacy", "Landroidx/health/connect/client/permission/HealthPermission;", "recordType", "createWritePermissionLegacy", "getReadPermission", "getWritePermission", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        @NotNull
        public final HealthPermission createReadPermissionLegacy(@NotNull KClass<? extends Record> recordType) {
            Intrinsics.checkNotNullParameter(recordType, "recordType");
            return new HealthPermission(recordType, 1);
        }

        @JvmStatic
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        @NotNull
        public final HealthPermission createWritePermissionLegacy(@NotNull KClass<? extends Record> recordType) {
            Intrinsics.checkNotNullParameter(recordType, "recordType");
            return new HealthPermission(recordType, 2);
        }

        @NotNull
        public final Map<KClass<? extends Record>, String> getRECORD_TYPE_TO_PERMISSION$connect_client_release() {
            return HealthPermission.RECORD_TYPE_TO_PERMISSION;
        }

        @JvmStatic
        @NotNull
        public final String getReadPermission(@NotNull KClass<? extends Record> recordType) {
            Intrinsics.checkNotNullParameter(recordType, "recordType");
            if (getRECORD_TYPE_TO_PERMISSION$connect_client_release().get(recordType) != null) {
                return HealthPermission.READ_PERMISSION_PREFIX + getRECORD_TYPE_TO_PERMISSION$connect_client_release().get(recordType);
            }
            throw new IllegalArgumentException("Given recordType is not valid : " + recordType + ".simpleName");
        }

        @JvmStatic
        @NotNull
        public final String getWritePermission(@NotNull KClass<? extends Record> recordType) {
            Intrinsics.checkNotNullParameter(recordType, "recordType");
            if (getRECORD_TYPE_TO_PERMISSION$connect_client_release().get(recordType) != null) {
                return HealthPermission.WRITE_PERMISSION_PREFIX + ((String) getRECORD_TYPE_TO_PERMISSION$connect_client_release().getOrDefault(recordType, ""));
            }
            throw new IllegalArgumentException("Given recordType is not valid : " + recordType + ".simpleName");
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public HealthPermission(@NotNull KClass<? extends Record> recordType, int i2) {
        Intrinsics.checkNotNullParameter(recordType, "recordType");
        this.recordType = recordType;
        this.accessType = i2;
    }

    @JvmStatic
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final HealthPermission createReadPermissionLegacy(@NotNull KClass<? extends Record> kClass) {
        return INSTANCE.createReadPermissionLegacy(kClass);
    }

    @JvmStatic
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final HealthPermission createWritePermissionLegacy(@NotNull KClass<? extends Record> kClass) {
        return INSTANCE.createWritePermissionLegacy(kClass);
    }

    public static /* synthetic */ void getAccessType$connect_client_release$annotations() {
    }

    @JvmStatic
    @NotNull
    public static final String getReadPermission(@NotNull KClass<? extends Record> kClass) {
        return INSTANCE.getReadPermission(kClass);
    }

    @JvmStatic
    @NotNull
    public static final String getWritePermission(@NotNull KClass<? extends Record> kClass) {
        return INSTANCE.getWritePermission(kClass);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthPermission)) {
            return false;
        }
        HealthPermission healthPermission = (HealthPermission) other;
        return Intrinsics.areEqual(this.recordType, healthPermission.recordType) && this.accessType == healthPermission.accessType;
    }

    /* renamed from: getAccessType$connect_client_release, reason: from getter */
    public final int getAccessType() {
        return this.accessType;
    }

    @NotNull
    public final KClass<? extends Record> getRecordType$connect_client_release() {
        return this.recordType;
    }

    public int hashCode() {
        return (this.recordType.hashCode() * 31) + this.accessType;
    }
}
