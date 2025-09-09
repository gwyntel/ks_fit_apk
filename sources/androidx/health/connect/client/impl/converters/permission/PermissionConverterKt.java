package androidx.health.connect.client.impl.converters.permission;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.impl.converters.datatype.DataTypeConverterKt;
import androidx.health.connect.client.permission.HealthPermission;
import androidx.health.connect.client.records.Record;
import androidx.health.platform.client.proto.PermissionProto;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\f\u0010\u0004\u001a\u00020\u0003*\u00020\u0001H\u0002\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0007\u001a\n\u0010\b\u001a\u00020\u0007*\u00020\u0006¨\u0006\t"}, d2 = {"toAccessTypeProto", "Landroidx/health/platform/client/proto/PermissionProto$AccessType;", "accessType", "", "toAccessType", "toJetpackPermission", "Landroidx/health/connect/client/permission/HealthPermission;", "Landroidx/health/platform/client/proto/PermissionProto$Permission;", "toProtoPermission", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class PermissionConverterKt {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PermissionProto.AccessType.values().length];
            try {
                iArr[PermissionProto.AccessType.ACCESS_TYPE_WRITE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PermissionProto.AccessType.ACCESS_TYPE_READ.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final int toAccessType(PermissionProto.AccessType accessType) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()];
        if (i2 == 1) {
            return 2;
        }
        if (i2 == 2) {
            return 1;
        }
        throw new IllegalStateException("Unknown access type");
    }

    private static final PermissionProto.AccessType toAccessTypeProto(int i2) {
        return i2 != 1 ? i2 != 2 ? PermissionProto.AccessType.ACCESS_TYPE_UNKNOWN : PermissionProto.AccessType.ACCESS_TYPE_WRITE : PermissionProto.AccessType.ACCESS_TYPE_READ;
    }

    @NotNull
    public static final HealthPermission toJetpackPermission(@NotNull PermissionProto.Permission permission) {
        Intrinsics.checkNotNullParameter(permission, "<this>");
        String name = permission.getDataType().getName();
        Intrinsics.checkNotNullExpressionValue(name, "dataType.name");
        KClass<? extends Record> dataTypeKClass = DataTypeConverterKt.toDataTypeKClass(name);
        PermissionProto.AccessType accessType = permission.getAccessType();
        Intrinsics.checkNotNullExpressionValue(accessType, "accessType");
        return new HealthPermission(dataTypeKClass, toAccessType(accessType));
    }

    @NotNull
    public static final PermissionProto.Permission toProtoPermission(@NotNull HealthPermission healthPermission) {
        Intrinsics.checkNotNullParameter(healthPermission, "<this>");
        PermissionProto.Permission permissionBuild = PermissionProto.Permission.newBuilder().setDataType(DataTypeConverterKt.toDataType(healthPermission.getRecordType$connect_client_release())).setAccessType(toAccessTypeProto(healthPermission.getAccessType())).build();
        Intrinsics.checkNotNullExpressionValue(permissionBuild, "newBuilder()\n        .se…ssType))\n        .build()");
        return permissionBuild;
    }
}
