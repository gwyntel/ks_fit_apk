package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.AggregationType;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord;
import androidx.health.connect.client.records.BasalMetabolicRateRecord;
import androidx.health.connect.client.records.DistanceRecord;
import androidx.health.connect.client.records.ElevationGainedRecord;
import androidx.health.connect.client.records.ExerciseSessionRecord;
import androidx.health.connect.client.records.FloorsClimbedRecord;
import androidx.health.connect.client.records.HeartRateRecord;
import androidx.health.connect.client.records.HeightRecord;
import androidx.health.connect.client.records.HydrationRecord;
import androidx.health.connect.client.records.NutritionRecord;
import androidx.health.connect.client.records.PowerRecord;
import androidx.health.connect.client.records.RestingHeartRateRecord;
import androidx.health.connect.client.records.SleepSessionRecord;
import androidx.health.connect.client.records.StepsRecord;
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord;
import androidx.health.connect.client.records.WeightRecord;
import androidx.health.connect.client.records.WheelchairPushesRecord;
import androidx.health.connect.client.units.Energy;
import androidx.health.connect.client.units.Length;
import androidx.health.connect.client.units.Mass;
import androidx.health.connect.client.units.Power;
import androidx.health.connect.client.units.Volume;
import java.time.Duration;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import org.jetbrains.annotations.NotNull;

@RequiresApi(api = 34)
@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\",\u0010\u0000\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\",\u0010\u0007\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006\",\u0010\u000b\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006\",\u0010\u000f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006\",\u0010\u0013\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006\",\u0010\u0015\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006\",\u0010\u0019\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006\",\u0010\u001b\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006\",\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006¨\u0006#"}, d2 = {"DOUBLE_AGGREGATION_METRIC_TYPE_MAP", "", "Landroidx/health/connect/client/aggregate/AggregateMetric;", "", "Landroid/health/connect/datatypes/AggregationType;", "getDOUBLE_AGGREGATION_METRIC_TYPE_MAP", "()Ljava/util/Map;", "DURATION_AGGREGATION_METRIC_TYPE_MAP", "Ljava/time/Duration;", "", "getDURATION_AGGREGATION_METRIC_TYPE_MAP", "ENERGY_AGGREGATION_METRIC_TYPE_MAP", "Landroidx/health/connect/client/units/Energy;", "Landroid/health/connect/datatypes/units/Energy;", "getENERGY_AGGREGATION_METRIC_TYPE_MAP", "GRAMS_AGGREGATION_METRIC_TYPE_MAP", "Landroidx/health/connect/client/units/Mass;", "Landroid/health/connect/datatypes/units/Mass;", "getGRAMS_AGGREGATION_METRIC_TYPE_MAP", "KILOGRAMS_AGGREGATION_METRIC_TYPE_MAP", "getKILOGRAMS_AGGREGATION_METRIC_TYPE_MAP", "LENGTH_AGGREGATION_METRIC_TYPE_MAP", "Landroidx/health/connect/client/units/Length;", "Landroid/health/connect/datatypes/units/Length;", "getLENGTH_AGGREGATION_METRIC_TYPE_MAP", "LONG_AGGREGATION_METRIC_TYPE_MAP", "getLONG_AGGREGATION_METRIC_TYPE_MAP", "POWER_AGGREGATION_METRIC_TYPE_MAP", "Landroidx/health/connect/client/units/Power;", "Landroid/health/connect/datatypes/units/Power;", "getPOWER_AGGREGATION_METRIC_TYPE_MAP", "VOLUME_AGGREGATION_METRIC_TYPE_MAP", "Landroidx/health/connect/client/units/Volume;", "Landroid/health/connect/datatypes/units/Volume;", "getVOLUME_AGGREGATION_METRIC_TYPE_MAP", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class AggregationMappingsKt {

    @NotNull
    private static final Map<AggregateMetric<Double>, AggregationType<Double>> DOUBLE_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(FloorsClimbedRecord.FLOORS_CLIMBED_TOTAL, android.health.connect.datatypes.FloorsClimbedRecord.FLOORS_CLIMBED_TOTAL));

    @NotNull
    private static final Map<AggregateMetric<Duration>, AggregationType<Long>> DURATION_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(ExerciseSessionRecord.EXERCISE_DURATION_TOTAL, android.health.connect.datatypes.ExerciseSessionRecord.EXERCISE_DURATION_TOTAL), TuplesKt.to(SleepSessionRecord.SLEEP_DURATION_TOTAL, android.health.connect.datatypes.SleepSessionRecord.SLEEP_DURATION_TOTAL));

    @NotNull
    private static final Map<AggregateMetric<Energy>, AggregationType<android.health.connect.datatypes.units.Energy>> ENERGY_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(ActiveCaloriesBurnedRecord.ACTIVE_CALORIES_TOTAL, android.health.connect.datatypes.ActiveCaloriesBurnedRecord.ACTIVE_CALORIES_TOTAL), TuplesKt.to(BasalMetabolicRateRecord.BASAL_CALORIES_TOTAL, android.health.connect.datatypes.BasalMetabolicRateRecord.BASAL_CALORIES_TOTAL), TuplesKt.to(NutritionRecord.ENERGY_TOTAL, android.health.connect.datatypes.NutritionRecord.ENERGY_TOTAL), TuplesKt.to(NutritionRecord.ENERGY_FROM_FAT_TOTAL, android.health.connect.datatypes.NutritionRecord.ENERGY_FROM_FAT_TOTAL), TuplesKt.to(TotalCaloriesBurnedRecord.ENERGY_TOTAL, android.health.connect.datatypes.TotalCaloriesBurnedRecord.ENERGY_TOTAL));

    @NotNull
    private static final Map<AggregateMetric<Length>, AggregationType<android.health.connect.datatypes.units.Length>> LENGTH_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(DistanceRecord.DISTANCE_TOTAL, android.health.connect.datatypes.DistanceRecord.DISTANCE_TOTAL), TuplesKt.to(ElevationGainedRecord.ELEVATION_GAINED_TOTAL, android.health.connect.datatypes.ElevationGainedRecord.ELEVATION_GAINED_TOTAL), TuplesKt.to(HeightRecord.HEIGHT_AVG, android.health.connect.datatypes.HeightRecord.HEIGHT_AVG), TuplesKt.to(HeightRecord.HEIGHT_MIN, android.health.connect.datatypes.HeightRecord.HEIGHT_MIN), TuplesKt.to(HeightRecord.HEIGHT_MAX, android.health.connect.datatypes.HeightRecord.HEIGHT_MAX));

    @NotNull
    private static final Map<AggregateMetric<Long>, AggregationType<Long>> LONG_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(HeartRateRecord.BPM_AVG, android.health.connect.datatypes.HeartRateRecord.BPM_AVG), TuplesKt.to(HeartRateRecord.BPM_MIN, android.health.connect.datatypes.HeartRateRecord.BPM_MIN), TuplesKt.to(HeartRateRecord.BPM_MAX, android.health.connect.datatypes.HeartRateRecord.BPM_MAX), TuplesKt.to(HeartRateRecord.MEASUREMENTS_COUNT, android.health.connect.datatypes.HeartRateRecord.HEART_MEASUREMENTS_COUNT), TuplesKt.to(RestingHeartRateRecord.BPM_AVG, android.health.connect.datatypes.RestingHeartRateRecord.BPM_AVG), TuplesKt.to(RestingHeartRateRecord.BPM_MIN, android.health.connect.datatypes.RestingHeartRateRecord.BPM_MIN), TuplesKt.to(RestingHeartRateRecord.BPM_MAX, android.health.connect.datatypes.RestingHeartRateRecord.BPM_MAX), TuplesKt.to(StepsRecord.COUNT_TOTAL, android.health.connect.datatypes.StepsRecord.STEPS_COUNT_TOTAL), TuplesKt.to(WheelchairPushesRecord.COUNT_TOTAL, android.health.connect.datatypes.WheelchairPushesRecord.WHEEL_CHAIR_PUSHES_COUNT_TOTAL));

    @NotNull
    private static final Map<AggregateMetric<Mass>, AggregationType<android.health.connect.datatypes.units.Mass>> GRAMS_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(NutritionRecord.BIOTIN_TOTAL, android.health.connect.datatypes.NutritionRecord.BIOTIN_TOTAL), TuplesKt.to(NutritionRecord.CAFFEINE_TOTAL, android.health.connect.datatypes.NutritionRecord.CAFFEINE_TOTAL), TuplesKt.to(NutritionRecord.CALCIUM_TOTAL, android.health.connect.datatypes.NutritionRecord.CALCIUM_TOTAL), TuplesKt.to(NutritionRecord.CHLORIDE_TOTAL, android.health.connect.datatypes.NutritionRecord.CHLORIDE_TOTAL), TuplesKt.to(NutritionRecord.CHOLESTEROL_TOTAL, android.health.connect.datatypes.NutritionRecord.CHOLESTEROL_TOTAL), TuplesKt.to(NutritionRecord.CHROMIUM_TOTAL, android.health.connect.datatypes.NutritionRecord.CHROMIUM_TOTAL), TuplesKt.to(NutritionRecord.COPPER_TOTAL, android.health.connect.datatypes.NutritionRecord.COPPER_TOTAL), TuplesKt.to(NutritionRecord.DIETARY_FIBER_TOTAL, android.health.connect.datatypes.NutritionRecord.DIETARY_FIBER_TOTAL), TuplesKt.to(NutritionRecord.FOLATE_TOTAL, android.health.connect.datatypes.NutritionRecord.FOLATE_TOTAL), TuplesKt.to(NutritionRecord.FOLIC_ACID_TOTAL, android.health.connect.datatypes.NutritionRecord.FOLIC_ACID_TOTAL), TuplesKt.to(NutritionRecord.IODINE_TOTAL, android.health.connect.datatypes.NutritionRecord.IODINE_TOTAL), TuplesKt.to(NutritionRecord.IRON_TOTAL, android.health.connect.datatypes.NutritionRecord.IRON_TOTAL), TuplesKt.to(NutritionRecord.MAGNESIUM_TOTAL, android.health.connect.datatypes.NutritionRecord.MAGNESIUM_TOTAL), TuplesKt.to(NutritionRecord.MANGANESE_TOTAL, android.health.connect.datatypes.NutritionRecord.MANGANESE_TOTAL), TuplesKt.to(NutritionRecord.MOLYBDENUM_TOTAL, android.health.connect.datatypes.NutritionRecord.MOLYBDENUM_TOTAL), TuplesKt.to(NutritionRecord.MONOUNSATURATED_FAT_TOTAL, android.health.connect.datatypes.NutritionRecord.MONOUNSATURATED_FAT_TOTAL), TuplesKt.to(NutritionRecord.NIACIN_TOTAL, android.health.connect.datatypes.NutritionRecord.NIACIN_TOTAL), TuplesKt.to(NutritionRecord.PANTOTHENIC_ACID_TOTAL, android.health.connect.datatypes.NutritionRecord.PANTOTHENIC_ACID_TOTAL), TuplesKt.to(NutritionRecord.PHOSPHORUS_TOTAL, android.health.connect.datatypes.NutritionRecord.PHOSPHORUS_TOTAL), TuplesKt.to(NutritionRecord.POLYUNSATURATED_FAT_TOTAL, android.health.connect.datatypes.NutritionRecord.POLYUNSATURATED_FAT_TOTAL), TuplesKt.to(NutritionRecord.POTASSIUM_TOTAL, android.health.connect.datatypes.NutritionRecord.POTASSIUM_TOTAL), TuplesKt.to(NutritionRecord.PROTEIN_TOTAL, android.health.connect.datatypes.NutritionRecord.PROTEIN_TOTAL), TuplesKt.to(NutritionRecord.RIBOFLAVIN_TOTAL, android.health.connect.datatypes.NutritionRecord.RIBOFLAVIN_TOTAL), TuplesKt.to(NutritionRecord.SATURATED_FAT_TOTAL, android.health.connect.datatypes.NutritionRecord.SATURATED_FAT_TOTAL), TuplesKt.to(NutritionRecord.SELENIUM_TOTAL, android.health.connect.datatypes.NutritionRecord.SELENIUM_TOTAL), TuplesKt.to(NutritionRecord.SODIUM_TOTAL, android.health.connect.datatypes.NutritionRecord.SODIUM_TOTAL), TuplesKt.to(NutritionRecord.SUGAR_TOTAL, android.health.connect.datatypes.NutritionRecord.SUGAR_TOTAL), TuplesKt.to(NutritionRecord.THIAMIN_TOTAL, android.health.connect.datatypes.NutritionRecord.THIAMIN_TOTAL), TuplesKt.to(NutritionRecord.TOTAL_CARBOHYDRATE_TOTAL, android.health.connect.datatypes.NutritionRecord.TOTAL_CARBOHYDRATE_TOTAL), TuplesKt.to(NutritionRecord.TOTAL_FAT_TOTAL, android.health.connect.datatypes.NutritionRecord.TOTAL_FAT_TOTAL), TuplesKt.to(NutritionRecord.UNSATURATED_FAT_TOTAL, android.health.connect.datatypes.NutritionRecord.UNSATURATED_FAT_TOTAL), TuplesKt.to(NutritionRecord.VITAMIN_A_TOTAL, android.health.connect.datatypes.NutritionRecord.VITAMIN_A_TOTAL), TuplesKt.to(NutritionRecord.VITAMIN_B12_TOTAL, android.health.connect.datatypes.NutritionRecord.VITAMIN_B12_TOTAL), TuplesKt.to(NutritionRecord.VITAMIN_B6_TOTAL, android.health.connect.datatypes.NutritionRecord.VITAMIN_B6_TOTAL), TuplesKt.to(NutritionRecord.VITAMIN_C_TOTAL, android.health.connect.datatypes.NutritionRecord.VITAMIN_C_TOTAL), TuplesKt.to(NutritionRecord.VITAMIN_D_TOTAL, android.health.connect.datatypes.NutritionRecord.VITAMIN_D_TOTAL), TuplesKt.to(NutritionRecord.VITAMIN_E_TOTAL, android.health.connect.datatypes.NutritionRecord.VITAMIN_E_TOTAL), TuplesKt.to(NutritionRecord.VITAMIN_K_TOTAL, android.health.connect.datatypes.NutritionRecord.VITAMIN_K_TOTAL), TuplesKt.to(NutritionRecord.ZINC_TOTAL, android.health.connect.datatypes.NutritionRecord.ZINC_TOTAL));

    @NotNull
    private static final Map<AggregateMetric<Mass>, AggregationType<android.health.connect.datatypes.units.Mass>> KILOGRAMS_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(WeightRecord.WEIGHT_AVG, android.health.connect.datatypes.WeightRecord.WEIGHT_AVG), TuplesKt.to(WeightRecord.WEIGHT_MIN, android.health.connect.datatypes.WeightRecord.WEIGHT_MIN), TuplesKt.to(WeightRecord.WEIGHT_MAX, android.health.connect.datatypes.WeightRecord.WEIGHT_MAX));

    @NotNull
    private static final Map<AggregateMetric<Power>, AggregationType<android.health.connect.datatypes.units.Power>> POWER_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(PowerRecord.POWER_AVG, android.health.connect.datatypes.PowerRecord.POWER_AVG), TuplesKt.to(PowerRecord.POWER_MAX, android.health.connect.datatypes.PowerRecord.POWER_MAX), TuplesKt.to(PowerRecord.POWER_MIN, android.health.connect.datatypes.PowerRecord.POWER_MIN));

    @NotNull
    private static final Map<AggregateMetric<Volume>, AggregationType<android.health.connect.datatypes.units.Volume>> VOLUME_AGGREGATION_METRIC_TYPE_MAP = MapsKt.mapOf(TuplesKt.to(HydrationRecord.VOLUME_TOTAL, android.health.connect.datatypes.HydrationRecord.VOLUME_TOTAL));

    @NotNull
    public static final Map<AggregateMetric<Double>, AggregationType<Double>> getDOUBLE_AGGREGATION_METRIC_TYPE_MAP() {
        return DOUBLE_AGGREGATION_METRIC_TYPE_MAP;
    }

    @NotNull
    public static final Map<AggregateMetric<Duration>, AggregationType<Long>> getDURATION_AGGREGATION_METRIC_TYPE_MAP() {
        return DURATION_AGGREGATION_METRIC_TYPE_MAP;
    }

    @NotNull
    public static final Map<AggregateMetric<Energy>, AggregationType<android.health.connect.datatypes.units.Energy>> getENERGY_AGGREGATION_METRIC_TYPE_MAP() {
        return ENERGY_AGGREGATION_METRIC_TYPE_MAP;
    }

    @NotNull
    public static final Map<AggregateMetric<Mass>, AggregationType<android.health.connect.datatypes.units.Mass>> getGRAMS_AGGREGATION_METRIC_TYPE_MAP() {
        return GRAMS_AGGREGATION_METRIC_TYPE_MAP;
    }

    @NotNull
    public static final Map<AggregateMetric<Mass>, AggregationType<android.health.connect.datatypes.units.Mass>> getKILOGRAMS_AGGREGATION_METRIC_TYPE_MAP() {
        return KILOGRAMS_AGGREGATION_METRIC_TYPE_MAP;
    }

    @NotNull
    public static final Map<AggregateMetric<Length>, AggregationType<android.health.connect.datatypes.units.Length>> getLENGTH_AGGREGATION_METRIC_TYPE_MAP() {
        return LENGTH_AGGREGATION_METRIC_TYPE_MAP;
    }

    @NotNull
    public static final Map<AggregateMetric<Long>, AggregationType<Long>> getLONG_AGGREGATION_METRIC_TYPE_MAP() {
        return LONG_AGGREGATION_METRIC_TYPE_MAP;
    }

    @NotNull
    public static final Map<AggregateMetric<Power>, AggregationType<android.health.connect.datatypes.units.Power>> getPOWER_AGGREGATION_METRIC_TYPE_MAP() {
        return POWER_AGGREGATION_METRIC_TYPE_MAP;
    }

    @NotNull
    public static final Map<AggregateMetric<Volume>, AggregationType<android.health.connect.datatypes.units.Volume>> getVOLUME_AGGREGATION_METRIC_TYPE_MAP() {
        return VOLUME_AGGREGATION_METRIC_TYPE_MAP;
    }
}
