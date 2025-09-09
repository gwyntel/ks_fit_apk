package androidx.health.connect.client.impl.platform.records;

import androidx.annotation.RequiresApi;
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
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@RequiresApi(api = 34)
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"4\u0010\u0000\u001a\"\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u00060\u0005j\u0002`\u00060\u00040\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"SDK_TO_PLATFORM_RECORD_CLASS", "", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "Ljava/lang/Class;", "Landroid/health/connect/datatypes/Record;", "Landroidx/health/connect/client/impl/platform/records/PlatformRecord;", "getSDK_TO_PLATFORM_RECORD_CLASS", "()Ljava/util/Map;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class RecordMappingsKt {

    @NotNull
    private static final Map<KClass<? extends Record>, Class<? extends android.health.connect.datatypes.Record>> SDK_TO_PLATFORM_RECORD_CLASS = MapsKt.mapOf(TuplesKt.to(Reflection.getOrCreateKotlinClass(ActiveCaloriesBurnedRecord.class), vp.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(BasalBodyTemperatureRecord.class), xp.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(BasalMetabolicRateRecord.class), jq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(BloodGlucoseRecord.class), tq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(BloodPressureRecord.class), uq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(BodyFatRecord.class), vq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(BodyTemperatureRecord.class), wq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(BodyWaterMassRecord.class), xq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(BoneMassRecord.class), yq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(CervicalMucusRecord.class), zq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(CyclingPedalingCadenceRecord.class), gq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(DistanceRecord.class), rq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(ElevationGainedRecord.class), ar.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(ExerciseSessionRecord.class), br.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(FloorsClimbedRecord.class), cr.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(HeartRateRecord.class), dr.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(HeartRateVariabilityRmssdRecord.class), er.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(HeightRecord.class), fr.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(HydrationRecord.class), gr.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(IntermenstrualBleedingRecord.class), wp.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(LeanBodyMassRecord.class), yp.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(MenstruationFlowRecord.class), zp.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(MenstruationPeriodRecord.class), aq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(NutritionRecord.class), bq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(OvulationTestRecord.class), cq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(OxygenSaturationRecord.class), dq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(PowerRecord.class), eq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(RespiratoryRateRecord.class), fq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(RestingHeartRateRecord.class), hq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(SexualActivityRecord.class), iq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(SleepSessionRecord.class), kq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(SpeedRecord.class), lq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(StepsCadenceRecord.class), mq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(StepsRecord.class), nq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class), oq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(Vo2MaxRecord.class), pq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(WeightRecord.class), qq.a()), TuplesKt.to(Reflection.getOrCreateKotlinClass(WheelchairPushesRecord.class), sq.a()));

    @NotNull
    public static final Map<KClass<? extends Record>, Class<? extends android.health.connect.datatypes.Record>> getSDK_TO_PLATFORM_RECORD_CLASS() {
        return SDK_TO_PLATFORM_RECORD_CLASS;
    }
}
