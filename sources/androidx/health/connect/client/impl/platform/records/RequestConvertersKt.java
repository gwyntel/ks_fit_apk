package androidx.health.connect.client.impl.platform.records;

import android.health.connect.AggregateRecordsRequest;
import android.health.connect.LocalTimeRangeFilter;
import android.health.connect.ReadRecordsRequestUsingFilters;
import android.health.connect.TimeInstantRangeFilter;
import android.health.connect.changelog.ChangeLogTokenRequest;
import android.health.connect.datatypes.AggregationType;
import android.health.connect.datatypes.Record;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.records.metadata.DataOrigin;
import androidx.health.connect.client.request.AggregateGroupByDurationRequest;
import androidx.health.connect.client.request.AggregateGroupByPeriodRequest;
import androidx.health.connect.client.request.AggregateRequest;
import androidx.health.connect.client.request.ChangesTokenRequest;
import androidx.health.connect.client.request.ReadRecordsRequest;
import androidx.health.connect.client.time.TimeRangeFilter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00020\u0003\u001a\u0014\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005*\u00020\u0007H\u0002\u001a\n\u0010\b\u001a\u00020\t*\u00020\n\u001a\u0010\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f*\u00020\r\u001a\u0010\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f*\u00020\u000e\u001a\u0010\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f*\u00020\u000f\u001a\n\u0010\u000b\u001a\u00020\u0010*\u00020\u0011\u001a\u001a\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u0012*\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0014\u001a\n\u0010\u0016\u001a\u00020\u0017*\u00020\n¨\u0006\u0018"}, d2 = {"toAggregationType", "Landroid/health/connect/datatypes/AggregationType;", "", "Landroidx/health/connect/client/aggregate/AggregateMetric;", "toLocalDateTime", "Ljava/time/LocalDateTime;", "kotlin.jvm.PlatformType", "Ljava/time/Instant;", "toPlatformLocalTimeRangeFilter", "Landroid/health/connect/LocalTimeRangeFilter;", "Landroidx/health/connect/client/time/TimeRangeFilter;", "toPlatformRequest", "Landroid/health/connect/AggregateRecordsRequest;", "Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;", "Landroidx/health/connect/client/request/AggregateGroupByPeriodRequest;", "Landroidx/health/connect/client/request/AggregateRequest;", "Landroid/health/connect/changelog/ChangeLogTokenRequest;", "Landroidx/health/connect/client/request/ChangesTokenRequest;", "Landroid/health/connect/ReadRecordsRequestUsingFilters;", "Landroid/health/connect/datatypes/Record;", "Landroidx/health/connect/client/request/ReadRecordsRequest;", "Landroidx/health/connect/client/records/Record;", "toPlatformTimeRangeFilter", "Landroid/health/connect/TimeRangeFilter;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RequiresApi(api = 34)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nRequestConverters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RequestConverters.kt\nandroidx/health/connect/client/impl/platform/records/RequestConvertersKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,143:1\n1855#2,2:144\n1855#2,2:147\n1855#2,2:149\n1855#2,2:151\n1855#2,2:153\n1855#2,2:155\n1855#2,2:157\n1855#2,2:159\n1855#2,2:161\n1#3:146\n*S KotlinDebug\n*F\n+ 1 RequestConverters.kt\nandroidx/health/connect/client/impl/platform/records/RequestConvertersKt\n*L\n50#1:144,2\n94#1:147,2\n95#1:149,2\n103#1:151,2\n104#1:153,2\n112#1:155,2\n113#1:157,2\n124#1:159,2\n125#1:161,2\n*E\n"})
/* loaded from: classes.dex */
public final class RequestConvertersKt {
    @NotNull
    public static final AggregationType<Object> toAggregationType(@NotNull AggregateMetric<? extends Object> aggregateMetric) {
        Intrinsics.checkNotNullParameter(aggregateMetric, "<this>");
        AggregationType<Object> aggregationTypeA = ir.a(AggregationMappingsKt.getDOUBLE_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric));
        if (aggregationTypeA != null || (aggregationTypeA = ir.a(AggregationMappingsKt.getDURATION_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric))) != null || (aggregationTypeA = ir.a(AggregationMappingsKt.getENERGY_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric))) != null || (aggregationTypeA = ir.a(AggregationMappingsKt.getLENGTH_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric))) != null || (aggregationTypeA = ir.a(AggregationMappingsKt.getLONG_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric))) != null || (aggregationTypeA = ir.a(AggregationMappingsKt.getGRAMS_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric))) != null || (aggregationTypeA = ir.a(AggregationMappingsKt.getKILOGRAMS_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric))) != null || (aggregationTypeA = ir.a(AggregationMappingsKt.getPOWER_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric))) != null || (aggregationTypeA = ir.a(AggregationMappingsKt.getVOLUME_AGGREGATION_METRIC_TYPE_MAP().get(aggregateMetric))) != null) {
            return aggregationTypeA;
        }
        throw new IllegalArgumentException("Unsupported aggregation type " + aggregateMetric.getMetricKey$connect_client_release());
    }

    private static final LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, nr.a(ZoneOffset.UTC));
    }

    @NotNull
    public static final LocalTimeRangeFilter toPlatformLocalTimeRangeFilter(@NotNull TimeRangeFilter timeRangeFilter) {
        Intrinsics.checkNotNullParameter(timeRangeFilter, "<this>");
        if (timeRangeFilter.getLocalStartTime() != null || timeRangeFilter.getLocalEndTime() != null) {
            LocalTimeRangeFilter localTimeRangeFilterBuild = yr.a().setStartTime(timeRangeFilter.getLocalStartTime()).setEndTime(timeRangeFilter.getLocalEndTime()).build();
            Intrinsics.checkNotNullExpressionValue(localTimeRangeFilterBuild, "Builder()\n              …\n                .build()");
            return localTimeRangeFilterBuild;
        }
        if (timeRangeFilter.getStartTime() == null && timeRangeFilter.getEndTime() == null) {
            LocalTimeRangeFilter.Builder builderA = yr.a();
            Instant EPOCH = Instant.EPOCH;
            Intrinsics.checkNotNullExpressionValue(EPOCH, "EPOCH");
            LocalTimeRangeFilter localTimeRangeFilterBuild2 = builderA.setStartTime(toLocalDateTime(EPOCH)).build();
            Intrinsics.checkNotNullExpressionValue(localTimeRangeFilterBuild2, "Builder().setStartTime(I…oLocalDateTime()).build()");
            return localTimeRangeFilterBuild2;
        }
        LocalTimeRangeFilter.Builder builderA2 = yr.a();
        Instant startTime = timeRangeFilter.getStartTime();
        LocalTimeRangeFilter.Builder startTime2 = builderA2.setStartTime(startTime != null ? toLocalDateTime(startTime) : null);
        Instant endTime = timeRangeFilter.getEndTime();
        LocalTimeRangeFilter localTimeRangeFilterBuild3 = startTime2.setEndTime(endTime != null ? toLocalDateTime(endTime) : null).build();
        Intrinsics.checkNotNullExpressionValue(localTimeRangeFilterBuild3, "Builder()\n              …\n                .build()");
        return localTimeRangeFilterBuild3;
    }

    @NotNull
    public static final ReadRecordsRequestUsingFilters<? extends Record> toPlatformRequest(@NotNull ReadRecordsRequest<? extends androidx.health.connect.client.records.Record> readRecordsRequest) {
        Intrinsics.checkNotNullParameter(readRecordsRequest, "<this>");
        cs.a();
        ReadRecordsRequestUsingFilters.Builder pageSize = bs.a(RecordConvertersKt.toPlatformRecordClass(readRecordsRequest.getRecordType$connect_client_release())).setTimeRangeFilter(toPlatformTimeRangeFilter(readRecordsRequest.getTimeRangeFilter())).setPageSize(readRecordsRequest.getPageSize());
        Iterator<T> it = readRecordsRequest.getDataOriginFilter$connect_client_release().iterator();
        while (it.hasNext()) {
            pageSize.addDataOrigins(MetadataConvertersKt.toPlatformDataOrigin((DataOrigin) it.next()));
        }
        String pageToken = readRecordsRequest.getPageToken();
        if (pageToken != null) {
            pageSize.setPageToken(Long.parseLong(pageToken));
        }
        if (readRecordsRequest.getPageToken() == null) {
            pageSize.setAscending(readRecordsRequest.getAscendingOrder());
        }
        ReadRecordsRequestUsingFilters<? extends Record> readRecordsRequestUsingFiltersBuild = pageSize.build();
        Intrinsics.checkNotNullExpressionValue(readRecordsRequestUsingFiltersBuild, "Builder(recordType.toPla…       }\n        .build()");
        return readRecordsRequestUsingFiltersBuild;
    }

    @NotNull
    public static final android.health.connect.TimeRangeFilter toPlatformTimeRangeFilter(@NotNull TimeRangeFilter timeRangeFilter) {
        Intrinsics.checkNotNullParameter(timeRangeFilter, "<this>");
        if (timeRangeFilter.getStartTime() != null || timeRangeFilter.getEndTime() != null) {
            TimeInstantRangeFilter timeInstantRangeFilterBuild = xr.a().setStartTime(timeRangeFilter.getStartTime()).setEndTime(timeRangeFilter.getEndTime()).build();
            Intrinsics.checkNotNullExpressionValue(timeInstantRangeFilterBuild, "{\n        TimeInstantRan…me(endTime).build()\n    }");
            return gs.a(timeInstantRangeFilterBuild);
        }
        if (timeRangeFilter.getLocalStartTime() == null && timeRangeFilter.getLocalEndTime() == null) {
            TimeInstantRangeFilter timeInstantRangeFilterBuild2 = xr.a().setStartTime(Instant.EPOCH).build();
            Intrinsics.checkNotNullExpressionValue(timeInstantRangeFilterBuild2, "{\n        // Platform do…tant.EPOCH).build()\n    }");
            return gs.a(timeInstantRangeFilterBuild2);
        }
        LocalTimeRangeFilter localTimeRangeFilterBuild = yr.a().setStartTime(timeRangeFilter.getLocalStartTime()).setEndTime(timeRangeFilter.getLocalEndTime()).build();
        Intrinsics.checkNotNullExpressionValue(localTimeRangeFilterBuild, "{\n        LocalTimeRange…calEndTime).build()\n    }");
        return gs.a(localTimeRangeFilterBuild);
    }

    @NotNull
    public static final ChangeLogTokenRequest toPlatformRequest(@NotNull ChangesTokenRequest changesTokenRequest) {
        Intrinsics.checkNotNullParameter(changesTokenRequest, "<this>");
        ChangeLogTokenRequest.Builder builderA = wr.a();
        Iterator<T> it = changesTokenRequest.getDataOriginFilters$connect_client_release().iterator();
        while (it.hasNext()) {
            builderA.addDataOriginFilter(MetadataConvertersKt.toPlatformDataOrigin((DataOrigin) it.next()));
        }
        Iterator<T> it2 = changesTokenRequest.getRecordTypes$connect_client_release().iterator();
        while (it2.hasNext()) {
            builderA.addRecordType(RecordConvertersKt.toPlatformRecordClass((KClass) it2.next()));
        }
        ChangeLogTokenRequest changeLogTokenRequestBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(changeLogTokenRequestBuild, "Builder()\n        .apply…       }\n        .build()");
        return changeLogTokenRequestBuild;
    }

    @NotNull
    public static final AggregateRecordsRequest<Object> toPlatformRequest(@NotNull AggregateRequest aggregateRequest) {
        Intrinsics.checkNotNullParameter(aggregateRequest, "<this>");
        as.a();
        AggregateRecordsRequest.Builder builderA = zr.a(toPlatformTimeRangeFilter(aggregateRequest.getTimeRangeFilter()));
        Iterator<T> it = aggregateRequest.getDataOriginFilter$connect_client_release().iterator();
        while (it.hasNext()) {
            builderA.addDataOriginsFilter(MetadataConvertersKt.toPlatformDataOrigin((DataOrigin) it.next()));
        }
        Iterator<T> it2 = aggregateRequest.getMetrics$connect_client_release().iterator();
        while (it2.hasNext()) {
            builderA.addAggregationType(toAggregationType((AggregateMetric) it2.next()));
        }
        AggregateRecordsRequest<Object> aggregateRecordsRequestBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(aggregateRecordsRequestBuild, "Builder<Any>(timeRangeFi…       }\n        .build()");
        return aggregateRecordsRequestBuild;
    }

    @NotNull
    public static final AggregateRecordsRequest<Object> toPlatformRequest(@NotNull AggregateGroupByDurationRequest aggregateGroupByDurationRequest) {
        Intrinsics.checkNotNullParameter(aggregateGroupByDurationRequest, "<this>");
        as.a();
        AggregateRecordsRequest.Builder builderA = zr.a(toPlatformTimeRangeFilter(aggregateGroupByDurationRequest.getTimeRangeFilter()));
        Iterator<T> it = aggregateGroupByDurationRequest.getDataOriginFilter$connect_client_release().iterator();
        while (it.hasNext()) {
            builderA.addDataOriginsFilter(MetadataConvertersKt.toPlatformDataOrigin((DataOrigin) it.next()));
        }
        Iterator<T> it2 = aggregateGroupByDurationRequest.getMetrics$connect_client_release().iterator();
        while (it2.hasNext()) {
            builderA.addAggregationType(toAggregationType((AggregateMetric) it2.next()));
        }
        AggregateRecordsRequest<Object> aggregateRecordsRequestBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(aggregateRecordsRequestBuild, "Builder<Any>(timeRangeFi…       }\n        .build()");
        return aggregateRecordsRequestBuild;
    }

    @NotNull
    public static final AggregateRecordsRequest<Object> toPlatformRequest(@NotNull AggregateGroupByPeriodRequest aggregateGroupByPeriodRequest) {
        Intrinsics.checkNotNullParameter(aggregateGroupByPeriodRequest, "<this>");
        as.a();
        AggregateRecordsRequest.Builder builderA = zr.a(gs.a(toPlatformLocalTimeRangeFilter(aggregateGroupByPeriodRequest.getTimeRangeFilter())));
        Iterator<T> it = aggregateGroupByPeriodRequest.getDataOriginFilter$connect_client_release().iterator();
        while (it.hasNext()) {
            builderA.addDataOriginsFilter(MetadataConvertersKt.toPlatformDataOrigin((DataOrigin) it.next()));
        }
        Iterator<T> it2 = aggregateGroupByPeriodRequest.getMetrics$connect_client_release().iterator();
        while (it2.hasNext()) {
            builderA.addAggregationType(toAggregationType((AggregateMetric) it2.next()));
        }
        AggregateRecordsRequest<Object> aggregateRecordsRequestBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(aggregateRecordsRequestBuild, "Builder<Any>(\n          …       }\n        .build()");
        return aggregateRecordsRequestBuild;
    }
}
