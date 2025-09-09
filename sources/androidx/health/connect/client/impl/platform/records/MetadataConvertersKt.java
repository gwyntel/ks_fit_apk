package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.DataOrigin;
import android.health.connect.datatypes.Device;
import android.health.connect.datatypes.Metadata;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.time.Instant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0010\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00020\u0003H\u0000\u001a\u0010\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006*\u00020\u0007H\u0000\u001a\u0010\u0010\b\u001a\u00060\tj\u0002`\n*\u00020\u000bH\u0000\u001a\u0010\u0010\f\u001a\u00020\u0003*\u00060\u0001j\u0002`\u0002H\u0000\u001a\u0010\u0010\r\u001a\u00020\u0007*\u00060\u0005j\u0002`\u0006H\u0000\u001a\u0010\u0010\u000e\u001a\u00020\u000b*\u00060\tj\u0002`\nH\u0000¨\u0006\u000f"}, d2 = {"toPlatformDataOrigin", "Landroid/health/connect/datatypes/DataOrigin;", "Landroidx/health/connect/client/impl/platform/records/PlatformDataOrigin;", "Landroidx/health/connect/client/records/metadata/DataOrigin;", "toPlatformDevice", "Landroid/health/connect/datatypes/Device;", "Landroidx/health/connect/client/impl/platform/records/PlatformDevice;", "Landroidx/health/connect/client/records/metadata/Device;", "toPlatformMetadata", "Landroid/health/connect/datatypes/Metadata;", "Landroidx/health/connect/client/impl/platform/records/PlatformMetadata;", "Landroidx/health/connect/client/records/metadata/Metadata;", "toSdkDataOrigin", "toSdkDevice", "toSdkMetadata", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RequiresApi(api = 34)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nMetadataConverters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MetadataConverters.kt\nandroidx/health/connect/client/impl/platform/records/MetadataConvertersKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,78:1\n1#2:79\n*E\n"})
/* loaded from: classes.dex */
public final class MetadataConvertersKt {
    @NotNull
    public static final DataOrigin toPlatformDataOrigin(@NotNull androidx.health.connect.client.records.metadata.DataOrigin dataOrigin) {
        Intrinsics.checkNotNullParameter(dataOrigin, "<this>");
        DataOrigin.Builder builderA = i2.a();
        builderA.setPackageName(dataOrigin.getPackageName());
        DataOrigin dataOriginBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(dataOriginBuild, "PlatformDataOriginBuilde…me(packageName) }.build()");
        return dataOriginBuild;
    }

    @NotNull
    public static final Device toPlatformDevice(@NotNull androidx.health.connect.client.records.metadata.Device device) {
        Intrinsics.checkNotNullParameter(device, "<this>");
        Device.Builder builderA = h2.a();
        builderA.setType(device.getType());
        String manufacturer = device.getManufacturer();
        if (manufacturer != null) {
            builderA.setManufacturer(manufacturer);
        }
        String model = device.getModel();
        if (model != null) {
            builderA.setModel(model);
        }
        Device deviceBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(deviceBuild, "PlatformDeviceBuilder()\n…       }\n        .build()");
        return deviceBuild;
    }

    @NotNull
    public static final android.health.connect.datatypes.Metadata toPlatformMetadata(@NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Device platformDevice;
        Intrinsics.checkNotNullParameter(metadata, "<this>");
        Metadata.Builder builderA = j2.a();
        androidx.health.connect.client.records.metadata.Device device = metadata.getDevice();
        if (device != null && (platformDevice = toPlatformDevice(device)) != null) {
            builderA.setDevice(platformDevice);
        }
        builderA.setLastModifiedTime(metadata.getLastModifiedTime());
        builderA.setId(metadata.getId());
        builderA.setDataOrigin(toPlatformDataOrigin(metadata.getDataOrigin()));
        builderA.setClientRecordId(metadata.getClientRecordId());
        builderA.setClientRecordVersion(metadata.getClientRecordVersion());
        builderA.setRecordingMethod(IntDefMappingsKt.toPlatformRecordingMethod(metadata.getRecordingMethod()));
        android.health.connect.datatypes.Metadata metadataBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(metadataBuild, "PlatformMetadataBuilder(…       }\n        .build()");
        return metadataBuild;
    }

    @NotNull
    public static final androidx.health.connect.client.records.metadata.DataOrigin toSdkDataOrigin(@NotNull DataOrigin dataOrigin) {
        Intrinsics.checkNotNullParameter(dataOrigin, "<this>");
        String packageName = dataOrigin.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
        return new androidx.health.connect.client.records.metadata.DataOrigin(packageName);
    }

    @NotNull
    public static final androidx.health.connect.client.records.metadata.Device toSdkDevice(@NotNull Device device) {
        Intrinsics.checkNotNullParameter(device, "<this>");
        return new androidx.health.connect.client.records.metadata.Device(device.getManufacturer(), device.getModel(), device.getType());
    }

    @NotNull
    public static final androidx.health.connect.client.records.metadata.Metadata toSdkMetadata(@NotNull android.health.connect.datatypes.Metadata metadata) {
        Intrinsics.checkNotNullParameter(metadata, "<this>");
        String id = metadata.getId();
        DataOrigin dataOrigin = metadata.getDataOrigin();
        Intrinsics.checkNotNullExpressionValue(dataOrigin, "dataOrigin");
        androidx.health.connect.client.records.metadata.DataOrigin sdkDataOrigin = toSdkDataOrigin(dataOrigin);
        Instant lastModifiedTime = metadata.getLastModifiedTime();
        String clientRecordId = metadata.getClientRecordId();
        long clientRecordVersion = metadata.getClientRecordVersion();
        int sdkRecordingMethod = IntDefMappingsKt.toSdkRecordingMethod(metadata.getRecordingMethod());
        Device device = metadata.getDevice();
        Intrinsics.checkNotNullExpressionValue(device, "device");
        androidx.health.connect.client.records.metadata.Device sdkDevice = toSdkDevice(device);
        Intrinsics.checkNotNullExpressionValue(id, "id");
        Intrinsics.checkNotNullExpressionValue(lastModifiedTime, "lastModifiedTime");
        return new androidx.health.connect.client.records.metadata.Metadata(id, sdkDataOrigin, lastModifiedTime, clientRecordId, clientRecordVersion, sdkDevice, sdkRecordingMethod);
    }
}
