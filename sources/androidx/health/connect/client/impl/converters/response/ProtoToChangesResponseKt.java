package androidx.health.connect.client.impl.converters.response;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.changes.Change;
import androidx.health.connect.client.changes.DeletionChange;
import androidx.health.connect.client.changes.UpsertionChange;
import androidx.health.connect.client.impl.converters.records.ProtoToRecordConvertersKt;
import androidx.health.connect.client.response.ChangesResponse;
import androidx.health.platform.client.proto.ChangeProto;
import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.ResponseProto;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001H\u0002\u001a\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"extractChanges", "", "Landroidx/health/connect/client/changes/Change;", "changes", "Landroidx/health/platform/client/proto/ChangeProto$DataChange;", "toChangesResponse", "Landroidx/health/connect/client/response/ChangesResponse;", "proto", "Landroidx/health/platform/client/proto/ResponseProto$GetChangesResponse;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nProtoToChangesResponse.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProtoToChangesResponse.kt\nandroidx/health/connect/client/impl/converters/response/ProtoToChangesResponseKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,50:1\n1603#2,9:51\n1855#2:60\n1856#2:62\n1612#2:63\n1#3:61\n*S KotlinDebug\n*F\n+ 1 ProtoToChangesResponse.kt\nandroidx/health/connect/client/impl/converters/response/ProtoToChangesResponseKt\n*L\n42#1:51,9\n42#1:60\n42#1:62\n42#1:63\n42#1:61\n*E\n"})
/* loaded from: classes.dex */
public final class ProtoToChangesResponseKt {
    private static final List<Change> extractChanges(List<ChangeProto.DataChange> list) {
        Change upsertionChange;
        ArrayList arrayList = new ArrayList();
        for (ChangeProto.DataChange dataChange : list) {
            if (dataChange.hasDeleteUid()) {
                String deleteUid = dataChange.getDeleteUid();
                Intrinsics.checkNotNullExpressionValue(deleteUid, "it.deleteUid");
                upsertionChange = new DeletionChange(deleteUid);
            } else if (dataChange.hasUpsertDataPoint()) {
                DataProto.DataPoint upsertDataPoint = dataChange.getUpsertDataPoint();
                Intrinsics.checkNotNullExpressionValue(upsertDataPoint, "it.upsertDataPoint");
                upsertionChange = new UpsertionChange(ProtoToRecordConvertersKt.toRecord(upsertDataPoint));
            } else {
                upsertionChange = null;
            }
            if (upsertionChange != null) {
                arrayList.add(upsertionChange);
            }
        }
        return arrayList;
    }

    @NotNull
    public static final ChangesResponse toChangesResponse(@NotNull ResponseProto.GetChangesResponse proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        List<ChangeProto.DataChange> changesList = proto.getChangesList();
        Intrinsics.checkNotNullExpressionValue(changesList, "proto.changesList");
        List<Change> listExtractChanges = extractChanges(changesList);
        String nextChangesToken = proto.getNextChangesToken();
        Intrinsics.checkNotNullExpressionValue(nextChangesToken, "proto.nextChangesToken");
        return new ChangesResponse(listExtractChanges, nextChangesToken, proto.getHasMore(), proto.getChangesTokenExpired());
    }
}
