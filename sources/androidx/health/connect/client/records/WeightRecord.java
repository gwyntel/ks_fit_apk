package androidx.health.connect.client.records;

import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.units.Mass;
import androidx.health.connect.client.units.MassKt;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.time.Instant;
import java.time.ZoneOffset;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001a"}, d2 = {"Landroidx/health/connect/client/records/WeightRecord;", "Landroidx/health/connect/client/records/InstantaneousRecord;", "time", "Ljava/time/Instant;", "zoneOffset", "Ljava/time/ZoneOffset;", WeightRecord.WEIGHT_FIELD, "Landroidx/health/connect/client/units/Mass;", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/records/metadata/Metadata;)V", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getTime", "()Ljava/time/Instant;", "getWeight", "()Landroidx/health/connect/client/units/Mass;", "getZoneOffset", "()Ljava/time/ZoneOffset;", "equals", "", "other", "", "hashCode", "", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class WeightRecord implements InstantaneousRecord {

    @NotNull
    private static final Mass MAX_WEIGHT = MassKt.getKilograms(4.94E-321d);

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> WEIGHT_AVG;

    @NotNull
    private static final String WEIGHT_FIELD = "weight";

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> WEIGHT_MAX;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> WEIGHT_MIN;

    @NotNull
    private static final String WEIGHT_NAME = "Weight";

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;

    @NotNull
    private final Instant time;

    @NotNull
    private final Mass weight;

    @Nullable
    private final ZoneOffset zoneOffset;

    static {
        AggregateMetric.Companion companion = AggregateMetric.INSTANCE;
        AggregateMetric.AggregationType aggregationType = AggregateMetric.AggregationType.AVERAGE;
        Mass.Companion companion2 = Mass.INSTANCE;
        WEIGHT_AVG = companion.doubleMetric$connect_client_release(WEIGHT_NAME, aggregationType, WEIGHT_FIELD, new WeightRecord$Companion$WEIGHT_AVG$1(companion2));
        WEIGHT_MIN = companion.doubleMetric$connect_client_release(WEIGHT_NAME, AggregateMetric.AggregationType.MINIMUM, WEIGHT_FIELD, new WeightRecord$Companion$WEIGHT_MIN$1(companion2));
        WEIGHT_MAX = companion.doubleMetric$connect_client_release(WEIGHT_NAME, AggregateMetric.AggregationType.MAXIMUM, WEIGHT_FIELD, new WeightRecord$Companion$WEIGHT_MAX$1(companion2));
    }

    public WeightRecord(@NotNull Instant time, @Nullable ZoneOffset zoneOffset, @NotNull Mass weight, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(time, "time");
        Intrinsics.checkNotNullParameter(weight, "weight");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.time = time;
        this.zoneOffset = zoneOffset;
        this.weight = weight;
        this.metadata = metadata;
        UtilsKt.requireNotLess(weight, weight.zero$connect_client_release(), WEIGHT_FIELD);
        UtilsKt.requireNotMore(weight, MAX_WEIGHT, WEIGHT_FIELD);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WeightRecord)) {
            return false;
        }
        WeightRecord weightRecord = (WeightRecord) other;
        return Intrinsics.areEqual(this.weight, weightRecord.weight) && Intrinsics.areEqual(getTime(), weightRecord.getTime()) && Intrinsics.areEqual(getZoneOffset(), weightRecord.getZoneOffset()) && Intrinsics.areEqual(getMetadata(), weightRecord.getMetadata());
    }

    @Override // androidx.health.connect.client.records.Record
    @NotNull
    public androidx.health.connect.client.records.metadata.Metadata getMetadata() {
        return this.metadata;
    }

    @Override // androidx.health.connect.client.records.InstantaneousRecord
    @NotNull
    public Instant getTime() {
        return this.time;
    }

    @NotNull
    public final Mass getWeight() {
        return this.weight;
    }

    @Override // androidx.health.connect.client.records.InstantaneousRecord
    @Nullable
    public ZoneOffset getZoneOffset() {
        return this.zoneOffset;
    }

    public int hashCode() {
        int iHashCode = ((this.weight.hashCode() * 31) + getTime().hashCode()) * 31;
        ZoneOffset zoneOffset = getZoneOffset();
        return ((iHashCode + (zoneOffset != null ? zoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ WeightRecord(Instant instant, ZoneOffset zoneOffset, Mass mass, androidx.health.connect.client.records.metadata.Metadata metadata, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, mass, (i2 & 8) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
