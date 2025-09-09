package androidx.health.connect.client.impl.converters.changes;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.changes.Change;
import androidx.health.connect.client.changes.ChangesEvent;
import androidx.health.connect.client.changes.DeletionChange;
import androidx.health.connect.client.changes.UpsertionChange;
import androidx.health.connect.client.impl.converters.records.ProtoToRecordConvertersKt;
import androidx.health.platform.client.proto.ChangeProto;
import androidx.health.platform.client.proto.DataProto;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001H\u0002\u001a\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"extractApiChanges", "", "Landroidx/health/connect/client/changes/Change;", "changes", "Landroidx/health/platform/client/proto/ChangeProto$DataChange;", "toApiChangesEvent", "Landroidx/health/connect/client/changes/ChangesEvent;", "proto", "Landroidx/health/platform/client/proto/ChangeProto$ChangesEvent;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nChangesEventConverter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChangesEventConverter.kt\nandroidx/health/connect/client/impl/converters/changes/ChangesEventConverterKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,45:1\n1603#2,9:46\n1855#2:55\n1856#2:57\n1612#2:58\n1#3:56\n*S KotlinDebug\n*F\n+ 1 ChangesEventConverter.kt\nandroidx/health/connect/client/impl/converters/changes/ChangesEventConverterKt\n*L\n37#1:46,9\n37#1:55\n37#1:57\n37#1:58\n37#1:56\n*E\n"})
/* loaded from: classes.dex */
public final class ChangesEventConverterKt {
    private static final List<Change> extractApiChanges(List<ChangeProto.DataChange> list) {
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
    public static final ChangesEvent toApiChangesEvent(@NotNull ChangeProto.ChangesEvent proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        List<ChangeProto.DataChange> changesList = proto.getChangesList();
        Intrinsics.checkNotNullExpressionValue(changesList, "proto.changesList");
        List<Change> listExtractApiChanges = extractApiChanges(changesList);
        String nextChangesToken = proto.getNextChangesToken();
        Intrinsics.checkNotNullExpressionValue(nextChangesToken, "nextChangesToken");
        return new ChangesEvent(nextChangesToken, listExtractApiChanges);
    }
}
