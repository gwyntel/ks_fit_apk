package androidx.health.connect.client.impl.converters.request;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.impl.converters.aggregate.AggregateMetricToProtoKt;
import androidx.health.connect.client.impl.converters.time.TimeRangeFilterConverterKt;
import androidx.health.connect.client.records.metadata.DataOrigin;
import androidx.health.connect.client.request.AggregateGroupByDurationRequest;
import androidx.health.connect.client.request.AggregateGroupByPeriodRequest;
import androidx.health.connect.client.request.AggregateRequest;
import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.RequestProto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0003\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0004\u001a \u0010\u0005\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u00070\u0006*\b\u0012\u0004\u0012\u00020\n0\tH\u0002¨\u0006\u000b"}, d2 = {"toProto", "Landroidx/health/platform/client/proto/RequestProto$AggregateDataRequest;", "Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;", "Landroidx/health/connect/client/request/AggregateGroupByPeriodRequest;", "Landroidx/health/connect/client/request/AggregateRequest;", "toProtoList", "", "Landroidx/health/platform/client/proto/DataProto$DataOrigin;", "kotlin.jvm.PlatformType", "", "Landroidx/health/connect/client/records/metadata/DataOrigin;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nAggregateRequestToProto.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AggregateRequestToProto.kt\nandroidx/health/connect/client/impl/converters/request/AggregateRequestToProtoKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,57:1\n1549#2:58\n1620#2,3:59\n1549#2:62\n1620#2,3:63\n1549#2:66\n1620#2,3:67\n1549#2:70\n1620#2,3:71\n*S KotlinDebug\n*F\n+ 1 AggregateRequestToProto.kt\nandroidx/health/connect/client/impl/converters/request/AggregateRequestToProtoKt\n*L\n34#1:58\n34#1:59,3\n42#1:62\n42#1:63,3\n51#1:66\n51#1:67,3\n56#1:70\n56#1:71,3\n*E\n"})
/* loaded from: classes.dex */
public final class AggregateRequestToProtoKt {
    @NotNull
    public static final RequestProto.AggregateDataRequest toProto(@NotNull AggregateRequest aggregateRequest) {
        Intrinsics.checkNotNullParameter(aggregateRequest, "<this>");
        RequestProto.AggregateDataRequest.Builder builderAddAllDataOrigin = RequestProto.AggregateDataRequest.newBuilder().setTimeSpec(TimeRangeFilterConverterKt.toProto(aggregateRequest.getTimeRangeFilter())).addAllDataOrigin(toProtoList(aggregateRequest.getDataOriginFilter$connect_client_release()));
        Set<AggregateMetric<?>> metrics$connect_client_release = aggregateRequest.getMetrics$connect_client_release();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(metrics$connect_client_release, 10));
        Iterator<T> it = metrics$connect_client_release.iterator();
        while (it.hasNext()) {
            arrayList.add(AggregateMetricToProtoKt.toProto((AggregateMetric) it.next()));
        }
        RequestProto.AggregateDataRequest aggregateDataRequestBuild = builderAddAllDataOrigin.addAllMetricSpec(arrayList).build();
        Intrinsics.checkNotNullExpressionValue(aggregateDataRequestBuild, "newBuilder()\n        .se…oto() })\n        .build()");
        return aggregateDataRequestBuild;
    }

    private static final List<DataProto.DataOrigin> toProtoList(Set<DataOrigin> set) {
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(DataProto.DataOrigin.newBuilder().setApplicationId(((DataOrigin) it.next()).getPackageName()).build());
        }
        return arrayList;
    }

    @NotNull
    public static final RequestProto.AggregateDataRequest toProto(@NotNull AggregateGroupByDurationRequest aggregateGroupByDurationRequest) {
        Intrinsics.checkNotNullParameter(aggregateGroupByDurationRequest, "<this>");
        RequestProto.AggregateDataRequest.Builder builderAddAllDataOrigin = RequestProto.AggregateDataRequest.newBuilder().setTimeSpec(TimeRangeFilterConverterKt.toProto(aggregateGroupByDurationRequest.getTimeRangeFilter())).addAllDataOrigin(toProtoList(aggregateGroupByDurationRequest.getDataOriginFilter$connect_client_release()));
        Set<AggregateMetric<?>> metrics$connect_client_release = aggregateGroupByDurationRequest.getMetrics$connect_client_release();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(metrics$connect_client_release, 10));
        Iterator<T> it = metrics$connect_client_release.iterator();
        while (it.hasNext()) {
            arrayList.add(AggregateMetricToProtoKt.toProto((AggregateMetric) it.next()));
        }
        RequestProto.AggregateDataRequest aggregateDataRequestBuild = builderAddAllDataOrigin.addAllMetricSpec(arrayList).setSliceDurationMillis(aggregateGroupByDurationRequest.getTimeRangeSlicer().toMillis()).build();
        Intrinsics.checkNotNullExpressionValue(aggregateDataRequestBuild, "newBuilder()\n        .se…illis())\n        .build()");
        return aggregateDataRequestBuild;
    }

    @NotNull
    public static final RequestProto.AggregateDataRequest toProto(@NotNull AggregateGroupByPeriodRequest aggregateGroupByPeriodRequest) {
        Intrinsics.checkNotNullParameter(aggregateGroupByPeriodRequest, "<this>");
        RequestProto.AggregateDataRequest.Builder builderAddAllDataOrigin = RequestProto.AggregateDataRequest.newBuilder().setTimeSpec(TimeRangeFilterConverterKt.toProto(aggregateGroupByPeriodRequest.getTimeRangeFilter())).addAllDataOrigin(toProtoList(aggregateGroupByPeriodRequest.getDataOriginFilter$connect_client_release()));
        Set<AggregateMetric<?>> metrics$connect_client_release = aggregateGroupByPeriodRequest.getMetrics$connect_client_release();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(metrics$connect_client_release, 10));
        Iterator<T> it = metrics$connect_client_release.iterator();
        while (it.hasNext()) {
            arrayList.add(AggregateMetricToProtoKt.toProto((AggregateMetric) it.next()));
        }
        RequestProto.AggregateDataRequest aggregateDataRequestBuild = builderAddAllDataOrigin.addAllMetricSpec(arrayList).setSlicePeriod(aggregateGroupByPeriodRequest.getTimeRangeSlicer().toString()).build();
        Intrinsics.checkNotNullExpressionValue(aggregateDataRequestBuild, "newBuilder()\n        .se…tring())\n        .build()");
        return aggregateDataRequestBuild;
    }
}
