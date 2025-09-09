package androidx.health.connect.client.impl.platform.response;

import androidx.annotation.RequiresApi;
import androidx.health.connect.client.impl.w;
import androidx.health.connect.client.response.InsertRecordsResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@RequiresApi(api = 34)
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"toKtResponse", "Landroidx/health/connect/client/response/InsertRecordsResponse;", "Landroid/health/connect/InsertRecordsResponse;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nInsertRecordsResponseConverter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InsertRecordsResponseConverter.kt\nandroidx/health/connect/client/impl/platform/response/InsertRecordsResponseConverterKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,30:1\n1549#2:31\n1620#2,3:32\n*S KotlinDebug\n*F\n+ 1 InsertRecordsResponseConverter.kt\nandroidx/health/connect/client/impl/platform/response/InsertRecordsResponseConverterKt\n*L\n27#1:31\n27#1:32,3\n*E\n"})
/* loaded from: classes.dex */
public final class InsertRecordsResponseConverterKt {
    @NotNull
    public static final InsertRecordsResponse toKtResponse(@NotNull android.health.connect.InsertRecordsResponse insertRecordsResponse) {
        Intrinsics.checkNotNullParameter(insertRecordsResponse, "<this>");
        List records = insertRecordsResponse.getRecords();
        Intrinsics.checkNotNullExpressionValue(records, "records");
        List list = records;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String id = w.a(it.next()).getMetadata().getId();
            Intrinsics.checkNotNullExpressionValue(id, "record.metadata.id");
            arrayList.add(id);
        }
        return new InsertRecordsResponse(arrayList);
    }
}
