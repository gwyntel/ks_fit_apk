package androidx.health.connect.client.aggregate;

import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.time.LocalDateTime;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Landroidx/health/connect/client/aggregate/AggregationResultGroupedByPeriod;", "", "result", "Landroidx/health/connect/client/aggregate/AggregationResult;", "startTime", "Ljava/time/LocalDateTime;", AUserTrack.UTKEY_END_TIME, "(Landroidx/health/connect/client/aggregate/AggregationResult;Ljava/time/LocalDateTime;Ljava/time/LocalDateTime;)V", "getEndTime", "()Ljava/time/LocalDateTime;", "getResult", "()Landroidx/health/connect/client/aggregate/AggregationResult;", "getStartTime", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAggregationResultGroupedByPeriod.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AggregationResultGroupedByPeriod.kt\nandroidx/health/connect/client/aggregate/AggregationResultGroupedByPeriod\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,39:1\n1#2:40\n*E\n"})
/* loaded from: classes.dex */
public final class AggregationResultGroupedByPeriod {

    @NotNull
    private final LocalDateTime endTime;

    @NotNull
    private final AggregationResult result;

    @NotNull
    private final LocalDateTime startTime;

    public AggregationResultGroupedByPeriod(@NotNull AggregationResult result, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
        if (!startTime.isBefore(g.a(endTime))) {
            throw new IllegalArgumentException("start time must be before end time".toString());
        }
    }

    @NotNull
    public final LocalDateTime getEndTime() {
        return this.endTime;
    }

    @NotNull
    public final AggregationResult getResult() {
        return this.result;
    }

    @NotNull
    public final LocalDateTime getStartTime() {
        return this.startTime;
    }
}
