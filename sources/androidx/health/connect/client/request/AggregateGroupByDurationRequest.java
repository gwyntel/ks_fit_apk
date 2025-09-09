package androidx.health.connect.client.request;

import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.records.metadata.DataOrigin;
import androidx.health.connect.client.time.TimeRangeFilter;
import java.time.Duration;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B7\u0012\u0010\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0018"}, d2 = {"Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;", "", "metrics", "", "Landroidx/health/connect/client/aggregate/AggregateMetric;", "timeRangeFilter", "Landroidx/health/connect/client/time/TimeRangeFilter;", "timeRangeSlicer", "Ljava/time/Duration;", "dataOriginFilter", "Landroidx/health/connect/client/records/metadata/DataOrigin;", "(Ljava/util/Set;Landroidx/health/connect/client/time/TimeRangeFilter;Ljava/time/Duration;Ljava/util/Set;)V", "getDataOriginFilter$connect_client_release", "()Ljava/util/Set;", "getMetrics$connect_client_release", "getTimeRangeFilter$connect_client_release", "()Landroidx/health/connect/client/time/TimeRangeFilter;", "getTimeRangeSlicer$connect_client_release", "()Ljava/time/Duration;", "equals", "", "other", "hashCode", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AggregateGroupByDurationRequest {

    @NotNull
    private final Set<DataOrigin> dataOriginFilter;

    @NotNull
    private final Set<AggregateMetric<?>> metrics;

    @NotNull
    private final TimeRangeFilter timeRangeFilter;

    @NotNull
    private final Duration timeRangeSlicer;

    /* JADX WARN: Multi-variable type inference failed */
    public AggregateGroupByDurationRequest(@NotNull Set<? extends AggregateMetric<?>> metrics, @NotNull TimeRangeFilter timeRangeFilter, @NotNull Duration timeRangeSlicer, @NotNull Set<DataOrigin> dataOriginFilter) {
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        Intrinsics.checkNotNullParameter(timeRangeFilter, "timeRangeFilter");
        Intrinsics.checkNotNullParameter(timeRangeSlicer, "timeRangeSlicer");
        Intrinsics.checkNotNullParameter(dataOriginFilter, "dataOriginFilter");
        this.metrics = metrics;
        this.timeRangeFilter = timeRangeFilter;
        this.timeRangeSlicer = timeRangeSlicer;
        this.dataOriginFilter = dataOriginFilter;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(AggregateGroupByDurationRequest.class, other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type androidx.health.connect.client.request.AggregateGroupByDurationRequest");
        AggregateGroupByDurationRequest aggregateGroupByDurationRequest = (AggregateGroupByDurationRequest) other;
        return Intrinsics.areEqual(this.metrics, aggregateGroupByDurationRequest.metrics) && Intrinsics.areEqual(this.timeRangeFilter, aggregateGroupByDurationRequest.timeRangeFilter) && Intrinsics.areEqual(this.timeRangeSlicer, aggregateGroupByDurationRequest.timeRangeSlicer) && Intrinsics.areEqual(this.dataOriginFilter, aggregateGroupByDurationRequest.dataOriginFilter);
    }

    @NotNull
    public final Set<DataOrigin> getDataOriginFilter$connect_client_release() {
        return this.dataOriginFilter;
    }

    @NotNull
    public final Set<AggregateMetric<?>> getMetrics$connect_client_release() {
        return this.metrics;
    }

    @NotNull
    /* renamed from: getTimeRangeFilter$connect_client_release, reason: from getter */
    public final TimeRangeFilter getTimeRangeFilter() {
        return this.timeRangeFilter;
    }

    @NotNull
    /* renamed from: getTimeRangeSlicer$connect_client_release, reason: from getter */
    public final Duration getTimeRangeSlicer() {
        return this.timeRangeSlicer;
    }

    public int hashCode() {
        return (((((this.metrics.hashCode() * 31) + this.timeRangeFilter.hashCode()) * 31) + this.timeRangeSlicer.hashCode()) * 31) + this.dataOriginFilter.hashCode();
    }

    public /* synthetic */ AggregateGroupByDurationRequest(Set set, TimeRangeFilter timeRangeFilter, Duration duration, Set set2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, timeRangeFilter, duration, (i2 & 8) != 0 ? SetsKt.emptySet() : set2);
    }
}
