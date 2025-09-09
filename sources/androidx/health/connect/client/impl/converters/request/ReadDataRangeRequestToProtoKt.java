package androidx.health.connect.client.impl.converters.request;

import androidx.annotation.RestrictTo;
import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.impl.converters.datatype.DataTypeConverterKt;
import androidx.health.connect.client.impl.converters.time.TimeRangeFilterConverterKt;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.records.metadata.DataOrigin;
import androidx.health.connect.client.request.ReadRecordsRequest;
import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.RequestProto;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005¨\u0006\u0006"}, d2 = {"toReadDataRangeRequestProto", "Landroidx/health/platform/client/proto/RequestProto$ReadDataRangeRequest;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/connect/client/records/Record;", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/health/connect/client/request/ReadRecordsRequest;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nReadDataRangeRequestToProto.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadDataRangeRequestToProto.kt\nandroidx/health/connect/client/impl/converters/request/ReadDataRangeRequestToProtoKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,50:1\n1549#2:51\n1620#2,3:52\n1#3:55\n*S KotlinDebug\n*F\n+ 1 ReadDataRangeRequestToProto.kt\nandroidx/health/connect/client/impl/converters/request/ReadDataRangeRequestToProtoKt\n*L\n40#1:51\n40#1:52,3\n*E\n"})
/* loaded from: classes.dex */
public final class ReadDataRangeRequestToProtoKt {
    @NotNull
    public static final <T extends Record> RequestProto.ReadDataRangeRequest toReadDataRangeRequestProto(@NotNull ReadRecordsRequest<T> request) {
        Intrinsics.checkNotNullParameter(request, "request");
        RequestProto.ReadDataRangeRequest.Builder dataType = RequestProto.ReadDataRangeRequest.newBuilder().setDataType(DataTypeConverterKt.toDataType(request.getRecordType$connect_client_release()));
        dataType.setTimeSpec(TimeRangeFilterConverterKt.toProto(request.getTimeRangeFilter()));
        Set<DataOrigin> dataOriginFilter$connect_client_release = request.getDataOriginFilter$connect_client_release();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(dataOriginFilter$connect_client_release, 10));
        Iterator<T> it = dataOriginFilter$connect_client_release.iterator();
        while (it.hasNext()) {
            arrayList.add(DataProto.DataOrigin.newBuilder().setApplicationId(((DataOrigin) it.next()).getPackageName()).build());
        }
        dataType.addAllDataOriginFilters(arrayList);
        dataType.setAscOrdering(request.getAscendingOrder());
        dataType.setPageSize(request.getPageSize());
        String pageToken = request.getPageToken();
        if (pageToken != null) {
            dataType.setPageToken(pageToken);
        }
        GeneratedMessageLite generatedMessageLiteBuild = dataType.build();
        Intrinsics.checkNotNullExpressionValue(generatedMessageLiteBuild, "newBuilder()\n        .se…       }\n        .build()");
        return (RequestProto.ReadDataRangeRequest) generatedMessageLiteBuild;
    }
}
