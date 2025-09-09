package androidx.health.connect.client.records;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.units.Pressure;
import androidx.health.connect.client.units.PressureKt;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\u0018\u0000 %2\u00020\u0001:\u0005#$%&'BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0096\u0002J\b\u0010\"\u001a\u00020\nH\u0016R\u0017\u0010\t\u001a\u00020\n¢\u0006\u000e\n\u0000\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u000b\u001a\u00020\n¢\u0006\u000e\n\u0000\u0012\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0016\u0010\u0012R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006("}, d2 = {"Landroidx/health/connect/client/records/BloodPressureRecord;", "Landroidx/health/connect/client/records/InstantaneousRecord;", "time", "Ljava/time/Instant;", "zoneOffset", "Ljava/time/ZoneOffset;", BloodPressureRecord.SYSTOLIC_FIELD_NAME, "Landroidx/health/connect/client/units/Pressure;", BloodPressureRecord.DIASTOLIC_FIELD_NAME, "bodyPosition", "", "measurementLocation", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Landroidx/health/connect/client/units/Pressure;Landroidx/health/connect/client/units/Pressure;IILandroidx/health/connect/client/records/metadata/Metadata;)V", "getBodyPosition$annotations", "()V", "getBodyPosition", "()I", "getDiastolic", "()Landroidx/health/connect/client/units/Pressure;", "getMeasurementLocation$annotations", "getMeasurementLocation", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getSystolic", "getTime", "()Ljava/time/Instant;", "getZoneOffset", "()Ljava/time/ZoneOffset;", "equals", "", "other", "", "hashCode", "BodyPosition", "BodyPositions", "Companion", "MeasurementLocation", "MeasurementLocations", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BloodPressureRecord implements InstantaneousRecord {

    @NotNull
    private static final String BLOOD_PRESSURE_NAME = "BloodPressure";

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<Integer, String> BODY_POSITION_INT_TO_STRING_MAP;
    public static final int BODY_POSITION_LYING_DOWN = 3;
    public static final int BODY_POSITION_RECLINING = 4;
    public static final int BODY_POSITION_SITTING_DOWN = 2;
    public static final int BODY_POSITION_STANDING_UP = 1;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<String, Integer> BODY_POSITION_STRING_TO_INT_MAP;
    public static final int BODY_POSITION_UNKNOWN = 0;

    @JvmField
    @NotNull
    public static final AggregateMetric<Pressure> DIASTOLIC_AVG;

    @NotNull
    private static final String DIASTOLIC_FIELD_NAME = "diastolic";

    @JvmField
    @NotNull
    public static final AggregateMetric<Pressure> DIASTOLIC_MAX;

    @JvmField
    @NotNull
    public static final AggregateMetric<Pressure> DIASTOLIC_MIN;

    @NotNull
    private static final Pressure MAX_DIASTOLIC;

    @NotNull
    private static final Pressure MAX_SYSTOLIC;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<Integer, String> MEASUREMENT_LOCATION_INT_TO_STRING_MAP;
    public static final int MEASUREMENT_LOCATION_LEFT_UPPER_ARM = 3;
    public static final int MEASUREMENT_LOCATION_LEFT_WRIST = 1;
    public static final int MEASUREMENT_LOCATION_RIGHT_UPPER_ARM = 4;
    public static final int MEASUREMENT_LOCATION_RIGHT_WRIST = 2;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<String, Integer> MEASUREMENT_LOCATION_STRING_TO_INT_MAP;
    public static final int MEASUREMENT_LOCATION_UNKNOWN = 0;

    @NotNull
    private static final Pressure MIN_DIASTOLIC;

    @NotNull
    private static final Pressure MIN_SYSTOLIC;

    @JvmField
    @NotNull
    public static final AggregateMetric<Pressure> SYSTOLIC_AVG;

    @NotNull
    private static final String SYSTOLIC_FIELD_NAME = "systolic";

    @JvmField
    @NotNull
    public static final AggregateMetric<Pressure> SYSTOLIC_MAX;

    @JvmField
    @NotNull
    public static final AggregateMetric<Pressure> SYSTOLIC_MIN;
    private final int bodyPosition;

    @NotNull
    private final Pressure diastolic;
    private final int measurementLocation;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;

    @NotNull
    private final Pressure systolic;

    @NotNull
    private final Instant time;

    @Nullable
    private final ZoneOffset zoneOffset;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Landroidx/health/connect/client/records/BloodPressureRecord$BodyPosition;", "", "()V", "LYING_DOWN", "", "RECLINING", "SITTING_DOWN", "STANDING_UP", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class BodyPosition {

        @NotNull
        public static final BodyPosition INSTANCE = new BodyPosition();

        @NotNull
        public static final String LYING_DOWN = "lying_down";

        @NotNull
        public static final String RECLINING = "reclining";

        @NotNull
        public static final String SITTING_DOWN = "sitting_down";

        @NotNull
        public static final String STANDING_UP = "standing_up";

        private BodyPosition() {
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/BloodPressureRecord$BodyPositions;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface BodyPositions {
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Landroidx/health/connect/client/records/BloodPressureRecord$MeasurementLocation;", "", "()V", "LEFT_UPPER_ARM", "", "LEFT_WRIST", "RIGHT_UPPER_ARM", "RIGHT_WRIST", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class MeasurementLocation {

        @NotNull
        public static final MeasurementLocation INSTANCE = new MeasurementLocation();

        @NotNull
        public static final String LEFT_UPPER_ARM = "left_upper_arm";

        @NotNull
        public static final String LEFT_WRIST = "left_wrist";

        @NotNull
        public static final String RIGHT_UPPER_ARM = "right_upper_arm";

        @NotNull
        public static final String RIGHT_WRIST = "right_wrist";

        private MeasurementLocation() {
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/BloodPressureRecord$MeasurementLocations;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface MeasurementLocations {
    }

    static {
        Map<String, Integer> mapMapOf = MapsKt.mapOf(TuplesKt.to(MeasurementLocation.LEFT_UPPER_ARM, 3), TuplesKt.to(MeasurementLocation.LEFT_WRIST, 1), TuplesKt.to(MeasurementLocation.RIGHT_UPPER_ARM, 4), TuplesKt.to(MeasurementLocation.RIGHT_WRIST, 2));
        MEASUREMENT_LOCATION_STRING_TO_INT_MAP = mapMapOf;
        MEASUREMENT_LOCATION_INT_TO_STRING_MAP = UtilsKt.reverse(mapMapOf);
        Map<String, Integer> mapMapOf2 = MapsKt.mapOf(TuplesKt.to(BodyPosition.LYING_DOWN, 3), TuplesKt.to(BodyPosition.RECLINING, 4), TuplesKt.to(BodyPosition.SITTING_DOWN, 2), TuplesKt.to(BodyPosition.STANDING_UP, 1));
        BODY_POSITION_STRING_TO_INT_MAP = mapMapOf2;
        BODY_POSITION_INT_TO_STRING_MAP = UtilsKt.reverse(mapMapOf2);
        MIN_SYSTOLIC = PressureKt.getMillimetersOfMercury(9.9E-323d);
        MAX_SYSTOLIC = PressureKt.getMillimetersOfMercury(9.9E-322d);
        MIN_DIASTOLIC = PressureKt.getMillimetersOfMercury(4.9E-323d);
        MAX_DIASTOLIC = PressureKt.getMillimetersOfMercury(8.9E-322d);
        AggregateMetric.Companion companion = AggregateMetric.INSTANCE;
        AggregateMetric.AggregationType aggregationType = AggregateMetric.AggregationType.AVERAGE;
        Pressure.Companion companion2 = Pressure.INSTANCE;
        SYSTOLIC_AVG = companion.doubleMetric$connect_client_release(BLOOD_PRESSURE_NAME, aggregationType, SYSTOLIC_FIELD_NAME, new BloodPressureRecord$Companion$SYSTOLIC_AVG$1(companion2));
        AggregateMetric.AggregationType aggregationType2 = AggregateMetric.AggregationType.MINIMUM;
        SYSTOLIC_MIN = companion.doubleMetric$connect_client_release(BLOOD_PRESSURE_NAME, aggregationType2, SYSTOLIC_FIELD_NAME, new BloodPressureRecord$Companion$SYSTOLIC_MIN$1(companion2));
        AggregateMetric.AggregationType aggregationType3 = AggregateMetric.AggregationType.MAXIMUM;
        SYSTOLIC_MAX = companion.doubleMetric$connect_client_release(BLOOD_PRESSURE_NAME, aggregationType3, SYSTOLIC_FIELD_NAME, new BloodPressureRecord$Companion$SYSTOLIC_MAX$1(companion2));
        DIASTOLIC_AVG = companion.doubleMetric$connect_client_release(BLOOD_PRESSURE_NAME, aggregationType, DIASTOLIC_FIELD_NAME, new BloodPressureRecord$Companion$DIASTOLIC_AVG$1(companion2));
        DIASTOLIC_MIN = companion.doubleMetric$connect_client_release(BLOOD_PRESSURE_NAME, aggregationType2, DIASTOLIC_FIELD_NAME, new BloodPressureRecord$Companion$DIASTOLIC_MIN$1(companion2));
        DIASTOLIC_MAX = companion.doubleMetric$connect_client_release(BLOOD_PRESSURE_NAME, aggregationType3, DIASTOLIC_FIELD_NAME, new BloodPressureRecord$Companion$DIASTOLIC_MAX$1(companion2));
    }

    public BloodPressureRecord(@NotNull Instant time, @Nullable ZoneOffset zoneOffset, @NotNull Pressure systolic, @NotNull Pressure diastolic, int i2, int i3, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(time, "time");
        Intrinsics.checkNotNullParameter(systolic, "systolic");
        Intrinsics.checkNotNullParameter(diastolic, "diastolic");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.time = time;
        this.zoneOffset = zoneOffset;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.bodyPosition = i2;
        this.measurementLocation = i3;
        this.metadata = metadata;
        UtilsKt.requireNotLess(systolic, MIN_SYSTOLIC, SYSTOLIC_FIELD_NAME);
        UtilsKt.requireNotMore(systolic, MAX_SYSTOLIC, SYSTOLIC_FIELD_NAME);
        UtilsKt.requireNotLess(diastolic, MIN_DIASTOLIC, DIASTOLIC_FIELD_NAME);
        UtilsKt.requireNotMore(diastolic, MAX_DIASTOLIC, DIASTOLIC_FIELD_NAME);
    }

    public static /* synthetic */ void getBodyPosition$annotations() {
    }

    public static /* synthetic */ void getMeasurementLocation$annotations() {
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BloodPressureRecord)) {
            return false;
        }
        BloodPressureRecord bloodPressureRecord = (BloodPressureRecord) other;
        return Intrinsics.areEqual(this.systolic, bloodPressureRecord.systolic) && Intrinsics.areEqual(this.diastolic, bloodPressureRecord.diastolic) && this.bodyPosition == bloodPressureRecord.bodyPosition && this.measurementLocation == bloodPressureRecord.measurementLocation && Intrinsics.areEqual(getTime(), bloodPressureRecord.getTime()) && Intrinsics.areEqual(getZoneOffset(), bloodPressureRecord.getZoneOffset()) && Intrinsics.areEqual(getMetadata(), bloodPressureRecord.getMetadata());
    }

    public final int getBodyPosition() {
        return this.bodyPosition;
    }

    @NotNull
    public final Pressure getDiastolic() {
        return this.diastolic;
    }

    public final int getMeasurementLocation() {
        return this.measurementLocation;
    }

    @Override // androidx.health.connect.client.records.Record
    @NotNull
    public androidx.health.connect.client.records.metadata.Metadata getMetadata() {
        return this.metadata;
    }

    @NotNull
    public final Pressure getSystolic() {
        return this.systolic;
    }

    @Override // androidx.health.connect.client.records.InstantaneousRecord
    @NotNull
    public Instant getTime() {
        return this.time;
    }

    @Override // androidx.health.connect.client.records.InstantaneousRecord
    @Nullable
    public ZoneOffset getZoneOffset() {
        return this.zoneOffset;
    }

    public int hashCode() {
        int iHashCode = ((((((((this.systolic.hashCode() * 31) + this.diastolic.hashCode()) * 31) + this.bodyPosition) * 31) + this.measurementLocation) * 31) + getTime().hashCode()) * 31;
        ZoneOffset zoneOffset = getZoneOffset();
        return ((iHashCode + (zoneOffset != null ? zoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ BloodPressureRecord(Instant instant, ZoneOffset zoneOffset, Pressure pressure, Pressure pressure2, int i2, int i3, androidx.health.connect.client.records.metadata.Metadata metadata, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, pressure, pressure2, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? 0 : i3, (i4 & 64) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
