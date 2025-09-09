package androidx.health.connect.client.impl.converters.records;

import androidx.annotation.RestrictTo;
import androidx.health.platform.client.proto.DataProto;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0005H\u0000\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0007H\u0000\u001a&\u0010\b\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00070\u000bH\u0000\u001a\u0010\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\rH\u0000\u001a\u0010\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0007H\u0000¨\u0006\u000f"}, d2 = {"boolVal", "Landroidx/health/platform/client/proto/DataProto$Value;", "value", "", "doubleVal", "", "enumVal", "", "enumValFromInt", "", "intToStringMap", "", "longVal", "", "stringVal", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nValueExt.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ValueExt.kt\nandroidx/health/connect/client/impl/converters/records/ValueExtKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n1#2:45\n*E\n"})
/* loaded from: classes.dex */
public final class ValueExtKt {
    @NotNull
    public static final DataProto.Value boolVal(boolean z2) {
        DataProto.Value valueBuild = DataProto.Value.newBuilder().setBooleanVal(z2).build();
        Intrinsics.checkNotNullExpressionValue(valueBuild, "newBuilder().setBooleanVal(value).build()");
        return valueBuild;
    }

    @NotNull
    public static final DataProto.Value doubleVal(double d2) {
        DataProto.Value valueBuild = DataProto.Value.newBuilder().setDoubleVal(d2).build();
        Intrinsics.checkNotNullExpressionValue(valueBuild, "newBuilder().setDoubleVal(value).build()");
        return valueBuild;
    }

    @NotNull
    public static final DataProto.Value enumVal(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        DataProto.Value valueBuild = DataProto.Value.newBuilder().setEnumVal(value).build();
        Intrinsics.checkNotNullExpressionValue(valueBuild, "newBuilder().setEnumVal(value).build()");
        return valueBuild;
    }

    @Nullable
    public static final DataProto.Value enumValFromInt(int i2, @NotNull Map<Integer, String> intToStringMap) {
        Intrinsics.checkNotNullParameter(intToStringMap, "intToStringMap");
        String str = intToStringMap.get(Integer.valueOf(i2));
        if (str != null) {
            return enumVal(str);
        }
        return null;
    }

    @NotNull
    public static final DataProto.Value longVal(long j2) {
        DataProto.Value valueBuild = DataProto.Value.newBuilder().setLongVal(j2).build();
        Intrinsics.checkNotNullExpressionValue(valueBuild, "newBuilder().setLongVal(value).build()");
        return valueBuild;
    }

    @NotNull
    public static final DataProto.Value stringVal(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        DataProto.Value valueBuild = DataProto.Value.newBuilder().setStringVal(value).build();
        Intrinsics.checkNotNullExpressionValue(valueBuild, "newBuilder().setStringVal(value).build()");
        return valueBuild;
    }
}
