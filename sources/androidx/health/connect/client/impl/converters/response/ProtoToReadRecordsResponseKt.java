package androidx.health.connect.client.impl.converters.response;

import androidx.annotation.RestrictTo;
import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.impl.converters.records.ProtoToRecordConvertersKt;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.response.ReadRecordsResponse;
import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.ResponseProto;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"toReadRecordsResponse", "Landroidx/health/connect/client/response/ReadRecordsResponse;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/connect/client/records/Record;", "proto", "Landroidx/health/platform/client/proto/ResponseProto$ReadDataRangeResponse;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nProtoToReadRecordsResponse.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProtoToReadRecordsResponse.kt\nandroidx/health/connect/client/impl/converters/response/ProtoToReadRecordsResponseKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,37:1\n1549#2:38\n1620#2,3:39\n*S KotlinDebug\n*F\n+ 1 ProtoToReadRecordsResponse.kt\nandroidx/health/connect/client/impl/converters/response/ProtoToReadRecordsResponseKt\n*L\n34#1:38\n34#1:39,3\n*E\n"})
/* loaded from: classes.dex */
public final class ProtoToReadRecordsResponseKt {
    @NotNull
    public static final <T extends Record> ReadRecordsResponse<T> toReadRecordsResponse(@NotNull ResponseProto.ReadDataRangeResponse proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        List<DataProto.DataPoint> dataPointList = proto.getDataPointList();
        Intrinsics.checkNotNullExpressionValue(dataPointList, "proto.dataPointList");
        List<DataProto.DataPoint> list = dataPointList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (DataProto.DataPoint it : list) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            Record record = ProtoToRecordConvertersKt.toRecord(it);
            Intrinsics.checkNotNull(record, "null cannot be cast to non-null type T of androidx.health.connect.client.impl.converters.response.ProtoToReadRecordsResponseKt.toReadRecordsResponse$lambda$0");
            arrayList.add(record);
        }
        return new ReadRecordsResponse<>(arrayList, proto.getPageToken());
    }
}
