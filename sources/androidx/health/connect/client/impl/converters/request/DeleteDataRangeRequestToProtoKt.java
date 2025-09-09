package androidx.health.connect.client.impl.converters.request;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.impl.converters.datatype.DataTypeConverterKt;
import androidx.health.connect.client.impl.converters.time.TimeRangeFilterConverterKt;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.time.TimeRangeFilter;
import androidx.health.platform.client.proto.RequestProto;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"toDeleteDataRangeRequestProto", "Landroidx/health/platform/client/proto/RequestProto$DeleteDataRangeRequest;", "dataTypeKC", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "timeRangeFilter", "Landroidx/health/connect/client/time/TimeRangeFilter;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class DeleteDataRangeRequestToProtoKt {
    @NotNull
    public static final RequestProto.DeleteDataRangeRequest toDeleteDataRangeRequestProto(@NotNull KClass<? extends Record> dataTypeKC, @NotNull TimeRangeFilter timeRangeFilter) {
        Intrinsics.checkNotNullParameter(dataTypeKC, "dataTypeKC");
        Intrinsics.checkNotNullParameter(timeRangeFilter, "timeRangeFilter");
        RequestProto.DeleteDataRangeRequest deleteDataRangeRequestBuild = RequestProto.DeleteDataRangeRequest.newBuilder().addDataType(DataTypeConverterKt.toDataType(dataTypeKC)).setTimeSpec(TimeRangeFilterConverterKt.toProto(timeRangeFilter)).build();
        Intrinsics.checkNotNullExpressionValue(deleteDataRangeRequestBuild, "newBuilder()\n        .ad…Proto())\n        .build()");
        return deleteDataRangeRequestBuild;
    }
}
