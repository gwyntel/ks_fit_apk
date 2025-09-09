package androidx.health.connect.client.aggregate;

import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.time.Instant;
import java.time.ZoneOffset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B'\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Landroidx/health/connect/client/aggregate/AggregationResultGroupedByDuration;", "", "result", "Landroidx/health/connect/client/aggregate/AggregationResult;", "startTime", "Ljava/time/Instant;", AUserTrack.UTKEY_END_TIME, "zoneOffset", "Ljava/time/ZoneOffset;", "(Landroidx/health/connect/client/aggregate/AggregationResult;Ljava/time/Instant;Ljava/time/Instant;Ljava/time/ZoneOffset;)V", "getEndTime", "()Ljava/time/Instant;", "getResult", "()Landroidx/health/connect/client/aggregate/AggregationResult;", "getStartTime", "getZoneOffset", "()Ljava/time/ZoneOffset;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAggregationResultGroupedByDuration.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AggregationResultGroupedByDuration.kt\nandroidx/health/connect/client/aggregate/AggregationResultGroupedByDuration\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n1#2:45\n*E\n"})
/* loaded from: classes.dex */
public final class AggregationResultGroupedByDuration {

    @NotNull
    private final Instant endTime;

    @NotNull
    private final AggregationResult result;

    @NotNull
    private final Instant startTime;

    @NotNull
    private final ZoneOffset zoneOffset;

    public AggregationResultGroupedByDuration(@NotNull AggregationResult result, @NotNull Instant startTime, @NotNull Instant endTime, @NotNull ZoneOffset zoneOffset) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(zoneOffset, "zoneOffset");
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
        this.zoneOffset = zoneOffset;
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("start time must be before end time".toString());
        }
    }

    @NotNull
    public final Instant getEndTime() {
        return this.endTime;
    }

    @NotNull
    public final AggregationResult getResult() {
        return this.result;
    }

    @NotNull
    public final Instant getStartTime() {
        return this.startTime;
    }

    @NotNull
    public final ZoneOffset getZoneOffset() {
        return this.zoneOffset;
    }
}
