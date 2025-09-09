package androidx.health.connect.client.impl.platform.records;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\bb\u001a$\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001*\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001H\u0002\u001a\f\u0010D\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010E\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010F\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010G\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010H\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010I\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010J\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010K\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010L\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010M\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010N\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010O\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010P\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010Q\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010R\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010S\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010T\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010U\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010V\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010W\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010X\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010Y\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010Z\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010[\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010\\\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010]\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010^\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010_\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010`\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010a\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010b\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010c\u001a\u00020\u0002*\u00020\u0002H\u0000\" \u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\" \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0004\" \u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0004\" \u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0004\" \u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0004\" \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0004\" \u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0004\" \u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0004\" \u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0004\" \u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0004\" \u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0004\" \u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0004\" \u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0004\" \u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0004\" \u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0004\" \u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0004\" \u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0004\" \u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0004\" \u0010'\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0004\" \u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0004\" \u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0004\" \u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0004\" \u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0004\" \u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0004\" \u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0004\" \u00105\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0004\" \u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0004\" \u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\u0004\" \u0010;\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\u0004\" \u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\u0004\" \u0010?\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\u0004\" \u0010A\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0004¨\u0006d"}, d2 = {"PLATFORM_TO_SDK_BLOOD_GLUCOSE_RELATION_TO_MEAL", "", "", "getPLATFORM_TO_SDK_BLOOD_GLUCOSE_RELATION_TO_MEAL", "()Ljava/util/Map;", "PLATFORM_TO_SDK_BLOOD_PRESSURE_BODY_POSITION", "getPLATFORM_TO_SDK_BLOOD_PRESSURE_BODY_POSITION", "PLATFORM_TO_SDK_BLOOD_PRESSURE_MEASUREMENT_LOCATION", "getPLATFORM_TO_SDK_BLOOD_PRESSURE_MEASUREMENT_LOCATION", "PLATFORM_TO_SDK_BODY_TEMPERATURE_MEASUREMENT_LOCATION", "getPLATFORM_TO_SDK_BODY_TEMPERATURE_MEASUREMENT_LOCATION", "PLATFORM_TO_SDK_CERVICAL_MUCUS_APPEARANCE", "getPLATFORM_TO_SDK_CERVICAL_MUCUS_APPEARANCE", "PLATFORM_TO_SDK_CERVICAL_MUCUS_SENSATION", "getPLATFORM_TO_SDK_CERVICAL_MUCUS_SENSATION", "PLATFORM_TO_SDK_EXERCISE_SEGMENT_TYPE", "getPLATFORM_TO_SDK_EXERCISE_SEGMENT_TYPE", "PLATFORM_TO_SDK_EXERCISE_SESSION_TYPE", "getPLATFORM_TO_SDK_EXERCISE_SESSION_TYPE", "PLATFORM_TO_SDK_GLUCOSE_SPECIMEN_SOURCE", "getPLATFORM_TO_SDK_GLUCOSE_SPECIMEN_SOURCE", "PLATFORM_TO_SDK_MEAL_TYPE", "getPLATFORM_TO_SDK_MEAL_TYPE", "PLATFORM_TO_SDK_MENSTRUATION_FLOW_TYPE", "getPLATFORM_TO_SDK_MENSTRUATION_FLOW_TYPE", "PLATFORM_TO_SDK_OVULATION_TEST_RESULT", "getPLATFORM_TO_SDK_OVULATION_TEST_RESULT", "PLATFORM_TO_SDK_RECORDING_METHOD", "getPLATFORM_TO_SDK_RECORDING_METHOD", "PLATFORM_TO_SDK_SEXUAL_ACTIVITY_PROTECTION_USED", "getPLATFORM_TO_SDK_SEXUAL_ACTIVITY_PROTECTION_USED", "PLATFORM_TO_SDK_SLEEP_STAGE_TYPE", "getPLATFORM_TO_SDK_SLEEP_STAGE_TYPE", "PLATFORM_TO_SDK_VO2_MAX_MEASUREMENT_METHOD", "getPLATFORM_TO_SDK_VO2_MAX_MEASUREMENT_METHOD", "SDK_TO_PLATFORM_BLOOD_GLUCOSE_RELATION_TO_MEAL", "getSDK_TO_PLATFORM_BLOOD_GLUCOSE_RELATION_TO_MEAL", "SDK_TO_PLATFORM_BLOOD_GLUCOSE_SPECIMEN_SOURCE", "getSDK_TO_PLATFORM_BLOOD_GLUCOSE_SPECIMEN_SOURCE", "SDK_TO_PLATFORM_BLOOD_PRESSURE_BODY_POSITION", "getSDK_TO_PLATFORM_BLOOD_PRESSURE_BODY_POSITION", "SDK_TO_PLATFORM_BLOOD_PRESSURE_MEASUREMENT_LOCATION", "getSDK_TO_PLATFORM_BLOOD_PRESSURE_MEASUREMENT_LOCATION", "SDK_TO_PLATFORM_BODY_TEMPERATURE_MEASUREMENT_LOCATION", "getSDK_TO_PLATFORM_BODY_TEMPERATURE_MEASUREMENT_LOCATION", "SDK_TO_PLATFORM_CERVICAL_MUCUS_APPEARANCE", "getSDK_TO_PLATFORM_CERVICAL_MUCUS_APPEARANCE", "SDK_TO_PLATFORM_CERVICAL_MUCUS_SENSATION", "getSDK_TO_PLATFORM_CERVICAL_MUCUS_SENSATION", "SDK_TO_PLATFORM_EXERCISE_SEGMENT_TYPE", "getSDK_TO_PLATFORM_EXERCISE_SEGMENT_TYPE", "SDK_TO_PLATFORM_EXERCISE_SESSION_TYPE", "getSDK_TO_PLATFORM_EXERCISE_SESSION_TYPE", "SDK_TO_PLATFORM_MEAL_TYPE", "getSDK_TO_PLATFORM_MEAL_TYPE", "SDK_TO_PLATFORM_MENSTRUATION_FLOW_TYPE", "getSDK_TO_PLATFORM_MENSTRUATION_FLOW_TYPE", "SDK_TO_PLATFORM_OVULATION_TEST_RESULT", "getSDK_TO_PLATFORM_OVULATION_TEST_RESULT", "SDK_TO_PLATFORM_RECORDING_METHOD", "getSDK_TO_PLATFORM_RECORDING_METHOD", "SDK_TO_PLATFORM_SEXUAL_ACTIVITY_PROTECTION_USED", "getSDK_TO_PLATFORM_SEXUAL_ACTIVITY_PROTECTION_USED", "SDK_TO_PLATFORM_SLEEP_STAGE_TYPE", "getSDK_TO_PLATFORM_SLEEP_STAGE_TYPE", "SDK_TO_PLATFORM_VO2_MAX_MEASUREMENT_METHOD", "getSDK_TO_PLATFORM_VO2_MAX_MEASUREMENT_METHOD", "reversed", "toPlatformBloodGlucoseRelationToMeal", "toPlatformBloodGlucoseSpecimenSource", "toPlatformBloodPressureBodyPosition", "toPlatformBloodPressureMeasurementLocation", "toPlatformBodyTemperatureMeasurementLocation", "toPlatformCervicalMucusAppearance", "toPlatformCervicalMucusSensation", "toPlatformExerciseSegmentType", "toPlatformExerciseSessionType", "toPlatformMealType", "toPlatformMenstruationFlow", "toPlatformOvulationTestResult", "toPlatformRecordingMethod", "toPlatformSexualActivityProtectionUsed", "toPlatformSleepStageType", "toPlatformVo2MaxMeasurementMethod", "toSdkBloodGlucoseSpecimenSource", "toSdkBloodPressureBodyPosition", "toSdkBloodPressureMeasurementLocation", "toSdkBodyTemperatureMeasurementLocation", "toSdkCervicalMucusAppearance", "toSdkCervicalMucusSensation", "toSdkExerciseSegmentType", "toSdkExerciseSessionType", "toSdkMealType", "toSdkMenstruationFlow", "toSdkOvulationTestResult", "toSdkProtectionUsed", "toSdkRecordingMethod", "toSdkRelationToMeal", "toSdkSleepStageType", "toSdkVo2MaxMeasurementMethod", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RequiresApi(api = 34)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nIntDefMappings.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IntDefMappings.kt\nandroidx/health/connect/client/impl/platform/records/IntDefMappingsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,663:1\n1179#2,2:664\n1253#2,4:666\n*S KotlinDebug\n*F\n+ 1 IntDefMappings.kt\nandroidx/health/connect/client/impl/platform/records/IntDefMappingsKt\n*L\n661#1:664,2\n661#1:666,4\n*E\n"})
/* loaded from: classes.dex */
public final class IntDefMappingsKt {

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_BLOOD_GLUCOSE_RELATION_TO_MEAL;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_BLOOD_PRESSURE_BODY_POSITION;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_BLOOD_PRESSURE_MEASUREMENT_LOCATION;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_BODY_TEMPERATURE_MEASUREMENT_LOCATION;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_CERVICAL_MUCUS_APPEARANCE;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_CERVICAL_MUCUS_SENSATION;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_EXERCISE_SEGMENT_TYPE;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_EXERCISE_SESSION_TYPE;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_GLUCOSE_SPECIMEN_SOURCE;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_MEAL_TYPE;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_MENSTRUATION_FLOW_TYPE;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_OVULATION_TEST_RESULT;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_RECORDING_METHOD;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_SEXUAL_ACTIVITY_PROTECTION_USED;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_SLEEP_STAGE_TYPE;

    @NotNull
    private static final Map<Integer, Integer> PLATFORM_TO_SDK_VO2_MAX_MEASUREMENT_METHOD;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_BLOOD_GLUCOSE_RELATION_TO_MEAL;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_BLOOD_GLUCOSE_SPECIMEN_SOURCE;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_BLOOD_PRESSURE_BODY_POSITION;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_BLOOD_PRESSURE_MEASUREMENT_LOCATION;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_BODY_TEMPERATURE_MEASUREMENT_LOCATION;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_CERVICAL_MUCUS_APPEARANCE;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_CERVICAL_MUCUS_SENSATION;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_EXERCISE_SEGMENT_TYPE;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_EXERCISE_SESSION_TYPE;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_MEAL_TYPE;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_MENSTRUATION_FLOW_TYPE;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_OVULATION_TEST_RESULT;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_RECORDING_METHOD;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_SEXUAL_ACTIVITY_PROTECTION_USED;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_SLEEP_STAGE_TYPE;

    @NotNull
    private static final Map<Integer, Integer> SDK_TO_PLATFORM_VO2_MAX_MEASUREMENT_METHOD;

    static {
        Map<Integer, Integer> mapMapOf = MapsKt.mapOf(TuplesKt.to(5, 5), TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4), TuplesKt.to(6, 6));
        SDK_TO_PLATFORM_CERVICAL_MUCUS_APPEARANCE = mapMapOf;
        PLATFORM_TO_SDK_CERVICAL_MUCUS_APPEARANCE = reversed(mapMapOf);
        Map<Integer, Integer> mapMapOf2 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4));
        SDK_TO_PLATFORM_BLOOD_PRESSURE_BODY_POSITION = mapMapOf2;
        PLATFORM_TO_SDK_BLOOD_PRESSURE_BODY_POSITION = reversed(mapMapOf2);
        Map<Integer, Integer> mapMapOf3 = MapsKt.mapOf(TuplesKt.to(0, 58), TuplesKt.to(2, 1), TuplesKt.to(4, 2), TuplesKt.to(5, 3), TuplesKt.to(8, 4), TuplesKt.to(9, 5), TuplesKt.to(10, 6), TuplesKt.to(11, 7), TuplesKt.to(13, 8), TuplesKt.to(14, 9), TuplesKt.to(16, 10), TuplesKt.to(25, 60), TuplesKt.to(26, 11), TuplesKt.to(27, 12), TuplesKt.to(28, 13), TuplesKt.to(29, 14), TuplesKt.to(31, 15), TuplesKt.to(32, 16), TuplesKt.to(33, 17), TuplesKt.to(34, 18), TuplesKt.to(35, 19), TuplesKt.to(36, 20), TuplesKt.to(37, 21), TuplesKt.to(38, 22), TuplesKt.to(39, 23), TuplesKt.to(44, 24), TuplesKt.to(46, 25), TuplesKt.to(47, 26), TuplesKt.to(48, 27), TuplesKt.to(50, 28), TuplesKt.to(51, 29), TuplesKt.to(52, 30), TuplesKt.to(53, 31), TuplesKt.to(54, 61), TuplesKt.to(55, 32), TuplesKt.to(56, 33), TuplesKt.to(57, 34), TuplesKt.to(58, 35), TuplesKt.to(59, 36), TuplesKt.to(60, 37), TuplesKt.to(61, 38), TuplesKt.to(62, 39), TuplesKt.to(63, 40), TuplesKt.to(64, 41), TuplesKt.to(65, 42), TuplesKt.to(66, 43), TuplesKt.to(68, 44), TuplesKt.to(69, 59), TuplesKt.to(70, 45), TuplesKt.to(71, 46), TuplesKt.to(72, 47), TuplesKt.to(73, 48), TuplesKt.to(74, 49), TuplesKt.to(75, 50), TuplesKt.to(76, 51), TuplesKt.to(78, 52), TuplesKt.to(79, 53), TuplesKt.to(80, 54), TuplesKt.to(81, 55), TuplesKt.to(82, 56), TuplesKt.to(83, 57));
        SDK_TO_PLATFORM_EXERCISE_SESSION_TYPE = mapMapOf3;
        PLATFORM_TO_SDK_EXERCISE_SESSION_TYPE = reversed(mapMapOf3);
        Map<Integer, Integer> mapMapOf4 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4));
        SDK_TO_PLATFORM_MEAL_TYPE = mapMapOf4;
        PLATFORM_TO_SDK_MEAL_TYPE = reversed(mapMapOf4);
        Map<Integer, Integer> mapMapOf5 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4), TuplesKt.to(5, 5));
        SDK_TO_PLATFORM_VO2_MAX_MEASUREMENT_METHOD = mapMapOf5;
        PLATFORM_TO_SDK_VO2_MAX_MEASUREMENT_METHOD = reversed(mapMapOf5);
        Map<Integer, Integer> mapMapOf6 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3));
        SDK_TO_PLATFORM_MENSTRUATION_FLOW_TYPE = mapMapOf6;
        PLATFORM_TO_SDK_MENSTRUATION_FLOW_TYPE = reversed(mapMapOf6);
        Map<Integer, Integer> mapMapOf7 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4), TuplesKt.to(5, 5), TuplesKt.to(6, 6), TuplesKt.to(7, 7), TuplesKt.to(8, 8), TuplesKt.to(9, 9), TuplesKt.to(10, 10));
        SDK_TO_PLATFORM_BODY_TEMPERATURE_MEASUREMENT_LOCATION = mapMapOf7;
        PLATFORM_TO_SDK_BODY_TEMPERATURE_MEASUREMENT_LOCATION = reversed(mapMapOf7);
        Map<Integer, Integer> mapMapOf8 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4));
        SDK_TO_PLATFORM_BLOOD_PRESSURE_MEASUREMENT_LOCATION = mapMapOf8;
        PLATFORM_TO_SDK_BLOOD_PRESSURE_MEASUREMENT_LOCATION = reversed(mapMapOf8);
        Map<Integer, Integer> mapMapOf9 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(0, 0));
        SDK_TO_PLATFORM_OVULATION_TEST_RESULT = mapMapOf9;
        PLATFORM_TO_SDK_OVULATION_TEST_RESULT = reversed(mapMapOf9);
        Map<Integer, Integer> mapMapOf10 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3));
        SDK_TO_PLATFORM_CERVICAL_MUCUS_SENSATION = mapMapOf10;
        PLATFORM_TO_SDK_CERVICAL_MUCUS_SENSATION = reversed(mapMapOf10);
        Map<Integer, Integer> mapMapOf11 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2));
        SDK_TO_PLATFORM_SEXUAL_ACTIVITY_PROTECTION_USED = mapMapOf11;
        PLATFORM_TO_SDK_SEXUAL_ACTIVITY_PROTECTION_USED = reversed(mapMapOf11);
        Map<Integer, Integer> mapMapOf12 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4), TuplesKt.to(5, 5), TuplesKt.to(6, 6));
        SDK_TO_PLATFORM_BLOOD_GLUCOSE_SPECIMEN_SOURCE = mapMapOf12;
        PLATFORM_TO_SDK_GLUCOSE_SPECIMEN_SOURCE = reversed(mapMapOf12);
        Map<Integer, Integer> mapMapOf13 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4));
        SDK_TO_PLATFORM_BLOOD_GLUCOSE_RELATION_TO_MEAL = mapMapOf13;
        PLATFORM_TO_SDK_BLOOD_GLUCOSE_RELATION_TO_MEAL = reversed(mapMapOf13);
        Map<Integer, Integer> mapMapOf14 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3), TuplesKt.to(4, 4), TuplesKt.to(5, 5), TuplesKt.to(6, 6), TuplesKt.to(7, 7));
        SDK_TO_PLATFORM_SLEEP_STAGE_TYPE = mapMapOf14;
        PLATFORM_TO_SDK_SLEEP_STAGE_TYPE = reversed(mapMapOf14);
        Map<Integer, Integer> mapMapOf15 = MapsKt.mapOf(TuplesKt.to(1, 26), TuplesKt.to(2, 27), TuplesKt.to(3, 28), TuplesKt.to(4, 1), TuplesKt.to(5, 29), TuplesKt.to(6, 2), TuplesKt.to(7, 3), TuplesKt.to(8, 4), TuplesKt.to(9, 30), TuplesKt.to(10, 31), TuplesKt.to(11, 32), TuplesKt.to(12, 33), TuplesKt.to(13, 5), TuplesKt.to(14, 6), TuplesKt.to(15, 7), TuplesKt.to(16, 8), TuplesKt.to(17, 34), TuplesKt.to(18, 9), TuplesKt.to(19, 10), TuplesKt.to(20, 11), TuplesKt.to(21, 12), TuplesKt.to(22, 13), TuplesKt.to(23, 35), TuplesKt.to(24, 62), TuplesKt.to(25, 36), TuplesKt.to(26, 37), TuplesKt.to(27, 38), TuplesKt.to(28, 39), TuplesKt.to(29, 40), TuplesKt.to(30, 41), TuplesKt.to(31, 42), TuplesKt.to(32, 43), TuplesKt.to(33, 44), TuplesKt.to(34, 45), TuplesKt.to(35, 46), TuplesKt.to(36, 47), TuplesKt.to(37, 48), TuplesKt.to(38, 64), TuplesKt.to(39, 67), TuplesKt.to(40, 14), TuplesKt.to(41, 49), TuplesKt.to(42, 50), TuplesKt.to(43, 51), TuplesKt.to(44, 66), TuplesKt.to(45, 15), TuplesKt.to(46, 16), TuplesKt.to(47, 17), TuplesKt.to(48, 52), TuplesKt.to(49, 53), TuplesKt.to(50, 54), TuplesKt.to(51, 55), TuplesKt.to(52, 18), TuplesKt.to(53, 19), TuplesKt.to(54, 20), TuplesKt.to(55, 57), TuplesKt.to(56, 58), TuplesKt.to(57, 59), TuplesKt.to(58, 56), TuplesKt.to(59, 60), TuplesKt.to(60, 21), TuplesKt.to(61, 61), TuplesKt.to(62, 22), TuplesKt.to(63, 23), TuplesKt.to(64, 24), TuplesKt.to(65, 63), TuplesKt.to(66, 25), TuplesKt.to(67, 65));
        SDK_TO_PLATFORM_EXERCISE_SEGMENT_TYPE = mapMapOf15;
        PLATFORM_TO_SDK_EXERCISE_SEGMENT_TYPE = reversed(mapMapOf15);
        Map<Integer, Integer> mapMapOf16 = MapsKt.mapOf(TuplesKt.to(1, 1), TuplesKt.to(2, 2), TuplesKt.to(3, 3));
        SDK_TO_PLATFORM_RECORDING_METHOD = mapMapOf16;
        PLATFORM_TO_SDK_RECORDING_METHOD = reversed(mapMapOf16);
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_BLOOD_GLUCOSE_RELATION_TO_MEAL() {
        return PLATFORM_TO_SDK_BLOOD_GLUCOSE_RELATION_TO_MEAL;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_BLOOD_PRESSURE_BODY_POSITION() {
        return PLATFORM_TO_SDK_BLOOD_PRESSURE_BODY_POSITION;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_BLOOD_PRESSURE_MEASUREMENT_LOCATION() {
        return PLATFORM_TO_SDK_BLOOD_PRESSURE_MEASUREMENT_LOCATION;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_BODY_TEMPERATURE_MEASUREMENT_LOCATION() {
        return PLATFORM_TO_SDK_BODY_TEMPERATURE_MEASUREMENT_LOCATION;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_CERVICAL_MUCUS_APPEARANCE() {
        return PLATFORM_TO_SDK_CERVICAL_MUCUS_APPEARANCE;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_CERVICAL_MUCUS_SENSATION() {
        return PLATFORM_TO_SDK_CERVICAL_MUCUS_SENSATION;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_EXERCISE_SEGMENT_TYPE() {
        return PLATFORM_TO_SDK_EXERCISE_SEGMENT_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_EXERCISE_SESSION_TYPE() {
        return PLATFORM_TO_SDK_EXERCISE_SESSION_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_GLUCOSE_SPECIMEN_SOURCE() {
        return PLATFORM_TO_SDK_GLUCOSE_SPECIMEN_SOURCE;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_MEAL_TYPE() {
        return PLATFORM_TO_SDK_MEAL_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_MENSTRUATION_FLOW_TYPE() {
        return PLATFORM_TO_SDK_MENSTRUATION_FLOW_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_OVULATION_TEST_RESULT() {
        return PLATFORM_TO_SDK_OVULATION_TEST_RESULT;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_RECORDING_METHOD() {
        return PLATFORM_TO_SDK_RECORDING_METHOD;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_SEXUAL_ACTIVITY_PROTECTION_USED() {
        return PLATFORM_TO_SDK_SEXUAL_ACTIVITY_PROTECTION_USED;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_SLEEP_STAGE_TYPE() {
        return PLATFORM_TO_SDK_SLEEP_STAGE_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getPLATFORM_TO_SDK_VO2_MAX_MEASUREMENT_METHOD() {
        return PLATFORM_TO_SDK_VO2_MAX_MEASUREMENT_METHOD;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_BLOOD_GLUCOSE_RELATION_TO_MEAL() {
        return SDK_TO_PLATFORM_BLOOD_GLUCOSE_RELATION_TO_MEAL;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_BLOOD_GLUCOSE_SPECIMEN_SOURCE() {
        return SDK_TO_PLATFORM_BLOOD_GLUCOSE_SPECIMEN_SOURCE;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_BLOOD_PRESSURE_BODY_POSITION() {
        return SDK_TO_PLATFORM_BLOOD_PRESSURE_BODY_POSITION;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_BLOOD_PRESSURE_MEASUREMENT_LOCATION() {
        return SDK_TO_PLATFORM_BLOOD_PRESSURE_MEASUREMENT_LOCATION;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_BODY_TEMPERATURE_MEASUREMENT_LOCATION() {
        return SDK_TO_PLATFORM_BODY_TEMPERATURE_MEASUREMENT_LOCATION;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_CERVICAL_MUCUS_APPEARANCE() {
        return SDK_TO_PLATFORM_CERVICAL_MUCUS_APPEARANCE;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_CERVICAL_MUCUS_SENSATION() {
        return SDK_TO_PLATFORM_CERVICAL_MUCUS_SENSATION;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_EXERCISE_SEGMENT_TYPE() {
        return SDK_TO_PLATFORM_EXERCISE_SEGMENT_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_EXERCISE_SESSION_TYPE() {
        return SDK_TO_PLATFORM_EXERCISE_SESSION_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_MEAL_TYPE() {
        return SDK_TO_PLATFORM_MEAL_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_MENSTRUATION_FLOW_TYPE() {
        return SDK_TO_PLATFORM_MENSTRUATION_FLOW_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_OVULATION_TEST_RESULT() {
        return SDK_TO_PLATFORM_OVULATION_TEST_RESULT;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_RECORDING_METHOD() {
        return SDK_TO_PLATFORM_RECORDING_METHOD;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_SEXUAL_ACTIVITY_PROTECTION_USED() {
        return SDK_TO_PLATFORM_SEXUAL_ACTIVITY_PROTECTION_USED;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_SLEEP_STAGE_TYPE() {
        return SDK_TO_PLATFORM_SLEEP_STAGE_TYPE;
    }

    @NotNull
    public static final Map<Integer, Integer> getSDK_TO_PLATFORM_VO2_MAX_MEASUREMENT_METHOD() {
        return SDK_TO_PLATFORM_VO2_MAX_MEASUREMENT_METHOD;
    }

    private static final Map<Integer, Integer> reversed(Map<Integer, Integer> map) {
        Set<Map.Entry<Integer, Integer>> setEntrySet = map.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(Integer.valueOf(((Number) entry.getValue()).intValue()), Integer.valueOf(((Number) entry.getKey()).intValue()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }

    public static final int toPlatformBloodGlucoseRelationToMeal(int i2) {
        Integer num = SDK_TO_PLATFORM_BLOOD_GLUCOSE_RELATION_TO_MEAL.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformBloodGlucoseSpecimenSource(int i2) {
        Integer num = SDK_TO_PLATFORM_BLOOD_GLUCOSE_SPECIMEN_SOURCE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformBloodPressureBodyPosition(int i2) {
        Integer num = SDK_TO_PLATFORM_BLOOD_PRESSURE_BODY_POSITION.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformBloodPressureMeasurementLocation(int i2) {
        Integer num = SDK_TO_PLATFORM_BLOOD_PRESSURE_MEASUREMENT_LOCATION.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformBodyTemperatureMeasurementLocation(int i2) {
        Integer num = SDK_TO_PLATFORM_BODY_TEMPERATURE_MEASUREMENT_LOCATION.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformCervicalMucusAppearance(int i2) {
        Integer num = SDK_TO_PLATFORM_CERVICAL_MUCUS_APPEARANCE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformCervicalMucusSensation(int i2) {
        Integer num = SDK_TO_PLATFORM_CERVICAL_MUCUS_SENSATION.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformExerciseSegmentType(int i2) {
        Integer num = SDK_TO_PLATFORM_EXERCISE_SEGMENT_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformExerciseSessionType(int i2) {
        Integer num = SDK_TO_PLATFORM_EXERCISE_SESSION_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformMealType(int i2) {
        Integer num = SDK_TO_PLATFORM_MEAL_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformMenstruationFlow(int i2) {
        Integer num = SDK_TO_PLATFORM_MENSTRUATION_FLOW_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformOvulationTestResult(int i2) {
        Integer num = SDK_TO_PLATFORM_OVULATION_TEST_RESULT.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformRecordingMethod(int i2) {
        Integer num = SDK_TO_PLATFORM_RECORDING_METHOD.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformSexualActivityProtectionUsed(int i2) {
        Integer num = SDK_TO_PLATFORM_SEXUAL_ACTIVITY_PROTECTION_USED.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformSleepStageType(int i2) {
        Integer num = SDK_TO_PLATFORM_SLEEP_STAGE_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toPlatformVo2MaxMeasurementMethod(int i2) {
        Integer num = SDK_TO_PLATFORM_VO2_MAX_MEASUREMENT_METHOD.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkBloodGlucoseSpecimenSource(int i2) {
        Integer num = PLATFORM_TO_SDK_GLUCOSE_SPECIMEN_SOURCE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkBloodPressureBodyPosition(int i2) {
        Integer num = PLATFORM_TO_SDK_BLOOD_PRESSURE_BODY_POSITION.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkBloodPressureMeasurementLocation(int i2) {
        Integer num = PLATFORM_TO_SDK_BLOOD_PRESSURE_MEASUREMENT_LOCATION.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkBodyTemperatureMeasurementLocation(int i2) {
        Integer num = PLATFORM_TO_SDK_BODY_TEMPERATURE_MEASUREMENT_LOCATION.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkCervicalMucusAppearance(int i2) {
        Integer num = PLATFORM_TO_SDK_CERVICAL_MUCUS_APPEARANCE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkCervicalMucusSensation(int i2) {
        Integer num = PLATFORM_TO_SDK_CERVICAL_MUCUS_SENSATION.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkExerciseSegmentType(int i2) {
        Integer num = PLATFORM_TO_SDK_EXERCISE_SEGMENT_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkExerciseSessionType(int i2) {
        Integer num = PLATFORM_TO_SDK_EXERCISE_SESSION_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkMealType(int i2) {
        Integer num = PLATFORM_TO_SDK_MEAL_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkMenstruationFlow(int i2) {
        Integer num = PLATFORM_TO_SDK_MENSTRUATION_FLOW_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkOvulationTestResult(int i2) {
        Integer num = PLATFORM_TO_SDK_OVULATION_TEST_RESULT.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkProtectionUsed(int i2) {
        Integer num = PLATFORM_TO_SDK_SEXUAL_ACTIVITY_PROTECTION_USED.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkRecordingMethod(int i2) {
        Integer num = PLATFORM_TO_SDK_RECORDING_METHOD.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkRelationToMeal(int i2) {
        Integer num = PLATFORM_TO_SDK_BLOOD_GLUCOSE_RELATION_TO_MEAL.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkSleepStageType(int i2) {
        Integer num = PLATFORM_TO_SDK_SLEEP_STAGE_TYPE.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final int toSdkVo2MaxMeasurementMethod(int i2) {
        Integer num = PLATFORM_TO_SDK_VO2_MAX_MEASUREMENT_METHOD.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }
}
