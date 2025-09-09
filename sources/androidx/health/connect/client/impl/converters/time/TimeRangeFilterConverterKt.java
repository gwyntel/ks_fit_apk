package androidx.health.connect.client.impl.converters.time;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.time.TimeRangeFilter;
import androidx.health.platform.client.proto.TimeProto;
import java.time.Instant;
import java.time.LocalDateTime;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"toProto", "Landroidx/health/platform/client/proto/TimeProto$TimeSpec;", "Landroidx/health/connect/client/time/TimeRangeFilter;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nTimeRangeFilterConverter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TimeRangeFilterConverter.kt\nandroidx/health/connect/client/impl/converters/time/TimeRangeFilterConverterKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,39:1\n1#2:40\n*E\n"})
/* loaded from: classes.dex */
public final class TimeRangeFilterConverterKt {
    @NotNull
    public static final TimeProto.TimeSpec toProto(@NotNull TimeRangeFilter timeRangeFilter) {
        Intrinsics.checkNotNullParameter(timeRangeFilter, "<this>");
        TimeProto.TimeSpec.Builder builderNewBuilder = TimeProto.TimeSpec.newBuilder();
        Instant startTime = timeRangeFilter.getStartTime();
        if (startTime != null) {
            builderNewBuilder.setStartTimeEpochMs(startTime.toEpochMilli());
        }
        Instant endTime = timeRangeFilter.getEndTime();
        if (endTime != null) {
            builderNewBuilder.setEndTimeEpochMs(endTime.toEpochMilli());
        }
        LocalDateTime localStartTime = timeRangeFilter.getLocalStartTime();
        if (localStartTime != null) {
            builderNewBuilder.setStartLocalDateTime(localStartTime.toString());
        }
        LocalDateTime localEndTime = timeRangeFilter.getLocalEndTime();
        if (localEndTime != null) {
            builderNewBuilder.setEndLocalDateTime(localEndTime.toString());
        }
        TimeProto.TimeSpec timeSpecBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(timeSpecBuild, "newBuilder()\n        .ap…       }\n        .build()");
        return timeSpecBuild;
    }
}
