package androidx.health.connect.client.records;

import androidx.annotation.FloatRange;
import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u001e2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u001e\u001fBA\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0096\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0014\u0010\u0007\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011¨\u0006 "}, d2 = {"Landroidx/health/connect/client/records/CyclingPedalingCadenceRecord;", "Landroidx/health/connect/client/records/SeriesRecord;", "Landroidx/health/connect/client/records/CyclingPedalingCadenceRecord$Sample;", "startTime", "Ljava/time/Instant;", "startZoneOffset", "Ljava/time/ZoneOffset;", AUserTrack.UTKEY_END_TIME, "endZoneOffset", "samples", "", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Ljava/time/Instant;Ljava/time/ZoneOffset;Ljava/util/List;Landroidx/health/connect/client/records/metadata/Metadata;)V", "getEndTime", "()Ljava/time/Instant;", "getEndZoneOffset", "()Ljava/time/ZoneOffset;", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getSamples", "()Ljava/util/List;", "getStartTime", "getStartZoneOffset", "equals", "", "other", "", "hashCode", "", "Companion", "Sample", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCyclingPedalingCadenceRecord.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CyclingPedalingCadenceRecord.kt\nandroidx/health/connect/client/records/CyclingPedalingCadenceRecord\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,139:1\n1#2:140\n*E\n"})
/* loaded from: classes.dex */
public final class CyclingPedalingCadenceRecord implements SeriesRecord<Sample> {
    private static final double MAX_RPM = 10000.0d;

    @JvmField
    @NotNull
    public static final AggregateMetric<Double> RPM_AVG;

    @NotNull
    private static final String RPM_FIELD = "rpm";

    @JvmField
    @NotNull
    public static final AggregateMetric<Double> RPM_MAX;

    @JvmField
    @NotNull
    public static final AggregateMetric<Double> RPM_MIN;

    @NotNull
    private static final String TYPE = "CyclingPedalingCadenceSeries";

    @NotNull
    private final Instant endTime;

    @Nullable
    private final ZoneOffset endZoneOffset;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;

    @NotNull
    private final List<Sample> samples;

    @NotNull
    private final Instant startTime;

    @Nullable
    private final ZoneOffset startZoneOffset;

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Landroidx/health/connect/client/records/CyclingPedalingCadenceRecord$Sample;", "", "time", "Ljava/time/Instant;", "revolutionsPerMinute", "", "(Ljava/time/Instant;D)V", "getRevolutionsPerMinute", "()D", "getTime", "()Ljava/time/Instant;", "equals", "", "other", "hashCode", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Sample {
        private final double revolutionsPerMinute;

        @NotNull
        private final Instant time;

        public Sample(@NotNull Instant time, @FloatRange(from = 0.0d, to = 10000.0d) double d2) {
            Intrinsics.checkNotNullParameter(time, "time");
            this.time = time;
            this.revolutionsPerMinute = d2;
            UtilsKt.requireNonNegative(d2, "revolutionsPerMinute");
            UtilsKt.requireNotMore(Double.valueOf(d2), Double.valueOf(CyclingPedalingCadenceRecord.MAX_RPM), "revolutionsPerMinute");
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Sample)) {
                return false;
            }
            Sample sample = (Sample) other;
            return Intrinsics.areEqual(this.time, sample.time) && this.revolutionsPerMinute == sample.revolutionsPerMinute;
        }

        public final double getRevolutionsPerMinute() {
            return this.revolutionsPerMinute;
        }

        @NotNull
        public final Instant getTime() {
            return this.time;
        }

        public int hashCode() {
            return (this.time.hashCode() * 31) + c.a(this.revolutionsPerMinute);
        }
    }

    static {
        AggregateMetric.Companion companion = AggregateMetric.INSTANCE;
        RPM_AVG = companion.doubleMetric$connect_client_release(TYPE, AggregateMetric.AggregationType.AVERAGE, RPM_FIELD);
        RPM_MIN = companion.doubleMetric$connect_client_release(TYPE, AggregateMetric.AggregationType.MINIMUM, RPM_FIELD);
        RPM_MAX = companion.doubleMetric$connect_client_release(TYPE, AggregateMetric.AggregationType.MAXIMUM, RPM_FIELD);
    }

    public CyclingPedalingCadenceRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, @NotNull List<Sample> samples, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(samples, "samples");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.startTime = startTime;
        this.startZoneOffset = zoneOffset;
        this.endTime = endTime;
        this.endZoneOffset = zoneOffset2;
        this.samples = samples;
        this.metadata = metadata;
        if (!(!getStartTime().isAfter(getEndTime()))) {
            throw new IllegalArgumentException("startTime must not be after endTime.".toString());
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CyclingPedalingCadenceRecord)) {
            return false;
        }
        CyclingPedalingCadenceRecord cyclingPedalingCadenceRecord = (CyclingPedalingCadenceRecord) other;
        return Intrinsics.areEqual(getStartTime(), cyclingPedalingCadenceRecord.getStartTime()) && Intrinsics.areEqual(getStartZoneOffset(), cyclingPedalingCadenceRecord.getStartZoneOffset()) && Intrinsics.areEqual(getEndTime(), cyclingPedalingCadenceRecord.getEndTime()) && Intrinsics.areEqual(getEndZoneOffset(), cyclingPedalingCadenceRecord.getEndZoneOffset()) && Intrinsics.areEqual(getSamples(), cyclingPedalingCadenceRecord.getSamples()) && Intrinsics.areEqual(getMetadata(), cyclingPedalingCadenceRecord.getMetadata());
    }

    @Override // androidx.health.connect.client.records.IntervalRecord
    @NotNull
    public Instant getEndTime() {
        return this.endTime;
    }

    @Override // androidx.health.connect.client.records.IntervalRecord
    @Nullable
    public ZoneOffset getEndZoneOffset() {
        return this.endZoneOffset;
    }

    @Override // androidx.health.connect.client.records.Record
    @NotNull
    public androidx.health.connect.client.records.metadata.Metadata getMetadata() {
        return this.metadata;
    }

    @Override // androidx.health.connect.client.records.SeriesRecord
    @NotNull
    public List<Sample> getSamples() {
        return this.samples;
    }

    @Override // androidx.health.connect.client.records.IntervalRecord
    @NotNull
    public Instant getStartTime() {
        return this.startTime;
    }

    @Override // androidx.health.connect.client.records.IntervalRecord
    @Nullable
    public ZoneOffset getStartZoneOffset() {
        return this.startZoneOffset;
    }

    public int hashCode() {
        int iHashCode = getStartTime().hashCode() * 31;
        ZoneOffset startZoneOffset = getStartZoneOffset();
        int iHashCode2 = (((iHashCode + (startZoneOffset != null ? startZoneOffset.hashCode() : 0)) * 31) + getEndTime().hashCode()) * 31;
        ZoneOffset endZoneOffset = getEndZoneOffset();
        return ((((iHashCode2 + (endZoneOffset != null ? endZoneOffset.hashCode() : 0)) * 31) + getSamples().hashCode()) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ CyclingPedalingCadenceRecord(Instant instant, ZoneOffset zoneOffset, Instant instant2, ZoneOffset zoneOffset2, List list, androidx.health.connect.client.records.metadata.Metadata metadata, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, instant2, zoneOffset2, list, (i2 & 32) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
