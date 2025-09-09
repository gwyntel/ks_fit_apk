package androidx.health.connect.client.records;

import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.units.Energy;
import androidx.health.connect.client.units.Power;
import androidx.health.connect.client.units.PowerKt;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.time.Instant;
import java.time.ZoneOffset;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001a"}, d2 = {"Landroidx/health/connect/client/records/BasalMetabolicRateRecord;", "Landroidx/health/connect/client/records/InstantaneousRecord;", "time", "Ljava/time/Instant;", "zoneOffset", "Ljava/time/ZoneOffset;", "basalMetabolicRate", "Landroidx/health/connect/client/units/Power;", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Landroidx/health/connect/client/units/Power;Landroidx/health/connect/client/records/metadata/Metadata;)V", "getBasalMetabolicRate", "()Landroidx/health/connect/client/units/Power;", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getTime", "()Ljava/time/Instant;", "getZoneOffset", "()Ljava/time/ZoneOffset;", "equals", "", "other", "", "hashCode", "", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BasalMetabolicRateRecord implements InstantaneousRecord {

    @NotNull
    private final Power basalMetabolicRate;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;

    @NotNull
    private final Instant time;

    @Nullable
    private final ZoneOffset zoneOffset;

    @NotNull
    private static final Power MAX_BASAL_METABLOIC_RATE = PowerKt.getKilocaloriesPerDay(4.9407E-320d);

    @NotNull
    private static final String BASAL_CALORIES_TYPE_NAME = "BasalCaloriesBurned";

    @NotNull
    private static final String ENERGY_FIELD_NAME = "energy";

    @JvmField
    @NotNull
    public static final AggregateMetric<Energy> BASAL_CALORIES_TOTAL = AggregateMetric.INSTANCE.doubleMetric$connect_client_release(BASAL_CALORIES_TYPE_NAME, AggregateMetric.AggregationType.TOTAL, ENERGY_FIELD_NAME, new BasalMetabolicRateRecord$Companion$BASAL_CALORIES_TOTAL$1(Energy.INSTANCE));

    public BasalMetabolicRateRecord(@NotNull Instant time, @Nullable ZoneOffset zoneOffset, @NotNull Power basalMetabolicRate, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(time, "time");
        Intrinsics.checkNotNullParameter(basalMetabolicRate, "basalMetabolicRate");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.time = time;
        this.zoneOffset = zoneOffset;
        this.basalMetabolicRate = basalMetabolicRate;
        this.metadata = metadata;
        UtilsKt.requireNotLess(basalMetabolicRate, basalMetabolicRate.zero$connect_client_release(), "bmr");
        UtilsKt.requireNotMore(basalMetabolicRate, MAX_BASAL_METABLOIC_RATE, "bmr");
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BasalMetabolicRateRecord)) {
            return false;
        }
        BasalMetabolicRateRecord basalMetabolicRateRecord = (BasalMetabolicRateRecord) other;
        return Intrinsics.areEqual(this.basalMetabolicRate, basalMetabolicRateRecord.basalMetabolicRate) && Intrinsics.areEqual(getTime(), basalMetabolicRateRecord.getTime()) && Intrinsics.areEqual(getZoneOffset(), basalMetabolicRateRecord.getZoneOffset()) && Intrinsics.areEqual(getMetadata(), basalMetabolicRateRecord.getMetadata());
    }

    @NotNull
    public final Power getBasalMetabolicRate() {
        return this.basalMetabolicRate;
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

    @Override // androidx.health.connect.client.records.InstantaneousRecord
    @Nullable
    public ZoneOffset getZoneOffset() {
        return this.zoneOffset;
    }

    public int hashCode() {
        int iHashCode = ((this.basalMetabolicRate.hashCode() * 31) + getTime().hashCode()) * 31;
        ZoneOffset zoneOffset = getZoneOffset();
        return ((iHashCode + (zoneOffset != null ? zoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ BasalMetabolicRateRecord(Instant instant, ZoneOffset zoneOffset, Power power, androidx.health.connect.client.records.metadata.Metadata metadata, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, power, (i2 & 8) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
