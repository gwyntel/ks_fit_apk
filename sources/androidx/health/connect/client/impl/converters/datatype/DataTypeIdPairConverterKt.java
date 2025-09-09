package androidx.health.connect.client.impl.converters.datatype;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.records.Record;
import androidx.health.platform.client.proto.RequestProto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006\u001a*\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b¨\u0006\n"}, d2 = {"toDataTypeIdPairProto", "Landroidx/health/platform/client/proto/RequestProto$DataTypeIdPair;", "dataTypeKC", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "uid", "", "toDataTypeIdPairProtoList", "", "uidsList", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class DataTypeIdPairConverterKt {
    @NotNull
    public static final RequestProto.DataTypeIdPair toDataTypeIdPairProto(@NotNull KClass<? extends Record> dataTypeKC, @NotNull String uid) {
        Intrinsics.checkNotNullParameter(dataTypeKC, "dataTypeKC");
        Intrinsics.checkNotNullParameter(uid, "uid");
        RequestProto.DataTypeIdPair dataTypeIdPairBuild = RequestProto.DataTypeIdPair.newBuilder().setDataType(DataTypeConverterKt.toDataType(dataTypeKC)).setId(uid).build();
        Intrinsics.checkNotNullExpressionValue(dataTypeIdPairBuild, "newBuilder().setDataType…ype()).setId(uid).build()");
        return dataTypeIdPairBuild;
    }

    @NotNull
    public static final List<RequestProto.DataTypeIdPair> toDataTypeIdPairProtoList(@NotNull KClass<? extends Record> dataTypeKC, @NotNull List<String> uidsList) {
        Intrinsics.checkNotNullParameter(dataTypeKC, "dataTypeKC");
        Intrinsics.checkNotNullParameter(uidsList, "uidsList");
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = uidsList.iterator();
        while (it.hasNext()) {
            RequestProto.DataTypeIdPair dataTypeIdPairBuild = RequestProto.DataTypeIdPair.newBuilder().setDataType(DataTypeConverterKt.toDataType(dataTypeKC)).setId(it.next()).build();
            Intrinsics.checkNotNullExpressionValue(dataTypeIdPairBuild, "newBuilder()\n           …\n                .build()");
            arrayList.add(dataTypeIdPairBuild);
        }
        return arrayList;
    }
}
