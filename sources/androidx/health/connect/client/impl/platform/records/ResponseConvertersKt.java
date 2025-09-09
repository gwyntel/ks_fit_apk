package androidx.health.connect.client.impl.platform.records;

import android.health.connect.AggregateRecordsGroupedByDurationResponse;
import android.health.connect.AggregateRecordsGroupedByPeriodResponse;
import android.health.connect.AggregateRecordsResponse;
import android.health.connect.datatypes.AggregationType;
import android.health.connect.datatypes.DataOrigin;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.aggregate.AggregationResult;
import androidx.health.connect.client.aggregate.AggregationResultGroupedByDuration;
import androidx.health.connect.client.aggregate.AggregationResultGroupedByPeriod;
import androidx.health.connect.client.units.Energy;
import androidx.health.connect.client.units.Mass;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a^\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u001a\u0010\u0006\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00072$\b\u0002\u0010\t\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b\u0012\u000e\u0012\f\u0012\b\u0012\u00060\nj\u0002`\u000b0\u00030\u0007H\u0001\u001a.\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0018\u0010\u0010\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00050\rH\u0001\u001a.\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00120\r2\u0018\u0010\u0010\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00050\rH\u0001\u001a$\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00050\u00152\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003\u001a$\u0010\u0013\u001a\u00020\u0016*\b\u0012\u0004\u0012\u00020\u00050\u00172\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003\u001a4\u0010\u0013\u001a\u00020\u0016*\b\u0012\u0004\u0012\u00020\u00050\u00172\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019\u001a$\u0010\u0013\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00050\u001b2\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003¨\u0006\u001c"}, d2 = {"buildAggregationResult", "Landroidx/health/connect/client/aggregate/AggregationResult;", "metrics", "", "Landroidx/health/connect/client/aggregate/AggregateMetric;", "", "aggregationValueGetter", "Lkotlin/Function1;", "Landroid/health/connect/datatypes/AggregationType;", "platformDataOriginsGetter", "Landroid/health/connect/datatypes/DataOrigin;", "Landroidx/health/connect/client/impl/platform/records/PlatformDataOrigin;", "getDoubleMetricValues", "", "", "", "metricValueMap", "getLongMetricValues", "", "toSdkResponse", "Landroidx/health/connect/client/aggregate/AggregationResultGroupedByDuration;", "Landroid/health/connect/AggregateRecordsGroupedByDurationResponse;", "Landroidx/health/connect/client/aggregate/AggregationResultGroupedByPeriod;", "Landroid/health/connect/AggregateRecordsGroupedByPeriodResponse;", "bucketStartTime", "Ljava/time/LocalDateTime;", "bucketEndTime", "Landroid/health/connect/AggregateRecordsResponse;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RequiresApi(api = 34)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nResponseConverters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ResponseConverters.kt\nandroidx/health/connect/client/impl/platform/records/ResponseConvertersKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,143:1\n1855#2:144\n1856#2:146\n1446#2,2:147\n1549#2:149\n1620#2,3:150\n1448#2,3:153\n1#3:145\n215#4,2:156\n215#4,2:158\n*S KotlinDebug\n*F\n+ 1 ResponseConverters.kt\nandroidx/health/connect/client/impl/platform/records/ResponseConvertersKt\n*L\n81#1:144\n81#1:146\n88#1:147,2\n89#1:149\n89#1:150,3\n88#1:153,3\n99#1:156,2\n115#1:158,2\n*E\n"})
/* loaded from: classes.dex */
public final class ResponseConvertersKt {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.health.connect.client.impl.platform.records.ResponseConvertersKt$toSdkResponse$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C03201 extends FunctionReferenceImpl implements Function1<AggregationType<Object>, Object> {
        C03201(Object obj) {
            super(1, obj, ft.a(), TmpConstant.PROPERTY_IDENTIFIER_GET, "get(Landroid/health/connect/datatypes/AggregationType;)Ljava/lang/Object;", 0);
        }

        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(@NotNull AggregationType<Object> p02) {
            Intrinsics.checkNotNullParameter(p02, "p0");
            return androidx.health.connect.client.impl.u.a(this.receiver).get(p02);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(AggregationType<Object> aggregationType) {
            return invoke2(ir.a(aggregationType));
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.health.connect.client.impl.platform.records.ResponseConvertersKt$toSdkResponse$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<AggregationType<Object>, Set<DataOrigin>> {
        AnonymousClass2(Object obj) {
            super(1, obj, ft.a(), "getDataOrigins", "getDataOrigins(Landroid/health/connect/datatypes/AggregationType;)Ljava/util/Set;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Set<DataOrigin> invoke(AggregationType<Object> aggregationType) {
            return invoke2(ir.a(aggregationType));
        }

        @NotNull
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Set<DataOrigin> invoke2(@NotNull AggregationType<Object> p02) {
            Intrinsics.checkNotNullParameter(p02, "p0");
            return androidx.health.connect.client.impl.u.a(this.receiver).getDataOrigins(p02);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.health.connect.client.impl.platform.records.ResponseConvertersKt$toSdkResponse$3, reason: invalid class name */
    /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1<AggregationType<Object>, Object> {
        AnonymousClass3(Object obj) {
            super(1, obj, ht.a(), TmpConstant.PROPERTY_IDENTIFIER_GET, "get(Landroid/health/connect/datatypes/AggregationType;)Ljava/lang/Object;", 0);
        }

        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(@NotNull AggregationType<Object> p02) {
            Intrinsics.checkNotNullParameter(p02, "p0");
            return androidx.health.connect.client.impl.v.a(this.receiver).get(p02);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(AggregationType<Object> aggregationType) {
            return invoke2(ir.a(aggregationType));
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.health.connect.client.impl.platform.records.ResponseConvertersKt$toSdkResponse$4, reason: invalid class name */
    /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1<AggregationType<Object>, Object> {
        AnonymousClass4(Object obj) {
            super(1, obj, lt.a(), TmpConstant.PROPERTY_IDENTIFIER_GET, "get(Landroid/health/connect/datatypes/AggregationType;)Ljava/lang/Object;", 0);
        }

        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(@NotNull AggregationType<Object> p02) {
            Intrinsics.checkNotNullParameter(p02, "p0");
            return androidx.health.connect.client.impl.z.a(this.receiver).get(p02);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(AggregationType<Object> aggregationType) {
            return invoke2(ir.a(aggregationType));
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.health.connect.client.impl.platform.records.ResponseConvertersKt$toSdkResponse$5, reason: invalid class name */
    /* synthetic */ class AnonymousClass5 extends FunctionReferenceImpl implements Function1<AggregationType<Object>, Object> {
        AnonymousClass5(Object obj) {
            super(1, obj, lt.a(), TmpConstant.PROPERTY_IDENTIFIER_GET, "get(Landroid/health/connect/datatypes/AggregationType;)Ljava/lang/Object;", 0);
        }

        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(@NotNull AggregationType<Object> p02) {
            Intrinsics.checkNotNullParameter(p02, "p0");
            return androidx.health.connect.client.impl.z.a(this.receiver).get(p02);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(AggregationType<Object> aggregationType) {
            return invoke2(ir.a(aggregationType));
        }
    }

    @VisibleForTesting
    @NotNull
    public static final AggregationResult buildAggregationResult(@NotNull Set<? extends AggregateMetric<? extends Object>> metrics, @NotNull Function1<? super AggregationType<Object>, ? extends Object> aggregationValueGetter, @NotNull Function1<? super AggregationType<Object>, ? extends Set<DataOrigin>> platformDataOriginsGetter) {
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        Intrinsics.checkNotNullParameter(aggregationValueGetter, "aggregationValueGetter");
        Intrinsics.checkNotNullParameter(platformDataOriginsGetter, "platformDataOriginsGetter");
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        Iterator<T> it = metrics.iterator();
        while (it.hasNext()) {
            AggregateMetric aggregateMetric = (AggregateMetric) it.next();
            Object objInvoke = aggregationValueGetter.invoke(RequestConvertersKt.toAggregationType(aggregateMetric));
            if (objInvoke != null) {
                mapCreateMapBuilder.put(aggregateMetric, objInvoke);
            }
        }
        Map mapBuild = MapsKt.build(mapCreateMapBuilder);
        Map<String, Long> longMetricValues = getLongMetricValues(mapBuild);
        Map<String, Double> doubleMetricValues = getDoubleMetricValues(mapBuild);
        HashSet hashSet = new HashSet();
        Iterator<T> it2 = metrics.iterator();
        while (it2.hasNext()) {
            Set<DataOrigin> setInvoke = platformDataOriginsGetter.invoke(RequestConvertersKt.toAggregationType((AggregateMetric) it2.next()));
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setInvoke, 10));
            Iterator<T> it3 = setInvoke.iterator();
            while (it3.hasNext()) {
                arrayList.add(MetadataConvertersKt.toSdkDataOrigin(at.a(it3.next())));
            }
            CollectionsKt.addAll(hashSet, arrayList);
        }
        return new AggregationResult(longMetricValues, doubleMetricValues, hashSet);
    }

    public static /* synthetic */ AggregationResult buildAggregationResult$default(Set set, Function1 function1, Function1 function12, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            function12 = new Function1<AggregationType<Object>, Set<? extends DataOrigin>>() { // from class: androidx.health.connect.client.impl.platform.records.ResponseConvertersKt.buildAggregationResult.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Set<? extends DataOrigin> invoke(AggregationType<Object> aggregationType) {
                    return invoke2(ir.a(aggregationType));
                }

                @NotNull
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final Set<DataOrigin> invoke2(@NotNull AggregationType<Object> aggregationType) {
                    Intrinsics.checkNotNullParameter(aggregationType, "<anonymous parameter 0>");
                    return SetsKt.emptySet();
                }
            };
        }
        return buildAggregationResult(set, function1, function12);
    }

    @VisibleForTesting
    @NotNull
    public static final Map<String, Double> getDoubleMetricValues(@NotNull Map<AggregateMetric<Object>, ? extends Object> metricValueMap) {
        Intrinsics.checkNotNullParameter(metricValueMap, "metricValueMap");
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (Map.Entry<AggregateMetric<Object>, ? extends Object> entry : metricValueMap.entrySet()) {
            AggregateMetric<Object> key = entry.getKey();
            Object value = entry.getValue();
            if (AggregationMappingsKt.getDOUBLE_AGGREGATION_METRIC_TYPE_MAP().containsKey(key)) {
                String metricKey$connect_client_release = key.getMetricKey$connect_client_release();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Double");
                mapCreateMapBuilder.put(metricKey$connect_client_release, (Double) value);
            } else if (AggregationMappingsKt.getENERGY_AGGREGATION_METRIC_TYPE_MAP().containsKey(key)) {
                String metricKey$connect_client_release2 = key.getMetricKey$connect_client_release();
                Energy.Companion companion = Energy.INSTANCE;
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type android.health.connect.datatypes.units.Energy");
                mapCreateMapBuilder.put(metricKey$connect_client_release2, Double.valueOf(companion.calories(dt.a(value).getInCalories()).getKilocalories()));
            } else if (AggregationMappingsKt.getLENGTH_AGGREGATION_METRIC_TYPE_MAP().containsKey(key)) {
                String metricKey$connect_client_release3 = key.getMetricKey$connect_client_release();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type android.health.connect.datatypes.units.Length{ androidx.health.connect.client.impl.platform.records.PlatformRecordAliasesKt.PlatformLength }");
                mapCreateMapBuilder.put(metricKey$connect_client_release3, Double.valueOf(ps.a(value).getInMeters()));
            } else if (AggregationMappingsKt.getGRAMS_AGGREGATION_METRIC_TYPE_MAP().containsKey(key)) {
                String metricKey$connect_client_release4 = key.getMetricKey$connect_client_release();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type android.health.connect.datatypes.units.Mass{ androidx.health.connect.client.impl.platform.records.PlatformRecordAliasesKt.PlatformMass }");
                mapCreateMapBuilder.put(metricKey$connect_client_release4, Double.valueOf(rs.a(value).getInGrams()));
            } else if (AggregationMappingsKt.getKILOGRAMS_AGGREGATION_METRIC_TYPE_MAP().containsKey(key)) {
                String metricKey$connect_client_release5 = key.getMetricKey$connect_client_release();
                Mass.Companion companion2 = Mass.INSTANCE;
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type android.health.connect.datatypes.units.Mass{ androidx.health.connect.client.impl.platform.records.PlatformRecordAliasesKt.PlatformMass }");
                mapCreateMapBuilder.put(metricKey$connect_client_release5, Double.valueOf(companion2.grams(rs.a(value).getInGrams()).getKilograms()));
            } else if (AggregationMappingsKt.getPOWER_AGGREGATION_METRIC_TYPE_MAP().containsKey(key)) {
                String metricKey$connect_client_release6 = key.getMetricKey$connect_client_release();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type android.health.connect.datatypes.units.Power{ androidx.health.connect.client.impl.platform.records.PlatformRecordAliasesKt.PlatformPower }");
                mapCreateMapBuilder.put(metricKey$connect_client_release6, Double.valueOf(ts.a(value).getInWatts()));
            } else if (AggregationMappingsKt.getVOLUME_AGGREGATION_METRIC_TYPE_MAP().containsKey(key)) {
                String metricKey$connect_client_release7 = key.getMetricKey$connect_client_release();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type android.health.connect.datatypes.units.Volume");
                mapCreateMapBuilder.put(metricKey$connect_client_release7, Double.valueOf(ms.a(value).getInLiters()));
            }
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    @VisibleForTesting
    @NotNull
    public static final Map<String, Long> getLongMetricValues(@NotNull Map<AggregateMetric<Object>, ? extends Object> metricValueMap) {
        Intrinsics.checkNotNullParameter(metricValueMap, "metricValueMap");
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (Map.Entry<AggregateMetric<Object>, ? extends Object> entry : metricValueMap.entrySet()) {
            AggregateMetric<Object> key = entry.getKey();
            Object value = entry.getValue();
            if (AggregationMappingsKt.getDURATION_AGGREGATION_METRIC_TYPE_MAP().containsKey(key) || AggregationMappingsKt.getLONG_AGGREGATION_METRIC_TYPE_MAP().containsKey(key)) {
                String metricKey$connect_client_release = key.getMetricKey$connect_client_release();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Long");
                mapCreateMapBuilder.put(metricKey$connect_client_release, (Long) value);
            }
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    @NotNull
    public static final AggregationResult toSdkResponse(@NotNull AggregateRecordsResponse<Object> aggregateRecordsResponse, @NotNull Set<? extends AggregateMetric<? extends Object>> metrics) {
        Intrinsics.checkNotNullParameter(aggregateRecordsResponse, "<this>");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        return buildAggregationResult(metrics, new C03201(aggregateRecordsResponse), new AnonymousClass2(aggregateRecordsResponse));
    }

    @NotNull
    public static final AggregationResultGroupedByDuration toSdkResponse(@NotNull AggregateRecordsGroupedByDurationResponse<Object> aggregateRecordsGroupedByDurationResponse, @NotNull Set<? extends AggregateMetric<? extends Object>> metrics) {
        Intrinsics.checkNotNullParameter(aggregateRecordsGroupedByDurationResponse, "<this>");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        AggregationResult aggregationResultBuildAggregationResult$default = buildAggregationResult$default(metrics, new AnonymousClass3(aggregateRecordsGroupedByDurationResponse), null, 4, null);
        Instant startTime = aggregateRecordsGroupedByDurationResponse.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        Instant endTime = aggregateRecordsGroupedByDurationResponse.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset zoneOffset = aggregateRecordsGroupedByDurationResponse.getZoneOffset(RequestConvertersKt.toAggregationType((AggregateMetric) CollectionsKt.first(metrics)));
        if (zoneOffset == null) {
            zoneOffset = ZoneOffset.systemDefault().getRules().getOffset(aggregateRecordsGroupedByDurationResponse.getStartTime());
        }
        Intrinsics.checkNotNullExpressionValue(zoneOffset, "getZoneOffset(metrics.fi…ules.getOffset(startTime)");
        return new AggregationResultGroupedByDuration(aggregationResultBuildAggregationResult$default, startTime, endTime, zoneOffset);
    }

    @NotNull
    public static final AggregationResultGroupedByPeriod toSdkResponse(@NotNull AggregateRecordsGroupedByPeriodResponse<Object> aggregateRecordsGroupedByPeriodResponse, @NotNull Set<? extends AggregateMetric<? extends Object>> metrics) {
        Intrinsics.checkNotNullParameter(aggregateRecordsGroupedByPeriodResponse, "<this>");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        AggregationResult aggregationResultBuildAggregationResult$default = buildAggregationResult$default(metrics, new AnonymousClass4(aggregateRecordsGroupedByPeriodResponse), null, 4, null);
        LocalDateTime startTime = aggregateRecordsGroupedByPeriodResponse.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        LocalDateTime endTime = aggregateRecordsGroupedByPeriodResponse.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        return new AggregationResultGroupedByPeriod(aggregationResultBuildAggregationResult$default, startTime, endTime);
    }

    @NotNull
    public static final AggregationResultGroupedByPeriod toSdkResponse(@NotNull AggregateRecordsGroupedByPeriodResponse<Object> aggregateRecordsGroupedByPeriodResponse, @NotNull Set<? extends AggregateMetric<? extends Object>> metrics, @NotNull LocalDateTime bucketStartTime, @NotNull LocalDateTime bucketEndTime) {
        Intrinsics.checkNotNullParameter(aggregateRecordsGroupedByPeriodResponse, "<this>");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        Intrinsics.checkNotNullParameter(bucketStartTime, "bucketStartTime");
        Intrinsics.checkNotNullParameter(bucketEndTime, "bucketEndTime");
        return new AggregationResultGroupedByPeriod(buildAggregationResult$default(metrics, new AnonymousClass5(aggregateRecordsGroupedByPeriodResponse), null, 4, null), bucketStartTime, bucketEndTime);
    }
}
