package androidx.health.connect.client.records;

import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.time.Instant;
import java.time.ZoneOffset;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001a"}, d2 = {"Landroidx/health/connect/client/records/RestingHeartRateRecord;", "Landroidx/health/connect/client/records/InstantaneousRecord;", "time", "Ljava/time/Instant;", "zoneOffset", "Ljava/time/ZoneOffset;", "beatsPerMinute", "", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;JLandroidx/health/connect/client/records/metadata/Metadata;)V", "getBeatsPerMinute", "()J", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getTime", "()Ljava/time/Instant;", "getZoneOffset", "()Ljava/time/ZoneOffset;", "equals", "", "other", "", "hashCode", "", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RestingHeartRateRecord implements InstantaneousRecord {

    @JvmField
    @NotNull
    public static final AggregateMetric<Long> BPM_AVG;

    @NotNull
    private static final String BPM_FIELD_NAME = "bpm";

    @JvmField
    @NotNull
    public static final AggregateMetric<Long> BPM_MAX;

    @JvmField
    @NotNull
    public static final AggregateMetric<Long> BPM_MIN;

    @NotNull
    private static final String REST_HEART_RATE_TYPE_NAME = "RestingHeartRate";
    private final long beatsPerMinute;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;

    @NotNull
    private final Instant time;

    @Nullable
    private final ZoneOffset zoneOffset;

    static {
        AggregateMetric.Companion companion = AggregateMetric.INSTANCE;
        BPM_AVG = companion.longMetric$connect_client_release(REST_HEART_RATE_TYPE_NAME, AggregateMetric.AggregationType.AVERAGE, BPM_FIELD_NAME);
        BPM_MIN = companion.longMetric$connect_client_release(REST_HEART_RATE_TYPE_NAME, AggregateMetric.AggregationType.MINIMUM, BPM_FIELD_NAME);
        BPM_MAX = companion.longMetric$connect_client_release(REST_HEART_RATE_TYPE_NAME, AggregateMetric.AggregationType.MAXIMUM, BPM_FIELD_NAME);
    }

    public RestingHeartRateRecord(@NotNull Instant time, @Nullable ZoneOffset zoneOffset, long j2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(time, "time");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.time = time;
        this.zoneOffset = zoneOffset;
        this.beatsPerMinute = j2;
        this.metadata = metadata;
        UtilsKt.requireNonNegative(j2, "beatsPerMinute");
        UtilsKt.requireNotMore(Long.valueOf(j2), 300L, "beatsPerMinute");
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RestingHeartRateRecord)) {
            return false;
        }
        RestingHeartRateRecord restingHeartRateRecord = (RestingHeartRateRecord) other;
        return this.beatsPerMinute == restingHeartRateRecord.beatsPerMinute && Intrinsics.areEqual(getTime(), restingHeartRateRecord.getTime()) && Intrinsics.areEqual(getZoneOffset(), restingHeartRateRecord.getZoneOffset()) && Intrinsics.areEqual(getMetadata(), restingHeartRateRecord.getMetadata());
    }

    public final long getBeatsPerMinute() {
        return this.beatsPerMinute;
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
        int iA = ((g.a(this.beatsPerMinute) * 31) + getTime().hashCode()) * 31;
        ZoneOffset zoneOffset = getZoneOffset();
        return ((iA + (zoneOffset != null ? zoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ RestingHeartRateRecord(Instant instant, ZoneOffset zoneOffset, long j2, androidx.health.connect.client.records.metadata.Metadata metadata, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, j2, (i2 & 8) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
