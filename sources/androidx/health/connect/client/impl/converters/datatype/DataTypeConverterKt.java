package androidx.health.connect.client.impl.converters.datatype;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.records.Record;
import androidx.health.platform.client.proto.DataProto;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\u001a\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002*\u00020\u0001\u001a\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002*\u00020\u0005\u001a\u0012\u0010\u0006\u001a\u00020\u0005*\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002¨\u0006\u0007"}, d2 = {"toDataType", "Landroidx/health/platform/client/proto/DataProto$DataType;", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "toDataTypeKClass", "", "toDataTypeName", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class DataTypeConverterKt {
    @NotNull
    public static final DataProto.DataType toDataType(@NotNull KClass<? extends Record> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        DataProto.DataType dataTypeBuild = DataProto.DataType.newBuilder().setName(toDataTypeName(kClass)).build();
        Intrinsics.checkNotNullExpressionValue(dataTypeBuild, "newBuilder().setName(toDataTypeName()).build()");
        return dataTypeBuild;
    }

    @NotNull
    public static final KClass<? extends Record> toDataTypeKClass(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        KClass<? extends Record> kClass = RecordsTypeNameMapKt.getRECORDS_TYPE_NAME_MAP().get(str);
        if (kClass != null) {
            return kClass;
        }
        throw new UnsupportedOperationException("Not supported yet: " + str);
    }

    @NotNull
    public static final String toDataTypeName(@NotNull KClass<? extends Record> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        String str = RecordsTypeNameMapKt.getRECORDS_CLASS_NAME_MAP().get(kClass);
        if (str != null) {
            return str;
        }
        throw new UnsupportedOperationException("Not supported yet: " + kClass);
    }

    @NotNull
    public static final KClass<? extends Record> toDataTypeKClass(@NotNull DataProto.DataType dataType) {
        Intrinsics.checkNotNullParameter(dataType, "<this>");
        String name = dataType.getName();
        Intrinsics.checkNotNullExpressionValue(name, "name");
        return toDataTypeKClass(name);
    }
}
